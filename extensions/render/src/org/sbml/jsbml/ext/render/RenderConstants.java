/*
 *
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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
package org.sbml.jsbml.ext.render;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains some constants related to the render package.
 * 
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @author David Emanuel Vetter
 * @since 1.0
 */
public class RenderConstants {
  /**
   * 
   */
  public static final int MIN_SBML_LEVEL = 3;
  /**
   * 
   */
  public static final int MIN_SBML_VERSION = 1;

  /**
   * The namespace URI of this parser for SBML level 3, version 1 and package version 1.
   */
  public static final String namespaceURI_L3V1V1 = "http://www.sbml.org/sbml/level3/version1/render/version1";

  /**
   * The latest namespace URI of this parser, this value can change between releases.
   */
  public static final String namespaceURI = namespaceURI_L3V1V1;

  /**
   * 
   */
  public static final String shortLabel = "render";

  /**
   * 
   */
  public static final String namespaceURI_L2 = "http://projects.eml.org/bcb/sbml/render/level2";

  /**
   * 
   */
  public static final List<String> namespaces_L3;
  /**
   * 
   */
  public static final List<String> namespaces_L2;

  static {
    namespaces_L3 = new ArrayList<String>();
    namespaces_L3.add(namespaceURI_L3V1V1);

    namespaces_L2 = new ArrayList<String>();
    namespaces_L2.add(namespaceURI_L2);
  }


