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
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.comp;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 */
public class CompConstants {

  /**
   * The namespace URI of this parser.
   */
  public static final String namespaceURI = "http://www.sbml.org/sbml/level3/version1/comp/version1";

  public static final String shortLabel = "comp";

  public static final int MIN_SBML_LEVEL = 3;
  public static final int MIN_SBML_VERSION = 1;

  public static final String source = "source";
  public static final String modelRef = "modelRef";

  public static final String portRef = "portRef";
  public static final String idRef = "idRef";
  public static final String unitRef = "unitRef";
  public static final String metaIdRef = "metaIdRef";
  public static final String sBaseRef = "sBaseRef";

  public static final String replacedBy = "replacedBy";

  public static final String submodelRef = "submodelRef";
  public static final String deletion = "deletion";
  public static final String conversionFactor = "conversionFactor";

  public static final String timeConversionFactor = "timeConversionFactor";
  public static final String extentConversionFactor = "extentConversionFactor";

  public static final String listOfSubmodels = "listOfSubmodels";

  public static final String listOfPorts = "listOfPorts";

  public static final String listOfExternalModelDefinitions = "listOfExternalModelDefinitions";

  public static final String listOfModelDefinitions = "listOfModelDefinitions";

  public static final String listOfDeletions = "listOfDeletions";

  public static final String listOfReplacedElements = "listOfReplacedElements";

  public static final String replacedElement = "replacedElement";

  public static final String md5 = "md5";

  public static final String packageName = "Hierarchical Model Composition";

  /**
   * @param level
   * @param version
   * @return
   */
  public static String getNamespaceURI(int level, int version) {
    return namespaceURI;
  }


}
