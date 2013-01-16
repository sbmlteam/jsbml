/*
 * $Id: CompParser.java 1445 2013-01-04 08:54:54Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/extensions/comp/src/org/sbml/jsbml/xml/parsers/CompParser.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.xml.parsers;

import static org.sbml.jsbml.ext.comp.CompConstant.namespaceURI;
import static org.sbml.jsbml.ext.comp.CompConstant.shortLabel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.SBasePlugin;

import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * A CompParser is used to parse the comp extension package elements and
 * attributes. The namespaceURI URI of this parser is
 * "http://www.sbml.org/sbml/level3/version1/comp/version1". This parser is
 * able to read and write elements of the comp package (implements
 * ReadingParser and WritingParser).
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 * @version $Rev: 1445 $
 */
public class CompParser extends AbstractReaderWriter {

	private Logger logger = Logger.getLogger(CompParser.class);
	
	/**
	 * 
	 * @return the namespaceURI of this parser.
	 */
	public String getNamespaceURI() {
		return namespaceURI;
	}

	@Override
	public String getShortLabel() {
		return shortLabel;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object sbase)
	 */
	@Override
	public List<Object> getListOfSBMLElementsToWrite(Object treeNode) {

		if (logger.isDebugEnabled()) {
			logger.debug("getListOfSBMLElementsToWrite : " + treeNode.getClass().getCanonicalName());
		}	
		
		List<Object> listOfElementsToWrite = new ArrayList<Object>();
		
		// test if this treeNode is an extended SBase.
		if (treeNode instanceof SBase && ((SBase) treeNode).getExtension(getNamespaceURI()) != null) {
			SBasePlugin sbasePlugin = (SBasePlugin) ((Model) treeNode).getExtension(getNamespaceURI());
			
			if (sbasePlugin != null) {
				listOfElementsToWrite = super.getListOfSBMLElementsToWrite(sbasePlugin);
				logger.debug("getListOfSBMLElementsToWrite : nb children = " + sbasePlugin.getChildCount());
			}
		} else {
			listOfElementsToWrite = super.getListOfSBMLElementsToWrite(treeNode);
		}

		return listOfElementsToWrite;
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.ReadingParser#processStartElement(String
	 * elementName, String prefix, boolean hasAttributes, boolean hasNamespaces,
	 * Object contextObject)
	 */
	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces, Object contextObject) {

		// TODO : make it generic by using reflection on the contextObject
		
		
		if (contextObject instanceof Model) 
		{
			Model model = (Model) contextObject;
			MultiModel multiModel = null;
			
			if (model.getExtension(namespaceURI) != null) {
				multiModel = (MultiModel) model.getExtension(namespaceURI);
			} else {
				multiModel = new MultiModel(model);
				model.addExtension(namespaceURI, multiModel);
			}

			if (elementName.equals(listOfSpeciesTypes)) 
			{
				return multiModel.getListOfSpeciesTypes();
			} 
			else if (elementName.equals(listOfSelectors)) 
			{
				return multiModel.getListOfSelectors();
			}
		} // end Model
		/*
		else if (contextObject instanceof SpeciesType)
		{
			SpeciesType speciesType = (SpeciesType) contextObject;
			
			if (elementName.equals(listOfStateFeatures)) {
				return speciesType.getListOfStateFeatures();
			}			
		} // end SpeciesType
		else if (contextObject instanceof StateFeature)
		{
			StateFeature stateFeature = (StateFeature) contextObject;
			
			if (elementName.equals(listOfPossibleValues)) {
				return stateFeature.getListOfPossibleValues();
			}			
		} // end StateFeature
		else if (contextObject instanceof Selector)
		{
			Selector selector = (Selector) contextObject;
			
			if (elementName.equals(listOfSpeciesTypeStates)) 
			{
				return selector.getListOfSpeciesTypeStates();
			}
			else if (elementName.equals(listOfBonds)) 
			{
				return selector.getListOfBonds();
			} 
			else if (elementName.equals(listOfUnboundBindingSites)) 
			{
				return selector.getListOfUnboundBindingSites();
			} 
		} // end Selector
		else if (contextObject instanceof SpeciesTypeState)
		{
			SpeciesTypeState speciesTypeState = (SpeciesTypeState) contextObject;
			
			if (elementName.equals(listOfStateFeatureInstances)) 
			{
				return speciesTypeState.getListOfStateFeatureInstances();
			}			
			else if (elementName.equals(listOfContainedSpeciesTypes)) 
			{
				return speciesTypeState.getListOfContainedSpeciesTypes();
			} 
		} // end SpeciesTypeState
		else if (contextObject instanceof StateFeatureInstance)
		{
			StateFeatureInstance stateFeatureInstance = (StateFeatureInstance) contextObject;
			
			if (elementName.equals(listOfStateFeatureValues)) {
				return stateFeatureInstance.getListOfStateFeatureValues();
			}			
		} // end StateFeatureInstance
		else if (contextObject instanceof Bond)
		{
			Bond bond = (Bond) contextObject;
			
			if (elementName.equals(bindingSiteReference)) 
			{
				BindingSiteReference bindingSiteReference = new BindingSiteReference();
				bond.addBindingSiteReference(bindingSiteReference);
				return bindingSiteReference;
			} 
		} // end Bond
		*/
		else if (contextObject instanceof ListOf<?>) 
		{
			ListOf<?> listOf = (ListOf<?>) contextObject;
			
			Object newElement = createListOfChild(listOf, elementName);
			
			return newElement;
			
			// TODO : SpeciesTypeInstance, SelectorReference, ....
		}
		
		
		return contextObject;
	}

	/**
	 * 
	 * @param listOf
	 * @param elementName
	 * @return
	 */
	private Object createListOfChild(ListOf<?> listOf, String elementName) {

		Object parentSBase = listOf.getParent();
		
		if (parentSBase == null) {
			return null;
		} else if (parentSBase instanceof Model) {
			parentSBase = ((Model) parentSBase).getExtension(namespaceURI);
		}

		String createMethodName = "create" + elementName.substring(0, 1).toUpperCase() + elementName.substring(1);  
		Method createMethod = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Method '" + createMethodName + "' will be used");
		}
		
		try {
			createMethod = parentSBase.getClass().getMethod(createMethodName, (Class<?>[]) null);
			
			return createMethod.invoke(parentSBase, (Object[]) null);
			
		} catch (SecurityException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("Method '" + createMethodName + "' is not accessible on " + parentSBase.getClass().getSimpleName());
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("Method '" + createMethodName + "' does not exist on " + parentSBase.getClass().getSimpleName());
			}
		} catch (IllegalArgumentException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("Problem invoking the method '" + createMethodName + "' on " + parentSBase.getClass().getSimpleName());
				logger.debug(e.getMessage());
			}
		} catch (IllegalAccessException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("Problem invoking the method '" + createMethodName + "' on " + parentSBase.getClass().getSimpleName());
				logger.debug(e.getMessage());
			}
		} catch (InvocationTargetException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("Problem invoking the method '" + createMethodName + "' on " + parentSBase.getClass().getSimpleName());
				logger.debug(e.getMessage());
			}
		}
		
		// TODO : try to use the default constructor + the addXX method
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#writeElement(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
	 */
	@Override
	public void writeElement(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) 
	{
		super.writeElement(xmlObject, sbmlElementToWrite);
		
		if (logger.isDebugEnabled()) {
			logger.debug("writeElement : " + sbmlElementToWrite.getClass().getSimpleName());
		}
	}


}
