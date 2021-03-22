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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.ui.internal.messages;

import org.eclipse.osgi.util.NLS;

/**
 * Messages for the current pluginS
 *
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.ui.internal.messages.messages"; //$NON-NLS-1$
	public static String siriusdiagramEditorInput_NoName;
	public static String siriusdiagramEditorInput_NoDescription;
	public static String siriusdiagramEditorInput_Tooltip;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
