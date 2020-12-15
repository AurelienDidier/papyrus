/*****************************************************************************
 * Copyright (c) 2020 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.elementtypes.internal.checkers;

import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.ELEMENTTYPES_EXTENSION_POINT_IDENTIFIER;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.utils.PluginErrorReporter;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Validation of the <tt>plugin.xml</tt> for element-type set configuration extensions.
 */
final class ElementTypesPluginXMLValidator {

	static final String CATEGORY = "element_types"; //$NON-NLS-1$
	static final String ELEMENT_TYPE_SET = "elementTypeSet"; //$NON-NLS-1$
	static final String CLIENT_CONTEXT_ID = "clientContextID"; //$NON-NLS-1$
	static final String PATH = "path"; //$NON-NLS-1$

	private final IFile modelFile;

	/**
	 * Initializes me with the model file that I validate.
	 *
	 * @param modelFile
	 *            the model file
	 */
	ElementTypesPluginXMLValidator(IFile modelFile) {
		super();

		this.modelFile = modelFile;
	}

	Optional<Element> matchExtension(Element element, String point, ElementTypeSetConfiguration model) {
		switch (point) {
		case ELEMENTTYPES_EXTENSION_POINT_IDENTIFIER:
			NodeList children = element.getElementsByTagName(ELEMENT_TYPE_SET);
			for (int i = 0; i < children.getLength(); i++) {
				Element typeSet = (Element) children.item(i);
				if (matchElementTypeSet(typeSet, model)) {
					return Optional.of(typeSet);
				}
			}
			break;
		default:
			break;
		}

		return Optional.empty();
	}

	boolean matchElementTypeSet(Element element, ElementTypeSetConfiguration model) {
		String path = element.getAttribute(PATH);
		return Objects.equals(path, modelFile.getProjectRelativePath().toString());
	}

	void checkExtension(Element element, String point, ElementTypeSetConfiguration model, PluginErrorReporter.ProblemReport problems) {
		switch (element.getTagName()) {
		case ELEMENT_TYPE_SET:
			String clientContextID = element.getAttribute(CLIENT_CONTEXT_ID);
			if (clientContextID == null || clientContextID.isBlank()) {
				problems.reportProblem(Diagnostic.ERROR, element, "Missing client context ID.", CATEGORY, null);
			}
			break;
		default:
			break;
		}
	}

}