  /**
   * 
   */
  public static final String fontFamily = "font-family";
  /**
   * 
   */
  public static final String fontSize = "font-size";
  /**
   * 
   */
  public static final String fontWeightBold = "font-weight";
  /**
   * 
   */
  public static final String fontWeightBoldTrue = "bold";
  /**
   * 
   */
  public static final String fontWeightBoldFalse = "normal";
  /**
   * 
   */
  public static final String fontStyleItalic = "font-style";
  /**
   * 
   */
  public static final String fontStyleItalicTrue = "italic";
  /**
   * 
   */
  public static final String fontStyleItalicFalse = "normal";
  /**
   * 
   */
  public static final String textAnchor = "text-anchor";
  /**
   * 
   */
  public static final String vTextAnchor = "vtext-anchor";
  /**
   * 
   */
  public static final String x = "x";
  /**
   * 
   */
  public static final String y = "y";
  /**
   * 
   */
  public static final String z = "z";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteX = "absolute-x";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteY = "absolute-y";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteZ = "absolute-z";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteX1 = "absolute-x1";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteY1 = "absolute-y1";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteZ1 = "absolute-z1";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteX2 = "absolute-x2";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteY2 = "absolute-y2";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteZ2 = "absolute-z2";
  /**
   * 
   */
  public static final String x1 = "x1";
  /**
   * 
   */
  public static final String y1 = "y1";
  /**
   * 
   */
  public static final String z1 = "z1";
  /**
   *
   */
  public static final String x2 = "x2";
  /**
   * 
   */
  public static final String y2 = "y2";
  /**
   * 
   */
  public static final String z2 = "z2";

  
  // COPASI and the render-specification (January 2020) use camel-case basePoint 
  /**
   * 
   */
  public static final String basepoint1_x = "basePoint1_x"; // JSBML used "x1" for a few years
  /**
   * 
   */
  public static final String basepoint1_y = "basePoint1_y"; // JSBML used "y1" for a few years
  /**
   * 
   */
  public static final String basepoint1_z = "basePoint1_z"; // JSBML used "z1" for a few years
  /**
   * 
   */
  public static final String basepoint2_x = "basePoint2_x"; // JSBML used "x2" for a few years
  /**
   * 
   */
  public static final String basepoint2_y = "basePoint2_y"; // JSBML used "y2" for a few years
  /**
   * 
   */
  public static final String basepoint2_z = "basePoint2_z"; // JSBML used "z2" for a few years
  /**
   * 
   */
  public static final String width = "width";
  /**
   * 
   */
  public static final String height = "height";
  /**
   * 
   */
  public static final String ry = "ry";
  /**
   * 
   */
  public static final String rx = "rx";
  /**
   * The rx/ry-ratio of an ellipse
   */
  public static final String ratio = "ratio";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteWidth = "absolute-width";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteHeight = "absolute-height";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteRx = "absolute-rx";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteRy = "absolute-ry";
  /**
   * 
   */
  public static final String fill = "fill";
  /**
   * 
   */
  public static final String fillRule = "fill-rule";
  /**
   * 
   */
  public static final String stroke = "stroke";
  /**
   * 
   */
  public static final String strokeWidth = "stroke-width";
  /**
   * 
   */
  public static final String strokeDashArray = "stroke-dasharray";
  /**
   * 
   */
  public static final String endHead = "endHead";
  /**
   * 
   */
  public static final String startHead = "startHead";
  /**
   * 
   */
  public static final String cx = "cx";
  /**
   * 
   */
  public static final String cy = "cy";
  /**
   * 
   */
  public static final String cz = "cz";
  /**
   * 
   */
  public static final String fx = "fx";
  /**
   * 
   */
  public static final String fy = "fy";
  /**
   * 
   */
  public static final String fz = "fz";
  /**
   * 
   */
  public static final String r = "r";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteCy = "absolute-cx";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteCx = "absolute-cy";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteCz = "absolute-cz";
  /**
   * 
   */
  public static final String stopColor = "stop-color";
  /**
   * 
   */
  public static final String offset = "offset";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteR = "absolute-r";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteFx = "absolute-fx";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteFy = "absolute-fy";
  /** @deprecated Made obsolete by the introduction of {@link RelAbsVector} */
  @Deprecated
  public static final String absoluteFz = "absolute-fz";
  /**
   * 
   */
  public static final String listOfElements = "listOfElements"; // JSBML used "list-of-elements", "listOfRenderPoints" or "listOfRenderCubicBeziers" for a few years
  /**
   * 
   */
  public static final String list_of_elements = "list-of-elements"; 
  /**
   * 
   */
  public static final String id = "id";
  /**
   * 
   */
  public static final String href = "href";
  /**
   * This previously (before 2020/03) held the outdated value "enable-rotation-mapping" 
   */
  public static final String enableRotationMapping = "enableRotationalMapping"; 
  /**
   * 
   */
  public static final String group = "g";
  /**
   * 
   */
  public static final String boundingBox = "boundingBox";
  /**
   * 
   */
  public static final String transform = "transform";
  /**
   * 
   */
  public static final String versionMinor = "versionMinor";
  /**
   * 
   */
  public static final String versionMajor = "versionMajor";
  /**
   * 
   */
  public static final String spreadMethod = "spreadMethod";
  /**
   * 
   */
  public static final String typeList = "typeList";
  /**
   * 
   */
  public static final String roleList = "roleList";
  /**
   * 
   */
  public static final String value = "value";
  /**
   * 
   */
  public static final String programName = "programName";
  /**
   * 
   */
  public static final String programVersion = "programVersion";
  /**
   * 
   */
  public static final String referenceRenderInformation = "referenceRenderInformation";
  /**
   * 
   */
  public static final String backgroundColor = "backgroundColor";
  /**
   * 
   */
  public static final String idList = "idList";
  /**
   * 
   */
  public static final String renderInformation = "renderInformation";
  // added while working on RenderParser
  /**
   * 
   */
  public static final String listOfStyles = "listOfStyles";
  /**
   * 
   */
  public static final String listOfGradientDefinitions = "listOfGradientDefinitions";
  /**
   * 
   */
  public static final String listOfLineEndings = "listOfLineEndings";
  /**
   * 
   */
  public static final String listOfColorDefinitions = "listOfColorDefinitions";
  /**
   * 
   */
  public static final String listOfLocalStyles = "listOfStyles"; // JSBML used "listOfLocalStyles" for a few years
  /**
   * 
   */
  public static final String renderCurve = "curve"; // JSBML used "renderCurve" for a few years
  /**
   * 
   */
  public static final String renderPoint = "element"; // JSBML used "renderPoint" for a few years
  /**
   * 
   */
  public static final String renderCubicBezier = "element"; // JSBML used "renderCubicBezier" for a few years
  /**
   * 
   */
  public static final String style = "style";
  /**
   * 
   */
  public static final String localStyle = "style"; // JSBML used "locaStyle" for a few years
  /**
   * 
   */
  public static final String gradientStop = "gradientStop";
  /**
   * 
   */
  public static final String colorDefiniton = "colorDefinition";
  /**
   * 
   */
  public static final String gradientBase = "gradientBase";
  /**
   * 
   */
  public static final String lineEnding = "lineEnding";
  /**
   * 
   */
  public static final String localRenderInformation = "renderInformation"; // JSBML used "localRenderInformation" for a few years
  /**
   * 
   */
  public static final String globalRenderInformation = "renderInformation"; // JSBML used "globalRenderInformation" for a few years
  /**
   * 
   */
  public static final String listOfGradientStops = "listOfGradientStops";
  
