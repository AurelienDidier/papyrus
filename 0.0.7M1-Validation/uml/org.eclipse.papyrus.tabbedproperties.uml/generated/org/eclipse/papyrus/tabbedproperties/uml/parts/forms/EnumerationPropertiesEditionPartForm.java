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
package org.eclipse.papyrus.tabbedproperties.uml.parts.forms;

// Start of user code for imports

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.parts.CompositePropertiesEditionPart;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IMessageManager;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.events.IExpansionListener;

import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.papyrus.tabbedproperties.uml.providers.UMLMessages;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.impl.parts.CompositePropertiesEditionPart;
import org.eclipse.emf.eef.runtime.api.policies.IPropertiesEditionPolicy;
import org.eclipse.emf.eef.runtime.api.providers.IPropertiesEditionPolicyProvider;
import org.eclipse.emf.eef.runtime.impl.policies.EObjectPropertiesEditionContext;
import org.eclipse.emf.eef.runtime.impl.services.PropertiesEditionPolicyProviderService;
import org.eclipse.emf.eef.runtime.ui.widgets.FormUtils;
import org.eclipse.emf.eef.runtime.api.parts.EEFMessageManager;
import org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.emf.eef.runtime.ui.widgets.EMFComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import java.util.Map;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.TemplateBinding;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.UseCase;
import org.eclipse.uml2.uml.CollaborationUse;
import org.eclipse.uml2.uml.Substitution;

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
import org.eclipse.emf.eef.runtime.api.providers.IPropertiesEditionPolicyProvider;
import org.eclipse.uml2.uml.GeneralizationSet;
import org.eclipse.uml2.uml.UseCase;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.emf.eef.runtime.impl.filters.EObjectFilter;

import org.eclipse.emf.eef.runtime.ui.widgets.HorizontalBox;
import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;

