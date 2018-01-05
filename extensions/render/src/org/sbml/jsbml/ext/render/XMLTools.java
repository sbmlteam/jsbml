/*
 *
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
package org.sbml.jsbml.ext.render;

import java.awt.Color;
import java.util.Locale;

import org.sbml.jsbml.util.StringTools;

/**
 * Utility class to help write the XML
 * 
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class XMLTools {

  /**
   * @param x
   * @param absoluteX
   * @return
   */
  public static String positioningToString(double x, boolean absoluteX) {
    if (absoluteX) {
      return StringTools.toString(Locale.ENGLISH, x);
    }
    else {
      return StringTools.toString(Locale.ENGLISH, x) + "%";
    }
  }

  /**
   * @param fontStyleItalic
   * @return
   */
  // Returns "italic" if fontStyleItalic is true, else "normal"
  public static String fontStyleItalicToString(boolean fontStyleItalic) {
    return (fontStyleItalic ?
      RenderConstants.fontStyleItalicTrue : RenderConstants.fontStyleItalicFalse);
  }

  /**
   * @param fontWeightBold
   * @return
   */
  //Returns "italic" if fontStyleBold is true, else "normal"
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
      return StringTools.parseSBMLDouble(value.substring(0, value.length() - 1));
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

  /**
   * @param value
   * @return
   */
  public static String encodeColorToString(Color value) {
    int r = value.getRed();
    int g = value.getGreen();
    int b = value.getBlue();
    int a = value.getAlpha();
    return ("#" + toHexString(r) +
        toHexString(g) + toHexString(b) + toHexString(a)).toUpperCase();
  }

  /**
   * @param a
   * @return
   */
  private static String toHexString(int a) {
    String h = Integer.toHexString(a);
    return h.length() == 1 ? "0" + h : h;
  }

  /**
   * 
   * @param value
   * @return
   */
  public static Color decodeStringToColor(String value) {
    int r = Integer.parseInt(value.substring(1, 3), 16);
    int g = Integer.parseInt(value.substring(3, 5), 16);
    int b = Integer.parseInt(value.substring(5, 7), 16);
    int a = 255;
    if (value.length() == 9) {
      a = Integer.parseInt(value.substring(7, 9), 16);
    }
    return new Color(r, g, b, a);
  }

  /**
   * @param list
   * @return
   */
  public static String arrayToWhitespaceSeparatedString(String[] list) {
    String output = "";
    for (int i = 0; i < list.length; i++) {
      output += list[i];
      if (i != (list.length - 1)) {
        output += " ";
      }
    }
    return output;
  }

  /**
   * 
   * @param array
   * @return
   */
  public static String encodeArrayDoubleToString(Double[] array) {
    String s = "";

    for (int i = 0; i < array.length; i++) {
      if (array[i] != null) {
        if (!s.isEmpty()) {
          s +=", ";
        }
        s += StringTools.toString(Locale.ENGLISH, array[i]);
      }
    }
    return s;
  }

  /**
   * 
   * @param value
   * @return
   */
  public static Double[] decodeStringToArrayDouble(String value) {
    String[] array = value.split(", ");
    Double[] temp = new Double[array.length];
    for (int i = 0; i < array.length; i++) {
      temp[i] = StringTools.parseSBMLDouble(array[i]);
    }
    return temp;
  }

  /**
   * 
   * @param array
   * @return
   */
  public static String encodeArrayShortToString(Short[] array) {
    String s = "";

    for (int i = 0; i < array.length; i++) {
      if (!s.isEmpty()) {
        s += ", ";
      }
      s += Short.toString(array[i]);
    }
    return s;
  }

  /**
   * 
   * @param value
   * @return
   */
  public static Short[] decodeStringToArrayShort(String value) {
    String[] array = value.split(", ");
    Short[] temp = new Short[array.length];
    for (int i = 0; i < array.length; i++) {
      temp[i] = StringTools.parseSBMLShort(array[i]);
    }
    return temp;
  }

}
