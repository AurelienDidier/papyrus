/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.sysml14.diagram.blockdefinition.command;

import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.view.factories.optimal.BasicCompartmentViewFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.adapter.SemanticAdapter;

/**
 * Command to create the compartment displaying shapes for an element
 */
public class CreateDynamicCompartmentViewCommand extends RecordingCommand {

	/** owner of the compartment view to create */
	private View owner;

	/** boolean that indicates if the comaprtment has to be visible or not */
	private boolean isVisible;

	private String visualHint;

	private PreferencesHint preferenceHint;

	/**
	 * Creates a new CreateShapeCompartmentViewCommand.
	 *
	 * @param domain
	 *            editing domain used to manipulate model
	 * @param label
	 *            the label of the command
	 * @param description
	 *            description of the command
	 */
	public CreateDynamicCompartmentViewCommand(TransactionalEditingDomain domain, String label,String visualHint, String description, View owner, boolean isVisible,PreferencesHint preferenceHint) {
		super(domain, label, description);
		this.owner = owner;
		this.setVisible(isVisible);
		this.visualHint= visualHint;
		this.preferenceHint= preferenceHint;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute() {
		BasicCompartmentViewFactory compartmentViewFactory= new BasicCompartmentViewFactory();
		compartmentViewFactory.createView(new SemanticAdapter(owner.getElement(), null), owner, visualHint, -1, true,preferenceHint );
	}

	/**
	 * Returns <code>true</code> if the created compartment should be visible
	 *
	 * @return <code>true</code> if the created compartment should be visible
	 */
	public boolean isVisible() {
		return isVisible;
	}

	/**
	 * Sets the visiblity of the created compartment
	 *
	 * @param isVisible
	 *            <code>true</code> if the compartment should be visible
	 */
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
}
