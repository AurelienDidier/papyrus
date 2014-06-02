/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Ernest Wozniak (CEA LIST) ernest.wozniak@cea.fr - Initial API and implementation
 *  Patrick Tessier (CEA LIST) patrick.tessier@cea.fr - modification
 *****************************************************************************/
package org.eclipse.papyrus.dsml.validation.wizard;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.papyrus.customization.plugin.PluginEditor;
import org.eclipse.papyrus.dsml.validation.model.elements.interfaces.Category;
import org.eclipse.papyrus.dsml.validation.model.elements.interfaces.IConstraintProvider;
import org.eclipse.papyrus.dsml.validation.model.elements.interfaces.IConstraintsCategory;
import org.eclipse.papyrus.dsml.validation.model.elements.interfaces.IConstraintsManager;
import org.eclipse.papyrus.dsml.validation.model.elements.interfaces.IValidationRule;
import org.eclipse.papyrus.dsml.validation.model.profilenames.Utils;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IPluginProjectEditor;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Stereotype;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * This is the main class that generates the EMF Validation plugin. It creates
 * extension points and adds required dependencies.
 * 
 * 
 */
public class ValidationPluginGenerator {

	private static final String XML_CONSTRAINT_TARGET = "target";  //$NON-NLS-1$

	private static final String XML_CONSTRAINT_MESSAGE = "message";  //$NON-NLS-1$

	private static final String XML_CONSTRAINT_CLASS = "class";  //$NON-NLS-1$

	private static final String XML_CONSTRAINT_IS_ENABLED_BY_DEFAULT = "isEnabledByDefault";  //$NON-NLS-1$

	private static final String JAVA_LANGUAGE = "Java"; //$NON-NLS-1$

	private static final String OCL_LANGUAGE = "OCL";  //$NON-NLS-1$
	
	private static final String XML_CONSTRAINT_MODE = "mode"; //$NON-NLS-1$

	private static final String XML_CONSTRAINT_SEVERITY = "severity";  //$NON-NLS-1$

	private static final String XML_CONSTRAINT_STATUS_CODE = "statusCode";  //$NON-NLS-1$

	private static final String XML_CONSTRAINT_LANG = "lang"; //$NON-NLS-1$

	private static final String XML_CONSTRAINT_NAME = "name"; //$NON-NLS-1$

	private static final String SEPARATOR = ".";  //$NON-NLS-1$

	private static final String EMF_VALIDATION_CONSTRAINT_CHILD = "constraint";  //$NON-NLS-1$
	
	private static final String ID = "id";  //$NON-NLS-1$

	private static final String EMF_VALIDATION_CONSTRAINT_PROVIDERS_EXTENSIONPOINT = "org.eclipse.emf.validation.constraintProviders";  //$NON-NLS-1$

	private static final String UML_PLUGIN = "org.eclipse.uml2.uml";  //$NON-NLS-1$

	private static final String EMF_VALIDATION_PLUGIN = "org.eclipse.emf.validation";  //$NON-NLS-1$

	private static final String UML_URL = "http://www.eclipse.org/uml2/5.0.0/UML";  //$NON-NLS-1$


	/**
	 * singleton
	 */
	public static ValidationPluginGenerator instance = new ValidationPluginGenerator();

	private final static String PLUGIN_NATURE_ID = "org.eclipse.pde.PluginNature"; //$NON-NLS-1$

	private IConstraintsManager constraintsManager;

