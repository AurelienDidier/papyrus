/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.timing.custom.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.diagram.core.commands.DeleteCommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.diagram.common.util.CrossReferencerUtil;
import org.eclipse.papyrus.uml.diagram.timing.custom.Messages;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DestructionOccurrenceSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullStateInvariantVerticalLineEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.InteractionEditPartTN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageOccurrenceSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.OccurrenceSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimingDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.timing.part.UMLVisualIDRegistry;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.StateInvariant;

/** Utility class for manipulating {@link OccurrenceSpecification}s */
public final class OccurrenceSpecificationUtils {

	/** The key for the EAnnotation on an OccurrenceSpecification that indicates that its name is autogenerated */
	private static final String AUTOGENERATED_OCCURRENCE_SPECIFICATION_NAME = "org.eclipse.papyrus.uml.diagram.timing.autogeneratedOccurrenceSpecificationName"; //$NON-NLS-1$

	private OccurrenceSpecificationUtils() {
		// utility class
	}

	public static boolean isOccurrenceSpecificationEditPart(final String visualID) {
		return OccurrenceSpecificationEditPartCN.VISUAL_ID.equals(visualID) || MessageOccurrenceSpecificationEditPartCN.VISUAL_ID.equals(visualID) || DestructionOccurrenceSpecificationEditPartCN.VISUAL_ID.equals(visualID);
	}

	public static boolean isOccurrenceSpecificationEditPart(final EditPart editPart) {
		return editPart instanceof OccurrenceSpecificationEditPartCN || editPart instanceof MessageOccurrenceSpecificationEditPartCN || editPart instanceof DestructionOccurrenceSpecificationEditPartCN;
	}

	public static boolean isOccurrenceSpecificationView(final View view) {
		final String visualID = UMLVisualIDRegistry.getVisualID(view);
		return isOccurrenceSpecificationEditPart(visualID);
	}

	private static EAnnotation getAutogeneratedAnnotation(final OccurrenceSpecification occurrenceSpecification) {
		return occurrenceSpecification.getEAnnotation(AUTOGENERATED_OCCURRENCE_SPECIFICATION_NAME);
	}

	/**
	 * Marks with an EAnnotation whether the given OccurrenceSpecification has an auto-generated name.
	 *
	 * @param occurrenceSpecification
	 *            the OccurrenceSpecification for which to specify whether it has an auto-generated name
	 * @param autogenerated
	 *            whether the name is auto-generated
	 */
	public static void setAutogeneratedName(final OccurrenceSpecification occurrenceSpecification, final boolean autogenerated) {
		EAnnotation eAnnotation = getAutogeneratedAnnotation(occurrenceSpecification);
		if (autogenerated && eAnnotation == null) {
			eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			eAnnotation.setSource(AUTOGENERATED_OCCURRENCE_SPECIFICATION_NAME);
			occurrenceSpecification.getEAnnotations().add(eAnnotation);
		} else if (!autogenerated && eAnnotation != null) {
			occurrenceSpecification.getEAnnotations().remove(eAnnotation);
		}
	}

	/**
	 * Returns whether the given OccurrenceSpecification has an auto-generated name.
	 *
	 * @param occurrenceSpecification
	 *            the OccurrenceSpecification for which to determine whether it has an auto-generated name
	 * @return whether the name of the given OccurrenceSpecification is auto-generated
	 */
	public static boolean isAutogeneratedName(final OccurrenceSpecification occurrenceSpecification) {
		final EAnnotation eAnnotation = getAutogeneratedAnnotation(occurrenceSpecification);
		return eAnnotation != null;
	}

