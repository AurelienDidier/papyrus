/*****************************************************************************
 * Copyright (c) 2011 Atos.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Atos - Initial API and implementation
 *   Arthur daussy (Atos) arthur.daussy@atos.net - Bug : 365405: [State Machine Diagram] Behaviours (Entry,exit,do) on states should have their own mechanisms
 *
 *****************************************************************************/
package org.eclipse.papyrus.diagram.statemachine.custom.listeners;

import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.diagram.statemachine.edit.parts.DoActivityStateBehaviorStateEditPart;
import org.eclipse.papyrus.diagram.statemachine.edit.parts.EntryStateBehaviorEditPart;
import org.eclipse.papyrus.diagram.statemachine.edit.parts.ExitStateBehaviorEditPart;
import org.eclipse.papyrus.diagram.statemachine.edit.parts.StateBehaviorCompartmentEditPart;
import org.eclipse.papyrus.diagram.statemachine.part.UMLVisualIDRegistry;
import org.eclipse.uml2.uml.UMLPackage;

import com.google.common.collect.Sets;

/**
 * This listener will handle the creation of visual for element for behavior (/do /entry /exit).
 * 
 * @author Arthur Daussy
 * 
 */
public class StateBehaviorsListener extends AbstractStateListener {

	protected final Set<String> viewIds = Sets.newHashSet(
		getFactoryHint(UMLPackage.Literals.STATE__DO_ACTIVITY),
		getFactoryHint(UMLPackage.Literals.STATE__EXIT),
		getFactoryHint(UMLPackage.Literals.STATE__ENTRY)
	);

	protected static NotificationFilter filter = NotificationFilter.createFeatureFilter(UMLPackage.Literals.STATE__DO_ACTIVITY).or(NotificationFilter.createFeatureFilter(UMLPackage.Literals.STATE__EXIT)).or(NotificationFilter.createFeatureFilter(UMLPackage.Literals.STATE__ENTRY));

	@Override
	public NotificationFilter getFilter() {
		return filter;
	}

	/**
	 * Handle modification on behavior attribute of state (entry , exit , do activity)
	 * 
	 * @param notif
	 */
	@Override
	protected ICommand getModificationCommand(Notification notif) {
		if(notif.getEventType() == Notification.SET) {
			Object object = notif.getNewValue();
			Object oldObject = notif.getOldValue();
			CompositeCommand cc = new CompositeCommand("Modification command triggered by modedication of one of the behaviros of selected state");//$NON-NLS-0$
			/**
			 * Delete Command
			 */
			getDestroyCommand(oldObject, cc, viewIds);
			/**
			 * Create Command
			 */
			getCreationCommand(notif, object, cc);
			/**
			 * Refresh layout
			 * If any problems with layout uncomments following
			 */
//			StateEditPart stateEditPart = getContainingEditPart(notif.getNotifier());
//			if(stateEditPart != null) {
//				ChangeBoundsRequest chReq = new ChangeBoundsRequest(org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants.REQ_REFRESH);
//				chReq.setEditParts(stateEditPart);
//				chReq.setMoveDelta(new Point(0, 0));
//				Command cmd3 = stateEditPart.getCommand(chReq);
//				if(cmd3 != null && cmd3.canExecute()) {
//					cc.compose(new CommandProxy(cmd3));
//				}
//			}
			return cc;
		}
		return null;
	}
	@Override
	protected int getCompartmentID() {
		return StateBehaviorCompartmentEditPart.VISUAL_ID;
	}

	/**
	 * Get the semantic id from the feature
	 * 
	 * @param feature
	 * @return
	 */
	@Override
	protected String getFactoryHint(EStructuralFeature feature) {
		int result;
		switch(feature.getFeatureID()) {
		case UMLPackage.STATE__DO_ACTIVITY:
			result = DoActivityStateBehaviorStateEditPart.VISUAL_ID;
			break;
		case UMLPackage.STATE__ENTRY:
			result = EntryStateBehaviorEditPart.VISUAL_ID;
			break;
		case UMLPackage.STATE__EXIT:
			result = ExitStateBehaviorEditPart.VISUAL_ID;
			break;
		default:
			result = -1;
			break;
		}
		return UMLVisualIDRegistry.getType(result);
	}


}
