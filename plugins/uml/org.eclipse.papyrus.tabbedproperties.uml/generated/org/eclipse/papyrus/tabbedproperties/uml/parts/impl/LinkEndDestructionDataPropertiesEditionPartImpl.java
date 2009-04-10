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
package org.eclipse.papyrus.tabbedproperties.uml.parts.impl;

// Start of user code for imports

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.parts.ISWTPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.api.policies.IPropertiesEditionPolicy;
import org.eclipse.emf.eef.runtime.api.providers.IPropertiesEditionPolicyProvider;
import org.eclipse.emf.eef.runtime.impl.notify.PathedPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.parts.CompositePropertiesEditionPart;
import org.eclipse.emf.eef.runtime.impl.policies.EObjectPropertiesEditionContext;
import org.eclipse.emf.eef.runtime.impl.services.PropertiesEditionPolicyProviderService;
import org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.SWTUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.papyrus.tabbedproperties.uml.parts.LinkEndDestructionDataPropertiesEditionPart;
import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;
import org.eclipse.papyrus.tabbedproperties.uml.providers.UMLMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.QualifierValue;
import org.eclipse.uml2.uml.UMLFactory;

// End of user code
/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class LinkEndDestructionDataPropertiesEditionPartImpl extends CompositePropertiesEditionPart implements ISWTPropertiesEditionPart, LinkEndDestructionDataPropertiesEditionPart {

	private EMFListEditUtil ownedCommentEditUtil;
	private ReferencesTable<?> ownedComment;
	private EMFListEditUtil qualifierEditUtil;
	private ReferencesTable<?> qualifier;
	private Button isDestroyDuplicates;




	
	public LinkEndDestructionDataPropertiesEditionPartImpl(IPropertiesEditionComponent editionComponent) {
		super(editionComponent);
	}
	
	public Composite createFigure(final Composite parent) {
		view = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		view.setLayout(layout);	
		
		createControls(view);
		
		return view;
	}
	
	public void createControls(Composite view) { 
		createPropertiesGroup(view);
		
		// Start of user code for additional ui definition
		
		// End of user code		
	}

	protected void createPropertiesGroup(Composite parent) {
		Group propertiesGroup = new Group(parent, SWT.NONE);
		propertiesGroup.setText(UMLMessages.LinkEndDestructionDataPropertiesEditionPart_PropertiesGroupLabel);
		GridData propertiesGroupData = new GridData(GridData.FILL_HORIZONTAL);
		propertiesGroupData.horizontalSpan = 3;
		propertiesGroup.setLayoutData(propertiesGroupData);
		GridLayout propertiesGroupLayout = new GridLayout();
		propertiesGroupLayout.numColumns = 3;
		propertiesGroup.setLayout(propertiesGroupLayout);
		createOwnedCommentTableComposition(propertiesGroup);
		createQualifierTableComposition(propertiesGroup);
		createIsDestroyDuplicatesCheckbox(propertiesGroup);
   	}
	/**
	 * @param container
	 */
	protected void createOwnedCommentTableComposition(Composite parent) {
		this.ownedComment = new ReferencesTable<Comment>(UMLMessages.LinkEndDestructionDataPropertiesEditionPart_OwnedCommentLabel, new ReferencesTableListener<Comment>() {			
			public void handleAdd() { addToOwnedComment();}
			public void handleEdit(Comment element) { editOwnedComment(element); }
			public void handleMove(Comment element, int oldIndex, int newIndex) { moveOwnedComment(element, oldIndex, newIndex); }			
			public void handleRemove(Comment element) { removeFromOwnedComment(element); }
			public void navigateTo(Comment element) { System.out.println("---> navigateTo"); }
		});
		this.ownedComment.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.LinkEndDestructionData.ownedComment, UMLViewsRepository.SWT_KIND));
		this.ownedComment.createControls(parent);
		GridData ownedCommentData = new GridData(GridData.FILL_HORIZONTAL);
		ownedCommentData.horizontalSpan = 3;
		this.ownedComment.setLayoutData(ownedCommentData);
	}
		
	/**
	 * 
	 */
	private void moveOwnedComment(Comment element, int oldIndex, int newIndex) {
				
		EObject editedElement = ownedCommentEditUtil.foundCorrespondingEObject(element);
		ownedCommentEditUtil.moveElement(element, oldIndex, newIndex);
		ownedComment.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(LinkEndDestructionDataPropertiesEditionPartImpl.this, UMLViewsRepository.LinkEndDestructionData.ownedComment, PathedPropertiesEditionEvent.COMMIT, PathedPropertiesEditionEvent.MOVE, editedElement, newIndex));	
		
	}	
	
	/**
	 * 
	 */
	private void addToOwnedComment() {
	
		// Start of user code addToOwnedComment() method body


		Comment eObject = UMLFactory.eINSTANCE.createComment();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject,resourceSet));
			if (propertiesEditionObject != null) {
				ownedCommentEditUtil.addElement(propertiesEditionObject);
				ownedComment.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(LinkEndDestructionDataPropertiesEditionPartImpl.this, UMLViewsRepository.LinkEndDestructionData.ownedComment, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}
		
		
		// End of user code		
	}

	/**
	 * 
	 */
	private void removeFromOwnedComment(Comment element) {

		// Start of user code for the removeFromOwnedComment() method body

		EObject editedElement = ownedCommentEditUtil.foundCorrespondingEObject(element);
		ownedCommentEditUtil.removeElement(element);
		ownedComment.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(LinkEndDestructionDataPropertiesEditionPartImpl.this, UMLViewsRepository.LinkEndDestructionData.ownedComment, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editOwnedComment(Comment element) {

		// Start of user code editOwnedComment() method body
				 
		EObject editedElement = ownedCommentEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider	.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element,resourceSet));
			if (propertiesEditionObject != null) {
				ownedCommentEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedComment.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(LinkEndDestructionDataPropertiesEditionPartImpl.this, UMLViewsRepository.LinkEndDestructionData.ownedComment, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}
	/**
	 * @param container
	 */
	protected void createQualifierTableComposition(Composite parent) {
		this.qualifier = new ReferencesTable<QualifierValue>(UMLMessages.LinkEndDestructionDataPropertiesEditionPart_QualifierLabel, new ReferencesTableListener<QualifierValue>() {			
			public void handleAdd() { addToQualifier();}
			public void handleEdit(QualifierValue element) { editQualifier(element); }
			public void handleMove(QualifierValue element, int oldIndex, int newIndex) { moveQualifier(element, oldIndex, newIndex); }			
			public void handleRemove(QualifierValue element) { removeFromQualifier(element); }
			public void navigateTo(QualifierValue element) { System.out.println("---> navigateTo"); }
		});
		this.qualifier.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.LinkEndDestructionData.qualifier, UMLViewsRepository.SWT_KIND));
		this.qualifier.createControls(parent);
		GridData qualifierData = new GridData(GridData.FILL_HORIZONTAL);
		qualifierData.horizontalSpan = 3;
		this.qualifier.setLayoutData(qualifierData);
	}
		
	/**
	 * 
	 */
	private void moveQualifier(QualifierValue element, int oldIndex, int newIndex) {
				
		EObject editedElement = qualifierEditUtil.foundCorrespondingEObject(element);
		qualifierEditUtil.moveElement(element, oldIndex, newIndex);
		qualifier.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(LinkEndDestructionDataPropertiesEditionPartImpl.this, UMLViewsRepository.LinkEndDestructionData.qualifier, PathedPropertiesEditionEvent.COMMIT, PathedPropertiesEditionEvent.MOVE, editedElement, newIndex));	
		
	}	
	
	/**
	 * 
	 */
	private void addToQualifier() {
	
		// Start of user code addToQualifier() method body


		QualifierValue eObject = UMLFactory.eINSTANCE.createQualifierValue();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject,resourceSet));
			if (propertiesEditionObject != null) {
				qualifierEditUtil.addElement(propertiesEditionObject);
				qualifier.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(LinkEndDestructionDataPropertiesEditionPartImpl.this, UMLViewsRepository.LinkEndDestructionData.qualifier, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}
		
		
		// End of user code		
	}

	/**
	 * 
	 */
	private void removeFromQualifier(QualifierValue element) {

		// Start of user code for the removeFromQualifier() method body

		EObject editedElement = qualifierEditUtil.foundCorrespondingEObject(element);
		qualifierEditUtil.removeElement(element);
		qualifier.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(LinkEndDestructionDataPropertiesEditionPartImpl.this, UMLViewsRepository.LinkEndDestructionData.qualifier, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editQualifier(QualifierValue element) {

		// Start of user code editQualifier() method body
				 
		EObject editedElement = qualifierEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider	.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element,resourceSet));
			if (propertiesEditionObject != null) {
				qualifierEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				qualifier.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(LinkEndDestructionDataPropertiesEditionPartImpl.this, UMLViewsRepository.LinkEndDestructionData.qualifier, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}
	protected void createIsDestroyDuplicatesCheckbox(Composite parent) {
		isDestroyDuplicates = new Button(parent, SWT.CHECK);
		isDestroyDuplicates.setText(UMLMessages.LinkEndDestructionDataPropertiesEditionPart_IsDestroyDuplicatesLabel);
		GridData isDestroyDuplicatesData = new GridData(GridData.FILL_HORIZONTAL);
		isDestroyDuplicatesData.horizontalSpan = 2;
		isDestroyDuplicates.setLayoutData(isDestroyDuplicatesData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.LinkEndDestructionData.isDestroyDuplicates, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	
	public void firePropertiesChanged(PathedPropertiesEditionEvent event) {
		// Start of user code for tab synchronization
		
		// End of user code		
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.LinkEndDestructionDataPropertiesEditionPart#getOwnedCommentToAdd()
	 */
	public List getOwnedCommentToAdd() {
		return ownedCommentEditUtil.getElementsToAdd();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.LinkEndDestructionDataPropertiesEditionPart#getOwnedCommentToRemove()
	 */
	public List getOwnedCommentToRemove() {
		return ownedCommentEditUtil.getElementsToRemove();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.LinkEndDestructionDataPropertiesEditionPart#getOwnedCommentToEdit()
	 */
	public Map getOwnedCommentToEdit() {
		return ownedCommentEditUtil.getElementsToRefresh();
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.LinkEndDestructionDataPropertiesEditionPart#getOwnedCommentToMove()
	 */
	public List getOwnedCommentToMove() {
		return ownedCommentEditUtil.getElementsToMove();
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.LinkEndDestructionDataPropertiesEditionPart#getOwnedCommentTable()
	 */
	public List getOwnedCommentTable() {
		return ownedCommentEditUtil.getVirtualList();
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.LinkEndDestructionDataPropertiesEditionPart#initOwnedComment(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initOwnedComment(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			ownedCommentEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else	
			ownedCommentEditUtil = new EMFListEditUtil(current, feature);	
		this.ownedComment.setInput(ownedCommentEditUtil.getVirtualList());
	}

/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.LinkEndDestructionDataPropertiesEditionPart#updateOwnedComment(EObject newValue)
	 */
	public void updateOwnedComment(EObject newValue) {
		if(ownedCommentEditUtil!=null){
			ownedCommentEditUtil.reinit(newValue);
			ownedComment.refresh();
		}		
	}
	
	public void setMessageForOwnedComment (String msg, int msgLevel) {
	
	}
	
	public void unsetMessageForOwnedComment () {
	
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.LinkEndDestructionDataPropertiesEditionPart#getQualifierToAdd()
	 */
	public List getQualifierToAdd() {
		return qualifierEditUtil.getElementsToAdd();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.LinkEndDestructionDataPropertiesEditionPart#getQualifierToRemove()
	 */
	public List getQualifierToRemove() {
		return qualifierEditUtil.getElementsToRemove();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.LinkEndDestructionDataPropertiesEditionPart#getQualifierToEdit()
	 */
	public Map getQualifierToEdit() {
		return qualifierEditUtil.getElementsToRefresh();
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.LinkEndDestructionDataPropertiesEditionPart#getQualifierToMove()
	 */
	public List getQualifierToMove() {
		return qualifierEditUtil.getElementsToMove();
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.LinkEndDestructionDataPropertiesEditionPart#getQualifierTable()
	 */
	public List getQualifierTable() {
		return qualifierEditUtil.getVirtualList();
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.LinkEndDestructionDataPropertiesEditionPart#initQualifier(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initQualifier(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			qualifierEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else	
			qualifierEditUtil = new EMFListEditUtil(current, feature);	
		this.qualifier.setInput(qualifierEditUtil.getVirtualList());
	}

/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.LinkEndDestructionDataPropertiesEditionPart#updateQualifier(EObject newValue)
	 */
	public void updateQualifier(EObject newValue) {
		if(qualifierEditUtil!=null){
			qualifierEditUtil.reinit(newValue);
			qualifier.refresh();
		}		
	}
	
	public void setMessageForQualifier (String msg, int msgLevel) {
	
	}
	
	public void unsetMessageForQualifier () {
	
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.LinkEndDestructionDataPropertiesEditionPart#getIsDestroyDuplicates()
	 */
	public Boolean getIsDestroyDuplicates() {
		return Boolean.valueOf(isDestroyDuplicates.getSelection());
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.LinkEndDestructionDataPropertiesEditionPart#setIsDestroyDuplicates(Boolean newValue)
	 */
	public void setIsDestroyDuplicates(Boolean newValue) {
		isDestroyDuplicates.setSelection(newValue.booleanValue());
	}
	
	public void setMessageForIsDestroyDuplicates (String msg, int msgLevel) {
	
	}
	
	public void unsetMessageForIsDestroyDuplicates () {
	
	}

	
	

	
	



	// Start of user code additional methods
 	
	// End of user code
}	