	/**
	 * Get all elements to delete when deleting an OccurrenceSpecification.
	 *
	 * @param occurrenceSpecification
	 *            the OccurrenceSpecification being deleted
	 * @return the elements (EObjects and Views) that should be deleted together with the OccurrenceSpecification
	 */
	public static Collection<EObject> getElementsToDelete(final OccurrenceSpecification occurrenceSpecification) {
		final Set<EObject> elementsToDestroy = new HashSet<EObject>();
		final Set<View> crossReferencingViews = CrossReferencerUtil.getCrossReferencingViews(occurrenceSpecification, TimingDiagramEditPart.MODEL_ID);
		for (final View view : crossReferencingViews) {
			elementsToDestroy.addAll(getElementsToRemove(view, false));
		}
		return elementsToDestroy;
	}

	/**
	 * Get all Views to remove when hiding an OccurrenceSpecification (i.e., deleting an OccurrenceSpecification's
	 * View).
	 *
	 * @param occurrenceSpecification
	 *            the OccurrenceSpecification being hidden
	 * @return the Views that should be hidden together with the given OccurrenceSpecification View
	 */
	public static Collection<View> getViewsToHide(final View occurrenceSpecificationView) {
		final Set<View> viewsToHide = new HashSet<View>();
		final Collection<EObject> elementsToRemove = getElementsToRemove(occurrenceSpecificationView, true);
		for (final EObject eObject : elementsToRemove) {
			if (eObject instanceof View) {
				viewsToHide.add((View) eObject);
			} else {
				throw new IllegalStateException("Only views should be returned"); //$NON-NLS-1$
			}
		}
		return viewsToHide;
	}

	/**
	 * Get all elements to remove when removing an OccurrenceSpecification.
	 *
	 * @param occurrenceSpecificationView
	 *            the OccurrenceSpecification View being removed
	 * @param hideOnly
	 *            if <code>true</code>, only return the Views, not the semantic elements
	 * @return the elements (Views and EObjects if <code>hideOnly</code> is <code>false</code>) that should be removed
	 *         together with the OccurrenceSpecification
	 */
	public static Collection<EObject> getElementsToRemove(final View occurrenceSpecificationView, final boolean hideOnly) {
		final Set<EObject> elementsToRemove = new HashSet<EObject>();
		if (OccurrenceSpecificationUtils.isOccurrenceSpecificationView(occurrenceSpecificationView)) {
			// remove associated ticks
			final List<Node> associatedTickViews = TickUtils.getAssociatedTickViews(occurrenceSpecificationView);
			elementsToRemove.addAll(associatedTickViews);

			// only remove Views under the same Interaction
			final View interactionView = ViewUtils.findSuperViewWithId(occurrenceSpecificationView, InteractionEditPartTN.VISUAL_ID);
			final EObject eContainer = occurrenceSpecificationView.eContainer();
			if (eContainer instanceof Node) {
				final Node node = (Node) eContainer;
				@SuppressWarnings("unchecked")
				final EList<View> children = node.getChildren();

				final int childrenSize = children.size();
				final int index = children.indexOf(occurrenceSpecificationView);

				boolean done = false;
				// the Lifeline starts by an OccurrenceSpecification (created with a Create Message)
				if (index == 0) {
					if (index + 1 < childrenSize) {
						final View following = children.get(index + 1);
						if (following.getType().equals(Constants.verticalLineId)) {
							elementsToRemove.add(following);
						}
					}
					// let the Lifeline start from a StateInvariant again
					done = true;
				}

				// state change in a full lifeline
				if (!done && index + 2 < childrenSize) {
					final View following1 = children.get(index + 1);
					final View following2 = children.get(index + 2);
					if (following1.getType().equals(Constants.verticalLineId) && following2.getType().equals(Constants.fullStateInvariantId)) {
						elementsToRemove.add(following1);
						elementsToRemove.add(following2);
						if (!hideOnly) {
							elementsToRemove.add(following2.getElement());
						}
						elementsToRemove.addAll(StateInvariantUtils.getRelatedElementsToRemove(following2.getElement(), hideOnly, interactionView));
						done = true;
					}
				}

				// state change in a compact lifeline, or non-state-change OccurrenceSpecification in any Lifeline
				if (!done && index + 1 < childrenSize) {
					final View following = children.get(index + 1);
					if (following.getType().equals(Constants.fullStateInvariantId) || following.getType().equals(Constants.compactStateInvariantId)) {
						elementsToRemove.add(following);
						if (!hideOnly) {
							elementsToRemove.add(following.getElement());
						}
						elementsToRemove.addAll(StateInvariantUtils.getRelatedElementsToRemove(following.getElement(), hideOnly, interactionView));
					}
				}
			}

			final OccurrenceSpecification occurrenceSpecification = (OccurrenceSpecification) occurrenceSpecificationView.getElement();
			elementsToRemove.addAll(getRelatedElementsToRemove(occurrenceSpecification, hideOnly, interactionView));

		}
		return elementsToRemove;
	}

