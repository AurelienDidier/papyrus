/*****************************************************************************
 * Copyright (c) 2011 Atos
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Tristan FAURE - tristan.faure@atos.net - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider;


/**
 * Interface for adaptation, if instantiated it mean it can be opened
 *
 * @author tfaure
 *
 */
public interface IOpenable {

	Object getPageIdentifier();

	static class Openable implements IOpenable {

		private final Object identifier;

		public Openable(Object identifier) {
			this.identifier = identifier;
		}

		@Override
		public Object getPageIdentifier() {
			return identifier;
		}
	}
}
