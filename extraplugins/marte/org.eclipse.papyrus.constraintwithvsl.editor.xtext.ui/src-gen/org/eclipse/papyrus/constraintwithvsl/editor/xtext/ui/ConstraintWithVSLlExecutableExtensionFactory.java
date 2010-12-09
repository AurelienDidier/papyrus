
/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
 
package org.eclipse.papyrus.constraintwithvsl.editor.xtext.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

/**
 *@generated
 */
public class ConstraintWithVSLlExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return org.eclipse.papyrus.constraintwithvsl.editor.xtext.ui.internal.ConstraintWithVSLlActivator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return org.eclipse.papyrus.constraintwithvsl.editor.xtext.ui.internal.ConstraintWithVSLlActivator.getInstance().getInjector("org.eclipse.papyrus.constraintwithvsl.editor.xtext.ConstraintWithVSLl");
	}
	
}
