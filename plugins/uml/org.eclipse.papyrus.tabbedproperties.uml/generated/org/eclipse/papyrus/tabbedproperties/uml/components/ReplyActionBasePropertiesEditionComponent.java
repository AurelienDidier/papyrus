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
import org.eclipse.papyrus.tabbedproperties.uml.parts.ReplyActionPropertiesEditionPart;
import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.ExceptionHandler;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.ReplyAction;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;

// End of user code

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class ReplyActionBasePropertiesEditionComponent extends StandardPropertiesEditionComponent {

	public static String BASE_PART = "Base"; //$NON-NLS-1$

	private String[] parts = { BASE_PART };

	/**
	 * The EObject to edit
	 */
	private ReplyAction replyAction;

	/**
	 * The Base part
	 */
	private ReplyActionPropertiesEditionPart basePart;

	/**
	 * Default constructor
	 */
	public ReplyActionBasePropertiesEditionComponent(EObject replyAction, String editing_mode) {
		if (replyAction instanceof ReplyAction) {
			this.replyAction = (ReplyAction) replyAction;
			if (IPropertiesEditionComponent.LIVE_MODE.equals(editing_mode)) {
				semanticAdapter = initializeSemanticAdapter();
				this.replyAction.eAdapters().add(semanticAdapter);
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
					ReplyActionBasePropertiesEditionComponent.this.dispose();
				else {
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getElement_OwnedComment() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getComment())) {
						basePart.updateOwnedComment(replyAction);
					}
					if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(msg.getFeature()) && basePart != null)
						basePart.setName((String) msg.getNewValue());

					if (UMLPackage.eINSTANCE.getNamedElement_Visibility().equals(msg.getFeature()) && basePart != null)
						basePart.setVisibility((Enumerator) msg.getNewValue());

					if (UMLPackage.eINSTANCE.getNamedElement_ClientDependency().equals(msg.getFeature()))
						basePart.updateClientDependency(replyAction);
					if (UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf().equals(msg.getFeature())
							&& basePart != null)
						basePart.setIsLeaf((Boolean) msg.getNewValue());

					if (UMLPackage.eINSTANCE.getActivityNode_Outgoing().equals(msg.getFeature()))
						basePart.updateOutgoing(replyAction);
					if (UMLPackage.eINSTANCE.getActivityNode_Incoming().equals(msg.getFeature()))
						basePart.updateIncoming(replyAction);
					if (UMLPackage.eINSTANCE.getActivityNode_InPartition().equals(msg.getFeature()))
						basePart.updateInPartition(replyAction);
					if (UMLPackage.eINSTANCE.getActivityNode_InInterruptibleRegion().equals(msg.getFeature()))
						basePart.updateInInterruptibleRegion(replyAction);
					if (UMLPackage.eINSTANCE.getActivityNode_RedefinedNode().equals(msg.getFeature()))
						basePart.updateRedefinedNode(replyAction);
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getExecutableNode_Handler() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getExceptionHandler())) {
						basePart.updateHandler(replyAction);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getAction_LocalPrecondition() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getConstraint())) {
						basePart.updateLocalPrecondition(replyAction);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getAction_LocalPostcondition() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getConstraint())) {
						basePart.updateLocalPostcondition(replyAction);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getReplyAction_ReplyValue() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getInputPin())) {
						basePart.updateReplyValue(replyAction);
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
			return UMLViewsRepository.ReplyAction.class;
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
		if (replyAction != null && BASE_PART.equals(key)) {
			if (basePart == null) {
				IPropertiesEditionPartProvider provider = PropertiesEditionPartProviderService.getInstance()
						.getProvider(UMLViewsRepository.class);
				if (provider != null) {
					basePart = (ReplyActionPropertiesEditionPart) provider.getPropertiesEditionPart(
							UMLViewsRepository.ReplyAction.class, kind, this);
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
		if (key == UMLViewsRepository.ReplyAction.class)
			this.basePart = (ReplyActionPropertiesEditionPart) propertiesEditionPart;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#initPart(java.lang.Class,
	 *      int, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.resource.ResourceSet)
	 */
	public void initPart(java.lang.Class key, int kind, EObject elt, ResourceSet allResource) {
		if (basePart != null && key == UMLViewsRepository.ReplyAction.class) {
			((IPropertiesEditionPart) basePart).setContext(elt, allResource);
			ReplyAction replyAction = (ReplyAction) elt;
			// init values
			basePart.initOwnedComment(replyAction, null, UMLPackage.eINSTANCE.getElement_OwnedComment());
			if (replyAction.getName() != null)
				basePart.setName(replyAction.getName());

			basePart.initVisibility((EEnum) UMLPackage.eINSTANCE.getNamedElement_Visibility().getEType(), replyAction
					.getVisibility());
			basePart.initClientDependency(replyAction, null, UMLPackage.eINSTANCE.getNamedElement_ClientDependency());
			basePart.setIsLeaf(replyAction.isLeaf());

			basePart.initOutgoing(replyAction, null, UMLPackage.eINSTANCE.getActivityNode_Outgoing());
			basePart.initIncoming(replyAction, null, UMLPackage.eINSTANCE.getActivityNode_Incoming());
			basePart.initInPartition(replyAction, null, UMLPackage.eINSTANCE.getActivityNode_InPartition());
			basePart.initInInterruptibleRegion(replyAction, null, UMLPackage.eINSTANCE
					.getActivityNode_InInterruptibleRegion());
			basePart.initRedefinedNode(replyAction, null, UMLPackage.eINSTANCE.getActivityNode_RedefinedNode());
			basePart.initHandler(replyAction, null, UMLPackage.eINSTANCE.getExecutableNode_Handler());
			basePart.initLocalPrecondition(replyAction, null, UMLPackage.eINSTANCE.getAction_LocalPrecondition());
			basePart.initLocalPostcondition(replyAction, null, UMLPackage.eINSTANCE.getAction_LocalPostcondition());
			basePart.initReplyValue(replyAction, null, UMLPackage.eINSTANCE.getReplyAction_ReplyValue());

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

			basePart.addFilterToOutgoing(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
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
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
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
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
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
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof EObject)
						return (!basePart.getInInterruptibleRegionTable().contains(element));
					return false;
				}

			});
			basePart.addFilterToInInterruptibleRegion(new EObjectFilter(UMLPackage.eINSTANCE
					.getInterruptibleActivityRegion()));
			// Start of user code for additional businessfilters for inInterruptibleRegion

			// End of user code
			basePart.addFilterToRedefinedNode(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
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
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
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
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
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
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof Constraint); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for localPostcondition

			// End of user code
			basePart.addFilterToReplyValue(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof InputPin); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for replyValue

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
		if (replyAction != null) {
			List ownedCommentToAddFromOwnedComment = basePart.getOwnedCommentToAdd();
			for (Iterator iter = ownedCommentToAddFromOwnedComment.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE.getElement_OwnedComment(),
						iter.next()));
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
				cc.append(MoveCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE.getComment(), moveElement
						.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE.getNamedElement_Name(),
					basePart.getName()));

			cc.append(SetCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE.getNamedElement_Visibility(),
					basePart.getVisibility()));

			List clientDependencyToAddFromClientDependency = basePart.getClientDependencyToAdd();
			for (Iterator iter = clientDependencyToAddFromClientDependency.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE
						.getNamedElement_ClientDependency(), iter.next()));
			List clientDependencyToRemoveFromClientDependency = basePart.getClientDependencyToRemove();
			for (Iterator iter = clientDependencyToRemoveFromClientDependency.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE
						.getNamedElement_ClientDependency(), iter.next()));
			// List clientDependencyToMoveFromClientDependency =
			// basePart.getClientDependencyToMove();
			// for (Iterator iter = clientDependencyToMoveFromClientDependency.iterator();
			// iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, replyAction,
			// UMLPackage.eINSTANCE.getDependency(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			cc.append(SetCommand.create(editingDomain, replyAction,
					UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf(), basePart.getIsLeaf()));

			List outgoingToAddFromOutgoing = basePart.getOutgoingToAdd();
			for (Iterator iter = outgoingToAddFromOutgoing.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, replyAction,
						UMLPackage.eINSTANCE.getActivityNode_Outgoing(), iter.next()));
			List outgoingToRemoveFromOutgoing = basePart.getOutgoingToRemove();
			for (Iterator iter = outgoingToRemoveFromOutgoing.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE
						.getActivityNode_Outgoing(), iter.next()));
			// List outgoingToMoveFromOutgoing = basePart.getOutgoingToMove();
			// for (Iterator iter = outgoingToMoveFromOutgoing.iterator(); iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, replyAction,
			// UMLPackage.eINSTANCE.getActivityEdge(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List incomingToAddFromIncoming = basePart.getIncomingToAdd();
			for (Iterator iter = incomingToAddFromIncoming.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, replyAction,
						UMLPackage.eINSTANCE.getActivityNode_Incoming(), iter.next()));
			List incomingToRemoveFromIncoming = basePart.getIncomingToRemove();
			for (Iterator iter = incomingToRemoveFromIncoming.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE
						.getActivityNode_Incoming(), iter.next()));
			// List incomingToMoveFromIncoming = basePart.getIncomingToMove();
			// for (Iterator iter = incomingToMoveFromIncoming.iterator(); iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, replyAction,
			// UMLPackage.eINSTANCE.getActivityEdge(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List inPartitionToAddFromInPartition = basePart.getInPartitionToAdd();
			for (Iterator iter = inPartitionToAddFromInPartition.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE
						.getActivityNode_InPartition(), iter.next()));
			List inPartitionToRemoveFromInPartition = basePart.getInPartitionToRemove();
			for (Iterator iter = inPartitionToRemoveFromInPartition.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE
						.getActivityNode_InPartition(), iter.next()));
			// List inPartitionToMoveFromInPartition = basePart.getInPartitionToMove();
			// for (Iterator iter = inPartitionToMoveFromInPartition.iterator(); iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, replyAction,
			// UMLPackage.eINSTANCE.getActivityPartition(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List inInterruptibleRegionToAddFromInInterruptibleRegion = basePart.getInInterruptibleRegionToAdd();
			for (Iterator iter = inInterruptibleRegionToAddFromInInterruptibleRegion.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE
						.getActivityNode_InInterruptibleRegion(), iter.next()));
			List inInterruptibleRegionToRemoveFromInInterruptibleRegion = basePart.getInInterruptibleRegionToRemove();
			for (Iterator iter = inInterruptibleRegionToRemoveFromInInterruptibleRegion.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE
						.getActivityNode_InInterruptibleRegion(), iter.next()));
			// List inInterruptibleRegionToMoveFromInInterruptibleRegion =
			// basePart.getInInterruptibleRegionToMove();
			// for (Iterator iter = inInterruptibleRegionToMoveFromInInterruptibleRegion.iterator();
			// iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, replyAction,
			// UMLPackage.eINSTANCE.getInterruptibleActivityRegion(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List redefinedNodeToAddFromRedefinedNode = basePart.getRedefinedNodeToAdd();
			for (Iterator iter = redefinedNodeToAddFromRedefinedNode.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE
						.getActivityNode_RedefinedNode(), iter.next()));
			List redefinedNodeToRemoveFromRedefinedNode = basePart.getRedefinedNodeToRemove();
			for (Iterator iter = redefinedNodeToRemoveFromRedefinedNode.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE
						.getActivityNode_RedefinedNode(), iter.next()));
			// List redefinedNodeToMoveFromRedefinedNode = basePart.getRedefinedNodeToMove();
			// for (Iterator iter = redefinedNodeToMoveFromRedefinedNode.iterator();
			// iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, replyAction,
			// UMLPackage.eINSTANCE.getActivityNode(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List handlerToAddFromHandler = basePart.getHandlerToAdd();
			for (Iterator iter = handlerToAddFromHandler.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE
						.getExecutableNode_Handler(), iter.next()));
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
			for (Iterator iter = handlerToMoveFromHandler.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE.getExceptionHandler(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List localPreconditionToAddFromLocalPrecondition = basePart.getLocalPreconditionToAdd();
			for (Iterator iter = localPreconditionToAddFromLocalPrecondition.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE
						.getAction_LocalPrecondition(), iter.next()));
			Map localPreconditionToRefreshFromLocalPrecondition = basePart.getLocalPreconditionToEdit();
			for (Iterator iter = localPreconditionToRefreshFromLocalPrecondition.keySet().iterator(); iter.hasNext();) {

				// Start of user code for localPrecondition reference refreshment from
				// localPrecondition

				Constraint nextElement = (Constraint) iter.next();
				Constraint localPrecondition = (Constraint) localPreconditionToRefreshFromLocalPrecondition
						.get(nextElement);

				// End of user code

			}
			List localPreconditionToRemoveFromLocalPrecondition = basePart.getLocalPreconditionToRemove();
			for (Iterator iter = localPreconditionToRemoveFromLocalPrecondition.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List localPreconditionToMoveFromLocalPrecondition = basePart.getLocalPreconditionToMove();
			for (Iterator iter = localPreconditionToMoveFromLocalPrecondition.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE.getConstraint(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List localPostconditionToAddFromLocalPostcondition = basePart.getLocalPostconditionToAdd();
			for (Iterator iter = localPostconditionToAddFromLocalPostcondition.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE
						.getAction_LocalPostcondition(), iter.next()));
			Map localPostconditionToRefreshFromLocalPostcondition = basePart.getLocalPostconditionToEdit();
			for (Iterator iter = localPostconditionToRefreshFromLocalPostcondition.keySet().iterator(); iter.hasNext();) {

				// Start of user code for localPostcondition reference refreshment from
				// localPostcondition

				Constraint nextElement = (Constraint) iter.next();
				Constraint localPostcondition = (Constraint) localPostconditionToRefreshFromLocalPostcondition
						.get(nextElement);

				// End of user code

			}
			List localPostconditionToRemoveFromLocalPostcondition = basePart.getLocalPostconditionToRemove();
			for (Iterator iter = localPostconditionToRemoveFromLocalPostcondition.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List localPostconditionToMoveFromLocalPostcondition = basePart.getLocalPostconditionToMove();
			for (Iterator iter = localPostconditionToMoveFromLocalPostcondition.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE.getConstraint(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List replyValueToAddFromReplyValue = basePart.getReplyValueToAdd();
			for (Iterator iter = replyValueToAddFromReplyValue.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE
						.getReplyAction_ReplyValue(), iter.next()));
			Map replyValueToRefreshFromReplyValue = basePart.getReplyValueToEdit();
			for (Iterator iter = replyValueToRefreshFromReplyValue.keySet().iterator(); iter.hasNext();) {

				// Start of user code for replyValue reference refreshment from replyValue

				InputPin nextElement = (InputPin) iter.next();
				InputPin replyValue = (InputPin) replyValueToRefreshFromReplyValue.get(nextElement);

				// End of user code

			}
			List replyValueToRemoveFromReplyValue = basePart.getReplyValueToRemove();
			for (Iterator iter = replyValueToRemoveFromReplyValue.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List replyValueToMoveFromReplyValue = basePart.getReplyValueToMove();
			for (Iterator iter = replyValueToMoveFromReplyValue.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, replyAction, UMLPackage.eINSTANCE.getInputPin(),
						moveElement.getElement(), moveElement.getIndex()));
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
		if (source instanceof ReplyAction) {
			ReplyAction replyActionToUpdate = (ReplyAction) source;
			replyActionToUpdate.getOwnedComments().addAll(basePart.getOwnedCommentToAdd());
			replyActionToUpdate.setName(basePart.getName());

			replyActionToUpdate.setVisibility((VisibilityKind) basePart.getVisibility());

			replyActionToUpdate.getClientDependencies().addAll(basePart.getClientDependencyToAdd());
			replyActionToUpdate.setIsLeaf(new Boolean(basePart.getIsLeaf()).booleanValue());

			replyActionToUpdate.getOutgoings().addAll(basePart.getOutgoingToAdd());
			replyActionToUpdate.getIncomings().addAll(basePart.getIncomingToAdd());
			replyActionToUpdate.getInPartitions().addAll(basePart.getInPartitionToAdd());
			replyActionToUpdate.getInInterruptibleRegions().addAll(basePart.getInInterruptibleRegionToAdd());
			replyActionToUpdate.getRedefinedNodes().addAll(basePart.getRedefinedNodeToAdd());
			replyActionToUpdate.getHandlers().addAll(basePart.getHandlerToAdd());
			replyActionToUpdate.getLocalPreconditions().addAll(basePart.getLocalPreconditionToAdd());
			replyActionToUpdate.getLocalPostconditions().addAll(basePart.getLocalPostconditionToAdd());
			replyActionToUpdate.getReplyValues().addAll(basePart.getReplyValueToAdd());

			return replyActionToUpdate;
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
			if (UMLViewsRepository.ReplyAction.ownedComment == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Comment oldValue = (Comment) event.getOldValue();
					Comment newValue = (Comment) event.getNewValue();

					// Start of user code for ownedComment live update command
					// TODO: Complete the replyAction update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getElement_OwnedComment(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, replyAction,
							UMLPackage.eINSTANCE.getComment(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReplyAction.name == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
						.getNamedElement_Name(), event.getNewValue()));

			if (UMLViewsRepository.ReplyAction.visibility == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
						.getNamedElement_Visibility(), event.getNewValue()));

			if (UMLViewsRepository.ReplyAction.clientDependency == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getNamedElement_ClientDependency(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getNamedElement_ClientDependency(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getNamedElement_ClientDependency(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReplyAction.isLeaf == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
						.getRedefinableElement_IsLeaf(), event.getNewValue()));

			if (UMLViewsRepository.ReplyAction.outgoing == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getActivityNode_Outgoing(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getActivityNode_Outgoing(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getActivityNode_Outgoing(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReplyAction.incoming == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getActivityNode_Incoming(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getActivityNode_Incoming(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getActivityNode_Incoming(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReplyAction.inPartition == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getActivityNode_InPartition(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getActivityNode_InPartition(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getActivityNode_InPartition(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReplyAction.inInterruptibleRegion == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getActivityNode_InInterruptibleRegion(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getActivityNode_InInterruptibleRegion(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getActivityNode_InInterruptibleRegion(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReplyAction.redefinedNode == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getActivityNode_RedefinedNode(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getActivityNode_RedefinedNode(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getActivityNode_RedefinedNode(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReplyAction.handler == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					ExceptionHandler oldValue = (ExceptionHandler) event.getOldValue();
					ExceptionHandler newValue = (ExceptionHandler) event.getNewValue();

					// Start of user code for handler live update command
					// TODO: Complete the replyAction update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getExecutableNode_Handler(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getExceptionHandler(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReplyAction.localPrecondition == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Constraint oldValue = (Constraint) event.getOldValue();
					Constraint newValue = (Constraint) event.getNewValue();

					// Start of user code for localPrecondition live update command
					// TODO: Complete the replyAction update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getAction_LocalPrecondition(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getConstraint(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReplyAction.localPostcondition == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Constraint oldValue = (Constraint) event.getOldValue();
					Constraint newValue = (Constraint) event.getNewValue();

					// Start of user code for localPostcondition live update command
					// TODO: Complete the replyAction update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getAction_LocalPostcondition(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getConstraint(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ReplyAction.replyValue == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					InputPin oldValue = (InputPin) event.getOldValue();
					InputPin newValue = (InputPin) event.getNewValue();

					// Start of user code for replyValue live update command
					// TODO: Complete the replyAction update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getReplyAction_ReplyValue(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, replyAction, UMLPackage.eINSTANCE
							.getInputPin(), event.getNewValue(), event.getNewIndex()));
			}

			liveEditingDomain.getCommandStack().execute(command);
		} else if (PropertiesEditionEvent.CHANGE == event.getState()) {
			Diagnostic diag = this.validateValue(event);
			if (diag != null && diag.getSeverity() != Diagnostic.OK) {

				if (UMLViewsRepository.ReplyAction.name == event.getAffectedEditor())
					basePart.setMessageForName(diag.getMessage(), IMessageProvider.ERROR);

			} else {

				if (UMLViewsRepository.ReplyAction.name == event.getAffectedEditor())
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
		return key == UMLViewsRepository.ReplyAction.isLeaf;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#getHelpContent(java.lang.String,
	 *      int)
	 */
	public String getHelpContent(String key, int kind) {
		if (key == UMLViewsRepository.ReplyAction.ownedComment)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReplyAction.name)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReplyAction.visibility)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReplyAction.clientDependency)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReplyAction.isLeaf)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReplyAction.outgoing)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReplyAction.incoming)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReplyAction.inPartition)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReplyAction.inInterruptibleRegion)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReplyAction.redefinedNode)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReplyAction.handler)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReplyAction.localPrecondition)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReplyAction.localPostcondition)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ReplyAction.replyValue)
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
			if (UMLViewsRepository.ReplyAction.name == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getNamedElement_Name()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getNamedElement_Name().getEAttributeType(),
						newValue);
			}
			if (UMLViewsRepository.ReplyAction.visibility == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getNamedElement_Visibility()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getNamedElement_Visibility()
						.getEAttributeType(), newValue);
			}
			if (UMLViewsRepository.ReplyAction.isLeaf == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf()
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
			return Diagnostician.INSTANCE.validate(replyAction);
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
			replyAction.eAdapters().remove(semanticAdapter);
	}

}
