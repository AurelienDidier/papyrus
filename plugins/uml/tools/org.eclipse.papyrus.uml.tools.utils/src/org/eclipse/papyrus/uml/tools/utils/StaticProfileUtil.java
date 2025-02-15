/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation (Ansgar Radermacher)
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.utils;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.Stereotype;


/**
 * Obtain information about an eventually applied Ecore::EPackage stereotype.
 * The ecore profile should be a static one, but it is not. Therefore, information
 * needs to be retrieved using the generic functions and string constants.
 */
public class StaticProfileUtil {

	/**
	 * qualified name of EPackage stereotype (used for static profile detection)
	 */
	public static final String EPackage_QNAME = "Ecore::EPackage"; //$NON-NLS-1$

	/**
	 * Attributes of EPackage stereotype
	 */
	public static final String EPKG_BASE_PACKAGE = "basePackage"; //$NON-NLS-1$
	public static final String EPKG_PACKAGE_NAME = "packageName"; //$NON-NLS-1$
	public static final String EPKG_NS_URI = "nsURI"; //$NON-NLS-1$
	public static final String EPKG_NS_PREFIX = "nsPrefix"; //$NON-NLS-1$

	/**
	 * set the profile information
	 * @param profile
	 */
	public StaticProfileUtil(Profile profile) {
		Stereotype ePkg = profile.getAppliedStereotype(EPackage_QNAME);
		// create definition
		definition = EcoreFactory.eINSTANCE.createEPackage();
		if (ePkg != null) {
			basePackage = (String) profile.getValue(ePkg, EPKG_BASE_PACKAGE);
			packageName = (String) profile.getValue(ePkg, EPKG_PACKAGE_NAME);
			definition.setNsPrefix((String) profile.getValue(ePkg, EPKG_NS_PREFIX));
		}
		else {
			basePackage = null;
			packageName = null;
			definition.setNsPrefix(""); //$NON-NLS-1$
		}
		String nsURI = null;
		if (ePkg != null) {
			// examine the ePkg stereotype attribute first
			nsURI = (String) profile.getValue(ePkg, EPKG_NS_URI);
		}
		if (nsURI == null || nsURI.isEmpty()) {
			// still undefined or empty, get URI from UML attribute
			nsURI = profile.getURI();
		}
		if (nsURI == null) {
			nsURI = ""; //$NON-NLS-1$
		}
		definition.setNsURI(nsURI);
	}

	/**
	 * @return the base package.
	 */
	public String getBasePackage() {
		return basePackage;
	}

	/**
	 * @return the package name.
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @return a "definition" filled with information about nsPrefix and nsURI.
	 */
	public EPackage getDefinition() {
		return definition;
	}

	protected String basePackage;

	protected String packageName;

	protected EPackage definition;
}


