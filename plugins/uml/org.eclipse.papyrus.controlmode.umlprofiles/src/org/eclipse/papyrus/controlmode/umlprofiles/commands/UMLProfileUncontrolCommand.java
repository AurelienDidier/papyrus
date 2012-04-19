/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Emilien Perico (Atos Origin) emilien.perico@atosorigin.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.controlmode.umlprofiles.commands;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.controlmode.commands.IUncontrolCommand;
import org.eclipse.papyrus.controlmode.umlprofiles.helpers.ProfileApplicationHelper;
import org.eclipse.uml2.common.edit.command.ChangeCommand;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;
import org.eclipse.uml2.uml.resource.UMLResource;


/**
 * The Class UMLProfileUncontrolCommand is custom command to manage UML profiles and stereotypes when uncontrol action is performed
 */
public class UMLProfileUncontrolCommand implements IUncontrolCommand {

	/**
	 * {@inheritDoc}
	 */
	public boolean provides(EObject selection, STATE_CONTROL state, Resource source, Resource target) {
		return target != null && target.getURI() != null && UMLResource.FILE_EXTENSION.equals(target.getURI().fileExtension());
	}

	/**
	 * {@inheritDoc}
	 */
	public void uncontrol(EditingDomain domain, final EObject selection, STATE_CONTROL state, Resource source, final Resource target, CompoundCommand commandToModify) {
		if (selection instanceof Package) {
			switch(state) {
			case PRE_MODEL:
				commandToModify.append(new ChangeCommand(domain, new Runnable() {

					public void run() {
						unapplyDuplicateProfiles((Package)selection, target);
					}
				}));
				break;
			case POST_MODEL:
				commandToModify.append(new ChangeCommand(domain, new Runnable() {

					public void run() {
						ProfileApplicationHelper.nestedRelocateStereotypeApplications((Package)selection, target);
					}
				}));
				break;
			default:
			}
		}
	}

	/**
	 * Unapply profiles duplicated for control action
	 * 
	 * @param selection
	 * @param target
	 *        the resource target
	 */
	private void unapplyDuplicateProfiles(final Package _package, Resource target) {
		for(Profile profile : _package.getAppliedProfiles()) {
			// profile is duplicated, unapply it
			ProfileApplicationHelper.removeProfileApplicationDuplication(_package, profile, true);

		}
	}

}
