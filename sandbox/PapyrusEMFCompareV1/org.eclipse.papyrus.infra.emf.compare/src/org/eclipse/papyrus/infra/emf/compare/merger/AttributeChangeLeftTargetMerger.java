/*******************************************************************************
 * Copyright (c) 2006, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.infra.emf.compare.merger;

import java.util.List;

import org.eclipse.emf.compare.EMFComparePlugin;
import org.eclipse.emf.compare.FactoryException;
import org.eclipse.emf.compare.diff.merge.DefaultMerger;
import org.eclipse.emf.compare.diff.metamodel.AttributeChangeLeftTarget;
import org.eclipse.emf.compare.util.EFactory;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;

/**
 * Merger for an {@link AttributeChangeLeftTarget} operation.<br/>
 * <p>
 * Are considered for this merger :
 * <ul>
 * <li>{@link RemoveAttribute}</li>
 * <li>{@link RemoteAddAttribute}</li>
 * </ul>
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class AttributeChangeLeftTargetMerger extends DefaultMerger {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.diff.merge.api.AbstractMerger#doApplyInOrigin()
	 */
	@Override
	public void doApplyInOrigin() {
		final AttributeChangeLeftTarget theDiff = (AttributeChangeLeftTarget)this.diff;
		final EObject origin = theDiff.getLeftElement();
		final Object value = theDiff.getLeftTarget();
		final EAttribute attr = theDiff.getAttribute();
		try {
			EFactory.eRemove(origin, attr.getName(), value);
		} catch (FactoryException e) {
			EMFComparePlugin.log(e, true);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.diff.merge.api.AbstractMerger#doUndoInTarget()
	 */
	@Override
	public void doUndoInTarget() {
		final AttributeChangeLeftTarget theDiff = (AttributeChangeLeftTarget)this.diff;
		final EObject target = theDiff.getRightElement();
		final Object value = theDiff.getLeftTarget();
		final EAttribute attr = theDiff.getAttribute();
		try {
			int valueIndex = -1;
			if (attr.isMany()) {
				final EObject leftElement = theDiff.getLeftElement();
				final Object leftValues = leftElement.eGet(attr);
				if (leftValues instanceof List) {
					final List leftValuesList = (List)leftValues;
					valueIndex = leftValuesList.indexOf(value);
				}
			}
			EFactory.eAdd(target, attr.getName(), value, valueIndex);
		} catch (FactoryException e) {
			EMFComparePlugin.log(e, true);
		}
	}
}
