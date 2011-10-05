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
 *  Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.diagram.modelexplorer;


import java.text.MessageFormat;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.papyrus.core.editorsfactory.IPageIconsRegistry;
import org.eclipse.papyrus.core.editorsfactory.PageIconsRegistry;
import org.eclipse.papyrus.core.services.ServiceException;
import org.eclipse.papyrus.core.utils.EditorUtils;
import org.eclipse.papyrus.core.utils.ServiceUtils;
import org.eclipse.papyrus.modelexplorer.MoDiscoLabelProvider;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.uml2.uml.NamedElement;

/**
 * the label provider that inherits of modisco label provider
 * 
 */
public class DiagramLabelProvider extends MoDiscoLabelProvider  {
	
	protected static final String SEPARATOR = ": ";
	protected static final String PAPYRUS_UML = "PapyrusUML";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText(Object element) {
		String text = super.getText(element);
		if(element instanceof Diagram) {
			Diagram diagram = (Diagram)element;
			if(diagram.getElement() instanceof NamedElement){
			text = text +" [" +((NamedElement)diagram.getElement()).getQualifiedName()+"]";
			}
		} 
		return text;
	}


}
