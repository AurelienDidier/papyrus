/*****************************************************************************
 * Copyright (c) 2007, 2010, 2014 Borland Software Corporation, CEA, and others
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Alexander Shatalin (Borland) - initial API and implementation
 * Michael Golubev (Borland) - [243151] explicit source/target for links
 * Michael Golubev (Montages) - API extracted to gmf.tooling.runtime, template migrated to Xtend2
 * Christian W. Damus (CEA) - bug 426732: override the cross-reference searches for views to use the CrossReferenceAdapter        
 * 
 *****************************************************************************/
package aspects.xpt.diagram.updater

import aspects.xpt.Common
import aspects.xpt.editor.VisualIDRegistry
import com.google.inject.Inject
import com.google.inject.Singleton
import metamodel.MetaModel
import org.eclipse.gmf.codegen.gmfgen.FeatureLinkModelFacet
import org.eclipse.gmf.codegen.gmfgen.GenCommonBase
import org.eclipse.gmf.codegen.gmfgen.GenContainerBase
import org.eclipse.gmf.codegen.gmfgen.GenDiagram
import org.eclipse.gmf.codegen.gmfgen.GenDiagramUpdater
import org.eclipse.gmf.codegen.gmfgen.GenLink
import org.eclipse.gmf.codegen.gmfgen.GenNode
import org.eclipse.gmf.codegen.gmfgen.TypeLinkModelFacet
import org.eclipse.papyrus.papyrusgmfgenextension.CustomDiagramUpdaterSingleton
import org.eclipse.papyrus.papyrusgmfgenextension.SpecificDiagramUpdater
import xpt.Common_qvto
import xpt.GenModelUtils_qvto
import xpt.diagram.updater.UpdaterLinkType
import xpt.diagram.updater.Utils_qvto
import java.util.Set
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature

// we removed all static modifiers and all private methods becames protected to allow to override method. 
//see bug421212: [Diagram] Papyrus should provide actions for Show/Hide related links in all diagrams
@Singleton class DiagramUpdater extends xpt.diagram.updater.DiagramUpdater {
	@Inject extension Common;
	@Inject extension Common_qvto;
	@Inject extension Utils_qvto;
	@Inject extension GenModelUtils_qvto;
	@Inject xpt.diagram.updater.LinkDescriptor linkDescriptor;
	@Inject VisualIDRegistry xptVisualIDRegistry;
	@Inject xpt.diagram.updater.NodeDescriptor nodeDescriptor;

	@Inject MetaModel xptMetaModel;

	def diagramUpdaterInstanceToUse(GenDiagramUpdater it) '''
		«IF it.eResource.allContents.filter(typeof(CustomDiagramUpdaterSingleton)).filter[v|v.singletonPath != null].size ==
			1»
			«it.eResource.allContents.filter(typeof(CustomDiagramUpdaterSingleton)).filter[v|v.singletonPath != null].head.
			singletonPath»
		«ELSE»
			«diagramUpdaterQualifiedClassName».INSTANCE
		«ENDIF»
	'''

	protected def typeOfCrossReferenceAdapter() '''org.eclipse.gmf.runtime.emf.core.util.CrossReferenceAdapter'''

	override getSemanticChildrenMethodCall(GenContainerBase it) '''«diagramUpdaterInstanceToUse(it.diagramUpdater)».«getSemanticChildrenMethodName(
		it)»'''

	override doGetSomeLinksMethodCall(GenCommonBase it, UpdaterLinkType linkType) '''«diagramUpdaterInstanceToUse(
		it.getDiagram().diagramUpdater)».«linkGetterName(linkType)»'''

	//	override runtimeTypedInstanceName(GenDiagramUpdater it) '''INSTANCE'''
	//
	//	override runtimeTypedInstanceCall(GenDiagramUpdater it) '''«qualifiedClassName(it)».«runtimeTypedInstanceName(it)»'''
	//protected constructor to allow overriding	
	def _constructor(GenDiagramUpdater it) '''
		«generatedMemberComment()»
		protected «diagramUpdaterClassName»(){
			//to prevent instantiation allowing the override
		}
	'''

