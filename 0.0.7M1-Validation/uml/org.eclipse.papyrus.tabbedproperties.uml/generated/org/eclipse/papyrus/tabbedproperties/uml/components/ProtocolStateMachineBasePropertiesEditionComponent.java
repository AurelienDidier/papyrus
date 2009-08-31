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
package org.eclipse.papyrus.tabbedproperties.uml.components;

// Start of user code for imports

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionListener;
import org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.api.providers.IPropertiesEditionPartProvider;
import org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.impl.filters.EObjectFilter;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.services.PropertiesContextService;
import org.eclipse.emf.eef.runtime.impl.services.PropertiesEditionPartProviderService;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.tabbedproperties.uml.parts.ProtocolStateMachinePropertiesEditionPart;
import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.CollaborationUse;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ParameterSet;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.ProtocolConformance;
import org.eclipse.uml2.uml.ProtocolStateMachine;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Reception;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.Substitution;
import org.eclipse.uml2.uml.TemplateBinding;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UseCase;
import org.eclipse.uml2.uml.VisibilityKind;

// End of user code

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class ProtocolStateMachineBasePropertiesEditionComponent extends StandardPropertiesEditionComponent {

	public static String BASE_PART = "Base"; //$NON-NLS-1$

	private String[] parts = { BASE_PART };

	/**
	 * The EObject to edit
	 */
	private ProtocolStateMachine protocolStateMachine;

	/**
	 * The Base part
	 */
	private ProtocolStateMachinePropertiesEditionPart basePart;

	/**
	 * Default constructor
	 */
	public ProtocolStateMachineBasePropertiesEditionComponent(EObject protocolStateMachine, String editing_mode) {
		if (protocolStateMachine instanceof ProtocolStateMachine) {
			this.protocolStateMachine = (ProtocolStateMachine) protocolStateMachine;
			if (IPropertiesEditionComponent.LIVE_MODE.equals(editing_mode)) {
				semanticAdapter = initializeSemanticAdapter();
				this.protocolStateMachine.eAdapters().add(semanticAdapter);
			}
		}
		this.editing_mode = editing_mode;
	}

	/**
	 * Initialize the semantic model listener for live editing mode
	 * 
	 * @return the semantic model listener
	 */
	private AdapterImpl initializeSemanticAdapter() {
		return new EContentAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
			 */
			public void notifyChanged(Notification msg) {
				if (basePart == null)
					ProtocolStateMachineBasePropertiesEditionComponent.this.dispose();
				else {
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getElement_OwnedComment() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getComment())) {
						basePart.updateOwnedComment(protocolStateMachine);
					}
					if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(msg.getFeature()) && basePart != null)
						basePart.setName((String) msg.getNewValue());

					if (UMLPackage.eINSTANCE.getNamedElement_Visibility().equals(msg.getFeature()) && basePart != null)
						basePart.setVisibility((Enumerator) msg.getNewValue());

					if (UMLPackage.eINSTANCE.getNamedElement_ClientDependency().equals(msg.getFeature()))
						basePart.updateClientDependency(protocolStateMachine);
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getNamespace_ElementImport() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getElementImport())) {
						basePart.updateElementImport(protocolStateMachine);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getNamespace_PackageImport() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getPackageImport())) {
						basePart.updatePackageImport(protocolStateMachine);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getNamespace_OwnedRule() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getConstraint())) {
						basePart.updateOwnedRule(protocolStateMachine);
					}
					if (UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf().equals(msg.getFeature())
							&& basePart != null)
						basePart.setIsLeaf((Boolean) msg.getNewValue());

					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getTemplateableElement_TemplateBinding() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getTemplateBinding())) {
						basePart.updateTemplateBinding(protocolStateMachine);
					}
					if (UMLPackage.eINSTANCE.getClassifier_IsAbstract().equals(msg.getFeature()) && basePart != null)
						basePart.setIsAbstract((Boolean) msg.getNewValue());

					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getClassifier_Generalization() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getGeneralization())) {
						basePart.updateGeneralization(protocolStateMachine);
					}
					if (UMLPackage.eINSTANCE.getClassifier_PowertypeExtent().equals(msg.getFeature()))
						basePart.updatePowertypeExtent(protocolStateMachine);
					if (UMLPackage.eINSTANCE.getClassifier_RedefinedClassifier().equals(msg.getFeature()))
						basePart.updateRedefinedClassifier(protocolStateMachine);
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getClassifier_Substitution() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getSubstitution())) {
						basePart.updateSubstitution(protocolStateMachine);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getClassifier_CollaborationUse() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getCollaborationUse())) {
						basePart.updateCollaborationUse(protocolStateMachine);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getClassifier_OwnedUseCase() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getUseCase())) {
						basePart.updateOwnedUseCase(protocolStateMachine);
					}
					if (UMLPackage.eINSTANCE.getClassifier_UseCase().equals(msg.getFeature()))
						basePart.updateUseCase(protocolStateMachine);
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getStructuredClassifier_OwnedAttribute() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getProperty())) {
						basePart.updateOwnedAttribute(protocolStateMachine);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getStructuredClassifier_OwnedConnector() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getConnector())) {
						basePart.updateOwnedConnector(protocolStateMachine);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getBehavioredClassifier_OwnedBehavior() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getBehavior())) {
						basePart.updateOwnedBehavior(protocolStateMachine);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getBehavioredClassifier_InterfaceRealization() || ((EStructuralFeature) msg
									.getFeature()).getEContainingClass() == UMLPackage.eINSTANCE
									.getInterfaceRealization())) {
						basePart.updateInterfaceRealization(protocolStateMachine);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getBehavioredClassifier_OwnedTrigger() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getTrigger())) {
						basePart.updateOwnedTrigger(protocolStateMachine);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getClass_NestedClassifier() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getClassifier())) {
						basePart.updateNestedClassifier(protocolStateMachine);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getClass_OwnedOperation() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getOperation())) {
						basePart.updateOwnedOperation(protocolStateMachine);
					}
					if (UMLPackage.eINSTANCE.getClass_IsActive().equals(msg.getFeature()) && basePart != null)
						basePart.setIsActive((Boolean) msg.getNewValue());

					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getClass_OwnedReception() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getReception())) {
						basePart.updateOwnedReception(protocolStateMachine);
					}
					if (UMLPackage.eINSTANCE.getBehavior_IsReentrant().equals(msg.getFeature()) && basePart != null)
						basePart.setIsReentrant((Boolean) msg.getNewValue());

					if (UMLPackage.eINSTANCE.getBehavior_RedefinedBehavior().equals(msg.getFeature()))
						basePart.updateRedefinedBehavior(protocolStateMachine);
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getBehavior_OwnedParameter() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getParameter())) {
						basePart.updateOwnedParameter(protocolStateMachine);
					}
					if (UMLPackage.eINSTANCE.getBehavior_Precondition().equals(msg.getFeature()))
						basePart.updatePrecondition(protocolStateMachine);
					if (UMLPackage.eINSTANCE.getBehavior_Postcondition().equals(msg.getFeature()))
						basePart.updatePostcondition(protocolStateMachine);
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getBehavior_OwnedParameterSet() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getParameterSet())) {
						basePart.updateOwnedParameterSet(protocolStateMachine);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getStateMachine_Region() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getRegion())) {
						basePart.updateRegion(protocolStateMachine);
					}
					if (UMLPackage.eINSTANCE.getStateMachine_SubmachineState().equals(msg.getFeature()))
						basePart.updateSubmachineState(protocolStateMachine);
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getStateMachine_ConnectionPoint() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getPseudostate())) {
						basePart.updateConnectionPoint(protocolStateMachine);
					}
					if (UMLPackage.eINSTANCE.getStateMachine_ExtendedStateMachine().equals(msg.getFeature()))
						basePart.updateExtendedStateMachine(protocolStateMachine);
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getProtocolStateMachine_Conformance() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getProtocolConformance())) {
						basePart.updateConformance(protocolStateMachine);
					}

				}
			}

		};
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#translatePart(java.lang.String)
	 */
	public java.lang.Class translatePart(String key) {
		if (BASE_PART.equals(key))
			return UMLViewsRepository.ProtocolStateMachine.class;
		return super.translatePart(key);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#partsList()
	 */
	public String[] partsList() {
		return parts;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#getPropertiesEditionPart
	 *      (java.lang.String, java.lang.String)
	 */
	public IPropertiesEditionPart getPropertiesEditionPart(int kind, String key) {
		if (protocolStateMachine != null && BASE_PART.equals(key)) {
			if (basePart == null) {
				IPropertiesEditionPartProvider provider = PropertiesEditionPartProviderService.getInstance()
						.getProvider(UMLViewsRepository.class);
				if (provider != null) {
					basePart = (ProtocolStateMachinePropertiesEditionPart) provider.getPropertiesEditionPart(
							UMLViewsRepository.ProtocolStateMachine.class, kind, this);
					addListener((IPropertiesEditionListener) basePart);
				}
			}
			return (IPropertiesEditionPart) basePart;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#
	 *      setPropertiesEditionPart(java.lang.Class, int,
	 *      org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart)
	 */
	public void setPropertiesEditionPart(java.lang.Class key, int kind, IPropertiesEditionPart propertiesEditionPart) {
		if (key == UMLViewsRepository.ProtocolStateMachine.class)
			this.basePart = (ProtocolStateMachinePropertiesEditionPart) propertiesEditionPart;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#initPart(java.lang.Class,
	 *      int, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.resource.ResourceSet)
	 */
	public void initPart(java.lang.Class key, int kind, EObject elt, ResourceSet allResource) {
		if (basePart != null && key == UMLViewsRepository.ProtocolStateMachine.class) {
			((IPropertiesEditionPart) basePart).setContext(elt, allResource);
			ProtocolStateMachine protocolStateMachine = (ProtocolStateMachine) elt;
			// init values
			basePart.initOwnedComment(protocolStateMachine, null, UMLPackage.eINSTANCE.getElement_OwnedComment());
			if (protocolStateMachine.getName() != null)
				basePart.setName(protocolStateMachine.getName());

			basePart.initVisibility((EEnum) UMLPackage.eINSTANCE.getNamedElement_Visibility().getEType(),
					protocolStateMachine.getVisibility());
			basePart.initClientDependency(protocolStateMachine, null, UMLPackage.eINSTANCE
					.getNamedElement_ClientDependency());
			basePart.initElementImport(protocolStateMachine, null, UMLPackage.eINSTANCE.getNamespace_ElementImport());
			basePart.initPackageImport(protocolStateMachine, null, UMLPackage.eINSTANCE.getNamespace_PackageImport());
			basePart.initOwnedRule(protocolStateMachine, null, UMLPackage.eINSTANCE.getNamespace_OwnedRule());
			basePart.setIsLeaf(protocolStateMachine.isLeaf());

			basePart.initTemplateBinding(protocolStateMachine, null, UMLPackage.eINSTANCE
					.getTemplateableElement_TemplateBinding());
			basePart.setIsAbstract(protocolStateMachine.isAbstract());

			basePart
					.initGeneralization(protocolStateMachine, null, UMLPackage.eINSTANCE.getClassifier_Generalization());
			basePart.initPowertypeExtent(protocolStateMachine, null, UMLPackage.eINSTANCE
					.getClassifier_PowertypeExtent());
			basePart.initRedefinedClassifier(protocolStateMachine, null, UMLPackage.eINSTANCE
					.getClassifier_RedefinedClassifier());
			basePart.initSubstitution(protocolStateMachine, null, UMLPackage.eINSTANCE.getClassifier_Substitution());
			basePart.initCollaborationUse(protocolStateMachine, null, UMLPackage.eINSTANCE
					.getClassifier_CollaborationUse());
			basePart.initOwnedUseCase(protocolStateMachine, null, UMLPackage.eINSTANCE.getClassifier_OwnedUseCase());
			basePart.initUseCase(protocolStateMachine, null, UMLPackage.eINSTANCE.getClassifier_UseCase());
			basePart.initOwnedAttribute(protocolStateMachine, null, UMLPackage.eINSTANCE
					.getStructuredClassifier_OwnedAttribute());
			basePart.initOwnedConnector(protocolStateMachine, null, UMLPackage.eINSTANCE
					.getStructuredClassifier_OwnedConnector());
			basePart.initOwnedBehavior(protocolStateMachine, null, UMLPackage.eINSTANCE
					.getBehavioredClassifier_OwnedBehavior());
			basePart.initInterfaceRealization(protocolStateMachine, null, UMLPackage.eINSTANCE
					.getBehavioredClassifier_InterfaceRealization());
			basePart.initOwnedTrigger(protocolStateMachine, null, UMLPackage.eINSTANCE
					.getBehavioredClassifier_OwnedTrigger());
			basePart.initNestedClassifier(protocolStateMachine, null, UMLPackage.eINSTANCE.getClass_NestedClassifier());
			basePart.initOwnedOperation(protocolStateMachine, null, UMLPackage.eINSTANCE.getClass_OwnedOperation());
			basePart.setIsActive(protocolStateMachine.isActive());

			basePart.initOwnedReception(protocolStateMachine, null, UMLPackage.eINSTANCE.getClass_OwnedReception());
			basePart.setIsReentrant(protocolStateMachine.isReentrant());

			basePart.initRedefinedBehavior(protocolStateMachine, null, UMLPackage.eINSTANCE
					.getBehavior_RedefinedBehavior());
			basePart.initOwnedParameter(protocolStateMachine, null, UMLPackage.eINSTANCE.getBehavior_OwnedParameter());
			basePart.initPrecondition(protocolStateMachine, null, UMLPackage.eINSTANCE.getBehavior_Precondition());
			basePart.initPostcondition(protocolStateMachine, null, UMLPackage.eINSTANCE.getBehavior_Postcondition());
			basePart.initOwnedParameterSet(protocolStateMachine, null, UMLPackage.eINSTANCE
					.getBehavior_OwnedParameterSet());
			basePart.initRegion(protocolStateMachine, null, UMLPackage.eINSTANCE.getStateMachine_Region());
			basePart.initSubmachineState(protocolStateMachine, null, UMLPackage.eINSTANCE
					.getStateMachine_SubmachineState());
			basePart.initConnectionPoint(protocolStateMachine, null, UMLPackage.eINSTANCE
					.getStateMachine_ConnectionPoint());
			basePart.initExtendedStateMachine(protocolStateMachine, null, UMLPackage.eINSTANCE
					.getStateMachine_ExtendedStateMachine());
			basePart.initConformance(protocolStateMachine, null, UMLPackage.eINSTANCE
					.getProtocolStateMachine_Conformance());

			// init filters
			basePart.addFilterToOwnedComment(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof Comment); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for ownedComment

			// End of user code

			basePart.addFilterToClientDependency(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof EObject)
						return (!basePart.getClientDependencyTable().contains(element));
					return false;
				}

			});
			basePart.addFilterToClientDependency(new EObjectFilter(UMLPackage.eINSTANCE.getDependency()));
			// Start of user code for additional businessfilters for clientDependency

			// End of user code
			basePart.addFilterToElementImport(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof ElementImport); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for elementImport

			// End of user code
			basePart.addFilterToPackageImport(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof PackageImport); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for packageImport

			// End of user code
			basePart.addFilterToOwnedRule(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof Constraint); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for ownedRule

			// End of user code

			basePart.addFilterToTemplateBinding(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof TemplateBinding); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for templateBinding

			// End of user code

			basePart.addFilterToGeneralization(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof Generalization); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for generalization

			// End of user code
			basePart.addFilterToPowertypeExtent(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof EObject)
						return (!basePart.getPowertypeExtentTable().contains(element));
					return false;
				}

			});
			basePart.addFilterToPowertypeExtent(new EObjectFilter(UMLPackage.eINSTANCE.getGeneralizationSet()));
			// Start of user code for additional businessfilters for powertypeExtent

			// End of user code
			basePart.addFilterToRedefinedClassifier(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof EObject)
						return (!basePart.getRedefinedClassifierTable().contains(element));
					return false;
				}

			});
			basePart.addFilterToRedefinedClassifier(new EObjectFilter(UMLPackage.eINSTANCE.getClassifier()));
			// Start of user code for additional businessfilters for redefinedClassifier

			// End of user code
			basePart.addFilterToSubstitution(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof Substitution); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for substitution

			// End of user code
			basePart.addFilterToCollaborationUse(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof CollaborationUse); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for collaborationUse

			// End of user code
			basePart.addFilterToOwnedUseCase(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof UseCase); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for ownedUseCase

			// End of user code
			basePart.addFilterToUseCase(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof EObject)
						return (!basePart.getUseCaseTable().contains(element));
					return false;
				}

			});
			basePart.addFilterToUseCase(new EObjectFilter(UMLPackage.eINSTANCE.getUseCase()));
			// Start of user code for additional businessfilters for useCase

			// End of user code
			basePart.addFilterToOwnedAttribute(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof Property); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for ownedAttribute

			// End of user code
			basePart.addFilterToOwnedConnector(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof Connector); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for ownedConnector

			// End of user code
			basePart.addFilterToOwnedBehavior(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof Behavior); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for ownedBehavior

			// End of user code
			basePart.addFilterToInterfaceRealization(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof InterfaceRealization); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for interfaceRealization

			// End of user code
			basePart.addFilterToOwnedTrigger(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof Trigger); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for ownedTrigger

			// End of user code
			basePart.addFilterToNestedClassifier(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof Classifier); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for nestedClassifier

			// End of user code
			basePart.addFilterToOwnedOperation(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof Operation); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for ownedOperation

			// End of user code

			basePart.addFilterToOwnedReception(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof Reception); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for ownedReception

			// End of user code

			basePart.addFilterToRedefinedBehavior(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof EObject)
						return (!basePart.getRedefinedBehaviorTable().contains(element));
					return false;
				}

			});
			basePart.addFilterToRedefinedBehavior(new EObjectFilter(UMLPackage.eINSTANCE.getBehavior()));
			// Start of user code for additional businessfilters for redefinedBehavior

			// End of user code
			basePart.addFilterToOwnedParameter(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof Parameter); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for ownedParameter

			// End of user code
			basePart.addFilterToPrecondition(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof EObject)
						return (!basePart.getPreconditionTable().contains(element));
					return false;
				}

			});
			basePart.addFilterToPrecondition(new EObjectFilter(UMLPackage.eINSTANCE.getConstraint()));
			// Start of user code for additional businessfilters for precondition

			// End of user code
			basePart.addFilterToPostcondition(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof EObject)
						return (!basePart.getPostconditionTable().contains(element));
					return false;
				}

			});
			basePart.addFilterToPostcondition(new EObjectFilter(UMLPackage.eINSTANCE.getConstraint()));
			// Start of user code for additional businessfilters for postcondition

			// End of user code
			basePart.addFilterToOwnedParameterSet(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof ParameterSet); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for ownedParameterSet

			// End of user code
			basePart.addFilterToRegion(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof Region);

				}

			});
			// Start of user code for additional businessfilters for region

			// End of user code
			basePart.addFilterToSubmachineState(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof EObject)
						return (!basePart.getSubmachineStateTable().contains(element));
					return false;
				}

			});
			basePart.addFilterToSubmachineState(new EObjectFilter(UMLPackage.eINSTANCE.getState()));
			// Start of user code for additional businessfilters for submachineState

			// End of user code
			basePart.addFilterToConnectionPoint(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof Pseudostate); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for connectionPoint

			// End of user code
			basePart.addFilterToExtendedStateMachine(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					if (element instanceof EObject)
						return (!basePart.getExtendedStateMachineTable().contains(element));
					return false;
				}

			});
			basePart.addFilterToExtendedStateMachine(new EObjectFilter(UMLPackage.eINSTANCE.getStateMachine()));
			// Start of user code for additional businessfilters for extendedStateMachine

			// End of user code
			basePart.addFilterToConformance(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof ProtocolConformance); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for conformance

			// End of user code
		}
		// init values for referenced views

		// init filters for referenced views

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#getPropertiesEditionCommand
	 *      (org.eclipse.emf.edit.domain.EditingDomain)
	 */
	public CompoundCommand getPropertiesEditionCommand(EditingDomain editingDomain) {
		CompoundCommand cc = new CompoundCommand();
		if (protocolStateMachine != null) {
			List ownedCommentToAddFromOwnedComment = basePart.getOwnedCommentToAdd();
			for (Iterator iter = ownedCommentToAddFromOwnedComment.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getElement_OwnedComment(), iter.next()));
			Map ownedCommentToRefreshFromOwnedComment = basePart.getOwnedCommentToEdit();
			for (Iterator iter = ownedCommentToRefreshFromOwnedComment.keySet().iterator(); iter.hasNext();) {

				// Start of user code for ownedComment reference refreshment from ownedComment

				Comment nextElement = (Comment) iter.next();
				Comment ownedComment = (Comment) ownedCommentToRefreshFromOwnedComment.get(nextElement);

				// End of user code

			}
			List ownedCommentToRemoveFromOwnedComment = basePart.getOwnedCommentToRemove();
			for (Iterator iter = ownedCommentToRemoveFromOwnedComment.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List ownedCommentToMoveFromOwnedComment = basePart.getOwnedCommentToMove();
			for (Iterator iter = ownedCommentToMoveFromOwnedComment.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE.getComment(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
					.getNamedElement_Name(), basePart.getName()));

			cc.append(SetCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
					.getNamedElement_Visibility(), basePart.getVisibility()));

			List clientDependencyToAddFromClientDependency = basePart.getClientDependencyToAdd();
			for (Iterator iter = clientDependencyToAddFromClientDependency.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getNamedElement_ClientDependency(), iter.next()));
			List clientDependencyToRemoveFromClientDependency = basePart.getClientDependencyToRemove();
			for (Iterator iter = clientDependencyToRemoveFromClientDependency.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getNamedElement_ClientDependency(), iter.next()));
			// List clientDependencyToMoveFromClientDependency =
			// basePart.getClientDependencyToMove();
			// for (Iterator iter = clientDependencyToMoveFromClientDependency.iterator();
			// iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, protocolStateMachine,
			// UMLPackage.eINSTANCE.getDependency(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List elementImportToAddFromElementImport = basePart.getElementImportToAdd();
			for (Iterator iter = elementImportToAddFromElementImport.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getNamespace_ElementImport(), iter.next()));
			Map elementImportToRefreshFromElementImport = basePart.getElementImportToEdit();
			for (Iterator iter = elementImportToRefreshFromElementImport.keySet().iterator(); iter.hasNext();) {

				// Start of user code for elementImport reference refreshment from elementImport

				ElementImport nextElement = (ElementImport) iter.next();
				ElementImport elementImport = (ElementImport) elementImportToRefreshFromElementImport.get(nextElement);

				// End of user code

			}
			List elementImportToRemoveFromElementImport = basePart.getElementImportToRemove();
			for (Iterator iter = elementImportToRemoveFromElementImport.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List elementImportToMoveFromElementImport = basePart.getElementImportToMove();
			for (Iterator iter = elementImportToMoveFromElementImport.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getElementImport(), moveElement.getElement(), moveElement.getIndex()));
			}
			List packageImportToAddFromPackageImport = basePart.getPackageImportToAdd();
			for (Iterator iter = packageImportToAddFromPackageImport.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getNamespace_PackageImport(), iter.next()));
			Map packageImportToRefreshFromPackageImport = basePart.getPackageImportToEdit();
			for (Iterator iter = packageImportToRefreshFromPackageImport.keySet().iterator(); iter.hasNext();) {

				// Start of user code for packageImport reference refreshment from packageImport

				PackageImport nextElement = (PackageImport) iter.next();
				PackageImport packageImport = (PackageImport) packageImportToRefreshFromPackageImport.get(nextElement);

				// End of user code

			}
			List packageImportToRemoveFromPackageImport = basePart.getPackageImportToRemove();
			for (Iterator iter = packageImportToRemoveFromPackageImport.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List packageImportToMoveFromPackageImport = basePart.getPackageImportToMove();
			for (Iterator iter = packageImportToMoveFromPackageImport.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getPackageImport(), moveElement.getElement(), moveElement.getIndex()));
			}
			List ownedRuleToAddFromOwnedRule = basePart.getOwnedRuleToAdd();
			for (Iterator iter = ownedRuleToAddFromOwnedRule.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getNamespace_OwnedRule(), iter.next()));
			Map ownedRuleToRefreshFromOwnedRule = basePart.getOwnedRuleToEdit();
			for (Iterator iter = ownedRuleToRefreshFromOwnedRule.keySet().iterator(); iter.hasNext();) {

				// Start of user code for ownedRule reference refreshment from ownedRule

				Constraint nextElement = (Constraint) iter.next();
				Constraint ownedRule = (Constraint) ownedRuleToRefreshFromOwnedRule.get(nextElement);

				// End of user code

			}
			List ownedRuleToRemoveFromOwnedRule = basePart.getOwnedRuleToRemove();
			for (Iterator iter = ownedRuleToRemoveFromOwnedRule.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List ownedRuleToMoveFromOwnedRule = basePart.getOwnedRuleToMove();
			for (Iterator iter = ownedRuleToMoveFromOwnedRule.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE.getConstraint(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
					.getRedefinableElement_IsLeaf(), basePart.getIsLeaf()));

			List templateBindingToAddFromTemplateBinding = basePart.getTemplateBindingToAdd();
			for (Iterator iter = templateBindingToAddFromTemplateBinding.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getTemplateableElement_TemplateBinding(), iter.next()));
			Map templateBindingToRefreshFromTemplateBinding = basePart.getTemplateBindingToEdit();
			for (Iterator iter = templateBindingToRefreshFromTemplateBinding.keySet().iterator(); iter.hasNext();) {

				// Start of user code for templateBinding reference refreshment from templateBinding

				TemplateBinding nextElement = (TemplateBinding) iter.next();
				TemplateBinding templateBinding = (TemplateBinding) templateBindingToRefreshFromTemplateBinding
						.get(nextElement);

				// End of user code

			}
			List templateBindingToRemoveFromTemplateBinding = basePart.getTemplateBindingToRemove();
			for (Iterator iter = templateBindingToRemoveFromTemplateBinding.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List templateBindingToMoveFromTemplateBinding = basePart.getTemplateBindingToMove();
			for (Iterator iter = templateBindingToMoveFromTemplateBinding.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getTemplateBinding(), moveElement.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
					.getClassifier_IsAbstract(), basePart.getIsAbstract()));

			List generalizationToAddFromGeneralization = basePart.getGeneralizationToAdd();
			for (Iterator iter = generalizationToAddFromGeneralization.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getClassifier_Generalization(), iter.next()));
			Map generalizationToRefreshFromGeneralization = basePart.getGeneralizationToEdit();
			for (Iterator iter = generalizationToRefreshFromGeneralization.keySet().iterator(); iter.hasNext();) {

				// Start of user code for generalization reference refreshment from generalization

				Generalization nextElement = (Generalization) iter.next();
				Generalization generalization = (Generalization) generalizationToRefreshFromGeneralization
						.get(nextElement);

				// End of user code

			}
			List generalizationToRemoveFromGeneralization = basePart.getGeneralizationToRemove();
			for (Iterator iter = generalizationToRemoveFromGeneralization.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List generalizationToMoveFromGeneralization = basePart.getGeneralizationToMove();
			for (Iterator iter = generalizationToMoveFromGeneralization.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getGeneralization(), moveElement.getElement(), moveElement.getIndex()));
			}
			List powertypeExtentToAddFromPowertypeExtent = basePart.getPowertypeExtentToAdd();
			for (Iterator iter = powertypeExtentToAddFromPowertypeExtent.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getClassifier_PowertypeExtent(), iter.next()));
			List powertypeExtentToRemoveFromPowertypeExtent = basePart.getPowertypeExtentToRemove();
			for (Iterator iter = powertypeExtentToRemoveFromPowertypeExtent.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getClassifier_PowertypeExtent(), iter.next()));
			// List powertypeExtentToMoveFromPowertypeExtent = basePart.getPowertypeExtentToMove();
			// for (Iterator iter = powertypeExtentToMoveFromPowertypeExtent.iterator();
			// iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, protocolStateMachine,
			// UMLPackage.eINSTANCE.getGeneralizationSet(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List redefinedClassifierToAddFromRedefinedClassifier = basePart.getRedefinedClassifierToAdd();
			for (Iterator iter = redefinedClassifierToAddFromRedefinedClassifier.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getClassifier_RedefinedClassifier(), iter.next()));
			List redefinedClassifierToRemoveFromRedefinedClassifier = basePart.getRedefinedClassifierToRemove();
			for (Iterator iter = redefinedClassifierToRemoveFromRedefinedClassifier.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getClassifier_RedefinedClassifier(), iter.next()));
			// List redefinedClassifierToMoveFromRedefinedClassifier =
			// basePart.getRedefinedClassifierToMove();
			// for (Iterator iter = redefinedClassifierToMoveFromRedefinedClassifier.iterator();
			// iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, protocolStateMachine,
			// UMLPackage.eINSTANCE.getClassifier(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List substitutionToAddFromSubstitution = basePart.getSubstitutionToAdd();
			for (Iterator iter = substitutionToAddFromSubstitution.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getClassifier_Substitution(), iter.next()));
			Map substitutionToRefreshFromSubstitution = basePart.getSubstitutionToEdit();
			for (Iterator iter = substitutionToRefreshFromSubstitution.keySet().iterator(); iter.hasNext();) {

				// Start of user code for substitution reference refreshment from substitution

				Substitution nextElement = (Substitution) iter.next();
				Substitution substitution = (Substitution) substitutionToRefreshFromSubstitution.get(nextElement);

				// End of user code

			}
			List substitutionToRemoveFromSubstitution = basePart.getSubstitutionToRemove();
			for (Iterator iter = substitutionToRemoveFromSubstitution.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List substitutionToMoveFromSubstitution = basePart.getSubstitutionToMove();
			for (Iterator iter = substitutionToMoveFromSubstitution.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getSubstitution(), moveElement.getElement(), moveElement.getIndex()));
			}
			List collaborationUseToAddFromCollaborationUse = basePart.getCollaborationUseToAdd();
			for (Iterator iter = collaborationUseToAddFromCollaborationUse.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getClassifier_CollaborationUse(), iter.next()));
			Map collaborationUseToRefreshFromCollaborationUse = basePart.getCollaborationUseToEdit();
			for (Iterator iter = collaborationUseToRefreshFromCollaborationUse.keySet().iterator(); iter.hasNext();) {

				// Start of user code for collaborationUse reference refreshment from
				// collaborationUse

				CollaborationUse nextElement = (CollaborationUse) iter.next();
				CollaborationUse collaborationUse = (CollaborationUse) collaborationUseToRefreshFromCollaborationUse
						.get(nextElement);

				// End of user code

			}
			List collaborationUseToRemoveFromCollaborationUse = basePart.getCollaborationUseToRemove();
			for (Iterator iter = collaborationUseToRemoveFromCollaborationUse.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List collaborationUseToMoveFromCollaborationUse = basePart.getCollaborationUseToMove();
			for (Iterator iter = collaborationUseToMoveFromCollaborationUse.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getCollaborationUse(), moveElement.getElement(), moveElement.getIndex()));
			}
			List ownedUseCaseToAddFromOwnedUseCase = basePart.getOwnedUseCaseToAdd();
			for (Iterator iter = ownedUseCaseToAddFromOwnedUseCase.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getClassifier_OwnedUseCase(), iter.next()));
			Map ownedUseCaseToRefreshFromOwnedUseCase = basePart.getOwnedUseCaseToEdit();
			for (Iterator iter = ownedUseCaseToRefreshFromOwnedUseCase.keySet().iterator(); iter.hasNext();) {

				// Start of user code for ownedUseCase reference refreshment from ownedUseCase

				UseCase nextElement = (UseCase) iter.next();
				UseCase ownedUseCase = (UseCase) ownedUseCaseToRefreshFromOwnedUseCase.get(nextElement);

				// End of user code

			}
			List ownedUseCaseToRemoveFromOwnedUseCase = basePart.getOwnedUseCaseToRemove();
			for (Iterator iter = ownedUseCaseToRemoveFromOwnedUseCase.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List ownedUseCaseToMoveFromOwnedUseCase = basePart.getOwnedUseCaseToMove();
			for (Iterator iter = ownedUseCaseToMoveFromOwnedUseCase.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE.getUseCase(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List useCaseToAddFromUseCase = basePart.getUseCaseToAdd();
			for (Iterator iter = useCaseToAddFromUseCase.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getClassifier_UseCase(), iter.next()));
			List useCaseToRemoveFromUseCase = basePart.getUseCaseToRemove();
			for (Iterator iter = useCaseToRemoveFromUseCase.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getClassifier_UseCase(), iter.next()));
			// List useCaseToMoveFromUseCase = basePart.getUseCaseToMove();
			// for (Iterator iter = useCaseToMoveFromUseCase.iterator(); iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, protocolStateMachine,
			// UMLPackage.eINSTANCE.getUseCase(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List ownedAttributeToAddFromOwnedAttribute = basePart.getOwnedAttributeToAdd();
			for (Iterator iter = ownedAttributeToAddFromOwnedAttribute.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getStructuredClassifier_OwnedAttribute(), iter.next()));
			Map ownedAttributeToRefreshFromOwnedAttribute = basePart.getOwnedAttributeToEdit();
			for (Iterator iter = ownedAttributeToRefreshFromOwnedAttribute.keySet().iterator(); iter.hasNext();) {

				// Start of user code for ownedAttribute reference refreshment from ownedAttribute

				Property nextElement = (Property) iter.next();
				Property ownedAttribute = (Property) ownedAttributeToRefreshFromOwnedAttribute.get(nextElement);

				// End of user code

			}
			List ownedAttributeToRemoveFromOwnedAttribute = basePart.getOwnedAttributeToRemove();
			for (Iterator iter = ownedAttributeToRemoveFromOwnedAttribute.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List ownedAttributeToMoveFromOwnedAttribute = basePart.getOwnedAttributeToMove();
			for (Iterator iter = ownedAttributeToMoveFromOwnedAttribute.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE.getProperty(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List ownedConnectorToAddFromOwnedConnector = basePart.getOwnedConnectorToAdd();
			for (Iterator iter = ownedConnectorToAddFromOwnedConnector.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getStructuredClassifier_OwnedConnector(), iter.next()));
			Map ownedConnectorToRefreshFromOwnedConnector = basePart.getOwnedConnectorToEdit();
			for (Iterator iter = ownedConnectorToRefreshFromOwnedConnector.keySet().iterator(); iter.hasNext();) {

				// Start of user code for ownedConnector reference refreshment from ownedConnector

				Connector nextElement = (Connector) iter.next();
				Connector ownedConnector = (Connector) ownedConnectorToRefreshFromOwnedConnector.get(nextElement);

				// End of user code

			}
			List ownedConnectorToRemoveFromOwnedConnector = basePart.getOwnedConnectorToRemove();
			for (Iterator iter = ownedConnectorToRemoveFromOwnedConnector.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List ownedConnectorToMoveFromOwnedConnector = basePart.getOwnedConnectorToMove();
			for (Iterator iter = ownedConnectorToMoveFromOwnedConnector.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE.getConnector(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List ownedBehaviorToAddFromOwnedBehavior = basePart.getOwnedBehaviorToAdd();
			for (Iterator iter = ownedBehaviorToAddFromOwnedBehavior.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getBehavioredClassifier_OwnedBehavior(), iter.next()));
			Map ownedBehaviorToRefreshFromOwnedBehavior = basePart.getOwnedBehaviorToEdit();
			for (Iterator iter = ownedBehaviorToRefreshFromOwnedBehavior.keySet().iterator(); iter.hasNext();) {

				// Start of user code for ownedBehavior reference refreshment from ownedBehavior

				Behavior nextElement = (Behavior) iter.next();
				Behavior ownedBehavior = (Behavior) ownedBehaviorToRefreshFromOwnedBehavior.get(nextElement);

				// End of user code

			}
			List ownedBehaviorToRemoveFromOwnedBehavior = basePart.getOwnedBehaviorToRemove();
			for (Iterator iter = ownedBehaviorToRemoveFromOwnedBehavior.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List ownedBehaviorToMoveFromOwnedBehavior = basePart.getOwnedBehaviorToMove();
			for (Iterator iter = ownedBehaviorToMoveFromOwnedBehavior.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE.getBehavior(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List interfaceRealizationToAddFromInterfaceRealization = basePart.getInterfaceRealizationToAdd();
			for (Iterator iter = interfaceRealizationToAddFromInterfaceRealization.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getBehavioredClassifier_InterfaceRealization(), iter.next()));
			Map interfaceRealizationToRefreshFromInterfaceRealization = basePart.getInterfaceRealizationToEdit();
			for (Iterator iter = interfaceRealizationToRefreshFromInterfaceRealization.keySet().iterator(); iter
					.hasNext();) {

				// Start of user code for interfaceRealization reference refreshment from
				// interfaceRealization

				InterfaceRealization nextElement = (InterfaceRealization) iter.next();
				InterfaceRealization interfaceRealization = (InterfaceRealization) interfaceRealizationToRefreshFromInterfaceRealization
						.get(nextElement);

				// End of user code

			}
			List interfaceRealizationToRemoveFromInterfaceRealization = basePart.getInterfaceRealizationToRemove();
			for (Iterator iter = interfaceRealizationToRemoveFromInterfaceRealization.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List interfaceRealizationToMoveFromInterfaceRealization = basePart.getInterfaceRealizationToMove();
			for (Iterator iter = interfaceRealizationToMoveFromInterfaceRealization.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getInterfaceRealization(), moveElement.getElement(), moveElement.getIndex()));
			}
			List ownedTriggerToAddFromOwnedTrigger = basePart.getOwnedTriggerToAdd();
			for (Iterator iter = ownedTriggerToAddFromOwnedTrigger.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getBehavioredClassifier_OwnedTrigger(), iter.next()));
			Map ownedTriggerToRefreshFromOwnedTrigger = basePart.getOwnedTriggerToEdit();
			for (Iterator iter = ownedTriggerToRefreshFromOwnedTrigger.keySet().iterator(); iter.hasNext();) {

				// Start of user code for ownedTrigger reference refreshment from ownedTrigger

				Trigger nextElement = (Trigger) iter.next();
				Trigger ownedTrigger = (Trigger) ownedTriggerToRefreshFromOwnedTrigger.get(nextElement);

				// End of user code

			}
			List ownedTriggerToRemoveFromOwnedTrigger = basePart.getOwnedTriggerToRemove();
			for (Iterator iter = ownedTriggerToRemoveFromOwnedTrigger.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List ownedTriggerToMoveFromOwnedTrigger = basePart.getOwnedTriggerToMove();
			for (Iterator iter = ownedTriggerToMoveFromOwnedTrigger.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE.getTrigger(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List nestedClassifierToAddFromNestedClassifier = basePart.getNestedClassifierToAdd();
			for (Iterator iter = nestedClassifierToAddFromNestedClassifier.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getClass_NestedClassifier(), iter.next()));
			Map nestedClassifierToRefreshFromNestedClassifier = basePart.getNestedClassifierToEdit();
			for (Iterator iter = nestedClassifierToRefreshFromNestedClassifier.keySet().iterator(); iter.hasNext();) {

				// Start of user code for nestedClassifier reference refreshment from
				// nestedClassifier

				Classifier nextElement = (Classifier) iter.next();
				Classifier nestedClassifier = (Classifier) nestedClassifierToRefreshFromNestedClassifier
						.get(nextElement);

				// End of user code

			}
			List nestedClassifierToRemoveFromNestedClassifier = basePart.getNestedClassifierToRemove();
			for (Iterator iter = nestedClassifierToRemoveFromNestedClassifier.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List nestedClassifierToMoveFromNestedClassifier = basePart.getNestedClassifierToMove();
			for (Iterator iter = nestedClassifierToMoveFromNestedClassifier.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE.getClassifier(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List ownedOperationToAddFromOwnedOperation = basePart.getOwnedOperationToAdd();
			for (Iterator iter = ownedOperationToAddFromOwnedOperation.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getClass_OwnedOperation(), iter.next()));
			Map ownedOperationToRefreshFromOwnedOperation = basePart.getOwnedOperationToEdit();
			for (Iterator iter = ownedOperationToRefreshFromOwnedOperation.keySet().iterator(); iter.hasNext();) {

				// Start of user code for ownedOperation reference refreshment from ownedOperation

				Operation nextElement = (Operation) iter.next();
				Operation ownedOperation = (Operation) ownedOperationToRefreshFromOwnedOperation.get(nextElement);

				// End of user code

			}
			List ownedOperationToRemoveFromOwnedOperation = basePart.getOwnedOperationToRemove();
			for (Iterator iter = ownedOperationToRemoveFromOwnedOperation.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List ownedOperationToMoveFromOwnedOperation = basePart.getOwnedOperationToMove();
			for (Iterator iter = ownedOperationToMoveFromOwnedOperation.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE.getOperation(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE.getClass_IsActive(),
					basePart.getIsActive()));

			List ownedReceptionToAddFromOwnedReception = basePart.getOwnedReceptionToAdd();
			for (Iterator iter = ownedReceptionToAddFromOwnedReception.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getClass_OwnedReception(), iter.next()));
			Map ownedReceptionToRefreshFromOwnedReception = basePart.getOwnedReceptionToEdit();
			for (Iterator iter = ownedReceptionToRefreshFromOwnedReception.keySet().iterator(); iter.hasNext();) {

				// Start of user code for ownedReception reference refreshment from ownedReception

				Reception nextElement = (Reception) iter.next();
				Reception ownedReception = (Reception) ownedReceptionToRefreshFromOwnedReception.get(nextElement);

				// End of user code

			}
			List ownedReceptionToRemoveFromOwnedReception = basePart.getOwnedReceptionToRemove();
			for (Iterator iter = ownedReceptionToRemoveFromOwnedReception.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List ownedReceptionToMoveFromOwnedReception = basePart.getOwnedReceptionToMove();
			for (Iterator iter = ownedReceptionToMoveFromOwnedReception.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE.getReception(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
					.getBehavior_IsReentrant(), basePart.getIsReentrant()));

			List redefinedBehaviorToAddFromRedefinedBehavior = basePart.getRedefinedBehaviorToAdd();
			for (Iterator iter = redefinedBehaviorToAddFromRedefinedBehavior.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getBehavior_RedefinedBehavior(), iter.next()));
			List redefinedBehaviorToRemoveFromRedefinedBehavior = basePart.getRedefinedBehaviorToRemove();
			for (Iterator iter = redefinedBehaviorToRemoveFromRedefinedBehavior.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getBehavior_RedefinedBehavior(), iter.next()));
			// List redefinedBehaviorToMoveFromRedefinedBehavior =
			// basePart.getRedefinedBehaviorToMove();
			// for (Iterator iter = redefinedBehaviorToMoveFromRedefinedBehavior.iterator();
			// iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, protocolStateMachine,
			// UMLPackage.eINSTANCE.getBehavior(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List ownedParameterToAddFromOwnedParameter = basePart.getOwnedParameterToAdd();
			for (Iterator iter = ownedParameterToAddFromOwnedParameter.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getBehavior_OwnedParameter(), iter.next()));
			Map ownedParameterToRefreshFromOwnedParameter = basePart.getOwnedParameterToEdit();
			for (Iterator iter = ownedParameterToRefreshFromOwnedParameter.keySet().iterator(); iter.hasNext();) {

				// Start of user code for ownedParameter reference refreshment from ownedParameter

				Parameter nextElement = (Parameter) iter.next();
				Parameter ownedParameter = (Parameter) ownedParameterToRefreshFromOwnedParameter.get(nextElement);

				// End of user code

			}
			List ownedParameterToRemoveFromOwnedParameter = basePart.getOwnedParameterToRemove();
			for (Iterator iter = ownedParameterToRemoveFromOwnedParameter.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List ownedParameterToMoveFromOwnedParameter = basePart.getOwnedParameterToMove();
			for (Iterator iter = ownedParameterToMoveFromOwnedParameter.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE.getParameter(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List preconditionToAddFromPrecondition = basePart.getPreconditionToAdd();
			for (Iterator iter = preconditionToAddFromPrecondition.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getBehavior_Precondition(), iter.next()));
			List preconditionToRemoveFromPrecondition = basePart.getPreconditionToRemove();
			for (Iterator iter = preconditionToRemoveFromPrecondition.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getBehavior_Precondition(), iter.next()));
			// List preconditionToMoveFromPrecondition = basePart.getPreconditionToMove();
			// for (Iterator iter = preconditionToMoveFromPrecondition.iterator(); iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, protocolStateMachine,
			// UMLPackage.eINSTANCE.getConstraint(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List postconditionToAddFromPostcondition = basePart.getPostconditionToAdd();
			for (Iterator iter = postconditionToAddFromPostcondition.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getBehavior_Postcondition(), iter.next()));
			List postconditionToRemoveFromPostcondition = basePart.getPostconditionToRemove();
			for (Iterator iter = postconditionToRemoveFromPostcondition.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getBehavior_Postcondition(), iter.next()));
			// List postconditionToMoveFromPostcondition = basePart.getPostconditionToMove();
			// for (Iterator iter = postconditionToMoveFromPostcondition.iterator();
			// iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, protocolStateMachine,
			// UMLPackage.eINSTANCE.getConstraint(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List ownedParameterSetToAddFromOwnedParameterSet = basePart.getOwnedParameterSetToAdd();
			for (Iterator iter = ownedParameterSetToAddFromOwnedParameterSet.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getBehavior_OwnedParameterSet(), iter.next()));
			Map ownedParameterSetToRefreshFromOwnedParameterSet = basePart.getOwnedParameterSetToEdit();
			for (Iterator iter = ownedParameterSetToRefreshFromOwnedParameterSet.keySet().iterator(); iter.hasNext();) {

				// Start of user code for ownedParameterSet reference refreshment from
				// ownedParameterSet

				ParameterSet nextElement = (ParameterSet) iter.next();
				ParameterSet ownedParameterSet = (ParameterSet) ownedParameterSetToRefreshFromOwnedParameterSet
						.get(nextElement);

				// End of user code

			}
			List ownedParameterSetToRemoveFromOwnedParameterSet = basePart.getOwnedParameterSetToRemove();
			for (Iterator iter = ownedParameterSetToRemoveFromOwnedParameterSet.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List ownedParameterSetToMoveFromOwnedParameterSet = basePart.getOwnedParameterSetToMove();
			for (Iterator iter = ownedParameterSetToMoveFromOwnedParameterSet.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getParameterSet(), moveElement.getElement(), moveElement.getIndex()));
			}
			List regionToAddFromRegion = basePart.getRegionToAdd();
			for (Iterator iter = regionToAddFromRegion.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getStateMachine_Region(), iter.next()));
			Map regionToRefreshFromRegion = basePart.getRegionToEdit();
			for (Iterator iter = regionToRefreshFromRegion.keySet().iterator(); iter.hasNext();) {

				// Start of user code for region reference refreshment from region

				Region nextElement = (Region) iter.next();
				Region region = (Region) regionToRefreshFromRegion.get(nextElement);

				// End of user code

			}
			List regionToRemoveFromRegion = basePart.getRegionToRemove();
			for (Iterator iter = regionToRemoveFromRegion.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List regionToMoveFromRegion = basePart.getRegionToMove();
			for (Iterator iter = regionToMoveFromRegion.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE.getRegion(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List submachineStateToAddFromSubmachineState = basePart.getSubmachineStateToAdd();
			for (Iterator iter = submachineStateToAddFromSubmachineState.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getStateMachine_SubmachineState(), iter.next()));
			List submachineStateToRemoveFromSubmachineState = basePart.getSubmachineStateToRemove();
			for (Iterator iter = submachineStateToRemoveFromSubmachineState.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getStateMachine_SubmachineState(), iter.next()));
			// List submachineStateToMoveFromSubmachineState = basePart.getSubmachineStateToMove();
			// for (Iterator iter = submachineStateToMoveFromSubmachineState.iterator();
			// iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, protocolStateMachine,
			// UMLPackage.eINSTANCE.getState(), moveElement.getElement(), moveElement.getIndex()));
			// }
			List connectionPointToAddFromConnectionPoint = basePart.getConnectionPointToAdd();
			for (Iterator iter = connectionPointToAddFromConnectionPoint.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getStateMachine_ConnectionPoint(), iter.next()));
			Map connectionPointToRefreshFromConnectionPoint = basePart.getConnectionPointToEdit();
			for (Iterator iter = connectionPointToRefreshFromConnectionPoint.keySet().iterator(); iter.hasNext();) {

				// Start of user code for connectionPoint reference refreshment from connectionPoint

				Pseudostate nextElement = (Pseudostate) iter.next();
				Pseudostate connectionPoint = (Pseudostate) connectionPointToRefreshFromConnectionPoint
						.get(nextElement);

				// End of user code

			}
			List connectionPointToRemoveFromConnectionPoint = basePart.getConnectionPointToRemove();
			for (Iterator iter = connectionPointToRemoveFromConnectionPoint.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List connectionPointToMoveFromConnectionPoint = basePart.getConnectionPointToMove();
			for (Iterator iter = connectionPointToMoveFromConnectionPoint.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine,
						UMLPackage.eINSTANCE.getPseudostate(), moveElement.getElement(), moveElement.getIndex()));
			}
			List extendedStateMachineToAddFromExtendedStateMachine = basePart.getExtendedStateMachineToAdd();
			for (Iterator iter = extendedStateMachineToAddFromExtendedStateMachine.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getStateMachine_ExtendedStateMachine(), iter.next()));
			List extendedStateMachineToRemoveFromExtendedStateMachine = basePart.getExtendedStateMachineToRemove();
			for (Iterator iter = extendedStateMachineToRemoveFromExtendedStateMachine.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getStateMachine_ExtendedStateMachine(), iter.next()));
			// List extendedStateMachineToMoveFromExtendedStateMachine =
			// basePart.getExtendedStateMachineToMove();
			// for (Iterator iter = extendedStateMachineToMoveFromExtendedStateMachine.iterator();
			// iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, protocolStateMachine,
			// UMLPackage.eINSTANCE.getStateMachine(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List conformanceToAddFromConformance = basePart.getConformanceToAdd();
			for (Iterator iter = conformanceToAddFromConformance.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getProtocolStateMachine_Conformance(), iter.next()));
			Map conformanceToRefreshFromConformance = basePart.getConformanceToEdit();
			for (Iterator iter = conformanceToRefreshFromConformance.keySet().iterator(); iter.hasNext();) {

				// Start of user code for conformance reference refreshment from conformance

				ProtocolConformance nextElement = (ProtocolConformance) iter.next();
				ProtocolConformance conformance = (ProtocolConformance) conformanceToRefreshFromConformance
						.get(nextElement);

				// End of user code

			}
			List conformanceToRemoveFromConformance = basePart.getConformanceToRemove();
			for (Iterator iter = conformanceToRemoveFromConformance.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List conformanceToMoveFromConformance = basePart.getConformanceToMove();
			for (Iterator iter = conformanceToMoveFromConformance.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getProtocolConformance(), moveElement.getElement(), moveElement.getIndex()));
			}

		}
		if (!cc.isEmpty())
			return cc;
		cc.append(IdentityCommand.INSTANCE);
		return cc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#getPropertiesEditionObject()
	 */
	public EObject getPropertiesEditionObject(EObject source) {
		if (source instanceof ProtocolStateMachine) {
			ProtocolStateMachine protocolStateMachineToUpdate = (ProtocolStateMachine) source;
			protocolStateMachineToUpdate.getOwnedComments().addAll(basePart.getOwnedCommentToAdd());
			protocolStateMachineToUpdate.setName(basePart.getName());

			protocolStateMachineToUpdate.setVisibility((VisibilityKind) basePart.getVisibility());

			protocolStateMachineToUpdate.getClientDependencies().addAll(basePart.getClientDependencyToAdd());
			protocolStateMachineToUpdate.getElementImports().addAll(basePart.getElementImportToAdd());
			protocolStateMachineToUpdate.getPackageImports().addAll(basePart.getPackageImportToAdd());
			protocolStateMachineToUpdate.getOwnedRules().addAll(basePart.getOwnedRuleToAdd());
			protocolStateMachineToUpdate.setIsLeaf(new Boolean(basePart.getIsLeaf()).booleanValue());

			protocolStateMachineToUpdate.getTemplateBindings().addAll(basePart.getTemplateBindingToAdd());
			protocolStateMachineToUpdate.setIsAbstract(new Boolean(basePart.getIsAbstract()).booleanValue());

			protocolStateMachineToUpdate.getGeneralizations().addAll(basePart.getGeneralizationToAdd());
			protocolStateMachineToUpdate.getPowertypeExtents().addAll(basePart.getPowertypeExtentToAdd());
			protocolStateMachineToUpdate.getRedefinedClassifiers().addAll(basePart.getRedefinedClassifierToAdd());
			protocolStateMachineToUpdate.getSubstitutions().addAll(basePart.getSubstitutionToAdd());
			protocolStateMachineToUpdate.getCollaborationUses().addAll(basePart.getCollaborationUseToAdd());
			protocolStateMachineToUpdate.getOwnedUseCases().addAll(basePart.getOwnedUseCaseToAdd());
			protocolStateMachineToUpdate.getUseCases().addAll(basePart.getUseCaseToAdd());
			protocolStateMachineToUpdate.getOwnedAttributes().addAll(basePart.getOwnedAttributeToAdd());
			protocolStateMachineToUpdate.getOwnedConnectors().addAll(basePart.getOwnedConnectorToAdd());
			protocolStateMachineToUpdate.getOwnedBehaviors().addAll(basePart.getOwnedBehaviorToAdd());
			protocolStateMachineToUpdate.getInterfaceRealizations().addAll(basePart.getInterfaceRealizationToAdd());
			protocolStateMachineToUpdate.getOwnedTriggers().addAll(basePart.getOwnedTriggerToAdd());
			protocolStateMachineToUpdate.getNestedClassifiers().addAll(basePart.getNestedClassifierToAdd());
			protocolStateMachineToUpdate.getOwnedOperations().addAll(basePart.getOwnedOperationToAdd());
			protocolStateMachineToUpdate.setIsActive(new Boolean(basePart.getIsActive()).booleanValue());

			protocolStateMachineToUpdate.getOwnedReceptions().addAll(basePart.getOwnedReceptionToAdd());
			protocolStateMachineToUpdate.setIsReentrant(new Boolean(basePart.getIsReentrant()).booleanValue());

			protocolStateMachineToUpdate.getRedefinedBehaviors().addAll(basePart.getRedefinedBehaviorToAdd());
			protocolStateMachineToUpdate.getOwnedParameters().addAll(basePart.getOwnedParameterToAdd());
			protocolStateMachineToUpdate.getPreconditions().addAll(basePart.getPreconditionToAdd());
			protocolStateMachineToUpdate.getPostconditions().addAll(basePart.getPostconditionToAdd());
			protocolStateMachineToUpdate.getOwnedParameterSets().addAll(basePart.getOwnedParameterSetToAdd());
			protocolStateMachineToUpdate.getRegions().addAll(basePart.getRegionToAdd());
			protocolStateMachineToUpdate.getSubmachineStates().addAll(basePart.getSubmachineStateToAdd());
			protocolStateMachineToUpdate.getConnectionPoints().addAll(basePart.getConnectionPointToAdd());
			protocolStateMachineToUpdate.getExtendedStateMachines().addAll(basePart.getExtendedStateMachineToAdd());
			protocolStateMachineToUpdate.getConformances().addAll(basePart.getConformanceToAdd());

			return protocolStateMachineToUpdate;
		} else
			return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionListener#firePropertiesChanged(org.eclipse.emf.common.notify.Notification)
	 */
	public void firePropertiesChanged(PropertiesEditionEvent event) {
		super.firePropertiesChanged(event);
		if (PropertiesEditionEvent.COMMIT == event.getState()
				&& IPropertiesEditionComponent.LIVE_MODE.equals(editing_mode)) {
			CompoundCommand command = new CompoundCommand();
			if (UMLViewsRepository.ProtocolStateMachine.ownedComment == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Comment oldValue = (Comment) event.getOldValue();
					Comment newValue = (Comment) event.getNewValue();

					// Start of user code for ownedComment live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getElement_OwnedComment(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getComment(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.name == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getNamedElement_Name(), event.getNewValue()));

			if (UMLViewsRepository.ProtocolStateMachine.visibility == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getNamedElement_Visibility(), event.getNewValue()));

			if (UMLViewsRepository.ProtocolStateMachine.clientDependency == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getNamedElement_ClientDependency(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getNamedElement_ClientDependency(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getNamedElement_ClientDependency(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.elementImport == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					ElementImport oldValue = (ElementImport) event.getOldValue();
					ElementImport newValue = (ElementImport) event.getNewValue();

					// Start of user code for elementImport live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getNamespace_ElementImport(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getElementImport(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.packageImport == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					PackageImport oldValue = (PackageImport) event.getOldValue();
					PackageImport newValue = (PackageImport) event.getNewValue();

					// Start of user code for packageImport live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getNamespace_PackageImport(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getPackageImport(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.ownedRule == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Constraint oldValue = (Constraint) event.getOldValue();
					Constraint newValue = (Constraint) event.getNewValue();

					// Start of user code for ownedRule live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getNamespace_OwnedRule(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getConstraint(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.isLeaf == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getRedefinableElement_IsLeaf(), event.getNewValue()));

			if (UMLViewsRepository.ProtocolStateMachine.templateBinding == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					TemplateBinding oldValue = (TemplateBinding) event.getOldValue();
					TemplateBinding newValue = (TemplateBinding) event.getNewValue();

					// Start of user code for templateBinding live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getTemplateableElement_TemplateBinding(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getTemplateBinding(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.isAbstract == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getClassifier_IsAbstract(), event.getNewValue()));

			if (UMLViewsRepository.ProtocolStateMachine.generalization == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Generalization oldValue = (Generalization) event.getOldValue();
					Generalization newValue = (Generalization) event.getNewValue();

					// Start of user code for generalization live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getClassifier_Generalization(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getGeneralization(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.powertypeExtent == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getClassifier_PowertypeExtent(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getClassifier_PowertypeExtent(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getClassifier_PowertypeExtent(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.redefinedClassifier == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getClassifier_RedefinedClassifier(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getClassifier_RedefinedClassifier(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getClassifier_RedefinedClassifier(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.substitution == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Substitution oldValue = (Substitution) event.getOldValue();
					Substitution newValue = (Substitution) event.getNewValue();

					// Start of user code for substitution live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getClassifier_Substitution(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getSubstitution(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.collaborationUse == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					CollaborationUse oldValue = (CollaborationUse) event.getOldValue();
					CollaborationUse newValue = (CollaborationUse) event.getNewValue();

					// Start of user code for collaborationUse live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getClassifier_CollaborationUse(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getCollaborationUse(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.ownedUseCase == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					UseCase oldValue = (UseCase) event.getOldValue();
					UseCase newValue = (UseCase) event.getNewValue();

					// Start of user code for ownedUseCase live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getClassifier_OwnedUseCase(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getUseCase(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.useCase == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getClassifier_UseCase(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getClassifier_UseCase(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getClassifier_UseCase(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.ownedAttribute == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Property oldValue = (Property) event.getOldValue();
					Property newValue = (Property) event.getNewValue();

					// Start of user code for ownedAttribute live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getStructuredClassifier_OwnedAttribute(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getProperty(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.ownedConnector == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Connector oldValue = (Connector) event.getOldValue();
					Connector newValue = (Connector) event.getNewValue();

					// Start of user code for ownedConnector live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getStructuredClassifier_OwnedConnector(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getConnector(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.ownedBehavior == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Behavior oldValue = (Behavior) event.getOldValue();
					Behavior newValue = (Behavior) event.getNewValue();

					// Start of user code for ownedBehavior live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getBehavioredClassifier_OwnedBehavior(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getBehavior(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.interfaceRealization == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					InterfaceRealization oldValue = (InterfaceRealization) event.getOldValue();
					InterfaceRealization newValue = (InterfaceRealization) event.getNewValue();

					// Start of user code for interfaceRealization live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getBehavioredClassifier_InterfaceRealization(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getInterfaceRealization(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.ownedTrigger == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Trigger oldValue = (Trigger) event.getOldValue();
					Trigger newValue = (Trigger) event.getNewValue();

					// Start of user code for ownedTrigger live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getBehavioredClassifier_OwnedTrigger(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getTrigger(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.nestedClassifier == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Classifier oldValue = (Classifier) event.getOldValue();
					Classifier newValue = (Classifier) event.getNewValue();

					// Start of user code for nestedClassifier live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getClass_NestedClassifier(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getClassifier(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.ownedOperation == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Operation oldValue = (Operation) event.getOldValue();
					Operation newValue = (Operation) event.getNewValue();

					// Start of user code for ownedOperation live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getClass_OwnedOperation(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getOperation(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.isActive == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getClass_IsActive(), event.getNewValue()));

			if (UMLViewsRepository.ProtocolStateMachine.ownedReception == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Reception oldValue = (Reception) event.getOldValue();
					Reception newValue = (Reception) event.getNewValue();

					// Start of user code for ownedReception live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getClass_OwnedReception(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getReception(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.isReentrant == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
						.getBehavior_IsReentrant(), event.getNewValue()));

			if (UMLViewsRepository.ProtocolStateMachine.redefinedBehavior == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getBehavior_RedefinedBehavior(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getBehavior_RedefinedBehavior(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getBehavior_RedefinedBehavior(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.ownedParameter == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Parameter oldValue = (Parameter) event.getOldValue();
					Parameter newValue = (Parameter) event.getNewValue();

					// Start of user code for ownedParameter live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getBehavior_OwnedParameter(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getParameter(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.precondition == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getBehavior_Precondition(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getBehavior_Precondition(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getBehavior_Precondition(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.postcondition == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getBehavior_Postcondition(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getBehavior_Postcondition(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getBehavior_Postcondition(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.ownedParameterSet == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					ParameterSet oldValue = (ParameterSet) event.getOldValue();
					ParameterSet newValue = (ParameterSet) event.getNewValue();

					// Start of user code for ownedParameterSet live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getBehavior_OwnedParameterSet(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getParameterSet(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.region == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Region oldValue = (Region) event.getOldValue();
					Region newValue = (Region) event.getNewValue();

					// Start of user code for region live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getStateMachine_Region(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getRegion(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.submachineState == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getStateMachine_SubmachineState(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getStateMachine_SubmachineState(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getStateMachine_SubmachineState(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.connectionPoint == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Pseudostate oldValue = (Pseudostate) event.getOldValue();
					Pseudostate newValue = (Pseudostate) event.getNewValue();

					// Start of user code for connectionPoint live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getStateMachine_ConnectionPoint(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getPseudostate(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.extendedStateMachine == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getStateMachine_ExtendedStateMachine(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getStateMachine_ExtendedStateMachine(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getStateMachine_ExtendedStateMachine(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.ProtocolStateMachine.conformance == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					ProtocolConformance oldValue = (ProtocolConformance) event.getOldValue();
					ProtocolConformance newValue = (ProtocolConformance) event.getNewValue();

					// Start of user code for conformance live update command
					// TODO: Complete the protocolStateMachine update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getProtocolStateMachine_Conformance(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, protocolStateMachine, UMLPackage.eINSTANCE
							.getProtocolConformance(), event.getNewValue(), event.getNewIndex()));
			}

			liveEditingDomain.getCommandStack().execute(command);
		} else if (PropertiesEditionEvent.CHANGE == event.getState()) {
			Diagnostic diag = this.validateValue(event);
			if (diag != null && diag.getSeverity() != Diagnostic.OK) {

				if (UMLViewsRepository.ProtocolStateMachine.name == event.getAffectedEditor())
					basePart.setMessageForName(diag.getMessage(), IMessageProvider.ERROR);

			} else {

				if (UMLViewsRepository.ProtocolStateMachine.name == event.getAffectedEditor())
					basePart.unsetMessageForName();

			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#isRequired(java.lang.String,
	 *      int)
	 */
	public boolean isRequired(String key, int kind) {
		return key == UMLViewsRepository.ProtocolStateMachine.isLeaf
				|| key == UMLViewsRepository.ProtocolStateMachine.isAbstract
				|| key == UMLViewsRepository.ProtocolStateMachine.isActive
				|| key == UMLViewsRepository.ProtocolStateMachine.isReentrant
				|| key == UMLViewsRepository.ProtocolStateMachine.region;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#getHelpContent(java.lang.String,
	 *      int)
	 */
	public String getHelpContent(String key, int kind) {
		if (key == UMLViewsRepository.ProtocolStateMachine.ownedComment)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.name)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.visibility)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.clientDependency)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.elementImport)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.packageImport)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.ownedRule)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.isLeaf)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.templateBinding)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.isAbstract)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.generalization)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.powertypeExtent)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.redefinedClassifier)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.substitution)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.collaborationUse)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.ownedUseCase)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.useCase)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.ownedAttribute)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.ownedConnector)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.ownedBehavior)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.interfaceRealization)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.ownedTrigger)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.nestedClassifier)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.ownedOperation)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.isActive)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.ownedReception)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.isReentrant)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.redefinedBehavior)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.ownedParameter)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.precondition)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.postcondition)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.ownedParameterSet)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.region)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.submachineState)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.connectionPoint)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.extendedStateMachine)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.ProtocolStateMachine.conformance)
			return null; //$NON-NLS-1$
		return super.getHelpContent(key, kind);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#validateValue(org.eclipse.emf.common.notify.Notification)
	 */
	public Diagnostic validateValue(PropertiesEditionEvent event) {
		String newStringValue = event.getNewValue().toString();
		Diagnostic ret = null;
		try {
			if (UMLViewsRepository.ProtocolStateMachine.name == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getNamedElement_Name()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getNamedElement_Name().getEAttributeType(),
						newValue);
			}
			if (UMLViewsRepository.ProtocolStateMachine.visibility == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getNamedElement_Visibility()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getNamedElement_Visibility()
						.getEAttributeType(), newValue);
			}
			if (UMLViewsRepository.ProtocolStateMachine.isLeaf == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf()
						.getEAttributeType(), newValue);
			}
			if (UMLViewsRepository.ProtocolStateMachine.isAbstract == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getClassifier_IsAbstract()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getClassifier_IsAbstract()
						.getEAttributeType(), newValue);
			}
			if (UMLViewsRepository.ProtocolStateMachine.isActive == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getClass_IsActive()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getClass_IsActive().getEAttributeType(),
						newValue);
			}
			if (UMLViewsRepository.ProtocolStateMachine.isReentrant == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getBehavior_IsReentrant()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getBehavior_IsReentrant()
						.getEAttributeType(), newValue);
			}

		} catch (IllegalArgumentException iae) {
			ret = BasicDiagnostic.toDiagnostic(iae);
		}
		return ret;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#validate()
	 */
	public Diagnostic validate() {
		if (IPropertiesEditionComponent.BATCH_MODE.equals(editing_mode)) {
			EObject copy = EcoreUtil.copy(PropertiesContextService.getInstance().entryPointElement());
			copy = PropertiesContextService.getInstance().entryPointComponent().getPropertiesEditionObject(copy);
			return Diagnostician.INSTANCE.validate(copy);
		} else if (IPropertiesEditionComponent.LIVE_MODE.equals(editing_mode))
			return Diagnostician.INSTANCE.validate(protocolStateMachine);
		else
			return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#dispose()
	 */
	public void dispose() {
		if (semanticAdapter != null)
			protocolStateMachine.eAdapters().remove(semanticAdapter);
	}

}
