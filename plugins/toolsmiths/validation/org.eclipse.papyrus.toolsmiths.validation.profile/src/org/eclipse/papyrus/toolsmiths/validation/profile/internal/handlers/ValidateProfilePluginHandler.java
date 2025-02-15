/*****************************************************************************
 * Copyright (c) 2019 CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.profile.internal.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.toolsmiths.validation.profile.internal.checkers.ProfilePluginChecker;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * This handler allows to validate papyrus profile plug-ins.
 */
public class ValidateProfilePluginHandler extends AbstractHandler {

	/**
	 * This allows to validate a plugin which contains papyrus static profile.
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection == null || !(selection instanceof StructuredSelection) || selection.isEmpty()) {
			return null;
		}

		final StructuredSelection structuredSelection = (StructuredSelection) selection;
		for (final Object selectedElement : structuredSelection.toList()) {
			if (selectedElement instanceof IProject) {
				final IProject project = (IProject) selectedElement;
				ProfilePluginChecker.checkProfilePlugin(project);
			}
		}

		return null;
	}

}
