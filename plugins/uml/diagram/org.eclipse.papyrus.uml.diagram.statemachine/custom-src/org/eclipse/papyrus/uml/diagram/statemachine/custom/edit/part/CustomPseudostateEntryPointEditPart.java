/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.statemachine.custom.edit.part;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointEditPart;

/**
 * @author Mickael ADAM
 *
 */
public class CustomPseudostateEntryPointEditPart extends PseudostateEntryPointEditPart {

	/**
	 * Constructor.
	 *
	 * @param view
	 */
	public CustomPseudostateEntryPointEditPart(View view) {
		super(view);
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.RoundedBorderNamedElementEditPart#getDefaultIsOvalValue()
	 *
	 * @return
	 */
	@Override
	protected boolean getDefaultIsOvalValue() {
		return true;
	}

}
