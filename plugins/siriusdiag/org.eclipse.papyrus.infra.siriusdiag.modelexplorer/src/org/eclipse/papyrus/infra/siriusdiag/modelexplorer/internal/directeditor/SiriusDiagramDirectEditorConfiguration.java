/*****************************************************************************
 * Copyright (c) 2019 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Yupanqui Munoz (CEA LIST) yupanqui.munozjulho@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.siriusdiag.modelexplorer.internal.directeditor;

import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors.configuration.AbstractBasicDirectEditorConfiguration;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalizationPreferencesUtils;
import org.eclipse.papyrus.infra.internationalization.utils.utils.LabelInternationalizationUtils;
import org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.DocumentTemplate;

/**
 * This class provides a Specific direct editor configuration to rename Document.
 *
 */
public class SiriusDiagramDirectEditorConfiguration extends AbstractBasicDirectEditorConfiguration {


	/**
	 * This allows to determinate if the label is set and can be modified.
	 *
	 * @param objectToEdit
	 *            The object to edit.
	 * @return <code>true</code> if this is a label modification, <code>false</code> otherwise.
	 */
	public boolean isLabelSet(final Object objectToEdit) {
		boolean result = false;
		if (objectToEdit instanceof DocumentTemplate) {
			final String documentLabel = LabelInternationalizationUtils.getLabelWithoutSubstract(((DocumentTemplate) objectToEdit), true);
			result = null != documentLabel && LabelInternationalizationPreferencesUtils.getInternationalizationPreference(((DocumentTemplate) objectToEdit));
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTextToEdit(final Object objectToEdit) {
		String result = null;
		if (objectToEdit instanceof DocumentTemplate) {
			final String documentLabel = LabelInternationalizationUtils.getLabelWithoutSubstract(((DocumentTemplate) objectToEdit), true);
			if (null != documentLabel && LabelInternationalizationPreferencesUtils.getInternationalizationPreference(((DocumentTemplate) objectToEdit))) {
				result = documentLabel;
			} else {
				result = ((DocumentTemplate) objectToEdit).getName();
			}
		}

		return null != result ? result : super.getTextToEdit(objectToEdit);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IParser createDirectEditorParser() {
		return new SiriusDiagramDirectEditorParser(getTextToEdit(objectToEdit), isLabelSet(objectToEdit));
	}

}
