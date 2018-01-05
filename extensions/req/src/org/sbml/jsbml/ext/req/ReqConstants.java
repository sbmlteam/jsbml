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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.ext.req;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains some constants related to the req package.
 * <p>
 * The SBML Level 3 Required Elements package is a small package that allows
 * models to declare exactly which components of a model have the meaning of
 * their mathematical semantics changed by another package. It does this simply
 * by defining optional children of SBase which can be attached to any component
 * of a model with associated mathematics.
 *
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class ReqConstants {

  /**
   * The namespace URI of this parser for SBML level 3, version 1 and package version 1.
   */
  public static final String namespaceURI_L3V1V1 = "http://www.sbml.org/sbml/level3/version1/req/version1";

  /**
   * The latest namespace URI of this parser, this value can change between releases.
   */
  public static final String namespaceURI = namespaceURI_L3V1V1;


  /**
   * 
   */
  public static final String shortLabel = "req";

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
  public static final String listOfChangedMaths = "listOfChangedMaths";
  /**
   * 
   */
  public static final String changedMath = "changedMath";

  /**
   * 
   */
  public static final String changedBy = "changedBy";
  /**
   * 
   */
  public static final String viableWithoutChange = "viableWithoutChange";

  /**
   * Gets the namespace for this package that correspond to the given SBML level, version
   * and the package version.
   * 
   * <p>Returns null if the combined level, version and packageVersion is
   * invalid or not known from the package parser implementation.
   * 
   * @param level - the SBML level
   * @param version - the SBML version
   * @param packageVersion - the package version
   * @return the namespace for this package that correspond to the given SBML level and version
   * and the package version or null if nothing valid is found.
   */
  public static String getNamespaceURI(int level, int version, int packageVersion) {

    if (level == 3 && version == 1 && packageVersion == 1) {
      return ReqConstants.namespaceURI_L3V1V1;
    }

    return null;
  }

}
