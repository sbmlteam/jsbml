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
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.multi;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 16.10.2013
 */
public class MultiConstants {

  /**
   * The namespace URI of this parser.
   */
  public static final String namespaceURI = "http://www.sbml.org/sbml/level3/version1/multi/version1";

  public static final String shortLabel = "multi";

  public static final List<String> namespaces;

  static {
    namespaces = new ArrayList<String>();
    namespaces.add(namespaceURI);
  }

  public static final String listOfSpeciesTypes = "listOfSpeciesTypes";
  public static final String listOfSelectors = "listOfSelectors";
  public static final String listOfStateFeatures = "listOfStateFeatures";
  public static final String listOfPossibleValues = "listOfPossibleValues";
  public static final String listOfSpeciesTypeStates = "listOfSpeciesTypeStates";
  public static final String listOfStateFeatureInstances = "listOfStateFeatureInstances";
  public static final String listOfStateFeatureValues = "listOfStateFeatureValues";
  public static final String listOfContainedSpeciesTypes = "listOfContainedSpeciesTypes";
  public static final String listOfBonds = "listOfBonds";
  public static final String listOfUnboundBindingSites = "listOfUnboundBindingSites";


  public static final String speciesType = "speciesType";
  public static final String selector = "selector";
  public static final String stateFeature = "stateFeature";
  public static final String possibleValue = "possibleValue";
  public static final String speciesTypeState = "speciesTypeState";
  public static final String stateFeatureInstance = "stateFeatureInstance";
  public static final String stateFeatureValue = "stateFeatureValue";
  public static final String containedSpeciesType = "containedSpeciesType";
  public static final String bond = "bond";
  public static final String bindingSiteReference = "bindingSiteReference";

  // speciesType
  public static final String bindingSite = "bindingSite";

  // speciesTypeState
  public static final String minOccur = "minOccur";
  public static final String maxOccur = "maxOccur";
  public static final String connex = "connex";
  public static final String saturated = "saturated";

  // bond
  public static final String occurrence = "occurrence";

  public static String speciesTypeRestriction = "speciesTypeRestriction";

  public static final String packageName = "Multistate and Multicomponent Species";

  /**
   * @param level
   * @param version
   * @return
   */
  public static String getNamespaceURI(int level, int version) {
    return namespaceURI;
  }

}
