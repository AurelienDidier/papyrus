/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   alex - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.plugin.builder.quickfix;

import org.eclipse.core.resources.IMarker;
import org.eclipse.papyrus.eclipse.project.editors.file.BuildEditor;
import org.eclipse.papyrus.toolsmiths.plugin.builder.Messages;
import org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants;

/**
 * Resolution that adds the missing file to the binary build.
 */
public class ResourceMissingFromBinaryBuildMarkerResolution extends AbstractPapyrusMarkerResolution {

	@Override
	public String getDescription() {
		return Messages.ResourceMissingFromBinaryBuildMarkerResolution_description;
	}

	@Override
	public String getLabel() {
		return Messages.ResourceMissingFromBinaryBuildMarkerResolution_label;
	}

	@Override
	public void run(IMarker marker) {
		BuildEditor buildEditor = new BuildEditor(marker.getResource().getProject());
		buildEditor.init();
		buildEditor.addToBuild(marker.getAttribute(ProfilePluginValidationConstants.STATIC_PROFILE_BINARY_BUILD_PATH, "")); //$NON-NLS-1$
		buildEditor.save();
	}

}
