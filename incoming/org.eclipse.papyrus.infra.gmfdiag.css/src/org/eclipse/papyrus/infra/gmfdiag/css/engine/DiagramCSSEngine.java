/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.css.engine;

import java.io.IOException;
import java.net.URL;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.handler.RefreshHandler;
import org.eclipse.papyrus.infra.gmfdiag.css.Activator;
import org.eclipse.papyrus.infra.gmfdiag.css.helper.SemanticElementHelper;
import org.eclipse.papyrus.infra.gmfdiag.css.notation.CSSDiagram;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StyleSheet;
import org.eclipse.papyrus.infra.gmfdiag.css.stylesheets.StyleSheetReference;
import org.w3c.dom.Element;

@SuppressWarnings("restriction")
public class DiagramCSSEngine extends ExtendedCSSEngineImpl implements IChangeListener {

	private CSSDiagram diagram;

	public DiagramCSSEngine(ExtendedCSSEngine parent, CSSDiagram diagram) {
		super(parent);
		this.diagram = diagram;

		setElementProvider(new GMFElementProvider());
	}

	@Override
	protected void reloadStyleSheets() {
		styleSheets.clear();
		for(StyleSheet styleSheet : diagram.getStyleSheets()) {
			//Do not call super#addStyleSheet(styleSheet) to avoid a StackOverFlow
			styleSheets.add(styleSheet);
		}
	}

	@Override
	protected void parseStyleSheet(StyleSheetReference styleSheet) throws IOException {
		String path = styleSheet.getPath();
		if(path.startsWith("/")) {
			path = "platform:/resource" + path; //Either plug-in or workspace
		} else {
			URI uri = URI.createURI(styleSheet.getPath());
			uri = uri.resolve(diagram.eResource().getURI());
			path = uri.toString();
		}
		URL url = new URL(path);
		parseStyleSheet(url.openStream());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * Returns the GMF Notation EObject
	 */
	@Override
	public EObject getNativeWidget(Object element) {
		element = super.getNativeWidget(element);

		if(element == null) {
			return null;
		}

		if(!(element instanceof EObject)) {
			throw new IllegalArgumentException("Unknown element : " + element);
		}

		return (EObject)element; //GMFElement
	}

	public void handleChange(ChangeEvent event) {
		resetCache();
		try {
			(new RefreshHandler()).execute(null);
		} catch (ExecutionException ex) {
			Activator.log.error(ex);
		}
	}

	@Override
	public Element getElement(Object node) {
		if(node == null) {
			return null;
		}

		EObject notationElement = getNativeWidget(node);
		View canonicalNotationElement = SemanticElementHelper.findPrimaryView(notationElement);

		//A View and a Compartment associated to the same Semantic Element
		//must have the same XML Element. They share the same children.
		//This is required to map the Semantic model (Used by the CSS selectors) 
		//to the Notation model (Used by the CSS properties)
		return super.getElement(canonicalNotationElement);
	}

}
