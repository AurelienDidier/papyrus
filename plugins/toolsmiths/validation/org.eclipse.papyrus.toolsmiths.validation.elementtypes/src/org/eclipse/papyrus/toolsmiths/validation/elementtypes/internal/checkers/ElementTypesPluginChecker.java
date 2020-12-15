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

package org.eclipse.papyrus.toolsmiths.validation.elementtypes.internal.checkers;

import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.ELEMENTTYPES_EXTENSION_POINT_IDENTIFIER;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.emf.helpers.BundleResourceURIHelper;
import org.eclipse.papyrus.infra.types.ElementTypeSetConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
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
import org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdvicePackage;
import org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdvicePackage;
import org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherPackage;

/**
 * This allows to check an element types plug-in (extensions, builds, dependencies, ...).
 */
public class ElementTypesPluginChecker {

	public static final String ELEMENT_TYPES_CONFIGURATION_EXTENSION = "elementtypesconfigurations";//$NON-NLS-1$

	// TODO: Why were some of these in the original definition of base requirements?
	// private static final Set<String> ADDITIONAL_REQUIREMENTS = Set.of(
	// "org.eclipse.papyrus.infra.types.core", //$NON-NLS-1$
	// "org.eclipse.gmf.runtime.emf.type.core", //$NON-NLS-1$
	// "org.eclipse.papyrus.uml.service.types", //$NON-NLS-1$
	// "org.eclipse.papyrus.infra.services.edit", //$NON-NLS-1$
	// "org.eclipse.papyrus.infra.types", //$NON-NLS-1$
	// "org.eclipse.papyrus.uml.tools.utils" //$NON-NLS-1$
	// );
	private static final Set<String> ADDITIONAL_REQUIREMENTS = Set.of(
			"org.eclipse.papyrus.infra.types.core", //$NON-NLS-1$
			"org.eclipse.papyrus.infra.types" //$NON-NLS-1$
	);

