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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 477911
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gmf.runtime.diagram.core.commands.SetPropertyCommand;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.TopGraphicEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.PropertyHandlerEditPolicy;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.commands.ShowHideCompartmentRequest;

/**
 *
 * This EditPolicy provides the same commands that {@link PropertyHandlerEditPolicy}, more the command to Show/Hide a given
 * compartment
 *
 */
public class ShowHideCompartmentEditPolicy extends AbstractEditPolicy {

	/** key for this editpolicy */
	public static final String SHOW_HIDE_COMPARTMENT_POLICY = "Show/Hide Compartment Policy"; //$NON-NLS-1$

	/** the list of the listened compartment */
	List<View> childlistened = new ArrayList<View>();

	/** A listener on the created compartment to refresh parent */
	private NotificationListener listener = new NotificationListener() {
		@Override
		public void notifyChanged(Notification notification) {
			getHost().refresh();
		}
	};

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editpolicies.PropertyHandlerEditPolicy#getCommand(org.eclipse.gef.Request)
	 *
	 * @param request
	 * @return
	 */
	@Override
	public Command getCommand(Request request) {
		if (request.getType().equals(ShowHideCompartmentRequest.SHOW_HIDE_COMPARTMENT)) {
			ShowHideCompartmentRequest req = (ShowHideCompartmentRequest) request;
			return getShowHideCompartmentCommand(req);
		}
		return null;
	}

	/**
	 * Return the command to show/hide a compartment.
	 *
	 * @param request
	 *            the request
	 * @return the command to show/hide a compartment
	 */
	protected Command getShowHideCompartmentCommand(ShowHideCompartmentRequest request) {
		if (getHost() instanceof TopGraphicEditPart) {
			List<?> views = getAllNotationViews((TopGraphicEditPart) getHost());
			for (Iterator<?> iter = views.iterator(); iter.hasNext();) {
				View childView = (View) iter.next();
				if (ViewUtil.isPropertySupported(childView, request.getPropertyID())) {
					if (childView.equals(request.getCompartment())) {

						DiagramEventBroker diagramEventBroker = getDiagramEventBroker();

						if (request.getValue() instanceof Boolean && null != diagramEventBroker) {
							if ((Boolean) request.getValue()) {
								// add a listener on the compartment
								diagramEventBroker.addNotificationListener(childView, listener);
								childlistened.add(childView);
							} else {
								// remove a listener on the compartment
								diagramEventBroker.removeNotificationListener(childView, listener);
								childlistened.remove(childView);
							}
						}
						return new ICommandProxy(new SetPropertyCommand(getEditingDomain(), new EObjectAdapter(childView), request.getPropertyID(), request.getPropertyName(), request.getValue()));
					}
				}
			}
		}
		return null;
	}

	/**
	 * Gets the diagram event broker from the editing domain.
	 *
	 * @return the diagram event broker
	 */
	protected DiagramEventBroker getDiagramEventBroker() {
		DiagramEventBroker eventBroker = null;
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
		if (null != editingDomain) {
			eventBroker = DiagramEventBroker.getInstance(editingDomain);
		}
		return eventBroker;
	}

	/**
	 * @see org.eclipse.gef.editpolicies.AbstractEditPolicy#deactivate()
	 *
	 */
	@Override
	public void deactivate() {
		// remove listener from the DiagramEventBroker
		DiagramEventBroker diagramEventBroker = getDiagramEventBroker();
		if (null != diagramEventBroker) {
			for (View child : childlistened) {
				diagramEventBroker.removeNotificationListener(child, listener);
			}
		}
		super.deactivate();
	}

	/**
	 * Returns all the views associated to this editpart
	 *
	 * @param ep
	 *            a {@link TopGraphicEditPart}
	 * @return all the views associated to this editpart
	 */
	public List<?> getAllNotationViews(TopGraphicEditPart ep) {
		View view = ep.getNotationView();
		if (view != null) {
			List<View> views = new ArrayList<View>();
			Iterator<?> childrenIterator = view.getChildren().iterator();
			while (childrenIterator.hasNext()) {
				View child = (View) childrenIterator.next();
				if (child instanceof Node) {
					views.add(child);
				}
			}
			return views;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * Return the current {@link EditingDomain}
	 *
	 * @return the current {@link EditingDomain}
	 */
	protected TransactionalEditingDomain getEditingDomain() {
		return ((IGraphicalEditPart) getHost()).getEditingDomain();
	}
}
