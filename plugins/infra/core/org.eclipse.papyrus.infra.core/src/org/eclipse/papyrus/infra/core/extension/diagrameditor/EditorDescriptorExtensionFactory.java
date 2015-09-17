/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.extension.diagrameditor;

import static org.eclipse.papyrus.infra.core.Activator.log;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.core.extension.BadNameExtensionException;
import org.eclipse.papyrus.infra.core.extension.ExtensionException;
import org.eclipse.papyrus.infra.core.extension.ExtensionUtils;

/**
 * A factory used to create editor descriptor object from Eclipse extensions points elements.
 *
 * @author Cedric Dumoulin
 * @author Patrick Tessier
 */
public class EditorDescriptorExtensionFactory extends ExtensionUtils {

	/** singleton eINSTANCE of this class */
	public final static EditorDescriptorExtensionFactory eINSTANCE = new EditorDescriptorExtensionFactory();

	/** constant for the editor diagram **/
	public final static String EDITOR_DIAGRAM_EXTENSIONPOINT = "editorDiagram";

	/** constant for the attribute factoryClass **/
	public final static String FACTORYCLASS_ATTRIBUTE = "factoryClass";

	/** constant for the attribute contextId **/
	public final static String ACTIONBARCONTRIBUTORID_ATTRIBUTE = "actionBarContributorId";

	/** constant for the attribute icon **/
	public final static String ICON_ATTRIBUTE = "icon";

	/** constant for the order attribute */
	public final static String ORDER_ATTRIBUTE = "order";

	/**
	 * @return the eINSTANCE
	 */
	public static EditorDescriptorExtensionFactory getInstance() {
		return eINSTANCE;
	}

	/**
	 * Create a descriptor instance corresponding to the ConfigurationElement.
	 *
	 * @param element
	 *            an {@link IConfigurationElement} see eclipse extension point
	 * @return a nestedEditorDescriptor structure that contains information to create diagrams
	 * @throws BadNameExtensionException
	 */
	@SuppressWarnings("unchecked")
	public EditorDescriptor createNestedEditorDescriptor(IConfigurationElement element) throws ExtensionException {
		EditorDescriptor res;

		checkTagName(element, EDITOR_DIAGRAM_EXTENSIONPOINT);

		res = new EditorDescriptor();
		res.setEditorFactoryClass((Class<IPluggableEditorFactory>) parseClass(element, FACTORYCLASS_ATTRIBUTE, EDITOR_DIAGRAM_EXTENSIONPOINT));
		res.setActionBarContributorId(element.getAttribute(ACTIONBARCONTRIBUTORID_ATTRIBUTE));

		int order = 0; // Default
		try {
			String orderAttribute = element.getAttribute(ORDER_ATTRIBUTE);
			if (orderAttribute != null) {
				order = Integer.parseInt(orderAttribute);
			}
		} catch (NumberFormatException ex) {
			Activator.log.warn("Invalid order provided by " + element.getContributor() + ". Order should be an integer value"); //$NON-NLS-1$ //$NON-NLS-2$
		}

		res.setOrder(order);

		String iconPath = element.getAttribute(ICON_ATTRIBUTE);
		if (iconPath != null) {
			/** Implementation which set the icon and register the complete URL of the icon : Bug eclipse 358732 */
			res.setIcon(element, iconPath, org.eclipse.papyrus.infra.core.Activator.PLUGIN_ID);

		}

		if (log.isDebugEnabled()) {
			log.debug("Read editor descriptor " + res); //$NON-NLS-1$
		}
		return res;
	}
}
