/*****************************************************************************
 * Copyright (c) 2010, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Saadia Dhouib saadia.dhouib@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 462958
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.communication.custom.commands;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.adapter.SemanticAdapter;
import org.eclipse.papyrus.uml.diagram.communication.edit.parts.MessageNameEditPart;
import org.eclipse.papyrus.uml.diagram.communication.part.UMLVisualIDRegistry;

/**
 * The Class CustomMessageViewCreateCommand is intended to create the graphical view of the message when the target and source lifelines are already
 * connected. In this case, only a label is created
 */
public class CustomMessageViewCreateCommand extends AbstractTransactionalCommand {

	/** The node. */
	private static View node;

	/** The container view. */
	// private View containerView;
	/** The location. */
	@SuppressWarnings("unused")
	private Point location;

	/** The preference hint. */
	@SuppressWarnings("unused")
	private PreferencesHint preferenceHint;

	/** The result. */
	public EObjectAdapter result;

	/** The semantic apdater. */
	private IAdaptable semanticApdater;

	/** The semantic view apdater. */
	private SemanticAdapter semanticViewApdater;

	/** The viewer. */
	private EditPartViewer viewer;

	/** The existing link. */
	private View existingLink;

	/**
	 * Instantiates a new custom message view create command.
	 *
	 * @param domain
	 *            the domain
	 * @param container
	 *            the container
	 * @param viewer
	 *            the viewer
	 * @param preferencesHint
	 *            the preferences hint
	 * @param point
	 *            the point
	 * @param semanticAdapter
	 *            the semantic adapter
	 * @param link
	 *            the link
	 */
	public CustomMessageViewCreateCommand(TransactionalEditingDomain domain, EditPartViewer viewer, PreferencesHint preferencesHint, Point point, IAdaptable semanticAdapter, View link) {
		super(domain, "MessageClassViewCreateCommand", null); //$NON-NLS-1$
		// this.containerView = container;
		this.viewer = viewer;
		this.preferenceHint = preferencesHint;
		this.location = point;
		this.existingLink = link;
		this.semanticApdater = semanticAdapter;
		this.semanticViewApdater = new SemanticAdapter(null, null);
		setResult(CommandResult.newOKCommandResult(semanticViewApdater));
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param monitor
	 * @param info
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		// Connector linkConnector = linkView.get
		CustomMessageViewCreateCommand.node = customCreateLabel(((EObject) semanticApdater.getAdapter(EObject.class)), existingLink, UMLVisualIDRegistry.getType(MessageNameEditPart.VISUAL_ID));
		// put to the good position
		Location notationLocation = NotationFactory.eINSTANCE.createLocation();
		// notationLocation.setX(location.x);
		// notationLocation.setY(location.y);
		notationLocation.setX(1);
		notationLocation.setY(-23);
		((Node) CustomMessageViewCreateCommand.node).setLayoutConstraint(notationLocation);
		semanticViewApdater.setView(CustomMessageViewCreateCommand.node);
		return CommandResult.newOKCommandResult(semanticViewApdater);
	}

	/**
	 * Gets the affected files.
	 *
	 * @return the affected files {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List getAffectedFiles() {
		if (viewer != null) {
			EditPart editpart = viewer.getRootEditPart().getContents();
			if (editpart instanceof IGraphicalEditPart) {
				View view = (View) ((IGraphicalEditPart) editpart).getModel();
				if (view != null) {
					IFile f = WorkspaceSynchronizer.getFile(view.eResource());
					return f != null ? Collections.singletonList(f) : Collections.EMPTY_LIST;
				}
			}
		}
		return super.getAffectedFiles();
	}

	/**
	 * used to obtain the created node.
	 *
	 * @return the created node
	 */
	public View getNode() {
		return node;
	}

	/**
	 * Custom create label.
	 * this custom create label enables to associate a semantic element with a label
	 *
	 * @param domainElement
	 *            the domain element
	 * @param owner
	 *            the owner
	 * @param hint
	 *            the hint
	 * @return the node
	 *
	 */
	public Node customCreateLabel(EObject domainElement, View owner, String hint) {
		DecorationNode rv = NotationFactory.eINSTANCE.createDecorationNode();
		rv.setType(hint);
		ViewUtil.insertChildView(owner, rv, ViewUtil.APPEND, true);
		rv.setElement(domainElement);
		return rv;
	}
}
