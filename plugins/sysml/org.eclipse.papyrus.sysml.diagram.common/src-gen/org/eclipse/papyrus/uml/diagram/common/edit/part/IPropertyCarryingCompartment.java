/*****************************************************************************
 * Copyright (c) 2012 Atos.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *		
 *		Olivier M�lois (ATOS) olivier.melois@atos.net - Initial API implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.edit.part;

import org.eclipse.uml2.uml.Property;

/**
 * Interface used to check whether a compartment, which is meant to carry
 * properties, can actually carry a certain property (that depends on the
 * stereotype applications)
 * @author omelois
 *
 */
public interface IPropertyCarryingCompartment {
	
	public boolean acceptProperty(Property property);
}
