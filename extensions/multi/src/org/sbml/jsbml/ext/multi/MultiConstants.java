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
package org.sbml.jsbml.ext.multi;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains some constants related to the multi package.
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 16.10.2013
 */
public class MultiConstants {

  /**
   * The namespace URI of this parser for SBML level 3, version 1 and package version 1.
   */
  public static final String namespaceURI_L3V1V1 = "http://www.sbml.org/sbml/level3/version1/multi/version1";

  /**
   * The latest namespace URI of this parser, this value can change between releases.
   */
  public static final String namespaceURI = namespaceURI_L3V1V1;

  /**
   * 
   */
  public static final String packageDisplayName = "Multistate and Multicomponent Species";

  /**
   * 
   */
  public static final String packageName = "multi";

  /**
   * 
   */
  public static final String shortLabel = "multi";

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
  public static final String listOfSpeciesTypes = "listOfSpeciesTypes";
  /**
   * 
   */
  public static final String listOfStateFeatures = "listOfStateFeatures";
  /**
   * 
   */
  public static final String listOfPossibleValues = "listOfPossibleValues";
  /**
   * 
   */
  public static final String listOfSpeciesTypeStates = "listOfSpeciesTypeStates";
  /**
   * 
   */
  public static final String listOfStateFeatureInstances = "listOfStateFeatureInstances";
  /**
   * 
   */
  public static final String listOfStateFeatureValues = "listOfStateFeatureValues";


  /**
   * 
   */
  public static final String speciesType = "speciesType";

  // speciesType
  /**
   * 
   */
  public static final String bindingSite = "bindingSite";

  /**
   * 
   */
  public static final String compartmentType = "compartmentType";

  /**
   * 
   */
  public static final String isType = "isType";

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
  public static final String compartment = "compartment";

  /**
   * 
   */
  public static final String numericValue = "numericValue";

  /**
   * 
   */
  public static final String compartmentReference = "compartmentReference";

  /**
   * 
   */
  public static final String occur = "occur";

  /**
   * 
   */
  public static final String speciesTypeComponentIndex = "speciesTypeComponentIndex";

  /**
   * 
   */
  public static final String denotedSpeciesTypeComponentIndex = "denotedSpeciesTypeComponentIndex";

  /**
   * 
   */
  public static final String component = "component";

  /**
   * 
   */
  public static final String identifyingParent = "indentifyingParent";

  /**
   * 
   */
  public static final String bindingSite1 = "bindingSite1";

  /**
   * 
   */
  public static final String bindingSite2 = "bindingSite2";

  /**
   * 
   */
  public static final String bindingStatus = "bindingStatus";

  /**
   * 
   */
  public static final String outwardBindingSite = "outwardBindingSite";

  /**
   * 
   */
  public static final String value = "value";

  /**
   * 
   */
  public static final String speciesFeatureType = "speciesFeatureType";

  /**
   * 
   */
  public static final String reactantSpeciesFeature = "reactantSpeciesFeature";

  /**
   * 
   */
  public static final String productSpeciesFeature = "productSpeciesFeature";
  
  /**
   * 
   */
  public static final String reactant = "reactant";

  /**
   * 
   */
  public static final String productComponent = "productComponent";

  /**
   * 
   */
  public static final String reactantComponent = "reactantComponent";

  
  
  /**
   * @param level
   * @param version
   * @return
   */
  public static String getNamespaceURI(int level, int version) {
    return namespaceURI;
  }

  
  // TODO - delete values not used any more
  
  /**
   * 
   */
  public static final String selector = "selector";
  /**
   * 
   */
  public static final String stateFeature = "stateFeature";
  /**
   * 
   */
  public static final String speciesTypeState = "speciesTypeState";
  /**
   * 
   */
  public static final String stateFeatureInstance = "stateFeatureInstance";
  /**
   * 
   */
  public static final String stateFeatureValue = "stateFeatureValue";
  /**
   * 
   */
  public static final String containedSpeciesType = "containedSpeciesType";
  /**
   * 
   */
  public static final String bond = "bond";
  /**
   * 
   */
  public static final String bindingSiteReference = "bindingSiteReference";


  // speciesTypeState
  /**
   * 
   */
  public static final String minOccur = "minOccur";
  /**
   * 
   */
  public static final String maxOccur = "maxOccur";
  /**
   * 
   */
  public static final String connex = "connex";
  /**
   * 
   */
  public static final String saturated = "saturated";

  // bond
  /**
   * 
   */
  public static final String occurrence = "occurrence";

  /**
   * 
   */
  public static String speciesTypeRestriction = "speciesTypeRestriction";

  /**
   * 
   */
  public static final String listOfSelectors = "listOfSelectors";
  /**
   * 
   */
  public static final String listOfContainedSpeciesTypes = "listOfContainedSpeciesTypes";
  /**
   * 
   */
  public static final String listOfBonds = "listOfBonds";
  /**
   * 
   */
  public static final String listOfUnboundBindingSites = "listOfUnboundBindingSites";

  /**
   * 
   */
  public static final String listOfPossibleSpeciesFeatureValues = "listOfPossibleSpeciesFeatureValues";

  /**
   * 
   */
  public static final String possibleSpeciesFeatureValue = "possibleSpeciesFeatureValue";







  


}
