/*****************************************************************************
 * Copyright (c) 2013-2015 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (camille.letavernier@cea.fr) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.table.cell;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter;
import org.eclipse.nebula.widgets.nattable.data.validate.DataValidator;
import org.eclipse.nebula.widgets.nattable.data.validate.IDataValidator;
import org.eclipse.nebula.widgets.nattable.edit.editor.ICellEditor;
import org.eclipse.nebula.widgets.nattable.edit.editor.TextCellEditor;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.celleditor.config.AbstractCellAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.manager.table.ITableAxisElementProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.uml.tools.util.MultiplicityParser;
import org.eclipse.papyrus.views.properties.table.Activator;
import org.eclipse.papyrus.views.properties.table.axis.DerivedUMLPropertiesAxisManager;

/**
 * Specific CellEditorConfiguration for UML Multiplicities (And derived UML properties in general)
 *
 * @author Camille Letavernier
 *
 */
public class DerivedUMLPropertiesCellEditorConfiguration extends AbstractCellAxisConfiguration {

	public static final String CONFIGURATION_ID = Activator.PLUGIN_ID + ".multiplicityEditor"; //$NON-NLS-1$

	@Override
	public IDisplayConverter getDisplayConvert(Table table, Object axisElement, ILabelProvider provider) {
		return null;
	}

	@Override
	public ICellPainter getCellPainter(Table table, Object axisElement) {
		return new TextPainter();
	}

	@Override
	public ICellEditor getICellEditor(Table table, Object axisElement, ITableAxisElementProvider elementProvider) {
		return new TextCellEditor();
	}

	@Override
	public String getDisplayMode(Table table, Object axisElement) {
		return DisplayMode.NORMAL;
	}

	@Override
	public IDataValidator getDataValidator(Table table, Object axisElement) {
		return new DataValidator() {

			@Override
			public boolean validate(int columnIndex, int rowIndex, Object newValue) {

				if (!(newValue instanceof String)) {
					return false;
				}

				String multiplicityValue = (String) newValue;
				return MultiplicityParser.isValidMultiplicity(multiplicityValue);
			}
		};
	}

	@Override
	public String getEditorConfigId() {
		return DerivedUMLPropertiesAxisManager.MULTIPLICITY;
	}

	@Override
	public boolean handles(Table table, Object object) {
		return DerivedUMLPropertiesAxisManager.MULTIPLICITY.equals(object);
	}

	@Override
	public String getEditorDescription() {
		return "Specific editor for Multiplicities";
	}

	@Override
	public String getConfigurationId() {
		return CONFIGURATION_ID;
	}

	@Override
	public String getConfigurationDescription() {
		return "Editor for structured UML Multiplicities (e.g. 1..*)";
	}

}
