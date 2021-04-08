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

package org.eclipse.papyrus.infra.siriusdiag.ui.internal.adapter;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IOpenable;
import org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IOpenableWithContainer;
import org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.DocumentTemplate;

/**
 * Adapter factory converting Document to IOpenable.
 *
 */
public class SiriusDiagramViewAdapterFactory implements IAdapterFactory {

	/**
	 *
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapter(java.lang.Object, java.lang.Class)
	 *
	 * @param adaptableObject
	 * @param adapterType
	 * @return
	 */
	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (adapterType == IOpenable.class) {
			if (adaptableObject instanceof DocumentTemplate) {
				DocumentTemplate document = (DocumentTemplate) adaptableObject;
				return new IOpenableWithContainer.Openable(adaptableObject, document.getSemanticContext());
			}
		}

		return null;
	}

	/**
	 *
	 * @see org.eclipse.core.runtime.IAdapterFactory#getAdapterList()
	 *
	 * @return
	 */
	@Override
	public Class<?>[] getAdapterList() {
		return new Class[] { IOpenable.class };
	}

}
