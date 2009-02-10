/***************************************************************************
 * Copyright (c) 2007 Conselleria de Infraestructuras y Transporte,
 * Generalitat de la Comunitat Valenciana . All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Gabriel Merin Cubero (Prodevelop) – Sequence Diagram implementation
 *
 ******************************************************************************/
package org.eclipse.papyrus.diagram.sequence.edit.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.commands.DuplicateEObjectsCommand;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DuplicateElementsRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.diagram.common.part.DiagramCanvasSwitch;
import org.eclipse.papyrus.diagram.common.util.BasicEcoreSwitch;
import org.eclipse.papyrus.diagram.common.util.DiagramEditPartsUtil;
import org.eclipse.papyrus.diagram.common.util.MultiDiagramUtil;
import org.eclipse.papyrus.diagram.sequence.edit.commands.CommentCreateCommand;
import org.eclipse.papyrus.diagram.sequence.edit.commands.InteractionCreateCommand;
import org.eclipse.papyrus.diagram.sequence.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.util.UMLSwitch;


/**
 * @generated
 */
public class PackageItemSemanticEditPolicy extends UMLBaseItemSemanticEditPolicy {

	/**
	 * @generated NOT
	 * 
	 *            modified to handle an Interaction as canvas element.
	 */
	static class EReferenceDiagramSwitch extends BasicEcoreSwitch<EReference, IElementType> {

		@Override
		public EReference doSwitch(EObject modelElement) {

			final IElementType type = getInfo();
			if (type == null) {
				return null;
			}
			UMLSwitch<EReference> aSwitch = new UMLSwitch<EReference>() {

				@Override
				public EReference casePackage(Package modelElement) {
					EReference reference = null;
					if (type.equals(UMLElementTypes.Interaction_1001)) {
						reference = UMLPackage.eINSTANCE.getPackage_PackagedElement();
					}
					if (type.equals(UMLElementTypes.Comment_2005)) {
						reference = UMLPackage.eINSTANCE.getElement_OwnedComment();
					}

					return reference;
				}

				@Override
				public EReference caseInteraction(Interaction modelElement) {
					EReference reference = null;
					if (type.equals(UMLElementTypes.Comment_2005)) {
						reference = UMLPackage.eINSTANCE.getElement_OwnedComment();
					}
					return reference;
				}

			};

			return aSwitch.doSwitch(modelElement);
		}
	}

	/**
	 * @generated
	 */
	private EReferenceDiagramSwitch aSwitch = new EReferenceDiagramSwitch();

	/**
	 * @generated
	 */
	@Override
	protected Command getCreateCommand(CreateElementRequest req) {
		Diagram diagram = DiagramEditPartsUtil.findDiagramFromEditPart(getHost());
		if (diagram != null) {
			req.getParameters().put(MultiDiagramUtil.BelongToDiagramSource, diagram);
		}
		EObject canvasElement = DiagramCanvasSwitch.getCanvasElement(this);
		if (canvasElement == null) {
			return null;
		}
		aSwitch.setInfo(req.getElementType());
		req.setContainmentFeature(aSwitch.doSwitch(canvasElement));
		if (UMLElementTypes.Interaction_1001 == req.getElementType()) {
			return getGEFWrapper(InteractionCreateCommand.create(req, canvasElement));
		}
		if (UMLElementTypes.Comment_2005 == req.getElementType()) {
			return getGEFWrapper(CommentCreateCommand.create(req, canvasElement));
		}
		return super.getCreateCommand(req);
	}

	/**
	 * @generated
	 */
	@Override
	protected Command getDuplicateCommand(DuplicateElementsRequest req) {
		TransactionalEditingDomain editingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
		Diagram currentDiagram = null;
		if (getHost() instanceof IGraphicalEditPart) {
			currentDiagram = ((IGraphicalEditPart) getHost()).getNotationView().getDiagram();
		}
		return getGEFWrapper(new DuplicateAnythingCommand(editingDomain, req, currentDiagram));
	}

	/**
	 * @generated
	 */
	private static class DuplicateAnythingCommand extends DuplicateEObjectsCommand {

		private Diagram diagram;

		/**
		 * @generated
		 */
		public DuplicateAnythingCommand(TransactionalEditingDomain editingDomain, DuplicateElementsRequest req, Diagram currentDiagram) {
			super(editingDomain, req.getLabel(), req.getElementsToBeDuplicated(), req.getAllDuplicatedElementsMap());
			this.diagram = currentDiagram;
		}

		/**
		 * @generated
		 */
		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
			CommandResult result = super.doExecuteWithResult(progressMonitor, info);
			List objectsToBeShown = new ArrayList();
			for (Object duplicatedObject : this.getAllDuplicatedObjectsMap().keySet()) {
				if (duplicatedObject instanceof EObject) {
					if (MultiDiagramUtil.findEObjectReferencedInEAnnotation(diagram, (EObject) duplicatedObject)) {
						MultiDiagramUtil.AddEAnnotationReferenceToDiagram(diagram, (EObject) this.getAllDuplicatedObjectsMap().get(duplicatedObject));
					}
				}
			}
			return result;
		}

	}

}
