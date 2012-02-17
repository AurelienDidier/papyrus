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
package org.eclipse.papyrus.uml.compare.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.compare.common.handler.AbstractEMFCompareEditorHandler;
import org.eclipse.papyrus.uml.compare.editor.UMLCompareEditor;
import org.eclipse.uml2.uml.Element;


public class UMLCompareEditorHandler extends AbstractEMFCompareEditorHandler {

	public UMLCompareEditorHandler() {
		super(UMLCompareEditor.EDITOR_TYPE);
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.emf.compare.common.handler.AbstractEMFCompareEditorHandler#getSelection()
	 *
	 * @return
	 */
	@Override
	protected List<EObject> getSelection() {
		List<EObject> umlSelection = new ArrayList<EObject>();
		for(EObject current : super.getSelection()) {
			if(current instanceof Element) {
				umlSelection.add(current);
			}
		}
		return umlSelection;
	}

}
