/*****************************************************************************
 * Copyright (c) 2010, 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Thibault Le Ouay t.leouay@sherpa-eng.com - Add SelectOutputPage
 *  Christian W. Damus (CEA) - bug 422257
 *
 *****************************************************************************/
package org.eclipse.papyrus.customization.properties.generation.wizard;

import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.papyrus.customization.properties.generation.extensionpoint.GeneratorExtensionPoint;
import org.eclipse.papyrus.customization.properties.generation.generators.IGenerator;
import org.eclipse.papyrus.customization.properties.generation.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * A WizardPage for selecting the method of generation (e.g. from Ecore
 * Metamodel or from Profile model)
 *
 * @author Camille Letavernier
 *
 */
public class CreateContextMainPage extends AbstractCreateContextPage implements Listener {

	private CCombo combo;

	private final List<IGenerator> generators;

	/**
	 * Constructor
	 */
	public CreateContextMainPage() {
		super(Messages.CreateContextMainPage_title);

		generators = new GeneratorExtensionPoint().getGenerators();
	}

	@Override
	public void dispose() {
		try {
			for (IGenerator next : generators) {
				next.dispose();
			}
		} finally {
			super.dispose();
		}
	}

	public void createControl(Composite parent) {
		Composite root = new Composite(parent, SWT.NONE);
		root.setLayout(new GridLayout(1, false));

		combo = new CCombo(root, SWT.BORDER);
		for (IGenerator generator : generators) {
			combo.add(generator.getName());
		}
		combo.setEditable(false);
		combo.setBackground(new Color(combo.getDisplay(), 255, 255, 255));
		combo.select(0);
		combo.addListener(SWT.Selection, this);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		setControl(root);
		setDescription(Messages.CreateContextMainPage_description);
	}

	@Override
	public IWizardPage getNextPage() {
		int selection = combo.getSelectionIndex();
		getWizard().setGenerator(generators.get(selection));
		// getWizard().generatorPage.clearTarget();
		return getWizard().generatorPage;
	}

	public void handleEvent(Event event) {
		super.setPageComplete(true);
	}

}