	/**
	 * generate the java code form constraints contained in the profile
	 * @param project the project eclipse
	 * @param wizard the ref to to wizard
	 * @param constraintsManager the class in charge to collect all information from the model
	 * @throws CoreException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public void generate(IProject project, IConstraintsManager constraintsManager, EPackage definition) throws CoreException,
	IOException, SAXException, ParserConfigurationException {
		PluginEditor editor;

		this.constraintsManager = constraintsManager;

		// prepare the plugin
		editor = new PluginEditor(project);
		// it's possible that editor.getManifestEditor() logs an exception due to resource out of sync.
		String pluginID = editor.getSymbolicBundleName();
		if (editor.getBundleName() == null) {
			// initialize bundle name with symbolic name, if not name is given
			editor.setBundleName(pluginID);
		}
		editor.setSingleton(true);
		editor.getBuildEditor().addToBuild(IPluginProjectEditor.PLUGIN_XML_FILE);
		Set<String> natures = new HashSet<String>();
		natures.add(PLUGIN_NATURE_ID);
		editor.addNatures(natures);

		editor.getManifestEditor().addDependency(EMF_VALIDATION_PLUGIN);
		editor.getManifestEditor().addDependency(UML_PLUGIN);

		Element constraintProviderExtension =
				createOrCleanExtension(editor, EMF_VALIDATION_CONSTRAINT_PROVIDERS_EXTENSIONPOINT);
		
		// creation of categories extension point
		// that corresponds to  profile and sub-profiles 
		this.createHierarchyOfCategories(pluginID,this.constraintsManager.getPrimeCategory(), constraintProviderExtension, editor);

		//add the constraint provider extension point, normally it exist only per profile so per category
		for (IConstraintProvider constraintProvider : constraintsManager.getConstraintsProviders()) {
			//create the extension point for the provider
			Element extElForConstraintsProvider = createExtensionForConstraintsProvider(constraintProvider, constraintProviderExtension, editor, definition);
			// go though category  (profile)
			for (IConstraintsCategory constraintCategory : constraintProvider.getConstraintsCategories()) {

				//create the extension point validationRule for category
				Element extElForConstraintsCategory = createExtensionForConstraintsCategory(pluginID, constraintCategory, extElForConstraintsProvider, editor, constraintsManager);

				for (IValidationRule constraint : constraintCategory.getConstraints()) {

					// is this a Java constraint?
					if (Utils.hasSpecificationForJava(constraint.getConstraint())) {
						createExtensionForConstraint(pluginID,constraint, extElForConstraintsCategory, editor);
					}

					// is this an OCL constraint?
					if (Utils.hasSpecificationForOCL(constraint.getConstraint())) {
						createOCLExtensionForConstraint(constraint, extElForConstraintsCategory, editor);
					}
					//((ValidationRuleImpl) constraint).setParentID();
				}

			}
		}

		generateBindings(pluginID, editor, this.constraintsManager);
		
		editor.getProject().refreshLocal(0, null);

		try {
			editor.save();
		} catch (Throwable ex) {

			return;
		}
	}

	@SuppressWarnings("nls")
	private Element createExtensionForConstraint(String pluginID, IValidationRule constraint,
		Element parentElement, PluginEditor editor) {

		Element extElForConstraint = editor.getPluginEditor().addChild(
			parentElement, EMF_VALIDATION_CONSTRAINT_CHILD);

		extElForConstraint.setAttribute(ID, constraint.getID());
		extElForConstraint.setAttribute(XML_CONSTRAINT_NAME, constraint.getName());
		extElForConstraint.setAttribute(XML_CONSTRAINT_LANG, JAVA_LANGUAGE);
		extElForConstraint.setAttribute(XML_CONSTRAINT_STATUS_CODE, constraint.getStatusCode().toString());
		extElForConstraint.setAttribute(XML_CONSTRAINT_SEVERITY, constraint.getSeverity().name());

		extElForConstraint.setAttribute(XML_CONSTRAINT_CLASS, pluginID + SEPARATOR + constraint.getImplementingClass());

		extElForConstraint.setAttribute(XML_CONSTRAINT_MODE, constraint.getMode().name());
		extElForConstraint.setAttribute(XML_CONSTRAINT_IS_ENABLED_BY_DEFAULT,
			String.valueOf(constraint.isEnabledByDefault()));

		if (constraint.getTargets() != null) {
			for (String target : constraint.getTargets()) {
				Element targetExtension = editor.addChild(extElForConstraint, XML_CONSTRAINT_TARGET);
				targetExtension.setAttribute(XML_CONSTRAINT_CLASS, target);
			}
		}

		if (constraint.getMessage() != null) {
			Element message = editor.addChild(extElForConstraint, XML_CONSTRAINT_MESSAGE);
			message.setTextContent(constraint.getMessage());
		}
		else{
			Element message = editor.addChild(extElForConstraint, XML_CONSTRAINT_MESSAGE);
			message.setTextContent(constraint.getName() + " not validated");
		}

		if (constraint.getDescription() != null) {
			Element description = editor.addChild(extElForConstraint, "description");
			description.setTextContent(constraint.getDescription());
		}

		return extElForConstraint;

	}

	/**
	 * create the extension point for constraint of emf validation
	 * @param validation the validation rule
	 * @param parentElement
	 * @param editor
	 * @return the extension point
	 */
	private Element createOCLExtensionForConstraint(IValidationRule validation,
		Element parentElement, PluginEditor editor) {

		Element extElForConstraint = editor.getPluginEditor().addChild(
			parentElement, EMF_VALIDATION_CONSTRAINT_CHILD);

		extElForConstraint.setAttribute(ID, validation.getID());
		extElForConstraint.setAttribute(XML_CONSTRAINT_NAME, validation.getName());
		extElForConstraint.setAttribute(XML_CONSTRAINT_LANG, OCL_LANGUAGE);
		extElForConstraint.setAttribute(XML_CONSTRAINT_STATUS_CODE, validation
			.getStatusCode().toString());
		extElForConstraint.setAttribute(XML_CONSTRAINT_SEVERITY, validation.getSeverity()
			.name());

		extElForConstraint.setAttribute(XML_CONSTRAINT_MODE, validation.getMode().name());
		extElForConstraint.setAttribute(XML_CONSTRAINT_IS_ENABLED_BY_DEFAULT,
			String.valueOf(validation.isEnabledByDefault()));

		if (validation.getTargets() != null) {
			for (String target : validation.getTargets()) {
				Element targetExtension = editor.addChild(extElForConstraint,
					XML_CONSTRAINT_TARGET);
				targetExtension.setAttribute(XML_CONSTRAINT_CLASS, target);
			}
		}

		if (validation.getMessage() != null) {
			Element message = editor.addChild(extElForConstraint, XML_CONSTRAINT_MESSAGE);
			message.setTextContent(validation.getMessage());
		}

		if (validation.getDescription() != null) {
			Element description = editor.addChild(extElForConstraint,
				"description");
			description.setTextContent(validation.getDescription());
		}

		if (Utils.getOCLConstraintBody(validation.getConstraint()) != null) {
			Document doc = editor.getDocument();

			CDATASection cdata = doc.createCDATASection(Utils
				.getOCLConstraintBody(validation.getConstraint()));
			extElForConstraint.appendChild(cdata);
		}

		parentElement.appendChild(extElForConstraint);

		return extElForConstraint;

	}

