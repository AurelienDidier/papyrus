/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 382954
 */
package org.eclipse.papyrus.uml.diagram.clazz.custom.command;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.papyrus.uml.diagram.clazz.custom.helper.InstanceSpecificationLinkHelper;
import org.eclipse.papyrus.uml.diagram.clazz.custom.ui.AssociationSelectionDialog;
import org.eclipse.papyrus.uml.diagram.clazz.edit.commands.InstanceSpecificationLinkCreateCommand;
import org.eclipse.papyrus.uml.tools.utils.NamedElementUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.InstanceValue;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Slot;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;

/**
 * Use to construct the instance specification link between two instance
 *
 */
public class CInstanceSpecificationLinkCreateCommand extends InstanceSpecificationLinkCreateCommand {

	public CInstanceSpecificationLinkCreateCommand(CreateRelationshipRequest request, EObject source, EObject target) {
		super(request, source, target);
	}

	@Override
	public boolean canExecute() {
		/*
		 * Case 0: Only the target is null
		 */
		if (source != null && target == null) {
			return source instanceof InstanceSpecification && ((InstanceSpecification) source).getClassifiers().size() > 0;
		}
		/*
		 * Case 1 : source and target != null
		 * look for if it exist at least a common association between classifiers referenced between these instances
		 */
		if (source == null || target == null) {
			return false;
		}
		if (false == source instanceof InstanceSpecification) {
			return false;
		}
		if (false == target instanceof InstanceSpecification) {
			return false;
		}
		return ((InstanceSpecification) source).getClassifiers().size() > 0 && ((InstanceSpecification) target).getClassifiers().size() > 0;
	}

	/**
	 * Gets the instance associations.
	 *
	 * @param instance
	 *            the instance
	 * @return the instance associations
	 */
	private HashSet<Association> getInstanceAssociations(InstanceSpecification instance) {
		// Initialise set of associations
		HashSet<Association> instanceAssociationsSet = new HashSet<Association>();
		// Extract all associations of Instance Specification's classifiers
		Iterator<Classifier> iterator = getSpecificationClassifier(instance).iterator();
		while (iterator.hasNext()) {
			Classifier classifier = iterator.next();
			instanceAssociationsSet.addAll(classifier.getAssociations());
		}
		return instanceAssociationsSet;
	}

	/**
	 * Gets the specification classifiers.
	 *
	 * @param instance
	 *            the instance
	 * @return the specification classifiers
	 */
	private Set<Classifier> getSpecificationClassifier(InstanceSpecification instance) {
		// Initialise Set of Classifiers
		Set<Classifier> specificationClassicfiersSet = new HashSet<Classifier>();
		// Explore first rank classifiers
		for (Classifier classifier : instance.getClassifiers()) {

			// Explore only Classifier which are not already in Set
			if (!specificationClassicfiersSet.contains(classifier)) {
				specificationClassicfiersSet.add(classifier);
				specificationClassicfiersSet.addAll(getInheritedClassifier(classifier, null));
			}
		}
		return specificationClassicfiersSet;
	}

