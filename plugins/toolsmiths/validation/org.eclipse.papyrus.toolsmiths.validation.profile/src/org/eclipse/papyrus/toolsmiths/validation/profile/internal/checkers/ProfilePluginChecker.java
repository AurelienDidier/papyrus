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

package org.eclipse.papyrus.toolsmiths.validation.profile.internal.checkers;

import static org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants.PROFILE_PLUGIN_VALIDATION_MARKER_TYPE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.BuildPropertiesChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CustomModelChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ExtensionsChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker2;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ModelDependenciesChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.ModelValidationChecker;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.utils.PluginErrorReporter;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.MarkersService;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.PluginValidationService;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.ProjectManagementService;
import org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants;
import org.eclipse.papyrus.uml.tools.utils.PackageUtil;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This allows to check an profile plug-in (extensions, builds, dependencies, ...).
 */
public class ProfilePluginChecker {

	private static final Set<String> ADDITIONAL_REQUIREMENTS = Set.of("org.eclipse.uml2.uml.resources"); //$NON-NLS-1$

	/**
	 * This allows to check the profile plug-in.
	 *
	 * @param project
	 *            The current project to check.
	 * @param A
	 *            monitor to report progress
	 */
	public static void checkProfilePlugin(final IProject project, IProgressMonitor monitor) {
		final Collection<IFile> profileFiles = ProjectManagementService.getFilesFromProject(project, "profile.uml", true); //$NON-NLS-1$
		monitor.beginTask("Validate Profile plug-in", 1 + (profileFiles.size() * 3)); // $NON-NLS-1$

		monitor.subTask("Prepare plug-in validation"); //$NON-NLS-1$
		// First of all, delete the existing markers for project
		MarkersService.deleteMarkers(project, PROFILE_PLUGIN_VALIDATION_MARKER_TYPE);

		// Create the plug-in validation service
		final PluginValidationService pluginValidationService = new PluginValidationService();

		// First, check the static dependencies needed
		pluginValidationService.addPluginChecker(createModelDependenciesChecker(project));

		// For all profile files in the plug-in
		for (final IFile profileFile : profileFiles) {
			if (monitor.isCanceled()) {
				return;
			}

			// Get the resource
			final URI profileFileURI = URI.createPlatformResourceURI(profileFile.getFullPath().toOSString(), true);
			final Resource resource = new ResourceSetImpl().getResource(profileFileURI, true);

			// Check the validation of the element types file
			pluginValidationService.addPluginChecker(createModelValidationChecker(project, profileFile, resource));

			// Check the extension point
			pluginValidationService.addPluginChecker(createExtensionsChecker(project, profileFile, resource));

			// Check the external dependencies needed
			pluginValidationService.addPluginChecker(createModelDependenciesChecker(project, profileFile, resource));
		}

		monitor.worked(1);

		// Call the validate
		pluginValidationService.validate(monitor);
	}

	/**
	 * Obtain a dependencies checker factory for the specified bundle dependencies validation.
	 *
	 * @return the dependencies checker factory
	 */
	public static IPluginChecker2.Factory modelDependenciesCheckerFactory() {
		// When checking the project, we have some additional requirements that aren't model-specific
		return IPluginChecker2.Factory.forProject(ProfilePluginChecker::createModelDependenciesChecker)
				.or(IPluginChecker2.Factory.forEMFResource(ProfilePluginChecker::createModelDependenciesChecker));
	}

	private static ModelDependenciesChecker createModelDependenciesChecker(IProject project) {
		// When checking the project, we have some additional requirements that aren't model-specific
		return new ModelDependenciesChecker(project, null, null, PROFILE_PLUGIN_VALIDATION_MARKER_TYPE)
				.addRequirements(ADDITIONAL_REQUIREMENTS)
				.withSeverityFunction(bundle -> ADDITIONAL_REQUIREMENTS.contains(bundle) ? Diagnostic.WARNING : Diagnostic.ERROR);
	}

