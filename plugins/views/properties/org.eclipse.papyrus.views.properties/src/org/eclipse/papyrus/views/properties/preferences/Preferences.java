/*****************************************************************************
 * Copyright (c) 2010, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 482930
 *  Christian W. Damus - bug 482927
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.preferences;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.papyrus.views.properties.contexts.Context;
import org.eclipse.papyrus.views.properties.messages.Messages;
import org.eclipse.papyrus.views.properties.runtime.ConfigurationConflict;
import org.eclipse.papyrus.views.properties.runtime.ConfigurationManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * The PreferencePage for the Papyrus Property View. Offers an UI to enable or disable
 * property view contexts.
 *
 * @author Camille Letavernier
 */
public class Preferences extends PreferencePage implements IWorkbenchPreferencePage {

	@Override
	public void init(IWorkbench workbench) {
		// Nothing
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite self = new Composite(parent, SWT.NONE);
		self.setLayout(new GridLayout(1, false));
		self.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Label label = new Label(self, SWT.NONE);
		label.setText(Messages.Preferences_Contexts);

		final ConfigurationManager configurationManager = ConfigurationManager.getInstance();

		// Only customizable Property view contexts should appear here
		List<Context> contexts = new java.util.ArrayList<Context>(configurationManager.getCustomizableContexts());
		contexts.addAll(configurationManager.getMissingContexts());
		Collections.sort(contexts, contextOrdering());

		for (Context context : contexts) {
			boolean applied = configurationManager.isApplied(context);
			Button checkbox = new Button(self, SWT.CHECK);
			checkbox.setText(getLabel(context));
			checkbox.setSelection(applied);
			final Context theContext = context;
			contextState.setContextState(theContext, applied);

			checkbox.addSelectionListener(new SelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					contextState.setContextState(theContext, ((Button) e.widget).getSelection());
				}

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// Nothing
				}

			});

			checkboxes.put(context, checkbox);
		}

		contextState.snapshot();
		return null;
	}

	protected Comparator<? super Context> contextOrdering() {
		return Comparator.comparingInt(this::getCategory).thenComparing(
				Comparator.comparing(Context::getUserLabel));
	}

	protected int getCategory(Context context) {
		ConfigurationManager mgr = ConfigurationManager.getInstance();
		return mgr.isCustomizable(context)
				? mgr.isPlugin(context) ? 0 : 1
				: 1000;
	}

	@Override
	public boolean performOk() {
		return contextState.saveContext() && super.performOk();
	}

	@Override
	public void performApply() {
		contextState.saveContext();
	}

	@Override
	public void performDefaults() {
		for (Context context : ConfigurationManager.getInstance().getContexts()) {
			boolean applied = ConfigurationManager.getInstance().isAppliedByDefault(context);
			Button checkbox = checkboxes.get(context);
			if (checkbox != null) {
				checkbox.setSelection(applied);
				contextState.setContextState(context, applied);
			}
		}
	}

	private String getLabel(Context context) {
		String qualifier;

		if (ConfigurationManager.getInstance().isPlugin(context)) {
			qualifier = Messages.Preferences_Plugin;
		} else if (ConfigurationManager.getInstance().isMissing(context)) {
			qualifier = "missing";
		} else {
			qualifier = Messages.Preferences_Custom;
		}

		return String.format("%s (%s)", context.getUserLabel(), qualifier); //$NON-NLS-1$
	}

	private final ContextState contextState = new ContextState();

	private Map<Context, Button> checkboxes = new HashMap<Context, Button>();

	private class ContextState {

		private Map<Context, Boolean> contexts = new HashMap<>();

		private Map<Context, Boolean> snapshot;

		ContextState() {
			super();
		}

		void snapshot() {
			snapshot = new HashMap<>(contexts);
		}

		public void setContextState(Context context, boolean applied) {
			contexts.put(context, applied);
		}

		public boolean saveContext() {
			for (Entry<Context, Boolean> entry : contexts.entrySet()) {
				if (entry.getValue()) {
					ConfigurationManager.getInstance().enableContext(entry.getKey(), false);
				} else {
					ConfigurationManager.getInstance().disableContext(entry.getKey(), false);
				}
			}

			ConfigurationManager.getInstance().update();

			Set<Context> delta = getChangedContexts();
			if (!delta.isEmpty()) {
				Collection<ConfigurationConflict> conflicts = ConfigurationManager.getInstance().checkConflicts(delta);

				if (!conflicts.isEmpty()) {
					String errorMessage = Messages.Preferences_ConflictWarning1;
					for (ConfigurationConflict conflict : conflicts) {
						errorMessage += conflict.toString() + "\n"; //$NON-NLS-1$
					}
					errorMessage += Messages.Preferences_ConflictWarning2;

					MessageDialog dialog = new MessageDialog(getShell(), Messages.Preferences_ConflictWarningTitle, null, errorMessage, MessageDialog.WARNING,
							new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL },
							1);
					int result = dialog.open();
					if (result != 0) {
						return false;
					}
				}

				snapshot();
			}

			return true;
		}

		Set<Context> getChangedContexts() {
			return (snapshot == null)
					? contexts.keySet()
					: snapshot.keySet().stream()
							.filter(c -> !Objects.equals(snapshot.get(c), contexts.get(c)))
							.collect(Collectors.toSet());
		}
	}

}
