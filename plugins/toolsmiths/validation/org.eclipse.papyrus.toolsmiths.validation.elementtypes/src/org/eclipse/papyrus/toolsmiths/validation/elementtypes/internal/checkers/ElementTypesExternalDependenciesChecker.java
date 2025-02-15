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
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.elementtypes.internal.checkers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.URIMappingRegistryImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.MarkersService;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.ProjectManagementService;
import org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants;

/**
 * This class allows to check that the plug-in has the correct dependencies depending to the external profile references.
 */
public class ElementTypesExternalDependenciesChecker implements IPluginChecker {

	/**
	 * The plug-ins to detect as warning instead of errors.
	 * This can be filled.
	 */
	@SuppressWarnings("serial")
	private static Set<String> WARNING_PLUGINS_EXCEPTION = new HashSet<String>() {
		{
			add("org.eclipse.uml2.uml.resources"); //$NON-NLS-1$
		}
	};

	/**
	 * The current project resource.
	 */
	private final IProject project;

	/**
	 * The file defining element types.
	 */
	private final IFile elementTypesFile;

	/**
	 * The EMF resource.
	 */
	private final Resource resource;

	/**
	 * Constructor.
	 *
	 * @param project
	 *            The current project resource.
	 * @param elementTypesFile
	 *            The file defining element types.
	 * @param resource
	 *            The EMF resource.
	 */
	public ElementTypesExternalDependenciesChecker(final IProject project, final IFile elementTypesFile, final Resource resource) {
		this.project = project;
		this.elementTypesFile = elementTypesFile;
		this.resource = resource;
	}

	/**
	 * This allows to check that the plug-in has the correct dependencies depending to the external profile references.
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker#check(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void check(final IProgressMonitor monitor) {

		if (null != monitor) {
			monitor.subTask("Validate dependencies for element types '" + elementTypesFile.getName() + "'."); //$NON-NLS-1$ //$NON-NLS-2$
		}

		// Get the external reference paths
		final Collection<URI> externalReferencesPaths = getExternalReferencesPaths(project, elementTypesFile, resource);

		// Calculate plug-ins names from URI
		final Collection<String> requiredPlugins = new HashSet<>();
		externalReferencesPaths.stream().forEach(externalReferencePath -> requiredPlugins.add(getPluginNameFromURI(externalReferencePath)));

		// For each external reference, get its plug-in name and search its dependency in the plug-in
		final Collection<String> existingRequiredPlugins = new HashSet<>();
		ProjectManagementService.getPluginDependencies(project).stream().forEach(dependency -> existingRequiredPlugins.add(dependency.getName()));
		requiredPlugins.removeIf(requiredPlugin -> existingRequiredPlugins.contains(requiredPlugin));

		// If requiredPlugins is not empty, that means, the dependency is not available in the profile plug-in
		// So, create the warning markers
		if (!requiredPlugins.isEmpty()) {
			final IFile manifestFile = ProjectManagementService.getManifestFile(project);

			requiredPlugins.stream().forEach(requiredPlugin -> {
				int severity = IMarker.SEVERITY_ERROR;
				if (WARNING_PLUGINS_EXCEPTION.contains(requiredPlugin)) {
					severity = IMarker.SEVERITY_WARNING;
				}
				MarkersService.createMarker(manifestFile,
						ElementTypesPluginValidationConstants.ELEMENTTYPES_PLUGIN_VALIDATION_TYPE,
						"The plug-in '" + requiredPlugin + "' must be defined as required plug-in (for element types configurations file '" + elementTypesFile.getName() + "').", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						severity);
			});
		}

		if (null != monitor) {
			monitor.worked(1);
		}
	}

	/**
	 * This allows to get the external references paths.
	 *
	 * @param project
	 *            The current project.
	 * @param elementTypesFile
	 *            The file defining element types.
	 * @param resource
	 *            The resource to get external references paths.
	 * @return The external references paths.
	 */
	private Collection<URI> getExternalReferencesPaths(final IProject project, final IFile elementTypesFile, final Resource resource) {
		final Collection<URI> externalReferencesPaths = new HashSet<>();

		// First step, resolve all references
		EcoreUtil.resolveAll(resource);

		for (final Resource currentResource : resource.getResourceSet().getResources()) {
			// Check that the resource is not the current one or is not available in the same plugin
			if (!isCurrentProjectReference(project, currentResource)) {
				final URI resourceURI = currentResource.getURI();

				// React differently if this is a pathmap
				if (resourceURI.toString().startsWith("pathmap://")) { //$NON-NLS-1$
					// Try to resolve the pathmap
					final URI correspondingURI = getCorrespondingURIFromPathmap(resourceURI);
					if (null == correspondingURI) {
						// If this case, the pathmap cannot be resolved, so create a marker
						MarkersService.createMarker(elementTypesFile,
								ElementTypesPluginValidationConstants.ELEMENTTYPES_PLUGIN_VALIDATION_TYPE,
								"The pathmap '" + resourceURI.toString() + "' cannot be resolved.", //$NON-NLS-1$ //$NON-NLS-2$
								IMarker.SEVERITY_ERROR);
					} else {
						externalReferencesPaths.add(correspondingURI);
					}
				} else {
					externalReferencesPaths.add(resourceURI);
				}
			}
		}

		return externalReferencesPaths;
	}

	/**
	 * This allows to determinate if the external reference must be managed or not.
	 * For example, we don't have to manage references of files from the same plug-in.
	 *
	 * @param project
	 *            The current project.
	 * @param resource
	 *            The resource to check.
	 * @return <code>true</code> if we have to manage reference, <code>false</code> otherwise.
	 */
	private boolean isCurrentProjectReference(final IProject project, final Resource resource) {
		final String resourceURI = resource.getURI().toString();

		// We don't have to manage references of files from the same plug-in
		if (resourceURI.startsWith("platform:/plugin/" + project.getName() + "/") || //$NON-NLS-1$ //$NON-NLS-2$
				resourceURI.startsWith("platform:/resource/" + project.getName() + "/")) { //$NON-NLS-1$ //$NON-NLS-2$
			return true;
		}

		return false;
	}

	/**
	 * This allows to resolve pathmap. To do this, we trim last segments until we got the correct corresponding URI.
	 * It is possible that we don't find pathmap, in this case, just return null.
	 *
	 * @param uri
	 *            The pathmap URI to search.
	 * @return The corresponding URI to the pathmap.
	 */
	private URI getCorrespondingURIFromPathmap(final URI uri) {
		URI copiedURI = URI.createURI(uri.toString());
		URI foundCorrespondingURI = null;

		while (null == foundCorrespondingURI) {
			foundCorrespondingURI = URIMappingRegistryImpl.INSTANCE.get(copiedURI);
			if (null == foundCorrespondingURI) {
				if (copiedURI.segmentCount() <= 0) {
					break;
				}
				copiedURI = copiedURI.trimSegments(1);
			}
		}

		return foundCorrespondingURI;
	}

	/**
	 * This allows to get the plug-in name from the URI.
	 *
	 * @param uri
	 *            The initial URI.
	 * @return The plug-in name from URI or <code>null</code> if any problem occurred.
	 */
	private String getPluginNameFromURI(final URI uri) {
		String pluginName = null;

		// Take we correct segment (without authority)
		final int takenSegment = uri.hasAuthority() ? 0 : 1;
		if (uri.segmentCount() > takenSegment) {
			pluginName = uri.segment(takenSegment);
		}

		return pluginName;
	}
}
