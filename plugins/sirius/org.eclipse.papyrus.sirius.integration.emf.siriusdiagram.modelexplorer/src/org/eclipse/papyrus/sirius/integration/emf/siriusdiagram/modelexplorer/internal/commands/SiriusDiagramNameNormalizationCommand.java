/*****************************************************************************
 * Copyright (c) 2019 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Yupanqui Munoz (CEA LIST) yupanqui.munozjulho@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.modelexplorer.internal.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.ui.menu.NameNormalizationCommand;
import org.eclipse.sirius.diagram.ui.tools.api.editor.DDiagramEditor;

/**
 * This class provides document template name normalization command.
 *
 */
public class SiriusDiagramNameNormalizationCommand extends NameNormalizationCommand {

	/**
	 * Constructor.
	 *
	 * @param domain
	 *            the editing domain
	 * @param SiriusDiagram
	 *            the edited document template
	 * @param normalization
	 *            the parameter defining the kind of normalization
	 */
	public SiriusDiagramNameNormalizationCommand(final TransactionalEditingDomain domain, final EObject SiriusDiagram, final String normalization) {
		super(domain, SiriusDiagram, normalization);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doExecute() {
		if (this.source instanceof DDiagramEditor) {
			final DDiagramEditor doc = (DDiagramEditor) this.source;
			// TODO final String newName = normalizeName(doc.getName(), parameter);
			// doc.setName(newName);
			final String newName = normalizeName(doc.getRepresentation().getName(), parameter);
			// TODO ((DRepresentation) doc.getRepresentation()).setName(newName);
		}
	}

}
