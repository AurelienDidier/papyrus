/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.siriusdiag.properties.internal;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.viewers.IFilter;
import org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.DocumentStructureTemplatePackage;

/**
 * Property section dedicated to element coming from DocumentStructureTemplate
 */
public class SiriusDiagramPackageSectionFilter implements IFilter {

	/**
	 *
	 * Constructor.
	 *
	 */
	public SiriusDiagramPackageSectionFilter() {
		// nothing to do
	}

	/**
	 *
	 * @see org.eclipse.jface.viewers.IFilter#select(java.lang.Object)
	 *
	 * @param toTest
	 * @return
	 */
	@Override
	public boolean select(final Object toTest) {
		if (false == toTest instanceof EObject) {
			return false;
		}
		final EObject eobject = (EObject) toTest;
		final EPackage epackage = eobject.eClass().getEPackage();
		return epackage == DocumentStructureTemplatePackage.eINSTANCE;
	}

}
