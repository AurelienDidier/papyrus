/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.profile.custom.requests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;

/**
 * looks like org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest
 *
 * This class was created to use the CustomCreateElementRequestAdapter
 *
 */
public class CustomCreateViewRequest extends org.eclipse.gef.requests.CreateRequest {
	/**
	 * A view descriptor that contains attributes needed to create the view The
	 * class is also a mutable adapter that initially adapts to nothing, but
	 * can adapt to <code> IView </code> after the view has been created (by
	 * executing the creation command returned from edit policies in response
	 * to this request) This is how GEF works!!
	 */
	public static class ViewDescriptor implements IAdaptable {
		/** the element adapter */
		private final IAdaptable elementAdapter;
		/** the view kind */
		private final Class<?> viewKind;
		/** the semantic hint */
		private String semanticHint;
		/** the index */
		private final int index;
		/** The underlying view element */
		private View view;
		/** persisted view flag. */
		private boolean _persisted;
		/**
		 * The hint used to find the appropriate preference store from which general
		 * diagramming preference values for properties of shapes, connections, and
		 * diagrams can be retrieved. This hint is mapped to a preference store in
		 * the {@link DiagramPreferencesRegistry}.
		 */
		private PreferencesHint preferencesHint;

		/**
		 * Creates a new view descriptor using element adapter
		 *
		 * @param elementAdapter
		 *            the element adapter referened by the view
		 * @param preferencesHint
		 *            The preference hint that is to be used to find the appropriate
		 *            preference store from which to retrieve diagram preference
		 *            values. The preference hint is mapped to a preference store in
		 *            the preference registry <@link DiagramPreferencesRegistry>.
		 */
		public ViewDescriptor(IAdaptable elementAdapter, PreferencesHint preferencesHint) {
			this(elementAdapter, Node.class, preferencesHint);
		}

		/**
		 * Creates a new view descriptor using element adapter and a view kind
		 *
		 * @param elementAdapter
		 *            the element adapter referened by the view
		 * @param viewkind
		 *            the kind of the view to be created (a concrete class
		 *            derived from IView)
		 * @param preferencesHint
		 *            The preference hint that is to be used to find the appropriate
		 *            preference store from which to retrieve diagram preference
		 *            values. The preference hint is mapped to a preference store in
		 *            the preference registry <@link DiagramPreferencesRegistry>.
		 */
		public ViewDescriptor(IAdaptable elementAdapter, Class<?> viewkind, PreferencesHint preferencesHint) {
			this(elementAdapter, viewkind, "", preferencesHint); //$NON-NLS-1$
		}

		/**
		 * Creates a new view descriptor using element adapter and a view kind
		 *
		 * @param elementAdapter
		 *            the element adapter referened by the view
		 * @param viewkind
		 *            the kind of the view to be created (a concrete class
		 *            derived from IView)
		 * @param persisted
		 *            indicates if the view will be created as a persisted
		 *            view or transient
		 * @param preferencesHint
		 *            The preference hint that is to be used to find the appropriate
		 *            preference store from which to retrieve diagram preference
		 *            values. The preference hint is mapped to a preference store in
		 *            the preference registry <@link DiagramPreferencesRegistry>.
		 */
		public ViewDescriptor(IAdaptable elementAdapter, Class<?> viewkind, boolean persisted, PreferencesHint preferencesHint) {
			this(elementAdapter, viewkind, "", persisted, preferencesHint); //$NON-NLS-1$
		}

		/**
		 * Creates a new view descriptor using element adapter, a view kind and
		 * a factory hint
		 *
		 * @param elementAdapter
		 *            the element adapter referened by the view
		 * @param viewkind
		 *            the kind of the view to be created
		 * @param semanticHint
		 *            the semantic hint of the view
		 * @param preferencesHint
		 *            The preference hint that is to be used to find the appropriate
		 *            preference store from which to retrieve diagram preference
		 *            values. The preference hint is mapped to a preference store in
		 *            the preference registry <@link DiagramPreferencesRegistry>.
		 */
		public ViewDescriptor(IAdaptable elementAdapter, Class<?> viewkind, String semanticHint, PreferencesHint preferencesHint) {
			this(elementAdapter, viewkind, semanticHint, ViewUtil.APPEND, preferencesHint);
		}

