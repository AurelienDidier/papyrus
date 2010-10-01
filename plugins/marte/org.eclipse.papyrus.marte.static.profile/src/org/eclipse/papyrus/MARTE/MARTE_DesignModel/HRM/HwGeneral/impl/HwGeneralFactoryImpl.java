/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.papyrus.MARTE.MARTE_DesignModel.HRM.HwGeneral.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrus.MARTE.MARTE_DesignModel.HRM.HwGeneral.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HwGeneralFactoryImpl extends EFactoryImpl implements HwGeneralFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static HwGeneralFactory init() {
		try {
			HwGeneralFactory theHwGeneralFactory = (HwGeneralFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.eclipse.org/papyrus/HwGeneral/1"); 
			if (theHwGeneralFactory != null) {
				return theHwGeneralFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new HwGeneralFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HwGeneralFactoryImpl() {
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
			case HwGeneralPackage.HW_RESOURCE_SERVICE: return createHwResourceService();
			case HwGeneralPackage.HW_RESOURCE: return createHwResource();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HwResourceService createHwResourceService() {
		HwResourceServiceImpl hwResourceService = new HwResourceServiceImpl();
		return hwResourceService;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HwResource createHwResource() {
		HwResourceImpl hwResource = new HwResourceImpl();
		return hwResource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HwGeneralPackage getHwGeneralPackage() {
		return (HwGeneralPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static HwGeneralPackage getPackage() {
		return HwGeneralPackage.eINSTANCE;
	}

} //HwGeneralFactoryImpl
