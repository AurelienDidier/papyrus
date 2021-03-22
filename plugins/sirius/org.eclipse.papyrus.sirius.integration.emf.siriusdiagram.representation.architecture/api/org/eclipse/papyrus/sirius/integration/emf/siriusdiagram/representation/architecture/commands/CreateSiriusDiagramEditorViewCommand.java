/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.representation.architecture.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.representation.SiriusDiagramPrototype;
import org.eclipse.sirius.diagram.DDiagram;

/**
 * Create a DDiagram Editor view
 */
public class CreateSiriusDiagramEditorViewCommand extends AbstractCreatePapyrusEditorViewCommand<DDiagram> {

	/**
	 * the {@link DDiagram} used to create the {@link SiriusDiagram} model and its editor view
	 */
	private final SiriusDiagramPrototype diagram;

	/**
	 * the main title of the created {@link SiriusDiagram}
	 */
	private final String mainTitle;

	/**
	 *
	 * Constructor.
	 *
	 * @param domain
	 * @param docProto
	 * @param documentName
	 * @param documentMainTitle
	 * @param semanticContext
	 * @param graphicalContext
	 * @param openAfterCreation
	 */
	public CreateSiriusDiagramEditorViewCommand(final TransactionalEditingDomain domain, final SiriusDiagramPrototype docProto, final String documentName, final String documentMainTitle, final EObject semanticContext,
			final EObject graphicalContext, final boolean openAfterCreation) {
		super(domain, "Create new SiriusDiagram", documentName, semanticContext, graphicalContext, openAfterCreation); //$NON-NLS-1$
		this.diagram = docProto;
		this.mainTitle = documentMainTitle;
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param domain
	 * @param docProto
	 * @param documentName
	 * @param documentMainTitle
	 * @param semanticContext
	 * @param openAfterCreation
	 */
	public CreateSiriusDiagramEditorViewCommand(final TransactionalEditingDomain domain, final SiriusDiagramPrototype docProto, final String documentName, final String documentMainTitle, final EObject semanticContext,
			final boolean openAfterCreation) {
		this(domain, docProto, documentName, documentMainTitle, semanticContext, null, openAfterCreation);
	}

	/**
	 *
	 * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
	 *
	 */
	@Override
	protected void doExecute() {
		final SiriusDiagramPrototype template = this.diagram;

		final SiriusDiagramPrototype newInstance = EcoreUtil.copy(template);

		if (newInstance instanceof DDiagram) {
			// TODO newInstance.setMainTitle(this.mainTitle);
		}


		attachToResource(semanticContext, newInstance);

		// final IDocumentStructureGeneratorConfiguration generator = newInstance.getDocumentStructureGeneratorConfiguration();
		// if (null != generator) {
		// generator.setDocumentName(this.editorViewName);
		// }

		// newInstance.setDDiagram(this.prototype);
		// newInstance.setGraphicalContext(this.graphicalContext);
		// newInstance.setSemanticContext(this.semanticContext);
		// newInstance.setName(this.editorViewName);
		if (this.openAfterCreation) {
			openEditor(newInstance);
		}
		if (newInstance.eResource() != null) {
			// we suppose all is ok
			this.createdEditorView = newInstance;
		}

	}

}
