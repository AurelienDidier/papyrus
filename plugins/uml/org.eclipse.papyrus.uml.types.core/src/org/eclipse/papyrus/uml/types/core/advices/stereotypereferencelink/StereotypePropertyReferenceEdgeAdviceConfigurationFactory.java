/*****************************************************************************
 * Copyright (c) 2017 CEA LIST, ALL4TEC and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Mickaël ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.types.core.advices.stereotypereferencelink;

import org.eclipse.gmf.runtime.emf.type.core.edithelper.IEditHelperAdvice;
import org.eclipse.papyrus.infra.types.AbstractAdviceBindingConfiguration;
import org.eclipse.papyrus.infra.types.core.factories.impl.AbstractAdviceBindingFactory;
import org.eclipse.papyrus.infra.types.core.impl.NullEditHelperAdvice;
import org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceConfiguration;
import org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceFactory;


/**
 * A factory for creating {@link StereotypePropertyReferenceEdgeAdviceConfiguration} objects.
 * 
 * @since 3.1
 */
public class StereotypePropertyReferenceEdgeAdviceConfigurationFactory extends AbstractAdviceBindingFactory<AbstractAdviceBindingConfiguration> {


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.types.core.factories.impl.AbstractAdviceFactory#getEditHelperAdvice(org.eclipse.papyrus.infra.types.AdviceConfiguration)
	 */
	@Override
	protected IEditHelperAdvice getEditHelperAdvice(final AbstractAdviceBindingConfiguration adviceConfiguration) {
		if (adviceConfiguration instanceof StereotypePropertyReferenceEdgeAdviceConfiguration) {
			IEditHelperAdvice editHelperAdvice = new StereotypePropertyReferenceEdgeAdviceEditHelperAdvice((StereotypePropertyReferenceEdgeAdviceConfiguration) adviceConfiguration);
			return editHelperAdvice;
		}
		return NullEditHelperAdvice.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.types.core.factories.impl.AbstractAdviceBindingFactory#createAdviceBindingConfiguration()
	 */
	@Override
	public AbstractAdviceBindingConfiguration createAdviceBindingConfiguration() {
		return StereotypePropertyReferenceEdgeAdviceFactory.eINSTANCE.createStereotypePropertyReferenceEdgeAdviceConfiguration();
	}

}
