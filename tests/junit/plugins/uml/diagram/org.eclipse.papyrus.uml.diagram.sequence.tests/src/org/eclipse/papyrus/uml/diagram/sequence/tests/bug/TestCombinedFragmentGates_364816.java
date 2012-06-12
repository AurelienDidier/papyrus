package org.eclipse.papyrus.uml.diagram.sequence.tests.bug;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequestFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.core.extension.commands.ICreationCommand;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.CombinedFragmentEditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.Message2EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.Message6EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.edit.parts.Message7EditPart;
import org.eclipse.papyrus.uml.diagram.sequence.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.sequence.tests.canonical.CreateSequenceDiagramCommand;
import org.eclipse.papyrus.uml.diagram.sequence.tests.canonical.TestLink;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Message;
import org.junit.Test;

/**
A different gate is created for each message that have a combined fragment as
source or destination. This is incompatible with the UML standard. The first
gate should be used as a source by the second message. A dialog box allowing
the selection of an existing gate on a combined fragment would allow the reuse
of that gate.
 */
public class TestCombinedFragmentGates_364816 extends TestLink {
	protected ICreationCommand getDiagramCommandCreation() {
		return  new CreateSequenceDiagramCommand();
	}
	
	@Test
	public void testMessageAsyc_Gate() {
		installEnvironment(UMLElementTypes.Lifeline_3001, UMLElementTypes.CombinedFragment_3004);
		CombinedFragmentEditPart cep = (CombinedFragmentEditPart)target;
		CombinedFragment cf = (CombinedFragment)cep.resolveSemanticElement();

		createLink( UMLElementTypes.Message_4004, source, target, getAbsoluteCenter(source), getLeft(target));
		assertTrue("", source.getSourceConnections().size() == 1);
		assertTrue("", source.getSourceConnections().get(0) instanceof Message2EditPart);
		assertTrue("Gate size: ", cf.getCfragmentGates().size() == 1);
		
		PopupUtil.addDialogCloseHandler();
		createLink( UMLElementTypes.Message_4004, source, target, getAbsoluteCenter(source).translate(0, 40), getLeft(target).translate(0, 40));
		waitForComplete();
		assertTrue("", source.getSourceConnections().size() == 2);
		assertTrue("", source.getSourceConnections().get(1) instanceof Message2EditPart);
		assertTrue("Gate size: ", cf.getCfragmentGates().size() == 1);

		Message2EditPart conn1 = (Message2EditPart)source.getSourceConnections().get(0);
		Message2EditPart conn2 = (Message2EditPart)source.getSourceConnections().get(1);
		assertTrue("Gate : ", ((Message)conn1.resolveSemanticElement()).getReceiveEvent() == cf.getCfragmentGates().get(0));
		assertTrue("Gate : ", ((Message)conn2.resolveSemanticElement()).getReceiveEvent() == cf.getCfragmentGates().get(0));
	}
	
	@Test
	public void testMessageFound_Gate() {
		installEnvironment(UMLElementTypes.Lifeline_3001, UMLElementTypes.CombinedFragment_3004);
		source = (GraphicalEditPart)source.getParent().getParent();  //interaction
		
		CombinedFragmentEditPart cep = (CombinedFragmentEditPart)target;
		CombinedFragment cf = (CombinedFragment)cep.resolveSemanticElement();

		createLink( UMLElementTypes.Message_4009, source, target, new Point(0,0), getLeft(target));
		assertTrue("", source.getSourceConnections().size() == 1);
		assertTrue("", source.getSourceConnections().get(0) instanceof Message7EditPart);
		assertTrue("Gate size: ", cf.getCfragmentGates().size() == 1);
		
		PopupUtil.addDialogCloseHandler();
		createLink( UMLElementTypes.Message_4009, source, target,new Point(0,20), getLeft(target).translate(0, 40));
		waitForComplete();
		assertTrue("", source.getSourceConnections().size() == 2);
		assertTrue("", source.getSourceConnections().get(1) instanceof Message7EditPart);
		assertTrue("Gate size: ", cf.getCfragmentGates().size() == 1);

		Message7EditPart conn1 = (Message7EditPart)source.getSourceConnections().get(0);
		Message7EditPart conn2 = (Message7EditPart)source.getSourceConnections().get(1);
		assertTrue("Gate : ", ((Message)conn1.resolveSemanticElement()).getReceiveEvent() == cf.getCfragmentGates().get(0));
		assertTrue("Gate : ", ((Message)conn2.resolveSemanticElement()).getReceiveEvent() == cf.getCfragmentGates().get(0));		
	}
	
