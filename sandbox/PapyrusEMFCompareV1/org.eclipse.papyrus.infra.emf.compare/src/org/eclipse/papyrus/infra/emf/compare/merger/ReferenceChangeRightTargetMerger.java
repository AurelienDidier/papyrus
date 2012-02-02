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

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.compare.EMFComparePlugin;
import org.eclipse.emf.compare.FactoryException;
import org.eclipse.emf.compare.diff.merge.DefaultMerger;
import org.eclipse.emf.compare.diff.merge.service.MergeService;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.ReferenceChangeRightTarget;
import org.eclipse.emf.compare.diff.metamodel.ReferenceOrderChange;
import org.eclipse.emf.compare.diff.metamodel.ResourceDependencyChange;
import org.eclipse.emf.compare.util.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * Merger for an {@link ReferenceChangeRightTarget} operation.<br/>
 * <p>
 * Are considered for this merger :
 * <ul>
 * <li>RemoveReferenceValue</li>
 * <li>RemoteAddReferenceValue</li>
 * </ul>
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class ReferenceChangeRightTargetMerger extends DefaultMerger {
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.diff.merge.api.AbstractMerger#doApplyInOrigin()
	 */
	@Override
	public void doApplyInOrigin() {
		final ReferenceChangeRightTarget theDiff = (ReferenceChangeRightTarget)this.diff;
		final EReference reference = theDiff.getReference();
		final EObject element = theDiff.getLeftElement();
		final EObject rightTarget = theDiff.getRightTarget();
		final EObject leftTarget = theDiff.getLeftTarget();

		// ordering handling:
		int index = -1;
		if (reference.isMany()) {
			final EObject rightElement = theDiff.getRightElement();
			final Object rightRefValue = rightElement.eGet(reference);
			if (rightRefValue instanceof List) {
				final List refRightValueList = (List)rightRefValue;
				index = refRightValueList.indexOf(rightTarget);
			}
		}
		final EObject copiedValue = MergeService.getCopier(diff).copyReferenceValue(reference, element,
				rightTarget, leftTarget, index);

		// We'll now look through this reference's eOpposite as they are already taken care of
		final Iterator<EObject> related = getDiffModel().eAllContents();
		while (related.hasNext()) {
			final DiffElement op = (DiffElement)related.next();
			if (op instanceof ReferenceChangeRightTarget) {
				final ReferenceChangeRightTarget link = (ReferenceChangeRightTarget)op;
				// If this is my eOpposite, delete it from the DiffModel (merged along with this one)
				if (link.getReference().equals(theDiff.getReference().getEOpposite())
						&& link.getRightTarget().equals(element)) {
					removeFromContainer(link);
				}
			} else if (op instanceof ReferenceOrderChange) {
				final ReferenceOrderChange link = (ReferenceOrderChange)op;
				if (link.getLeftElement() == element && link.getReference() == reference) {
					final ListIterator<EObject> targetIterator = link.getLeftTarget().listIterator();
					boolean replaced = false;
					while (!replaced && targetIterator.hasNext()) {
						final EObject target = targetIterator.next();
						if (target.eIsProxy()
								&& equalProxyURIs(((InternalEObject)target).eProxyURI(),
										EcoreUtil.getURI(rightTarget))) {
							targetIterator.set(copiedValue);
							replaced = true;
						}
					}
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.diff.merge.api.AbstractMerger#doUndoInTarget()
	 */
	@Override
	public void doUndoInTarget() {
		final ReferenceChangeRightTarget theDiff = (ReferenceChangeRightTarget)this.diff;
		final EObject element = theDiff.getRightElement();
		final EObject rightTarget = theDiff.getRightTarget();
		try {
			EFactory.eRemove(element, theDiff.getReference().getName(), rightTarget);
		} catch (final FactoryException e) {
			EMFComparePlugin.log(e, true);
		}
		// we should now have a look for AddReferencesLinks needing this object
		final Iterator<EObject> related = getDiffModel().eAllContents();
		while (related.hasNext()) {
			final DiffElement op = (DiffElement)related.next();
			if (op instanceof ReferenceChangeRightTarget) {
				final ReferenceChangeRightTarget link = (ReferenceChangeRightTarget)op;
				// now if I'm in the target References I should put my copy in the origin
				if (link.getReference().equals(theDiff.getReference().getEOpposite())
						&& link.getRightTarget().equals(element)) {
					removeFromContainer(link);
				}
			} else if (op instanceof ResourceDependencyChange) {
				final ResourceDependencyChange link = (ResourceDependencyChange)op;
				final Resource res = link.getRoots().get(0).eResource();
				if (res == rightTarget.eResource()) {
					EcoreUtil.remove(link);
					res.unload();
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.diff.merge.DefaultMerger#getDependencies(boolean)
	 */
	@Override
	protected List<DiffElement> getDependencies(boolean applyInOrigin) {
		if (applyInOrigin) {
			return diff.getRequires();
		}
		return super.getDependencies(applyInOrigin);
	}
}
