/*
 * $Id$
 * $URL$
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

import java.awt.Color;
import java.util.ArrayList;

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

  /**
   * @param value
   * @return
   */
  public static String decodeColorToString(Color value) {
    int r = value.getRed();
    int g = value.getGreen();
    int b = value.getBlue();
    return ("#" + Integer.toHexString(r) +
        Integer.toHexString(g) + Integer.toHexString(b)).toUpperCase();
  }

  /**
   * @param roleList
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
 
  
  public static String encodeColorToString(Color value){
	  int r = value.getRed();
	  int g = value.getGreen();
	  int b = value.getBlue();
	  int a = value.getAlpha();
	  return ("#" + Integer.toHexString(r) + Integer.toHexString(g) + 
			  		Integer.toHexString(b) + Integer.toHexString(a)).toUpperCase();
  }
  
  public static Color decodeStringToColor(String string){
	  int r = Integer.parseInt(string.substring(1, 3), 16);
	  int g = Integer.parseInt(string.substring(3, 5), 16);
	  int b = Integer.parseInt(string.substring(5, 7), 16);
	  int a = 255;
	  if (string.length() == 9){
		  a = Integer.parseInt(string.substring(7, 9), 16);
	  }
	  
	  return new Color(r,g,b,a);
  }
  
  public static String encodeColorDefintionToString(ColorDefinition cd){
	  return cd.getId() + "," + encodeColorToString(cd.getValue());
  }
  
  public static ColorDefinition decodeStringToColorDefintion(String value){
	  String id = value.substring(0, value.indexOf(","));
	  Color color = decodeStringToColor(value.substring(value.indexOf(",") + 1));
	  return new ColorDefinition(id, color);
  }

  public static String encodeArrayDoubleToString(Double[] array) {
	
	  String s = "";
	  
	  for (int i = 0; i < array.length; i++) {
		  if(!s.isEmpty()){
			  s+=",";
		  }
		  s+= Double.toString(array[i]);
	  }
	  return s;
  }

  public static Double[] decodeStringToArrayDouble(String value, int length) {
	
	  Double[] array = new Double[length];
	  String sub = "";
	  
	  for (int i = 0; i < length; i++) {
		  int index = value.indexOf(",");
		  if(index == -1) {
			  sub = value.substring(0);
		  }
		  else{
			  sub = value.substring(0, index);
			  value = value.substring(index+1);
		  }
		  
		  array[i] = StringTools.parseSBMLDouble(sub);
	  }
	  return array;
  }

  
  public static String encodeArrayShortToString(Short[] array) {
		
	  String s = "";
	  
	  for (int i = 0; i < array.length; i++) {
		  if(!s.isEmpty()){
			  s+=",";
		  }
		  s+= Short.toString(array[i]);
	  }
	  return s;
  }
  
  public static Short[] decodeStringToArrayShort(String value) {
	
	  ArrayList<Short> list = new ArrayList<Short>();
	  String sub = "";
	  int index;
	  
	  while(!value.isEmpty()) {
		  index = value.indexOf(",");
		  if(index == -1) {
			  list.add(Short.valueOf(value.substring(0)));
			  return (Short[]) list.toArray();
		  }
		  else{
			  sub = value.substring(0, index);
			  value = value.substring(index+1);
		  }
		  
		  //array[i] = StringTools.parseSBMLShort(sub);
		  list.add(Short.valueOf(sub));
	  }
	  return (Short[]) list.toArray();
  }
 
}
