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
package org.eclipse.papyrus.uml.compare.merge.commands;

import java.util.List;

import org.eclipse.emf.compare.diff.merge.IMergeListener;
import org.eclipse.emf.compare.diff.merge.MergeEvent;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;


public class FireMergeDiffStartCommand extends AbstractFireMergeCommand {

	public FireMergeDiffStartCommand(final DiffElement diffElement, final List<IMergeListener> listeners) {
		super(diffElement, listeners);
	}

	public void execute() {
		List<IMergeListener> listeners = getMergeListeners();
		if(getDiffElement() != null) {
			for(IMergeListener current : listeners) {
				current.mergeDiffEnd(new MergeEvent(getDiffElement()));
			}

		}
	}

	@Override
	public void undo() {
		//TODO?
	}
}
