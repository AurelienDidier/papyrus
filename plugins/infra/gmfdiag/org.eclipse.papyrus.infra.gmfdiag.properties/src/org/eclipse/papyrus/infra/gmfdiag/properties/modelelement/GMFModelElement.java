/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.properties.modelelement;

import java.util.List;

import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.FeaturePath;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.NamedStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.Style;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.providers.EMFContentProvider;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramUtils;
import org.eclipse.papyrus.infra.gmfdiag.properties.Activator;
import org.eclipse.papyrus.infra.gmfdiag.properties.databinding.GradientObservableValue;
import org.eclipse.papyrus.infra.gmfdiag.properties.provider.ModelContentProvider;
import org.eclipse.papyrus.infra.gmfdiag.properties.util.LegacyOwnerObservable;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.infra.viewpoints.style.StylePackage;
import org.eclipse.papyrus.infra.widgets.providers.EmptyContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;
import org.eclipse.papyrus.uml.tools.databinding.PapyrusObservableList;
import org.eclipse.papyrus.uml.tools.databinding.PapyrusObservableValue;
import org.eclipse.papyrus.views.properties.modelelement.EMFModelElement;
import org.eclipse.swt.graphics.Image;

/**
 * A ModelElement to represent an element of the GMF Notation metamodel
 *
 * @author Camille Letavernier
 *
 */
public class GMFModelElement extends EMFModelElement {

	/**
	 *
	 * Constructor.
	 *
	 * @param source
	 *            The source element (from the Notation metamodel)
	 */
	public GMFModelElement(EObject source) {
		super(source);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param source
	 *            The source element (from the Notation metamodel)
	 * @param domain
	 *            The editing domain on which the commands will be executed.
	 *            May be null
	 */
	public GMFModelElement(EObject source, EditingDomain domain) {
		super(source, domain);
	}

	@Override
	protected boolean isFeatureEditable(String propertyPath) {
		if (propertyPath.endsWith("owner")) {
			return true;
		}

		if (super.isFeatureEditable(propertyPath)) {
			return true;
		}

		return getStyleFor(propertyPath) != null;
	}

	@Override
	protected IObservable doGetObservable(String propertyPath) {
		if (propertyPath.endsWith("owner")) {
			Diagram diagram = (Diagram) source;
			Style style = diagram.getStyle(StylePackage.Literals.PAPYRUS_VIEW_STYLE);
			if (style != null) {
				return new PapyrusObservableValue(style, StylePackage.Literals.PAPYRUS_VIEW_STYLE__OWNER, domain);
			}
			return new LegacyOwnerObservable(diagram, StylePackage.Literals.PAPYRUS_VIEW_STYLE__OWNER, domain);
		} else if (propertyPath.endsWith("prototype")) {
			Diagram diagram = (Diagram) source;
			Style style = diagram.getStyle(StylePackage.Literals.PAPYRUS_VIEW_STYLE);
			if (style != null) {
				return new PapyrusObservableValue(style, StylePackage.Literals.PAPYRUS_VIEW_STYLE__CONFIGURATION, domain);
			}
			return new LegacyOwnerObservable(diagram, StylePackage.Literals.PAPYRUS_VIEW_STYLE__CONFIGURATION, domain);
		}

		FeaturePath featurePath = getFeaturePath(propertyPath);
		EStructuralFeature feature = getFeature(propertyPath);

		if (feature == null) {
			return findStyleObservable(propertyPath);
		}

		if (feature.getEType() == NotationPackage.eINSTANCE.getGradientData()) {
			return new GradientObservableValue(source, feature, domain);
		}

		if (feature.getUpperBound() != 1) {
			IObservableList list = domain == null ? EMFProperties.list(featurePath).observe(source) : new PapyrusObservableList(EMFProperties.list(featurePath).observe(source), domain, getSource(featurePath), feature);
			return list;
		}

		IObservableValue value = domain == null ? EMFProperties.value(featurePath).observe(source) : new PapyrusObservableValue(getSource(featurePath), feature, domain);
		return value;
	}

	protected Style getStyleFor(String propertyPath) {
		if (!(source instanceof View)) {
			return null;
		}

		View view = (View) source;

		for (Style style : (List<Style>) view.getStyles()) {
			// NamedStyles are application-specific and should be handled separately
			if (style instanceof NamedStyle) {
				continue;
			}

			EStructuralFeature styleFeature = style.eClass().getEStructuralFeature(propertyPath);
			if (styleFeature != null) {
				return style;
			}
		}

		return null;
	}

	/**
	 * Fallback: if the object doesn't have the required property, search for its owned styles
	 *
	 * @param propertyPath
	 * @return
	 */
	protected IObservable findStyleObservable(String propertyPath) {
		Style style = getStyleFor(propertyPath);
		if (style == null) {
			return null;
		}

		EStructuralFeature styleFeature = style.eClass().getEStructuralFeature(propertyPath);
		if (styleFeature == null) {
			return null; // Shouldn't happen, since this is already checked by getStyleFor()
		}

		if (styleFeature.getUpperBound() != 1) {
			IObservableList list = domain == null ? EMFProperties.list(styleFeature).observe(source) : new PapyrusObservableList(EMFProperties.list(styleFeature).observe(source), domain, style, styleFeature);
			return list;
		}

		IObservableValue value = domain == null ? EMFProperties.value(styleFeature).observe(source) : new PapyrusObservableValue(style, styleFeature, domain);
		return value;
	}

	@Override
	public ILabelProvider getLabelProvider(String propertyPath) {
		if (propertyPath.endsWith("prototype")) {
			return new ILabelProvider() {

				public void addListener(ILabelProviderListener listener) {
				}

				public void removeListener(ILabelProviderListener listener) {
				}

				public void dispose() {
				}

				public boolean isLabelProperty(Object element, String property) {
					return false;
				}

				public Image getImage(Object element) {
					ViewPrototype proto = DiagramUtils.getPrototype((Diagram) source);
					return proto.getIcon();
				}

				public String getText(Object element) {
					ViewPrototype proto = DiagramUtils.getPrototype((Diagram) source);
					return proto.getQualifiedName();
				}
			};
		}
		try {
			return ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, source).getLabelProvider();
		} catch (ServiceException ex) {
			Activator.log.error(ex);
			return new LabelProvider();
		}
	}

