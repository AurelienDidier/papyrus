/**
 * Copyright (c) 2014 CEA LIST.
  *
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License 2.0
  * which accompanies this distribution, and is available at
  * https://www.eclipse.org/legal/epl-2.0/
  *
  * SPDX-License-Identifier: EPL-2.0
  *
  * Contributors:
  *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.composite.edit.parts;

import org.eclipse.draw2d.Connection;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITreeBranchEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultSemanticEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editparts.UMLConnectionNodeEditPart;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeLabelDisplayEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeLinkLabelDisplayEditPolicy;
import org.eclipse.papyrus.uml.diagram.composite.custom.figures.InformationFlowFigure;

/**
 * @generated
 */
public class InformationFlowEditPart extends UMLConnectionNodeEditPart implements ITreeBranchEditPart {

	/**
	 * @generated
	 */
	public static final String VISUAL_ID = "InformationFlow_Edge";

	/**
	 * @generated
	 */
	public InformationFlowEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new DefaultSemanticEditPolicy());
		installEditPolicy(AppliedStereotypeLinkLabelDisplayEditPolicy.STEREOTYPE_LABEL_POLICY, new AppliedStereotypeLinkLabelDisplayEditPolicy());
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof InformationFlowConveyedLabelEditPart) {
			((InformationFlowConveyedLabelEditPart) childEditPart).setLabel(
					getPrimaryShape().getConveyedLabel());
		}
		if (childEditPart instanceof InformationFlowAppliedStereotypeEditPart) {
			((InformationFlowAppliedStereotypeEditPart) childEditPart).setLabel(
					getPrimaryShape().getAppliedStereotypeLabel());
		}
		if (childEditPart instanceof InformationFlowNameEditPart) {
			((InformationFlowNameEditPart) childEditPart).setLabel(
					getPrimaryShape().getNameLabel());
		}
		return false;
	}

	/**
	 * @generated
	 */
	@Override
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (addFixedChild(childEditPart)) {
			return;
		}
		super.addChildVisual(childEditPart, -1);
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof InformationFlowConveyedLabelEditPart) {
			return true;
		}
		if (childEditPart instanceof InformationFlowAppliedStereotypeEditPart) {
			return true;
		}
		if (childEditPart instanceof InformationFlowNameEditPart) {
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	@Override
	protected void removeChildVisual(EditPart childEditPart) {
		if (removeFixedChild(childEditPart)) {
			return;
		}
		super.removeChildVisual(childEditPart);
	}

	/**
	 * Creates figure for this edit part.
	 *
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 *
	 * @generated
	 */
	@Override
	protected Connection createConnectionFigure() {
		return new InformationFlowFigure();
	}

	/**
	 * @generated
	 */
	@Override
	public InformationFlowFigure getPrimaryShape() {
		return (InformationFlowFigure) getFigure();
	}

}
