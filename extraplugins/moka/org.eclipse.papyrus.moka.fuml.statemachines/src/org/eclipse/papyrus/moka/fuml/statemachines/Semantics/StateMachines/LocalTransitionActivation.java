/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
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
package org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines;

import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.Execution;
import org.eclipse.uml2.uml.Vertex;

public class LocalTransitionActivation extends TransitionActivation {

	protected StateActivation getContainingState(){
		// The container of a local transition is determined in the following manner:
		// 1 - If the source vertex is an entry point then the containing state is the
		//     the state that has this entry point on its edge
		// 2 - Else in the case where the source contains the target then the containing
		//	   state is the source itself. Otherwise the source is the target
		StateActivation containingState = null; 
		if(this.vertexSourceActivation instanceof EntryPointActivation){
			containingState = (StateActivation) this.vertexSourceActivation.getParentState();
		}else{
			if(this.vertexSourceActivation.getVertexActivation((Vertex)this.vertexTargetActivation.getNode())!=null){
				containingState = (StateActivation) this.vertexSourceActivation;
			}else{
				containingState = (StateActivation) this.vertexTargetActivation;
			}
		}
		return containingState;
	}
	
	@Override
	protected void exitSource() {
		// Exiting the source state of a local transition consists in the following set of actions:
		// 1 - Check if the source state can be exited (if it cannot then do nothing)
		// 2 - If the source can be exited and this latter is an entry point then the exit
		//     of the source is trivial (i.e., it only consists in exiting the entry point)
		//   - If the source can be exited and this latter is a composite state then the top
		//     vertex that is located in the top region which contains (maybe in deep nesting)
		//     the tar get is exited. At this point, if the source is not the containing state
		//     then it is also exited
		StateActivation containingState = this.getContainingState();
		if(this.vertexSourceActivation.isExitable(this)){
			if(this.vertexSourceActivation instanceof EntryPointActivation){
				this.vertexSourceActivation.exit(this, null);
			}else{
				int i=0;
				RegionActivation containingRegion = null; 
				while(containingRegion==null && i < containingState.regionActivation.size()){
					RegionActivation regionActivation = containingState.regionActivation.get(i);
					if(regionActivation.getVertexActivation((Vertex)this.vertexTargetActivation.getNode())!=null){
						containingRegion = regionActivation;
					}
					i++;
				}
				if(containingRegion!=null){
					i = 0;
					VertexActivation vertexActivationToBeExited = null;
					Execution stateMachineExecution = this.getStateMachineExecution();
					while(vertexActivationToBeExited == null && i < containingRegion.vertexActivations.size()){
						VertexActivation currentActivation = containingRegion.vertexActivations.get(i);
						if(((StateMachineExecution)stateMachineExecution).getConfiguration().isActive(currentActivation)){
							vertexActivationToBeExited = currentActivation;
						}
						i++;
					}
					if(vertexActivationToBeExited != null){
						vertexActivationToBeExited.exit(this, null);
					}
				}
				if(this.vertexSourceActivation != containingState){
					this.vertexSourceActivation.exit(this, this.getLeastCommonAncestor());
				}
			}
		}
	}
	
	@Override
	protected void enterTarget() {
		// Entering the target of local transition consists in checking if the target can be entered. If
		// this is the case then only when the target is not also the containing state it is entered 
		if(this.vertexTargetActivation.isEnterable(this)){	
			if(this.vertexTargetActivation != this.getContainingState()){
				this.vertexTargetActivation.enter(this, this.getLeastCommonAncestor());
			}
		}
	}
}
