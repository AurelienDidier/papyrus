/*****************************************************************************
 * Copyright (c) 2012, 2014 CEA LIST and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Francois Le fevre - francois.le-fevre@cea.fr
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.stereotypeproperty;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.infra.core.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.stereotype.edition.editpart.AppliedStereotypeEmptyEditPart;
import org.eclipse.papyrus.uml.tools.commands.UnapplyStereotypeCommand;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Stereotype;
import org.junit.Rule;
import org.junit.Test;


/**
 * Unapply Stereotype. The comments have to disappear.
 */
@PluginResource({TestUnapplyStereotypeComment.MODEL_DI })
public class TestUnapplyStereotypeComment extends AbstractPapyrusTestCase {

	final static String MODEL_DI = "resource/UnapplyStereotype.di";
	
	final static String STEREOTYPEDCLASS = "StereotypedClass";
	final static String CLASS1 = "Class1";
	final static String STEREOTYPEDLINK = "StereotypedLink";
	
	final static String STEREOTYPE1 = "Stereotype1";

	private IMultiDiagramEditor editor;

	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();


	@Test
	@ActiveDiagram("ClassDiagram")
	public void testUnApplyStereotypeApplication() {

		editor = fixture.getEditor(MODEL_DI);
		fixture.openDiagram("ClassDiagram");
		
		//Check initial model
		NamedElement stereotypedClass = fixture.getModel().getOwnedMember(STEREOTYPEDCLASS);
		EditPart stereotypedClassEditPart = fixture.findEditPart(stereotypedClass);
		for(Object o : stereotypedClassEditPart.getChildren()){
			if(o instanceof AppliedStereotypeEmptyEditPart){
				assertTrue(STEREOTYPEDCLASS+" do not refer to any AppliedStereotypeEmptyEditPart comment", true);
			}
		}
		//List of EditPart
		//ClassNameEditPart
		//ClassAttributeCompartmentEditPart
		//ClassOperationCompartmentEditPart
		//ClassNestedclassifierCompartemtnEditPart
		//ClassFloatingNameEditPart
		//AppliedStereotypeEmptyEditPart
		assertEquals(STEREOTYPEDCLASS + " has not the right number of children", 6, stereotypedClassEditPart.getChildren().size());
		
		NamedElement stereotypedLink = fixture.getModel().getOwnedMember(STEREOTYPEDLINK);
		EditPart stereotypedLinkEditPart = fixture.findEditPart(stereotypedLink);
		for(Object o : stereotypedLinkEditPart.getChildren()){
			if(o instanceof AppliedStereotypeEmptyEditPart){
				assertTrue(STEREOTYPEDLINK+" do not refer to any AppliedStereotypeEmptyEditPart comment", true);
			}
		}
		//List of EditPart
		//DependencyNameEditPart
		//AppliedStereotypeDependencyEditPart
		//AppliedStereotypeEmptyEditPart
		assertEquals(STEREOTYPEDLINK + " has not the right number of children", 3, stereotypedLinkEditPart.getChildren().size());
		
		//Remove the stereotypes
		EList<NamedElement> elements= fixture.getModel().getOwnedMembers();
		for(NamedElement element : elements){
			EList<Stereotype> stereotypes = element.getAppliedStereotypes();
			for(Stereotype stereotype : stereotypes){
				
				if(STEREOTYPE1.compareTo(stereotype.getLabel())==0){
					TransactionalEditingDomain domain = fixture.getEditingDomain(editor);
					fixture.execute(new UnapplyStereotypeCommand(element, stereotype, domain));
				}
				
			}
		}

		//check the final model
		//List of EditPart
		//ClassNameEditPart
		//ClassAttributeCompartmentEditPart
		//ClassOperationCompartmentEditPart
		//ClassNestedclassifierCompartemtnEditPart
		//ClassFloatingNameEditPart
		//AppliedStereotypeEmptyEditPart --> removed
		assertEquals(STEREOTYPEDCLASS + " has not the right number of children", 5, stereotypedClassEditPart.getChildren().size());
		for(Object ee : stereotypedClassEditPart.getChildren()){
			EditPart a = (EditPart)ee;
			if(a instanceof AppliedStereotypeEmptyEditPart){
				assertTrue(STEREOTYPEDCLASS+" still refer to a AppliedStereotypeEmptyEditPart comment", false);
			}
		}
		//List of EditPart
		//DependencyNameEditPart
		//AppliedStereotypeDependencyEditPart
		//AppliedStereotypeEmptyEditPart --> removed
		assertEquals(STEREOTYPEDLINK + " has not the right number of children", 2, stereotypedLinkEditPart.getChildren().size());
		for(Object ee : stereotypedLinkEditPart.getChildren()){
			EditPart a = (EditPart)ee;
			if(a instanceof AppliedStereotypeEmptyEditPart){
				assertTrue(STEREOTYPEDLINK+" still refer to a AppliedStereotypeEmptyEditPart comment", false);
			}
		}
	}


}
