/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
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
import org.mangosdk.spi.ProviderFor;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.spatial.SpatialConstants;
import org.sbml.jsbml.xml.stax.SBMLObjectForXML;

/**
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
@ProviderFor(ReadingParser.class)
public class SpatialParser extends AbstractReaderWriter implements PackageParser {

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
		return SpatialConstants.namespaceURI;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#getListOfSBMLElementsToWrite(java.lang.Object)
	 */
	public List<Object> getListOfSBMLElementsToWrite(Object treeNode)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("getListOfSBMLElementsToWrite: " + treeNode.getClass().getCanonicalName());
		}	
		
		List<Object> listOfElementsToWrite = new ArrayList<Object>();
		
		// test if this treeNode is an extended SBase.
		if (treeNode instanceof SBase && (! (treeNode instanceof Model)) && ((SBase) treeNode).getExtension(getNamespaceURI()) != null) {
			SBasePlugin sbasePlugin = (SBasePlugin) ((SBase) treeNode).getExtension(getNamespaceURI());
			
			if (sbasePlugin != null) {
				listOfElementsToWrite = super.getListOfSBMLElementsToWrite(sbasePlugin);
				logger.debug("getListOfSBMLElementsToWrite: nb children = " + sbasePlugin.getChildCount());
			}
		} else {
			listOfElementsToWrite = super.getListOfSBMLElementsToWrite(treeNode);
		}
		
		return listOfElementsToWrite;
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.WritingParser#writeElement(org.sbml.jsbml.xml.stax.SBMLObjectForXML, java.lang.Object)
	 */
	@Override
	public void writeElement(SBMLObjectForXML xmlObject,
			Object sbmlElementToWrite) 
	{
		super.writeElement(xmlObject, sbmlElementToWrite);
		
		if (logger.isDebugEnabled()) {
			logger.debug("writeElement: " + sbmlElementToWrite.getClass().getSimpleName());
		}
	}


	@Override
	public Object processStartElement(String elementName, String uri, 
			String prefix, boolean hasAttributes, boolean hasNamespaces,
			Object contextObject) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNamespaceFor(String level, String version,	String packageVersion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getNamespaces() {		
		return SpatialConstants.namespaces;
	}

	@Override
	public List<String> getPackageNamespaces() {		
		return getNamespaces();
	}

	@Override
	public String getPackageName() {
		return SpatialConstants.shortLabel;
	}

	@Override
	public boolean isRequired() {
		return true;
	}

	@Override
	public SBasePlugin createPluginFor(SBase sbase) {
		// TODO Auto-generated method stub
		return null;
	}



}
