/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.ui.internal.viewpoint;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.representation.SiriusDiagramPrototype;

/**
 * Interface for ViewPrototype to get the result of the instantiation instead of a boolean
 * TODO : propose me to Papyrus
 */
public interface ExtendedViewPrototype<T extends EObject> {

	/**
	 *
	 * @param semanticOwner
	 *            the semantic owner of the instantiated view
	 * @param graphicalOwner
	 *            the graphical owner of the instantiated view
	 * @param name
	 *            the name of the instantiated view
	 * @param openCreatedView
	 *            if <code>true</code> the created view will be open
	 * @return
	 *         the instantiated view
	 */
	public SiriusDiagramPrototype instantiateOn(EObject semanticOwner, EObject graphicalOwner, String name, boolean openCreatedView);

}
