/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.properties.constraints;

import org.eclipse.papyrus.properties.Activator;
import org.eclipse.papyrus.properties.contexts.ConstraintDescriptor;
import org.eclipse.papyrus.properties.contexts.DisplayUnit;
import org.eclipse.papyrus.properties.contexts.View;

/**
 * An abstract implementation for the Constraint interface.
 * 
 * @author Camille Letavernier
 * 
 */
public abstract class AbstractConstraint implements Constraint {

	/**
	 * The descriptor used to instantiate this constraint.
	 * Contains some attributes for this constraint
	 */
	protected ConstraintDescriptor descriptor;

	/**
	 * The display unit (Section or View) associated to this constraint
	 */
	protected DisplayUnit display;

	public void setConstraintDescriptor(ConstraintDescriptor descriptor) {
		this.descriptor = descriptor;
		display = descriptor.getDisplay();
	}

	public View getView() {
		if(display instanceof View) {
			return (View)display;
		} else {
			Activator.log.warn("This constraint isn't owned by a View"); //$NON-NLS-1$
			return null;
		}
	}

	/**
	 * A constraint for a Single element (Exactly one) overrides
	 * the same constraint for a multiple element (One or more)
	 */
	public boolean overrides(Constraint constraint) {
		if(equivalent(constraint)) {
			if(getView().getElementMultiplicity() == 1) {
				if(constraint.getView().getElementMultiplicity() != 1) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Tests if two constraints are equivalent.
	 * Two constraints are equivalent if they have the same parameters.
	 * Two equivalent constraints may have different Display units, with
	 * different multiplicities.
	 * 
	 * @param constraint
	 * @return
	 *         True if this object is equivalent to the given constraint
	 */
	protected abstract boolean equivalent(Constraint constraint);

	public ConstraintDescriptor getDescriptor() {
		return descriptor;
	}

}
