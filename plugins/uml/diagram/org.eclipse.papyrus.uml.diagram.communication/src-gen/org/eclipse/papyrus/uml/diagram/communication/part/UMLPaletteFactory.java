/**
 * Copyright (c) 2014 CEA LIST.
  * 
  * All rights reserved. This program and the accompanying materials
  * are made available under the terms of the Eclipse Public License 2.0
  * which accompanies this distribution, and is available at
  * https://www.eclipse.org/legal/epl-2.0/
  *
  * SPDX-License-Identifier: EPL-2.0
  * 
  * Contributors:
  *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.communication.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.Tool;
import org.eclipse.gmf.runtime.diagram.ui.services.palette.PaletteFactory;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeConnectionTool;
import org.eclipse.papyrus.infra.gmfdiag.common.service.palette.AspectUnspecifiedTypeCreationTool;
import org.eclipse.papyrus.uml.diagram.communication.providers.UMLElementTypes;

/**
 * @generated
 */
public class UMLPaletteFactory extends PaletteFactory.Adapter {
	//RS: New Palette generation

	//Generates the ID for the tool elements
	//Generate the tool factory (if(ID) createtool...)
	/**
	 * @generated
	 */
	private final static String COMMUNICATION_TOOL_LIFELINE = "communication.tool.lifeline";//$NON-NLS-1$
	/**
	 * @generated
	 */
	private final static String COMMUNICATION_TOOL_COMMENT = "communication.tool.comment";//$NON-NLS-1$
	/**
	 * @generated
	 */
	private final static String COMMUNICATION_TOOL_CONSTRAINT = "communication.tool.constraint";//$NON-NLS-1$
	/**
	 * @generated
	 */
	private final static String COMMUNICATION_TOOL_DURATIONOBSERVATION = "communication.tool.durationobservation";//$NON-NLS-1$
	/**
	 * @generated
	 */
	private final static String COMMUNICATION_TOOL_TIMEOBSERVATION = "communication.tool.timeobservation";//$NON-NLS-1$
	/**
	 * @generated
	 */
	private final static String CREATEMESSAGECREATECREATIONTOOL = "createMessageCreateCreationTool";//$NON-NLS-1$
	/**
	 * @generated
	 */
	private final static String CREATELINK13CREATIONTOOL = "createLink13CreationTool";//$NON-NLS-1$

	/**
	 * @generated
	 */
	public UMLPaletteFactory() {

	}

	/**
	 * @generated
	 */
	public Tool createTool(String toolId) {
		if (toolId.equals(COMMUNICATION_TOOL_LIFELINE)) {
			return createLifelineComCreationTool();
		}
		if (toolId.equals(COMMUNICATION_TOOL_COMMENT)) {
			return createComment7CreationTool();
		}
		if (toolId.equals(COMMUNICATION_TOOL_CONSTRAINT)) {
			return createConstraint5CreationTool();
		}
		if (toolId.equals(COMMUNICATION_TOOL_DURATIONOBSERVATION)) {
			return createDurationObservation4CreationTool();
		}
		if (toolId.equals(COMMUNICATION_TOOL_TIMEOBSERVATION)) {
			return createTimeObservation6CreationTool();
		}
		if (toolId.equals(CREATEMESSAGECREATECREATIONTOOL)) {
			return createMessageCreateCreationTool();
		}
		if (toolId.equals(CREATELINK13CREATIONTOOL)) {
			return createLink13CreationTool();
		}
		// default return: null
		return null;
	}

	/**
	 * @generated
	 */
	public Object getTemplate(String templateId) {

		// default return: null
		return null;
	}

	/**
	 * @generated
	 */
	private Tool createLifelineComCreationTool() {
		List<IElementType> types = new ArrayList<IElementType>(1);
		types.add(UMLElementTypes.Lifeline_Shape);
		Tool tool = new AspectUnspecifiedTypeCreationTool(types);
		return tool;
	}

	/**
	 * @generated
	 */
	private Tool createComment7CreationTool() {
		List<IElementType> types = new ArrayList<IElementType>(1);
		types.add(UMLElementTypes.Comment_Shape);
		Tool tool = new AspectUnspecifiedTypeCreationTool(types);
		return tool;
	}

	/**
	 * @generated
	 */
	private Tool createConstraint5CreationTool() {
		List<IElementType> types = new ArrayList<IElementType>(1);
		types.add(UMLElementTypes.Constraint_Shape);
		Tool tool = new AspectUnspecifiedTypeCreationTool(types);
		return tool;
	}

	/**
	 * @generated
	 */
	private Tool createDurationObservation4CreationTool() {
		List<IElementType> types = new ArrayList<IElementType>(1);
		types.add(UMLElementTypes.DurationObservation_Shape);
		Tool tool = new AspectUnspecifiedTypeCreationTool(types);
		return tool;
	}

	/**
	 * @generated
	 */
	private Tool createTimeObservation6CreationTool() {
		List<IElementType> types = new ArrayList<IElementType>(1);
		types.add(UMLElementTypes.TimeObservation_Shape);
		Tool tool = new AspectUnspecifiedTypeCreationTool(types);
		return tool;
	}

	/**
	 * @generated
	 */
	private Tool createMessageCreateCreationTool() {
		List<IElementType> types = new ArrayList<IElementType>(1);
		types.add(UMLElementTypes.Path_Edge);
		Tool tool = new AspectUnspecifiedTypeConnectionTool(types);
		return tool;
	}

	/**
	 * @generated
	 */
	private Tool createLink13CreationTool() {
		List<IElementType> types = new ArrayList<IElementType>(4);
		types.add(UMLElementTypes.Comment_AnnotatedElementEdge);
		types.add(UMLElementTypes.Constraint_ConstrainedElementEdge);
		types.add(UMLElementTypes.DurationObservation_EventEdge);
		types.add(UMLElementTypes.TimeObservation_EventEdge);
		Tool tool = new AspectUnspecifiedTypeConnectionTool(types);
		return tool;
	}

}