	//create the singleton using custom class defined in GMFGen
	public def classSingleton(GenDiagramUpdater it) '''
		«««we create the singleton only in the case where there is no custom diagram updater
	«IF it.eResource.allContents.filter(typeof(CustomDiagramUpdaterSingleton)).filter[v|v.singletonPath != null].size != 1»
			«generatedMemberComment()»
			public static final  «diagramUpdaterQualifiedClassName» INSTANCE = new «diagramUpdaterClassName»();
		«ENDIF»
	'''

	override DiagramUpdater(GenDiagramUpdater it) '''
			«copyright(editorGen)»
			package «packageName(it)»;
			
			«generatedClassComment»
			public class «className(it)» implements  org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater {
			«classSingleton(it)»
			«_constructor(it)»
			«isShortcutOrphaned(it)»
			«var semanticContainers = it.editorGen.diagram.allContainers.filter[container|hasSemanticChildren(container)]»
			«getGenericSemanticChildrenOfView(it, semanticContainers)»
			«FOR next : semanticContainers»
			«getSemanticChildrenOfView(next)»
			«ENDFOR»
			
			«getPhantomNodesIterator(it)»
			
			«getGenericConnectedLinks(it, getAllSemanticElements(editorGen.diagram), UpdaterLinkType::CONTAINED)»
			
			«getGenericConnectedLinks(it, getAllSemanticDiagramElements(editorGen.diagram), UpdaterLinkType::INCOMING)»
			
			«getGenericConnectedLinks(it, getAllSemanticDiagramElements(editorGen.diagram), UpdaterLinkType::OUTGOING)»
			«FOR e : getAllSemanticElements(editorGen.diagram)»
			«getContainedLinks(e)»
			«ENDFOR»
			«FOR e : getAllSemanticDiagramElements(editorGen.diagram)»
			«getIncomingLinks(e)»
			«ENDFOR»
			«FOR e : getAllSemanticDiagramElements(editorGen.diagram)»
			«getOutgoingLinks(e)»
			«ENDFOR»
			«FOR link : getAllContainedLinks(editorGen.diagram)»
			«getContainedLinksByTypeMethod(link)»
			«ENDFOR»
			«FOR link : getAllIncomingLinks(editorGen.diagram)»
			«getIncomingLinksByTypeMethod(link)»
			«ENDFOR»
			«FOR link : getAllOutgoingLinks(editorGen.diagram)»
			«getOutgoingLinksByTypeMethod(link)»
			«ENDFOR»
			
			«runtimeTypedInstance(it)»
			
			«additions(it)»
		}
	'''

	override def getConnectedLinks(GenCommonBase it, Iterable<GenLink> genLinks, UpdaterLinkType linkType,
		boolean needCrossReferencer) '''
		
		«generatedMemberComment»
		«««remove static modifier
		public «listOfLinkDescriptors(it)» «linkGetterName(it, linkType)»(org.eclipse.gmf.runtime.notation.View view) {
		«IF genLinks.notEmpty»
			«xptMetaModel.DeclareAndAssign(it.metaClass, 'modelElement', 'view.getElement()')»
			«IF needCrossReferencer»
				«typeOfCrossReferenceAdapter» crossReferencer = «typeOfCrossReferenceAdapter».getCrossReferenceAdapter(view.eResource().getResourceSet());
			«ENDIF»
			«newLinkedListOfLinkDescriptors(it.diagramUpdater, 'result')»();
			«FOR link : genLinks»
				«colectConnectedLinks(link, linkType, needCrossReferencer, isExternalInterface(it.metaClass))»
			«ENDFOR»
			return result;
		«ELSE»
			return «newEmptyList()»;
		«ENDIF»
		}
	'''

