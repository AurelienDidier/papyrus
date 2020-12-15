/*****************************************************************************
 * Copyright (c) 2020 CEA LIST, EclipseSource, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Remi SChnekenburger (EclipseSource) - Initial API and implementation
 *   Christian W. Damus - bug 569357
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.validation.common.internal.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.URIMappingRegistryImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.toolsmiths.validation.common.Activator;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.IPluginChecker2;
import org.eclipse.pde.internal.core.builders.CompilerFlags;
import org.eclipse.pde.internal.core.builders.IncrementalErrorReporter.VirtualMarker;
import org.eclipse.pde.internal.core.builders.ManifestErrorReporter;
import org.eclipse.pde.internal.core.builders.PDEMarkerFactory;
import org.eclipse.pde.internal.core.builders.XMLErrorReporter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

/**
 * Error reporter for specific extensions in the <tt>plugin.xml</tt>.
 *
 * @param <T>
 *            the type of model for which I validate <tt>plugin.xml</tt> extensions
 */
@SuppressWarnings("restriction")
public class PluginErrorReporter<T extends EObject> extends ManifestErrorReporter implements IPluginChecker2 {

	private static final String PLATFORM_PLUGIN = "platform:/plugin/";

	private static final String POINT = "point"; //$NON-NLS-1$

	private static final String SEPARATOR = "_";

	private final Map<String, ExtensionMatcher<? super T>> requiredExtensionPoints = new HashMap<>();
	private final Multimap<String, ExtensionChecker<? super T>> extensionCheckers = HashMultimap.create();
	private final Map<String, ExtensionFixProvider<? super T>> extensionFixProviders = new HashMap<>();
	private final Set<String> architectureImpliedExtensionPoints = new HashSet<>();
	private final Set<String> foundExtensionPoints = new HashSet<>();

	private final Map<String, String> localURIMappings = new HashMap<>();

	private final IFile modelFile;
	private final T model;
	private final String markerType;
	private final ProblemReport problems;

	private String sourceID;

	private Function<? super T, String> nameFunction;

	/**
	 * Constructor.
	 *
	 * @param file
	 *            the <tt>plugin.xml</tt> file
	 * @param modelFile
	 *            the file containing the model object to check
	 * @param model
	 *            the model object to check
	 * @param nameFunction
	 *            computes an unique name for the {@code model} object
	 */
	public PluginErrorReporter(IFile file, IFile modelFile, T model, Function<? super T, String> nameFunction) {
		this(file, modelFile, model, PDEMarkerFactory.MARKER_ID, nameFunction);
	}

	/**
	 * Constructor.
	 *
	 * @param file
	 *            the <tt>plugin.xml</tt> file
	 * @param modelFile
	 *            the file containing the model object to check
	 * @param model
	 *            the model object to check
	 * @param markerType
	 *            the type of resource markers to create
	 * @param nameFunction
	 *            computes an unique name for the {@code model} object
	 */
	public PluginErrorReporter(IFile file, IFile modelFile, T model, String markerType, Function<? super T, String> nameFunction) {
		super(file);

		this.modelFile = modelFile;
		this.model = model;
		this.markerType = markerType;
		this.nameFunction = nameFunction;
		this.problems = new ProblemReportImpl();
		sourceID = sourceID(modelFile, model, nameFunction);
	}

	public final IFile getModelFile() {
		return modelFile;
	}

	public final T getModel() {
		return model;
	}

	/**
	 * Add an extension points to the extensions that must be present and, when present, validated.
	 *
	 * @param point
	 *            extension point to add to validation for required extensions
	 * @param matcher
	 *            to extract the element in the extension that registers the model
	 * @param checker
	 *            to validate the matched extension element. Can be {@code null} if only the existence of the extension element needs to be checked
	 *
	 * @return myself, for convenience of call chaining
	 */
	public PluginErrorReporter<T> requireExtensionPoint(String point, ExtensionMatcher<? super T> matcher, ExtensionChecker<? super T> checker, ExtensionFixProvider<? super T> fixProvider) {
		requiredExtensionPoints.put(point, matcher);
		if (checker != null) {
			extensionCheckers.put(point, checker);
		}
		if (fixProvider != null) {
			extensionFixProviders.put(point, fixProvider);
		}
		return this;
	}

	/**
	 * Add extension points to the extensions that are implied by reference from an <em>Architecture Context</em> model.
	 * When a model that I validate is included in an architecture context, then the extension is not required.
	 * But if the extension exists, it is validated for well-formedness.
	 *
	 * @param extensionPoints
	 *            extension points that are implied by reference of my model from some <em>Architecture Context</em>
	 * @return myself, for convenience of call chaining
	 */
	public PluginErrorReporter<T> impliedByArchitectureContexts(String... extensionPoints) {
		architectureImpliedExtensionPoints.addAll(Arrays.asList(extensionPoints));
		return this;
	}

