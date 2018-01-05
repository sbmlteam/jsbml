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
package org.sbml.jsbml.ext.spatial;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.sbml.jsbml.util.ResourceManager;

/**
 * Contains some constants related to the spatial package.
 * 
 * @author Alex Thomas
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class SpatialConstants {

  /**
   * The namespace URI of this parser for SBML level 3, version 1 and package version 1.
   */
  public static final String namespaceURI_L3V1V1 = "http://www.sbml.org/sbml/level3/version1/spatial/version1";

  /**
   * The latest namespace URI of this parser, this value can change between releases.
   */
  public static final String namespaceURI = namespaceURI_L3V1V1;

  /**
   * 
   */
  public static final String shortLabel = "spatial";
  /**
   * 
   */
  public static final ResourceBundle bundle = ResourceManager.getBundle("org.sbml.jsbml.ext.spatial.Messages");


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
  public static final String compartment = "compartment";
  /**
   * 
   */
  public static final String unitSize = "unitSize";
  /**
   * 
   */
  public static final String spatialSymbolReference = "spatialSymbolReference";
  /**
   * 
   */
  public static final String diffusionCoefficient = "diffusionCoefficient";
  /**
   * 
   */
  public static final String advectionCoefficient = "advectionCoefficient";
  /**
   * 
   */
  public static final String boundaryCondition = "boundaryCondition";
  /**
   * 
   */
  public static final String coordinate = "coordinate";
  /**
   * 
   */
  public static final String domain1 = "domain1";
  /**
   * 
   */
  public static final String domain2 = "domain2";
  /**
   * 
   */
  public static final String geometry = "geometry";
  /**
   * 
   */
  public static final String isSpatial = "isSpatial";
  /**
   * 
   */
  public static final String compartmentMapping = "compartmentMapping";
  /**
   * 
   */
  public static final String packageName = "Spatial";
  /**
   * 
   */
  public static final String coordinateSystem = "coordinateSystem";
  /**
   * 
   */
  public static final String componentType = "componentType";
  /**
   * 
   */
  public static final String index = "index";
  /**
   * 
   */
  public static final String boundaryMaximum = "boundaryMax";
  /**
   * 
   */
  public static final String boundaryMinimum = "boundaryMin";
  /**
   * 
   */
  public static final String unit = "unit";
  /**
   * 
   */
  public static final String value = "value";
  /**
   * 
   */
  public static final String spatialId = "id";
  /**
   * 
   */
  public static final String spatialDimensions = "spatialDimensions";
  /**
   * 
   */
  public static final String domainType = "domainType";
  /**
   * 
   */
  public static final String coord1 = "coord1";
  /**
   * 
   */
  public static final String coord2 = "coord2";
  /**
   * 
   */
  public static final String coord3 = "coord3";
  /**
   * 
   */
  public static final String functionType = "functionType";
  /**
   * 
   */
  public static final String ordinal = "ordinal";
  /**
   * 
   */
  public static final String sampledField = "sampledField";
  /**
   * 
   */
  public static final String sampledValue = "sampledValue";
  /**
   * 
   */
  public static final String minValue = "minValue";
  /**
   * 
   */
  public static final String maxValue = "maxValue";
  /**
   * 
   */
  public static final String dataType = "dataType";
  /**
   * 
   */
  public static final String numSamples1 = "numSamples1";
  /**
   * 
   */
  public static final String numSamples2 = "numSamples2";
  /**
   * 
   */
  public static final String numSamples3 = "numSamples3";
  /**
   * 
   */
  public static final String encoding = "encoding";
  /**
   * 
   */
  public static final String imageData = "imageData";
  /**
   * 
   */
  public static final String csgNode = "csgNode";
  /**
   * 
   */
  public static final String primitiveType = "primitiveType";
  /**
   * 
   */
  public static final String translateX = "translateX";
  /**
   * 
   */
  public static final String translateY = "translateY";
  /**
   * 
   */
  public static final String translateZ = "translateZ";
  /**
   * 
   */
  public static final String rotateAxisX = "rotateAxisX";
  /**
   * 
   */
  public static final String rotateAxisY = "rotateAxisY";
  /**
   * 
   */
  public static final String rotateAxisZ = "rotateAxisZ";
  /**
   * 
   */
  public static final String rotateAngleInRadians = "rotateAngleInRadians";
  /**
   * 
   */
  public static final String scaleX = "scaleX";
  /**
   * 
   */
  public static final String scaleY = "scaleY";
  /**
   * 
   */
  public static final String scaleZ = "scaleZ";
  /**
   * 
   */
  public static final String components = "components";
  /**
   * 
   */
  public static final String componentsLength = "componentsLength";
  /**
   * 
   */
  public static final String domain = "domain";
  /**
   * 
   */
  public static final String polygonType = "polygonType";
  /**
   * 
   */
  public static final String polygonObject = "polygonObject";
  /**
   * 
   */
  public static final String pointIndex = "pointIndex";
  /**
   * 
   */
  public static final String samples = "samples";
  /**
   * 
   */
  public static final String samplesLength = "samplesLength";
  /**
   * 
   */
  public static final String forwardTransformation = "forwardTransformation";
  /**
   * 
   */
  public static final String reverseTransformation = "reverseTransformation";
  /**
   * 
   */
  public static final String operationType = "operationType";
  /**
   * 
   */
  public static final String isLocal = "isLocal";
  /**
   * 
   */
  public static final String type = "type";
  /**
   * 
   */
  public static final String variable = "variable";
  /**
   * 
   */
  public static final String coordinateBoundary = "coordinateBoundary";
  /**
   * 
   */
  public static final String boundaryDomainType = "boundaryDomainType";
  /**
   * 
   */
  public static final String listOfCoordinateComponents = "listOfCoordinateComponents";
  /**
   * 
   */
  public static final String listOfDomainTypes = "listOfDomainTypes";
  /**
   * 
   */
  public static final String listOfDomains = "listOfDomains";
  /**
   * 
   */
  public static final String listOfAdjacentDomains = "listOfAdjacentDomains";
  /**
   * 
   */
  public static final String listOfGeometryDefinitions = "listOfGeometryDefinitions";
  /**
   * 
   */
  public static final String coordinateComponent = "coordinateComponent";
  /**
   * 
   */
  public static final String adjacentDomains = "adjacentDomains";
  /**
   * 
   */
  public static final String geometryDefinition = "geometryDefinition";

  /**
   * 
   */
  public static final String sampledFieldGeometry = "sampledFieldGeometry";
  /**
   * 
   */
  public static final String analyticGeometry = "analyticGeometry";
  /**
   * 
   */
  public static final String csGeometry = "csGeometry";
  /**
   * 
   */
  public static final String parametricGeometry = "parametricGeometry";
  /**
   * 
   */
  public static final String listOfInteriorPoints = "listOfInteriorPoints";
  /**
   * 
   */
  public static final String interiorPoint = "interiorPoint";

  /**
   * 
   */
  public static final String listOfAnalyticVolumes = "listOfAnalyticVolumes";
  /**
   * 
   */
  public static final String analyticVolume = "analyticVolume";
  /**
   * 
   */
  public static final String listOfSampledVolumes = "listOfSampledVolumes";
  /**
   * 
   */
  public static final String sampledVolume = "sampledVolume";
  /**
   * 
   */
  public static final String listOfCSGObjects = "listOfCSGObjects";

  /**
   * 
   */
  public static final String csgObject = "csgObject";

  /**
   * 
   */
  public static final String csgTransformation = "csgTransformation";
  /**
   * 
   */
  public static final String csgTranslation = "csgTranslation";
  /**
   * 
   */
  public static final String csgRotation = "csgRotation";
  /**
   * 
   */
  public static final String csgScale = "csgScale";
  /**
   * 
   */
  public static final String csgHomogeneousTransformation = "csgHomogeneousTransformation";
  /**
   * 
   */
  public static final String transformationComponent = "transformationComponent";
  /**
   * 
   */
  public static final String csgPrimitive = "csgPrimitive";
  /**
   * 
   */
  public static final String csgPseudoPrimitive = "csgPseudoPrimitive";
  /**
   * 
   */
  public static final String csgSetOperator = "csgSetOperator";
  /**
   * 
   */
  public static final String listOfCSGNodes = "listOfCSGNodes";

  /**
   * 
   */
  public static final String listOfSpatialPoints = "listOfSpatialPoints";
  /**
   * 
   */
  public static final String listOfParametricObjects = "listOfParametricObjects";
  /**
   *
   */
  public static final String spatialPoints = "spatialPoints";  
  /**
   * 
   */
  public static final String parametricObject = "parametricObject";
  /**
   * 
   */
  public static final String diffusionKind = "diffusionKind";

  /**
   * 
   */
  public static final String coordinateReference1 = "coordinateReference1";
  /**
   * 
   */
  public static final String coordinateReference2 = "coordinateReference2";

  /**
   * 
   */
  public static final String isActive = "isActive";

  /**
   * 
   */
  public static final String complementA = "complementA";
  /**
   * 
   */
  public static final String complementB = "complementB";
  /**
   * 
   */
  public static final String interpolationType = "interpolationType";
  /**
   * 
   */
  public static final String compression = "compression";
  /**
   * 
   */
  public static final String data = "data";
  /**
   * 
   */
  public static final String pointIndexLength = "pointIndexLength";

  /**
   * 
   */
  public static final String arrayData = "arrayData";
  
  /**
   * 
   */
  public static final String arrayDataLength = "arrayDataLength";

  /**
   * 
   */
  public static final String spatialRef = "spatialRef";

  /**
   * 
   */
  public static final Object listOfSampledFields = "listOfSampledFields";

  /**
   * @param level
   * @param version
   * @return
   */
  public static String getNamespaceURI(int level, int version) {
    return namespaceURI;
  }

}
