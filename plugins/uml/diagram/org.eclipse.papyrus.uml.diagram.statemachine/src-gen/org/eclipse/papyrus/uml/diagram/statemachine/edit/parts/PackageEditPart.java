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
package org.eclipse.papyrus.uml.diagram.statemachine.edit.parts;

import org.eclipse.gef.EditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.PapyrusDiagramEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.AbstractShowHideRelatedLinkEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultCompartmentSemanticEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.DefaultCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.PasteEditPolicy;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.policies.CustomPackageCreationEditPolicy;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.policies.CustomPackageXYLayoutEditPolicy;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.policies.CustomStateMachineDiagramDragDropEditPolicy;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.policies.ShowHideRelatedLinkEditPolicy;

/**
 * @generated
 */
public class PackageEditPart extends PapyrusDiagramEditPart {

	/**
	 * @generated
	 */
	public final static String MODEL_ID = "PapyrusUMLStateMachineDiagram"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static final String VISUAL_ID = "Package_StateMachineDiagram";

	/**
	 * @generated
	 */
	public PackageEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();

		installEditPolicy(EditPolicyRoles.CREATION_ROLE, new DefaultCreationEditPolicy());
		installEditPolicy(PasteEditPolicy.PASTE_ROLE, new PasteEditPolicy());

		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new DefaultCompartmentSemanticEditPolicy());
		// in Papyrus diagrams are not strongly synchronised
		// installEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CANONICAL_ROLE, new org.eclipse.papyrus.uml.diagram.statemachine.edit.policies.PackageCanonicalEditPolicy());

		installEditPolicy(EditPolicyRoles.CREATION_ROLE, new CustomPackageCreationEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new CustomPackageXYLayoutEditPolicy());
		installEditPolicy(EditPolicyRoles.DRAG_DROP_ROLE, new CustomStateMachineDiagramDragDropEditPolicy());
		installEditPolicy(AbstractShowHideRelatedLinkEditPolicy.SHOW_HIDE_RELATED_LINK_ROLE, new ShowHideRelatedLinkEditPolicy());
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.POPUPBAR_ROLE);
	}

}
