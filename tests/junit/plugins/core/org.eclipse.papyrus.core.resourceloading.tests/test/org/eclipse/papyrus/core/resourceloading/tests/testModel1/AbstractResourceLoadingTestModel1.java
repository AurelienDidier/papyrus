/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Emilien Perico (Atos Origin) emilien.perico@atosorigin.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.core.resourceloading.tests.testModel1;

import java.net.URL;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.core.Activator;
import org.eclipse.papyrus.core.resourceloading.OnDemandLoadingModelSetServiceFactory;
import org.eclipse.papyrus.core.resourceloading.preferences.StrategyChooser;
import org.eclipse.papyrus.core.resourceloading.tests.testModel2.ITestConstants;
import org.eclipse.papyrus.resource.ModelSet;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;


/**
 * Abstract class to test all the strategies with TestModel1
 * - Check that all the controlled resources are loaded
 * - Check that all the needed profiles (resource and pathmap) are loaded
 * - Check that all references to controlled resource are resolved
 * 
 * @author eperico
 * 
 */
public abstract class AbstractResourceLoadingTestModel1 extends TestCase {

	private static final String INITIAL_PATH = "resources/TestModel1/";

	public static final String RESOURCE_URI = ITestConstants.FRAGMENT_ID + "/" + INITIAL_PATH;

	private String[] resources = new String[]{ "model1", "Package0", "Package1" };

	private String[] extensions = new String[]{ ".di", ".notation", ".uml" };

