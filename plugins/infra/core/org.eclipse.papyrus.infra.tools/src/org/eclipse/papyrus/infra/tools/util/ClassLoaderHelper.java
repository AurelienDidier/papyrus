/*****************************************************************************
 * Copyright (c) 2010, 2019 CEA LIST, EclipseSource and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  EclipseSource - Bug 543723
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - bug 553247
 *****************************************************************************/
package org.eclipse.papyrus.infra.tools.util;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.tools.Activator;
import org.osgi.framework.Bundle;

/**
 * <p>
 * A Helper class for Class Loading. It keeps a cache of classes once they've been loaded,
 * to speed up future lookups.
 * </p>
 * <p>
 * When loading a class, it is recommended to pass a Bundle ID (Or URI), to narrow down the
 * list of bundles to explore for that class. This helper can also take a simple qualified name,
 * but in that case it will explore all bundles that depend on this bundle; which may have
 * severe impact on performances (at least for initial load; after that, the cache will speed
 * everything up).
 * </p>
 *
 * @author Camille Letavernier
 */
public class ClassLoaderHelper {

	/**
	 * Usually, there are few classes with many different accesses. Using a cache, we can improve
	 * the performances between 10 and 20 times, with really little memory consumption
	 */
	private static final Map<String, Class<?>> classes = new HashMap<>();

	/**
	 * <p>
	 * Loads the class matching the given className. The context {@link URI} is
	 * used as a hint to find the most appropriate class loader: if this URI is
	 * a platform-plugin URI, the represented plug-in's class loader will be used.
	 * </p>
	 *
	 * @param className
	 *            The qualified name of the Class to load.
	 * @param context
	 *            The context URI
	 * @return
	 *         The loaded Class, or null if an error occurred
	 * @since 3.1
	 */
	public static Class<?> loadClass(String className, URI context) {
		if (classes.containsKey(className)) {
			return classes.get(className);
		}
		return loadClass(className, getPluginId(context));
	}

	/**
	 * <p>
	 * Loads the class matching the given className from the specified bundle.
	 * </p>
	 *
	 * @param className
	 *            The qualified name of the Class to load.
	 * @param bundleId
	 *            The bundle in which the class is located
	 * @return
	 *         The loaded Class, or null if an error occurred
	 * @since 3.1
	 */
	public static Class<?> loadClass(String className, String bundleId) {
		if (classes.containsKey(className)) {
			return classes.get(className);
		}
		Bundle bundle = Platform.getBundle(bundleId);
		return bundle == null ? loadClass(className) : loadClass(className, bundle);
	}

	/**
	 * Loads the class matching the given className. Exceptions are caught and sent
	 * to the Logger.
	 * This method is very slow (See Bug 543723).
	 * Use {@link #loadClass(String, Bundle)} instead
	 *
	 * @param className
	 *            The qualified name of the Class to load.
	 * @return
	 *         The loaded Class, or null if an error occurred
	 *
	 */
	public static Class<?> loadClass(String className) {
		try {
			Class<?> result = classes.get(className);
			if (result == null) {
				result = Activator.getDefault().getBundle().loadClass(className);
				classes.put(className, result);
			}
			return result;
		} catch (ClassNotFoundException ex) {
			Activator.log.error(String.format("The class %s doesn't exist", className), ex); //$NON-NLS-1$
		} catch (NullPointerException ex) {
			Activator.log.error("Cannot load class " + className, ex); //$NON-NLS-1$
		}

		return null;
	}

