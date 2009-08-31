/*******************************************************************************
 * Copyright (c) 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.tabbedproperties.uml.components;

// Start of user code for imports

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionListener;
import org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.api.providers.IPropertiesEditionPartProvider;
import org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.services.PropertiesContextService;
import org.eclipse.emf.eef.runtime.impl.services.PropertiesEditionPartProviderService;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.tabbedproperties.uml.parts.LinkEndDestructionDataPropertiesEditionPart;
import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.LinkEndDestructionData;
import org.eclipse.uml2.uml.QualifierValue;
import org.eclipse.uml2.uml.UMLPackage;

// End of user code

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class LinkEndDestructionDataBasePropertiesEditionComponent extends StandardPropertiesEditionComponent {

	public static String BASE_PART = "Base"; //$NON-NLS-1$

	private String[] parts = { BASE_PART };

	/**
	 * The EObject to edit
	 */
	private LinkEndDestructionData linkEndDestructionData;

	/**
	 * The Base part
	 */
	private LinkEndDestructionDataPropertiesEditionPart basePart;

	/**
	 * Default constructor
	 */
	public LinkEndDestructionDataBasePropertiesEditionComponent(EObject linkEndDestructionData, String editing_mode) {
		if (linkEndDestructionData instanceof LinkEndDestructionData) {
			this.linkEndDestructionData = (LinkEndDestructionData) linkEndDestructionData;
			if (IPropertiesEditionComponent.LIVE_MODE.equals(editing_mode)) {
				semanticAdapter = initializeSemanticAdapter();
				this.linkEndDestructionData.eAdapters().add(semanticAdapter);
			}
		}
		this.editing_mode = editing_mode;
	}

	/**
	 * Initialize the semantic model listener for live editing mode
	 * 
	 * @return the semantic model listener
	 */
	private AdapterImpl initializeSemanticAdapter() {
		return new EContentAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
			 */
			public void notifyChanged(Notification msg) {
				if (basePart == null)
					LinkEndDestructionDataBasePropertiesEditionComponent.this.dispose();
				else {
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getElement_OwnedComment() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getComment())) {
						basePart.updateOwnedComment(linkEndDestructionData);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getLinkEndData_Qualifier() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getQualifierValue())) {
						basePart.updateQualifier(linkEndDestructionData);
					}
					if (UMLPackage.eINSTANCE.getLinkEndDestructionData_IsDestroyDuplicates().equals(msg.getFeature())
							&& basePart != null)
						basePart.setIsDestroyDuplicates((Boolean) msg.getNewValue());

				}
			}

		};
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#translatePart(java.lang.String)
	 */
	public java.lang.Class translatePart(String key) {
		if (BASE_PART.equals(key))
			return UMLViewsRepository.LinkEndDestructionData.class;
		return super.translatePart(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#partsList()
	 */
	public String[] partsList() {
		return parts;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#getPropertiesEditionPart
	 *      (java.lang.String, java.lang.String)
	 */
	public IPropertiesEditionPart getPropertiesEditionPart(int kind, String key) {
		if (linkEndDestructionData != null && BASE_PART.equals(key)) {
			if (basePart == null) {
				IPropertiesEditionPartProvider provider = PropertiesEditionPartProviderService.getInstance()
						.getProvider(UMLViewsRepository.class);
				if (provider != null) {
					basePart = (LinkEndDestructionDataPropertiesEditionPart) provider.getPropertiesEditionPart(
							UMLViewsRepository.LinkEndDestructionData.class, kind, this);
					addListener((IPropertiesEditionListener) basePart);
				}
			}
			return (IPropertiesEditionPart) basePart;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#
	 *      setPropertiesEditionPart(java.lang.Class, int,
	 *      org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart)
	 */
	public void setPropertiesEditionPart(java.lang.Class key, int kind, IPropertiesEditionPart propertiesEditionPart) {
		if (key == UMLViewsRepository.LinkEndDestructionData.class)
			this.basePart = (LinkEndDestructionDataPropertiesEditionPart) propertiesEditionPart;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#initPart(java.lang.Class,
	 *      int, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.resource.ResourceSet)
	 */
	public void initPart(java.lang.Class key, int kind, EObject elt, ResourceSet allResource) {
		if (basePart != null && key == UMLViewsRepository.LinkEndDestructionData.class) {
			((IPropertiesEditionPart) basePart).setContext(elt, allResource);
			LinkEndDestructionData linkEndDestructionData = (LinkEndDestructionData) elt;
			// init values
			basePart.initOwnedComment(linkEndDestructionData, null, UMLPackage.eINSTANCE.getElement_OwnedComment());
			basePart.initQualifier(linkEndDestructionData, null, UMLPackage.eINSTANCE.getLinkEndData_Qualifier());
			basePart.setIsDestroyDuplicates(linkEndDestructionData.isDestroyDuplicates());

			// init filters
			basePart.addFilterToOwnedComment(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof Comment); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for ownedComment

			// End of user code
			basePart.addFilterToQualifier(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof QualifierValue); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for qualifier

			// End of user code

		}
		// init values for referenced views

		// init filters for referenced views

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#getPropertiesEditionCommand
	 *      (org.eclipse.emf.edit.domain.EditingDomain)
	 */
	public CompoundCommand getPropertiesEditionCommand(EditingDomain editingDomain) {
		CompoundCommand cc = new CompoundCommand();
		if (linkEndDestructionData != null) {
			List ownedCommentToAddFromOwnedComment = basePart.getOwnedCommentToAdd();
			for (Iterator iter = ownedCommentToAddFromOwnedComment.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, linkEndDestructionData, UMLPackage.eINSTANCE
						.getElement_OwnedComment(), iter.next()));
			Map ownedCommentToRefreshFromOwnedComment = basePart.getOwnedCommentToEdit();
			for (Iterator iter = ownedCommentToRefreshFromOwnedComment.keySet().iterator(); iter.hasNext();) {

				// Start of user code for ownedComment reference refreshment from ownedComment

				Comment nextElement = (Comment) iter.next();
				Comment ownedComment = (Comment) ownedCommentToRefreshFromOwnedComment.get(nextElement);

				// End of user code

			}
			List ownedCommentToRemoveFromOwnedComment = basePart.getOwnedCommentToRemove();
			for (Iterator iter = ownedCommentToRemoveFromOwnedComment.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List ownedCommentToMoveFromOwnedComment = basePart.getOwnedCommentToMove();
			for (Iterator iter = ownedCommentToMoveFromOwnedComment.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, linkEndDestructionData, UMLPackage.eINSTANCE.getComment(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List qualifierToAddFromQualifier = basePart.getQualifierToAdd();
			for (Iterator iter = qualifierToAddFromQualifier.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, linkEndDestructionData, UMLPackage.eINSTANCE
						.getLinkEndData_Qualifier(), iter.next()));
			Map qualifierToRefreshFromQualifier = basePart.getQualifierToEdit();
			for (Iterator iter = qualifierToRefreshFromQualifier.keySet().iterator(); iter.hasNext();) {

				// Start of user code for qualifier reference refreshment from qualifier

				QualifierValue nextElement = (QualifierValue) iter.next();
				QualifierValue qualifier = (QualifierValue) qualifierToRefreshFromQualifier.get(nextElement);

				// End of user code

			}
			List qualifierToRemoveFromQualifier = basePart.getQualifierToRemove();
			for (Iterator iter = qualifierToRemoveFromQualifier.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List qualifierToMoveFromQualifier = basePart.getQualifierToMove();
			for (Iterator iter = qualifierToMoveFromQualifier.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, linkEndDestructionData, UMLPackage.eINSTANCE
						.getQualifierValue(), moveElement.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, linkEndDestructionData, UMLPackage.eINSTANCE
					.getLinkEndDestructionData_IsDestroyDuplicates(), basePart.getIsDestroyDuplicates()));

		}
		if (!cc.isEmpty())
			return cc;
		cc.append(IdentityCommand.INSTANCE);
		return cc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#getPropertiesEditionObject()
	 */
	public EObject getPropertiesEditionObject(EObject source) {
		if (source instanceof LinkEndDestructionData) {
			LinkEndDestructionData linkEndDestructionDataToUpdate = (LinkEndDestructionData) source;
			linkEndDestructionDataToUpdate.getOwnedComments().addAll(basePart.getOwnedCommentToAdd());
			linkEndDestructionDataToUpdate.getQualifiers().addAll(basePart.getQualifierToAdd());
			linkEndDestructionDataToUpdate.setIsDestroyDuplicates(new Boolean(basePart.getIsDestroyDuplicates())
					.booleanValue());

			return linkEndDestructionDataToUpdate;
		} else
			return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionListener#firePropertiesChanged(org.eclipse.emf.common.notify.Notification)
	 */
	public void firePropertiesChanged(PropertiesEditionEvent event) {
		super.firePropertiesChanged(event);
		if (PropertiesEditionEvent.COMMIT == event.getState()
				&& IPropertiesEditionComponent.LIVE_MODE.equals(editing_mode)) {
			CompoundCommand command = new CompoundCommand();
			if (UMLViewsRepository.LinkEndDestructionData.ownedComment == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Comment oldValue = (Comment) event.getOldValue();
					Comment newValue = (Comment) event.getNewValue();

					// Start of user code for ownedComment live update command
					// TODO: Complete the linkEndDestructionData update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, linkEndDestructionData, UMLPackage.eINSTANCE
							.getElement_OwnedComment(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, linkEndDestructionData, UMLPackage.eINSTANCE
							.getComment(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.LinkEndDestructionData.qualifier == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					QualifierValue oldValue = (QualifierValue) event.getOldValue();
					QualifierValue newValue = (QualifierValue) event.getNewValue();

					// Start of user code for qualifier live update command
					// TODO: Complete the linkEndDestructionData update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, linkEndDestructionData, UMLPackage.eINSTANCE
							.getLinkEndData_Qualifier(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, linkEndDestructionData, UMLPackage.eINSTANCE
							.getQualifierValue(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.LinkEndDestructionData.isDestroyDuplicates == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, linkEndDestructionData, UMLPackage.eINSTANCE
						.getLinkEndDestructionData_IsDestroyDuplicates(), event.getNewValue()));

			liveEditingDomain.getCommandStack().execute(command);
		} else if (PropertiesEditionEvent.CHANGE == event.getState()) {
			Diagnostic diag = this.validateValue(event);
			if (diag != null && diag.getSeverity() != Diagnostic.OK) {

			} else {

			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#isRequired(java.lang.String,
	 *      int)
	 */
	public boolean isRequired(String key, int kind) {
		return key == UMLViewsRepository.LinkEndDestructionData.isDestroyDuplicates;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#getHelpContent(java.lang.String,
	 *      int)
	 */
	public String getHelpContent(String key, int kind) {
		if (key == UMLViewsRepository.LinkEndDestructionData.ownedComment)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.LinkEndDestructionData.qualifier)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.LinkEndDestructionData.isDestroyDuplicates)
			return null; //$NON-NLS-1$
		return super.getHelpContent(key, kind);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#validateValue(org.eclipse.emf.common.notify.Notification)
	 */
	public Diagnostic validateValue(PropertiesEditionEvent event) {
		String newStringValue = event.getNewValue().toString();
		Diagnostic ret = null;
		try {
			if (UMLViewsRepository.LinkEndDestructionData.isDestroyDuplicates == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE
						.getLinkEndDestructionData_IsDestroyDuplicates().getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE
						.getLinkEndDestructionData_IsDestroyDuplicates().getEAttributeType(), newValue);
			}

		} catch (IllegalArgumentException iae) {
			ret = BasicDiagnostic.toDiagnostic(iae);
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#validate()
	 */
	public Diagnostic validate() {
		if (IPropertiesEditionComponent.BATCH_MODE.equals(editing_mode)) {
			EObject copy = EcoreUtil.copy(PropertiesContextService.getInstance().entryPointElement());
			copy = PropertiesContextService.getInstance().entryPointComponent().getPropertiesEditionObject(copy);
			return Diagnostician.INSTANCE.validate(copy);
		} else if (IPropertiesEditionComponent.LIVE_MODE.equals(editing_mode))
			return Diagnostician.INSTANCE.validate(linkEndDestructionData);
		else
			return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#dispose()
	 */
	public void dispose() {
		if (semanticAdapter != null)
			linkEndDestructionData.eAdapters().remove(semanticAdapter);
	}

}
