package org.eclipse.papyrus.sirius.clazz.diagram;

import org.eclipse.ui.IStartup;

/**
 * Early start-up hook for the externalized profile applications subsystem.
 */
public class Startup implements IStartup {

	public Startup() {
		super();
	}

	@Override
	public void earlyStartup() {
		// Kick the index
		Activator.getDefault();
	}
}