	/**
	 * Gets the inherited classifier.
	 *
	 * @param classifier
	 *            the classifier
	 * @return the inherited classifier
	 */
	private Set<Classifier> getInheritedClassifier(Classifier classifier, Set<Classifier> alreadyParsedClassifier) {

		// Initialise set of Classifier from Generalisation
		Set<Classifier> generalizationClassifiers = new HashSet<Classifier>();


		// Keep track of parsed Classifier to avoid loop
		Set<Classifier> parsedClassifiersSet = new HashSet<Classifier>();
		if (alreadyParsedClassifier != null) {
			parsedClassifiersSet.addAll(alreadyParsedClassifier);
		}

		// Explore only Classifier which are not already parsed
		if (!parsedClassifiersSet.contains(classifier)) {
			parsedClassifiersSet.add(classifier);

			// Explore all generalisation of Classifier
			EList<Classifier> classifierGeneralizations = classifier.parents();
			generalizationClassifiers.addAll(classifierGeneralizations);

			for (Classifier generalClassifier : classifierGeneralizations) {
				generalizationClassifiers.addAll(getInheritedClassifier(generalClassifier, parsedClassifiersSet));
			}
		}

		return generalizationClassifiers;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.clazz.edit.commands.InstanceSpecificationLinkCreateCommand#doExecuteWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param monitor
	 * @param info
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		if (((InstanceSpecification) source).getClassifiers().isEmpty() || ((InstanceSpecification) target).getClassifiers().isEmpty()) {
			return CommandResult.newCancelledCommandResult();
		}
		AssociationSelectionDialog associationSelectionDialog = new AssociationSelectionDialog(new Shell(), SWT.NATIVE, getModelAssociations());
		associationSelectionDialog.open();
		Association selectedAssociation = associationSelectionDialog.getSelectedAssociation();
		if (selectedAssociation == null && associationSelectionDialog.isCanceled()) {
			return CommandResult.newCancelledCommandResult();
		}
		/*
		 * Creation of the instance specification link
		 * with a name a container, and set the source and target
		 */
		InstanceSpecification instanceSpecification = UMLFactory.eINSTANCE.createInstanceSpecification();
		getContainer().getPackagedElements().add(instanceSpecification);
		instanceSpecification.setName(NamedElementUtil.getDefaultNameWithIncrementFromBase(instanceSpecification.eClass().getName(), instanceSpecification.getOwner().eContents()));
		Set<Classifier> sourceSpecificationClassifiersSet = getSpecificationClassifier((InstanceSpecification) source);
		Set<Classifier> targetSpecificationClassifiersSet = getSpecificationClassifier((InstanceSpecification) target);
		boolean revertEnds = false;
		if (selectedAssociation != null) {
			instanceSpecification.getClassifiers().add(selectedAssociation);
			Type sourceType = selectedAssociation.getMemberEnds().get(0).getType();
			revertEnds = false == sourceSpecificationClassifiersSet.contains(sourceType);
		}
		if (revertEnds) {
			InstanceSpecificationLinkHelper.addEnd(instanceSpecification, ((InstanceSpecification) target));
			InstanceSpecificationLinkHelper.addEnd(instanceSpecification, ((InstanceSpecification) source));
		} else {
			InstanceSpecificationLinkHelper.addEnd(instanceSpecification, ((InstanceSpecification) source));
			InstanceSpecificationLinkHelper.addEnd(instanceSpecification, ((InstanceSpecification) target));
		}
		setupSlots(selectedAssociation, instanceSpecification, sourceSpecificationClassifiersSet, targetSpecificationClassifiersSet);
		return CommandResult.newOKCommandResult(instanceSpecification);
	}

	private Set<Association> getModelAssociations() {
		if (source == null || target == null) {
			return Collections.emptySet();
		}
		if (false == source instanceof InstanceSpecification || false == target instanceof InstanceSpecification) {
			return Collections.emptySet();
		}
		Set<Association> result = new HashSet<Association>();
		result.addAll(getInstanceAssociations((InstanceSpecification) source));
		result.retainAll(getInstanceAssociations((InstanceSpecification) target));
		return result;
	}

	private void setupSlots(Association selectedAssociation, InstanceSpecification instanceSpecification, Set<Classifier> sourceSpecificationClassifiersSet, Set<Classifier> targetSpecificationClassifiersSet) {
		if (selectedAssociation == null) {
			return;
		}
		// Creation of slots into the good instance by taking in account the association
		Iterator<Property> proIterator = selectedAssociation.getMemberEnds().iterator();
		while (proIterator.hasNext()) {
			Property property = proIterator.next();
			Slot slot = UMLFactory.eINSTANCE.createSlot();
			slot.setDefiningFeature(property);
			if (sourceSpecificationClassifiersSet.contains(property.getOwner())) {
				((InstanceSpecification) source).getSlots().add(slot);
				associateValue(((InstanceSpecification) target), slot, property.getType());
			} else if (targetSpecificationClassifiersSet.contains(property.getOwner())) {
				((InstanceSpecification) target).getSlots().add(slot);
				associateValue(((InstanceSpecification) source), slot, property.getType());
			} else {
				instanceSpecification.getSlots().add(slot);
				if (sourceSpecificationClassifiersSet.contains(property.getType())) {
					associateValue(((InstanceSpecification) source), slot, property.getType());
				} else {
					associateValue(((InstanceSpecification) target), slot, property.getType());
				}
			}
		}
	}

	/**
	 * create an instanceValue for the slot (owner) with the reference to InstanceSpecification and the good type
	 *
	 * @param instanceSpecification
	 *            that is referenced by the instanceValue
	 * @param owner
	 *            of the instance value
	 * @param type
	 *            of the instanceValue
	 * @return a instanceValue
	 */
	protected InstanceValue associateValue(InstanceSpecification instanceSpecification, Slot owner, Type type) {
		InstanceValue instanceValue = UMLFactory.eINSTANCE.createInstanceValue();
		instanceValue.setName(NamedElementUtil.getDefaultNameWithIncrementFromBase(instanceValue.eClass().getName(), owner.eContents()));
		instanceValue.setType(type);
		instanceValue.setInstance(instanceSpecification);
		owner.getValues().add(instanceValue);
		return instanceValue;
	}
}
