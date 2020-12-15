/*****************************************************************************
 * Copyright (c) 2020 CEA LIST, EclipseSource, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Remi Schnekenburger - Initial API and implementation
 *   Christian W. Damus - bug 569357
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.validation.common.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.AbstractTreeIterator;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.editor.ModelSetServiceFactory;
import org.eclipse.papyrus.infra.core.resource.EditingDomainServiceFactory;
import org.eclipse.papyrus.infra.core.resource.ModelMultiException;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.resource.ModelsReader;
import org.eclipse.papyrus.infra.core.services.ExtensionServicesRegistry;
import org.eclipse.papyrus.infra.core.services.ServiceDescriptor;
import org.eclipse.papyrus.infra.core.services.ServiceDescriptor.ServiceTypeKind;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServiceStartKind;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;
import org.eclipse.papyrus.infra.emf.utils.ResourceUtils;
import org.eclipse.papyrus.infra.tools.util.Iterators2;
import org.eclipse.papyrus.toolsmiths.validation.common.Activator;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

/**
 * Generic mapper for model resources in a resource container.
 *
 * @param <T>
 *            the kind of model object that I extract from EMF resources
 */
public class ModelResourceMapper<T extends EObject> {

	private final IContainer container;

	/**
	 * Initializes me with the {@code container} for which I map resources.
	 */
	public ModelResourceMapper(IContainer container) {
		super();

		this.container = container;
	}

	/**
	 * Map resources to be validated in my project.
	 *
	 * @param <R>
	 *            the kind of resource set to use
	 * @param filePredicate
	 *            selects the files in the project to validate
	 * @param resourceSetFunction
	 *            creates the resource set in which to load EMF resources
	 * @param modelExtractor
	 *            extracts objects to be validated from EMF resources
	 *
	 * @return the mapping of model objects to validate, by file
	 */
	public <R extends ResourceSet> ListMultimap<IFile, T> map(Predicate<? super IFile> filePredicate, Function<? super URI, ? extends R> resourceSetFunction, Function<? super R, ? extends Stream<? extends T>> modelExtractor) {
		ModelVisitor<R> visitor = new ModelVisitor<>(filePredicate, resourceSetFunction, modelExtractor);

		try {
			container.accept(visitor, IResource.DEPTH_INFINITE, true);
		} catch (CoreException e) {
			Activator.log.error(e);
		}

		return visitor.getMap();
	}

	private final class ModelVisitor<R extends ResourceSet> implements IResourceVisitor {
		private final Predicate<? super IFile> filePredicate;
		private final Function<? super URI, ? extends R> resourceSetFunction;
		private final Function<? super R, ? extends Stream<? extends T>> modelExtractor;

		private final ListMultimap<IFile, T> map = ArrayListMultimap.create();

		ModelVisitor(Predicate<? super IFile> filePredicate, Function<? super URI, ? extends R> resourceSetFunction, Function<? super R, ? extends Stream<? extends T>> modelExtractor) {
			super();

			this.filePredicate = filePredicate;
			this.resourceSetFunction = resourceSetFunction;
			this.modelExtractor = modelExtractor;
		}

		@Override
		public boolean visit(IResource resource) throws CoreException {
			if ((resource.getType() == IResource.FILE) && filePredicate.test((IFile) resource)) {
				IFile file = (IFile) resource;

				URI uri = URI.createPlatformResourceURI(file.getFullPath().toString(), true);
				R resourceSet = resourceSetFunction.apply(uri);
				if (resourceSet != null) {
					try {
						resourceSet.getResource(uri, true);
					} catch (Exception e) {
						throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to load file " + file.getFullPath(), e)); //$NON-NLS-1$
					}
					modelExtractor.apply(resourceSet).forEach(model -> map.put(file, model));
				}
			}

			return true;
		}

		ListMultimap<IFile, T> getMap() {
			return map;
		}

	}

	/**
	 * Select files to validate by extension.
	 *
	 * @param extension
	 *            the file extension to match
	 */
	public static Predicate<IResource> byExtension(String extension) {
		return file -> //
		extension.equals(file.getFileExtension());
	}

	/**
	 * Create standard EMF resource sets with platform scheme support for cross-document references.
	 */
	public static Function<URI, ResourceSet> resourceSets() {
		return ModelResourceMapper::createResourceSet;
	}

