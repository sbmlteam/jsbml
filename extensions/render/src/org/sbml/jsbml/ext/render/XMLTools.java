/*
 * $Id:  XMLTools.java 14:58:10 jakob $
 * $URL: XMLTools.java $
 *
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2011 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.render;

import org.sbml.jsbml.util.StringTools;


/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 03.06.2012
 */
public class XMLTools {

  /**
   * @param x
   * @param absoluteX
   * @return
   */
  public static String positioningToString(double x, boolean absoluteX) {
    if (absoluteX) {
      return StringTools.toString(x);
    }
    else {
      return StringTools.toString(x * 100d) + "%";
    }
  }

  /**
   * @param fontStyleItalic
   * @return
   */
  public static String fontStyleItalicToString(boolean fontStyleItalic) {
    return (fontStyleItalic ?
      RenderConstants.fontStyleItalicTrue : RenderConstants.fontStyleItalicFalse);
  }

  /**
   * @param fontWeightBold
   * @return
   */
  public static String fontWeightBoldToString(boolean fontWeightBold) {
    return (fontWeightBold ?
      RenderConstants.fontWeightBoldTrue : RenderConstants.fontWeightBoldFalse);
  }

  /**
   * @param value
   * @return
   */
  public static Boolean isAbsolutePosition(String value) {
    return (! value.endsWith("%"));
  }

  /**
   * @param value
   * @return
   */
  public static Double parsePosition(String value) {
    if (isAbsolutePosition(value)) {
      return StringTools.parseSBMLDouble(value);
    }
    else {
      return (StringTools.parseSBMLDouble(value.substring(0, value.length() - 1))
          / 100d);
    }
  }

  /**
   * @param value
   * @return
   */
  public static boolean parseFontStyleItalic(String value) {
    // TODO: throw Exception if value not in { "normal", "italic" }?
    return (value.equals(RenderConstants.fontStyleItalicTrue));
  }

  /**
   * @param value
   * @return
   */
  public static boolean parseFontWeightBold(String value) {
    return (value.equals(RenderConstants.fontWeightBoldTrue));
  }
}