	/**
	 * @see org.eclipse.papyrus.views.properties.modelelement.EMFModelElement#getContentProvider(java.lang.String)
	 */
	@Override
	public IStaticContentProvider getContentProvider(String propertyPath) {
		if ("element".equals(propertyPath)) {
			if (source instanceof Diagram) {
				Diagram diagram = (Diagram) source;
				return new ModelContentProvider(diagram, getRoot(diagram.getElement())) {

					@Override
					protected boolean isValid(EObject selection, Diagram diagram, ViewPrototype prototype) {
						return PolicyChecker.getCurrent().canHaveNewView(selection, DiagramUtils.getOwner(diagram), prototype);
					}
				};
			} else {
				return EmptyContentProvider.instance;
			}
		} else if ("owner".equals(propertyPath)) {
			Diagram diagram = (Diagram) source;
			return new ModelContentProvider(diagram, getRoot(diagram.getElement())) {

				@Override
				protected boolean isValid(EObject selection, Diagram diagram, ViewPrototype prototype) {
					return (PolicyChecker.getCurrent().getOwningRuleFor(prototype, selection) != null);
				}
			};
		}


		IStaticContentProvider provider = super.getContentProvider(propertyPath);
		if (provider != null && !(provider instanceof EmptyContentProvider)) {
			return provider;
		}

		Style style = getStyleFor(propertyPath);
		if (style == null) {
			return null;
		}

		EStructuralFeature feature = style.eClass().getEStructuralFeature(propertyPath);
		if (feature == null) {
			return null; // Shouldn't happen, since getStyleFor() already checks that
		}

		return new EMFContentProvider(style, style.eClass().getEStructuralFeature(propertyPath));
	}

	/**
	 * Gets the root EObject from the given one
	 *
	 * @param obj
	 *            An object
	 * @return The root object which is an ancestor of the given one
	 */
	private EObject getRoot(EObject obj) {
		EObject current = obj;
		EObject parent = obj.eContainer();
		while (parent != null) {
			current = parent;
			parent = parent.eContainer();
		}
		return current;
	}
}