	override def colectConnectedLinks(GenLink it, UpdaterLinkType linkType, boolean needCrossReferencer,
		boolean isExternalInterface) '''
		«IF it.modelFacet != null»
			«IF isExternalInterface && !it.modelFacet.oclIsKindOf(typeof(FeatureLinkModelFacet))»
				if («xptMetaModel.IsInstance(it.modelFacet.getLinkEndType(linkType), 'modelElement')») {
			«ENDIF»
			result.addAll(«chooseConnectedLinksByTypeMethodName(it.modelFacet, linkType, it)»(« //
		IF isExternalInterface && !it.modelFacet.oclIsKindOf(typeof(FeatureLinkModelFacet))»«xptMetaModel.
			CastEObject(it.modelFacet.getLinkEndType(linkType), 'modelElement')»«ELSE»modelElement«ENDIF»«IF needCrossReferencer», crossReferencer«ENDIF»));  
			«IF isExternalInterface && !it.modelFacet.oclIsKindOf(typeof(FeatureLinkModelFacet))»
				}
			«ENDIF»
		«ENDIF»
	'''

	override def getIncomingLinksByTypeMethod(GenLink it) '''
		   «generatedMemberComment»
		   «««remove static modifier + private->protected
		protected java.util.Collection<«linkDescriptor.qualifiedClassName(it.diagramUpdater)»> «getConnectedLinksByTypeMethodName(
			UpdaterLinkType::INCOMING)»(«xptMetaModel.QualifiedClassName(it.modelFacet.targetType)» target, «typeOfCrossReferenceAdapter» crossReferencer) {
		 «newLinkedListOfLinkDescriptors(it.diagramUpdater, 'result')»();
		 java.util.Collection<org.eclipse.emf.ecore.EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		 for (org.eclipse.emf.ecore.EStructuralFeature.Setting setting : settings) {
		    «getIncomingLinksByTypeMethodBody(it.modelFacet, it)»
		 }
		 return result;  
		}
	'''

	def CharSequence getICustomDiagramUpdater(GenContainerBase it) '''org.eclipse.papyrus.uml.diagram.common.part.ICustomDiagramUpdater<«nodeDescriptor.
		qualifiedClassName(it.diagramUpdater)»>'''

