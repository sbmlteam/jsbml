/*
 * $Id$
 * $URL$
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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.spatial.SpatialConstant;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class SpatialParser extends AbstractReaderWriter {

	private Logger logger = Logger.getLogger(SpatialParser.class);
	
	/**
	 * The name space of required elements.
	 */
	public static final String namespaceURIrequired = "http://www.sbml.org/sbml/level3/version1/req/version1";
	


	@Override
	public String getShortLabel() {
		return "spatial";
	}

	@Override
	public String getNamespaceURI() {
		return SpatialConstant.namespaceURI;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#getListOfSBMLElementsToWrite(java.lang.Object)
	 */
	public List<Object> getListOfSBMLElementsToWrite(Object treeNode)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("getListOfSBMLElementsToWrite : " + treeNode.getClass().getCanonicalName());
		}	
		
		List<Object> listOfElementsToWrite = new ArrayList<Object>();
		
		// test if this treeNode is an extended SBase.
		if (treeNode instanceof SBase && (! (treeNode instanceof Model)) && ((SBase) treeNode).getExtension(getNamespaceURI()) != null) {
			SBasePlugin sbasePlugin = (SBasePlugin) ((SBase) treeNode).getExtension(getNamespaceURI());
			
			if (sbasePlugin != null) {
				listOfElementsToWrite = super.getListOfSBMLElementsToWrite(sbasePlugin);
				logger.debug("getListOfSBMLElementsToWrite : nb children = " + sbasePlugin.getChildCount());
			}
		} else {
			listOfElementsToWrite = super.getListOfSBMLElementsToWrite(treeNode);
		}

		if (treeNode instanceof Model) {
			String sbmlNamespace = JSBML.getNamespaceFrom(((Model) treeNode).getLevel(), ((Model) treeNode).getVersion());
			
			((Model) treeNode).addNamespace(sbmlNamespace);
			
			for (Object child : listOfElementsToWrite) {
				if (child instanceof SBase && ((SBase) child).getNamespaces().size() == 0) {
					SBase sbase = (SBase) child;
					logger.debug("Found one suspect Model child : " + sbase.getElementName() + ". Setting the SBML namespace to it.");
					sbase.addNamespace(sbmlNamespace);
				}
			}
		}
		
		return listOfElementsToWrite;
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#writeElement(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
	 */
	public void writeElement(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) 
	{
		super.writeElement(xmlObject, sbmlElementToWrite);
		
		if (logger.isDebugEnabled()) {
			logger.debug("writeElement : " + sbmlElementToWrite.getClass().getSimpleName());
		}
	}


	@Override
	public Object processStartElement(String elementName,
			String prefix, boolean hasAttributes, boolean hasNamespaces,
			Object contextObject) 
	{
		// TODO Auto-generated method stub
		return null;
	}


}
