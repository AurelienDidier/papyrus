/*****************************************************************************
 * Copyright (c) 2006, 2010, 2013 Borland Software Corporation and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Dmitry Stadnik (Borland) - initial API and implementation
 * Alexander Shatalin (Borland) - initial API and implementation
 * Michael Golubev (Montages) - #386838 - migrate to Xtend2
 * Thibault Landre (Atos Origin) - initial API and implementation
 * 
 *****************************************************************************/
package aspects.impl.diagram.editparts

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.gmf.codegen.gmfgen.GenLinkLabel
import xpt.diagram.editparts.Common

//DOCUMENTATION: PapyrusGencode
//Overlaod only the method  handlenotificationEventBody

@Singleton class LinkLabelEditPart extends impl.diagram.editparts.LinkLabelEditPart {
	@Inject extension Common;

	override handleNotificationEventBody(GenLinkLabel it) '''
		Object feature = event.getFeature();
		«handleText (it)»
		
		«««	START Papyrus Code
		«IF elementIcon»
			if(event.getNewValue() instanceof org.eclipse.emf.ecore.EAnnotation && org.eclipse.papyrus.infra.emf.appearance.helper.VisualInformationPapyrusConstants.DISPLAY_NAMELABELICON.equals(((org.eclipse.emf.ecore.EAnnotation)event.getNewValue()).getSource())){	
				refreshLabel();
			}
		«ENDIF»
		«««	End Papyrus Code
		super.handleNotificationEvent(event);
	'''
	
	override additionalEditPolicies(GenLinkLabel it)
	'''
	installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE, new org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.PapyrusLinkLabelDragPolicy());
    '''

}
