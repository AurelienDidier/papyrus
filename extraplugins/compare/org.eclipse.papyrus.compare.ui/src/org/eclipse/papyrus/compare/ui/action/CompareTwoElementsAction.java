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
 *  Tatiana Fesenko (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.compare.ui.action;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.CompareViewerPane;
import org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.compare.EMFCompareException;
import org.eclipse.emf.compare.diff.metamodel.AbstractDiffExtension;
import org.eclipse.emf.compare.diff.metamodel.ComparisonResourceSnapshot;
import org.eclipse.emf.compare.diff.metamodel.ComparisonSnapshot;
import org.eclipse.emf.compare.diff.metamodel.DiffFactory;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.service.DiffService;
import org.eclipse.emf.compare.match.MatchOptions;
import org.eclipse.emf.compare.match.engine.GenericMatchScopeProvider;
import org.eclipse.emf.compare.match.engine.IMatchEngine;
import org.eclipse.emf.compare.match.metamodel.MatchModel;
import org.eclipse.emf.compare.ui.editor.ModelCompareEditorInput;
import org.eclipse.emf.compare.ui.viewer.content.ModelContentMergeViewer;
import org.eclipse.emf.compare.util.EMFCompareMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.papyrus.compare.diff.extension.UMLStyledStructureLabelProvider;
import org.eclipse.papyrus.compare.element.ElementContentDiffEngine;
import org.eclipse.papyrus.compare.element.ElementContentMatchEngine;
import org.eclipse.papyrus.compare.ui.Activator;
import org.eclipse.papyrus.compare.ui.PapyrusLabelProvider;
import org.eclipse.papyrus.compare.ui.viewer.content.ElementContentMergeContentProvider;
import org.eclipse.papyrus.compare.ui.viewer.content.UMLModelContentMergeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.team.internal.ui.actions.TeamAction;
import org.eclipse.ui.PlatformUI;

public class CompareTwoElementsAction extends TeamAction {

	protected void execute(IAction action) throws InvocationTargetException, InterruptedException {

		Object[] selectedElements = getSelection().toArray();
		if(selectedElements.length != 2) {
			Activator.logInfo(Messages.bind(Messages.CompareTwoElementsAction_only_only_element_is_selected, selectedElements.length));
			return;
		}

		EObject left = getElementFor(selectedElements[0]);
		EObject right = getElementFor(selectedElements[1]);
		if(left == null) {
			Activator.logInfo(Messages.CompareTwoElementsAction_left_element_is_null);
			return;
		}

		if(right == null) {
			Activator.logInfo(Messages.CompareTwoElementsAction_right_element_is_null);
			return;
		}

		ComparisonResourceSnapshot snapshot = doContentCompare(left, right);
		openInCompare(snapshot);
	}

	public boolean isEnabled() {
		Object[] selectedElements = getSelection().toArray();
		if(selectedElements.length != 2) {
			return false;
		}
		EObject left = getElementFor(selectedElements[0]);
		EObject right = getElementFor(selectedElements[1]);
		return left != null && right != null;
	}

	private void openInCompare(ComparisonSnapshot snapshot) {
		CompareUI.openCompareEditor(new ModelCompareEditorInput(snapshot) {

			@Override
			protected ModelContentMergeViewer createMergeViewer(CompareViewerPane pane, CompareConfiguration config) {
				return new UMLModelContentMergeViewer(pane, config) {
					@Override
					protected IMergeViewerContentProvider createMergeViewerContentProvider() {
						return new ElementContentMergeContentProvider(configuration);
					}
				};
			}

			@Override
			public Control createOutlineContents(Composite parent, int direction) {
				Control result = super.createOutlineContents(parent, direction);
				structureMergeViewer.setLabelProvider(new UMLStyledStructureLabelProvider(new PapyrusLabelProvider()));
				return result;
			}

		});
	}

	protected ComparisonResourceSnapshot doContentCompare(final EObject left, final EObject right) {
		final ComparisonResourceSnapshot snapshot = DiffFactory.eINSTANCE.createComparisonResourceSnapshot();

		try {
			PlatformUI.getWorkbench().getProgressService().busyCursorWhile(new IRunnableWithProgress() {

				public void run(IProgressMonitor monitor) throws InterruptedException {
					final MatchModel match = contentMatch(left, right, monitor);
					final DiffModel diff = contentDiff(left, right, match);

					snapshot.setDiff(diff);
					snapshot.setMatch(match);
				}

			});
		} catch (final InterruptedException e) {
			Activator.logError(e);
		} catch (final EMFCompareException e) {
			Activator.logError(e);
		} catch (final InvocationTargetException e) {
			Activator.logError(e);
		}
		return snapshot;
	}

	protected DiffModel contentDiff(final EObject left, final EObject right, final MatchModel match) {
		ElementContentDiffEngine engine = new ElementContentDiffEngine(left, right);
		final DiffModel diff = engine.doDiff(match);
		final Collection<AbstractDiffExtension> extensions = DiffService.getCorrespondingDiffExtensions(match);
		for(final AbstractDiffExtension ext : extensions) {
			if(ext != null) {
				ext.visit(diff);
			}
		}

		engine.reset();
		return diff;
	}

	protected MatchModel contentMatch(final EObject left, final EObject right, IProgressMonitor monitor) throws InterruptedException {
		final Map<String, Object> options = new EMFCompareMap<String, Object>();
		options.put(MatchOptions.OPTION_PROGRESS_MONITOR, monitor);
		options.put(MatchOptions.OPTION_MATCH_SCOPE_PROVIDER, new GenericMatchScopeProvider(left.eResource(), right.eResource()));
		options.put(MatchOptions.OPTION_IGNORE_ID, Boolean.TRUE);
		options.put(MatchOptions.OPTION_IGNORE_XMI_ID, Boolean.TRUE);

		final IMatchEngine matchEngine = new ElementContentMatchEngine(left, right);
		final MatchModel match = matchEngine.contentMatch(left, right, options);
		return match;
	}

	protected EObject getElementFor(Object object) {
		if(object instanceof IAdaptable) {
			return (EObject)((IAdaptable)object).getAdapter(EObject.class);
		}

		if(object instanceof EObject) {
			return (EObject)object;
		}
		return null;
	}

}
