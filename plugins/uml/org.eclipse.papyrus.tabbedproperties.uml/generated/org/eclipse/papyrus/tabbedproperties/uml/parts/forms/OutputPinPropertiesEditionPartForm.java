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
import org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart;
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
import org.eclipse.uml2.uml.Comment;

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
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.InterruptibleActivityRegion;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.State;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.emf.eef.runtime.impl.filters.EObjectFilter;

import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;

// End of user code

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class OutputPinPropertiesEditionPartForm extends CompositePropertiesEditionPart implements
		IFormPropertiesEditionPart, OutputPinPropertiesEditionPart {

	protected EMFListEditUtil ownedCommentEditUtil;

	protected ReferencesTable<?> ownedComment;

	protected List<ViewerFilter> ownedCommentBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> ownedCommentFilters = new ArrayList<ViewerFilter>();

	protected Text name;

	protected EMFComboViewer visibility;

	private EMFListEditUtil clientDependencyEditUtil;

	protected ReferencesTable<?> clientDependency;

	protected List<ViewerFilter> clientDependencyBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> clientDependencyFilters = new ArrayList<ViewerFilter>();

	protected Button isLeaf;

	private EMFListEditUtil outgoingEditUtil;

	protected ReferencesTable<?> outgoing;

	protected List<ViewerFilter> outgoingBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> outgoingFilters = new ArrayList<ViewerFilter>();

	private EMFListEditUtil incomingEditUtil;

	protected ReferencesTable<?> incoming;

	protected List<ViewerFilter> incomingBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> incomingFilters = new ArrayList<ViewerFilter>();

	private EMFListEditUtil inPartitionEditUtil;

	protected ReferencesTable<?> inPartition;

	protected List<ViewerFilter> inPartitionBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> inPartitionFilters = new ArrayList<ViewerFilter>();

	private EMFListEditUtil inInterruptibleRegionEditUtil;

	protected ReferencesTable<?> inInterruptibleRegion;

	protected List<ViewerFilter> inInterruptibleRegionBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> inInterruptibleRegionFilters = new ArrayList<ViewerFilter>();

	private EMFListEditUtil redefinedNodeEditUtil;

	protected ReferencesTable<?> redefinedNode;

	protected List<ViewerFilter> redefinedNodeBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> redefinedNodeFilters = new ArrayList<ViewerFilter>();

	protected EMFComboViewer ordering;

	protected Button isControlType;

	private EMFListEditUtil inStateEditUtil;

	protected ReferencesTable<?> inState;

	protected List<ViewerFilter> inStateBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> inStateFilters = new ArrayList<ViewerFilter>();

	protected Button isOrdered;

	protected Button isUnique;

	protected Button isControl;

	public OutputPinPropertiesEditionPartForm(IPropertiesEditionComponent editionComponent) {
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
		createPropertiesGroup(widgetFactory, view);
		// Start of user code for additional ui definition

		// End of user code

	}

	protected void createPropertiesGroup(FormToolkit widgetFactory, final Composite view) {
		Section propertiesSection = widgetFactory.createSection(view, Section.TITLE_BAR | Section.TWISTIE
				| Section.EXPANDED);
		propertiesSection.setText(UMLMessages.OutputPinPropertiesEditionPart_PropertiesGroupLabel);
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
		createOrderingEMFComboViewer(widgetFactory, propertiesGroup);
		createIsControlTypeCheckbox(widgetFactory, propertiesGroup);
		createInStateReferencesTable(widgetFactory, propertiesGroup);
		createIsOrderedCheckbox(widgetFactory, propertiesGroup);
		createIsUniqueCheckbox(widgetFactory, propertiesGroup);
		createIsControlCheckbox(widgetFactory, propertiesGroup);
		propertiesSection.setClient(propertiesGroup);
	}

	/**
	 * @param container
	 */
	protected void createOwnedCommentTableComposition(FormToolkit widgetFactory, Composite parent) {
		this.ownedComment = new ReferencesTable<Comment>(UMLMessages.OutputPinPropertiesEditionPart_OwnedCommentLabel,
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
				UMLViewsRepository.OutputPin.ownedComment, UMLViewsRepository.FORM_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.ownedComment,
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
						OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.ownedComment,
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
				OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.ownedComment,
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
						OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.ownedComment,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createNameText(FormToolkit widgetFactory, Composite parent) {
		FormUtils.createPartLabel(widgetFactory, parent, UMLMessages.OutputPinPropertiesEditionPart_NameLabel,
				propertiesEditionComponent.isRequired(UMLViewsRepository.OutputPin.name, UMLViewsRepository.FORM_KIND));
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
							OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.name,
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
							OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.name,
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
								OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.name,
								PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
				}
			}

		});
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.OutputPin.name, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$

	}

	protected void createVisibilityEMFComboViewer(FormToolkit widgetFactory, Composite parent) {
		FormUtils.createPartLabel(widgetFactory, parent, UMLMessages.OutputPinPropertiesEditionPart_VisibilityLabel,
				propertiesEditionComponent.isRequired(UMLViewsRepository.OutputPin.visibility,
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
							OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.visibility,
							PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, getVisibility()));
			}

		});
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.OutputPin.visibility, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}

	protected void createClientDependencyReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.clientDependency = new ReferencesTable<Dependency>(
				UMLMessages.OutputPinPropertiesEditionPart_ClientDependencyLabel,
				new ReferencesTableListener<Dependency>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<Dependency> dialog = new TabElementTreeSelectionDialog<Dependency>(
								resourceSet, clientDependencyFilters, clientDependencyBusinessFilters, "Dependency",
								UMLPackage.eINSTANCE.getDependency()) {

							@Override
							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!clientDependencyEditUtil.getVirtualList().contains(elem))
										clientDependencyEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											OutputPinPropertiesEditionPartForm.this,
											UMLViewsRepository.OutputPin.clientDependency,
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
				UMLViewsRepository.OutputPin.clientDependency, UMLViewsRepository.FORM_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.clientDependency,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
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
				OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.clientDependency,
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
						OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.clientDependency,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createIsLeafCheckbox(FormToolkit widgetFactory, Composite parent) {
		isLeaf = widgetFactory.createButton(parent, UMLMessages.OutputPinPropertiesEditionPart_IsLeafLabel, SWT.CHECK);
		isLeaf.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
							OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.isLeaf,
							PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(isLeaf
									.getSelection())));
			}

		});
		GridData isLeafData = new GridData(GridData.FILL_HORIZONTAL);
		isLeafData.horizontalSpan = 2;
		isLeaf.setLayoutData(isLeafData);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.OutputPin.isLeaf, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}

	protected void createOutgoingReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.outgoing = new ReferencesTable<ActivityEdge>(UMLMessages.OutputPinPropertiesEditionPart_OutgoingLabel,
				new ReferencesTableListener<ActivityEdge>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<ActivityEdge> dialog = new TabElementTreeSelectionDialog<ActivityEdge>(
								resourceSet, outgoingFilters, outgoingBusinessFilters, "ActivityEdge",
								UMLPackage.eINSTANCE.getActivityEdge()) {

							@Override
							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!outgoingEditUtil.getVirtualList().contains(elem))
										outgoingEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											OutputPinPropertiesEditionPartForm.this,
											UMLViewsRepository.OutputPin.outgoing, PropertiesEditionEvent.COMMIT,
											PropertiesEditionEvent.ADD, null, elem));
								}
								outgoing.refresh();
							}
						};
						dialog.open();
					}

					public void handleEdit(ActivityEdge element) {
						editOutgoing(element);
					}

					public void handleMove(ActivityEdge element, int oldIndex, int newIndex) {
						moveOutgoing(element, oldIndex, newIndex);
					}

					public void handleRemove(ActivityEdge element) {
						removeFromOutgoing(element);
					}

					public void navigateTo(ActivityEdge element) {
					}
				});
		this.outgoing.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.OutputPin.outgoing,
				UMLViewsRepository.FORM_KIND));
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
				OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.outgoing,
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
						OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.outgoing,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createIncomingReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.incoming = new ReferencesTable<ActivityEdge>(UMLMessages.OutputPinPropertiesEditionPart_IncomingLabel,
				new ReferencesTableListener<ActivityEdge>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<ActivityEdge> dialog = new TabElementTreeSelectionDialog<ActivityEdge>(
								resourceSet, incomingFilters, incomingBusinessFilters, "ActivityEdge",
								UMLPackage.eINSTANCE.getActivityEdge()) {

							@Override
							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!incomingEditUtil.getVirtualList().contains(elem))
										incomingEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											OutputPinPropertiesEditionPartForm.this,
											UMLViewsRepository.OutputPin.incoming, PropertiesEditionEvent.COMMIT,
											PropertiesEditionEvent.ADD, null, elem));
								}
								incoming.refresh();
							}
						};
						dialog.open();
					}

					public void handleEdit(ActivityEdge element) {
						editIncoming(element);
					}

					public void handleMove(ActivityEdge element, int oldIndex, int newIndex) {
						moveIncoming(element, oldIndex, newIndex);
					}

					public void handleRemove(ActivityEdge element) {
						removeFromIncoming(element);
					}

					public void navigateTo(ActivityEdge element) {
					}
				});
		this.incoming.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.OutputPin.incoming,
				UMLViewsRepository.FORM_KIND));
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
				OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.incoming,
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
						OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.incoming,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createInPartitionReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.inPartition = new ReferencesTable<ActivityPartition>(
				UMLMessages.OutputPinPropertiesEditionPart_InPartitionLabel,
				new ReferencesTableListener<ActivityPartition>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<ActivityPartition> dialog = new TabElementTreeSelectionDialog<ActivityPartition>(
								resourceSet, inPartitionFilters, inPartitionBusinessFilters, "ActivityPartition",
								UMLPackage.eINSTANCE.getActivityPartition()) {

							@Override
							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!inPartitionEditUtil.getVirtualList().contains(elem))
										inPartitionEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											OutputPinPropertiesEditionPartForm.this,
											UMLViewsRepository.OutputPin.inPartition, PropertiesEditionEvent.COMMIT,
											PropertiesEditionEvent.ADD, null, elem));
								}
								inPartition.refresh();
							}
						};
						dialog.open();
					}

					public void handleEdit(ActivityPartition element) {
						editInPartition(element);
					}

					public void handleMove(ActivityPartition element, int oldIndex, int newIndex) {
						moveInPartition(element, oldIndex, newIndex);
					}

					public void handleRemove(ActivityPartition element) {
						removeFromInPartition(element);
					}

					public void navigateTo(ActivityPartition element) {
					}
				});
		this.inPartition.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.OutputPin.inPartition, UMLViewsRepository.FORM_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.inPartition,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
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
				OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.inPartition,
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
						OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.inPartition,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createInInterruptibleRegionReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.inInterruptibleRegion = new ReferencesTable<InterruptibleActivityRegion>(
				UMLMessages.OutputPinPropertiesEditionPart_InInterruptibleRegionLabel,
				new ReferencesTableListener<InterruptibleActivityRegion>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<InterruptibleActivityRegion> dialog = new TabElementTreeSelectionDialog<InterruptibleActivityRegion>(
								resourceSet, inInterruptibleRegionFilters, inInterruptibleRegionBusinessFilters,
								"InterruptibleActivityRegion", UMLPackage.eINSTANCE.getInterruptibleActivityRegion()) {

							@Override
							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!inInterruptibleRegionEditUtil.getVirtualList().contains(elem))
										inInterruptibleRegionEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											OutputPinPropertiesEditionPartForm.this,
											UMLViewsRepository.OutputPin.inInterruptibleRegion,
											PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
								}
								inInterruptibleRegion.refresh();
							}
						};
						dialog.open();
					}

					public void handleEdit(InterruptibleActivityRegion element) {
						editInInterruptibleRegion(element);
					}

					public void handleMove(InterruptibleActivityRegion element, int oldIndex, int newIndex) {
						moveInInterruptibleRegion(element, oldIndex, newIndex);
					}

					public void handleRemove(InterruptibleActivityRegion element) {
						removeFromInInterruptibleRegion(element);
					}

					public void navigateTo(InterruptibleActivityRegion element) {
					}
				});
		this.inInterruptibleRegion.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.OutputPin.inInterruptibleRegion, UMLViewsRepository.FORM_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.inInterruptibleRegion,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
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
				OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.inInterruptibleRegion,
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
						OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.inInterruptibleRegion,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createRedefinedNodeReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.redefinedNode = new ReferencesTable<ActivityNode>(
				UMLMessages.OutputPinPropertiesEditionPart_RedefinedNodeLabel,
				new ReferencesTableListener<ActivityNode>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<ActivityNode> dialog = new TabElementTreeSelectionDialog<ActivityNode>(
								resourceSet, redefinedNodeFilters, redefinedNodeBusinessFilters, "ActivityNode",
								UMLPackage.eINSTANCE.getActivityNode()) {

							@Override
							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!redefinedNodeEditUtil.getVirtualList().contains(elem))
										redefinedNodeEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											OutputPinPropertiesEditionPartForm.this,
											UMLViewsRepository.OutputPin.redefinedNode, PropertiesEditionEvent.COMMIT,
											PropertiesEditionEvent.ADD, null, elem));
								}
								redefinedNode.refresh();
							}
						};
						dialog.open();
					}

					public void handleEdit(ActivityNode element) {
						editRedefinedNode(element);
					}

					public void handleMove(ActivityNode element, int oldIndex, int newIndex) {
						moveRedefinedNode(element, oldIndex, newIndex);
					}

					public void handleRemove(ActivityNode element) {
						removeFromRedefinedNode(element);
					}

					public void navigateTo(ActivityNode element) {
					}
				});
		this.redefinedNode.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.OutputPin.redefinedNode, UMLViewsRepository.FORM_KIND));
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
				OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.redefinedNode,
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
						OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.redefinedNode,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createOrderingEMFComboViewer(FormToolkit widgetFactory, Composite parent) {
		FormUtils.createPartLabel(widgetFactory, parent, UMLMessages.OutputPinPropertiesEditionPart_OrderingLabel,
				propertiesEditionComponent.isRequired(UMLViewsRepository.OutputPin.ordering,
						UMLViewsRepository.FORM_KIND));
		ordering = new EMFComboViewer(parent);
		ordering.setContentProvider(new ArrayContentProvider());
		ordering.setLabelProvider(new AdapterFactoryLabelProvider(new EcoreAdapterFactory()));
		GridData orderingData = new GridData(GridData.FILL_HORIZONTAL);
		ordering.getCombo().setLayoutData(orderingData);
		ordering.addSelectionChangedListener(new ISelectionChangedListener() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
			 */
			public void selectionChanged(SelectionChangedEvent event) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
							OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.ordering,
							PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, getOrdering()));
			}

		});
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.OutputPin.ordering, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}

	protected void createIsControlTypeCheckbox(FormToolkit widgetFactory, Composite parent) {
		isControlType = widgetFactory.createButton(parent,
				UMLMessages.OutputPinPropertiesEditionPart_IsControlTypeLabel, SWT.CHECK);
		isControlType.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
							OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.isControlType,
							PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(isControlType
									.getSelection())));
			}

		});
		GridData isControlTypeData = new GridData(GridData.FILL_HORIZONTAL);
		isControlTypeData.horizontalSpan = 2;
		isControlType.setLayoutData(isControlTypeData);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.OutputPin.isControlType, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}

	protected void createInStateReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.inState = new ReferencesTable<State>(UMLMessages.OutputPinPropertiesEditionPart_InStateLabel,
				new ReferencesTableListener<State>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<State> dialog = new TabElementTreeSelectionDialog<State>(
								resourceSet, inStateFilters, inStateBusinessFilters, "State", UMLPackage.eINSTANCE
										.getState()) {

							@Override
							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!inStateEditUtil.getVirtualList().contains(elem))
										inStateEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											OutputPinPropertiesEditionPartForm.this,
											UMLViewsRepository.OutputPin.inState, PropertiesEditionEvent.COMMIT,
											PropertiesEditionEvent.ADD, null, elem));
								}
								inState.refresh();
							}
						};
						dialog.open();
					}

					public void handleEdit(State element) {
						editInState(element);
					}

					public void handleMove(State element, int oldIndex, int newIndex) {
						moveInState(element, oldIndex, newIndex);
					}

					public void handleRemove(State element) {
						removeFromInState(element);
					}

					public void navigateTo(State element) {
					}
				});
		this.inState.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.OutputPin.inState,
				UMLViewsRepository.FORM_KIND));
		this.inState.createControls(parent, widgetFactory);
		GridData inStateData = new GridData(GridData.FILL_HORIZONTAL);
		inStateData.horizontalSpan = 3;
		this.inState.setLayoutData(inStateData);
		this.inState.disableMove();
	}

	/**
	 * 
	 */
	protected void moveInState(State element, int oldIndex, int newIndex) {
		EObject editedElement = inStateEditUtil.foundCorrespondingEObject(element);
		inStateEditUtil.moveElement(element, oldIndex, newIndex);
		inState.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.inState,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	protected void removeFromInState(State element) {

		// Start of user code for the removeFromInState() method body

		EObject editedElement = inStateEditUtil.foundCorrespondingEObject(element);
		inStateEditUtil.removeElement(element);
		inState.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.inState,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	protected void editInState(State element) {

		// Start of user code editInState() method body

		EObject editedElement = inStateEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				inStateEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				inState.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.inState,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createIsOrderedCheckbox(FormToolkit widgetFactory, Composite parent) {
		isOrdered = widgetFactory.createButton(parent, UMLMessages.OutputPinPropertiesEditionPart_IsOrderedLabel,
				SWT.CHECK);
		isOrdered.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
							OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.isOrdered,
							PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(isOrdered
									.getSelection())));
			}

		});
		GridData isOrderedData = new GridData(GridData.FILL_HORIZONTAL);
		isOrderedData.horizontalSpan = 2;
		isOrdered.setLayoutData(isOrderedData);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.OutputPin.isOrdered, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}

	protected void createIsUniqueCheckbox(FormToolkit widgetFactory, Composite parent) {
		isUnique = widgetFactory.createButton(parent, UMLMessages.OutputPinPropertiesEditionPart_IsUniqueLabel,
				SWT.CHECK);
		isUnique.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
							OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.isUnique,
							PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(isUnique
									.getSelection())));
			}

		});
		GridData isUniqueData = new GridData(GridData.FILL_HORIZONTAL);
		isUniqueData.horizontalSpan = 2;
		isUnique.setLayoutData(isUniqueData);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.OutputPin.isUnique, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}

	protected void createIsControlCheckbox(FormToolkit widgetFactory, Composite parent) {
		isControl = widgetFactory.createButton(parent, UMLMessages.OutputPinPropertiesEditionPart_IsControlLabel,
				SWT.CHECK);
		isControl.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
							OutputPinPropertiesEditionPartForm.this, UMLViewsRepository.OutputPin.isControl,
							PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(isControl
									.getSelection())));
			}

		});
		GridData isControlData = new GridData(GridData.FILL_HORIZONTAL);
		isControlData.horizontalSpan = 2;
		isControl.setLayoutData(isControlData);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.OutputPin.isControl, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}

	public void firePropertiesChanged(PropertiesEditionEvent event) {
		// Start of user code for tab synchronization

		// End of user code

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getOwnedCommentToAdd()
	 */
	public List getOwnedCommentToAdd() {
		return ownedCommentEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getOwnedCommentToRemove()
	 */
	public List getOwnedCommentToRemove() {
		return ownedCommentEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getOwnedCommentToEdit()
	 */
	public Map getOwnedCommentToEdit() {
		return ownedCommentEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getOwnedCommentToMove()
	 */
	public List getOwnedCommentToMove() {
		return ownedCommentEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getOwnedCommentTable()
	 */
	public List getOwnedCommentTable() {
		return ownedCommentEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#initOwnedComment(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#updateOwnedComment(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#addFilterOwnedComment(ViewerFilter
	 *      filter)
	 */
	public void addFilterToOwnedComment(ViewerFilter filter) {
		ownedCommentFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#addBusinessFilterOwnedComment(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToOwnedComment(ViewerFilter filter) {
		ownedCommentBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getName()
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#setName(String
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getVisibility()
	 */
	public Enumerator getVisibility() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) visibility.getSelection()).getFirstElement();
		return selection.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#initVisibility(EEnum
	 *      eenum, Enumerator current)
	 */
	public void initVisibility(EEnum eenum, Enumerator current) {
		visibility.setInput(eenum.getELiterals());
		visibility.modelUpdating(new StructuredSelection(current));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#setVisibility(Enumerator
	 *      newValue)
	 */
	public void setVisibility(Enumerator newValue) {
		visibility.modelUpdating(new StructuredSelection(newValue));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getClientDependencyToAdd()
	 */
	public List getClientDependencyToAdd() {
		return clientDependencyEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getClientDependencyToRemove()
	 */
	public List getClientDependencyToRemove() {
		return clientDependencyEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getClientDependencyTable()
	 */
	public List getClientDependencyTable() {
		return clientDependencyEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#initClientDependency(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#updateClientDependency(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#addFilterClientDependency(ViewerFilter
	 *      filter)
	 */
	public void addFilterToClientDependency(ViewerFilter filter) {
		clientDependencyFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#addBusinessFilterClientDependency(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToClientDependency(ViewerFilter filter) {
		clientDependencyBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getIsLeaf()
	 */
	public Boolean getIsLeaf() {
		return Boolean.valueOf(isLeaf.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#setIsLeaf(Boolean
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getOutgoingToAdd()
	 */
	public List getOutgoingToAdd() {
		return outgoingEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getOutgoingToRemove()
	 */
	public List getOutgoingToRemove() {
		return outgoingEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getOutgoingTable()
	 */
	public List getOutgoingTable() {
		return outgoingEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#initOutgoing(EObject
	 *      current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#updateOutgoing(EObject
	 *      newValue)
	 */
	public void updateOutgoing(EObject newValue) {
		if (outgoingEditUtil != null) {
			outgoingEditUtil.reinit(newValue);
			outgoing.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#addFilterOutgoing(ViewerFilter
	 *      filter)
	 */
	public void addFilterToOutgoing(ViewerFilter filter) {
		outgoingFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#addBusinessFilterOutgoing(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToOutgoing(ViewerFilter filter) {
		outgoingBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getIncomingToAdd()
	 */
	public List getIncomingToAdd() {
		return incomingEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getIncomingToRemove()
	 */
	public List getIncomingToRemove() {
		return incomingEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getIncomingTable()
	 */
	public List getIncomingTable() {
		return incomingEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#initIncoming(EObject
	 *      current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#updateIncoming(EObject
	 *      newValue)
	 */
	public void updateIncoming(EObject newValue) {
		if (incomingEditUtil != null) {
			incomingEditUtil.reinit(newValue);
			incoming.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#addFilterIncoming(ViewerFilter
	 *      filter)
	 */
	public void addFilterToIncoming(ViewerFilter filter) {
		incomingFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#addBusinessFilterIncoming(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToIncoming(ViewerFilter filter) {
		incomingBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getInPartitionToAdd()
	 */
	public List getInPartitionToAdd() {
		return inPartitionEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getInPartitionToRemove()
	 */
	public List getInPartitionToRemove() {
		return inPartitionEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getInPartitionTable()
	 */
	public List getInPartitionTable() {
		return inPartitionEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#initInPartition(EObject
	 *      current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#updateInPartition(EObject
	 *      newValue)
	 */
	public void updateInPartition(EObject newValue) {
		if (inPartitionEditUtil != null) {
			inPartitionEditUtil.reinit(newValue);
			inPartition.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#addFilterInPartition(ViewerFilter
	 *      filter)
	 */
	public void addFilterToInPartition(ViewerFilter filter) {
		inPartitionFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#addBusinessFilterInPartition(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToInPartition(ViewerFilter filter) {
		inPartitionBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getInInterruptibleRegionToAdd()
	 */
	public List getInInterruptibleRegionToAdd() {
		return inInterruptibleRegionEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getInInterruptibleRegionToRemove()
	 */
	public List getInInterruptibleRegionToRemove() {
		return inInterruptibleRegionEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getInInterruptibleRegionTable()
	 */
	public List getInInterruptibleRegionTable() {
		return inInterruptibleRegionEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#initInInterruptibleRegion(EObject
	 *      current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#updateInInterruptibleRegion(EObject
	 *      newValue)
	 */
	public void updateInInterruptibleRegion(EObject newValue) {
		if (inInterruptibleRegionEditUtil != null) {
			inInterruptibleRegionEditUtil.reinit(newValue);
			inInterruptibleRegion.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#addFilterInInterruptibleRegion(ViewerFilter
	 *      filter)
	 */
	public void addFilterToInInterruptibleRegion(ViewerFilter filter) {
		inInterruptibleRegionFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#addBusinessFilterInInterruptibleRegion(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToInInterruptibleRegion(ViewerFilter filter) {
		inInterruptibleRegionBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getRedefinedNodeToAdd()
	 */
	public List getRedefinedNodeToAdd() {
		return redefinedNodeEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getRedefinedNodeToRemove()
	 */
	public List getRedefinedNodeToRemove() {
		return redefinedNodeEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getRedefinedNodeTable()
	 */
	public List getRedefinedNodeTable() {
		return redefinedNodeEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#initRedefinedNode(EObject
	 *      current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#updateRedefinedNode(EObject
	 *      newValue)
	 */
	public void updateRedefinedNode(EObject newValue) {
		if (redefinedNodeEditUtil != null) {
			redefinedNodeEditUtil.reinit(newValue);
			redefinedNode.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#addFilterRedefinedNode(ViewerFilter
	 *      filter)
	 */
	public void addFilterToRedefinedNode(ViewerFilter filter) {
		redefinedNodeFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#addBusinessFilterRedefinedNode(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToRedefinedNode(ViewerFilter filter) {
		redefinedNodeBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getOrdering()
	 */
	public Enumerator getOrdering() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) ordering.getSelection()).getFirstElement();
		return selection.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#initOrdering(EEnum
	 *      eenum, Enumerator current)
	 */
	public void initOrdering(EEnum eenum, Enumerator current) {
		ordering.setInput(eenum.getELiterals());
		ordering.modelUpdating(new StructuredSelection(current));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#setOrdering(Enumerator
	 *      newValue)
	 */
	public void setOrdering(Enumerator newValue) {
		ordering.modelUpdating(new StructuredSelection(newValue));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getIsControlType()
	 */
	public Boolean getIsControlType() {
		return Boolean.valueOf(isControlType.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#setIsControlType(Boolean
	 *      newValue)
	 */
	public void setIsControlType(Boolean newValue) {
		if (newValue != null) {
			isControlType.setSelection(newValue.booleanValue());
		} else {
			isControlType.setSelection(false);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getInStateToAdd()
	 */
	public List getInStateToAdd() {
		return inStateEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getInStateToRemove()
	 */
	public List getInStateToRemove() {
		return inStateEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getInStateTable()
	 */
	public List getInStateTable() {
		return inStateEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#initInState(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initInState(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			inStateEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			inStateEditUtil = new EMFListEditUtil(current, feature);
		this.inState.setInput(inStateEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#updateInState(EObject
	 *      newValue)
	 */
	public void updateInState(EObject newValue) {
		if (inStateEditUtil != null) {
			inStateEditUtil.reinit(newValue);
			inState.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#addFilterInState(ViewerFilter
	 *      filter)
	 */
	public void addFilterToInState(ViewerFilter filter) {
		inStateFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#addBusinessFilterInState(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToInState(ViewerFilter filter) {
		inStateBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getIsOrdered()
	 */
	public Boolean getIsOrdered() {
		return Boolean.valueOf(isOrdered.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#setIsOrdered(Boolean
	 *      newValue)
	 */
	public void setIsOrdered(Boolean newValue) {
		if (newValue != null) {
			isOrdered.setSelection(newValue.booleanValue());
		} else {
			isOrdered.setSelection(false);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getIsUnique()
	 */
	public Boolean getIsUnique() {
		return Boolean.valueOf(isUnique.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#setIsUnique(Boolean
	 *      newValue)
	 */
	public void setIsUnique(Boolean newValue) {
		if (newValue != null) {
			isUnique.setSelection(newValue.booleanValue());
		} else {
			isUnique.setSelection(false);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#getIsControl()
	 */
	public Boolean getIsControl() {
		return Boolean.valueOf(isControl.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.OutputPinPropertiesEditionPart#setIsControl(Boolean
	 *      newValue)
	 */
	public void setIsControl(Boolean newValue) {
		if (newValue != null) {
			isControl.setSelection(newValue.booleanValue());
		} else {
			isControl.setSelection(false);
		}
	}

	// Start of user code additional methods

	// End of user code

}