	/**
	 * <p>
	 * Loads the class matching the given className from the specified bundle.
	 * </p>
	 *
	 * @param className
	 *            The qualified name of the Class to load.
	 * @param bundle
	 *            The bundle in which the class is located
	 * @return
	 *         The loaded Class, or null if an error occurred
	 * @since 3.1
	 */
	public static Class<?> loadClass(String className, Bundle bundle) {
		try {
			Class<?> result = classes.get(className);
			if (result == null) {
				result = bundle.loadClass(className);
				classes.put(className, result);
			}
			return result;
		} catch (ClassNotFoundException ex) {
			Activator.log.error(String.format("The class %s doesn't exist", className), ex); //$NON-NLS-1$
			return loadClass(className); // We received the wrong bundle-context, Fallback to the buddy-policy and try again
		} catch (NullPointerException ex) {
			Activator.log.error("Cannot load class " + className, ex); //$NON-NLS-1$
		}

		return null;
	}

	/**
	 * <p>
	 * Loads and returns the class represented by the given className.
	 * Checks that the loaded class is a subtype of the given Class.
	 * </p>
	 * <p>
	 * The context {@link URI} is used as a hint to find the most appropriate
	 * class loader: if this URI is a platform-plugin URI, the represented
	 * plug-in's class loader will be used.
	 * </p>
	 *
	 * @param className
	 *            The qualified name of the class to be loaded
	 * @param asSubClass
	 *            The interface or class that the loaded class must implement or extend
	 * @param context
	 *            The context URI
	 * @return
	 *         The loaded class, or null if the class doesn't exist or is invalid.
	 *         In such a case, the exception is logged.
	 * @since 3.1
	 */
	public static <T> Class<? extends T> loadClass(String className, Class<T> asSubClass, URI context) {
		return loadClass(className, asSubClass, getPluginId(context));
	}

	/**
	 * <p>
	 * Loads and returns the class represented by the given className
	 * from the specified bundle. Checks that the loaded class is a subtype
	 * of the given Class.
	 * </p>
	 *
	 * @param className
	 *            The qualified name of the class to be loaded
	 * @param asSubClass
	 *            The interface or class that the loaded class must implement or extend
	 * @param bundleId
	 *            The bundle in which the class is located
	 * @return
	 *         The loaded class, or null if the class doesn't exist or is invalid.
	 *         In such a case, the exception is logged.
	 * @since 3.1
	 */
	public static <T> Class<? extends T> loadClass(String className, Class<T> asSubClass, String bundleId) {
		Class<?> clazz = classes.get(className);
		if (clazz != null) {
			return clazz.asSubclass(asSubClass);// to avoid to load the bundle when it is not required
		}
		return loadClass(className, asSubClass, bundleId == null ? null : Platform.getBundle(bundleId));
	}

	/**
	 * <p>
	 * Loads and returns the class represented by the given className
	 * from the specified bundle. Checks that the loaded class is a subtype
	 * of the given Class.
	 * </p>
	 *
	 * @param className
	 *            The qualified name of the class to be loaded
	 * @param asSubClass
	 *            The interface or class that the loaded class must implement or extend
	 * @param bundle
	 *            The bundle in which the class is located
	 * @return
	 *         The loaded class, or null if the class doesn't exist or is invalid.
	 *         In such a case, the exception is logged.
	 * @since 3.1
	 */
	public static <T> Class<? extends T> loadClass(String className, Class<T> asSubClass, Bundle bundle) {
		if (bundle == null) {
			Activator.log.warn("Using ClassLoaderHelper#loadClass without an appropriate context. This may degrade performances (Class: " + className + ")"); //$NON-NLS-1$ //$NON-NLS-2$
			bundle = Activator.getDefault().getBundle();
		}
		Class<?> theClass = loadClass(className, bundle);
		if (theClass == null) {
			return null;
		}

		try {
			Class<? extends T> typedClass = theClass.asSubclass(asSubClass);
			return typedClass;
		} catch (ClassCastException ex) {
			Activator.log.error(String.format("The class %1$s doesn't extend or implement %2$s", className, asSubClass.getName()), ex); //$NON-NLS-1$
		}

		return null;
	}

