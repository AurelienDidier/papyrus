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
import org.eclipse.emf.eef.runtime.api.parts.EEFMessageManager;
import org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.api.policies.IPropertiesEditionPolicy;
import org.eclipse.emf.eef.runtime.api.providers.IPropertiesEditionPolicyProvider;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.parts.CompositePropertiesEditionPart;
import org.eclipse.emf.eef.runtime.impl.policies.EObjectPropertiesEditionContext;
import org.eclipse.emf.eef.runtime.impl.services.PropertiesEditionPolicyProviderService;
import org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil;
import org.eclipse.emf.eef.runtime.ui.widgets.EMFComboViewer;
import org.eclipse.emf.eef.runtime.ui.widgets.FormUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.HorizontalBox;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.TabElementTreeSelectionDialog;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart;
import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;
import org.eclipse.papyrus.tabbedproperties.uml.providers.UMLMessages;
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
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IMessageManager;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.CollaborationUse;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.GeneralizationSet;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Substitution;
import org.eclipse.uml2.uml.TemplateBinding;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UseCase;

// End of user code

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class AssociationPropertiesEditionPartForm extends CompositePropertiesEditionPart implements IFormPropertiesEditionPart, AssociationPropertiesEditionPart {

	protected Text name;
	protected EMFComboViewer visibility;
	protected Button isDerived;
	protected Button isAbstract;
	protected Button isLeaf;
	protected EMFListEditUtil ownedEndEditUtil;
	protected ReferencesTable<? extends EObject> ownedEnd;
	protected List<ViewerFilter> ownedEndBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> ownedEndFilters = new ArrayList<ViewerFilter>();
	private EMFListEditUtil memberEndEditUtil;
	protected ReferencesTable<? extends EObject> memberEnd;
	protected List<ViewerFilter> memberEndBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> memberEndFilters = new ArrayList<ViewerFilter>();
	private EMFListEditUtil navigableOwnedEndEditUtil;
	protected ReferencesTable<? extends EObject> navigableOwnedEnd;
	protected List<ViewerFilter> navigableOwnedEndBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> navigableOwnedEndFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil templateBindingEditUtil;
	protected ReferencesTable<? extends EObject> templateBinding;
	protected List<ViewerFilter> templateBindingBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> templateBindingFilters = new ArrayList<ViewerFilter>();
	private EMFListEditUtil useCaseEditUtil;
	protected ReferencesTable<? extends EObject> useCase;
	protected List<ViewerFilter> useCaseBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> useCaseFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil ownedUseCaseEditUtil;
	protected ReferencesTable<? extends EObject> ownedUseCase;
	protected List<ViewerFilter> ownedUseCaseBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> ownedUseCaseFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil collaborationUseEditUtil;
	protected ReferencesTable<? extends EObject> collaborationUse;
	protected List<ViewerFilter> collaborationUseBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> collaborationUseFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil substitutionEditUtil;
	protected ReferencesTable<? extends EObject> substitution;
	protected List<ViewerFilter> substitutionBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> substitutionFilters = new ArrayList<ViewerFilter>();
	private EMFListEditUtil redefinedClassifierEditUtil;
	protected ReferencesTable<? extends EObject> redefinedClassifier;
	protected List<ViewerFilter> redefinedClassifierBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> redefinedClassifierFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil generalizationEditUtil;
	protected ReferencesTable<? extends EObject> generalization;
	protected List<ViewerFilter> generalizationBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> generalizationFilters = new ArrayList<ViewerFilter>();
	private EMFListEditUtil powertypeExtentEditUtil;
	protected ReferencesTable<? extends EObject> powertypeExtent;
	protected List<ViewerFilter> powertypeExtentBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> powertypeExtentFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil elementImportEditUtil;
	protected ReferencesTable<? extends EObject> elementImport;
	protected List<ViewerFilter> elementImportBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> elementImportFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil packageImportEditUtil;
	protected ReferencesTable<? extends EObject> packageImport;
	protected List<ViewerFilter> packageImportBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> packageImportFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil ownedRuleEditUtil;
	protected ReferencesTable<? extends EObject> ownedRule;
	protected List<ViewerFilter> ownedRuleBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> ownedRuleFilters = new ArrayList<ViewerFilter>();




	
	/**
	 * Default constructor
	 * @param editionComponent the {@link IPropertiesEditionComponent} that manage this part
	 */
	public AssociationPropertiesEditionPartForm(IPropertiesEditionComponent editionComponent) {
		super(editionComponent);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart#
	 * 			createFigure(org.eclipse.swt.widgets.Composite, org.eclipse.ui.forms.widgets.FormToolkit)
	 */
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
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart#
	 * 			createControls(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite, org.eclipse.ui.forms.IMessageManager)
	 */
	public void createControls(final FormToolkit widgetFactory, Composite view, IMessageManager messageManager) {
		this.messageManager = messageManager;
		createGeneralGroup(widgetFactory, view);
		createOthersGroup(widgetFactory, view);
		// Start of user code for additional ui definition

		// End of user code
		
	}

	protected void createGeneralGroup(FormToolkit widgetFactory, final Composite view) {
		Section generalSection = widgetFactory.createSection(view, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		generalSection.setText(UMLMessages.AssociationPropertiesEditionPart_GeneralGroupLabel);
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
		createGeneralHBox2HBox(widgetFactory, generalGroup);
		generalSection.setClient(generalGroup);
	}
	protected void createNameText(FormToolkit widgetFactory, Composite parent) {
		FormUtils.createPartLabel(widgetFactory, parent, UMLMessages.AssociationPropertiesEditionPart_NameLabel, propertiesEditionComponent.isRequired(UMLViewsRepository.Association.name, UMLViewsRepository.FORM_KIND));
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.name, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, null, name.getText()));
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.name, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
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
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.name, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
				}
			}

		});
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.name, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}
	protected void createVisibilityEMFComboViewer(FormToolkit widgetFactory, Composite parent) {
		FormUtils.createPartLabel(widgetFactory, parent, UMLMessages.AssociationPropertiesEditionPart_VisibilityLabel, propertiesEditionComponent.isRequired(UMLViewsRepository.Association.visibility, UMLViewsRepository.FORM_KIND));
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.visibility, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, getVisibility()));
			}

		});
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.visibility, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}
	protected void createGeneralHBox1HBox(FormToolkit widgetFactory, Composite parent) {
		Composite container = widgetFactory.createComposite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan=3;
		container.setLayoutData(gridData);
		HorizontalBox generalHBox1HBox = new HorizontalBox(container);
		//Apply constraint for checkbox
		GridData constraint = new GridData(GridData.FILL_HORIZONTAL);
		constraint.horizontalAlignment = GridData.BEGINNING;
		generalHBox1HBox.setLayoutData(constraint);
		//create sub figures
				createIsDerivedCheckbox(widgetFactory, generalHBox1HBox);
				createIsAbstractCheckbox(widgetFactory, generalHBox1HBox);
				createIsLeafCheckbox(widgetFactory, generalHBox1HBox);
		container.pack();
	}
	protected void createIsDerivedCheckbox(FormToolkit widgetFactory, Composite parent) {
		isDerived = widgetFactory.createButton(parent, UMLMessages.AssociationPropertiesEditionPart_IsDerivedLabel, SWT.CHECK);
		isDerived.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.isDerived, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(isDerived.getSelection())));
			}

		});
		GridData isDerivedData = new GridData(GridData.FILL_HORIZONTAL);
		isDerivedData.horizontalSpan = 2;
		isDerived.setLayoutData(isDerivedData);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.isDerived, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}
	protected void createIsAbstractCheckbox(FormToolkit widgetFactory, Composite parent) {
		isAbstract = widgetFactory.createButton(parent, UMLMessages.AssociationPropertiesEditionPart_IsAbstractLabel, SWT.CHECK);
		isAbstract.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.isAbstract, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(isAbstract.getSelection())));
			}

		});
		GridData isAbstractData = new GridData(GridData.FILL_HORIZONTAL);
		isAbstractData.horizontalSpan = 2;
		isAbstract.setLayoutData(isAbstractData);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.isAbstract, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}
	protected void createIsLeafCheckbox(FormToolkit widgetFactory, Composite parent) {
		isLeaf = widgetFactory.createButton(parent, UMLMessages.AssociationPropertiesEditionPart_IsLeafLabel, SWT.CHECK);
		isLeaf.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.isLeaf, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(isLeaf.getSelection())));
			}

		});
		GridData isLeafData = new GridData(GridData.FILL_HORIZONTAL);
		isLeafData.horizontalSpan = 2;
		isLeaf.setLayoutData(isLeafData);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.isLeaf, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}
	protected void createGeneralHBox2HBox(FormToolkit widgetFactory, Composite parent) {
		Composite container = widgetFactory.createComposite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan=3;
		container.setLayoutData(gridData);
		HorizontalBox generalHBox2HBox = new HorizontalBox(container);
		//create sub figures
				createOwnedEndTableComposition(widgetFactory, generalHBox2HBox);
				createMemberEndReferencesTable(widgetFactory, generalHBox2HBox);
				createNavigableOwnedEndReferencesTable(widgetFactory, generalHBox2HBox);
		container.pack();
	}
	/**
	 * @param container
	 */
	protected void createOwnedEndTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.ownedEnd = new ReferencesTable<Property>(UMLMessages.AssociationPropertiesEditionPart_OwnedEndLabel, new ReferencesTableListener<Property>() {			
			public void handleAdd() { addToOwnedEnd();}
			public void handleEdit(Property element) { editOwnedEnd(element); }
			public void handleMove(Property element, int oldIndex, int newIndex) { moveOwnedEnd(element, oldIndex, newIndex); }
			public void handleRemove(Property element) { removeFromOwnedEnd(element); }
			public void navigateTo(Property element) { }
		});
		this.ownedEnd.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.ownedEnd, UMLViewsRepository.FORM_KIND));
		this.ownedEnd.createControls(parent, widgetFactory);
		GridData ownedEndData = new GridData(GridData.FILL_HORIZONTAL);
		ownedEndData.horizontalSpan = 3;
		this.ownedEnd.setLayoutData(ownedEndData);
	}

	/**
	 * 
	 */
	protected void moveOwnedEnd(Property element, int oldIndex, int newIndex) {
		EObject editedElement = ownedEndEditUtil.foundCorrespondingEObject(element);
		ownedEndEditUtil.moveElement(element, oldIndex, newIndex);
		ownedEnd.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.ownedEnd, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
	}

	/**
	 * 
	 */
	protected void addToOwnedEnd() {
		// Start of user code addToOwnedEnd() method body
		Property eObject = UMLFactory.eINSTANCE.createProperty();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				ownedEndEditUtil.addElement(propertiesEditionObject);
				ownedEnd.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.ownedEnd,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}
		// End of user code

	}

	/**
	 * 
	 */
	protected void removeFromOwnedEnd(Property element) {
		// Start of user code for the removeFromOwnedEnd() method body
		EObject editedElement = ownedEndEditUtil.foundCorrespondingEObject(element);
		ownedEndEditUtil.removeElement(element);
		ownedEnd.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.ownedEnd,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));
		// End of user code

	}

	/**
	 * 
	 */
	protected void editOwnedEnd(Property element) {
		// Start of user code editOwnedEnd() method body
		EObject editedElement = ownedEndEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedEndEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedEnd.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.ownedEnd,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}
		// End of user code

	}
	protected void createMemberEndReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.memberEnd = new ReferencesTable<Property>(UMLMessages.AssociationPropertiesEditionPart_MemberEndLabel, new ReferencesTableListener<Property>() {
			public void handleAdd() {
				TabElementTreeSelectionDialog<Property> dialog = new TabElementTreeSelectionDialog<Property>(resourceSet, memberEndFilters, memberEndBusinessFilters,
				"Property", UMLPackage.eINSTANCE.getProperty(), current.eResource()) {
					@Override
					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!memberEndEditUtil.getVirtualList().contains(elem))
								memberEndEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.memberEnd,
								PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
						}
						memberEnd.refresh();
					}
				};
				dialog.open();
			}
			public void handleEdit(Property element) { editMemberEnd(element); }
			public void handleMove(Property element, int oldIndex, int newIndex) { moveMemberEnd(element, oldIndex, newIndex); }
			public void handleRemove(Property element) { removeFromMemberEnd(element); }
			public void navigateTo(Property element) { }
		});
		this.memberEnd.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.memberEnd, UMLViewsRepository.FORM_KIND));
		this.memberEnd.createControls(parent, widgetFactory);
		GridData memberEndData = new GridData(GridData.FILL_HORIZONTAL);
		memberEndData.horizontalSpan = 3;
		this.memberEnd.setLayoutData(memberEndData);
		this.memberEnd.disableMove();
	}

	/**
	 * 
	 */
	protected void moveMemberEnd(Property element, int oldIndex, int newIndex) {
		EObject editedElement = memberEndEditUtil.foundCorrespondingEObject(element);
		memberEndEditUtil.moveElement(element, oldIndex, newIndex);
		memberEnd.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.memberEnd, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	protected void removeFromMemberEnd(Property element) {
		// Start of user code for the removeFromMemberEnd() method body
		EObject editedElement = memberEndEditUtil.foundCorrespondingEObject(element);
		memberEndEditUtil.removeElement(element);
		memberEnd.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.memberEnd,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));
		// End of user code

	}

	/**
	 * 
	 */
	protected void editMemberEnd(Property element) {
		// Start of user code editMemberEnd() method body

		EObject editedElement = memberEndEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				memberEndEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				memberEnd.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.memberEnd,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}
		// End of user code

	}
	protected void createNavigableOwnedEndReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.navigableOwnedEnd = new ReferencesTable<Property>(UMLMessages.AssociationPropertiesEditionPart_NavigableOwnedEndLabel, new ReferencesTableListener<Property>() {
			public void handleAdd() {
				TabElementTreeSelectionDialog<Property> dialog = new TabElementTreeSelectionDialog<Property>(resourceSet, navigableOwnedEndFilters, navigableOwnedEndBusinessFilters,
				"Property", UMLPackage.eINSTANCE.getProperty(), current.eResource()) {
					@Override
					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!navigableOwnedEndEditUtil.getVirtualList().contains(elem))
								navigableOwnedEndEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.navigableOwnedEnd,
								PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
						}
						navigableOwnedEnd.refresh();
					}
				};
				dialog.open();
			}
			public void handleEdit(Property element) { editNavigableOwnedEnd(element); }
			public void handleMove(Property element, int oldIndex, int newIndex) { moveNavigableOwnedEnd(element, oldIndex, newIndex); }
			public void handleRemove(Property element) { removeFromNavigableOwnedEnd(element); }
			public void navigateTo(Property element) { }
		});
		this.navigableOwnedEnd.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.navigableOwnedEnd, UMLViewsRepository.FORM_KIND));
		this.navigableOwnedEnd.createControls(parent, widgetFactory);
		GridData navigableOwnedEndData = new GridData(GridData.FILL_HORIZONTAL);
		navigableOwnedEndData.horizontalSpan = 3;
		this.navigableOwnedEnd.setLayoutData(navigableOwnedEndData);
		this.navigableOwnedEnd.disableMove();
	}

	/**
	 * 
	 */
	protected void moveNavigableOwnedEnd(Property element, int oldIndex, int newIndex) {
		EObject editedElement = navigableOwnedEndEditUtil.foundCorrespondingEObject(element);
		navigableOwnedEndEditUtil.moveElement(element, oldIndex, newIndex);
		navigableOwnedEnd.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.navigableOwnedEnd, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	protected void removeFromNavigableOwnedEnd(Property element) {
		// Start of user code for the removeFromNavigableOwnedEnd() method body
		EObject editedElement = navigableOwnedEndEditUtil.foundCorrespondingEObject(element);
		navigableOwnedEndEditUtil.removeElement(element);
		navigableOwnedEnd.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.navigableOwnedEnd,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));
		// End of user code

	}

	/**
	 * 
	 */
	protected void editNavigableOwnedEnd(Property element) {
		// Start of user code editNavigableOwnedEnd() method body

		EObject editedElement = navigableOwnedEndEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				navigableOwnedEndEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				navigableOwnedEnd.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.navigableOwnedEnd,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}
		// End of user code

	}
	protected void createOthersGroup(FormToolkit widgetFactory, final Composite view) {
		Section othersSection = widgetFactory.createSection(view, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		othersSection.setText(UMLMessages.AssociationPropertiesEditionPart_OthersGroupLabel);
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
		gridData.horizontalSpan=3;
		container.setLayoutData(gridData);
		HorizontalBox othersHBox1HBox = new HorizontalBox(container);
		//create sub figures
				createTemplateBindingTableComposition(widgetFactory, othersHBox1HBox);
				createUseCaseReferencesTable(widgetFactory, othersHBox1HBox);
				createOwnedUseCaseTableComposition(widgetFactory, othersHBox1HBox);
				createCollaborationUseTableComposition(widgetFactory, othersHBox1HBox);
				createSubstitutionTableComposition(widgetFactory, othersHBox1HBox);
				createRedefinedClassifierReferencesTable(widgetFactory, othersHBox1HBox);
				createGeneralizationTableComposition(widgetFactory, othersHBox1HBox);
				createPowertypeExtentReferencesTable(widgetFactory, othersHBox1HBox);
				createElementImportTableComposition(widgetFactory, othersHBox1HBox);
				createPackageImportTableComposition(widgetFactory, othersHBox1HBox);
				createOwnedRuleTableComposition(widgetFactory, othersHBox1HBox);
		container.pack();
	}
	/**
	 * @param container
	 */
	protected void createTemplateBindingTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.templateBinding = new ReferencesTable<TemplateBinding>(UMLMessages.AssociationPropertiesEditionPart_TemplateBindingLabel, new ReferencesTableListener<TemplateBinding>() {			
			public void handleAdd() { addToTemplateBinding();}
			public void handleEdit(TemplateBinding element) { editTemplateBinding(element); }
			public void handleMove(TemplateBinding element, int oldIndex, int newIndex) { moveTemplateBinding(element, oldIndex, newIndex); }
			public void handleRemove(TemplateBinding element) { removeFromTemplateBinding(element); }
			public void navigateTo(TemplateBinding element) { }
		});
		this.templateBinding.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.templateBinding, UMLViewsRepository.FORM_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.templateBinding, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.templateBinding,
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
				AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.templateBinding,
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.templateBinding,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}
		// End of user code

	}
	protected void createUseCaseReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.useCase = new ReferencesTable<UseCase>(UMLMessages.AssociationPropertiesEditionPart_UseCaseLabel, new ReferencesTableListener<UseCase>() {
			public void handleAdd() {
				TabElementTreeSelectionDialog<UseCase> dialog = new TabElementTreeSelectionDialog<UseCase>(resourceSet, useCaseFilters, useCaseBusinessFilters,
				"UseCase", UMLPackage.eINSTANCE.getUseCase(), current.eResource()) {
					@Override
					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!useCaseEditUtil.getVirtualList().contains(elem))
								useCaseEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.useCase,
								PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
						}
						useCase.refresh();
					}
				};
				dialog.open();
			}
			public void handleEdit(UseCase element) { editUseCase(element); }
			public void handleMove(UseCase element, int oldIndex, int newIndex) { moveUseCase(element, oldIndex, newIndex); }
			public void handleRemove(UseCase element) { removeFromUseCase(element); }
			public void navigateTo(UseCase element) { }
		});
		this.useCase.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.useCase, UMLViewsRepository.FORM_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.useCase, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
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
				AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.useCase,
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.useCase,
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
		this.ownedUseCase = new ReferencesTable<UseCase>(UMLMessages.AssociationPropertiesEditionPart_OwnedUseCaseLabel, new ReferencesTableListener<UseCase>() {			
			public void handleAdd() { addToOwnedUseCase();}
			public void handleEdit(UseCase element) { editOwnedUseCase(element); }
			public void handleMove(UseCase element, int oldIndex, int newIndex) { moveOwnedUseCase(element, oldIndex, newIndex); }
			public void handleRemove(UseCase element) { removeFromOwnedUseCase(element); }
			public void navigateTo(UseCase element) { }
		});
		this.ownedUseCase.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.ownedUseCase, UMLViewsRepository.FORM_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.ownedUseCase, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.ownedUseCase,
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
				AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.ownedUseCase,
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.ownedUseCase,
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
		this.collaborationUse = new ReferencesTable<CollaborationUse>(UMLMessages.AssociationPropertiesEditionPart_CollaborationUseLabel, new ReferencesTableListener<CollaborationUse>() {			
			public void handleAdd() { addToCollaborationUse();}
			public void handleEdit(CollaborationUse element) { editCollaborationUse(element); }
			public void handleMove(CollaborationUse element, int oldIndex, int newIndex) { moveCollaborationUse(element, oldIndex, newIndex); }
			public void handleRemove(CollaborationUse element) { removeFromCollaborationUse(element); }
			public void navigateTo(CollaborationUse element) { }
		});
		this.collaborationUse.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.collaborationUse, UMLViewsRepository.FORM_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.collaborationUse, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.collaborationUse,
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
				AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.collaborationUse,
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.collaborationUse,
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
		this.substitution = new ReferencesTable<Substitution>(UMLMessages.AssociationPropertiesEditionPart_SubstitutionLabel, new ReferencesTableListener<Substitution>() {			
			public void handleAdd() { addToSubstitution();}
			public void handleEdit(Substitution element) { editSubstitution(element); }
			public void handleMove(Substitution element, int oldIndex, int newIndex) { moveSubstitution(element, oldIndex, newIndex); }
			public void handleRemove(Substitution element) { removeFromSubstitution(element); }
			public void navigateTo(Substitution element) { }
		});
		this.substitution.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.substitution, UMLViewsRepository.FORM_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.substitution, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.substitution,
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
				AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.substitution,
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.substitution,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}
		// End of user code

	}
	protected void createRedefinedClassifierReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.redefinedClassifier = new ReferencesTable<Classifier>(UMLMessages.AssociationPropertiesEditionPart_RedefinedClassifierLabel, new ReferencesTableListener<Classifier>() {
			public void handleAdd() {
				TabElementTreeSelectionDialog<Classifier> dialog = new TabElementTreeSelectionDialog<Classifier>(resourceSet, redefinedClassifierFilters, redefinedClassifierBusinessFilters,
				"Classifier", UMLPackage.eINSTANCE.getClassifier(), current.eResource()) {
					@Override
					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!redefinedClassifierEditUtil.getVirtualList().contains(elem))
								redefinedClassifierEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.redefinedClassifier,
								PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
						}
						redefinedClassifier.refresh();
					}
				};
				dialog.open();
			}
			public void handleEdit(Classifier element) { editRedefinedClassifier(element); }
			public void handleMove(Classifier element, int oldIndex, int newIndex) { moveRedefinedClassifier(element, oldIndex, newIndex); }
			public void handleRemove(Classifier element) { removeFromRedefinedClassifier(element); }
			public void navigateTo(Classifier element) { }
		});
		this.redefinedClassifier.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.redefinedClassifier, UMLViewsRepository.FORM_KIND));
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
				AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.redefinedClassifier,
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.redefinedClassifier,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}
		// End of user code

	}
	/**
	 * @param container
	 */
	protected void createGeneralizationTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.generalization = new ReferencesTable<Generalization>(UMLMessages.AssociationPropertiesEditionPart_GeneralizationLabel, new ReferencesTableListener<Generalization>() {			
			public void handleAdd() { addToGeneralization();}
			public void handleEdit(Generalization element) { editGeneralization(element); }
			public void handleMove(Generalization element, int oldIndex, int newIndex) { moveGeneralization(element, oldIndex, newIndex); }
			public void handleRemove(Generalization element) { removeFromGeneralization(element); }
			public void navigateTo(Generalization element) { }
		});
		this.generalization.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.generalization, UMLViewsRepository.FORM_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.generalization, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.generalization,
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
				AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.generalization,
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.generalization,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}
		// End of user code

	}
	protected void createPowertypeExtentReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.powertypeExtent = new ReferencesTable<GeneralizationSet>(UMLMessages.AssociationPropertiesEditionPart_PowertypeExtentLabel, new ReferencesTableListener<GeneralizationSet>() {
			public void handleAdd() {
				TabElementTreeSelectionDialog<GeneralizationSet> dialog = new TabElementTreeSelectionDialog<GeneralizationSet>(resourceSet, powertypeExtentFilters, powertypeExtentBusinessFilters,
				"GeneralizationSet", UMLPackage.eINSTANCE.getGeneralizationSet(), current.eResource()) {
					@Override
					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!powertypeExtentEditUtil.getVirtualList().contains(elem))
								powertypeExtentEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.powertypeExtent,
								PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
						}
						powertypeExtent.refresh();
					}
				};
				dialog.open();
			}
			public void handleEdit(GeneralizationSet element) { editPowertypeExtent(element); }
			public void handleMove(GeneralizationSet element, int oldIndex, int newIndex) { movePowertypeExtent(element, oldIndex, newIndex); }
			public void handleRemove(GeneralizationSet element) { removeFromPowertypeExtent(element); }
			public void navigateTo(GeneralizationSet element) { }
		});
		this.powertypeExtent.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.powertypeExtent, UMLViewsRepository.FORM_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.powertypeExtent, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
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
				AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.powertypeExtent,
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.powertypeExtent,
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
		this.elementImport = new ReferencesTable<ElementImport>(UMLMessages.AssociationPropertiesEditionPart_ElementImportLabel, new ReferencesTableListener<ElementImport>() {			
			public void handleAdd() { addToElementImport();}
			public void handleEdit(ElementImport element) { editElementImport(element); }
			public void handleMove(ElementImport element, int oldIndex, int newIndex) { moveElementImport(element, oldIndex, newIndex); }
			public void handleRemove(ElementImport element) { removeFromElementImport(element); }
			public void navigateTo(ElementImport element) { }
		});
		this.elementImport.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.elementImport, UMLViewsRepository.FORM_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.elementImport, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.elementImport,
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
				AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.elementImport,
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.elementImport,
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
		this.packageImport = new ReferencesTable<PackageImport>(UMLMessages.AssociationPropertiesEditionPart_PackageImportLabel, new ReferencesTableListener<PackageImport>() {			
			public void handleAdd() { addToPackageImport();}
			public void handleEdit(PackageImport element) { editPackageImport(element); }
			public void handleMove(PackageImport element, int oldIndex, int newIndex) { movePackageImport(element, oldIndex, newIndex); }
			public void handleRemove(PackageImport element) { removeFromPackageImport(element); }
			public void navigateTo(PackageImport element) { }
		});
		this.packageImport.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.packageImport, UMLViewsRepository.FORM_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.packageImport, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.packageImport,
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
				AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.packageImport,
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.packageImport,
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
		this.ownedRule = new ReferencesTable<Constraint>(UMLMessages.AssociationPropertiesEditionPart_OwnedRuleLabel, new ReferencesTableListener<Constraint>() {			
			public void handleAdd() { addToOwnedRule();}
			public void handleEdit(Constraint element) { editOwnedRule(element); }
			public void handleMove(Constraint element, int oldIndex, int newIndex) { moveOwnedRule(element, oldIndex, newIndex); }
			public void handleRemove(Constraint element) { removeFromOwnedRule(element); }
			public void navigateTo(Constraint element) { }
		});
		this.ownedRule.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Association.ownedRule, UMLViewsRepository.FORM_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.ownedRule, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.ownedRule,
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
				AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.ownedRule,
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
						AssociationPropertiesEditionPartForm.this, UMLViewsRepository.Association.ownedRule,
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getName()
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#setName(String newValue)
	 */
	public void setName(String newValue) {
		if (newValue != null) {
			name.setText(newValue);
		} else {
			name.setText("");  //$NON-NLS-1$
		}
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getVisibility()
	 */
	public Enumerator getVisibility() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) visibility.getSelection()).getFirstElement();
		return selection.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#initVisibility(EEnum eenum, Enumerator current)
	 */
	public void initVisibility(EEnum eenum, Enumerator current) {
		visibility.setInput(eenum.getELiterals());
		visibility.modelUpdating(new StructuredSelection(current));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#setVisibility(Enumerator newValue)
	 */
	public void setVisibility(Enumerator newValue) {
		visibility.modelUpdating(new StructuredSelection(newValue));
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getIsDerived()
	 */
	public Boolean getIsDerived() {
		return Boolean.valueOf(isDerived.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#setIsDerived(Boolean newValue)
	 */
	public void setIsDerived(Boolean newValue) {
		if (newValue != null) {
			isDerived.setSelection(newValue.booleanValue());
		} else {
			isDerived.setSelection(false);
		}
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getIsAbstract()
	 */
	public Boolean getIsAbstract() {
		return Boolean.valueOf(isAbstract.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#setIsAbstract(Boolean newValue)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getIsLeaf()
	 */
	public Boolean getIsLeaf() {
		return Boolean.valueOf(isLeaf.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#setIsLeaf(Boolean newValue)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getOwnedEndToAdd()
	 */
	public List getOwnedEndToAdd() {
		return ownedEndEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getOwnedEndToRemove()
	 */
	public List getOwnedEndToRemove() {
		return ownedEndEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getOwnedEndToEdit()
	 */
	public Map getOwnedEndToEdit() {
		return ownedEndEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getOwnedEndToMove()
	 */
	public List getOwnedEndToMove() {
		return ownedEndEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getOwnedEndTable()
	 */
	public List getOwnedEndTable() {
		return ownedEndEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#initOwnedEnd(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initOwnedEnd(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			ownedEndEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			ownedEndEditUtil = new EMFListEditUtil(current, feature);
		this.ownedEnd.setInput(ownedEndEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#updateOwnedEnd(EObject newValue)
	 */
	public void updateOwnedEnd(EObject newValue) {
		if(ownedEndEditUtil != null){
			ownedEndEditUtil.reinit(newValue);
			ownedEnd.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addFilterOwnedEnd(ViewerFilter filter)
	 */
	public void addFilterToOwnedEnd(ViewerFilter filter) {
		ownedEndFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addBusinessFilterOwnedEnd(ViewerFilter filter)
	 */
	public void addBusinessFilterToOwnedEnd(ViewerFilter filter) {
		ownedEndBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#isContainedInOwnedEndTable(EObject element)
	 */
	public boolean isContainedInOwnedEndTable(EObject element) {
		return ownedEndEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getMemberEndToAdd()
	 */
	public List getMemberEndToAdd() {
		return memberEndEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getMemberEndToRemove()
	 */
	public List getMemberEndToRemove() {
		return memberEndEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getMemberEndTable()
	 */
	public List getMemberEndTable() {
		return memberEndEditUtil.getVirtualList();
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#initMemberEnd(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initMemberEnd(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			memberEndEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			memberEndEditUtil = new EMFListEditUtil(current, feature);
		this.memberEnd.setInput(memberEndEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#updateMemberEnd(EObject newValue)
	 */
	public void updateMemberEnd(EObject newValue) {
		if(memberEndEditUtil != null){
			memberEndEditUtil.reinit(newValue);
			memberEnd.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addFilterMemberEnd(ViewerFilter filter)
	 */
	public void addFilterToMemberEnd(ViewerFilter filter) {
		memberEndFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addBusinessFilterMemberEnd(ViewerFilter filter)
	 */
	public void addBusinessFilterToMemberEnd(ViewerFilter filter) {
		memberEndBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#isContainedInMemberEndTable(EObject element)
	 */
	public boolean isContainedInMemberEndTable(EObject element) {
		return memberEndEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getNavigableOwnedEndToAdd()
	 */
	public List getNavigableOwnedEndToAdd() {
		return navigableOwnedEndEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getNavigableOwnedEndToRemove()
	 */
	public List getNavigableOwnedEndToRemove() {
		return navigableOwnedEndEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getNavigableOwnedEndTable()
	 */
	public List getNavigableOwnedEndTable() {
		return navigableOwnedEndEditUtil.getVirtualList();
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#initNavigableOwnedEnd(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initNavigableOwnedEnd(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			navigableOwnedEndEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			navigableOwnedEndEditUtil = new EMFListEditUtil(current, feature);
		this.navigableOwnedEnd.setInput(navigableOwnedEndEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#updateNavigableOwnedEnd(EObject newValue)
	 */
	public void updateNavigableOwnedEnd(EObject newValue) {
		if(navigableOwnedEndEditUtil != null){
			navigableOwnedEndEditUtil.reinit(newValue);
			navigableOwnedEnd.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addFilterNavigableOwnedEnd(ViewerFilter filter)
	 */
	public void addFilterToNavigableOwnedEnd(ViewerFilter filter) {
		navigableOwnedEndFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addBusinessFilterNavigableOwnedEnd(ViewerFilter filter)
	 */
	public void addBusinessFilterToNavigableOwnedEnd(ViewerFilter filter) {
		navigableOwnedEndBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#isContainedInNavigableOwnedEndTable(EObject element)
	 */
	public boolean isContainedInNavigableOwnedEndTable(EObject element) {
		return navigableOwnedEndEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getTemplateBindingToAdd()
	 */
	public List getTemplateBindingToAdd() {
		return templateBindingEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getTemplateBindingToRemove()
	 */
	public List getTemplateBindingToRemove() {
		return templateBindingEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getTemplateBindingToEdit()
	 */
	public Map getTemplateBindingToEdit() {
		return templateBindingEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getTemplateBindingToMove()
	 */
	public List getTemplateBindingToMove() {
		return templateBindingEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getTemplateBindingTable()
	 */
	public List getTemplateBindingTable() {
		return templateBindingEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#initTemplateBinding(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#updateTemplateBinding(EObject newValue)
	 */
	public void updateTemplateBinding(EObject newValue) {
		if(templateBindingEditUtil != null){
			templateBindingEditUtil.reinit(newValue);
			templateBinding.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addFilterTemplateBinding(ViewerFilter filter)
	 */
	public void addFilterToTemplateBinding(ViewerFilter filter) {
		templateBindingFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addBusinessFilterTemplateBinding(ViewerFilter filter)
	 */
	public void addBusinessFilterToTemplateBinding(ViewerFilter filter) {
		templateBindingBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#isContainedInTemplateBindingTable(EObject element)
	 */
	public boolean isContainedInTemplateBindingTable(EObject element) {
		return templateBindingEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getUseCaseToAdd()
	 */
	public List getUseCaseToAdd() {
		return useCaseEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getUseCaseToRemove()
	 */
	public List getUseCaseToRemove() {
		return useCaseEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getUseCaseTable()
	 */
	public List getUseCaseTable() {
		return useCaseEditUtil.getVirtualList();
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#initUseCase(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#updateUseCase(EObject newValue)
	 */
	public void updateUseCase(EObject newValue) {
		if(useCaseEditUtil != null){
			useCaseEditUtil.reinit(newValue);
			useCase.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addFilterUseCase(ViewerFilter filter)
	 */
	public void addFilterToUseCase(ViewerFilter filter) {
		useCaseFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addBusinessFilterUseCase(ViewerFilter filter)
	 */
	public void addBusinessFilterToUseCase(ViewerFilter filter) {
		useCaseBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#isContainedInUseCaseTable(EObject element)
	 */
	public boolean isContainedInUseCaseTable(EObject element) {
		return useCaseEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getOwnedUseCaseToAdd()
	 */
	public List getOwnedUseCaseToAdd() {
		return ownedUseCaseEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getOwnedUseCaseToRemove()
	 */
	public List getOwnedUseCaseToRemove() {
		return ownedUseCaseEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getOwnedUseCaseToEdit()
	 */
	public Map getOwnedUseCaseToEdit() {
		return ownedUseCaseEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getOwnedUseCaseToMove()
	 */
	public List getOwnedUseCaseToMove() {
		return ownedUseCaseEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getOwnedUseCaseTable()
	 */
	public List getOwnedUseCaseTable() {
		return ownedUseCaseEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#initOwnedUseCase(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#updateOwnedUseCase(EObject newValue)
	 */
	public void updateOwnedUseCase(EObject newValue) {
		if(ownedUseCaseEditUtil != null){
			ownedUseCaseEditUtil.reinit(newValue);
			ownedUseCase.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addFilterOwnedUseCase(ViewerFilter filter)
	 */
	public void addFilterToOwnedUseCase(ViewerFilter filter) {
		ownedUseCaseFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addBusinessFilterOwnedUseCase(ViewerFilter filter)
	 */
	public void addBusinessFilterToOwnedUseCase(ViewerFilter filter) {
		ownedUseCaseBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#isContainedInOwnedUseCaseTable(EObject element)
	 */
	public boolean isContainedInOwnedUseCaseTable(EObject element) {
		return ownedUseCaseEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getCollaborationUseToAdd()
	 */
	public List getCollaborationUseToAdd() {
		return collaborationUseEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getCollaborationUseToRemove()
	 */
	public List getCollaborationUseToRemove() {
		return collaborationUseEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getCollaborationUseToEdit()
	 */
	public Map getCollaborationUseToEdit() {
		return collaborationUseEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getCollaborationUseToMove()
	 */
	public List getCollaborationUseToMove() {
		return collaborationUseEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getCollaborationUseTable()
	 */
	public List getCollaborationUseTable() {
		return collaborationUseEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#initCollaborationUse(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#updateCollaborationUse(EObject newValue)
	 */
	public void updateCollaborationUse(EObject newValue) {
		if(collaborationUseEditUtil != null){
			collaborationUseEditUtil.reinit(newValue);
			collaborationUse.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addFilterCollaborationUse(ViewerFilter filter)
	 */
	public void addFilterToCollaborationUse(ViewerFilter filter) {
		collaborationUseFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addBusinessFilterCollaborationUse(ViewerFilter filter)
	 */
	public void addBusinessFilterToCollaborationUse(ViewerFilter filter) {
		collaborationUseBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#isContainedInCollaborationUseTable(EObject element)
	 */
	public boolean isContainedInCollaborationUseTable(EObject element) {
		return collaborationUseEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getSubstitutionToAdd()
	 */
	public List getSubstitutionToAdd() {
		return substitutionEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getSubstitutionToRemove()
	 */
	public List getSubstitutionToRemove() {
		return substitutionEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getSubstitutionToEdit()
	 */
	public Map getSubstitutionToEdit() {
		return substitutionEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getSubstitutionToMove()
	 */
	public List getSubstitutionToMove() {
		return substitutionEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getSubstitutionTable()
	 */
	public List getSubstitutionTable() {
		return substitutionEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#initSubstitution(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#updateSubstitution(EObject newValue)
	 */
	public void updateSubstitution(EObject newValue) {
		if(substitutionEditUtil != null){
			substitutionEditUtil.reinit(newValue);
			substitution.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addFilterSubstitution(ViewerFilter filter)
	 */
	public void addFilterToSubstitution(ViewerFilter filter) {
		substitutionFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addBusinessFilterSubstitution(ViewerFilter filter)
	 */
	public void addBusinessFilterToSubstitution(ViewerFilter filter) {
		substitutionBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#isContainedInSubstitutionTable(EObject element)
	 */
	public boolean isContainedInSubstitutionTable(EObject element) {
		return substitutionEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getRedefinedClassifierToAdd()
	 */
	public List getRedefinedClassifierToAdd() {
		return redefinedClassifierEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getRedefinedClassifierToRemove()
	 */
	public List getRedefinedClassifierToRemove() {
		return redefinedClassifierEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getRedefinedClassifierTable()
	 */
	public List getRedefinedClassifierTable() {
		return redefinedClassifierEditUtil.getVirtualList();
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#initRedefinedClassifier(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#updateRedefinedClassifier(EObject newValue)
	 */
	public void updateRedefinedClassifier(EObject newValue) {
		if(redefinedClassifierEditUtil != null){
			redefinedClassifierEditUtil.reinit(newValue);
			redefinedClassifier.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addFilterRedefinedClassifier(ViewerFilter filter)
	 */
	public void addFilterToRedefinedClassifier(ViewerFilter filter) {
		redefinedClassifierFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addBusinessFilterRedefinedClassifier(ViewerFilter filter)
	 */
	public void addBusinessFilterToRedefinedClassifier(ViewerFilter filter) {
		redefinedClassifierBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#isContainedInRedefinedClassifierTable(EObject element)
	 */
	public boolean isContainedInRedefinedClassifierTable(EObject element) {
		return redefinedClassifierEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getGeneralizationToAdd()
	 */
	public List getGeneralizationToAdd() {
		return generalizationEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getGeneralizationToRemove()
	 */
	public List getGeneralizationToRemove() {
		return generalizationEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getGeneralizationToEdit()
	 */
	public Map getGeneralizationToEdit() {
		return generalizationEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getGeneralizationToMove()
	 */
	public List getGeneralizationToMove() {
		return generalizationEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getGeneralizationTable()
	 */
	public List getGeneralizationTable() {
		return generalizationEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#initGeneralization(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#updateGeneralization(EObject newValue)
	 */
	public void updateGeneralization(EObject newValue) {
		if(generalizationEditUtil != null){
			generalizationEditUtil.reinit(newValue);
			generalization.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addFilterGeneralization(ViewerFilter filter)
	 */
	public void addFilterToGeneralization(ViewerFilter filter) {
		generalizationFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addBusinessFilterGeneralization(ViewerFilter filter)
	 */
	public void addBusinessFilterToGeneralization(ViewerFilter filter) {
		generalizationBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#isContainedInGeneralizationTable(EObject element)
	 */
	public boolean isContainedInGeneralizationTable(EObject element) {
		return generalizationEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getPowertypeExtentToAdd()
	 */
	public List getPowertypeExtentToAdd() {
		return powertypeExtentEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getPowertypeExtentToRemove()
	 */
	public List getPowertypeExtentToRemove() {
		return powertypeExtentEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getPowertypeExtentTable()
	 */
	public List getPowertypeExtentTable() {
		return powertypeExtentEditUtil.getVirtualList();
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#initPowertypeExtent(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#updatePowertypeExtent(EObject newValue)
	 */
	public void updatePowertypeExtent(EObject newValue) {
		if(powertypeExtentEditUtil != null){
			powertypeExtentEditUtil.reinit(newValue);
			powertypeExtent.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addFilterPowertypeExtent(ViewerFilter filter)
	 */
	public void addFilterToPowertypeExtent(ViewerFilter filter) {
		powertypeExtentFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addBusinessFilterPowertypeExtent(ViewerFilter filter)
	 */
	public void addBusinessFilterToPowertypeExtent(ViewerFilter filter) {
		powertypeExtentBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#isContainedInPowertypeExtentTable(EObject element)
	 */
	public boolean isContainedInPowertypeExtentTable(EObject element) {
		return powertypeExtentEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getElementImportToAdd()
	 */
	public List getElementImportToAdd() {
		return elementImportEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getElementImportToRemove()
	 */
	public List getElementImportToRemove() {
		return elementImportEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getElementImportToEdit()
	 */
	public Map getElementImportToEdit() {
		return elementImportEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getElementImportToMove()
	 */
	public List getElementImportToMove() {
		return elementImportEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getElementImportTable()
	 */
	public List getElementImportTable() {
		return elementImportEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#initElementImport(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#updateElementImport(EObject newValue)
	 */
	public void updateElementImport(EObject newValue) {
		if(elementImportEditUtil != null){
			elementImportEditUtil.reinit(newValue);
			elementImport.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addFilterElementImport(ViewerFilter filter)
	 */
	public void addFilterToElementImport(ViewerFilter filter) {
		elementImportFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addBusinessFilterElementImport(ViewerFilter filter)
	 */
	public void addBusinessFilterToElementImport(ViewerFilter filter) {
		elementImportBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#isContainedInElementImportTable(EObject element)
	 */
	public boolean isContainedInElementImportTable(EObject element) {
		return elementImportEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getPackageImportToAdd()
	 */
	public List getPackageImportToAdd() {
		return packageImportEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getPackageImportToRemove()
	 */
	public List getPackageImportToRemove() {
		return packageImportEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getPackageImportToEdit()
	 */
	public Map getPackageImportToEdit() {
		return packageImportEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getPackageImportToMove()
	 */
	public List getPackageImportToMove() {
		return packageImportEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getPackageImportTable()
	 */
	public List getPackageImportTable() {
		return packageImportEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#initPackageImport(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#updatePackageImport(EObject newValue)
	 */
	public void updatePackageImport(EObject newValue) {
		if(packageImportEditUtil != null){
			packageImportEditUtil.reinit(newValue);
			packageImport.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addFilterPackageImport(ViewerFilter filter)
	 */
	public void addFilterToPackageImport(ViewerFilter filter) {
		packageImportFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addBusinessFilterPackageImport(ViewerFilter filter)
	 */
	public void addBusinessFilterToPackageImport(ViewerFilter filter) {
		packageImportBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#isContainedInPackageImportTable(EObject element)
	 */
	public boolean isContainedInPackageImportTable(EObject element) {
		return packageImportEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getOwnedRuleToAdd()
	 */
	public List getOwnedRuleToAdd() {
		return ownedRuleEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getOwnedRuleToRemove()
	 */
	public List getOwnedRuleToRemove() {
		return ownedRuleEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getOwnedRuleToEdit()
	 */
	public Map getOwnedRuleToEdit() {
		return ownedRuleEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getOwnedRuleToMove()
	 */
	public List getOwnedRuleToMove() {
		return ownedRuleEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#getOwnedRuleTable()
	 */
	public List getOwnedRuleTable() {
		return ownedRuleEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#initOwnedRule(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#updateOwnedRule(EObject newValue)
	 */
	public void updateOwnedRule(EObject newValue) {
		if(ownedRuleEditUtil != null){
			ownedRuleEditUtil.reinit(newValue);
			ownedRule.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addFilterOwnedRule(ViewerFilter filter)
	 */
	public void addFilterToOwnedRule(ViewerFilter filter) {
		ownedRuleFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#addBusinessFilterOwnedRule(ViewerFilter filter)
	 */
	public void addBusinessFilterToOwnedRule(ViewerFilter filter) {
		ownedRuleBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.AssociationPropertiesEditionPart#isContainedInOwnedRuleTable(EObject element)
	 */
	public boolean isContainedInOwnedRuleTable(EObject element) {
		return ownedRuleEditUtil.contains(element);
	}











	
	// Start of user code additional methods

	// End of user code

}	