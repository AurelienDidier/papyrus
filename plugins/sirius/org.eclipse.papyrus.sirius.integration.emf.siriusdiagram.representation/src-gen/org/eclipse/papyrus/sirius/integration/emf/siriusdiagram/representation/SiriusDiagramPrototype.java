/**
 * Copyright (c) 2019 CEA LIST.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.representation;

import java.util.Map;

import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Papyrus Document Prototype</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * This class allows to reference the SiriusDiagramPrototype to use to create a new SiriusDiagram
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.representation.SiriusDiagramPrototype#getSiriusDiagramPrototype <em>Document Template Prototype</em>}</li>
 * <li>{@link org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.representation.SiriusDiagramPrototype#getCreationCommandClass <em>Creation Command Class</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.representation.RepresentationPackage#getPapyrusDocumentPrototype()
 * @model
 * @generated
 */
public interface SiriusDiagramPrototype extends PapyrusRepresentationKind {
	/**
	 * Returns the value of the '<em><b>Document Template Prototype</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Document Template Prototype</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This feature references the SiriusDiagramPrototype to use to create a new SiriusDiagram
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Document Template Prototype</em>' reference.
	 * @see #setSiriusDiagramPrototype(SiriusDiagramPrototype)
	 * @see org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.representation.RepresentationPackage#getPapyrusDocumentPrototype_SiriusDiagramPrototype()
	 * @model required="true"
	 * @generated
	 */
	SiriusDiagramPrototype getSiriusDiagramPrototype();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.representation.SiriusDiagramPrototype#getSiriusDiagramPrototype <em>Document Template Prototype</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Document Template Prototype</em>' reference.
	 * @see #getSiriusDiagramPrototype()
	 * @generated
	 */
	void setSiriusDiagramPrototype(SiriusDiagramPrototype value);

	/**
	 * Returns the value of the '<em><b>Creation Command Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * This feature allows to define the class to use to create the new document. The class must implements ICreateSiriusDiagramEditorCommand.
	 * <!-- end-model-doc -->
	 *
	 * @return the value of the '<em>Creation Command Class</em>' attribute.
	 * @see #setCreationCommandClass(String)
	 * @see org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.representation.RepresentationPackage#getPapyrusDocumentPrototype_CreationCommandClass()
	 * @model required="true"
	 * @generated
	 */
	String getCreationCommandClass();

	/**
	 * Sets the value of the '{@link org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.representation.SiriusDiagramPrototype#getCreationCommandClass <em>Creation Command Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param value
	 *            the new value of the '<em>Creation Command Class</em>' attribute.
	 * @see #getCreationCommandClass()
	 * @generated
	 */
	void setCreationCommandClass(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * this methid is used by the emf validation framework
	 * <!-- end-model-doc -->
	 *
	 * @model
	 * @generated
	 */
	boolean isValidClass(DiagnosticChain chain, Map<Object, Object> context);

} // PapyrusDocumentPrototype
