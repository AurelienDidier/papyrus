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

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.toolsmiths.plugin.builder.helper.StaticProfileHelper;
import org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants;
import org.eclipse.papyrus.uml.tools.utils.StaticProfileUtil;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.uml2.uml.Profile;

/**
 * Helper class with utility methods used by {@link IMarkerResolution marker resolutions}.
 */
public final class MarkerResolutionUtils {

	/**
	 * Returns the genmodel file for the profile for which the given marker was created.
	 */
	public static IFile getGenModelFile(IMarker marker) throws CoreException {
		IFile umlModelFile = getUMLModelFile(marker);
		if (umlModelFile == null) {
			return null;
		}

		IPath genModelFilePath = umlModelFile.getFullPath().removeFileExtension().addFileExtension("genmodel"); //$NON-NLS-1$
		return ResourcesPlugin.getWorkspace().getRoot().getFile(genModelFilePath);

	}

	/** Returns the UML model file of the profile for which this marker was created. */
	public static IFile getUMLModelFile(IMarker marker) throws CoreException {
		Optional<Profile> profileOptional = MarkerResolutionUtils.getProfile(marker);
		if (profileOptional.isEmpty()) {
			return null;
		}
		Profile profile = profileOptional.get();
		Resource umlModelResource = profile.eResource();
		return ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(umlModelResource.getURI().toPlatformString(true)));
	}

	/**
	 * Returns the stereotype URI of the profile for which the given marker was created.
	 *
	 * @see MarkerResolutionUtils#getProfile(IMarker)
	 */
	public static String getStereotypeUri(IMarker marker) {
		Optional<Profile> profile = getProfile(marker);
		if (profile.isEmpty()) {
			return null;
		}
		return getStereotypeUri(profile.get());

	}

	/** Returns the NS URI of the stereotype applied to the profile. */
	public static String getStereotypeUri(Profile profile) {
		return new StaticProfileUtil(profile).getDefinition().getNsURI();
	}

	/** Returns the profile name stored in the marker attribute {@link StaticProfilePluginErrorReporter#STATIC_PROFILE_MARKER_ATTRIBUTE} or <code>null</code> if the attribute does not exist. */
	public static String getProfileName(IMarker marker) {
		return marker.getAttribute(ProfilePluginValidationConstants.STATIC_PROFILE_MARKER_ATTRIBUTE, null);
	}

	/**
	 * Returns the {@link Profile} for which this marker was created.
	 *
	 * @see MarkerResolutionUtils#getProfileName(IMarker)
	 */
	public static Optional<Profile> getProfile(IMarker marker) {
		String profileName = getProfileName(marker);
		if (profileName == null || profileName.isBlank()) {
			return Optional.empty();
		}
		Map<IFile, List<Profile>> profiles = StaticProfileHelper.findStaticProfiles(marker.getResource().getProject(), true);
		return profiles.values().stream().flatMap(List::stream).filter(profile -> Objects.equals(profile.getName(), profileName)).findFirst();
	}

}
