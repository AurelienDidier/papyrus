/*****************************************************************************
 * Copyright (c) 2010, 2014 CEA LIST and others.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 437217
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.common;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.ui.palette.PaletteViewer;
import org.eclipse.gef.ui.views.palette.PalettePage;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IPrimaryEditPart;
import org.eclipse.gmf.runtime.diagram.ui.internal.properties.WorkspaceViewerProperties;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditDomain;
import org.eclipse.gmf.runtime.diagram.ui.parts.DiagramGraphicalViewer;
import org.eclipse.gmf.runtime.diagram.ui.resources.editor.parts.DiagramDocumentEditor;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.papyrus.commands.CheckedDiagramCommandStack;
import org.eclipse.papyrus.infra.core.editor.reload.IReloadContextProvider;
import org.eclipse.papyrus.infra.gmfdiag.common.preferences.PreferencesConstantsHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramReconciler;
import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramReconcilersReader;
import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramVersioningUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.CommandIds;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.GMFUnsafe;
import org.eclipse.papyrus.infra.tools.util.EclipseCommandUtils;
import org.eclipse.papyrus.infra.widgets.util.IRevealSemanticElement;
import org.eclipse.papyrus.infra.widgets.util.NavigationTarget;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.part.IPageSite;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * 
 * This GMF editor contains a methods in order to reveal visual element from a list of semantic element.
 * <p/>
 * It also provides subclasses with common capability to automatically migrate (reconcile) input 
 * diagrams between papurus versions.  
 */

@SuppressWarnings("restriction")
//suppress the warning for WorkspaceViewerProperties
public class SynchronizableGmfDiagramEditor extends DiagramDocumentEditor implements IRevealSemanticElement, NavigationTarget {

	private Collection<PalettePageWrapper> palettePages;
	
	private Object palettePageState;

	public SynchronizableGmfDiagramEditor(boolean hasFlyoutPalette) {
		super(hasFlyoutPalette);
	}

