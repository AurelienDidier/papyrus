/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.papyrusgmfgenextension.popupaction;

import org.eclipse.osgi.util.NLS;


/**
 * The Class Messages.
 */
public class Messages extends NLS {

	/** The Constant BUNDLE_NAME. */
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.papyrusgmfgenextension.popupaction.messages"; //$NON-NLS-1$

	/** The rules about named element. */
	public static String rulesAboutNamedElement;

	/** The message_ intro. */
	public static String message_Intro;

	/** The rules about shape edit part. */
	public static String rulesAboutShapeEditPart;

	/** The rule about change edit policy. */
	public static String ruleAboutChangeEditPolicy;

	/** The general information. */
	public static String generalInformation;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	/**
	 * Instantiates a new messages.
	 */
	private Messages() {
	}
}
