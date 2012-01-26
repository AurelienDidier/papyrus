package org.eclipse.papyrus.infra.core.wizard;

import org.eclipse.papyrus.uml.diagram.wizards.NewPapyrusProjectWizard;
import org.eclipse.papyrus.uml.diagram.wizards.pages.SelectDiagramCategoryPage;
import org.eclipse.papyrus.uml.diagram.wizards.pages.SelectDiagramKindPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;


public class TestNewProjectWizard extends TestNewModelWizardBase {
	
	
	protected IWorkbenchWizard createWizard() {
		return new NewPapyrusProjectWizard();
	}

	public void testOrderOfPages() {
		
		Class[] expectedPages = new Class[]{
			WizardNewProjectCreationPage.class,
			SelectDiagramCategoryPage.class,
			SelectDiagramKindPage.class,
		};

		IWorkbenchWizard wizard = initWizardDialog();
		testOrderOfPages(wizard, expectedPages);
	}

	public void testProfileExtension() {
	}


}