	public static Collection<? extends EObject> getRelatedElementsToRemove(final EObject occurrenceSpecification, final boolean hideOnly, final View interactionView) {
		Assert.isLegal(occurrenceSpecification instanceof OccurrenceSpecification);
		final Set<EObject> elementsToRemove = new HashSet<EObject>();
		elementsToRemove.addAll(TimeElementUtils.getTimeElementsToRemove(occurrenceSpecification, hideOnly, interactionView));
		elementsToRemove.addAll(MessageUtils.getReferencingMessagesToRemove(occurrenceSpecification, hideOnly, interactionView));
		elementsToRemove.addAll(GeneralOrderingUtils.getReferencingGeneralOrderingsToRemove(occurrenceSpecification, hideOnly, interactionView));
		return elementsToRemove;
	}

	/**
	 * Delete all {@link StateInvariant}s and {@link OccurrenceSpecification}s after the given
	 * DestructionOccurrenceSpecification (which signifies the end of a Lifeline). Doesn't delete time elements.
	 *
	 * @param occurrenceSpecification
	 *            the OccurrenceSpecification after which everything must be deleted
	 * @param occurrenceSpecificationView
	 *            the OccurrenceSpecification View after which other views must be deleted (if <code>null</code>, then
	 *            the views will be searched)
	 */
	private static void deleteEverythingAfterOrBefore(final OccurrenceSpecification occurrenceSpecification, final View occurrenceSpecificationView, final boolean before) {
		// remove fragments from UML model
		final EList<Lifeline> coveredLifelines = occurrenceSpecification.getCovereds();
		for (final Lifeline lifeline : coveredLifelines) {
			final EList<InteractionFragment> coveredBys = lifeline.getCoveredBys();
			int index = coveredBys.indexOf(occurrenceSpecification);
			if (index != -1) {
				// if we are removing everything before an OccurrenceSpecification in the middle of a StateInvariant,
				// then we need to move the StateInvariant after the OccurrenceSpecification
				if (before && index + 1 < coveredBys.size() && !(coveredBys.get(index + 1) instanceof StateInvariant)) {
					if (movePreviousStateInvariantAfter(coveredBys, occurrenceSpecification)) {
						// the OccurrenceSpecification moved since we removed an element before it
						index--;
					}
				}
				final List<InteractionFragment> fragmentsToRemove = new ArrayList<InteractionFragment>();
				for (int i = before ? index - 1 : index + 1; before ? i >= 0 : i < coveredBys.size(); i = (before ? i - 1 : i + 1)) {
					fragmentsToRemove.add(coveredBys.get(i));
				}
				for (final InteractionFragment fragmentToRemove : fragmentsToRemove) {
					// remove the related elements
					Collection<? extends EObject> relatedElementsToRemove = Collections.emptyList();
					if (fragmentToRemove instanceof OccurrenceSpecification) {
						final OccurrenceSpecification occurrence = (OccurrenceSpecification) fragmentToRemove;
						relatedElementsToRemove = OccurrenceSpecificationUtils.getRelatedElementsToRemove(occurrence, false, null);
					} else if (fragmentToRemove instanceof StateInvariant) {
						final StateInvariant stateInvariant = (StateInvariant) fragmentToRemove;
						relatedElementsToRemove = StateInvariantUtils.getRelatedElementsToRemove(stateInvariant, false, null);
					}
					for (final EObject eObject : relatedElementsToRemove) {
						DestroyElementCommand.destroy(eObject);
					}
					// remove the fragment
					DestroyElementCommand.destroy(fragmentToRemove);
				}
			}
		}
		// remove Views from notation model
		final Collection<View> views;
		if (occurrenceSpecificationView == null) {
			views = CrossReferencerUtil.getCrossReferencingViews(occurrenceSpecification, TimingDiagramEditPart.MODEL_ID);
		} else {
			views = Collections.singletonList(occurrenceSpecificationView);
		}
		for (final View view : views) {
			final View parentView = (View) view.eContainer();
			@SuppressWarnings("unchecked")
			final EList<View> children = parentView.getChildren();
			final int index = children.indexOf(view);
			if (index != -1) {
				final List<View> viewsToRemove = new ArrayList<View>();
				for (int i = before ? index - 1 : index + 1; before ? i >= 0 : i < children.size(); i = (before ? i - 1 : i + 1)) {
					final View childView = children.get(i);
					final String visualID = UMLVisualIDRegistry.getVisualID(childView);
					if (isOccurrenceSpecificationEditPart(visualID) || StateInvariantUtils.isStateInvariantEditPart(visualID) || FullStateInvariantVerticalLineEditPart.VISUAL_ID.equals(visualID)) {
						viewsToRemove.add(childView);
					}
				}
				// remove the vertical line (a state change is not possible anymore
				// since we deleted the preceding state invariant)
				if (before && index + 1 < children.size()) {
					final View childView = children.get(index + 1);
					final String visualID = UMLVisualIDRegistry.getVisualID(childView);
					if (FullStateInvariantVerticalLineEditPart.VISUAL_ID.equals(visualID)) {
						viewsToRemove.add(childView);
					}
				}
				for (final View viewToRemove : viewsToRemove) {
					ViewUtil.destroy(viewToRemove);
				}
			}
		}
	}

