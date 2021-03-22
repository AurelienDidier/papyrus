/*****************************************************************************
 * Copyright (c) 2019-2020 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 559824
 *****************************************************************************/
package org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.ui.internal.editor;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.resource.ResourceItemProvider;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceSetItemProvider;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.diagram.ui.tools.internal.editor.DDiagramEditorImpl;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;

/**
 * DocumenView Editor.
 *
 * This editor is contributed throw the extension point org.eclipse.papyrus.infra.ui.papyrusDiagram.
 *
 * In order to get the new child menu, we register the action bar contribution using this same extension point and we use if for this editor.
 */
@SuppressWarnings("restriction")
public class NestedSiriusDiagramViewEditor extends DDiagramEditorImpl {

	/** the service registry */
	protected ServicesRegistry servicesRegistry;

	/**
	 * The edited document template
	 */
	private DDiagram document;

	/**
	 *
	 * Constructor.
	 *
	 * @param servicesRegistry
	 *            the Papyrus service registry, it can't be <code>null</code>
	 * @param rawModel
	 *            the edited element, it can't be <code>null</code>
	 */
	public NestedSiriusDiagramViewEditor(ServicesRegistry servicesRegistry, DDiagram rawModel) {
		super();
		this.document = rawModel;
		this.servicesRegistry = servicesRegistry;
		Assert.isNotNull(this.document, "The edited DDiagram is null. The DDiagram Editor creation failed"); //$NON-NLS-1$
		Assert.isNotNull(this.servicesRegistry, "The papyrus ServicesRegistry is null. The DDiagram Editor creation failed."); //$NON-NLS-1$
		initializeEditingDomain();
	}

	/**
	 *
	 * @see org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.ui.internal.editor.CustomsiriusdiagramEditor#initializeEditingDomain()
	 *
	 */

	public void initializeEditingDomain() {
		if (this.servicesRegistry == null) {
			return;
		}
		// super.initializeEditingDomain();
	}


	// /**
	// *
	// * @see org.eclipse.papyrus.sirius.emf.siriusdiagram.presentation.siriusdiagramEditor#getEditingDomain()
	// *
	// * @return
	// */
	// @Override
	// public TransactionalEditingDomain getEditingDomain() {
	// try {
	// return ServiceUtils.getInstance().getTransactionalEditingDomain(this.servicesRegistry);
	// } catch (final ServiceException e) {
	// Activator.log.error(e);
	// }
	// return null;
	// }
	//
	// /**
	// *
	// * @see org.eclipse.papyrus.sirius.emf.siriusdiagram.presentation.siriusdiagramEditor#doSave(org.eclipse.core.runtime.IProgressMonitor)
	// *
	// * @param monitor
	// */
	// @Override
	// public void doSave(IProgressMonitor monitor) {
	// // manage by the Papyrus main editor
	// }
	//
	// /**
	// *
	// * @see org.eclipse.papyrus.sirius.emf.siriusdiagram.presentation.siriusdiagramEditor#doSaveAs()
	// *
	// */
	// @Override
	// public void doSaveAs() {
	// }
	//
	// /**
	// *
	// * @see org.eclipse.papyrus.sirius.emf.siriusdiagram.presentation.siriusdiagramEditor#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	// *
	// * @param site
	// * @param input
	// */
	// @Override
	// public void init(IEditorSite site, IEditorInput input) {// throws PartInitException {
	// final SiriusDiagramEditorInput documentViewEditorInput = new SiriusDiagramEditorInput(this.document);
	// // super.init(site, documentViewEditorInput);
	// }
	//
	// @Override
	// public boolean isDirty() {
	// // manage by the Papyrus main editor
	// return false;
	// }
	//
	// /**
	// *
	// * @see org.eclipse.papyrus.sirius.emf.siriusdiagram.presentation.siriusdiagramEditor#isSaveAsAllowed()
	// *
	// * @return
	// */
	// @Override
	// public boolean isSaveAsAllowed() {
	// // manage by the Papyrus main editor
	// return false;
	// }
	//

