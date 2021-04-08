/*****************************************************************************
 * Copyright (c) 2019 CEA LIST.
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
 *****************************************************************************/

package org.eclipse.papyrus.infra.siriusdiag.ui.modelresource;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.infra.core.resource.AbstractDynamicModel;
import org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.DocumentTemplate;
import org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.utils.DocumentStructureTemplateConstants;



/**
 * This class manages PapyrusDocument in notation model resource.
 *
 *
 */
public class SiriusDiagramModel extends AbstractDynamicModel<DocumentTemplate> {

	/**
	 * Document Model ID.
	 */
	public static final String DOCUMENT_MODEL_ID = "org.eclipse.papyrus.infra.siriusdiag.ui.DocumentTemplate"; //$NON-NLS-1$

	/**
	 * the file extension where document are stored.
	 */
	public static final String DOCUMENT_MODEL_FILE_EXTENSION = DocumentStructureTemplateConstants.DOCUMENT_STRUCTURE_TEMPLATE_FILE_EXTENSION; // $NON-NLS-1$

	/**
	 *
	 * Constructor.
	 *
	 */
	public SiriusDiagramModel() {
		super();
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.resource.AbstractBaseModel#getModelFileExtension()
	 *
	 * @return
	 */
	@Override
	protected String getModelFileExtension() {
		return DOCUMENT_MODEL_FILE_EXTENSION;
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.resource.AbstractModel#getIdentifier()
	 *
	 * @return
	 */
	@Override
	public String getIdentifier() {
		return DOCUMENT_MODEL_ID;
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.resource.AbstractBaseModel#loadModel(org.eclipse.emf.common.util.URI)
	 *
	 * @param uriWithoutExtension
	 */
	@Override
	public void loadModel(URI uriWithoutExtension) {
		// It is a common use case that this resource does not (and will not)
		// exist
		if (exists(uriWithoutExtension)) {
			try {
				super.loadModel(uriWithoutExtension);
			} catch (Exception ex) {
				createModel(uriWithoutExtension);
			}
		}

		if (resource == null) {
			createModel(uriWithoutExtension);
		}
	}

	/**
	 * Add a new initialized document to the pdst model.
	 *
	 * @param document
	 * @param context
	 *            we need the context to be able to calculate the resource name were the DocumentTemplate will be saved.
	 *            because this value is maybe not yet set to {@link DocumentTemplate#setSemanticContext(EObject)}
	 */
	public void addDocument(final DocumentTemplate document, final EObject context) {
		if (context != null) { // we check the resource for control mode feature
			Resource targetResource;
			Resource contextResource = context.eResource();
			if (!contextResource.getURI().trimFileExtension().equals(getResource().getURI().trimFileExtension())) {
				URI uri = contextResource.getURI();
				uri = uri.trimFileExtension();
				uri = uri.appendFileExtension(getModelFileExtension());
				ResourceSet set = contextResource.getResourceSet();
				targetResource = set.getResource(uri, true);
			} else {
				targetResource = getResource();
			}
			if (targetResource != null) {
				targetResource.getContents().add(document);
			}
		}
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.core.resource.IEMFModel#canPersist(org.eclipse.emf.ecore.EObject)
	 *
	 * @param object
	 * @return
	 */
	@Override
	public boolean canPersist(EObject object) {
		return (getResource() != null) && isSupportedRoot(object);
	}

	/**
	 *
	 * @param object
	 * @return
	 */
	protected boolean isSupportedRoot(EObject object) {
		return object instanceof DocumentTemplate;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.core.resource.IEMFModel#persist(org.eclipse.emf.ecore.EObject)
	 *
	 * @param object
	 */
	@Override
	public void persist(EObject object) {
		if (!canPersist(object)) {
			throw new IllegalArgumentException("cannot persist " + object); //$NON-NLS-1$
		}

		getResource().getContents().add(object);
	}

	/**
	 * @see org.eclipse.papyrus.infra.core.resource.AbstractBaseModel#configureResource(org.eclipse.emf.ecore.resource.Resource)
	 *
	 * @param resource
	 */
	@Override
	protected void configureResource(Resource resource) {
		super.configureResource(resource);
	}
}
