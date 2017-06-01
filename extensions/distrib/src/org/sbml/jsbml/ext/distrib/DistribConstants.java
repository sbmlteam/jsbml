/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2017 jointly by the following organizations:
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
package org.sbml.jsbml.ext.distrib;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class DistribConstants {

  /**
   * 
   */
  public static final String shortLabel = "distrib";

  /**
   * 
   */
  public static final String namespaceURI_L3V1V1 = "http://www.sbml.org/sbml/level3/version1/distrib/version1";

  /**
   * 
   */
  public static final String UNCERT_ML_URI_L3 = "http://www.uncertml.org/3.0";

  /**
   * 
   */
  public static final String namespaceURI = namespaceURI_L3V1V1;

  /**
   * 
   */
  public static final String drawFromDistribution = "drawFromDistribution";

  /**
   * 
   */
  public static final String uncertainty = "uncertainty";

  /**
   * 
   */
  public static final String distribInput = "distribInput";

  /**
   * 
   */
  public static final String listOfDistribInputs = "listOfDistribInputs";

  /**
   * 
   */
  public static final String uncertML = "UncertML";

  /**
   * 
   */
  public static final String index = "index";

  /**
   * 
   */
  public static final List<String> namespaces;

  static {
    namespaces = new ArrayList<String>();
    namespaces.add(namespaceURI_L3V1V1);
  }

}
