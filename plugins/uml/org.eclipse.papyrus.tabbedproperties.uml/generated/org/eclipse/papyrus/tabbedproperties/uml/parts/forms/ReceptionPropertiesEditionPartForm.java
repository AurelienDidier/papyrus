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
import org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart;
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
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterSet;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Constraint;



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
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Type;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.emf.eef.runtime.impl.filters.EObjectFilter;

import org.eclipse.emf.eef.runtime.ui.widgets.HorizontalBox;
import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;

// End of user code

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class ReceptionPropertiesEditionPartForm extends CompositePropertiesEditionPart implements IFormPropertiesEditionPart, ReceptionPropertiesEditionPart {

	protected Text name;
	protected EMFComboViewer visibility;
	protected EMFComboViewer concurrency;
	protected Button isAbstract;
	protected Button isLeaf;
	protected Button isStatic;
	private EMFListEditUtil methodEditUtil;
	protected ReferencesTable<?> method;
	protected List<ViewerFilter> methodBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> methodFilters = new ArrayList<ViewerFilter>();
	private EMFListEditUtil raisedExceptionEditUtil;
	protected ReferencesTable<?> raisedException;
	protected List<ViewerFilter> raisedExceptionBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> raisedExceptionFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil ownedParameterEditUtil;
	protected ReferencesTable<?> ownedParameter;
	protected List<ViewerFilter> ownedParameterBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> ownedParameterFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil ownedParameterSetEditUtil;
	protected ReferencesTable<?> ownedParameterSet;
	protected List<ViewerFilter> ownedParameterSetBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> ownedParameterSetFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil packageImportEditUtil;
	protected ReferencesTable<?> packageImport;
	protected List<ViewerFilter> packageImportBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> packageImportFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil elementImportEditUtil;
	protected ReferencesTable<?> elementImport;
	protected List<ViewerFilter> elementImportBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> elementImportFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil ownedRuleEditUtil;
	protected ReferencesTable<?> ownedRule;
	protected List<ViewerFilter> ownedRuleBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> ownedRuleFilters = new ArrayList<ViewerFilter>();




	
	public ReceptionPropertiesEditionPartForm(IPropertiesEditionComponent editionComponent) {
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
		Section generalSection = widgetFactory.createSection(view, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		generalSection.setText(UMLMessages.ReceptionPropertiesEditionPart_GeneralGroupLabel);
		GridData generalSectionData = new GridData(GridData.FILL_HORIZONTAL);
		generalSectionData.horizontalSpan = 3;
		generalSection.setLayoutData(generalSectionData);
		Composite generalGroup = widgetFactory.createComposite(generalSection);
		GridLayout generalGroupLayout = new GridLayout();
		generalGroupLayout.numColumns = 3;
		generalGroup.setLayout(generalGroupLayout);
		createNameText(widgetFactory, generalGroup);
		createVisibilityEMFComboViewer(widgetFactory, generalGroup);
		createConcurrencyEMFComboViewer(widgetFactory, generalGroup);
		createGeneralHBox1HBox(widgetFactory, generalGroup);
		createGeneralHBox2HBox(widgetFactory, generalGroup);
		generalSection.setClient(generalGroup);
	}
	protected void createNameText(FormToolkit widgetFactory, Composite parent) {
		FormUtils.createPartLabel(widgetFactory, parent, UMLMessages.ReceptionPropertiesEditionPart_NameLabel, propertiesEditionComponent.isRequired(UMLViewsRepository.Reception.name, UMLViewsRepository.FORM_KIND));
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.name, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, null, name.getText()));
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.name, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
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
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.name, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
				}
			}
			
		});
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.Reception.name, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$

	}
	protected void createVisibilityEMFComboViewer(FormToolkit widgetFactory, Composite parent) {
		FormUtils.createPartLabel(widgetFactory, parent, UMLMessages.ReceptionPropertiesEditionPart_VisibilityLabel, propertiesEditionComponent.isRequired(UMLViewsRepository.Reception.visibility, UMLViewsRepository.FORM_KIND));
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.visibility, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, getVisibility()));
			}
			
		});
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.Reception.visibility, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}
	protected void createConcurrencyEMFComboViewer(FormToolkit widgetFactory, Composite parent) {
		FormUtils.createPartLabel(widgetFactory, parent, UMLMessages.ReceptionPropertiesEditionPart_ConcurrencyLabel, propertiesEditionComponent.isRequired(UMLViewsRepository.Reception.concurrency, UMLViewsRepository.FORM_KIND));
		concurrency = new EMFComboViewer(parent);
		concurrency.setContentProvider(new ArrayContentProvider());
		concurrency.setLabelProvider(new AdapterFactoryLabelProvider(new EcoreAdapterFactory()));
		GridData concurrencyData = new GridData(GridData.FILL_HORIZONTAL);
		concurrency.getCombo().setLayoutData(concurrencyData);
		concurrency.addSelectionChangedListener(new ISelectionChangedListener() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
			 */
			public void selectionChanged(SelectionChangedEvent event) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.concurrency, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, getConcurrency()));
			}
			
		});
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.Reception.concurrency, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
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
				createIsAbstractCheckbox(widgetFactory, generalHBox1HBox);
				createIsLeafCheckbox(widgetFactory, generalHBox1HBox);
				createIsStaticCheckbox(widgetFactory, generalHBox1HBox);
		
		container.pack();
	}
	protected void createIsAbstractCheckbox(FormToolkit widgetFactory, Composite parent) {
		isAbstract = widgetFactory.createButton(parent, UMLMessages.ReceptionPropertiesEditionPart_IsAbstractLabel, SWT.CHECK);
		isAbstract.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.isAbstract, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(isAbstract.getSelection())));
			}
			
		});
		GridData isAbstractData = new GridData(GridData.FILL_HORIZONTAL);
		//isAbstractData.horizontalSpan = 2;
		isAbstract.setLayoutData(isAbstractData);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.Reception.isAbstract, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}
	protected void createIsLeafCheckbox(FormToolkit widgetFactory, Composite parent) {
		isLeaf = widgetFactory.createButton(parent, UMLMessages.ReceptionPropertiesEditionPart_IsLeafLabel, SWT.CHECK);
		isLeaf.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.isLeaf, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(isLeaf.getSelection())));
			}
			
		});
		GridData isLeafData = new GridData(GridData.FILL_HORIZONTAL);
		//isLeafData.horizontalSpan = 2;
		isLeaf.setLayoutData(isLeafData);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.Reception.isLeaf, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}
	protected void createIsStaticCheckbox(FormToolkit widgetFactory, Composite parent) {
		isStatic = widgetFactory.createButton(parent, UMLMessages.ReceptionPropertiesEditionPart_IsStaticLabel, SWT.CHECK);
		isStatic.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.isStatic, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(isStatic.getSelection())));
			}
			
		});
		GridData isStaticData = new GridData(GridData.FILL_HORIZONTAL);
		//isStaticData.horizontalSpan = 2;
		isStatic.setLayoutData(isStaticData);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.Reception.isStatic, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
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
				createMethodReferencesTable(widgetFactory, generalHBox2HBox);
				createRaisedExceptionReferencesTable(widgetFactory, generalHBox2HBox);
				createOwnedParameterTableComposition(widgetFactory, generalHBox2HBox);
		
		container.pack();
	}
	protected void createMethodReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.method = new ReferencesTable<Behavior>(UMLMessages.ReceptionPropertiesEditionPart_MethodLabel, new ReferencesTableListener<Behavior>() {
			public void handleAdd() {
				TabElementTreeSelectionDialog<Behavior> dialog = new TabElementTreeSelectionDialog<Behavior>(resourceSet, methodFilters, methodBusinessFilters,
				"Behavior", UMLPackage.eINSTANCE.getBehavior()) {
					@Override
					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!methodEditUtil.getVirtualList().contains(elem))
								methodEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.method,
								PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
						}
						method.refresh();
					}
				};
				dialog.open();
			}
			public void handleEdit(Behavior element) { editMethod(element); }
			public void handleMove(Behavior element, int oldIndex, int newIndex) { moveMethod(element, oldIndex, newIndex); }
			public void handleRemove(Behavior element) { removeFromMethod(element); }
			public void navigateTo(Behavior element) { }
		});
		this.method.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Reception.method, UMLViewsRepository.FORM_KIND));
		this.method.createControls(parent, widgetFactory);
		GridData methodData = new GridData(GridData.FILL_HORIZONTAL);
		methodData.horizontalSpan = 3;
		this.method.setLayoutData(methodData);
		this.method.disableMove();
	}
	
	/**
	 * 
	 */
	protected void moveMethod(Behavior element, int oldIndex, int newIndex) {
	}
	
	/**
	 * 
	 */
	protected void removeFromMethod(Behavior element) {

		// Start of user code for the removeFromMethod() method body

		EObject editedElement = methodEditUtil.foundCorrespondingEObject(element);
		methodEditUtil.removeElement(element);
		method.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.method, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editMethod(Behavior element) {

		// Start of user code editMethod() method body
		
		EObject editedElement = methodEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider	.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element,resourceSet));
			if (propertiesEditionObject != null) {
				methodEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				method.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.method, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code

	}
	protected void createRaisedExceptionReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.raisedException = new ReferencesTable<Type>(UMLMessages.ReceptionPropertiesEditionPart_RaisedExceptionLabel, new ReferencesTableListener<Type>() {
			public void handleAdd() {
				TabElementTreeSelectionDialog<Type> dialog = new TabElementTreeSelectionDialog<Type>(resourceSet, raisedExceptionFilters, raisedExceptionBusinessFilters,
				"Type", UMLPackage.eINSTANCE.getType()) {
					@Override
					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!raisedExceptionEditUtil.getVirtualList().contains(elem))
								raisedExceptionEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.raisedException,
								PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
						}
						raisedException.refresh();
					}
				};
				dialog.open();
			}
			public void handleEdit(Type element) { editRaisedException(element); }
			public void handleMove(Type element, int oldIndex, int newIndex) { moveRaisedException(element, oldIndex, newIndex); }
			public void handleRemove(Type element) { removeFromRaisedException(element); }
			public void navigateTo(Type element) { }
		});
		this.raisedException.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Reception.raisedException, UMLViewsRepository.FORM_KIND));
		this.raisedException.createControls(parent, widgetFactory);
		GridData raisedExceptionData = new GridData(GridData.FILL_HORIZONTAL);
		raisedExceptionData.horizontalSpan = 3;
		this.raisedException.setLayoutData(raisedExceptionData);
		this.raisedException.disableMove();
	}
	
	/**
	 * 
	 */
	protected void moveRaisedException(Type element, int oldIndex, int newIndex) {
	}
	
	/**
	 * 
	 */
	protected void removeFromRaisedException(Type element) {

		// Start of user code for the removeFromRaisedException() method body

		EObject editedElement = raisedExceptionEditUtil.foundCorrespondingEObject(element);
		raisedExceptionEditUtil.removeElement(element);
		raisedException.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.raisedException, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editRaisedException(Type element) {

		// Start of user code editRaisedException() method body
		
		EObject editedElement = raisedExceptionEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider	.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element,resourceSet));
			if (propertiesEditionObject != null) {
				raisedExceptionEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				raisedException.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.raisedException, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code

	}
	/**
	 * @param container
	 */
	protected void createOwnedParameterTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.ownedParameter = new ReferencesTable<Parameter>(UMLMessages.ReceptionPropertiesEditionPart_OwnedParameterLabel, new ReferencesTableListener<Parameter>() {			
			public void handleAdd() { addToOwnedParameter();}
			public void handleEdit(Parameter element) { editOwnedParameter(element); }
			public void handleMove(Parameter element, int oldIndex, int newIndex) { moveOwnedParameter(element, oldIndex, newIndex); }
			public void handleRemove(Parameter element) { removeFromOwnedParameter(element); }
			public void navigateTo(Parameter element) { }
		});
		this.ownedParameter.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Reception.ownedParameter, UMLViewsRepository.FORM_KIND));
		this.ownedParameter.createControls(parent, widgetFactory);
		GridData ownedParameterData = new GridData(GridData.FILL_HORIZONTAL);
		ownedParameterData.horizontalSpan = 3;
		this.ownedParameter.setLayoutData(ownedParameterData);
	}
	
	/**
	 * 
	 */
	protected void moveOwnedParameter(Parameter element, int oldIndex, int newIndex) {
		EObject editedElement = ownedParameterEditUtil.foundCorrespondingEObject(element);
		ownedParameterEditUtil.moveElement(element, oldIndex, newIndex);
		ownedParameter.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.ownedParameter, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
		
	}
	
	/**
	 * 
	 */
	protected void addToOwnedParameter() {
	
		// Start of user code addToOwnedParameter() method body
		
		
		Parameter eObject = UMLFactory.eINSTANCE.createParameter();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject,resourceSet));
			if (propertiesEditionObject != null) {
				ownedParameterEditUtil.addElement(propertiesEditionObject);
				ownedParameter.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.ownedParameter, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}
		
			
		// End of user code
		
	}

	/**
	 * 
	 */
	protected void removeFromOwnedParameter(Parameter element) {

		// Start of user code for the removeFromOwnedParameter() method body

		EObject editedElement = ownedParameterEditUtil.foundCorrespondingEObject(element);
		ownedParameterEditUtil.removeElement(element);
		ownedParameter.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.ownedParameter, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editOwnedParameter(Parameter element) {

		// Start of user code editOwnedParameter() method body
				 
		EObject editedElement = ownedParameterEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider	.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element,resourceSet));
			if (propertiesEditionObject != null) {
				ownedParameterEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedParameter.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.ownedParameter, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code

	}
	protected void createOthersGroup(FormToolkit widgetFactory, final Composite view) {
		Section othersSection = widgetFactory.createSection(view, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		othersSection.setText(UMLMessages.ReceptionPropertiesEditionPart_OthersGroupLabel);
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
				createOwnedParameterSetTableComposition(widgetFactory, othersHBox1HBox);
				createPackageImportTableComposition(widgetFactory, othersHBox1HBox);
				createElementImportTableComposition(widgetFactory, othersHBox1HBox);
				createOwnedRuleTableComposition(widgetFactory, othersHBox1HBox);
		
		container.pack();
	}
	/**
	 * @param container
	 */
	protected void createOwnedParameterSetTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.ownedParameterSet = new ReferencesTable<ParameterSet>(UMLMessages.ReceptionPropertiesEditionPart_OwnedParameterSetLabel, new ReferencesTableListener<ParameterSet>() {			
			public void handleAdd() { addToOwnedParameterSet();}
			public void handleEdit(ParameterSet element) { editOwnedParameterSet(element); }
			public void handleMove(ParameterSet element, int oldIndex, int newIndex) { moveOwnedParameterSet(element, oldIndex, newIndex); }
			public void handleRemove(ParameterSet element) { removeFromOwnedParameterSet(element); }
			public void navigateTo(ParameterSet element) { }
		});
		this.ownedParameterSet.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Reception.ownedParameterSet, UMLViewsRepository.FORM_KIND));
		this.ownedParameterSet.createControls(parent, widgetFactory);
		GridData ownedParameterSetData = new GridData(GridData.FILL_HORIZONTAL);
		ownedParameterSetData.horizontalSpan = 3;
		this.ownedParameterSet.setLayoutData(ownedParameterSetData);
	}
	
	/**
	 * 
	 */
	protected void moveOwnedParameterSet(ParameterSet element, int oldIndex, int newIndex) {
		EObject editedElement = ownedParameterSetEditUtil.foundCorrespondingEObject(element);
		ownedParameterSetEditUtil.moveElement(element, oldIndex, newIndex);
		ownedParameterSet.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.ownedParameterSet, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
		
	}
	
	/**
	 * 
	 */
	protected void addToOwnedParameterSet() {
	
		// Start of user code addToOwnedParameterSet() method body
		
		
		ParameterSet eObject = UMLFactory.eINSTANCE.createParameterSet();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject,resourceSet));
			if (propertiesEditionObject != null) {
				ownedParameterSetEditUtil.addElement(propertiesEditionObject);
				ownedParameterSet.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.ownedParameterSet, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}
		
			
		// End of user code
		
	}

	/**
	 * 
	 */
	protected void removeFromOwnedParameterSet(ParameterSet element) {

		// Start of user code for the removeFromOwnedParameterSet() method body

		EObject editedElement = ownedParameterSetEditUtil.foundCorrespondingEObject(element);
		ownedParameterSetEditUtil.removeElement(element);
		ownedParameterSet.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.ownedParameterSet, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editOwnedParameterSet(ParameterSet element) {

		// Start of user code editOwnedParameterSet() method body
				 
		EObject editedElement = ownedParameterSetEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider	.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element,resourceSet));
			if (propertiesEditionObject != null) {
				ownedParameterSetEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedParameterSet.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.ownedParameterSet, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code

	}
	/**
	 * @param container
	 */
	protected void createPackageImportTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.packageImport = new ReferencesTable<PackageImport>(UMLMessages.ReceptionPropertiesEditionPart_PackageImportLabel, new ReferencesTableListener<PackageImport>() {			
			public void handleAdd() { addToPackageImport();}
			public void handleEdit(PackageImport element) { editPackageImport(element); }
			public void handleMove(PackageImport element, int oldIndex, int newIndex) { movePackageImport(element, oldIndex, newIndex); }
			public void handleRemove(PackageImport element) { removeFromPackageImport(element); }
			public void navigateTo(PackageImport element) { }
		});
		this.packageImport.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Reception.packageImport, UMLViewsRepository.FORM_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.packageImport, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
		
	}
	
	/**
	 * 
	 */
	protected void addToPackageImport() {
	
		// Start of user code addToPackageImport() method body
		
		
		PackageImport eObject = UMLFactory.eINSTANCE.createPackageImport();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject,resourceSet));
			if (propertiesEditionObject != null) {
				packageImportEditUtil.addElement(propertiesEditionObject);
				packageImport.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.packageImport, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.packageImport, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editPackageImport(PackageImport element) {

		// Start of user code editPackageImport() method body
				 
		EObject editedElement = packageImportEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider	.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element,resourceSet));
			if (propertiesEditionObject != null) {
				packageImportEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				packageImport.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.packageImport, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code

	}
	/**
	 * @param container
	 */
	protected void createElementImportTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.elementImport = new ReferencesTable<ElementImport>(UMLMessages.ReceptionPropertiesEditionPart_ElementImportLabel, new ReferencesTableListener<ElementImport>() {			
			public void handleAdd() { addToElementImport();}
			public void handleEdit(ElementImport element) { editElementImport(element); }
			public void handleMove(ElementImport element, int oldIndex, int newIndex) { moveElementImport(element, oldIndex, newIndex); }
			public void handleRemove(ElementImport element) { removeFromElementImport(element); }
			public void navigateTo(ElementImport element) { }
		});
		this.elementImport.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Reception.elementImport, UMLViewsRepository.FORM_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.elementImport, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
		
	}
	
	/**
	 * 
	 */
	protected void addToElementImport() {
	
		// Start of user code addToElementImport() method body
		
		
		ElementImport eObject = UMLFactory.eINSTANCE.createElementImport();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject,resourceSet));
			if (propertiesEditionObject != null) {
				elementImportEditUtil.addElement(propertiesEditionObject);
				elementImport.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.elementImport, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.elementImport, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editElementImport(ElementImport element) {

		// Start of user code editElementImport() method body
				 
		EObject editedElement = elementImportEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider	.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element,resourceSet));
			if (propertiesEditionObject != null) {
				elementImportEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				elementImport.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.elementImport, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code

	}
	/**
	 * @param container
	 */
	protected void createOwnedRuleTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.ownedRule = new ReferencesTable<Constraint>(UMLMessages.ReceptionPropertiesEditionPart_OwnedRuleLabel, new ReferencesTableListener<Constraint>() {			
			public void handleAdd() { addToOwnedRule();}
			public void handleEdit(Constraint element) { editOwnedRule(element); }
			public void handleMove(Constraint element, int oldIndex, int newIndex) { moveOwnedRule(element, oldIndex, newIndex); }
			public void handleRemove(Constraint element) { removeFromOwnedRule(element); }
			public void navigateTo(Constraint element) { }
		});
		this.ownedRule.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Reception.ownedRule, UMLViewsRepository.FORM_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.ownedRule, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
		
	}
	
	/**
	 * 
	 */
	protected void addToOwnedRule() {
	
		// Start of user code addToOwnedRule() method body
		
		
		Constraint eObject = UMLFactory.eINSTANCE.createConstraint();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject,resourceSet));
			if (propertiesEditionObject != null) {
				ownedRuleEditUtil.addElement(propertiesEditionObject);
				ownedRule.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.ownedRule, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.ownedRule, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editOwnedRule(Constraint element) {

		// Start of user code editOwnedRule() method body
				 
		EObject editedElement = ownedRuleEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider	.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element,resourceSet));
			if (propertiesEditionObject != null) {
				ownedRuleEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedRule.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(ReceptionPropertiesEditionPartForm.this, UMLViewsRepository.Reception.ownedRule, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getName()
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#setName(String newValue)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getVisibility()
	 */
	public Enumerator getVisibility() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) visibility.getSelection()).getFirstElement();
		return selection.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#initVisibility(EEnum eenum, Enumerator current)
	 */
	public void initVisibility(EEnum eenum, Enumerator current) {
		visibility.setInput(eenum.getELiterals());
		visibility.modelUpdating(new StructuredSelection(current));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#setVisibility(Enumerator newValue)
	 */
	public void setVisibility(Enumerator newValue) {
		visibility.modelUpdating(new StructuredSelection(newValue));
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getConcurrency()
	 */
	public Enumerator getConcurrency() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) concurrency.getSelection()).getFirstElement();
		return selection.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#initConcurrency(EEnum eenum, Enumerator current)
	 */
	public void initConcurrency(EEnum eenum, Enumerator current) {
		concurrency.setInput(eenum.getELiterals());
		concurrency.modelUpdating(new StructuredSelection(current));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#setConcurrency(Enumerator newValue)
	 */
	public void setConcurrency(Enumerator newValue) {
		concurrency.modelUpdating(new StructuredSelection(newValue));
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getIsAbstract()
	 */
	public Boolean getIsAbstract() {
		return Boolean.valueOf(isAbstract.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#setIsAbstract(Boolean newValue)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getIsLeaf()
	 */
	public Boolean getIsLeaf() {
		return Boolean.valueOf(isLeaf.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#setIsLeaf(Boolean newValue)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getIsStatic()
	 */
	public Boolean getIsStatic() {
		return Boolean.valueOf(isStatic.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#setIsStatic(Boolean newValue)
	 */
	public void setIsStatic(Boolean newValue) {
		if (newValue != null) {
			isStatic.setSelection(newValue.booleanValue());
		} else {
			isStatic.setSelection(false);
		}
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getMethodToAdd()
	 */
	public List getMethodToAdd() {
		return methodEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getMethodToRemove()
	 */
	public List getMethodToRemove() {
		return methodEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getMethodTable()
	 */
	public List getMethodTable() {
		return methodEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#initMethod(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initMethod(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			methodEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			methodEditUtil = new EMFListEditUtil(current, feature);
		this.method.setInput(methodEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#updateMethod(EObject newValue)
	 */
	public void updateMethod(EObject newValue) {
		if(methodEditUtil!=null){
			methodEditUtil.reinit(newValue);
			method.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#addFilterMethod(ViewerFilter filter)
	 */
	public void addFilterToMethod(ViewerFilter filter) {
		methodFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#addBusinessFilterMethod(ViewerFilter filter)
	 */
	public void addBusinessFilterToMethod(ViewerFilter filter) {
		methodBusinessFilters.add(filter);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getRaisedExceptionToAdd()
	 */
	public List getRaisedExceptionToAdd() {
		return raisedExceptionEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getRaisedExceptionToRemove()
	 */
	public List getRaisedExceptionToRemove() {
		return raisedExceptionEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getRaisedExceptionTable()
	 */
	public List getRaisedExceptionTable() {
		return raisedExceptionEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#initRaisedException(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initRaisedException(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			raisedExceptionEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			raisedExceptionEditUtil = new EMFListEditUtil(current, feature);
		this.raisedException.setInput(raisedExceptionEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#updateRaisedException(EObject newValue)
	 */
	public void updateRaisedException(EObject newValue) {
		if(raisedExceptionEditUtil!=null){
			raisedExceptionEditUtil.reinit(newValue);
			raisedException.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#addFilterRaisedException(ViewerFilter filter)
	 */
	public void addFilterToRaisedException(ViewerFilter filter) {
		raisedExceptionFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#addBusinessFilterRaisedException(ViewerFilter filter)
	 */
	public void addBusinessFilterToRaisedException(ViewerFilter filter) {
		raisedExceptionBusinessFilters.add(filter);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getOwnedParameterToAdd()
	 */
	public List getOwnedParameterToAdd() {
		return ownedParameterEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getOwnedParameterToRemove()
	 */
	public List getOwnedParameterToRemove() {
		return ownedParameterEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getOwnedParameterToEdit()
	 */
	public Map getOwnedParameterToEdit() {
		return ownedParameterEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getOwnedParameterToMove()
	 */
	public List getOwnedParameterToMove() {
		return ownedParameterEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getOwnedParameterTable()
	 */
	public List getOwnedParameterTable() {
		return ownedParameterEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#initOwnedParameter(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initOwnedParameter(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			ownedParameterEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			ownedParameterEditUtil = new EMFListEditUtil(current, feature);
		this.ownedParameter.setInput(ownedParameterEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#updateOwnedParameter(EObject newValue)
	 */
	public void updateOwnedParameter(EObject newValue) {
		if(ownedParameterEditUtil!=null){
			ownedParameterEditUtil.reinit(newValue);
			ownedParameter.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#addFilterOwnedParameter(ViewerFilter filter)
	 */
	public void addFilterToOwnedParameter(ViewerFilter filter) {
		ownedParameterFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#addBusinessFilterOwnedParameter(ViewerFilter filter)
	 */
	public void addBusinessFilterToOwnedParameter(ViewerFilter filter) {
		ownedParameterBusinessFilters.add(filter);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getOwnedParameterSetToAdd()
	 */
	public List getOwnedParameterSetToAdd() {
		return ownedParameterSetEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getOwnedParameterSetToRemove()
	 */
	public List getOwnedParameterSetToRemove() {
		return ownedParameterSetEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getOwnedParameterSetToEdit()
	 */
	public Map getOwnedParameterSetToEdit() {
		return ownedParameterSetEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getOwnedParameterSetToMove()
	 */
	public List getOwnedParameterSetToMove() {
		return ownedParameterSetEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getOwnedParameterSetTable()
	 */
	public List getOwnedParameterSetTable() {
		return ownedParameterSetEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#initOwnedParameterSet(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initOwnedParameterSet(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			ownedParameterSetEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			ownedParameterSetEditUtil = new EMFListEditUtil(current, feature);
		this.ownedParameterSet.setInput(ownedParameterSetEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#updateOwnedParameterSet(EObject newValue)
	 */
	public void updateOwnedParameterSet(EObject newValue) {
		if(ownedParameterSetEditUtil!=null){
			ownedParameterSetEditUtil.reinit(newValue);
			ownedParameterSet.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#addFilterOwnedParameterSet(ViewerFilter filter)
	 */
	public void addFilterToOwnedParameterSet(ViewerFilter filter) {
		ownedParameterSetFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#addBusinessFilterOwnedParameterSet(ViewerFilter filter)
	 */
	public void addBusinessFilterToOwnedParameterSet(ViewerFilter filter) {
		ownedParameterSetBusinessFilters.add(filter);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getPackageImportToAdd()
	 */
	public List getPackageImportToAdd() {
		return packageImportEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getPackageImportToRemove()
	 */
	public List getPackageImportToRemove() {
		return packageImportEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getPackageImportToEdit()
	 */
	public Map getPackageImportToEdit() {
		return packageImportEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getPackageImportToMove()
	 */
	public List getPackageImportToMove() {
		return packageImportEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getPackageImportTable()
	 */
	public List getPackageImportTable() {
		return packageImportEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#initPackageImport(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#updatePackageImport(EObject newValue)
	 */
	public void updatePackageImport(EObject newValue) {
		if(packageImportEditUtil!=null){
			packageImportEditUtil.reinit(newValue);
			packageImport.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#addFilterPackageImport(ViewerFilter filter)
	 */
	public void addFilterToPackageImport(ViewerFilter filter) {
		packageImportFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#addBusinessFilterPackageImport(ViewerFilter filter)
	 */
	public void addBusinessFilterToPackageImport(ViewerFilter filter) {
		packageImportBusinessFilters.add(filter);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getElementImportToAdd()
	 */
	public List getElementImportToAdd() {
		return elementImportEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getElementImportToRemove()
	 */
	public List getElementImportToRemove() {
		return elementImportEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getElementImportToEdit()
	 */
	public Map getElementImportToEdit() {
		return elementImportEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getElementImportToMove()
	 */
	public List getElementImportToMove() {
		return elementImportEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getElementImportTable()
	 */
	public List getElementImportTable() {
		return elementImportEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#initElementImport(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#updateElementImport(EObject newValue)
	 */
	public void updateElementImport(EObject newValue) {
		if(elementImportEditUtil!=null){
			elementImportEditUtil.reinit(newValue);
			elementImport.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#addFilterElementImport(ViewerFilter filter)
	 */
	public void addFilterToElementImport(ViewerFilter filter) {
		elementImportFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#addBusinessFilterElementImport(ViewerFilter filter)
	 */
	public void addBusinessFilterToElementImport(ViewerFilter filter) {
		elementImportBusinessFilters.add(filter);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getOwnedRuleToAdd()
	 */
	public List getOwnedRuleToAdd() {
		return ownedRuleEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getOwnedRuleToRemove()
	 */
	public List getOwnedRuleToRemove() {
		return ownedRuleEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getOwnedRuleToEdit()
	 */
	public Map getOwnedRuleToEdit() {
		return ownedRuleEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getOwnedRuleToMove()
	 */
	public List getOwnedRuleToMove() {
		return ownedRuleEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#getOwnedRuleTable()
	 */
	public List getOwnedRuleTable() {
		return ownedRuleEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#initOwnedRule(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#updateOwnedRule(EObject newValue)
	 */
	public void updateOwnedRule(EObject newValue) {
		if(ownedRuleEditUtil!=null){
			ownedRuleEditUtil.reinit(newValue);
			ownedRule.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#addFilterOwnedRule(ViewerFilter filter)
	 */
	public void addFilterToOwnedRule(ViewerFilter filter) {
		ownedRuleFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ReceptionPropertiesEditionPart#addBusinessFilterOwnedRule(ViewerFilter filter)
	 */
	public void addBusinessFilterToOwnedRule(ViewerFilter filter) {
		ownedRuleBusinessFilters.add(filter);
	}











	
	// Start of user code additional methods
	
	// End of user code

}	
