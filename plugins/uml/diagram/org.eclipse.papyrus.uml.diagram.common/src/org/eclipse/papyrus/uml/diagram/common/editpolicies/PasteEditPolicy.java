/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 358625
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ContainerEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.internal.commands.DuplicateViewsCommand;
import org.eclipse.gmf.runtime.diagram.ui.l10n.DiagramUIMessages;
import org.eclipse.gmf.runtime.diagram.ui.requests.DuplicateRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.EditCommandRequestWrapper;
import org.eclipse.gmf.runtime.diagram.ui.requests.PasteViewRequest;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.MapModeUtil;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.requests.PasteRequest;
import org.eclipse.papyrus.uml.diagram.common.commands.PapyrusDuplicateViewsCommand;
import org.eclipse.uml2.uml.Element;

/**
 * This class has in charge to give a paste command, ie to copy graphically
 * element.
 */

@SuppressWarnings({ "rawtypes", "restriction" })
public class PasteEditPolicy extends ContainerEditPolicy {

	public static final String PASTE_ROLE = "PASTE_ROLE"; //$NON-NLS-1$

	/**
	 * @see org.eclipse.gef.EditPolicy#getCommand(Request)
	 */
	public Command getCommand(Request request) {

		if(PasteRequest.REQ_PAPYRUS_PASTE.equals(request.getType())) {

			if(request instanceof PasteRequest) {
				return getPasteCommand((PasteRequest)request);
			} else {

				// Use parent implementation to manage this kind of request
				return getPasteCommand((PasteViewRequest)request);
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	protected Command getPasteCommand(PasteRequest request) {
		List notationView = new ArrayList();
		if(request.getElementToPaste() != null && request.getElementToPaste().size() > 0) {
			notationView.addAll(request.getElementToPaste());
			HashSet semanticElement = new HashSet();
			return constructDuplicationCommand(notationView, semanticElement, request.getDuplicate(), ((IGraphicalEditPart)getHost()).getEditingDomain());
		}
		return null;
	}


	/**
	 * Code comes from superclass {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	protected Command constructDuplicationCommand(List notationViewsToDuplicate, Set elementsToDuplicate, DuplicateRequest request, TransactionalEditingDomain editingDomain) {
		/*
		 * We must append all inner edges of a node being duplicated. Edges are
		 * non-containment references, hence they won't be duplicated for free.
		 * Therefore, we add them here to the list views to duplicate. We don't
		 * add semantic elements of the edges to the list of semantic elements
		 * to duplicate since we assume that their semantic elements are owned
		 * by source or target or their semantic containers.
		 */
		/**
		 * Until duplicate views action enablement is driven by the created
		 * duplicate views command, we can't look for edges to duplicate. It's a
		 * performance hit.
		 */
		// List<Edge> allInnerEdges = new LinkedList<Edge>();
		// for (Iterator itr = notationViewsToDuplicate.iterator();
		// itr.hasNext();) {
		// allInnerEdges.addAll(ViewUtil.getAllInnerEdges((View) itr.next()));
		// }
		// notationViewsToDuplicate.addAll(allInnerEdges);

		if(!notationViewsToDuplicate.isEmpty()) {
			if(!elementsToDuplicate.isEmpty()) {
				ArrayList<EObject> stereotypedSelection = new ArrayList<EObject>();
				// copy stereotype contained into
				Iterator<EObject> iter = elementsToDuplicate.iterator();
				while(iter.hasNext()) {
					EObject subeObject = (EObject)iter.next();
					if(subeObject instanceof Element) {
						stereotypedSelection.addAll(((Element)subeObject).getStereotypeApplications());
					}

				}
				ArrayList<EObject> resultToCopy = new ArrayList(elementsToDuplicate);
				resultToCopy.addAll(stereotypedSelection);
				org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest duplicateElementsRequest = new DuplicateElementsRequest(editingDomain, new ArrayList(resultToCopy));
				Command duplicateElementsCommand = getHost().getEditPolicy(EditPolicyRoles.SEMANTIC_ROLE).getCommand(new EditCommandRequestWrapper(duplicateElementsRequest, request.getExtendedData()));
				if(duplicateElementsCommand != null && duplicateElementsCommand.canExecute()) {
					CompositeCommand cc = new CompositeCommand(DiagramUIMessages.Commands_Duplicate_Label);
					cc.compose(new CommandProxy(duplicateElementsCommand));

					cc.compose(new DuplicateViewsCommand(editingDomain, DiagramUIMessages.Commands_Duplicate_Label, request, notationViewsToDuplicate, duplicateElementsRequest.getAllDuplicatedElementsMap(), getDuplicateViewsOffset(request)));
					return new ICommandProxy(cc);
				}
			} else {
				return new ICommandProxy(new PapyrusDuplicateViewsCommand(editingDomain, DiagramUIMessages.Commands_Duplicate_Label, request, notationViewsToDuplicate, new HashMap(), getDuplicateViewsOffset(request), (View)getHost().getModel()));
			}
		}
		return null;
	}

	/**
	 * code comes from super class
	 */
	protected Point getDuplicateViewsOffset(DuplicateRequest request) {
		if(request.getOffset() != null) {
			return request.getOffset();
		}
		int offset = MapModeUtil.getMapMode(((org.eclipse.gef.GraphicalEditPart)getHost()).getFigure()).DPtoLP(10);
		return new Point(offset, offset);
	}
}
