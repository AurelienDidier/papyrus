/*****************************************************************************
 * Copyright (c) 2019 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Yupanqui Munoz (CEA LIST) yupanqui.munozjulho@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.siriusdiag.modelexplorer.internal.messages;

import org.eclipse.osgi.util.NLS;

/**
 * This class provides messages.
 *
 */
public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.papyrus.infra.siriusdiag.modelexplorer.internal.messages.messages"; //$NON-NLS-1$

	public static String RenameDocumentTemplateHandler_NewName;

	public static String RenameDocumentTemplateHandler_RenameAnExistingDocumentTemplate;

	public static String RenameDocumentTemplateHandler_Label_DialogTitle;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	/**
	 * Constructor.
	 *
	 */
	private Messages() {
		// to avoid instantiation
	}

}
