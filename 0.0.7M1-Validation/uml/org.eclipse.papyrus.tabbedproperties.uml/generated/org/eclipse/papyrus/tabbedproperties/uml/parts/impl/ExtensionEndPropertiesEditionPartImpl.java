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
import org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart;
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
import org.eclipse.uml2.uml.Deployment;
import org.eclipse.uml2.uml.TemplateBinding;
import org.eclipse.uml2.uml.Property;

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
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Property;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.emf.eef.runtime.impl.filters.EObjectFilter;

import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;

// End of user code

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class ExtensionEndPropertiesEditionPartImpl extends CompositePropertiesEditionPart implements
		ISWTPropertiesEditionPart, ExtensionEndPropertiesEditionPart {

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

	protected Button isLeaf;

	protected Button isStatic;

	protected Button isOrdered;

	protected Button isUnique;

	protected Button isReadOnly;

	protected EMFListEditUtil deploymentEditUtil;

	protected ReferencesTable<?> deployment;

	protected List<ViewerFilter> deploymentBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> deploymentFilters = new ArrayList<ViewerFilter>();

	protected Button isDerived;

	protected Button isDerivedUnion;

	protected EMFComboViewer aggregation;

	protected EMFListEditUtil redefinedPropertyEditUtil;

	protected ReferencesTable<?> redefinedProperty;

	protected List<ViewerFilter> redefinedPropertyBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> redefinedPropertyFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil subsettedPropertyEditUtil;

	protected ReferencesTable<?> subsettedProperty;

	protected List<ViewerFilter> subsettedPropertyBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> subsettedPropertyFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil qualifierEditUtil;

	protected ReferencesTable<?> qualifier;

	protected List<ViewerFilter> qualifierBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> qualifierFilters = new ArrayList<ViewerFilter>();

	public ExtensionEndPropertiesEditionPartImpl(IPropertiesEditionComponent editionComponent) {
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
		propertiesGroup.setText(UMLMessages.ExtensionEndPropertiesEditionPart_PropertiesGroupLabel);
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
		createIsLeafCheckbox(propertiesGroup);
		createIsStaticCheckbox(propertiesGroup);
		createIsOrderedCheckbox(propertiesGroup);
		createIsUniqueCheckbox(propertiesGroup);
		createIsReadOnlyCheckbox(propertiesGroup);
		createDeploymentAdvancedTableComposition(propertiesGroup);
		createIsDerivedCheckbox(propertiesGroup);
		createIsDerivedUnionCheckbox(propertiesGroup);
		createAggregationEMFComboViewer(propertiesGroup);
		createRedefinedPropertyAdvancedReferencesTable(propertiesGroup);
		createSubsettedPropertyAdvancedReferencesTable(propertiesGroup);
		createQualifierAdvancedTableComposition(propertiesGroup);
	}

	/**
	 * @param container
	 */
	protected void createOwnedCommentAdvancedTableComposition(Composite parent) {
		this.ownedComment = new ReferencesTable<Comment>(
				UMLMessages.ExtensionEndPropertiesEditionPart_OwnedCommentLabel,
				new ReferencesTableListener<Comment>() {

					public void handleAdd() {
						addToOwnedComment();
					}

					public void handleEdit(Comment element) {
						editOwnedComment(element);
					}

					public void handleMove(Comment element, int oldIndex, int newIndex) {
						moveOwnedComment(element, oldIndex, newIndex);
					}

					public void handleRemove(Comment element) {
						removeFromOwnedComment(element);
					}

					public void navigateTo(Comment element) {
					}
				});
		this.ownedComment.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExtensionEnd.ownedComment, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.ownedComment,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToOwnedComment() {

		// Start of user code addToOwnedComment() method body

		Comment eObject = UMLFactory.eINSTANCE.createComment();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				ownedCommentEditUtil.addElement(propertiesEditionObject);
				ownedComment.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.ownedComment,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.ownedComment,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editOwnedComment(Comment element) {

		// Start of user code editOwnedComment() method body

		EObject editedElement = ownedCommentEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedCommentEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedComment.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.ownedComment,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createNameText(Composite parent) {
		SWTUtils.createPartLabel(parent, UMLMessages.ExtensionEndPropertiesEditionPart_NameLabel,
				propertiesEditionComponent
						.isRequired(UMLViewsRepository.ExtensionEnd.name, UMLViewsRepository.SWT_KIND));
		name = new Text(parent, SWT.BORDER);
		GridData nameData = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(nameData);
		name.addModifyListener(new ModifyListener() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			public void modifyText(ModifyEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
							ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.name,
							PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, null, name.getText()));
			}

		});

		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExtensionEnd.name, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createVisibilityEMFComboViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UMLMessages.ExtensionEndPropertiesEditionPart_VisibilityLabel,
				propertiesEditionComponent.isRequired(UMLViewsRepository.ExtensionEnd.visibility,
						UMLViewsRepository.SWT_KIND));
		visibility = new EMFComboViewer(parent);
		visibility.setContentProvider(new ArrayContentProvider());
		visibility.setLabelProvider(new AdapterFactoryLabelProvider(new EcoreAdapterFactory()));
		GridData visibilityData = new GridData(GridData.FILL_HORIZONTAL);
		visibility.getCombo().setLayoutData(visibilityData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExtensionEnd.visibility, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createClientDependencyAdvancedReferencesTable(Composite parent) {
		this.clientDependency = new ReferencesTable<Dependency>(
				UMLMessages.ExtensionEndPropertiesEditionPart_ClientDependencyLabel,
				new ReferencesTableListener<Dependency>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<Dependency> dialog = new TabElementTreeSelectionDialog<Dependency>(
								resourceSet, clientDependencyFilters, clientDependencyBusinessFilters, "Dependency",
								UMLPackage.eINSTANCE.getDependency()) {

							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!clientDependencyEditUtil.getVirtualList().contains(elem))
										clientDependencyEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											ExtensionEndPropertiesEditionPartImpl.this,
											UMLViewsRepository.ExtensionEnd.clientDependency,
											PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
								}
								clientDependency.refresh();
							}

						};
						dialog.open();
					}

					public void handleEdit(Dependency element) {
						editClientDependency(element);
					}

					public void handleMove(Dependency element, int oldIndex, int newIndex) {
						moveClientDependency(element, oldIndex, newIndex);
					}

					public void handleRemove(Dependency element) {
						removeFromClientDependency(element);
					}

					public void navigateTo(Dependency element) {
					}
				});
		this.clientDependency.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExtensionEnd.clientDependency, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.clientDependency,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	private void removeFromClientDependency(Dependency element) {

		// Start of user code removeFromClientDependency() method body

		EObject editedElement = clientDependencyEditUtil.foundCorrespondingEObject(element);
		clientDependencyEditUtil.removeElement(element);
		clientDependency.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.clientDependency,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editClientDependency(Dependency element) {

		// Start of user code editClientDependency() method body

		EObject editedElement = clientDependencyEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				clientDependencyEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				clientDependency.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.clientDependency,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createIsLeafCheckbox(Composite parent) {
		isLeaf = new Button(parent, SWT.CHECK);
		isLeaf.setText(UMLMessages.ExtensionEndPropertiesEditionPart_IsLeafLabel);
		GridData isLeafData = new GridData(GridData.FILL_HORIZONTAL);
		isLeafData.horizontalSpan = 2;
		isLeaf.setLayoutData(isLeafData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExtensionEnd.isLeaf, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createIsStaticCheckbox(Composite parent) {
		isStatic = new Button(parent, SWT.CHECK);
		isStatic.setText(UMLMessages.ExtensionEndPropertiesEditionPart_IsStaticLabel);
		GridData isStaticData = new GridData(GridData.FILL_HORIZONTAL);
		isStaticData.horizontalSpan = 2;
		isStatic.setLayoutData(isStaticData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExtensionEnd.isStatic, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createIsOrderedCheckbox(Composite parent) {
		isOrdered = new Button(parent, SWT.CHECK);
		isOrdered.setText(UMLMessages.ExtensionEndPropertiesEditionPart_IsOrderedLabel);
		GridData isOrderedData = new GridData(GridData.FILL_HORIZONTAL);
		isOrderedData.horizontalSpan = 2;
		isOrdered.setLayoutData(isOrderedData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExtensionEnd.isOrdered, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createIsUniqueCheckbox(Composite parent) {
		isUnique = new Button(parent, SWT.CHECK);
		isUnique.setText(UMLMessages.ExtensionEndPropertiesEditionPart_IsUniqueLabel);
		GridData isUniqueData = new GridData(GridData.FILL_HORIZONTAL);
		isUniqueData.horizontalSpan = 2;
		isUnique.setLayoutData(isUniqueData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExtensionEnd.isUnique, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createIsReadOnlyCheckbox(Composite parent) {
		isReadOnly = new Button(parent, SWT.CHECK);
		isReadOnly.setText(UMLMessages.ExtensionEndPropertiesEditionPart_IsReadOnlyLabel);
		GridData isReadOnlyData = new GridData(GridData.FILL_HORIZONTAL);
		isReadOnlyData.horizontalSpan = 2;
		isReadOnly.setLayoutData(isReadOnlyData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExtensionEnd.isReadOnly, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	/**
	 * @param container
	 */
	protected void createDeploymentAdvancedTableComposition(Composite parent) {
		this.deployment = new ReferencesTable<Deployment>(
				UMLMessages.ExtensionEndPropertiesEditionPart_DeploymentLabel,
				new ReferencesTableListener<Deployment>() {

					public void handleAdd() {
						addToDeployment();
					}

					public void handleEdit(Deployment element) {
						editDeployment(element);
					}

					public void handleMove(Deployment element, int oldIndex, int newIndex) {
						moveDeployment(element, oldIndex, newIndex);
					}

					public void handleRemove(Deployment element) {
						removeFromDeployment(element);
					}

					public void navigateTo(Deployment element) {
					}
				});
		this.deployment.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExtensionEnd.deployment, UMLViewsRepository.SWT_KIND));
		this.deployment.createControls(parent);
		GridData deploymentData = new GridData(GridData.FILL_HORIZONTAL);
		deploymentData.horizontalSpan = 3;
		this.deployment.setLayoutData(deploymentData);
	}

	/**
	 * 
	 */
	private void moveDeployment(Deployment element, int oldIndex, int newIndex) {

		EObject editedElement = deploymentEditUtil.foundCorrespondingEObject(element);
		deploymentEditUtil.moveElement(element, oldIndex, newIndex);
		deployment.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.deployment,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToDeployment() {

		// Start of user code addToDeployment() method body

		Deployment eObject = UMLFactory.eINSTANCE.createDeployment();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				deploymentEditUtil.addElement(propertiesEditionObject);
				deployment.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.deployment,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromDeployment(Deployment element) {

		// Start of user code removeFromDeployment() method body

		EObject editedElement = deploymentEditUtil.foundCorrespondingEObject(element);
		deploymentEditUtil.removeElement(element);
		deployment.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.deployment,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editDeployment(Deployment element) {

		// Start of user code editDeployment() method body

		EObject editedElement = deploymentEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				deploymentEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				deployment.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.deployment,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createIsDerivedCheckbox(Composite parent) {
		isDerived = new Button(parent, SWT.CHECK);
		isDerived.setText(UMLMessages.ExtensionEndPropertiesEditionPart_IsDerivedLabel);
		GridData isDerivedData = new GridData(GridData.FILL_HORIZONTAL);
		isDerivedData.horizontalSpan = 2;
		isDerived.setLayoutData(isDerivedData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExtensionEnd.isDerived, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createIsDerivedUnionCheckbox(Composite parent) {
		isDerivedUnion = new Button(parent, SWT.CHECK);
		isDerivedUnion.setText(UMLMessages.ExtensionEndPropertiesEditionPart_IsDerivedUnionLabel);
		GridData isDerivedUnionData = new GridData(GridData.FILL_HORIZONTAL);
		isDerivedUnionData.horizontalSpan = 2;
		isDerivedUnion.setLayoutData(isDerivedUnionData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExtensionEnd.isDerivedUnion, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createAggregationEMFComboViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UMLMessages.ExtensionEndPropertiesEditionPart_AggregationLabel,
				propertiesEditionComponent.isRequired(UMLViewsRepository.ExtensionEnd.aggregation,
						UMLViewsRepository.SWT_KIND));
		aggregation = new EMFComboViewer(parent);
		aggregation.setContentProvider(new ArrayContentProvider());
		aggregation.setLabelProvider(new AdapterFactoryLabelProvider(new EcoreAdapterFactory()));
		GridData aggregationData = new GridData(GridData.FILL_HORIZONTAL);
		aggregation.getCombo().setLayoutData(aggregationData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExtensionEnd.aggregation, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createRedefinedPropertyAdvancedReferencesTable(Composite parent) {
		this.redefinedProperty = new ReferencesTable<Property>(
				UMLMessages.ExtensionEndPropertiesEditionPart_RedefinedPropertyLabel,
				new ReferencesTableListener<Property>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<Property> dialog = new TabElementTreeSelectionDialog<Property>(
								resourceSet, redefinedPropertyFilters, redefinedPropertyBusinessFilters, "Property",
								UMLPackage.eINSTANCE.getProperty()) {

							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!redefinedPropertyEditUtil.getVirtualList().contains(elem))
										redefinedPropertyEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											ExtensionEndPropertiesEditionPartImpl.this,
											UMLViewsRepository.ExtensionEnd.redefinedProperty,
											PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
								}
								redefinedProperty.refresh();
							}

						};
						dialog.open();
					}

					public void handleEdit(Property element) {
						editRedefinedProperty(element);
					}

					public void handleMove(Property element, int oldIndex, int newIndex) {
						moveRedefinedProperty(element, oldIndex, newIndex);
					}

					public void handleRemove(Property element) {
						removeFromRedefinedProperty(element);
					}

					public void navigateTo(Property element) {
					}
				});
		this.redefinedProperty.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExtensionEnd.redefinedProperty, UMLViewsRepository.SWT_KIND));
		this.redefinedProperty.createControls(parent);
		GridData redefinedPropertyData = new GridData(GridData.FILL_HORIZONTAL);
		redefinedPropertyData.horizontalSpan = 3;
		this.redefinedProperty.setLayoutData(redefinedPropertyData);
		this.redefinedProperty.disableMove();
	}

	/**
	 * 
	 */
	private void moveRedefinedProperty(Property element, int oldIndex, int newIndex) {
		EObject editedElement = redefinedPropertyEditUtil.foundCorrespondingEObject(element);
		redefinedPropertyEditUtil.moveElement(element, oldIndex, newIndex);
		redefinedProperty.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.redefinedProperty,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	private void removeFromRedefinedProperty(Property element) {

		// Start of user code removeFromRedefinedProperty() method body

		EObject editedElement = redefinedPropertyEditUtil.foundCorrespondingEObject(element);
		redefinedPropertyEditUtil.removeElement(element);
		redefinedProperty.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.redefinedProperty,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editRedefinedProperty(Property element) {

		// Start of user code editRedefinedProperty() method body

		EObject editedElement = redefinedPropertyEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				redefinedPropertyEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				redefinedProperty.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.redefinedProperty,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createSubsettedPropertyAdvancedReferencesTable(Composite parent) {
		this.subsettedProperty = new ReferencesTable<Property>(
				UMLMessages.ExtensionEndPropertiesEditionPart_SubsettedPropertyLabel,
				new ReferencesTableListener<Property>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<Property> dialog = new TabElementTreeSelectionDialog<Property>(
								resourceSet, subsettedPropertyFilters, subsettedPropertyBusinessFilters, "Property",
								UMLPackage.eINSTANCE.getProperty()) {

							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!subsettedPropertyEditUtil.getVirtualList().contains(elem))
										subsettedPropertyEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											ExtensionEndPropertiesEditionPartImpl.this,
											UMLViewsRepository.ExtensionEnd.subsettedProperty,
											PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
								}
								subsettedProperty.refresh();
							}

						};
						dialog.open();
					}

					public void handleEdit(Property element) {
						editSubsettedProperty(element);
					}

					public void handleMove(Property element, int oldIndex, int newIndex) {
						moveSubsettedProperty(element, oldIndex, newIndex);
					}

					public void handleRemove(Property element) {
						removeFromSubsettedProperty(element);
					}

					public void navigateTo(Property element) {
					}
				});
		this.subsettedProperty.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExtensionEnd.subsettedProperty, UMLViewsRepository.SWT_KIND));
		this.subsettedProperty.createControls(parent);
		GridData subsettedPropertyData = new GridData(GridData.FILL_HORIZONTAL);
		subsettedPropertyData.horizontalSpan = 3;
		this.subsettedProperty.setLayoutData(subsettedPropertyData);
		this.subsettedProperty.disableMove();
	}

	/**
	 * 
	 */
	private void moveSubsettedProperty(Property element, int oldIndex, int newIndex) {
		EObject editedElement = subsettedPropertyEditUtil.foundCorrespondingEObject(element);
		subsettedPropertyEditUtil.moveElement(element, oldIndex, newIndex);
		subsettedProperty.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.subsettedProperty,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	private void removeFromSubsettedProperty(Property element) {

		// Start of user code removeFromSubsettedProperty() method body

		EObject editedElement = subsettedPropertyEditUtil.foundCorrespondingEObject(element);
		subsettedPropertyEditUtil.removeElement(element);
		subsettedProperty.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.subsettedProperty,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editSubsettedProperty(Property element) {

		// Start of user code editSubsettedProperty() method body

		EObject editedElement = subsettedPropertyEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				subsettedPropertyEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				subsettedProperty.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.subsettedProperty,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createQualifierAdvancedTableComposition(Composite parent) {
		this.qualifier = new ReferencesTable<Property>(UMLMessages.ExtensionEndPropertiesEditionPart_QualifierLabel,
				new ReferencesTableListener<Property>() {

					public void handleAdd() {
						addToQualifier();
					}

					public void handleEdit(Property element) {
						editQualifier(element);
					}

					public void handleMove(Property element, int oldIndex, int newIndex) {
						moveQualifier(element, oldIndex, newIndex);
					}

					public void handleRemove(Property element) {
						removeFromQualifier(element);
					}

					public void navigateTo(Property element) {
					}
				});
		this.qualifier.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.ExtensionEnd.qualifier,
				UMLViewsRepository.SWT_KIND));
		this.qualifier.createControls(parent);
		GridData qualifierData = new GridData(GridData.FILL_HORIZONTAL);
		qualifierData.horizontalSpan = 3;
		this.qualifier.setLayoutData(qualifierData);
	}

	/**
	 * 
	 */
	private void moveQualifier(Property element, int oldIndex, int newIndex) {

		EObject editedElement = qualifierEditUtil.foundCorrespondingEObject(element);
		qualifierEditUtil.moveElement(element, oldIndex, newIndex);
		qualifier.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.qualifier,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToQualifier() {

		// Start of user code addToQualifier() method body

		Property eObject = UMLFactory.eINSTANCE.createProperty();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				qualifierEditUtil.addElement(propertiesEditionObject);
				qualifier.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.qualifier,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromQualifier(Property element) {

		// Start of user code removeFromQualifier() method body

		EObject editedElement = qualifierEditUtil.foundCorrespondingEObject(element);
		qualifierEditUtil.removeElement(element);
		qualifier.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.qualifier,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editQualifier(Property element) {

		// Start of user code editQualifier() method body

		EObject editedElement = qualifierEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				qualifierEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				qualifier.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ExtensionEndPropertiesEditionPartImpl.this, UMLViewsRepository.ExtensionEnd.qualifier,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getOwnedCommentToAdd()
	 */
	public List getOwnedCommentToAdd() {
		return ownedCommentEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getOwnedCommentToRemove()
	 */
	public List getOwnedCommentToRemove() {
		return ownedCommentEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getOwnedCommentToEdit()
	 */
	public Map getOwnedCommentToEdit() {
		return ownedCommentEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getOwnedCommentToMove()
	 */
	public List getOwnedCommentToMove() {
		return ownedCommentEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getOwnedCommentTable()
	 */
	public List getOwnedCommentTable() {
		return ownedCommentEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#initOwnedComment(EObject
	 *      current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#updateOwnedComment(EObject
	 *      newValue)
	 */
	public void updateOwnedComment(EObject newValue) {
		if (ownedCommentEditUtil != null) {
			ownedCommentEditUtil.reinit(newValue);
			ownedComment.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#addFilterOwnedComment(ViewerFilter
	 *      filter)
	 */
	public void addFilterToOwnedComment(ViewerFilter filter) {
		ownedCommentFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#addBusinessFilterOwnedComment(ViewerFilter
	 *      filter)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getName()
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#setName(String
	 *      newValue)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getVisibility()
	 */
	public Enumerator getVisibility() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) visibility.getSelection()).getFirstElement();
		return selection.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#initVisibility(EEnum
	 *      eenum, Enumerator current)
	 */
	public void initVisibility(EEnum eenum, Enumerator current) {
		visibility.setInput(eenum.getELiterals());
		visibility.modelUpdating(new StructuredSelection(current));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#setVisibility(Enumerator
	 *      newValue)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getClientDependencyToAdd()
	 */
	public List getClientDependencyToAdd() {
		return clientDependencyEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getClientDependencyToRemove()
	 */
	public List getClientDependencyToRemove() {
		return clientDependencyEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getClientDependencyTable()
	 */
	public List getClientDependencyTable() {
		return clientDependencyEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#initClientDependency(EObject
	 *      current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#updateClientDependency(EObject
	 *      newValue)
	 */
	public void updateClientDependency(EObject newValue) {
		if (clientDependencyEditUtil != null) {
			clientDependencyEditUtil.reinit(newValue);
			clientDependency.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#addFilterClientDependency(ViewerFilter
	 *      filter)
	 */
	public void addFilterToClientDependency(ViewerFilter filter) {
		clientDependencyFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#addBusinessFilterClientDependency(ViewerFilter
	 *      filter)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getIsLeaf()
	 */
	public Boolean getIsLeaf() {
		return Boolean.valueOf(isLeaf.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#setIsLeaf(Boolean
	 *      newValue)
	 */
	public void setIsLeaf(Boolean newValue) {
		if (newValue != null) {
			isLeaf.setSelection(newValue.booleanValue());
		} else {
			isLeaf.setSelection(false);
		}
	}

	public void setMessageForIsLeaf(String msg, int msgLevel) {

	}

	public void unsetMessageForIsLeaf() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getIsStatic()
	 */
	public Boolean getIsStatic() {
		return Boolean.valueOf(isStatic.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#setIsStatic(Boolean
	 *      newValue)
	 */
	public void setIsStatic(Boolean newValue) {
		if (newValue != null) {
			isStatic.setSelection(newValue.booleanValue());
		} else {
			isStatic.setSelection(false);
		}
	}

	public void setMessageForIsStatic(String msg, int msgLevel) {

	}

	public void unsetMessageForIsStatic() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getIsOrdered()
	 */
	public Boolean getIsOrdered() {
		return Boolean.valueOf(isOrdered.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#setIsOrdered(Boolean
	 *      newValue)
	 */
	public void setIsOrdered(Boolean newValue) {
		if (newValue != null) {
			isOrdered.setSelection(newValue.booleanValue());
		} else {
			isOrdered.setSelection(false);
		}
	}

	public void setMessageForIsOrdered(String msg, int msgLevel) {

	}

	public void unsetMessageForIsOrdered() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getIsUnique()
	 */
	public Boolean getIsUnique() {
		return Boolean.valueOf(isUnique.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#setIsUnique(Boolean
	 *      newValue)
	 */
	public void setIsUnique(Boolean newValue) {
		if (newValue != null) {
			isUnique.setSelection(newValue.booleanValue());
		} else {
			isUnique.setSelection(false);
		}
	}

	public void setMessageForIsUnique(String msg, int msgLevel) {

	}

	public void unsetMessageForIsUnique() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getIsReadOnly()
	 */
	public Boolean getIsReadOnly() {
		return Boolean.valueOf(isReadOnly.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#setIsReadOnly(Boolean
	 *      newValue)
	 */
	public void setIsReadOnly(Boolean newValue) {
		if (newValue != null) {
			isReadOnly.setSelection(newValue.booleanValue());
		} else {
			isReadOnly.setSelection(false);
		}
	}

	public void setMessageForIsReadOnly(String msg, int msgLevel) {

	}

	public void unsetMessageForIsReadOnly() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getDeploymentToAdd()
	 */
	public List getDeploymentToAdd() {
		return deploymentEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getDeploymentToRemove()
	 */
	public List getDeploymentToRemove() {
		return deploymentEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getDeploymentToEdit()
	 */
	public Map getDeploymentToEdit() {
		return deploymentEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getDeploymentToMove()
	 */
	public List getDeploymentToMove() {
		return deploymentEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getDeploymentTable()
	 */
	public List getDeploymentTable() {
		return deploymentEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#initDeployment(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initDeployment(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			deploymentEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			deploymentEditUtil = new EMFListEditUtil(current, feature);
		this.deployment.setInput(deploymentEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#updateDeployment(EObject
	 *      newValue)
	 */
	public void updateDeployment(EObject newValue) {
		if (deploymentEditUtil != null) {
			deploymentEditUtil.reinit(newValue);
			deployment.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#addFilterDeployment(ViewerFilter
	 *      filter)
	 */
	public void addFilterToDeployment(ViewerFilter filter) {
		deploymentFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#addBusinessFilterDeployment(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToDeployment(ViewerFilter filter) {
		deploymentBusinessFilters.add(filter);
	}

	public void setMessageForDeployment(String msg, int msgLevel) {

	}

	public void unsetMessageForDeployment() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getIsDerived()
	 */
	public Boolean getIsDerived() {
		return Boolean.valueOf(isDerived.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#setIsDerived(Boolean
	 *      newValue)
	 */
	public void setIsDerived(Boolean newValue) {
		if (newValue != null) {
			isDerived.setSelection(newValue.booleanValue());
		} else {
			isDerived.setSelection(false);
		}
	}

	public void setMessageForIsDerived(String msg, int msgLevel) {

	}

	public void unsetMessageForIsDerived() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getIsDerivedUnion()
	 */
	public Boolean getIsDerivedUnion() {
		return Boolean.valueOf(isDerivedUnion.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#setIsDerivedUnion(Boolean
	 *      newValue)
	 */
	public void setIsDerivedUnion(Boolean newValue) {
		if (newValue != null) {
			isDerivedUnion.setSelection(newValue.booleanValue());
		} else {
			isDerivedUnion.setSelection(false);
		}
	}

	public void setMessageForIsDerivedUnion(String msg, int msgLevel) {

	}

	public void unsetMessageForIsDerivedUnion() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getAggregation()
	 */
	public Enumerator getAggregation() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) aggregation.getSelection()).getFirstElement();
		return selection.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#initAggregation(EEnum
	 *      eenum, Enumerator current)
	 */
	public void initAggregation(EEnum eenum, Enumerator current) {
		aggregation.setInput(eenum.getELiterals());
		aggregation.modelUpdating(new StructuredSelection(current));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#setAggregation(Enumerator
	 *      newValue)
	 */
	public void setAggregation(Enumerator newValue) {
		aggregation.modelUpdating(new StructuredSelection(newValue));
	}

	public void setMessageForAggregation(String msg, int msgLevel) {

	}

	public void unsetMessageForAggregation() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getRedefinedPropertyToAdd()
	 */
	public List getRedefinedPropertyToAdd() {
		return redefinedPropertyEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getRedefinedPropertyToRemove()
	 */
	public List getRedefinedPropertyToRemove() {
		return redefinedPropertyEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getRedefinedPropertyTable()
	 */
	public List getRedefinedPropertyTable() {
		return redefinedPropertyEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#initRedefinedProperty(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initRedefinedProperty(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			redefinedPropertyEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			redefinedPropertyEditUtil = new EMFListEditUtil(current, feature);
		this.redefinedProperty.setInput(redefinedPropertyEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#updateRedefinedProperty(EObject
	 *      newValue)
	 */
	public void updateRedefinedProperty(EObject newValue) {
		if (redefinedPropertyEditUtil != null) {
			redefinedPropertyEditUtil.reinit(newValue);
			redefinedProperty.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#addFilterRedefinedProperty(ViewerFilter
	 *      filter)
	 */
	public void addFilterToRedefinedProperty(ViewerFilter filter) {
		redefinedPropertyFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#addBusinessFilterRedefinedProperty(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToRedefinedProperty(ViewerFilter filter) {
		redefinedPropertyBusinessFilters.add(filter);
	}

	public void setMessageForRedefinedProperty(String msg, int msgLevel) {

	}

	public void unsetMessageForRedefinedProperty() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getSubsettedPropertyToAdd()
	 */
	public List getSubsettedPropertyToAdd() {
		return subsettedPropertyEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getSubsettedPropertyToRemove()
	 */
	public List getSubsettedPropertyToRemove() {
		return subsettedPropertyEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getSubsettedPropertyTable()
	 */
	public List getSubsettedPropertyTable() {
		return subsettedPropertyEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#initSubsettedProperty(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initSubsettedProperty(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			subsettedPropertyEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			subsettedPropertyEditUtil = new EMFListEditUtil(current, feature);
		this.subsettedProperty.setInput(subsettedPropertyEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#updateSubsettedProperty(EObject
	 *      newValue)
	 */
	public void updateSubsettedProperty(EObject newValue) {
		if (subsettedPropertyEditUtil != null) {
			subsettedPropertyEditUtil.reinit(newValue);
			subsettedProperty.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#addFilterSubsettedProperty(ViewerFilter
	 *      filter)
	 */
	public void addFilterToSubsettedProperty(ViewerFilter filter) {
		subsettedPropertyFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#addBusinessFilterSubsettedProperty(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToSubsettedProperty(ViewerFilter filter) {
		subsettedPropertyBusinessFilters.add(filter);
	}

	public void setMessageForSubsettedProperty(String msg, int msgLevel) {

	}

	public void unsetMessageForSubsettedProperty() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getQualifierToAdd()
	 */
	public List getQualifierToAdd() {
		return qualifierEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getQualifierToRemove()
	 */
	public List getQualifierToRemove() {
		return qualifierEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getQualifierToEdit()
	 */
	public Map getQualifierToEdit() {
		return qualifierEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getQualifierToMove()
	 */
	public List getQualifierToMove() {
		return qualifierEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#getQualifierTable()
	 */
	public List getQualifierTable() {
		return qualifierEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#initQualifier(EObject
	 *      current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#updateQualifier(EObject
	 *      newValue)
	 */
	public void updateQualifier(EObject newValue) {
		if (qualifierEditUtil != null) {
			qualifierEditUtil.reinit(newValue);
			qualifier.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#addFilterQualifier(ViewerFilter
	 *      filter)
	 */
	public void addFilterToQualifier(ViewerFilter filter) {
		qualifierFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExtensionEndPropertiesEditionPart#addBusinessFilterQualifier(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToQualifier(ViewerFilter filter) {
		qualifierBusinessFilters.add(filter);
	}

	public void setMessageForQualifier(String msg, int msgLevel) {

	}

	public void unsetMessageForQualifier() {

	}

	// Start of user code additional methods

	// End of user code

}
