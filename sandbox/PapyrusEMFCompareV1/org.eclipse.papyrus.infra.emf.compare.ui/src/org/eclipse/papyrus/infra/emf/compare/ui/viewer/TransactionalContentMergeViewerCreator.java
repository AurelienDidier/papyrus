/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
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
package org.eclipse.papyrus.infra.emf.compare.ui.viewer;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IViewerCreator;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;


public class TransactionalContentMergeViewerCreator implements IViewerCreator {

	public TransactionalContentMergeViewerCreator() {
		// TODO Auto-generated constructor stub
	}

	public Viewer createViewer(Composite parent, CompareConfiguration config) {
		//return new  ModelContentMergeViewer(parent,config);
		return new TransactionalModelContentMergeViewer(parent, config);
	}
//	CompareConfiguration
	
}
