/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
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

import org.apache.log4j.Logger;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.groups.Group;
import org.sbml.jsbml.ext.groups.GroupModel;
import org.sbml.jsbml.ext.groups.Member;

/**
 * This class is used to parse the groups extension package elements and
 * attributes. The namespaceURI URI of this parser is
 * "http://www.sbml.org/sbml/level3/version1/groups/version1". This parser is
 * able to read and write elements of the groups package (implements
 * ReadingParser and WritingParser).
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 * @version $Rev$
 */
public class GroupsParser extends AbstractReaderWriter {

	/**
	 * The namespace URI of this parser.
	 */
	public static final String namespaceURI = "http://www.sbml.org/sbml/level3/version1/groups/version1";

	public static final String shortLabel = "groups";

	private Logger logger = Logger.getLogger(GroupsParser.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object sbase)
	 * 
	 */
	public ArrayList<Object> getListOfSBMLElementsToWrite(Object sbase) {

		logger.debug("getListOfSBMLElementsToWrite : " + sbase.getClass().getCanonicalName());
		
		ArrayList<Object> listOfElementsToWrite = new ArrayList<Object>();
		
		if (sbase instanceof Model) {
			SBasePlugin modelPlugin = (SBasePlugin) ((Model) sbase).getExtension(getNamespaceURI());
			
			if (modelPlugin != null) {
				listOfElementsToWrite = super.getListOfSBMLElementsToWrite(modelPlugin);
			}
		} else {
			listOfElementsToWrite = super.getListOfSBMLElementsToWrite(sbase);
		}

		return listOfElementsToWrite;
	}


	/**
	 * 
	 * @see org.sbml.jsbml.xml.parsers.ReadingParser#processStartElement(String
	 *      elementName, String prefix, boolean hasAttributes, boolean
	 *      hasNamespaces, Object contextObject)
	 */
	// Create the proper object and link it to his parent.
	@SuppressWarnings("unchecked")
	public Object processStartElement(String elementName, String prefix,
			boolean hasAttributes, boolean hasNamespaces, Object contextObject) 
	{
		if (contextObject instanceof Model) {
			Model model = (Model) contextObject;
			
			if (elementName.equals("listOfGroups")) {

				GroupModel groupModel = new GroupModel(model);
				model.addExtension(namespaceURI, groupModel);

				return groupModel.getListOfGroups();
			}
		} else if (contextObject instanceof Group) {
			Group group = (Group) contextObject;
			
			if (elementName.equals("listOfMembers")) {

				return group.getListOfMembers();
			}
		}

		else if (contextObject instanceof ListOf<?>) {
			ListOf<SBase> listOf = (ListOf<SBase>) contextObject;

			if (elementName.equals("group")) {
				Model model = (Model) listOf.getParentSBMLObject();
				GroupModel extendeModel = (GroupModel) model.getExtension(namespaceURI); 
				
				Group group = new Group();
				extendeModel.addGroup(group);

				return group;
			} else if (elementName.equals("member")) {
				Member member = new Member();
				listOf.add(member);

				return member;
			}

		}
		return contextObject;
	}


	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getShortLabel()
	 */
	@Override
	public String getShortLabel() {
		return shortLabel;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getNamespaceURI()
	 */
	@Override
	public String getNamespaceURI() {
		return namespaceURI;
	}

	
}