	/**
	 * Replace the reporter created by default on abstract class, to implement our specific one.
	 *
	 * @see SelectiveDeleteErrorReporter.
	 */
	private void replaceReporter(IFile file, DiagnosticChain diagnostics) {
		Field errorReporterField;
		try {
			errorReporterField = XMLErrorReporter.class.getDeclaredField("fErrorReporter"); //$NON-NLS-1$
			errorReporterField.setAccessible(true);
			Field modifiersField = Field.class.getDeclaredField("modifiers");//$NON-NLS-1$
			modifiersField.setAccessible(true);
			modifiersField.setInt(modifiersField, modifiersField.getModifiers() & ~Modifier.FINAL);
			errorReporterField.set(this, new DiagnosticErrorReporter(file, markerType, diagnostics));
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			Activator.log.error(e);
		}
	}

	/**
	 * Returns a unique id for the specified model element in the given model file.
	 *
	 * @param file
	 *            the model file
	 * @param model
	 *            the model element
	 * @param nameFunction
	 *            computes an unique name for the {@code model} object
	 *
	 * @return the unique identifier for this {@code model} in the plugin, which will allow
	 *         to identify markers associated to this {@code model} on the <tt>plugin.xml</tt>
	 */
	private static <T extends EObject> String sourceID(IFile file, T model, Function<? super T, String> nameFunction) {
		StringBuilder builder = new StringBuilder();
		builder.append(model.eClass().getName());
		builder.append(SEPARATOR);
		builder.append(file.getProjectRelativePath().toString());
		builder.append(SEPARATOR);
		String name = nameFunction.apply(model);
		builder.append(name);
		return builder.toString();
	}

	@Override
	public final void check(DiagnosticChain diagnostics, IProgressMonitor monitor) {
		// Install a reporter that will append diagnostics to this chain instead of creating markers
		replaceReporter(getFile(), diagnostics);

		// Validate and tell the reporter to generate those diagnostics
		validateContent(monitor);
	}