	override getSemanticChildrenOfView(GenContainerBase it) '''
		«««remove static modifier
		«IF it.eResource.allContents.filter(typeof(SpecificDiagramUpdater)).filter[v|v.genNode == it && v.classpath != null].
			size != 0»
			«generatedMemberComment»
			«FOR updater : it.eResource.allContents.filter(typeof(SpecificDiagramUpdater)).filter[v|
			v.genNode == it && v.classpath != null].toIterable»
				public  «listOfNodeDescriptors» «getSemanticChildrenMethodName(it)»(org.eclipse.gmf.runtime.notation.View view) {
					«getICustomDiagramUpdater(it)» customUpdater = new «updater.classpath»();
					return customUpdater.getSemanticChildren(view);
				}
			«ENDFOR»	
			«ELSE»
			«generatedMemberComment»
			public  «listOfNodeDescriptors» «getSemanticChildrenMethodName(it)»(org.eclipse.gmf.runtime.notation.View view) {
				«IF getSemanticChildrenChildFeatures(it).notEmpty || it.getPhantomNodes().notEmpty»
					«defineModelElement(it)»
					«newLinkedListOfNodeDescriptors(it.diagramUpdater, 'result')»();
					«/* childMetaFeature can be null here! */FOR childMetaFeature : getSemanticChildrenChildFeatures(it)»
						«IF null == childMetaFeature»
							{ 	/*FIXME no containment/child feature found in the genmodel, toolsmith need to specify Class here manually*/ childElement = 
								/*FIXME no containment/child feature found in the genmodel, toolsmith need to specify correct one here manually*/;
						«ELSEIF childMetaFeature.listType»
							for (java.util.Iterator<?> it = «xptMetaModel.getFeatureValue(childMetaFeature, 'modelElement', it.getModelElementType())».iterator(); it.hasNext();) {
							«xptMetaModel.DeclareAndAssign(childMetaFeature.typeGenClass, 'childElement', 'it.next()', true)»
						«ELSE»
							{ «xptMetaModel.DeclareAndAssign(childMetaFeature.typeGenClass, 'childElement', 'modelElement',
			it.getModelElementType(), childMetaFeature)»
						«ENDIF»
						String visualID = «xptVisualIDRegistry.getNodeVisualIDMethodCall(it.diagram)»(view, «xptMetaModel.
			DowncastToEObject(childMetaFeature.typeGenClass, 'childElement')»);
						«FOR next : getSemanticChildren(it, childMetaFeature)»
							«checkChildElementVisualID(next, null != childMetaFeature && childMetaFeature.listType)»
						«ENDFOR»
						}
					«ENDFOR»
					«IF it.getPhantomNodes.notEmpty»
						org.eclipse.emf.ecore.resource.Resource resource = modelElement.eResource();
						for (java.util.Iterator<org.eclipse.emf.ecore.EObject> it = getPhantomNodesIterator(resource); it.hasNext();) {
							org.eclipse.emf.ecore.EObject childElement = it.next();
							if (childElement == modelElement) {
								continue;
							}
							«FOR phantom : it.phantomNodes»
								«addNextIfPhantom(phantom)»
							«ENDFOR»
						}
					«ENDIF»		
					return result;
				«ELSE»
					return «newEmptyList()»;
				«ENDIF»
			}
			«ENDIF»	
	'''
	
	
	override defineLinkSource(TypeLinkModelFacet it, boolean inLoop) '''
		«IF sourceMetaFeature.listType»
			java.util.List<?> sources = «xptMetaModel.getFeatureValue(sourceMetaFeature, 'link', metaClass)»;
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if («xptMetaModel.NotInstance(it.sourceType, 'theSource')») {
				«stopLinkProcessing(inLoop)»
			}
			«xptMetaModel.DeclareAndAssign(it.sourceType, 'src', 'theSource', true)»
		«ELSE»
			«xptMetaModel.DeclareAndAssign(it.sourceType, 'src', 'link', metaClass, sourceMetaFeature)»
		«ENDIF»
	'''

	def isDiagram(GenDiagram it) ''''''

	override defineLinkDestination(TypeLinkModelFacet it, Boolean inLoop) '''
		«IF targetMetaFeature.listType»
			java.util.List<?> targets = «xptMetaModel.getFeatureValue(it.targetMetaFeature, 'link', metaClass)»;
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if («xptMetaModel.NotInstance(it.targetType, 'theTarget')») {
				«stopLinkProcessing(inLoop)»
			}
			«xptMetaModel.DeclareAndAssign(it.targetType, 'dst', 'theTarget', true)»
		«ELSE»
			«xptMetaModel.DeclareAndAssign(it.targetType, 'dst', 'link', metaClass, targetMetaFeature)»
		«ENDIF»
	'''

	//remove static modifier + private->protected
	override getOutgoingLinksByTypeMethodSignature(GenLink it) '''protected java.util.Collection<«linkDescriptor.
		qualifiedClassName(it.diagramUpdater)»> «getConnectedLinksByTypeMethodName(UpdaterLinkType::OUTGOING)»(«xptMetaModel.
		QualifiedClassName(it.modelFacet.sourceType)» source)'''

