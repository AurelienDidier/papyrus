/*******************************************************************************
 * Copyright (c) 2009 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.tabbedproperties.uml.parts.forms;

// Start of user code for imports

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.parts.EEFMessageManager;
import org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.api.policies.IPropertiesEditionPolicy;
import org.eclipse.emf.eef.runtime.api.providers.IPropertiesEditionPolicyProvider;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.parts.CompositePropertiesEditionPart;
import org.eclipse.emf.eef.runtime.impl.policies.EObjectPropertiesEditionContext;
import org.eclipse.emf.eef.runtime.impl.services.PropertiesEditionPolicyProviderService;
import org.eclipse.emf.eef.runtime.impl.utils.EMFListEditUtil;
import org.eclipse.emf.eef.runtime.ui.widgets.FormUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.TabElementTreeSelectionDialog;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.tabbedproperties.uml.parts.GeneralizationPropertiesEditionPart;
import org.eclipse.papyrus.tabbedproperties.uml.parts.UMLViewsRepository;
import org.eclipse.papyrus.tabbedproperties.uml.providers.UMLMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IMessageManager;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.uml2.uml.GeneralizationSet;
import org.eclipse.uml2.uml.UMLPackage;

// End of user code

/**
 * @author <a href="mailto:jerome.benois@obeo.fr">Jerome Benois</a>
 */
public class GeneralizationPropertiesEditionPartForm extends CompositePropertiesEditionPart implements IFormPropertiesEditionPart, GeneralizationPropertiesEditionPart {

