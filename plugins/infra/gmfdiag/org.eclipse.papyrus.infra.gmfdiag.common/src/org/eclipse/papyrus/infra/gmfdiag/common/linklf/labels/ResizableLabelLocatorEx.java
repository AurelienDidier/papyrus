package org.eclipse.papyrus.infra.gmfdiag.common.linklf.labels;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.internal.figures.ResizableLabelLocator;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;

class ResizableLabelLocatorEx extends ResizableLabelLocator {

	public ResizableLabelLocatorEx(IFigure parent, Rectangle bounds, int alignment) {
		super(parent, bounds, alignment);
	}

	@Override
	protected Point getReferencePoint() {
		if(parent instanceof Connection) {
			PointList ptList = ((Connection)parent).getPoints();
			int percents = LinkLabelOffsetConvention.getPercentageOffsetAmongTheLineForAlignment(getAlignment());
			return PointListUtilities.calculatePointRelativeToLine(ptList, 0, percents, true);
		} else {
			return parent.getBounds().getLocation();
		}
	}

}
