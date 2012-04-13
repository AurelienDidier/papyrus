/*****************************************************************************
 * Copyright (c) 2011 Atos.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Arthur Daussy (Atos) - Initial API and implementation
 *   Arthur Daussy - 371712 : 372745: [ActivityDiagram] Major refactoring group framework
 *
 *****************************************************************************/
package org.eclipse.papyrus.diagram.activity.activitygroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.naming.InitialContext;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.commands.wrappers.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.diagram.activity.activitygroup.editpolicy.notifiers.GroupNotifyingOnMoveEditPolicy;
import org.eclipse.papyrus.diagram.activity.activitygroup.editpolicy.notifiers.IGroupNotifier;
import org.eclipse.papyrus.diagram.activity.activitygroup.predicates.AncestorFilter;
import org.eclipse.papyrus.diagram.activity.activitygroup.predicates.DescendantsFilter;
import org.eclipse.papyrus.diagram.activity.activitygroup.predicates.DescendantsFilterIGroupNotifier;
import org.eclipse.papyrus.diagram.activity.activitygroup.request.IGroupRequest;
import org.eclipse.papyrus.diagram.activity.activitygroup.request.SetDeferredRequest;
import org.eclipse.papyrus.diagram.activity.activitygroup.ui.IntegrateViewToConfigureComposite;
import org.eclipse.papyrus.diagram.activity.activitygroup.utils.DebugUtils;
import org.eclipse.papyrus.diagram.activity.activitygroup.utils.Utils;
import org.eclipse.papyrus.diagram.activity.commands.DeferredSetValueCommand;
import org.eclipse.papyrus.diagram.activity.commands.RunNotificationCommand;
import org.eclipse.papyrus.diagram.activity.edit.part.ActivityGroup.IGroupCompartmentEditPart;
import org.eclipse.papyrus.diagram.activity.part.UMLDiagramEditorPlugin;
import org.eclipse.papyrus.diagram.common.commands.RemoveValueCommand;
import org.eclipse.papyrus.diagram.common.commands.RemoveValueRequest;
import org.eclipse.papyrus.log.LogHelper;
import org.eclipse.papyrus.ui.toolbox.notification.ICompositeCreator;
import org.eclipse.papyrus.ui.toolbox.notification.INotification;
import org.eclipse.papyrus.ui.toolbox.notification.NotificationRunnable;
import org.eclipse.papyrus.ui.toolbox.notification.Type;
import org.eclipse.papyrus.ui.toolbox.notification.builders.IContext;
import org.eclipse.papyrus.ui.toolbox.notification.builders.NotificationBuilder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

public class GroupRequestAdvisor implements IGroupRequestAdvisor {

	/**
	 * Exception message
	 */
	private static final String UNABLE_TO_GET_THE_INTIAL_TARGET_REQUEST_BOUNDS = "Unable to get the intial target request bounds";////$NON-NLS-1$

	private static final String GROUP_FRAMEWORK_GRAPHICAL_PARENT = "GroupFramework_GraphicalParent";

	/**
	 * Debug message
	 */
	private static final String CHILDREN_REFERENCES_ARE = " ---- Children references are : ---- ";

	/**
	 * Debug message
	 */
	private static final String ALL_PARENT_REFERENCES_ARE = " ---- All parent references are : ---- ";

	/**
	 * Runnable to run command to change parent of selected element
	 * @author arthur daussy
	 *
	 */
	private final class ChangeGraphicalParentRunnable implements NotificationRunnable {
		/**
		 * New graphical host
		 */
		private final IGraphicalEditPart host;
		/**
		 * Initial request
		 */
		private final IGroupRequest request;
		/**
		 * List of all modified view
		 * Used to compute list of modfied files
		 */
		private List<View> viewTomodify;