	private static boolean movePreviousStateInvariantAfter(final EList<InteractionFragment> fragments, final OccurrenceSpecification occurrenceSpecification) {
		final int umlIndex = fragments.indexOf(occurrenceSpecification);
		// move the StateInvariant in the UML model
		final int previousStateInvariantIndex = findPreviousStateInvariantIndex(umlIndex, fragments);
		if (previousStateInvariantIndex == -1) {
			Activator.log.error(new IllegalStateException("No StateInvariant after or before the OccurrenceSpecification")); //$NON-NLS-1$
			return false;
		}
		final StateInvariant stateInvariant = (StateInvariant) fragments.get(previousStateInvariantIndex);
		fragments.move(umlIndex, previousStateInvariantIndex);

		// move the StateInvariant View in the notation model
		final Collection<View> stateInvariantViews = CrossReferencerUtil.getCrossReferencingViews(stateInvariant, TimingDiagramEditPart.MODEL_ID);
		for (final View stateInvariantView : stateInvariantViews) {
			final View parentView = (View) stateInvariantView.eContainer();
			@SuppressWarnings("unchecked")
			final EList<View> children = parentView.getPersistedChildren();
			// find the OccurrenceSpecification View index
			int viewIndex = -1;
			for (int i = 0; i < children.size(); i++) {
				final View view = children.get(i);
				if (view.getElement() == occurrenceSpecification) {
					viewIndex = i;
					break;
				}
			}
			children.move(viewIndex, stateInvariantView);
		}
		return true;
	}

