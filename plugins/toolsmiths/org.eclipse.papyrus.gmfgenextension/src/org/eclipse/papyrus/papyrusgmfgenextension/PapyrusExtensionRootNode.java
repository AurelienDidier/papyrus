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
package org.eclipse.papyrus.papyrusgmfgenextension;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Papyrus Extension Root Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.papyrusgmfgenextension.PapyrusExtensionRootNode#getExtensionNodes <em>Extension Nodes</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.papyrusgmfgenextension.PapyrusgmfgenextensionPackage#getPapyrusExtensionRootNode()
 * @model
 * @generated
 */
public interface PapyrusExtensionRootNode extends CommentedElement {
	/**
	 * Returns the value of the '<em><b>Extension Nodes</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.papyrusgmfgenextension.CommentedElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extension Nodes</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extension Nodes</em>' containment reference list.
	 * @see org.eclipse.papyrus.papyrusgmfgenextension.PapyrusgmfgenextensionPackage#getPapyrusExtensionRootNode_ExtensionNodes()
	 * @model containment="true"
	 * @generated
	 */
	EList<CommentedElement> getExtensionNodes();

} // PapyrusExtensionRootNode
