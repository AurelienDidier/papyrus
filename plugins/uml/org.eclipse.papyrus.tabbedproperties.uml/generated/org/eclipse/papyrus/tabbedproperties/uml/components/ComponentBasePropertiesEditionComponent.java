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
import org.eclipse.papyrus.tabbedproperties.uml.parts.ComponentPropertiesEditionPart;
import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.CollaborationUse;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.ComponentRealization;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Reception;
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
public class ComponentBasePropertiesEditionComponent extends StandardPropertiesEditionComponent {

	public static String BASE_PART = "Base"; //$NON-NLS-1$

	private String[] parts = { BASE_PART };

	/**
	 * The EObject to edit
	 */
	private Component component;

	/**
	 * The Base part
	 */
	private ComponentPropertiesEditionPart basePart;

	/**
	 * Default constructor
	 */
	public ComponentBasePropertiesEditionComponent(EObject component, String editing_mode) {
		if (component instanceof Component) {
			this.component = (Component) component;
			if (IPropertiesEditionComponent.LIVE_MODE.equals(editing_mode)) {
				semanticAdapter = initializeSemanticAdapter();
				this.component.eAdapters().add(semanticAdapter);
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
					ComponentBasePropertiesEditionComponent.this.dispose();
				else {
					if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(msg.getFeature()) && basePart != null)
						basePart.setName((String) msg.getNewValue());

					if (UMLPackage.eINSTANCE.getNamedElement_Visibility().equals(msg.getFeature()) && basePart != null)
						basePart.setVisibility((Enumerator) msg.getNewValue());

					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getNamespace_ElementImport() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getElementImport())) {
						basePart.updateElementImport(component);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getNamespace_PackageImport() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getPackageImport())) {
						basePart.updatePackageImport(component);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getNamespace_OwnedRule() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getConstraint())) {
						basePart.updateOwnedRule(component);
					}
					if (UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf().equals(msg.getFeature())
							&& basePart != null)
						basePart.setIsLeaf((Boolean) msg.getNewValue());

					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getTemplateableElement_TemplateBinding() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getTemplateBinding())) {
						basePart.updateTemplateBinding(component);
					}
					if (UMLPackage.eINSTANCE.getClassifier_IsAbstract().equals(msg.getFeature()) && basePart != null)
						basePart.setIsAbstract((Boolean) msg.getNewValue());

					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getClassifier_Generalization() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getGeneralization())) {
						basePart.updateGeneralization(component);
					}
					if (UMLPackage.eINSTANCE.getClassifier_PowertypeExtent().equals(msg.getFeature()))
						basePart.updatePowertypeExtent(component);
					if (UMLPackage.eINSTANCE.getClassifier_RedefinedClassifier().equals(msg.getFeature()))
						basePart.updateRedefinedClassifier(component);
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getClassifier_Substitution() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getSubstitution())) {
						basePart.updateSubstitution(component);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getClassifier_CollaborationUse() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getCollaborationUse())) {
						basePart.updateCollaborationUse(component);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getClassifier_OwnedUseCase() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getUseCase())) {
						basePart.updateOwnedUseCase(component);
					}
					if (UMLPackage.eINSTANCE.getClassifier_UseCase().equals(msg.getFeature()))
						basePart.updateUseCase(component);
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getStructuredClassifier_OwnedAttribute() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getProperty())) {
						basePart.updateOwnedAttribute(component);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getStructuredClassifier_OwnedConnector() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getConnector())) {
						basePart.updateOwnedConnector(component);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getBehavioredClassifier_OwnedBehavior() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getBehavior())) {
						basePart.updateOwnedBehavior(component);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getBehavioredClassifier_InterfaceRealization() || ((EStructuralFeature) msg
									.getFeature()).getEContainingClass() == UMLPackage.eINSTANCE
									.getInterfaceRealization())) {
						basePart.updateInterfaceRealization(component);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getBehavioredClassifier_OwnedTrigger() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getTrigger())) {
						basePart.updateOwnedTrigger(component);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getClass_NestedClassifier() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getClassifier())) {
						basePart.updateNestedClassifier(component);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getClass_OwnedOperation() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getOperation())) {
						basePart.updateOwnedOperation(component);
					}
					if (UMLPackage.eINSTANCE.getClass_IsActive().equals(msg.getFeature()) && basePart != null)
						basePart.setIsActive((Boolean) msg.getNewValue());

					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getClass_OwnedReception() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getReception())) {
						basePart.updateOwnedReception(component);
					}
					if (UMLPackage.eINSTANCE.getComponent_IsIndirectlyInstantiated().equals(msg.getFeature())
							&& basePart != null)
						basePart.setIsIndirectlyInstantiated((Boolean) msg.getNewValue());

					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getComponent_PackagedElement() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getPackageableElement())) {
						basePart.updatePackagedElement(component);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getComponent_Realization() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getComponentRealization())) {
						basePart.updateRealization(component);
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
			return UMLViewsRepository.Component.class;
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
		if (component != null && BASE_PART.equals(key)) {
			if (basePart == null) {
				IPropertiesEditionPartProvider provider = PropertiesEditionPartProviderService.getInstance()
						.getProvider(UMLViewsRepository.class);
				if (provider != null) {
					basePart = (ComponentPropertiesEditionPart) provider.getPropertiesEditionPart(
							UMLViewsRepository.Component.class, kind, this);
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
		if (key == UMLViewsRepository.Component.class)
			this.basePart = (ComponentPropertiesEditionPart) propertiesEditionPart;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#initPart(java.lang.Class,
	 *      int, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.resource.ResourceSet)
	 */
	public void initPart(java.lang.Class key, int kind, EObject elt, ResourceSet allResource) {
		if (basePart != null && key == UMLViewsRepository.Component.class) {
			((IPropertiesEditionPart) basePart).setContext(elt, allResource);
			Component component = (Component) elt;
			// init values
			if (component.getName() != null)
				basePart.setName(component.getName());

			basePart.initVisibility((EEnum) UMLPackage.eINSTANCE.getNamedElement_Visibility().getEType(), component
					.getVisibility());
			basePart.initElementImport(component, null, UMLPackage.eINSTANCE.getNamespace_ElementImport());
			basePart.initPackageImport(component, null, UMLPackage.eINSTANCE.getNamespace_PackageImport());
			basePart.initOwnedRule(component, null, UMLPackage.eINSTANCE.getNamespace_OwnedRule());
			basePart.setIsLeaf(component.isLeaf());

			basePart
					.initTemplateBinding(component, null, UMLPackage.eINSTANCE.getTemplateableElement_TemplateBinding());
			basePart.setIsAbstract(component.isAbstract());

			basePart.initGeneralization(component, null, UMLPackage.eINSTANCE.getClassifier_Generalization());
			basePart.initPowertypeExtent(component, null, UMLPackage.eINSTANCE.getClassifier_PowertypeExtent());
			basePart.initRedefinedClassifier(component, null, UMLPackage.eINSTANCE.getClassifier_RedefinedClassifier());
			basePart.initSubstitution(component, null, UMLPackage.eINSTANCE.getClassifier_Substitution());
			basePart.initCollaborationUse(component, null, UMLPackage.eINSTANCE.getClassifier_CollaborationUse());
			basePart.initOwnedUseCase(component, null, UMLPackage.eINSTANCE.getClassifier_OwnedUseCase());
			basePart.initUseCase(component, null, UMLPackage.eINSTANCE.getClassifier_UseCase());
			basePart.initOwnedAttribute(component, null, UMLPackage.eINSTANCE.getStructuredClassifier_OwnedAttribute());
			basePart.initOwnedConnector(component, null, UMLPackage.eINSTANCE.getStructuredClassifier_OwnedConnector());
			basePart.initOwnedBehavior(component, null, UMLPackage.eINSTANCE.getBehavioredClassifier_OwnedBehavior());
			basePart.initInterfaceRealization(component, null, UMLPackage.eINSTANCE
					.getBehavioredClassifier_InterfaceRealization());
			basePart.initOwnedTrigger(component, null, UMLPackage.eINSTANCE.getBehavioredClassifier_OwnedTrigger());
			basePart.initNestedClassifier(component, null, UMLPackage.eINSTANCE.getClass_NestedClassifier());
			basePart.initOwnedOperation(component, null, UMLPackage.eINSTANCE.getClass_OwnedOperation());
			basePart.setIsActive(component.isActive());

			basePart.initOwnedReception(component, null, UMLPackage.eINSTANCE.getClass_OwnedReception());
			basePart.setIsIndirectlyInstantiated(component.isIndirectlyInstantiated());

			basePart.initPackagedElement(component, null, UMLPackage.eINSTANCE.getComponent_PackagedElement());
			basePart.initRealization(component, null, UMLPackage.eINSTANCE.getComponent_Realization());

			// init filters

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

			basePart.addFilterToPackagedElement(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof PackageableElement); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for packagedElement

			// End of user code
			basePart.addFilterToRealization(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof ComponentRealization); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for realization

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
		if (component != null) {
			cc.append(SetCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getNamedElement_Name(), basePart
					.getName()));

			cc.append(SetCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getNamedElement_Visibility(),
					basePart.getVisibility()));

			List elementImportToAddFromElementImport = basePart.getElementImportToAdd();
			for (Iterator iter = elementImportToAddFromElementImport.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component,
						UMLPackage.eINSTANCE.getNamespace_ElementImport(), iter.next()));
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
				cc.append(MoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getElementImport(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List packageImportToAddFromPackageImport = basePart.getPackageImportToAdd();
			for (Iterator iter = packageImportToAddFromPackageImport.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component,
						UMLPackage.eINSTANCE.getNamespace_PackageImport(), iter.next()));
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
				cc.append(MoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getPackageImport(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List ownedRuleToAddFromOwnedRule = basePart.getOwnedRuleToAdd();
			for (Iterator iter = ownedRuleToAddFromOwnedRule.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getNamespace_OwnedRule(),
						iter.next()));
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
				cc.append(MoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getConstraint(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf(),
					basePart.getIsLeaf()));

			List templateBindingToAddFromTemplateBinding = basePart.getTemplateBindingToAdd();
			for (Iterator iter = templateBindingToAddFromTemplateBinding.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component, UMLPackage.eINSTANCE
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
				cc.append(MoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getTemplateBinding(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getClassifier_IsAbstract(),
					basePart.getIsAbstract()));

			List generalizationToAddFromGeneralization = basePart.getGeneralizationToAdd();
			for (Iterator iter = generalizationToAddFromGeneralization.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component, UMLPackage.eINSTANCE
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
				cc.append(MoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getGeneralization(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List powertypeExtentToAddFromPowertypeExtent = basePart.getPowertypeExtentToAdd();
			for (Iterator iter = powertypeExtentToAddFromPowertypeExtent.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component, UMLPackage.eINSTANCE
						.getClassifier_PowertypeExtent(), iter.next()));
			List powertypeExtentToRemoveFromPowertypeExtent = basePart.getPowertypeExtentToRemove();
			for (Iterator iter = powertypeExtentToRemoveFromPowertypeExtent.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE
						.getClassifier_PowertypeExtent(), iter.next()));
			// List powertypeExtentToMoveFromPowertypeExtent = basePart.getPowertypeExtentToMove();
			// for (Iterator iter = powertypeExtentToMoveFromPowertypeExtent.iterator();
			// iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, component,
			// UMLPackage.eINSTANCE.getGeneralizationSet(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List redefinedClassifierToAddFromRedefinedClassifier = basePart.getRedefinedClassifierToAdd();
			for (Iterator iter = redefinedClassifierToAddFromRedefinedClassifier.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component, UMLPackage.eINSTANCE
						.getClassifier_RedefinedClassifier(), iter.next()));
			List redefinedClassifierToRemoveFromRedefinedClassifier = basePart.getRedefinedClassifierToRemove();
			for (Iterator iter = redefinedClassifierToRemoveFromRedefinedClassifier.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE
						.getClassifier_RedefinedClassifier(), iter.next()));
			// List redefinedClassifierToMoveFromRedefinedClassifier =
			// basePart.getRedefinedClassifierToMove();
			// for (Iterator iter = redefinedClassifierToMoveFromRedefinedClassifier.iterator();
			// iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, component,
			// UMLPackage.eINSTANCE.getClassifier(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List substitutionToAddFromSubstitution = basePart.getSubstitutionToAdd();
			for (Iterator iter = substitutionToAddFromSubstitution.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component,
						UMLPackage.eINSTANCE.getClassifier_Substitution(), iter.next()));
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
				cc.append(MoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getSubstitution(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List collaborationUseToAddFromCollaborationUse = basePart.getCollaborationUseToAdd();
			for (Iterator iter = collaborationUseToAddFromCollaborationUse.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component, UMLPackage.eINSTANCE
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
				cc.append(MoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getCollaborationUse(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List ownedUseCaseToAddFromOwnedUseCase = basePart.getOwnedUseCaseToAdd();
			for (Iterator iter = ownedUseCaseToAddFromOwnedUseCase.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component,
						UMLPackage.eINSTANCE.getClassifier_OwnedUseCase(), iter.next()));
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
				cc.append(MoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getUseCase(), moveElement
						.getElement(), moveElement.getIndex()));
			}
			List useCaseToAddFromUseCase = basePart.getUseCaseToAdd();
			for (Iterator iter = useCaseToAddFromUseCase.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getClassifier_UseCase(),
						iter.next()));
			List useCaseToRemoveFromUseCase = basePart.getUseCaseToRemove();
			for (Iterator iter = useCaseToRemoveFromUseCase.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getClassifier_UseCase(),
						iter.next()));
			// List useCaseToMoveFromUseCase = basePart.getUseCaseToMove();
			// for (Iterator iter = useCaseToMoveFromUseCase.iterator(); iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, component,
			// UMLPackage.eINSTANCE.getUseCase(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List ownedAttributeToAddFromOwnedAttribute = basePart.getOwnedAttributeToAdd();
			for (Iterator iter = ownedAttributeToAddFromOwnedAttribute.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component, UMLPackage.eINSTANCE
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
				cc.append(MoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getProperty(), moveElement
						.getElement(), moveElement.getIndex()));
			}
			List ownedConnectorToAddFromOwnedConnector = basePart.getOwnedConnectorToAdd();
			for (Iterator iter = ownedConnectorToAddFromOwnedConnector.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component, UMLPackage.eINSTANCE
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
				cc.append(MoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getConnector(), moveElement
						.getElement(), moveElement.getIndex()));
			}
			List ownedBehaviorToAddFromOwnedBehavior = basePart.getOwnedBehaviorToAdd();
			for (Iterator iter = ownedBehaviorToAddFromOwnedBehavior.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component, UMLPackage.eINSTANCE
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
				cc.append(MoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getBehavior(), moveElement
						.getElement(), moveElement.getIndex()));
			}
			List interfaceRealizationToAddFromInterfaceRealization = basePart.getInterfaceRealizationToAdd();
			for (Iterator iter = interfaceRealizationToAddFromInterfaceRealization.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component, UMLPackage.eINSTANCE
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
				cc.append(MoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getInterfaceRealization(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List ownedTriggerToAddFromOwnedTrigger = basePart.getOwnedTriggerToAdd();
			for (Iterator iter = ownedTriggerToAddFromOwnedTrigger.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component, UMLPackage.eINSTANCE
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
				cc.append(MoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getTrigger(), moveElement
						.getElement(), moveElement.getIndex()));
			}
			List nestedClassifierToAddFromNestedClassifier = basePart.getNestedClassifierToAdd();
			for (Iterator iter = nestedClassifierToAddFromNestedClassifier.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getClass_NestedClassifier(),
						iter.next()));
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
				cc.append(MoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getClassifier(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List ownedOperationToAddFromOwnedOperation = basePart.getOwnedOperationToAdd();
			for (Iterator iter = ownedOperationToAddFromOwnedOperation.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getClass_OwnedOperation(),
						iter.next()));
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
				cc.append(MoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getOperation(), moveElement
						.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getClass_IsActive(), basePart
					.getIsActive()));

			List ownedReceptionToAddFromOwnedReception = basePart.getOwnedReceptionToAdd();
			for (Iterator iter = ownedReceptionToAddFromOwnedReception.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getClass_OwnedReception(),
						iter.next()));
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
				cc.append(MoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getReception(), moveElement
						.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, component, UMLPackage.eINSTANCE
					.getComponent_IsIndirectlyInstantiated(), basePart.getIsIndirectlyInstantiated()));

			List packagedElementToAddFromPackagedElement = basePart.getPackagedElementToAdd();
			for (Iterator iter = packagedElementToAddFromPackagedElement.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component, UMLPackage.eINSTANCE
						.getComponent_PackagedElement(), iter.next()));
			Map packagedElementToRefreshFromPackagedElement = basePart.getPackagedElementToEdit();
			for (Iterator iter = packagedElementToRefreshFromPackagedElement.keySet().iterator(); iter.hasNext();) {

				// Start of user code for packagedElement reference refreshment from packagedElement

				PackageableElement nextElement = (PackageableElement) iter.next();
				PackageableElement packagedElement = (PackageableElement) packagedElementToRefreshFromPackagedElement
						.get(nextElement);

				// End of user code

			}
			List packagedElementToRemoveFromPackagedElement = basePart.getPackagedElementToRemove();
			for (Iterator iter = packagedElementToRemoveFromPackagedElement.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List packagedElementToMoveFromPackagedElement = basePart.getPackagedElementToMove();
			for (Iterator iter = packagedElementToMoveFromPackagedElement.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getPackageableElement(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List realizationToAddFromRealization = basePart.getRealizationToAdd();
			for (Iterator iter = realizationToAddFromRealization.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getComponent_Realization(),
						iter.next()));
			Map realizationToRefreshFromRealization = basePart.getRealizationToEdit();
			for (Iterator iter = realizationToRefreshFromRealization.keySet().iterator(); iter.hasNext();) {

				// Start of user code for realization reference refreshment from realization

				ComponentRealization nextElement = (ComponentRealization) iter.next();
				ComponentRealization realization = (ComponentRealization) realizationToRefreshFromRealization
						.get(nextElement);

				// End of user code

			}
			List realizationToRemoveFromRealization = basePart.getRealizationToRemove();
			for (Iterator iter = realizationToRemoveFromRealization.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List realizationToMoveFromRealization = basePart.getRealizationToMove();
			for (Iterator iter = realizationToMoveFromRealization.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, component, UMLPackage.eINSTANCE.getComponentRealization(),
						moveElement.getElement(), moveElement.getIndex()));
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
		if (source instanceof Component) {
			Component componentToUpdate = (Component) source;
			componentToUpdate.setName(basePart.getName());

			componentToUpdate.setVisibility((VisibilityKind) basePart.getVisibility());

			componentToUpdate.getElementImports().addAll(basePart.getElementImportToAdd());
			componentToUpdate.getPackageImports().addAll(basePart.getPackageImportToAdd());
			componentToUpdate.getOwnedRules().addAll(basePart.getOwnedRuleToAdd());
			componentToUpdate.setIsLeaf(new Boolean(basePart.getIsLeaf()).booleanValue());

			componentToUpdate.getTemplateBindings().addAll(basePart.getTemplateBindingToAdd());
			componentToUpdate.setIsAbstract(new Boolean(basePart.getIsAbstract()).booleanValue());

			componentToUpdate.getGeneralizations().addAll(basePart.getGeneralizationToAdd());
			componentToUpdate.getPowertypeExtents().addAll(basePart.getPowertypeExtentToAdd());
			componentToUpdate.getRedefinedClassifiers().addAll(basePart.getRedefinedClassifierToAdd());
			componentToUpdate.getSubstitutions().addAll(basePart.getSubstitutionToAdd());
			componentToUpdate.getCollaborationUses().addAll(basePart.getCollaborationUseToAdd());
			componentToUpdate.getOwnedUseCases().addAll(basePart.getOwnedUseCaseToAdd());
			componentToUpdate.getUseCases().addAll(basePart.getUseCaseToAdd());
			componentToUpdate.getOwnedAttributes().addAll(basePart.getOwnedAttributeToAdd());
			componentToUpdate.getOwnedConnectors().addAll(basePart.getOwnedConnectorToAdd());
			componentToUpdate.getOwnedBehaviors().addAll(basePart.getOwnedBehaviorToAdd());
			componentToUpdate.getInterfaceRealizations().addAll(basePart.getInterfaceRealizationToAdd());
			componentToUpdate.getOwnedTriggers().addAll(basePart.getOwnedTriggerToAdd());
			componentToUpdate.getNestedClassifiers().addAll(basePart.getNestedClassifierToAdd());
			componentToUpdate.getOwnedOperations().addAll(basePart.getOwnedOperationToAdd());
			componentToUpdate.setIsActive(new Boolean(basePart.getIsActive()).booleanValue());

			componentToUpdate.getOwnedReceptions().addAll(basePart.getOwnedReceptionToAdd());
			componentToUpdate.setIsIndirectlyInstantiated(new Boolean(basePart.getIsIndirectlyInstantiated())
					.booleanValue());

			componentToUpdate.getPackagedElements().addAll(basePart.getPackagedElementToAdd());
			componentToUpdate.getRealizations().addAll(basePart.getRealizationToAdd());

			return componentToUpdate;
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
			if (UMLViewsRepository.Component.name == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
						.getNamedElement_Name(), event.getNewValue()));

			if (UMLViewsRepository.Component.visibility == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
						.getNamedElement_Visibility(), event.getNewValue()));

			if (UMLViewsRepository.Component.elementImport == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					ElementImport oldValue = (ElementImport) event.getOldValue();
					ElementImport newValue = (ElementImport) event.getNewValue();

					// Start of user code for elementImport live update command
					// TODO: Complete the component update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getNamespace_ElementImport(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getElementImport(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.packageImport == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					PackageImport oldValue = (PackageImport) event.getOldValue();
					PackageImport newValue = (PackageImport) event.getNewValue();

					// Start of user code for packageImport live update command
					// TODO: Complete the component update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getNamespace_PackageImport(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getPackageImport(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.ownedRule == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Constraint oldValue = (Constraint) event.getOldValue();
					Constraint newValue = (Constraint) event.getNewValue();

					// Start of user code for ownedRule live update command
					// TODO: Complete the component update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getNamespace_OwnedRule(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getConstraint(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.isLeaf == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
						.getRedefinableElement_IsLeaf(), event.getNewValue()));

			if (UMLViewsRepository.Component.templateBinding == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					TemplateBinding oldValue = (TemplateBinding) event.getOldValue();
					TemplateBinding newValue = (TemplateBinding) event.getNewValue();

					// Start of user code for templateBinding live update command
					// TODO: Complete the component update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getTemplateableElement_TemplateBinding(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getTemplateBinding(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.isAbstract == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
						.getClassifier_IsAbstract(), event.getNewValue()));

			if (UMLViewsRepository.Component.generalization == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Generalization oldValue = (Generalization) event.getOldValue();
					Generalization newValue = (Generalization) event.getNewValue();

					// Start of user code for generalization live update command
					// TODO: Complete the component update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getClassifier_Generalization(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getGeneralization(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.powertypeExtent == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getClassifier_PowertypeExtent(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getClassifier_PowertypeExtent(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getClassifier_PowertypeExtent(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.redefinedClassifier == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getClassifier_RedefinedClassifier(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getClassifier_RedefinedClassifier(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getClassifier_RedefinedClassifier(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.substitution == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Substitution oldValue = (Substitution) event.getOldValue();
					Substitution newValue = (Substitution) event.getNewValue();

					// Start of user code for substitution live update command
					// TODO: Complete the component update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getClassifier_Substitution(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getSubstitution(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.collaborationUse == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					CollaborationUse oldValue = (CollaborationUse) event.getOldValue();
					CollaborationUse newValue = (CollaborationUse) event.getNewValue();

					// Start of user code for collaborationUse live update command
					// TODO: Complete the component update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getClassifier_CollaborationUse(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getCollaborationUse(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.ownedUseCase == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					UseCase oldValue = (UseCase) event.getOldValue();
					UseCase newValue = (UseCase) event.getNewValue();

					// Start of user code for ownedUseCase live update command
					// TODO: Complete the component update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getClassifier_OwnedUseCase(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE.getUseCase(),
							event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.useCase == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getClassifier_UseCase(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getClassifier_UseCase(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getClassifier_UseCase(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.ownedAttribute == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Property oldValue = (Property) event.getOldValue();
					Property newValue = (Property) event.getNewValue();

					// Start of user code for ownedAttribute live update command
					// TODO: Complete the component update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getStructuredClassifier_OwnedAttribute(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE.getProperty(),
							event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.ownedConnector == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Connector oldValue = (Connector) event.getOldValue();
					Connector newValue = (Connector) event.getNewValue();

					// Start of user code for ownedConnector live update command
					// TODO: Complete the component update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getStructuredClassifier_OwnedConnector(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component,
							UMLPackage.eINSTANCE.getConnector(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.ownedBehavior == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Behavior oldValue = (Behavior) event.getOldValue();
					Behavior newValue = (Behavior) event.getNewValue();

					// Start of user code for ownedBehavior live update command
					// TODO: Complete the component update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getBehavioredClassifier_OwnedBehavior(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE.getBehavior(),
							event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.interfaceRealization == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					InterfaceRealization oldValue = (InterfaceRealization) event.getOldValue();
					InterfaceRealization newValue = (InterfaceRealization) event.getNewValue();

					// Start of user code for interfaceRealization live update command
					// TODO: Complete the component update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getBehavioredClassifier_InterfaceRealization(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getInterfaceRealization(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.ownedTrigger == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Trigger oldValue = (Trigger) event.getOldValue();
					Trigger newValue = (Trigger) event.getNewValue();

					// Start of user code for ownedTrigger live update command
					// TODO: Complete the component update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getBehavioredClassifier_OwnedTrigger(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE.getTrigger(),
							event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.nestedClassifier == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Classifier oldValue = (Classifier) event.getOldValue();
					Classifier newValue = (Classifier) event.getNewValue();

					// Start of user code for nestedClassifier live update command
					// TODO: Complete the component update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getClass_NestedClassifier(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getClassifier(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.ownedOperation == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Operation oldValue = (Operation) event.getOldValue();
					Operation newValue = (Operation) event.getNewValue();

					// Start of user code for ownedOperation live update command
					// TODO: Complete the component update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getClass_OwnedOperation(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component,
							UMLPackage.eINSTANCE.getOperation(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.isActive == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, component,
						UMLPackage.eINSTANCE.getClass_IsActive(), event.getNewValue()));

			if (UMLViewsRepository.Component.ownedReception == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Reception oldValue = (Reception) event.getOldValue();
					Reception newValue = (Reception) event.getNewValue();

					// Start of user code for ownedReception live update command
					// TODO: Complete the component update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getClass_OwnedReception(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component,
							UMLPackage.eINSTANCE.getReception(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.isIndirectlyInstantiated == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
						.getComponent_IsIndirectlyInstantiated(), event.getNewValue()));

			if (UMLViewsRepository.Component.packagedElement == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					PackageableElement oldValue = (PackageableElement) event.getOldValue();
					PackageableElement newValue = (PackageableElement) event.getNewValue();

					// Start of user code for packagedElement live update command
					// TODO: Complete the component update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getComponent_PackagedElement(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getPackageableElement(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Component.realization == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					ComponentRealization oldValue = (ComponentRealization) event.getOldValue();
					ComponentRealization newValue = (ComponentRealization) event.getNewValue();

					// Start of user code for realization live update command
					// TODO: Complete the component update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getComponent_Realization(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, component, UMLPackage.eINSTANCE
							.getComponentRealization(), event.getNewValue(), event.getNewIndex()));
			}

			liveEditingDomain.getCommandStack().execute(command);
		} else if (PropertiesEditionEvent.CHANGE == event.getState()) {
			Diagnostic diag = this.validateValue(event);
			if (diag != null && diag.getSeverity() != Diagnostic.OK) {
				if (UMLViewsRepository.Component.name == event.getAffectedEditor())
					basePart.setMessageForName(diag.getMessage(), IMessageProvider.ERROR);

			} else {
				if (UMLViewsRepository.Component.name == event.getAffectedEditor())
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
		return key == UMLViewsRepository.Component.isLeaf || key == UMLViewsRepository.Component.isAbstract
				|| key == UMLViewsRepository.Component.isActive
				|| key == UMLViewsRepository.Component.isIndirectlyInstantiated;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#getHelpContent(java.lang.String,
	 *      int)
	 */
	public String getHelpContent(String key, int kind) {
		if (key == UMLViewsRepository.Component.name)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.visibility)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.elementImport)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.packageImport)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.ownedRule)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.isLeaf)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.templateBinding)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.isAbstract)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.generalization)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.powertypeExtent)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.redefinedClassifier)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.substitution)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.collaborationUse)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.ownedUseCase)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.useCase)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.ownedAttribute)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.ownedConnector)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.ownedBehavior)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.interfaceRealization)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.ownedTrigger)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.nestedClassifier)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.ownedOperation)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.isActive)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.ownedReception)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.isIndirectlyInstantiated)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.packagedElement)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Component.realization)
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
			if (UMLViewsRepository.Component.name == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getNamedElement_Name()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getNamedElement_Name().getEAttributeType(),
						newValue);
			}
			if (UMLViewsRepository.Component.visibility == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getNamedElement_Visibility()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getNamedElement_Visibility()
						.getEAttributeType(), newValue);
			}
			if (UMLViewsRepository.Component.isLeaf == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf()
						.getEAttributeType(), newValue);
			}
			if (UMLViewsRepository.Component.isAbstract == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getClassifier_IsAbstract()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getClassifier_IsAbstract()
						.getEAttributeType(), newValue);
			}
			if (UMLViewsRepository.Component.isActive == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getClass_IsActive()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getClass_IsActive().getEAttributeType(),
						newValue);
			}
			if (UMLViewsRepository.Component.isIndirectlyInstantiated == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE
						.getComponent_IsIndirectlyInstantiated().getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getComponent_IsIndirectlyInstantiated()
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
			return Diagnostician.INSTANCE.validate(component);
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
			component.eAdapters().remove(semanticAdapter);
	}

}
