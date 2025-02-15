/*****************************************************************************
 * Copyright (c) 2009-2010 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.component.custom.actions;

import org.eclipse.papyrus.uml.diagram.common.actions.handlers.AbstractShowHideHandler;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.ShowHideRelatedContentsEditPolicy;


/**
 * Handler for the {@link ShowHideRelatedContentsAction}.
 * @since 3.0
 */
public class ShowHideRelatedContentsHandler extends AbstractShowHideHandler {

	/**
	 *
	 * Constructor.
	 *
	 */
	public ShowHideRelatedContentsHandler() {
		super(new ShowHideRelatedContentsAction(), ShowHideRelatedContentsEditPolicy.SHOW_HIDE_RELATED_CONTENTS_POLICY);
	}
}
