/**
 * Copyright (c) 2017 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors:
 *  Maged Elaasar - Initial API and implementation
 *  
 * 
 */
package org.eclipse.papyrus.infra.core.architecture.merged;

/**
 * An element that represents a merged collection of {@link org.eclipse.papyrus.infra.core.
 * architecture.ArchitectureFramework}s. This allows the definition of architecture 
 * framework to be split across several architectural models (*.architecture). 
 * 
 * This class is a subclass of {@link org.eclipse.papyrus.infra.core.architecture.merged.
 * MergedArchitectureContext}s
 *  
 * @see org.eclipse.papyrus.infra.core.architecture.ArchitectureFramework
 * @since 1.0
 */
public class MergedArchitectureFramework extends MergedArchitectureContext {

	/**
	 * Create a new '<em><b>Merged Architecture Framework</b></em>'.
	 *
	 * @param domain the merged parent domain of this framework
	 */
	public MergedArchitectureFramework(MergedArchitectureDomain domain) {
		super(domain);
	}

}
