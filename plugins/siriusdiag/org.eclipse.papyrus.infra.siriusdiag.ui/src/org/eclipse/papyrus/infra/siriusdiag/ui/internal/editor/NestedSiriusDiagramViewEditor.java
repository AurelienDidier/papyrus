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
package org.eclipse.papyrus.infra.siriusdiag.ui.internal.editor;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProvider;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceSetItemProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.siriusdiag.ui.Activator;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.sirius.diagram.ui.tools.internal.editor.DDiagramEditorImpl;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;

/**
 * DocumenView Editor.
 *
 * This editor is contributed throw the extension point org.eclipse.papyrus.infra.ui.papyrusDiagram.
 *
 * In order to get the new child menu, we register the action bar contribution using this same extension point and we use if for this editor.
 */
public class NestedSiriusDiagramViewEditor extends DDiagramEditorImpl {

	/** the service registry */
	protected ServicesRegistry servicesRegistry;

	/**
	 * The edited document template
	 */
	private DSemanticDiagram diagram;

	/**
	 *
	 * Constructor.
	 *
	 * @param servicesRegistry
	 *            the Papyrus service registry, it can't be <code>null</code>
	 * @param rawModel
	 *            the edited element, it can't be <code>null</code>
	 */
	public NestedSiriusDiagramViewEditor(ServicesRegistry servicesRegistry, DSemanticDiagram rawModel) {
		super();
		this.diagram = rawModel;
		this.servicesRegistry = servicesRegistry;
		Assert.isNotNull(this.diagram, "The edited DSemanticDiagram is null. The DocumentStructureTemplate Editor creation failed"); //$NON-NLS-1$
		Assert.isNotNull(this.servicesRegistry, "The papyrus ServicesRegistry is null. The DocumentStructureTemplate Editor creation failed."); //$NON-NLS-1$
		initializeEditingDomain();
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.siriusdiag.ui.internal.editor.CustomDocumentStructureTemplateEditor#initializeEditingDomain()
	 *
	 */
	@Override
	public void initializeEditingDomain() {
		if (this.servicesRegistry == null) {
			return;
		}
		super.initializeEditingDomain();
	}


	/**
	 *
	 * @see org.eclipse.papyrus.infra.siriusdiag.presentation.DocumentStructureTemplateEditor#getEditingDomain()
	 *
	 * @return
	 */
	@Override
	public TransactionalEditingDomain getEditingDomain() {
		try {
			return ServiceUtils.getInstance().getTransactionalEditingDomain(this.servicesRegistry);
		} catch (final ServiceException e) {
			Activator.log.error(e);
		}
		return null;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.siriusdiag.presentation.DocumentStructureTemplateEditor#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 *
	 * @param monitor
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		// manage by the Papyrus main editor
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.siriusdiag.presentation.DocumentStructureTemplateEditor#doSaveAs()
	 *
	 */
	@Override
	public void doSaveAs() {
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.siriusdiag.presentation.DocumentStructureTemplateEditor#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 *
	 * @param site
	 * @param input
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) {// throws PartInitException {
		final SiriusDiagramEditorInput documentViewEditorInput = new SiriusDiagramEditorInput(this.diagram);
		super.init(site, documentViewEditorInput);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.siriusdiag.presentation.DocumentStructureTemplateEditor#isDirty()
	 *
	 * @return
	 */
	@Override
	public boolean isDirty() {
		// manage by the Papyrus main editor
		return false;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.siriusdiag.presentation.DocumentStructureTemplateEditor#isSaveAsAllowed()
	 *
	 * @return
	 */
	@Override
	public boolean isSaveAsAllowed() {
		// manage by the Papyrus main editor
		return false;
	}


	/**
	 *
	 * @param commandStack
	 */
	@Override
	protected void initDomainAndStack() {
		final TransactionalEditingDomain domain = getEditingDomain();
		Assert.isTrue(domain instanceof AdapterFactoryEditingDomain);
		this.editingDomain = (AdapterFactoryEditingDomain) domain;
		final CommandStack stack = this.editingDomain.getCommandStack();
		addCommandStackListener(stack);
	}

	/**
	 * @see org.eclipse.papyrus.infra.siriusdiag.ui.internal.editor.CustomDocumentStructureTemplateEditor#initAdapterFactory()
	 *
	 */
	@Override
	protected void initAdapterFactory() {
		adapterFactory = createComposedAdapterFactory();
		adapterFactory.addAdapterFactory(new CustomResourceItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new EcoreItemProviderAdapterFactory());
		adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
	}

	/**
	 * @see org.eclipse.ui.part.MultiPageEditorPart#createSite(org.eclipse.ui.IEditorPart)
	 *
	 * @param editor
	 * @return
	 */
	@Override
	protected IEditorSite createSite(IEditorPart editor) {
		// used to be able to have the error editor part nested in the embedded emf editor
		return getEditorSite();
	}

	/**
	 *
	 * Custom ResourceItemProviderAdapterFactory to be able to show only the structure of the DSemanticDiagram
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
			return Collections.singleton(diagram.eResource());
		}
	}

	/**
	 *
	 * Custom ResourceItemProvider to get only the {@link DSemanticDiagram} displayed in the EcoreEditor
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
				return Collections.singletonList(diagram);
			}
			return super.getChildren(object);
		}

	}

}