	private static ModelDependenciesChecker createModelDependenciesChecker(IProject project, IFile modelFile, Resource resource) {
		return new ModelDependenciesChecker(project, modelFile, resource, PROFILE_PLUGIN_VALIDATION_MARKER_TYPE);
	}

	/**
	 * Obtain a model validation checker factory.
	 *
	 * @return the model validation checker factory
	 */
	public static IPluginChecker2.Factory modelValidationCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ProfilePluginChecker::createModelValidationChecker);
	}

	private static ModelValidationChecker createModelValidationChecker(IProject project, IFile modelFile, Resource resource) {
		return new ModelValidationChecker(modelFile, resource, PROFILE_PLUGIN_VALIDATION_MARKER_TYPE);
	}

	/**
	 * Obtain a build properties checker factory.
	 *
	 * @return the build properties checker factory
	 */
	public static IPluginChecker2.Factory buildPropertiesCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ProfilePluginChecker::createBuildPropertiesChecker);
	}

	private static BuildPropertiesChecker createBuildPropertiesChecker(IProject project, IFile modelFile, Resource resource) {
		return new BuildPropertiesChecker(project, modelFile, PROFILE_PLUGIN_VALIDATION_MARKER_TYPE)
				.withEMFGeneratorModels();
	}

	/**
	 * Obtain a <tt>plugin.xml</tt> extensions checker factory.
	 *
	 * @return the extensions checker factory
	 */
	public static IPluginChecker2.Factory extensionsCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ProfilePluginChecker::createExtensionsChecker);
	}

	private static ExtensionsChecker<Profile, PluginErrorReporter<Profile>> createExtensionsChecker(
			IProject project, IFile modelFile, Resource resource) {

		Collection<Profile> sets = EcoreUtil.getObjectsByType(resource.getContents(), UMLPackage.Literals.PROFILE);
		Collection<Profile> allProfiles = new ArrayList<>();
		for (Profile profile : sets) {
			allProfiles.add(profile);
			allProfiles.addAll(PackageUtil.getSubProfiles(profile));
		}
		return new ExtensionsChecker<>(project, modelFile, allProfiles, PROFILE_PLUGIN_VALIDATION_MARKER_TYPE, ProfilePluginChecker::createPluginErrorReporter);
	}

	private static PluginErrorReporter<Profile> createPluginErrorReporter(IFile pluginXML, IFile modelFile, Profile model) {
		ProfilePluginXMLValidator validator = new ProfilePluginXMLValidator(modelFile);

		PluginErrorReporter<Profile> reporter = new PluginErrorReporter<>(pluginXML, modelFile, model, PROFILE_PLUGIN_VALIDATION_MARKER_TYPE, profile -> profile.getName())
				.requireExtensionPoint(ProfilePluginValidationConstants.ECORE_GENERATED_PACKAGE_EXTENSION_POINT, validator::matchExtension, validator::checkExtension, validator::problemId)
				.requireExtensionPoint(ProfilePluginValidationConstants.UML_GENERATED_PACKAGE_EXTENSION_POINT, validator::matchExtension, validator::checkExtension, validator::problemId);

		if (model.getNestingPackage() == null) {
			return reporter.requireExtensionPoint(ProfilePluginValidationConstants.UMLPROFILE_EXTENSION_POINT, validator::matchExtension, validator::checkExtension, validator::problemId);
		}
		return reporter;
	}

	/**
	 * Obtain a checker factory for custom model validation rules.
	 *
	 * @return the custom model checker factory
	 */
	public static IPluginChecker2.Factory customModelCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ProfilePluginChecker::createCustomModelChecker);
	}

	private static CustomModelChecker createCustomModelChecker(IProject project, IFile modelFile, Resource resource) {
		return new CustomModelChecker(modelFile, resource, PROFILE_PLUGIN_VALIDATION_MARKER_TYPE);
	}

}
