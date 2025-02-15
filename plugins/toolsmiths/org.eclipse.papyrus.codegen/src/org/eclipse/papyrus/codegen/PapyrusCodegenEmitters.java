/****************************************************************************
 * Copyright (c) 2008, 2017 Atos Origin, CEA, ALL4TEC.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		Thibault Landre (Atos Origin) - Initial API and implementation
 *		Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Bug 510587: the palette must not be generated by using GMF gen
 *
 *****************************************************************************/
package org.eclipse.papyrus.codegen;

import org.eclipse.gmf.codegen.xtend.ui.handlers.CodegenEmittersWithXtend2;
import org.eclipse.gmf.common.UnexpectedBehaviourException;
import org.eclipse.gmf.internal.common.codegen.JavaClassEmitter;
import org.eclipse.gmf.internal.common.codegen.TextEmitter;

@SuppressWarnings("restriction")
public class PapyrusCodegenEmitters extends CodegenEmittersWithXtend2 {

	public PapyrusCodegenEmitters(boolean useBaseTemplatesOnly, String templateDirectory, boolean includeDynamicModelTemplates) {
		super(useBaseTemplatesOnly, templateDirectory, includeDynamicModelTemplates);
	}

	@Override
	public JavaClassEmitter getDiagramCanonicalEditPolicyEmitter() {
		return null;
	}

	@Override
	public JavaClassEmitter getUpdateCommandEmitter() {
		return null;
	}

	@Override
	public JavaClassEmitter getModelingAssistantProviderEmitter() throws UnexpectedBehaviourException {
		return null;
	}

	@Override
	public JavaClassEmitter getChildContainerCanonicalEditPolicyEmitter() {
		return null;
	}


	@Override
	public JavaClassEmitter getNodeEditPartModelingAssistantProviderEmitter() {
		return null;
	}

	@Override
	public JavaClassEmitter getDiagramItemSemanticEditPolicyEmitter() {
		return null;
	}

	@Override
	public JavaClassEmitter getDeleteElementActionEmitter() {
		return null;
	}

	@Override
	public JavaClassEmitter getCreateNodeCommandEmitter() throws UnexpectedBehaviourException {
		return null;
	}

	@Override
	public JavaClassEmitter getReorientLinkCommandEmitter() throws UnexpectedBehaviourException {
		return null;
	}

	@Override
	public JavaClassEmitter getReorientRefLinkCommandEmitter() throws UnexpectedBehaviourException {
		return null;
	}

	@Override
	public JavaClassEmitter getCreateLinkCommandEmitter() throws UnexpectedBehaviourException {
		return null;
	}

	@Override
	public JavaClassEmitter getCompartmentItemSemanticEditPolicyEmitter() {
		return null;
	}

	@Override
	public JavaClassEmitter getNodeItemSemanticEditPolicyEmitter() {
		return null;
	}

	@Override
	public JavaClassEmitter getLinkItemSemanticEditPolicyEmitter() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * Overridden to disable code generation of old palette framework.
	 * 
	 * @see org.eclipse.gmf.codegen.util.CodegenEmitters#getPaletteEmitter()
	 */
	@Override
	public JavaClassEmitter getPaletteEmitter() throws UnexpectedBehaviourException {
		return null;
	}

	/**
	 * Get the {@link TextEmitter} to generate palette configuration model for papyrus diagrams.
	 */
	public TextEmitter getPaletteConfigurationEmitter() {
		return createJavaClassEmitter("aspects::xpt::editor::palette::PaletteConfiguration", "PaletteConfiguration"); //$NON-NLS-1$ //$NON-NLS-2$
	}
}
