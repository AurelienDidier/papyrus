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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.parts.CompositePropertiesEditionPart;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.papyrus.tabbedproperties.uml.providers.UMLMessages;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.parts.ISWTPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.impl.parts.CompositePropertiesEditionPart;
import org.eclipse.emf.eef.runtime.api.policies.IPropertiesEditionPolicy;
import org.eclipse.emf.eef.runtime.api.providers.IPropertiesEditionPolicyProvider;
import org.eclipse.emf.eef.runtime.impl.policies.EObjectPropertiesEditionContext;
import org.eclipse.emf.eef.runtime.impl.services.PropertiesEditionPolicyProviderService;

import org.eclipse.emf.eef.runtime.ui.widgets.SWTUtils;
import org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart;

import org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.jface.viewers.StructuredSelection;
import java.util.Iterator;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.emf.eef.runtime.ui.widgets.EMFModelViewerDialog;
import org.eclipse.emf.eef.runtime.ui.widgets.TabElementTreeSelectionDialog;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.emf.eef.runtime.impl.filters.EObjectFilter;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.emf.eef.runtime.ui.widgets.EMFComboViewer;
import java.util.Map;
import org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.GeneralOrdering;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.Gate;


import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;

// End of user code

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class CombinedFragmentPropertiesEditionPartImpl extends CompositePropertiesEditionPart implements ISWTPropertiesEditionPart, CombinedFragmentPropertiesEditionPart {

	protected EMFListEditUtil ownedCommentEditUtil;
	protected ReferencesTable<?> ownedComment;
	protected List<ViewerFilter> ownedCommentBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> ownedCommentFilters = new ArrayList<ViewerFilter>();
	protected Text name;
	protected EMFComboViewer visibility;
	protected EMFListEditUtil clientDependencyEditUtil;
	protected ReferencesTable<?> clientDependency;
	protected List<ViewerFilter> clientDependencyBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> clientDependencyFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil coveredEditUtil;
	protected ReferencesTable<?> covered;
	protected List<ViewerFilter> coveredBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> coveredFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil generalOrderingEditUtil;
	protected ReferencesTable<?> generalOrdering;
	protected List<ViewerFilter> generalOrderingBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> generalOrderingFilters = new ArrayList<ViewerFilter>();
	protected EMFComboViewer interactionOperator;
	protected EMFListEditUtil operandEditUtil;
	protected ReferencesTable<?> operand;
	protected List<ViewerFilter> operandBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> operandFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil cfragmentGateEditUtil;
	protected ReferencesTable<?> cfragmentGate;
	protected List<ViewerFilter> cfragmentGateBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> cfragmentGateFilters = new ArrayList<ViewerFilter>();




	
	public CombinedFragmentPropertiesEditionPartImpl(IPropertiesEditionComponent editionComponent) {
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
		propertiesGroup.setText(UMLMessages.CombinedFragmentPropertiesEditionPart_PropertiesGroupLabel);
		GridData propertiesGroupData = new GridData(GridData.FILL_HORIZONTAL);
		propertiesGroupData.horizontalSpan = 3;
		propertiesGroup.setLayoutData(propertiesGroupData);
		GridLayout propertiesGroupLayout = new GridLayout();
		propertiesGroupLayout.numColumns = 3;
		propertiesGroup.setLayout(propertiesGroupLayout);
		createOwnedCommentAdvancedTableComposition(propertiesGroup);
		createNameText(propertiesGroup);
		createVisibilityEMFComboViewer(propertiesGroup);
		createClientDependencyAdvancedReferencesTable(propertiesGroup);
		createCoveredAdvancedReferencesTable(propertiesGroup);
		createGeneralOrderingAdvancedTableComposition(propertiesGroup);
		createInteractionOperatorEMFComboViewer(propertiesGroup);
		createOperandAdvancedTableComposition(propertiesGroup);
		createCfragmentGateAdvancedTableComposition(propertiesGroup);
	}
	/**
	 * @param container
	 */
	protected void createOwnedCommentAdvancedTableComposition(Composite parent) {
		this.ownedComment = new ReferencesTable<Comment>(UMLMessages.CombinedFragmentPropertiesEditionPart_OwnedCommentLabel, new ReferencesTableListener<Comment>() {			
			public void handleAdd() { addToOwnedComment();}
			public void handleEdit(Comment element) { editOwnedComment(element); }
			public void handleMove(Comment element, int oldIndex, int newIndex) { moveOwnedComment(element, oldIndex, newIndex); }
			public void handleRemove(Comment element) { removeFromOwnedComment(element); }
			public void navigateTo(Comment element) { }
		});
		this.ownedComment.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.CombinedFragment.ownedComment, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.ownedComment, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
		
	}

	/**
	 * 
	 */
	protected void addToOwnedComment() {

		// Start of user code addToOwnedComment() method body


		Comment eObject = UMLFactory.eINSTANCE.createComment();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject,resourceSet));
			if (propertiesEditionObject != null) {
				ownedCommentEditUtil.addElement(propertiesEditionObject);
				ownedComment.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.ownedComment, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}
		
		
		// End of user code
		
	}

	/**
	 * 
	 */
	private void removeFromOwnedComment(Comment element) {

		// Start of user code removeFromOwnedComment() method body

		EObject editedElement = ownedCommentEditUtil.foundCorrespondingEObject(element);
		ownedCommentEditUtil.removeElement(element);
		ownedComment.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.ownedComment, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

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
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.ownedComment, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}
		
		// End of user code

	}
	protected void createNameText(Composite parent) {
		SWTUtils.createPartLabel(parent, UMLMessages.CombinedFragmentPropertiesEditionPart_NameLabel, propertiesEditionComponent.isRequired(UMLViewsRepository.CombinedFragment.name, UMLViewsRepository.SWT_KIND));
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.name, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, null, name.getText()));
			}
			
		});

		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.CombinedFragment.name, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}
	protected void createVisibilityEMFComboViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UMLMessages.CombinedFragmentPropertiesEditionPart_VisibilityLabel, propertiesEditionComponent.isRequired(UMLViewsRepository.CombinedFragment.visibility, UMLViewsRepository.SWT_KIND));
		visibility = new EMFComboViewer(parent);
		visibility.setContentProvider(new ArrayContentProvider());
		visibility.setLabelProvider(new AdapterFactoryLabelProvider(new EcoreAdapterFactory()));
		GridData visibilityData = new GridData(GridData.FILL_HORIZONTAL);
		visibility.getCombo().setLayoutData(visibilityData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.CombinedFragment.visibility, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}
	protected void createClientDependencyAdvancedReferencesTable(Composite parent) {
		this.clientDependency = new ReferencesTable<Dependency>(UMLMessages.CombinedFragmentPropertiesEditionPart_ClientDependencyLabel, new ReferencesTableListener<Dependency>() {
			public void handleAdd() {
				TabElementTreeSelectionDialog<Dependency> dialog = new TabElementTreeSelectionDialog<Dependency>(resourceSet, clientDependencyFilters, clientDependencyBusinessFilters,
				"Dependency", UMLPackage.eINSTANCE.getDependency()) {

					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!clientDependencyEditUtil.getVirtualList().contains(elem))
								clientDependencyEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.clientDependency,
								PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
						}
						clientDependency.refresh();
					}

				};
				dialog.open();
			}
			public void handleEdit(Dependency element) { editClientDependency(element); }
			public void handleMove(Dependency element, int oldIndex, int newIndex) { moveClientDependency(element, oldIndex, newIndex); }
			public void handleRemove(Dependency element) { removeFromClientDependency(element); }
			public void navigateTo(Dependency element) { }
		});
		this.clientDependency.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.CombinedFragment.clientDependency, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.clientDependency, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}
	
	/**
	 * 
	 */
	private void removeFromClientDependency(Dependency element) {

		// Start of user code removeFromClientDependency() method body

		EObject editedElement = clientDependencyEditUtil.foundCorrespondingEObject(element);
		clientDependencyEditUtil.removeElement(element);
		clientDependency.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.clientDependency, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

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
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.clientDependency, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code

	}
	protected void createCoveredAdvancedReferencesTable(Composite parent) {
		this.covered = new ReferencesTable<Lifeline>(UMLMessages.CombinedFragmentPropertiesEditionPart_CoveredLabel, new ReferencesTableListener<Lifeline>() {
			public void handleAdd() {
				TabElementTreeSelectionDialog<Lifeline> dialog = new TabElementTreeSelectionDialog<Lifeline>(resourceSet, coveredFilters, coveredBusinessFilters,
				"Lifeline", UMLPackage.eINSTANCE.getLifeline()) {

					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!coveredEditUtil.getVirtualList().contains(elem))
								coveredEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.covered,
								PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
						}
						covered.refresh();
					}

				};
				dialog.open();
			}
			public void handleEdit(Lifeline element) { editCovered(element); }
			public void handleMove(Lifeline element, int oldIndex, int newIndex) { moveCovered(element, oldIndex, newIndex); }
			public void handleRemove(Lifeline element) { removeFromCovered(element); }
			public void navigateTo(Lifeline element) { }
		});
		this.covered.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.CombinedFragment.covered, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.covered, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}
	
	/**
	 * 
	 */
	private void removeFromCovered(Lifeline element) {

		// Start of user code removeFromCovered() method body

		EObject editedElement = coveredEditUtil.foundCorrespondingEObject(element);
		coveredEditUtil.removeElement(element);
		covered.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.covered, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

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
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.covered, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code

	}
	/**
	 * @param container
	 */
	protected void createGeneralOrderingAdvancedTableComposition(Composite parent) {
		this.generalOrdering = new ReferencesTable<GeneralOrdering>(UMLMessages.CombinedFragmentPropertiesEditionPart_GeneralOrderingLabel, new ReferencesTableListener<GeneralOrdering>() {			
			public void handleAdd() { addToGeneralOrdering();}
			public void handleEdit(GeneralOrdering element) { editGeneralOrdering(element); }
			public void handleMove(GeneralOrdering element, int oldIndex, int newIndex) { moveGeneralOrdering(element, oldIndex, newIndex); }
			public void handleRemove(GeneralOrdering element) { removeFromGeneralOrdering(element); }
			public void navigateTo(GeneralOrdering element) { }
		});
		this.generalOrdering.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.CombinedFragment.generalOrdering, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.generalOrdering, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
		
	}

	/**
	 * 
	 */
	protected void addToGeneralOrdering() {

		// Start of user code addToGeneralOrdering() method body


		GeneralOrdering eObject = UMLFactory.eINSTANCE.createGeneralOrdering();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject,resourceSet));
			if (propertiesEditionObject != null) {
				generalOrderingEditUtil.addElement(propertiesEditionObject);
				generalOrdering.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.generalOrdering, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}
		
		
		// End of user code
		
	}

	/**
	 * 
	 */
	private void removeFromGeneralOrdering(GeneralOrdering element) {

		// Start of user code removeFromGeneralOrdering() method body

		EObject editedElement = generalOrderingEditUtil.foundCorrespondingEObject(element);
		generalOrderingEditUtil.removeElement(element);
		generalOrdering.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.generalOrdering, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

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
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.generalOrdering, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}
		
		// End of user code

	}
	protected void createInteractionOperatorEMFComboViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UMLMessages.CombinedFragmentPropertiesEditionPart_InteractionOperatorLabel, propertiesEditionComponent.isRequired(UMLViewsRepository.CombinedFragment.interactionOperator, UMLViewsRepository.SWT_KIND));
		interactionOperator = new EMFComboViewer(parent);
		interactionOperator.setContentProvider(new ArrayContentProvider());
		interactionOperator.setLabelProvider(new AdapterFactoryLabelProvider(new EcoreAdapterFactory()));
		GridData interactionOperatorData = new GridData(GridData.FILL_HORIZONTAL);
		interactionOperator.getCombo().setLayoutData(interactionOperatorData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.CombinedFragment.interactionOperator, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}
	/**
	 * @param container
	 */
	protected void createOperandAdvancedTableComposition(Composite parent) {
		this.operand = new ReferencesTable<InteractionOperand>(UMLMessages.CombinedFragmentPropertiesEditionPart_OperandLabel, new ReferencesTableListener<InteractionOperand>() {			
			public void handleAdd() { addToOperand();}
			public void handleEdit(InteractionOperand element) { editOperand(element); }
			public void handleMove(InteractionOperand element, int oldIndex, int newIndex) { moveOperand(element, oldIndex, newIndex); }
			public void handleRemove(InteractionOperand element) { removeFromOperand(element); }
			public void navigateTo(InteractionOperand element) { }
		});
		this.operand.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.CombinedFragment.operand, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.operand, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
		
	}

	/**
	 * 
	 */
	protected void addToOperand() {

		// Start of user code addToOperand() method body


		InteractionOperand eObject = UMLFactory.eINSTANCE.createInteractionOperand();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject,resourceSet));
			if (propertiesEditionObject != null) {
				operandEditUtil.addElement(propertiesEditionObject);
				operand.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.operand, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}
		
		
		// End of user code
		
	}

	/**
	 * 
	 */
	private void removeFromOperand(InteractionOperand element) {

		// Start of user code removeFromOperand() method body

		EObject editedElement = operandEditUtil.foundCorrespondingEObject(element);
		operandEditUtil.removeElement(element);
		operand.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.operand, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

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
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.operand, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}
		
		// End of user code

	}
	/**
	 * @param container
	 */
	protected void createCfragmentGateAdvancedTableComposition(Composite parent) {
		this.cfragmentGate = new ReferencesTable<Gate>(UMLMessages.CombinedFragmentPropertiesEditionPart_CfragmentGateLabel, new ReferencesTableListener<Gate>() {			
			public void handleAdd() { addToCfragmentGate();}
			public void handleEdit(Gate element) { editCfragmentGate(element); }
			public void handleMove(Gate element, int oldIndex, int newIndex) { moveCfragmentGate(element, oldIndex, newIndex); }
			public void handleRemove(Gate element) { removeFromCfragmentGate(element); }
			public void navigateTo(Gate element) { }
		});
		this.cfragmentGate.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.CombinedFragment.cfragmentGate, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.cfragmentGate, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
		
	}

	/**
	 * 
	 */
	protected void addToCfragmentGate() {

		// Start of user code addToCfragmentGate() method body


		Gate eObject = UMLFactory.eINSTANCE.createGate();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject,resourceSet));
			if (propertiesEditionObject != null) {
				cfragmentGateEditUtil.addElement(propertiesEditionObject);
				cfragmentGate.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.cfragmentGate, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}
		
		
		// End of user code
		
	}

	/**
	 * 
	 */
	private void removeFromCfragmentGate(Gate element) {

		// Start of user code removeFromCfragmentGate() method body

		EObject editedElement = cfragmentGateEditUtil.foundCorrespondingEObject(element);
		cfragmentGateEditUtil.removeElement(element);
		cfragmentGate.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.cfragmentGate, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

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
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(CombinedFragmentPropertiesEditionPartImpl.this, UMLViewsRepository.CombinedFragment.cfragmentGate, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}
		
		// End of user code

	}


	public void firePropertiesChanged(PropertiesEditionEvent event) {
		// Start of user code for tab synchronization
		
		// End of user code

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getOwnedCommentToAdd()
	 */
	public List getOwnedCommentToAdd() {
		return ownedCommentEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getOwnedCommentToRemove()
	 */
	public List getOwnedCommentToRemove() {
		return ownedCommentEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getOwnedCommentToEdit()
	 */
	public Map getOwnedCommentToEdit() {
		return ownedCommentEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getOwnedCommentToMove()
	 */
	public List getOwnedCommentToMove() {
		return ownedCommentEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getOwnedCommentTable()
	 */
	public List getOwnedCommentTable() {
		return ownedCommentEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#initOwnedComment(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#updateOwnedComment(EObject newValue)
	 */
	public void updateOwnedComment(EObject newValue) {
		if(ownedCommentEditUtil!=null){
			ownedCommentEditUtil.reinit(newValue);
			ownedComment.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#addFilterOwnedComment(ViewerFilter filter)
	 */
	public void addFilterToOwnedComment(ViewerFilter filter) {
		ownedCommentFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#addBusinessFilterOwnedComment(ViewerFilter filter)
	 */
	public void addBusinessFilterToOwnedComment(ViewerFilter filter) {
		ownedCommentBusinessFilters.add(filter);
	}

	public void setMessageForOwnedComment(String msg, int msgLevel) {

	}

	public void unsetMessageForOwnedComment() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getName()
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#setName(String newValue)
	 */
	public void setName(String newValue) {
		name.setText(newValue);
	}

	public void setMessageForName(String msg, int msgLevel) {

	}

	public void unsetMessageForName() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getVisibility()
	 */
	public Enumerator getVisibility() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) visibility.getSelection()).getFirstElement();
		return selection.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#initVisibility(EEnum eenum, Enumerator current)
	 */
	public void initVisibility(EEnum eenum, Enumerator current) {
		visibility.setInput(eenum.getELiterals());
		visibility.modelUpdating(new StructuredSelection(current));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#setVisibility(Enumerator newValue)
	 */
	public void setVisibility(Enumerator newValue) {
		visibility.modelUpdating(new StructuredSelection(newValue));
	}

	public void setMessageForVisibility(String msg, int msgLevel) {

	}

	public void unsetMessageForVisibility() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getClientDependencyToAdd()
	 */
	public List getClientDependencyToAdd() {
		return clientDependencyEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getClientDependencyToRemove()
	 */
	public List getClientDependencyToRemove() {
		return clientDependencyEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getClientDependencyTable()
	 */
	public List getClientDependencyTable() {
		return clientDependencyEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#initClientDependency(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#updateClientDependency(EObject newValue)
	 */
	public void updateClientDependency(EObject newValue) {
		if(clientDependencyEditUtil!=null){
			clientDependencyEditUtil.reinit(newValue);
			clientDependency.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#addFilterClientDependency(ViewerFilter filter)
	 */
	public void addFilterToClientDependency(ViewerFilter filter) {
		clientDependencyFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#addBusinessFilterClientDependency(ViewerFilter filter)
	 */
	public void addBusinessFilterToClientDependency(ViewerFilter filter) {
		clientDependencyBusinessFilters.add(filter);
	}

	public void setMessageForClientDependency(String msg, int msgLevel) {

	}

	public void unsetMessageForClientDependency() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getCoveredToAdd()
	 */
	public List getCoveredToAdd() {
		return coveredEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getCoveredToRemove()
	 */
	public List getCoveredToRemove() {
		return coveredEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getCoveredTable()
	 */
	public List getCoveredTable() {
		return coveredEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#initCovered(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#updateCovered(EObject newValue)
	 */
	public void updateCovered(EObject newValue) {
		if(coveredEditUtil!=null){
			coveredEditUtil.reinit(newValue);
			covered.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#addFilterCovered(ViewerFilter filter)
	 */
	public void addFilterToCovered(ViewerFilter filter) {
		coveredFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#addBusinessFilterCovered(ViewerFilter filter)
	 */
	public void addBusinessFilterToCovered(ViewerFilter filter) {
		coveredBusinessFilters.add(filter);
	}

	public void setMessageForCovered(String msg, int msgLevel) {

	}

	public void unsetMessageForCovered() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getGeneralOrderingToAdd()
	 */
	public List getGeneralOrderingToAdd() {
		return generalOrderingEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getGeneralOrderingToRemove()
	 */
	public List getGeneralOrderingToRemove() {
		return generalOrderingEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getGeneralOrderingToEdit()
	 */
	public Map getGeneralOrderingToEdit() {
		return generalOrderingEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getGeneralOrderingToMove()
	 */
	public List getGeneralOrderingToMove() {
		return generalOrderingEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getGeneralOrderingTable()
	 */
	public List getGeneralOrderingTable() {
		return generalOrderingEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#initGeneralOrdering(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#updateGeneralOrdering(EObject newValue)
	 */
	public void updateGeneralOrdering(EObject newValue) {
		if(generalOrderingEditUtil!=null){
			generalOrderingEditUtil.reinit(newValue);
			generalOrdering.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#addFilterGeneralOrdering(ViewerFilter filter)
	 */
	public void addFilterToGeneralOrdering(ViewerFilter filter) {
		generalOrderingFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#addBusinessFilterGeneralOrdering(ViewerFilter filter)
	 */
	public void addBusinessFilterToGeneralOrdering(ViewerFilter filter) {
		generalOrderingBusinessFilters.add(filter);
	}

	public void setMessageForGeneralOrdering(String msg, int msgLevel) {

	}

	public void unsetMessageForGeneralOrdering() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getInteractionOperator()
	 */
	public Enumerator getInteractionOperator() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) interactionOperator.getSelection()).getFirstElement();
		return selection.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#initInteractionOperator(EEnum eenum, Enumerator current)
	 */
	public void initInteractionOperator(EEnum eenum, Enumerator current) {
		interactionOperator.setInput(eenum.getELiterals());
		interactionOperator.modelUpdating(new StructuredSelection(current));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#setInteractionOperator(Enumerator newValue)
	 */
	public void setInteractionOperator(Enumerator newValue) {
		interactionOperator.modelUpdating(new StructuredSelection(newValue));
	}

	public void setMessageForInteractionOperator(String msg, int msgLevel) {

	}

	public void unsetMessageForInteractionOperator() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getOperandToAdd()
	 */
	public List getOperandToAdd() {
		return operandEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getOperandToRemove()
	 */
	public List getOperandToRemove() {
		return operandEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getOperandToEdit()
	 */
	public Map getOperandToEdit() {
		return operandEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getOperandToMove()
	 */
	public List getOperandToMove() {
		return operandEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getOperandTable()
	 */
	public List getOperandTable() {
		return operandEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#initOperand(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#updateOperand(EObject newValue)
	 */
	public void updateOperand(EObject newValue) {
		if(operandEditUtil!=null){
			operandEditUtil.reinit(newValue);
			operand.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#addFilterOperand(ViewerFilter filter)
	 */
	public void addFilterToOperand(ViewerFilter filter) {
		operandFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#addBusinessFilterOperand(ViewerFilter filter)
	 */
	public void addBusinessFilterToOperand(ViewerFilter filter) {
		operandBusinessFilters.add(filter);
	}

	public void setMessageForOperand(String msg, int msgLevel) {

	}

	public void unsetMessageForOperand() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getCfragmentGateToAdd()
	 */
	public List getCfragmentGateToAdd() {
		return cfragmentGateEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getCfragmentGateToRemove()
	 */
	public List getCfragmentGateToRemove() {
		return cfragmentGateEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getCfragmentGateToEdit()
	 */
	public Map getCfragmentGateToEdit() {
		return cfragmentGateEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getCfragmentGateToMove()
	 */
	public List getCfragmentGateToMove() {
		return cfragmentGateEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#getCfragmentGateTable()
	 */
	public List getCfragmentGateTable() {
		return cfragmentGateEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#initCfragmentGate(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#updateCfragmentGate(EObject newValue)
	 */
	public void updateCfragmentGate(EObject newValue) {
		if(cfragmentGateEditUtil!=null){
			cfragmentGateEditUtil.reinit(newValue);
			cfragmentGate.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#addFilterCfragmentGate(ViewerFilter filter)
	 */
	public void addFilterToCfragmentGate(ViewerFilter filter) {
		cfragmentGateFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.CombinedFragmentPropertiesEditionPart#addBusinessFilterCfragmentGate(ViewerFilter filter)
	 */
	public void addBusinessFilterToCfragmentGate(ViewerFilter filter) {
		cfragmentGateBusinessFilters.add(filter);
	}

	public void setMessageForCfragmentGate(String msg, int msgLevel) {

	}

	public void unsetMessageForCfragmentGate() {

	}








	// Start of user code additional methods
	
	// End of user code

}