	/**
	 *
	 * @param commandStack
	 */
	protected void initDomainAndStack() {
		// final TransactionalEditingDomain domain = getEditingDomain();
		// Assert.isTrue(domain instanceof AdapterFactoryEditingDomain);
		// this.editingDomain = (AdapterFactoryEditingDomain) domain;
		// final CommandStack stack = this.editingDomain.getCommandStack();
		// addCommandStackListener(stack);
	}

	/**
	 * @see org.eclipse.papyrus.sirius.integration.emf.siriusdiagram.ui.internal.editor.CustomsiriusdiagramEditor#initAdapterFactory()
	 *
	 */
	protected void initAdapterFactory() {
		// adapterFactory = createComposedAdapterFactory();
		// adapterFactory.addAdapterFactory(new CustomResourceItemProviderAdapterFactory());
		// adapterFactory.addAdapterFactory(new SiriusDiagramItemProviderAdapterFactory());
		// adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
		// adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
	}

	/**
	 * @see org.eclipse.ui.part.MultiPageEditorPart#createSite(org.eclipse.ui.IEditorPart)
	 *
	 * @param editor
	 * @return
	 */
	protected IEditorSite createSite(IEditorPart editor) {
		// used to be able to have the error editor part nested in the embedded emf editor
		// return getEditorSite();
		return null;
	}

	/**
	 *
	 * Custom ResourceItemProviderAdapterFactory to be able to show only the structure of the SiriusDiagram
	 * and not other elements contained in the file
	 *
	 */
	private class CustomResourceItemProviderAdapterFactory extends ResourceItemProviderAdapterFactory {

		/**
		 * @see org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory#createResourceSetAdapter()
		 *
		 * @return
		 */
		@Override
		public Adapter createResourceSetAdapter() {
			return new CustomResourceSetItemProvider(this);
		}

		/**
		 * @see org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory#createResourceAdapter()
		 *
		 * @return
		 */
		@Override
		public Adapter createResourceAdapter() {
			return new CustomResourceItemProvider(this);
		}
	}


	/**
	 *
	 * Custom ResourceSetItemProvider used to display only the edited documentemplate and not other file contents
	 *
	 */
	private class CustomResourceSetItemProvider extends ResourceSetItemProvider {

		/**
		 * Constructor.
		 *
		 * @param adapterFactory
		 */
		public CustomResourceSetItemProvider(AdapterFactory adapterFactory) {
			super(adapterFactory);
		}


		/**
		 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getElements(java.lang.Object)
		 *
		 * @param object
		 * @return
		 */
		@Override
		public Collection<?> getElements(Object object) {
			return Collections.singleton(document.eResource());
		}
	}

	/**
	 *
	 * Custom ResourceItemProvider to get only the {@link SiriusDiagram} displayed in the EcoreEditor
	 *
	 */
	private class CustomResourceItemProvider extends ResourceItemProvider {

		/**
		 * Constructor.
		 *
		 * @param adapterFactory
		 */
		public CustomResourceItemProvider(AdapterFactory adapterFactory) {
			super(adapterFactory);
		}

		/**
		 *
		 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#getElements(java.lang.Object)
		 *
		 * @param object
		 * @return
		 */
		@Override
		public Collection<?> getElements(Object object) {
			return super.getElements(object);
		}

		/**
		 *
		 * @see org.eclipse.emf.edit.provider.ItemProviderAdapter#hasChildren(java.lang.Object)
		 *
		 * @param object
		 * @return
		 */
		@Override
		public boolean hasChildren(Object object) {
			if (object instanceof Resource) {
				return true;
			}
			return super.hasChildren(object);
		}

		/**
		 *
		 * @see org.eclipse.emf.edit.provider.resource.ResourceItemProvider#getParent(java.lang.Object)
		 *
		 * @param object
		 * @return
		 */
		@Override
		public Object getParent(Object object) {
			return super.getParent(object);
		}

		/**
		 *
		 * @see org.eclipse.emf.edit.provider.resource.ResourceItemProvider#getChildren(java.lang.Object)
		 *
		 * @param object
		 * @return
		 */
		@Override
		public Collection<?> getChildren(Object object) {
			if (object instanceof Resource) {
				return Collections.singletonList(document);
			}
			return super.getChildren(object);
		}

	}

}
