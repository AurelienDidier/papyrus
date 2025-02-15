/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Maged Elaasar - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.architecture.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;

/**
 * Represents a descriptor for properties of type EPackage.
 * This class lists the currently loaded ECore metamodels.
 *
 * @author Laurent Wouters
 */
public class EPackagePropertyDescriptor extends SurrogateItemPropertyDescriptor {
	public EPackagePropertyDescriptor(IItemPropertyDescriptor inner) {
		super(inner);
	}

	@Override
	public Collection<?> getChoiceOfValues(Object object) {
		EPackage.Registry reg = EPackage.Registry.INSTANCE;
		Collection<EPackage> result = new ArrayList<EPackage>(reg.size());
			Collection<String> keys = new ArrayList<String>(reg.keySet());
			for (String key : keys) {
				try {
					EPackage pack = reg.getEPackage(key);
					if (!result.contains(pack)) {
						result.add(reg.getEPackage(key));
					}
				} catch (Throwable e) {
					continue;
				}
			}
		return result;
	}

	@Override
	public IItemLabelProvider getLabelProvider(Object object) {
		return new IItemLabelProvider() {
			public String getText(Object object) {
				return ((EPackage) object).getNsURI();
			}

			public Object getImage(Object object) {
				return null;
			}
		};
	}
}
