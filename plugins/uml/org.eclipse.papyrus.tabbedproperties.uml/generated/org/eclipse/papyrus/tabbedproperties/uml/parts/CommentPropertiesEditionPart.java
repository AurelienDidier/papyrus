/*******************************************************************************
 * Copyright (c) 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.tabbedproperties.uml.parts;

// Start of user code for imports

import java.util.List;
import java.util.Map;

import java.util.List;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;

// End of user code
/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public interface CommentPropertiesEditionPart {

	/**
	 * @return the body
	 */
	public String getBody();

	/**
	 * Defines a new body
	 * 
	 * @param newValue
	 *            the new body to set
	 */
	public void setBody(String newValue);

	public void setMessageForBody(String msg, int msgLevel);

	public void unsetMessageForBody();

	/**
	 * @return the annotatedElement to add
	 */
	public List getAnnotatedElementToAdd();

	/**
	 * @return the annotatedElement to remove
	 */
	public List getAnnotatedElementToRemove();

	/**
	 * Init the annotatedElement
	 * 
	 * @param current
	 *            the current value
	 * @param containgFeature
	 *            the feature where to navigate if necessary
	 * @param feature
	 *            the feature to manage
	 */
	public void initAnnotatedElement(EObject current, EReference containingFeature, EReference feature);

	/**
	 * Update the annotatedElement
	 * 
	 * @param newValue
	 *            the annotatedElement to update
	 */
	public void updateAnnotatedElement(EObject newValue);

	// Start of user code for additional methods

	// End of user code
}
