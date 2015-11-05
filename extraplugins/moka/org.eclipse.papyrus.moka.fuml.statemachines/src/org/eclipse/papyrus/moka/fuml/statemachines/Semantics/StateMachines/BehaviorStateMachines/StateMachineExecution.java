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
package org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines.BehaviorStateMachines;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.Execution;
import org.eclipse.papyrus.moka.fuml.statemachines.Semantics.StateMachines.BehaviorStateMachines.Configuration.StateMachineConfiguration;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Vertex;

/**
 * Visitor class used to execute a state-machine
 */
public class StateMachineExecution extends Execution {

	/**
	 * List of visitors associated to regions owned by the state-machine
	 */
	protected List<RegionActivation> regionActivation;
	
	/**
	 * The current "state" of state-machine 
	 */
	protected StateMachineConfiguration configuration;
	
	public StateMachineExecution(){
		super();
		this.regionActivation = new ArrayList<RegionActivation>();
		this.configuration = new StateMachineConfiguration(this);
	}
	
	public StateMachineConfiguration getConfiguration(){
		return this.configuration;
	}
	
	public StateMachineExecution(Object_ context){
		super();
		this.context = context;
		this.regionActivation = new ArrayList<RegionActivation>();
		this.configuration = new StateMachineConfiguration(this);
	}
	
	protected void initRegions(){
		StateMachine machine = null;
		if(!this.getTypes().isEmpty()){
			machine = (StateMachine) this.getTypes().get(0);
		}
		if(machine!=null){
			for(Region region: machine.getRegions()){
				RegionActivation activation = (RegionActivation) this.locus.factory.instantiateVisitor(region);
				activation.setParent(this);
				activation.setNode(region);
				this.regionActivation.add(activation);
			}
		}
	}
	
	/**
	 * Search the corresponding visitor in the hierarchy owned by this
	 * StateMachineExecution
	 * @param vertex - the vertex for which an activation is searched
	 * @return vertexActivation - the corresponding activation
	 */
	protected VertexActivation getVertexActivation(Vertex vertex){
		int i = 0;
		VertexActivation vertexActivation = null;
		while(vertexActivation==null && i < this.regionActivation.size()){
			 vertexActivation = this.regionActivation.get(i).getVertexActivation(vertex);
			 i++;
		}
		return vertexActivation;
	}
	
	@Override
	public void execute() {
		/*0. Initialization*/
		if(this.context!=null && this.context.objectActivation!=null){
			this.context.register(new SM_EventAccepter(this));
		}
		this.initRegions();
		/*1. Create visitors for all vertices*/
		for(RegionActivation activation: this.regionActivation){
			activation.activate();
		}
		/*2. Create visitors for all transitions*/
		for(RegionActivation activation: this.regionActivation){
			activation.activateTransitions();
		}
		/*3. Fire "concurrently" all initial transition in the different regions*/
		for(RegionActivation activation: this.regionActivation){
			activation.fireInitialTransition();
		}
	}

	@Override
	public Value new_() {
		if(this.context!=null){
			return new StateMachineExecution(this.context);
		}
		return new StateMachineExecution();
	}
	
	public List<RegionActivation> getRegionActivation() {
		return regionActivation;
	}
}
