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
package org.sbml.jsbml.ext.distrib;

import java.util.ArrayList;
import java.util.List;

/**
 * 
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
  // public static final String namespaceURI_L3V2V1 = "http://www.sbml.org/sbml/level3/version2/distrib/version1"; // TODO - removed definitively once Lucian and Sarah agree on something

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
  public static final String uncertainty = "uncertainty";

  /**
   * 
   */
  public static final String uncertParameter = "uncertParameter";

  /**
   * 
   */
  public static final String uncertSpan = "uncertspan";

  /**
   * 
   */
  public static final String value = "value";

  /**
   * 
   */
  public static final String var = "var";

  /**
   * 
   */
  public static final String inclusive = "inclusive";

  /**
   * 
   */
  public static final String varLower = "varLower";

  /**
   * 
   */
  public static final String varUpper = "varUpper";
  
  /**
   * 
   */
  public static final String valueLower = "valueLower";

  /**
   * 
   */
  public static final String valueUpper = "valueUpper";

  /**
   * 
   */
  public static final String singleValueStatistic = "singleValueStatistic";
  
  /**
   * 
   */
  public static final String spanStatistic = "spanStatistic";

  /**
   * 
   */
  public static final String truncationLowerBound = "truncationLowerBound";

  /**
   * 
   */
  public static final String truncationUpperBound = "truncationUpperBound";
  
  /**
   * 
   */
  public static final String mean = "mean";
  
  /**
   * 
   */
  public static final String stddev = "stddev";
  
  /**
   * 
   */
  public static final String variance = "variance";
  
  /**
   * 
   */
  public static final String location = "location";
  
  /**
   * 
   */
  public static final String scale = "scale";

  /**
   * 
   */
  public static final String logScale = "logScale";

  /**
   * 
   */
  public static final String shape = "shape";

  /**
   * 
   */
  public static final String degreesOfFreedom = "degreesOfFreedom";

  /**
   * 
   */
  public static final String numberOfClasses = "numberOfClasses";

  /**
   * 
   */
  public static final String listOfUncertainties = "listOfUncertainties";

  /**
   * 
   */
  public static final String distribution = "distribution";
  
  /**
   * 
   */
  public static final String uncertStatistics = "uncertStatistics";
  
  /**
   * 
   */
  public static final String uncertStatisticSpan = "uncertStatisticSpan";
  
  /**
   * 
   */
  public static final List<String> namespaces;

  /**
   * 
   */
  public static final String type = "type";

  /**
   * 
   */
  public static final String units = "units";

  /**
   * 
   */
  public static final String definitionURL = "definitionURL";

  static {
    namespaces = new ArrayList<String>();
    namespaces.add(namespaceURI_L3V1V1);
  }

}
