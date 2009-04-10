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
public interface DeploymentPropertiesEditionPart {

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
	 * @return the clientDependency to add
	 */
	public List getClientDependencyToAdd();
	
	/**
	 * @return the clientDependency to remove
	 */
	public List getClientDependencyToRemove();
	
	/**
	 * Init the clientDependency
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initClientDependency(EObject current, EReference containingFeature, EReference feature);

/**
	 * Update the clientDependency
	 * @param newValue the clientDependency to update
	 */
	public void updateClientDependency(EObject newValue);
	
	
	
	
	
	/**
	 * @return the supplier to add
	 */
	public List getSupplierToAdd();
	
	/**
	 * @return the supplier to remove
	 */
	public List getSupplierToRemove();
	
	/**
	 * Init the supplier
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initSupplier(EObject current, EReference containingFeature, EReference feature);

/**
	 * Update the supplier
	 * @param newValue the supplier to update
	 */
	public void updateSupplier(EObject newValue);
	
	
	
	
	
	/**
	 * @return the client to add
	 */
	public List getClientToAdd();
	
	/**
	 * @return the client to remove
	 */
	public List getClientToRemove();
	
	/**
	 * Init the client
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initClient(EObject current, EReference containingFeature, EReference feature);

/**
	 * Update the client
	 * @param newValue the client to update
	 */
	public void updateClient(EObject newValue);
	
	
	
	
	
	/**
	 * @return the deployedArtifact to add
	 */
	public List getDeployedArtifactToAdd();
	
	/**
	 * @return the deployedArtifact to remove
	 */
	public List getDeployedArtifactToRemove();
	
	/**
	 * Init the deployedArtifact
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initDeployedArtifact(EObject current, EReference containingFeature, EReference feature);

/**
	 * Update the deployedArtifact
	 * @param newValue the deployedArtifact to update
	 */
	public void updateDeployedArtifact(EObject newValue);
	
	
	
	
	
	/**
	 * @return the configuration to add
	 */
	public List getConfigurationToAdd();
	
	/**
	 * @return the configuration to remove
	 */
	public List getConfigurationToRemove();
	
	/**
	 * @return the configuration to move
	 */
	public List getConfigurationToMove();
	
	/**
	 * @return the configuration to edit
	 */
	public Map getConfigurationToEdit();
	
	/**
	 * @return the current configuration
	 */
	public List getConfigurationTable();
	
	/**
	 * Init the configuration
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initConfiguration(EObject current, EReference containingFeature, EReference feature);

/**
	 * Update the configuration
	 * @param newValue the configuration to update
	 */
	public void updateConfiguration(EObject newValue);
	
	
	
	
	





	// Start of user code for additional methods
 	
	// End of user code
}

