/*****************************************************************************
 * Copyright (c) 2019, 2020 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   Christian W. Damus - bug 569357
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.utils;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.emf.validation.DependencyValidationUtils;
import org.eclipse.papyrus.toolsmiths.validation.common.Activator;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker2;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.utils.MarkersManagementUtils;

/**
 * This allows to provide services about markers.
 * In this case, we can provide methods to create and delete markers.
 */
public class MarkersService {

	/**
	 * This allows to create a marker for a resource.
	 *
	 * @param resource
	 *            The resource where create the marker.
	 * @param type
	 *            The type of the marker validation.
	 * @param message
	 *            The message of the marker.
	 * @param severity
	 *            The severity of the marker.
	 * @return The created marker or <code>null</code> if there is an error.
	 */
	public static IMarker createMarker(final IResource resource, final String type, final String message, final int severity) {
		return MarkersManagementUtils.createMarker(resource, type, message, severity);
	}

	/**
	 * This allows to create a marker for a resource.
	 *
	 * @param resource
	 *            The resource where create the marker.
	 * @param type
	 *            The type of the marker validation.
	 * @return The created marker or <code>null</code> if there is an error.
	 */
	public static IMarker createMarker(final IResource resource, final String type) {
		return MarkersManagementUtils.createMarker(resource, type);
	}

	/**
	 * Create a problem marker from a {@code diagnostic}.
	 *
	 * @param resource
	 *            a resource on which to create the marker
	 * @param type
	 *            the marker type to create
	 * @param diagnostic
	 *            the diagnostic containing message and severity information as well as problem objects to specify as targets
	 *
	 * @return the marker
	 */
	public static IMarker createMarker(final IResource resource, final String type, Diagnostic diagnostic) {
		int severity = diagnostic.getSeverity() == IStatus.INFO
				? IMarker.SEVERITY_INFO
				: diagnostic.getSeverity() == IStatus.WARNING
						? IMarker.SEVERITY_WARNING
						: IMarker.SEVERITY_ERROR;

		IMarker result = createMarker(resource, type, IPluginChecker2.getMessage(diagnostic), severity);

		String target = null;
		StringBuilder related = null;
		String missingDependency = null;

		for (Object next : diagnostic.getData()) {
			if (next instanceof EObject) {
				String uri = String.valueOf(EcoreUtil.getURI((EObject) next));

				if (target == null) {
					target = uri;
				} else {
					if (related == null) {
						related = new StringBuilder();
					} else {
						related.append(' ');
					}

					// EMF decodes these when navigating the marker into the editor. The encoding
					// covers potential spaces within the URI, so that the space as list separator
					// will work
					related.append(URI.encodeFragment(uri, false));
				}
				break;
			} else if (next instanceof IPluginChecker2.MarkerAttribute) {
				IPluginChecker2.MarkerAttribute attr = (IPluginChecker2.MarkerAttribute) next;
				if (DependencyValidationUtils.MISSING_DEPENDENCIES.equals(attr.getName())) {
					missingDependency = (String) attr.getValue();
				}
			}
		}

		try {
			if (diagnostic.getSource() != null && !diagnostic.getSource().isBlank()) {
				result.setAttribute(IPluginChecker2.MARKER_ATTRIBUTE_DIAGNOSTIC_SOURCE, diagnostic.getSource());
			}
			if (target != null) {
				result.setAttribute(EValidator.URI_ATTRIBUTE, target);
			}
			if (related != null) {
				result.setAttribute(EValidator.RELATED_URIS_ATTRIBUTE, related.toString());
			}

			if (missingDependency != null) {
				result.setAttribute(DependencyValidationUtils.MISSING_DEPENDENCIES, missingDependency);
			}
		} catch (CoreException e) {
			Activator.log.error("Failed to set attributes of problem marker.", e);
		}

		return result;
	}

	/**
	 * This allows to delete markers of a resource.
	 *
	 * @param resource
	 *            The resource.
	 * @param type
	 *            The type of markers to delete.
	 */
	public static void deleteMarkers(final IResource resource, final String type) {
		MarkersManagementUtils.deleteMarkers(resource, type);
	}
}