	private static int findPreviousStateInvariantIndex(final int index, final EList<InteractionFragment> fragments) {
		for (int i = index - 1; i >= 0; i--) {
			final InteractionFragment fragment = fragments.get(i);
			if (fragment instanceof StateInvariant) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Delete all {@link StateInvariant}s and {@link OccurrenceSpecification}s after the given
	 * DestructionOccurrenceSpecification (which signifies the end of a Lifeline). Doesn't delete time elements.
	 *
	 * @param occurrenceSpecification
	 *            the OccurrenceSpecification after which everything must be deleted
	 * @param occurrenceSpecificationView
	 *            the OccurrenceSpecification View after which other views must be deleted (if <code>null</code>, then
	 *            the views will be searched)
	 */
	public static void deleteEverythingAfter(final OccurrenceSpecification occurrenceSpecification, final View occurrenceSpecificationView) {
		deleteEverythingAfterOrBefore(occurrenceSpecification, occurrenceSpecificationView, false);
	}

	/**
	 * Delete all {@link StateInvariant}s and {@link OccurrenceSpecification}s before the given {@link OccurrenceSpecification} (which signifies the
	 * creation of a Lifeline). Doesn't delete time elements.
	 *
	 * @param occurrenceSpecification
	 *            the OccurrenceSpecification before which everything must be deleted
	 * @param occurrenceSpecificationView
	 *            the OccurrenceSpecification View before which other views must be deleted (if <code>null</code>, then
	 *            the views will be searched)
	 */
	public static void deleteEverythingBefore(final OccurrenceSpecification occurrenceSpecification, final View occurrenceSpecificationView) {
		deleteEverythingAfterOrBefore(occurrenceSpecification, occurrenceSpecificationView, true);
	}

	/**
	 * Find at which index to insert an OccurrenceSpecification in a compartment, based on a location.
	 *
	 * @param pt
	 *            the location
	 * @param compartmentView
	 *            the compartment view in which the OccurrenceSpecification will be inserted
	 * @param viewer
	 *            the viewer in which the EditParts corresponding to the Views are registered
	 * @return the index in the compartment where the OccurrenceSpecification should be inserted
	 */
	public static int findInsertionIndexFor(final Point pt, final View compartmentView, final EditPartViewer viewer) {
		@SuppressWarnings("unchecked")
		final EList<View> children = compartmentView.getChildren();
		int index = 0;
		for (final View view : children) {
			if (view instanceof Node) {
				final Node node = (Node) view;
				final Object editPart = viewer.getEditPartRegistry().get(node);
				if (editPart instanceof GraphicalEditPart) {
					final GraphicalEditPart graphicalEditPart = (GraphicalEditPart) editPart;
					final Rectangle bounds = graphicalEditPart.getFigure().getBounds();
					if (pt.x < bounds.x()) {
						break;
					}
				} else {
					Activator.log.error(new Exception("No EditPart found for the Node")); //$NON-NLS-1$
				}
			}
			index++;
		}
		return index;
	}

	/**
	 * Wrap the given <code>baseCommand</code> (that hides an OccurrenceSpecification View) into a {@link CompoundCommand}, and add a deletion of
	 * other Views associated to the OccurrenceSpecification.
	 */
	public static CompoundCommand getHideOccurrenceSpecificationCommand(final EditPart occurrenceSpecificationEditPart, final Command baseCommand) {
		final View view = (View) occurrenceSpecificationEditPart.getModel();
		if (!OccurrenceSpecificationUtils.isOccurrenceSpecificationEditPart(UMLVisualIDRegistry.getVisualID(view))) {
			return null;
		}
		final Collection<View> viewsToHide = OccurrenceSpecificationUtils.getViewsToHide(view);
		final CompoundCommand compoundCommand = new CompoundCommand(Messages.OccurrenceSpecificationUtils_HideOccurrenceSpecification);
		if (baseCommand != null) {
			compoundCommand.add(baseCommand);
		}
		for (final View viewToHide : viewsToHide) {
			compoundCommand.add(new ICommandProxy(new DeleteCommand(viewToHide)));
		}
		if (compoundCommand.isEmpty()) {
			return null;
		}
		return compoundCommand;
	}

}
