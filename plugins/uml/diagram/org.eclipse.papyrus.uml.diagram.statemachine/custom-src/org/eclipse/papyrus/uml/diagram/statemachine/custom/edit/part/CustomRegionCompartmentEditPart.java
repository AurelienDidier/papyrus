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
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 */
package org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gef.tools.DeselectAllTracker;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.diagram.ui.internal.tools.RubberbandDragTracker;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.statemachine.custom.figures.CustomRegionCompartmentFigure;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionCompartmentEditPart;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.Region;

public class CustomRegionCompartmentEditPart extends RegionCompartmentEditPart {

	public CustomRegionCompartmentEditPart(View view) {
		super(view);
	}

	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		removeEditPolicy(EditPolicyRoles.CONNECTION_HANDLES_ROLE);
		removeEditPolicy(EditPolicyRoles.POPUPBAR_ROLE);
	}

	@Override
	public IFigure createFigure() {
		CustomRegionCompartmentFigure result = new CustomRegionCompartmentFigure(getCompartmentName(), getMapMode());
		return result;
	}

	@Override
	public DragTracker getDragTracker(Request req) {
		if (!supportsDragSelection()) {
			return super.getDragTracker(req);
		}

		if (req instanceof SelectionRequest && ((SelectionRequest) req).getLastButtonPressed() == 3) {
			return new DeselectAllTracker(this) {

				@Override
				protected boolean handleButtonDown(int button) {
					getCurrentViewer().select(getParent());
					return true;
				}
			};
		}
		return new RubberbandDragTracker() {

			@Override
			protected void handleFinished() {
				if (getViewer().getSelectedEditParts().isEmpty()) {
					getViewer().select(getParent());
				}
			}
		};
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();

		View regionView = (View) ((View) getModel()).eContainer();
		Region region = (Region) regionView.getElement();

		((ResizableCompartmentFigure) getFigure()).setToolTip(UMLLabelInternationalization.getInstance().getLabel(region));

	}
}
