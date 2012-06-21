package org.eclipse.papyrus.uml.compare.merger.internal.old.merger;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.compare.diff.merge.EMFCompareEObjectCopier;
import org.eclipse.emf.compare.diff.merge.IMergeListener;
import org.eclipse.emf.compare.diff.merge.IMerger;
import org.eclipse.emf.compare.diff.merge.MergeEvent;
import org.eclipse.emf.compare.diff.merge.service.MergeFactory;
import org.eclipse.emf.compare.diff.merge.service.MergeService;
import org.eclipse.emf.compare.diff.metamodel.ConflictingDiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffElement;
import org.eclipse.emf.compare.diff.metamodel.DiffGroup;
import org.eclipse.emf.compare.diff.metamodel.DiffModel;
import org.eclipse.emf.compare.diff.metamodel.ModelElementChangeLeftTarget;
import org.eclipse.emf.compare.diff.metamodel.ModelElementChangeRightTarget;
import org.eclipse.emf.compare.diff.metamodel.ReferenceChange;
import org.eclipse.emf.compare.diff.metamodel.ReferenceChangeLeftTarget;
import org.eclipse.emf.compare.diff.metamodel.ReferenceChangeRightTarget;
import org.eclipse.emf.compare.diff.metamodel.UpdateReference;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.commands.wrappers.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.uml.compare.merger.services.PapyrusMergeFactory;
import org.eclipse.papyrus.uml.compare.merger.services.TransactionalMergeService;
import org.eclipse.papyrus.uml.compare.merger.utils.ITransactionalMerger;

/**
 * 
 * should replace DefaultMerger
 * 
 */
public class TransactionalDefaultMerger implements ITransactionalMerger {

	//---------------------These methods comes from ITransactionalMerger
	public Command getApplyInOriginCommand(TransactionalEditingDomain domain) {
		// TODO Auto-generated method stub
		return null;
	}

	public Command getUndoInTargetCommand(TransactionalEditingDomain domain) {
		// TODO Auto-generated method stub
		return null;
	}

	public Command getDoApplyInOriginCommand(TransactionalEditingDomain domain) {
		// TODO Auto-generated method stub
		return null;
	}

	public Command getDoUndoInTargetCommand(TransactionalEditingDomain domain) {
		// TODO Auto-generated method stub
		return null;
	}

	public Command getMergeRequiredDifferencesCommand(TransactionalEditingDomain domain, boolean applyInOrigin) {
		if(mergedDiffs == null) {
			mergedDiffs = new ArrayList<DiffElement>();
			if(mergedDiffslistener == null) {
				mergedDiffslistener = new MergedDiffsListener();
				TransactionalMergeService.addMergeListener(mergedDiffslistener);
			}
		}
		mergedDiffs.add(diff);

		for(DiffElement requiredDiff : getDependencies(applyInOrigin)) {
			if(requiredDiff.eContainer() != null && !mergedDiffs.contains(requiredDiff)) {
				final IMerger merger = PapyrusMergeFactory.createMerger(requiredDiff);
				if(merger instanceof ITransactionalMerger) {
					if(applyInOrigin) {
						((ITransactionalMerger)merger).getApplyInOriginCommand(domain);
					} else {
						((ITransactionalMerger)merger).getUndoInTargetCommand(domain);
					}
				}else{
					throw new UnsupportedOperationException(NLS.bind("I can't found an ITransactionalMerger to merge to required differences for {0}", this));
				}
			}
		}
		return null;
	}

	public Command getPostProcessCommand(TransactionalEditingDomain domain) {
		return new GMFtoEMFCommandWrapper(new AbstractTransactionalCommand(domain, "Merge Required Differences", null) { //$NON-NLS-1$

			@Override
			protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {
				handleMutuallyDerivedReferences();
				//ensureXMIIDCopied();
				final EMFCompareEObjectCopier copier = TransactionalMergeService.getCopier(diff);
				copier.copyXMIIDs();
				removeFromContainer(diff);
				return null;
			}
		});
	}


	//---------------------from Here to the end : duplicated and adapted code from DefaultMerger
	/** The scheme used for all of EMF Compare's internal proxies. */
	private static final String PROXY_SCHEME = "emfCompareProxy"; //$NON-NLS-1$

