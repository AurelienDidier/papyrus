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
import org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart;
import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Comment;
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
public class CombinedFragmentBasePropertiesEditionComponent extends StandardPropertiesEditionComponent {

	public static String BASE_PART = "Base"; //$NON-NLS-1$

	private String[] parts = {BASE_PART};

	/**
	 * The EObject to edit
	 */
	private CombinedFragment combinedFragment;

	/**
	 * The Base part
	 */
	private CombinedFragmentPropertiesEditionPart basePart;

	/**
	 * Default constructor
	 */
	public CombinedFragmentBasePropertiesEditionComponent(EObject combinedFragment, String editing_mode) {
		if (combinedFragment instanceof CombinedFragment) {
			this.combinedFragment = (CombinedFragment)combinedFragment;
			if (IPropertiesEditionComponent.LIVE_MODE.equals(editing_mode)) {
				semanticAdapter = initializeSemanticAdapter();
				this.combinedFragment.eAdapters().add(semanticAdapter);
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
					CombinedFragmentBasePropertiesEditionComponent.this.dispose();
				else {
					if (msg.getFeature() != null && 
							(((EStructuralFeature)msg.getFeature()) == UMLPackage.eINSTANCE.getElement_OwnedComment()
							|| ((EStructuralFeature)msg.getFeature()).getEContainingClass() == UMLPackage.eINSTANCE.getComment())) {
						basePart.updateOwnedComment(combinedFragment);
					}
					if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(msg.getFeature()) && basePart != null)
						basePart.setName((String)msg.getNewValue());

					if (UMLPackage.eINSTANCE.getNamedElement_Visibility().equals(msg.getFeature()) && basePart != null)
						basePart.setVisibility((Enumerator)msg.getNewValue());

					if (UMLPackage.eINSTANCE.getNamedElement_ClientDependency().equals(msg.getFeature()))
						basePart.updateClientDependency(combinedFragment);
					if (UMLPackage.eINSTANCE.getInteractionFragment_Covered().equals(msg.getFeature()))
						basePart.updateCovered(combinedFragment);
					if (msg.getFeature() != null && 
							(((EStructuralFeature)msg.getFeature()) == UMLPackage.eINSTANCE.getInteractionFragment_GeneralOrdering()
							|| ((EStructuralFeature)msg.getFeature()).getEContainingClass() == UMLPackage.eINSTANCE.getGeneralOrdering())) {
						basePart.updateGeneralOrdering(combinedFragment);
					}
					if (UMLPackage.eINSTANCE.getCombinedFragment_InteractionOperator().equals(msg.getFeature()) && basePart != null)
						basePart.setInteractionOperator((Enumerator)msg.getNewValue());

					if (msg.getFeature() != null && 
							(((EStructuralFeature)msg.getFeature()) == UMLPackage.eINSTANCE.getCombinedFragment_Operand()
							|| ((EStructuralFeature)msg.getFeature()).getEContainingClass() == UMLPackage.eINSTANCE.getInteractionOperand())) {
						basePart.updateOperand(combinedFragment);
					}
					if (msg.getFeature() != null && 
							(((EStructuralFeature)msg.getFeature()) == UMLPackage.eINSTANCE.getCombinedFragment_CfragmentGate()
							|| ((EStructuralFeature)msg.getFeature()).getEContainingClass() == UMLPackage.eINSTANCE.getGate())) {
						basePart.updateCfragmentGate(combinedFragment);
					}


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
			return UMLViewsRepository.CombinedFragment.class;
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
	 * (java.lang.String, java.lang.String)
	 */
	public IPropertiesEditionPart getPropertiesEditionPart(int kind, String key) {
		if (combinedFragment != null && BASE_PART.equals(key)) {
			if (basePart == null) {
				IPropertiesEditionPartProvider provider = PropertiesEditionPartProviderService.getInstance().getProvider(UMLViewsRepository.class);
				if (provider != null) {
					basePart = (CombinedFragmentPropertiesEditionPart)provider.getPropertiesEditionPart(UMLViewsRepository.CombinedFragment.class, kind, this);
					addListener((IPropertiesEditionListener)basePart);
				}
			}
			return (IPropertiesEditionPart)basePart;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#
	 *      setPropertiesEditionPart(java.lang.Class, int, org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart)
	 */
	public void setPropertiesEditionPart(java.lang.Class key, int kind, IPropertiesEditionPart propertiesEditionPart) {
		if (key == UMLViewsRepository.CombinedFragment.class)
			this.basePart = (CombinedFragmentPropertiesEditionPart) propertiesEditionPart;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#initPart(java.lang.Class, int, org.eclipse.emf.ecore.EObject, 
	 *      org.eclipse.emf.ecore.resource.ResourceSet)
	 */
	public void initPart(java.lang.Class key, int kind, EObject elt, ResourceSet allResource) {
		if (basePart != null && key == UMLViewsRepository.CombinedFragment.class) {
			((IPropertiesEditionPart)basePart).setContext(elt, allResource);
			CombinedFragment combinedFragment = (CombinedFragment)elt;
			// init values
			basePart.initOwnedComment(combinedFragment, null, UMLPackage.eINSTANCE.getElement_OwnedComment());
			if (combinedFragment.getName() != null)
				basePart.setName(combinedFragment.getName());

			basePart.initVisibility((EEnum) UMLPackage.eINSTANCE.getNamedElement_Visibility().getEType(), combinedFragment.getVisibility());
			basePart.initClientDependency(combinedFragment, null, UMLPackage.eINSTANCE.getNamedElement_ClientDependency());
			basePart.initCovered(combinedFragment, null, UMLPackage.eINSTANCE.getInteractionFragment_Covered());
			basePart.initGeneralOrdering(combinedFragment, null, UMLPackage.eINSTANCE.getInteractionFragment_GeneralOrdering());
			basePart.initInteractionOperator((EEnum) UMLPackage.eINSTANCE.getCombinedFragment_InteractionOperator().getEType(), combinedFragment.getInteractionOperator());
			basePart.initOperand(combinedFragment, null, UMLPackage.eINSTANCE.getCombinedFragment_Operand());
			basePart.initCfragmentGate(combinedFragment, null, UMLPackage.eINSTANCE.getCombinedFragment_CfragmentGate());
			
			// init filters
			basePart.addFilterToOwnedComment(new ViewerFilter() {

					/*
					 * (non-Javadoc)
					 * 
					 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
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
				 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
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
				 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
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
					 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
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
					 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
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
					 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
					 */
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						return (element instanceof String && element.equals("")) || (element instanceof Gate); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for cfragmentGate
			
			// End of user code
		}
		// init values for referenced views

		// init filters for referenced views

	}














	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#getPropertiesEditionCommand
	 *     (org.eclipse.emf.edit.domain.EditingDomain)
	 */
	public CompoundCommand getPropertiesEditionCommand(EditingDomain editingDomain) {
		CompoundCommand cc = new CompoundCommand();
		if (combinedFragment != null) {
			List ownedCommentToAddFromOwnedComment = basePart.getOwnedCommentToAdd();
			for (Iterator iter = ownedCommentToAddFromOwnedComment.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, combinedFragment, UMLPackage.eINSTANCE.getElement_OwnedComment(), iter.next()));
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
			for (Iterator iter = ownedCommentToMoveFromOwnedComment.iterator(); iter.hasNext();){
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
				cc.append(MoveCommand.create(editingDomain, combinedFragment, UMLPackage.eINSTANCE.getComment(), moveElement.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, combinedFragment, UMLPackage.eINSTANCE.getNamedElement_Name(), basePart.getName()));

			cc.append(SetCommand.create(editingDomain, combinedFragment, UMLPackage.eINSTANCE.getNamedElement_Visibility(), basePart.getVisibility()));

			List clientDependencyToAddFromClientDependency = basePart.getClientDependencyToAdd();
			for (Iterator iter = clientDependencyToAddFromClientDependency.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, combinedFragment, UMLPackage.eINSTANCE.getNamedElement_ClientDependency(), iter.next()));
			List clientDependencyToRemoveFromClientDependency = basePart.getClientDependencyToRemove();
			for (Iterator iter = clientDependencyToRemoveFromClientDependency.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, combinedFragment, UMLPackage.eINSTANCE.getNamedElement_ClientDependency(), iter.next()));
			//List clientDependencyToMoveFromClientDependency = basePart.getClientDependencyToMove();
			//for (Iterator iter = clientDependencyToMoveFromClientDependency.iterator(); iter.hasNext();){
			//	org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			//	cc.append(MoveCommand.create(editingDomain, combinedFragment, UMLPackage.eINSTANCE.getDependency(), moveElement.getElement(), moveElement.getIndex()));
			//}
			List coveredToAddFromCovered = basePart.getCoveredToAdd();
			for (Iterator iter = coveredToAddFromCovered.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, combinedFragment, UMLPackage.eINSTANCE.getInteractionFragment_Covered(), iter.next()));
			List coveredToRemoveFromCovered = basePart.getCoveredToRemove();
			for (Iterator iter = coveredToRemoveFromCovered.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, combinedFragment, UMLPackage.eINSTANCE.getInteractionFragment_Covered(), iter.next()));
			//List coveredToMoveFromCovered = basePart.getCoveredToMove();
			//for (Iterator iter = coveredToMoveFromCovered.iterator(); iter.hasNext();){
			//	org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			//	cc.append(MoveCommand.create(editingDomain, combinedFragment, UMLPackage.eINSTANCE.getLifeline(), moveElement.getElement(), moveElement.getIndex()));
			//}
			List generalOrderingToAddFromGeneralOrdering = basePart.getGeneralOrderingToAdd();
			for (Iterator iter = generalOrderingToAddFromGeneralOrdering.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, combinedFragment, UMLPackage.eINSTANCE.getInteractionFragment_GeneralOrdering(), iter.next()));
			Map generalOrderingToRefreshFromGeneralOrdering = basePart.getGeneralOrderingToEdit();
			for (Iterator iter = generalOrderingToRefreshFromGeneralOrdering.keySet().iterator(); iter.hasNext();) {
				
				// Start of user code for generalOrdering reference refreshment from generalOrdering
				
				GeneralOrdering nextElement = (GeneralOrdering) iter.next();
				GeneralOrdering generalOrdering = (GeneralOrdering) generalOrderingToRefreshFromGeneralOrdering.get(nextElement);
				
				// End of user code
				
			}
			List generalOrderingToRemoveFromGeneralOrdering = basePart.getGeneralOrderingToRemove();
			for (Iterator iter = generalOrderingToRemoveFromGeneralOrdering.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List generalOrderingToMoveFromGeneralOrdering = basePart.getGeneralOrderingToMove();
			for (Iterator iter = generalOrderingToMoveFromGeneralOrdering.iterator(); iter.hasNext();){
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
				cc.append(MoveCommand.create(editingDomain, combinedFragment, UMLPackage.eINSTANCE.getGeneralOrdering(), moveElement.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, combinedFragment, UMLPackage.eINSTANCE.getCombinedFragment_InteractionOperator(), basePart.getInteractionOperator()));

			List operandToAddFromOperand = basePart.getOperandToAdd();
			for (Iterator iter = operandToAddFromOperand.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, combinedFragment, UMLPackage.eINSTANCE.getCombinedFragment_Operand(), iter.next()));
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
			for (Iterator iter = operandToMoveFromOperand.iterator(); iter.hasNext();){
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
				cc.append(MoveCommand.create(editingDomain, combinedFragment, UMLPackage.eINSTANCE.getInteractionOperand(), moveElement.getElement(), moveElement.getIndex()));
			}
			List cfragmentGateToAddFromCfragmentGate = basePart.getCfragmentGateToAdd();
			for (Iterator iter = cfragmentGateToAddFromCfragmentGate.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, combinedFragment, UMLPackage.eINSTANCE.getCombinedFragment_CfragmentGate(), iter.next()));
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
			for (Iterator iter = cfragmentGateToMoveFromCfragmentGate.iterator(); iter.hasNext();){
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
				cc.append(MoveCommand.create(editingDomain, combinedFragment, UMLPackage.eINSTANCE.getGate(), moveElement.getElement(), moveElement.getIndex()));
			}


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
		if (source instanceof CombinedFragment) {
			CombinedFragment combinedFragmentToUpdate = (CombinedFragment)source;
			combinedFragmentToUpdate.getOwnedComments().addAll(basePart.getOwnedCommentToAdd());
			combinedFragmentToUpdate.setName(basePart.getName());

			combinedFragmentToUpdate.setVisibility((VisibilityKind)basePart.getVisibility());	

			combinedFragmentToUpdate.getClientDependencies().addAll(basePart.getClientDependencyToAdd());
			combinedFragmentToUpdate.getCovereds().addAll(basePart.getCoveredToAdd());
			combinedFragmentToUpdate.getGeneralOrderings().addAll(basePart.getGeneralOrderingToAdd());
			combinedFragmentToUpdate.setInteractionOperator((InteractionOperatorKind)basePart.getInteractionOperator());	

			combinedFragmentToUpdate.getOperands().addAll(basePart.getOperandToAdd());
			combinedFragmentToUpdate.getCfragmentGates().addAll(basePart.getCfragmentGateToAdd());


			return combinedFragmentToUpdate;
		}
		else
			return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionListener#firePropertiesChanged(org.eclipse.emf.common.notify.Notification)
	 */
	public void firePropertiesChanged(PropertiesEditionEvent event) {
		super.firePropertiesChanged(event);
		if (PropertiesEditionEvent.COMMIT == event.getState() && IPropertiesEditionComponent.LIVE_MODE.equals(editing_mode)) {
			CompoundCommand command = new CompoundCommand();
			if (UMLViewsRepository.CombinedFragment.ownedComment == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Comment oldValue = (Comment)event.getOldValue();
					Comment newValue = (Comment)event.getNewValue();
					
					// Start of user code for ownedComment live update command
					// TODO: Complete the combinedFragment update command
					// End of user code
					
				}
				else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, combinedFragment, UMLPackage.eINSTANCE.getElement_OwnedComment(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, combinedFragment, UMLPackage.eINSTANCE.getComment(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.CombinedFragment.name == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, combinedFragment, UMLPackage.eINSTANCE.getNamedElement_Name(), event.getNewValue()));

			if (UMLViewsRepository.CombinedFragment.visibility == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, combinedFragment, UMLPackage.eINSTANCE.getNamedElement_Visibility(), event.getNewValue()));

			if (UMLViewsRepository.CombinedFragment.clientDependency == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, combinedFragment, UMLPackage.eINSTANCE.getNamedElement_ClientDependency(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, combinedFragment, UMLPackage.eINSTANCE.getNamedElement_ClientDependency(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, combinedFragment, UMLPackage.eINSTANCE.getNamedElement_ClientDependency(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.CombinedFragment.covered == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, combinedFragment, UMLPackage.eINSTANCE.getInteractionFragment_Covered(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, combinedFragment, UMLPackage.eINSTANCE.getInteractionFragment_Covered(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, combinedFragment, UMLPackage.eINSTANCE.getInteractionFragment_Covered(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.CombinedFragment.generalOrdering == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					GeneralOrdering oldValue = (GeneralOrdering)event.getOldValue();
					GeneralOrdering newValue = (GeneralOrdering)event.getNewValue();
					
					// Start of user code for generalOrdering live update command
					// TODO: Complete the combinedFragment update command
					// End of user code
					
				}
				else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, combinedFragment, UMLPackage.eINSTANCE.getInteractionFragment_GeneralOrdering(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, combinedFragment, UMLPackage.eINSTANCE.getGeneralOrdering(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.CombinedFragment.interactionOperator == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, combinedFragment, UMLPackage.eINSTANCE.getCombinedFragment_InteractionOperator(), event.getNewValue()));

			if (UMLViewsRepository.CombinedFragment.operand == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					InteractionOperand oldValue = (InteractionOperand)event.getOldValue();
					InteractionOperand newValue = (InteractionOperand)event.getNewValue();
					
					// Start of user code for operand live update command
					// TODO: Complete the combinedFragment update command
					// End of user code
					
				}
				else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, combinedFragment, UMLPackage.eINSTANCE.getCombinedFragment_Operand(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, combinedFragment, UMLPackage.eINSTANCE.getInteractionOperand(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.CombinedFragment.cfragmentGate == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Gate oldValue = (Gate)event.getOldValue();
					Gate newValue = (Gate)event.getNewValue();
					
					// Start of user code for cfragmentGate live update command
					// TODO: Complete the combinedFragment update command
					// End of user code
					
				}
				else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, combinedFragment, UMLPackage.eINSTANCE.getCombinedFragment_CfragmentGate(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, combinedFragment, UMLPackage.eINSTANCE.getGate(), event.getNewValue(), event.getNewIndex()));
			}


			liveEditingDomain.getCommandStack().execute(command);
		} else if (PropertiesEditionEvent.CHANGE == event.getState()) {
			Diagnostic diag = this.validateValue(event);
			if (diag != null && diag.getSeverity() != Diagnostic.OK) {

				if (UMLViewsRepository.CombinedFragment.name == event.getAffectedEditor())
					basePart.setMessageForName(diag.getMessage(), IMessageProvider.ERROR);









			} else {

				if (UMLViewsRepository.CombinedFragment.name == event.getAffectedEditor())
					basePart.unsetMessageForName();









			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#isRequired(java.lang.String, int)
	 */
	public boolean isRequired(String key, int kind) {
		return key == UMLViewsRepository.CombinedFragment.interactionOperator || key == UMLViewsRepository.CombinedFragment.operand;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#getHelpContent(java.lang.String, int)
	 */
	public String getHelpContent(String key, int kind) {
		if (key == UMLViewsRepository.CombinedFragment.ownedComment)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.CombinedFragment.name)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.CombinedFragment.visibility)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.CombinedFragment.clientDependency)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.CombinedFragment.covered)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.CombinedFragment.generalOrdering)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.CombinedFragment.interactionOperator)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.CombinedFragment.operand)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.CombinedFragment.cfragmentGate)
			return null
; //$NON-NLS-1$
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
			if (UMLViewsRepository.CombinedFragment.name == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getNamedElement_Name().getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getNamedElement_Name().getEAttributeType(), newValue);
			}
			if (UMLViewsRepository.CombinedFragment.visibility == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getNamedElement_Visibility().getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getNamedElement_Visibility().getEAttributeType(), newValue);
			}
			if (UMLViewsRepository.CombinedFragment.interactionOperator == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getCombinedFragment_InteractionOperator().getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getCombinedFragment_InteractionOperator().getEAttributeType(), newValue);
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
		}
		else if (IPropertiesEditionComponent.LIVE_MODE.equals(editing_mode))
			return Diagnostician.INSTANCE.validate(combinedFragment);
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
			combinedFragment.eAdapters().remove(semanticAdapter);
	}

}