	public static Document parseXmlFile(String filename, boolean validating) {
		try {
			// Create a builder factory
			DocumentBuilderFactory factory = DocumentBuilderFactory
				.newInstance();
			factory.setValidating(validating);

			// Create the builder and parse the file
			Document doc = factory.newDocumentBuilder().parse(
				new File(filename));
			return doc;
		} catch (SAXException e) {
			// A parsing error occurred; the xml input is not valid
		} catch (ParserConfigurationException e) {
		} catch (IOException e) {
		}
		return null;
	}

	private Element createExtensionForCategory(String pluginID, Category category,
		Element parentElement, PluginEditor editor) {

		Element extElForCategory = editor.getPluginEditor().addChild(
			parentElement, "category"); //$NON-NLS-1$

		extElForCategory.setAttribute(ID, pluginID+SEPARATOR+category.getID());

		extElForCategory.setAttribute(XML_CONSTRAINT_NAME, pluginID + SEPARATOR + category.getName());

		extElForCategory.setAttribute("mandatory", String.valueOf(category.isMandatory())); //$NON-NLS-1$

		return extElForCategory;

	}

	/**
	 * create the constraint provider extension point
	 * @param constraintProvider
	 * @param parentElement
	 * @param editor
	 * @return the extension point
	 */
	@SuppressWarnings("nls")
	private Element createExtensionForConstraintsProvider(
		IConstraintProvider constraintProvider, Element parentElement,
		PluginEditor editor, EPackage definition) {

		Element extElForConstraintsProvider = editor.getPluginEditor().addChild(parentElement, "constraintProvider");

		extElForConstraintsProvider.setAttribute(XML_CONSTRAINT_MODE, constraintProvider.getMode().name());

		extElForConstraintsProvider.setAttribute("cache", String.valueOf(constraintProvider.getCache()));

		Element pcg = editor.addChild(extElForConstraintsProvider, "package");
		
		if(constraintProvider.getEPackage()==null){
			pcg.setAttribute("namespaceUri", UML_URL);
		}
		else{
			pcg.setAttribute("namespaceUri", constraintProvider.getEPackage().getNsURI());
			
		}

		return extElForConstraintsProvider;
	}

