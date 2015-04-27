/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Thibault Le Ouay (Sherpa Engineering) t.leouay@sherpa-eng.com  - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.wizards.pages;

import org.eclipse.papyrus.uml.diagram.wizards.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;


public class PapyrusProjectCreationPage extends WizardNewProjectCreationPage {

	private Text fileName;

	private Listener fileNameModifyListener = new Listener() {

		public void handleEvent(Event e) {
			boolean valid = canFlipToNextPage();
			setPageComplete(valid);

		}
	};

	public PapyrusProjectCreationPage(String pageName) {
		super(pageName);
	}



	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		Composite composite = (Composite)getControl();
		composite.setLayoutData(new GridData(SWT.FILL, SWT.DOWN, true, false));
		GridLayout gridLayout = new GridLayout(1, false);
		composite.setLayout(gridLayout);
		setControl(composite);
		Group group = createGroup(composite, Messages.PapyrusProjectCreationPage_0);
		fileName = new Text(group, SWT.BORDER);
		fileName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		fileName.setText(Messages.PapyrusProjectCreationPage_1);
		fileName.addListener(SWT.Modify, fileNameModifyListener);
		setPageComplete(false);
	}

	/**
	 * Creates the group.
	 *
	 * @param parent
	 *        the parent
	 * @param name
	 *        the name
	 * @return the group
	 */
	private static Group createGroup(Composite parent, String name) {
		Group group = new Group(parent, SWT.NONE);
		group.setText(name);
		GridLayout layout = new GridLayout(1, true);
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		group.setLayout(layout);
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		group.setLayoutData(data);
		return group;
	}

	@Override
	protected boolean validatePage() {
		if(fileName != null) {
			if(fileName.getText().equals("")) { //$NON-NLS-1$
				this.setErrorMessage(Messages.PapyrusProjectCreationPage_3);
				return false;
			}
		}

		return super.validatePage();
	}

	public String getFileName() {
		return fileName.getText();
	}

	@Override
	public boolean canFlipToNextPage() {
		if(validatePage() && !fileName.getText().equals("")) { //$NON-NLS-1$
			return true;
		}
		return false;
	}

	@Override
	public void performHelp() {
		PlatformUI.getWorkbench().getHelpSystem().displayHelp("org.eclipse.papyrus.uml.diagram.wizards.NewProject"); //$NON-NLS-1$

	}
}
