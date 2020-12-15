/*****************************************************************************
 * Copyright (c) 2020 CEA LIST, EclipseSource, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Remi Schnekenburger - Initial API and implementation
 *   Christian W. Damus - bug 569357
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.plugin.builder.helper;

import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.byExtension;
import static org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper.modelSets;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.NotFoundException;
import org.eclipse.papyrus.infra.core.resource.sasheditor.DiModel;
import org.eclipse.papyrus.toolsmiths.validation.common.utils.ModelResourceMapper;
import org.eclipse.papyrus.uml.tools.model.UmlModel;
import org.eclipse.papyrus.uml.tools.model.UmlUtils;
import org.eclipse.papyrus.uml.tools.utils.PackageUtil;
import org.eclipse.uml2.uml.Profile;

import com.google.common.collect.Multimaps;

/**
 * Helper for Static profile builders.
 */
public class StaticProfileHelper {

	private static final String GENMODEL_EXTENSION = "genmodel";

	private StaticProfileHelper() {
		// no instance
	}

	/**
	 * Return the list of the static profile files (.di) for a given project.
	 *
	 * @param builtProject
	 *            the project in which static profiles are found.
	 * @param includeSubProfiles
	 *            <code>true</code> if all profiles within the files are to be returned, only the root profile if <code>false</code>
	 * @return the map of the found profile files, with the list of contained profile model elements as a value
	 */
	public final static Map<IFile, List<Profile>> findStaticProfiles(IProject builtProject, boolean includeSubProfiles) {
		ModelResourceMapper<Profile> mapper = new ModelResourceMapper<>(builtProject);
		return Multimaps.asMap(mapper.map(diWithGenmodel(), modelSets(), modelSet -> getProfiles(modelSet, includeSubProfiles)));
	}

	public static Predicate<IResource> diWithGenmodel() {
		return byExtension(DiModel.DI_FILE_EXTENSION).and(file -> hasGenmodel(file));
	}

	static boolean hasGenmodel(IResource diFile) {
		String fileName = diFile.getFullPath().removeFileExtension().addFileExtension(GENMODEL_EXTENSION).lastSegment();
		IResource genModelResource = diFile.getParent().findMember(fileName);
		return genModelResource instanceof IFile;
	}

	static Stream<Profile> getProfiles(ModelSet modelSet, boolean includeSubProfiles) {
		try {
			EObject root = UmlUtils.getUmlModel(modelSet).lookupRoot();
			if (root instanceof Profile) {
				Profile profile = (Profile) root;

				if (!includeSubProfiles) {
					return Stream.of(profile);
				} else {
					return profileWithSubProfiles(profile);
				}
			}
		} catch (NotFoundException e) {
			// Nothing to return
		}

		return Stream.empty();
	}

	static public Stream<Profile> profileWithSubProfiles(Profile profile) {
		return Stream.concat(Stream.of(profile), PackageUtil.getSubProfiles(profile).stream());
	}

	/**
	 * @return
	 */
	public static Predicate<IResource> umlWithGenmodel() {
		return byExtension(UmlModel.UML_FILE_EXTENSION).and(file -> hasGenmodel(file));
	}

}
