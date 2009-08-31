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
import org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart;

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
import org.eclipse.uml2.uml.Classifier;
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
import org.eclipse.uml2.uml.Slot;
import org.eclipse.uml2.uml.Deployment;

import org.eclipse.emf.eef.runtime.ui.widgets.HorizontalBox;
import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;

// End of user code

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class InstanceSpecificationPropertiesEditionPartImpl extends CompositePropertiesEditionPart implements
		ISWTPropertiesEditionPart, InstanceSpecificationPropertiesEditionPart {

	protected Text name;

	protected EMFComboViewer visibility;

	protected EMFListEditUtil slotEditUtil;

	protected ReferencesTable<?> slot;

	protected List<ViewerFilter> slotBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> slotFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil classifierEditUtil;

	protected ReferencesTable<?> classifier;

	protected List<ViewerFilter> classifierBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> classifierFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil deploymentEditUtil;

	protected ReferencesTable<?> deployment;

	protected List<ViewerFilter> deploymentBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> deploymentFilters = new ArrayList<ViewerFilter>();

	public InstanceSpecificationPropertiesEditionPartImpl(IPropertiesEditionComponent editionComponent) {
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

		// Start of user code for additional ui definition

		// End of user code

	}

	protected void createGeneralGroup(Composite parent) {
		Group generalGroup = new Group(parent, SWT.NONE);
		generalGroup.setText(UMLMessages.InstanceSpecificationPropertiesEditionPart_GeneralGroupLabel);
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
		SWTUtils.createPartLabel(parent, UMLMessages.InstanceSpecificationPropertiesEditionPart_NameLabel,
				propertiesEditionComponent.isRequired(UMLViewsRepository.InstanceSpecification.name,
						UMLViewsRepository.SWT_KIND));
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
							InstanceSpecificationPropertiesEditionPartImpl.this,
							UMLViewsRepository.InstanceSpecification.name, PropertiesEditionEvent.CHANGE,
							PropertiesEditionEvent.SET, null, name.getText()));
			}

		});

		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.InstanceSpecification.name, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createVisibilityEMFComboViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UMLMessages.InstanceSpecificationPropertiesEditionPart_VisibilityLabel,
				propertiesEditionComponent.isRequired(UMLViewsRepository.InstanceSpecification.visibility,
						UMLViewsRepository.SWT_KIND));
		visibility = new EMFComboViewer(parent);
		visibility.setContentProvider(new ArrayContentProvider());
		visibility.setLabelProvider(new AdapterFactoryLabelProvider(new EcoreAdapterFactory()));
		GridData visibilityData = new GridData(GridData.FILL_HORIZONTAL);
		visibility.getCombo().setLayoutData(visibilityData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.InstanceSpecification.visibility, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createGeneralHBox1HBox(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 2;
		container.setLayoutData(gridData);
		HorizontalBox generalHBox1HBox = new HorizontalBox(container);

		// create sub figures
		createSlotAdvancedTableComposition(generalHBox1HBox);
		createClassifierAdvancedReferencesTable(generalHBox1HBox);
		createDeploymentAdvancedTableComposition(generalHBox1HBox);

		parent.pack();
	}

	/**
	 * @param container
	 */
	protected void createSlotAdvancedTableComposition(Composite parent) {
		this.slot = new ReferencesTable<Slot>(UMLMessages.InstanceSpecificationPropertiesEditionPart_SlotLabel,
				new ReferencesTableListener<Slot>() {

					public void handleAdd() {
						addToSlot();
					}

					public void handleEdit(Slot element) {
						editSlot(element);
					}

					public void handleMove(Slot element, int oldIndex, int newIndex) {
						moveSlot(element, oldIndex, newIndex);
					}

					public void handleRemove(Slot element) {
						removeFromSlot(element);
					}

					public void navigateTo(Slot element) {
					}
				});
		this.slot.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.InstanceSpecification.slot,
				UMLViewsRepository.SWT_KIND));
		this.slot.createControls(parent);
		GridData slotData = new GridData(GridData.FILL_HORIZONTAL);
		slotData.horizontalSpan = 3;
		this.slot.setLayoutData(slotData);
	}

	/**
	 * 
	 */
	private void moveSlot(Slot element, int oldIndex, int newIndex) {

		EObject editedElement = slotEditUtil.foundCorrespondingEObject(element);
		slotEditUtil.moveElement(element, oldIndex, newIndex);
		slot.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				InstanceSpecificationPropertiesEditionPartImpl.this, UMLViewsRepository.InstanceSpecification.slot,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToSlot() {

		// Start of user code addToSlot() method body

		Slot eObject = UMLFactory.eINSTANCE.createSlot();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				slotEditUtil.addElement(propertiesEditionObject);
				slot.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						InstanceSpecificationPropertiesEditionPartImpl.this,
						UMLViewsRepository.InstanceSpecification.slot, PropertiesEditionEvent.CHANGE,
						PropertiesEditionEvent.ADD, null, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * 
	 */
	private void removeFromSlot(Slot element) {

		// Start of user code removeFromSlot() method body

		EObject editedElement = slotEditUtil.foundCorrespondingEObject(element);
		slotEditUtil.removeElement(element);
		slot.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				InstanceSpecificationPropertiesEditionPartImpl.this, UMLViewsRepository.InstanceSpecification.slot,
				PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editSlot(Slot element) {

		// Start of user code editSlot() method body

		EObject editedElement = slotEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				slotEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				slot.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						InstanceSpecificationPropertiesEditionPartImpl.this,
						UMLViewsRepository.InstanceSpecification.slot, PropertiesEditionEvent.CHANGE,
						PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createClassifierAdvancedReferencesTable(Composite parent) {
		this.classifier = new ReferencesTable<Classifier>(
				UMLMessages.InstanceSpecificationPropertiesEditionPart_ClassifierLabel,
				new ReferencesTableListener<Classifier>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<Classifier> dialog = new TabElementTreeSelectionDialog<Classifier>(
								resourceSet, classifierFilters, classifierBusinessFilters, "Classifier",
								UMLPackage.eINSTANCE.getClassifier()) {

							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!classifierEditUtil.getVirtualList().contains(elem))
										classifierEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											InstanceSpecificationPropertiesEditionPartImpl.this,
											UMLViewsRepository.InstanceSpecification.classifier,
											PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
								}
								classifier.refresh();
							}

						};
						dialog.open();
					}

					public void handleEdit(Classifier element) {
						editClassifier(element);
					}

					public void handleMove(Classifier element, int oldIndex, int newIndex) {
						moveClassifier(element, oldIndex, newIndex);
					}

					public void handleRemove(Classifier element) {
						removeFromClassifier(element);
					}

					public void navigateTo(Classifier element) {
					}
				});
		this.classifier.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.InstanceSpecification.classifier, UMLViewsRepository.SWT_KIND));
		this.classifier.createControls(parent);
		GridData classifierData = new GridData(GridData.FILL_HORIZONTAL);
		classifierData.horizontalSpan = 3;
		this.classifier.setLayoutData(classifierData);
		this.classifier.disableMove();
	}

	/**
	 * 
	 */
	private void moveClassifier(Classifier element, int oldIndex, int newIndex) {
	}

	/**
	 * 
	 */
	private void removeFromClassifier(Classifier element) {

		// Start of user code removeFromClassifier() method body

		EObject editedElement = classifierEditUtil.foundCorrespondingEObject(element);
		classifierEditUtil.removeElement(element);
		classifier.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				InstanceSpecificationPropertiesEditionPartImpl.this,
				UMLViewsRepository.InstanceSpecification.classifier, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editClassifier(Classifier element) {

		// Start of user code editClassifier() method body

		EObject editedElement = classifierEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				classifierEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				classifier.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						InstanceSpecificationPropertiesEditionPartImpl.this,
						UMLViewsRepository.InstanceSpecification.classifier, PropertiesEditionEvent.COMMIT,
						PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createDeploymentAdvancedTableComposition(Composite parent) {
		this.deployment = new ReferencesTable<Deployment>(
				UMLMessages.InstanceSpecificationPropertiesEditionPart_DeploymentLabel,
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
				UMLViewsRepository.InstanceSpecification.deployment, UMLViewsRepository.SWT_KIND));
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
				InstanceSpecificationPropertiesEditionPartImpl.this,
				UMLViewsRepository.InstanceSpecification.deployment, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

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
						InstanceSpecificationPropertiesEditionPartImpl.this,
						UMLViewsRepository.InstanceSpecification.deployment, PropertiesEditionEvent.CHANGE,
						PropertiesEditionEvent.ADD, null, propertiesEditionObject));
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
				InstanceSpecificationPropertiesEditionPartImpl.this,
				UMLViewsRepository.InstanceSpecification.deployment, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

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
						InstanceSpecificationPropertiesEditionPartImpl.this,
						UMLViewsRepository.InstanceSpecification.deployment, PropertiesEditionEvent.CHANGE,
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#getName()
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#setName(String
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#getVisibility()
	 */
	public Enumerator getVisibility() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) visibility.getSelection()).getFirstElement();
		return selection.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#initVisibility(EEnum
	 *      eenum, Enumerator current)
	 */
	public void initVisibility(EEnum eenum, Enumerator current) {
		visibility.setInput(eenum.getELiterals());
		visibility.modelUpdating(new StructuredSelection(current));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#setVisibility(Enumerator
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#getSlotToAdd()
	 */
	public List getSlotToAdd() {
		return slotEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#getSlotToRemove()
	 */
	public List getSlotToRemove() {
		return slotEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#getSlotToEdit()
	 */
	public Map getSlotToEdit() {
		return slotEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#getSlotToMove()
	 */
	public List getSlotToMove() {
		return slotEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#getSlotTable()
	 */
	public List getSlotTable() {
		return slotEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#initSlot(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initSlot(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			slotEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			slotEditUtil = new EMFListEditUtil(current, feature);
		this.slot.setInput(slotEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#updateSlot(EObject
	 *      newValue)
	 */
	public void updateSlot(EObject newValue) {
		if (slotEditUtil != null) {
			slotEditUtil.reinit(newValue);
			slot.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#addFilterSlot(ViewerFilter
	 *      filter)
	 */
	public void addFilterToSlot(ViewerFilter filter) {
		slotFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#addBusinessFilterSlot(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToSlot(ViewerFilter filter) {
		slotBusinessFilters.add(filter);
	}

	public void setMessageForSlot(String msg, int msgLevel) {

	}

	public void unsetMessageForSlot() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#getClassifierToAdd()
	 */
	public List getClassifierToAdd() {
		return classifierEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#getClassifierToRemove()
	 */
	public List getClassifierToRemove() {
		return classifierEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#getClassifierTable()
	 */
	public List getClassifierTable() {
		return classifierEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#initClassifier(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initClassifier(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			classifierEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			classifierEditUtil = new EMFListEditUtil(current, feature);
		this.classifier.setInput(classifierEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#updateClassifier(EObject
	 *      newValue)
	 */
	public void updateClassifier(EObject newValue) {
		if (classifierEditUtil != null) {
			classifierEditUtil.reinit(newValue);
			classifier.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#addFilterClassifier(ViewerFilter
	 *      filter)
	 */
	public void addFilterToClassifier(ViewerFilter filter) {
		classifierFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#addBusinessFilterClassifier(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToClassifier(ViewerFilter filter) {
		classifierBusinessFilters.add(filter);
	}

	public void setMessageForClassifier(String msg, int msgLevel) {

	}

	public void unsetMessageForClassifier() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#getDeploymentToAdd()
	 */
	public List getDeploymentToAdd() {
		return deploymentEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#getDeploymentToRemove()
	 */
	public List getDeploymentToRemove() {
		return deploymentEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#getDeploymentToEdit()
	 */
	public Map getDeploymentToEdit() {
		return deploymentEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#getDeploymentToMove()
	 */
	public List getDeploymentToMove() {
		return deploymentEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#getDeploymentTable()
	 */
	public List getDeploymentTable() {
		return deploymentEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#initDeployment(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#updateDeployment(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#addFilterDeployment(ViewerFilter
	 *      filter)
	 */
	public void addFilterToDeployment(ViewerFilter filter) {
		deploymentFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.InstanceSpecificationPropertiesEditionPart#addBusinessFilterDeployment(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToDeployment(ViewerFilter filter) {
		deploymentBusinessFilters.add(filter);
	}

	public void setMessageForDeployment(String msg, int msgLevel) {

	}

	public void unsetMessageForDeployment() {

	}

	// Start of user code additional methods

	// End of user code

}