		/**
		 * Creates a new view descriptor using element adapter, a view kind and
		 * a factory hint
		 *
		 * @param elementAdapter
		 *            the element adapter referened by the view
		 * @param viewkind
		 *            the kind of the view to be created
		 * @param semanticHint
		 *            the semantic hint of the view
		 * @param persisted
		 *            indicates if the view will be created as a persisted
		 *            view or transient
		 * @param preferencesHint
		 *            The preference hint that is to be used to find the appropriate
		 *            preference store from which to retrieve diagram preference
		 *            values. The preference hint is mapped to a preference store in
		 *            the preference registry <@link DiagramPreferencesRegistry>.
		 */
		public ViewDescriptor(IAdaptable elementAdapter, Class<?> viewkind, String semanticHint, boolean persisted, PreferencesHint preferencesHint) {
			this(elementAdapter, viewkind, semanticHint, ViewUtil.APPEND, persisted, preferencesHint);
		}

		/**
		 * Creates a new view descriptor using the supplied element adapter,
		 * view kind, factory hint and index.
		 * <P>
		 * Same as calling <code>new ViewDescriptor(elementAdapter, viewKind, factoryHint, index, true);</code>
		 *
		 * @param elementAdapter
		 *            the element adapter referened by the view
		 * @param viewKind
		 *            the kind of the view to be created (a concrete class derived from View)
		 * @param factoryHint
		 *            the semantic hint of the view
		 * @param index
		 *            the index of the view in its parent's children collection
		 */
		public ViewDescriptor(IAdaptable elementAdapter, Class<?> viewKind, String factoryHint, int index, PreferencesHint preferencesHint) {
			this(elementAdapter, viewKind, factoryHint, index, true, preferencesHint);
		}

		/**
		 * Creates a new view descriptor using the supplied element adapter,
		 * view kind, factory hint, index and persistence flag.
		 *
		 * @param elementAdapter
		 *            the element adapter referened by the view
		 * @param viewKind
		 *            the kind of the view to be created (a concrete class derived from View)
		 * @param semanticHint
		 *            the semantic hint of the view
		 * @param index
		 *            the index of the view in its parent's children collection
		 * @param persisted
		 *            set <true> to create a persisted (attached) view; <tt>false</tt> to create a detached (non-persisted) view.
		 */
		public ViewDescriptor(IAdaptable elementAdapter, Class<?> viewKind, String semanticHint, int index, boolean persisted, PreferencesHint preferencesHint) {
			Assert.isNotNull(viewKind);
			Assert.isTrue(index >= ViewUtil.APPEND);
			this.elementAdapter = elementAdapter;
			this.viewKind = viewKind;
			this.semanticHint = semanticHint;
			this.index = index;
			_persisted = persisted;
			this.preferencesHint = preferencesHint;
		}

		/**
		 * Adapts to IView
		 *
		 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(Class)
		 */
		@SuppressWarnings("rawtypes")
		public Object getAdapter(Class adapter) {
			if (adapter.isInstance(view)) {
				return view;
			}
			return null;
		}

		/**
		 * Method setView.
		 *
		 * @param view
		 */
		public void setView(View view) {
			this.view = view;
		}

		/**
		 * Method setPersisted.
		 *
		 * @param persisted
		 */
		public void setPersisted(boolean persisted) {
			_persisted = persisted;
		}

		/**
		 * Method getelementAdapter.
		 *
		 * @return IAdaptable
		 */
		public IAdaptable getElementAdapter() {
			return elementAdapter;
		}

		/**
		 * Method getViewKind.
		 *
		 * @return Class
		 */
		public Class<?> getViewKind() {
			return viewKind;
		}

		/**
		 * Method getSemanticHint.
		 *
		 * @return String
		 */
		public String getSemanticHint() {
			return semanticHint;
		}

		/**
		 * Method getIndex.
		 *
		 * @return int
		 */
		public int getIndex() {
			return index;
		}

		/**
		 * Return <tt>true</tt> if the view will be persisted; otherwise <tt>false</tt>
		 *
		 * @return <code>true</code> or <code>false</code>
		 */
		public final boolean isPersisted() {
			return _persisted;
		}

		/**
		 * Gets the preferences hint that is to be used to find the appropriate
		 * preference store from which to retrieve diagram preference values. The
		 * preference hint is mapped to a preference store in the preference
		 * registry <@link DiagramPreferencesRegistry>.
		 *
		 * @return the preferences hint
		 */
		public PreferencesHint getPreferencesHint() {
			return preferencesHint;
		}