	/**
	 * This allows to check the element types plug-in.
	 *
	 * @param project
	 *            The current project to check.
	 * @param A
	 *            monitor to report progress
	 */
	public static void checkElementTypesPlugin(final IProject project, IProgressMonitor monitor) {
		// Open the progress monitor dialog
		final Collection<IFile> elementTypesFiles = ProjectManagementService.getFilesFromProject(project, ELEMENT_TYPES_CONFIGURATION_EXTENSION, true);
		monitor.beginTask("Validate Element Types plug-in", 1 + (elementTypesFiles.size() * 3)); // $NON-NLS-1$

		monitor.subTask("Prepare plug-in validation"); //$NON-NLS-1$
		// First of all, delete the existing markers for project
		MarkersService.deleteMarkers(project, ElementTypesPluginValidationConstants.ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE);

		// Create the plug-in validation service
		final PluginValidationService pluginValidationService = new PluginValidationService();

		// First, check the static dependencies needed
		pluginValidationService.addPluginChecker(createModelDependenciesChecker(project));

		// For all element types files in the plug-in
		for (final IFile elementTypesFile : elementTypesFiles) {
			if (monitor.isCanceled()) {
				return;
			}

			// Get the resource
			final URI elementTypesFileURI = URI.createPlatformResourceURI(elementTypesFile.getFullPath().toOSString(), true);
			final Resource resource = new ResourceSetImpl().getResource(elementTypesFileURI, true);

			// Check the validation of the element types file
			pluginValidationService.addPluginChecker(createModelValidationChecker(project, elementTypesFile, resource));

			// Check the extension point
			pluginValidationService.addPluginChecker(createExtensionsChecker(project, elementTypesFile, resource));

			// Check the external dependencies needed
			pluginValidationService.addPluginChecker(createModelDependenciesChecker(project, elementTypesFile, resource));
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
		return IPluginChecker2.Factory.forProject(ElementTypesPluginChecker::createModelDependenciesChecker)
				.or(IPluginChecker2.Factory.forEMFResource(ElementTypesPluginChecker::createModelDependenciesChecker));
	}

	private static ModelDependenciesChecker createModelDependenciesChecker(IProject project) {
		// When checking the project, we have some additional requirements that aren't model-specific
		return new ModelDependenciesChecker(project, null, null, ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE)
				.addRequirements(ADDITIONAL_REQUIREMENTS)
				.withSeverityFunction(bundle -> ADDITIONAL_REQUIREMENTS.contains(bundle) ? Diagnostic.WARNING : Diagnostic.ERROR);
	}

	private static ModelDependenciesChecker createModelDependenciesChecker(IProject project, IFile modelFile, Resource resource) {
		return new ModelDependenciesChecker(project, modelFile, resource, ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE)
				.withAdditionalRequirements(ElementTypesPluginChecker::getBundlesFromMetamodelNSURI);
	}

	private static Collection<String> getBundlesFromMetamodelNSURI(Resource resource) {
		return EcoreUtil.<ElementTypeSetConfiguration> getObjectsByType(resource.getContents(), ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_SET_CONFIGURATION).stream()
				.map(ElementTypeSetConfiguration::getMetamodelNsURI).filter(Objects::nonNull)
				.map(BundleResourceURIHelper.INSTANCE::getBundleNameFromNS_URI).filter(Objects::nonNull)
				.collect(Collectors.toSet());
	}

	/**
	 * Obtain a model validation checker factory.
	 *
	 * @return the model validation checker factory
	 */
	public static IPluginChecker2.Factory modelValidationCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ElementTypesPluginChecker::createModelValidationChecker);
	}

	private static ModelValidationChecker createModelValidationChecker(IProject project, IFile modelFile, Resource resource) {
		return new ModelValidationChecker(modelFile, resource, ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE);
	}

	/**
	 * Obtain a build properties checker factory.
	 *
	 * @return the build properties checker factory
	 */
	public static IPluginChecker2.Factory buildPropertiesCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ElementTypesPluginChecker::createBuildPropertiesChecker);
	}

	private static BuildPropertiesChecker createBuildPropertiesChecker(IProject project, IFile modelFile, Resource resource) {
		return new BuildPropertiesChecker(project, modelFile, ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE)
				.withDependencies(file -> new ReferencedProfilesBuildPropertiesDependencies(resource).getDependencies());
	}

	/**
	 * Obtain a <tt>plugin.xml</tt> extensions checker factory.
	 *
	 * @return the extensions checker factory
	 */
	public static IPluginChecker2.Factory extensionsCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ElementTypesPluginChecker::createExtensionsChecker);
	}

	private static ExtensionsChecker<ElementTypeSetConfiguration, PluginErrorReporter<ElementTypeSetConfiguration>> createExtensionsChecker(
			IProject project, IFile modelFile, Resource resource) {

		Collection<ElementTypeSetConfiguration> sets = EcoreUtil.getObjectsByType(resource.getContents(), ElementTypesConfigurationsPackage.Literals.ELEMENT_TYPE_SET_CONFIGURATION);
		return new ExtensionsChecker<>(project, modelFile, sets, ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE, ElementTypesPluginChecker::createPluginErrorReporter);
	}

	private static PluginErrorReporter<ElementTypeSetConfiguration> createPluginErrorReporter(IFile pluginXML, IFile modelFile, ElementTypeSetConfiguration model) {
		ElementTypesPluginXMLValidator validator = new ElementTypesPluginXMLValidator(modelFile);

		return new PluginErrorReporter<>(pluginXML, modelFile, model, ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE, set -> set.getIdentifier())
				.requireExtensionPoint(ELEMENTTYPES_EXTENSION_POINT_IDENTIFIER, validator::matchExtension, validator::checkExtension, null)
				.impliedByArchitectureContexts(ELEMENTTYPES_EXTENSION_POINT_IDENTIFIER);
	}

	/**
	 * Obtain a checker factory for custom model validation rules.
	 *
	 * @return the custom model checker factory
	 */
	public static IPluginChecker2.Factory customModelCheckerFactory() {
		return IPluginChecker2.Factory.forEMFResource(ElementTypesPluginChecker::createCustomModelChecker);
	}

	private static CustomModelChecker createCustomModelChecker(IProject project, IFile modelFile, Resource resource) {
		return new CustomModelChecker(modelFile, resource, ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE)
				.withValidator(ElementTypesConfigurationsPackage.eNS_URI, ElementTypesCustomValidator::new)
				.withValidator(ApplyStereotypeAdvicePackage.eNS_URI, ApplyStereotypeAdviceCustomValidator::new)
				.withValidator(StereotypeApplicationMatcherPackage.eNS_URI, StereotypeApplicationMatcherCustomValidator::new)
				.withValidator(StereotypePropertyReferenceEdgeAdvicePackage.eNS_URI, StereotypePropertyReferenceEdgeAdviceCustomValidator::new);
	}

}
