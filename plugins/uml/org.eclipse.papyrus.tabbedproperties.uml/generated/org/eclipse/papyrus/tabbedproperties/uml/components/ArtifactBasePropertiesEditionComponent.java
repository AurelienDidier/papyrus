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
import org.eclipse.papyrus.tabbedproperties.uml.parts.ArtifactPropertiesEditionPart;
import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.CollaborationUse;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Manifestation;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Substitution;
import org.eclipse.uml2.uml.TemplateBinding;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UseCase;
import org.eclipse.uml2.uml.VisibilityKind;

// End of user code

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class ArtifactBasePropertiesEditionComponent extends StandardPropertiesEditionComponent {

	public static String BASE_PART = "Base"; //$NON-NLS-1$

	private String[] parts = { BASE_PART };

	/**
	 * The EObject to edit
	 */
	private Artifact artifact;

	/**
	 * The Base part
	 */
	private ArtifactPropertiesEditionPart basePart;

	/**
	 * Default constructor
	 */
	public ArtifactBasePropertiesEditionComponent(EObject artifact, String editing_mode) {
		if (artifact instanceof Artifact) {
			this.artifact = (Artifact) artifact;
			if (IPropertiesEditionComponent.LIVE_MODE.equals(editing_mode)) {
				semanticAdapter = initializeSemanticAdapter();
				this.artifact.eAdapters().add(semanticAdapter);
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
					ArtifactBasePropertiesEditionComponent.this.dispose();
				else {
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getElement_OwnedComment() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getComment())) {
						basePart.updateOwnedComment(artifact);
					}
					if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(msg.getFeature()) && basePart != null)
						basePart.setName((String) msg.getNewValue());

					if (UMLPackage.eINSTANCE.getNamedElement_Visibility().equals(msg.getFeature()) && basePart != null)
						basePart.setVisibility((Enumerator) msg.getNewValue());

					if (UMLPackage.eINSTANCE.getNamedElement_ClientDependency().equals(msg.getFeature()))
						basePart.updateClientDependency(artifact);
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getNamespace_ElementImport() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getElementImport())) {
						basePart.updateElementImport(artifact);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getNamespace_PackageImport() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getPackageImport())) {
						basePart.updatePackageImport(artifact);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getNamespace_OwnedRule() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getConstraint())) {
						basePart.updateOwnedRule(artifact);
					}
					if (UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf().equals(msg.getFeature())
							&& basePart != null)
						basePart.setIsLeaf((Boolean) msg.getNewValue());

					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getTemplateableElement_TemplateBinding() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getTemplateBinding())) {
						basePart.updateTemplateBinding(artifact);
					}
					if (UMLPackage.eINSTANCE.getClassifier_IsAbstract().equals(msg.getFeature()) && basePart != null)
						basePart.setIsAbstract((Boolean) msg.getNewValue());

					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getClassifier_Generalization() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getGeneralization())) {
						basePart.updateGeneralization(artifact);
					}
					if (UMLPackage.eINSTANCE.getClassifier_PowertypeExtent().equals(msg.getFeature()))
						basePart.updatePowertypeExtent(artifact);
					if (UMLPackage.eINSTANCE.getClassifier_RedefinedClassifier().equals(msg.getFeature()))
						basePart.updateRedefinedClassifier(artifact);
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getClassifier_Substitution() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getSubstitution())) {
						basePart.updateSubstitution(artifact);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getClassifier_CollaborationUse() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getCollaborationUse())) {
						basePart.updateCollaborationUse(artifact);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getClassifier_OwnedUseCase() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getUseCase())) {
						basePart.updateOwnedUseCase(artifact);
					}
					if (UMLPackage.eINSTANCE.getClassifier_UseCase().equals(msg.getFeature()))
						basePart.updateUseCase(artifact);
					if (UMLPackage.eINSTANCE.getArtifact_FileName().equals(msg.getFeature()) && basePart != null)
						basePart.setFileName((String) msg.getNewValue());

					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getArtifact_NestedArtifact() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getArtifact())) {
						basePart.updateNestedArtifact(artifact);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getArtifact_Manifestation() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getManifestation())) {
						basePart.updateManifestation(artifact);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getArtifact_OwnedOperation() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getOperation())) {
						basePart.updateOwnedOperation(artifact);
					}
					if (msg.getFeature() != null
							&& (((EStructuralFeature) msg.getFeature()) == UMLPackage.eINSTANCE
									.getArtifact_OwnedAttribute() || ((EStructuralFeature) msg.getFeature())
									.getEContainingClass() == UMLPackage.eINSTANCE.getProperty())) {
						basePart.updateOwnedAttribute(artifact);
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
			return UMLViewsRepository.Artifact.class;
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
		if (artifact != null && BASE_PART.equals(key)) {
			if (basePart == null) {
				IPropertiesEditionPartProvider provider = PropertiesEditionPartProviderService.getInstance()
						.getProvider(UMLViewsRepository.class);
				if (provider != null) {
					basePart = (ArtifactPropertiesEditionPart) provider.getPropertiesEditionPart(
							UMLViewsRepository.Artifact.class, kind, this);
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
		if (key == UMLViewsRepository.Artifact.class)
			this.basePart = (ArtifactPropertiesEditionPart) propertiesEditionPart;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent#initPart(java.lang.Class,
	 *      int, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.resource.ResourceSet)
	 */
	public void initPart(java.lang.Class key, int kind, EObject elt, ResourceSet allResource) {
		if (basePart != null && key == UMLViewsRepository.Artifact.class) {
			((IPropertiesEditionPart) basePart).setContext(elt, allResource);
			Artifact artifact = (Artifact) elt;
			// init values
			basePart.initOwnedComment(artifact, null, UMLPackage.eINSTANCE.getElement_OwnedComment());
			if (artifact.getName() != null)
				basePart.setName(artifact.getName());

			basePart.initVisibility((EEnum) UMLPackage.eINSTANCE.getNamedElement_Visibility().getEType(), artifact
					.getVisibility());
			basePart.initClientDependency(artifact, null, UMLPackage.eINSTANCE.getNamedElement_ClientDependency());
			basePart.initElementImport(artifact, null, UMLPackage.eINSTANCE.getNamespace_ElementImport());
			basePart.initPackageImport(artifact, null, UMLPackage.eINSTANCE.getNamespace_PackageImport());
			basePart.initOwnedRule(artifact, null, UMLPackage.eINSTANCE.getNamespace_OwnedRule());
			basePart.setIsLeaf(artifact.isLeaf());

			basePart.initTemplateBinding(artifact, null, UMLPackage.eINSTANCE.getTemplateableElement_TemplateBinding());
			basePart.setIsAbstract(artifact.isAbstract());

			basePart.initGeneralization(artifact, null, UMLPackage.eINSTANCE.getClassifier_Generalization());
			basePart.initPowertypeExtent(artifact, null, UMLPackage.eINSTANCE.getClassifier_PowertypeExtent());
			basePart.initRedefinedClassifier(artifact, null, UMLPackage.eINSTANCE.getClassifier_RedefinedClassifier());
			basePart.initSubstitution(artifact, null, UMLPackage.eINSTANCE.getClassifier_Substitution());
			basePart.initCollaborationUse(artifact, null, UMLPackage.eINSTANCE.getClassifier_CollaborationUse());
			basePart.initOwnedUseCase(artifact, null, UMLPackage.eINSTANCE.getClassifier_OwnedUseCase());
			basePart.initUseCase(artifact, null, UMLPackage.eINSTANCE.getClassifier_UseCase());
			if (artifact.getFileName() != null)
				basePart.setFileName(artifact.getFileName());

			basePart.initNestedArtifact(artifact, null, UMLPackage.eINSTANCE.getArtifact_NestedArtifact());
			basePart.initManifestation(artifact, null, UMLPackage.eINSTANCE.getArtifact_Manifestation());
			basePart.initOwnedOperation(artifact, null, UMLPackage.eINSTANCE.getArtifact_OwnedOperation());
			basePart.initOwnedAttribute(artifact, null, UMLPackage.eINSTANCE.getArtifact_OwnedAttribute());

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

			basePart.addFilterToNestedArtifact(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof Artifact); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for nestedArtifact

			// End of user code
			basePart.addFilterToManifestation(new ViewerFilter() {

				/*
				 * (non-Javadoc)
				 * 
				 * @see
				 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer,
				 * java.lang.Object, java.lang.Object)
				 */
				public boolean select(Viewer viewer, Object parentElement, Object element) {
					return (element instanceof String && element.equals("")) || (element instanceof Manifestation); //$NON-NLS-1$ 

				}

			});
			// Start of user code for additional businessfilters for manifestation

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
		if (artifact != null) {
			List ownedCommentToAddFromOwnedComment = basePart.getOwnedCommentToAdd();
			for (Iterator iter = ownedCommentToAddFromOwnedComment.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getElement_OwnedComment(),
						iter.next()));
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
				cc.append(MoveCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getComment(), moveElement
						.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getNamedElement_Name(), basePart
					.getName()));

			cc.append(SetCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getNamedElement_Visibility(),
					basePart.getVisibility()));

			List clientDependencyToAddFromClientDependency = basePart.getClientDependencyToAdd();
			for (Iterator iter = clientDependencyToAddFromClientDependency.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE
						.getNamedElement_ClientDependency(), iter.next()));
			List clientDependencyToRemoveFromClientDependency = basePart.getClientDependencyToRemove();
			for (Iterator iter = clientDependencyToRemoveFromClientDependency.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE
						.getNamedElement_ClientDependency(), iter.next()));
			// List clientDependencyToMoveFromClientDependency =
			// basePart.getClientDependencyToMove();
			// for (Iterator iter = clientDependencyToMoveFromClientDependency.iterator();
			// iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, artifact,
			// UMLPackage.eINSTANCE.getDependency(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List elementImportToAddFromElementImport = basePart.getElementImportToAdd();
			for (Iterator iter = elementImportToAddFromElementImport.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getNamespace_ElementImport(),
						iter.next()));
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
				cc.append(MoveCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getElementImport(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List packageImportToAddFromPackageImport = basePart.getPackageImportToAdd();
			for (Iterator iter = packageImportToAddFromPackageImport.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getNamespace_PackageImport(),
						iter.next()));
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
				cc.append(MoveCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getPackageImport(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List ownedRuleToAddFromOwnedRule = basePart.getOwnedRuleToAdd();
			for (Iterator iter = ownedRuleToAddFromOwnedRule.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getNamespace_OwnedRule(),
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
				cc.append(MoveCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getConstraint(), moveElement
						.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf(),
					basePart.getIsLeaf()));

			List templateBindingToAddFromTemplateBinding = basePart.getTemplateBindingToAdd();
			for (Iterator iter = templateBindingToAddFromTemplateBinding.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE
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
				cc.append(MoveCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getTemplateBinding(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			cc.append(SetCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getClassifier_IsAbstract(),
					basePart.getIsAbstract()));

			List generalizationToAddFromGeneralization = basePart.getGeneralizationToAdd();
			for (Iterator iter = generalizationToAddFromGeneralization.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE
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
				cc.append(MoveCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getGeneralization(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List powertypeExtentToAddFromPowertypeExtent = basePart.getPowertypeExtentToAdd();
			for (Iterator iter = powertypeExtentToAddFromPowertypeExtent.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE
						.getClassifier_PowertypeExtent(), iter.next()));
			List powertypeExtentToRemoveFromPowertypeExtent = basePart.getPowertypeExtentToRemove();
			for (Iterator iter = powertypeExtentToRemoveFromPowertypeExtent.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE
						.getClassifier_PowertypeExtent(), iter.next()));
			// List powertypeExtentToMoveFromPowertypeExtent = basePart.getPowertypeExtentToMove();
			// for (Iterator iter = powertypeExtentToMoveFromPowertypeExtent.iterator();
			// iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, artifact,
			// UMLPackage.eINSTANCE.getGeneralizationSet(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List redefinedClassifierToAddFromRedefinedClassifier = basePart.getRedefinedClassifierToAdd();
			for (Iterator iter = redefinedClassifierToAddFromRedefinedClassifier.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE
						.getClassifier_RedefinedClassifier(), iter.next()));
			List redefinedClassifierToRemoveFromRedefinedClassifier = basePart.getRedefinedClassifierToRemove();
			for (Iterator iter = redefinedClassifierToRemoveFromRedefinedClassifier.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE
						.getClassifier_RedefinedClassifier(), iter.next()));
			// List redefinedClassifierToMoveFromRedefinedClassifier =
			// basePart.getRedefinedClassifierToMove();
			// for (Iterator iter = redefinedClassifierToMoveFromRedefinedClassifier.iterator();
			// iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, artifact,
			// UMLPackage.eINSTANCE.getClassifier(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			List substitutionToAddFromSubstitution = basePart.getSubstitutionToAdd();
			for (Iterator iter = substitutionToAddFromSubstitution.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getClassifier_Substitution(),
						iter.next()));
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
				cc.append(MoveCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getSubstitution(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List collaborationUseToAddFromCollaborationUse = basePart.getCollaborationUseToAdd();
			for (Iterator iter = collaborationUseToAddFromCollaborationUse.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE
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
				cc.append(MoveCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getCollaborationUse(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List ownedUseCaseToAddFromOwnedUseCase = basePart.getOwnedUseCaseToAdd();
			for (Iterator iter = ownedUseCaseToAddFromOwnedUseCase.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getClassifier_OwnedUseCase(),
						iter.next()));
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
				cc.append(MoveCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getUseCase(), moveElement
						.getElement(), moveElement.getIndex()));
			}
			List useCaseToAddFromUseCase = basePart.getUseCaseToAdd();
			for (Iterator iter = useCaseToAddFromUseCase.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getClassifier_UseCase(), iter
						.next()));
			List useCaseToRemoveFromUseCase = basePart.getUseCaseToRemove();
			for (Iterator iter = useCaseToRemoveFromUseCase.iterator(); iter.hasNext();)
				cc.append(RemoveCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getClassifier_UseCase(),
						iter.next()));
			// List useCaseToMoveFromUseCase = basePart.getUseCaseToMove();
			// for (Iterator iter = useCaseToMoveFromUseCase.iterator(); iter.hasNext();){
			// org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement =
			// (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement)iter.next();
			// cc.append(MoveCommand.create(editingDomain, artifact,
			// UMLPackage.eINSTANCE.getUseCase(), moveElement.getElement(),
			// moveElement.getIndex()));
			// }
			cc.append(SetCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getArtifact_FileName(), basePart
					.getFileName()));

			List nestedArtifactToAddFromNestedArtifact = basePart.getNestedArtifactToAdd();
			for (Iterator iter = nestedArtifactToAddFromNestedArtifact.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getArtifact_NestedArtifact(),
						iter.next()));
			Map nestedArtifactToRefreshFromNestedArtifact = basePart.getNestedArtifactToEdit();
			for (Iterator iter = nestedArtifactToRefreshFromNestedArtifact.keySet().iterator(); iter.hasNext();) {

				// Start of user code for nestedArtifact reference refreshment from nestedArtifact

				Artifact nextElement = (Artifact) iter.next();
				Artifact nestedArtifact = (Artifact) nestedArtifactToRefreshFromNestedArtifact.get(nextElement);

				// End of user code

			}
			List nestedArtifactToRemoveFromNestedArtifact = basePart.getNestedArtifactToRemove();
			for (Iterator iter = nestedArtifactToRemoveFromNestedArtifact.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List nestedArtifactToMoveFromNestedArtifact = basePart.getNestedArtifactToMove();
			for (Iterator iter = nestedArtifactToMoveFromNestedArtifact.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getArtifact(), moveElement
						.getElement(), moveElement.getIndex()));
			}
			List manifestationToAddFromManifestation = basePart.getManifestationToAdd();
			for (Iterator iter = manifestationToAddFromManifestation.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getArtifact_Manifestation(),
						iter.next()));
			Map manifestationToRefreshFromManifestation = basePart.getManifestationToEdit();
			for (Iterator iter = manifestationToRefreshFromManifestation.keySet().iterator(); iter.hasNext();) {

				// Start of user code for manifestation reference refreshment from manifestation

				Manifestation nextElement = (Manifestation) iter.next();
				Manifestation manifestation = (Manifestation) manifestationToRefreshFromManifestation.get(nextElement);

				// End of user code

			}
			List manifestationToRemoveFromManifestation = basePart.getManifestationToRemove();
			for (Iterator iter = manifestationToRemoveFromManifestation.iterator(); iter.hasNext();)
				cc.append(DeleteCommand.create(editingDomain, iter.next()));
			List manifestationToMoveFromManifestation = basePart.getManifestationToMove();
			for (Iterator iter = manifestationToMoveFromManifestation.iterator(); iter.hasNext();) {
				org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement moveElement = (org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil.MoveElement) iter
						.next();
				cc.append(MoveCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getManifestation(),
						moveElement.getElement(), moveElement.getIndex()));
			}
			List ownedOperationToAddFromOwnedOperation = basePart.getOwnedOperationToAdd();
			for (Iterator iter = ownedOperationToAddFromOwnedOperation.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getArtifact_OwnedOperation(),
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
				cc.append(MoveCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getOperation(), moveElement
						.getElement(), moveElement.getIndex()));
			}
			List ownedAttributeToAddFromOwnedAttribute = basePart.getOwnedAttributeToAdd();
			for (Iterator iter = ownedAttributeToAddFromOwnedAttribute.iterator(); iter.hasNext();)
				cc.append(AddCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getArtifact_OwnedAttribute(),
						iter.next()));
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
				cc.append(MoveCommand.create(editingDomain, artifact, UMLPackage.eINSTANCE.getProperty(), moveElement
						.getElement(), moveElement.getIndex()));
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
		if (source instanceof Artifact) {
			Artifact artifactToUpdate = (Artifact) source;
			artifactToUpdate.getOwnedComments().addAll(basePart.getOwnedCommentToAdd());
			artifactToUpdate.setName(basePart.getName());

			artifactToUpdate.setVisibility((VisibilityKind) basePart.getVisibility());

			artifactToUpdate.getClientDependencies().addAll(basePart.getClientDependencyToAdd());
			artifactToUpdate.getElementImports().addAll(basePart.getElementImportToAdd());
			artifactToUpdate.getPackageImports().addAll(basePart.getPackageImportToAdd());
			artifactToUpdate.getOwnedRules().addAll(basePart.getOwnedRuleToAdd());
			artifactToUpdate.setIsLeaf(new Boolean(basePart.getIsLeaf()).booleanValue());

			artifactToUpdate.getTemplateBindings().addAll(basePart.getTemplateBindingToAdd());
			artifactToUpdate.setIsAbstract(new Boolean(basePart.getIsAbstract()).booleanValue());

			artifactToUpdate.getGeneralizations().addAll(basePart.getGeneralizationToAdd());
			artifactToUpdate.getPowertypeExtents().addAll(basePart.getPowertypeExtentToAdd());
			artifactToUpdate.getRedefinedClassifiers().addAll(basePart.getRedefinedClassifierToAdd());
			artifactToUpdate.getSubstitutions().addAll(basePart.getSubstitutionToAdd());
			artifactToUpdate.getCollaborationUses().addAll(basePart.getCollaborationUseToAdd());
			artifactToUpdate.getOwnedUseCases().addAll(basePart.getOwnedUseCaseToAdd());
			artifactToUpdate.getUseCases().addAll(basePart.getUseCaseToAdd());
			artifactToUpdate.setFileName(basePart.getFileName());

			artifactToUpdate.getNestedArtifacts().addAll(basePart.getNestedArtifactToAdd());
			artifactToUpdate.getManifestations().addAll(basePart.getManifestationToAdd());
			artifactToUpdate.getOwnedOperations().addAll(basePart.getOwnedOperationToAdd());
			artifactToUpdate.getOwnedAttributes().addAll(basePart.getOwnedAttributeToAdd());

			return artifactToUpdate;
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
			if (UMLViewsRepository.Artifact.ownedComment == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Comment oldValue = (Comment) event.getOldValue();
					Comment newValue = (Comment) event.getNewValue();

					// Start of user code for ownedComment live update command
					// TODO: Complete the artifact update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getElement_OwnedComment(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE.getComment(),
							event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Artifact.name == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
						.getNamedElement_Name(), event.getNewValue()));

			if (UMLViewsRepository.Artifact.visibility == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
						.getNamedElement_Visibility(), event.getNewValue()));

			if (UMLViewsRepository.Artifact.clientDependency == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getNamedElement_ClientDependency(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getNamedElement_ClientDependency(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getNamedElement_ClientDependency(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Artifact.elementImport == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					ElementImport oldValue = (ElementImport) event.getOldValue();
					ElementImport newValue = (ElementImport) event.getNewValue();

					// Start of user code for elementImport live update command
					// TODO: Complete the artifact update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getNamespace_ElementImport(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getElementImport(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Artifact.packageImport == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					PackageImport oldValue = (PackageImport) event.getOldValue();
					PackageImport newValue = (PackageImport) event.getNewValue();

					// Start of user code for packageImport live update command
					// TODO: Complete the artifact update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getNamespace_PackageImport(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getPackageImport(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Artifact.ownedRule == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Constraint oldValue = (Constraint) event.getOldValue();
					Constraint newValue = (Constraint) event.getNewValue();

					// Start of user code for ownedRule live update command
					// TODO: Complete the artifact update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getNamespace_OwnedRule(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, artifact,
							UMLPackage.eINSTANCE.getConstraint(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Artifact.isLeaf == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
						.getRedefinableElement_IsLeaf(), event.getNewValue()));

			if (UMLViewsRepository.Artifact.templateBinding == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					TemplateBinding oldValue = (TemplateBinding) event.getOldValue();
					TemplateBinding newValue = (TemplateBinding) event.getNewValue();

					// Start of user code for templateBinding live update command
					// TODO: Complete the artifact update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getTemplateableElement_TemplateBinding(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getTemplateBinding(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Artifact.isAbstract == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
						.getClassifier_IsAbstract(), event.getNewValue()));

			if (UMLViewsRepository.Artifact.generalization == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Generalization oldValue = (Generalization) event.getOldValue();
					Generalization newValue = (Generalization) event.getNewValue();

					// Start of user code for generalization live update command
					// TODO: Complete the artifact update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getClassifier_Generalization(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getGeneralization(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Artifact.powertypeExtent == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getClassifier_PowertypeExtent(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getClassifier_PowertypeExtent(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getClassifier_PowertypeExtent(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Artifact.redefinedClassifier == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getClassifier_RedefinedClassifier(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getClassifier_RedefinedClassifier(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getClassifier_RedefinedClassifier(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Artifact.substitution == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Substitution oldValue = (Substitution) event.getOldValue();
					Substitution newValue = (Substitution) event.getNewValue();

					// Start of user code for substitution live update command
					// TODO: Complete the artifact update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getClassifier_Substitution(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getSubstitution(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Artifact.collaborationUse == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					CollaborationUse oldValue = (CollaborationUse) event.getOldValue();
					CollaborationUse newValue = (CollaborationUse) event.getNewValue();

					// Start of user code for collaborationUse live update command
					// TODO: Complete the artifact update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getClassifier_CollaborationUse(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getCollaborationUse(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Artifact.ownedUseCase == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					UseCase oldValue = (UseCase) event.getOldValue();
					UseCase newValue = (UseCase) event.getNewValue();

					// Start of user code for ownedUseCase live update command
					// TODO: Complete the artifact update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getClassifier_OwnedUseCase(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE.getUseCase(),
							event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Artifact.useCase == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getClassifier_UseCase(), event.getNewValue()));
				if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(RemoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getClassifier_UseCase(), event.getNewValue()));
				if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getClassifier_UseCase(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Artifact.fileName == event.getAffectedEditor())
				command.append(SetCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
						.getArtifact_FileName(), event.getNewValue()));

			if (UMLViewsRepository.Artifact.nestedArtifact == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Artifact oldValue = (Artifact) event.getOldValue();
					Artifact newValue = (Artifact) event.getNewValue();

					// Start of user code for nestedArtifact live update command
					// TODO: Complete the artifact update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getArtifact_NestedArtifact(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE.getArtifact(),
							event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Artifact.manifestation == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Manifestation oldValue = (Manifestation) event.getOldValue();
					Manifestation newValue = (Manifestation) event.getNewValue();

					// Start of user code for manifestation live update command
					// TODO: Complete the artifact update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getArtifact_Manifestation(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getManifestation(), event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Artifact.ownedOperation == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Operation oldValue = (Operation) event.getOldValue();
					Operation newValue = (Operation) event.getNewValue();

					// Start of user code for ownedOperation live update command
					// TODO: Complete the artifact update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getArtifact_OwnedOperation(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE.getOperation(),
							event.getNewValue(), event.getNewIndex()));
			}
			if (UMLViewsRepository.Artifact.ownedAttribute == event.getAffectedEditor()) {
				if (PropertiesEditionEvent.SET == event.getKind()) {
					Property oldValue = (Property) event.getOldValue();
					Property newValue = (Property) event.getNewValue();

					// Start of user code for ownedAttribute live update command
					// TODO: Complete the artifact update command
					// End of user code

				} else if (PropertiesEditionEvent.ADD == event.getKind())
					command.append(AddCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE
							.getArtifact_OwnedAttribute(), event.getNewValue()));
				else if (PropertiesEditionEvent.REMOVE == event.getKind())
					command.append(DeleteCommand.create(liveEditingDomain, event.getNewValue()));
				else if (PropertiesEditionEvent.MOVE == event.getKind())
					command.append(MoveCommand.create(liveEditingDomain, artifact, UMLPackage.eINSTANCE.getProperty(),
							event.getNewValue(), event.getNewIndex()));
			}

			liveEditingDomain.getCommandStack().execute(command);
		} else if (PropertiesEditionEvent.CHANGE == event.getState()) {
			Diagnostic diag = this.validateValue(event);
			if (diag != null && diag.getSeverity() != Diagnostic.OK) {

				if (UMLViewsRepository.Artifact.name == event.getAffectedEditor())
					basePart.setMessageForName(diag.getMessage(), IMessageProvider.ERROR);

				if (UMLViewsRepository.Artifact.fileName == event.getAffectedEditor())
					basePart.setMessageForFileName(diag.getMessage(), IMessageProvider.ERROR);

			} else {

				if (UMLViewsRepository.Artifact.name == event.getAffectedEditor())
					basePart.unsetMessageForName();

				if (UMLViewsRepository.Artifact.fileName == event.getAffectedEditor())
					basePart.unsetMessageForFileName();

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
		return key == UMLViewsRepository.Artifact.isLeaf || key == UMLViewsRepository.Artifact.isAbstract;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.impl.components.StandardPropertiesEditionComponent#getHelpContent(java.lang.String,
	 *      int)
	 */
	public String getHelpContent(String key, int kind) {
		if (key == UMLViewsRepository.Artifact.ownedComment)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.name)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.visibility)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.clientDependency)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.elementImport)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.packageImport)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.ownedRule)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.isLeaf)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.templateBinding)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.isAbstract)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.generalization)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.powertypeExtent)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.redefinedClassifier)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.substitution)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.collaborationUse)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.ownedUseCase)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.useCase)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.fileName)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.nestedArtifact)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.manifestation)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.ownedOperation)
			return null; //$NON-NLS-1$
		if (key == UMLViewsRepository.Artifact.ownedAttribute)
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
			if (UMLViewsRepository.Artifact.name == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getNamedElement_Name()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getNamedElement_Name().getEAttributeType(),
						newValue);
			}
			if (UMLViewsRepository.Artifact.visibility == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getNamedElement_Visibility()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getNamedElement_Visibility()
						.getEAttributeType(), newValue);
			}
			if (UMLViewsRepository.Artifact.isLeaf == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getRedefinableElement_IsLeaf()
						.getEAttributeType(), newValue);
			}
			if (UMLViewsRepository.Artifact.isAbstract == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getClassifier_IsAbstract()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getClassifier_IsAbstract()
						.getEAttributeType(), newValue);
			}
			if (UMLViewsRepository.Artifact.fileName == event.getAffectedEditor()) {
				Object newValue = EcoreUtil.createFromString(UMLPackage.eINSTANCE.getArtifact_FileName()
						.getEAttributeType(), newStringValue);
				ret = Diagnostician.INSTANCE.validate(UMLPackage.eINSTANCE.getArtifact_FileName().getEAttributeType(),
						newValue);
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
			return Diagnostician.INSTANCE.validate(artifact);
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
			artifact.eAdapters().remove(semanticAdapter);
	}

}
