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
package org.eclipse.papyrus.papyrusgmfgenextension.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.gmf.codegen.gmfgen.GenCompartment;

import org.eclipse.papyrus.papyrusgmfgenextension.CompartmentTitleVisibilityPreference;
import org.eclipse.papyrus.papyrusgmfgenextension.PapyrusgmfgenextensionPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Compartment Title Visibility Preference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.papyrusgmfgenextension.impl.CompartmentTitleVisibilityPreferenceImpl#isVisibleByDefault <em>Visible By Default</em>}</li>
 *   <li>{@link org.eclipse.papyrus.papyrusgmfgenextension.impl.CompartmentTitleVisibilityPreferenceImpl#getCompartments <em>Compartments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CompartmentTitleVisibilityPreferenceImpl extends CommentedElementImpl implements CompartmentTitleVisibilityPreference {
	/**
	 * The default value of the '{@link #isVisibleByDefault() <em>Visible By Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVisibleByDefault()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VISIBLE_BY_DEFAULT_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isVisibleByDefault() <em>Visible By Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVisibleByDefault()
	 * @generated
	 * @ordered
	 */
	protected boolean visibleByDefault = VISIBLE_BY_DEFAULT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCompartments() <em>Compartments</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompartments()
	 * @generated
	 * @ordered
	 */
	protected EList<GenCompartment> compartments;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CompartmentTitleVisibilityPreferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PapyrusgmfgenextensionPackage.Literals.COMPARTMENT_TITLE_VISIBILITY_PREFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isVisibleByDefault() {
		return visibleByDefault;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVisibleByDefault(boolean newVisibleByDefault) {
		boolean oldVisibleByDefault = visibleByDefault;
		visibleByDefault = newVisibleByDefault;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PapyrusgmfgenextensionPackage.COMPARTMENT_TITLE_VISIBILITY_PREFERENCE__VISIBLE_BY_DEFAULT, oldVisibleByDefault, visibleByDefault));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<GenCompartment> getCompartments() {
		if (compartments == null) {
			compartments = new EObjectResolvingEList<GenCompartment>(GenCompartment.class, this, PapyrusgmfgenextensionPackage.COMPARTMENT_TITLE_VISIBILITY_PREFERENCE__COMPARTMENTS);
		}
		return compartments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PapyrusgmfgenextensionPackage.COMPARTMENT_TITLE_VISIBILITY_PREFERENCE__VISIBLE_BY_DEFAULT:
				return isVisibleByDefault();
			case PapyrusgmfgenextensionPackage.COMPARTMENT_TITLE_VISIBILITY_PREFERENCE__COMPARTMENTS:
				return getCompartments();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case PapyrusgmfgenextensionPackage.COMPARTMENT_TITLE_VISIBILITY_PREFERENCE__VISIBLE_BY_DEFAULT:
				setVisibleByDefault((Boolean)newValue);
				return;
			case PapyrusgmfgenextensionPackage.COMPARTMENT_TITLE_VISIBILITY_PREFERENCE__COMPARTMENTS:
				getCompartments().clear();
				getCompartments().addAll((Collection<? extends GenCompartment>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case PapyrusgmfgenextensionPackage.COMPARTMENT_TITLE_VISIBILITY_PREFERENCE__VISIBLE_BY_DEFAULT:
				setVisibleByDefault(VISIBLE_BY_DEFAULT_EDEFAULT);
				return;
			case PapyrusgmfgenextensionPackage.COMPARTMENT_TITLE_VISIBILITY_PREFERENCE__COMPARTMENTS:
				getCompartments().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case PapyrusgmfgenextensionPackage.COMPARTMENT_TITLE_VISIBILITY_PREFERENCE__VISIBLE_BY_DEFAULT:
				return visibleByDefault != VISIBLE_BY_DEFAULT_EDEFAULT;
			case PapyrusgmfgenextensionPackage.COMPARTMENT_TITLE_VISIBILITY_PREFERENCE__COMPARTMENTS:
				return compartments != null && !compartments.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (visibleByDefault: ");
		result.append(visibleByDefault);
		result.append(')');
		return result.toString();
	}

} //CompartmentTitleVisibilityPreferenceImpl
