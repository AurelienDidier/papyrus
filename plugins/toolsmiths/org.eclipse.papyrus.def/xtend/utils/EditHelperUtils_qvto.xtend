/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Florian Noyrit - Initial API and implementation
 * 
 *****************************************************************************/
package utils

import com.google.inject.Singleton
import org.eclipse.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.papyrus.papyrusgmfgenextension.AdditionalEditPartCandies

@Singleton class EditHelperUtils_qvto {

	def String getBaseEditHelperFullName(GenDiagram diagram) {
		if(!(diagram.eResource.allContents.filter(typeof(AdditionalEditPartCandies)).empty)) {
			return diagram.eResource.allContents.filter(typeof(AdditionalEditPartCandies)).head.baseEditHelperPackage + "." + diagram.baseEditHelperClassName
		} else {
			return diagram.getBaseEditHelperQualifiedClassName();
		}

	}
}
