/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.edit.advices;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.uml.diagram.activity.edit.commands.util.PinUpdateCommand;
import org.eclipse.papyrus.uml.diagram.activity.edit.dialogs.CreateCallOperationActionDialog;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.PinUpdaterFactory;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.CallOperationAction;
import org.eclipse.uml2.uml.InvocationAction;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Edit helper advice for {@link CallOperationAction}, that pops up a dialog during creation
 */
public class CallOperationActionEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ICommand getBeforeConfigureCommand(ConfigureRequest request) {
		// get the activity containing the new element
		Activity parentActivity = null;
		EObject parent = request.getElementToConfigure();
		while (parent != null && parentActivity == null) {
			if (parent instanceof Activity) {
				parentActivity = (Activity) parent;
			}
			parent = parent.eContainer();
		}
		CreateCallOperationActionDialog dialog = new CreateCallOperationActionDialog(Display.getDefault().getActiveShell(), parentActivity, (InvocationAction) request.getElementToConfigure());
		if (IDialogConstants.OK_ID == dialog.open()) {
			// initialize the invoked element (no need to use a command, since action is being created)
			CompositeCommand command = new CompositeCommand("Configure created element");
			IElementEditService service = ElementEditServiceUtils.getCommandProvider(request.getElementToConfigure());
			EObject operation = dialog.getSelectedInvoked();
			if (operation instanceof Operation) {
				SetRequest setOperationRequest = new SetRequest(request.getElementToConfigure(), UMLPackage.eINSTANCE.getCallOperationAction_Operation(), operation);
				command.add(service.getEditCommand(setOperationRequest));
			}
			// initialize synchronous
			SetRequest setSynchronousReqest = new SetRequest(request.getElementToConfigure(), UMLPackage.eINSTANCE.getCallAction_IsSynchronous(), dialog.getIsSynchronous());
			command.add(service.getEditCommand(setSynchronousReqest));
			return command;
		}
		return null;
	}
	
	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterSetCommand(org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterSetCommand(SetRequest request) {
		CallOperationAction editedModelElement = (CallOperationAction) request.getElementToEdit();
		if(request.getFeature()==UMLPackage.eINSTANCE.getCallOperationAction_Operation()){
			IPinUpdater<CallOperationAction> updater =PinUpdaterFactory.getInstance().instantiate(editedModelElement);
			return new PinUpdateCommand<CallOperationAction>("Update call operation action pins", updater, editedModelElement);
		}else{
			return null;
		}
	}
}