// End of user code

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class EnumerationPropertiesEditionPartForm extends CompositePropertiesEditionPart implements
		IFormPropertiesEditionPart, EnumerationPropertiesEditionPart {

	protected Text name;

	protected EMFComboViewer visibility;

	protected Button isAbstract;

	protected Button isLeaf;

	protected EMFListEditUtil generalizationEditUtil;

	protected ReferencesTable<?> generalization;

	protected List<ViewerFilter> generalizationBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> generalizationFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil templateBindingEditUtil;

	protected ReferencesTable<?> templateBinding;

	protected List<ViewerFilter> templateBindingBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> templateBindingFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil elementImportEditUtil;

	protected ReferencesTable<?> elementImport;

	protected List<ViewerFilter> elementImportBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> elementImportFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil packageImportEditUtil;

	protected ReferencesTable<?> packageImport;

	protected List<ViewerFilter> packageImportBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> packageImportFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil ownedRuleEditUtil;

	protected ReferencesTable<?> ownedRule;

	protected List<ViewerFilter> ownedRuleBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> ownedRuleFilters = new ArrayList<ViewerFilter>();

	private EMFListEditUtil powertypeExtentEditUtil;

	protected ReferencesTable<?> powertypeExtent;

	protected List<ViewerFilter> powertypeExtentBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> powertypeExtentFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil ownedAttributeEditUtil;

	protected ReferencesTable<?> ownedAttribute;

	protected List<ViewerFilter> ownedAttributeBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> ownedAttributeFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil ownedOperationEditUtil;

	protected ReferencesTable<?> ownedOperation;

	protected List<ViewerFilter> ownedOperationBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> ownedOperationFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil ownedLiteralEditUtil;

	protected ReferencesTable<?> ownedLiteral;

	protected List<ViewerFilter> ownedLiteralBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> ownedLiteralFilters = new ArrayList<ViewerFilter>();

	private EMFListEditUtil useCaseEditUtil;

	protected ReferencesTable<?> useCase;

	protected List<ViewerFilter> useCaseBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> useCaseFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil ownedUseCaseEditUtil;

	protected ReferencesTable<?> ownedUseCase;

	protected List<ViewerFilter> ownedUseCaseBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> ownedUseCaseFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil collaborationUseEditUtil;

	protected ReferencesTable<?> collaborationUse;

	protected List<ViewerFilter> collaborationUseBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> collaborationUseFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil substitutionEditUtil;

	protected ReferencesTable<?> substitution;

	protected List<ViewerFilter> substitutionBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> substitutionFilters = new ArrayList<ViewerFilter>();

	private EMFListEditUtil redefinedClassifierEditUtil;

	protected ReferencesTable<?> redefinedClassifier;

	protected List<ViewerFilter> redefinedClassifierBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> redefinedClassifierFilters = new ArrayList<ViewerFilter>();

	public EnumerationPropertiesEditionPartForm(IPropertiesEditionComponent editionComponent) {
		super(editionComponent);
	}

	public Composite createFigure(final Composite parent, final FormToolkit widgetFactory) {
		ScrolledForm scrolledForm = widgetFactory.createScrolledForm(parent);
		Form form = scrolledForm.getForm();
		view = form.getBody();
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		view.setLayout(layout);
		createControls(widgetFactory, view, new EEFMessageManager(scrolledForm, widgetFactory));
		return scrolledForm;
	}

	public void createControls(final FormToolkit widgetFactory, Composite view, IMessageManager messageManager) {
		this.messageManager = messageManager;
		createGeneralGroup(widgetFactory, view);
		createOthersGroup(widgetFactory, view);
		// Start of user code for additional ui definition

		// End of user code

	}

	protected void createGeneralGroup(FormToolkit widgetFactory, final Composite view) {
		Section generalSection = widgetFactory.createSection(view, Section.TITLE_BAR | Section.TWISTIE
				| Section.EXPANDED);
		generalSection.setText(UMLMessages.EnumerationPropertiesEditionPart_GeneralGroupLabel);
		GridData generalSectionData = new GridData(GridData.FILL_HORIZONTAL);
		generalSectionData.horizontalSpan = 3;
		generalSection.setLayoutData(generalSectionData);
		Composite generalGroup = widgetFactory.createComposite(generalSection);
		GridLayout generalGroupLayout = new GridLayout();
		generalGroupLayout.numColumns = 3;
		generalGroup.setLayout(generalGroupLayout);
		createNameText(widgetFactory, generalGroup);
		createVisibilityEMFComboViewer(widgetFactory, generalGroup);
		createGeneralHBox1HBox(widgetFactory, generalGroup);
		generalSection.setClient(generalGroup);
	}

	protected void createNameText(FormToolkit widgetFactory, Composite parent) {
		FormUtils.createPartLabel(widgetFactory, parent, UMLMessages.EnumerationPropertiesEditionPart_NameLabel,
				propertiesEditionComponent
						.isRequired(UMLViewsRepository.Enumeration.name, UMLViewsRepository.FORM_KIND));
		name = widgetFactory.createText(parent, ""); //$NON-NLS-1$
		name.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		widgetFactory.paintBordersFor(parent);
		GridData nameData = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(nameData);
		name.addModifyListener(new ModifyListener() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			public void modifyText(ModifyEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
							EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.name,
							PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, null, name.getText()));
			}

		});
		name.addFocusListener(new FocusAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
			 */
			public void focusLost(FocusEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
							EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.name,
							PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
			}

		});
		name.addKeyListener(new KeyAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
			 */
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.CR) {
					if (propertiesEditionComponent != null)
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
								EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.name,
								PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
				}
			}

		});
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Enumeration.name, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$

	}

	protected void createVisibilityEMFComboViewer(FormToolkit widgetFactory, Composite parent) {
		FormUtils.createPartLabel(widgetFactory, parent, UMLMessages.EnumerationPropertiesEditionPart_VisibilityLabel,
				propertiesEditionComponent.isRequired(UMLViewsRepository.Enumeration.visibility,
						UMLViewsRepository.FORM_KIND));
		visibility = new EMFComboViewer(parent);
		visibility.setContentProvider(new ArrayContentProvider());
		visibility.setLabelProvider(new AdapterFactoryLabelProvider(new EcoreAdapterFactory()));
		GridData visibilityData = new GridData(GridData.FILL_HORIZONTAL);
		visibility.getCombo().setLayoutData(visibilityData);
		visibility.addSelectionChangedListener(new ISelectionChangedListener() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
			 */
			public void selectionChanged(SelectionChangedEvent event) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
							EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.visibility,
							PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, getVisibility()));
			}

		});
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Enumeration.visibility, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}

	protected void createGeneralHBox1HBox(FormToolkit widgetFactory, Composite parent) {
		Composite container = widgetFactory.createComposite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 3;
		container.setLayoutData(gridData);
		HorizontalBox generalHBox1HBox = new HorizontalBox(container);

		// Apply constraint for checkbox
		GridData constraint = new GridData(GridData.FILL_HORIZONTAL);
		constraint.horizontalAlignment = GridData.BEGINNING;
		generalHBox1HBox.setLayoutData(constraint);

		// create sub figures
		createIsAbstractCheckbox(widgetFactory, generalHBox1HBox);
		createIsLeafCheckbox(widgetFactory, generalHBox1HBox);

		container.pack();
	}

	protected void createIsAbstractCheckbox(FormToolkit widgetFactory, Composite parent) {
		isAbstract = widgetFactory.createButton(parent, UMLMessages.EnumerationPropertiesEditionPart_IsAbstractLabel,
				SWT.CHECK);
		isAbstract.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
							EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.isAbstract,
							PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(isAbstract
									.getSelection())));
			}

		});
		GridData isAbstractData = new GridData(GridData.FILL_HORIZONTAL);
		// isAbstractData.horizontalSpan = 2;
		isAbstract.setLayoutData(isAbstractData);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Enumeration.isAbstract, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}

	protected void createIsLeafCheckbox(FormToolkit widgetFactory, Composite parent) {
		isLeaf = widgetFactory
				.createButton(parent, UMLMessages.EnumerationPropertiesEditionPart_IsLeafLabel, SWT.CHECK);
		isLeaf.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
							EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.isLeaf,
							PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(isLeaf
									.getSelection())));
			}

		});
		GridData isLeafData = new GridData(GridData.FILL_HORIZONTAL);
		// isLeafData.horizontalSpan = 2;
		isLeaf.setLayoutData(isLeafData);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Enumeration.isLeaf, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}

	protected void createOthersGroup(FormToolkit widgetFactory, final Composite view) {
		Section othersSection = widgetFactory.createSection(view, Section.TITLE_BAR | Section.TWISTIE
				| Section.EXPANDED);
		othersSection.setText(UMLMessages.EnumerationPropertiesEditionPart_OthersGroupLabel);
		GridData othersSectionData = new GridData(GridData.FILL_HORIZONTAL);
		othersSectionData.horizontalSpan = 3;
		othersSection.setLayoutData(othersSectionData);
		Composite othersGroup = widgetFactory.createComposite(othersSection);
		GridLayout othersGroupLayout = new GridLayout();
		othersGroupLayout.numColumns = 3;
		othersGroup.setLayout(othersGroupLayout);
		createOthersHBox1HBox(widgetFactory, othersGroup);
		othersSection.setClient(othersGroup);
	}

	protected void createOthersHBox1HBox(FormToolkit widgetFactory, Composite parent) {
		Composite container = widgetFactory.createComposite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 3;
		container.setLayoutData(gridData);
		HorizontalBox othersHBox1HBox = new HorizontalBox(container);

		// create sub figures
		createGeneralizationTableComposition(widgetFactory, othersHBox1HBox);
		createTemplateBindingTableComposition(widgetFactory, othersHBox1HBox);
		createElementImportTableComposition(widgetFactory, othersHBox1HBox);
		createPackageImportTableComposition(widgetFactory, othersHBox1HBox);
		createOwnedRuleTableComposition(widgetFactory, othersHBox1HBox);
		createPowertypeExtentReferencesTable(widgetFactory, othersHBox1HBox);
		createOwnedAttributeTableComposition(widgetFactory, othersHBox1HBox);
		createOwnedOperationTableComposition(widgetFactory, othersHBox1HBox);
		createOwnedLiteralTableComposition(widgetFactory, othersHBox1HBox);
		createUseCaseReferencesTable(widgetFactory, othersHBox1HBox);
		createOwnedUseCaseTableComposition(widgetFactory, othersHBox1HBox);
		createCollaborationUseTableComposition(widgetFactory, othersHBox1HBox);
		createSubstitutionTableComposition(widgetFactory, othersHBox1HBox);
		createRedefinedClassifierReferencesTable(widgetFactory, othersHBox1HBox);

		container.pack();
	}

	/**
	 * @param container
	 */
	protected void createGeneralizationTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.generalization = new ReferencesTable<Generalization>(
				UMLMessages.EnumerationPropertiesEditionPart_GeneralizationLabel,
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
				UMLViewsRepository.Enumeration.generalization, UMLViewsRepository.FORM_KIND));
		this.generalization.createControls(parent, widgetFactory);
		GridData generalizationData = new GridData(GridData.FILL_HORIZONTAL);
		generalizationData.horizontalSpan = 3;
		this.generalization.setLayoutData(generalizationData);
	}

	/**
	 * 
	 */
	protected void moveGeneralization(Generalization element, int oldIndex, int newIndex) {
		EObject editedElement = generalizationEditUtil.foundCorrespondingEObject(element);
		generalizationEditUtil.moveElement(element, oldIndex, newIndex);
		generalization.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.generalization,
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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.generalization,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	protected void removeFromGeneralization(Generalization element) {

		// Start of user code for the removeFromGeneralization() method body

		EObject editedElement = generalizationEditUtil.foundCorrespondingEObject(element);
		generalizationEditUtil.removeElement(element);
		generalization.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.generalization,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editGeneralization(Generalization element) {

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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.generalization,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createTemplateBindingTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.templateBinding = new ReferencesTable<TemplateBinding>(
				UMLMessages.EnumerationPropertiesEditionPart_TemplateBindingLabel,
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
				UMLViewsRepository.Enumeration.templateBinding, UMLViewsRepository.FORM_KIND));
		this.templateBinding.createControls(parent, widgetFactory);
		GridData templateBindingData = new GridData(GridData.FILL_HORIZONTAL);
		templateBindingData.horizontalSpan = 3;
		this.templateBinding.setLayoutData(templateBindingData);
	}

	/**
	 * 
	 */
	protected void moveTemplateBinding(TemplateBinding element, int oldIndex, int newIndex) {
		EObject editedElement = templateBindingEditUtil.foundCorrespondingEObject(element);
		templateBindingEditUtil.moveElement(element, oldIndex, newIndex);
		templateBinding.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.templateBinding,
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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.templateBinding,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	protected void removeFromTemplateBinding(TemplateBinding element) {

		// Start of user code for the removeFromTemplateBinding() method body

		EObject editedElement = templateBindingEditUtil.foundCorrespondingEObject(element);
		templateBindingEditUtil.removeElement(element);
		templateBinding.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.templateBinding,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editTemplateBinding(TemplateBinding element) {

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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.templateBinding,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createElementImportTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.elementImport = new ReferencesTable<ElementImport>(
				UMLMessages.EnumerationPropertiesEditionPart_ElementImportLabel,
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
				UMLViewsRepository.Enumeration.elementImport, UMLViewsRepository.FORM_KIND));
		this.elementImport.createControls(parent, widgetFactory);
		GridData elementImportData = new GridData(GridData.FILL_HORIZONTAL);
		elementImportData.horizontalSpan = 3;
		this.elementImport.setLayoutData(elementImportData);
	}

	/**
	 * 
	 */
	protected void moveElementImport(ElementImport element, int oldIndex, int newIndex) {
		EObject editedElement = elementImportEditUtil.foundCorrespondingEObject(element);
		elementImportEditUtil.moveElement(element, oldIndex, newIndex);
		elementImport.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.elementImport,
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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.elementImport,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	protected void removeFromElementImport(ElementImport element) {

		// Start of user code for the removeFromElementImport() method body

		EObject editedElement = elementImportEditUtil.foundCorrespondingEObject(element);
		elementImportEditUtil.removeElement(element);
		elementImport.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.elementImport,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editElementImport(ElementImport element) {

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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.elementImport,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createPackageImportTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.packageImport = new ReferencesTable<PackageImport>(
				UMLMessages.EnumerationPropertiesEditionPart_PackageImportLabel,
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
				UMLViewsRepository.Enumeration.packageImport, UMLViewsRepository.FORM_KIND));
		this.packageImport.createControls(parent, widgetFactory);
		GridData packageImportData = new GridData(GridData.FILL_HORIZONTAL);
		packageImportData.horizontalSpan = 3;
		this.packageImport.setLayoutData(packageImportData);
	}

	/**
	 * 
	 */
	protected void movePackageImport(PackageImport element, int oldIndex, int newIndex) {
		EObject editedElement = packageImportEditUtil.foundCorrespondingEObject(element);
		packageImportEditUtil.moveElement(element, oldIndex, newIndex);
		packageImport.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.packageImport,
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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.packageImport,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	protected void removeFromPackageImport(PackageImport element) {

		// Start of user code for the removeFromPackageImport() method body

		EObject editedElement = packageImportEditUtil.foundCorrespondingEObject(element);
		packageImportEditUtil.removeElement(element);
		packageImport.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.packageImport,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editPackageImport(PackageImport element) {

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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.packageImport,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createOwnedRuleTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.ownedRule = new ReferencesTable<Constraint>(UMLMessages.EnumerationPropertiesEditionPart_OwnedRuleLabel,
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
		this.ownedRule.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Enumeration.ownedRule,
				UMLViewsRepository.FORM_KIND));
		this.ownedRule.createControls(parent, widgetFactory);
		GridData ownedRuleData = new GridData(GridData.FILL_HORIZONTAL);
		ownedRuleData.horizontalSpan = 3;
		this.ownedRule.setLayoutData(ownedRuleData);
	}

	/**
	 * 
	 */
	protected void moveOwnedRule(Constraint element, int oldIndex, int newIndex) {
		EObject editedElement = ownedRuleEditUtil.foundCorrespondingEObject(element);
		ownedRuleEditUtil.moveElement(element, oldIndex, newIndex);
		ownedRule.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedRule,
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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedRule,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	protected void removeFromOwnedRule(Constraint element) {

		// Start of user code for the removeFromOwnedRule() method body

		EObject editedElement = ownedRuleEditUtil.foundCorrespondingEObject(element);
		ownedRuleEditUtil.removeElement(element);
		ownedRule.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedRule,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editOwnedRule(Constraint element) {

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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedRule,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createPowertypeExtentReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.powertypeExtent = new ReferencesTable<GeneralizationSet>(
				UMLMessages.EnumerationPropertiesEditionPart_PowertypeExtentLabel,
				new ReferencesTableListener<GeneralizationSet>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<GeneralizationSet> dialog = new TabElementTreeSelectionDialog<GeneralizationSet>(
								resourceSet, powertypeExtentFilters, powertypeExtentBusinessFilters,
								"GeneralizationSet", UMLPackage.eINSTANCE.getGeneralizationSet()) {

							@Override
							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!powertypeExtentEditUtil.getVirtualList().contains(elem))
										powertypeExtentEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											EnumerationPropertiesEditionPartForm.this,
											UMLViewsRepository.Enumeration.powertypeExtent,
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
				UMLViewsRepository.Enumeration.powertypeExtent, UMLViewsRepository.FORM_KIND));
		this.powertypeExtent.createControls(parent, widgetFactory);
		GridData powertypeExtentData = new GridData(GridData.FILL_HORIZONTAL);
		powertypeExtentData.horizontalSpan = 3;
		this.powertypeExtent.setLayoutData(powertypeExtentData);
		this.powertypeExtent.disableMove();
	}

	/**
	 * 
	 */
	protected void movePowertypeExtent(GeneralizationSet element, int oldIndex, int newIndex) {
		EObject editedElement = powertypeExtentEditUtil.foundCorrespondingEObject(element);
		powertypeExtentEditUtil.moveElement(element, oldIndex, newIndex);
		powertypeExtent.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.powertypeExtent,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	protected void removeFromPowertypeExtent(GeneralizationSet element) {

		// Start of user code for the removeFromPowertypeExtent() method body

		EObject editedElement = powertypeExtentEditUtil.foundCorrespondingEObject(element);
		powertypeExtentEditUtil.removeElement(element);
		powertypeExtent.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.powertypeExtent,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editPowertypeExtent(GeneralizationSet element) {

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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.powertypeExtent,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createOwnedAttributeTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.ownedAttribute = new ReferencesTable<Property>(
				UMLMessages.EnumerationPropertiesEditionPart_OwnedAttributeLabel,
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
				UMLViewsRepository.Enumeration.ownedAttribute, UMLViewsRepository.FORM_KIND));
		this.ownedAttribute.createControls(parent, widgetFactory);
		GridData ownedAttributeData = new GridData(GridData.FILL_HORIZONTAL);
		ownedAttributeData.horizontalSpan = 3;
		this.ownedAttribute.setLayoutData(ownedAttributeData);
	}

	/**
	 * 
	 */
	protected void moveOwnedAttribute(Property element, int oldIndex, int newIndex) {
		EObject editedElement = ownedAttributeEditUtil.foundCorrespondingEObject(element);
		ownedAttributeEditUtil.moveElement(element, oldIndex, newIndex);
		ownedAttribute.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedAttribute,
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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedAttribute,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	protected void removeFromOwnedAttribute(Property element) {

		// Start of user code for the removeFromOwnedAttribute() method body

		EObject editedElement = ownedAttributeEditUtil.foundCorrespondingEObject(element);
		ownedAttributeEditUtil.removeElement(element);
		ownedAttribute.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedAttribute,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editOwnedAttribute(Property element) {

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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedAttribute,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createOwnedOperationTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.ownedOperation = new ReferencesTable<Operation>(
				UMLMessages.EnumerationPropertiesEditionPart_OwnedOperationLabel,
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
				UMLViewsRepository.Enumeration.ownedOperation, UMLViewsRepository.FORM_KIND));
		this.ownedOperation.createControls(parent, widgetFactory);
		GridData ownedOperationData = new GridData(GridData.FILL_HORIZONTAL);
		ownedOperationData.horizontalSpan = 3;
		this.ownedOperation.setLayoutData(ownedOperationData);
	}

	/**
	 * 
	 */
	protected void moveOwnedOperation(Operation element, int oldIndex, int newIndex) {
		EObject editedElement = ownedOperationEditUtil.foundCorrespondingEObject(element);
		ownedOperationEditUtil.moveElement(element, oldIndex, newIndex);
		ownedOperation.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedOperation,
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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedOperation,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	protected void removeFromOwnedOperation(Operation element) {

		// Start of user code for the removeFromOwnedOperation() method body

		EObject editedElement = ownedOperationEditUtil.foundCorrespondingEObject(element);
		ownedOperationEditUtil.removeElement(element);
		ownedOperation.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedOperation,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editOwnedOperation(Operation element) {

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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedOperation,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createOwnedLiteralTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.ownedLiteral = new ReferencesTable<EnumerationLiteral>(
				UMLMessages.EnumerationPropertiesEditionPart_OwnedLiteralLabel,
				new ReferencesTableListener<EnumerationLiteral>() {

					public void handleAdd() {
						addToOwnedLiteral();
					}

					public void handleEdit(EnumerationLiteral element) {
						editOwnedLiteral(element);
					}

					public void handleMove(EnumerationLiteral element, int oldIndex, int newIndex) {
						moveOwnedLiteral(element, oldIndex, newIndex);
					}

					public void handleRemove(EnumerationLiteral element) {
						removeFromOwnedLiteral(element);
					}

					public void navigateTo(EnumerationLiteral element) {
					}
				});
		this.ownedLiteral.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.Enumeration.ownedLiteral, UMLViewsRepository.FORM_KIND));
		this.ownedLiteral.createControls(parent, widgetFactory);
		GridData ownedLiteralData = new GridData(GridData.FILL_HORIZONTAL);
		ownedLiteralData.horizontalSpan = 3;
		this.ownedLiteral.setLayoutData(ownedLiteralData);
	}

	/**
	 * 
	 */
	protected void moveOwnedLiteral(EnumerationLiteral element, int oldIndex, int newIndex) {
		EObject editedElement = ownedLiteralEditUtil.foundCorrespondingEObject(element);
		ownedLiteralEditUtil.moveElement(element, oldIndex, newIndex);
		ownedLiteral.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedLiteral,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToOwnedLiteral() {

		// Start of user code addToOwnedLiteral() method body

		EnumerationLiteral eObject = UMLFactory.eINSTANCE.createEnumerationLiteral();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				ownedLiteralEditUtil.addElement(propertiesEditionObject);
				ownedLiteral.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedLiteral,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	protected void removeFromOwnedLiteral(EnumerationLiteral element) {

		// Start of user code for the removeFromOwnedLiteral() method body

		EObject editedElement = ownedLiteralEditUtil.foundCorrespondingEObject(element);
		ownedLiteralEditUtil.removeElement(element);
		ownedLiteral.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedLiteral,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editOwnedLiteral(EnumerationLiteral element) {

		// Start of user code editOwnedLiteral() method body

		EObject editedElement = ownedLiteralEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedLiteralEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedLiteral.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedLiteral,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createUseCaseReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.useCase = new ReferencesTable<UseCase>(UMLMessages.EnumerationPropertiesEditionPart_UseCaseLabel,
				new ReferencesTableListener<UseCase>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<UseCase> dialog = new TabElementTreeSelectionDialog<UseCase>(
								resourceSet, useCaseFilters, useCaseBusinessFilters, "UseCase", UMLPackage.eINSTANCE
										.getUseCase()) {

							@Override
							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!useCaseEditUtil.getVirtualList().contains(elem))
										useCaseEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											EnumerationPropertiesEditionPartForm.this,
											UMLViewsRepository.Enumeration.useCase, PropertiesEditionEvent.COMMIT,
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
		this.useCase.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Enumeration.useCase,
				UMLViewsRepository.FORM_KIND));
		this.useCase.createControls(parent, widgetFactory);
		GridData useCaseData = new GridData(GridData.FILL_HORIZONTAL);
		useCaseData.horizontalSpan = 3;
		this.useCase.setLayoutData(useCaseData);
		this.useCase.disableMove();
	}

	/**
	 * 
	 */
	protected void moveUseCase(UseCase element, int oldIndex, int newIndex) {
		EObject editedElement = useCaseEditUtil.foundCorrespondingEObject(element);
		useCaseEditUtil.moveElement(element, oldIndex, newIndex);
		useCase.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.useCase,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	protected void removeFromUseCase(UseCase element) {

		// Start of user code for the removeFromUseCase() method body

		EObject editedElement = useCaseEditUtil.foundCorrespondingEObject(element);
		useCaseEditUtil.removeElement(element);
		useCase.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.useCase,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editUseCase(UseCase element) {

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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.useCase,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createOwnedUseCaseTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.ownedUseCase = new ReferencesTable<UseCase>(
				UMLMessages.EnumerationPropertiesEditionPart_OwnedUseCaseLabel, new ReferencesTableListener<UseCase>() {

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
				UMLViewsRepository.Enumeration.ownedUseCase, UMLViewsRepository.FORM_KIND));
		this.ownedUseCase.createControls(parent, widgetFactory);
		GridData ownedUseCaseData = new GridData(GridData.FILL_HORIZONTAL);
		ownedUseCaseData.horizontalSpan = 3;
		this.ownedUseCase.setLayoutData(ownedUseCaseData);
	}

	/**
	 * 
	 */
	protected void moveOwnedUseCase(UseCase element, int oldIndex, int newIndex) {
		EObject editedElement = ownedUseCaseEditUtil.foundCorrespondingEObject(element);
		ownedUseCaseEditUtil.moveElement(element, oldIndex, newIndex);
		ownedUseCase.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedUseCase,
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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedUseCase,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	protected void removeFromOwnedUseCase(UseCase element) {

		// Start of user code for the removeFromOwnedUseCase() method body

		EObject editedElement = ownedUseCaseEditUtil.foundCorrespondingEObject(element);
		ownedUseCaseEditUtil.removeElement(element);
		ownedUseCase.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedUseCase,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editOwnedUseCase(UseCase element) {

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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.ownedUseCase,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createCollaborationUseTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.collaborationUse = new ReferencesTable<CollaborationUse>(
				UMLMessages.EnumerationPropertiesEditionPart_CollaborationUseLabel,
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
				UMLViewsRepository.Enumeration.collaborationUse, UMLViewsRepository.FORM_KIND));
		this.collaborationUse.createControls(parent, widgetFactory);
		GridData collaborationUseData = new GridData(GridData.FILL_HORIZONTAL);
		collaborationUseData.horizontalSpan = 3;
		this.collaborationUse.setLayoutData(collaborationUseData);
	}

	/**
	 * 
	 */
	protected void moveCollaborationUse(CollaborationUse element, int oldIndex, int newIndex) {
		EObject editedElement = collaborationUseEditUtil.foundCorrespondingEObject(element);
		collaborationUseEditUtil.moveElement(element, oldIndex, newIndex);
		collaborationUse.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.collaborationUse,
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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.collaborationUse,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	protected void removeFromCollaborationUse(CollaborationUse element) {

		// Start of user code for the removeFromCollaborationUse() method body

		EObject editedElement = collaborationUseEditUtil.foundCorrespondingEObject(element);
		collaborationUseEditUtil.removeElement(element);
		collaborationUse.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.collaborationUse,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editCollaborationUse(CollaborationUse element) {

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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.collaborationUse,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createSubstitutionTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.substitution = new ReferencesTable<Substitution>(
				UMLMessages.EnumerationPropertiesEditionPart_SubstitutionLabel,
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
				UMLViewsRepository.Enumeration.substitution, UMLViewsRepository.FORM_KIND));
		this.substitution.createControls(parent, widgetFactory);
		GridData substitutionData = new GridData(GridData.FILL_HORIZONTAL);
		substitutionData.horizontalSpan = 3;
		this.substitution.setLayoutData(substitutionData);
	}

	/**
	 * 
	 */
	protected void moveSubstitution(Substitution element, int oldIndex, int newIndex) {
		EObject editedElement = substitutionEditUtil.foundCorrespondingEObject(element);
		substitutionEditUtil.moveElement(element, oldIndex, newIndex);
		substitution.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.substitution,
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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.substitution,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	protected void removeFromSubstitution(Substitution element) {

		// Start of user code for the removeFromSubstitution() method body

		EObject editedElement = substitutionEditUtil.foundCorrespondingEObject(element);
		substitutionEditUtil.removeElement(element);
		substitution.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.substitution,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editSubstitution(Substitution element) {

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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.substitution,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createRedefinedClassifierReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.redefinedClassifier = new ReferencesTable<Classifier>(
				UMLMessages.EnumerationPropertiesEditionPart_RedefinedClassifierLabel,
				new ReferencesTableListener<Classifier>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<Classifier> dialog = new TabElementTreeSelectionDialog<Classifier>(
								resourceSet, redefinedClassifierFilters, redefinedClassifierBusinessFilters,
								"Classifier", UMLPackage.eINSTANCE.getClassifier()) {

							@Override
							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!redefinedClassifierEditUtil.getVirtualList().contains(elem))
										redefinedClassifierEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											EnumerationPropertiesEditionPartForm.this,
											UMLViewsRepository.Enumeration.redefinedClassifier,
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
				UMLViewsRepository.Enumeration.redefinedClassifier, UMLViewsRepository.FORM_KIND));
		this.redefinedClassifier.createControls(parent, widgetFactory);
		GridData redefinedClassifierData = new GridData(GridData.FILL_HORIZONTAL);
		redefinedClassifierData.horizontalSpan = 3;
		this.redefinedClassifier.setLayoutData(redefinedClassifierData);
		this.redefinedClassifier.disableMove();
	}

	/**
	 * 
	 */
	protected void moveRedefinedClassifier(Classifier element, int oldIndex, int newIndex) {
	}

	/**
	 * 
	 */
	protected void removeFromRedefinedClassifier(Classifier element) {

		// Start of user code for the removeFromRedefinedClassifier() method body

		EObject editedElement = redefinedClassifierEditUtil.foundCorrespondingEObject(element);
		redefinedClassifierEditUtil.removeElement(element);
		redefinedClassifier.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.redefinedClassifier,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editRedefinedClassifier(Classifier element) {

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
						EnumerationPropertiesEditionPartForm.this, UMLViewsRepository.Enumeration.redefinedClassifier,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getName()
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#setName(String
	 *      newValue)
	 */
	public void setName(String newValue) {
		name.setText(newValue);
	}

	public void setMessageForName(String msg, int msgLevel) {
		messageManager.addMessage("Name_key", msg, null, msgLevel, name);
	}

	public void unsetMessageForName() {
		messageManager.removeMessage("Name_key", name);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getVisibility()
	 */
	public Enumerator getVisibility() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) visibility.getSelection()).getFirstElement();
		return selection.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#initVisibility(EEnum
	 *      eenum, Enumerator current)
	 */
	public void initVisibility(EEnum eenum, Enumerator current) {
		visibility.setInput(eenum.getELiterals());
		visibility.modelUpdating(new StructuredSelection(current));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#setVisibility(Enumerator
	 *      newValue)
	 */
	public void setVisibility(Enumerator newValue) {
		visibility.modelUpdating(new StructuredSelection(newValue));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getIsAbstract()
	 */
	public Boolean getIsAbstract() {
		return Boolean.valueOf(isAbstract.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#setIsAbstract(Boolean
	 *      newValue)
	 */
	public void setIsAbstract(Boolean newValue) {
		if (newValue != null) {
			isAbstract.setSelection(newValue.booleanValue());
		} else {
			isAbstract.setSelection(false);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getIsLeaf()
	 */
	public Boolean getIsLeaf() {
		return Boolean.valueOf(isLeaf.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#setIsLeaf(Boolean
	 *      newValue)
	 */
	public void setIsLeaf(Boolean newValue) {
		if (newValue != null) {
			isLeaf.setSelection(newValue.booleanValue());
		} else {
			isLeaf.setSelection(false);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getGeneralizationToAdd()
	 */
	public List getGeneralizationToAdd() {
		return generalizationEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getGeneralizationToRemove()
	 */
	public List getGeneralizationToRemove() {
		return generalizationEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getGeneralizationToEdit()
	 */
	public Map getGeneralizationToEdit() {
		return generalizationEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getGeneralizationToMove()
	 */
	public List getGeneralizationToMove() {
		return generalizationEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getGeneralizationTable()
	 */
	public List getGeneralizationTable() {
		return generalizationEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#initGeneralization(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#updateGeneralization(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addFilterGeneralization(ViewerFilter
	 *      filter)
	 */
	public void addFilterToGeneralization(ViewerFilter filter) {
		generalizationFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addBusinessFilterGeneralization(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToGeneralization(ViewerFilter filter) {
		generalizationBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getTemplateBindingToAdd()
	 */
	public List getTemplateBindingToAdd() {
		return templateBindingEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getTemplateBindingToRemove()
	 */
	public List getTemplateBindingToRemove() {
		return templateBindingEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getTemplateBindingToEdit()
	 */
	public Map getTemplateBindingToEdit() {
		return templateBindingEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getTemplateBindingToMove()
	 */
	public List getTemplateBindingToMove() {
		return templateBindingEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getTemplateBindingTable()
	 */
	public List getTemplateBindingTable() {
		return templateBindingEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#initTemplateBinding(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#updateTemplateBinding(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addFilterTemplateBinding(ViewerFilter
	 *      filter)
	 */
	public void addFilterToTemplateBinding(ViewerFilter filter) {
		templateBindingFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addBusinessFilterTemplateBinding(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToTemplateBinding(ViewerFilter filter) {
		templateBindingBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getElementImportToAdd()
	 */
	public List getElementImportToAdd() {
		return elementImportEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getElementImportToRemove()
	 */
	public List getElementImportToRemove() {
		return elementImportEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getElementImportToEdit()
	 */
	public Map getElementImportToEdit() {
		return elementImportEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getElementImportToMove()
	 */
	public List getElementImportToMove() {
		return elementImportEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getElementImportTable()
	 */
	public List getElementImportTable() {
		return elementImportEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#initElementImport(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#updateElementImport(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addFilterElementImport(ViewerFilter
	 *      filter)
	 */
	public void addFilterToElementImport(ViewerFilter filter) {
		elementImportFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addBusinessFilterElementImport(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToElementImport(ViewerFilter filter) {
		elementImportBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getPackageImportToAdd()
	 */
	public List getPackageImportToAdd() {
		return packageImportEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getPackageImportToRemove()
	 */
	public List getPackageImportToRemove() {
		return packageImportEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getPackageImportToEdit()
	 */
	public Map getPackageImportToEdit() {
		return packageImportEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getPackageImportToMove()
	 */
	public List getPackageImportToMove() {
		return packageImportEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getPackageImportTable()
	 */
	public List getPackageImportTable() {
		return packageImportEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#initPackageImport(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#updatePackageImport(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addFilterPackageImport(ViewerFilter
	 *      filter)
	 */
	public void addFilterToPackageImport(ViewerFilter filter) {
		packageImportFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addBusinessFilterPackageImport(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToPackageImport(ViewerFilter filter) {
		packageImportBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedRuleToAdd()
	 */
	public List getOwnedRuleToAdd() {
		return ownedRuleEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedRuleToRemove()
	 */
	public List getOwnedRuleToRemove() {
		return ownedRuleEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedRuleToEdit()
	 */
	public Map getOwnedRuleToEdit() {
		return ownedRuleEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedRuleToMove()
	 */
	public List getOwnedRuleToMove() {
		return ownedRuleEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedRuleTable()
	 */
	public List getOwnedRuleTable() {
		return ownedRuleEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#initOwnedRule(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#updateOwnedRule(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addFilterOwnedRule(ViewerFilter
	 *      filter)
	 */
	public void addFilterToOwnedRule(ViewerFilter filter) {
		ownedRuleFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addBusinessFilterOwnedRule(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToOwnedRule(ViewerFilter filter) {
		ownedRuleBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getPowertypeExtentToAdd()
	 */
	public List getPowertypeExtentToAdd() {
		return powertypeExtentEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getPowertypeExtentToRemove()
	 */
	public List getPowertypeExtentToRemove() {
		return powertypeExtentEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getPowertypeExtentTable()
	 */
	public List getPowertypeExtentTable() {
		return powertypeExtentEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#initPowertypeExtent(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#updatePowertypeExtent(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addFilterPowertypeExtent(ViewerFilter
	 *      filter)
	 */
	public void addFilterToPowertypeExtent(ViewerFilter filter) {
		powertypeExtentFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addBusinessFilterPowertypeExtent(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToPowertypeExtent(ViewerFilter filter) {
		powertypeExtentBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedAttributeToAdd()
	 */
	public List getOwnedAttributeToAdd() {
		return ownedAttributeEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedAttributeToRemove()
	 */
	public List getOwnedAttributeToRemove() {
		return ownedAttributeEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedAttributeToEdit()
	 */
	public Map getOwnedAttributeToEdit() {
		return ownedAttributeEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedAttributeToMove()
	 */
	public List getOwnedAttributeToMove() {
		return ownedAttributeEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedAttributeTable()
	 */
	public List getOwnedAttributeTable() {
		return ownedAttributeEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#initOwnedAttribute(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#updateOwnedAttribute(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addFilterOwnedAttribute(ViewerFilter
	 *      filter)
	 */
	public void addFilterToOwnedAttribute(ViewerFilter filter) {
		ownedAttributeFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addBusinessFilterOwnedAttribute(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToOwnedAttribute(ViewerFilter filter) {
		ownedAttributeBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedOperationToAdd()
	 */
	public List getOwnedOperationToAdd() {
		return ownedOperationEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedOperationToRemove()
	 */
	public List getOwnedOperationToRemove() {
		return ownedOperationEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedOperationToEdit()
	 */
	public Map getOwnedOperationToEdit() {
		return ownedOperationEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedOperationToMove()
	 */
	public List getOwnedOperationToMove() {
		return ownedOperationEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedOperationTable()
	 */
	public List getOwnedOperationTable() {
		return ownedOperationEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#initOwnedOperation(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#updateOwnedOperation(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addFilterOwnedOperation(ViewerFilter
	 *      filter)
	 */
	public void addFilterToOwnedOperation(ViewerFilter filter) {
		ownedOperationFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addBusinessFilterOwnedOperation(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToOwnedOperation(ViewerFilter filter) {
		ownedOperationBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedLiteralToAdd()
	 */
	public List getOwnedLiteralToAdd() {
		return ownedLiteralEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedLiteralToRemove()
	 */
	public List getOwnedLiteralToRemove() {
		return ownedLiteralEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedLiteralToEdit()
	 */
	public Map getOwnedLiteralToEdit() {
		return ownedLiteralEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedLiteralToMove()
	 */
	public List getOwnedLiteralToMove() {
		return ownedLiteralEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedLiteralTable()
	 */
	public List getOwnedLiteralTable() {
		return ownedLiteralEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#initOwnedLiteral(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initOwnedLiteral(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			ownedLiteralEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			ownedLiteralEditUtil = new EMFListEditUtil(current, feature);
		this.ownedLiteral.setInput(ownedLiteralEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#updateOwnedLiteral(EObject
	 *      newValue)
	 */
	public void updateOwnedLiteral(EObject newValue) {
		if (ownedLiteralEditUtil != null) {
			ownedLiteralEditUtil.reinit(newValue);
			ownedLiteral.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addFilterOwnedLiteral(ViewerFilter
	 *      filter)
	 */
	public void addFilterToOwnedLiteral(ViewerFilter filter) {
		ownedLiteralFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addBusinessFilterOwnedLiteral(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToOwnedLiteral(ViewerFilter filter) {
		ownedLiteralBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getUseCaseToAdd()
	 */
	public List getUseCaseToAdd() {
		return useCaseEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getUseCaseToRemove()
	 */
	public List getUseCaseToRemove() {
		return useCaseEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getUseCaseTable()
	 */
	public List getUseCaseTable() {
		return useCaseEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#initUseCase(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#updateUseCase(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addFilterUseCase(ViewerFilter
	 *      filter)
	 */
	public void addFilterToUseCase(ViewerFilter filter) {
		useCaseFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addBusinessFilterUseCase(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToUseCase(ViewerFilter filter) {
		useCaseBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedUseCaseToAdd()
	 */
	public List getOwnedUseCaseToAdd() {
		return ownedUseCaseEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedUseCaseToRemove()
	 */
	public List getOwnedUseCaseToRemove() {
		return ownedUseCaseEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedUseCaseToEdit()
	 */
	public Map getOwnedUseCaseToEdit() {
		return ownedUseCaseEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedUseCaseToMove()
	 */
	public List getOwnedUseCaseToMove() {
		return ownedUseCaseEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getOwnedUseCaseTable()
	 */
	public List getOwnedUseCaseTable() {
		return ownedUseCaseEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#initOwnedUseCase(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#updateOwnedUseCase(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addFilterOwnedUseCase(ViewerFilter
	 *      filter)
	 */
	public void addFilterToOwnedUseCase(ViewerFilter filter) {
		ownedUseCaseFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addBusinessFilterOwnedUseCase(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToOwnedUseCase(ViewerFilter filter) {
		ownedUseCaseBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getCollaborationUseToAdd()
	 */
	public List getCollaborationUseToAdd() {
		return collaborationUseEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getCollaborationUseToRemove()
	 */
	public List getCollaborationUseToRemove() {
		return collaborationUseEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getCollaborationUseToEdit()
	 */
	public Map getCollaborationUseToEdit() {
		return collaborationUseEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getCollaborationUseToMove()
	 */
	public List getCollaborationUseToMove() {
		return collaborationUseEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getCollaborationUseTable()
	 */
	public List getCollaborationUseTable() {
		return collaborationUseEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#initCollaborationUse(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#updateCollaborationUse(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addFilterCollaborationUse(ViewerFilter
	 *      filter)
	 */
	public void addFilterToCollaborationUse(ViewerFilter filter) {
		collaborationUseFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addBusinessFilterCollaborationUse(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToCollaborationUse(ViewerFilter filter) {
		collaborationUseBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getSubstitutionToAdd()
	 */
	public List getSubstitutionToAdd() {
		return substitutionEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getSubstitutionToRemove()
	 */
	public List getSubstitutionToRemove() {
		return substitutionEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getSubstitutionToEdit()
	 */
	public Map getSubstitutionToEdit() {
		return substitutionEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getSubstitutionToMove()
	 */
	public List getSubstitutionToMove() {
		return substitutionEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getSubstitutionTable()
	 */
	public List getSubstitutionTable() {
		return substitutionEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#initSubstitution(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#updateSubstitution(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addFilterSubstitution(ViewerFilter
	 *      filter)
	 */
	public void addFilterToSubstitution(ViewerFilter filter) {
		substitutionFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addBusinessFilterSubstitution(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToSubstitution(ViewerFilter filter) {
		substitutionBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getRedefinedClassifierToAdd()
	 */
	public List getRedefinedClassifierToAdd() {
		return redefinedClassifierEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getRedefinedClassifierToRemove()
	 */
	public List getRedefinedClassifierToRemove() {
		return redefinedClassifierEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#getRedefinedClassifierTable()
	 */
	public List getRedefinedClassifierTable() {
		return redefinedClassifierEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#initRedefinedClassifier(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#updateRedefinedClassifier(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addFilterRedefinedClassifier(ViewerFilter
	 *      filter)
	 */
	public void addFilterToRedefinedClassifier(ViewerFilter filter) {
		redefinedClassifierFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.EnumerationPropertiesEditionPart#addBusinessFilterRedefinedClassifier(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToRedefinedClassifier(ViewerFilter filter) {
		redefinedClassifierBusinessFilters.add(filter);
	}

	// Start of user code additional methods

	// End of user code

}
