/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;
import org.eclipse.uml2.uml.Reception;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Semantic Parser for {@link Reception}
 */
public class ReceptionLabelParser extends NamedElementLabelParser implements IMaskManagedSemanticParser {

	/** The String format for displaying a Property with visibility */
	protected static final String PREFIX = "\u00ABsignal\u00BB ";

	/** The String format for displaying a Property label with its name */
	protected static final String NAME_FORMAT = "%s%s";

	/** The String format for displaying a Property label with its type */
	protected static final String TYPE_FORMAT = "%s: %s";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPrintString(IAdaptable element, int flags) {

		Collection<String> maskValues = getMaskValues(element);

		if (maskValues.isEmpty()) {
			return MaskedLabel;
		}

		String result = PREFIX;
		EObject eObject = EMFHelper.getEObject(element);

		if ((eObject != null) && (eObject instanceof Reception)) {

			Reception reception = (Reception) eObject;

			// manage name
			if (maskValues.contains(ICustomAppearance.DISP_NAME) && reception.isSetName()) {
				String name = UMLLabelInternationalization.getInstance().getLabel(reception);
				result = String.format(NAME_FORMAT, result, name);
			}

			// manage signal
			if (maskValues.contains(ILabelPreferenceConstants.DISP_SIGNAL)) {
				String type = "<Undefined>";
				if (reception.getSignal() != null) {
					type = UMLLabelInternationalization.getInstance().getLabel(reception.getSignal());
				}
				result = String.format(TYPE_FORMAT, result, type);
			}
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAffectingEvent(Object event, int flags) {

		if (event instanceof Notification) {
			Object feature = ((Notification) event).getFeature();
			if (feature instanceof EStructuralFeature) {
				return UMLPackage.eINSTANCE.getReception_Signal().equals(feature) || super.isAffectingEvent(event, flags);
			}
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<EObject> getSemanticElementsBeingParsed(EObject element) {
		List<EObject> semanticElementsBeingParsed = new ArrayList<EObject>();

		if ((element != null) && (element instanceof Reception)) {
			Reception semElement = (Reception) element;

			semanticElementsBeingParsed.add(semElement);
			if (semElement.getSignal() != null) {
				semanticElementsBeingParsed.add(semElement.getSignal());
			}
		}
		return semanticElementsBeingParsed;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> getMasks() {
		Map<String, String> masks = new HashMap<String, String>();
		masks.put(ICustomAppearance.DISP_NAME, "Name");
		masks.put(ILabelPreferenceConstants.DISP_SIGNAL, "Signal");
		return masks;
	}

	@Override
	public Collection<String> getDefaultValue(IAdaptable element) {
		return Arrays.asList(ICustomAppearance.DISP_NAME, ILabelPreferenceConstants.DISP_SIGNAL);
	}
}
