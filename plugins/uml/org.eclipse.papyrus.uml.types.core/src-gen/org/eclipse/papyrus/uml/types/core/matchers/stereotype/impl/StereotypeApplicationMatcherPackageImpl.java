/**
 * Copyright (c) 2014 CEA LIST.
 * 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherFactory;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class StereotypeApplicationMatcherPackageImpl extends EPackageImpl implements StereotypeApplicationMatcherPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stereotypeApplicationMatcherConfigurationEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private StereotypeApplicationMatcherPackageImpl() {
		super(eNS_URI, StereotypeApplicationMatcherFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link StereotypeApplicationMatcherPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static StereotypeApplicationMatcherPackage init() {
		if (isInited) return (StereotypeApplicationMatcherPackage)EPackage.Registry.INSTANCE.getEPackage(StereotypeApplicationMatcherPackage.eNS_URI);

		// Obtain or create and register package
		StereotypeApplicationMatcherPackageImpl theStereotypeApplicationMatcherPackage = (StereotypeApplicationMatcherPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof StereotypeApplicationMatcherPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new StereotypeApplicationMatcherPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		ElementTypesConfigurationsPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theStereotypeApplicationMatcherPackage.createPackageContents();

		// Initialize created meta-data
		theStereotypeApplicationMatcherPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theStereotypeApplicationMatcherPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(StereotypeApplicationMatcherPackage.eNS_URI, theStereotypeApplicationMatcherPackage);
		return theStereotypeApplicationMatcherPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStereotypeApplicationMatcherConfiguration() {
		return stereotypeApplicationMatcherConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStereotypeApplicationMatcherConfiguration_StereotypesQualifiedNames() {
		return (EAttribute)stereotypeApplicationMatcherConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStereotypeApplicationMatcherConfiguration_ProfileUri() {
		return (EAttribute)stereotypeApplicationMatcherConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StereotypeApplicationMatcherFactory getStereotypeApplicationMatcherFactory() {
		return (StereotypeApplicationMatcherFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		stereotypeApplicationMatcherConfigurationEClass = createEClass(STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION);
		createEAttribute(stereotypeApplicationMatcherConfigurationEClass, STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES);
		createEAttribute(stereotypeApplicationMatcherConfigurationEClass, STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__PROFILE_URI);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		ElementTypesConfigurationsPackage theElementTypesConfigurationsPackage = (ElementTypesConfigurationsPackage)EPackage.Registry.INSTANCE.getEPackage(ElementTypesConfigurationsPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		stereotypeApplicationMatcherConfigurationEClass.getESuperTypes().add(theElementTypesConfigurationsPackage.getAbstractMatcherConfiguration());

		// Initialize classes, features, and operations; add parameters
		initEClass(stereotypeApplicationMatcherConfigurationEClass, StereotypeApplicationMatcherConfiguration.class, "StereotypeApplicationMatcherConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStereotypeApplicationMatcherConfiguration_StereotypesQualifiedNames(), theEcorePackage.getEString(), "stereotypesQualifiedNames", null, 1, -1, StereotypeApplicationMatcherConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStereotypeApplicationMatcherConfiguration_ProfileUri(), ecorePackage.getEString(), "profileUri", null, 0, 1, StereotypeApplicationMatcherConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //StereotypeApplicationMatcherPackageImpl
