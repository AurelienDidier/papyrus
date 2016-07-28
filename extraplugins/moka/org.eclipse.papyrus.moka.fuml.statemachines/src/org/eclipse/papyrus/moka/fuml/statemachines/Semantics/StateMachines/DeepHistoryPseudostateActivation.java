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


import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.EventOccurrence;
import org.eclipse.uml2.uml.Vertex;

public class DeepHistoryPseudostateActivation extends HistoryPseudostateActivation {
	
	protected boolean canRestore(StateActivation stateActivation){
		// A state activation can only be restored if it is contained that is istelf
		// contained in a region from which the the region containing the history state
		// can be reached (directly or indirectly).
		boolean canRestore = false;
		if(stateActivation != null){
			RegionActivation historyRegion = (RegionActivation) this.getParent(); 
			if(historyRegion.getVertexActivation((Vertex)stateActivation.getNode()) != null){
				canRestore = true;
			}
		}
		return canRestore;
	}
	
	public void restore(RegionActivation regionActivation, TransitionActivation enteringTransition, EventOccurrence eventOccurrence){
		// Restore the execution of a Region. 
		// If the Region restored is the region which contained
		// the deep history then two executions are possible:
		// 1. If the restored region has already been entered then its last known configuration 
		//    (i.e. active state) is restored.
		// 2. If the restored region has never been entered then if the deep history has an outgoing
		//    transition then this transition is fired to force the entrance of the target state.
		// If the region restored is not the region which contains the deep history then
		// two execution are possible:
		// 1. If the region already has an history then this history is restored
		// 2. If the region has no history then it is entered using the implicit rule.
		regionActivation.isCompleted = false;
		if(regionActivation == this.getParent()){
			if(regionActivation.history != null){
				this.restore(regionActivation.history, enteringTransition, eventOccurrence);
			}else{
				if(this.outgoingTransitionActivations.size()==1){
					this.outgoingTransitionActivations.get(0).fire(eventOccurrence);
				}
			}
		}else{
			if(this.canRestore(regionActivation.history)){
				this.restore(regionActivation.history, enteringTransition, eventOccurrence);
			}else{
				regionActivation.enter(enteringTransition, eventOccurrence);
			}
		}	
	}
}