	@Override
	public void validate(IProgressMonitor monitor) {
		foundExtensionPoints.clear();

		Element element = getDocumentRoot();
		if (element == null) {
			return;
		}
		String elementName = element.getNodeName();
		if ("plugin".equals(elementName) || "fragment".equals(elementName)) { //$NON-NLS-1$ //$NON-NLS-2$
			// Find and validate existing extensions
			NodeList children = element.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				if (monitor.isCanceled()) {
					break;
				}

				Node child = children.item(i);
				switch (child.getNodeType()) {
				case Node.ELEMENT_NODE:
					validateElement((Element) child);
					break;
				default:
					break;
				}
			}

			// Report missing extensions
			Set<String> missingExtensionPoints = new HashSet<>(requiredExtensionPoints.keySet());
			missingExtensionPoints.removeAll(foundExtensionPoints);

			// If there are some extensions that we haven't found, perhaps they are implied by architecture contexts?
			Set<String> acExtensionPoints = new HashSet<>(architectureImpliedExtensionPoints);
			acExtensionPoints.retainAll(missingExtensionPoints);
			if (!acExtensionPoints.isEmpty() && findArchitectureContextReference()) {
				// Found the model in an architecture context. These extensions are not missing, then
				missingExtensionPoints.removeAll(acExtensionPoints);
			}

			if (!missingExtensionPoints.isEmpty()) {
				// Not in architecture contexts, either
				missingExtensionPoints.forEach(this::reportMissingExtension);
			}
		}
	}

	/**
	 * Report a missing extension of the given {@code point}.
	 *
	 * @param point
	 *            the extension point for which an extension should be registered
	 */
	protected void reportMissingExtension(String point) {
		int severity = CompilerFlags.ERROR;
		if (architectureImpliedExtensionPoints.contains(point)) {
			// Only report this as a warning because perhaps the architecture model just isn't available in the workspace or PDE Target
			severity = CompilerFlags.WARNING;
		}

		VirtualMarker marker = reportProblem(NLS.bind("Missing extension on point ''{0}'' for ''{2}'' [{1}].", new Object[] { point, getModelFile().getProjectRelativePath(), nameFunction.apply(model) }),
				1, severity, extensionFixProviders.get(point) != null ? extensionFixProviders.get(point).problemId(point, model) : 0, null, null, "missing_extensions"); //$NON-NLS-1$
		marker.setAttribute("staticProfile", nameFunction.apply(model));
	}

	protected void validateElement(Element element) {
		String name = element.getNodeName();
		switch (name) {
		case "extension": //$NON-NLS-1$
			validateExtension(element);
			break;
		default:
			break;
		}
	}

	protected void validateExtension(Element element) {
		// do not let default validation be done, this will be done by standard plugin builder
		String pointID = element.getAttribute(POINT);
		if (pointID != null && !pointID.isBlank()) {
			matchExtension(element, pointID, model).ifPresent(matched -> {
				matchedExtensionPoint(pointID);
				validateExtension(matched, pointID, model);
			});
		}
	}

	protected Optional<Element> matchExtension(Element element, String point, T model) {
		return Optional.ofNullable(requiredExtensionPoints.get(point))
				.flatMap(matcher -> matcher.matchExtension(element, point, model));
	}

	protected void validateExtension(Element element, String point, T model) {
		extensionCheckers.get(point).forEach(checker -> checker.checkExtension(element, point, model, problems));
	}

	protected void matchedExtensionPoint(String pointID) {
		foundExtensionPoints.add(pointID);
	}

	protected VirtualMarker reportProblem(String message, int line, int severity, int fixId, Element element, String attrName,
			String category) {
		VirtualMarker marker = report(message, line, severity, fixId, element, attrName, category);
		addMarkerID(marker);
		return marker;
	}

	/**
	 * Overrides super version with a new version of the {@link #generateLocationPath(Node, String)} method that was mismatching the nodes
	 * <p>
	 * Text nodes were also added in the index, and not only elements as expected by the missing attribute marker resolution)
	 * </p>
	 *
	 * @see org.eclipse.pde.internal.core.builders.XMLErrorReporter#report(java.lang.String, int, int, int, org.w3c.dom.Element, java.lang.String, java.lang.String)
	 */
	@Override
	public VirtualMarker report(String message, int line, int severity, int fixId, Element element, String attrName,
			String category) {
		VirtualMarker marker = report(message, line, severity, fixId, category);
		if (marker == null) {
			return null;
		}
		marker.setAttribute(PDEMarkerFactory.MPK_LOCATION_PATH, generateLocationPath(element, attrName));
		return marker;
	}

	private String generateLocationPath(Node node, String attrName) {
		if (node == null) {
			return ""; // //$NON-NLS-1$
		}

		int childIndex = 0;
		for (Node previousSibling = node.getPreviousSibling(); previousSibling != null; previousSibling = previousSibling.getPreviousSibling()) {
			if ((previousSibling instanceof Element)) {
				// filter text
				childIndex += 1;
			}
		}

		StringBuilder sb = new StringBuilder();
		Node parent = node.getParentNode();
		if (parent != null && !(parent instanceof Document)) {
			sb.append(generateLocationPath(parent, null));
			sb.append(F_CHILD_SEP);
		}
		composeNodeString(node, childIndex, attrName, sb);
		return sb.toString();
	}

	private String composeNodeString(Node node, int index, String attrName, StringBuilder sb) {
		sb.append('(');
		sb.append(index);
		sb.append(')');
		sb.append(node.getNodeName());
		if (attrName != null) {
			sb.append(F_ATT_PREFIX);
			sb.append(attrName);
		}
		return sb.toString();
	}

	protected VirtualMarker reportProblem(String message, int line, int severity, String category) {
		VirtualMarker marker = report(message, line, severity, category);
		addMarkerID(marker);
		return marker;
	}

	private void addMarkerID(VirtualMarker marker) {
		if (marker == null) {
			return;
		}
		addMarkerAttribute(marker, DiagnosticErrorReporter.SOURCE_ID, sourceID);
	}



	protected String decodePath(String path) {
		if (path == null) {
			return null;
		}

		// check pathmap, relative URI or platform based uri
		if (path.startsWith("pathmap://")) {
			// try to decode using uri mappers extensions
			return decodePathmapPath(path);
		} else if (path.startsWith(PLATFORM_PLUGIN)) {
			// check if path is valid within the plugin
			return decodePlatformPath(path);
		}

		// relative path?
		return path;
	}

	protected String decodePlatformPath(String path) {
		return cutPluginPath(path);
	}

	protected String decodePathmapPath(String path) {
		String decodePath = null;
		// check first local mappings
		for (Entry<String, String> entry : localURIMappings.entrySet()) {
			String sourceURI = entry.getKey().toString();
			if (path.startsWith(sourceURI)) {
				String targetURI = entry.getValue();
				decodePath = replaceString(path, sourceURI, targetURI);
				return cutPluginPath(decodePath);
			}
		}
		for (Entry<URI, URI> entry : URIMappingRegistryImpl.INSTANCE.entrySet()) {
			String sourceURI = entry.getKey().toString();
			if (path.startsWith(sourceURI)) {
				String targetURI = entry.getValue().toString();
				decodePath = replaceString(path, sourceURI, targetURI);
				return cutPluginPath(decodePath);
			}
		}

		// cut platform:/plugin/<profile-name> to get a project relative path
		return path;
	}

	protected String cutPluginPath(String decodePath) {
		if (decodePath.startsWith(PLATFORM_PLUGIN)) {
			String cutPath = decodePath.substring(PLATFORM_PLUGIN.length());
			int index = cutPath.indexOf('/');
			cutPath = cutPath.substring(index + 1); // remove initial '/'
			return cutPath;
		}
		return decodePath;
	}

	protected String replaceString(String path, String sourceURI, String targetURI) {
		String newPath = path.substring(sourceURI.length(), path.length());
		if (!targetURI.endsWith("/")) {
			newPath = "/".concat(newPath);
		}
		newPath = targetURI.concat(newPath);
		return newPath;
	}

	/**
	 * Search the registered architecture context models to find one that cross-references to the
	 * {@link #getModel() model} that I validate.
	 *
	 * @return whether a cross-reference is found in any architecture context
	 */
	protected boolean findArchitectureContextReference() {
		boolean result = false;

		// We do not need extensions on the set registration point if some architecture context includes the set
		URIConverter converter = getModel().eResource().getResourceSet().getURIConverter();
		URI myModelURI = converter.normalize(EcoreUtil.getURI(getModel()));

		try {
			Multimap<EObject, EStructuralFeature.Setting> xrefs = ArchitectureIndex.getInstance().getExternalCrossReferences().get();

			// The architecture models are loaded in their own resource set, so look for our model by URI
			result = xrefs.keySet().stream()
					.map(EcoreUtil::getURI)
					.map(converter::normalize)
					.filter(myModelURI::equals)
					.anyMatch(myModelURI::equals);
		} catch (ExecutionException | InterruptedException e) {
			// Cannot access the architecture index? Then we didn't find anything
			Activator.log.error("Error querying Architecture Context models.", e); //$NON-NLS-1$
		}

		return result;
	}

	//
	// Nested types
	//

	public interface ExtensionMatcher<T extends EObject> {
		/**
		 * Find the extension element that "registers" (for whatever that means in the domain of the given {@code model})
		 * the {@code model} being validated.
		 *
		 * @param element
		 *            the <tt>plugin.xml</tt> element that is an extension of the given {@code point}
		 * @param point
		 *            the extension point in which extension {@code element} to search for "registration" of the {@code model}
		 * @param model
		 *            the model being validated
		 *
		 * @return the element that is the "registration" of the {@code model} on the extension {@code point}, or empty if none is found
		 */
		Optional<Element> matchExtension(Element element, String point, T model);
	}

	public interface ExtensionChecker<T extends EObject> {
		/**
		 * Validate the extension {@code element} that "registers" (for whatever that means in the domain of the given {@code model})
		 * the {@code model} being validated.
		 *
		 * @param element
		 *            the <tt>plugin.xml</tt> element previously found by an {@code ExtensionMatcher}
		 * @param point
		 *            the extension point in which the extension {@code element} "registers" the {@code model}
		 * @param model
		 *            the model being validated
		 * @param problems
		 *            a sink for problems
		 */
		void checkExtension(Element element, String point, T model, ProblemReport problems);
	}

	public interface ProblemReport {

		/**
		 * Report a fixable problem in some attribute of an {@code element} of the <tt>plugin.xml</tt>.
		 *
		 * @param severity
		 *            the problem severity, as per {@link Diagnostic#ERROR Diagnostic} severity codes
		 * @param element
		 *            the XML element on which to report the problem
		 * @param attrName
		 *            the attribute of the {@code element} on which to report the problem
		 * @param message
		 *            a description of the problem
		 * @param fixId
		 *            the ID of a fix for the problem
		 * @param category
		 *            the problem category
		 * @param data
		 *            additional data to pass with the problem
		 */
		void reportProblem(int severity, Element element, String attrName, String message, int fixId, String category, Map<String, String> data);

		/**
		 * Report a problem in some attribute of an {@code element} of the <tt>plugin.xml</tt>.
		 *
		 * @param severity
		 *            the problem severity, as per {@link Diagnostic#ERROR Diagnostic} severity codes
		 * @param element
		 *            the XML element on which to report the problem
		 * @param attrName
		 *            the attribute of the {@code element} on which to report the problem
		 * @param message
		 *            a description of the problem
		 * @param category
		 *            the problem category
		 * @param data
		 *            additional data to pass with the problem
		 */
		void reportProblem(int severity, Element element, String attrName, String message, String category, Map<String, String> data);

		/**
		 * Report a fixable problem in some {@code element} of the <tt>plugin.xml</tt>.
		 *
		 * @param severity
		 *            the problem severity, as per {@link Diagnostic#ERROR Diagnostic} severity codes
		 * @param element
		 *            the XML element on which to report the problem
		 * @param message
		 *            a description of the problem
		 * @param fixId
		 *            the ID of a fix for the problem
		 * @param category
		 *            the problem category
		 * @param data
		 *            additional data to pass with the problem
		 */
		default void reportProblem(int severity, Element element, String message, int fixId, String category, Map<String, String> data) {
			reportProblem(severity, element, null, message, fixId, category, data);
		}

		/**
		 * Report a problem in some {@code element} of the <tt>plugin.xml</tt>.
		 *
		 * @param severity
		 *            the problem severity, as per {@link Diagnostic#ERROR Diagnostic} severity codes
		 * @param element
		 *            the XML element on which to report the problem
		 * @param message
		 *            a description of the problem
		 * @param category
		 *            the problem category
		 * @param data
		 *            additional data to pass with the problem
		 */
		default void reportProblem(int severity, Element element, String message, String category, Map<String, String> data) {
			reportProblem(severity, element, null, message, category, data);
		}

		/**
		 * Report a fixable problem on the <tt>plugin.xml</tt> as a whole.
		 *
		 * @param severity
		 *            the problem severity, as per {@link Diagnostic#ERROR Diagnostic} severity codes
		 * @param message
		 *            a description of the problem
		 * @param fixId
		 *            the ID of a fix for the problem
		 * @param category
		 *            the problem category
		 * @param data
		 *            additional data to pass with the problem
		 */
		void reportProblem(int severity, String message, int fixId, String category, Map<String, String> data);

		/**
		 * Report a problem on the <tt>plugin.xml</tt> as a whole.
		 *
		 * @param severity
		 *            the problem severity, as per {@link Diagnostic#ERROR Diagnostic} severity codes
		 * @param message
		 *            a description of the problem
		 * @param category
		 *            the problem category
		 * @param data
		 *            additional data to pass with the problem
		 */
		void reportProblem(int severity, String message, String category, Map<String, String> data);

	}

	private class ProblemReportImpl implements ProblemReport {

		@Override
		public void reportProblem(int severity, Element element, String attrName, String message, int fixId, String category, Map<String, String> data) {
			VirtualMarker problem = PluginErrorReporter.this.reportProblem(message, getLine(element, attrName), toCompilerSeverity(severity), fixId, element, attrName, category);
			applyData(problem, data);
		}

		@Override
		public void reportProblem(int severity, Element element, String attrName, String message, String category, Map<String, String> data) {
			VirtualMarker problem = PluginErrorReporter.this.reportProblem(message, getLine(element, attrName), toCompilerSeverity(severity), category);
			applyData(problem, data);
		}

		private int getLine(Element element, String attrName) {
			return attrName == null ? PluginErrorReporter.this.getLine(element) : PluginErrorReporter.this.getLine(element, attrName);
		}

		@Override
		public void reportProblem(int severity, String message, int fixId, String category, Map<String, String> data) {
			VirtualMarker problem = PluginErrorReporter.this.report(message, 1, toCompilerSeverity(severity), fixId, category);
			applyData(problem, data);
		}

		@Override
		public void reportProblem(int severity, String message, String category, Map<String, String> data) {
			VirtualMarker problem = PluginErrorReporter.this.report(message, 1, toCompilerSeverity(severity), category);
			applyData(problem, data);
		}

		void applyData(VirtualMarker problem, Map<String, String> data) {
			if (data != null) {
				data.forEach((k, v) -> {
					problem.setAttribute(k, v);
				});
			}
		}

		private int toCompilerSeverity(int diagnosticSeverity) {
			switch (diagnosticSeverity) {
			case Diagnostic.INFO:
				return CompilerFlags.INFO;
			case Diagnostic.WARNING:
				return CompilerFlags.WARNING;
			case Diagnostic.ERROR:
			case Diagnostic.CANCEL:
				return CompilerFlags.ERROR;
			default:
				return CompilerFlags.IGNORE;
			}
		}

	}

	public interface ExtensionFixProvider<T extends EObject> {
		int problemId(String point, T model);
	}

}