	/**
	 * reveal all editpart that represent an element in the given list.
	 * 
	 * @see org.eclipse.papyrus.infra.core.ui.IRevealSemanticElement#revealSemanticElement(java.util.List)
	 * 
	 */
	public void revealSemanticElement(List<?> elementList) {
		revealElement(elementList);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean revealElement(Object element) {
		return revealElement(Collections.singleton(element));
	}


	/**
	 * {@inheritDoc}
	 * 
	 * reveal all editpart that represent an element in the given list.
	 * 
	 * @see org.eclipse.papyrus.infra.core.ui.IRevealSemanticElement#revealSemanticElement(java.util.List)
	 * 
	 */
	public boolean revealElement(Collection<?> elementList) {
		//create an instance that can get semantic element from gmf
		SemanticFromGMFElement semanticFromGMFElement = new SemanticFromGMFElement();

		// get the graphical viewer
		GraphicalViewer graphicalViewer = getGraphicalViewer();
		if(graphicalViewer != null) {

			//look for among all edit part if the semantic is contained in the list
			Iterator<?> iter = graphicalViewer.getEditPartRegistry().values().iterator();
			IGraphicalEditPart researchedEditPart = null;

			while(iter.hasNext() && researchedEditPart == null) {
				Object currentEditPart = iter.next();
				//look for only among IPrimary editpart to avoid compartment and labels of links
				if(currentEditPart instanceof IPrimaryEditPart) {
					if(elementList.contains(semanticFromGMFElement.getSemanticElement(currentEditPart))) {
						researchedEditPart = ((IGraphicalEditPart)currentEditPart);
						break;
					}
				}
			}

			//We may also search for a GMF View (Instead of a semantic model Element)
			if(researchedEditPart == null) {
				for(Object element : elementList) {
					if(graphicalViewer.getEditPartRegistry().containsKey(element)) {
						researchedEditPart = (IGraphicalEditPart)graphicalViewer.getEditPartRegistry().get(element);
						break;
					}
				}
			}

			//an editpart has been found so put selection on it.
			if(researchedEditPart != null) {
				graphicalViewer.select(researchedEditPart); //Set selection
				graphicalViewer.reveal(researchedEditPart); //If needed, scroll to make the edit part visible
				return true;
			}
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getAdapter(@SuppressWarnings("rawtypes") Class type) {
		if(type == DiagramEditPart.class) {
			return getDiagramEditPart();
		}
		if(type == Diagram.class) {
			return getDiagram();
		}
		if(type == IReloadContextProvider.class) {
			return new DiagramReloadContextProvider(this);
		}
		if(type == PalettePage.class) {
			if(palettePages == null) {
				palettePages = Lists.newArrayListWithExpectedSize(1);
			} else {
				cleanUpPalettePages();
				if(!palettePages.isEmpty()) {
					// Make the new page look just like the last one (for continuity of the UI when the
					// PapyrusPaletteSynchronizer causes a new page to be created)
					Iterables.getLast(palettePages, null).saveState();
				}
			}
			PalettePageWrapper result = new PalettePageWrapper((CustomPalettePage)super.getAdapter(type));
			palettePages.add(result);
			return result;
		}
		return super.getAdapter(type);
	}

	Collection<? extends PalettePage> getPalettePages() {
		if(palettePages == null) {
			return Collections.emptyList();
		} else {
			cleanUpPalettePages();
			return Collections.unmodifiableCollection(palettePages);
		}
	}
	
	void setDeferredPalettePageReloadContext(Object reloadContext) {
		palettePageState = reloadContext;
	}
	
	private void cleanUpPalettePages() {
		for(Iterator<PalettePageWrapper> iter = palettePages.iterator(); iter.hasNext();) {
			if(iter.next().isDisposed()) {
				iter.remove();
			}
		}
	}
	
	/**
	 * Configures my diagram edit domain with its command stack.
	 * This method has been completely overridden in order to use a proxy stack.
	 */
	@Override
	protected void configureDiagramEditDomain() {

		DefaultEditDomain editDomain = getEditDomain();

		if(editDomain != null) {
			CommandStack stack = editDomain.getCommandStack();
			if(stack != null) {
				// dispose the old stack
				stack.dispose();
			}

			// create and assign the new stack
			CheckedDiagramCommandStack diagramStack = new CheckedDiagramCommandStack(getDiagramEditDomain());

			editDomain.setCommandStack(diagramStack);
		}

		DiagramEditDomain diagEditDomain = (DiagramEditDomain)getDiagramEditDomain();
		diagEditDomain.setActionManager(createActionManager());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setFocus() {
		super.setFocus();
		updateToggleActionState();
	}


	/**
	 * this command update the status of the toggle actions
	 */
	protected void updateToggleActionState() {
		final ICommandService commandService = (ICommandService)PlatformUI.getWorkbench().getActiveWorkbenchWindow().getService(ICommandService.class);
		if(commandService != null) {
			final IPreferenceStore wsPreferenceStore = ((DiagramGraphicalViewer)getDiagramGraphicalViewer()).getWorkspaceViewerPreferenceStore();
			org.eclipse.core.commands.Command command = commandService.getCommand(CommandIds.VIEW_GRID_COMMAND);
			EclipseCommandUtils.updateToggleCommandState(command, wsPreferenceStore.getBoolean(WorkspaceViewerProperties.VIEWGRID));

			command = commandService.getCommand(CommandIds.VIEW_RULER_COMMAND);
			EclipseCommandUtils.updateToggleCommandState(command, wsPreferenceStore.getBoolean(WorkspaceViewerProperties.VIEWRULERS));

			command = commandService.getCommand(CommandIds.VIEW_PAGE_BREAK_COMMAND);
			EclipseCommandUtils.updateToggleCommandState(command, wsPreferenceStore.getBoolean(WorkspaceViewerProperties.VIEWPAGEBREAKS));

			command = commandService.getCommand(CommandIds.SNAP_TO_GRID_COMMAND);
			EclipseCommandUtils.updateToggleCommandState(command, wsPreferenceStore.getBoolean(WorkspaceViewerProperties.SNAPTOGRID));

		} else {
			throw new RuntimeException(String.format("The Eclipse service %s has not been found", ICommandService.class)); //$NON-NLS-1$
		}
	}


	@Override
	protected void addDefaultPreferences() {
		super.addDefaultPreferences();
		final PreferencesHint preferencesHint = getPreferencesHint();
		final IPreferenceStore globalPreferenceStore = (IPreferenceStore)preferencesHint.getPreferenceStore();
		final String diagramType = getDiagram().getType();
		//get the preferences
		final boolean viewGrid = globalPreferenceStore.getBoolean(PreferencesConstantsHelper.getDiagramConstant(diagramType, PreferencesConstantsHelper.VIEW_GRID));
		final boolean viewRuler = globalPreferenceStore.getBoolean(PreferencesConstantsHelper.getDiagramConstant(diagramType, PreferencesConstantsHelper.VIEW_RULER));
		final int rulerUnit = globalPreferenceStore.getInt(PreferencesConstantsHelper.getDiagramConstant(diagramType, PreferencesConstantsHelper.RULER_UNITS));
		final boolean snapToGrid = globalPreferenceStore.getBoolean(PreferencesConstantsHelper.getDiagramConstant(diagramType, PreferencesConstantsHelper.SNAP_TO_GRID));
		final boolean snapToGeometry = globalPreferenceStore.getBoolean(PreferencesConstantsHelper.getDiagramConstant(diagramType, PreferencesConstantsHelper.SNAP_TO_GEOMETRY));
		final RGB rgb = PreferenceConverter.getColor(globalPreferenceStore, PreferencesConstantsHelper.getDiagramConstant(diagramType, PreferencesConstantsHelper.GRID_LINE_COLOR));
		final int gridLineColor = FigureUtilities.RGBToInteger(rgb);
		final double gridSpacing = globalPreferenceStore.getDouble(PreferencesConstantsHelper.getDiagramConstant(diagramType, PreferencesConstantsHelper.GRID_SPACING));
		final boolean gridOrder = globalPreferenceStore.getBoolean(PreferencesConstantsHelper.getDiagramConstant(diagramType, PreferencesConstantsHelper.GRID_ORDER));
		final int gridLineStyle = globalPreferenceStore.getInt(PreferencesConstantsHelper.getDiagramConstant(diagramType, PreferencesConstantsHelper.GRID_LINE_STYLE));

		//set the preferences
		final IPreferenceStore localStore = getWorkspaceViewerPreferenceStore();
		localStore.setValue(PreferencesConstantsHelper.VIEW_GRID_CONSTANT, viewGrid);
		localStore.setValue(PreferencesConstantsHelper.VIEW_RULERS_CONSTANT, viewRuler);
		localStore.setValue(PreferencesConstantsHelper.RULER_UNITS_CONSTANT, rulerUnit);
		localStore.setValue(PreferencesConstantsHelper.SNAP_TO_GRID_CONSTANT, snapToGrid);
		localStore.setValue(PreferencesConstantsHelper.SNAP_TO_GEOMETRY_CONSTANT, snapToGeometry);
		localStore.setValue(PreferencesConstantsHelper.GRID_LINE_COLOR_CONSTANT, gridLineColor);
		localStore.setValue(PreferencesConstantsHelper.GRID_SPACING_CONSTANT, gridSpacing);
		
		//to force refresh
		localStore.setValue(PreferencesConstantsHelper.GRID_ORDER_CONSTANT, !gridOrder);
		localStore.setValue(PreferencesConstantsHelper.GRID_ORDER_CONSTANT, gridOrder);
		
		localStore.setValue(PreferencesConstantsHelper.GRID_LINE_STYLE_CONSTANT, gridLineStyle);

	}
	
	/**
	 * Encapsulates the reconciling, that is diagram migration between version of Papyrus.
	 */
	protected static class ReconcileHelper {
		private final TransactionalEditingDomain domain;
		
		/**
		 * Instantiates helper that will work with given {@link TransactionalEditingDomain}.
		 * Note that reconcile operations are performed outside the diagram command stack using {@link GMFUnsafe}. 
		 */
		public ReconcileHelper(TransactionalEditingDomain domain) {
			this.domain = domain;
		}
		
		/**
		 * Process diagram reconcilers to migrate models. Does nothing if the diagram is already of the current Papyrus version based on
		 * {@link DiagramVersioningUtils#isOfCurrentPapyrusVersion(Diagram)} check.
		 * <p/>
		 * This method needs configured {@link DiagramEditDomain} to execute collected {@link ICommand} when needed, so it can't be called from
		 * constructor
		 * 
		 * @param diagram
		 *        the diagram to reconcile
		 * @throws CoreException
		 *         subclass may throw wrapping any problem thrown from execution of reconcile using {@link GMFUnsafe}. Default implementation does not
		 *         throw it however
		 */
		public void reconcileDiagram(Diagram diagram) throws CoreException {
			CompositeCommand migration = buildReconcileCommand(diagram);
			if (migration == null) {
				return;
			}
			migration.add(DiagramVersioningUtils.createStampCurrentVersionCommand(diagram));
			try {
				GMFUnsafe.write(domain, migration);
			} catch (ExecutionException e) {
				handleReconcileException(diagram, e);
			} catch (InterruptedException e) {
				handleReconcileException(diagram, e);
			} catch (RollbackException e) {
				handleReconcileException(diagram, e);
			}
		}

		/**
		 * Process diagram reconcilers to migrate models.
		 * 
		 * Returns <code>null</code> if the diagram is already of the current Papyrus version based on
		 * {@link DiagramVersioningUtils#isOfCurrentPapyrusVersion(Diagram)} check.
		 * <p/>
		 * If one of the reconcilers returns un-executable command, this method logs the problem and returns <code>null</code>
		 * 
		 * @param diagram
		 *        the diagram to reconcile
		 */
		protected CompositeCommand buildReconcileCommand(Diagram diagram) {
			if(DiagramVersioningUtils.isOfCurrentPapyrusVersion(diagram)) {
				return null;
			}
			String sourceVersion = DiagramVersioningUtils.getCompatibilityVersion(diagram);
			Map<String, Collection<DiagramReconciler>> diagramReconcilers = DiagramReconcilersReader.getInstance().load();
			String diagramType = diagram.getType();
			if(!diagramReconcilers.containsKey(diagramType)) {
				return null;
			}
			Collection<DiagramReconciler> reconcilers = diagramReconcilers.get(diagram.getType());

			boolean someFailed = false;
			CompositeCommand whole = new CompositeCommand("Reconciling");
			for(DiagramReconciler next : reconcilers) {
				if(!next.canReconcileFrom(diagram, sourceVersion)) {
					//asked for ignore it for this instance, all fine
					continue;
				}
				ICommand nextCommand = next.getReconcileCommand(diagram);
				if(nextCommand == null) {
					//legitimate no-op response, all fine
					continue;
				}
				if(nextCommand.canExecute()) {
					whole.add(nextCommand);
				} else {
					Activator.log.error("Diagram reconciler " + next + " failed to reconcile diagram : " + diagram, null); //$NON-NLS-1$ //$NON-NLS-2$  
					someFailed = true;
				}
			}
			if(someFailed) {
				//probably better to fail the whole reconicle process as user will have a chance to reconcile later when we fix the problem with one of the reconcilers
				//executing partial reconciliation will leave the diagram in the state with partially current and partially outdated versions
				return null;
			}

			return whole;
		}

		/**
		 * Handles exception from running the diagram reconciler under {@link GMFUnsafe}.
		 * At the time method is called the diagram is probably broken, but default implementation just logs error.
		 * <p/>
		 * This is to allow subclass to decide whether it is worth opening the problem diagram.
		 */
		protected void handleReconcileException(Diagram diagram, Exception e) throws CoreException {
			Activator.getInstance().logError("Reconciling the diagram: " + diagram, e); 
		}
	}
	
	protected class PalettePageWrapper implements PalettePage, IAdaptable {

		private final CustomPalettePage delegate;
		
		private boolean disposed;

		protected PalettePageWrapper(CustomPalettePage delegate) {
			this.delegate = delegate;
		}

		public void createControl(Composite parent) {
			Control existing = getControl();
			if((existing != null) && !existing.isDisposed()) {
				// Attempting to creating the page controls again? Bail
				return;
			}
			
			delegate.createControl(parent);

			delegate.getControl().addDisposeListener(new DisposeListener() {

				@Override
				public void widgetDisposed(DisposeEvent e) {
					disposed = true;
					
					SynchronizableGmfDiagramEditor.this.palettePages.remove(PalettePageWrapper.this);
				}
			});

			if(palettePageState != null) {
				// We're re-creating the palette page after having closed it, either for editor re-load
				// or the PapyrusPaletteSynchronizer forcing a palette refresh. Reinitialize from the
				// last saved state
				PaletteViewerReloadContextProvider.getInstance(getPaletteViewer()).restore(palettePageState);
				palettePageState = null;
			}
		}

		public void dispose() {
			// Save current state for potential re-opening later
			saveState();
			delegate.dispose();
		}
		
		public boolean isDisposed() {
			return disposed;
		}
		
		void saveState() {
			PaletteViewer palette = getPaletteViewer();
			if(palette != null) {
				palettePageState = PaletteViewerReloadContextProvider.getInstance(palette).createReloadContext();
			}
		}

		public Object getAdapter(@SuppressWarnings("rawtypes") Class adapter) {
			if(adapter == IReloadContextProvider.class) {
				return new IReloadContextProvider() {

					@Override
					public Object createReloadContext() {
						return (getPaletteViewer() != null) ? PaletteViewerReloadContextProvider.getInstance(getPaletteViewer()).createReloadContext() : null;
					}

					@Override
					public void restore(Object reloadContext) {
						if(getPaletteViewer() != null) {
							PaletteViewerReloadContextProvider.getInstance(getPaletteViewer()).restore(reloadContext);
						} else {
							// We'll defer this until the page control is created
							palettePageState = reloadContext;
						}
					}
				};
			}
			return delegate.getAdapter(adapter);
		}

		public Control getControl() {
			// CustomPalettePage will NPE if asked for the control before the PaletteViewer is created
			return (delegate.getPaletteViewer() == null) ? null : delegate.getControl();
		}

		public void setFocus() {
			delegate.setFocus();
		}

		public void setActionBars(IActionBars actionBars) {
			delegate.setActionBars(actionBars);
		}

		public void init(IPageSite pageSite) {
			delegate.init(pageSite);
		}

		public IPageSite getSite() {
			return delegate.getSite();
		}

		public PaletteViewer getPaletteViewer() {
			return delegate.getPaletteViewer();
		}

	}

}
