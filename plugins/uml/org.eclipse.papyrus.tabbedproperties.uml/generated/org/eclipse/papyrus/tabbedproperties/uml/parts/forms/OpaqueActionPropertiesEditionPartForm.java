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

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.edit.ui.celleditor.FeatureEditorDialog;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.parts.EEFMessageManager;
import org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.api.policies.IPropertiesEditionPolicy;
import org.eclipse.emf.eef.runtime.api.providers.IPropertiesEditionPolicyProvider;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.parts.CompositePropertiesEditionPart;
import org.eclipse.emf.eef.runtime.impl.policies.EObjectPropertiesEditionContext;
import org.eclipse.emf.eef.runtime.impl.services.PropertiesContextService;
import org.eclipse.emf.eef.runtime.impl.services.PropertiesEditionPolicyProviderService;
import org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil;
import org.eclipse.emf.eef.runtime.ui.widgets.EMFComboViewer;
import org.eclipse.emf.eef.runtime.ui.widgets.FormUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.TabElementTreeSelectionDialog;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IMessageManager;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.ExceptionHandler;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.InterruptibleActivityRegion;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

// End of user code

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class OpaqueActionPropertiesEditionPartForm extends CompositePropertiesEditionPart implements IFormPropertiesEditionPart, OpaqueActionPropertiesEditionPart {

	protected EMFListEditUtil ownedCommentEditUtil;
	protected ReferencesTable<? extends EObject> ownedComment;
	protected List<ViewerFilter> ownedCommentBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> ownedCommentFilters = new ArrayList<ViewerFilter>();
	protected Text name;
	protected EMFComboViewer visibility;
	private EMFListEditUtil clientDependencyEditUtil;
	protected ReferencesTable<? extends EObject> clientDependency;
	protected List<ViewerFilter> clientDependencyBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> clientDependencyFilters = new ArrayList<ViewerFilter>();
	protected Button isLeaf;
	private EMFListEditUtil outgoingEditUtil;
	protected ReferencesTable<? extends EObject> outgoing;
	protected List<ViewerFilter> outgoingBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> outgoingFilters = new ArrayList<ViewerFilter>();
	private EMFListEditUtil incomingEditUtil;
	protected ReferencesTable<? extends EObject> incoming;
	protected List<ViewerFilter> incomingBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> incomingFilters = new ArrayList<ViewerFilter>();
	private EMFListEditUtil inPartitionEditUtil;
	protected ReferencesTable<? extends EObject> inPartition;
	protected List<ViewerFilter> inPartitionBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> inPartitionFilters = new ArrayList<ViewerFilter>();
	private EMFListEditUtil inInterruptibleRegionEditUtil;
	protected ReferencesTable<? extends EObject> inInterruptibleRegion;
	protected List<ViewerFilter> inInterruptibleRegionBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> inInterruptibleRegionFilters = new ArrayList<ViewerFilter>();
	private EMFListEditUtil redefinedNodeEditUtil;
	protected ReferencesTable<? extends EObject> redefinedNode;
	protected List<ViewerFilter> redefinedNodeBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> redefinedNodeFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil handlerEditUtil;
	protected ReferencesTable<? extends EObject> handler;
	protected List<ViewerFilter> handlerBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> handlerFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil localPreconditionEditUtil;
	protected ReferencesTable<? extends EObject> localPrecondition;
	protected List<ViewerFilter> localPreconditionBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> localPreconditionFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil localPostconditionEditUtil;
	protected ReferencesTable<? extends EObject> localPostcondition;
	protected List<ViewerFilter> localPostconditionBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> localPostconditionFilters = new ArrayList<ViewerFilter>();
	protected Text body;
	protected Button editBody;
	private EList bodyList;
	protected Text language;
	protected Button editLanguage;
	private EList languageList;
	protected EMFListEditUtil inputValueEditUtil;
	protected ReferencesTable<? extends EObject> inputValue;
	protected List<ViewerFilter> inputValueBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> inputValueFilters = new ArrayList<ViewerFilter>();
	protected EMFListEditUtil outputValueEditUtil;
	protected ReferencesTable<? extends EObject> outputValue;
	protected List<ViewerFilter> outputValueBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> outputValueFilters = new ArrayList<ViewerFilter>();




	
	/**
	 * Default constructor
	 * @param editionComponent the {@link IPropertiesEditionComponent} that manage this part
	 */
	public OpaqueActionPropertiesEditionPartForm(IPropertiesEditionComponent editionComponent) {
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
		createPropertiesGroup(widgetFactory, view);
		// Start of user code for additional ui definition

		// End of user code
		
	}

	protected void createPropertiesGroup(FormToolkit widgetFactory, final Composite view) {
		Section propertiesSection = widgetFactory.createSection(view, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		propertiesSection.setText(UMLMessages.OpaqueActionPropertiesEditionPart_PropertiesGroupLabel);
		GridData propertiesSectionData = new GridData(GridData.FILL_HORIZONTAL);
		propertiesSectionData.horizontalSpan = 3;
		propertiesSection.setLayoutData(propertiesSectionData);
		Composite propertiesGroup = widgetFactory.createComposite(propertiesSection);
		GridLayout propertiesGroupLayout = new GridLayout();
		propertiesGroupLayout.numColumns = 3;
		propertiesGroup.setLayout(propertiesGroupLayout);
		createOwnedCommentTableComposition(widgetFactory, propertiesGroup);
		createNameText(widgetFactory, propertiesGroup);
		createVisibilityEMFComboViewer(widgetFactory, propertiesGroup);
		createClientDependencyReferencesTable(widgetFactory, propertiesGroup);
		createIsLeafCheckbox(widgetFactory, propertiesGroup);
		createOutgoingReferencesTable(widgetFactory, propertiesGroup);
		createIncomingReferencesTable(widgetFactory, propertiesGroup);
		createInPartitionReferencesTable(widgetFactory, propertiesGroup);
		createInInterruptibleRegionReferencesTable(widgetFactory, propertiesGroup);
		createRedefinedNodeReferencesTable(widgetFactory, propertiesGroup);
		createHandlerTableComposition(widgetFactory, propertiesGroup);
		createLocalPreconditionTableComposition(widgetFactory, propertiesGroup);
		createLocalPostconditionTableComposition(widgetFactory, propertiesGroup);
		createBodyMultiValuedEditor(widgetFactory, propertiesGroup);
		createLanguageMultiValuedEditor(widgetFactory, propertiesGroup);
		createInputValueTableComposition(widgetFactory, propertiesGroup);
		createOutputValueTableComposition(widgetFactory, propertiesGroup);
		propertiesSection.setClient(propertiesGroup);
	}
	/**
	 * @param container
	 */
	protected void createOwnedCommentTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.ownedComment = new ReferencesTable<Comment>(UMLMessages.OpaqueActionPropertiesEditionPart_OwnedCommentLabel, new ReferencesTableListener<Comment>() {			
			public void handleAdd() { addToOwnedComment();}
			public void handleEdit(Comment element) { editOwnedComment(element); }
			public void handleMove(Comment element, int oldIndex, int newIndex) { moveOwnedComment(element, oldIndex, newIndex); }
			public void handleRemove(Comment element) { removeFromOwnedComment(element); }
			public void navigateTo(Comment element) { }
		});
		this.ownedComment.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.OpaqueAction.ownedComment, UMLViewsRepository.FORM_KIND));
		this.ownedComment.createControls(parent, widgetFactory);
		GridData ownedCommentData = new GridData(GridData.FILL_HORIZONTAL);
		ownedCommentData.horizontalSpan = 3;
		this.ownedComment.setLayoutData(ownedCommentData);
	}

	/**
	 * 
	 */
	protected void moveOwnedComment(Comment element, int oldIndex, int newIndex) {
		EObject editedElement = ownedCommentEditUtil.foundCorrespondingEObject(element);
		ownedCommentEditUtil.moveElement(element, oldIndex, newIndex);
		ownedComment.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.ownedComment, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
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
						OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.ownedComment,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}
		// End of user code

	}

	/**
	 * 
	 */
	protected void removeFromOwnedComment(Comment element) {
		// Start of user code for the removeFromOwnedComment() method body
		EObject editedElement = ownedCommentEditUtil.foundCorrespondingEObject(element);
		ownedCommentEditUtil.removeElement(element);
		ownedComment.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.ownedComment,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));
		// End of user code

	}

	/**
	 * 
	 */
	protected void editOwnedComment(Comment element) {
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
						OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.ownedComment,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}
		// End of user code

	}
	protected void createNameText(FormToolkit widgetFactory, Composite parent) {
		FormUtils.createPartLabel(widgetFactory, parent, UMLMessages.OpaqueActionPropertiesEditionPart_NameLabel, propertiesEditionComponent.isRequired(UMLViewsRepository.OpaqueAction.name, UMLViewsRepository.FORM_KIND));
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.name, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, null, name.getText()));
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.name, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
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
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.name, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
				}
			}

		});
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.OpaqueAction.name, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}
	protected void createVisibilityEMFComboViewer(FormToolkit widgetFactory, Composite parent) {
		FormUtils.createPartLabel(widgetFactory, parent, UMLMessages.OpaqueActionPropertiesEditionPart_VisibilityLabel, propertiesEditionComponent.isRequired(UMLViewsRepository.OpaqueAction.visibility, UMLViewsRepository.FORM_KIND));
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.visibility, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, getVisibility()));
			}

		});
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.OpaqueAction.visibility, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}
	protected void createClientDependencyReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.clientDependency = new ReferencesTable<Dependency>(UMLMessages.OpaqueActionPropertiesEditionPart_ClientDependencyLabel, new ReferencesTableListener<Dependency>() {
			public void handleAdd() {
				TabElementTreeSelectionDialog<Dependency> dialog = new TabElementTreeSelectionDialog<Dependency>(resourceSet, clientDependencyFilters, clientDependencyBusinessFilters,
				"Dependency", UMLPackage.eINSTANCE.getDependency(), current.eResource()) {
					@Override
					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!clientDependencyEditUtil.getVirtualList().contains(elem))
								clientDependencyEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.clientDependency,
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
		this.clientDependency.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.OpaqueAction.clientDependency, UMLViewsRepository.FORM_KIND));
		this.clientDependency.createControls(parent, widgetFactory);
		GridData clientDependencyData = new GridData(GridData.FILL_HORIZONTAL);
		clientDependencyData.horizontalSpan = 3;
		this.clientDependency.setLayoutData(clientDependencyData);
		this.clientDependency.disableMove();
	}

	/**
	 * 
	 */
	protected void moveClientDependency(Dependency element, int oldIndex, int newIndex) {
		EObject editedElement = clientDependencyEditUtil.foundCorrespondingEObject(element);
		clientDependencyEditUtil.moveElement(element, oldIndex, newIndex);
		clientDependency.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.clientDependency, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	protected void removeFromClientDependency(Dependency element) {
		// Start of user code for the removeFromClientDependency() method body
		EObject editedElement = clientDependencyEditUtil.foundCorrespondingEObject(element);
		clientDependencyEditUtil.removeElement(element);
		clientDependency.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.clientDependency,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));
		// End of user code

	}

	/**
	 * 
	 */
	protected void editClientDependency(Dependency element) {
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
						OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.clientDependency,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}
		// End of user code

	}
	protected void createIsLeafCheckbox(FormToolkit widgetFactory, Composite parent) {
		isLeaf = widgetFactory.createButton(parent, UMLMessages.OpaqueActionPropertiesEditionPart_IsLeafLabel, SWT.CHECK);
		isLeaf.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.isLeaf, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(isLeaf.getSelection())));
			}

		});
		GridData isLeafData = new GridData(GridData.FILL_HORIZONTAL);
		isLeafData.horizontalSpan = 2;
		isLeaf.setLayoutData(isLeafData);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.OpaqueAction.isLeaf, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}
	protected void createOutgoingReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.outgoing = new ReferencesTable<ActivityEdge>(UMLMessages.OpaqueActionPropertiesEditionPart_OutgoingLabel, new ReferencesTableListener<ActivityEdge>() {
			public void handleAdd() {
				TabElementTreeSelectionDialog<ActivityEdge> dialog = new TabElementTreeSelectionDialog<ActivityEdge>(resourceSet, outgoingFilters, outgoingBusinessFilters,
				"ActivityEdge", UMLPackage.eINSTANCE.getActivityEdge(), current.eResource()) {
					@Override
					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!outgoingEditUtil.getVirtualList().contains(elem))
								outgoingEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.outgoing,
								PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
						}
						outgoing.refresh();
					}
				};
				dialog.open();
			}
			public void handleEdit(ActivityEdge element) { editOutgoing(element); }
			public void handleMove(ActivityEdge element, int oldIndex, int newIndex) { moveOutgoing(element, oldIndex, newIndex); }
			public void handleRemove(ActivityEdge element) { removeFromOutgoing(element); }
			public void navigateTo(ActivityEdge element) { }
		});
		this.outgoing.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.OpaqueAction.outgoing, UMLViewsRepository.FORM_KIND));
		this.outgoing.createControls(parent, widgetFactory);
		GridData outgoingData = new GridData(GridData.FILL_HORIZONTAL);
		outgoingData.horizontalSpan = 3;
		this.outgoing.setLayoutData(outgoingData);
		this.outgoing.disableMove();
	}

	/**
	 * 
	 */
	protected void moveOutgoing(ActivityEdge element, int oldIndex, int newIndex) {
	}

	/**
	 * 
	 */
	protected void removeFromOutgoing(ActivityEdge element) {
		// Start of user code for the removeFromOutgoing() method body
		EObject editedElement = outgoingEditUtil.foundCorrespondingEObject(element);
		outgoingEditUtil.removeElement(element);
		outgoing.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.outgoing,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));
		// End of user code

	}

	/**
	 * 
	 */
	protected void editOutgoing(ActivityEdge element) {
		// Start of user code editOutgoing() method body

		EObject editedElement = outgoingEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				outgoingEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				outgoing.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.outgoing,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}
		// End of user code

	}
	protected void createIncomingReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.incoming = new ReferencesTable<ActivityEdge>(UMLMessages.OpaqueActionPropertiesEditionPart_IncomingLabel, new ReferencesTableListener<ActivityEdge>() {
			public void handleAdd() {
				TabElementTreeSelectionDialog<ActivityEdge> dialog = new TabElementTreeSelectionDialog<ActivityEdge>(resourceSet, incomingFilters, incomingBusinessFilters,
				"ActivityEdge", UMLPackage.eINSTANCE.getActivityEdge(), current.eResource()) {
					@Override
					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!incomingEditUtil.getVirtualList().contains(elem))
								incomingEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.incoming,
								PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
						}
						incoming.refresh();
					}
				};
				dialog.open();
			}
			public void handleEdit(ActivityEdge element) { editIncoming(element); }
			public void handleMove(ActivityEdge element, int oldIndex, int newIndex) { moveIncoming(element, oldIndex, newIndex); }
			public void handleRemove(ActivityEdge element) { removeFromIncoming(element); }
			public void navigateTo(ActivityEdge element) { }
		});
		this.incoming.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.OpaqueAction.incoming, UMLViewsRepository.FORM_KIND));
		this.incoming.createControls(parent, widgetFactory);
		GridData incomingData = new GridData(GridData.FILL_HORIZONTAL);
		incomingData.horizontalSpan = 3;
		this.incoming.setLayoutData(incomingData);
		this.incoming.disableMove();
	}

	/**
	 * 
	 */
	protected void moveIncoming(ActivityEdge element, int oldIndex, int newIndex) {
	}

	/**
	 * 
	 */
	protected void removeFromIncoming(ActivityEdge element) {
		// Start of user code for the removeFromIncoming() method body
		EObject editedElement = incomingEditUtil.foundCorrespondingEObject(element);
		incomingEditUtil.removeElement(element);
		incoming.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.incoming,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));
		// End of user code

	}

	/**
	 * 
	 */
	protected void editIncoming(ActivityEdge element) {
		// Start of user code editIncoming() method body

		EObject editedElement = incomingEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				incomingEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				incoming.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.incoming,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}
		// End of user code

	}
	protected void createInPartitionReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.inPartition = new ReferencesTable<ActivityPartition>(UMLMessages.OpaqueActionPropertiesEditionPart_InPartitionLabel, new ReferencesTableListener<ActivityPartition>() {
			public void handleAdd() {
				TabElementTreeSelectionDialog<ActivityPartition> dialog = new TabElementTreeSelectionDialog<ActivityPartition>(resourceSet, inPartitionFilters, inPartitionBusinessFilters,
				"ActivityPartition", UMLPackage.eINSTANCE.getActivityPartition(), current.eResource()) {
					@Override
					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!inPartitionEditUtil.getVirtualList().contains(elem))
								inPartitionEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.inPartition,
								PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
						}
						inPartition.refresh();
					}
				};
				dialog.open();
			}
			public void handleEdit(ActivityPartition element) { editInPartition(element); }
			public void handleMove(ActivityPartition element, int oldIndex, int newIndex) { moveInPartition(element, oldIndex, newIndex); }
			public void handleRemove(ActivityPartition element) { removeFromInPartition(element); }
			public void navigateTo(ActivityPartition element) { }
		});
		this.inPartition.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.OpaqueAction.inPartition, UMLViewsRepository.FORM_KIND));
		this.inPartition.createControls(parent, widgetFactory);
		GridData inPartitionData = new GridData(GridData.FILL_HORIZONTAL);
		inPartitionData.horizontalSpan = 3;
		this.inPartition.setLayoutData(inPartitionData);
		this.inPartition.disableMove();
	}

	/**
	 * 
	 */
	protected void moveInPartition(ActivityPartition element, int oldIndex, int newIndex) {
		EObject editedElement = inPartitionEditUtil.foundCorrespondingEObject(element);
		inPartitionEditUtil.moveElement(element, oldIndex, newIndex);
		inPartition.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.inPartition, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	protected void removeFromInPartition(ActivityPartition element) {
		// Start of user code for the removeFromInPartition() method body
		EObject editedElement = inPartitionEditUtil.foundCorrespondingEObject(element);
		inPartitionEditUtil.removeElement(element);
		inPartition.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.inPartition,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));
		// End of user code

	}

	/**
	 * 
	 */
	protected void editInPartition(ActivityPartition element) {
		// Start of user code editInPartition() method body

		EObject editedElement = inPartitionEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				inPartitionEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				inPartition.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.inPartition,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}
		// End of user code

	}
	protected void createInInterruptibleRegionReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.inInterruptibleRegion = new ReferencesTable<InterruptibleActivityRegion>(UMLMessages.OpaqueActionPropertiesEditionPart_InInterruptibleRegionLabel, new ReferencesTableListener<InterruptibleActivityRegion>() {
			public void handleAdd() {
				TabElementTreeSelectionDialog<InterruptibleActivityRegion> dialog = new TabElementTreeSelectionDialog<InterruptibleActivityRegion>(resourceSet, inInterruptibleRegionFilters, inInterruptibleRegionBusinessFilters,
				"InterruptibleActivityRegion", UMLPackage.eINSTANCE.getInterruptibleActivityRegion(), current.eResource()) {
					@Override
					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!inInterruptibleRegionEditUtil.getVirtualList().contains(elem))
								inInterruptibleRegionEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.inInterruptibleRegion,
								PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
						}
						inInterruptibleRegion.refresh();
					}
				};
				dialog.open();
			}
			public void handleEdit(InterruptibleActivityRegion element) { editInInterruptibleRegion(element); }
			public void handleMove(InterruptibleActivityRegion element, int oldIndex, int newIndex) { moveInInterruptibleRegion(element, oldIndex, newIndex); }
			public void handleRemove(InterruptibleActivityRegion element) { removeFromInInterruptibleRegion(element); }
			public void navigateTo(InterruptibleActivityRegion element) { }
		});
		this.inInterruptibleRegion.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.OpaqueAction.inInterruptibleRegion, UMLViewsRepository.FORM_KIND));
		this.inInterruptibleRegion.createControls(parent, widgetFactory);
		GridData inInterruptibleRegionData = new GridData(GridData.FILL_HORIZONTAL);
		inInterruptibleRegionData.horizontalSpan = 3;
		this.inInterruptibleRegion.setLayoutData(inInterruptibleRegionData);
		this.inInterruptibleRegion.disableMove();
	}

	/**
	 * 
	 */
	protected void moveInInterruptibleRegion(InterruptibleActivityRegion element, int oldIndex, int newIndex) {
		EObject editedElement = inInterruptibleRegionEditUtil.foundCorrespondingEObject(element);
		inInterruptibleRegionEditUtil.moveElement(element, oldIndex, newIndex);
		inInterruptibleRegion.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.inInterruptibleRegion, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	protected void removeFromInInterruptibleRegion(InterruptibleActivityRegion element) {
		// Start of user code for the removeFromInInterruptibleRegion() method body
		EObject editedElement = inInterruptibleRegionEditUtil.foundCorrespondingEObject(element);
		inInterruptibleRegionEditUtil.removeElement(element);
		inInterruptibleRegion.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.inInterruptibleRegion,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));
		// End of user code

	}

	/**
	 * 
	 */
	protected void editInInterruptibleRegion(InterruptibleActivityRegion element) {
		// Start of user code editInInterruptibleRegion() method body

		EObject editedElement = inInterruptibleRegionEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				inInterruptibleRegionEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				inInterruptibleRegion.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						OpaqueActionPropertiesEditionPartForm.this,
						UMLViewsRepository.OpaqueAction.inInterruptibleRegion, PropertiesEditionEvent.COMMIT,
						PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}
		// End of user code

	}
	protected void createRedefinedNodeReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.redefinedNode = new ReferencesTable<ActivityNode>(UMLMessages.OpaqueActionPropertiesEditionPart_RedefinedNodeLabel, new ReferencesTableListener<ActivityNode>() {
			public void handleAdd() {
				TabElementTreeSelectionDialog<ActivityNode> dialog = new TabElementTreeSelectionDialog<ActivityNode>(resourceSet, redefinedNodeFilters, redefinedNodeBusinessFilters,
				"ActivityNode", UMLPackage.eINSTANCE.getActivityNode(), current.eResource()) {
					@Override
					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!redefinedNodeEditUtil.getVirtualList().contains(elem))
								redefinedNodeEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.redefinedNode,
								PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
						}
						redefinedNode.refresh();
					}
				};
				dialog.open();
			}
			public void handleEdit(ActivityNode element) { editRedefinedNode(element); }
			public void handleMove(ActivityNode element, int oldIndex, int newIndex) { moveRedefinedNode(element, oldIndex, newIndex); }
			public void handleRemove(ActivityNode element) { removeFromRedefinedNode(element); }
			public void navigateTo(ActivityNode element) { }
		});
		this.redefinedNode.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.OpaqueAction.redefinedNode, UMLViewsRepository.FORM_KIND));
		this.redefinedNode.createControls(parent, widgetFactory);
		GridData redefinedNodeData = new GridData(GridData.FILL_HORIZONTAL);
		redefinedNodeData.horizontalSpan = 3;
		this.redefinedNode.setLayoutData(redefinedNodeData);
		this.redefinedNode.disableMove();
	}

	/**
	 * 
	 */
	protected void moveRedefinedNode(ActivityNode element, int oldIndex, int newIndex) {
	}

	/**
	 * 
	 */
	protected void removeFromRedefinedNode(ActivityNode element) {
		// Start of user code for the removeFromRedefinedNode() method body
		EObject editedElement = redefinedNodeEditUtil.foundCorrespondingEObject(element);
		redefinedNodeEditUtil.removeElement(element);
		redefinedNode.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.redefinedNode,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));
		// End of user code

	}

	/**
	 * 
	 */
	protected void editRedefinedNode(ActivityNode element) {
		// Start of user code editRedefinedNode() method body

		EObject editedElement = redefinedNodeEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				redefinedNodeEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				redefinedNode.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.redefinedNode,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}
		// End of user code

	}
	/**
	 * @param container
	 */
	protected void createHandlerTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.handler = new ReferencesTable<ExceptionHandler>(UMLMessages.OpaqueActionPropertiesEditionPart_HandlerLabel, new ReferencesTableListener<ExceptionHandler>() {			
			public void handleAdd() { addToHandler();}
			public void handleEdit(ExceptionHandler element) { editHandler(element); }
			public void handleMove(ExceptionHandler element, int oldIndex, int newIndex) { moveHandler(element, oldIndex, newIndex); }
			public void handleRemove(ExceptionHandler element) { removeFromHandler(element); }
			public void navigateTo(ExceptionHandler element) { }
		});
		this.handler.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.OpaqueAction.handler, UMLViewsRepository.FORM_KIND));
		this.handler.createControls(parent, widgetFactory);
		GridData handlerData = new GridData(GridData.FILL_HORIZONTAL);
		handlerData.horizontalSpan = 3;
		this.handler.setLayoutData(handlerData);
	}

	/**
	 * 
	 */
	protected void moveHandler(ExceptionHandler element, int oldIndex, int newIndex) {
		EObject editedElement = handlerEditUtil.foundCorrespondingEObject(element);
		handlerEditUtil.moveElement(element, oldIndex, newIndex);
		handler.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.handler, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
	}

	/**
	 * 
	 */
	protected void addToHandler() {
		// Start of user code addToHandler() method body
		ExceptionHandler eObject = UMLFactory.eINSTANCE.createExceptionHandler();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				handlerEditUtil.addElement(propertiesEditionObject);
				handler.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.handler,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}
		// End of user code

	}

	/**
	 * 
	 */
	protected void removeFromHandler(ExceptionHandler element) {
		// Start of user code for the removeFromHandler() method body
		EObject editedElement = handlerEditUtil.foundCorrespondingEObject(element);
		handlerEditUtil.removeElement(element);
		handler.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.handler,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));
		// End of user code

	}

	/**
	 * 
	 */
	protected void editHandler(ExceptionHandler element) {
		// Start of user code editHandler() method body
		EObject editedElement = handlerEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				handlerEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				handler.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.handler,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}
		// End of user code

	}
	/**
	 * @param container
	 */
	protected void createLocalPreconditionTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.localPrecondition = new ReferencesTable<Constraint>(UMLMessages.OpaqueActionPropertiesEditionPart_LocalPreconditionLabel, new ReferencesTableListener<Constraint>() {			
			public void handleAdd() { addToLocalPrecondition();}
			public void handleEdit(Constraint element) { editLocalPrecondition(element); }
			public void handleMove(Constraint element, int oldIndex, int newIndex) { moveLocalPrecondition(element, oldIndex, newIndex); }
			public void handleRemove(Constraint element) { removeFromLocalPrecondition(element); }
			public void navigateTo(Constraint element) { }
		});
		this.localPrecondition.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.OpaqueAction.localPrecondition, UMLViewsRepository.FORM_KIND));
		this.localPrecondition.createControls(parent, widgetFactory);
		GridData localPreconditionData = new GridData(GridData.FILL_HORIZONTAL);
		localPreconditionData.horizontalSpan = 3;
		this.localPrecondition.setLayoutData(localPreconditionData);
	}

	/**
	 * 
	 */
	protected void moveLocalPrecondition(Constraint element, int oldIndex, int newIndex) {
		EObject editedElement = localPreconditionEditUtil.foundCorrespondingEObject(element);
		localPreconditionEditUtil.moveElement(element, oldIndex, newIndex);
		localPrecondition.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.localPrecondition, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
	}

	/**
	 * 
	 */
	protected void addToLocalPrecondition() {
		// Start of user code addToLocalPrecondition() method body
		Constraint eObject = UMLFactory.eINSTANCE.createConstraint();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				localPreconditionEditUtil.addElement(propertiesEditionObject);
				localPrecondition.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.localPrecondition,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}
		// End of user code

	}

	/**
	 * 
	 */
	protected void removeFromLocalPrecondition(Constraint element) {
		// Start of user code for the removeFromLocalPrecondition() method body
		EObject editedElement = localPreconditionEditUtil.foundCorrespondingEObject(element);
		localPreconditionEditUtil.removeElement(element);
		localPrecondition.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.localPrecondition,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));
		// End of user code

	}

	/**
	 * 
	 */
	protected void editLocalPrecondition(Constraint element) {
		// Start of user code editLocalPrecondition() method body
		EObject editedElement = localPreconditionEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				localPreconditionEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				localPrecondition.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.localPrecondition,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}
		// End of user code

	}
	/**
	 * @param container
	 */
	protected void createLocalPostconditionTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.localPostcondition = new ReferencesTable<Constraint>(UMLMessages.OpaqueActionPropertiesEditionPart_LocalPostconditionLabel, new ReferencesTableListener<Constraint>() {			
			public void handleAdd() { addToLocalPostcondition();}
			public void handleEdit(Constraint element) { editLocalPostcondition(element); }
			public void handleMove(Constraint element, int oldIndex, int newIndex) { moveLocalPostcondition(element, oldIndex, newIndex); }
			public void handleRemove(Constraint element) { removeFromLocalPostcondition(element); }
			public void navigateTo(Constraint element) { }
		});
		this.localPostcondition.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.OpaqueAction.localPostcondition, UMLViewsRepository.FORM_KIND));
		this.localPostcondition.createControls(parent, widgetFactory);
		GridData localPostconditionData = new GridData(GridData.FILL_HORIZONTAL);
		localPostconditionData.horizontalSpan = 3;
		this.localPostcondition.setLayoutData(localPostconditionData);
	}

	/**
	 * 
	 */
	protected void moveLocalPostcondition(Constraint element, int oldIndex, int newIndex) {
		EObject editedElement = localPostconditionEditUtil.foundCorrespondingEObject(element);
		localPostconditionEditUtil.moveElement(element, oldIndex, newIndex);
		localPostcondition.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.localPostcondition, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
	}

	/**
	 * 
	 */
	protected void addToLocalPostcondition() {
		// Start of user code addToLocalPostcondition() method body
		Constraint eObject = UMLFactory.eINSTANCE.createConstraint();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				localPostconditionEditUtil.addElement(propertiesEditionObject);
				localPostcondition.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.localPostcondition,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}
		// End of user code

	}

	/**
	 * 
	 */
	protected void removeFromLocalPostcondition(Constraint element) {
		// Start of user code for the removeFromLocalPostcondition() method body
		EObject editedElement = localPostconditionEditUtil.foundCorrespondingEObject(element);
		localPostconditionEditUtil.removeElement(element);
		localPostcondition.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.localPostcondition,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));
		// End of user code

	}

	/**
	 * 
	 */
	protected void editLocalPostcondition(Constraint element) {
		// Start of user code editLocalPostcondition() method body
		EObject editedElement = localPostconditionEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				localPostconditionEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				localPostcondition.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.localPostcondition,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}
		// End of user code

	}
	protected void createBodyMultiValuedEditor(FormToolkit widgetFactory, Composite parent) {
		body = widgetFactory.createText(parent, "", SWT.READ_ONLY); //$NON-NLS-1$
		GridData bodyData = new GridData(GridData.FILL_HORIZONTAL);
		bodyData.horizontalSpan = 2;
		body.setLayoutData(bodyData);
		editBody = widgetFactory.createButton(parent, UMLMessages.OpaqueActionPropertiesEditionPart_BodyLabel, SWT.NONE);
		GridData editBodyData = new GridData();
		editBody.setLayoutData(editBodyData);
		editBody.addSelectionListener(new SelectionAdapter() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				EObject opaqueAction = PropertiesContextService.getInstance().lastElement();
				FeatureEditorDialog dialog = new FeatureEditorDialog(Display.getDefault().getActiveShell(), new AdapterFactoryLabelProvider(adapterFactory), opaqueAction, UMLPackage.eINSTANCE.getOpaqueAction_Body().getEType(), 
						bodyList, "OpaqueAction", null, false, false); //$NON-NLS-1$
				if (dialog.open() == Window.OK) {
					bodyList = dialog.getResult();
					if (bodyList == null) {
						bodyList = new BasicEList();
					}
					body.setText(bodyList.toString());
					setHasChanged(true);
				}
			}
		});
	}
	protected void createLanguageMultiValuedEditor(FormToolkit widgetFactory, Composite parent) {
		language = widgetFactory.createText(parent, "", SWT.READ_ONLY); //$NON-NLS-1$
		GridData languageData = new GridData(GridData.FILL_HORIZONTAL);
		languageData.horizontalSpan = 2;
		language.setLayoutData(languageData);
		editLanguage = widgetFactory.createButton(parent, UMLMessages.OpaqueActionPropertiesEditionPart_LanguageLabel, SWT.NONE);
		GridData editLanguageData = new GridData();
		editLanguage.setLayoutData(editLanguageData);
		editLanguage.addSelectionListener(new SelectionAdapter() {

			/*
			 * (non-Javadoc)
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				EObject opaqueAction = PropertiesContextService.getInstance().lastElement();
				FeatureEditorDialog dialog = new FeatureEditorDialog(Display.getDefault().getActiveShell(), new AdapterFactoryLabelProvider(adapterFactory), opaqueAction, UMLPackage.eINSTANCE.getOpaqueAction_Language().getEType(), 
						languageList, "OpaqueAction", null, false, false); //$NON-NLS-1$
				if (dialog.open() == Window.OK) {
					languageList = dialog.getResult();
					if (languageList == null) {
						languageList = new BasicEList();
					}
					language.setText(languageList.toString());
					setHasChanged(true);
				}
			}
		});
	}
	/**
	 * @param container
	 */
	protected void createInputValueTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.inputValue = new ReferencesTable<InputPin>(UMLMessages.OpaqueActionPropertiesEditionPart_InputValueLabel, new ReferencesTableListener<InputPin>() {			
			public void handleAdd() { addToInputValue();}
			public void handleEdit(InputPin element) { editInputValue(element); }
			public void handleMove(InputPin element, int oldIndex, int newIndex) { moveInputValue(element, oldIndex, newIndex); }
			public void handleRemove(InputPin element) { removeFromInputValue(element); }
			public void navigateTo(InputPin element) { }
		});
		this.inputValue.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.OpaqueAction.inputValue, UMLViewsRepository.FORM_KIND));
		this.inputValue.createControls(parent, widgetFactory);
		GridData inputValueData = new GridData(GridData.FILL_HORIZONTAL);
		inputValueData.horizontalSpan = 3;
		this.inputValue.setLayoutData(inputValueData);
	}

	/**
	 * 
	 */
	protected void moveInputValue(InputPin element, int oldIndex, int newIndex) {
		EObject editedElement = inputValueEditUtil.foundCorrespondingEObject(element);
		inputValueEditUtil.moveElement(element, oldIndex, newIndex);
		inputValue.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.inputValue, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
	}

	/**
	 * 
	 */
	protected void addToInputValue() {
		// Start of user code addToInputValue() method body
		InputPin eObject = UMLFactory.eINSTANCE.createInputPin();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				inputValueEditUtil.addElement(propertiesEditionObject);
				inputValue.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.inputValue,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}
		// End of user code

	}

	/**
	 * 
	 */
	protected void removeFromInputValue(InputPin element) {
		// Start of user code for the removeFromInputValue() method body
		EObject editedElement = inputValueEditUtil.foundCorrespondingEObject(element);
		inputValueEditUtil.removeElement(element);
		inputValue.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.inputValue,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));
		// End of user code

	}

	/**
	 * 
	 */
	protected void editInputValue(InputPin element) {
		// Start of user code editInputValue() method body
		EObject editedElement = inputValueEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				inputValueEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				inputValue.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.inputValue,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}
		// End of user code

	}
	/**
	 * @param container
	 */
	protected void createOutputValueTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.outputValue = new ReferencesTable<OutputPin>(UMLMessages.OpaqueActionPropertiesEditionPart_OutputValueLabel, new ReferencesTableListener<OutputPin>() {			
			public void handleAdd() { addToOutputValue();}
			public void handleEdit(OutputPin element) { editOutputValue(element); }
			public void handleMove(OutputPin element, int oldIndex, int newIndex) { moveOutputValue(element, oldIndex, newIndex); }
			public void handleRemove(OutputPin element) { removeFromOutputValue(element); }
			public void navigateTo(OutputPin element) { }
		});
		this.outputValue.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.OpaqueAction.outputValue, UMLViewsRepository.FORM_KIND));
		this.outputValue.createControls(parent, widgetFactory);
		GridData outputValueData = new GridData(GridData.FILL_HORIZONTAL);
		outputValueData.horizontalSpan = 3;
		this.outputValue.setLayoutData(outputValueData);
	}

	/**
	 * 
	 */
	protected void moveOutputValue(OutputPin element, int oldIndex, int newIndex) {
		EObject editedElement = outputValueEditUtil.foundCorrespondingEObject(element);
		outputValueEditUtil.moveElement(element, oldIndex, newIndex);
		outputValue.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.outputValue, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));	
	}

	/**
	 * 
	 */
	protected void addToOutputValue() {
		// Start of user code addToOutputValue() method body
		OutputPin eObject = UMLFactory.eINSTANCE.createOutputPin();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				outputValueEditUtil.addElement(propertiesEditionObject);
				outputValue.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.outputValue,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}
		// End of user code

	}

	/**
	 * 
	 */
	protected void removeFromOutputValue(OutputPin element) {
		// Start of user code for the removeFromOutputValue() method body
		EObject editedElement = outputValueEditUtil.foundCorrespondingEObject(element);
		outputValueEditUtil.removeElement(element);
		outputValue.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.outputValue,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));
		// End of user code

	}

	/**
	 * 
	 */
	protected void editOutputValue(OutputPin element) {
		// Start of user code editOutputValue() method body
		EObject editedElement = outputValueEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				outputValueEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				outputValue.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						OpaqueActionPropertiesEditionPartForm.this, UMLViewsRepository.OpaqueAction.outputValue,
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getOwnedCommentToAdd()
	 */
	public List getOwnedCommentToAdd() {
		return ownedCommentEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getOwnedCommentToRemove()
	 */
	public List getOwnedCommentToRemove() {
		return ownedCommentEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getOwnedCommentToEdit()
	 */
	public Map getOwnedCommentToEdit() {
		return ownedCommentEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getOwnedCommentToMove()
	 */
	public List getOwnedCommentToMove() {
		return ownedCommentEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getOwnedCommentTable()
	 */
	public List getOwnedCommentTable() {
		return ownedCommentEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#initOwnedComment(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#updateOwnedComment(EObject newValue)
	 */
	public void updateOwnedComment(EObject newValue) {
		if(ownedCommentEditUtil != null){
			ownedCommentEditUtil.reinit(newValue);
			ownedComment.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addFilterOwnedComment(ViewerFilter filter)
	 */
	public void addFilterToOwnedComment(ViewerFilter filter) {
		ownedCommentFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addBusinessFilterOwnedComment(ViewerFilter filter)
	 */
	public void addBusinessFilterToOwnedComment(ViewerFilter filter) {
		ownedCommentBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#isContainedInOwnedCommentTable(EObject element)
	 */
	public boolean isContainedInOwnedCommentTable(EObject element) {
		return ownedCommentEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getName()
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#setName(String newValue)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getVisibility()
	 */
	public Enumerator getVisibility() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) visibility.getSelection()).getFirstElement();
		return selection.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#initVisibility(EEnum eenum, Enumerator current)
	 */
	public void initVisibility(EEnum eenum, Enumerator current) {
		visibility.setInput(eenum.getELiterals());
		visibility.modelUpdating(new StructuredSelection(current));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#setVisibility(Enumerator newValue)
	 */
	public void setVisibility(Enumerator newValue) {
		visibility.modelUpdating(new StructuredSelection(newValue));
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getClientDependencyToAdd()
	 */
	public List getClientDependencyToAdd() {
		return clientDependencyEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getClientDependencyToRemove()
	 */
	public List getClientDependencyToRemove() {
		return clientDependencyEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getClientDependencyTable()
	 */
	public List getClientDependencyTable() {
		return clientDependencyEditUtil.getVirtualList();
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#initClientDependency(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#updateClientDependency(EObject newValue)
	 */
	public void updateClientDependency(EObject newValue) {
		if(clientDependencyEditUtil != null){
			clientDependencyEditUtil.reinit(newValue);
			clientDependency.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addFilterClientDependency(ViewerFilter filter)
	 */
	public void addFilterToClientDependency(ViewerFilter filter) {
		clientDependencyFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addBusinessFilterClientDependency(ViewerFilter filter)
	 */
	public void addBusinessFilterToClientDependency(ViewerFilter filter) {
		clientDependencyBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#isContainedInClientDependencyTable(EObject element)
	 */
	public boolean isContainedInClientDependencyTable(EObject element) {
		return clientDependencyEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getIsLeaf()
	 */
	public Boolean getIsLeaf() {
		return Boolean.valueOf(isLeaf.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#setIsLeaf(Boolean newValue)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getOutgoingToAdd()
	 */
	public List getOutgoingToAdd() {
		return outgoingEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getOutgoingToRemove()
	 */
	public List getOutgoingToRemove() {
		return outgoingEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getOutgoingTable()
	 */
	public List getOutgoingTable() {
		return outgoingEditUtil.getVirtualList();
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#initOutgoing(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initOutgoing(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			outgoingEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			outgoingEditUtil = new EMFListEditUtil(current, feature);
		this.outgoing.setInput(outgoingEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#updateOutgoing(EObject newValue)
	 */
	public void updateOutgoing(EObject newValue) {
		if(outgoingEditUtil != null){
			outgoingEditUtil.reinit(newValue);
			outgoing.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addFilterOutgoing(ViewerFilter filter)
	 */
	public void addFilterToOutgoing(ViewerFilter filter) {
		outgoingFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addBusinessFilterOutgoing(ViewerFilter filter)
	 */
	public void addBusinessFilterToOutgoing(ViewerFilter filter) {
		outgoingBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#isContainedInOutgoingTable(EObject element)
	 */
	public boolean isContainedInOutgoingTable(EObject element) {
		return outgoingEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getIncomingToAdd()
	 */
	public List getIncomingToAdd() {
		return incomingEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getIncomingToRemove()
	 */
	public List getIncomingToRemove() {
		return incomingEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getIncomingTable()
	 */
	public List getIncomingTable() {
		return incomingEditUtil.getVirtualList();
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#initIncoming(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initIncoming(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			incomingEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			incomingEditUtil = new EMFListEditUtil(current, feature);
		this.incoming.setInput(incomingEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#updateIncoming(EObject newValue)
	 */
	public void updateIncoming(EObject newValue) {
		if(incomingEditUtil != null){
			incomingEditUtil.reinit(newValue);
			incoming.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addFilterIncoming(ViewerFilter filter)
	 */
	public void addFilterToIncoming(ViewerFilter filter) {
		incomingFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addBusinessFilterIncoming(ViewerFilter filter)
	 */
	public void addBusinessFilterToIncoming(ViewerFilter filter) {
		incomingBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#isContainedInIncomingTable(EObject element)
	 */
	public boolean isContainedInIncomingTable(EObject element) {
		return incomingEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getInPartitionToAdd()
	 */
	public List getInPartitionToAdd() {
		return inPartitionEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getInPartitionToRemove()
	 */
	public List getInPartitionToRemove() {
		return inPartitionEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getInPartitionTable()
	 */
	public List getInPartitionTable() {
		return inPartitionEditUtil.getVirtualList();
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#initInPartition(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initInPartition(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			inPartitionEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			inPartitionEditUtil = new EMFListEditUtil(current, feature);
		this.inPartition.setInput(inPartitionEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#updateInPartition(EObject newValue)
	 */
	public void updateInPartition(EObject newValue) {
		if(inPartitionEditUtil != null){
			inPartitionEditUtil.reinit(newValue);
			inPartition.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addFilterInPartition(ViewerFilter filter)
	 */
	public void addFilterToInPartition(ViewerFilter filter) {
		inPartitionFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addBusinessFilterInPartition(ViewerFilter filter)
	 */
	public void addBusinessFilterToInPartition(ViewerFilter filter) {
		inPartitionBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#isContainedInInPartitionTable(EObject element)
	 */
	public boolean isContainedInInPartitionTable(EObject element) {
		return inPartitionEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getInInterruptibleRegionToAdd()
	 */
	public List getInInterruptibleRegionToAdd() {
		return inInterruptibleRegionEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getInInterruptibleRegionToRemove()
	 */
	public List getInInterruptibleRegionToRemove() {
		return inInterruptibleRegionEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getInInterruptibleRegionTable()
	 */
	public List getInInterruptibleRegionTable() {
		return inInterruptibleRegionEditUtil.getVirtualList();
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#initInInterruptibleRegion(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initInInterruptibleRegion(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			inInterruptibleRegionEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			inInterruptibleRegionEditUtil = new EMFListEditUtil(current, feature);
		this.inInterruptibleRegion.setInput(inInterruptibleRegionEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#updateInInterruptibleRegion(EObject newValue)
	 */
	public void updateInInterruptibleRegion(EObject newValue) {
		if(inInterruptibleRegionEditUtil != null){
			inInterruptibleRegionEditUtil.reinit(newValue);
			inInterruptibleRegion.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addFilterInInterruptibleRegion(ViewerFilter filter)
	 */
	public void addFilterToInInterruptibleRegion(ViewerFilter filter) {
		inInterruptibleRegionFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addBusinessFilterInInterruptibleRegion(ViewerFilter filter)
	 */
	public void addBusinessFilterToInInterruptibleRegion(ViewerFilter filter) {
		inInterruptibleRegionBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#isContainedInInInterruptibleRegionTable(EObject element)
	 */
	public boolean isContainedInInInterruptibleRegionTable(EObject element) {
		return inInterruptibleRegionEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getRedefinedNodeToAdd()
	 */
	public List getRedefinedNodeToAdd() {
		return redefinedNodeEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getRedefinedNodeToRemove()
	 */
	public List getRedefinedNodeToRemove() {
		return redefinedNodeEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getRedefinedNodeTable()
	 */
	public List getRedefinedNodeTable() {
		return redefinedNodeEditUtil.getVirtualList();
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#initRedefinedNode(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initRedefinedNode(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			redefinedNodeEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			redefinedNodeEditUtil = new EMFListEditUtil(current, feature);
		this.redefinedNode.setInput(redefinedNodeEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#updateRedefinedNode(EObject newValue)
	 */
	public void updateRedefinedNode(EObject newValue) {
		if(redefinedNodeEditUtil != null){
			redefinedNodeEditUtil.reinit(newValue);
			redefinedNode.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addFilterRedefinedNode(ViewerFilter filter)
	 */
	public void addFilterToRedefinedNode(ViewerFilter filter) {
		redefinedNodeFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addBusinessFilterRedefinedNode(ViewerFilter filter)
	 */
	public void addBusinessFilterToRedefinedNode(ViewerFilter filter) {
		redefinedNodeBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#isContainedInRedefinedNodeTable(EObject element)
	 */
	public boolean isContainedInRedefinedNodeTable(EObject element) {
		return redefinedNodeEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getHandlerToAdd()
	 */
	public List getHandlerToAdd() {
		return handlerEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getHandlerToRemove()
	 */
	public List getHandlerToRemove() {
		return handlerEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getHandlerToEdit()
	 */
	public Map getHandlerToEdit() {
		return handlerEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getHandlerToMove()
	 */
	public List getHandlerToMove() {
		return handlerEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getHandlerTable()
	 */
	public List getHandlerTable() {
		return handlerEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#initHandler(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initHandler(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			handlerEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			handlerEditUtil = new EMFListEditUtil(current, feature);
		this.handler.setInput(handlerEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#updateHandler(EObject newValue)
	 */
	public void updateHandler(EObject newValue) {
		if(handlerEditUtil != null){
			handlerEditUtil.reinit(newValue);
			handler.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addFilterHandler(ViewerFilter filter)
	 */
	public void addFilterToHandler(ViewerFilter filter) {
		handlerFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addBusinessFilterHandler(ViewerFilter filter)
	 */
	public void addBusinessFilterToHandler(ViewerFilter filter) {
		handlerBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#isContainedInHandlerTable(EObject element)
	 */
	public boolean isContainedInHandlerTable(EObject element) {
		return handlerEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getLocalPreconditionToAdd()
	 */
	public List getLocalPreconditionToAdd() {
		return localPreconditionEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getLocalPreconditionToRemove()
	 */
	public List getLocalPreconditionToRemove() {
		return localPreconditionEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getLocalPreconditionToEdit()
	 */
	public Map getLocalPreconditionToEdit() {
		return localPreconditionEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getLocalPreconditionToMove()
	 */
	public List getLocalPreconditionToMove() {
		return localPreconditionEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getLocalPreconditionTable()
	 */
	public List getLocalPreconditionTable() {
		return localPreconditionEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#initLocalPrecondition(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initLocalPrecondition(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			localPreconditionEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			localPreconditionEditUtil = new EMFListEditUtil(current, feature);
		this.localPrecondition.setInput(localPreconditionEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#updateLocalPrecondition(EObject newValue)
	 */
	public void updateLocalPrecondition(EObject newValue) {
		if(localPreconditionEditUtil != null){
			localPreconditionEditUtil.reinit(newValue);
			localPrecondition.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addFilterLocalPrecondition(ViewerFilter filter)
	 */
	public void addFilterToLocalPrecondition(ViewerFilter filter) {
		localPreconditionFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addBusinessFilterLocalPrecondition(ViewerFilter filter)
	 */
	public void addBusinessFilterToLocalPrecondition(ViewerFilter filter) {
		localPreconditionBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#isContainedInLocalPreconditionTable(EObject element)
	 */
	public boolean isContainedInLocalPreconditionTable(EObject element) {
		return localPreconditionEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getLocalPostconditionToAdd()
	 */
	public List getLocalPostconditionToAdd() {
		return localPostconditionEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getLocalPostconditionToRemove()
	 */
	public List getLocalPostconditionToRemove() {
		return localPostconditionEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getLocalPostconditionToEdit()
	 */
	public Map getLocalPostconditionToEdit() {
		return localPostconditionEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getLocalPostconditionToMove()
	 */
	public List getLocalPostconditionToMove() {
		return localPostconditionEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getLocalPostconditionTable()
	 */
	public List getLocalPostconditionTable() {
		return localPostconditionEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#initLocalPostcondition(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initLocalPostcondition(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			localPostconditionEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			localPostconditionEditUtil = new EMFListEditUtil(current, feature);
		this.localPostcondition.setInput(localPostconditionEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#updateLocalPostcondition(EObject newValue)
	 */
	public void updateLocalPostcondition(EObject newValue) {
		if(localPostconditionEditUtil != null){
			localPostconditionEditUtil.reinit(newValue);
			localPostcondition.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addFilterLocalPostcondition(ViewerFilter filter)
	 */
	public void addFilterToLocalPostcondition(ViewerFilter filter) {
		localPostconditionFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addBusinessFilterLocalPostcondition(ViewerFilter filter)
	 */
	public void addBusinessFilterToLocalPostcondition(ViewerFilter filter) {
		localPostconditionBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#isContainedInLocalPostconditionTable(EObject element)
	 */
	public boolean isContainedInLocalPostconditionTable(EObject element) {
		return localPostconditionEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getBody()
	 */
	public EList getBody() {
		return bodyList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#setBody(EList newValue)
	 */
	public void setBody(EList newValue) {
		bodyList = newValue;
		if (newValue != null) {
			body.setText(bodyList.toString());
		} else {
			body.setText(""); //$NON-NLS-1$
		}
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getLanguage()
	 */
	public EList getLanguage() {
		return languageList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#setLanguage(EList newValue)
	 */
	public void setLanguage(EList newValue) {
		languageList = newValue;
		if (newValue != null) {
			language.setText(languageList.toString());
		} else {
			language.setText(""); //$NON-NLS-1$
		}
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getInputValueToAdd()
	 */
	public List getInputValueToAdd() {
		return inputValueEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getInputValueToRemove()
	 */
	public List getInputValueToRemove() {
		return inputValueEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getInputValueToEdit()
	 */
	public Map getInputValueToEdit() {
		return inputValueEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getInputValueToMove()
	 */
	public List getInputValueToMove() {
		return inputValueEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getInputValueTable()
	 */
	public List getInputValueTable() {
		return inputValueEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#initInputValue(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initInputValue(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			inputValueEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			inputValueEditUtil = new EMFListEditUtil(current, feature);
		this.inputValue.setInput(inputValueEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#updateInputValue(EObject newValue)
	 */
	public void updateInputValue(EObject newValue) {
		if(inputValueEditUtil != null){
			inputValueEditUtil.reinit(newValue);
			inputValue.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addFilterInputValue(ViewerFilter filter)
	 */
	public void addFilterToInputValue(ViewerFilter filter) {
		inputValueFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addBusinessFilterInputValue(ViewerFilter filter)
	 */
	public void addBusinessFilterToInputValue(ViewerFilter filter) {
		inputValueBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#isContainedInInputValueTable(EObject element)
	 */
	public boolean isContainedInInputValueTable(EObject element) {
		return inputValueEditUtil.contains(element);
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getOutputValueToAdd()
	 */
	public List getOutputValueToAdd() {
		return outputValueEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getOutputValueToRemove()
	 */
	public List getOutputValueToRemove() {
		return outputValueEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getOutputValueToEdit()
	 */
	public Map getOutputValueToEdit() {
		return outputValueEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getOutputValueToMove()
	 */
	public List getOutputValueToMove() {
		return outputValueEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#getOutputValueTable()
	 */
	public List getOutputValueTable() {
		return outputValueEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#initOutputValue(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initOutputValue(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			outputValueEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			outputValueEditUtil = new EMFListEditUtil(current, feature);
		this.outputValue.setInput(outputValueEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#updateOutputValue(EObject newValue)
	 */
	public void updateOutputValue(EObject newValue) {
		if(outputValueEditUtil != null){
			outputValueEditUtil.reinit(newValue);
			outputValue.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addFilterOutputValue(ViewerFilter filter)
	 */
	public void addFilterToOutputValue(ViewerFilter filter) {
		outputValueFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#addBusinessFilterOutputValue(ViewerFilter filter)
	 */
	public void addBusinessFilterToOutputValue(ViewerFilter filter) {
		outputValueBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OpaqueActionPropertiesEditionPart#isContainedInOutputValueTable(EObject element)
	 */
	public boolean isContainedInOutputValueTable(EObject element) {
		return outputValueEditUtil.contains(element);
	}











	
	// Start of user code additional methods

	// End of user code

}	