	/**
	 * Loads and returns the class denoted by the given className.
	 * Checks that the loaded class is a subtype of the given Class.
	 *
	 * @param className
	 *            The qualified name of the class to be loaded
	 * @param asSubClass
	 *            The interface or class that the loaded class must implement or extend
	 * @return
	 *         The loaded class, or null if the class doesn't exist or is invalid.
	 *         In such a case, the exception is logged.
	 * @deprecated Since 3.1 This method is very slow (See Bug 543723).
	 *             Use {@link #loadClass(String, Class, Bundle)} instead
	 */
	@Deprecated
	public static <T> Class<? extends T> loadClass(String className, Class<T> asSubClass) {
		if (!classes.containsKey(className)) {
			Activator.log.warn("Using ClassLoaderHelper#loadClass without an appropriate context. This may degrade performances (Class: " + className + ")"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return loadClass(className, asSubClass, Activator.getDefault().getBundle());
	}

	/**
	 * <p>
	 * Creates a new instance of class denoted by the given className.
	 * Checks that the instantiated class is a subtype of the given class.
	 * </p>
	 * <p>
	 * The context {@link URI} is used as a hint to find the most appropriate
	 * class loader: if this URI is a platform-plugin URI, the represented
	 * plug-in's class loader will be used.
	 * </p>
	 *
	 * @param className
	 *            The qualified name of the class to be instantiated
	 * @param asSubclass
	 *            The interface or class that the loaded class must implement or extend
	 * @param context
	 *            The context URI
	 * @return
	 *         An instance of the loaded class, or null if a valid instance
	 *         cannot be created. In such a case, the exception is logged.
	 * @since 3.1
	 */
	public static <T> T newInstance(String className, Class<T> asSubclass, URI context) {
		Class<? extends T> typedClass = loadClass(className, asSubclass, context);
		if (typedClass == null) {
			return null;
		}

		return newInstance(typedClass);
	}

	/**
	 * <p>
	 * Creates a new instance of class denoted by the given className from the specified bundle.
	 * Checks that the instantiated class is a subtype of the given class.
	 * </p>
	 *
	 * @param className
	 *            The qualified name of the class to be instantiated
	 * @param asSubclass
	 *            The interface or class that the loaded class must implement or extend
	 * @param bundleId
	 *            The bundle in which the class is located
	 * @return
	 *         An instance of the loaded class, or null if a valid instance
	 *         cannot be created. In such a case, the exception is logged.
	 * @since 3.1
	 */
	public static <T> T newInstance(String className, Class<T> asSubclass, String bundleId) {
		Class<? extends T> typedClass = loadClass(className, asSubclass, bundleId);
		if (typedClass == null) {
			return null;
		}

		return newInstance(typedClass);
	}

	/**
	 * <p>
	 * Creates a new instance of class denoted by the given className from the specified bundle.
	 * Checks that the instantiated class is a subtype of the given class.
	 * </p>
	 *
	 * @param className
	 *            The qualified name of the class to be instantiated
	 * @param asSubclass
	 *            The interface or class that the loaded class must implement or extend
	 * @param bundle
	 *            The bundle in which the class is located
	 * @return
	 *         An instance of the loaded class, or null if a valid instance
	 *         cannot be created. In such a case, the exception is logged.
	 * @since 3.1
	 */
	public static <T> T newInstance(String className, Class<T> asSubclass, Bundle bundle) {
		Class<? extends T> typedClass = loadClass(className, asSubclass, bundle);
		if (typedClass == null) {
			return null;
		}

		return newInstance(typedClass);
	}

	/**
	 * Creates a new instance of class denoted by the given className.
	 * Checks that the instantiated class is a subtype of the given class
	 *
	 * @param className
	 *            The qualified name of the class to be instantiated
	 * @param asSubclass
	 *            The interface or class that the loaded class must implement or extend
	 * @return
	 *         An instance of the loaded class, or null if a valid instance
	 *         cannot be created. In such a case, the exception is logged.
	 * @deprecated Since 3.1 This method is very slow (See Bug 543723).
	 *             Use {@link #newInstance(String, Class, Bundle)} instead
	 */
	@Deprecated
	public static <T> T newInstance(String className, Class<T> asSubclass) {
		Class<? extends T> typedClass = loadClass(className, asSubclass);
		if (typedClass == null) {
			return null;
		}

		return newInstance(typedClass);
	}

	/**
	 * <p>
	 * Returns a new Instance of the given class.
	 * </p>
	 * <p>
	 * The context {@link URI} is used as a hint to find the most appropriate
	 * class loader: if this URI is a platform-plugin URI, the represented
	 * plug-in's class loader will be used.
	 * </p>
	 *
	 * @param className
	 *            The qualified name of the Class to instantiate
	 * @param context
	 *            the context URI
	 * @return
	 *         A new instance of the given class, or null if the class couldn't be
	 *         instantiated
	 * @since 3.1
	 */
	public static Object newInstance(String className, URI context) {
		return newInstance(loadClass(className, context));
	}

	/**
	 * </p>
	 * Returns a new Instance of the given class from the specified bundle
	 * </p>
	 *
	 * @param className
	 *            The qualified name of the Class to instantiate
	 * @param bundleId
	 *            The bundle in which the class is located
	 * @return
	 *         A new instance of the given class, or null if the class couldn't be
	 *         instantiated
	 * @since 3.1
	 */
	public static Object newInstance(String className, String bundleId) {
		return newInstance(loadClass(className, bundleId));
	}

	/**
	 * <p>
	 * Returns a new Instance of the given class
	 * </p>
	 *
	 * @param className
	 *            The qualified name of the Class to instantiate
	 * @param bundle
	 *            The bundle in which the class is located
	 * @return
	 *         A new instance of the given class, or null if the class couldn't be
	 *         instantiated
	 * @since 3.1
	 */
	public static Object newInstance(String className, Bundle bundle) {
		return newInstance(loadClass(className, bundle));
	}

	/**
	 * Returns a new Instance of the given class
	 *
	 * @param className
	 *            The qualified name of the Class to instantiate
	 * @return
	 *         A new instance of the given class, or null if the class couldn't be
	 *         instantiated
	 * @deprecated Since 3.1 This method is very slow (See Bug 543723).
	 *             Use {@link #newInstance(String, Bundle)} instead
	 */
	@Deprecated
	public static Object newInstance(String className) {
		return newInstance(loadClass(className));
	}

	/**
	 * Returns a new Instance of the given class
	 *
	 * @param theClass
	 *            The Class to instantiate
	 * @return
	 *         A new instance of the given class, or null if the class couldn't be
	 *         instantiated
	 */
	public static <T extends Object> T newInstance(Class<T> theClass) {
		if (theClass == null) {
			return null;
		}

		try {
			return theClass.newInstance();
		} catch (IllegalAccessException ex) {
			Activator.log.error("Cannot find a valid public constructor for the class " + theClass.getName(), ex); //$NON-NLS-1$
		} catch (InstantiationException ex) {
			Activator.log.error(String.format("The class %s cannot be instantiated.", theClass.getName()), ex); //$NON-NLS-1$
		}

		return null;
	}

	/**
	 * <p>
	 * Retrieve the bundle ID from a URI. The URI should be either a {@link URI#isPlatformPlugin() PlatformPlugin URI}
	 * or use the custom properties "ppe:/" protocol
	 * </p>
	 *
	 * @param uri
	 * @return
	 *         The ID of the Bundle containing the model represented by this URI,
	 *         or <code>null</code> if the URI doesn't reference a bundle
	 */
	private static final String getPluginId(URI uri) {
		if (uri != null && (uri.isPlatformPlugin() || "ppe".equals(uri.scheme()))) { // ppe is a custom URI scheme used for properties view //$NON-NLS-1$
			String[] segments = uri.segments();
			if (segments.length > 2) {
				return segments[1];
			}
		}
		return null;
	}
}
