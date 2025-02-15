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
package org.eclipse.papyrus.infra.types;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Edit Helper Advice Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.AbstractEditHelperAdviceConfiguration#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getAbstractEditHelperAdviceConfiguration()
 * @model abstract="true"
 * @generated
 */
public interface AbstractEditHelperAdviceConfiguration extends AdviceConfiguration {
	/**
	 * Returns the value of the '<em><b>Target</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getEditHelperAdviceConfiguration <em>Edit Helper Advice Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' container reference.
	 * @see #setTarget(SpecializationTypeConfiguration)
	 * @see org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage#getAbstractEditHelperAdviceConfiguration_Target()
	 * @see org.eclipse.papyrus.infra.types.SpecializationTypeConfiguration#getEditHelperAdviceConfiguration
	 * @model opposite="editHelperAdviceConfiguration" required="true" transient="false"
	 * @generated
	 */
	SpecializationTypeConfiguration getTarget();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.infra.types.AbstractEditHelperAdviceConfiguration#getTarget <em>Target</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target</em>' container reference.
	 * @see #getTarget()
	 * @generated
	 */
	void setTarget(SpecializationTypeConfiguration value);

} // AbstractEditHelperAdviceConfiguration
