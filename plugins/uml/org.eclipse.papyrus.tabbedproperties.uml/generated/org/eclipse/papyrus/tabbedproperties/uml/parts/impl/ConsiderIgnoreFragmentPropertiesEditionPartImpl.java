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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.parts.ISWTPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.api.policies.IPropertiesEditionPolicy;
import org.eclipse.emf.eef.runtime.api.providers.IPropertiesEditionPolicyProvider;
import org.eclipse.emf.eef.runtime.impl.filters.EObjectFilter;
import org.eclipse.emf.eef.runtime.impl.notify.PathedPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.parts.CompositePropertiesEditionPart;
import org.eclipse.emf.eef.runtime.impl.policies.EObjectPropertiesEditionContext;
import org.eclipse.emf.eef.runtime.impl.services.PropertiesEditionPolicyProviderService;
import org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil;
import org.eclipse.emf.eef.runtime.ui.widgets.EMFEnumViewer;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.SWTUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.TabElementTreeSelectionDialog;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart;
import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;
import org.eclipse.papyrus.tabbedproperties.uml.providers.UMLMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Gate;
import org.eclipse.uml2.uml.GeneralOrdering;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

// End of user code
/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class ConsiderIgnoreFragmentPropertiesEditionPartImpl extends CompositePropertiesEditionPart implements ISWTPropertiesEditionPart, ConsiderIgnoreFragmentPropertiesEditionPart {

	private EMFListEditUtil ownedCommentEditUtil;
	private ReferencesTable<?> ownedComment;
	private Text name;
	private EMFEnumViewer visibility;
	private EMFListEditUtil clientDependencyEditUtil;
	private ReferencesTable<?> clientDependency;
	private EMFListEditUtil coveredEditUtil;
	private ReferencesTable<?> covered;
	private EMFListEditUtil generalOrderingEditUtil;
	private ReferencesTable<?> generalOrdering;
	private EMFEnumViewer interactionOperator;
	private EMFListEditUtil operandEditUtil;
	private ReferencesTable<?> operand;
	private EMFListEditUtil cfragmentGateEditUtil;
	private ReferencesTable<?> cfragmentGate;
	private EMFListEditUtil messageEditUtil;
	private ReferencesTable<?> message;




	
	public ConsiderIgnoreFragmentPropertiesEditionPartImpl(IPropertiesEditionComponent editionComponent) {
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
		propertiesGroup.setText(UMLMessages.ConsiderIgnoreFragmentPropertiesEditionPart_PropertiesGroupLabel);
		GridData propertiesGroupData = new GridData(GridData.FILL_HORIZONTAL);
		propertiesGroupData.horizontalSpan = 3;
		propertiesGroup.setLayoutData(propertiesGroupData);
		GridLayout propertiesGroupLayout = new GridLayout();
		propertiesGroupLayout.numColumns = 3;
		propertiesGroup.setLayout(propertiesGroupLayout);
		createOwnedCommentTableComposition(propertiesGroup);
		createNameText(propertiesGroup);
		createVisibilityEEnumViewer(propertiesGroup);
		createClientDependencyReferencesTable(propertiesGroup);
		createCoveredReferencesTable(propertiesGroup);
		createGeneralOrderingTableComposition(propertiesGroup);
		createInteractionOperatorEEnumViewer(propertiesGroup);
		createOperandTableComposition(propertiesGroup);
		createCfragmentGateTableComposition(propertiesGroup);
		createMessageReferencesTable(propertiesGroup);
   	}
	/**
	 * @param container
	 */
	protected void createOwnedCommentTableComposition(Composite parent) {
		this.ownedComment = new ReferencesTable<Comment>(UMLMessages.ConsiderIgnoreFragmentPropertiesEditionPart_OwnedCommentLabel, new ReferencesTableListener<Comment>() {			
			public void handleAdd() { addToOwnedComment();}
			public void handleEdit(Comment element) { editOwnedComment(element); }
			public void handleMove(Comment element, int oldIndex, int newIndex) { moveOwnedComment(element, oldIndex, newIndex); }			
			public void handleRemove(Comment element) { removeFromOwnedComment(element); }
			public void navigateTo(Comment element) { System.out.println("---> navigateTo"); }
		});
		this.ownedComment.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.ConsiderIgnoreFragment.ownedComment, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.ownedComment, PathedPropertiesEditionEvent.COMMIT, PathedPropertiesEditionEvent.MOVE, editedElement, newIndex));	
		
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
				propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.ownedComment, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.ADD, null, propertiesEditionObject));
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
		propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.ownedComment, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.REMOVE, null, editedElement));

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
				propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.ownedComment, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}
	protected void createNameText(Composite parent) {
		SWTUtils.createPartLabel(parent, UMLMessages.ConsiderIgnoreFragmentPropertiesEditionPart_NameLabel, propertiesEditionComponent.isRequired(UMLViewsRepository.ConsiderIgnoreFragment.name, UMLViewsRepository.SWT_KIND));
		name = new Text(parent, SWT.BORDER);
		GridData nameData = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(nameData);
		name.addModifyListener(new ModifyListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			public void modifyText(ModifyEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.name, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.SET, null, name.getText()));
			}
			
		});

		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.ConsiderIgnoreFragment.name, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}
	protected void createVisibilityEEnumViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UMLMessages.ConsiderIgnoreFragmentPropertiesEditionPart_VisibilityLabel, propertiesEditionComponent.isRequired(UMLViewsRepository.ConsiderIgnoreFragment.visibility, UMLViewsRepository.SWT_KIND));
		visibility = new EMFEnumViewer(parent);
		visibility.setContentProvider(new ArrayContentProvider());
		visibility.setLabelProvider(new AdapterFactoryLabelProvider(new EcoreAdapterFactory()));
		GridData visibilityData = new GridData(GridData.FILL_HORIZONTAL);
		visibility.getCombo().setLayoutData(visibilityData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.ConsiderIgnoreFragment.visibility, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}
	protected void createClientDependencyReferencesTable(Composite parent) {
		this.clientDependency = new ReferencesTable<Dependency>(UMLMessages.ConsiderIgnoreFragmentPropertiesEditionPart_ClientDependencyLabel, new ReferencesTableListener<Dependency>() {
			public void handleAdd() {				
				ViewerFilter clientDependencyFilter = new EObjectFilter(UMLPackage.eINSTANCE.getDependency());
				ViewerFilter viewerFilter = new ViewerFilter() {					
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						if (element instanceof EObject)
							return (!clientDependencyEditUtil.contains((EObject)element));
						return false;					
					}
				};				
				ViewerFilter[] filters = { clientDependencyFilter, viewerFilter };		
				TabElementTreeSelectionDialog<Dependency> dialog = new TabElementTreeSelectionDialog<Dependency>(resourceSet, filters,
				"Dependency", UMLPackage.eINSTANCE.getDependency()) {
					@Override
					public void process(IStructuredSelection selection) {						
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!clientDependencyEditUtil.getVirtualList().contains(elem))
								clientDependencyEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.clientDependency,
								PathedPropertiesEditionEvent.COMMIT, PathedPropertiesEditionEvent.ADD, null, elem));	
						}
						clientDependency.refresh();											
					}
				};
				dialog.open();
			}
			public void handleEdit(Dependency element) { editClientDependency(element); }
			public void handleMove(Dependency element, int oldIndex, int newIndex) { moveClientDependency(element, oldIndex, newIndex); }
			public void handleRemove(Dependency element) { removeFromClientDependency(element); }
			public void navigateTo(Dependency element) { System.out.println("---> navigateTo"); }
		});
		this.clientDependency.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.ConsiderIgnoreFragment.clientDependency, UMLViewsRepository.SWT_KIND));
		this.clientDependency.createControls(parent);
		GridData clientDependencyData = new GridData(GridData.FILL_HORIZONTAL);
		clientDependencyData.horizontalSpan = 3;
		this.clientDependency.setLayoutData(clientDependencyData);
		this.clientDependency.disableMove();
	}
	
	/**
	 * 
	 */
	private void moveClientDependency(Dependency element, int oldIndex, int newIndex) {
				
		EObject editedElement = clientDependencyEditUtil.foundCorrespondingEObject(element);
		clientDependencyEditUtil.moveElement(element, oldIndex, newIndex);
		clientDependency.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.clientDependency, PathedPropertiesEditionEvent.COMMIT, PathedPropertiesEditionEvent.MOVE, editedElement, newIndex));	
		
	}
	
	/**
	 * 
	 */
	private void removeFromClientDependency(Dependency element) {

		// Start of user code for the removeFromClientDependency() method body

		EObject editedElement = clientDependencyEditUtil.foundCorrespondingEObject(element);
		clientDependencyEditUtil.removeElement(element);
		clientDependency.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.clientDependency, PathedPropertiesEditionEvent.COMMIT, PathedPropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editClientDependency(Dependency element) {

		// Start of user code editClientDependency() method body
				 
		EObject editedElement = clientDependencyEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider	.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element,resourceSet));
			if (propertiesEditionObject != null) {
				clientDependencyEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				clientDependency.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.clientDependency, PathedPropertiesEditionEvent.COMMIT, PathedPropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}
	protected void createCoveredReferencesTable(Composite parent) {
		this.covered = new ReferencesTable<Lifeline>(UMLMessages.ConsiderIgnoreFragmentPropertiesEditionPart_CoveredLabel, new ReferencesTableListener<Lifeline>() {
			public void handleAdd() {				
				ViewerFilter coveredFilter = new EObjectFilter(UMLPackage.eINSTANCE.getLifeline());
				ViewerFilter viewerFilter = new ViewerFilter() {					
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						if (element instanceof EObject)
							return (!coveredEditUtil.contains((EObject)element));
						return false;					
					}
				};				
				ViewerFilter[] filters = { coveredFilter, viewerFilter };		
				TabElementTreeSelectionDialog<Lifeline> dialog = new TabElementTreeSelectionDialog<Lifeline>(resourceSet, filters,
				"Lifeline", UMLPackage.eINSTANCE.getLifeline()) {
					@Override
					public void process(IStructuredSelection selection) {						
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!coveredEditUtil.getVirtualList().contains(elem))
								coveredEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.covered,
								PathedPropertiesEditionEvent.COMMIT, PathedPropertiesEditionEvent.ADD, null, elem));	
						}
						covered.refresh();											
					}
				};
				dialog.open();
			}
			public void handleEdit(Lifeline element) { editCovered(element); }
			public void handleMove(Lifeline element, int oldIndex, int newIndex) { moveCovered(element, oldIndex, newIndex); }
			public void handleRemove(Lifeline element) { removeFromCovered(element); }
			public void navigateTo(Lifeline element) { System.out.println("---> navigateTo"); }
		});
		this.covered.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.ConsiderIgnoreFragment.covered, UMLViewsRepository.SWT_KIND));
		this.covered.createControls(parent);
		GridData coveredData = new GridData(GridData.FILL_HORIZONTAL);
		coveredData.horizontalSpan = 3;
		this.covered.setLayoutData(coveredData);
		this.covered.disableMove();
	}
	
	/**
	 * 
	 */
	private void moveCovered(Lifeline element, int oldIndex, int newIndex) {
				
		EObject editedElement = coveredEditUtil.foundCorrespondingEObject(element);
		coveredEditUtil.moveElement(element, oldIndex, newIndex);
		covered.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.covered, PathedPropertiesEditionEvent.COMMIT, PathedPropertiesEditionEvent.MOVE, editedElement, newIndex));	
		
	}
	
	/**
	 * 
	 */
	private void removeFromCovered(Lifeline element) {

		// Start of user code for the removeFromCovered() method body

		EObject editedElement = coveredEditUtil.foundCorrespondingEObject(element);
		coveredEditUtil.removeElement(element);
		covered.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.covered, PathedPropertiesEditionEvent.COMMIT, PathedPropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editCovered(Lifeline element) {

		// Start of user code editCovered() method body
				 
		EObject editedElement = coveredEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider	.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element,resourceSet));
			if (propertiesEditionObject != null) {
				coveredEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				covered.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.covered, PathedPropertiesEditionEvent.COMMIT, PathedPropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}
	/**
	 * @param container
	 */
	protected void createGeneralOrderingTableComposition(Composite parent) {
		this.generalOrdering = new ReferencesTable<GeneralOrdering>(UMLMessages.ConsiderIgnoreFragmentPropertiesEditionPart_GeneralOrderingLabel, new ReferencesTableListener<GeneralOrdering>() {			
			public void handleAdd() { addToGeneralOrdering();}
			public void handleEdit(GeneralOrdering element) { editGeneralOrdering(element); }
			public void handleMove(GeneralOrdering element, int oldIndex, int newIndex) { moveGeneralOrdering(element, oldIndex, newIndex); }			
			public void handleRemove(GeneralOrdering element) { removeFromGeneralOrdering(element); }
			public void navigateTo(GeneralOrdering element) { System.out.println("---> navigateTo"); }
		});
		this.generalOrdering.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.ConsiderIgnoreFragment.generalOrdering, UMLViewsRepository.SWT_KIND));
		this.generalOrdering.createControls(parent);
		GridData generalOrderingData = new GridData(GridData.FILL_HORIZONTAL);
		generalOrderingData.horizontalSpan = 3;
		this.generalOrdering.setLayoutData(generalOrderingData);
	}
		
	/**
	 * 
	 */
	private void moveGeneralOrdering(GeneralOrdering element, int oldIndex, int newIndex) {
				
		EObject editedElement = generalOrderingEditUtil.foundCorrespondingEObject(element);
		generalOrderingEditUtil.moveElement(element, oldIndex, newIndex);
		generalOrdering.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.generalOrdering, PathedPropertiesEditionEvent.COMMIT, PathedPropertiesEditionEvent.MOVE, editedElement, newIndex));	
		
	}	
	
	/**
	 * 
	 */
	private void addToGeneralOrdering() {
	
		// Start of user code addToGeneralOrdering() method body


		GeneralOrdering eObject = UMLFactory.eINSTANCE.createGeneralOrdering();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject,resourceSet));
			if (propertiesEditionObject != null) {
				generalOrderingEditUtil.addElement(propertiesEditionObject);
				generalOrdering.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.generalOrdering, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}
		
		
		// End of user code		
	}

	/**
	 * 
	 */
	private void removeFromGeneralOrdering(GeneralOrdering element) {

		// Start of user code for the removeFromGeneralOrdering() method body

		EObject editedElement = generalOrderingEditUtil.foundCorrespondingEObject(element);
		generalOrderingEditUtil.removeElement(element);
		generalOrdering.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.generalOrdering, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editGeneralOrdering(GeneralOrdering element) {

		// Start of user code editGeneralOrdering() method body
				 
		EObject editedElement = generalOrderingEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider	.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element,resourceSet));
			if (propertiesEditionObject != null) {
				generalOrderingEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				generalOrdering.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.generalOrdering, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}
	protected void createInteractionOperatorEEnumViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UMLMessages.ConsiderIgnoreFragmentPropertiesEditionPart_InteractionOperatorLabel, propertiesEditionComponent.isRequired(UMLViewsRepository.ConsiderIgnoreFragment.interactionOperator, UMLViewsRepository.SWT_KIND));
		interactionOperator = new EMFEnumViewer(parent);
		interactionOperator.setContentProvider(new ArrayContentProvider());
		interactionOperator.setLabelProvider(new AdapterFactoryLabelProvider(new EcoreAdapterFactory()));
		GridData interactionOperatorData = new GridData(GridData.FILL_HORIZONTAL);
		interactionOperator.getCombo().setLayoutData(interactionOperatorData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.ConsiderIgnoreFragment.interactionOperator, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}
	/**
	 * @param container
	 */
	protected void createOperandTableComposition(Composite parent) {
		this.operand = new ReferencesTable<InteractionOperand>(UMLMessages.ConsiderIgnoreFragmentPropertiesEditionPart_OperandLabel, new ReferencesTableListener<InteractionOperand>() {			
			public void handleAdd() { addToOperand();}
			public void handleEdit(InteractionOperand element) { editOperand(element); }
			public void handleMove(InteractionOperand element, int oldIndex, int newIndex) { moveOperand(element, oldIndex, newIndex); }			
			public void handleRemove(InteractionOperand element) { removeFromOperand(element); }
			public void navigateTo(InteractionOperand element) { System.out.println("---> navigateTo"); }
		});
		this.operand.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.ConsiderIgnoreFragment.operand, UMLViewsRepository.SWT_KIND));
		this.operand.createControls(parent);
		GridData operandData = new GridData(GridData.FILL_HORIZONTAL);
		operandData.horizontalSpan = 3;
		this.operand.setLayoutData(operandData);
	}
		
	/**
	 * 
	 */
	private void moveOperand(InteractionOperand element, int oldIndex, int newIndex) {
				
		EObject editedElement = operandEditUtil.foundCorrespondingEObject(element);
		operandEditUtil.moveElement(element, oldIndex, newIndex);
		operand.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.operand, PathedPropertiesEditionEvent.COMMIT, PathedPropertiesEditionEvent.MOVE, editedElement, newIndex));	
		
	}	
	
	/**
	 * 
	 */
	private void addToOperand() {
	
		// Start of user code addToOperand() method body


		InteractionOperand eObject = UMLFactory.eINSTANCE.createInteractionOperand();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject,resourceSet));
			if (propertiesEditionObject != null) {
				operandEditUtil.addElement(propertiesEditionObject);
				operand.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.operand, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}
		
		
		// End of user code		
	}

	/**
	 * 
	 */
	private void removeFromOperand(InteractionOperand element) {

		// Start of user code for the removeFromOperand() method body

		EObject editedElement = operandEditUtil.foundCorrespondingEObject(element);
		operandEditUtil.removeElement(element);
		operand.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.operand, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editOperand(InteractionOperand element) {

		// Start of user code editOperand() method body
				 
		EObject editedElement = operandEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider	.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element,resourceSet));
			if (propertiesEditionObject != null) {
				operandEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				operand.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.operand, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}
	/**
	 * @param container
	 */
	protected void createCfragmentGateTableComposition(Composite parent) {
		this.cfragmentGate = new ReferencesTable<Gate>(UMLMessages.ConsiderIgnoreFragmentPropertiesEditionPart_CfragmentGateLabel, new ReferencesTableListener<Gate>() {			
			public void handleAdd() { addToCfragmentGate();}
			public void handleEdit(Gate element) { editCfragmentGate(element); }
			public void handleMove(Gate element, int oldIndex, int newIndex) { moveCfragmentGate(element, oldIndex, newIndex); }			
			public void handleRemove(Gate element) { removeFromCfragmentGate(element); }
			public void navigateTo(Gate element) { System.out.println("---> navigateTo"); }
		});
		this.cfragmentGate.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.ConsiderIgnoreFragment.cfragmentGate, UMLViewsRepository.SWT_KIND));
		this.cfragmentGate.createControls(parent);
		GridData cfragmentGateData = new GridData(GridData.FILL_HORIZONTAL);
		cfragmentGateData.horizontalSpan = 3;
		this.cfragmentGate.setLayoutData(cfragmentGateData);
	}
		
	/**
	 * 
	 */
	private void moveCfragmentGate(Gate element, int oldIndex, int newIndex) {
				
		EObject editedElement = cfragmentGateEditUtil.foundCorrespondingEObject(element);
		cfragmentGateEditUtil.moveElement(element, oldIndex, newIndex);
		cfragmentGate.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.cfragmentGate, PathedPropertiesEditionEvent.COMMIT, PathedPropertiesEditionEvent.MOVE, editedElement, newIndex));	
		
	}	
	
	/**
	 * 
	 */
	private void addToCfragmentGate() {
	
		// Start of user code addToCfragmentGate() method body


		Gate eObject = UMLFactory.eINSTANCE.createGate();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject,resourceSet));
			if (propertiesEditionObject != null) {
				cfragmentGateEditUtil.addElement(propertiesEditionObject);
				cfragmentGate.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.cfragmentGate, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}
		
		
		// End of user code		
	}

	/**
	 * 
	 */
	private void removeFromCfragmentGate(Gate element) {

		// Start of user code for the removeFromCfragmentGate() method body

		EObject editedElement = cfragmentGateEditUtil.foundCorrespondingEObject(element);
		cfragmentGateEditUtil.removeElement(element);
		cfragmentGate.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.cfragmentGate, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editCfragmentGate(Gate element) {

		// Start of user code editCfragmentGate() method body
				 
		EObject editedElement = cfragmentGateEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider	.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element,resourceSet));
			if (propertiesEditionObject != null) {
				cfragmentGateEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				cfragmentGate.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.cfragmentGate, PathedPropertiesEditionEvent.CHANGE, PathedPropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}
	protected void createMessageReferencesTable(Composite parent) {
		this.message = new ReferencesTable<NamedElement>(UMLMessages.ConsiderIgnoreFragmentPropertiesEditionPart_MessageLabel, new ReferencesTableListener<NamedElement>() {
			public void handleAdd() {				
				ViewerFilter messageFilter = new EObjectFilter(UMLPackage.eINSTANCE.getNamedElement());
				ViewerFilter viewerFilter = new ViewerFilter() {					
					public boolean select(Viewer viewer, Object parentElement, Object element) {
						if (element instanceof EObject)
							return (!messageEditUtil.contains((EObject)element));
						return false;					
					}
				};				
				ViewerFilter[] filters = { messageFilter, viewerFilter };		
				TabElementTreeSelectionDialog<NamedElement> dialog = new TabElementTreeSelectionDialog<NamedElement>(resourceSet, filters,
				"NamedElement", UMLPackage.eINSTANCE.getNamedElement()) {
					@Override
					public void process(IStructuredSelection selection) {						
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!messageEditUtil.getVirtualList().contains(elem))
								messageEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.message,
								PathedPropertiesEditionEvent.COMMIT, PathedPropertiesEditionEvent.ADD, null, elem));	
						}
						message.refresh();											
					}
				};
				dialog.open();
			}
			public void handleEdit(NamedElement element) { editMessage(element); }
			public void handleMove(NamedElement element, int oldIndex, int newIndex) { moveMessage(element, oldIndex, newIndex); }
			public void handleRemove(NamedElement element) { removeFromMessage(element); }
			public void navigateTo(NamedElement element) { System.out.println("---> navigateTo"); }
		});
		this.message.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.ConsiderIgnoreFragment.message, UMLViewsRepository.SWT_KIND));
		this.message.createControls(parent);
		GridData messageData = new GridData(GridData.FILL_HORIZONTAL);
		messageData.horizontalSpan = 3;
		this.message.setLayoutData(messageData);
		this.message.disableMove();
	}
	
	/**
	 * 
	 */
	private void moveMessage(NamedElement element, int oldIndex, int newIndex) {
	}
	
	/**
	 * 
	 */
	private void removeFromMessage(NamedElement element) {

		// Start of user code for the removeFromMessage() method body

		EObject editedElement = messageEditUtil.foundCorrespondingEObject(element);
		messageEditUtil.removeElement(element);
		message.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.message, PathedPropertiesEditionEvent.COMMIT, PathedPropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editMessage(NamedElement element) {

		// Start of user code editMessage() method body
				 
		EObject editedElement = messageEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider	.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element,resourceSet));
			if (propertiesEditionObject != null) {
				messageEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				message.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PathedPropertiesEditionEvent(ConsiderIgnoreFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.ConsiderIgnoreFragment.message, PathedPropertiesEditionEvent.COMMIT, PathedPropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	
	public void firePropertiesChanged(PathedPropertiesEditionEvent event) {
		// Start of user code for tab synchronization
		
		// End of user code		
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getOwnedCommentToAdd()
	 */
	public List getOwnedCommentToAdd() {
		return ownedCommentEditUtil.getElementsToAdd();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getOwnedCommentToRemove()
	 */
	public List getOwnedCommentToRemove() {
		return ownedCommentEditUtil.getElementsToRemove();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getOwnedCommentToEdit()
	 */
	public Map getOwnedCommentToEdit() {
		return ownedCommentEditUtil.getElementsToRefresh();
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getOwnedCommentToMove()
	 */
	public List getOwnedCommentToMove() {
		return ownedCommentEditUtil.getElementsToMove();
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getOwnedCommentTable()
	 */
	public List getOwnedCommentTable() {
		return ownedCommentEditUtil.getVirtualList();
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#initOwnedComment(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#updateOwnedComment(EObject newValue)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getName()
	 */
	public String getName() {
		return name.getText();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#setName(String newValue)
	 */
	public void setName(String newValue) {
		name.setText(newValue);
	}
	
	public void setMessageForName (String msg, int msgLevel) {
	
	}
	
	public void unsetMessageForName () {
	
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getVisibility()
	 */
	public Enumerator getVisibility() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) visibility.getSelection()).getFirstElement();
		return selection.getInstance();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#initVisibility(EEnum eenum, Enumerator current)
	 */
	public void initVisibility(EEnum eenum, Enumerator current) {
		visibility.setInput(eenum.getELiterals());
		visibility.setSelection(new StructuredSelection(current));
	}

/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#setVisibility(Enumerator newValue)
	 */
	public void setVisibility(Enumerator newValue) {
		visibility.modelUpdating(new StructuredSelection(newValue));
	}
	
	public void setMessageForVisibility (String msg, int msgLevel) {
	
	}
	
	public void unsetMessageForVisibility () {
	
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getClientDependencyToAdd()
	 */
	public List getClientDependencyToAdd() {
		return clientDependencyEditUtil.getElementsToAdd();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getClientDependencyToRemove()
	 */
	public List getClientDependencyToRemove() {
		return clientDependencyEditUtil.getElementsToRemove();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#initClientDependency(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initClientDependency(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			clientDependencyEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else	
			clientDependencyEditUtil = new EMFListEditUtil(current, feature);	
		this.clientDependency.setInput(clientDependencyEditUtil.getVirtualList());
	}

/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#updateClientDependency(EObject newValue)
	 */
	public void updateClientDependency(EObject newValue) {
		if(clientDependencyEditUtil!=null){
			clientDependencyEditUtil.reinit(newValue);
			clientDependency.refresh();
		}		
	}
	
	public void setMessageForClientDependency (String msg, int msgLevel) {
	
	}
	
	public void unsetMessageForClientDependency () {
	
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getCoveredToAdd()
	 */
	public List getCoveredToAdd() {
		return coveredEditUtil.getElementsToAdd();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getCoveredToRemove()
	 */
	public List getCoveredToRemove() {
		return coveredEditUtil.getElementsToRemove();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#initCovered(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initCovered(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			coveredEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else	
			coveredEditUtil = new EMFListEditUtil(current, feature);	
		this.covered.setInput(coveredEditUtil.getVirtualList());
	}

/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#updateCovered(EObject newValue)
	 */
	public void updateCovered(EObject newValue) {
		if(coveredEditUtil!=null){
			coveredEditUtil.reinit(newValue);
			covered.refresh();
		}		
	}
	
	public void setMessageForCovered (String msg, int msgLevel) {
	
	}
	
	public void unsetMessageForCovered () {
	
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getGeneralOrderingToAdd()
	 */
	public List getGeneralOrderingToAdd() {
		return generalOrderingEditUtil.getElementsToAdd();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getGeneralOrderingToRemove()
	 */
	public List getGeneralOrderingToRemove() {
		return generalOrderingEditUtil.getElementsToRemove();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getGeneralOrderingToEdit()
	 */
	public Map getGeneralOrderingToEdit() {
		return generalOrderingEditUtil.getElementsToRefresh();
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getGeneralOrderingToMove()
	 */
	public List getGeneralOrderingToMove() {
		return generalOrderingEditUtil.getElementsToMove();
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getGeneralOrderingTable()
	 */
	public List getGeneralOrderingTable() {
		return generalOrderingEditUtil.getVirtualList();
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#initGeneralOrdering(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initGeneralOrdering(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			generalOrderingEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else	
			generalOrderingEditUtil = new EMFListEditUtil(current, feature);	
		this.generalOrdering.setInput(generalOrderingEditUtil.getVirtualList());
	}

/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#updateGeneralOrdering(EObject newValue)
	 */
	public void updateGeneralOrdering(EObject newValue) {
		if(generalOrderingEditUtil!=null){
			generalOrderingEditUtil.reinit(newValue);
			generalOrdering.refresh();
		}		
	}
	
	public void setMessageForGeneralOrdering (String msg, int msgLevel) {
	
	}
	
	public void unsetMessageForGeneralOrdering () {
	
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getInteractionOperator()
	 */
	public Enumerator getInteractionOperator() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) interactionOperator.getSelection()).getFirstElement();
		return selection.getInstance();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#initInteractionOperator(EEnum eenum, Enumerator current)
	 */
	public void initInteractionOperator(EEnum eenum, Enumerator current) {
		interactionOperator.setInput(eenum.getELiterals());
		interactionOperator.setSelection(new StructuredSelection(current));
	}

/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#setInteractionOperator(Enumerator newValue)
	 */
	public void setInteractionOperator(Enumerator newValue) {
		interactionOperator.modelUpdating(new StructuredSelection(newValue));
	}
	
	public void setMessageForInteractionOperator (String msg, int msgLevel) {
	
	}
	
	public void unsetMessageForInteractionOperator () {
	
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getOperandToAdd()
	 */
	public List getOperandToAdd() {
		return operandEditUtil.getElementsToAdd();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getOperandToRemove()
	 */
	public List getOperandToRemove() {
		return operandEditUtil.getElementsToRemove();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getOperandToEdit()
	 */
	public Map getOperandToEdit() {
		return operandEditUtil.getElementsToRefresh();
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getOperandToMove()
	 */
	public List getOperandToMove() {
		return operandEditUtil.getElementsToMove();
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getOperandTable()
	 */
	public List getOperandTable() {
		return operandEditUtil.getVirtualList();
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#initOperand(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initOperand(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			operandEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else	
			operandEditUtil = new EMFListEditUtil(current, feature);	
		this.operand.setInput(operandEditUtil.getVirtualList());
	}

/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#updateOperand(EObject newValue)
	 */
	public void updateOperand(EObject newValue) {
		if(operandEditUtil!=null){
			operandEditUtil.reinit(newValue);
			operand.refresh();
		}		
	}
	
	public void setMessageForOperand (String msg, int msgLevel) {
	
	}
	
	public void unsetMessageForOperand () {
	
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getCfragmentGateToAdd()
	 */
	public List getCfragmentGateToAdd() {
		return cfragmentGateEditUtil.getElementsToAdd();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getCfragmentGateToRemove()
	 */
	public List getCfragmentGateToRemove() {
		return cfragmentGateEditUtil.getElementsToRemove();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getCfragmentGateToEdit()
	 */
	public Map getCfragmentGateToEdit() {
		return cfragmentGateEditUtil.getElementsToRefresh();
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getCfragmentGateToMove()
	 */
	public List getCfragmentGateToMove() {
		return cfragmentGateEditUtil.getElementsToMove();
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getCfragmentGateTable()
	 */
	public List getCfragmentGateTable() {
		return cfragmentGateEditUtil.getVirtualList();
	};
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#initCfragmentGate(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initCfragmentGate(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			cfragmentGateEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else	
			cfragmentGateEditUtil = new EMFListEditUtil(current, feature);	
		this.cfragmentGate.setInput(cfragmentGateEditUtil.getVirtualList());
	}

/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#updateCfragmentGate(EObject newValue)
	 */
	public void updateCfragmentGate(EObject newValue) {
		if(cfragmentGateEditUtil!=null){
			cfragmentGateEditUtil.reinit(newValue);
			cfragmentGate.refresh();
		}		
	}
	
	public void setMessageForCfragmentGate (String msg, int msgLevel) {
	
	}
	
	public void unsetMessageForCfragmentGate () {
	
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getMessageToAdd()
	 */
	public List getMessageToAdd() {
		return messageEditUtil.getElementsToAdd();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#getMessageToRemove()
	 */
	public List getMessageToRemove() {
		return messageEditUtil.getElementsToRemove();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#initMessage(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initMessage(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			messageEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else	
			messageEditUtil = new EMFListEditUtil(current, feature);	
		this.message.setInput(messageEditUtil.getVirtualList());
	}

/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ConsiderIgnoreFragmentPropertiesEditionPart#updateMessage(EObject newValue)
	 */
	public void updateMessage(EObject newValue) {
		if(messageEditUtil!=null){
			messageEditUtil.reinit(newValue);
			message.refresh();
		}		
	}
	
	public void setMessageForMessage (String msg, int msgLevel) {
	
	}
	
	public void unsetMessageForMessage () {
	
	}

	
	

	
	



	// Start of user code additional methods
 	
	// End of user code
}	
