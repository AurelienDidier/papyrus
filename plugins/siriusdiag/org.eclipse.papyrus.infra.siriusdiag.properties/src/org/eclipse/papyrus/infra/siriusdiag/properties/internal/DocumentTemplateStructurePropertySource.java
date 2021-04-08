/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.siriusdiag.properties.internal;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.ui.provider.PropertySource;
import org.eclipse.papyrus.infra.siriusdiag.properties.descriptors.CustomPropertyDescriptor;
import org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.DocumentStructureTemplatePackage;
import org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.edit.editors.factories.EAttributeTemplateEAttributeEditorFactory;
import org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.edit.editors.factories.EClassTemplateEClassEditorFactory;
import org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.edit.editors.factories.EReferenceTemplateEReferenceEditorFactory;
import org.eclipse.papyrus.model2doc.emf.documentstructuretemplate.edit.editors.factories.EStructuralFeatureTemplateEStructuralFeatureEditorFactory;
import org.eclipse.ui.views.properties.IPropertyDescriptor;

/**
 * The {@link PropertySource} used to provide a better edition way for some properties of the DocumentStructureTemplate metamodel
 */
public class DocumentTemplateStructurePropertySource extends PropertySource {

	/**
	 * Constructor.
	 *
	 * @param object
	 * @param itemPropertySource
	 */
	public DocumentTemplateStructurePropertySource(final Object object, final IItemPropertySource itemPropertySource) {
		super(object, itemPropertySource);
	}

	/**
	 * @see org.eclipse.emf.edit.ui.provider.PropertySource#createPropertyDescriptor(org.eclipse.emf.edit.provider.IItemPropertyDescriptor)
	 *
	 * @param itemPropertyDescriptor
	 * @return
	 */
	@Override
	protected IPropertyDescriptor createPropertyDescriptor(final IItemPropertyDescriptor itemPropertyDescriptor) {
		final EStructuralFeature f = (EStructuralFeature) itemPropertyDescriptor.getFeature(this.object);
		if (f == DocumentStructureTemplatePackage.eINSTANCE.getEReferenceTemplate_EReference()) {
			return new CustomPropertyDescriptor(this.object, itemPropertyDescriptor, new EReferenceTemplateEReferenceEditorFactory());
		}
		if (f == DocumentStructureTemplatePackageDi.eINSTANCE.getEClassTemplate_EClass()) {
			return new CustomPropertyDescriptor(this.object, itemPropertyDescriptor, new EClassTemplateEClassEditorFactory());
		}
		if (f == DocumentStructureTemplatePackage.eINSTANCE.getEStructuralFeatureTemplate_Feature()) {
			return new CustomPropertyDescriptor(this.object, itemPropertyDescriptor, new EStructuralFeatureTemplateEStructuralFeatureEditorFactory());
		}
		if (f == DocumentStructureTemplatePackage.eINSTANCE.getEAttributeTemplate_EAttribute()) {
			return new CustomPropertyDescriptor(this.object, itemPropertyDescriptor, new EAttributeTemplateEAttributeEditorFactory());
		}
		return super.createPropertyDescriptor(itemPropertyDescriptor);
	}
}
