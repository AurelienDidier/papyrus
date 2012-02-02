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

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.compare.EMFComparePlugin;
import org.eclipse.emf.compare.FactoryException;
import org.eclipse.emf.compare.diff.internal.merge.impl.ModelElementChangeLeftTargetMerger;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.ModelElementChangeLeftTarget;
import org.eclipse.emf.compare.diff.metamodel.ReferenceChangeLeftTarget;
import org.eclipse.emf.compare.diff.metamodel.ReferenceOrderChange;
import org.eclipse.emf.compare.util.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.emf.compare.Activator;
import org.eclipse.papyrus.infra.emf.compare.merger.provider.PapyrusMergeCommandProvider;
import org.eclipse.papyrus.infra.emf.compare.utils.CompareEditorConfiguration;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

/**
 * Merger for an {@link ModelElementChangeLeftTarget} operation.<br/>
 * <p>
 * Are considered for this merger :
 * <ul>
 * <li>AddModelElement</li>
 * <li>RemoteRemoveModelElement</li>
 * </ul>
 * </p>
 * 
 * @author <a href="mailto:laurent.goubet@obeo.fr">Laurent Goubet</a>
 */
public class PapyrusModelElementChangeLeftTargetMerger extends ModelElementChangeLeftTargetMerger {

private boolean useEditService(){
	CompareEditorConfiguration config = getConfiguration();
	return config != null && config.isEditServiceUsing();
}

private CompareEditorConfiguration getConfiguration(){
	IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
	return Activator.getDefault().getConfigurationFor(editor);
}
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.diff.merge.api.AbstractMerger#doApplyInOrigin()
	 */
	@Override
	protected void doApplyInOrigin() {
		
		if(useEditService()) {
			System.out.println("Synchronization avec le diagramme : " + useEditService());
			final ModelElementChangeLeftTarget theDiff = (ModelElementChangeLeftTarget)this.diff;
			final EObject element = theDiff.getLeftElement();
			TransactionalEditingDomain domain = getConfiguration().getEditingDomain();
			Command cmd = PapyrusMergeCommandProvider.INSTANCE.getDestroyCommand(domain, element);
			domain.getCommandStack().execute(cmd);
		} else {
			System.out.println("Synchronization avec le diagramme : " + useEditService());
			super.doApplyInOrigin();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.diff.merge.api.AbstractMerger#doUndoInTarget()
	 */
	@Override
	protected void doUndoInTarget() {
		final ModelElementChangeLeftTarget theDiff = (ModelElementChangeLeftTarget)this.diff;
		// we should copy the element to the Origin one.
		final EObject origin = theDiff.getRightParent();
		final EObject element = theDiff.getLeftElement();
		final EObject newOne = copy(element);
		final EReference ref = element.eContainmentFeature();
		if(ref != null) {
			try {
				int elementIndex = -1;
				if(ref.isMany()) {
					final Object containmentRefVal = element.eContainer().eGet(ref);
					if(containmentRefVal instanceof List<?>) {
						@SuppressWarnings("unchecked")
						final List<EObject> listVal = (List<EObject>)containmentRefVal;
						elementIndex = listVal.indexOf(element);
					}
				}
				EFactory.eAdd(origin, ref.getName(), newOne, elementIndex, true);
				setXMIID(newOne, getXMIID(element));
			} catch (final FactoryException e) {
				EMFComparePlugin.log(e, true);
			}
		} else if(origin == null && getDiffModel().getRightRoots().size() > 0) {
			getDiffModel().getRightRoots().get(0).eResource().getContents().add(newOne);
		} else if(origin != null) {
			origin.eResource().getContents().add(newOne);
		} else {
			// FIXME throw exception : couldn't merge this
		}
		// we should now have a look for RemovedReferencesLinks needing elements to apply
		final Iterator<EObject> siblings = getDiffModel().eAllContents();
		while(siblings.hasNext()) {
			final Object op = siblings.next();
			if(op instanceof ReferenceChangeLeftTarget) {
				final ReferenceChangeLeftTarget link = (ReferenceChangeLeftTarget)op;
				// now if I'm in the target References I should put my copy in the origin
				if(link.getRightTarget() != null && link.getRightTarget() == element) {
					link.setLeftTarget(newOne);
				}
			} else if(op instanceof ReferenceOrderChange) {
				final ReferenceOrderChange link = (ReferenceOrderChange)op;
				if(link.getRightElement() == origin && link.getReference() == ref) {
					final ListIterator<EObject> targetIterator = link.getRightTarget().listIterator();
					boolean replaced = false;
					while(!replaced && targetIterator.hasNext()) {
						final EObject target = targetIterator.next();
						if(target.eIsProxy() && equalProxyURIs(((InternalEObject)target).eProxyURI(), EcoreUtil.getURI(element))) {
							targetIterator.set(newOne);
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
	 * @see org.eclipse.emf.compare.diff.merge.DefaultMerger#getDependencies(boolean)
	 */
	@Override
	protected List<DiffElement> getDependencies(boolean applyInOrigin) {
		if(!applyInOrigin) {
			return diff.getRequires();
		}
		return super.getDependencies(applyInOrigin);
	}

	public Command doApplyInOriginCommand() {
		// TODO Auto-generated method stub
		return null;
	}

	public Command doUndoInTargetCommand() {
		// TODO Auto-generated method stub
		return null;
	}
}
