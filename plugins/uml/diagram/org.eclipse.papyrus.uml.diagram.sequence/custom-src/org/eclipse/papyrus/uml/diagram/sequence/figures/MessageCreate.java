/*****************************************************************************
 * Copyright (c) 2010 - 2017 CEA LIST, ALL4TEC and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Soyatec - Initial API and implementation
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 522305
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.figures;

import org.eclipse.draw2d.RotatableDecoration;

/**
 * @author Jin Liu (jin.liu@soyatec.com)
 */
public class MessageCreate extends MessageFigure {

	/**
	 * Constructor.
	 */
	public MessageCreate() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.diagram.sequence.figures.MessageFigure#createTargetDecoration()
	 */
	@Override
	protected RotatableDecoration createTargetDecoration() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.uml.diagram.sequence.figures.MessageFigure#createSourceDecoration()
	 */
	@Override
	protected RotatableDecoration createSourceDecoration() {
		return null;
	}

}
