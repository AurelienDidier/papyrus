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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
 

// End of user code
/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public interface LinkEndDestructionDataPropertiesEditionPart {

	/**
	 * @return the ownedComment to add
	 */
	public List getOwnedCommentToAdd();
	
	/**
	 * @return the ownedComment to remove
	 */
	public List getOwnedCommentToRemove();
	
	/**
	 * @return the ownedComment to move
	 */
	public List getOwnedCommentToMove();
	
	/**
	 * @return the ownedComment to edit
	 */
	public Map getOwnedCommentToEdit();
	
	/**
	 * @return the current ownedComment
	 */
	public List getOwnedCommentTable();
	
	/**
	 * Init the ownedComment
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initOwnedComment(EObject current, EReference containingFeature, EReference feature);

/**
	 * Update the ownedComment
	 * @param newValue the ownedComment to update
	 */
	public void updateOwnedComment(EObject newValue);
	
	
	
	
	
	/**
	 * @return the qualifier to add
	 */
	public List getQualifierToAdd();
	
	/**
	 * @return the qualifier to remove
	 */
	public List getQualifierToRemove();
	
	/**
	 * @return the qualifier to move
	 */
	public List getQualifierToMove();
	
	/**
	 * @return the qualifier to edit
	 */
	public Map getQualifierToEdit();
	
	/**
	 * @return the current qualifier
	 */
	public List getQualifierTable();
	
	/**
	 * Init the qualifier
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initQualifier(EObject current, EReference containingFeature, EReference feature);

/**
	 * Update the qualifier
	 * @param newValue the qualifier to update
	 */
	public void updateQualifier(EObject newValue);
	
	
	
	
	
	/**
	 * @return the isDestroyDuplicates
	 */
	public Boolean getIsDestroyDuplicates();
	
	/**
	 * Defines a new isDestroyDuplicates
	 * @param newValue the new isDestroyDuplicates to set
	 */
	public void setIsDestroyDuplicates(Boolean newValue);
	
	
	
	
	





	// Start of user code for additional methods
 	
	// End of user code
}