  /**
   * 
   */
  public static final String listOfLocalRenderInformation = "listOfRenderInformation"; // JSBML used "listOfLocalRenderInformation" for a few years
  /**
   * 
   */
  public static final String listOfGlobalRenderInformation = "listOfGlobalRenderInformation";
  /**
   * 
   */
  public static final String packageName = "render";
  /**
   * 
   */
  public static final String objectRole = "objectRole";
  /**
   * 
   */
  public static final String type = "type";
  /**
   * 
   */
  public static final String xsiShortLabel = "xsi";
  /**
   * 
   */
  public static final String element = "element";
  /**
   * 
   */
  public static final String text = "text";
  /**
   * 
   */
  public static final String ellipse = "ellipse";
  /**
   * 
   */
  public static final String rectangle = "rectangle";
  /**
   * 
   */
  public static final String polygon = "polygon";
  /**
   * 
   */
  public static final String image = "image";
  /**
   * 
   */
  public static final String radialGradient = "radialGradient";
  /**
   * 
   */
  public static final String stop = "stop";
  /**
   * 
   */
  public static final String linearGradient = "linearGradient";
  /**
   * 
   */
  public static final String defaultValues = "defaultValues";

  /** Refers to the absolute part of a RelAbsVector */
  public static final String absoluteValue = "absoluteValue";
  
  /** Refers to the relative part of a RelAbsVector */
  public static final String relativeValue = "relativeValue";

  /** For DefaultValues */
  public static final String linearGradient_x1 = "linearGradient_x1";
  /** For DefaultValues */
  public static final String linearGradient_y1 = "linearGradient_y1";
  /** For DefaultValues */
  public static final String linearGradient_z1 = "linearGradient_z1";
  /** For DefaultValues */
  public static final String linearGradient_x2 = "linearGradient_x2";
  /** For DefaultValues */
  public static final String linearGradient_y2 = "linearGradient_y2";
  /** For DefaultValues */
  public static final String linearGradient_z2 = "linearGradient_z2";
  /** For DefaultValues */
  public static final String radialGradient_cx = "radialGradient_cx";
  /** For DefaultValues */
  public static final String radialGradient_cy = "radialGradient_cy";
  /** For DefaultValues */
  public static final String radialGradient_cz = "radialGradient_cz";
  /** For DefaultValues */
  public static final String radialGradient_r = "radialGradient_r";
  /** For DefaultValues */
  public static final String radialGradient_fx = "radialGradient_fx";
  /** For DefaultValues */
  public static final String radialGradient_fy = "radialGradient_fy";
  /** For DefaultValues */
  public static final String radialGradient_fz = "radialGradient_fz";
  /** For DefaultValues */
  public static final String default_z = "default_z";
  
  /**
   * Returns the namespace URI corresponding to the given level and version.
   * 
   * @param level the SBML level
   * @param version the SBML version
   * @return the namespace URI corresponding to the level and version
   */
  public static String getNamespaceURI(int level, int version) {
    return level < 3 ? namespaceURI_L2 : namespaceURI;
  }

}
