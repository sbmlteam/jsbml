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
package org.sbml.jsbml.ext.groups;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains some constants related to the groups package.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class GroupsConstants {

  /**
   * The namespace URI of this parser for SBML level 3, version 1 and package version 1.
   */
  public static final String namespaceURI_L3V1V1 = "http://www.sbml.org/sbml/level3/version1/groups/version1";

  /**
   * The namespace URI of this parser, this value can change between releases.
   */
  public static final String namespaceURI = namespaceURI_L3V1V1;
  /**
   * 
   */
  public static final String shortLabel = "groups";
  /**
   * 
   */
  public static final int MIN_SBML_LEVEL = 3;
  /**
   * 
   */
  public static final int MIN_SBML_VERSION = 1;

  /**
   * 
   */
  public static final List<String> namespaces;

  static {
    namespaces = new ArrayList<String>();
    namespaces.add(namespaceURI_L3V1V1);
  }

  /**
   * 
   */
  public static final String idRef = "idRef";
  /**
   * 
   */
  public static final String metaIdRef = "metaIdRef";
  /**
   * 
   */
  public static final String identicalAttribute = "identicalAttribute";
  /**
   * 
   */
  public static final String distinctAttribute = "distinctAttribute";
  /**
   * 
   */
  public static final String membersShareType = "membersShareType";
  /**
   * 
   */
  public static final String memberConstraint = "memberConstraint";
  /**
   * 
   */
  public static final String listOfMembers = "listOfMembers";
  /**
   * 
   */
  public static final String listOfGroups = "listOfGroups";
  /**
   * 
   */
  public static final String listOfMemberConstraints = "listOfMemberConstraints";
  /**
   * 
   */
  public static String packageName = "Groups";
  
  
  /**
   * Returns a namespace URI corresponding to the given SBML level and version.
   * 
   * @param level the SBML level
   * @param version the SBML version
   * @return a namespace URI corresponding to the given SBML level and version. 
   */
  public static String getNamespaceURI(int level, int version) {
    return namespaceURI;
  }

}