	@SuppressWarnings("nls")
	private Element createExtensionForConstraintsCategory(String pluginID,IConstraintsCategory constraintsCategory, Element parentElement,PluginEditor editor, IConstraintsManager constraintManager)
	{
		Element extElForConstraintsCategory = editor.getPluginEditor().addChild(parentElement, "constraints"); //$NON-NLS-1$
		extElForConstraintsCategory.setAttribute("categories", pluginID + SEPARATOR + constraintManager.getPrimeCategory().getName());
		return extElForConstraintsCategory;

	}


	@SuppressWarnings("nls")
	private void generateBindings(String pluginID, PluginEditor editor, IConstraintsManager constraintsManager)
	{
		Element extension = createOrCleanExtension(editor, "org.eclipse.emf.validation.constraintBindings");
		
		// create a client context per stereotype
		Set<Stereotype>constrainedStereotype=constraintsManager.getConstraintsOfStereotype().keySet();
		for(Iterator<Stereotype> iterator = constrainedStereotype.iterator(); iterator.hasNext();) {
			Stereotype stereotype = (Stereotype)iterator.next();
			// ("+--> create clientContext for the stereotype "+stereotype.getName());
			Element clientContextElement = editor.getPluginEditor().addChild(extension, "clientContext");
			clientContextElement.setAttribute(ID, stereotype.getName() + "ClientContext");
			Element selectorElement = editor.addChild(clientContextElement, "selector");
			selectorElement.setAttribute(XML_CONSTRAINT_CLASS, pluginID + ".selectors." + stereotype.getName() + "ClientSelector");
			
			// create binding
			List<Constraint> constraints=constraintsManager.getConstraintsOfStereotype().get(stereotype);
			Element bindingelement = editor.getPluginEditor().addChild(extension, "binding");
			bindingelement.setAttribute("context",	stereotype.getName() + "ClientContext");
			Element category = editor.addChild(bindingelement, "category");
			category.setAttribute("ref", pluginID + SEPARATOR +
					constraintsManager.getPrimeCategory().getName());
			for(Iterator<Constraint> iteratorConstraint = constraints.iterator(); iteratorConstraint.hasNext();) {
				Constraint constraint = (Constraint)iteratorConstraint.next();
				constraintsManager.getValidationRuleMap().get(constraint).getID();
				Element constraintElement = editor.addChild(bindingelement, "constraint");
				constraintElement.setAttribute("ref", constraintsManager.getValidationRuleMap().get(constraint).getID());
				// ("+----> create binding for the constraint "+constraintsManager.getValidationRuleMap().get(constraint).getID());
			}
		}
	}

	/**
	 * Create a new extension with a given name or reuse an existing extension if an extension with the given
	 * name already exists. In case of the latter, the existing extensions will be clean first, i.e. all of its
	 * children are removed. This function enables the multiple generation phases without duplicating elements.
	 * @param editor the plugin editor
	 * @param extensionName the name of the extension
	 * @return
	 */
	protected Element createOrCleanExtension(PluginEditor editor, String extensionName) {
		List<Node> existingExtensions = editor.getPluginEditor().getExtensions(extensionName);
		if ((existingExtensions.size() > 0) && (existingExtensions.get(0) instanceof Element)) {
			Element extension = (Element) existingExtensions.get(0);
			// reuse extension and remove all children
			for (;;) {
				Node child = extension.getFirstChild();
				if (child == null) {
					break;
				}
				extension.removeChild(child);
			}
			return extension;
		}
		else {
			return editor.getPluginEditor().addExtension(EMF_VALIDATION_CONSTRAINT_PROVIDERS_EXTENSIONPOINT);
		}
	}
	
	/**
	 * create the extension point categories
	 * @param projectName the name of the project
	 * @param category a category
	 * @param parentElement 
	 * @param editor
	 */
	private void createHierarchyOfCategories(String projectName, Category category,Element parentElement, PluginEditor editor) {

		this.createExtensionForCategory(projectName, category,parentElement, editor);
	}

	public static String getContextprefix() {
		return ""; //$NON-NLS-1$
	}
}
