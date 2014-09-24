/*****************************************************************************
 * Copyright (c) 2012, 2014 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 440108
 *   
 *****************************************************************************/
package org.eclipse.papyrus.uml.tools.tests.tests;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ContentProviderTest.class, UMLStereotypePropertyContentProviderTest.class, DependencyManagementTest.class,
		NamedElementValidatorTest.class, StereotypeElementListenerTest.class

})
public class AllTests {
	// Test suite
}
