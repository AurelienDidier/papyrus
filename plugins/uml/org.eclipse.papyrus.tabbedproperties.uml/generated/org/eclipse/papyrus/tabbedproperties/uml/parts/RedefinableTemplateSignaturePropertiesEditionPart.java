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

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
 

// End of user code
/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public interface RedefinableTemplateSignaturePropertiesEditionPart {

	/**
	 * @return the name
	 */
	public String getName();
	
	/**
	 * Defines a new name
	 * @param newValue the new name to set
	 */
	public void setName(String newValue);
	
	public void setMessageForName (String msg, int msgLevel);	
	
	public void unsetMessageForName ();
	
	/**
	 * @return the visibility
	 */
	public Enumerator getVisibility();
	
	/**
	 * Init the visibility
	 * @param eenum the enum to manage
	 * @param current the current value
	 */
	public void initVisibility(EEnum eenum, Enumerator current);

/**
	 * Defines a new visibility
	 * @param newValue the new visibility to set
	 */
	public void setVisibility(Enumerator newValue);
	
	
	
	
	
	/**
	 * @return the isLeaf
	 */
	public Boolean getIsLeaf();
	
	/**
	 * Defines a new isLeaf
	 * @param newValue the new isLeaf to set
	 */
	public void setIsLeaf(Boolean newValue);
	
	
	
	
	
	/**
	 * @return the parameter to add
	 */
	public List getParameterToAdd();
	
	/**
	 * @return the parameter to remove
	 */
	public List getParameterToRemove();
	
	/**
	 * Init the parameter
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initParameter(EObject current, EReference containingFeature, EReference feature);

/**
	 * Update the parameter
	 * @param newValue the parameter to update
	 */
	public void updateParameter(EObject newValue);
	
	
	
	
	
	/**
	 * @return the ownedParameter to add
	 */
	public List getOwnedParameterToAdd();
	
	/**
	 * @return the ownedParameter to remove
	 */
	public List getOwnedParameterToRemove();
	
	/**
	 * @return the ownedParameter to move
	 */
	public List getOwnedParameterToMove();
	
	/**
	 * @return the ownedParameter to edit
	 */
	public Map getOwnedParameterToEdit();
	
	/**
	 * @return the current ownedParameter
	 */
	public List getOwnedParameterTable();
	
	/**
	 * Init the ownedParameter
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initOwnedParameter(EObject current, EReference containingFeature, EReference feature);

/**
	 * Update the ownedParameter
	 * @param newValue the ownedParameter to update
	 */
	public void updateOwnedParameter(EObject newValue);
	
	
	
	
	
	/**
	 * @return the extendedSignature to add
	 */
	public List getExtendedSignatureToAdd();
	
	/**
	 * @return the extendedSignature to remove
	 */
	public List getExtendedSignatureToRemove();
	
	/**
	 * Init the extendedSignature
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initExtendedSignature(EObject current, EReference containingFeature, EReference feature);

/**
	 * Update the extendedSignature
	 * @param newValue the extendedSignature to update
	 */
	public void updateExtendedSignature(EObject newValue);
	
	
	
	
	





	// Start of user code for additional methods
 	
	// End of user code
}

