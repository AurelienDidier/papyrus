/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.query;

import org.eclipse.papyrus.emf.facet.efacet.core.IFacetManager;
import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;
import org.eclipse.papyrus.emf.facet.query.java.core.IJavaQuery2;
import org.eclipse.papyrus.emf.facet.query.java.core.IParameterValueList2;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.views.properties.toolsmiths.editor.actions.ToggleDataContextAction;

/**
 * A Query to test if the DataContextElements should be displayed in the UIEditor
 *
 * @author Camille Letavernier
 */
public class ShowContextQuery implements IJavaQuery2<Context, Boolean> {


	@Override
	public Boolean evaluate(Context source, IParameterValueList2 parameterValues, IFacetManager facetManager) throws DerivedTypedElementException {
		Boolean result = ToggleDataContextAction.showDataContext;
		return result;
	}

}