	override getGenericSemanticChildrenOfView(GenDiagramUpdater it, Iterable<GenContainerBase> semanticContainers) '''
		
			«generatedMemberComment»
			««« remove static modifier
	public «listOfNodeDescriptors» getSemanticChildren(org.eclipse.gmf.runtime.notation.View view) {
			«IF semanticContainers.notEmpty»
				String vid = «xptVisualIDRegistry.getVisualIDMethodCall(editorGen.diagram)»(view);
				if (vid != null) {
					switch (vid) {
						«FOR next : semanticContainers»
							«getSemanticChildrenCase(next)»
						«ENDFOR»
					}
				}
			«ENDIF»
			return «newEmptyList()»;
		}
	'''

	override dispatch getContainedLinksByTypeMethod(TypeLinkModelFacet it, GenLink genLink) '''
		
		«generatedMemberComment»
		««« remove static modifier + private->protected
	protected java.util.Collection<«linkDescriptor.qualifiedClassName(genLink.diagramUpdater)»> «getConnectedLinksByTypeMethodName(
			genLink, UpdaterLinkType::CONTAINED)»(«xptMetaModel.QualifiedClassName(childMetaFeature.genClass)» container) {
			«getContainedLinksByTypeMethodBody(it, genLink, false)»
		}
	'''

	override getGenericConnectedLinks(GenDiagramUpdater it, Iterable<? extends GenCommonBase> linkContainers,
		UpdaterLinkType linkType) '''
		
		«generatedMemberComment»
		««« remove static modifier
	public «listOfLinkDescriptors» get«linkType.linkMethodSuffix»Links(org.eclipse.gmf.runtime.notation.View view) {
			«IF linkContainers.notEmpty»
				String vid = «xptVisualIDRegistry.getVisualIDMethodCall(it.editorGen.diagram)»(view);
				if (vid != null) {
					switch (vid) {
						«FOR next : linkContainers»
							«getContainedLinksCase(next, linkType)»
						«ENDFOR»
					}
				}
			«ENDIF»
			return «newEmptyList»;
		}
	'''

	override runtimeTypedInstance(GenDiagramUpdater it) '''
		'''

	/**
	 * XXX: [MG] suspicious code inside, EVEN after I moved ", " into the IF, there still may be problem if inner IF condition is not met.
	 * Need to check with case when it.modelFacet.childMetaFeature == null    
	 */
	override def checkChildElementVisualID(GenNode it, Boolean inLoop) '''
	if («VisualIDRegistry::visualID(it)».equals(visualID)) {
		result.add(new «nodeDescriptor.qualifiedClassName(it.getDiagram().diagramUpdater)»(«IF null != modelFacet.childMetaFeature»«xptMetaModel.DowncastToEObject(modelFacet.childMetaFeature.typeGenClass, 'childElement')», «ENDIF»visualID));
	«IF inLoop»
		continue;
	«ENDIF»
	}
	'''

	override def checkLinkVisualID(TypeLinkModelFacet it, GenLink genLink, boolean inLoop) '''
	if (!«VisualIDRegistry::visualID(genLink)».equals(«xptVisualIDRegistry.getLinkWithClassVisualIDMethodCall(genLink.diagram)»(«xptMetaModel.DowncastToEObject(metaClass, 'link')»))) {
		«stopLinkProcessing(inLoop)»
	}
	'''

	override def getSemanticChildrenMethodName(GenContainerBase it) '''get«stringUniqueIdentifier()»_SemanticChildren'''
	
	override protected def linkGetterName(GenCommonBase it, UpdaterLinkType linkType) '''get«stringUniqueIdentifier()»_«linkType.linkMethodSuffix»Links'''
	
	override def getConnectedLinksByTypeMethodName(GenLink it, UpdaterLinkType linkType) '''get«linkType.linkMethodSuffix»«getConnectedLinksByTypeMethodFragment(modelFacet)»_«stringVisualID»'''
	
	override def dispatch getConnectedLinksByTypeMethodFragment(TypeLinkModelFacet it) '''TypeModelFacetLinks'''
	
	override def dispatch getConnectedLinksByTypeMethodFragment(FeatureLinkModelFacet it) '''FeatureModelFacetLinks'''
}