	/** Stores the differences which are merged or being merged during a merge operation. */
	private static List<DiffElement> mergedDiffs;

	/** This listener will be used to reset the {@link mergedDiffs} list at the end of a merge operation. */
	private static MergedDiffsListener mergedDiffslistener;

	/** {@link DiffElement} to be merged by this merger. */
	protected DiffElement diff;

	/** Keeps a reference on the left resource for this merger. */
	@Deprecated
	protected Resource leftResource;

	/** Keeps a reference on the right resource for this merger. */
	@Deprecated
	protected Resource rightResource;

	/**
	 * Reset the {@link mergedDiffs} list.
	 * 
	 * @since 1.3
	 */
	protected static void resetMergedDiffs() {
		mergedDiffs = null;
	}

	/**
	 * Returns <code>true</code> if the given URI is an internal EMF Compare proxy.
	 * 
	 * @param uri
	 *        The URI to check.
	 * @return <code>true</code> if the given URI is an internal EMF Compare proxy.
	 * @since 1.3
	 */
	public static boolean isEMFCompareProxy(URI uri) {
		return uri.scheme() != null && uri.scheme().equals(PROXY_SCHEME);
	}

	/**
	 * Checks whether the two given proxy URIs are equal, ignoring EMF Compare's internal {@link #PROXY_PREFIX}.
	 * 
	 * @param uri1
	 *        First of the two URIs to compare.
	 * @param uri2
	 *        Second of the two URIs to compare.
	 * @return <code>true</code> if the two given URIs are equal.
	 * 
	 * @since 1.3
	 */
	public static boolean equalProxyURIs(URI uri1, URI uri2) {
		String stringValue1 = uri1.toString();
		String stringValue2 = uri2.toString();
		if(isEMFCompareProxy(uri1)) {
			stringValue1 = stringValue1.substring(PROXY_SCHEME.length() + 2);
		}
		if(isEMFCompareProxy(uri2)) {
			stringValue2 = stringValue2.substring(PROXY_SCHEME.length() + 2);
		}
		return stringValue1.equals(stringValue2);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.diff.merge.IMerger#applyInOrigin()
	 */
	public void applyInOrigin() {
		mergeRequiredDifferences(true);
		doApplyInOrigin();
		postProcess();
	}

	/**
	 * Applies the modification in the original (left) model.
	 * 
	 * @since 1.3
	 */
	protected void doApplyInOrigin() {
		// To specialize in child class.
	}

	/**
	 * Applies a post processing.
	 * 
	 * @since 1.3
	 */
	protected void postProcess() {
		handleMutuallyDerivedReferences();
		ensureXMIIDCopied();
		removeFromContainer(diff);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.diff.merge.IMerger#canApplyInOrigin()
	 */
	public boolean canApplyInOrigin() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.diff.merge.IMerger#canUndoInTarget()
	 */
	public boolean canUndoInTarget() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.diff.merge.IMerger#setDiffElement(org.eclipse.emf.compare.diff.metamodel.DiffElement)
	 */
	public void setDiffElement(DiffElement element) {
		diff = element;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.compare.diff.merge.IMerger#undoInTarget()
	 */
	public void undoInTarget() {
		mergeRequiredDifferences(false);
		doUndoInTarget();
		postProcess();
	}

	/**
	 * Cancels the modification in the target (right) model.
	 * 
	 * @since 1.3
	 */
	protected void doUndoInTarget() {
		// To specialize in child class.
	}

	/**
	 * Removes the given {@link DiffGroup} from its container if it was its last child, also calls for the
	 * same cleanup operation on its hierarchy.
	 * 
	 * @param diffGroup
	 *        {@link DiffGroup} we want to cleanup.
	 */
	protected void cleanDiffGroup(DiffGroup diffGroup) {
		if(diffGroup != null && diffGroup.getSubDiffElements().size() == 0) {
			final EObject parent = diffGroup.eContainer();
			if(parent instanceof DiffGroup) {
				EcoreUtil.remove(diffGroup);
				cleanDiffGroup((DiffGroup)parent);
			}
		}
	}

	/**
	 * Creates a copy of the given EObject as would {@link EcoreUtil#copy(EObject)} would, except we use
	 * specific handling for unmatched references.
	 * 
	 * @param eObject
	 *        The object to copy.
	 * @return the copied object.
	 */
	protected EObject copy(EObject eObject) {
		final EMFCompareEObjectCopier copier = MergeService.getCopier(diff);
		final EObject result = copier.copy(eObject);
		copier.copyReferences();
		copier.copyXMIIDs();
		return result;
	}

	/**
	 * This can be called after a merge operation to ensure that all objects created by the operation share
	 * the same XMI ID as their original.
	 * <p>
	 * Implemented because of bug 351591 : some of the objects we copy mays not have been added to a resource when we check their IDs. We thus need to
	 * wait till the merge operation has completed.
	 * </p>
	 * 
	 * @since 1.3
	 */
	protected void ensureXMIIDCopied() {
		final EMFCompareEObjectCopier copier = MergeService.getCopier(diff);
		copier.copyXMIIDs();
	}

	/**
	 * Returns the {@link DiffModel} containing the {@link DiffElement} this merger is intended to merge.
	 * 
	 * @return The {@link DiffModel} containing the {@link DiffElement} this merger is intended to merge.
	 */
	protected DiffModel getDiffModel() {
		EObject container = diff.eContainer();
		while(container != null) {
			if(container instanceof DiffModel)
				return (DiffModel)container;
			container = container.eContainer();
		}
		return null;
	}

	/**
	 * Returns the XMI ID of the given {@link EObject} or <code>null</code> if it cannot be resolved.
	 * 
	 * @param object
	 *        Object which we seek the XMI ID of.
	 * @return <code>object</code>'s XMI ID, <code>null</code> if not applicable.
	 */
	protected String getXMIID(EObject object) {
		String objectID = null;
		if(object != null && object.eResource() instanceof XMIResource) {
			objectID = ((XMIResource)object.eResource()).getID(object);
		}
		return objectID;
	}

	/**
	 * Removes all references to the given {@link EObject} from the {@link DiffModel}.
	 * 
	 * @param deletedObject
	 *        Object to remove all references to.
	 */
	protected void removeDanglingReferences(EObject deletedObject) {
		// EObject root = EcoreUtil.getRootContainer(deletedObject);
		// if (root instanceof ComparisonResourceSnapshot) {
		// root = ((ComparisonResourceSnapshot)root).getDiff();
		// }
		// if (root != null) {
		// // FIXME performance, find a way to cache this referencer
		// final Resource res = root.eResource();
		// final EcoreUtil.CrossReferencer referencer;
		// if (res != null && res.getResourceSet() != null) {
		// referencer = new EcoreUtil.CrossReferencer(res.getResourceSet()) {
		// private static final long serialVersionUID = 616050158241084372L;
		//
		// // initializer for this anonymous class
		// {
		// crossReference();
		// }
		//
		// @Override
		// protected boolean crossReference(EObject eObject, EReference eReference,
		// EObject crossReferencedEObject) {
		// if (eReference.isChangeable() && !eReference.isDerived())
		// return crossReferencedEObject.eResource() == null;
		// return false;
		// }
		// };
		// } else if (res != null) {
		// referencer = new EcoreUtil.CrossReferencer(res) {
		// private static final long serialVersionUID = 616050158241084372L;
		//
		// // initializer for this anonymous class
		// {
		// crossReference();
		// }
		//
		// @Override
		// protected boolean crossReference(EObject eObject, EReference eReference,
		// EObject crossReferencedEObject) {
		// if (eReference.isChangeable() && !eReference.isDerived())
		// return crossReferencedEObject.eResource() == null;
		// return false;
		// }
		// };
		// } else {
		// referencer = new EcoreUtil.CrossReferencer(root) {
		// private static final long serialVersionUID = 616050158241084372L;
		//
		// // initializer for this anonymous class
		// {
		// crossReference();
		// }
		//
		// @Override
		// protected boolean crossReference(EObject eObject, EReference eReference,
		// EObject crossReferencedEObject) {
		// if (eReference.isChangeable() && !eReference.isDerived())
		// return crossReferencedEObject.eResource() == null;
		// return false;
		// }
		// };
		// }
		// final Iterator<Map.Entry<EObject, Collection<EStructuralFeature.Setting>>> i = referencer
		// .entrySet().iterator();
		// while (i.hasNext()) {
		// final Map.Entry<EObject, Collection<EStructuralFeature.Setting>> entry = i.next();
		// final Iterator<EStructuralFeature.Setting> j = entry.getValue().iterator();
		// while (j.hasNext()) {
		// EcoreUtil.remove(j.next(), entry.getKey());
		// }
		// }
		// }
	}

	/**
	 * Removes a {@link DiffElement} from its {@link DiffGroup}.
	 * 
	 * @param diffElement
	 *        {@link DiffElement} to remove from its container.
	 */
	protected void removeFromContainer(DiffElement diffElement) {
		final EObject parent = diffElement.eContainer();
		EcoreUtil.remove(diffElement);
		removeDanglingReferences(parent);

		// If diff was contained by a ConflictingDiffElement, we call back this on it
		if(parent instanceof ConflictingDiffElement) {
			removeFromContainer((DiffElement)parent);
		}

		// if diff was in a diffGroup and it was the last one, we also remove the diffgroup
		if(parent instanceof DiffGroup) {
			cleanDiffGroup((DiffGroup)parent);
		}
	}

	/**
	 * Sets the XMI ID of the given {@link EObject} if it belongs in an {@link XMIResource}.
	 * 
	 * @param object
	 *        Object we want to set the XMI ID of.
	 * @param id
	 *        XMI ID to give to <code>object</code>.
	 */
	protected void setXMIID(EObject object, String id) {
		if(object != null && object.eResource() instanceof XMIResource) {
			((XMIResource)object.eResource()).setID(object, id);
		}
	}

	/**
	 * Merge the differences required by the current difference.
	 * 
	 * @param applyInOrigin
	 *        True if the merge has to apply in origin.
	 * @since 1.3
	 */
	protected void mergeRequiredDifferences(boolean applyInOrigin) {
		if(mergedDiffs == null) {
			mergedDiffs = new ArrayList<DiffElement>();
			if(mergedDiffslistener == null) {
				mergedDiffslistener = new MergedDiffsListener();
				MergeService.addMergeListener(mergedDiffslistener);
			}
		}
		mergedDiffs.add(diff);

		for(DiffElement requiredDiff : getDependencies(applyInOrigin)) {
			if(requiredDiff.eContainer() != null && !mergedDiffs.contains(requiredDiff)) {
				final IMerger merger = MergeFactory.createMerger(requiredDiff);
				if(applyInOrigin) {
					merger.applyInOrigin();
				} else {
					merger.undoInTarget();
				}
			}
		}
	}

	/**
	 * This default merge listener will allow us to reset the {@link #mergedDiffs} list at the end of the
	 * merge operation.
	 * 
	 * @author <a href="mailto:cedric.notot@obeo.fr">Cedric Notot</a>
	 */
	private class MergedDiffsListener implements IMergeListener {

		/**
		 * Default constructor.
		 */
		public MergedDiffsListener() {
			// Enhance visibility of the default constructor.
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.compare.diff.merge.IMergeListener#mergeDiffEnd(org.eclipse.emf.compare.diff.merge.MergeEvent)
		 */
		public void mergeDiffEnd(MergeEvent event) {
			// do nothing
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.compare.diff.merge.IMergeListener#mergeDiffStart(org.eclipse.emf.compare.diff.merge.MergeEvent)
		 */
		public void mergeDiffStart(MergeEvent event) {
			// do nothing
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.compare.diff.merge.IMergeListener#mergeOperationEnd(org.eclipse.emf.compare.diff.merge.MergeEvent)
		 */
		public void mergeOperationEnd(MergeEvent event) {
			resetMergedDiffs();
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.compare.diff.merge.IMergeListener#mergeOperationStart(org.eclipse.emf.compare.diff.merge.MergeEvent)
		 */
		public void mergeOperationStart(MergeEvent event) {
			// do nothing
		}
	}

	/**
	 * Get the dependencies of the difference {@link diff} to merge. These dependencies will be merged before
	 * itself. This method may be overridden by a specific merger to choose to exploit or not the 'requires'
	 * link of the {@link diff} according to the nature of the merger and the direction of the merge.
	 * 
	 * @param applyInOrigin
	 *        The direction of the merge.
	 * @return The list of the dependencies to exploit.
	 * @since 1.3
	 */
	protected List<DiffElement> getDependencies(boolean applyInOrigin) {
		return new ArrayList<DiffElement>();
	}

	/**
	 * Mutually derived references need specific handling : merging one will implicitely merge the other and
	 * there are no way to tell such references apart.
	 * <p>
	 * Currently known references raising such issues :
	 * <table>
	 * <tr>
	 * <td>{@link EcorePackage#ECLASS__ESUPER_TYPES}</td>
	 * <td>{@link EcorePackage#ECLASS__EGENERIC_SUPER_TYPES}</td>
	 * </tr>
	 * </table>
	 * </p>
	 */
	private void handleMutuallyDerivedReferences() {
		DiffElement toRemove = null;
		if(diff instanceof ReferenceChange) {
			final EReference reference = ((ReferenceChange)diff).getReference();
			if(reference == EcorePackage.eINSTANCE.getEClass_ESuperTypes()) {
				EObject referenceType = null;
				if(diff instanceof ReferenceChangeLeftTarget) {
					referenceType = ((ReferenceChangeLeftTarget)diff).getRightTarget();
				} else if(diff instanceof ReferenceChangeRightTarget) {
					referenceType = ((ReferenceChangeRightTarget)diff).getLeftTarget();
				} else if(diff instanceof UpdateReference) {
					referenceType = ((UpdateReference)diff).getLeftTarget();
				} else {
					// we did cover all the subclasses, we should have a RferenceOrderChange
				}
				for(final DiffElement siblingDiff : ((DiffGroup)diff.eContainer()).getSubDiffElements()) {
					if(siblingDiff instanceof ModelElementChangeLeftTarget) {
						if(((ModelElementChangeLeftTarget)siblingDiff).getLeftElement() instanceof EGenericType && ((EGenericType)((ModelElementChangeLeftTarget)siblingDiff).getLeftElement()).getEClassifier() == referenceType) {
							toRemove = siblingDiff;
							break;
						}
					} else if(siblingDiff instanceof ModelElementChangeRightTarget) {
						if(((ModelElementChangeRightTarget)siblingDiff).getRightElement() instanceof EGenericType && ((EGenericType)((ModelElementChangeRightTarget)siblingDiff).getRightElement()).getEClassifier() == referenceType) {
							toRemove = siblingDiff;
							break;
						}
					}
				}
			}
		} else if(diff instanceof ModelElementChangeLeftTarget && ((ModelElementChangeLeftTarget)diff).getLeftElement() instanceof EGenericType) {
			final ModelElementChangeLeftTarget theDiff = (ModelElementChangeLeftTarget)diff;
			final EClassifier referenceType = ((EGenericType)theDiff.getLeftElement()).getEClassifier();
			for(final DiffElement siblingDiff : ((DiffGroup)diff.eContainer()).getSubDiffElements()) {
				if(siblingDiff instanceof ReferenceChangeLeftTarget && ((ReferenceChangeLeftTarget)siblingDiff).getReference().getFeatureID() == EcorePackage.ECLASS__ESUPER_TYPES) {
					if(((ReferenceChangeLeftTarget)siblingDiff).getRightTarget() == referenceType) {
						toRemove = siblingDiff;
						break;
					}
				}
			}
		} else if(diff instanceof ModelElementChangeRightTarget && ((ModelElementChangeRightTarget)diff).getRightElement() instanceof EGenericType) {
			final ModelElementChangeRightTarget theDiff = (ModelElementChangeRightTarget)diff;
			final EClassifier referenceType = ((EGenericType)theDiff.getRightElement()).getEClassifier();
			for(final DiffElement siblingDiff : ((DiffGroup)diff.eContainer()).getSubDiffElements()) {
				if(siblingDiff instanceof ReferenceChangeRightTarget && ((ReferenceChangeRightTarget)siblingDiff).getReference().getFeatureID() == EcorePackage.ECLASS__ESUPER_TYPES) {
					if(((ReferenceChangeRightTarget)siblingDiff).getLeftTarget() == referenceType) {
						toRemove = siblingDiff;
						break;
					}
				}
			}
		}
		if(toRemove != null) {
			removeFromContainer(toRemove);
		}
	}
}
