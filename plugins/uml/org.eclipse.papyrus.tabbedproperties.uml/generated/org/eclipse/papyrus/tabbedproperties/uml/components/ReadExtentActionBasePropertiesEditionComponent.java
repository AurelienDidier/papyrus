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
import org.eclipse.papyrus.tabbedproperties.uml.parts.ReadExtentActionPropertiesEditionPart;
import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.ExceptionHandler;
import org.eclipse.uml2.uml.ReadExtentAction;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;


// End of user code

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class ReadExtentActionBasePropertiesEditionComponent extends StandardPropertiesEditionComponent {

	public static String BASE_PART = "Base"; //$NON-NLS-1$

	private String[] parts = {BASE_PART};

	/**
	 * The EObject to edit
	 */
	private ReadExtentAction readExtentAction;

	/**
	 * The Base part
	 */
	private ReadExtentActionPropertiesEditionPart basePart;

	/**
	 * Default constructor
	 */
	public ReadExtentActionBasePropertiesEditionComponent(EObject readExtentAction, String editing_mode) {
		if (readExtentAction instanceof ReadExtentAction) {
			this.readExtentAction = (ReadExtentAction)readExtentAction;
			if (IPropertiesEditionComponent.LIVE_MODE.equals(editing_mode)) {
				semanticAdapter = initializeSemanticAdapter();
				this.readExtentAction.eAdapters().add(semanticAdapter);
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
					ReadExtentActionBasePropertiesEditionComponent.this.dispose();
				else {
					if (msg.getFeature() != null && 
							(((EStructuralFeature)msg.getFeature()) == UMLPackage.eINSTANCE.getElement_OwnedComment()
							|| ((EStructuralFeature)msg.getFeature()).getEContainingClass() == UMLPackage.eINSTANCE.getComment())) {
						basePart.updateOwnedComment(readExtentAction);
					}
					if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(msg.getFeature()) && basePart != null)
						basePart.setName((String)msg.getNewValue());

					if (UMLPackage.eINSTANCE.getNamedElement_Visibility().equals(msg.getFeature()) && basePart != null)
						basePart.setVisibility((Enumerator)msg.getNewValue());

					if (UMLPackage.eINSTANCE.getNamedElement_ClientDependency().equals(msg.getFeature()))
						basePart.updateClientDependency(readExtentAction);
					if (UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf().equals(msg.getFeature()) && basePart != null)
						basePart.setIsLeaf((Boolean)msg.getNewValue());

					if (UMLPackage.eINSTANCE.getActivityNode_Outgoing().equals(msg.getFeature()))
						basePart.updateOutgoing(readExtentAction);
					if (UMLPackage.eINSTANCE.getActivityNode_Incoming().equals(msg.getFeature()))
						basePart.updateIncoming(readExtentAction);
					if (UMLPackage.eINSTANCE.getActivityNode_InPartition().equals(msg.getFeature()))
						basePart.updateInPartition(readExtentAction);
					if (UMLPackage.eINSTANCE.getActivityNode_InInterruptibleRegion().equals(msg.getFeature()))
						basePart.updateInInterruptibleRegion(readExtentAction);
					if (UMLPackage.eINSTANCE.getActivityNode_RedefinedNode().equals(msg.getFeature()))
						basePart.updateRedefinedNode(readExtentAction);
					if (msg.getFeature() != null && 
							(((EStructuralFeature)msg.getFeature()) == UMLPackage.eINSTANCE.getExecutableNode_Handler()
							|| ((EStructuralFeature)msg.getFeature()).getEContainingClass() == UMLPackage.eINSTANCE.getExceptionHandler())) {
						basePart.updateHandler(readExtentAction);
					}
					if (msg.getFeature() != null && 
							(((EStructuralFeature)msg.getFeature()) == UMLPackage.eINSTANCE.getAction_LocalPrecondition()
							|| ((EStructuralFeature)msg.getFeature()).getEContainingClass() == UMLPackage.eINSTANCE.getConstraint())) {
						basePart.updateLocalPrecondition(readExtentAction);
					}
					if (msg.getFeature() != null && 
							(((EStructuralFeature)msg.getFeature()) == UMLPackage.eINSTANCE.getAction_LocalPostcondition()
							|| ((EStructuralFeature)msg.getFeature()).getEContainingClass() == UMLPackage.eINSTANCE.getConstraint())) {
						basePart.updateLocalPostcondition(readExtentAction);
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
			return UMLViewsRepository.ReadExtentAction.class;
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
		if (readExtentAction != null && BASE_PART.equals(key)) {
			if (basePart == null) {
				IPropertiesEditionPartProvider provider = PropertiesEditionPartProviderService.getInstance().getProvider(UMLViewsRepository.class);
				if (provider != null) {
					basePart = (ReadExtentActionPropertiesEditionPart)provider.getPropertiesEditionPart(UMLViewsRepository.ReadExtentAction.class, kind, this);
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
		if (key == UMLViewsRepository.ReadExtentAction.class)
			this.basePart = (ReadExtentActionPropertiesEditionPart) propertiesEditionPart;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#initPart(java.lang.Class, int, org.eclipse.emf.ecore.EObject, 
	 *      org.eclipse.emf.ecore.resource.ResourceSet)
	 */
	public void initPart(java.lang.Class key, int kind, EObject elt, ResourceSet allResource) {
		if (basePart != null && key == UMLViewsRepository.ReadExtentAction.class) {
			((IPropertiesEditionPart)basePart).setContext(elt, allResource);
			ReadExtentAction readExtentAction = (ReadExtentAction)elt;
			// init values
			basePart.initOwnedComment(readExtentAction, null, UMLPackage.eINSTANCE.getElement_OwnedComment());
			if (readExtentAction.getName() != null)
				basePart.setName(readExtentAction.getName());

			basePart.initVisibility((EEnum) UMLPackage.eINSTANCE.getNamedElement_Visibility().getEType(), readExtentAction.getVisibility());
			basePart.initClientDependency(readExtentAction, null, UMLPackage.eINSTANCE.getNamedElement_ClientDependency());
basePart.setIsLeaf(readExtentAction.isLeaf());

			basePart.initOutgoing(readExtentAction, null, UMLPackage.eINSTANCE.getActivityNode_Outgoing());
			basePart.initIncoming(readExtentAction, null, UMLPackage.eINSTANCE.getActivityNode_Incoming());
			basePart.initInPartition(readExtentAction, null, UMLPackage.eINSTANCE.getActivityNode_InPartition());
			basePart.initInInterruptibleRegion(readExtentAction, null, UMLPackage.eINSTANCE.getActivityNode_InInterruptibleRegion());
			basePart.initRedefinedNode(readExtentAction, null, UMLPackage.eINSTANCE.getActivityNode_RedefinedNode());
			basePart.initHandler(readExtentAction, null, UMLPackage.eINSTANCE.getExecutableNode_Handler());
			basePart.initLocalPrecondition(readExtentAction, null, UMLPackage.eINSTANCE.getAction_LocalPrecondition());
			basePart.initLocalPostcondition(readExtentAction, null, UMLPackage.eINSTANCE.getAction_LocalPostcondition());
			
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

			basePart.addFilterToOutgoing(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof EObject)
						return (!basePart.getOutgoingTable().contains(element));
					return false;
				}

			});
			basePart.addFilterToOutgoing(new EObjectFilter(UMLPackage.eINSTANCE.getActivityEdge()));
			// Start of user code for additional businessfilters for outgoing
			
			// End of user code
			basePart.addFilterToIncoming(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof EObject)
						return (!basePart.getIncomingTable().contains(element));
					return false;
				}

			});
			basePart.addFilterToIncoming(new EObjectFilter(UMLPackage.eINSTANCE.getActivityEdge()));
			// Start of user code for additional businessfilters for incoming
			
			// End of user code
			basePart.addFilterToInPartition(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof EObject)
						return (!basePart.getInPartitionTable().contains(element));
					return false;
				}

			});
			basePart.addFilterToInPartition(new EObjectFilter(UMLPackage.eINSTANCE.getActivityPartition()));
			// Start of user code for additional businessfilters for inPartition
			
			// End of user code
			basePart.addFilterToInInterruptibleRegion(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof EObject)
						return (!basePart.getInInterruptibleRegionTable().contains(element));
					return false;
				}

			});
			basePart.addFilterToInInterruptibleRegion(new EObjectFilter(UMLPackage.eINSTANCE.getInterruptibleActivityRegion()));
			// Start of user code for additional businessfilters for inInterruptibleRegion
			
			// End of user code
			basePart.addFilterToRedefinedNode(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof EObject)
						return (!basePart.getRedefinedNodeTable().contains(element));
					return false;
				}

			});
			basePart.addFilterToRedefinedNode(new EObjectFilter(UMLPackage.eINSTANCE.getActivityNode()));
			// Start of user code for additional businessfilters for redefinedNode
			
			// End of user code
			basePart.addFilterToHandler(new ViewerFilter() {

					/*
					 * (non-Javadoc)
					 * 
					 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
					 */
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						return (element instanceof String && element.equals("")) || (element instanceof ExceptionHandler); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for handler
			
			// End of user code
			basePart.addFilterToLocalPrecondition(new ViewerFilter() {

					/*
					 * (non-Javadoc)
					 * 
					 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
					 */
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						return (element instanceof String && element.equals("")) || (element instanceof Constraint); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for localPrecondition
			
			// End of user code
			basePart.addFilterToLocalPostcondition(new ViewerFilter() {

					/*
					 * (non-Javadoc)
					 * 
					 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
					 */
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						return (element instanceof String && element.equals("")) || (element instanceof Constraint); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for localPostcondition
			
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
		if (readExtentAction != null) {
			List ownedCommentToAddFromOwnedComment = basePart.getOwnedCommentToAdd();
			for (Iterator iter = ownedCommentToAddFromOwnedComment.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getElement_OwnedComment(), iter.next()));
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
				cc.append(MoveCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getComment(), moveElement.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getNamedElement_Name(), basePart.getName()));

			cc.append(SetCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getNamedElement_Visibility(), basePart.getVisibility()));

			List clientDependencyToAddFromClientDependency = basePart.getClientDependencyToAdd();
			for (Iterator iter = clientDependencyToAddFromClientDependency.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getNamedElement_ClientDependency(), iter.next()));
			List clientDependencyToRemoveFromClientDependency = basePart.getClientDependencyToRemove();
			for (Iterator iter = clientDependencyToRemoveFromClientDependency.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getNamedElement_ClientDependency(), iter.next()));
			//List clientDependencyToMoveFromClientDependency = basePart.getClientDependencyToMove();
			//for (Iterator iter = clientDependencyToMoveFromClientDependency.iterator(); iter.hasNext();){
			//	org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			//	cc.append(MoveCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getDependency(), moveElement.getElement(), moveElement.getIndex()));
			//}
			cc.append(SetCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf(), basePart.getIsLeaf()));

			List outgoingToAddFromOutgoing = basePart.getOutgoingToAdd();
			for (Iterator iter = outgoingToAddFromOutgoing.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_Outgoing(), iter.next()));
			List outgoingToRemoveFromOutgoing = basePart.getOutgoingToRemove();
			for (Iterator iter = outgoingToRemoveFromOutgoing.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_Outgoing(), iter.next()));
			//List outgoingToMoveFromOutgoing = basePart.getOutgoingToMove();
			//for (Iterator iter = outgoingToMoveFromOutgoing.iterator(); iter.hasNext();){
			//	org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			//	cc.append(MoveCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityEdge(), moveElement.getElement(), moveElement.getIndex()));
			//}
			List incomingToAddFromIncoming = basePart.getIncomingToAdd();
			for (Iterator iter = incomingToAddFromIncoming.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_Incoming(), iter.next()));
			List incomingToRemoveFromIncoming = basePart.getIncomingToRemove();
			for (Iterator iter = incomingToRemoveFromIncoming.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_Incoming(), iter.next()));
			//List incomingToMoveFromIncoming = basePart.getIncomingToMove();
			//for (Iterator iter = incomingToMoveFromIncoming.iterator(); iter.hasNext();){
			//	org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			//	cc.append(MoveCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityEdge(), moveElement.getElement(), moveElement.getIndex()));
			//}
			List inPartitionToAddFromInPartition = basePart.getInPartitionToAdd();
			for (Iterator iter = inPartitionToAddFromInPartition.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_InPartition(), iter.next()));
			List inPartitionToRemoveFromInPartition = basePart.getInPartitionToRemove();
			for (Iterator iter = inPartitionToRemoveFromInPartition.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_InPartition(), iter.next()));
			//List inPartitionToMoveFromInPartition = basePart.getInPartitionToMove();
			//for (Iterator iter = inPartitionToMoveFromInPartition.iterator(); iter.hasNext();){
			//	org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			//	cc.append(MoveCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityPartition(), moveElement.getElement(), moveElement.getIndex()));
			//}
			List inInterruptibleRegionToAddFromInInterruptibleRegion = basePart.getInInterruptibleRegionToAdd();
			for (Iterator iter = inInterruptibleRegionToAddFromInInterruptibleRegion.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_InInterruptibleRegion(), iter.next()));
			List inInterruptibleRegionToRemoveFromInInterruptibleRegion = basePart.getInInterruptibleRegionToRemove();
			for (Iterator iter = inInterruptibleRegionToRemoveFromInInterruptibleRegion.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_InInterruptibleRegion(), iter.next()));
			//List inInterruptibleRegionToMoveFromInInterruptibleRegion = basePart.getInInterruptibleRegionToMove();
			//for (Iterator iter = inInterruptibleRegionToMoveFromInInterruptibleRegion.iterator(); iter.hasNext();){
			//	org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			//	cc.append(MoveCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getInterruptibleActivityRegion(), moveElement.getElement(), moveElement.getIndex()));
			//}
			List redefinedNodeToAddFromRedefinedNode = basePart.getRedefinedNodeToAdd();
			for (Iterator iter = redefinedNodeToAddFromRedefinedNode.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_RedefinedNode(), iter.next()));
			List redefinedNodeToRemoveFromRedefinedNode = basePart.getRedefinedNodeToRemove();
			for (Iterator iter = redefinedNodeToRemoveFromRedefinedNode.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_RedefinedNode(), iter.next()));
			//List redefinedNodeToMoveFromRedefinedNode = basePart.getRedefinedNodeToMove();
			//for (Iterator iter = redefinedNodeToMoveFromRedefinedNode.iterator(); iter.hasNext();){
			//	org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			//	cc.append(MoveCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode(), moveElement.getElement(), moveElement.getIndex()));
			//}
			List handlerToAddFromHandler = basePart.getHandlerToAdd();
			for (Iterator iter = handlerToAddFromHandler.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getExecutableNode_Handler(), iter.next()));
			Map handlerToRefreshFromHandler = basePart.getHandlerToEdit();
			for (Iterator iter = handlerToRefreshFromHandler.keySet().iterator(); iter.hasNext();) {
				
				// Start of user code for handler reference refreshment from handler
				
				ExceptionHandler nextElement = (ExceptionHandler) iter.next();
				ExceptionHandler handler = (ExceptionHandler) handlerToRefreshFromHandler.get(nextElement);
				
				// End of user code
				
			}
			List handlerToRemoveFromHandler = basePart.getHandlerToRemove();
			for (Iterator iter = handlerToRemoveFromHandler.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List handlerToMoveFromHandler = basePart.getHandlerToMove();
			for (Iterator iter = handlerToMoveFromHandler.iterator(); iter.hasNext();){
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
				cc.append(MoveCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getExceptionHandler(), moveElement.getElement(), moveElement.getIndex()));
			}
			List localPreconditionToAddFromLocalPrecondition = basePart.getLocalPreconditionToAdd();
			for (Iterator iter = localPreconditionToAddFromLocalPrecondition.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getAction_LocalPrecondition(), iter.next()));
			Map localPreconditionToRefreshFromLocalPrecondition = basePart.getLocalPreconditionToEdit();
			for (Iterator iter = localPreconditionToRefreshFromLocalPrecondition.keySet().iterator(); iter.hasNext();) {
				
				// Start of user code for localPrecondition reference refreshment from localPrecondition
				
				Constraint nextElement = (Constraint) iter.next();
				Constraint localPrecondition = (Constraint) localPreconditionToRefreshFromLocalPrecondition.get(nextElement);
				
				// End of user code
				
			}
			List localPreconditionToRemoveFromLocalPrecondition = basePart.getLocalPreconditionToRemove();
			for (Iterator iter = localPreconditionToRemoveFromLocalPrecondition.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List localPreconditionToMoveFromLocalPrecondition = basePart.getLocalPreconditionToMove();
			for (Iterator iter = localPreconditionToMoveFromLocalPrecondition.iterator(); iter.hasNext();){
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
				cc.append(MoveCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getConstraint(), moveElement.getElement(), moveElement.getIndex()));
			}
			List localPostconditionToAddFromLocalPostcondition = basePart.getLocalPostconditionToAdd();
			for (Iterator iter = localPostconditionToAddFromLocalPostcondition.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getAction_LocalPostcondition(), iter.next()));
			Map localPostconditionToRefreshFromLocalPostcondition = basePart.getLocalPostconditionToEdit();
			for (Iterator iter = localPostconditionToRefreshFromLocalPostcondition.keySet().iterator(); iter.hasNext();) {
				
				// Start of user code for localPostcondition reference refreshment from localPostcondition
				
				Constraint nextElement = (Constraint) iter.next();
				Constraint localPostcondition = (Constraint) localPostconditionToRefreshFromLocalPostcondition.get(nextElement);
				
				// End of user code
				
			}
			List localPostconditionToRemoveFromLocalPostcondition = basePart.getLocalPostconditionToRemove();
			for (Iterator iter = localPostconditionToRemoveFromLocalPostcondition.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List localPostconditionToMoveFromLocalPostcondition = basePart.getLocalPostconditionToMove();
			for (Iterator iter = localPostconditionToMoveFromLocalPostcondition.iterator(); iter.hasNext();){
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
				cc.append(MoveCommand.create(editingDomain, readExtentAction, UMLPackage.eINSTANCE.getConstraint(), moveElement.getElement(), moveElement.getIndex()));
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
		if (source instanceof ReadExtentAction) {
			ReadExtentAction readExtentActionToUpdate = (ReadExtentAction)source;
			readExtentActionToUpdate.getOwnedComments().addAll(basePart.getOwnedCommentToAdd());
			readExtentActionToUpdate.setName(basePart.getName());

			readExtentActionToUpdate.setVisibility((VisibilityKind)basePart.getVisibility());	

			readExtentActionToUpdate.getClientDependencies().addAll(basePart.getClientDependencyToAdd());
			readExtentActionToUpdate.setIsLeaf(new Boolean(basePart.getIsLeaf()).booleanValue());

			readExtentActionToUpdate.getOutgoings().addAll(basePart.getOutgoingToAdd());
			readExtentActionToUpdate.getIncomings().addAll(basePart.getIncomingToAdd());
			readExtentActionToUpdate.getInPartitions().addAll(basePart.getInPartitionToAdd());
			readExtentActionToUpdate.getInInterruptibleRegions().addAll(basePart.getInInterruptibleRegionToAdd());
			readExtentActionToUpdate.getRedefinedNodes().addAll(basePart.getRedefinedNodeToAdd());
			readExtentActionToUpdate.getHandlers().addAll(basePart.getHandlerToAdd());
			readExtentActionToUpdate.getLocalPreconditions().addAll(basePart.getLocalPreconditionToAdd());
			readExtentActionToUpdate.getLocalPostconditions().addAll(basePart.getLocalPostconditionToAdd());


			return readExtentActionToUpdate;
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
			if (UMLViewsRepository.ReadExtentAction.ownedComment == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Comment oldValue = (Comment)event.getOldValue();
					Comment newValue = (Comment)event.getNewValue();
					
					// Start of user code for ownedComment live update command
					// TODO: Complete the readExtentAction update command
					// End of user code
					
				}
				else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getElement_OwnedComment(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getComment(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReadExtentAction.name == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getNamedElement_Name(), event.getNewValue()));

			if (UMLViewsRepository.ReadExtentAction.visibility == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getNamedElement_Visibility(), event.getNewValue()));

			if (UMLViewsRepository.ReadExtentAction.clientDependency == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getNamedElement_ClientDependency(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getNamedElement_ClientDependency(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getNamedElement_ClientDependency(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReadExtentAction.isLeaf == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf(), event.getNewValue()));

			if (UMLViewsRepository.ReadExtentAction.outgoing == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_Outgoing(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_Outgoing(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_Outgoing(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReadExtentAction.incoming == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_Incoming(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_Incoming(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_Incoming(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReadExtentAction.inPartition == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_InPartition(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_InPartition(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_InPartition(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReadExtentAction.inInterruptibleRegion == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_InInterruptibleRegion(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_InInterruptibleRegion(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_InInterruptibleRegion(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReadExtentAction.redefinedNode == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_RedefinedNode(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_RedefinedNode(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getActivityNode_RedefinedNode(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReadExtentAction.handler == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					ExceptionHandler oldValue = (ExceptionHandler)event.getOldValue();
					ExceptionHandler newValue = (ExceptionHandler)event.getNewValue();
					
					// Start of user code for handler live update command
					// TODO: Complete the readExtentAction update command
					// End of user code
					
				}
				else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getExecutableNode_Handler(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getExceptionHandler(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReadExtentAction.localPrecondition == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Constraint oldValue = (Constraint)event.getOldValue();
					Constraint newValue = (Constraint)event.getNewValue();
					
					// Start of user code for localPrecondition live update command
					// TODO: Complete the readExtentAction update command
					// End of user code
					
				}
				else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getAction_LocalPrecondition(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getConstraint(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReadExtentAction.localPostcondition == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Constraint oldValue = (Constraint)event.getOldValue();
					Constraint newValue = (Constraint)event.getNewValue();
					
					// Start of user code for localPostcondition live update command
					// TODO: Complete the readExtentAction update command
					// End of user code
					
				}
				else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getAction_LocalPostcondition(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, readExtentAction, UMLPackage.eINSTANCE.getConstraint(), event.getNewValue(), event.getNewIndex()));
			}


			liveEditingDomain.getCommandStack().execute(command);
		} else if (PropertiesEditionEvent.CHANGE == event.getState()) {
			Diagnostic diag = this.validateValue(event);
			if (diag != null && diag.getSeverity() != Diagnostic.OK) {

				if (UMLViewsRepository.ReadExtentAction.name == event.getAffectedEditor())
					basePart.setMessageForName(diag.getMessage(), IMessageProvider.ERROR);













			} else {

				if (UMLViewsRepository.ReadExtentAction.name == event.getAffectedEditor())
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
		return key == UMLViewsRepository.ReadExtentAction.isLeaf;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#getHelpContent(java.lang.String, int)
	 */
	public String getHelpContent(String key, int kind) {
		if (key == UMLViewsRepository.ReadExtentAction.ownedComment)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReadExtentAction.name)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReadExtentAction.visibility)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReadExtentAction.clientDependency)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReadExtentAction.isLeaf)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReadExtentAction.outgoing)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReadExtentAction.incoming)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReadExtentAction.inPartition)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReadExtentAction.inInterruptibleRegion)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReadExtentAction.redefinedNode)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReadExtentAction.handler)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReadExtentAction.localPrecondition)
			return null
; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReadExtentAction.localPostcondition)
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
			if (UMLViewsRepository.ReadExtentAction.name == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getNamedElement_Name().getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getNamedElement_Name().getEAttributeType(), newValue);
			}
			if (UMLViewsRepository.ReadExtentAction.visibility == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getNamedElement_Visibility().getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getNamedElement_Visibility().getEAttributeType(), newValue);
			}
			if (UMLViewsRepository.ReadExtentAction.isLeaf == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf().getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf().getEAttributeType(), newValue);
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
			return Diagnostician.INSTANCE.validate(readExtentAction);
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
			readExtentAction.eAdapters().remove(semanticAdapter);
	}

}

