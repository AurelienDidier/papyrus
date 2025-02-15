/**
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   CEA LIST - Initial API and implementation
 * 
 */
package org.eclipse.papyrus.papyrusgmfgenextension.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.papyrus.papyrusgmfgenextension.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.papyrusgmfgenextension.PapyrusgmfgenextensionPackage
 * @generated
 */
public class PapyrusgmfgenextensionAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static PapyrusgmfgenextensionPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PapyrusgmfgenextensionAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = PapyrusgmfgenextensionPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PapyrusgmfgenextensionSwitch<Adapter> modelSwitch =
		new PapyrusgmfgenextensionSwitch<Adapter>() {
			@Override
			public Adapter caseExtendedGenView(ExtendedGenView object) {
				return createExtendedGenViewAdapter();
			}
			@Override
			public Adapter caseCommentedElement(CommentedElement object) {
				return createCommentedElementAdapter();
			}
			@Override
			public Adapter casePropertyRefreshHook(PropertyRefreshHook object) {
				return createPropertyRefreshHookAdapter();
			}
			@Override
			public Adapter caseExternalHook(ExternalHook object) {
				return createExternalHookAdapter();
			}
			@Override
			public Adapter caseSpecificLocator(SpecificLocator object) {
				return createSpecificLocatorAdapter();
			}
			@Override
			public Adapter casePapyrusExtensionRootNode(PapyrusExtensionRootNode object) {
				return createPapyrusExtensionRootNodeAdapter();
			}
			@Override
			public Adapter caseAlternateCanvas(AlternateCanvas object) {
				return createAlternateCanvasAdapter();
			}
			@Override
			public Adapter caseAlternateGenTopLevelNode(AlternateGenTopLevelNode object) {
				return createAlternateGenTopLevelNodeAdapter();
			}
			@Override
			public Adapter caseAlternateGenLink(AlternateGenLink object) {
				return createAlternateGenLinkAdapter();
			}
			@Override
			public Adapter caseMutatingCanvas(MutatingCanvas object) {
				return createMutatingCanvasAdapter();
			}
			@Override
			public Adapter caseOwnedEditpart(OwnedEditpart object) {
				return createOwnedEditpartAdapter();
			}
			@Override
			public Adapter caseSpecificDiagramUpdater(SpecificDiagramUpdater object) {
				return createSpecificDiagramUpdaterAdapter();
			}
			@Override
			public Adapter caseGenNodeConstraint(GenNodeConstraint object) {
				return createGenNodeConstraintAdapter();
			}
			@Override
			public Adapter caseSpecificLocatorExternalLabel(SpecificLocatorExternalLabel object) {
				return createSpecificLocatorExternalLabelAdapter();
			}
			@Override
			public Adapter caseAdditionalEditPartCandies(AdditionalEditPartCandies object) {
				return createAdditionalEditPartCandiesAdapter();
			}
			@Override
			public Adapter caseEditPartUsingDeleteService(EditPartUsingDeleteService object) {
				return createEditPartUsingDeleteServiceAdapter();
			}
			@Override
			public Adapter caseEditPartUsingReorientService(EditPartUsingReorientService object) {
				return createEditPartUsingReorientServiceAdapter();
			}
			@Override
			public Adapter caseLabelVisibilityPreference(LabelVisibilityPreference object) {
				return createLabelVisibilityPreferenceAdapter();
			}
			@Override
			public Adapter caseCompartmentVisibilityPreference(CompartmentVisibilityPreference object) {
				return createCompartmentVisibilityPreferenceAdapter();
			}
			@Override
			public Adapter caseCompartmentTitleVisibilityPreference(CompartmentTitleVisibilityPreference object) {
				return createCompartmentTitleVisibilityPreferenceAdapter();
			}
			@Override
			public Adapter caseConstrainedByReferenceCompartmentItemSemanticEditPolicy(ConstrainedByReferenceCompartmentItemSemanticEditPolicy object) {
				return createConstrainedByReferenceCompartmentItemSemanticEditPolicyAdapter();
			}
			@Override
			public Adapter caseGenerateUsingElementTypeCreationCommand(GenerateUsingElementTypeCreationCommand object) {
				return createGenerateUsingElementTypeCreationCommandAdapter();
			}
			@Override
			public Adapter caseCustomDiagramUpdaterSingleton(CustomDiagramUpdaterSingleton object) {
				return createCustomDiagramUpdaterSingletonAdapter();
			}
			@Override
			public Adapter caseSpecificNodePlate(SpecificNodePlate object) {
				return createSpecificNodePlateAdapter();
			}
			@Override
			public Adapter caseGenVisualTypeProvider(GenVisualTypeProvider object) {
				return createGenVisualTypeProviderAdapter();
			}
			@Override
			public Adapter caseVisualIDOverride(VisualIDOverride object) {
				return createVisualIDOverrideAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.ExtendedGenView <em>Extended Gen View</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.ExtendedGenView
	 * @generated
	 */
	public Adapter createExtendedGenViewAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.CommentedElement <em>Commented Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.CommentedElement
	 * @generated
	 */
	public Adapter createCommentedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.PropertyRefreshHook <em>Property Refresh Hook</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.PropertyRefreshHook
	 * @generated
	 */
	public Adapter createPropertyRefreshHookAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.ExternalHook <em>External Hook</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.ExternalHook
	 * @generated
	 */
	public Adapter createExternalHookAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.SpecificLocator <em>Specific Locator</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.SpecificLocator
	 * @generated
	 */
	public Adapter createSpecificLocatorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.PapyrusExtensionRootNode <em>Papyrus Extension Root Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.PapyrusExtensionRootNode
	 * @generated
	 */
	public Adapter createPapyrusExtensionRootNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.AlternateCanvas <em>Alternate Canvas</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.AlternateCanvas
	 * @generated
	 */
	public Adapter createAlternateCanvasAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.AlternateGenTopLevelNode <em>Alternate Gen Top Level Node</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.AlternateGenTopLevelNode
	 * @generated
	 */
	public Adapter createAlternateGenTopLevelNodeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.AlternateGenLink <em>Alternate Gen Link</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.AlternateGenLink
	 * @generated
	 */
	public Adapter createAlternateGenLinkAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.MutatingCanvas <em>Mutating Canvas</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.MutatingCanvas
	 * @generated
	 */
	public Adapter createMutatingCanvasAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.OwnedEditpart <em>Owned Editpart</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.OwnedEditpart
	 * @generated
	 */
	public Adapter createOwnedEditpartAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.SpecificDiagramUpdater <em>Specific Diagram Updater</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.SpecificDiagramUpdater
	 * @generated
	 */
	public Adapter createSpecificDiagramUpdaterAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.GenNodeConstraint <em>Gen Node Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.GenNodeConstraint
	 * @generated
	 */
	public Adapter createGenNodeConstraintAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.SpecificLocatorExternalLabel <em>Specific Locator External Label</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.SpecificLocatorExternalLabel
	 * @generated
	 */
	public Adapter createSpecificLocatorExternalLabelAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.AdditionalEditPartCandies <em>Additional Edit Part Candies</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.AdditionalEditPartCandies
	 * @generated
	 */
	public Adapter createAdditionalEditPartCandiesAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.EditPartUsingDeleteService <em>Edit Part Using Delete Service</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.EditPartUsingDeleteService
	 * @generated
	 */
	public Adapter createEditPartUsingDeleteServiceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.EditPartUsingReorientService <em>Edit Part Using Reorient Service</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.EditPartUsingReorientService
	 * @generated
	 */
	public Adapter createEditPartUsingReorientServiceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.LabelVisibilityPreference <em>Label Visibility Preference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.LabelVisibilityPreference
	 * @generated
	 */
	public Adapter createLabelVisibilityPreferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.CompartmentVisibilityPreference <em>Compartment Visibility Preference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.CompartmentVisibilityPreference
	 * @generated
	 */
	public Adapter createCompartmentVisibilityPreferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.CompartmentTitleVisibilityPreference <em>Compartment Title Visibility Preference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.CompartmentTitleVisibilityPreference
	 * @generated
	 */
	public Adapter createCompartmentTitleVisibilityPreferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.ConstrainedByReferenceCompartmentItemSemanticEditPolicy <em>Constrained By Reference Compartment Item Semantic Edit Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.ConstrainedByReferenceCompartmentItemSemanticEditPolicy
	 * @generated
	 */
	public Adapter createConstrainedByReferenceCompartmentItemSemanticEditPolicyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.GenerateUsingElementTypeCreationCommand <em>Generate Using Element Type Creation Command</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.GenerateUsingElementTypeCreationCommand
	 * @generated
	 */
	public Adapter createGenerateUsingElementTypeCreationCommandAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.CustomDiagramUpdaterSingleton <em>Custom Diagram Updater Singleton</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.CustomDiagramUpdaterSingleton
	 * @generated
	 */
	public Adapter createCustomDiagramUpdaterSingletonAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.SpecificNodePlate <em>Specific Node Plate</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.SpecificNodePlate
	 * @generated
	 */
	public Adapter createSpecificNodePlateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.GenVisualTypeProvider <em>Gen Visual Type Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.GenVisualTypeProvider
	 * @generated
	 */
	public Adapter createGenVisualTypeProviderAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.papyrusgmfgenextension.VisualIDOverride <em>Visual ID Override</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.VisualIDOverride
	 * @generated
	 */
	public Adapter createVisualIDOverrideAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //PapyrusgmfgenextensionAdapterFactory
