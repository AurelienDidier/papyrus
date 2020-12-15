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

import org.eclipse.core.resources.IMarker;
import org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants;
import org.eclipse.pde.internal.core.builders.PDEMarkerFactory;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

/**
 * Resolution generator for markers created by the validation of static profiles.
 *
 */
@SuppressWarnings("restriction")
public class StaticProfileMarkerResolutionGenerator implements IMarkerResolutionGenerator {
	private static IMarkerResolution[] NO_RESOLUTIONS = new IMarkerResolution[0];

	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		int problemId = marker.getAttribute(PDEMarkerFactory.PROBLEM_ID, PDEMarkerFactory.NO_RESOLUTION);
		switch (problemId) {
		case ProfilePluginValidationConstants.NO_GENMODEL_MARKER_ID:
			return new IMarkerResolution[] { new MissingGenModelAttributeMarkerResolution() };
		case ProfilePluginValidationConstants.NO_URI_MARKER_ID:
			return new IMarkerResolution[] { new MissingUriAttributeMarkerResolution() };
		case ProfilePluginValidationConstants.NO_ECORE_GEN_PACKAGE_MARKER_ID:
			return new IMarkerResolution[] { new NoEcoreGenPackageMarkerResolution() };
		case ProfilePluginValidationConstants.NO_UML2_GEN_PACKAGE_MARKER_ID:
			return new IMarkerResolution[] { new NoUMLGenPackageMarkerResolution() };
		case ProfilePluginValidationConstants.NO_UML2_GEN_PACKAGE_LOCATION_MARKER_ID:
			return new IMarkerResolution[] { new NoLocationUMLGenPackageMarkerResolution() };
		case ProfilePluginValidationConstants.PAPYRUS_PROFILE_EXTENSION_NO_NAME_MARKER_ID:
			return new IMarkerResolution[] { new PapyrusProfileExtensionMissingNameMarkerResolution() };
		case ProfilePluginValidationConstants.NO_PAPYRUS_PROFILE_MARKER_ID:
			return new IMarkerResolution[] { new NoPapyrusProfileExtensionMarkerResolution() };
		case ProfilePluginValidationConstants.MISSING_FROM_BINARY_BUILD_MARKER_ID:
			return new IMarkerResolution[] { new ResourceMissingFromBinaryBuildMarkerResolution() };
		default:
			return NO_RESOLUTIONS;
		}
	}

}
