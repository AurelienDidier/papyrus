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

package org.eclipse.papyrus.toolsmiths.validation.profile.checkers;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.papyrus.toolsmiths.validation.profile.internal.checkers.ProfilePluginChecker;

/**
 * This allows to check a profile plug-in (extensions, builds, dependencies).
 */
public class ProfilePluginCheckerService {

	/**
	 * This allows to check the profile plug-in.
	 *
	 * @param project
	 *            The current project to check.
	 */
	public static void checkProfilePlugin(final IProject project, IProgressMonitor monitor) {
		ProfilePluginChecker.checkProfilePlugin(project, monitor == null ? new NullProgressMonitor() : monitor);
	}

}
