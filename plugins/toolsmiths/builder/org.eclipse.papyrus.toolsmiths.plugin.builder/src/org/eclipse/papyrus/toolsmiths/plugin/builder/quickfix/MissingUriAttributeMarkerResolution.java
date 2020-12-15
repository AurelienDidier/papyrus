/*****************************************************************************
 * Copyright (c) 2020 CEA LIST, EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Alexandra Buzila (EclipseSource) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.plugin.builder.quickfix;

import org.eclipse.papyrus.toolsmiths.plugin.builder.Messages;
import org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants;

/**
 * Resolution for markers created for missing genModel attributes in the extension point.
 *
 */
public class MissingUriAttributeMarkerResolution
		extends AbstractMissingAttributeMarkerResolution {

	MissingUriAttributeMarkerResolution() {
		super("uri"); //$NON-NLS-1$
	}

	@Override
	public String getLabel() {
		return Messages.MissingUriAttributeMarkerResolution_label;
	}

	@Override
	public String getDescription() {
		return Messages.MissingUriAttributeMarkerResolution_description;
	}

	@Override
	protected String getAttributeValue() {
		return getMarker().getAttribute(ProfilePluginValidationConstants.STATIC_PROFILE_STEREOTYPE_URI, ""); //$NON-NLS-1$
	}
}
