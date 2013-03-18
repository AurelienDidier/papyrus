/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.moka.fuml.standardlibrary.library.io;

import java.util.List;

import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Object_;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.StringValue;
import org.eclipse.papyrus.moka.fuml.Semantics.Classes.Kernel.Value;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.Execution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.OpaqueBehaviorExecution;
import org.eclipse.papyrus.moka.fuml.Semantics.CommonBehaviors.BasicBehaviors.ParameterValue;
import org.eclipse.papyrus.moka.fuml.debug.Debug;
import org.eclipse.papyrus.moka.fuml.registry.SystemServicesRegistryUtils;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.Operation;

public class StandardOutputChannelImpl extends Object_ {

	protected static OpaqueBehavior writeLineMethod ;
	
	public StandardOutputChannelImpl(Class service) {
		super() ;
		this.types.add(service) ;
	}

	@Override
	public Execution dispatch(Operation operation) {
		if (operation.getName().equals("writeLine"))
			return new WriteLineExecution(operation) ;
		else if (operation.getName().equals("write"))
			return new Write(operation) ;
		// TODO complete with other operations
		return null ;
	}

	protected class WriteLineExecution extends OpaqueBehaviorExecution {

		protected Operation operation ;
		
		public WriteLineExecution(Operation operation) {
			this.operation = operation ;
		}
		
		@Override
		public Value new_() {
			return new WriteLineExecution(operation) ;
		}

		@Override
		public void doBody(List<ParameterValue> inputParameters, List<ParameterValue> outputParameters) {
			// Supposed to have only one input argument, corresponding to parameter 'value'
			try {
				String message = ((StringValue)inputParameters.get(0).values.get(0)).value;
				System.out.println(message);
				// This implementation does not produce errorStatus information.
			} catch (Exception e) {
				Debug.println("An error occured during the execution of writeLine " + e.getMessage());
			}
		}

		@Override
		public Behavior getBehavior() {
			if (writeLineMethod == null) {
				writeLineMethod = SystemServicesRegistryUtils.getInstance().generateOpaqueBehaviorSignature(operation) ;
			}
			return writeLineMethod ;
		}
		
	}
	
	protected class Write extends OpaqueBehaviorExecution {

		protected Operation operation ;
		
		public Write(Operation operation) {
			this.operation = operation ;
		}
		
		@Override
		public Value new_() {
			return new Write(operation) ;
		}

		@Override
		public void doBody(List<ParameterValue> inputParameters, List<ParameterValue> outputParameters) {
			// Supposed to have only one input argument, corresponding to parameter 'value'
			try {
				String message = ((StringValue)inputParameters.get(0).values.get(0)).value;
				System.out.print(message);
				// This implementation does not produce errorStatus information.
			} catch (Exception e) {
				Debug.println("An error occured during the execution of write " + e.getMessage());
			}
		}

		@Override
		public Behavior getBehavior() {
			if (writeLineMethod == null) {
				writeLineMethod = SystemServicesRegistryUtils.getInstance().generateOpaqueBehaviorSignature(operation) ;
			}
			return writeLineMethod ;
		}
		
	}
}
