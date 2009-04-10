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
public interface ExtensionEndPropertiesEditionPart {

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
	 * @return the isLeaf
	 */
	public Boolean getIsLeaf();
	
	/**
	 * Defines a new isLeaf
	 * @param newValue the new isLeaf to set
	 */
	public void setIsLeaf(Boolean newValue);
	
	
	
	
	
	/**
	 * @return the isStatic
	 */
	public Boolean getIsStatic();
	
	/**
	 * Defines a new isStatic
	 * @param newValue the new isStatic to set
	 */
	public void setIsStatic(Boolean newValue);
	
	
	
	
	
	/**
	 * @return the isOrdered
	 */
	public Boolean getIsOrdered();
	
	/**
	 * Defines a new isOrdered
	 * @param newValue the new isOrdered to set
	 */
	public void setIsOrdered(Boolean newValue);
	
	
	
	
	
	/**
	 * @return the isUnique
	 */
	public Boolean getIsUnique();
	
	/**
	 * Defines a new isUnique
	 * @param newValue the new isUnique to set
	 */
	public void setIsUnique(Boolean newValue);
	
	
	
	
	
	/**
	 * @return the isReadOnly
	 */
	public Boolean getIsReadOnly();
	
	/**
	 * Defines a new isReadOnly
	 * @param newValue the new isReadOnly to set
	 */
	public void setIsReadOnly(Boolean newValue);
	
	
	
	
	
	/**
	 * @return the deployment to add
	 */
	public List getDeploymentToAdd();
	
	/**
	 * @return the deployment to remove
	 */
	public List getDeploymentToRemove();
	
	/**
	 * @return the deployment to move
	 */
	public List getDeploymentToMove();
	
	/**
	 * @return the deployment to edit
	 */
	public Map getDeploymentToEdit();
	
	/**
	 * @return the current deployment
	 */
	public List getDeploymentTable();
	
	/**
	 * Init the deployment
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initDeployment(EObject current, EReference containingFeature, EReference feature);

/**
	 * Update the deployment
	 * @param newValue the deployment to update
	 */
	public void updateDeployment(EObject newValue);
	
	
	
	
	
	/**
	 * @return the templateBinding to add
	 */
	public List getTemplateBindingToAdd();
	
	/**
	 * @return the templateBinding to remove
	 */
	public List getTemplateBindingToRemove();
	
	/**
	 * @return the templateBinding to move
	 */
	public List getTemplateBindingToMove();
	
	/**
	 * @return the templateBinding to edit
	 */
	public Map getTemplateBindingToEdit();
	
	/**
	 * @return the current templateBinding
	 */
	public List getTemplateBindingTable();
	
	/**
	 * Init the templateBinding
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initTemplateBinding(EObject current, EReference containingFeature, EReference feature);

/**
	 * Update the templateBinding
	 * @param newValue the templateBinding to update
	 */
	public void updateTemplateBinding(EObject newValue);
	
	
	
	
	
	/**
	 * @return the isDerived
	 */
	public Boolean getIsDerived();
	
	/**
	 * Defines a new isDerived
	 * @param newValue the new isDerived to set
	 */
	public void setIsDerived(Boolean newValue);
	
	
	
	
	
	/**
	 * @return the isDerivedUnion
	 */
	public Boolean getIsDerivedUnion();
	
	/**
	 * Defines a new isDerivedUnion
	 * @param newValue the new isDerivedUnion to set
	 */
	public void setIsDerivedUnion(Boolean newValue);
	
	
	
	
	
	/**
	 * @return the aggregation
	 */
	public Enumerator getAggregation();
	
	/**
	 * Init the aggregation
	 * @param eenum the enum to manage
	 * @param current the current value
	 */
	public void initAggregation(EEnum eenum, Enumerator current);

/**
	 * Defines a new aggregation
	 * @param newValue the new aggregation to set
	 */
	public void setAggregation(Enumerator newValue);
	
	
	
	
	
	/**
	 * @return the redefinedProperty to add
	 */
	public List getRedefinedPropertyToAdd();
	
	/**
	 * @return the redefinedProperty to remove
	 */
	public List getRedefinedPropertyToRemove();
	
	/**
	 * Init the redefinedProperty
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initRedefinedProperty(EObject current, EReference containingFeature, EReference feature);

/**
	 * Update the redefinedProperty
	 * @param newValue the redefinedProperty to update
	 */
	public void updateRedefinedProperty(EObject newValue);
	
	
	
	
	
	/**
	 * @return the subsettedProperty to add
	 */
	public List getSubsettedPropertyToAdd();
	
	/**
	 * @return the subsettedProperty to remove
	 */
	public List getSubsettedPropertyToRemove();
	
	/**
	 * Init the subsettedProperty
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initSubsettedProperty(EObject current, EReference containingFeature, EReference feature);

/**
	 * Update the subsettedProperty
	 * @param newValue the subsettedProperty to update
	 */
	public void updateSubsettedProperty(EObject newValue);
	
	
	
	
	
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
	
	
	
	
	





	// Start of user code for additional methods
 	
	// End of user code
}

