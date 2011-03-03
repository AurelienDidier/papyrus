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
 *  Ansgar Radermacher (CEA LIST) ansgar.radermacher@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.diagram.common.resourceupdate;

import static org.eclipse.papyrus.core.Activator.log;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.papyrus.core.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.core.services.IService;
import org.eclipse.papyrus.core.services.ServiceException;
import org.eclipse.papyrus.core.services.ServicesRegistry;
import org.eclipse.papyrus.core.utils.EditorUtils;
import org.eclipse.papyrus.diagram.common.Activator;
import org.eclipse.papyrus.resource.ModelSet;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.uml2.uml.Profile;

/**
 * A listener for resource changes, used to trigger an update of
 * models whose underlying resources have been changed.
 * 
 * @author Ansgar Radermacher (CEA LIST)
 */
public class ResourceUpdateService implements IService, IResourceChangeListener, IResourceDeltaVisitor {

	public static final String RESOURCE_UPDATE_ID = Activator.ID + ".resourceUpdate";

	// public init (CoreMultiDiagramEditor editor, ISaveAndDirtyService saveAndDirty, ModelSet modelSet) {
	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
		isActive = true;

		modelSet = servicesRegistry.getService(ModelSet.class);
		editor = servicesRegistry.getService(IMultiDiagramEditor.class);
	}

	/**
	 * The listener operation that is called by the workspace
	 */
	public void resourceChanged(IResourceChangeEvent event) {
		if(isActive) {
			switch(event.getType()) {
			case IResourceChangeEvent.PRE_CLOSE:
			case IResourceChangeEvent.PRE_BUILD:
			case IResourceChangeEvent.POST_BUILD:
			case IResourceChangeEvent.PRE_DELETE:
				// do nothing (only handle change)
				break;
			case IResourceChangeEvent.POST_CHANGE:
				try {
					// delegate to visitor (event.getResource is typically null) and there
					// might be a tree of changed resources
					event.getDelta().accept(this);
				} catch (CoreException coreException) {
					log.error(coreException);
				}
				break;
			}
		}
	}

	/**
	 * A visitor for resource changes. Detects, whether a changed resource belongs to an opened editor
	 */
	public boolean visit(IResourceDelta delta) {
		if(!isActive) {
			// don't follow resource changes, once inactive (due to a pending user dialog) 
			return false;
		}
		IResource changedResource = delta.getResource();
		if(delta.getFlags() == IResourceDelta.MARKERS) {
			// only markers have been changed. Refresh their display only (no need to reload resources)
			// TODO called once for each new marker => assure asynchronous refresh
			modelSet.eNotify(new NotificationImpl(Notification.SET, new Object(), delta.getMarkerDeltas()));
			return false;
		}
		boolean resourceOfMainModelChanged = false;
		// only proceed in case of Files (not projects, folders, ...) for the moment
		if(!(changedResource instanceof IFile)) {
			return true;
		}
		final String changedResourcePath = changedResource.getFullPath().toString();
		IPath changedResourcePathWOExt = changedResource.getFullPath().removeFileExtension();
		URIConverter uriConverter = modelSet.getURIConverter();

		for(Resource resource : modelSet.getResources()) {
			URI uri = resource.getURI();
			URI normalizedURI = uriConverter.normalize(uri);

			// URI path is prefixed with /resource or /plugin (registered model), therefore compare with
			// endsWith.
			// Comparison is done on path level since resource and changedResource are never
			// identical. The latter is a generic system resource (File, ...), the former a
			// model-aware representation of the resource
			if(normalizedURI.path().endsWith(changedResourcePath)) {
				if(changedResourcePathWOExt.equals(modelSet.getFilenameWithoutExtension())) {
					// model itself has changed.
					// check before, if it is not the model of the active Papyrus editor (which may perform a save/saveAs operation)
					IMultiDiagramEditor editor = EditorUtils.getMultiDiagramEditor();
					if(editor != null) {
						try {
							ModelSet modelSetOfActiveEditor = editor.getServicesRegistry().getService(ModelSet.class);
							if(!changedResourcePathWOExt.equals(modelSetOfActiveEditor.getFilenameWithoutExtension())) {
								// if !equal: resource opened by a non-active editor has changed
								// => change is not the result of save/saveAs operation => ask user 
								resourceOfMainModelChanged = true;
								break;
							}
						} catch (ServiceException e) {
						}
					}
				}
				// changed resource does not belong to the model, it might however belong to a referenced
				// model. Since the referenced model is not editable (TODO: might change? see bug 317430),
				// it can be unloaded without asking the user (it will be reloaded on demand)

				else if(resource.isLoaded()) {
					EList<EObject> contents = resource.getContents();
					if((contents.size() > 0) && (contents.get(0) instanceof Profile)) {
						// don't touch profiles
					} else {
						resource.unload();
					}
				}
			}
		}
		if(!resourceOfMainModelChanged) {
			return true;
		}
		switch(delta.getKind()) {
		case IResourceDelta.ADDED:
			break;
		case IResourceDelta.REMOVED:
			// asynchronous notification to avoid that the removal of multiple resource files
			// belonging to the editor (e.g. .uml and .notation) at the same time leads to multiple
			// user feedback.
			isActive = false;
			Display.getDefault().asyncExec(new Runnable() {

				public void run() {

					MessageDialog.openInformation(new Shell(), "Resource removal", "The resource " + changedResourcePath + " that is in use by a Papyrus editor has been removed. Use save/save as, if you want to keep the model");
					isActive = true;
				}
			});
			break;
		case IResourceDelta.CHANGED:
			// reopen the editor asynchronously to avoid that changes of multiple resource files
			// belonging to the editor (e.g. .uml and .notation) lead to multiple reloads.
			// de-activate until user responds to message dialog 
			isActive = false;
			Display.getDefault().asyncExec(new Runnable() {

				public void run() {

					String message = "The resource " + changedResourcePath + " that is in use by a Papyrus editor has changed. Do you want to reopen the editor in order to update its contents?";
					if(editor.isDirty()) {
						message += " CAVEAT: the editor contains unsaved modifications that would be lost.";
					}

					if(MessageDialog.openQuestion(new Shell(), "Resource change", message)) {
						// unloading and reloading all resources of the main causes the following problems
						//  - since resources are removed during the modelSets unload operation, the call eResource().getContents ()
						//    used by the model explorer leads to a null pointer exception
						//  - diagrams in model explorer are not shown
						//  - would need to reset dirty flags
						// => clean & simple option is to close and reopen the editor.

						IWorkbench wb = PlatformUI.getWorkbench();
						IWorkbenchPage page = wb.getActiveWorkbenchWindow().getActivePage();
						IEditorInput input = editor.getEditorInput();
						page.closeEditor(editor, false);
						try {
							IEditorDescriptor desc = wb.getEditorRegistry().getDefaultEditor(input.getName());
							page.openEditor(input, desc.getId(), false);
						} catch (PartInitException e) {
							log.error(e);
						}
					} else {
						// response "no" => don't reload and reactivate listener
						isActive = true;
					}
				}
			});
			break;
		}
		return true; // visit the children
	}

	private boolean isActive;

	private IMultiDiagramEditor editor;

	private ModelSet modelSet;

	// private ILifeCycleEventsProvider lifeCycleEvents;

	private void activate() {
		// ... add service to the workspace
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this, IResourceChangeEvent.POST_CHANGE);
	}

	private void deactivate() {
		// remove it from workspace
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
	}

	public void startService() throws ServiceException {
		activate();
	}

	public void disposeService() throws ServiceException {
		deactivate();
		// lifeCycleEvents.removeDoSaveListener(listener);
	}
}
