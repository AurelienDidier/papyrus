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
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionListener;
import org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.api.providers.IPropertiesEditionPartProvider;
import org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.impl.filters.EObjectFilter;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.services.PropertiesContextService;
import org.eclipse.emf.eef.runtime.impl.services.PropertiesEditionPartProviderService;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart;
import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.ConsiderIgnoreFragment;
import org.eclipse.uml2.uml.Gate;
import org.eclipse.uml2.uml.GeneralOrdering;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.InteractionOperatorKind;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;

// End of user code

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class ConsiderIgnoreFragmentBasePropertiesEditionComponent extends StandardPropertiesEditionComponent {

	public static String BASE_PART = "Base"; //$NON-NLS-1$

	private String[] parts = { BASE_PART };

	/**
	 * The EObject to edit
	 */
	private ConsiderIgnoreFragment considerIgnoreFragment;

	/**
	 * The Base part
	 */
	private ConsiderIgnoreFragmentPropertiesEditionPart basePart;

	/**
	 * Default constructor
	 */
	public ConsiderIgnoreFragmentBasePropertiesEditionComponent(EObject considerIgnoreFragment, String editing_mode) {
		if (considerIgnoreFragment instanceof ConsiderIgnoreFragment) {
			this.considerIgnoreFragment = (ConsiderIgnoreFragment) considerIgnoreFragment;
			if (IPropertiesEditionComponent.LIVE_MODE.equals(editing_mode)) {
				semanticAdapter = initializeSemanticAdapter();
				this.considerIgnoreFragment.eAdapters().add(semanticAdapter);
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
					ConsiderIgnoreFragmentBasePropertiesEditionComponent.this.dispose();
				else {
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getElement_OwnedComment() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getComment())) {
						basePart.updateOwnedComment(considerIgnoreFragment);
					}
					if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(msg.getFeature()) && basePart != null)
						basePart.setName((String) msg.getNewValue());

					if (UMLPackage.eINSTANCE.getNamedElement_Visibility().equals(msg.getFeature()) && basePart != null)
						basePart.setVisibility((Enumerator) msg.getNewValue());

					if (UMLPackage.eINSTANCE.getNamedElement_ClientDependency().equals(msg.getFeature()))
						basePart.updateClientDependency(considerIgnoreFragment);
					if (UMLPackage.eINSTANCE.getInteractionFragment_Covered().equals(msg.getFeature()))
						basePart.updateCovered(considerIgnoreFragment);
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getInteractionFragment_GeneralOrdering() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getGeneralOrdering())) {
						basePart.updateGeneralOrdering(considerIgnoreFragment);
					}
					if (UMLPackage.eINSTANCE.getCombinedFragment_InteractionOperator().equals(msg.getFeature())
							&& basePart != null)
						basePart.setInteractionOperator((Enumerator) msg.getNewValue());

					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getCombinedFragment_Operand() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getInteractionOperand())) {
						basePart.updateOperand(considerIgnoreFragment);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getCombinedFragment_CfragmentGate() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getGate())) {
						basePart.updateCfragmentGate(considerIgnoreFragment);
					}
					if (UMLPackage.eINSTANCE.getConsiderIgnoreFragment_Message().equals(msg.getFeature()))
						basePart.updateMessage(considerIgnoreFragment);

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
			return UMLViewsRepository.ConsiderIgnoreFragment.class;
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
		if (considerIgnoreFragment != null && BASE_PART.equals(key)) {
			if (basePart == null) {
				IPropertiesEditionPartProvider provider = PropertiesEditionPartProviderService.getInstance()
						.getProvider(UMLViewsRepository.class);
				if (provider != null) {
					basePart = (ConsiderIgnoreFragmentPropertiesEditionPart) provider.getPropertiesEditionPart(
							UMLViewsRepository.ConsiderIgnoreFragment.class, kind, this);
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
		if (key == UMLViewsRepository.ConsiderIgnoreFragment.class)
			this.basePart = (ConsiderIgnoreFragmentPropertiesEditionPart) propertiesEditionPart;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#initPart(java.lang.Class,
	 *      int, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.resource.ResourceSet)
	 */
	public void initPart(java.lang.Class key, int kind, EObject elt, ResourceSet allResource) {
		if (basePart != null && key == UMLViewsRepository.ConsiderIgnoreFragment.class) {
			((IPropertiesEditionPart) basePart).setContext(elt, allResource);
			ConsiderIgnoreFragment considerIgnoreFragment = (ConsiderIgnoreFragment) elt;
			// init values
			basePart.initOwnedComment(considerIgnoreFragment, null, UMLPackage.eINSTANCE.getElement_OwnedComment());
			if (considerIgnoreFragment.getName() != null)
				basePart.setName(considerIgnoreFragment.getName());

			basePart.initVisibility((EEnum) UMLPackage.eINSTANCE.getNamedElement_Visibility().getEType(),
					considerIgnoreFragment.getVisibility());
			basePart.initClientDependency(considerIgnoreFragment, null, UMLPackage.eINSTANCE
					.getNamedElement_ClientDependency());
			basePart.initCovered(considerIgnoreFragment, null, UMLPackage.eINSTANCE.getInteractionFragment_Covered());
			basePart.initGeneralOrdering(considerIgnoreFragment, null, UMLPackage.eINSTANCE
					.getInteractionFragment_GeneralOrdering());
			basePart.initInteractionOperator((EEnum) UMLPackage.eINSTANCE.getCombinedFragment_InteractionOperator()
					.getEType(), considerIgnoreFragment.getInteractionOperator());
			basePart.initOperand(considerIgnoreFragment, null, UMLPackage.eINSTANCE.getCombinedFragment_Operand());
			basePart.initCfragmentGate(considerIgnoreFragment, null, UMLPackage.eINSTANCE
					.getCombinedFragment_CfragmentGate());
			basePart
					.initMessage(considerIgnoreFragment, null, UMLPackage.eINSTANCE.getConsiderIgnoreFragment_Message());

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

			basePart.addFilterToClientDependency(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof EObject)
						return (!basePart.getClientDependencyTable().contains(element));
					return false;
				}

			});
			basePart.addFilterToClientDependency(new EObjectFilter(UMLPackage.eINSTANCE.getDependency()));
			// Start of user code for additional businessfilters for clientDependency

			// End of user code
			basePart.addFilterToCovered(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof EObject)
						return (!basePart.getCoveredTable().contains(element));
					return false;
				}

			});
			basePart.addFilterToCovered(new EObjectFilter(UMLPackage.eINSTANCE.getLifeline()));
			// Start of user code for additional businessfilters for covered

			// End of user code
			basePart.addFilterToGeneralOrdering(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof GeneralOrdering); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for generalOrdering

			// End of user code

			basePart.addFilterToOperand(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof InteractionOperand);

				}

			});
			// Start of user code for additional businessfilters for operand

			// End of user code
			basePart.addFilterToCfragmentGate(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof Gate); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for cfragmentGate

			// End of user code
			basePart.addFilterToMessage(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof EObject)
						return (!basePart.getMessageTable().contains(element));
					return false;
				}

			});
			basePart.addFilterToMessage(new EObjectFilter(UMLPackage.eINSTANCE.getNamedElement()));
			// Start of user code for additional businessfilters for message

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
		if (considerIgnoreFragment != null) {
			List ownedCommentToAddFromOwnedComment = basePart.getOwnedCommentToAdd();
			for (Iterator iter = ownedCommentToAddFromOwnedComment.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
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
				cc.append(MoveCommand.create(editingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE.getComment(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
					.getNamedElement_Name(), basePart.getName()));

			cc.append(SetCommand.create(editingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
					.getNamedElement_Visibility(), basePart.getVisibility()));

			List clientDependencyToAddFromClientDependency = basePart.getClientDependencyToAdd();
			for (Iterator iter = clientDependencyToAddFromClientDependency.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
						.getNamedElement_ClientDependency(), iter.next()));
			List clientDependencyToRemoveFromClientDependency = basePart.getClientDependencyToRemove();
			for (Iterator iter = clientDependencyToRemoveFromClientDependency.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
						.getNamedElement_ClientDependency(), iter.next()));
			// List clientDependencyToMoveFromClientDependency =
			// basePart.getClientDependencyToMove();
			// for (Iterator iter = clientDependencyToMoveFromClientDependency.iterator();
			// iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, considerIgnoreFragment,
			// UMLPackage.eINSTANCE.getDependency(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List coveredToAddFromCovered = basePart.getCoveredToAdd();
			for (Iterator iter = coveredToAddFromCovered.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
						.getInteractionFragment_Covered(), iter.next()));
			List coveredToRemoveFromCovered = basePart.getCoveredToRemove();
			for (Iterator iter = coveredToRemoveFromCovered.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
						.getInteractionFragment_Covered(), iter.next()));
			// List coveredToMoveFromCovered = basePart.getCoveredToMove();
			// for (Iterator iter = coveredToMoveFromCovered.iterator(); iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, considerIgnoreFragment,
			// UMLPackage.eINSTANCE.getLifeline(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List generalOrderingToAddFromGeneralOrdering = basePart.getGeneralOrderingToAdd();
			for (Iterator iter = generalOrderingToAddFromGeneralOrdering.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
						.getInteractionFragment_GeneralOrdering(), iter.next()));
			Map generalOrderingToRefreshFromGeneralOrdering = basePart.getGeneralOrderingToEdit();
			for (Iterator iter = generalOrderingToRefreshFromGeneralOrdering.keySet().iterator(); iter.hasNext();) {

				// Start of user code for generalOrdering reference refreshment from generalOrdering

				GeneralOrdering nextElement = (GeneralOrdering) iter.next();
				GeneralOrdering generalOrdering = (GeneralOrdering) generalOrderingToRefreshFromGeneralOrdering
						.get(nextElement);

				// End of user code

			}
			List generalOrderingToRemoveFromGeneralOrdering = basePart.getGeneralOrderingToRemove();
			for (Iterator iter = generalOrderingToRemoveFromGeneralOrdering.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List generalOrderingToMoveFromGeneralOrdering = basePart.getGeneralOrderingToMove();
			for (Iterator iter = generalOrderingToMoveFromGeneralOrdering.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
						.getGeneralOrdering(), moveElement.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
					.getCombinedFragment_InteractionOperator(), basePart.getInteractionOperator()));

			List operandToAddFromOperand = basePart.getOperandToAdd();
			for (Iterator iter = operandToAddFromOperand.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
						.getCombinedFragment_Operand(), iter.next()));
			Map operandToRefreshFromOperand = basePart.getOperandToEdit();
			for (Iterator iter = operandToRefreshFromOperand.keySet().iterator(); iter.hasNext();) {

				// Start of user code for operand reference refreshment from operand

				InteractionOperand nextElement = (InteractionOperand) iter.next();
				InteractionOperand operand = (InteractionOperand) operandToRefreshFromOperand.get(nextElement);

				// End of user code

			}
			List operandToRemoveFromOperand = basePart.getOperandToRemove();
			for (Iterator iter = operandToRemoveFromOperand.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List operandToMoveFromOperand = basePart.getOperandToMove();
			for (Iterator iter = operandToMoveFromOperand.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
						.getInteractionOperand(), moveElement.getElement(), moveElement.getIndex()));
			}
			List cfragmentGateToAddFromCfragmentGate = basePart.getCfragmentGateToAdd();
			for (Iterator iter = cfragmentGateToAddFromCfragmentGate.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
						.getCombinedFragment_CfragmentGate(), iter.next()));
			Map cfragmentGateToRefreshFromCfragmentGate = basePart.getCfragmentGateToEdit();
			for (Iterator iter = cfragmentGateToRefreshFromCfragmentGate.keySet().iterator(); iter.hasNext();) {

				// Start of user code for cfragmentGate reference refreshment from cfragmentGate

				Gate nextElement = (Gate) iter.next();
				Gate cfragmentGate = (Gate) cfragmentGateToRefreshFromCfragmentGate.get(nextElement);

				// End of user code

			}
			List cfragmentGateToRemoveFromCfragmentGate = basePart.getCfragmentGateToRemove();
			for (Iterator iter = cfragmentGateToRemoveFromCfragmentGate.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List cfragmentGateToMoveFromCfragmentGate = basePart.getCfragmentGateToMove();
			for (Iterator iter = cfragmentGateToMoveFromCfragmentGate.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE.getGate(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List messageToAddFromMessage = basePart.getMessageToAdd();
			for (Iterator iter = messageToAddFromMessage.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
						.getConsiderIgnoreFragment_Message(), iter.next()));
			List messageToRemoveFromMessage = basePart.getMessageToRemove();
			for (Iterator iter = messageToRemoveFromMessage.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
						.getConsiderIgnoreFragment_Message(), iter.next()));
			// List messageToMoveFromMessage = basePart.getMessageToMove();
			// for (Iterator iter = messageToMoveFromMessage.iterator(); iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, considerIgnoreFragment,
			// UMLPackage.eINSTANCE.getNamedElement(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }

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
		if (source instanceof ConsiderIgnoreFragment) {
			ConsiderIgnoreFragment considerIgnoreFragmentToUpdate = (ConsiderIgnoreFragment) source;
			considerIgnoreFragmentToUpdate.getOwnedComments().addAll(basePart.getOwnedCommentToAdd());
			considerIgnoreFragmentToUpdate.setName(basePart.getName());

			considerIgnoreFragmentToUpdate.setVisibility((VisibilityKind) basePart.getVisibility());

			considerIgnoreFragmentToUpdate.getClientDependencies().addAll(basePart.getClientDependencyToAdd());
			considerIgnoreFragmentToUpdate.getCovereds().addAll(basePart.getCoveredToAdd());
			considerIgnoreFragmentToUpdate.getGeneralOrderings().addAll(basePart.getGeneralOrderingToAdd());
			considerIgnoreFragmentToUpdate.setInteractionOperator((InteractionOperatorKind) basePart
					.getInteractionOperator());

			considerIgnoreFragmentToUpdate.getOperands().addAll(basePart.getOperandToAdd());
			considerIgnoreFragmentToUpdate.getCfragmentGates().addAll(basePart.getCfragmentGateToAdd());
			considerIgnoreFragmentToUpdate.getMessages().addAll(basePart.getMessageToAdd());

			return considerIgnoreFragmentToUpdate;
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
			if (UMLViewsRepository.ConsiderIgnoreFragment.ownedComment == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Comment oldValue = (Comment) event.getOldValue();
					Comment newValue = (Comment) event.getNewValue();

					// Start of user code for ownedComment live update command
					// TODO: Complete the considerIgnoreFragment update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
							.getElement_OwnedComment(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
							.getComment(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ConsiderIgnoreFragment.name == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
						.getNamedElement_Name(), event.getNewValue()));

			if (UMLViewsRepository.ConsiderIgnoreFragment.visibility == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
						.getNamedElement_Visibility(), event.getNewValue()));

			if (UMLViewsRepository.ConsiderIgnoreFragment.clientDependency == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
							.getNamedElement_ClientDependency(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
							.getNamedElement_ClientDependency(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
							.getNamedElement_ClientDependency(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ConsiderIgnoreFragment.covered == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
							.getInteractionFragment_Covered(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
							.getInteractionFragment_Covered(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
							.getInteractionFragment_Covered(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ConsiderIgnoreFragment.generalOrdering == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					GeneralOrdering oldValue = (GeneralOrdering) event.getOldValue();
					GeneralOrdering newValue = (GeneralOrdering) event.getNewValue();

					// Start of user code for generalOrdering live update command
					// TODO: Complete the considerIgnoreFragment update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
							.getInteractionFragment_GeneralOrdering(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
							.getGeneralOrdering(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ConsiderIgnoreFragment.interactionOperator == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
						.getCombinedFragment_InteractionOperator(), event.getNewValue()));

			if (UMLViewsRepository.ConsiderIgnoreFragment.operand == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					InteractionOperand oldValue = (InteractionOperand) event.getOldValue();
					InteractionOperand newValue = (InteractionOperand) event.getNewValue();

					// Start of user code for operand live update command
					// TODO: Complete the considerIgnoreFragment update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
							.getCombinedFragment_Operand(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
							.getInteractionOperand(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ConsiderIgnoreFragment.cfragmentGate == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Gate oldValue = (Gate) event.getOldValue();
					Gate newValue = (Gate) event.getNewValue();

					// Start of user code for cfragmentGate live update command
					// TODO: Complete the considerIgnoreFragment update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
							.getCombinedFragment_CfragmentGate(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
							.getGate(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ConsiderIgnoreFragment.message == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
							.getConsiderIgnoreFragment_Message(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
							.getConsiderIgnoreFragment_Message(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, considerIgnoreFragment, UMLPackage.eINSTANCE
							.getConsiderIgnoreFragment_Message(), event.getNewValue(), event.getNewIndex()));
			}

			liveEditingDomain.getCommandStack().execute(command);
		} else if (PropertiesEditionEvent.CHANGE == event.getState()) {
			Diagnostic diag = this.validateValue(event);
			if (diag != null && diag.getSeverity() != Diagnostic.OK) {

				if (UMLViewsRepository.ConsiderIgnoreFragment.name == event.getAffectedEditor())
					basePart.setMessageForName(diag.getMessage(), IMessageProvider.ERROR);

			} else {

				if (UMLViewsRepository.ConsiderIgnoreFragment.name == event.getAffectedEditor())
					basePart.unsetMessageForName();

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
		return key == UMLViewsRepository.ConsiderIgnoreFragment.interactionOperator
				|| key == UMLViewsRepository.ConsiderIgnoreFragment.operand;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#getHelpContent(java.lang.String,
	 *      int)
	 */
	public String getHelpContent(String key, int kind) {
		if (key == UMLViewsRepository.ConsiderIgnoreFragment.ownedComment)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ConsiderIgnoreFragment.name)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ConsiderIgnoreFragment.visibility)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ConsiderIgnoreFragment.clientDependency)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ConsiderIgnoreFragment.covered)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ConsiderIgnoreFragment.generalOrdering)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ConsiderIgnoreFragment.interactionOperator)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ConsiderIgnoreFragment.operand)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ConsiderIgnoreFragment.cfragmentGate)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ConsiderIgnoreFragment.message)
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
			if (UMLViewsRepository.ConsiderIgnoreFragment.name == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getNamedElement_Name()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getNamedElement_Name().getEAttributeType(),
						newValue);
			}
			if (UMLViewsRepository.ConsiderIgnoreFragment.visibility == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getNamedElement_Visibility()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getNamedElement_Visibility()
						.getEAttributeType(), newValue);
			}
			if (UMLViewsRepository.ConsiderIgnoreFragment.interactionOperator == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE
						.getCombinedFragment_InteractionOperator().getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getCombinedFragment_InteractionOperator()
						.getEAttributeType(), newValue);
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
			return Diagnostician.INSTANCE.validate(considerIgnoreFragment);
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
			considerIgnoreFragment.eAdapters().remove(semanticAdapter);
	}

}
