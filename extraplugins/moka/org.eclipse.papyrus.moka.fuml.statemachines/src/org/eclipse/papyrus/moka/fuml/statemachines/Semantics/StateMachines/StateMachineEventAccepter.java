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
 *  Jeremie Tatibouet (CEA LIST) - Based on Ed Seidewitz remarks
 *
 *****************************************************************************/
package org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.EventAccepter;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.Communications.EventOccurrence;
import org.eclipse.papyrus.moka.fuml.Semantics.Loci.LociL1.ChoiceStrategy;

public class StateMachineEventAccepter extends EventAccepter{

	// The execution that actually made the event accepter registered
	// in the object activation. Note this is particularly useful to access the
	// the configured related to the state-machine that registered this event
	// accepter.
	public StateMachineExecution registrationContext;
	
	public StateMachineEventAccepter(StateMachineExecution execution) {
		this.registrationContext = execution;
	}
	
	@Override
	public void accept(EventOccurrence eventOccurrence) {
		// When an event occurrence is accepted this marks the beginning of a new RTC step for
		// the executed state-machine. The following set of actions takes place:
		// 1 - The list of transition that can be fired using the given event occurrence is computed
		// 2 - This list is organized as a different sub-set of transitions that can be fired. One of the
		//     subset is chosen to be fired. Each transition fires **Concurrently**
		// 3 - When the RTC step is about to complete a new event accepter for the state-machine
		//     is registered at the waiting event accepter list handled by the object activation
		// Note that there always is a single event accepter for a state-machine (this works differently
		// than for activities).
		if(this.isDeferred(eventOccurrence)){
			this.defer(eventOccurrence);
		}else{
			List<TransitionActivation> fireableTransition = this.select(eventOccurrence);
			if(!fireableTransition.isEmpty()){
				int i = 0;
				while(i < fireableTransition.size()){
					fireableTransition.get(i).fire();
					i++;
				}
			}
		}
		Object_ context = this.registrationContext.context;
		if(context!=null && context.objectActivation!=null){
			context.register(new StateMachineEventAccepter(this.registrationContext));
		}
	}

	@Override
	public Boolean match(EventOccurrence eventOccurrence) {
		// There are two cases in which the state machine event accepter can match
		// 1 - In the current state machine configuration the event can be deferred
		// 2 - In the current state machine configuration the current event can trigger one or more transitions
		return this.isDeferred(eventOccurrence) | this.isTriggering(eventOccurrence);
	}
	
	
	protected boolean isDeferred(EventOccurrence eventOccurrence){
		// Determine if the dispatched event occurrence is deferred in the
		// current state machine configuration. An event occurrence can only be deferred
		// if the following conditions are fulfilled:
		// 1 - One active state in the hierarchy declares the event types as being deferred.
		// 2 - No transitions (ready to fire) with a higher priority than the deferring state
		//     could be found.
		return this._isDeferred(eventOccurrence, this.registrationContext.getConfiguration().rootConfiguration);
	}
	
	protected boolean _isDeferred(EventOccurrence eventOccurrence, StateConfiguration stateConfiguration){
		// Determine if the given state configuration is capable of deferring the given event occurrence.
		int i = 0;
		boolean deferred = false;
		while(!deferred && i < stateConfiguration.children.size()){
			deferred = this._isDeferred(eventOccurrence, stateConfiguration.children.get(i));
			i++;
		}
		if(!deferred && 
				stateConfiguration.vertexActivation != null && 
				this._select(eventOccurrence, stateConfiguration).isEmpty() &&
				((StateActivation)stateConfiguration.vertexActivation).canDefer(eventOccurrence)){
				deferred = true;
		}
		return deferred;
	}
	
	protected void defer(EventOccurrence eventOccurrence){
		// Defers the given event occurrence. A deferred event occurrence is registered in
		// the deferred event pool. This latter refers to the deferred event as well as to the
		// the deferring state.
		this._defer(eventOccurrence, this.registrationContext.getConfiguration().rootConfiguration);
	}
	
	protected boolean _defer(EventOccurrence eventOccurrence, StateConfiguration stateConfiguration){
		// Defers the given event occurrence in the context of the given state configuration.
		int i = 0;
		boolean deferred = false;
		while(!deferred && i < stateConfiguration.children.size()){
			deferred = this._defer(eventOccurrence, stateConfiguration.children.get(i));
			i++;
		}
		if(!deferred &&
				stateConfiguration.vertexActivation != null &&
				((StateActivation)stateConfiguration.vertexActivation).canDefer(eventOccurrence)){
			((StateActivation)stateConfiguration.vertexActivation).defer(eventOccurrence);
			deferred = true;
		}
		return deferred;
	}

	protected boolean isTriggering(EventOccurrence eventOccurrence){
		// Returns true when one or more transition are ready to be fired using this event
		// occurrence; false otherwise.
		return !this.select(eventOccurrence).isEmpty();
	}
	
	protected List<TransitionActivation> select(EventOccurrence eventOccurrence) {
		// Find for the given configuration the set of transition that can fire.
		return this._select(eventOccurrence, this.registrationContext.getConfiguration().rootConfiguration);
	}
	
	protected List<TransitionActivation> _select(EventOccurrence eventOccurrence, StateConfiguration stateConfiguration){
		// Find for the given state configuration all transition that can actually fire.
		// The set of transition only contains transitions with the highest priority. In addition
		// no conflicting transitions are added to that set.
		List<TransitionActivation> selectedTransitions = new ArrayList<TransitionActivation>();
		for(int i = 0; i < stateConfiguration.children.size(); i++){
			selectedTransitions.addAll(this._select(eventOccurrence, stateConfiguration.children.get(i)));
		}
		if(selectedTransitions.isEmpty() && stateConfiguration.vertexActivation != null){
			for(int i = 0; i < stateConfiguration.vertexActivation.getOutgoingTransitions().size(); i++){
				TransitionActivation transitionActivation = stateConfiguration.vertexActivation.getOutgoingTransitions().get(i);
				if(transitionActivation.canFireOn(eventOccurrence)){
					selectedTransitions.add(transitionActivation);
				}
			}
			if(selectedTransitions.size() > 1){
				ChoiceStrategy choiceStrategy = (ChoiceStrategy) this.registrationContext.locus.factory.getStrategy("choice");
				TransitionActivation electedTransition = selectedTransitions.get(choiceStrategy.choose(selectedTransitions.size()) - 1);
				selectedTransitions.clear();
				selectedTransitions.add(electedTransition);
			}
		}
		return selectedTransitions;
	}

}
