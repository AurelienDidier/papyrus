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
import org.eclipse.papyrus.views.properties.toolsmiths.Activator;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

/**
 * An action to toggle the UIEditor Preview
 *
 * @author Camille Letavernier
 */
public class TogglePreviewAction extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		// System.out.println("Toggle");

		event.getCommand().getState("org.eclipse.papyrus.customization.properties.previewstate"); //$NON-NLS-1$

		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		if (activePage == null) {
			return null;
		}

		// System.out.println("ActivePage is " + activePage);

		IViewReference viewReference = null;

		for (IViewReference reference : activePage.getViewReferences()) {
			if (reference.getId().equals(Activator.PREVIEW_ID)) {
				viewReference = reference;
			}
		}

		try {
			if (viewReference == null) {
				// System.out.println("Opening view");
				activePage.showView(Activator.PREVIEW_ID);
			} else {
				// System.out.println("Closing view");
				activePage.hideView((ViewPart) viewReference.getPart(false));
			}
		} catch (PartInitException ex) {
			Activator.log.error(ex);
		}

		return null;
	}


}
