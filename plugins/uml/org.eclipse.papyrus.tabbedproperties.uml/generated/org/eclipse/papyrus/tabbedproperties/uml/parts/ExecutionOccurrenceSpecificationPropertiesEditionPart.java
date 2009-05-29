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
import java.util.Map;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.ViewerFilter;
 

// End of user code

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public interface ExecutionOccurrenceSpecificationPropertiesEditionPart {

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
	 * Adds the given filter to the ownedComment edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 */
	public void addFilterToOwnedComment(ViewerFilter filter);

	/**
	 * Adds the given filter to the ownedComment edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 */
	public void addBusinessFilterToOwnedComment(ViewerFilter filter);





	/**
	 * @return the name
	 */
	public String getName();

	/**
	 * Defines a new name
	 * @param newValue the new name to set
	 */
	public void setName(String newValue);

	public void setMessageForName(String msg, int msgLevel);

	public void unsetMessageForName();

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
	 * @return the current clientDependency
	 */
	public List getClientDependencyTable();

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
	 * Adds the given filter to the clientDependency edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 */
	public void addFilterToClientDependency(ViewerFilter filter);

	/**
	 * Adds the given filter to the clientDependency edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 */
	public void addBusinessFilterToClientDependency(ViewerFilter filter);





	/**
	 * @return the covered to add
	 */
	public List getCoveredToAdd();

	/**
	 * @return the covered to remove
	 */
	public List getCoveredToRemove();

	/**
	 * @return the current covered
	 */
	public List getCoveredTable();

	/**
	 * Init the covered
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initCovered(EObject current, EReference containingFeature, EReference feature);

	/**
	 * Update the covered
	 * @param newValue the covered to update
	 */
	public void updateCovered(EObject newValue);

	/**
	 * Adds the given filter to the covered edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 */
	public void addFilterToCovered(ViewerFilter filter);

	/**
	 * Adds the given filter to the covered edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 */
	public void addBusinessFilterToCovered(ViewerFilter filter);





	/**
	 * @return the generalOrdering to add
	 */
	public List getGeneralOrderingToAdd();

	/**
	 * @return the generalOrdering to remove
	 */
	public List getGeneralOrderingToRemove();

	/**
	 * @return the generalOrdering to move
	 */
	public List getGeneralOrderingToMove();

	/**
	 * @return the generalOrdering to edit
	 */
	public Map getGeneralOrderingToEdit();

	/**
	 * @return the current generalOrdering
	 */
	public List getGeneralOrderingTable();

	/**
	 * Init the generalOrdering
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initGeneralOrdering(EObject current, EReference containingFeature, EReference feature);

	/**
	 * Update the generalOrdering
	 * @param newValue the generalOrdering to update
	 */
	public void updateGeneralOrdering(EObject newValue);

	/**
	 * Adds the given filter to the generalOrdering edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 */
	public void addFilterToGeneralOrdering(ViewerFilter filter);

	/**
	 * Adds the given filter to the generalOrdering edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 */
	public void addBusinessFilterToGeneralOrdering(ViewerFilter filter);





	/**
	 * @return the toBefore to add
	 */
	public List getToBeforeToAdd();

	/**
	 * @return the toBefore to remove
	 */
	public List getToBeforeToRemove();

	/**
	 * @return the current toBefore
	 */
	public List getToBeforeTable();

	/**
	 * Init the toBefore
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initToBefore(EObject current, EReference containingFeature, EReference feature);

	/**
	 * Update the toBefore
	 * @param newValue the toBefore to update
	 */
	public void updateToBefore(EObject newValue);

	/**
	 * Adds the given filter to the toBefore edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 */
	public void addFilterToToBefore(ViewerFilter filter);

	/**
	 * Adds the given filter to the toBefore edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 */
	public void addBusinessFilterToToBefore(ViewerFilter filter);





	/**
	 * @return the toAfter to add
	 */
	public List getToAfterToAdd();

	/**
	 * @return the toAfter to remove
	 */
	public List getToAfterToRemove();

	/**
	 * @return the current toAfter
	 */
	public List getToAfterTable();

	/**
	 * Init the toAfter
	 * @param current the current value
	 * @param containgFeature the feature where to navigate if necessary
	 * @param feature the feature to manage
	 */
	public void initToAfter(EObject current, EReference containingFeature, EReference feature);

	/**
	 * Update the toAfter
	 * @param newValue the toAfter to update
	 */
	public void updateToAfter(EObject newValue);

	/**
	 * Adds the given filter to the toAfter edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 */
	public void addFilterToToAfter(ViewerFilter filter);

	/**
	 * Adds the given filter to the toAfter edition editor.
	 * 
	 * @param filter
	 *            a viewer filter
	 * @see org.eclipse.jface.viewers.StructuredViewer#addFilter(ViewerFilter)
	 */
	public void addBusinessFilterToToAfter(ViewerFilter filter);










	// Start of user code for additional methods
	
	// End of user code

}

