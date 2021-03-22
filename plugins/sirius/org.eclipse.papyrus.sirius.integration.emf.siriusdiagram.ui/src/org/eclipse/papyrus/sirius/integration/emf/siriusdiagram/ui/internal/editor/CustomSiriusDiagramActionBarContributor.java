/*****************************************************************************
 * Copyright (c) 2019, 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 569419
 *****************************************************************************/

package org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.ui.internal.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.ui.Activator;
import org.eclipse.sirius.diagram.ui.part.SiriusDiagramActionBarContributor;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * This action bar contributor allows to group expressions creation in a submenu
 */
public class CustomSiriusDiagramActionBarContributor extends SiriusDiagramActionBarContributor {

	/**
	 * the expression creation submenu
	 */
	private static final String GENERATION_CONDITION_SUB_MENU_TITLE = "Define Generation Condition"; //$NON-NLS-1$

	private static final String EXPRESSION = "Expression"; //$NON-NLS-1$

	/**
	 * Constructor.
	 *
	 */
	public CustomSiriusDiagramActionBarContributor() {
		super();
	}

	/**
	 * @see org.eclipse.papyrus.sirius.emf.siriusdiagram.presentation.siriusdiagramActionBarContributor#contributeToMenu(org.eclipse.jface.action.IMenuManager)
	 *
	 * @param menuManager
	 */
	@Override
	public void contributeToMenu(IMenuManager menuManager) {
		// avoid to get the main menu see bug 569419
	}

	/**
	 *
	 * @see org.eclipse.papyrus.sirius.emf.siriusdiagram.presentation.siriusdiagramActionBarContributor#extractSubmenuActions(java.util.Collection)
	 *
	 * @param createActions
	 * @return
	 */
	protected Map<String, Collection<IAction>> extractSubmenuActions(Collection<IAction> createActions) {
		Map<String, Collection<IAction>> createSubmenuActions = new LinkedHashMap<>();
		if (createActions != null) {
			if (containsOnlyExpression(createActions)) {
				// we do nothing
				// return super.extractSubmenuActions(createActions);
			}
			for (Iterator<IAction> actions = createActions.iterator(); actions.hasNext();) {
				IAction action = actions.next();
				if (action.getText().contains(EXPRESSION)) {
					Collection<IAction> submenuActions = createSubmenuActions.get(GENERATION_CONDITION_SUB_MENU_TITLE);
					if (submenuActions == null) {
						createSubmenuActions.put(GENERATION_CONDITION_SUB_MENU_TITLE, submenuActions = new ArrayList<>());
					}
					// action.setText(st.nextToken().trim());
					submenuActions.add(action);
					actions.remove();
				}
			}
		}
		return createSubmenuActions;
	}


	/**
	 * @see org.eclipse.papyrus.sirius.emf.siriusdiagram.presentation.siriusdiagramActionBarContributor#populateManager(org.eclipse.jface.action.IContributionManager, java.util.Collection, java.lang.String)
	 *
	 * @param manager
	 * @param actions
	 * @param contributionID
	 */
	protected void populateManager(IContributionManager manager, Collection<? extends IAction> actions, String contributionID) {
		if (manager instanceof MenuManager) {

			// add an image to the created submenu
			if (GENERATION_CONDITION_SUB_MENU_TITLE.equals(((MenuManager) manager).getMenuText())) {
				String IMAGE_PATH = "icons/GenerateBranchSubMenuIcon.png"; //$NON-NLS-1$
				final ImageDescriptor descriptor = getImageDescriptor(Activator.PLUGIN_ID, IMAGE_PATH);
				if (null != descriptor) {
					((MenuManager) manager).setImageDescriptor(descriptor);
				}
			}
		}
	}

	/**
	 *
	 * @param pluginId
	 *            the pluginID where the image is stored
	 * @param imagePathInPlugin
	 *            the path of the image in the plugin
	 * @return
	 *         the image descriptor or <code>null</code> if not found
	 */
	private ImageDescriptor getImageDescriptor(final String pluginId, final String imagePathInPlugin) {
		ImageDescriptor imageDescriptor = Activator.getDefault().getImageRegistry().getDescriptor(imagePathInPlugin);
		if (null == imageDescriptor) {
			imageDescriptor = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, imagePathInPlugin);
			Activator.getDefault().getImageRegistry().put(imagePathInPlugin, imageDescriptor);
		}
		return imageDescriptor;
	}

	/**
	 *
	 * @param createActions
	 *            the create actions
	 * @return
	 *         <code>true</code> if the actions only contains create Expression action
	 */
	private boolean containsOnlyExpression(final Collection<IAction> createActions) {
		for (final IAction current : createActions) {
			if (false == current.getText().contains(EXPRESSION)) {
				return false;
			}
		}
		return true;
	}
}
