/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.rulebased.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.core.impl.ConfiguredHintedSpecializationElementType;
import org.eclipse.papyrus.infra.types.rulebased.Activator;
import org.eclipse.papyrus.infra.types.rulebased.AndRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.CompositeRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.NotRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.OrRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.RuleBasedTypeConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.RuleConfiguration;


public class DefaultRuleEditHelperAdvice extends AbstractEditHelperAdvice {


	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		List<ConfiguredHintedSpecializationElementType> types = getTypes(request);

		// Must approve from the whole hierarchy
		for (ConfiguredHintedSpecializationElementType configuredHintedSpecializationElementType : types) {
			if (!approveRequest(configuredHintedSpecializationElementType, request)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @param request
	 * 
	 */
	protected List<ConfiguredHintedSpecializationElementType> getTypes(IEditCommandRequest request) {
		List<ConfiguredHintedSpecializationElementType> result = new ArrayList<ConfiguredHintedSpecializationElementType>();
		if (request instanceof CreateElementRequest) {
			IElementType typeToCreate = ((CreateElementRequest) request).getElementType();
			List<ConfiguredHintedSpecializationElementType> superConfiguredTypes = getAllTypes(typeToCreate);
			result.addAll(superConfiguredTypes);
		} else if (request instanceof SetRequest) {
			// check the feature to set is a containment feature and element to move is an extended element type
			EStructuralFeature feature = ((SetRequest) request).getFeature();
			if (feature instanceof EReference) {
				if (((EReference) feature).isContainment()) {

					// containment. Check the kind of element to edit
					Object value = ((SetRequest) request).getValue();
					List<Object> values = new ArrayList<Object>();
					// value = single object or list ?
					if (value instanceof EObject) {
						values.add(value);
					} else if (value instanceof List) {
						values.addAll((List<Object>) value);
					}

					for (Object object : values) {
						if (object instanceof EObject) {
							IElementType[] types = ElementTypeRegistry.getInstance().getAllTypesMatching((EObject) object, request.getClientContext());
							for (IElementType type : types) {
								if (type instanceof ConfiguredHintedSpecializationElementType) {
									if (((ConfiguredHintedSpecializationElementType) type).getConfiguration() instanceof RuleBasedTypeConfiguration) {
										result.add((ConfiguredHintedSpecializationElementType) type);

										List<ConfiguredHintedSpecializationElementType> superConfiguredTypes = getAllSuperConfiguredTypes((ConfiguredHintedSpecializationElementType) type);
										result.addAll(superConfiguredTypes);
									}
								}
							}
						}
					}
				}
			}
		} else if (request instanceof MoveRequest) {
			// check the feature to set is a containment feature and element to move is an extended element type
			Map<EObject, EReference> objectsToMove = ((MoveRequest) request).getElementsToMove();
			for (Entry<EObject, EReference> movedElement : objectsToMove.entrySet()) {
				// do not compute with reference, this can be null. This could be interesting to check...
				IElementType[] types = ElementTypeRegistry.getInstance().getAllTypesMatching(movedElement.getKey(), request.getClientContext());
				for (IElementType type : types) {
					if (type instanceof ConfiguredHintedSpecializationElementType) {
						if (((ConfiguredHintedSpecializationElementType) type).getConfiguration() instanceof RuleBasedTypeConfiguration) {
							result.add((ConfiguredHintedSpecializationElementType) type);

							List<ConfiguredHintedSpecializationElementType> superConfiguredTypes = getAllSuperConfiguredTypes((ConfiguredHintedSpecializationElementType) type);
							result.addAll(superConfiguredTypes);
						}
					}
				}
			}
		}

		return result;

	}

	/**
	 * Returns the list of types (this one and supers) that are configuredTypes.
	 * 
	 * @param type
	 *            the type from which all s are retrieved
	 * @return the list of types in the hierarchy of specified type, including type itself if matching. Returns an empty list if none is matching
	 */
	protected List<ConfiguredHintedSpecializationElementType> getAllTypes(IElementType type) {
		List<ConfiguredHintedSpecializationElementType> result = new ArrayList<ConfiguredHintedSpecializationElementType>();

		if (!(type instanceof ConfiguredHintedSpecializationElementType)) {
			// no need to take care of metamodel types yet
			return result;
		}

		if (((ConfiguredHintedSpecializationElementType) type).getConfiguration() instanceof RuleBasedTypeConfiguration) {
			result.add((ConfiguredHintedSpecializationElementType) type);
		}

		IElementType[] superTypes = type.getAllSuperTypes();
		if (superTypes.length == 0) {
			return result;
		}
		// get the reverse order
		for (int i = superTypes.length - 1; i >= 0; i--) {
			if (superTypes[i] instanceof ConfiguredHintedSpecializationElementType) {
				if (((ConfiguredHintedSpecializationElementType) superTypes[i]).getConfiguration() instanceof RuleBasedTypeConfiguration) {
					result.add((ConfiguredHintedSpecializationElementType) superTypes[i]);
				}
			}
		}

		return result;
	}

	protected List<ConfiguredHintedSpecializationElementType> getAllSuperConfiguredTypes(ConfiguredHintedSpecializationElementType type) {
		IElementType[] superTypes = type.getAllSuperTypes();
		if (superTypes.length == 0) {
			return Collections.emptyList();
		}
		List<ConfiguredHintedSpecializationElementType> superElementTypes = new ArrayList<ConfiguredHintedSpecializationElementType>();
		// get the reverse order
		for (int i = superTypes.length - 1; i >= 0; i--) {
			if (superTypes[i] instanceof ConfiguredHintedSpecializationElementType) {
				if (((ConfiguredHintedSpecializationElementType) superTypes[i]).getConfiguration() instanceof RuleBasedTypeConfiguration) {
					superElementTypes.add((ConfiguredHintedSpecializationElementType) superTypes[i]);
				}
			}
		}
		return superElementTypes;
	}


	protected boolean processCompositeRule(CompositeRuleConfiguration compositeRule, IEditCommandRequest request) {
		Iterator<RuleConfiguration> iterator = compositeRule.getComposedRules().iterator();
		RuleConfiguration nextComposedRuleConfiguration = iterator.next();
		boolean result = processRule(nextComposedRuleConfiguration, request);

		while (iterator.hasNext()) {
			nextComposedRuleConfiguration = iterator.next();

			boolean resultNextComposedRuleConfiguration = processRule(nextComposedRuleConfiguration, request);

			if (compositeRule instanceof OrRuleConfiguration) {
				if (result == false && resultNextComposedRuleConfiguration) {
					result = true;
				}
			} else if (compositeRule instanceof AndRuleConfiguration) {
				if (result == true && !resultNextComposedRuleConfiguration) {
					result = false;
				}
			}
		}

		return result;
	}

	protected boolean processRule(RuleConfiguration ruleConfiguration, IEditCommandRequest request) {
		if (ruleConfiguration instanceof CompositeRuleConfiguration) {
			return processCompositeRule((CompositeRuleConfiguration) ruleConfiguration, request);
		} else if (ruleConfiguration instanceof NotRuleConfiguration) {
			RuleConfiguration composedRule = ((NotRuleConfiguration) ruleConfiguration).getComposedRule();
			return !processRule(composedRule, request);
		} else {
			return RuleConfigurationTypeRegistry.getInstance().getRule(ruleConfiguration).approveRequest(request);
		}
	}

	protected boolean approveRequest(ConfiguredHintedSpecializationElementType elementType, IEditCommandRequest request) {

		ElementTypeConfiguration configuration = ((ConfiguredHintedSpecializationElementType) elementType).getConfiguration();
		if (configuration instanceof RuleBasedTypeConfiguration) {
			RuleConfiguration ruleConfiguration = ((RuleBasedTypeConfiguration) configuration).getRuleConfiguration();

			return processRule(ruleConfiguration, request);
		} else {
			Activator.log.warn("Expected RuleConfiguration as configuration type for : " + elementType);
		}

		return true;
	}



}