		/**
		 * Constructor
		 * @param host  New graphical host
		 * @param request  Initial request
		 */
		private ChangeGraphicalParentRunnable(IGraphicalEditPart host, IGroupRequest request) {
			this.host = host;
			this.request = request;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.papyrus.ui.toolbox.notification.NotificationRunnable#run(org.eclipse.papyrus.ui.toolbox.notification.builders.IContext)
		 */
		public void run(IContext context) {
			IntegrateViewToConfigureComposite composite = (IntegrateViewToConfigureComposite)context.get(IContext.COMPOSITE_CREATED);
			final INotification notif = (INotification)context.get(IContext.NOTIFICATION_OBJECT);
			CompositeTransactionalCommand ccMovingElement = new CompositeTransactionalCommand(request.getHostRequest().getEditingDomain(), "Move graphical elements into a new host");////$NON-NLS-1$
			/*
			 * Use to calculate impacted file
			 */
			viewTomodify = Lists.newArrayList();
			IGraphicalEditPart hostEditPart = request.getHostRequest();
			viewTomodify.add(host.getNotationView());
			for(IGroupNotifier notifier : composite.getSelectedNotifier()) {
				IGraphicalEditPart notifierEditPart = notifier.getHostEditPart();
				IGraphicalEditPart hostCompartmentEditPart = request.getNodeDescpitor().getCompartmentPartFromView(hostEditPart);
				/*
				 * debugging label
				 */
				if(DebugUtils.isDebugging()) {
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append("Move ");
					stringBuilder.append(Utils.getCorrectLabel(notifierEditPart));
					stringBuilder.append(" to ");
					stringBuilder.append(Utils.getCorrectLabel(hostEditPart));
					DebugUtils.getLog().info(stringBuilder.toString());
				}
				if(notifierEditPart != null) {
					EObject semanticElementToDrop = notifierEditPart.resolveSemanticElement();
					if(semanticElementToDrop != null && hostCompartmentEditPart != null) {
						/*
						 * Request to change graphical parent 
						 */
						ChangeBoundsRequest chdBoundsRequest = new ChangeBoundsRequest(RequestConstants.REQ_ADD);
						chdBoundsRequest.setEditParts(notifierEditPart);
						viewTomodify.add(notifierEditPart.getNotationView());
						Command dropcommand = hostCompartmentEditPart.getCommand(chdBoundsRequest);
						if(dropcommand != null) {
							ccMovingElement.compose(new CommandProxy(dropcommand));
						}
					} else {
						//log error
					}
				} else {
					//log error
				}
			}
			/*
			 * Execute resulting command
			 */
			if(ccMovingElement != null && !ccMovingElement.isEmpty()) {
				host.getEditingDomain().getCommandStack().execute(new GMFtoEMFCommandWrapper(ccMovingElement));
			}
			notif.delete();
		}

		/* (non-Javadoc)
		 * @see org.eclipse.papyrus.ui.toolbox.notification.NotificationRunnable#getLabel()
		 */
		public String getLabel() {
			return "OK";
		}
		
		/**
		 * @return the list of all EObject modifed
		 */
		public List getModifiedObject(){
			return viewTomodify == null?Collections.emptyList():viewTomodify;
		}
	}

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance()
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class SingletonHolder {
		public static final GroupRequestAdvisor instance = new GroupRequestAdvisor();
	}

	/**
	 * Get the instance of {@link GroupRequestAdvisor}
	 * 
	 * @return
	 */
	public static GroupRequestAdvisor getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * Store all the listener
	 */
	private Multimap<EObject, IGroupNotifier> listenners;

	/**
	 * Logger
	 */
	private static LogHelper log = new LogHelper(UMLDiagramEditorPlugin.getInstance());

	/***
	 * Private constructor
	 */
	private GroupRequestAdvisor() {
		listenners = ArrayListMultimap.create();
	}

	/**
	 * Add a {@link IGroupNotifier}
	 * 
	 * @param editpolicy
	 */
	public void addListenner(EObject eObject, IGroupNotifier editpolicy) {
		listenners.put(eObject, editpolicy);
	}

