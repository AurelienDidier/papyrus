/*****************************************************************************
 * Copyright (c) 2012, 2014 CEA LIST and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 402525
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.nattable.dataprovider;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.nebula.widgets.nattable.edit.editor.IComboBoxDataProvider;
import org.eclipse.papyrus.infra.nattable.manager.table.ITableAxisElementProvider;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.uml.nattable.utils.UMLTableUtils;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 *
 * @author Vincent Lorenzo
 *         This class provides the possibles enumeration literal for properties of stereotype typed with a UMLEnumerationLiteral
 */
public class UMLStereotypeSingleEnumerationComboBoxDataProvider implements IComboBoxDataProvider {

	/**
	 * The table axis element provider
	 */
	private ITableAxisElementProvider elementProvider;

	/**
	 * the obejct represented by the axis
	 */
	private Object axisElement;

	/**
	 *
	 * Constructor.
	 *
	 * @param axisElement
	 *            the object represented by the axis
	 * @param elementProvider
	 *            The table axis element provider
	 */
	public UMLStereotypeSingleEnumerationComboBoxDataProvider(final Object axisElement, final ITableAxisElementProvider elementProvider) {
		this.axisElement = AxisUtils.getRepresentedElement(axisElement);
		this.elementProvider = elementProvider;
	}

	/**
	 *
	 * @see org.eclipse.nebula.widgets.nattable.edit.editor.IComboBoxDataProvider#getValues(int, int)
	 *
	 * @param columnIndex
	 * @param rowIndex
	 *
	 * @return
	 *         the list of the available enumeration literal
	 */
	@Override
	public List<?> getValues(int columnIndex, int rowIndex) {
		final List<Object> literals = new ArrayList<Object>();
		Object el = this.elementProvider.getColumnElement(columnIndex);
		Object rowElement = this.elementProvider.getRowElement(rowIndex);
		el = AxisUtils.getRepresentedElement(el);
		rowElement = AxisUtils.getRepresentedElement(rowElement);
		Element modelElement = null;
		if (rowElement instanceof Element && el == this.axisElement) {
			modelElement = (Element) rowElement;
		} else if (rowElement == this.axisElement && el instanceof Element) {
			modelElement = (Element) el;
		}
		if (modelElement != null) {
			final String id = AxisUtils.getPropertyId(this.axisElement);
			final Property property = UMLTableUtils.getRealStereotypeProperty(modelElement, id);
			final List<Stereotype> ste = UMLTableUtils.getApplicableStereotypesWithThisProperty(modelElement, id);
			if (ste.size() == 1) {
				final Stereotype current = ste.get(0);
				EEnum eenum = (EEnum) current.getProfile().getDefinition(property.getType());
				if (eenum == null) {
					// the enum is declared in an other file (external library)
					EClass stereotypeDef = (EClass) current.getProfile().getDefinition(current);
					List<EAttribute> attributes = stereotypeDef.getEAllAttributes();
					int i = 0;
					while (eenum == null && i < attributes.size()) {
						EAttribute attr = attributes.get(i);
						if (property.getName().equals(attr.getName())) {
							EClassifier tmp = attr.getEType();
							if (tmp instanceof EEnum) {
								eenum = (EEnum) tmp;
							}
						}
						i++;
					}
				}
				if (eenum != null) {
					for (final EEnumLiteral instances : eenum.getELiterals()) {
						literals.add(instances.getInstance());
					}
				}
			}
		}
		return literals;
	}

}
