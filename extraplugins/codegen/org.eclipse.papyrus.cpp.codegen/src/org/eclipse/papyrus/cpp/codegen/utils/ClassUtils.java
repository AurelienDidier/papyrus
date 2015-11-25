/*******************************************************************************
 * Copyright (c) 2006 - 2012 CEA LIST.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     CEA LIST - initial API and implementation
 *******************************************************************************/

package org.eclipse.papyrus.cpp.codegen.utils;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.papyrus.codegen.base.GenUtils;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Operation;

/**
 * A set of utility functions related to classes.
 *
 * @author ansgar (ansgar.radermacher@cea.fr)
 *
 */
public class ClassUtils {

	/**
	 * Calculate the list of classifiers that needs to be included
	 *
	 * @param currentClass
	 * @return
	 */
	public static EList<Classifier> includedClassifiers(Classifier currentClass) {
		// Retrieve package used by current package (dependencies)
		// use a unique list to avoid duplicates
		EList<Classifier> usedClasses = new UniqueEList<Classifier>();

		// class attributes dependencies
		usedClasses.addAll(GenUtils.getOwnedAttributeTypes(currentClass));
		// operation parameters dependencies
		usedClasses.addAll(GenUtils.getTypesViaOperations(currentClass));
		// inner classifier dependencies
		usedClasses.addAll(GenUtils.getInnerClassifierTypes(currentClass));
		// realized interface dependencies
		if (currentClass instanceof Class) {
			Class clazz = (Class) currentClass;
			EList<Interface> implementedInterfaces = clazz.getImplementedInterfaces();
			usedClasses.addAll(implementedInterfaces);
		}
		// dependencies and associations
		usedClasses.addAll(GenUtils.getTypesViaRelationshipsNoDeps(currentClass));

		// template parameters are declared locally (if owned) and do not correspond to a file
		// that can be included
		usedClasses.removeAll(GenUtils.getTemplateParameteredElements(currentClass));
		return usedClasses;
	}
	
	/**
	 * Retrieve the list of operations of classes nested in the current class
	 * without the operations directly owned by the current class
	 * 
	 * @param currentClass
	 * @return
	 */
	public static EList<Operation> nestedOperations(Classifier currentClass) {
		EList<Operation> nestedOperations = new UniqueEList<Operation>();
		
		for (Element element : currentClass.allOwnedElements()) {
			if (element instanceof Operation && !(element.getOwner().equals(currentClass))) {
				nestedOperations.add((Operation) element);
			}
		}
		
		return nestedOperations;
	}
}