	private static ResourceSet createResourceSet(URI uri) {
		ResourceSet result = new ResourceSetImpl();

		// Ensure that cross-doc references saved with the platform-scheme-aware URI handler can resolve
		// platform:/resource URIs to bundles in the target platform.
		result.setURIConverter(ResourceUtils.createWorkspaceAwareURIConverter());

		result.getResource(uri, true);

		return result;
	}

	/**
	 * Create Papyrus Model Sets with platform scheme support for cross-document references.
	 */
	public static Function<URI, ModelSet> modelSets() {
		return ModelResourceMapper::createModelSet;
	}

	private static ModelSet createModelSet(URI uri) {
		ServicesRegistry service = null;

		try {
			service = new ExtensionServicesRegistry();
		} catch (ServiceException e) {
			Activator.log.error(e);
			return null;
		}

		// Override service factory for Model Set
		ServiceDescriptor descriptor = new ServiceDescriptor(ModelSet.class, ModelSetServiceFactory.class.getName(),
				ServiceStartKind.STARTUP, 10);
		descriptor.setServiceTypeKind(ServiceTypeKind.serviceFactory);
		service.add(descriptor);

		// Override factory for editing domain
		descriptor = new ServiceDescriptor(TransactionalEditingDomain.class,
				EditingDomainServiceFactory.class.getName(), ServiceStartKind.STARTUP, 10,
				Collections.singletonList(ModelSet.class.getName()));
		descriptor.setServiceTypeKind(ServiceTypeKind.serviceFactory);
		service.add(descriptor);

		try {
			service.startServicesByClassKeys(ModelSet.class, TransactionalEditingDomain.class);
		} catch (ServiceException e) {
			Activator.log.error(e);
		}

		ModelSet modelSet = null;
		try {
			modelSet = ServiceUtils.getInstance().getModelSet(service);
		} catch (ServiceException e) {
			// Ignore service exception
		}

		// Instantiate a Model set
		if (modelSet == null) {
			modelSet = new ModelSet();
			try {
				ModelSetServiceFactory.setServiceRegistry(modelSet, service);
			} catch (ServiceException e) {
				// Ignore service exception
			}
		}

		// Ensure that cross-doc references saved with the platform-scheme-aware URI handler can resolve
		// platform:/resource URIs to bundles in the target platform.
		modelSet.setURIConverter(ResourceUtils.createWorkspaceAwareURIConverter());
		;

		// Read all Model from selected file
		ModelsReader modelsReader = new ModelsReader();
		modelsReader.readModel(modelSet);
		try {
			modelSet.loadModels(uri);
		} catch (ModelMultiException e) {
			Activator.log.error(e);
		}
		// Initialize an editing domain
		modelSet.getTransactionalEditingDomain();
		return modelSet;
	}

	/**
	 * Extract root objects of the given {@code type} from EMF resources.
	 */
	public static <T extends EObject> Function<ResourceSet, Stream<T>> rootsOfType(Class<? extends T> type) {
		return resourceSet -> Iterators2.stream(Iterators2.filter(roots(resourceSet), type));
	}

	public static <T extends EObject> Function<ResourceSet, Stream<T>> allElementsOfType(Class<? extends T> type) {
		return resourceSet -> Iterators2.stream(Iterators2.filter(allElements(resourceSet), type));
	}


	@SuppressWarnings("serial")
	private static TreeIterator<?> roots(ResourceSet resourceSet) {
		return new AbstractTreeIterator<>(resourceSet.getResources(), false) {
			@Override
			protected Iterator<?> getChildren(Object object) {
				return object instanceof Resource
						? ((Resource) object).getContents().iterator()
						: object instanceof Collection<?>
								? ((Collection<?>) object).iterator()
								: Collections.emptyIterator();
			}
		};
	}

	@SuppressWarnings("serial")
	private static TreeIterator<?> allElements(ResourceSet resourceSet) {
		return new AbstractTreeIterator<>(resourceSet.getResources(), false) {
			@Override
			protected Iterator<?> getChildren(Object object) {
				return object instanceof Resource
						? ((Resource) object).getAllContents()
						: object instanceof Collection<?>
								? ((Collection<?>) object).iterator()
								: Collections.emptyIterator();
			}
		};
	}


}
