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
package org.eclipse.papyrus.tabbedproperties.uml.providers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.providers.IPropertiesEditionProvider;
import org.eclipse.papyrus.tabbedproperties.uml.components.ElementPropertiesEditionComponent;
import org.eclipse.papyrus.tabbedproperties.uml.components.GeneralizationBasePropertiesEditionComponent;
import org.eclipse.papyrus.tabbedproperties.uml.components.GeneralizationPropertiesEditionComponent;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class GeneralizationPropertiesEditionProvider implements IPropertiesEditionProvider {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.providers.IPropertiesEditionProvider#provides(org.eclipse.emf.ecore.EObject)
	 */
	public boolean provides(EObject eObject) {
		return (eObject instanceof Generalization) && (UMLPackage.eINSTANCE.getGeneralization() == eObject.eClass());
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.providers.IPropertiesEditionProvider#getPropertiesEditionComponent(org.eclipse.emf.ecore.EObject,
	 *      java.lang.String)
	 */
	public IPropertiesEditionComponent getPropertiesEditionComponent(EObject eObject, String mode) {
		if (eObject instanceof Generalization) {
			return new GeneralizationPropertiesEditionComponent(eObject, mode);
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.providers.IPropertiesEditionProvider#getPropertiesEditionComponent(org.eclipse.emf.ecore.EObject,
	 *      java.lang.String, java.lang.String)
	 */
	public IPropertiesEditionComponent getPropertiesEditionComponent(EObject eObject, String mode, String part) {
		if (eObject instanceof Generalization) {
			if (GeneralizationBasePropertiesEditionComponent.BASE_PART.equals(part))
				return new GeneralizationBasePropertiesEditionComponent(eObject, mode);
			if (ElementPropertiesEditionComponent.COMMENTS_PART.equals(part))			
				return new ElementPropertiesEditionComponent(eObject, mode);
		}
		return null;
	}
	
}	
