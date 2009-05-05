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
import org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart;
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
import org.eclipse.uml2.uml.GeneralizationSet;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.UseCase;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.emf.eef.runtime.impl.filters.EObjectFilter;
import java.util.Map;
import org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.TemplateBinding;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Substitution;
import org.eclipse.uml2.uml.CollaborationUse;
import org.eclipse.uml2.uml.UseCase;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Reception;
import org.eclipse.uml2.uml.Deployment;
import org.eclipse.uml2.uml.Node;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.emf.eef.runtime.ui.widgets.EMFComboViewer;

import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;

// End of user code
/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class DevicePropertiesEditionPartImpl extends CompositePropertiesEditionPart implements ISWTPropertiesEditionPart, DevicePropertiesEditionPart {

	private EMFListEditUtil ownedCommentEditUtil;

	private ReferencesTable<?> ownedComment;

	private Text name;

	private EMFComboViewer visibility;

	private EMFListEditUtil clientDependencyEditUtil;

	private ReferencesTable<?> clientDependency;

	private EMFListEditUtil elementImportEditUtil;

	private ReferencesTable<?> elementImport;

	private EMFListEditUtil packageImportEditUtil;

	private ReferencesTable<?> packageImport;

	private EMFListEditUtil ownedRuleEditUtil;

	private ReferencesTable<?> ownedRule;

	private Button isLeaf;

	private EMFListEditUtil templateBindingEditUtil;

	private ReferencesTable<?> templateBinding;

	private Button isAbstract;

	private EMFListEditUtil generalizationEditUtil;

	private ReferencesTable<?> generalization;

	private EMFListEditUtil powertypeExtentEditUtil;

	private ReferencesTable<?> powertypeExtent;

	private EMFListEditUtil redefinedClassifierEditUtil;

	private ReferencesTable<?> redefinedClassifier;

	private EMFListEditUtil substitutionEditUtil;

	private ReferencesTable<?> substitution;

	private EMFListEditUtil collaborationUseEditUtil;

	private ReferencesTable<?> collaborationUse;

	private EMFListEditUtil ownedUseCaseEditUtil;

	private ReferencesTable<?> ownedUseCase;

	private EMFListEditUtil useCaseEditUtil;

	private ReferencesTable<?> useCase;

	private EMFListEditUtil ownedAttributeEditUtil;

	private ReferencesTable<?> ownedAttribute;

	private EMFListEditUtil ownedConnectorEditUtil;

	private ReferencesTable<?> ownedConnector;

	private EMFListEditUtil ownedBehaviorEditUtil;

	private ReferencesTable<?> ownedBehavior;

	private EMFListEditUtil interfaceRealizationEditUtil;

	private ReferencesTable<?> interfaceRealization;

	private EMFListEditUtil ownedTriggerEditUtil;

	private ReferencesTable<?> ownedTrigger;

	private EMFListEditUtil nestedClassifierEditUtil;

	private ReferencesTable<?> nestedClassifier;

	private EMFListEditUtil ownedOperationEditUtil;

	private ReferencesTable<?> ownedOperation;

	private Button isActive;

	private EMFListEditUtil ownedReceptionEditUtil;

	private ReferencesTable<?> ownedReception;

	private EMFListEditUtil deploymentEditUtil;

	private ReferencesTable<?> deployment;

	private EMFListEditUtil nestedNodeEditUtil;

	private ReferencesTable<?> nestedNode;

	public DevicePropertiesEditionPartImpl(IPropertiesEditionComponent editionComponent) {
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
		propertiesGroup.setText(UMLMessages.DevicePropertiesEditionPart_PropertiesGroupLabel);
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
		createElementImportTableComposition(propertiesGroup);
		createPackageImportTableComposition(propertiesGroup);
		createOwnedRuleTableComposition(propertiesGroup);
		createIsLeafCheckbox(propertiesGroup);
		createTemplateBindingTableComposition(propertiesGroup);
		createIsAbstractCheckbox(propertiesGroup);
		createGeneralizationTableComposition(propertiesGroup);
		createPowertypeExtentReferencesTable(propertiesGroup);
		createRedefinedClassifierReferencesTable(propertiesGroup);
		createSubstitutionTableComposition(propertiesGroup);
		createCollaborationUseTableComposition(propertiesGroup);
		createOwnedUseCaseTableComposition(propertiesGroup);
		createUseCaseReferencesTable(propertiesGroup);
		createOwnedAttributeTableComposition(propertiesGroup);
		createOwnedConnectorTableComposition(propertiesGroup);
		createOwnedBehaviorTableComposition(propertiesGroup);
		createInterfaceRealizationTableComposition(propertiesGroup);
		createOwnedTriggerTableComposition(propertiesGroup);
		createNestedClassifierTableComposition(propertiesGroup);
		createOwnedOperationTableComposition(propertiesGroup);
		createIsActiveCheckbox(propertiesGroup);
		createOwnedReceptionTableComposition(propertiesGroup);
		createDeploymentTableComposition(propertiesGroup);
		createNestedNodeTableComposition(propertiesGroup);
	}

	/**
	 * @param container
	 */
	protected void createOwnedCommentTableComposition(Composite parent) {
		this.ownedComment = new ReferencesTable<Comment>(UMLMessages.DevicePropertiesEditionPart_OwnedCommentLabel, new ReferencesTableListener<Comment>() {

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
		this.ownedComment.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.ownedComment, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedComment, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

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
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject, resourceSet));
			if (propertiesEditionObject != null) {
				ownedCommentEditUtil.addElement(propertiesEditionObject);
				ownedComment.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedComment,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedComment, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editOwnedComment(Comment element) {

		// Start of user code editOwnedComment() method body

		EObject editedElement = ownedCommentEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedCommentEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedComment.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedComment,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	protected void createNameText(Composite parent) {
		SWTUtils.createPartLabel(parent, UMLMessages.DevicePropertiesEditionPart_NameLabel, propertiesEditionComponent.isRequired(UMLViewsRepository.Device.name, UMLViewsRepository.SWT_KIND));
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.name, PropertiesEditionEvent.CHANGE,
							PropertiesEditionEvent.SET, null, name.getText()));
			}

		});

		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.name, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createVisibilityEEnumViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UMLMessages.DevicePropertiesEditionPart_VisibilityLabel, propertiesEditionComponent.isRequired(UMLViewsRepository.Device.visibility,
				UMLViewsRepository.SWT_KIND));
		visibility = new EMFComboViewer(parent);
		visibility.setContentProvider(new ArrayContentProvider());
		visibility.setLabelProvider(new AdapterFactoryLabelProvider(new EcoreAdapterFactory()));
		GridData visibilityData = new GridData(GridData.FILL_HORIZONTAL);
		visibility.getCombo().setLayoutData(visibilityData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.visibility, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createClientDependencyReferencesTable(Composite parent) {
		this.clientDependency = new ReferencesTable<Dependency>(UMLMessages.DevicePropertiesEditionPart_ClientDependencyLabel, new ReferencesTableListener<Dependency>() {

			public void handleAdd() {
				ViewerFilter clientDependencyFilter = new EObjectFilter(UMLPackage.eINSTANCE.getDependency());
				ViewerFilter viewerFilter = new ViewerFilter() {

					public boolean select(Viewer viewer, Object parentElement, Object element) {
						if (element instanceof EObject)
							return (!clientDependencyEditUtil.contains((EObject) element));
						return false;
					}

				};
				List filters = new ArrayList();
				filters.add(clientDependencyFilter);
				filters.add(viewerFilter);
				TabElementTreeSelectionDialog<Dependency> dialog = new TabElementTreeSelectionDialog<Dependency>(resourceSet, filters, "Dependency", UMLPackage.eINSTANCE.getDependency()) {

					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!clientDependencyEditUtil.getVirtualList().contains(elem))
								clientDependencyEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.clientDependency,
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
		this.clientDependency.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.clientDependency, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.clientDependency, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	private void removeFromClientDependency(Dependency element) {

		// Start of user code for the removeFromClientDependency() method body

		EObject editedElement = clientDependencyEditUtil.foundCorrespondingEObject(element);
		clientDependencyEditUtil.removeElement(element);
		clientDependency.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.clientDependency, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editClientDependency(Dependency element) {

		// Start of user code editClientDependency() method body

		EObject editedElement = clientDependencyEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				clientDependencyEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				clientDependency.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.clientDependency,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * @param container
	 */
	protected void createElementImportTableComposition(Composite parent) {
		this.elementImport = new ReferencesTable<ElementImport>(UMLMessages.DevicePropertiesEditionPart_ElementImportLabel, new ReferencesTableListener<ElementImport>() {

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
		this.elementImport.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.elementImport, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.elementImport, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	private void addToElementImport() {

		// Start of user code addToElementImport() method body

		ElementImport eObject = UMLFactory.eINSTANCE.createElementImport();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject, resourceSet));
			if (propertiesEditionObject != null) {
				elementImportEditUtil.addElement(propertiesEditionObject);
				elementImport.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.elementImport,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * 
	 */
	private void removeFromElementImport(ElementImport element) {

		// Start of user code for the removeFromElementImport() method body

		EObject editedElement = elementImportEditUtil.foundCorrespondingEObject(element);
		elementImportEditUtil.removeElement(element);
		elementImport.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.elementImport, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editElementImport(ElementImport element) {

		// Start of user code editElementImport() method body

		EObject editedElement = elementImportEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				elementImportEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				elementImport.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.elementImport,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * @param container
	 */
	protected void createPackageImportTableComposition(Composite parent) {
		this.packageImport = new ReferencesTable<PackageImport>(UMLMessages.DevicePropertiesEditionPart_PackageImportLabel, new ReferencesTableListener<PackageImport>() {

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
		this.packageImport.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.packageImport, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.packageImport, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	private void addToPackageImport() {

		// Start of user code addToPackageImport() method body

		PackageImport eObject = UMLFactory.eINSTANCE.createPackageImport();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject, resourceSet));
			if (propertiesEditionObject != null) {
				packageImportEditUtil.addElement(propertiesEditionObject);
				packageImport.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.packageImport,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * 
	 */
	private void removeFromPackageImport(PackageImport element) {

		// Start of user code for the removeFromPackageImport() method body

		EObject editedElement = packageImportEditUtil.foundCorrespondingEObject(element);
		packageImportEditUtil.removeElement(element);
		packageImport.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.packageImport, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editPackageImport(PackageImport element) {

		// Start of user code editPackageImport() method body

		EObject editedElement = packageImportEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				packageImportEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				packageImport.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.packageImport,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * @param container
	 */
	protected void createOwnedRuleTableComposition(Composite parent) {
		this.ownedRule = new ReferencesTable<Constraint>(UMLMessages.DevicePropertiesEditionPart_OwnedRuleLabel, new ReferencesTableListener<Constraint>() {

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
		this.ownedRule.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.ownedRule, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedRule, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	private void addToOwnedRule() {

		// Start of user code addToOwnedRule() method body

		Constraint eObject = UMLFactory.eINSTANCE.createConstraint();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject, resourceSet));
			if (propertiesEditionObject != null) {
				ownedRuleEditUtil.addElement(propertiesEditionObject);
				ownedRule.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedRule, PropertiesEditionEvent.CHANGE,
						PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * 
	 */
	private void removeFromOwnedRule(Constraint element) {

		// Start of user code for the removeFromOwnedRule() method body

		EObject editedElement = ownedRuleEditUtil.foundCorrespondingEObject(element);
		ownedRuleEditUtil.removeElement(element);
		ownedRule.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedRule, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editOwnedRule(Constraint element) {

		// Start of user code editOwnedRule() method body

		EObject editedElement = ownedRuleEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedRuleEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedRule.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedRule, PropertiesEditionEvent.CHANGE,
						PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	protected void createIsLeafCheckbox(Composite parent) {
		isLeaf = new Button(parent, SWT.CHECK);
		isLeaf.setText(UMLMessages.DevicePropertiesEditionPart_IsLeafLabel);
		GridData isLeafData = new GridData(GridData.FILL_HORIZONTAL);
		isLeafData.horizontalSpan = 2;
		isLeaf.setLayoutData(isLeafData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.isLeaf, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	/**
	 * @param container
	 */
	protected void createTemplateBindingTableComposition(Composite parent) {
		this.templateBinding = new ReferencesTable<TemplateBinding>(UMLMessages.DevicePropertiesEditionPart_TemplateBindingLabel, new ReferencesTableListener<TemplateBinding>() {

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
		this.templateBinding.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.templateBinding, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.templateBinding, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	private void addToTemplateBinding() {

		// Start of user code addToTemplateBinding() method body

		TemplateBinding eObject = UMLFactory.eINSTANCE.createTemplateBinding();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject, resourceSet));
			if (propertiesEditionObject != null) {
				templateBindingEditUtil.addElement(propertiesEditionObject);
				templateBinding.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.templateBinding,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * 
	 */
	private void removeFromTemplateBinding(TemplateBinding element) {

		// Start of user code for the removeFromTemplateBinding() method body

		EObject editedElement = templateBindingEditUtil.foundCorrespondingEObject(element);
		templateBindingEditUtil.removeElement(element);
		templateBinding.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.templateBinding, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editTemplateBinding(TemplateBinding element) {

		// Start of user code editTemplateBinding() method body

		EObject editedElement = templateBindingEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				templateBindingEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				templateBinding.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.templateBinding,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	protected void createIsAbstractCheckbox(Composite parent) {
		isAbstract = new Button(parent, SWT.CHECK);
		isAbstract.setText(UMLMessages.DevicePropertiesEditionPart_IsAbstractLabel);
		GridData isAbstractData = new GridData(GridData.FILL_HORIZONTAL);
		isAbstractData.horizontalSpan = 2;
		isAbstract.setLayoutData(isAbstractData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.isAbstract, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	/**
	 * @param container
	 */
	protected void createGeneralizationTableComposition(Composite parent) {
		this.generalization = new ReferencesTable<Generalization>(UMLMessages.DevicePropertiesEditionPart_GeneralizationLabel, new ReferencesTableListener<Generalization>() {

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
		this.generalization.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.generalization, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.generalization, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	private void addToGeneralization() {

		// Start of user code addToGeneralization() method body

		Generalization eObject = UMLFactory.eINSTANCE.createGeneralization();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject, resourceSet));
			if (propertiesEditionObject != null) {
				generalizationEditUtil.addElement(propertiesEditionObject);
				generalization.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.generalization,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * 
	 */
	private void removeFromGeneralization(Generalization element) {

		// Start of user code for the removeFromGeneralization() method body

		EObject editedElement = generalizationEditUtil.foundCorrespondingEObject(element);
		generalizationEditUtil.removeElement(element);
		generalization.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.generalization, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editGeneralization(Generalization element) {

		// Start of user code editGeneralization() method body

		EObject editedElement = generalizationEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				generalizationEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				generalization.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.generalization,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	protected void createPowertypeExtentReferencesTable(Composite parent) {
		this.powertypeExtent = new ReferencesTable<GeneralizationSet>(UMLMessages.DevicePropertiesEditionPart_PowertypeExtentLabel, new ReferencesTableListener<GeneralizationSet>() {

			public void handleAdd() {
				ViewerFilter powertypeExtentFilter = new EObjectFilter(UMLPackage.eINSTANCE.getGeneralizationSet());
				ViewerFilter viewerFilter = new ViewerFilter() {

					public boolean select(Viewer viewer, Object parentElement, Object element) {
						if (element instanceof EObject)
							return (!powertypeExtentEditUtil.contains((EObject) element));
						return false;
					}

				};
				List filters = new ArrayList();
				filters.add(powertypeExtentFilter);
				filters.add(viewerFilter);
				TabElementTreeSelectionDialog<GeneralizationSet> dialog = new TabElementTreeSelectionDialog<GeneralizationSet>(resourceSet, filters, "GeneralizationSet", UMLPackage.eINSTANCE
						.getGeneralizationSet()) {

					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!powertypeExtentEditUtil.getVirtualList().contains(elem))
								powertypeExtentEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.powertypeExtent,
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
		this.powertypeExtent.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.powertypeExtent, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.powertypeExtent, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	private void removeFromPowertypeExtent(GeneralizationSet element) {

		// Start of user code for the removeFromPowertypeExtent() method body

		EObject editedElement = powertypeExtentEditUtil.foundCorrespondingEObject(element);
		powertypeExtentEditUtil.removeElement(element);
		powertypeExtent.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.powertypeExtent, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editPowertypeExtent(GeneralizationSet element) {

		// Start of user code editPowertypeExtent() method body

		EObject editedElement = powertypeExtentEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				powertypeExtentEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				powertypeExtent.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.powertypeExtent,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	protected void createRedefinedClassifierReferencesTable(Composite parent) {
		this.redefinedClassifier = new ReferencesTable<Classifier>(UMLMessages.DevicePropertiesEditionPart_RedefinedClassifierLabel, new ReferencesTableListener<Classifier>() {

			public void handleAdd() {
				ViewerFilter redefinedClassifierFilter = new EObjectFilter(UMLPackage.eINSTANCE.getClassifier());
				ViewerFilter viewerFilter = new ViewerFilter() {

					public boolean select(Viewer viewer, Object parentElement, Object element) {
						if (element instanceof EObject)
							return (!redefinedClassifierEditUtil.contains((EObject) element));
						return false;
					}

				};
				List filters = new ArrayList();
				filters.add(redefinedClassifierFilter);
				filters.add(viewerFilter);
				TabElementTreeSelectionDialog<Classifier> dialog = new TabElementTreeSelectionDialog<Classifier>(resourceSet, filters, "Classifier", UMLPackage.eINSTANCE.getClassifier()) {

					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!redefinedClassifierEditUtil.getVirtualList().contains(elem))
								redefinedClassifierEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.redefinedClassifier,
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
		this.redefinedClassifier.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.redefinedClassifier, UMLViewsRepository.SWT_KIND));
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

		// Start of user code for the removeFromRedefinedClassifier() method body

		EObject editedElement = redefinedClassifierEditUtil.foundCorrespondingEObject(element);
		redefinedClassifierEditUtil.removeElement(element);
		redefinedClassifier.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.redefinedClassifier, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editRedefinedClassifier(Classifier element) {

		// Start of user code editRedefinedClassifier() method body

		EObject editedElement = redefinedClassifierEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				redefinedClassifierEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				redefinedClassifier.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.redefinedClassifier,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * @param container
	 */
	protected void createSubstitutionTableComposition(Composite parent) {
		this.substitution = new ReferencesTable<Substitution>(UMLMessages.DevicePropertiesEditionPart_SubstitutionLabel, new ReferencesTableListener<Substitution>() {

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
		this.substitution.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.substitution, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.substitution, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	private void addToSubstitution() {

		// Start of user code addToSubstitution() method body

		Substitution eObject = UMLFactory.eINSTANCE.createSubstitution();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject, resourceSet));
			if (propertiesEditionObject != null) {
				substitutionEditUtil.addElement(propertiesEditionObject);
				substitution.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.substitution,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * 
	 */
	private void removeFromSubstitution(Substitution element) {

		// Start of user code for the removeFromSubstitution() method body

		EObject editedElement = substitutionEditUtil.foundCorrespondingEObject(element);
		substitutionEditUtil.removeElement(element);
		substitution.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.substitution, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editSubstitution(Substitution element) {

		// Start of user code editSubstitution() method body

		EObject editedElement = substitutionEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				substitutionEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				substitution.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.substitution,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * @param container
	 */
	protected void createCollaborationUseTableComposition(Composite parent) {
		this.collaborationUse = new ReferencesTable<CollaborationUse>(UMLMessages.DevicePropertiesEditionPart_CollaborationUseLabel, new ReferencesTableListener<CollaborationUse>() {

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
		this.collaborationUse.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.collaborationUse, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.collaborationUse, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	private void addToCollaborationUse() {

		// Start of user code addToCollaborationUse() method body

		CollaborationUse eObject = UMLFactory.eINSTANCE.createCollaborationUse();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject, resourceSet));
			if (propertiesEditionObject != null) {
				collaborationUseEditUtil.addElement(propertiesEditionObject);
				collaborationUse.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.collaborationUse,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * 
	 */
	private void removeFromCollaborationUse(CollaborationUse element) {

		// Start of user code for the removeFromCollaborationUse() method body

		EObject editedElement = collaborationUseEditUtil.foundCorrespondingEObject(element);
		collaborationUseEditUtil.removeElement(element);
		collaborationUse.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.collaborationUse, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editCollaborationUse(CollaborationUse element) {

		// Start of user code editCollaborationUse() method body

		EObject editedElement = collaborationUseEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				collaborationUseEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				collaborationUse.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.collaborationUse,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * @param container
	 */
	protected void createOwnedUseCaseTableComposition(Composite parent) {
		this.ownedUseCase = new ReferencesTable<UseCase>(UMLMessages.DevicePropertiesEditionPart_OwnedUseCaseLabel, new ReferencesTableListener<UseCase>() {

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
		this.ownedUseCase.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.ownedUseCase, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedUseCase, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	private void addToOwnedUseCase() {

		// Start of user code addToOwnedUseCase() method body

		UseCase eObject = UMLFactory.eINSTANCE.createUseCase();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject, resourceSet));
			if (propertiesEditionObject != null) {
				ownedUseCaseEditUtil.addElement(propertiesEditionObject);
				ownedUseCase.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedUseCase,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * 
	 */
	private void removeFromOwnedUseCase(UseCase element) {

		// Start of user code for the removeFromOwnedUseCase() method body

		EObject editedElement = ownedUseCaseEditUtil.foundCorrespondingEObject(element);
		ownedUseCaseEditUtil.removeElement(element);
		ownedUseCase.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedUseCase, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editOwnedUseCase(UseCase element) {

		// Start of user code editOwnedUseCase() method body

		EObject editedElement = ownedUseCaseEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedUseCaseEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedUseCase.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedUseCase,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	protected void createUseCaseReferencesTable(Composite parent) {
		this.useCase = new ReferencesTable<UseCase>(UMLMessages.DevicePropertiesEditionPart_UseCaseLabel, new ReferencesTableListener<UseCase>() {

			public void handleAdd() {
				ViewerFilter useCaseFilter = new EObjectFilter(UMLPackage.eINSTANCE.getUseCase());
				ViewerFilter viewerFilter = new ViewerFilter() {

					public boolean select(Viewer viewer, Object parentElement, Object element) {
						if (element instanceof EObject)
							return (!useCaseEditUtil.contains((EObject) element));
						return false;
					}

				};
				List filters = new ArrayList();
				filters.add(useCaseFilter);
				filters.add(viewerFilter);
				TabElementTreeSelectionDialog<UseCase> dialog = new TabElementTreeSelectionDialog<UseCase>(resourceSet, filters, "UseCase", UMLPackage.eINSTANCE.getUseCase()) {

					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!useCaseEditUtil.getVirtualList().contains(elem))
								useCaseEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.useCase,
									PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
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
		this.useCase.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.useCase, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.useCase, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	private void removeFromUseCase(UseCase element) {

		// Start of user code for the removeFromUseCase() method body

		EObject editedElement = useCaseEditUtil.foundCorrespondingEObject(element);
		useCaseEditUtil.removeElement(element);
		useCase.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.useCase, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editUseCase(UseCase element) {

		// Start of user code editUseCase() method body

		EObject editedElement = useCaseEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				useCaseEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				useCase.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.useCase, PropertiesEditionEvent.COMMIT,
						PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * @param container
	 */
	protected void createOwnedAttributeTableComposition(Composite parent) {
		this.ownedAttribute = new ReferencesTable<Property>(UMLMessages.DevicePropertiesEditionPart_OwnedAttributeLabel, new ReferencesTableListener<Property>() {

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
		this.ownedAttribute.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.ownedAttribute, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedAttribute, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	private void addToOwnedAttribute() {

		// Start of user code addToOwnedAttribute() method body

		Property eObject = UMLFactory.eINSTANCE.createProperty();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject, resourceSet));
			if (propertiesEditionObject != null) {
				ownedAttributeEditUtil.addElement(propertiesEditionObject);
				ownedAttribute.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedAttribute,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * 
	 */
	private void removeFromOwnedAttribute(Property element) {

		// Start of user code for the removeFromOwnedAttribute() method body

		EObject editedElement = ownedAttributeEditUtil.foundCorrespondingEObject(element);
		ownedAttributeEditUtil.removeElement(element);
		ownedAttribute.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedAttribute, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editOwnedAttribute(Property element) {

		// Start of user code editOwnedAttribute() method body

		EObject editedElement = ownedAttributeEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedAttributeEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedAttribute.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedAttribute,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * @param container
	 */
	protected void createOwnedConnectorTableComposition(Composite parent) {
		this.ownedConnector = new ReferencesTable<Connector>(UMLMessages.DevicePropertiesEditionPart_OwnedConnectorLabel, new ReferencesTableListener<Connector>() {

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
		this.ownedConnector.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.ownedConnector, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedConnector, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	private void addToOwnedConnector() {

		// Start of user code addToOwnedConnector() method body

		Connector eObject = UMLFactory.eINSTANCE.createConnector();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject, resourceSet));
			if (propertiesEditionObject != null) {
				ownedConnectorEditUtil.addElement(propertiesEditionObject);
				ownedConnector.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedConnector,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * 
	 */
	private void removeFromOwnedConnector(Connector element) {

		// Start of user code for the removeFromOwnedConnector() method body

		EObject editedElement = ownedConnectorEditUtil.foundCorrespondingEObject(element);
		ownedConnectorEditUtil.removeElement(element);
		ownedConnector.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedConnector, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editOwnedConnector(Connector element) {

		// Start of user code editOwnedConnector() method body

		EObject editedElement = ownedConnectorEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedConnectorEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedConnector.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedConnector,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * @param container
	 */
	protected void createOwnedBehaviorTableComposition(Composite parent) {
		this.ownedBehavior = new ReferencesTable<Behavior>(UMLMessages.DevicePropertiesEditionPart_OwnedBehaviorLabel, new ReferencesTableListener<Behavior>() {

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
		this.ownedBehavior.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.ownedBehavior, UMLViewsRepository.SWT_KIND));
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
	private void addToOwnedBehavior() {

		// Start of user code addToOwnedBehavior() method body

		// End of user code
	}

	/**
	 * 
	 */
	private void removeFromOwnedBehavior(Behavior element) {

		// Start of user code for the removeFromOwnedBehavior() method body

		EObject editedElement = ownedBehaviorEditUtil.foundCorrespondingEObject(element);
		ownedBehaviorEditUtil.removeElement(element);
		ownedBehavior.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedBehavior, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editOwnedBehavior(Behavior element) {

		// Start of user code editOwnedBehavior() method body

		EObject editedElement = ownedBehaviorEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedBehaviorEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedBehavior.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedBehavior,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * @param container
	 */
	protected void createInterfaceRealizationTableComposition(Composite parent) {
		this.interfaceRealization = new ReferencesTable<InterfaceRealization>(UMLMessages.DevicePropertiesEditionPart_InterfaceRealizationLabel, new ReferencesTableListener<InterfaceRealization>() {

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
		this.interfaceRealization.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.interfaceRealization, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.interfaceRealization,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	private void addToInterfaceRealization() {

		// Start of user code addToInterfaceRealization() method body

		InterfaceRealization eObject = UMLFactory.eINSTANCE.createInterfaceRealization();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject, resourceSet));
			if (propertiesEditionObject != null) {
				interfaceRealizationEditUtil.addElement(propertiesEditionObject);
				interfaceRealization.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.interfaceRealization,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * 
	 */
	private void removeFromInterfaceRealization(InterfaceRealization element) {

		// Start of user code for the removeFromInterfaceRealization() method body

		EObject editedElement = interfaceRealizationEditUtil.foundCorrespondingEObject(element);
		interfaceRealizationEditUtil.removeElement(element);
		interfaceRealization.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.interfaceRealization,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editInterfaceRealization(InterfaceRealization element) {

		// Start of user code editInterfaceRealization() method body

		EObject editedElement = interfaceRealizationEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				interfaceRealizationEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				interfaceRealization.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.interfaceRealization,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * @param container
	 */
	protected void createOwnedTriggerTableComposition(Composite parent) {
		this.ownedTrigger = new ReferencesTable<Trigger>(UMLMessages.DevicePropertiesEditionPart_OwnedTriggerLabel, new ReferencesTableListener<Trigger>() {

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
		this.ownedTrigger.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.ownedTrigger, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedTrigger, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	private void addToOwnedTrigger() {

		// Start of user code addToOwnedTrigger() method body

		Trigger eObject = UMLFactory.eINSTANCE.createTrigger();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject, resourceSet));
			if (propertiesEditionObject != null) {
				ownedTriggerEditUtil.addElement(propertiesEditionObject);
				ownedTrigger.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedTrigger,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * 
	 */
	private void removeFromOwnedTrigger(Trigger element) {

		// Start of user code for the removeFromOwnedTrigger() method body

		EObject editedElement = ownedTriggerEditUtil.foundCorrespondingEObject(element);
		ownedTriggerEditUtil.removeElement(element);
		ownedTrigger.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedTrigger, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editOwnedTrigger(Trigger element) {

		// Start of user code editOwnedTrigger() method body

		EObject editedElement = ownedTriggerEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedTriggerEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedTrigger.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedTrigger,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * @param container
	 */
	protected void createNestedClassifierTableComposition(Composite parent) {
		this.nestedClassifier = new ReferencesTable<Classifier>(UMLMessages.DevicePropertiesEditionPart_NestedClassifierLabel, new ReferencesTableListener<Classifier>() {

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
		this.nestedClassifier.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.nestedClassifier, UMLViewsRepository.SWT_KIND));
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
	private void addToNestedClassifier() {

		// Start of user code addToNestedClassifier() method body

		// End of user code
	}

	/**
	 * 
	 */
	private void removeFromNestedClassifier(Classifier element) {

		// Start of user code for the removeFromNestedClassifier() method body

		EObject editedElement = nestedClassifierEditUtil.foundCorrespondingEObject(element);
		nestedClassifierEditUtil.removeElement(element);
		nestedClassifier.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.nestedClassifier, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editNestedClassifier(Classifier element) {

		// Start of user code editNestedClassifier() method body

		EObject editedElement = nestedClassifierEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				nestedClassifierEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				nestedClassifier.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.nestedClassifier,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * @param container
	 */
	protected void createOwnedOperationTableComposition(Composite parent) {
		this.ownedOperation = new ReferencesTable<Operation>(UMLMessages.DevicePropertiesEditionPart_OwnedOperationLabel, new ReferencesTableListener<Operation>() {

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
		this.ownedOperation.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.ownedOperation, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedOperation, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	private void addToOwnedOperation() {

		// Start of user code addToOwnedOperation() method body

		Operation eObject = UMLFactory.eINSTANCE.createOperation();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject, resourceSet));
			if (propertiesEditionObject != null) {
				ownedOperationEditUtil.addElement(propertiesEditionObject);
				ownedOperation.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedOperation,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * 
	 */
	private void removeFromOwnedOperation(Operation element) {

		// Start of user code for the removeFromOwnedOperation() method body

		EObject editedElement = ownedOperationEditUtil.foundCorrespondingEObject(element);
		ownedOperationEditUtil.removeElement(element);
		ownedOperation.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedOperation, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editOwnedOperation(Operation element) {

		// Start of user code editOwnedOperation() method body

		EObject editedElement = ownedOperationEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedOperationEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedOperation.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedOperation,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	protected void createIsActiveCheckbox(Composite parent) {
		isActive = new Button(parent, SWT.CHECK);
		isActive.setText(UMLMessages.DevicePropertiesEditionPart_IsActiveLabel);
		GridData isActiveData = new GridData(GridData.FILL_HORIZONTAL);
		isActiveData.horizontalSpan = 2;
		isActive.setLayoutData(isActiveData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.isActive, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	/**
	 * @param container
	 */
	protected void createOwnedReceptionTableComposition(Composite parent) {
		this.ownedReception = new ReferencesTable<Reception>(UMLMessages.DevicePropertiesEditionPart_OwnedReceptionLabel, new ReferencesTableListener<Reception>() {

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
		this.ownedReception.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.ownedReception, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedReception, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	private void addToOwnedReception() {

		// Start of user code addToOwnedReception() method body

		Reception eObject = UMLFactory.eINSTANCE.createReception();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject, resourceSet));
			if (propertiesEditionObject != null) {
				ownedReceptionEditUtil.addElement(propertiesEditionObject);
				ownedReception.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedReception,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * 
	 */
	private void removeFromOwnedReception(Reception element) {

		// Start of user code for the removeFromOwnedReception() method body

		EObject editedElement = ownedReceptionEditUtil.foundCorrespondingEObject(element);
		ownedReceptionEditUtil.removeElement(element);
		ownedReception.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedReception, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editOwnedReception(Reception element) {

		// Start of user code editOwnedReception() method body

		EObject editedElement = ownedReceptionEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				ownedReceptionEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				ownedReception.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.ownedReception,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * @param container
	 */
	protected void createDeploymentTableComposition(Composite parent) {
		this.deployment = new ReferencesTable<Deployment>(UMLMessages.DevicePropertiesEditionPart_DeploymentLabel, new ReferencesTableListener<Deployment>() {

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
		this.deployment.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.deployment, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.deployment, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	private void addToDeployment() {

		// Start of user code addToDeployment() method body

		Deployment eObject = UMLFactory.eINSTANCE.createDeployment();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject, resourceSet));
			if (propertiesEditionObject != null) {
				deploymentEditUtil.addElement(propertiesEditionObject);
				deployment.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.deployment, PropertiesEditionEvent.CHANGE,
						PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * 
	 */
	private void removeFromDeployment(Deployment element) {

		// Start of user code for the removeFromDeployment() method body

		EObject editedElement = deploymentEditUtil.foundCorrespondingEObject(element);
		deploymentEditUtil.removeElement(element);
		deployment.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.deployment, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editDeployment(Deployment element) {

		// Start of user code editDeployment() method body

		EObject editedElement = deploymentEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				deploymentEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				deployment.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.deployment, PropertiesEditionEvent.CHANGE,
						PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * @param container
	 */
	protected void createNestedNodeTableComposition(Composite parent) {
		this.nestedNode = new ReferencesTable<Node>(UMLMessages.DevicePropertiesEditionPart_NestedNodeLabel, new ReferencesTableListener<Node>() {

			public void handleAdd() {
				addToNestedNode();
			}

			public void handleEdit(Node element) {
				editNestedNode(element);
			}

			public void handleMove(Node element, int oldIndex, int newIndex) {
				moveNestedNode(element, oldIndex, newIndex);
			}

			public void handleRemove(Node element) {
				removeFromNestedNode(element);
			}

			public void navigateTo(Node element) {
			}
		});
		this.nestedNode.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Device.nestedNode, UMLViewsRepository.SWT_KIND));
		this.nestedNode.createControls(parent);
		GridData nestedNodeData = new GridData(GridData.FILL_HORIZONTAL);
		nestedNodeData.horizontalSpan = 3;
		this.nestedNode.setLayoutData(nestedNodeData);
	}

	/**
	 * 
	 */
	private void moveNestedNode(Node element, int oldIndex, int newIndex) {

		EObject editedElement = nestedNodeEditUtil.foundCorrespondingEObject(element);
		nestedNodeEditUtil.moveElement(element, oldIndex, newIndex);
		nestedNode.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.nestedNode, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	private void addToNestedNode() {

		// Start of user code addToNestedNode() method body

		Node eObject = UMLFactory.eINSTANCE.createNode();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent, eObject, resourceSet));
			if (propertiesEditionObject != null) {
				nestedNodeEditUtil.addElement(propertiesEditionObject);
				nestedNode.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.nestedNode, PropertiesEditionEvent.CHANGE,
						PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code
	}

	/**
	 * 
	 */
	private void removeFromNestedNode(Node element) {

		// Start of user code for the removeFromNestedNode() method body

		EObject editedElement = nestedNodeEditUtil.foundCorrespondingEObject(element);
		nestedNodeEditUtil.removeElement(element);
		nestedNode.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.nestedNode, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code
	}

	/**
	 * 
	 */
	private void editNestedNode(Node element) {

		// Start of user code editNestedNode() method body

		EObject editedElement = nestedNodeEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance().getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				nestedNodeEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				nestedNode.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DevicePropertiesEditionPartImpl.this, UMLViewsRepository.Device.nestedNode, PropertiesEditionEvent.CHANGE,
						PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedCommentToAdd()
	 */
	public List getOwnedCommentToAdd() {
		return ownedCommentEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedCommentToRemove()
	 */
	public List getOwnedCommentToRemove() {
		return ownedCommentEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedCommentToEdit()
	 */
	public Map getOwnedCommentToEdit() {
		return ownedCommentEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedCommentToMove()
	 */
	public List getOwnedCommentToMove() {
		return ownedCommentEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedCommentTable()
	 */
	public List getOwnedCommentTable() {
		return ownedCommentEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initOwnedComment(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateOwnedComment(EObject newValue)
	 */
	public void updateOwnedComment(EObject newValue) {
		if (ownedCommentEditUtil != null) {
			ownedCommentEditUtil.reinit(newValue);
			ownedComment.refresh();
		}
	}

	public void setMessageForOwnedComment(String msg, int msgLevel) {

	}

	public void unsetMessageForOwnedComment() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getName()
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#setName(String newValue)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getVisibility()
	 */
	public Enumerator getVisibility() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) visibility.getSelection()).getFirstElement();
		return selection.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initVisibility(EEnum eenum, Enumerator current)
	 */
	public void initVisibility(EEnum eenum, Enumerator current) {
		visibility.setInput(eenum.getELiterals());
		visibility.setSelection(new StructuredSelection(current));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#setVisibility(Enumerator newValue)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getClientDependencyToAdd()
	 */
	public List getClientDependencyToAdd() {
		return clientDependencyEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getClientDependencyToRemove()
	 */
	public List getClientDependencyToRemove() {
		return clientDependencyEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initClientDependency(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateClientDependency(EObject newValue)
	 */
	public void updateClientDependency(EObject newValue) {
		if (clientDependencyEditUtil != null) {
			clientDependencyEditUtil.reinit(newValue);
			clientDependency.refresh();
		}
	}

	public void setMessageForClientDependency(String msg, int msgLevel) {

	}

	public void unsetMessageForClientDependency() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getElementImportToAdd()
	 */
	public List getElementImportToAdd() {
		return elementImportEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getElementImportToRemove()
	 */
	public List getElementImportToRemove() {
		return elementImportEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getElementImportToEdit()
	 */
	public Map getElementImportToEdit() {
		return elementImportEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getElementImportToMove()
	 */
	public List getElementImportToMove() {
		return elementImportEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getElementImportTable()
	 */
	public List getElementImportTable() {
		return elementImportEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initElementImport(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateElementImport(EObject newValue)
	 */
	public void updateElementImport(EObject newValue) {
		if (elementImportEditUtil != null) {
			elementImportEditUtil.reinit(newValue);
			elementImport.refresh();
		}
	}

	public void setMessageForElementImport(String msg, int msgLevel) {

	}

	public void unsetMessageForElementImport() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getPackageImportToAdd()
	 */
	public List getPackageImportToAdd() {
		return packageImportEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getPackageImportToRemove()
	 */
	public List getPackageImportToRemove() {
		return packageImportEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getPackageImportToEdit()
	 */
	public Map getPackageImportToEdit() {
		return packageImportEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getPackageImportToMove()
	 */
	public List getPackageImportToMove() {
		return packageImportEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getPackageImportTable()
	 */
	public List getPackageImportTable() {
		return packageImportEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initPackageImport(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updatePackageImport(EObject newValue)
	 */
	public void updatePackageImport(EObject newValue) {
		if (packageImportEditUtil != null) {
			packageImportEditUtil.reinit(newValue);
			packageImport.refresh();
		}
	}

	public void setMessageForPackageImport(String msg, int msgLevel) {

	}

	public void unsetMessageForPackageImport() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedRuleToAdd()
	 */
	public List getOwnedRuleToAdd() {
		return ownedRuleEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedRuleToRemove()
	 */
	public List getOwnedRuleToRemove() {
		return ownedRuleEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedRuleToEdit()
	 */
	public Map getOwnedRuleToEdit() {
		return ownedRuleEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedRuleToMove()
	 */
	public List getOwnedRuleToMove() {
		return ownedRuleEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedRuleTable()
	 */
	public List getOwnedRuleTable() {
		return ownedRuleEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initOwnedRule(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateOwnedRule(EObject newValue)
	 */
	public void updateOwnedRule(EObject newValue) {
		if (ownedRuleEditUtil != null) {
			ownedRuleEditUtil.reinit(newValue);
			ownedRule.refresh();
		}
	}

	public void setMessageForOwnedRule(String msg, int msgLevel) {

	}

	public void unsetMessageForOwnedRule() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getIsLeaf()
	 */
	public Boolean getIsLeaf() {
		return Boolean.valueOf(isLeaf.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#setIsLeaf(Boolean newValue)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getTemplateBindingToAdd()
	 */
	public List getTemplateBindingToAdd() {
		return templateBindingEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getTemplateBindingToRemove()
	 */
	public List getTemplateBindingToRemove() {
		return templateBindingEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getTemplateBindingToEdit()
	 */
	public Map getTemplateBindingToEdit() {
		return templateBindingEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getTemplateBindingToMove()
	 */
	public List getTemplateBindingToMove() {
		return templateBindingEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getTemplateBindingTable()
	 */
	public List getTemplateBindingTable() {
		return templateBindingEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initTemplateBinding(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateTemplateBinding(EObject newValue)
	 */
	public void updateTemplateBinding(EObject newValue) {
		if (templateBindingEditUtil != null) {
			templateBindingEditUtil.reinit(newValue);
			templateBinding.refresh();
		}
	}

	public void setMessageForTemplateBinding(String msg, int msgLevel) {

	}

	public void unsetMessageForTemplateBinding() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getIsAbstract()
	 */
	public Boolean getIsAbstract() {
		return Boolean.valueOf(isAbstract.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#setIsAbstract(Boolean newValue)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getGeneralizationToAdd()
	 */
	public List getGeneralizationToAdd() {
		return generalizationEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getGeneralizationToRemove()
	 */
	public List getGeneralizationToRemove() {
		return generalizationEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getGeneralizationToEdit()
	 */
	public Map getGeneralizationToEdit() {
		return generalizationEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getGeneralizationToMove()
	 */
	public List getGeneralizationToMove() {
		return generalizationEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getGeneralizationTable()
	 */
	public List getGeneralizationTable() {
		return generalizationEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initGeneralization(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateGeneralization(EObject newValue)
	 */
	public void updateGeneralization(EObject newValue) {
		if (generalizationEditUtil != null) {
			generalizationEditUtil.reinit(newValue);
			generalization.refresh();
		}
	}

	public void setMessageForGeneralization(String msg, int msgLevel) {

	}

	public void unsetMessageForGeneralization() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getPowertypeExtentToAdd()
	 */
	public List getPowertypeExtentToAdd() {
		return powertypeExtentEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getPowertypeExtentToRemove()
	 */
	public List getPowertypeExtentToRemove() {
		return powertypeExtentEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initPowertypeExtent(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updatePowertypeExtent(EObject newValue)
	 */
	public void updatePowertypeExtent(EObject newValue) {
		if (powertypeExtentEditUtil != null) {
			powertypeExtentEditUtil.reinit(newValue);
			powertypeExtent.refresh();
		}
	}

	public void setMessageForPowertypeExtent(String msg, int msgLevel) {

	}

	public void unsetMessageForPowertypeExtent() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getRedefinedClassifierToAdd()
	 */
	public List getRedefinedClassifierToAdd() {
		return redefinedClassifierEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getRedefinedClassifierToRemove()
	 */
	public List getRedefinedClassifierToRemove() {
		return redefinedClassifierEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initRedefinedClassifier(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateRedefinedClassifier(EObject newValue)
	 */
	public void updateRedefinedClassifier(EObject newValue) {
		if (redefinedClassifierEditUtil != null) {
			redefinedClassifierEditUtil.reinit(newValue);
			redefinedClassifier.refresh();
		}
	}

	public void setMessageForRedefinedClassifier(String msg, int msgLevel) {

	}

	public void unsetMessageForRedefinedClassifier() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getSubstitutionToAdd()
	 */
	public List getSubstitutionToAdd() {
		return substitutionEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getSubstitutionToRemove()
	 */
	public List getSubstitutionToRemove() {
		return substitutionEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getSubstitutionToEdit()
	 */
	public Map getSubstitutionToEdit() {
		return substitutionEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getSubstitutionToMove()
	 */
	public List getSubstitutionToMove() {
		return substitutionEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getSubstitutionTable()
	 */
	public List getSubstitutionTable() {
		return substitutionEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initSubstitution(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateSubstitution(EObject newValue)
	 */
	public void updateSubstitution(EObject newValue) {
		if (substitutionEditUtil != null) {
			substitutionEditUtil.reinit(newValue);
			substitution.refresh();
		}
	}

	public void setMessageForSubstitution(String msg, int msgLevel) {

	}

	public void unsetMessageForSubstitution() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getCollaborationUseToAdd()
	 */
	public List getCollaborationUseToAdd() {
		return collaborationUseEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getCollaborationUseToRemove()
	 */
	public List getCollaborationUseToRemove() {
		return collaborationUseEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getCollaborationUseToEdit()
	 */
	public Map getCollaborationUseToEdit() {
		return collaborationUseEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getCollaborationUseToMove()
	 */
	public List getCollaborationUseToMove() {
		return collaborationUseEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getCollaborationUseTable()
	 */
	public List getCollaborationUseTable() {
		return collaborationUseEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initCollaborationUse(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateCollaborationUse(EObject newValue)
	 */
	public void updateCollaborationUse(EObject newValue) {
		if (collaborationUseEditUtil != null) {
			collaborationUseEditUtil.reinit(newValue);
			collaborationUse.refresh();
		}
	}

	public void setMessageForCollaborationUse(String msg, int msgLevel) {

	}

	public void unsetMessageForCollaborationUse() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedUseCaseToAdd()
	 */
	public List getOwnedUseCaseToAdd() {
		return ownedUseCaseEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedUseCaseToRemove()
	 */
	public List getOwnedUseCaseToRemove() {
		return ownedUseCaseEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedUseCaseToEdit()
	 */
	public Map getOwnedUseCaseToEdit() {
		return ownedUseCaseEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedUseCaseToMove()
	 */
	public List getOwnedUseCaseToMove() {
		return ownedUseCaseEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedUseCaseTable()
	 */
	public List getOwnedUseCaseTable() {
		return ownedUseCaseEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initOwnedUseCase(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateOwnedUseCase(EObject newValue)
	 */
	public void updateOwnedUseCase(EObject newValue) {
		if (ownedUseCaseEditUtil != null) {
			ownedUseCaseEditUtil.reinit(newValue);
			ownedUseCase.refresh();
		}
	}

	public void setMessageForOwnedUseCase(String msg, int msgLevel) {

	}

	public void unsetMessageForOwnedUseCase() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getUseCaseToAdd()
	 */
	public List getUseCaseToAdd() {
		return useCaseEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getUseCaseToRemove()
	 */
	public List getUseCaseToRemove() {
		return useCaseEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initUseCase(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateUseCase(EObject newValue)
	 */
	public void updateUseCase(EObject newValue) {
		if (useCaseEditUtil != null) {
			useCaseEditUtil.reinit(newValue);
			useCase.refresh();
		}
	}

	public void setMessageForUseCase(String msg, int msgLevel) {

	}

	public void unsetMessageForUseCase() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedAttributeToAdd()
	 */
	public List getOwnedAttributeToAdd() {
		return ownedAttributeEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedAttributeToRemove()
	 */
	public List getOwnedAttributeToRemove() {
		return ownedAttributeEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedAttributeToEdit()
	 */
	public Map getOwnedAttributeToEdit() {
		return ownedAttributeEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedAttributeToMove()
	 */
	public List getOwnedAttributeToMove() {
		return ownedAttributeEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedAttributeTable()
	 */
	public List getOwnedAttributeTable() {
		return ownedAttributeEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initOwnedAttribute(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateOwnedAttribute(EObject newValue)
	 */
	public void updateOwnedAttribute(EObject newValue) {
		if (ownedAttributeEditUtil != null) {
			ownedAttributeEditUtil.reinit(newValue);
			ownedAttribute.refresh();
		}
	}

	public void setMessageForOwnedAttribute(String msg, int msgLevel) {

	}

	public void unsetMessageForOwnedAttribute() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedConnectorToAdd()
	 */
	public List getOwnedConnectorToAdd() {
		return ownedConnectorEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedConnectorToRemove()
	 */
	public List getOwnedConnectorToRemove() {
		return ownedConnectorEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedConnectorToEdit()
	 */
	public Map getOwnedConnectorToEdit() {
		return ownedConnectorEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedConnectorToMove()
	 */
	public List getOwnedConnectorToMove() {
		return ownedConnectorEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedConnectorTable()
	 */
	public List getOwnedConnectorTable() {
		return ownedConnectorEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initOwnedConnector(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateOwnedConnector(EObject newValue)
	 */
	public void updateOwnedConnector(EObject newValue) {
		if (ownedConnectorEditUtil != null) {
			ownedConnectorEditUtil.reinit(newValue);
			ownedConnector.refresh();
		}
	}

	public void setMessageForOwnedConnector(String msg, int msgLevel) {

	}

	public void unsetMessageForOwnedConnector() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedBehaviorToAdd()
	 */
	public List getOwnedBehaviorToAdd() {
		return ownedBehaviorEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedBehaviorToRemove()
	 */
	public List getOwnedBehaviorToRemove() {
		return ownedBehaviorEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedBehaviorToEdit()
	 */
	public Map getOwnedBehaviorToEdit() {
		return ownedBehaviorEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedBehaviorToMove()
	 */
	public List getOwnedBehaviorToMove() {
		return ownedBehaviorEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedBehaviorTable()
	 */
	public List getOwnedBehaviorTable() {
		return ownedBehaviorEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initOwnedBehavior(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateOwnedBehavior(EObject newValue)
	 */
	public void updateOwnedBehavior(EObject newValue) {
		if (ownedBehaviorEditUtil != null) {
			ownedBehaviorEditUtil.reinit(newValue);
			ownedBehavior.refresh();
		}
	}

	public void setMessageForOwnedBehavior(String msg, int msgLevel) {

	}

	public void unsetMessageForOwnedBehavior() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getInterfaceRealizationToAdd()
	 */
	public List getInterfaceRealizationToAdd() {
		return interfaceRealizationEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getInterfaceRealizationToRemove()
	 */
	public List getInterfaceRealizationToRemove() {
		return interfaceRealizationEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getInterfaceRealizationToEdit()
	 */
	public Map getInterfaceRealizationToEdit() {
		return interfaceRealizationEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getInterfaceRealizationToMove()
	 */
	public List getInterfaceRealizationToMove() {
		return interfaceRealizationEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getInterfaceRealizationTable()
	 */
	public List getInterfaceRealizationTable() {
		return interfaceRealizationEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initInterfaceRealization(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateInterfaceRealization(EObject newValue)
	 */
	public void updateInterfaceRealization(EObject newValue) {
		if (interfaceRealizationEditUtil != null) {
			interfaceRealizationEditUtil.reinit(newValue);
			interfaceRealization.refresh();
		}
	}

	public void setMessageForInterfaceRealization(String msg, int msgLevel) {

	}

	public void unsetMessageForInterfaceRealization() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedTriggerToAdd()
	 */
	public List getOwnedTriggerToAdd() {
		return ownedTriggerEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedTriggerToRemove()
	 */
	public List getOwnedTriggerToRemove() {
		return ownedTriggerEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedTriggerToEdit()
	 */
	public Map getOwnedTriggerToEdit() {
		return ownedTriggerEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedTriggerToMove()
	 */
	public List getOwnedTriggerToMove() {
		return ownedTriggerEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedTriggerTable()
	 */
	public List getOwnedTriggerTable() {
		return ownedTriggerEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initOwnedTrigger(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateOwnedTrigger(EObject newValue)
	 */
	public void updateOwnedTrigger(EObject newValue) {
		if (ownedTriggerEditUtil != null) {
			ownedTriggerEditUtil.reinit(newValue);
			ownedTrigger.refresh();
		}
	}

	public void setMessageForOwnedTrigger(String msg, int msgLevel) {

	}

	public void unsetMessageForOwnedTrigger() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getNestedClassifierToAdd()
	 */
	public List getNestedClassifierToAdd() {
		return nestedClassifierEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getNestedClassifierToRemove()
	 */
	public List getNestedClassifierToRemove() {
		return nestedClassifierEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getNestedClassifierToEdit()
	 */
	public Map getNestedClassifierToEdit() {
		return nestedClassifierEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getNestedClassifierToMove()
	 */
	public List getNestedClassifierToMove() {
		return nestedClassifierEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getNestedClassifierTable()
	 */
	public List getNestedClassifierTable() {
		return nestedClassifierEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initNestedClassifier(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateNestedClassifier(EObject newValue)
	 */
	public void updateNestedClassifier(EObject newValue) {
		if (nestedClassifierEditUtil != null) {
			nestedClassifierEditUtil.reinit(newValue);
			nestedClassifier.refresh();
		}
	}

	public void setMessageForNestedClassifier(String msg, int msgLevel) {

	}

	public void unsetMessageForNestedClassifier() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedOperationToAdd()
	 */
	public List getOwnedOperationToAdd() {
		return ownedOperationEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedOperationToRemove()
	 */
	public List getOwnedOperationToRemove() {
		return ownedOperationEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedOperationToEdit()
	 */
	public Map getOwnedOperationToEdit() {
		return ownedOperationEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedOperationToMove()
	 */
	public List getOwnedOperationToMove() {
		return ownedOperationEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedOperationTable()
	 */
	public List getOwnedOperationTable() {
		return ownedOperationEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initOwnedOperation(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateOwnedOperation(EObject newValue)
	 */
	public void updateOwnedOperation(EObject newValue) {
		if (ownedOperationEditUtil != null) {
			ownedOperationEditUtil.reinit(newValue);
			ownedOperation.refresh();
		}
	}

	public void setMessageForOwnedOperation(String msg, int msgLevel) {

	}

	public void unsetMessageForOwnedOperation() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getIsActive()
	 */
	public Boolean getIsActive() {
		return Boolean.valueOf(isActive.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#setIsActive(Boolean newValue)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedReceptionToAdd()
	 */
	public List getOwnedReceptionToAdd() {
		return ownedReceptionEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedReceptionToRemove()
	 */
	public List getOwnedReceptionToRemove() {
		return ownedReceptionEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedReceptionToEdit()
	 */
	public Map getOwnedReceptionToEdit() {
		return ownedReceptionEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedReceptionToMove()
	 */
	public List getOwnedReceptionToMove() {
		return ownedReceptionEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getOwnedReceptionTable()
	 */
	public List getOwnedReceptionTable() {
		return ownedReceptionEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initOwnedReception(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateOwnedReception(EObject newValue)
	 */
	public void updateOwnedReception(EObject newValue) {
		if (ownedReceptionEditUtil != null) {
			ownedReceptionEditUtil.reinit(newValue);
			ownedReception.refresh();
		}
	}

	public void setMessageForOwnedReception(String msg, int msgLevel) {

	}

	public void unsetMessageForOwnedReception() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getDeploymentToAdd()
	 */
	public List getDeploymentToAdd() {
		return deploymentEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getDeploymentToRemove()
	 */
	public List getDeploymentToRemove() {
		return deploymentEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getDeploymentToEdit()
	 */
	public Map getDeploymentToEdit() {
		return deploymentEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getDeploymentToMove()
	 */
	public List getDeploymentToMove() {
		return deploymentEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getDeploymentTable()
	 */
	public List getDeploymentTable() {
		return deploymentEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initDeployment(EObject current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateDeployment(EObject newValue)
	 */
	public void updateDeployment(EObject newValue) {
		if (deploymentEditUtil != null) {
			deploymentEditUtil.reinit(newValue);
			deployment.refresh();
		}
	}

	public void setMessageForDeployment(String msg, int msgLevel) {

	}

	public void unsetMessageForDeployment() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getNestedNodeToAdd()
	 */
	public List getNestedNodeToAdd() {
		return nestedNodeEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getNestedNodeToRemove()
	 */
	public List getNestedNodeToRemove() {
		return nestedNodeEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getNestedNodeToEdit()
	 */
	public Map getNestedNodeToEdit() {
		return nestedNodeEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getNestedNodeToMove()
	 */
	public List getNestedNodeToMove() {
		return nestedNodeEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#getNestedNodeTable()
	 */
	public List getNestedNodeTable() {
		return nestedNodeEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#initNestedNode(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initNestedNode(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			nestedNodeEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			nestedNodeEditUtil = new EMFListEditUtil(current, feature);
		this.nestedNode.setInput(nestedNodeEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.DevicePropertiesEditionPart#updateNestedNode(EObject newValue)
	 */
	public void updateNestedNode(EObject newValue) {
		if (nestedNodeEditUtil != null) {
			nestedNodeEditUtil.reinit(newValue);
			nestedNode.refresh();
		}
	}

	public void setMessageForNestedNode(String msg, int msgLevel) {

	}

	public void unsetMessageForNestedNode() {

	}

	// Start of user code additional methods

	// End of user code
}
