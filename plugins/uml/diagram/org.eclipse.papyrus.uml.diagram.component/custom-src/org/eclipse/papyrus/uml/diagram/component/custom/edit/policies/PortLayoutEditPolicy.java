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
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.component.custom.edit.policies;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PortAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PortEditPart;
import org.eclipse.papyrus.uml.diagram.component.edit.parts.PortNameEditPart;
import org.eclipse.papyrus.uml.diagram.component.part.UMLVisualIDRegistry;

/**
 * This edit policy replaces the GMF generated edit policy for Port.
 * In particular it installs {@link ExternalLabelPrimaryDragRoleEditPolicy} on
 * children that are external label.
 * The code generated by GMF can be found in {@link PortEditPart#createLayoutEditPolicy()}.
 * @since 3.0
 */
public class PortLayoutEditPolicy extends LayoutEditPolicy {

	/**
	 * Creates the child edit policy.
	 *
	 * @param child
	 *            the child
	 * @return the edits the policy
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#createChildEditPolicy(org.eclipse.gef.EditPart)
	 */

	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		View childView = (View) child.getModel();
		switch (UMLVisualIDRegistry.getVisualID(childView)) {
		case PortNameEditPart.VISUAL_ID:
		case PortAppliedStereotypeEditPart.VISUAL_ID:
			return new ExternalLabelPrimaryDragRoleEditPolicy();
		}
		EditPolicy result = child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
		if (result == null) {
			result = new NonResizableEditPolicy();
		}
		return result;
	}

	/**
	 * Gets the move children command.
	 *
	 * @param request
	 *            the request
	 * @return the move children command
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getMoveChildrenCommand(org.eclipse.gef.Request)
	 */

	@Override
	protected Command getMoveChildrenCommand(Request request) {
		return null;
	}

	/**
	 * Gets the creates the command.
	 *
	 * @param request
	 *            the request
	 * @return the creates the command
	 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
	 */

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		return null;
	}
}
