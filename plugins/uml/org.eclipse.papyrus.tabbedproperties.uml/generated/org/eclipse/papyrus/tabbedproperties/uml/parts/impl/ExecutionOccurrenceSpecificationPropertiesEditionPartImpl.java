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
import org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart;

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
import org.eclipse.uml2.uml.GeneralOrdering;
import org.eclipse.uml2.uml.GeneralOrdering;
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

import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;

// End of user code

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class ExecutionOccurrenceSpecificationPropertiesEditionPartImpl extends CompositePropertiesEditionPart implements
		ISWTPropertiesEditionPart, ExecutionOccurrenceSpecificationPropertiesEditionPart {

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

	protected EMFListEditUtil toBeforeEditUtil;

	protected ReferencesTable<?> toBefore;

	protected List<ViewerFilter> toBeforeBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> toBeforeFilters = new ArrayList<ViewerFilter>();

	protected EMFListEditUtil toAfterEditUtil;

	protected ReferencesTable<?> toAfter;

	protected List<ViewerFilter> toAfterBusinessFilters = new ArrayList<ViewerFilter>();

	protected List<ViewerFilter> toAfterFilters = new ArrayList<ViewerFilter>();

	public ExecutionOccurrenceSpecificationPropertiesEditionPartImpl(IPropertiesEditionComponent editionComponent) {
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
		propertiesGroup.setText(UMLMessages.ExecutionOccurrenceSpecificationPropertiesEditionPart_PropertiesGroupLabel);
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
		createToBeforeAdvancedReferencesTable(propertiesGroup);
		createToAfterAdvancedReferencesTable(propertiesGroup);
	}

	/**
	 * @param container
	 */
	protected void createOwnedCommentAdvancedTableComposition(Composite parent) {
		this.ownedComment = new ReferencesTable<Comment>(
				UMLMessages.ExecutionOccurrenceSpecificationPropertiesEditionPart_OwnedCommentLabel,
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
				UMLViewsRepository.ExecutionOccurrenceSpecification.ownedComment, UMLViewsRepository.SWT_KIND));
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
				ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
				UMLViewsRepository.ExecutionOccurrenceSpecification.ownedComment, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

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
						ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
						UMLViewsRepository.ExecutionOccurrenceSpecification.ownedComment,
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
				ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
				UMLViewsRepository.ExecutionOccurrenceSpecification.ownedComment, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

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
						ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
						UMLViewsRepository.ExecutionOccurrenceSpecification.ownedComment,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createNameText(Composite parent) {
		SWTUtils.createPartLabel(parent, UMLMessages.ExecutionOccurrenceSpecificationPropertiesEditionPart_NameLabel,
				propertiesEditionComponent.isRequired(UMLViewsRepository.ExecutionOccurrenceSpecification.name,
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
							ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
							UMLViewsRepository.ExecutionOccurrenceSpecification.name, PropertiesEditionEvent.CHANGE,
							PropertiesEditionEvent.SET, null, name.getText()));
			}

		});

		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExecutionOccurrenceSpecification.name, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createVisibilityEMFComboViewer(Composite parent) {
		SWTUtils.createPartLabel(parent,
				UMLMessages.ExecutionOccurrenceSpecificationPropertiesEditionPart_VisibilityLabel,
				propertiesEditionComponent.isRequired(UMLViewsRepository.ExecutionOccurrenceSpecification.visibility,
						UMLViewsRepository.SWT_KIND));
		visibility = new EMFComboViewer(parent);
		visibility.setContentProvider(new ArrayContentProvider());
		visibility.setLabelProvider(new AdapterFactoryLabelProvider(new EcoreAdapterFactory()));
		GridData visibilityData = new GridData(GridData.FILL_HORIZONTAL);
		visibility.getCombo().setLayoutData(visibilityData);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExecutionOccurrenceSpecification.visibility, UMLViewsRepository.SWT_KIND), null); //$NON-NLS-1$
	}

	protected void createClientDependencyAdvancedReferencesTable(Composite parent) {
		this.clientDependency = new ReferencesTable<Dependency>(
				UMLMessages.ExecutionOccurrenceSpecificationPropertiesEditionPart_ClientDependencyLabel,
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
											ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
											UMLViewsRepository.ExecutionOccurrenceSpecification.clientDependency,
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
				UMLViewsRepository.ExecutionOccurrenceSpecification.clientDependency, UMLViewsRepository.SWT_KIND));
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
				ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
				UMLViewsRepository.ExecutionOccurrenceSpecification.clientDependency, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));
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
				ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
				UMLViewsRepository.ExecutionOccurrenceSpecification.clientDependency, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.REMOVE, null, editedElement));

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
						ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
						UMLViewsRepository.ExecutionOccurrenceSpecification.clientDependency,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createCoveredAdvancedReferencesTable(Composite parent) {
		this.covered = new ReferencesTable<Lifeline>(
				UMLMessages.ExecutionOccurrenceSpecificationPropertiesEditionPart_CoveredLabel,
				new ReferencesTableListener<Lifeline>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<Lifeline> dialog = new TabElementTreeSelectionDialog<Lifeline>(
								resourceSet, coveredFilters, coveredBusinessFilters, "Lifeline", UMLPackage.eINSTANCE
										.getLifeline()) {

							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!coveredEditUtil.getVirtualList().contains(elem))
										coveredEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
											UMLViewsRepository.ExecutionOccurrenceSpecification.covered,
											PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
								}
								covered.refresh();
							}

						};
						dialog.open();
					}

					public void handleEdit(Lifeline element) {
						editCovered(element);
					}

					public void handleMove(Lifeline element, int oldIndex, int newIndex) {
						moveCovered(element, oldIndex, newIndex);
					}

					public void handleRemove(Lifeline element) {
						removeFromCovered(element);
					}

					public void navigateTo(Lifeline element) {
					}
				});
		this.covered.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExecutionOccurrenceSpecification.covered, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
				UMLViewsRepository.ExecutionOccurrenceSpecification.covered, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	private void removeFromCovered(Lifeline element) {

		// Start of user code removeFromCovered() method body

		EObject editedElement = coveredEditUtil.foundCorrespondingEObject(element);
		coveredEditUtil.removeElement(element);
		covered.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
				UMLViewsRepository.ExecutionOccurrenceSpecification.covered, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editCovered(Lifeline element) {

		// Start of user code editCovered() method body

		EObject editedElement = coveredEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				coveredEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				covered.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
						UMLViewsRepository.ExecutionOccurrenceSpecification.covered, PropertiesEditionEvent.COMMIT,
						PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code

	}

	/**
	 * @param container
	 */
	protected void createGeneralOrderingAdvancedTableComposition(Composite parent) {
		this.generalOrdering = new ReferencesTable<GeneralOrdering>(
				UMLMessages.ExecutionOccurrenceSpecificationPropertiesEditionPart_GeneralOrderingLabel,
				new ReferencesTableListener<GeneralOrdering>() {

					public void handleAdd() {
						addToGeneralOrdering();
					}

					public void handleEdit(GeneralOrdering element) {
						editGeneralOrdering(element);
					}

					public void handleMove(GeneralOrdering element, int oldIndex, int newIndex) {
						moveGeneralOrdering(element, oldIndex, newIndex);
					}

					public void handleRemove(GeneralOrdering element) {
						removeFromGeneralOrdering(element);
					}

					public void navigateTo(GeneralOrdering element) {
					}
				});
		this.generalOrdering.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExecutionOccurrenceSpecification.generalOrdering, UMLViewsRepository.SWT_KIND));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
				UMLViewsRepository.ExecutionOccurrenceSpecification.generalOrdering, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));

	}

	/**
	 * 
	 */
	protected void addToGeneralOrdering() {

		// Start of user code addToGeneralOrdering() method body

		GeneralOrdering eObject = UMLFactory.eINSTANCE.createGeneralOrdering();
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(eObject);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(eObject);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(propertiesEditionComponent,
							eObject, resourceSet));
			if (propertiesEditionObject != null) {
				generalOrderingEditUtil.addElement(propertiesEditionObject);
				generalOrdering.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
						UMLViewsRepository.ExecutionOccurrenceSpecification.generalOrdering,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.ADD, null, propertiesEditionObject));
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
				UMLViewsRepository.ExecutionOccurrenceSpecification.generalOrdering, PropertiesEditionEvent.CHANGE,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editGeneralOrdering(GeneralOrdering element) {

		// Start of user code editGeneralOrdering() method body

		EObject editedElement = generalOrderingEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				generalOrderingEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				generalOrdering.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
						UMLViewsRepository.ExecutionOccurrenceSpecification.generalOrdering,
						PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, editedElement,
						propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createToBeforeAdvancedReferencesTable(Composite parent) {
		this.toBefore = new ReferencesTable<GeneralOrdering>(
				UMLMessages.ExecutionOccurrenceSpecificationPropertiesEditionPart_ToBeforeLabel,
				new ReferencesTableListener<GeneralOrdering>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<GeneralOrdering> dialog = new TabElementTreeSelectionDialog<GeneralOrdering>(
								resourceSet, toBeforeFilters, toBeforeBusinessFilters, "GeneralOrdering",
								UMLPackage.eINSTANCE.getGeneralOrdering()) {

							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!toBeforeEditUtil.getVirtualList().contains(elem))
										toBeforeEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
											UMLViewsRepository.ExecutionOccurrenceSpecification.toBefore,
											PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
								}
								toBefore.refresh();
							}

						};
						dialog.open();
					}

					public void handleEdit(GeneralOrdering element) {
						editToBefore(element);
					}

					public void handleMove(GeneralOrdering element, int oldIndex, int newIndex) {
						moveToBefore(element, oldIndex, newIndex);
					}

					public void handleRemove(GeneralOrdering element) {
						removeFromToBefore(element);
					}

					public void navigateTo(GeneralOrdering element) {
					}
				});
		this.toBefore.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExecutionOccurrenceSpecification.toBefore, UMLViewsRepository.SWT_KIND));
		this.toBefore.createControls(parent);
		GridData toBeforeData = new GridData(GridData.FILL_HORIZONTAL);
		toBeforeData.horizontalSpan = 3;
		this.toBefore.setLayoutData(toBeforeData);
		this.toBefore.disableMove();
	}

	/**
	 * 
	 */
	private void moveToBefore(GeneralOrdering element, int oldIndex, int newIndex) {
		EObject editedElement = toBeforeEditUtil.foundCorrespondingEObject(element);
		toBeforeEditUtil.moveElement(element, oldIndex, newIndex);
		toBefore.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
				UMLViewsRepository.ExecutionOccurrenceSpecification.toBefore, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	private void removeFromToBefore(GeneralOrdering element) {

		// Start of user code removeFromToBefore() method body

		EObject editedElement = toBeforeEditUtil.foundCorrespondingEObject(element);
		toBeforeEditUtil.removeElement(element);
		toBefore.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
				UMLViewsRepository.ExecutionOccurrenceSpecification.toBefore, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editToBefore(GeneralOrdering element) {

		// Start of user code editToBefore() method body

		EObject editedElement = toBeforeEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				toBeforeEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				toBefore.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
						UMLViewsRepository.ExecutionOccurrenceSpecification.toBefore, PropertiesEditionEvent.COMMIT,
						PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}

		// End of user code

	}

	protected void createToAfterAdvancedReferencesTable(Composite parent) {
		this.toAfter = new ReferencesTable<GeneralOrdering>(
				UMLMessages.ExecutionOccurrenceSpecificationPropertiesEditionPart_ToAfterLabel,
				new ReferencesTableListener<GeneralOrdering>() {

					public void handleAdd() {
						TabElementTreeSelectionDialog<GeneralOrdering> dialog = new TabElementTreeSelectionDialog<GeneralOrdering>(
								resourceSet, toAfterFilters, toAfterBusinessFilters, "GeneralOrdering",
								UMLPackage.eINSTANCE.getGeneralOrdering()) {

							public void process(IStructuredSelection selection) {
								for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
									EObject elem = (EObject) iter.next();
									if (!toAfterEditUtil.getVirtualList().contains(elem))
										toAfterEditUtil.addElement(elem);
									propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
											ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
											UMLViewsRepository.ExecutionOccurrenceSpecification.toAfter,
											PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
								}
								toAfter.refresh();
							}

						};
						dialog.open();
					}

					public void handleEdit(GeneralOrdering element) {
						editToAfter(element);
					}

					public void handleMove(GeneralOrdering element, int oldIndex, int newIndex) {
						moveToAfter(element, oldIndex, newIndex);
					}

					public void handleRemove(GeneralOrdering element) {
						removeFromToAfter(element);
					}

					public void navigateTo(GeneralOrdering element) {
					}
				});
		this.toAfter.setHelpText(propertiesEditionComponent.getHelpContent(
				UMLViewsRepository.ExecutionOccurrenceSpecification.toAfter, UMLViewsRepository.SWT_KIND));
		this.toAfter.createControls(parent);
		GridData toAfterData = new GridData(GridData.FILL_HORIZONTAL);
		toAfterData.horizontalSpan = 3;
		this.toAfter.setLayoutData(toAfterData);
		this.toAfter.disableMove();
	}

	/**
	 * 
	 */
	private void moveToAfter(GeneralOrdering element, int oldIndex, int newIndex) {
		EObject editedElement = toAfterEditUtil.foundCorrespondingEObject(element);
		toAfterEditUtil.moveElement(element, oldIndex, newIndex);
		toAfter.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
				UMLViewsRepository.ExecutionOccurrenceSpecification.toAfter, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	private void removeFromToAfter(GeneralOrdering element) {

		// Start of user code removeFromToAfter() method body

		EObject editedElement = toAfterEditUtil.foundCorrespondingEObject(element);
		toAfterEditUtil.removeElement(element);
		toAfter.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
				UMLViewsRepository.ExecutionOccurrenceSpecification.toAfter, PropertiesEditionEvent.COMMIT,
				PropertiesEditionEvent.REMOVE, null, editedElement));

		// End of user code

	}

	/**
	 * 
	 */
	private void editToAfter(GeneralOrdering element) {

		// Start of user code editToAfter() method body

		EObject editedElement = toAfterEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				toAfterEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				toAfter.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						ExecutionOccurrenceSpecificationPropertiesEditionPartImpl.this,
						UMLViewsRepository.ExecutionOccurrenceSpecification.toAfter, PropertiesEditionEvent.COMMIT,
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getOwnedCommentToAdd()
	 */
	public List getOwnedCommentToAdd() {
		return ownedCommentEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getOwnedCommentToRemove()
	 */
	public List getOwnedCommentToRemove() {
		return ownedCommentEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getOwnedCommentToEdit()
	 */
	public Map getOwnedCommentToEdit() {
		return ownedCommentEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getOwnedCommentToMove()
	 */
	public List getOwnedCommentToMove() {
		return ownedCommentEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getOwnedCommentTable()
	 */
	public List getOwnedCommentTable() {
		return ownedCommentEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#initOwnedComment(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#updateOwnedComment(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#addFilterOwnedComment(ViewerFilter
	 *      filter)
	 */
	public void addFilterToOwnedComment(ViewerFilter filter) {
		ownedCommentFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#addBusinessFilterOwnedComment(ViewerFilter
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getName()
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#setName(String
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getVisibility()
	 */
	public Enumerator getVisibility() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) visibility.getSelection()).getFirstElement();
		return selection.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#initVisibility(EEnum
	 *      eenum, Enumerator current)
	 */
	public void initVisibility(EEnum eenum, Enumerator current) {
		visibility.setInput(eenum.getELiterals());
		visibility.modelUpdating(new StructuredSelection(current));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#setVisibility(Enumerator
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getClientDependencyToAdd()
	 */
	public List getClientDependencyToAdd() {
		return clientDependencyEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getClientDependencyToRemove()
	 */
	public List getClientDependencyToRemove() {
		return clientDependencyEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getClientDependencyTable()
	 */
	public List getClientDependencyTable() {
		return clientDependencyEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#initClientDependency(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#updateClientDependency(EObject
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#addFilterClientDependency(ViewerFilter
	 *      filter)
	 */
	public void addFilterToClientDependency(ViewerFilter filter) {
		clientDependencyFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#addBusinessFilterClientDependency(ViewerFilter
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getCoveredToAdd()
	 */
	public List getCoveredToAdd() {
		return coveredEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getCoveredToRemove()
	 */
	public List getCoveredToRemove() {
		return coveredEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getCoveredTable()
	 */
	public List getCoveredTable() {
		return coveredEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#initCovered(EObject
	 *      current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#updateCovered(EObject
	 *      newValue)
	 */
	public void updateCovered(EObject newValue) {
		if (coveredEditUtil != null) {
			coveredEditUtil.reinit(newValue);
			covered.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#addFilterCovered(ViewerFilter
	 *      filter)
	 */
	public void addFilterToCovered(ViewerFilter filter) {
		coveredFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#addBusinessFilterCovered(ViewerFilter
	 *      filter)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getGeneralOrderingToAdd()
	 */
	public List getGeneralOrderingToAdd() {
		return generalOrderingEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getGeneralOrderingToRemove()
	 */
	public List getGeneralOrderingToRemove() {
		return generalOrderingEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getGeneralOrderingToEdit()
	 */
	public Map getGeneralOrderingToEdit() {
		return generalOrderingEditUtil.getElementsToRefresh();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getGeneralOrderingToMove()
	 */
	public List getGeneralOrderingToMove() {
		return generalOrderingEditUtil.getElementsToMove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getGeneralOrderingTable()
	 */
	public List getGeneralOrderingTable() {
		return generalOrderingEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#initGeneralOrdering(EObject
	 *      current, EReference containingFeature, EReference feature)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#updateGeneralOrdering(EObject
	 *      newValue)
	 */
	public void updateGeneralOrdering(EObject newValue) {
		if (generalOrderingEditUtil != null) {
			generalOrderingEditUtil.reinit(newValue);
			generalOrdering.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#addFilterGeneralOrdering(ViewerFilter
	 *      filter)
	 */
	public void addFilterToGeneralOrdering(ViewerFilter filter) {
		generalOrderingFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#addBusinessFilterGeneralOrdering(ViewerFilter
	 *      filter)
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
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getToBeforeToAdd()
	 */
	public List getToBeforeToAdd() {
		return toBeforeEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getToBeforeToRemove()
	 */
	public List getToBeforeToRemove() {
		return toBeforeEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getToBeforeTable()
	 */
	public List getToBeforeTable() {
		return toBeforeEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#initToBefore(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initToBefore(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			toBeforeEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			toBeforeEditUtil = new EMFListEditUtil(current, feature);
		this.toBefore.setInput(toBeforeEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#updateToBefore(EObject
	 *      newValue)
	 */
	public void updateToBefore(EObject newValue) {
		if (toBeforeEditUtil != null) {
			toBeforeEditUtil.reinit(newValue);
			toBefore.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#addFilterToBefore(ViewerFilter
	 *      filter)
	 */
	public void addFilterToToBefore(ViewerFilter filter) {
		toBeforeFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#addBusinessFilterToBefore(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToToBefore(ViewerFilter filter) {
		toBeforeBusinessFilters.add(filter);
	}

	public void setMessageForToBefore(String msg, int msgLevel) {

	}

	public void unsetMessageForToBefore() {

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getToAfterToAdd()
	 */
	public List getToAfterToAdd() {
		return toAfterEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getToAfterToRemove()
	 */
	public List getToAfterToRemove() {
		return toAfterEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#getToAfterTable()
	 */
	public List getToAfterTable() {
		return toAfterEditUtil.getVirtualList();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#initToAfter(EObject
	 *      current, EReference containingFeature, EReference feature)
	 */
	public void initToAfter(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			toAfterEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			toAfterEditUtil = new EMFListEditUtil(current, feature);
		this.toAfter.setInput(toAfterEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#updateToAfter(EObject
	 *      newValue)
	 */
	public void updateToAfter(EObject newValue) {
		if (toAfterEditUtil != null) {
			toAfterEditUtil.reinit(newValue);
			toAfter.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#addFilterToAfter(ViewerFilter
	 *      filter)
	 */
	public void addFilterToToAfter(ViewerFilter filter) {
		toAfterFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.ExecutionOccurrenceSpecificationPropertiesEditionPart#addBusinessFilterToAfter(ViewerFilter
	 *      filter)
	 */
	public void addBusinessFilterToToAfter(ViewerFilter filter) {
		toAfterBusinessFilters.add(filter);
	}

	public void setMessageForToAfter(String msg, int msgLevel) {

	}

	public void unsetMessageForToAfter() {

	}

	// Start of user code additional methods

	// End of user code

}