	protected ModelSet modelSet;


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		StrategyChooser.setCurrentStrategy(getStrategy());
		// first we need to create the test project from the plugin to the workspace test platform
		IProject project = copyTestModelToThePlatform();
		modelSet = (ModelSet)new OnDemandLoadingModelSetServiceFactory().createServiceInstance();
		if(project != null) {
			IFile modelFile = project.getFile(INITIAL_PATH + "model1.di");
			modelSet.loadModels(modelFile);
		} else {
			fail("Could not initialize correctly the project");
		}
	}

	/**
	 * Copy test model from the test plugin to the platform where the tests are executed
	 * 
	 * @return the project in the runtime platform
	 * @throws Exception
	 */
	private IProject copyTestModelToThePlatform() throws Exception {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(ITestConstants.FRAGMENT_ID);
		IProgressMonitor monitor = new NullProgressMonitor();

		if(project != null && !project.exists()) {
			project.create(monitor);
		}
		project.open(monitor);
		for(String res : resources) {
			for(String s : extensions) {
				IFile file = project.getFile(INITIAL_PATH + res + s);
				// link all the models resources
				if(!file.exists()) {
					createFolder(project, "resources/");
					createFolder(project, INITIAL_PATH);
					URL url = FileLocator.find(Platform.getBundle(ITestConstants.FRAGMENT_ID), new Path(INITIAL_PATH + res + s), null);
					URL newFile = FileLocator.resolve(url);
					file.createLink(newFile.toURI(), IResource.REPLACE, monitor);
				}
			}
		}
		// link the profile
		String profilePath = INITIAL_PATH + "MyProfile.uml";
		IFile file = project.getFile(profilePath);
		if(!file.exists()) {
			URL url = FileLocator.find(Platform.getBundle(ITestConstants.FRAGMENT_ID), new Path(profilePath), null);
			URL newFile = FileLocator.resolve(url);
			file.createLink(newFile.toURI(), IResource.REPLACE, monitor);
		}
		project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
		return project;
	}

	/**
	 * Gets the current resource loading strategy.
	 * 
	 * @return the strategy
	 */
	public abstract int getStrategy();

	/**
	 * Gets objects of controlled resources.
	 * - get an object (Class0) from the current resource (model1)
	 * - get an object (Class1) from the first controlled resource (Package0)
	 * - get an object (Class2) from the second controlled resource (Package1)
	 */
	public void testGetObjectOfControlledResource() {
		// test getting EObject of the specified URI
		// level 0
		URI uriClass0 = URI.createPlatformResourceURI(RESOURCE_URI + "model1.uml#_SEFk0H04Ed-RG-XtCG9Nmw", false);
		EObject eObject = modelSet.getEObject(uriClass0, true);
		assertTestGetObjectOfControlledResource("Load object of the current resource: ", eObject, uriClass0);
		// level 1
		URI uriClass1 = URI.createPlatformResourceURI(RESOURCE_URI + "Package0.uml#_nQhGUH04Ed-RG-XtCG9Nmw", false);
		EObject eObject2 = modelSet.getEObject(uriClass1, true);
		assertTestGetObjectOfControlledResource("Load object in a controlled resource at level 1", eObject2, uriClass1);
		// level 2
		URI uriClass2 = URI.createPlatformResourceURI(RESOURCE_URI + "Package1.uml#_sRCZ4H04Ed-RG-XtCG9Nmw", false);
		EObject eObject3 = modelSet.getEObject(uriClass2, true);
		assertTestGetObjectOfControlledResource("Load object in a controlled resource at level 2", eObject3, uriClass2);
	}

	private void assertTestGetObjectOfControlledResource(String message, Object object, URI uri) {
		switch(getStrategy()) {
		case 0:
			// Load all the needed resources
			assertNotNull(message, object);
			break;
		case 1:
			// Load the additional resources (profile and pathmap). Controlled resources are not loaded
			if(uri.toString().contains("model1")) {
				// object in current resource
				assertNotNull(message, object);
			} else {
				// object in controlled resource
				assertNull(message, object);
			}
			break;
		case 2:
			// Load the additional resources (profile and pathmap) and the needed controlled resources
			assertNotNull(message, object);
			break;
		default:
			break;
		}
	}

	/**
	 * Gets diagrams of controlled resources.
	 * - get a diagram (NewDiagram) from the current resource (model1)
	 * - get a diagram (Diagram0) from the first controlled resource (Package0)
	 * - get a diagram (Diagram1) from the second controlled resource (Package1)
	 */
	public void testGetDiagramOfControlledResource() {
		// get notation reference from di resource
		// level 0
		URI uriNewDiagram = URI.createPlatformResourceURI(RESOURCE_URI + "model1.notation#_QtEHgH04Ed-RG-XtCG9Nmw", false);
		EObject newDiagram = modelSet.getEObject(uriNewDiagram, true);
		assertTestGetDiagramOfControlledResource("Load diagram in a controlled resource at level 2", newDiagram, uriNewDiagram);
		// level 1
		URI uriDiagram0 = URI.createPlatformResourceURI(RESOURCE_URI + "Package0.notation#_mvutcH04Ed-RG-XtCG9Nmw", false);
		EObject diagram0 = modelSet.getEObject(uriDiagram0, true);
		assertTestGetDiagramOfControlledResource("Load diagram in a controlled resource at level 2", diagram0, uriDiagram0);
		// level 2
		URI uriDiagram1 = URI.createPlatformResourceURI(RESOURCE_URI + "Package1.notation#_r1npMH04Ed-RG-XtCG9Nmw", false);
		EObject diagram1 = modelSet.getEObject(uriDiagram1, true);
		assertTestGetDiagramOfControlledResource("Load diagram in a controlled resource at level 2", diagram1, uriDiagram1);
	}

	private void assertTestGetDiagramOfControlledResource(String message, Object object, URI uri) {
		switch(getStrategy()) {
		case 0:
			// Load all the needed resources
			assertNotNull(message, object);
			break;
		case 1:
			// Load the additional resources (profile and pathmap). Controlled resources are not loaded
			if(uri.toString().contains("model1")) {
				// object in current resource
				assertNotNull(message, object);
			} else {
				// object in controlled resource
				assertNull(message, object);
			}
			break;
		case 2:
			// Load the additional resources (profile and pathmap) and the needed controlled resources
			assertNotNull(message, object);
			break;
		default:
			break;
		}
	}

	/**
	 * Gets a needed profile resource from the local project
	 */
	public void testGetObjectOfProfileResource() {
		URI uriPlatformProfile = URI.createPlatformResourceURI(RESOURCE_URI + "MyProfile.uml#_XkGiwB07Ed-QQ4mYkrb7Gg", false);
		System.err.println(uriPlatformProfile);
		Resource resource = modelSet.getResource(uriPlatformProfile, true);
		assertNotNull("Resource should not be null", resource);

		EObject platformProfile = modelSet.getEObject(uriPlatformProfile, true);
		// profile must be loaded with all the strategies
		assertNotNull("Load a platform profile resource", platformProfile);
	}

	/**
	 * Gets a needed profile resource from a pathmap
	 */
	public void testGetObjectOfPathmapResource() {
		URI uriPathmapProfile = URI.createURI("pathmap://UML_PROFILES/Ecore.profile.uml#_0");
		EObject pathmapProfile = modelSet.getEObject(uriPathmapProfile, true);
		// pathmap must be loaded with all the strategies
		assertNotNull("Load a pathmap profile resource", pathmapProfile);
	}

	/**
	 * Gets a reference which has been moved to another resource after control action
	 * - Property0 from Class01 is typed with Class2 in Package2
	 * - Control Package2 from Package1 resource
	 * - try to get the property type from model1 resource to check the reference update
	 */
	public void testGetDanglingReferenceFromAControlledResource() {
		URI uriProperty0 = URI.createPlatformResourceURI(RESOURCE_URI + "model1.uml#_RHuPYIQsEd-SDs-So_GGkw", false);
		EObject property0 = modelSet.getEObject(uriProperty0, true);
		if(property0 instanceof Property) {
			Type type = ((Property)property0).getType();
			assertTestGetDanglingReferenceFromAControlledResource("Get type from controlled resource is resolved", type);
		}
	}

	private void assertTestGetDanglingReferenceFromAControlledResource(String message, EObject eObject) {
		switch(getStrategy()) {
		case 0:
			// Load all the needed resources
			assertTrue(message, !eObject.eIsProxy());
			break;
		case 1:
			// Load the additional resources (profile and pathmap). Controlled resources are not loaded
			assertTrue(message, eObject.eIsProxy());
			break;
		case 2:
			// Load the additional resources (profile and pathmap) and the needed controlled resources
			assertTrue(message, !eObject.eIsProxy());
			break;
		default:
			break;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void tearDown() throws Exception {
		// Unload models
		for(Resource r : modelSet.getResources()) {
			try {
				r.unload();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.tearDown();
	}

	/**
	 * Creates the folder name in the specified project
	 * 
	 * @param project
	 * @param name
	 * @throws CoreException
	 */
	private void createFolder(IProject project, String name) throws CoreException {
		IFolder parent = project.getFolder(name);
		if(!parent.exists()) {
			parent.create(true, true, new NullProgressMonitor());
		}
		assert (parent.exists());
	}
}
