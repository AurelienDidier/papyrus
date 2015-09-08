/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.papyrus.uml.diagram.common.helper.AssociationEndTargetMultiplicityLabelHelper;
import org.eclipse.papyrus.uml.diagram.common.helper.PropertyLabelHelper;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;

/**
 * this class enables to refresh the multiplicity label of the association end (target)
 *
 */
public class DisplayAssociationEndTargetMultiplicityEditPolicy extends DisplayAssociationEndTargetEditPolicy {

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.DisplayAssociationEndEditPolicy#getDefaultDisplayValue()
	 *
	 */
	@Override
	public Collection<String> getDefaultDisplayValue() {
		return Collections.singleton(ICustomAppearance.DISP_MULTIPLICITY_NO_BRACKETS);
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.DisplayAssociationEndEditPolicy#createPropertyLabelHelper()
	 *
	 * @return
	 */
	@Override
	protected PropertyLabelHelper createPropertyLabelHelper() {
		return AssociationEndTargetMultiplicityLabelHelper.getInstance();
	}
}
