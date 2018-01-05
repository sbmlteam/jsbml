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
package org.sbml.jsbml.ext.layout;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains some constants related to the layout package.
 * 
 * @since 1.0
 */
public class LayoutConstants {

  /**
   * 
   */
  public static final List<String> all_L3_namespaces;

  /**
   * 
   */
  public static final String basePoint1 = "basePoint1";

  /**
   * 
   */
  public static final String basePoint2 = "basePoint2";

  /**
   * 
   */
  public static final String boundingBox = "boundingBox";
  // Attribute names of the CompartmentGlyph XML type
  /**
   * 
   */
  public static final String compartment = "compartment";

  /**
   * 
   */
  public static final String compartmentGlyph = "compartmentGlyph";
  /**
   * 
   */
  public static final String cubicBezier = "cubicBezier";
  /**
   * 
   */
  public static final String curve = "curve";

  /**
   * 
   */
  public static final String curveSegment = "curveSegment";

  // Attribute names of the Dimensions XML type
  /**
   * 
   */
  public static final String depth = "depth";

  /**
   * 
   */
  public static final String dimensions = "dimensions";
  /**
   * 
   */
  public static final String end = "end";

  /**
   * 
   */
  public static final String generalGlyph = "generalGlyph";
  /**
   * 
   */
  public static final String glyph = "glyph";
  // Attribute names of the textGlyph XML type
  /**
   * 
   */
  /**
   * 
   */
  public static final String graphicalObject = "graphicalObject";
  /**
   * 
   */
  public static final String height = "height";
  /**
   * 
   */
  public static final String layout = "layout";
  /**
   * 
   */
  public static final String lineSegment = "lineSegment";
  /**
   * 
   */
  public static final String listOfAdditionalGraphicalObjects = "listOfAdditionalGraphicalObjects";
  /**
   * 
   */
  public static final String listOfCompartmentGlyphs = "listOfCompartmentGlyphs";
  /**
   * 
   */
  public static final String listOfCurveSegments = "listOfCurveSegments";
  /**
   * 
   */
  public static final String listOfGeneralGlyphs = "listOfGeneralGlyphs";
  /**
   * 
   */
  public static final String listOfLayouts = "listOfLayouts";


  /**
   * 
   */
  public static final String listOfLineSegments = "listOfLineSegments";
  /**
   * 
   */
  public static final String listOfReactionGlyphs = "listOfReactionGlyphs";
  /**
   * 
   */
  public static final String listOfReferenceGlyphs = "listOfReferenceGlyphs";
  /**
   * 
   */
  public static final String listOfSpeciesGlyphs = "listOfSpeciesGlyphs";
  /**
   * 
   */
  public static final String listOfSpeciesReferenceGlyphs = "listOfSpeciesReferenceGlyphs";
  /**
   * 
   */
  public static final String listOfSubGlyphs = "listOfSubGlyphs";
  /**
   * 
   */
  public static final String listOfTextGlyphs = "listOfTextGlyphs";
  // Attribute names of the GraphicalObject XML type
  /**
   * 
   */
  public static final String metaidRef = "metaidRef";
  /**
   * 
   */
  public static final List<String> namespaces_L2;
  /**
   * 
   */
  public static final List<String> namespaces_L3;
  /**
   * 
   */
  public static final String namespaceURI_L2 = "http://projects.eml.org/bcb/sbml/level2";
  /**
   * The namespace URI of this parser for SBML level 3, version 1 and package version 1.
   */
  public static final String namespaceURI_L3V1V1 = "http://www.sbml.org/sbml/level3/version1/layout/version1";
  /**
   * The latest namespace URI of this parser, this value can change between releases.
   */
  public static final String namespaceURI = namespaceURI_L3V1V1;
  /**
   * 
   */
  public static final String order = "order";
  /**
   * 
   */
  public static final String originOfText = "originOfText";

  /**
   * 
   */
  public static final String packageName = "Layout";

  /**
   * 
   */
  public static final String position = "position";
  /**
   * 
   */
  public static final String reaction = "reaction";
  /**
   * 
   */
  public static final String reactionGlyph = "reactionGlyph";

  // Attribute names of the ReferenceGlyph XML type
  /**
   * 
   */
  public static final String reference = "reference";
  /**
   * 
   */
  public static final String referenceGlyph = "referenceGlyph";
  /**
   * 
   */
  public static final String role = "role";

  /**
   * 
   */
  public static final String shortLabel = "layout";
  // Attribute names of the speciesGlyph XML type
  /**
   * 
   */
  public static final String species = "species";

  /**
   * 
   */
  public static final String speciesGlyph = "speciesGlyph";

  // Attribute names of the speciesReferenceGlyph XML type
  /**
   * 
   */
  public static final String speciesReference = "speciesReference";
  /**
   * 
   */
  public static final String speciesReferenceGlyph = "speciesReferenceGlyph";
  /**
   * 
   */
  public static final String start = "start";

  /**
   * 
   */
  public static final String text = "text";
  /**
   * 
   */
  public static final String textGlyph = "textGlyph";


  /**
   * 
   */
  public static final String type = "type";
  /**
   * 
   */
  public static final String width = "width";
  // Attribute names of the Point XML type
  /**
   * 
   */
  public static final String x = "x";

  /**
   * 
   */
  public static final String xsiNamespace = "http://www.w3.org/2001/XMLSchema-instance";
  /**
   * 
   */
  public static final String xsiShortLabel = "xsi";
  /**
   * 
   */
  public static final String y = "y";

  /**
   * 
   */
  public static final String z = "z";

  static {
    namespaces_L3 = new ArrayList<String>();
    namespaces_L3.add(namespaceURI_L3V1V1);

    namespaces_L2 = new ArrayList<String>();
    namespaces_L2.add(namespaceURI_L2);

    all_L3_namespaces = new ArrayList<String>();
    all_L3_namespaces.addAll(namespaces_L3);
    all_L3_namespaces.add(xsiNamespace);
  }

  /**
   * 
   * @param level
   * @param version
   * @return
   */
  public static String getNamespaceURI(int level, int version) {
    if (level < 3) {
      return namespaceURI_L2;
    }
    return namespaceURI;
  }

}
