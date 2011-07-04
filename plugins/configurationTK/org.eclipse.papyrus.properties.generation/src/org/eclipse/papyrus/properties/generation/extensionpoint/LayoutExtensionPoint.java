/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.properties.generation.extensionpoint;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.properties.Activator;
import org.eclipse.papyrus.properties.generation.layout.ILayoutGenerator;
import org.eclipse.papyrus.properties.generation.wizard.CreateContextWizard;

/**
 * Handles the extension point org.eclipse.papyrus.properties.generation.layout
 * Registers the given layout Generator to the Property view generation wizard
 * 
 * @author Camille Letavernier
 */
public class LayoutExtensionPoint {

	private final String EXTENSION_ID = "org.eclipse.papyrus.properties.generation.layout"; //$NON-NLS-1$

	/**
	 * Constructor.
	 */
	public LayoutExtensionPoint() {
		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);

		for(IConfigurationElement e : config) {
			String generatorClassName = e.getAttribute("generator"); //$NON-NLS-1$
			try {
				Class<? extends ILayoutGenerator> generatorClass = Class.forName(generatorClassName).asSubclass(ILayoutGenerator.class);
				ILayoutGenerator generator = generatorClass.newInstance();
				CreateContextWizard.addLayoutGenerator(generator);
			} catch (Exception ex) {
				Activator.log.error("Cannot instantiate the layout generator : " + generatorClassName, ex); //$NON-NLS-1$
			}
		}
	}
}