		public IAdaptable getElementAdapter(int i) {
			if (elementAdapter instanceof CustomCreateElementRequestAdapter) {
				return ((CustomCreateElementRequestAdapter) elementAdapter).getRequestAdapterDUMMY(i);
			} else {
				return getElementAdapter();
			}
		}

		public ArrayList<CreateElementRequestAdapter> getRequestAdapters() {
			if (elementAdapter instanceof CustomCreateElementRequestAdapter) {
				return ((CustomCreateElementRequestAdapter) elementAdapter).getRequestAdapters();
			} else {
				return null;
			}
		}
	}

	/**
	 * The view descriptors set by the user
	 */
	private List<? extends ViewDescriptor> viewDescriptors;

	/**
	 * Convenience constructor for CreateViewRequest using a <code>IElement</code>
	 *
	 * @param element
	 *            a semantic element
	 * @param preferencesHint
	 *            The preference hint that is to be used to find the appropriate
	 *            preference store from which to retrieve diagram preference
	 *            values. The preference hint is mapped to a preference store in
	 *            the preference registry <@link DiagramPreferencesRegistry>.
	 */
	public CustomCreateViewRequest(EObject element, PreferencesHint preferencesHint) {
		this(new ViewDescriptor(new EObjectAdapter(element), preferencesHint));
	}

	/**
	 * Constructor for CreateViewRequest using a <code>ViewDescriptor</code>
	 *
	 * @param viewDescriptor
	 *            a view descriptor
	 */
	public CustomCreateViewRequest(ViewDescriptor viewDescriptor) {
		this(Collections.singletonList(viewDescriptor));
	}

	/**
	 * Constructor for CreateViewRequest using a list of <code>ViewDescriptor</code> s
	 *
	 * @param viewDescriptors
	 *            a list of view descriptors
	 */
	public CustomCreateViewRequest(List<? extends ViewDescriptor> viewDescriptors) {
		Assert.isNotNull(viewDescriptors);
		this.viewDescriptors = viewDescriptors;
		setLocation(new Point(-1, -1));
	}

	/**
	 * Constructor for CreateViewRequest using a request type and a <code>ViewDescriptor</code>
	 *
	 * @param type
	 *            request type
	 * @param viewDescriptor
	 *            a view descriptor
	 */
	public CustomCreateViewRequest(Object type, ViewDescriptor viewDescriptor) {
		this(type, Collections.singletonList(viewDescriptor));
	}

	/**
	 * Constructor for CreateViewRequest using a request type and list of <code>ViewDescriptor</code> s
	 *
	 * @param type
	 *            the request type
	 * @param viewDescriptors
	 *            a list of view descriptors
	 */
	public CustomCreateViewRequest(Object type, List<? extends ViewDescriptor> viewDescriptors) {
		super(type);
		Assert.isNotNull(viewDescriptors);
		this.viewDescriptors = viewDescriptors;
	}

	/**
	 * Returns the viewDescriptors list
	 *
	 * @return List of <code>ViewDescriptor</code> s
	 */
	public List<? extends ViewDescriptor> getViewDescriptors() {
		return viewDescriptors;
	}

	/**
	 * A list of <code>IAdaptable</code> objects that adapt to <code>View</code> .class
	 *
	 * @see org.eclipse.gef.requests.CreateRequest#getNewObject()
	 */
	@Override
	public Object getNewObject() {
		return getViewDescriptors();
	}

	/**
	 * The type is a List of <code>IAdaptable</code> objects that adapt to <code>IView</code> .class
	 *
	 * @see org.eclipse.gef.requests.CreateRequest#getNewObjectType()
	 */
	@Override
	public Object getNewObjectType() {
		return List.class;
	}

	/**
	 * The factory mechanism is not used
	 *
	 * @throws UnsupportedOperationException
	 */
	@Override
	protected CreationFactory getFactory() {
		throw new UnsupportedOperationException("The Factory mechanism is not used"); //$NON-NLS-1$
	}

	/**
	 * The factory mechanism is not used
	 */
	@Override
	public void setFactory(CreationFactory factory) {
		throw new UnsupportedOperationException("The Factory mechanism is not used"); //$NON-NLS-1$
	}
}