	@Test
	public void testMessageLost_Gate() {
		installEnvironment(UMLElementTypes.CombinedFragment_3004 ,UMLElementTypes.Lifeline_3001 );
		target = (GraphicalEditPart)target.getParent().getParent();  //interaction
		
		CombinedFragmentEditPart cep = (CombinedFragmentEditPart)source;
		CombinedFragment cf = (CombinedFragment)cep.resolveSemanticElement();

		createLink( UMLElementTypes.Message_4008, source, target, getLeft(source), new Point(0, 150));
		assertTrue("", source.getSourceConnections().size() == 1);
		assertTrue("", source.getSourceConnections().get(0) instanceof Message6EditPart);
		assertTrue("Gate size: ", cf.getCfragmentGates().size() == 1);
		
		PopupUtil.addDialogCloseHandler();
		createLink( UMLElementTypes.Message_4008, source, target,getLeft(source), new Point(0, 200));
		waitForComplete();
		assertTrue("", source.getSourceConnections().size() == 2);
		assertTrue("", source.getSourceConnections().get(1) instanceof Message6EditPart);
		assertTrue("Gate size: ", cf.getCfragmentGates().size() == 1);

		Message6EditPart conn1 = (Message6EditPart)source.getSourceConnections().get(0);
		Message6EditPart conn2 = (Message6EditPart)source.getSourceConnections().get(1);
		assertTrue("Gate : ", ((Message)conn1.resolveSemanticElement()).getSendEvent() == cf.getCfragmentGates().get(0));
		assertTrue("Gate : ", ((Message)conn2.resolveSemanticElement()).getSendEvent() == cf.getCfragmentGates().get(0));		
	}
	
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		waitForComplete();
		PopupUtil.removeDialogCloseHandler();
	}
	
	public void createLink(IElementType linkType, EditPart source, EditPart target, Point sourcePoint, Point targetPoint) {
		CreateConnectionViewRequest req = createConnectionViewRequest(linkType, source, target, sourcePoint, targetPoint);
		Command command = target.getCommand(req);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CONTAINER_CREATION + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true);

		getDiagramCommandStack().execute(command);
	}

	CreateConnectionViewRequest createConnectionViewRequest(IElementType type, EditPart source, EditPart target, Point sourcePoint, Point targetPoint) {
		CreateConnectionViewRequest connectionRequest = CreateViewRequestFactory.getCreateConnectionRequest(type, ((IGraphicalEditPart)getDiagramEditPart()).getDiagramPreferencesHint());
		connectionRequest.setLocation(sourcePoint);

		connectionRequest.setSourceEditPart(null);
		connectionRequest.setTargetEditPart(source);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_START);
		Command cmd = source.getCommand(connectionRequest);
		// Now, setup the request in preparation to get the
		// connection end
		// command.
		connectionRequest.setSourceEditPart(source);
		connectionRequest.setTargetEditPart(target);
		connectionRequest.setType(RequestConstants.REQ_CONNECTION_END);
		connectionRequest.setLocation(targetPoint);
		
		EObject container = getRootEditPart().resolveSemanticElement();
		connectionRequest.getExtendedData().put(SOURCE_MODEL_CONTAINER, container);
		connectionRequest.getExtendedData().put(TARGET_MODEL_CONTAINER, container);

		return connectionRequest;
	}

	public void createNode(IElementType type, GraphicalEditPart parent, Point location, Dimension size) {
		//CREATION
		CreateViewRequest requestcreation = CreateViewRequestFactory.getCreateShapeRequest(type, getRootEditPart().getDiagramPreferencesHint());
		requestcreation.setLocation(location);
		requestcreation.setSize(size);
		Command command = parent.getCommand(requestcreation);
		assertNotNull(CREATION + COMMAND_NULL, command);
		assertTrue(CREATION + TEST_IF_THE_COMMAND_IS_CREATED, command != UnexecutableCommand.INSTANCE);
		assertTrue("CREATION: " + TEST_IF_THE_COMMAND_CAN_BE_EXECUTED, command.canExecute() == true);

		getDiagramCommandStack().execute(command);
	}

	public void installEnvironment(IElementType sourceType, IElementType targetType) {
		//create the source
		createNode(sourceType, getRootEditPart(), new Point(100, 100), new Dimension(62, 250));

		//create the target
		createNode(targetType, getRootEditPart(), new Point(300, 100), new Dimension(200, 200));

		source = (GraphicalEditPart)getRootEditPart().getChildren().get(0);
		target = (GraphicalEditPart)getRootEditPart().getChildren().get(1);
	}
	
	protected Point getLeft(IGraphicalEditPart part) {
		IFigure f = part.getFigure();
		Rectangle b = f.getBounds().getCopy();
		f.translateToAbsolute(b);
		Point c = b.getLeft().getCopy();
		return c;
	}
}
	