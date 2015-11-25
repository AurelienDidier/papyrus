/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.navigation.service;

import org.eclipse.papyrus.infra.core.services.IService;

/**
 * A Service to navigate from an element.
 * The navigation is based on external contributions.
 *
 * Examples:
 * - Navigate from a TypedElement to its Type declaration in the ModelExplorer
 * - ...
 *
 * @author Camille Letavernier
 *
 * @see NavigationContributor
 */
public interface NavigationService extends IService, NavigationContributor, NavigationMenuContributor {

	/**
	 * Creates a Selection Menu to display all the NavigableElement which can be reached from an element
	 *
	 * @param fromElement
	 * @param parent
	 * @return
	 */
	public NavigationMenu createNavigationList();

	/**
	 * Navigate to the target of the given NavigableElement (e.g. To the type of a TypedElement)
	 *
	 * @param navigableElement
	 */
	public void navigate(NavigableElement navigableElement);

	/**
	 * Navigate directly to the given element (e.g. a UML Element)
	 *
	 * @param element
	 */
	public void navigate(Object element);

	/**
	 * Navigate to the given element with the chosen target provider
	 *
	 * @param element
	 */
	public void navigate(Object element, String providerClassKey);
}
