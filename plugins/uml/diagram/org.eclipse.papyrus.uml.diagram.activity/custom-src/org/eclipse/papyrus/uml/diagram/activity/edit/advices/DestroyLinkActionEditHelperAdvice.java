/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.advices;

import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.uml.diagram.activity.edit.commands.util.PinUpdateCommand;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.IPinUpdater;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.PinUpdaterFactory;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.preferences.AutomatedModelCompletionPreferencesInitializer;
import org.eclipse.papyrus.uml.diagram.activity.edit.utils.updater.preferences.IAutomatedModelCompletionPreferencesConstants;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.uml2.uml.DestroyLinkAction;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * 
 * Pins of DestroyLinkAction should be create and update automatically
 * @since 3.0
 *  
 */
public class DestroyLinkActionEditHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#getAfterConfigureCommand(org.eclipse.gmf.runtime.emf.type.core.requests.ConfigureRequest)
	 *
	 * @param request
	 * @return
	 */
	@Override
	protected ICommand getAfterConfigureCommand(ConfigureRequest request) {
		// 1] get the preference for DestroyLinkAction
		final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
		boolean synchronizePin = (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.DESTROY_LINK_ACTION_ACCELERATOR).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION));
		// 2] check preference
		if (synchronizePin) {
			DestroyLinkAction editedModelElement = (DestroyLinkAction) request.getElementToConfigure();
			if (editedModelElement != null) {
				// 3] call the command for the DestroyLinkAction
				IPinUpdater<DestroyLinkAction> updater = PinUpdaterFactory.getInstance().instantiate(editedModelElement);
				return new PinUpdateCommand<DestroyLinkAction>("Update destroy link action pins", updater, editedModelElement); //$NON-NLS-1$
			}
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
		// 1] check if the setFeature is endData
		if (request.getFeature().equals(UMLPackage.eINSTANCE.getLinkAction_EndData())) {
			// 1] get the preference for DestroyLinkAction
			final IPreferenceStore prefStore = Activator.getDefault().getPreferenceStore();
			boolean synchronizePin = (prefStore.getString(IAutomatedModelCompletionPreferencesConstants.DESTROY_LINK_ACTION_ACCELERATOR).equals(AutomatedModelCompletionPreferencesInitializer.PIN_SYNCHRONIZATION));
			// 2] check preference
			if (synchronizePin) {
				DestroyLinkAction editedModelElement = (DestroyLinkAction) request.getElementToEdit();
				if (editedModelElement != null) {
					// 3] call the command for the DestroyLinkAction
					IPinUpdater<DestroyLinkAction> updater = PinUpdaterFactory.getInstance().instantiate(editedModelElement);
					return new PinUpdateCommand<DestroyLinkAction>("Update destroy link action pins", updater, editedModelElement); //$NON-NLS-1$
				}
			}
		}
		return null;
	}
}
