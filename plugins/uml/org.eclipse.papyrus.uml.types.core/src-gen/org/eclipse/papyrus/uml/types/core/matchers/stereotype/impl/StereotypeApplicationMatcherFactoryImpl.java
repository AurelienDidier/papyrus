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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrus.uml.types.core.matchers.stereotype.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class StereotypeApplicationMatcherFactoryImpl extends EFactoryImpl implements StereotypeApplicationMatcherFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static StereotypeApplicationMatcherFactory init() {
		try {
			StereotypeApplicationMatcherFactory theStereotypeApplicationMatcherFactory = (StereotypeApplicationMatcherFactory)EPackage.Registry.INSTANCE.getEFactory(StereotypeApplicationMatcherPackage.eNS_URI);
			if (theStereotypeApplicationMatcherFactory != null) {
				return theStereotypeApplicationMatcherFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new StereotypeApplicationMatcherFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StereotypeApplicationMatcherFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case StereotypeApplicationMatcherPackage.STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION: return createStereotypeApplicationMatcherConfiguration();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StereotypeApplicationMatcherConfiguration createStereotypeApplicationMatcherConfiguration() {
		StereotypeApplicationMatcherConfigurationImpl stereotypeApplicationMatcherConfiguration = new StereotypeApplicationMatcherConfigurationImpl();
		return stereotypeApplicationMatcherConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StereotypeApplicationMatcherPackage getStereotypeApplicationMatcherPackage() {
		return (StereotypeApplicationMatcherPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static StereotypeApplicationMatcherPackage getPackage() {
		return StereotypeApplicationMatcherPackage.eINSTANCE;
	}

} //StereotypeApplicationMatcherFactoryImpl