	/**
	 * Remove a {@link IGroupNotifier}
	 * 
	 * @param editpolicy
	 */
	public void removeListenner(IGroupNotifier editpolicy) {
		/**
		 * TODO test
		 */
		if(editpolicy == null) {
			return;
		}
		Iterator<Entry<EObject, IGroupNotifier>> iterator = listenners.entries().iterator();
		while(iterator.hasNext()) {
			Entry<EObject, IGroupNotifier> entry = iterator.next();
			if(editpolicy.equals(entry.getValue())) {
				iterator.remove();
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ICommand notifyGroupFramework(IGroupRequest request) {
		if(DebugUtils.isDebugging()) {
			log.debug("***********************BEGIN : Group Request Advisor***********************************");
		}
		/******************
		 * Handling parent
		 ******************/
		CompositeCommand cc = new CompositeCommand("Global command from GroupRequestAdvisor");
		/*
		 * All parent
		 */
		fillRequestWithAllPossibleParent(request);
		fillRequestWithAllPossibleChildren(request);
		Object elementAdapter = request.getTargetElement().getAdapter(EObject.class);
		if(elementAdapter instanceof EObject) {
			/*
			 * Handle children
			 */
			handleSemanticChildren(request, cc, (EObject)elementAdapter);
			/*
			 * Semantic of all the parent
			 */
			handleSemanticParents(request, cc, (EObject)elementAdapter);
		}
		if(DebugUtils.isDebugging()) {
			log.debug("***********************END : Group Request Advisor***********************************");
		}
		/*
		 * For all new child send request
		 * For all GrChild create new request from old one and use dispatcher
		 */
		if(cc != null && !cc.isEmpty()) {
			return cc;
		}
		return null;
	}

	/**
	 * Handle all the children of the group (graphical and non graphical children)
	 * @param request
	 * @param cc
	 * @param targetElement
	 */
	protected void handleSemanticChildren(final IGroupRequest request, CompositeCommand cc, EObject targetElement) {
		List<EObject> graphicalChildren = Lists.newArrayList();
		/*
		 * There is graphical childrne only when the node is moving
		 */
		if(request.getInitialRequest() instanceof ChangeBoundsRequest) {
			graphicalChildren = handleGraphicalChildren(request, cc);
		}
		/*
		 * Unset
		 */
		for(Entry<EReference, EObject> oldChildrenEntry : Utils.getOldChildren(request).entries()) {
			Multimap<EReference, EObject> allActualChildren = request.getChildrenEReferenceMap();
			EReference ref = oldChildrenEntry.getKey();
			if(ref != null) {
				if(!allActualChildren.containsEntry(oldChildrenEntry.getValue(), ref)) {
					EReference eOpposite = ref.getEOpposite();
					if(!graphicalChildren.contains(oldChildrenEntry.getValue())) {
						if(eOpposite != null && !eOpposite.isContainment() && !ref.isContainment()) {
							RemoveValueRequest rmVa = new RemoveValueRequest(targetElement, ref, Collections.singletonList(oldChildrenEntry.getValue()));
							RemoveValueCommand rmCmd = new RemoveValueCommand(rmVa);
							StringBuilder stringBuilder = new StringBuilder();
							stringBuilder.append("From handling parent : Remove ");////$NON-NLS-1$
							stringBuilder.append(Utils.getCorrectLabel(ref));
							stringBuilder.append(" from ");////$NON-NLS-1$
							stringBuilder.append(Utils.getCorrectLabel(targetElement));
							stringBuilder.append(" value ");////$NON-NLS-1$
							stringBuilder.append(Utils.getCorrectLabel(oldChildrenEntry.getValue()));////$NON-NLS-1$
							rmCmd.setLabel(stringBuilder.toString());
							if(rmCmd != null) {
								cc.compose(rmCmd);
							}
						}
					}
				}
			}
		}
		/*
		 * Set semantic
		 */
		Set<EObject> newChildren = Sets.newHashSet();
		for(Entry<EReference, EObject> entry : request.getChildrenEReferenceMap().entries()) {
			EReference ref = entry.getKey();
			if(ref != null) {
				EReference eOpposite = ref.getEOpposite();
				if(!graphicalChildren.contains(entry.getValue())) {
					newChildren.add(entry.getValue());
					if(eOpposite != null && !eOpposite.isContainment()) {
						SetDeferredRequest setRq = new SetDeferredRequest(request.getHostRequest().getEditingDomain(), request.getTargetElement(), ref, entry.getValue());
						DeferredSetValueCommand setCmd = new DeferredSetValueCommand(setRq);
						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append("From handling parent : Set deferred");////$NON-NLS-1$
						stringBuilder.append(Utils.getCorrectLabel(entry.getValue()));
						stringBuilder.append(" from ");////$NON-NLS-1$
						stringBuilder.append(Utils.getCorrectLabel(ref));
						stringBuilder.append(" value ");////$NON-NLS-1$
						stringBuilder.append(Utils.getCorrectLabel(entry.getValue()));////$NON-NLS-1$
						setCmd.setLabel(stringBuilder.toString());
						if(setCmd != null) {
							cc.compose(setCmd);
						}
					}
				}
				if(!newChildren.isEmpty()) {
					handleChangeParentNotificationCommand(request, cc, newChildren);
				}
			}
		}
	}
	/**
	 * Create a notification to user to ask him is he want to add its new children as graphical child of its parent group
	 * @param request {@link InitialContext} {@link IGroupRequest}
	 * @param cc {@link CompositeCommand} to compose new commands
	 * @param newChildren List of all new children
	 */
	protected void handleChangeParentNotificationCommand(final IGroupRequest request, CompositeCommand cc, Set<EObject> newChildren) {
		/*
		 * Create dialog to suggest user to move views
		 */
		final IGraphicalEditPart host = request.getHostRequest();
		/*
		 * Get editpart of all children

		 */
		Iterable<IGroupNotifier> listOfChidren = Iterables.transform(newChildren, new Function<EObject, IGroupNotifier>() {

			public IGroupNotifier apply(EObject arg0) {
				Collection<IGroupNotifier> notifiers = listenners.get(arg0);
				for(IGroupNotifier notifier : notifiers) {
					IGraphicalEditPart notifierEditPart = notifier.getHostEditPart();
					if(notifierEditPart != null) {
						if(EcoreUtil.isAncestor(host.getNotationView().getDiagram(), notifierEditPart.getNotationView())) {
							return notifier;
						}
					}
				}
				return null;
			}
		});
		final Iterable<IGroupNotifier> listOfFilteredChidren = Iterables.filter(listOfChidren, Predicates.notNull());
		/*
		 * create runnable to run the command
		 */
		ChangeGraphicalParentRunnable changeGraphicalParentRunnable = new ChangeGraphicalParentRunnable(host, request);
		final NotificationBuilder notification = createChangeGraphicalParentNotification(listOfFilteredChidren, changeGraphicalParentRunnable);
		
		RunNotificationCommand runNotifCmd = new RunNotificationCommand(request.getHostRequest().getEditingDomain(), "Notification command", getWorkspaceFiles(changeGraphicalParentRunnable.getModifiedObject()), notification);////$NON-NLS-1$
		cc.compose(runNotifCmd);
	}
	/**
	 * Create a notification to ask useris he want to change the graphical parent of some elements
	 * @param listOfFilteredChidren
	 * @param changeGraphicalParentRunnable
	 * @return
	 */
	private NotificationBuilder createChangeGraphicalParentNotification(final Iterable<IGroupNotifier> listOfFilteredChidren, ChangeGraphicalParentRunnable changeGraphicalParentRunnable) {
		return new NotificationBuilder().setType(Type.INFO).setAsynchronous(true).setTemporary(true).setDelay(3000).setTitle("Papyrus graphical modification").setComposite(new ICompositeCreator() {

			public Composite createComposite(Composite parent, FormToolkit toolkit) {
				return new IntegrateViewToConfigureComposite(parent, SWT.NONE, Lists.newArrayList(Lists.newArrayList(listOfFilteredChidren)),"New elements are visually contained in the current group.\n Please select the elements you want to integrate into this group figure.");
			}
		}).addAction(changeGraphicalParentRunnable);
	}

	/**
	 * Handle graphical children
	 * 
	 * @param request
	 *        {@link IGroupRequest}
	 * @param cc
	 *        {@link CompositeCommand} to compose new commands
	 * @return The list of all graphical children already handled
	 */
	protected List<EObject> handleGraphicalChildren(IGroupRequest request, CompositeCommand cc) {
		/*
		 * List of all the graphical children
		 */
		List<EObject> graphicalChildren = new ArrayList<EObject>();
		/*
		 * Handle graphical children
		 */
		//Get graphical children
		Object targetEditPart = request.getTargetElement().getAdapter(IGraphicalEditPart.class);
		IGraphicalEditPart compartementEditPart = null;
		if(targetEditPart instanceof IGroupCompartmentEditPart) {
			compartementEditPart = ((IGraphicalEditPart)targetEditPart);
		} else if(targetEditPart instanceof IGraphicalEditPart) {
			compartementEditPart = request.getNodeDescpitor().getCompartmentPartFromView((IGraphicalEditPart)targetEditPart);
		}
		if(compartementEditPart != null) {
			/*
			 * Transform an IGraphicalEditPart to the related GroupNotifyingEditPolicy
			 */
			@SuppressWarnings("unchecked")
			Iterable<GroupNotifyingOnMoveEditPolicy> policies = Iterables.transform(compartementEditPart.getChildren(), new Function<IGraphicalEditPart, GroupNotifyingOnMoveEditPolicy>() {

				public GroupNotifyingOnMoveEditPolicy apply(IGraphicalEditPart from) {
					EditPolicy editPolicy = from.getEditPolicy(IGroupEditPolicies.GROUP_FRAMEWORK_NOTIFYING_ON_MOVE_EDIT_POLICY);
					if(editPolicy instanceof GroupNotifyingOnMoveEditPolicy) {
						return (GroupNotifyingOnMoveEditPolicy)editPolicy;
					}
					return null;
				}
			});
			for(final GroupNotifyingOnMoveEditPolicy p : policies) {
				if(p != null) {
					if(DebugUtils.isDebugging()) {
						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append("+++ Work for child ");
						stringBuilder.append(Utils.getCorrectLabel(p.getEObject()));
						stringBuilder.append(" +++");
						DebugUtils.getLog().debug(stringBuilder.toString());
					}
					ChangeBoundsRequest auxChReq = Utils.getChangeBoundsRequestCopy((ChangeBoundsRequest)request.getInitialRequest(), p.getHostEditPart());
					/*
					 * Save graphical parent
					 */
					auxChReq.getExtendedData().put(GROUP_FRAMEWORK_GRAPHICAL_PARENT, compartementEditPart.resolveSemanticElement());
					graphicalChildren.add(p.getEObject());
					Command childCommand = p.getCommand(auxChReq);
					if(childCommand != null && childCommand.canExecute()) {
						cc.compose(new CommandProxy(childCommand));
					}
				}
			}
		}
		return graphicalChildren;
	}

	/**
	 * Set and Unset semantic relation with parents
	 * 
	 * @param request
	 *        {@link IGroupRequest}
	 * @param cc
	 *        {@link CompositeCommand} to compose command
	 * @param elementAdapter
	 */
	protected void handleSemanticParents(IGroupRequest request, CompositeCommand cc, Object elementAdapter) {
		EObject targetElement = (EObject)elementAdapter;
		Multimap<EReference, EObject> allActualParent = request.getParentEReferenceMap();
		for(Entry<EReference, EObject> entry : Utils.getOldParents(request).entries()) {
			EReference ref = entry.getKey();
			if(ref != null) {
				if(!allActualParent.containsEntry(ref, entry.getValue())) {
					EReference eOpposite = ref.getEOpposite();
					if(eOpposite != null && !eOpposite.isContainment()) {
						RemoveValueRequest rmVa = new RemoveValueRequest(targetElement, ref, Collections.singletonList(entry.getValue()));
						RemoveValueCommand rmCmd = new RemoveValueCommand(rmVa);
						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append("From handling parent : Remove ");////$NON-NLS-1$
						stringBuilder.append(Utils.getCorrectLabel(entry.getValue()));
						stringBuilder.append(" from ");////$NON-NLS-1$
						stringBuilder.append(Utils.getCorrectLabel(targetElement));
						stringBuilder.append(" value ");////$NON-NLS-1$
						stringBuilder.append(Utils.getCorrectLabel(entry.getValue()));////$NON-NLS-1$
						rmCmd.setLabel(stringBuilder.toString());
						if(rmCmd != null) {
							cc.compose(rmCmd);
						}
					}
				}
			}
		}
		/*
		 * Set semantic
		 *  If the EObject has not been already handle as graphical children
		 */

		for(Entry<EReference, EObject> entry : request.getParentEReferenceMap().entries()) {
			EReference ref = entry.getKey();
			if(ref != null) {
				EReference eOpposite = ref.getEOpposite();
				if(eOpposite != null && !eOpposite.isContainment() && !ref.isContainment()) {
					SetDeferredRequest setRq = new SetDeferredRequest(request.getHostRequest().getEditingDomain(), request.getTargetElement(), ref, entry.getValue());
					DeferredSetValueCommand setCmd = new DeferredSetValueCommand(setRq);
					StringBuilder stringBuilder = new StringBuilder();
					stringBuilder.append("From handling parent : Set deferred ");////$NON-NLS-1$
					stringBuilder.append(Utils.getCorrectLabel(ref));
					stringBuilder.append(" from ");////$NON-NLS-1$
					stringBuilder.append(Utils.getCorrectLabel(request.getTargetElement()));
					stringBuilder.append(" value ");////$NON-NLS-1$
					stringBuilder.append(Utils.getCorrectLabel(entry.getValue()));////$NON-NLS-1$
					setCmd.setLabel(stringBuilder.toString());
					if(setCmd != null) {
						cc.compose(setCmd);
					}
				}
			}
		}
	}

	/**
	 * Return a list of all IGroupRequestListenner which can be children of the target of the request
	 * 
	 * @param request
	 * @return
	 */
	protected Multimap<EReference, IGroupNotifier> fillRequestWithAllPossibleChildren(IGroupRequest request) {
		final Multimap<EReference, IGroupNotifier> result = fillReqestWithReferendedElement(request, false, false);
		/**
		 * TODO filter graphical parent which currently moving
		 * I1 in ActPart1
		 * ActPart1 move into ActPart2
		 * I1 should only reference ActPart1
		 */
		/*
		 * Debug
		 */
		DebugUtils.displayMultipmapDebug(CHILDREN_REFERENCES_ARE, request.getChildrenEReferenceMap());
		return result;
	}

	public EObject getPossibleModelParent(IGroupRequest request) {
		Multimap<EReference, IGroupNotifier> parentsMap = fillReqestWithReferendedElement(request, true, true);
		List<IGroupNotifier> parents = Lists.newArrayList(parentsMap.values());
		Collections.sort(parents);
		if(!parents.isEmpty()) {
			return parents.get(0).getEObject();
		}
		return request.getHostRequest().resolveSemanticElement();
	}

	/**
	 * Return only current displayed listener
	 * 
	 * @author adaussy
	 * 
	 */
	private static class ActiveListener implements Predicate<IGroupNotifier> {

		private Diagram currentDiagramDisplayed;

		public ActiveListener(Diagram currentDiagramDisplayed) {
			super();
			this.currentDiagramDisplayed = currentDiagramDisplayed;
			Assert.isNotNull(currentDiagramDisplayed);
		}

		public boolean apply(IGroupNotifier input) {
			IGraphicalEditPart host = input.getHostEditPart();
			if(host != null) {
				View primaryView = host.getPrimaryView();
				if(primaryView != null) {
					return currentDiagramDisplayed.equals(primaryView.getDiagram());
				}
			}
			return false;
		}
	}

	/**
	 * Get all the element in references which are included or include (depending of the parameter include)
	 * 
	 * @param request
	 *        {@link IGroupRequest}
	 * @param newBounds
	 *        New bounds of the elements
	 * @param references
	 *        All references to take into account
	 * @param eReferenceMapToFillInRequest
	 *        Map in the request to fill
	 * @param result
	 *        {@link Map} which link a {@link EReference} to a {@link IGroupNotifier}
	 * @param containementOnly
	 *        true if we are looking for containing references only
	 */
	protected void getReferenceElements(IGroupRequest request, final Rectangle newBounds, final List<EReference> references, Multimap<EReference, EObject> eReferenceMapToFillInRequest, Multimap<EReference, IGroupNotifier> result, boolean include, boolean containementOnly) {
		Iterable<IGroupNotifier> activeListeners = Iterables.filter(listenners.values(), new ActiveListener(getCurrentlyDisplayedDiagram(request)));
		for(IGroupNotifier input : activeListeners) {
			EObject inputEObject = input.getEObject();
			if(inputEObject == null) {
				continue;
			}
			Object adapter = request.getTargetElement().getAdapter(EObject.class);
			//			EObject targetElement = null;
			//			if(adapter instanceof EObject) {
			//				targetElement = (EObject)adapter;
			//			}
			if(inputEObject.equals(adapter)) {
				continue;
			}
			EReference refenceFounded = null;
			for(EReference ref : references) {
				EClass refType = ref.getEReferenceType();
				EClass eoBjectType = inputEObject.eClass();
				if(refType.isSuperTypeOf(eoBjectType)) {
					refenceFounded = ref;
					break;
				}
			}
			if(refenceFounded != null) {
				if((include && input.includes(newBounds)) || (!include && input.isIncludedIn(newBounds))) {
					if(containementOnly && refenceFounded.getEOpposite() != null && !refenceFounded.getEOpposite().isContainment()) {
						continue;
					}
					eReferenceMapToFillInRequest.put(refenceFounded, inputEObject);
					result.put(refenceFounded, input);
				}
			}
		}
	}

	/**
	 * Return the currently displayed diagram
	 * 
	 * @param request
	 * @return
	 */
	protected Diagram getCurrentlyDisplayedDiagram(IGroupRequest request) {
		IGraphicalEditPart graph = request.getHostRequest();
		if(graph != null) {
			Object m = graph.getModel();
			if(m instanceof View) {
				View v = (View)m;
				return v.getDiagram();
			}
		}
		throw new RuntimeException("Unable to get the current diagram displayed");////$NON-NLS-1$
	}

	/**
	 * Return the list of all {@link IGroupNotifier} which can be parent of the target of the request
	 * 
	 * @param request
	 * @return
	 */
	protected Multimap<EReference, IGroupNotifier> fillRequestWithAllPossibleParent(IGroupRequest request) {
		final Multimap<EReference, IGroupNotifier> result = fillReqestWithReferendedElement(request, true, false);
		/*
		 * Debug
		 */
		DebugUtils.displayMultipmapDebug(ALL_PARENT_REFERENCES_ARE, request.getParentEReferenceMap());
		return result;
	}

	protected Multimap<EReference, IGroupNotifier> fillReqestWithReferendedElement(IGroupRequest request, boolean lookingForParent, boolean onlyContainment) {
		final Rectangle newBounds = getInitalTargetRequestNewBounds(request);
		List<EReference> references = null;
		if(lookingForParent) {
			references = request.getNodeDescpitor().getParentReferences();
		} else {
			references = request.getNodeDescpitor().getChildrenReferences();
		}
		final Multimap<EReference, IGroupNotifier> auxResult = ArrayListMultimap.create();
		final Multimap<EReference, IGroupNotifier> result = ArrayListMultimap.create();
		final Multimap<EReference, EObject> eReferenceLookedForMap = ArrayListMultimap.create();
		getReferenceElements(request, newBounds, references, eReferenceLookedForMap, auxResult, lookingForParent, onlyContainment);
		/*
		 * Filter ancestors
		 */
		for(EReference ref : eReferenceLookedForMap.keySet()) {
			/*
			 * Filter descendant
			 * Example :
			 * 1 - ActPart1 include in Act1 then Act1 disappear
			 * 2 - ActPart1 include in ActPart2 then ActPart1 disappear
			 */
			Collection<EObject> filteredCollection = Collections2.filter(eReferenceLookedForMap.get(ref), lookingForParent ? new DescendantsFilter(eReferenceLookedForMap.values()) : new AncestorFilter(eReferenceLookedForMap.values()));
			if(lookingForParent) {
				request.getParentEReferenceMap().putAll(ref, filteredCollection);
			} else {
				request.getChildrenEReferenceMap().putAll(ref, filteredCollection);
			}
		}
		for(EReference ref : auxResult.keySet()) {
			/*
			 * Filter descendant
			 * Example :
			 * 1 - ActPart1 include in Act1 then Act1 disappear
			 * 2 - ActPart1 include in ActPart2 then ActPart1 disappear
			 */
			Iterable<IGroupNotifier> resultCollection = Iterables.filter(auxResult.get(ref), new DescendantsFilterIGroupNotifier(auxResult.values()));
			result.putAll(ref, resultCollection);
		}
		return result;
	}

	/**
	 * Get the absolute bounds of the target of the request
	 * 
	 * @param request
	 * @return
	 */
	public static Rectangle getInitalTargetRequestNewBounds(final IGroupRequest request) {
		Request initialRequest = request.getInitialRequest();
		if(initialRequest instanceof ChangeBoundsRequest) {
			return Utils.getAbslotueRequestBounds((ChangeBoundsRequest)initialRequest, request.getHostRequest());
		} else if(initialRequest instanceof CreateViewRequest) {
			return Utils.getAbslotueRequestBounds((CreateViewRequest)initialRequest);
		}
		throw new RuntimeException(UNABLE_TO_GET_THE_INTIAL_TARGET_REQUEST_BOUNDS);
	}

	/**
	 * Convenience method to get a list of workspaces files associated with <code>eObject</code>.
	 * 
	 * @param eObject
	 *        the model object, may be <code>null</code>
	 * @return the list of {@link IFile}s
	 */
	protected static List getWorkspaceFiles(EObject eObject) {
		List result = new ArrayList();
		if(eObject != null) {
			Resource resource = eObject.eResource();
			if(resource != null) {
				IFile file = WorkspaceSynchronizer.getFile(resource);
				if(file != null) {
					result.add(file);
				}
			}
		}
		return result;
	}
	
	 /**
     * Convenience method to get a list of workspaces files associated with
     * {@link EObject}s in <code>eObject</code>.
     * 
     * @param eObjects
     *            the list of model object
     * @return the list of {@link IFile}s
     */
    protected static List getWorkspaceFiles(List eObjects) {
        List result = new ArrayList();

        for (Iterator i = eObjects.iterator(); i.hasNext();) {
            Object next = i.next();

            if (next instanceof EObject) {
                Resource resource = ((EObject) next).eResource();
                
                if (resource != null) {
                    IFile file = WorkspaceSynchronizer.getFile(resource);
    
                    if (file != null) {
                        result.add(file);
                    }
                }
            }
        }

        return result;
    }
}
