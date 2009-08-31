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
import org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.emf.eef.runtime.ui.widgets.EMFComboViewer;

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
import org.eclipse.uml2.uml.UseCase;
import org.eclipse.uml2.uml.GeneralizationSet;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.emf.eef.runtime.impl.filters.EObjectFilter;
import java.util.Map;
import org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.ComponentRealization;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.UseCase;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Reception;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.CollaborationUse;
import org.eclipse.uml2.uml.Substitution;
import org.eclipse.uml2.uml.TemplateBinding;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.PackageImport;

import org.eclipse.emf.eef.runtime.ui.widgets.HorizontalBox;
import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;

// End of user code

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class ComponentPropertiesEditionPartImpl extends CompositePropertiesEditionPart implements
		ISWTPropertiesEditionPart, ComponentPropertiesEditionPart {

	protected Text name;

	protected EMFComboViewer visibility;

	protected Button isActive;

	protected Button isIndirectlyInstantiated;

	protected Button isAbstract;

	protected Button isLeaf;

	protected EMFListEditUtil ownedAttributeEditUtil;

	protected ReferencesTable<?> ownedAttribute;

	protected List<ViewerFilter> ownedAttributeBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> ownedAttributeFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil ownedOperationEditUtil;

	protected ReferencesTable<?> ownedOperation;

	protected List<ViewerFilter> ownedOperationBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> ownedOperationFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil nestedClassifierEditUtil;

	protected ReferencesTable<?> nestedClassifier;

	protected List<ViewerFilter> nestedClassifierBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> nestedClassifierFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil realizationEditUtil;

	protected ReferencesTable<?> realization;

	protected List<ViewerFilter> realizationBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> realizationFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil interfaceRealizationEditUtil;

	protected ReferencesTable<?> interfaceRealization;

	protected List<ViewerFilter> interfaceRealizationBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> interfaceRealizationFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil generalizationEditUtil;

	protected ReferencesTable<?> generalization;

	protected List<ViewerFilter> generalizationBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> generalizationFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil elementImportEditUtil;

	protected ReferencesTable<?> elementImport;

	protected List<ViewerFilter> elementImportBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> elementImportFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil ownedUseCaseEditUtil;

	protected ReferencesTable<?> ownedUseCase;

	protected List<ViewerFilter> ownedUseCaseBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> ownedUseCaseFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil packagedElementEditUtil;

	protected ReferencesTable<?> packagedElement;

	protected List<ViewerFilter> packagedElementBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> packagedElementFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil ownedReceptionEditUtil;

	protected ReferencesTable<?> ownedReception;

	protected List<ViewerFilter> ownedReceptionBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> ownedReceptionFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil ownedTriggerEditUtil;

	protected ReferencesTable<?> ownedTrigger;

	protected List<ViewerFilter> ownedTriggerBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> ownedTriggerFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil ownedBehaviorEditUtil;

	protected ReferencesTable<?> ownedBehavior;

	protected List<ViewerFilter> ownedBehaviorBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> ownedBehaviorFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil ownedConnectorEditUtil;

	protected ReferencesTable<?> ownedConnector;

	protected List<ViewerFilter> ownedConnectorBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> ownedConnectorFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil collaborationUseEditUtil;

	protected ReferencesTable<?> collaborationUse;

	protected List<ViewerFilter> collaborationUseBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> collaborationUseFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil useCaseEditUtil;

	protected ReferencesTable<?> useCase;

	protected List<ViewerFilter> useCaseBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> useCaseFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil substitutionEditUtil;

	protected ReferencesTable<?> substitution;

	protected List<ViewerFilter> substitutionBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> substitutionFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil powertypeExtentEditUtil;

	protected ReferencesTable<?> powertypeExtent;

	protected List<ViewerFilter> powertypeExtentBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> powertypeExtentFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil redefinedClassifierEditUtil;

	protected ReferencesTable<?> redefinedClassifier;

	protected List<ViewerFilter> redefinedClassifierBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> redefinedClassifierFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil templateBindingEditUtil;

	protected ReferencesTable<?> templateBinding;

	protected List<ViewerFilter> templateBindingBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> templateBindingFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil ownedRuleEditUtil;

	protected ReferencesTable<?> ownedRule;

	protected List<ViewerFilter> ownedRuleBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> ownedRuleFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil packageImportEditUtil;

	protected ReferencesTable<?> packageImport;

	protected List<ViewerFilter> packageImportBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> packageImportFilters = new ArrayList<ViewerFilter>();

	public ComponentPropertiesEditionPartImpl(IPropertiesEditionComponent editionComponent) {
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
		createGeneralGroup(view);
		createComponentSpecificationsGroup(view);
		createClassifierSpecificationsGroup(view);
		createOthersGroup(view);

		// Start of user code for additional ui definition

		// End of user code

	}

	protected void createGeneralGroup(Composite parent) {
		Group generalGroup = new Group(parent, SWT.NONE);
		generalGroup.setText(UMLMessages.ComponentPropertiesEditionPart_GeneralGroupLabel);
		GridData generalGroupData = new GridData(GridData.FILL_HORIZONTAL);
		generalGroupData.horizontalSpan = 3;
		generalGroup.setLayoutData(generalGroupData);
		GridLayout generalGroupLayout = new GridLayout();
		generalGroupLayout.numColumns = 3;
		generalGroup.setLayout(generalGroupLayout);
		createNameText(generalGroup);
		createVisibilityEMFComboViewer(generalGroup);
		createGeneralHBox1HBox(generalGroup);
	}

	protected void createNameText(Composite parent) {
		SWTUtils.createPartLabel(parent, UMLMessages.ComponentPropertiesEditionPart_NameLabel,
				propertiesEditionComponent.isRequired(UMLViewsRepository.Component.name, UMLViewsRepository.SWT_KIND));
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
							ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.name,
							PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, null, name.getText()));
			}

		});

		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.Component.name,
				UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createVisibilityEMFComboViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UMLMessages.ComponentPropertiesEditionPart_VisibilityLabel,
				propertiesEditionComponent.isRequired(UMLViewsRepository.Component.visibility,
						UMLViewsRepository.SWT_KIND));
		visibility = new EMFComboViewer(parent);
		visibility.setContentProvider(new ArrayContentProvider());
		visibility.setLabelProvider(new AdapterFactoryLabelProvider(new EcoreAdapterFactory()));
		GridData visibilityData = new GridData(GridData.FILL_HORIZONTAL);
		visibility.getCombo().setLayoutData(visibilityData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.visibility, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createGeneralHBox1HBox(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		container.setLayoutData(gridData);
		HorizontalBox generalHBox1HBox = new HorizontalBox(container);

		// Apply constraint for checkbox
		GridData constraint = new GridData(GridData.FILL_HORIZONTAL);
		constraint.horizontalAlignment = GridData.BEGINNING;
		generalHBox1HBox.setLayoutData(constraint);

		// create sub figures
		createIsActiveCheckbox(generalHBox1HBox);
		createIsIndirectlyInstantiatedCheckbox(generalHBox1HBox);
		createIsAbstractCheckbox(generalHBox1HBox);
		createIsLeafCheckbox(generalHBox1HBox);

		parent.pack();
	}

	protected void createIsActiveCheckbox(Composite parent) {
		isActive = new Button(parent, SWT.CHECK);
		isActive.setText(UMLMessages.ComponentPropertiesEditionPart_IsActiveLabel);
		GridData isActiveData = new GridData(GridData.FILL_HORIZONTAL);
		isActiveData.horizontalSpan = 2;
		isActive.setLayoutData(isActiveData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.isActive, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createIsIndirectlyInstantiatedCheckbox(Composite parent) {
		isIndirectlyInstantiated = new Button(parent, SWT.CHECK);
		isIndirectlyInstantiated.setText(UMLMessages.ComponentPropertiesEditionPart_IsIndirectlyInstantiatedLabel);
		GridData isIndirectlyInstantiatedData = new GridData(GridData.FILL_HORIZONTAL);
		isIndirectlyInstantiatedData.horizontalSpan = 2;
		isIndirectlyInstantiated.setLayoutData(isIndirectlyInstantiatedData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.isIndirectlyInstantiated, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createIsAbstractCheckbox(Composite parent) {
		isAbstract = new Button(parent, SWT.CHECK);
		isAbstract.setText(UMLMessages.ComponentPropertiesEditionPart_IsAbstractLabel);
		GridData isAbstractData = new GridData(GridData.FILL_HORIZONTAL);
		isAbstractData.horizontalSpan = 2;
		isAbstract.setLayoutData(isAbstractData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.isAbstract, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createIsLeafCheckbox(Composite parent) {
		isLeaf = new Button(parent, SWT.CHECK);
		isLeaf.setText(UMLMessages.ComponentPropertiesEditionPart_IsLeafLabel);
		GridData isLeafData = new GridData(GridData.FILL_HORIZONTAL);
		isLeafData.horizontalSpan = 2;
		isLeaf.setLayoutData(isLeafData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.isLeaf, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createComponentSpecificationsGroup(Composite parent) {
		Group componentSpecificationsGroup = new Group(parent, SWT.NONE);
		componentSpecificationsGroup
				.setText(UMLMessages.ComponentPropertiesEditionPart_ComponentSpecificationsGroupLabel);
		GridData componentSpecificationsGroupData = new GridData(GridData.FILL_HORIZONTAL);
		componentSpecificationsGroupData.horizontalSpan = 3;
		componentSpecificationsGroup.setLayoutData(componentSpecificationsGroupData);
		GridLayout componentSpecificationsGroupLayout = new GridLayout();
		componentSpecificationsGroupLayout.numColumns = 3;
		componentSpecificationsGroup.setLayout(componentSpecificationsGroupLayout);
		createComponentHBox1HBox(componentSpecificationsGroup);
	}

	protected void createComponentHBox1HBox(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		container.setLayoutData(gridData);
		HorizontalBox componentHBox1HBox = new HorizontalBox(container);

		// create sub figures
		createOwnedAttributeAdvancedTableComposition(componentHBox1HBox);
		createOwnedOperationAdvancedTableComposition(componentHBox1HBox);
		createNestedClassifierAdvancedTableComposition(componentHBox1HBox);

		parent.pack();
	}

	/**
	 * @param container
	 */
	protected void createOwnedAttributeAdvancedTableComposition(Composite parent) {
		this.ownedAttribute = new ReferencesTable<Property>(
				UMLMessages.ComponentPropertiesEditionPart_OwnedAttributeLabel,
				new ReferencesTableListener<Property>() {

					public void handleAdd() {
						addToOwnedAttribute();
					}

					public void handleEdit(Property element) {
						editOwnedAttribute(element);
					}

					public void handleMove(Property element, int oldIndex, int newIndex) {
						moveOwnedAttribute(element, oldIndex, newIndex);
					}

					public void handleRemove(Property element) {
						removeFromOwnedAttribute(element);
					}

					public void navigateTo(Property element) {
					}
				});
		this.ownedAttribute.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.ownedAttribute, UMLViewsRepository.SWT_KIND));
		this.ownedAttribute.createControls(parent);
		GridData ownedAttributeData = new GridData(GridData.FILL_HORIZONTAL);
		ownedAttributeData.horizontalSpan = 3;
		this.ownedAttribute.setLayoutData(ownedAttributeData);
	}

	/**
	 * 
	 */
	private void moveOwnedAttribute(Property element, int oldIndex, int newIndex) {

		EObject editedElement = ownedAttributeEditUtil.foundCorrespondingEObject(element);
		ownedAttributeEditUtil.moveElement(element, oldIndex, newIndex);
		ownedAttribute.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedAttribute,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToOwnedAttribute() {

		// Start of user code addToOwnedAttribute() method body

		Property eObject = UMLFactory.eINSTANCE.createProperty();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				ownedAttributeEditUtil.addElement(propertiesEditionObject);
				ownedAttribute.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedAttribute,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromOwnedAttribute(Property element) {

		// Start of user code removeFromOwnedAttribute() method body

		EObject editedElement = ownedAttributeEditUtil.foundCorrespondingEObject(element);
		ownedAttributeEditUtil.removeElement(element);
		ownedAttribute.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedAttribute,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editOwnedAttribute(Property element) {

		// Start of user code editOwnedAttribute() method body

		EObject editedElement = ownedAttributeEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedAttributeEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedAttribute.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedAttribute,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createOwnedOperationAdvancedTableComposition(Composite parent) {
		this.ownedOperation = new ReferencesTable<Operation>(
				UMLMessages.ComponentPropertiesEditionPart_OwnedOperationLabel,
				new ReferencesTableListener<Operation>() {

					public void handleAdd() {
						addToOwnedOperation();
					}

					public void handleEdit(Operation element) {
						editOwnedOperation(element);
					}

					public void handleMove(Operation element, int oldIndex, int newIndex) {
						moveOwnedOperation(element, oldIndex, newIndex);
					}

					public void handleRemove(Operation element) {
						removeFromOwnedOperation(element);
					}

					public void navigateTo(Operation element) {
					}
				});
		this.ownedOperation.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.ownedOperation, UMLViewsRepository.SWT_KIND));
		this.ownedOperation.createControls(parent);
		GridData ownedOperationData = new GridData(GridData.FILL_HORIZONTAL);
		ownedOperationData.horizontalSpan = 3;
		this.ownedOperation.setLayoutData(ownedOperationData);
	}

	/**
	 * 
	 */
	private void moveOwnedOperation(Operation element, int oldIndex, int newIndex) {

		EObject editedElement = ownedOperationEditUtil.foundCorrespondingEObject(element);
		ownedOperationEditUtil.moveElement(element, oldIndex, newIndex);
		ownedOperation.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedOperation,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToOwnedOperation() {

		// Start of user code addToOwnedOperation() method body

		Operation eObject = UMLFactory.eINSTANCE.createOperation();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				ownedOperationEditUtil.addElement(propertiesEditionObject);
				ownedOperation.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedOperation,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromOwnedOperation(Operation element) {

		// Start of user code removeFromOwnedOperation() method body

		EObject editedElement = ownedOperationEditUtil.foundCorrespondingEObject(element);
		ownedOperationEditUtil.removeElement(element);
		ownedOperation.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedOperation,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editOwnedOperation(Operation element) {

		// Start of user code editOwnedOperation() method body

		EObject editedElement = ownedOperationEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedOperationEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedOperation.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedOperation,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createNestedClassifierAdvancedTableComposition(Composite parent) {
		this.nestedClassifier = new ReferencesTable<Classifier>(
				UMLMessages.ComponentPropertiesEditionPart_NestedClassifierLabel,
				new ReferencesTableListener<Classifier>() {

					public void handleAdd() {
						addToNestedClassifier();
					}

					public void handleEdit(Classifier element) {
						editNestedClassifier(element);
					}

					public void handleMove(Classifier element, int oldIndex, int newIndex) {
						moveNestedClassifier(element, oldIndex, newIndex);
					}

					public void handleRemove(Classifier element) {
						removeFromNestedClassifier(element);
					}

					public void navigateTo(Classifier element) {
					}
				});
		this.nestedClassifier.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.nestedClassifier, UMLViewsRepository.SWT_KIND));
		this.nestedClassifier.createControls(parent);
		GridData nestedClassifierData = new GridData(GridData.FILL_HORIZONTAL);
		nestedClassifierData.horizontalSpan = 3;
		this.nestedClassifier.setLayoutData(nestedClassifierData);
	}

	/**
	 * 
	 */
	private void moveNestedClassifier(Classifier element, int oldIndex, int newIndex) {
	}

	/**
	 * 
	 */
	protected void addToNestedClassifier() {

		// Start of user code addToNestedClassifier() method body

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromNestedClassifier(Classifier element) {

		// Start of user code removeFromNestedClassifier() method body

		EObject editedElement = nestedClassifierEditUtil.foundCorrespondingEObject(element);
		nestedClassifierEditUtil.removeElement(element);
		nestedClassifier.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.nestedClassifier,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editNestedClassifier(Classifier element) {

		// Start of user code editNestedClassifier() method body

		EObject editedElement = nestedClassifierEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				nestedClassifierEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				nestedClassifier.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.nestedClassifier,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createClassifierSpecificationsGroup(Composite parent) {
		Group classifierSpecificationsGroup = new Group(parent, SWT.NONE);
		classifierSpecificationsGroup
				.setText(UMLMessages.ComponentPropertiesEditionPart_ClassifierSpecificationsGroupLabel);
		GridData classifierSpecificationsGroupData = new GridData(GridData.FILL_HORIZONTAL);
		classifierSpecificationsGroupData.horizontalSpan = 3;
		classifierSpecificationsGroup.setLayoutData(classifierSpecificationsGroupData);
		GridLayout classifierSpecificationsGroupLayout = new GridLayout();
		classifierSpecificationsGroupLayout.numColumns = 3;
		classifierSpecificationsGroup.setLayout(classifierSpecificationsGroupLayout);
		createClassifierHBox1HBox(classifierSpecificationsGroup);
	}

	protected void createClassifierHBox1HBox(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		container.setLayoutData(gridData);
		HorizontalBox classifierHBox1HBox = new HorizontalBox(container);

		// create sub figures
		createRealizationAdvancedTableComposition(classifierHBox1HBox);
		createInterfaceRealizationAdvancedTableComposition(classifierHBox1HBox);
		createGeneralizationAdvancedTableComposition(classifierHBox1HBox);

		parent.pack();
	}

	/**
	 * @param container
	 */
	protected void createRealizationAdvancedTableComposition(Composite parent) {
		this.realization = new ReferencesTable<ComponentRealization>(
				UMLMessages.ComponentPropertiesEditionPart_RealizationLabel,
				new ReferencesTableListener<ComponentRealization>() {

					public void handleAdd() {
						addToRealization();
					}

					public void handleEdit(ComponentRealization element) {
						editRealization(element);
					}

					public void handleMove(ComponentRealization element, int oldIndex, int newIndex) {
						moveRealization(element, oldIndex, newIndex);
					}

					public void handleRemove(ComponentRealization element) {
						removeFromRealization(element);
					}

					public void navigateTo(ComponentRealization element) {
					}
				});
		this.realization.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.realization, UMLViewsRepository.SWT_KIND));
		this.realization.createControls(parent);
		GridData realizationData = new GridData(GridData.FILL_HORIZONTAL);
		realizationData.horizontalSpan = 3;
		this.realization.setLayoutData(realizationData);
	}

	/**
	 * 
	 */
	private void moveRealization(ComponentRealization element, int oldIndex, int newIndex) {

		EObject editedElement = realizationEditUtil.foundCorrespondingEObject(element);
		realizationEditUtil.moveElement(element, oldIndex, newIndex);
		realization.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.realization,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToRealization() {

		// Start of user code addToRealization() method body

		ComponentRealization eObject = UMLFactory.eINSTANCE.createComponentRealization();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				realizationEditUtil.addElement(propertiesEditionObject);
				realization.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.realization,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromRealization(ComponentRealization element) {

		// Start of user code removeFromRealization() method body

		EObject editedElement = realizationEditUtil.foundCorrespondingEObject(element);
		realizationEditUtil.removeElement(element);
		realization.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.realization,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editRealization(ComponentRealization element) {

		// Start of user code editRealization() method body

		EObject editedElement = realizationEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				realizationEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				realization.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.realization,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createInterfaceRealizationAdvancedTableComposition(Composite parent) {
		this.interfaceRealization = new ReferencesTable<InterfaceRealization>(
				UMLMessages.ComponentPropertiesEditionPart_InterfaceRealizationLabel,
				new ReferencesTableListener<InterfaceRealization>() {

					public void handleAdd() {
						addToInterfaceRealization();
					}

					public void handleEdit(InterfaceRealization element) {
						editInterfaceRealization(element);
					}

					public void handleMove(InterfaceRealization element, int oldIndex, int newIndex) {
						moveInterfaceRealization(element, oldIndex, newIndex);
					}

					public void handleRemove(InterfaceRealization element) {
						removeFromInterfaceRealization(element);
					}

					public void navigateTo(InterfaceRealization element) {
					}
				});
		this.interfaceRealization.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.interfaceRealization, UMLViewsRepository.SWT_KIND));
		this.interfaceRealization.createControls(parent);
		GridData interfaceRealizationData = new GridData(GridData.FILL_HORIZONTAL);
		interfaceRealizationData.horizontalSpan = 3;
		this.interfaceRealization.setLayoutData(interfaceRealizationData);
	}

	/**
	 * 
	 */
	private void moveInterfaceRealization(InterfaceRealization element, int oldIndex, int newIndex) {

		EObject editedElement = interfaceRealizationEditUtil.foundCorrespondingEObject(element);
		interfaceRealizationEditUtil.moveElement(element, oldIndex, newIndex);
		interfaceRealization.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.interfaceRealization,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToInterfaceRealization() {

		// Start of user code addToInterfaceRealization() method body

		InterfaceRealization eObject = UMLFactory.eINSTANCE.createInterfaceRealization();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				interfaceRealizationEditUtil.addElement(propertiesEditionObject);
				interfaceRealization.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.interfaceRealization,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromInterfaceRealization(InterfaceRealization element) {

		// Start of user code removeFromInterfaceRealization() method body

		EObject editedElement = interfaceRealizationEditUtil.foundCorrespondingEObject(element);
		interfaceRealizationEditUtil.removeElement(element);
		interfaceRealization.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.interfaceRealization,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editInterfaceRealization(InterfaceRealization element) {

		// Start of user code editInterfaceRealization() method body

		EObject editedElement = interfaceRealizationEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				interfaceRealizationEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				interfaceRealization.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.interfaceRealization,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createGeneralizationAdvancedTableComposition(Composite parent) {
		this.generalization = new ReferencesTable<Generalization>(
				UMLMessages.ComponentPropertiesEditionPart_GeneralizationLabel,
				new ReferencesTableListener<Generalization>() {

					public void handleAdd() {
						addToGeneralization();
					}

					public void handleEdit(Generalization element) {
						editGeneralization(element);
					}

					public void handleMove(Generalization element, int oldIndex, int newIndex) {
						moveGeneralization(element, oldIndex, newIndex);
					}

					public void handleRemove(Generalization element) {
						removeFromGeneralization(element);
					}

					public void navigateTo(Generalization element) {
					}
				});
		this.generalization.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.generalization, UMLViewsRepository.SWT_KIND));
		this.generalization.createControls(parent);
		GridData generalizationData = new GridData(GridData.FILL_HORIZONTAL);
		generalizationData.horizontalSpan = 3;
		this.generalization.setLayoutData(generalizationData);
	}

	/**
	 * 
	 */
	private void moveGeneralization(Generalization element, int oldIndex, int newIndex) {

		EObject editedElement = generalizationEditUtil.foundCorrespondingEObject(element);
		generalizationEditUtil.moveElement(element, oldIndex, newIndex);
		generalization.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.generalization,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToGeneralization() {

		// Start of user code addToGeneralization() method body

		Generalization eObject = UMLFactory.eINSTANCE.createGeneralization();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				generalizationEditUtil.addElement(propertiesEditionObject);
				generalization.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.generalization,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromGeneralization(Generalization element) {

		// Start of user code removeFromGeneralization() method body

		EObject editedElement = generalizationEditUtil.foundCorrespondingEObject(element);
		generalizationEditUtil.removeElement(element);
		generalization.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.generalization,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editGeneralization(Generalization element) {

		// Start of user code editGeneralization() method body

		EObject editedElement = generalizationEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				generalizationEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				generalization.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.generalization,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createOthersGroup(Composite parent) {
		Group othersGroup = new Group(parent, SWT.NONE);
		othersGroup.setText(UMLMessages.ComponentPropertiesEditionPart_OthersGroupLabel);
		GridData othersGroupData = new GridData(GridData.FILL_HORIZONTAL);
		othersGroupData.horizontalSpan = 3;
		othersGroup.setLayoutData(othersGroupData);
		GridLayout othersGroupLayout = new GridLayout();
		othersGroupLayout.numColumns = 3;
		othersGroup.setLayout(othersGroupLayout);
		createElementImportAdvancedTableComposition(othersGroup);
		createOthersHBox1HBox(othersGroup);
	}

	/**
	 * @param container
	 */
	protected void createElementImportAdvancedTableComposition(Composite parent) {
		this.elementImport = new ReferencesTable<ElementImport>(
				UMLMessages.ComponentPropertiesEditionPart_ElementImportLabel,
				new ReferencesTableListener<ElementImport>() {

					public void handleAdd() {
						addToElementImport();
					}

					public void handleEdit(ElementImport element) {
						editElementImport(element);
					}

					public void handleMove(ElementImport element, int oldIndex, int newIndex) {
						moveElementImport(element, oldIndex, newIndex);
					}

					public void handleRemove(ElementImport element) {
						removeFromElementImport(element);
					}

					public void navigateTo(ElementImport element) {
					}
				});
		this.elementImport.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.elementImport, UMLViewsRepository.SWT_KIND));
		this.elementImport.createControls(parent);
		GridData elementImportData = new GridData(GridData.FILL_HORIZONTAL);
		elementImportData.horizontalSpan = 3;
		this.elementImport.setLayoutData(elementImportData);
	}

	/**
	 * 
	 */
	private void moveElementImport(ElementImport element, int oldIndex, int newIndex) {

		EObject editedElement = elementImportEditUtil.foundCorrespondingEObject(element);
		elementImportEditUtil.moveElement(element, oldIndex, newIndex);
		elementImport.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.elementImport,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToElementImport() {

		// Start of user code addToElementImport() method body

		ElementImport eObject = UMLFactory.eINSTANCE.createElementImport();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				elementImportEditUtil.addElement(propertiesEditionObject);
				elementImport.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.elementImport,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromElementImport(ElementImport element) {

		// Start of user code removeFromElementImport() method body

		EObject editedElement = elementImportEditUtil.foundCorrespondingEObject(element);
		elementImportEditUtil.removeElement(element);
		elementImport.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.elementImport,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editElementImport(ElementImport element) {

		// Start of user code editElementImport() method body

		EObject editedElement = elementImportEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				elementImportEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				elementImport.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.elementImport,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createOthersHBox1HBox(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		container.setLayoutData(gridData);
		HorizontalBox othersHBox1HBox = new HorizontalBox(container);

		// create sub figures
		createOwnedUseCaseAdvancedTableComposition(othersHBox1HBox);
		createPackagedElementAdvancedTableComposition(othersHBox1HBox);
		createOwnedReceptionAdvancedTableComposition(othersHBox1HBox);
		createOwnedTriggerAdvancedTableComposition(othersHBox1HBox);
		createOwnedBehaviorAdvancedTableComposition(othersHBox1HBox);
		createOwnedConnectorAdvancedTableComposition(othersHBox1HBox);
		createCollaborationUseAdvancedTableComposition(othersHBox1HBox);
		createUseCaseAdvancedReferencesTable(othersHBox1HBox);
		createSubstitutionAdvancedTableComposition(othersHBox1HBox);
		createPowertypeExtentAdvancedReferencesTable(othersHBox1HBox);
		createRedefinedClassifierAdvancedReferencesTable(othersHBox1HBox);
		createTemplateBindingAdvancedTableComposition(othersHBox1HBox);
		createOwnedRuleAdvancedTableComposition(othersHBox1HBox);
		createPackageImportAdvancedTableComposition(othersHBox1HBox);

		parent.pack();
	}

	/**
	 * @param container
	 */
	protected void createOwnedUseCaseAdvancedTableComposition(Composite parent) {
		this.ownedUseCase = new ReferencesTable<UseCase>(UMLMessages.ComponentPropertiesEditionPart_OwnedUseCaseLabel,
				new ReferencesTableListener<UseCase>() {

					public void handleAdd() {
						addToOwnedUseCase();
					}

					public void handleEdit(UseCase element) {
						editOwnedUseCase(element);
					}

					public void handleMove(UseCase element, int oldIndex, int newIndex) {
						moveOwnedUseCase(element, oldIndex, newIndex);
					}

					public void handleRemove(UseCase element) {
						removeFromOwnedUseCase(element);
					}

					public void navigateTo(UseCase element) {
					}
				});
		this.ownedUseCase.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.ownedUseCase, UMLViewsRepository.SWT_KIND));
		this.ownedUseCase.createControls(parent);
		GridData ownedUseCaseData = new GridData(GridData.FILL_HORIZONTAL);
		ownedUseCaseData.horizontalSpan = 3;
		this.ownedUseCase.setLayoutData(ownedUseCaseData);
	}

	/**
	 * 
	 */
	private void moveOwnedUseCase(UseCase element, int oldIndex, int newIndex) {

		EObject editedElement = ownedUseCaseEditUtil.foundCorrespondingEObject(element);
		ownedUseCaseEditUtil.moveElement(element, oldIndex, newIndex);
		ownedUseCase.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedUseCase,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToOwnedUseCase() {

		// Start of user code addToOwnedUseCase() method body

		UseCase eObject = UMLFactory.eINSTANCE.createUseCase();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				ownedUseCaseEditUtil.addElement(propertiesEditionObject);
				ownedUseCase.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedUseCase,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromOwnedUseCase(UseCase element) {

		// Start of user code removeFromOwnedUseCase() method body

		EObject editedElement = ownedUseCaseEditUtil.foundCorrespondingEObject(element);
		ownedUseCaseEditUtil.removeElement(element);
		ownedUseCase.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedUseCase,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editOwnedUseCase(UseCase element) {

		// Start of user code editOwnedUseCase() method body

		EObject editedElement = ownedUseCaseEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedUseCaseEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedUseCase.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedUseCase,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createPackagedElementAdvancedTableComposition(Composite parent) {
		this.packagedElement = new ReferencesTable<PackageableElement>(
				UMLMessages.ComponentPropertiesEditionPart_PackagedElementLabel,
				new ReferencesTableListener<PackageableElement>() {

					public void handleAdd() {
						addToPackagedElement();
					}

					public void handleEdit(PackageableElement element) {
						editPackagedElement(element);
					}

					public void handleMove(PackageableElement element, int oldIndex, int newIndex) {
						movePackagedElement(element, oldIndex, newIndex);
					}

					public void handleRemove(PackageableElement element) {
						removeFromPackagedElement(element);
					}

					public void navigateTo(PackageableElement element) {
					}
				});
		this.packagedElement.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.packagedElement, UMLViewsRepository.SWT_KIND));
		this.packagedElement.createControls(parent);
		GridData packagedElementData = new GridData(GridData.FILL_HORIZONTAL);
		packagedElementData.horizontalSpan = 3;
		this.packagedElement.setLayoutData(packagedElementData);
	}

	/**
	 * 
	 */
	private void movePackagedElement(PackageableElement element, int oldIndex, int newIndex) {
	}

	/**
	 * 
	 */
	protected void addToPackagedElement() {

		// Start of user code addToPackagedElement() method body

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromPackagedElement(PackageableElement element) {

		// Start of user code removeFromPackagedElement() method body

		EObject editedElement = packagedElementEditUtil.foundCorrespondingEObject(element);
		packagedElementEditUtil.removeElement(element);
		packagedElement.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.packagedElement,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editPackagedElement(PackageableElement element) {

		// Start of user code editPackagedElement() method body

		EObject editedElement = packagedElementEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				packagedElementEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				packagedElement.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.packagedElement,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createOwnedReceptionAdvancedTableComposition(Composite parent) {
		this.ownedReception = new ReferencesTable<Reception>(
				UMLMessages.ComponentPropertiesEditionPart_OwnedReceptionLabel,
				new ReferencesTableListener<Reception>() {

					public void handleAdd() {
						addToOwnedReception();
					}

					public void handleEdit(Reception element) {
						editOwnedReception(element);
					}

					public void handleMove(Reception element, int oldIndex, int newIndex) {
						moveOwnedReception(element, oldIndex, newIndex);
					}

					public void handleRemove(Reception element) {
						removeFromOwnedReception(element);
					}

					public void navigateTo(Reception element) {
					}
				});
		this.ownedReception.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.ownedReception, UMLViewsRepository.SWT_KIND));
		this.ownedReception.createControls(parent);
		GridData ownedReceptionData = new GridData(GridData.FILL_HORIZONTAL);
		ownedReceptionData.horizontalSpan = 3;
		this.ownedReception.setLayoutData(ownedReceptionData);
	}

	/**
	 * 
	 */
	private void moveOwnedReception(Reception element, int oldIndex, int newIndex) {

		EObject editedElement = ownedReceptionEditUtil.foundCorrespondingEObject(element);
		ownedReceptionEditUtil.moveElement(element, oldIndex, newIndex);
		ownedReception.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedReception,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToOwnedReception() {

		// Start of user code addToOwnedReception() method body

		Reception eObject = UMLFactory.eINSTANCE.createReception();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				ownedReceptionEditUtil.addElement(propertiesEditionObject);
				ownedReception.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedReception,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromOwnedReception(Reception element) {

		// Start of user code removeFromOwnedReception() method body

		EObject editedElement = ownedReceptionEditUtil.foundCorrespondingEObject(element);
		ownedReceptionEditUtil.removeElement(element);
		ownedReception.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedReception,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editOwnedReception(Reception element) {

		// Start of user code editOwnedReception() method body

		EObject editedElement = ownedReceptionEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedReceptionEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedReception.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedReception,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createOwnedTriggerAdvancedTableComposition(Composite parent) {
		this.ownedTrigger = new ReferencesTable<Trigger>(UMLMessages.ComponentPropertiesEditionPart_OwnedTriggerLabel,
				new ReferencesTableListener<Trigger>() {

					public void handleAdd() {
						addToOwnedTrigger();
					}

					public void handleEdit(Trigger element) {
						editOwnedTrigger(element);
					}

					public void handleMove(Trigger element, int oldIndex, int newIndex) {
						moveOwnedTrigger(element, oldIndex, newIndex);
					}

					public void handleRemove(Trigger element) {
						removeFromOwnedTrigger(element);
					}

					public void navigateTo(Trigger element) {
					}
				});
		this.ownedTrigger.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.ownedTrigger, UMLViewsRepository.SWT_KIND));
		this.ownedTrigger.createControls(parent);
		GridData ownedTriggerData = new GridData(GridData.FILL_HORIZONTAL);
		ownedTriggerData.horizontalSpan = 3;
		this.ownedTrigger.setLayoutData(ownedTriggerData);
	}

	/**
	 * 
	 */
	private void moveOwnedTrigger(Trigger element, int oldIndex, int newIndex) {

		EObject editedElement = ownedTriggerEditUtil.foundCorrespondingEObject(element);
		ownedTriggerEditUtil.moveElement(element, oldIndex, newIndex);
		ownedTrigger.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedTrigger,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToOwnedTrigger() {

		// Start of user code addToOwnedTrigger() method body

		Trigger eObject = UMLFactory.eINSTANCE.createTrigger();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				ownedTriggerEditUtil.addElement(propertiesEditionObject);
				ownedTrigger.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedTrigger,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromOwnedTrigger(Trigger element) {

		// Start of user code removeFromOwnedTrigger() method body

		EObject editedElement = ownedTriggerEditUtil.foundCorrespondingEObject(element);
		ownedTriggerEditUtil.removeElement(element);
		ownedTrigger.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedTrigger,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editOwnedTrigger(Trigger element) {

		// Start of user code editOwnedTrigger() method body

		EObject editedElement = ownedTriggerEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedTriggerEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedTrigger.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedTrigger,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createOwnedBehaviorAdvancedTableComposition(Composite parent) {
		this.ownedBehavior = new ReferencesTable<Behavior>(
				UMLMessages.ComponentPropertiesEditionPart_OwnedBehaviorLabel, new ReferencesTableListener<Behavior>() {

					public void handleAdd() {
						addToOwnedBehavior();
					}

					public void handleEdit(Behavior element) {
						editOwnedBehavior(element);
					}

					public void handleMove(Behavior element, int oldIndex, int newIndex) {
						moveOwnedBehavior(element, oldIndex, newIndex);
					}

					public void handleRemove(Behavior element) {
						removeFromOwnedBehavior(element);
					}

					public void navigateTo(Behavior element) {
					}
				});
		this.ownedBehavior.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.ownedBehavior, UMLViewsRepository.SWT_KIND));
		this.ownedBehavior.createControls(parent);
		GridData ownedBehaviorData = new GridData(GridData.FILL_HORIZONTAL);
		ownedBehaviorData.horizontalSpan = 3;
		this.ownedBehavior.setLayoutData(ownedBehaviorData);
	}

	/**
	 * 
	 */
	private void moveOwnedBehavior(Behavior element, int oldIndex, int newIndex) {
	}

	/**
	 * 
	 */
	protected void addToOwnedBehavior() {

		// Start of user code addToOwnedBehavior() method body

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromOwnedBehavior(Behavior element) {

		// Start of user code removeFromOwnedBehavior() method body

		EObject editedElement = ownedBehaviorEditUtil.foundCorrespondingEObject(element);
		ownedBehaviorEditUtil.removeElement(element);
		ownedBehavior.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedBehavior,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editOwnedBehavior(Behavior element) {

		// Start of user code editOwnedBehavior() method body

		EObject editedElement = ownedBehaviorEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedBehaviorEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedBehavior.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedBehavior,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createOwnedConnectorAdvancedTableComposition(Composite parent) {
		this.ownedConnector = new ReferencesTable<Connector>(
				UMLMessages.ComponentPropertiesEditionPart_OwnedConnectorLabel,
				new ReferencesTableListener<Connector>() {

					public void handleAdd() {
						addToOwnedConnector();
					}

					public void handleEdit(Connector element) {
						editOwnedConnector(element);
					}

					public void handleMove(Connector element, int oldIndex, int newIndex) {
						moveOwnedConnector(element, oldIndex, newIndex);
					}

					public void handleRemove(Connector element) {
						removeFromOwnedConnector(element);
					}

					public void navigateTo(Connector element) {
					}
				});
		this.ownedConnector.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.ownedConnector, UMLViewsRepository.SWT_KIND));
		this.ownedConnector.createControls(parent);
		GridData ownedConnectorData = new GridData(GridData.FILL_HORIZONTAL);
		ownedConnectorData.horizontalSpan = 3;
		this.ownedConnector.setLayoutData(ownedConnectorData);
	}

	/**
	 * 
	 */
	private void moveOwnedConnector(Connector element, int oldIndex, int newIndex) {

		EObject editedElement = ownedConnectorEditUtil.foundCorrespondingEObject(element);
		ownedConnectorEditUtil.moveElement(element, oldIndex, newIndex);
		ownedConnector.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedConnector,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToOwnedConnector() {

		// Start of user code addToOwnedConnector() method body

		Connector eObject = UMLFactory.eINSTANCE.createConnector();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				ownedConnectorEditUtil.addElement(propertiesEditionObject);
				ownedConnector.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedConnector,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromOwnedConnector(Connector element) {

		// Start of user code removeFromOwnedConnector() method body

		EObject editedElement = ownedConnectorEditUtil.foundCorrespondingEObject(element);
		ownedConnectorEditUtil.removeElement(element);
		ownedConnector.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedConnector,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editOwnedConnector(Connector element) {

		// Start of user code editOwnedConnector() method body

		EObject editedElement = ownedConnectorEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedConnectorEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedConnector.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedConnector,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createCollaborationUseAdvancedTableComposition(Composite parent) {
		this.collaborationUse = new ReferencesTable<CollaborationUse>(
				UMLMessages.ComponentPropertiesEditionPart_CollaborationUseLabel,
				new ReferencesTableListener<CollaborationUse>() {

					public void handleAdd() {
						addToCollaborationUse();
					}

					public void handleEdit(CollaborationUse element) {
						editCollaborationUse(element);
					}

					public void handleMove(CollaborationUse element, int oldIndex, int newIndex) {
						moveCollaborationUse(element, oldIndex, newIndex);
					}

					public void handleRemove(CollaborationUse element) {
						removeFromCollaborationUse(element);
					}

					public void navigateTo(CollaborationUse element) {
					}
				});
		this.collaborationUse.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.collaborationUse, UMLViewsRepository.SWT_KIND));
		this.collaborationUse.createControls(parent);
		GridData collaborationUseData = new GridData(GridData.FILL_HORIZONTAL);
		collaborationUseData.horizontalSpan = 3;
		this.collaborationUse.setLayoutData(collaborationUseData);
	}

	/**
	 * 
	 */
	private void moveCollaborationUse(CollaborationUse element, int oldIndex, int newIndex) {

		EObject editedElement = collaborationUseEditUtil.foundCorrespondingEObject(element);
		collaborationUseEditUtil.moveElement(element, oldIndex, newIndex);
		collaborationUse.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.collaborationUse,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToCollaborationUse() {

		// Start of user code addToCollaborationUse() method body

		CollaborationUse eObject = UMLFactory.eINSTANCE.createCollaborationUse();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				collaborationUseEditUtil.addElement(propertiesEditionObject);
				collaborationUse.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.collaborationUse,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromCollaborationUse(CollaborationUse element) {

		// Start of user code removeFromCollaborationUse() method body

		EObject editedElement = collaborationUseEditUtil.foundCorrespondingEObject(element);
		collaborationUseEditUtil.removeElement(element);
		collaborationUse.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.collaborationUse,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editCollaborationUse(CollaborationUse element) {

		// Start of user code editCollaborationUse() method body

		EObject editedElement = collaborationUseEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				collaborationUseEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				collaborationUse.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.collaborationUse,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createUseCaseAdvancedReferencesTable(Composite parent) {
		this.useCase = new ReferencesTable<UseCase>(UMLMessages.ComponentPropertiesEditionPart_UseCaseLabel,
				new ReferencesTableListener<UseCase>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<UseCase> dialog = new TabElementTreeSelectionDialog<UseCase>(
								resourceSet, useCaseFilters, useCaseBusinessFilters, "UseCase", UMLPackage.eINSTANCE
										.getUseCase()) {

							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!useCaseEditUtil.getVirtualList().contains(elem))
										useCaseEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											ComponentPropertiesEditionPartImpl.this,
											UMLViewsRepository.Component.useCase, PropertiesEditionEvent.COMMIT,
											PropertiesEditionEvent.ADD, null, elem));
								}
								useCase.refresh();
							}

						};
						dialog.open();
					}

					public void handleEdit(UseCase element) {
						editUseCase(element);
					}

					public void handleMove(UseCase element, int oldIndex, int newIndex) {
						moveUseCase(element, oldIndex, newIndex);
					}

					public void handleRemove(UseCase element) {
						removeFromUseCase(element);
					}

					public void navigateTo(UseCase element) {
					}
				});
		this.useCase.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Component.useCase,
				UMLViewsRepository.SWT_KIND));
		this.useCase.createControls(parent);
		GridData useCaseData = new GridData(GridData.FILL_HORIZONTAL);
		useCaseData.horizontalSpan = 3;
		this.useCase.setLayoutData(useCaseData);
		this.useCase.disableMove();
	}

	/**
	 * 
	 */
	private void moveUseCase(UseCase element, int oldIndex, int newIndex) {
		EObject editedElement = useCaseEditUtil.foundCorrespondingEObject(element);
		useCaseEditUtil.moveElement(element, oldIndex, newIndex);
		useCase.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.useCase,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	private void removeFromUseCase(UseCase element) {

		// Start of user code removeFromUseCase() method body

		EObject editedElement = useCaseEditUtil.foundCorrespondingEObject(element);
		useCaseEditUtil.removeElement(element);
		useCase.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.useCase,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editUseCase(UseCase element) {

		// Start of user code editUseCase() method body

		EObject editedElement = useCaseEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				useCaseEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				useCase.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.useCase,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createSubstitutionAdvancedTableComposition(Composite parent) {
		this.substitution = new ReferencesTable<Substitution>(
				UMLMessages.ComponentPropertiesEditionPart_SubstitutionLabel,
				new ReferencesTableListener<Substitution>() {

					public void handleAdd() {
						addToSubstitution();
					}

					public void handleEdit(Substitution element) {
						editSubstitution(element);
					}

					public void handleMove(Substitution element, int oldIndex, int newIndex) {
						moveSubstitution(element, oldIndex, newIndex);
					}

					public void handleRemove(Substitution element) {
						removeFromSubstitution(element);
					}

					public void navigateTo(Substitution element) {
					}
				});
		this.substitution.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.substitution, UMLViewsRepository.SWT_KIND));
		this.substitution.createControls(parent);
		GridData substitutionData = new GridData(GridData.FILL_HORIZONTAL);
		substitutionData.horizontalSpan = 3;
		this.substitution.setLayoutData(substitutionData);
	}

	/**
	 * 
	 */
	private void moveSubstitution(Substitution element, int oldIndex, int newIndex) {

		EObject editedElement = substitutionEditUtil.foundCorrespondingEObject(element);
		substitutionEditUtil.moveElement(element, oldIndex, newIndex);
		substitution.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.substitution,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToSubstitution() {

		// Start of user code addToSubstitution() method body

		Substitution eObject = UMLFactory.eINSTANCE.createSubstitution();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				substitutionEditUtil.addElement(propertiesEditionObject);
				substitution.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.substitution,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromSubstitution(Substitution element) {

		// Start of user code removeFromSubstitution() method body

		EObject editedElement = substitutionEditUtil.foundCorrespondingEObject(element);
		substitutionEditUtil.removeElement(element);
		substitution.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.substitution,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editSubstitution(Substitution element) {

		// Start of user code editSubstitution() method body

		EObject editedElement = substitutionEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				substitutionEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				substitution.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.substitution,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createPowertypeExtentAdvancedReferencesTable(Composite parent) {
		this.powertypeExtent = new ReferencesTable<GeneralizationSet>(
				UMLMessages.ComponentPropertiesEditionPart_PowertypeExtentLabel,
				new ReferencesTableListener<GeneralizationSet>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<GeneralizationSet> dialog = new TabElementTreeSelectionDialog<GeneralizationSet>(
								resourceSet, powertypeExtentFilters, powertypeExtentBusinessFilters,
								"GeneralizationSet", UMLPackage.eINSTANCE.getGeneralizationSet()) {

							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!powertypeExtentEditUtil.getVirtualList().contains(elem))
										powertypeExtentEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											ComponentPropertiesEditionPartImpl.this,
											UMLViewsRepository.Component.powertypeExtent,
											PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
								}
								powertypeExtent.refresh();
							}

						};
						dialog.open();
					}

					public void handleEdit(GeneralizationSet element) {
						editPowertypeExtent(element);
					}

					public void handleMove(GeneralizationSet element, int oldIndex, int newIndex) {
						movePowertypeExtent(element, oldIndex, newIndex);
					}

					public void handleRemove(GeneralizationSet element) {
						removeFromPowertypeExtent(element);
					}

					public void navigateTo(GeneralizationSet element) {
					}
				});
		this.powertypeExtent.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.powertypeExtent, UMLViewsRepository.SWT_KIND));
		this.powertypeExtent.createControls(parent);
		GridData powertypeExtentData = new GridData(GridData.FILL_HORIZONTAL);
		powertypeExtentData.horizontalSpan = 3;
		this.powertypeExtent.setLayoutData(powertypeExtentData);
		this.powertypeExtent.disableMove();
	}

	/**
	 * 
	 */
	private void movePowertypeExtent(GeneralizationSet element, int oldIndex, int newIndex) {
		EObject editedElement = powertypeExtentEditUtil.foundCorrespondingEObject(element);
		powertypeExtentEditUtil.moveElement(element, oldIndex, newIndex);
		powertypeExtent.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.powertypeExtent,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	private void removeFromPowertypeExtent(GeneralizationSet element) {

		// Start of user code removeFromPowertypeExtent() method body

		EObject editedElement = powertypeExtentEditUtil.foundCorrespondingEObject(element);
		powertypeExtentEditUtil.removeElement(element);
		powertypeExtent.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.powertypeExtent,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editPowertypeExtent(GeneralizationSet element) {

		// Start of user code editPowertypeExtent() method body

		EObject editedElement = powertypeExtentEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				powertypeExtentEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				powertypeExtent.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.powertypeExtent,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createRedefinedClassifierAdvancedReferencesTable(Composite parent) {
		this.redefinedClassifier = new ReferencesTable<Classifier>(
				UMLMessages.ComponentPropertiesEditionPart_RedefinedClassifierLabel,
				new ReferencesTableListener<Classifier>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<Classifier> dialog = new TabElementTreeSelectionDialog<Classifier>(
								resourceSet, redefinedClassifierFilters, redefinedClassifierBusinessFilters,
								"Classifier", UMLPackage.eINSTANCE.getClassifier()) {

							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!redefinedClassifierEditUtil.getVirtualList().contains(elem))
										redefinedClassifierEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											ComponentPropertiesEditionPartImpl.this,
											UMLViewsRepository.Component.redefinedClassifier,
											PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
								}
								redefinedClassifier.refresh();
							}

						};
						dialog.open();
					}

					public void handleEdit(Classifier element) {
						editRedefinedClassifier(element);
					}

					public void handleMove(Classifier element, int oldIndex, int newIndex) {
						moveRedefinedClassifier(element, oldIndex, newIndex);
					}

					public void handleRemove(Classifier element) {
						removeFromRedefinedClassifier(element);
					}

					public void navigateTo(Classifier element) {
					}
				});
		this.redefinedClassifier.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.redefinedClassifier, UMLViewsRepository.SWT_KIND));
		this.redefinedClassifier.createControls(parent);
		GridData redefinedClassifierData = new GridData(GridData.FILL_HORIZONTAL);
		redefinedClassifierData.horizontalSpan = 3;
		this.redefinedClassifier.setLayoutData(redefinedClassifierData);
		this.redefinedClassifier.disableMove();
	}

	/**
	 * 
	 */
	private void moveRedefinedClassifier(Classifier element, int oldIndex, int newIndex) {
	}

	/**
	 * 
	 */
	private void removeFromRedefinedClassifier(Classifier element) {

		// Start of user code removeFromRedefinedClassifier() method body

		EObject editedElement = redefinedClassifierEditUtil.foundCorrespondingEObject(element);
		redefinedClassifierEditUtil.removeElement(element);
		redefinedClassifier.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.redefinedClassifier,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editRedefinedClassifier(Classifier element) {

		// Start of user code editRedefinedClassifier() method body

		EObject editedElement = redefinedClassifierEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				redefinedClassifierEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				redefinedClassifier.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.redefinedClassifier,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createTemplateBindingAdvancedTableComposition(Composite parent) {
		this.templateBinding = new ReferencesTable<TemplateBinding>(
				UMLMessages.ComponentPropertiesEditionPart_TemplateBindingLabel,
				new ReferencesTableListener<TemplateBinding>() {

					public void handleAdd() {
						addToTemplateBinding();
					}

					public void handleEdit(TemplateBinding element) {
						editTemplateBinding(element);
					}

					public void handleMove(TemplateBinding element, int oldIndex, int newIndex) {
						moveTemplateBinding(element, oldIndex, newIndex);
					}

					public void handleRemove(TemplateBinding element) {
						removeFromTemplateBinding(element);
					}

					public void navigateTo(TemplateBinding element) {
					}
				});
		this.templateBinding.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.templateBinding, UMLViewsRepository.SWT_KIND));
		this.templateBinding.createControls(parent);
		GridData templateBindingData = new GridData(GridData.FILL_HORIZONTAL);
		templateBindingData.horizontalSpan = 3;
		this.templateBinding.setLayoutData(templateBindingData);
	}

	/**
	 * 
	 */
	private void moveTemplateBinding(TemplateBinding element, int oldIndex, int newIndex) {

		EObject editedElement = templateBindingEditUtil.foundCorrespondingEObject(element);
		templateBindingEditUtil.moveElement(element, oldIndex, newIndex);
		templateBinding.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.templateBinding,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToTemplateBinding() {

		// Start of user code addToTemplateBinding() method body

		TemplateBinding eObject = UMLFactory.eINSTANCE.createTemplateBinding();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				templateBindingEditUtil.addElement(propertiesEditionObject);
				templateBinding.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.templateBinding,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromTemplateBinding(TemplateBinding element) {

		// Start of user code removeFromTemplateBinding() method body

		EObject editedElement = templateBindingEditUtil.foundCorrespondingEObject(element);
		templateBindingEditUtil.removeElement(element);
		templateBinding.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.templateBinding,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editTemplateBinding(TemplateBinding element) {

		// Start of user code editTemplateBinding() method body

		EObject editedElement = templateBindingEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				templateBindingEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				templateBinding.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.templateBinding,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createOwnedRuleAdvancedTableComposition(Composite parent) {
		this.ownedRule = new ReferencesTable<Constraint>(UMLMessages.ComponentPropertiesEditionPart_OwnedRuleLabel,
				new ReferencesTableListener<Constraint>() {

					public void handleAdd() {
						addToOwnedRule();
					}

					public void handleEdit(Constraint element) {
						editOwnedRule(element);
					}

					public void handleMove(Constraint element, int oldIndex, int newIndex) {
						moveOwnedRule(element, oldIndex, newIndex);
					}

					public void handleRemove(Constraint element) {
						removeFromOwnedRule(element);
					}

					public void navigateTo(Constraint element) {
					}
				});
		this.ownedRule.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Component.ownedRule,
				UMLViewsRepository.SWT_KIND));
		this.ownedRule.createControls(parent);
		GridData ownedRuleData = new GridData(GridData.FILL_HORIZONTAL);
		ownedRuleData.horizontalSpan = 3;
		this.ownedRule.setLayoutData(ownedRuleData);
	}

	/**
	 * 
	 */
	private void moveOwnedRule(Constraint element, int oldIndex, int newIndex) {

		EObject editedElement = ownedRuleEditUtil.foundCorrespondingEObject(element);
		ownedRuleEditUtil.moveElement(element, oldIndex, newIndex);
		ownedRule.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedRule,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToOwnedRule() {

		// Start of user code addToOwnedRule() method body

		Constraint eObject = UMLFactory.eINSTANCE.createConstraint();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				ownedRuleEditUtil.addElement(propertiesEditionObject);
				ownedRule.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedRule,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromOwnedRule(Constraint element) {

		// Start of user code removeFromOwnedRule() method body

		EObject editedElement = ownedRuleEditUtil.foundCorrespondingEObject(element);
		ownedRuleEditUtil.removeElement(element);
		ownedRule.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedRule,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editOwnedRule(Constraint element) {

		// Start of user code editOwnedRule() method body

		EObject editedElement = ownedRuleEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedRuleEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedRule.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.ownedRule,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createPackageImportAdvancedTableComposition(Composite parent) {
		this.packageImport = new ReferencesTable<PackageImport>(
				UMLMessages.ComponentPropertiesEditionPart_PackageImportLabel,
				new ReferencesTableListener<PackageImport>() {

					public void handleAdd() {
						addToPackageImport();
					}

					public void handleEdit(PackageImport element) {
						editPackageImport(element);
					}

					public void handleMove(PackageImport element, int oldIndex, int newIndex) {
						movePackageImport(element, oldIndex, newIndex);
					}

					public void handleRemove(PackageImport element) {
						removeFromPackageImport(element);
					}

					public void navigateTo(PackageImport element) {
					}
				});
		this.packageImport.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Component.packageImport, UMLViewsRepository.SWT_KIND));
		this.packageImport.createControls(parent);
		GridData packageImportData = new GridData(GridData.FILL_HORIZONTAL);
		packageImportData.horizontalSpan = 3;
		this.packageImport.setLayoutData(packageImportData);
	}

	/**
	 * 
	 */
	private void movePackageImport(PackageImport element, int oldIndex, int newIndex) {

		EObject editedElement = packageImportEditUtil.foundCorrespondingEObject(element);
		packageImportEditUtil.moveElement(element, oldIndex, newIndex);
		packageImport.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.packageImport,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToPackageImport() {

		// Start of user code addToPackageImport() method body

		PackageImport eObject = UMLFactory.eINSTANCE.createPackageImport();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				packageImportEditUtil.addElement(propertiesEditionObject);
				packageImport.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.packageImport,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromPackageImport(PackageImport element) {

		// Start of user code removeFromPackageImport() method body

		EObject editedElement = packageImportEditUtil.foundCorrespondingEObject(element);
		packageImportEditUtil.removeElement(element);
		packageImport.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.packageImport,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editPackageImport(PackageImport element) {

		// Start of user code editPackageImport() method body

		EObject editedElement = packageImportEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				packageImportEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				packageImport.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ComponentPropertiesEditionPartImpl.this, UMLViewsRepository.Component.packageImport,
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getName()
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#setName(String
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getVisibility()
	 */
	public Enumerator getVisibility() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) visibility.getSelection()).getFirstElement();
		return selection.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initVisibility(EEnum
	 *      eenum, Enumerator current)
	 */
	public void initVisibility(EEnum eenum, Enumerator current) {
		visibility.setInput(eenum.getELiterals());
		visibility.modelUpdating(new StructuredSelection(current));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#setVisibility(Enumerator
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getIsActive()
	 */
	public Boolean getIsActive() {
		return Boolean.valueOf(isActive.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#setIsActive(Boolean
	 *      newValue)
	 */
	public void setIsActive(Boolean newValue) {
		if (newValue != null) {
			isActive.setSelection(newValue.booleanValue());
		} else {
			isActive.setSelection(false);
		}
	}

	public void setMessageForIsActive(String msg, int msgLevel) {

	}

	public void unsetMessageForIsActive() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getIsIndirectlyInstantiated()
	 */
	public Boolean getIsIndirectlyInstantiated() {
		return Boolean.valueOf(isIndirectlyInstantiated.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#setIsIndirectlyInstantiated(Boolean
	 *      newValue)
	 */
	public void setIsIndirectlyInstantiated(Boolean newValue) {
		if (newValue != null) {
			isIndirectlyInstantiated.setSelection(newValue.booleanValue());
		} else {
			isIndirectlyInstantiated.setSelection(false);
		}
	}

	public void setMessageForIsIndirectlyInstantiated(String msg, int msgLevel) {

	}

	public void unsetMessageForIsIndirectlyInstantiated() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getIsAbstract()
	 */
	public Boolean getIsAbstract() {
		return Boolean.valueOf(isAbstract.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#setIsAbstract(Boolean
	 *      newValue)
	 */
	public void setIsAbstract(Boolean newValue) {
		if (newValue != null) {
			isAbstract.setSelection(newValue.booleanValue());
		} else {
			isAbstract.setSelection(false);
		}
	}

	public void setMessageForIsAbstract(String msg, int msgLevel) {

	}

	public void unsetMessageForIsAbstract() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getIsLeaf()
	 */
	public Boolean getIsLeaf() {
		return Boolean.valueOf(isLeaf.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#setIsLeaf(Boolean
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedAttributeToAdd()
	 */
	public List getOwnedAttributeToAdd() {
		return ownedAttributeEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedAttributeToRemove()
	 */
	public List getOwnedAttributeToRemove() {
		return ownedAttributeEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedAttributeToEdit()
	 */
	public Map getOwnedAttributeToEdit() {
		return ownedAttributeEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedAttributeToMove()
	 */
	public List getOwnedAttributeToMove() {
		return ownedAttributeEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedAttributeTable()
	 */
	public List getOwnedAttributeTable() {
		return ownedAttributeEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initOwnedAttribute(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initOwnedAttribute(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			ownedAttributeEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			ownedAttributeEditUtil = new EMFListEditUtil(current, feature);
		this.ownedAttribute.setInput(ownedAttributeEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updateOwnedAttribute(EObject
	 *      newValue)
	 */
	public void updateOwnedAttribute(EObject newValue) {
		if (ownedAttributeEditUtil != null) {
			ownedAttributeEditUtil.reinit(newValue);
			ownedAttribute.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterOwnedAttribute(ViewerFilter
	 *      filter)
	 */
	public void addFilterToOwnedAttribute(ViewerFilter filter) {
		ownedAttributeFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterOwnedAttribute(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToOwnedAttribute(ViewerFilter filter) {
		ownedAttributeBusinessFilters.add(filter);
	}

	public void setMessageForOwnedAttribute(String msg, int msgLevel) {

	}

	public void unsetMessageForOwnedAttribute() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedOperationToAdd()
	 */
	public List getOwnedOperationToAdd() {
		return ownedOperationEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedOperationToRemove()
	 */
	public List getOwnedOperationToRemove() {
		return ownedOperationEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedOperationToEdit()
	 */
	public Map getOwnedOperationToEdit() {
		return ownedOperationEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedOperationToMove()
	 */
	public List getOwnedOperationToMove() {
		return ownedOperationEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedOperationTable()
	 */
	public List getOwnedOperationTable() {
		return ownedOperationEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initOwnedOperation(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initOwnedOperation(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			ownedOperationEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			ownedOperationEditUtil = new EMFListEditUtil(current, feature);
		this.ownedOperation.setInput(ownedOperationEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updateOwnedOperation(EObject
	 *      newValue)
	 */
	public void updateOwnedOperation(EObject newValue) {
		if (ownedOperationEditUtil != null) {
			ownedOperationEditUtil.reinit(newValue);
			ownedOperation.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterOwnedOperation(ViewerFilter
	 *      filter)
	 */
	public void addFilterToOwnedOperation(ViewerFilter filter) {
		ownedOperationFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterOwnedOperation(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToOwnedOperation(ViewerFilter filter) {
		ownedOperationBusinessFilters.add(filter);
	}

	public void setMessageForOwnedOperation(String msg, int msgLevel) {

	}

	public void unsetMessageForOwnedOperation() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getNestedClassifierToAdd()
	 */
	public List getNestedClassifierToAdd() {
		return nestedClassifierEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getNestedClassifierToRemove()
	 */
	public List getNestedClassifierToRemove() {
		return nestedClassifierEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getNestedClassifierToEdit()
	 */
	public Map getNestedClassifierToEdit() {
		return nestedClassifierEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getNestedClassifierToMove()
	 */
	public List getNestedClassifierToMove() {
		return nestedClassifierEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getNestedClassifierTable()
	 */
	public List getNestedClassifierTable() {
		return nestedClassifierEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initNestedClassifier(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initNestedClassifier(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			nestedClassifierEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			nestedClassifierEditUtil = new EMFListEditUtil(current, feature);
		this.nestedClassifier.setInput(nestedClassifierEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updateNestedClassifier(EObject
	 *      newValue)
	 */
	public void updateNestedClassifier(EObject newValue) {
		if (nestedClassifierEditUtil != null) {
			nestedClassifierEditUtil.reinit(newValue);
			nestedClassifier.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterNestedClassifier(ViewerFilter
	 *      filter)
	 */
	public void addFilterToNestedClassifier(ViewerFilter filter) {
		nestedClassifierFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterNestedClassifier(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToNestedClassifier(ViewerFilter filter) {
		nestedClassifierBusinessFilters.add(filter);
	}

	public void setMessageForNestedClassifier(String msg, int msgLevel) {

	}

	public void unsetMessageForNestedClassifier() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getRealizationToAdd()
	 */
	public List getRealizationToAdd() {
		return realizationEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getRealizationToRemove()
	 */
	public List getRealizationToRemove() {
		return realizationEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getRealizationToEdit()
	 */
	public Map getRealizationToEdit() {
		return realizationEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getRealizationToMove()
	 */
	public List getRealizationToMove() {
		return realizationEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getRealizationTable()
	 */
	public List getRealizationTable() {
		return realizationEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initRealization(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initRealization(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			realizationEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			realizationEditUtil = new EMFListEditUtil(current, feature);
		this.realization.setInput(realizationEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updateRealization(EObject
	 *      newValue)
	 */
	public void updateRealization(EObject newValue) {
		if (realizationEditUtil != null) {
			realizationEditUtil.reinit(newValue);
			realization.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterRealization(ViewerFilter
	 *      filter)
	 */
	public void addFilterToRealization(ViewerFilter filter) {
		realizationFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterRealization(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToRealization(ViewerFilter filter) {
		realizationBusinessFilters.add(filter);
	}

	public void setMessageForRealization(String msg, int msgLevel) {

	}

	public void unsetMessageForRealization() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getInterfaceRealizationToAdd()
	 */
	public List getInterfaceRealizationToAdd() {
		return interfaceRealizationEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getInterfaceRealizationToRemove()
	 */
	public List getInterfaceRealizationToRemove() {
		return interfaceRealizationEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getInterfaceRealizationToEdit()
	 */
	public Map getInterfaceRealizationToEdit() {
		return interfaceRealizationEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getInterfaceRealizationToMove()
	 */
	public List getInterfaceRealizationToMove() {
		return interfaceRealizationEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getInterfaceRealizationTable()
	 */
	public List getInterfaceRealizationTable() {
		return interfaceRealizationEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initInterfaceRealization(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initInterfaceRealization(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			interfaceRealizationEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			interfaceRealizationEditUtil = new EMFListEditUtil(current, feature);
		this.interfaceRealization.setInput(interfaceRealizationEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updateInterfaceRealization(EObject
	 *      newValue)
	 */
	public void updateInterfaceRealization(EObject newValue) {
		if (interfaceRealizationEditUtil != null) {
			interfaceRealizationEditUtil.reinit(newValue);
			interfaceRealization.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterInterfaceRealization(ViewerFilter
	 *      filter)
	 */
	public void addFilterToInterfaceRealization(ViewerFilter filter) {
		interfaceRealizationFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterInterfaceRealization(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToInterfaceRealization(ViewerFilter filter) {
		interfaceRealizationBusinessFilters.add(filter);
	}

	public void setMessageForInterfaceRealization(String msg, int msgLevel) {

	}

	public void unsetMessageForInterfaceRealization() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getGeneralizationToAdd()
	 */
	public List getGeneralizationToAdd() {
		return generalizationEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getGeneralizationToRemove()
	 */
	public List getGeneralizationToRemove() {
		return generalizationEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getGeneralizationToEdit()
	 */
	public Map getGeneralizationToEdit() {
		return generalizationEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getGeneralizationToMove()
	 */
	public List getGeneralizationToMove() {
		return generalizationEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getGeneralizationTable()
	 */
	public List getGeneralizationTable() {
		return generalizationEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initGeneralization(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initGeneralization(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			generalizationEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			generalizationEditUtil = new EMFListEditUtil(current, feature);
		this.generalization.setInput(generalizationEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updateGeneralization(EObject
	 *      newValue)
	 */
	public void updateGeneralization(EObject newValue) {
		if (generalizationEditUtil != null) {
			generalizationEditUtil.reinit(newValue);
			generalization.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterGeneralization(ViewerFilter
	 *      filter)
	 */
	public void addFilterToGeneralization(ViewerFilter filter) {
		generalizationFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterGeneralization(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToGeneralization(ViewerFilter filter) {
		generalizationBusinessFilters.add(filter);
	}

	public void setMessageForGeneralization(String msg, int msgLevel) {

	}

	public void unsetMessageForGeneralization() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getElementImportToAdd()
	 */
	public List getElementImportToAdd() {
		return elementImportEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getElementImportToRemove()
	 */
	public List getElementImportToRemove() {
		return elementImportEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getElementImportToEdit()
	 */
	public Map getElementImportToEdit() {
		return elementImportEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getElementImportToMove()
	 */
	public List getElementImportToMove() {
		return elementImportEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getElementImportTable()
	 */
	public List getElementImportTable() {
		return elementImportEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initElementImport(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initElementImport(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			elementImportEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			elementImportEditUtil = new EMFListEditUtil(current, feature);
		this.elementImport.setInput(elementImportEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updateElementImport(EObject
	 *      newValue)
	 */
	public void updateElementImport(EObject newValue) {
		if (elementImportEditUtil != null) {
			elementImportEditUtil.reinit(newValue);
			elementImport.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterElementImport(ViewerFilter
	 *      filter)
	 */
	public void addFilterToElementImport(ViewerFilter filter) {
		elementImportFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterElementImport(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToElementImport(ViewerFilter filter) {
		elementImportBusinessFilters.add(filter);
	}

	public void setMessageForElementImport(String msg, int msgLevel) {

	}

	public void unsetMessageForElementImport() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedUseCaseToAdd()
	 */
	public List getOwnedUseCaseToAdd() {
		return ownedUseCaseEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedUseCaseToRemove()
	 */
	public List getOwnedUseCaseToRemove() {
		return ownedUseCaseEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedUseCaseToEdit()
	 */
	public Map getOwnedUseCaseToEdit() {
		return ownedUseCaseEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedUseCaseToMove()
	 */
	public List getOwnedUseCaseToMove() {
		return ownedUseCaseEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedUseCaseTable()
	 */
	public List getOwnedUseCaseTable() {
		return ownedUseCaseEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initOwnedUseCase(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initOwnedUseCase(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			ownedUseCaseEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			ownedUseCaseEditUtil = new EMFListEditUtil(current, feature);
		this.ownedUseCase.setInput(ownedUseCaseEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updateOwnedUseCase(EObject
	 *      newValue)
	 */
	public void updateOwnedUseCase(EObject newValue) {
		if (ownedUseCaseEditUtil != null) {
			ownedUseCaseEditUtil.reinit(newValue);
			ownedUseCase.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterOwnedUseCase(ViewerFilter
	 *      filter)
	 */
	public void addFilterToOwnedUseCase(ViewerFilter filter) {
		ownedUseCaseFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterOwnedUseCase(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToOwnedUseCase(ViewerFilter filter) {
		ownedUseCaseBusinessFilters.add(filter);
	}

	public void setMessageForOwnedUseCase(String msg, int msgLevel) {

	}

	public void unsetMessageForOwnedUseCase() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getPackagedElementToAdd()
	 */
	public List getPackagedElementToAdd() {
		return packagedElementEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getPackagedElementToRemove()
	 */
	public List getPackagedElementToRemove() {
		return packagedElementEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getPackagedElementToEdit()
	 */
	public Map getPackagedElementToEdit() {
		return packagedElementEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getPackagedElementToMove()
	 */
	public List getPackagedElementToMove() {
		return packagedElementEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getPackagedElementTable()
	 */
	public List getPackagedElementTable() {
		return packagedElementEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initPackagedElement(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initPackagedElement(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			packagedElementEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			packagedElementEditUtil = new EMFListEditUtil(current, feature);
		this.packagedElement.setInput(packagedElementEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updatePackagedElement(EObject
	 *      newValue)
	 */
	public void updatePackagedElement(EObject newValue) {
		if (packagedElementEditUtil != null) {
			packagedElementEditUtil.reinit(newValue);
			packagedElement.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterPackagedElement(ViewerFilter
	 *      filter)
	 */
	public void addFilterToPackagedElement(ViewerFilter filter) {
		packagedElementFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterPackagedElement(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToPackagedElement(ViewerFilter filter) {
		packagedElementBusinessFilters.add(filter);
	}

	public void setMessageForPackagedElement(String msg, int msgLevel) {

	}

	public void unsetMessageForPackagedElement() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedReceptionToAdd()
	 */
	public List getOwnedReceptionToAdd() {
		return ownedReceptionEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedReceptionToRemove()
	 */
	public List getOwnedReceptionToRemove() {
		return ownedReceptionEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedReceptionToEdit()
	 */
	public Map getOwnedReceptionToEdit() {
		return ownedReceptionEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedReceptionToMove()
	 */
	public List getOwnedReceptionToMove() {
		return ownedReceptionEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedReceptionTable()
	 */
	public List getOwnedReceptionTable() {
		return ownedReceptionEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initOwnedReception(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initOwnedReception(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			ownedReceptionEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			ownedReceptionEditUtil = new EMFListEditUtil(current, feature);
		this.ownedReception.setInput(ownedReceptionEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updateOwnedReception(EObject
	 *      newValue)
	 */
	public void updateOwnedReception(EObject newValue) {
		if (ownedReceptionEditUtil != null) {
			ownedReceptionEditUtil.reinit(newValue);
			ownedReception.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterOwnedReception(ViewerFilter
	 *      filter)
	 */
	public void addFilterToOwnedReception(ViewerFilter filter) {
		ownedReceptionFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterOwnedReception(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToOwnedReception(ViewerFilter filter) {
		ownedReceptionBusinessFilters.add(filter);
	}

	public void setMessageForOwnedReception(String msg, int msgLevel) {

	}

	public void unsetMessageForOwnedReception() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedTriggerToAdd()
	 */
	public List getOwnedTriggerToAdd() {
		return ownedTriggerEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedTriggerToRemove()
	 */
	public List getOwnedTriggerToRemove() {
		return ownedTriggerEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedTriggerToEdit()
	 */
	public Map getOwnedTriggerToEdit() {
		return ownedTriggerEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedTriggerToMove()
	 */
	public List getOwnedTriggerToMove() {
		return ownedTriggerEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedTriggerTable()
	 */
	public List getOwnedTriggerTable() {
		return ownedTriggerEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initOwnedTrigger(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initOwnedTrigger(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			ownedTriggerEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			ownedTriggerEditUtil = new EMFListEditUtil(current, feature);
		this.ownedTrigger.setInput(ownedTriggerEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updateOwnedTrigger(EObject
	 *      newValue)
	 */
	public void updateOwnedTrigger(EObject newValue) {
		if (ownedTriggerEditUtil != null) {
			ownedTriggerEditUtil.reinit(newValue);
			ownedTrigger.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterOwnedTrigger(ViewerFilter
	 *      filter)
	 */
	public void addFilterToOwnedTrigger(ViewerFilter filter) {
		ownedTriggerFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterOwnedTrigger(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToOwnedTrigger(ViewerFilter filter) {
		ownedTriggerBusinessFilters.add(filter);
	}

	public void setMessageForOwnedTrigger(String msg, int msgLevel) {

	}

	public void unsetMessageForOwnedTrigger() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedBehaviorToAdd()
	 */
	public List getOwnedBehaviorToAdd() {
		return ownedBehaviorEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedBehaviorToRemove()
	 */
	public List getOwnedBehaviorToRemove() {
		return ownedBehaviorEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedBehaviorToEdit()
	 */
	public Map getOwnedBehaviorToEdit() {
		return ownedBehaviorEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedBehaviorToMove()
	 */
	public List getOwnedBehaviorToMove() {
		return ownedBehaviorEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedBehaviorTable()
	 */
	public List getOwnedBehaviorTable() {
		return ownedBehaviorEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initOwnedBehavior(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initOwnedBehavior(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			ownedBehaviorEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			ownedBehaviorEditUtil = new EMFListEditUtil(current, feature);
		this.ownedBehavior.setInput(ownedBehaviorEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updateOwnedBehavior(EObject
	 *      newValue)
	 */
	public void updateOwnedBehavior(EObject newValue) {
		if (ownedBehaviorEditUtil != null) {
			ownedBehaviorEditUtil.reinit(newValue);
			ownedBehavior.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterOwnedBehavior(ViewerFilter
	 *      filter)
	 */
	public void addFilterToOwnedBehavior(ViewerFilter filter) {
		ownedBehaviorFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterOwnedBehavior(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToOwnedBehavior(ViewerFilter filter) {
		ownedBehaviorBusinessFilters.add(filter);
	}

	public void setMessageForOwnedBehavior(String msg, int msgLevel) {

	}

	public void unsetMessageForOwnedBehavior() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedConnectorToAdd()
	 */
	public List getOwnedConnectorToAdd() {
		return ownedConnectorEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedConnectorToRemove()
	 */
	public List getOwnedConnectorToRemove() {
		return ownedConnectorEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedConnectorToEdit()
	 */
	public Map getOwnedConnectorToEdit() {
		return ownedConnectorEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedConnectorToMove()
	 */
	public List getOwnedConnectorToMove() {
		return ownedConnectorEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedConnectorTable()
	 */
	public List getOwnedConnectorTable() {
		return ownedConnectorEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initOwnedConnector(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initOwnedConnector(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			ownedConnectorEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			ownedConnectorEditUtil = new EMFListEditUtil(current, feature);
		this.ownedConnector.setInput(ownedConnectorEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updateOwnedConnector(EObject
	 *      newValue)
	 */
	public void updateOwnedConnector(EObject newValue) {
		if (ownedConnectorEditUtil != null) {
			ownedConnectorEditUtil.reinit(newValue);
			ownedConnector.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterOwnedConnector(ViewerFilter
	 *      filter)
	 */
	public void addFilterToOwnedConnector(ViewerFilter filter) {
		ownedConnectorFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterOwnedConnector(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToOwnedConnector(ViewerFilter filter) {
		ownedConnectorBusinessFilters.add(filter);
	}

	public void setMessageForOwnedConnector(String msg, int msgLevel) {

	}

	public void unsetMessageForOwnedConnector() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getCollaborationUseToAdd()
	 */
	public List getCollaborationUseToAdd() {
		return collaborationUseEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getCollaborationUseToRemove()
	 */
	public List getCollaborationUseToRemove() {
		return collaborationUseEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getCollaborationUseToEdit()
	 */
	public Map getCollaborationUseToEdit() {
		return collaborationUseEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getCollaborationUseToMove()
	 */
	public List getCollaborationUseToMove() {
		return collaborationUseEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getCollaborationUseTable()
	 */
	public List getCollaborationUseTable() {
		return collaborationUseEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initCollaborationUse(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initCollaborationUse(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			collaborationUseEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			collaborationUseEditUtil = new EMFListEditUtil(current, feature);
		this.collaborationUse.setInput(collaborationUseEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updateCollaborationUse(EObject
	 *      newValue)
	 */
	public void updateCollaborationUse(EObject newValue) {
		if (collaborationUseEditUtil != null) {
			collaborationUseEditUtil.reinit(newValue);
			collaborationUse.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterCollaborationUse(ViewerFilter
	 *      filter)
	 */
	public void addFilterToCollaborationUse(ViewerFilter filter) {
		collaborationUseFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterCollaborationUse(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToCollaborationUse(ViewerFilter filter) {
		collaborationUseBusinessFilters.add(filter);
	}

	public void setMessageForCollaborationUse(String msg, int msgLevel) {

	}

	public void unsetMessageForCollaborationUse() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getUseCaseToAdd()
	 */
	public List getUseCaseToAdd() {
		return useCaseEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getUseCaseToRemove()
	 */
	public List getUseCaseToRemove() {
		return useCaseEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getUseCaseTable()
	 */
	public List getUseCaseTable() {
		return useCaseEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initUseCase(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initUseCase(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			useCaseEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			useCaseEditUtil = new EMFListEditUtil(current, feature);
		this.useCase.setInput(useCaseEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updateUseCase(EObject
	 *      newValue)
	 */
	public void updateUseCase(EObject newValue) {
		if (useCaseEditUtil != null) {
			useCaseEditUtil.reinit(newValue);
			useCase.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterUseCase(ViewerFilter
	 *      filter)
	 */
	public void addFilterToUseCase(ViewerFilter filter) {
		useCaseFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterUseCase(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToUseCase(ViewerFilter filter) {
		useCaseBusinessFilters.add(filter);
	}

	public void setMessageForUseCase(String msg, int msgLevel) {

	}

	public void unsetMessageForUseCase() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getSubstitutionToAdd()
	 */
	public List getSubstitutionToAdd() {
		return substitutionEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getSubstitutionToRemove()
	 */
	public List getSubstitutionToRemove() {
		return substitutionEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getSubstitutionToEdit()
	 */
	public Map getSubstitutionToEdit() {
		return substitutionEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getSubstitutionToMove()
	 */
	public List getSubstitutionToMove() {
		return substitutionEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getSubstitutionTable()
	 */
	public List getSubstitutionTable() {
		return substitutionEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initSubstitution(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initSubstitution(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			substitutionEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			substitutionEditUtil = new EMFListEditUtil(current, feature);
		this.substitution.setInput(substitutionEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updateSubstitution(EObject
	 *      newValue)
	 */
	public void updateSubstitution(EObject newValue) {
		if (substitutionEditUtil != null) {
			substitutionEditUtil.reinit(newValue);
			substitution.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterSubstitution(ViewerFilter
	 *      filter)
	 */
	public void addFilterToSubstitution(ViewerFilter filter) {
		substitutionFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterSubstitution(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToSubstitution(ViewerFilter filter) {
		substitutionBusinessFilters.add(filter);
	}

	public void setMessageForSubstitution(String msg, int msgLevel) {

	}

	public void unsetMessageForSubstitution() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getPowertypeExtentToAdd()
	 */
	public List getPowertypeExtentToAdd() {
		return powertypeExtentEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getPowertypeExtentToRemove()
	 */
	public List getPowertypeExtentToRemove() {
		return powertypeExtentEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getPowertypeExtentTable()
	 */
	public List getPowertypeExtentTable() {
		return powertypeExtentEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initPowertypeExtent(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initPowertypeExtent(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			powertypeExtentEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			powertypeExtentEditUtil = new EMFListEditUtil(current, feature);
		this.powertypeExtent.setInput(powertypeExtentEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updatePowertypeExtent(EObject
	 *      newValue)
	 */
	public void updatePowertypeExtent(EObject newValue) {
		if (powertypeExtentEditUtil != null) {
			powertypeExtentEditUtil.reinit(newValue);
			powertypeExtent.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterPowertypeExtent(ViewerFilter
	 *      filter)
	 */
	public void addFilterToPowertypeExtent(ViewerFilter filter) {
		powertypeExtentFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterPowertypeExtent(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToPowertypeExtent(ViewerFilter filter) {
		powertypeExtentBusinessFilters.add(filter);
	}

	public void setMessageForPowertypeExtent(String msg, int msgLevel) {

	}

	public void unsetMessageForPowertypeExtent() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getRedefinedClassifierToAdd()
	 */
	public List getRedefinedClassifierToAdd() {
		return redefinedClassifierEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getRedefinedClassifierToRemove()
	 */
	public List getRedefinedClassifierToRemove() {
		return redefinedClassifierEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getRedefinedClassifierTable()
	 */
	public List getRedefinedClassifierTable() {
		return redefinedClassifierEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initRedefinedClassifier(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initRedefinedClassifier(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			redefinedClassifierEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			redefinedClassifierEditUtil = new EMFListEditUtil(current, feature);
		this.redefinedClassifier.setInput(redefinedClassifierEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updateRedefinedClassifier(EObject
	 *      newValue)
	 */
	public void updateRedefinedClassifier(EObject newValue) {
		if (redefinedClassifierEditUtil != null) {
			redefinedClassifierEditUtil.reinit(newValue);
			redefinedClassifier.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterRedefinedClassifier(ViewerFilter
	 *      filter)
	 */
	public void addFilterToRedefinedClassifier(ViewerFilter filter) {
		redefinedClassifierFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterRedefinedClassifier(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToRedefinedClassifier(ViewerFilter filter) {
		redefinedClassifierBusinessFilters.add(filter);
	}

	public void setMessageForRedefinedClassifier(String msg, int msgLevel) {

	}

	public void unsetMessageForRedefinedClassifier() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getTemplateBindingToAdd()
	 */
	public List getTemplateBindingToAdd() {
		return templateBindingEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getTemplateBindingToRemove()
	 */
	public List getTemplateBindingToRemove() {
		return templateBindingEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getTemplateBindingToEdit()
	 */
	public Map getTemplateBindingToEdit() {
		return templateBindingEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getTemplateBindingToMove()
	 */
	public List getTemplateBindingToMove() {
		return templateBindingEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getTemplateBindingTable()
	 */
	public List getTemplateBindingTable() {
		return templateBindingEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initTemplateBinding(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initTemplateBinding(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			templateBindingEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			templateBindingEditUtil = new EMFListEditUtil(current, feature);
		this.templateBinding.setInput(templateBindingEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updateTemplateBinding(EObject
	 *      newValue)
	 */
	public void updateTemplateBinding(EObject newValue) {
		if (templateBindingEditUtil != null) {
			templateBindingEditUtil.reinit(newValue);
			templateBinding.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterTemplateBinding(ViewerFilter
	 *      filter)
	 */
	public void addFilterToTemplateBinding(ViewerFilter filter) {
		templateBindingFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterTemplateBinding(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToTemplateBinding(ViewerFilter filter) {
		templateBindingBusinessFilters.add(filter);
	}

	public void setMessageForTemplateBinding(String msg, int msgLevel) {

	}

	public void unsetMessageForTemplateBinding() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedRuleToAdd()
	 */
	public List getOwnedRuleToAdd() {
		return ownedRuleEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedRuleToRemove()
	 */
	public List getOwnedRuleToRemove() {
		return ownedRuleEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedRuleToEdit()
	 */
	public Map getOwnedRuleToEdit() {
		return ownedRuleEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedRuleToMove()
	 */
	public List getOwnedRuleToMove() {
		return ownedRuleEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getOwnedRuleTable()
	 */
	public List getOwnedRuleTable() {
		return ownedRuleEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initOwnedRule(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initOwnedRule(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			ownedRuleEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			ownedRuleEditUtil = new EMFListEditUtil(current, feature);
		this.ownedRule.setInput(ownedRuleEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updateOwnedRule(EObject
	 *      newValue)
	 */
	public void updateOwnedRule(EObject newValue) {
		if (ownedRuleEditUtil != null) {
			ownedRuleEditUtil.reinit(newValue);
			ownedRule.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterOwnedRule(ViewerFilter
	 *      filter)
	 */
	public void addFilterToOwnedRule(ViewerFilter filter) {
		ownedRuleFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterOwnedRule(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToOwnedRule(ViewerFilter filter) {
		ownedRuleBusinessFilters.add(filter);
	}

	public void setMessageForOwnedRule(String msg, int msgLevel) {

	}

	public void unsetMessageForOwnedRule() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getPackageImportToAdd()
	 */
	public List getPackageImportToAdd() {
		return packageImportEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getPackageImportToRemove()
	 */
	public List getPackageImportToRemove() {
		return packageImportEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getPackageImportToEdit()
	 */
	public Map getPackageImportToEdit() {
		return packageImportEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getPackageImportToMove()
	 */
	public List getPackageImportToMove() {
		return packageImportEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#getPackageImportTable()
	 */
	public List getPackageImportTable() {
		return packageImportEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#initPackageImport(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initPackageImport(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			packageImportEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			packageImportEditUtil = new EMFListEditUtil(current, feature);
		this.packageImport.setInput(packageImportEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#updatePackageImport(EObject
	 *      newValue)
	 */
	public void updatePackageImport(EObject newValue) {
		if (packageImportEditUtil != null) {
			packageImportEditUtil.reinit(newValue);
			packageImport.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addFilterPackageImport(ViewerFilter
	 *      filter)
	 */
	public void addFilterToPackageImport(ViewerFilter filter) {
		packageImportFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart#addBusinessFilterPackageImport(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToPackageImport(ViewerFilter filter) {
		packageImportBusinessFilters.add(filter);
	}

	public void setMessageForPackageImport(String msg, int msgLevel) {

	}

	public void unsetMessageForPackageImport() {

	}

	// Start of user code additional methods

	// End of user code

}
