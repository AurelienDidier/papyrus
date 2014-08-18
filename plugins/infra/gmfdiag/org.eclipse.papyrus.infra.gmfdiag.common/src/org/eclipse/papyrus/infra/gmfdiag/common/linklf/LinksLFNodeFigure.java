package org.eclipse.papyrus.infra.gmfdiag.common.linklf;

import org.eclipse.draw2d.AbstractPointListShape;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ScalablePolygonShape;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.BaseSlidableAnchor;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;

public class LinksLFNodeFigure extends DefaultSizeNodeFigure {

	private final GraphicalEditPart myHost;

	public LinksLFNodeFigure(GraphicalEditPart hostEP, int width, int height) {
		super(width, height);
		myHost = hostEP;
	}

	@Override
	protected ConnectionAnchor createAnchor(PrecisionPoint p) {
		if(p == null) {
			// If the old terminal for the connection anchor cannot be resolved (by SlidableAnchor) a null
			// PrecisionPoint will passed in - this is handled here
			return createDefaultAnchor();
		}
		SlidableSnapToGridAnchor result = new SlidableSnapToGridAnchor(this, p);
		result.setEditPartViewer(myHost.getViewer());
		return result;
	}

	@Override
	protected double getSlidableAnchorArea() {
		return 0.9;
	}

	protected ConnectionAnchor createConnectionAnchor(Point p) {
		if(p == null) {
			return getConnectionAnchor(szAnchor);
		} else {
			Point temp = p.getCopy();
			translateToRelative(temp);
			PrecisionPoint pt = BaseSlidableAnchor.getAnchorRelativeLocation(temp, getBounds());
			if(isDefaultAnchorArea(pt)) {
				return getConnectionAnchor(szAnchor);
			}
			return createAnchor(pt);
		}
	}

	@Override
	public PointList getPolygonPoints() {
		if(getChildren().size() == 1) {
			IFigure primaryShape = (IFigure)getChildren().get(0);
			if(primaryShape instanceof ScalablePolygonShape) {
				ScalablePolygonShape scalable = (ScalablePolygonShape)primaryShape;
				PointList result = scalable.getScaledPoints().getCopy();
				result.translate(scalable.getLocation());
				return result;
			}
			if(primaryShape instanceof AbstractPointListShape) {
				return ((AbstractPointListShape)primaryShape).getPoints().getCopy();
			}
		}
		return super.getPolygonPoints();
	}

}
