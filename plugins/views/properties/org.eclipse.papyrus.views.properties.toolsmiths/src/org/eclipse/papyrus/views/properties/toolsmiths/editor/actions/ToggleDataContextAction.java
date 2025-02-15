/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.editor.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.papyrus.views.properties.toolsmiths.editor.UIEditor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

/**
 * An action to toggle the display of the DataContextElements in the UIEditor
 *
 * @author Camille Letavernier
 *
 */
public class ToggleDataContextAction extends AbstractHandler {

	/**
	 * Indicates if the DataContextElements should be displayed
	 */
	public static boolean showDataContext = false;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		showDataContext = !showDataContext;
		IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (editor instanceof UIEditor) {
			UIEditor uiEditor = (UIEditor) editor;
			uiEditor.getViewer().refresh();
		}

		//		State state = event.getCommand().getState("org.eclipse.papyrus.customization.properties.displayContextState"); //$NON-NLS-1$
		// state.setValue(showDataContext);
		return null;
	}

}
