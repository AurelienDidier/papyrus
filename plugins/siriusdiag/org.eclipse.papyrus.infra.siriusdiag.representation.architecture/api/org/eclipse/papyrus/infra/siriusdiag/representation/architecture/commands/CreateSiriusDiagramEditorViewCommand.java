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

package org.eclipse.papyrus.infra.siriusdiag.representation.architecture.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.model2doc.core.generatorconfiguration.IDocumentStructureGeneratorConfiguration;
import org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.DocumentTemplate;
import org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.DocumentTemplatePrototype;
import org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.TextDocumentTemplate;

/**
 * Create a DocumentTemplate Editor view
 */
public class CreateSiriusDiagramEditorViewCommand extends AbstractCreatePapyrusEditorViewCommand<DocumentTemplate> {

	/**
	 * the {@link DocumentTemplatePrototype} used to create the {@link DocumentTemplate} model and its editor view
	 */
	private final DocumentTemplatePrototype prototype;

	/**
	 * the main title of the created {@link DocumentTemplate}
	 */
	private final String mainTitle;

	/**
	 *
	 * Constructor.
	 *
	 * @param domain
	 * @param documentTemplatePrototype
	 * @param documentName
	 * @param documentMainTitle
	 * @param semanticContext
	 * @param graphicalContext
	 * @param openAfterCreation
	 */
	public CreateSiriusDiagramEditorViewCommand(final TransactionalEditingDomain domain, final DocumentTemplatePrototype documentTemplatePrototype, final String documentName, final String documentMainTitle, final EObject semanticContext,
			final EObject graphicalContext, final boolean openAfterCreation) {
		super(domain, "Create new DocumentTemplate", documentName, semanticContext, graphicalContext, openAfterCreation); //$NON-NLS-1$
		this.prototype = documentTemplatePrototype;
		this.mainTitle = documentMainTitle;
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param domain
	 * @param documentTemplatePrototype
	 * @param documentName
	 * @param documentMainTitle
	 * @param semanticContext
	 * @param openAfterCreation
	 */
	public CreateSiriusDiagramEditorViewCommand(final TransactionalEditingDomain domain, final DocumentTemplatePrototype documentTemplatePrototype, final String documentName, final String documentMainTitle, final EObject semanticContext,
			final boolean openAfterCreation) {
		this(domain, documentTemplatePrototype, documentName, documentMainTitle, semanticContext, null, openAfterCreation);
	}

	/**
	 *
	 * @see org.eclipse.emf.transaction.RecordingCommand#doExecute()
	 *
	 */
	@Override
	protected void doExecute() {
		final DocumentTemplate template = this.prototype.getDocumentTemplate();

		final DocumentTemplate newInstance = EcoreUtil.copy(template);

		if (newInstance instanceof TextDocumentTemplate) {
			((TextDocumentTemplate) newInstance).setMainTitle(this.mainTitle);
		}


		attachToResource(semanticContext, newInstance);

		final IDocumentStructureGeneratorConfiguration generator = newInstance.getDocumentStructureGeneratorConfiguration();
		if (null != generator) {
			generator.setDocumentName(this.editorViewName);
		}

		newInstance.setDocumentTemplatePrototype(this.prototype);
		newInstance.setGraphicalContext(this.graphicalContext);
		newInstance.setSemanticContext(this.semanticContext);
		newInstance.setName(this.editorViewName);
		if (this.openAfterCreation) {
			openEditor(newInstance);
		}
		if (newInstance.eResource() != null) {
			// we suppose all is ok
			this.createdEditorView = newInstance;
		}

	}

}
