/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Jeremie Tatibouet (CEA LIST)
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.statemachines.Semantics.Loci.LociL3;

import org.eclipse.papyrus.moka.composites.Semantics.Loci.LociL3.CS_ExecutionFactory;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.SemanticVisitor;
import org.eclipse.papyrus.moka.fuml.statemachines.Semantics.Classes.Kernel.SM_OpaqueExpressionEvaluation;
import org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines.BehaviorStateMachines.FinalStateActivation;
import org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines.BehaviorStateMachines.RegionActivation;
import org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines.BehaviorStateMachines.StateActivation;
import org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines.BehaviorStateMachines.StateMachineExecution;
import org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines.BehaviorStateMachines.TransitionActivation;
import org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines.BehaviorStateMachines.Pseudostate.EntryPointActivation;
import org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines.BehaviorStateMachines.Pseudostate.ExitPointActivation;
import org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines.BehaviorStateMachines.Pseudostate.InitialPseudostateActivation;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.FinalState;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;

public class SM_ExecutionFactory extends CS_ExecutionFactory {
	
	public SemanticVisitor instantiateVisitor(Element element) {
		SemanticVisitor visitor = null ;
		if(element instanceof StateMachine){
			visitor = new StateMachineExecution();
		}else if (element instanceof Pseudostate) {
			Pseudostate pseudostate = (Pseudostate) element;
			switch(pseudostate.getKind()){
				case INITIAL_LITERAL: visitor = new InitialPseudostateActivation(); break;
				case ENTRY_POINT_LITERAL: visitor = new EntryPointActivation(); break;
				case EXIT_POINT_LITERAL: visitor = new ExitPointActivation(); break;
				default: System.out.println("Unsupported construction: "+element);break;
			}
		}else if (element instanceof State) {
			if(element instanceof FinalState){
				visitor = new FinalStateActivation();
			}else{
				visitor = new StateActivation() ;
			}
		}else if (element instanceof Transition) {
			visitor = new TransitionActivation() ;
		}else if (element instanceof Region) {
			visitor = new RegionActivation();
		}else if(element instanceof OpaqueExpression) {
			visitor = new SM_OpaqueExpressionEvaluation();
		}else {
			visitor = super.instantiateVisitor(element);
		}
		return visitor;
	}
}
