/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
package org.sbml.jsbml.ext.fbc;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains constants related to the FBC package.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 * @version $Rev$
 */
public class FBCConstants {

  /**
   * 
   */
  public static final String activeObjective = "activeObjective";

  /**
   * 
   */
  public static final String charge = "charge";

  /**
   * 
   */
  public static final String chemicalFormula = "chemicalFormula";

  /**
   * 
   */
  public static final String coefficient = "coefficient";

  /**
   * @since jsbml 1.1, introduced to FBC in version 2.
   */
  public static final String geneProduct = "geneProduct";

  /**
   * 
   * @since jsbml 1.1, introduced in FBC version 2
   */
  public static final String geneProductAssociation = "geneProductAssociation";

  /**
   * 
   * @since jsbml 1.1, introduced in FBC version 2
   */
  public static final String geneProductReference = "geneProductRef";

  /**
   * @since jsbml 1.1, introduced to FBC in version 2.
   */
  public static final String geneProductIdentifier = "geneProductIdentifier"; // not in use any more?

  /**
   * 
   */
  public static final String label = "label";

  /**
   * 
   */
  public static final String listOfObjectives = "listOfObjectives";

  /**
   * @since jsbml 1.1, introduced to FBC in version 2.
   */
  public static final String listOfGeneProducts = "listOfGeneProducts";

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

  /**
   * The namespace URI of this parser for SBML level 3, version 1 and package version 1.
   */
  public static final String namespaceURI_L3V1V1 = "http://www.sbml.org/sbml/level3/version1/fbc/version1";
  /**
   * The namespace URI of this parser for SBML level 3, version 1 and package version 2.
   */
  public static final String namespaceURI_L3V1V2 = "http://www.sbml.org/sbml/level3/version1/fbc/version2";

  /**
   * The latest namespace URI of this parser, this value can change between releases.
   */
  public static final String namespaceURI = namespaceURI_L3V1V2;

  /**
   * 
   */
  public static final String operation = "operation";
  /**
   * 
   */
  public static final String packageName = "Flux Balance Constraints";
  /**
   * 
   */
  public static final String reaction = "reaction";
  /**
   * 
   */
  public static final String shortLabel = "fbc";
  /**
   * 
   */
  public static final String type = "type";
  /**
   * Introduced to FBC in version 2.
   */
  public static final String upperFluxBound = "upperFluxBound";
  /**
   * Introduced to FBC in version 2.
   */
  public static final String lowerFluxBound = "lowerFluxBound";
  /**
   * Introduced to FBC in version 2.
   */
  public static final String geneProteinAssociation = "geneProteinAssociation";
  /**
   * Introduced to FBC in version 2.
   */
  public static final String association = "association";
  /**
   * Introduced to FBC in version 2: The left child of a logical association rule.
   */
  public static final String leftChild = "leftChild";
  /**
   * Introduced to FBC in version 2: The right child of a logical association rule.
   */
  public static final String rightChild = "rightChild";

  /**
   * 
   */
  public static final String value = "value";

  /**
   * Introduced to FBC in version 2.
   */
  public static final String and = "and";
  /**
   * Introduced to FBC in version 2.
   */
  public static final String or = "or";

  /**
   * Introduced to FBC in version 2.
   */
  public static final String associatedSpecies = "associatedSpecies";

  /**
   * 
   */
  public static final String fluxObjective = "fluxObjective";

  /**
   * 
   */
  public static final String objective = "objective";

  /**
   * 
   */
  public static final String fluxBound = "fluxBound";

  /**
   * 
   */
  public static final String listOfFluxObjectives = "listOfFluxObjectives";

  /**
   * 
   */
  public static final String listOfFluxes = "listOfFluxes";

  static {
    namespaces = new ArrayList<String>();
    namespaces.add(namespaceURI_L3V1V1);
    namespaces.add(namespaceURI_L3V1V2);
  }

  /**
   * 
   * @param level
   * @param version
   * @return
   */
  public static String getNamespaceURI(int level, int version) {
    return namespaceURI;
  }

  /**
   * 
   * @param level
   * @param version
   * @param packageVersion
   * @return
   */
  public static String getNamespaceURI(int level, int version, int packageVersion) {
    switch (packageVersion) {
    case 1:
      return namespaceURI_L3V1V1;
    case 2:
      return namespaceURI_L3V1V2;
    default:
      return namespaceURI;
    }
  }

}
