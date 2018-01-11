/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
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
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.ASTNodePlugin;
import org.sbml.jsbml.ext.SBasePlugin;
import org.sbml.jsbml.ext.groups.Group;
import org.sbml.jsbml.ext.groups.GroupsConstants;
import org.sbml.jsbml.ext.groups.GroupsModelPlugin;
import org.sbml.jsbml.ext.groups.Member;
import org.sbml.jsbml.ext.groups.MemberConstraint;

/**
 * This class is used to parse the groups extension package elements and
 * attributes. The namespaceURI URI of this parser is
 * "http://www.sbml.org/sbml/level3/version1/groups/version1". This parser is
 * able to read and write elements of the groups package (implements
 * ReadingParser and WritingParser).
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
@SuppressWarnings("deprecation")
@ProviderFor(ReadingParser.class)
public class GroupsParser extends AbstractReaderWriter implements PackageParser {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(GroupsParser.class);

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.WritingParser#getListOfSBMLElementsToWrite(Object sbase)
   */
  @Override
  public List<Object> getListOfSBMLElementsToWrite(Object sbase) {

    logger.debug("getListOfSBMLElementsToWrite: " + sbase.getClass().getCanonicalName());

    List<Object> listOfElementsToWrite = new ArrayList<Object>();

    if (sbase instanceof Model) {
      SBasePlugin modelPlugin = ((Model) sbase).getExtension(getNamespaceURI());

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
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#processStartElement(String, String, String, boolean, boolean, Object)
   */
  @Override
  // Create the proper object and link it to his parent.
  @SuppressWarnings("unchecked")
  public Object processStartElement(String elementName, String uri, String prefix,
    boolean hasAttributes, boolean hasNamespaces, Object contextObject)
  {
    if (contextObject instanceof Model) {
      Model model = (Model) contextObject;

      if (elementName.equals("listOfGroups")) {

        GroupsModelPlugin groupModelPlugin = new GroupsModelPlugin(model);
        model.addExtension(GroupsConstants.namespaceURI, groupModelPlugin);

        return groupModelPlugin.getListOfGroups();
      }
    } else if (contextObject instanceof Group) {
      Group group = (Group) contextObject;

      if (elementName.equals("listOfMembers")) {

        return group.getListOfMembers();
      }
      else if (elementName.equals("listOfMemberConstraints")) {

     // removed in version 0.7 of the specs but could be added again in the future  
        // return group.getListOfMemberConstraints();
        logger.warn("Your model make use of listOfMemberConstraints which was removed in version 0.7 of the groups specification !");
      }

    }

    else if (contextObject instanceof ListOf<?>) {
      ListOf<SBase> listOf = (ListOf<SBase>) contextObject;

      if (elementName.equals("group")) {
        Model model = (Model) listOf.getParentSBMLObject();
        GroupsModelPlugin extendeModel = (GroupsModelPlugin) model.getExtension(GroupsConstants.namespaceURI);

        Group group = new Group();
        extendeModel.addGroup(group);

        return group;
      } else if (elementName.equals("member")) {
        Member member = new Member();
        listOf.add(member);

        return member;
      } else if (elementName.equals(GroupsConstants.memberConstraint)) {
        MemberConstraint member = new MemberConstraint();
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
    return GroupsConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.AbstractReaderWriter#getNamespaceURI()
   */
  @Override
  public String getNamespaceURI() {
    return GroupsConstants.namespaceURI;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#getNamespaceFor(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public String getNamespaceFor(int level, int version,	int packageVersion) {

    if (level == 3 && version == 1 && packageVersion == 1) {
      return GroupsConstants.namespaceURI_L3V1V1;
    }

    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.ReadingParser#getNamespaces()
   */
  @Override
  public List<String> getNamespaces() {
    return GroupsConstants.namespaces;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#getPackageNamespaces()
   */
  @Override
  public List<String> getPackageNamespaces() {
    return getNamespaces();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#getPackageName()
   */
  @Override
  public String getPackageName() {
    return GroupsConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.xml.parsers.PackageParser#isRequired()
   */
  @Override
  public boolean isRequired() {
    return false;
  }


  @Override
  public SBasePlugin createPluginFor(SBase sbase) {

    if (sbase != null) {
      if (sbase instanceof Model) {
        return new GroupsModelPlugin((Model) sbase);
      }
    }

    return null;
  }

  @Override
  public ASTNodePlugin createPluginFor(ASTNode astNode) {
    // This package does not extends ASTNode
    return null;
  }

}
