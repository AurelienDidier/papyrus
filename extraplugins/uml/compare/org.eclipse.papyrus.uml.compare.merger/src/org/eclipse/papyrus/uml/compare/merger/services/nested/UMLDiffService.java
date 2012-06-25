/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) Vincent.Lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.compare.merger.services.nested;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.compare.diff.engine.IDiffEngine;
import org.eclipse.emf.compare.diff.metamodel.AbstractDiffExtension;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffGroup;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.metamodel.MoveModelElement;
import org.eclipse.emf.compare.diff.service.DiffService;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.tools.util.ReflectHelper;
import org.eclipse.papyrus.uml.compare.merger.Activator;

/**
 * 
 * A specific DiffService for the Nested UML Compare Editor
 * 
 * @see DiffService
 * @see bug 377154
 */
//TODO ask to EMF-Compare to open their API
public class UMLDiffService {

	private static Method fillRequiredDifferencesMethod = null;

	/**
	 * Utility classes don't need to (and shouldn't) be instantiated.
	 */
	private UMLDiffService() {
		// prevents instantiation
	}



	/**
	 * Return a diffmodel created using the match model. This implementation is a generic and simple one.
	 * 
	 * @param match
	 *        the matching model
	 * @param threeWay
	 *        <code>True</code> if we're computing a three way comparison, <code>False</code> otherwise.
	 * @return the corresponding diff model
	 */
	public static DiffModel doDiff(MatchModel match, boolean threeWay) {
		//		final IDiffEngine engine = getBestDiffEngine(match);
		final IDiffEngine engine = new UMLDiffEngine(); //TODO verify that it is the correct diff engine for UML
		final DiffModel diff = engine.doDiff(match, threeWay);

		final Collection<AbstractDiffExtension> extensions = DiffService.getCorrespondingDiffExtensions(match);
		for(final AbstractDiffExtension ext : extensions) {
			if(ext != null) {
				ext.visit(diff);
			}
		}

		engine.reset();

		fillRequiredDifferences(diff);
		//clean the diff
		cleanDiff(diff);

		return diff;
	}

	private static void cleanDiff(final DiffModel diffModel) {
		EList<DiffElement> differences = diffModel.getDifferences();

		final List<DiffElement> toRemove = new ArrayList<DiffElement>();
		for(DiffElement current : differences) {
			if(current instanceof MoveModelElement) {//appears when we do a comparison between 2 elements of the same model (name change for example)
				EObject leftTarget = ((MoveModelElement)current).getLeftTarget();
				EObject rightTarget = ((MoveModelElement)current).getRightTarget();
				if(leftTarget == null && rightTarget == null) {
					toRemove.add(current);
				}
			}
		}
		for(DiffElement current : toRemove) {
			EcoreUtil.remove(current);
		}

		differences = diffModel.getOwnedElements();
		if(differences.size() == 1 && differences.get(0) instanceof DiffGroup) {
			DiffGroup group = (DiffGroup)differences.get(0);
			if(group.getSubDiffElements().size() == 1 && group.getSubDiffElements().get(0) instanceof DiffGroup) {
				DiffGroup group2 = (DiffGroup)group.getSubDiffElements().get(0);
				if(!group2.isConflicting() && group2.getSubDiffElements().size() == 1) {
					DiffGroup group3 = (DiffGroup)group.getSubDiffElements().get(0);
					if(!group2.isConflicting() && group2.getSubDiffElements().size() == 1) {
						//we should remove its parent!
						//					diffModel.getOwnedElements().add(group2);
						//					EcoreUtil.remove(group);
					}
				}
			}
		}
		int i = 0;
		i++;
		//		EList<DiffElement> differences2 = diffModel.getDifferences();
		//		if(differences2.size() == 1) {
		//			DiffElement el = differences2.get(0);
		//			if(el instanceof DiffGroup) {
		//				DiffGroup group = (DiffGroup)el;
		//				if(group.getSubDiffElements().size() == 1 && group.getSubDiffElements().get(0) instanceof DiffGroup) {
		//					group = (DiffGroup)group.getSubDiffElements().get(0);
		//					int i = 0;
		//					i++;
		//
		//				}
		//			}
		//		}
	}

	//TODO write a JUnit test to be sue that the method called reflexively is always available
	private static void fillRequiredDifferences(final DiffModel diff) {
		if(fillRequiredDifferencesMethod == null) {
			try {
				fillRequiredDifferencesMethod = ReflectHelper.getMethod(DiffService.class, "fillRequiredDifferences", new Class<?>[]{ EObject.class });
			} catch (SecurityException e) {
				Activator.log.error(e);
			} catch (NoSuchMethodException e) {
				Activator.log.error(e);
			}
		}
		final Object[] parameters = new Object[1];
		parameters[0] = diff;
		try {
			fillRequiredDifferencesMethod.invoke(null, parameters);
		} catch (IllegalArgumentException e) {
			Activator.log.error(e);
		} catch (IllegalAccessException e) {
			Activator.log.error(e);
		} catch (InvocationTargetException e) {
			Activator.log.error(e);
		}
	}
}
