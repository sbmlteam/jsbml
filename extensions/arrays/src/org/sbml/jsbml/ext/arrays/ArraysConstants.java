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
 * 6. The University of Utah, Salt Lake City, UT, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.arrays;

import java.util.ArrayList;
import java.util.List;


/**
 * This is where the constants that are frequently used in the arrays package are defined.
 * 
 * @author Leandro Watanabe
 * @since 1.0
 */
public class ArraysConstants {

  /**
   * The namespace URI of this parser for SBML level 3, version 1 and package version 1.
   */
  public static final String namespaceURI_L3V1V1 = "http://www.sbml.org/sbml/level3/version1/arrays/version1";

  /**
   * The namespace URI of this parser, this value can change between releases.
   */
  public static final String namespaceURI = namespaceURI_L3V1V1;
  /**
   * 
   */
  public static final String shortLabel = "arrays";
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
  public static final String listOfDimensions = "listOfDimensions";
  /**
   * 
   */
  public static final String listOfIndices = "listOfIndices";
  /**
   * 
   */
  public static final String arrayDimension = "arrayDimension";
  /**
   * 
   */
  public static final String size = "size";
  /**
   * 
   */
  public static final String referencedAttribute = "referencedAttribute";
  /**
   * 
   */
  public static final String math = "math";
  /**
   * 
   */
  public static final String dimension = "dimension";
  /**
   * 
   */
  public static final String index = "index";


  /**
   * 
   */
  public static String packageName = "arrays";

  /**
   * Returns the namespace URI associated with the arrays package.
   * 
   * @param level the SBML level
   * @param version the SBML version
   * @return the namespace URI associated with the arrays package.
   */
  public static String getNamespaceURI(int level, int version) {
    return namespaceURI;
  }
}