	protected Button isSubstitutable;
	private EMFListEditUtil generalizationSetEditUtil;
	protected ReferencesTable<? extends EObject> generalizationSet;
	protected List<ViewerFilter> generalizationSetBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> generalizationSetFilters = new ArrayList<ViewerFilter>();




	
	/**
	 * Default constructor
	 * @param editionComponent the {@link IPropertiesEditionComponent} that manage this part
	 */
	public GeneralizationPropertiesEditionPartForm(IPropertiesEditionComponent editionComponent) {
		super(editionComponent);
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart#
	 * 			createFigure(org.eclipse.swt.widgets.Composite, org.eclipse.ui.forms.widgets.FormToolkit)
	 */
	public Composite createFigure(final Composite parent, final FormToolkit widgetFactory) {
		ScrolledForm scrolledForm = widgetFactory.createScrolledForm(parent);
		Form form = scrolledForm.getForm();
		view = form.getBody();
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		view.setLayout(layout);
		createControls(widgetFactory, view, new EEFMessageManager(scrolledForm, widgetFactory));
		return scrolledForm;
	}
	
	/**
	 * {@inheritDoc}
	 * @see org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart#
	 * 			createControls(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite, org.eclipse.ui.forms.IMessageManager)
	 */
	public void createControls(final FormToolkit widgetFactory, Composite view, IMessageManager messageManager) {
		this.messageManager = messageManager;
		createGeneralGroup(widgetFactory, view);
		// Start of user code for additional ui definition

		// End of user code
		
	}

	protected void createGeneralGroup(FormToolkit widgetFactory, final Composite view) {
		Section generalSection = widgetFactory.createSection(view, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		generalSection.setText(UMLMessages.GeneralizationPropertiesEditionPart_GeneralGroupLabel);
		GridData generalSectionData = new GridData(GridData.FILL_HORIZONTAL);
		generalSectionData.horizontalSpan = 3;
		generalSection.setLayoutData(generalSectionData);
		Composite generalGroup = widgetFactory.createComposite(generalSection);
		GridLayout generalGroupLayout = new GridLayout();
		generalGroupLayout.numColumns = 3;
		generalGroup.setLayout(generalGroupLayout);
		createIsSubstitutableCheckbox(widgetFactory, generalGroup);
		createGeneralizationSetReferencesTable(widgetFactory, generalGroup);
		generalSection.setClient(generalGroup);
	}
	protected void createIsSubstitutableCheckbox(FormToolkit widgetFactory, Composite parent) {
		isSubstitutable = widgetFactory.createButton(parent, UMLMessages.GeneralizationPropertiesEditionPart_IsSubstitutableLabel, SWT.CHECK);
		isSubstitutable.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(GeneralizationPropertiesEditionPartForm.this, UMLViewsRepository.Generalization.isSubstitutable, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(isSubstitutable.getSelection())));
			}

		});
		GridData isSubstitutableData = new GridData(GridData.FILL_HORIZONTAL);
		isSubstitutableData.horizontalSpan = 2;
		isSubstitutable.setLayoutData(isSubstitutableData);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(UMLViewsRepository.Generalization.isSubstitutable, UMLViewsRepository.FORM_KIND), null); //$NON-NLS-1$
	}
	protected void createGeneralizationSetReferencesTable(FormToolkit widgetFactory, Composite parent) {
		this.generalizationSet = new ReferencesTable<GeneralizationSet>(UMLMessages.GeneralizationPropertiesEditionPart_GeneralizationSetLabel, new ReferencesTableListener<GeneralizationSet>() {
			public void handleAdd() {
				TabElementTreeSelectionDialog<GeneralizationSet> dialog = new TabElementTreeSelectionDialog<GeneralizationSet>(resourceSet, generalizationSetFilters, generalizationSetBusinessFilters,
				"GeneralizationSet", UMLPackage.eINSTANCE.getGeneralizationSet(), current.eResource()) {
					@Override
					public void process(IStructuredSelection selection) {
						for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
							EObject elem = (EObject) iter.next();
							if (!generalizationSetEditUtil.getVirtualList().contains(elem))
								generalizationSetEditUtil.addElement(elem);
							propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(GeneralizationPropertiesEditionPartForm.this, UMLViewsRepository.Generalization.generalizationSet,
								PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
						}
						generalizationSet.refresh();
					}
				};
				dialog.open();
			}
			public void handleEdit(GeneralizationSet element) { editGeneralizationSet(element); }
			public void handleMove(GeneralizationSet element, int oldIndex, int newIndex) { moveGeneralizationSet(element, oldIndex, newIndex); }
			public void handleRemove(GeneralizationSet element) { removeFromGeneralizationSet(element); }
			public void navigateTo(GeneralizationSet element) { }
		});
		this.generalizationSet.setHelpText(propertiesEditionComponent.getHelpContent(UMLViewsRepository.Generalization.generalizationSet, UMLViewsRepository.FORM_KIND));
		this.generalizationSet.createControls(parent, widgetFactory);
		GridData generalizationSetData = new GridData(GridData.FILL_HORIZONTAL);
		generalizationSetData.horizontalSpan = 3;
		this.generalizationSet.setLayoutData(generalizationSetData);
		this.generalizationSet.disableMove();
	}

	/**
	 * 
	 */
	protected void moveGeneralizationSet(GeneralizationSet element, int oldIndex, int newIndex) {
		EObject editedElement = generalizationSetEditUtil.foundCorrespondingEObject(element);
		generalizationSetEditUtil.moveElement(element, oldIndex, newIndex);
		generalizationSet.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(GeneralizationPropertiesEditionPartForm.this, UMLViewsRepository.Generalization.generalizationSet, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, editedElement, newIndex));
	}

	/**
	 * 
	 */
	protected void removeFromGeneralizationSet(GeneralizationSet element) {
		// Start of user code for the removeFromGeneralizationSet() method body
		EObject editedElement = generalizationSetEditUtil.foundCorrespondingEObject(element);
		generalizationSetEditUtil.removeElement(element);
		generalizationSet.refresh();
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
				GeneralizationPropertiesEditionPartForm.this, UMLViewsRepository.Generalization.generalizationSet,
				PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, editedElement));
		// End of user code

	}

	/**
	 * 
	 */
	protected void editGeneralizationSet(GeneralizationSet element) {
		// Start of user code editGeneralizationSet() method body

		EObject editedElement = generalizationSetEditUtil.foundCorrespondingEObject(element);
		IPropertiesEditionPolicyProvider policyProvider = PropertiesEditionPolicyProviderService.getInstance()
				.getProvider(element);
		IPropertiesEditionPolicy editionPolicy = policyProvider.getEditionPolicy(editedElement);
		if (editionPolicy != null) {
			EObject propertiesEditionObject = editionPolicy
					.getPropertiesEditionObject(new EObjectPropertiesEditionContext(null, element, resourceSet));
			if (propertiesEditionObject != null) {
				generalizationSetEditUtil.putElementToRefresh(editedElement, propertiesEditionObject);
				generalizationSet.refresh();
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(
						GeneralizationPropertiesEditionPartForm.this,
						UMLViewsRepository.Generalization.generalizationSet, PropertiesEditionEvent.COMMIT,
						PropertiesEditionEvent.SET, editedElement, propertiesEditionObject));
			}
		}
		// End of user code

	}

	
	public void firePropertiesChanged(PropertiesEditionEvent event) {
		// Start of user code for tab synchronization

		// End of user code
		
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.GeneralizationPropertiesEditionPart#getIsSubstitutable()
	 */
	public Boolean getIsSubstitutable() {
		return Boolean.valueOf(isSubstitutable.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.GeneralizationPropertiesEditionPart#setIsSubstitutable(Boolean newValue)
	 */
	public void setIsSubstitutable(Boolean newValue) {
		if (newValue != null) {
			isSubstitutable.setSelection(newValue.booleanValue());
		} else {
			isSubstitutable.setSelection(false);
		}
	}





	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.GeneralizationPropertiesEditionPart#getGeneralizationSetToAdd()
	 */
	public List getGeneralizationSetToAdd() {
		return generalizationSetEditUtil.getElementsToAdd();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.GeneralizationPropertiesEditionPart#getGeneralizationSetToRemove()
	 */
	public List getGeneralizationSetToRemove() {
		return generalizationSetEditUtil.getElementsToRemove();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.GeneralizationPropertiesEditionPart#getGeneralizationSetTable()
	 */
	public List getGeneralizationSetTable() {
		return generalizationSetEditUtil.getVirtualList();
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.GeneralizationPropertiesEditionPart#initGeneralizationSet(EObject current, EReference containingFeature, EReference feature)
	 */
	public void initGeneralizationSet(EObject current, EReference containingFeature, EReference feature) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		if (containingFeature != null)
			generalizationSetEditUtil = new EMFListEditUtil(current, containingFeature, feature);
		else
			generalizationSetEditUtil = new EMFListEditUtil(current, feature);
		this.generalizationSet.setInput(generalizationSetEditUtil.getVirtualList());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.GeneralizationPropertiesEditionPart#updateGeneralizationSet(EObject newValue)
	 */
	public void updateGeneralizationSet(EObject newValue) {
		if(generalizationSetEditUtil != null){
			generalizationSetEditUtil.reinit(newValue);
			generalizationSet.refresh();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.GeneralizationPropertiesEditionPart#addFilterGeneralizationSet(ViewerFilter filter)
	 */
	public void addFilterToGeneralizationSet(ViewerFilter filter) {
		generalizationSetFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.GeneralizationPropertiesEditionPart#addBusinessFilterGeneralizationSet(ViewerFilter filter)
	 */
	public void addBusinessFilterToGeneralizationSet(ViewerFilter filter) {
		generalizationSetBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.tabbedproperties.uml.parts.GeneralizationPropertiesEditionPart#isContainedInGeneralizationSetTable(EObject element)
	 */
	public boolean isContainedInGeneralizationSetTable(EObject element) {
		return generalizationSetEditUtil.contains(element);
	}











	
	// Start of user code additional methods

	// End of user code

}	