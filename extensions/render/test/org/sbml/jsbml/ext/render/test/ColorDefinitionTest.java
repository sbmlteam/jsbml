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
 * 6. Marquette University, Milwaukee, WI, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.render.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Test;
import org.sbml.jsbml.ext.render.ColorDefinition;


/**
 * @author Ibrahim Vazirabad
 * @since 1.0
 */
public class ColorDefinitionTest {

  /**
   * Test method for {@link org.sbml.jsbml.ext.render.ColorDefinition#getAllowsChildren()}.
   */
  @Test
  public void testGetAllowsChildren() {
    ColorDefinition color=new ColorDefinition();
    assertTrue(!color.getAllowsChildren());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.ColorDefinition#getChildCount()}.
   */
  @Test
  public void testGetChildCount() {
    ColorDefinition color=new ColorDefinition();
    assertEquals("ChildCountError",0,color.getChildCount());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.render.ColorDefinition#getValue()}.
   */
  @Test
  public void testGetValue() {
    ColorDefinition color=new ColorDefinition();
    Color red=Color.RED;
    color.setValue(red);
    assertEquals("ColorError",red,color.getValue());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.ColorDefinition#isSetValue()}.
   */
  @Test
  public void testIsSetValue() {
    ColorDefinition color=new ColorDefinition();
    Color red=Color.RED;
    color.setValue(red);
    assertTrue(color.isSetValue());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.ColorDefinition#setValue(java.awt.Color)}.
   */
  @Test
  public void testSetValue() {
    ColorDefinition color=new ColorDefinition();
    Color red=Color.RED;
    color.setValue(red);
    assertEquals("SetValueError",color.getValue(),red);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.render.ColorDefinition} and setting its ID
   */
  @Test
  public void testColorDefinitionId() {
    ColorDefinition color=new ColorDefinition("");
    assertTrue(!color.isSetId());
    assertTrue(color.getId().equals(""));
    color.setId("black");
    assertTrue(color.isSetId());
    assertTrue(color.getId()  ==  "black");
    color.unsetId();
    assertTrue(!color.isSetId());
    assertTrue(color.getId()  ==  "");
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.render.ColorDefinition} and testing its setters
   */
  @Test
  public void testColorDefinitionSetters()
  {
    Color colorValue=new Color(0);
    ColorDefinition color=new ColorDefinition("",colorValue);
    assertTrue(color.getValue().getRed() == 0);
    assertTrue(color.getValue().getGreen() == 0);
    assertTrue(color.getValue().getBlue() == 0);
    assertTrue(color.getValue().getAlpha() == 255);
    color.setValue(new Color(234,color.getValue().getGreen(),color.getValue().getBlue(),color.getValue().getAlpha()));
    assertTrue(color.getValue().getRed() == 234);
    assertTrue(color.getValue().getGreen() == 0);
    assertTrue(color.getValue().getBlue() == 0);
    assertTrue(color.getValue().getAlpha() == 255);
    color.setValue(new Color(color.getValue().getRed(),145,color.getValue().getBlue(),color.getValue().getAlpha()));
    assertTrue(color.getValue().getRed() == 234);
    assertTrue(color.getValue().getGreen() == 145);
    assertTrue(color.getValue().getBlue() == 0);
    assertTrue(color.getValue().getAlpha() == 255);
    color.setValue(new Color(color.getValue().getRed(),color.getValue().getGreen(),25,color.getValue().getAlpha()));
    assertTrue(color.getValue().getRed() == 234);
    assertTrue(color.getValue().getGreen() == 145);
    assertTrue(color.getValue().getBlue() == 25);
    assertTrue(color.getValue().getAlpha() == 255);
    color.setValue(new Color(color.getValue().getRed(),color.getValue().getGreen(),color.getValue().getBlue(),5));
    assertTrue(color.getValue().getRed() == 234);
    assertTrue(color.getValue().getGreen() == 145);
    assertTrue(color.getValue().getBlue() == 25);
    assertTrue(color.getValue().getAlpha() == 5);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.render.ColorDefinition} and testing the use of Color.decode for hex color codes.
   */
  @Test
  public void testColorDefinitionSetColorValue()
  {
    Color colorValue=new Color(0);
    ColorDefinition color=new ColorDefinition("",colorValue);
    assertTrue(color.getValue().getRed()==0);
    assertTrue(color.getValue().getGreen()==0);
    assertTrue(color.getValue().getBlue()==0);
    assertTrue(color.getValue().getAlpha()==255);
    // valid color definitions
    color.setValue(Color.decode("#FFFFFFF"));
    assertTrue(color.getValue().getRed()==255);
    assertTrue(color.getValue().getGreen()==255);
    assertTrue(color.getValue().getBlue()==255);
    assertTrue(color.getValue().getAlpha()==255);
    color.setValue((Color.decode("#00000000")));
    assertTrue(color.getValue().getRed()==0);
    assertTrue(color.getValue().getGreen()==0);
    assertTrue(color.getValue().getBlue()==0);
    assertTrue(color.getValue().getAlpha()==255);
    color.setValue((Color.decode("#a93838")));
    assertTrue(color.getValue().getRed()==169);
    assertTrue(color.getValue().getGreen()==56);
    assertTrue(color.getValue().getBlue()==56);
    assertTrue(color.getValue().getAlpha()==255);
    color.setValue((Color.decode("#305bb3")));
    assertTrue(color.getValue().getRed()==48);
    assertTrue(color.getValue().getGreen()==91);
    assertTrue(color.getValue().getBlue()==179);
    assertTrue(color.getValue().getAlpha()==255);
    color.setValue((Color.decode("#FFFFFF")));
    assertTrue(color.getValue().getRed()==255);
    assertTrue(color.getValue().getGreen()==255);
    assertTrue(color.getValue().getBlue()==255);
    assertTrue(color.getValue().getAlpha()==255);
    color.setValue((Color.decode("#000000")));
    assertTrue(color.getValue().getRed()==0);
    assertTrue(color.getValue().getGreen()==0);
    assertTrue(color.getValue().getBlue()==0);
    assertTrue(color.getValue().getAlpha()==255);
    color.setValue((Color.decode("#113355")));
    assertTrue(color.getValue().getRed()==17);
    assertTrue(color.getValue().getGreen()==51);
    assertTrue(color.getValue().getBlue()==85);
    assertTrue(color.getValue().getAlpha()==255);
    color.setValue((Color.decode("#F34A28")));
    assertTrue(color.getValue().getRed()==243);
    assertTrue(color.getValue().getGreen()==74);
    assertTrue(color.getValue().getBlue()==40);
    assertTrue(color.getValue().getAlpha()==255);
    color.setValue((Color.decode("#892E36")));
    assertTrue(color.getValue().getRed()==137);
    assertTrue(color.getValue().getGreen()==46);
    assertTrue(color.getValue().getBlue()==54);
    assertTrue(color.getValue().getAlpha()==255);
    color.setValue((Color.decode("#ffffff")));
    assertTrue(color.getValue().getRed()==255);
    assertTrue(color.getValue().getGreen()==255);
    assertTrue(color.getValue().getBlue()==255);
    assertTrue(color.getValue().getAlpha()==255);
    color.setValue((Color.decode("#941784")));
    assertTrue(color.getValue().getRed()==148);
    assertTrue(color.getValue().getGreen()==23);
    assertTrue(color.getValue().getBlue()==132);
    assertTrue(color.getValue().getAlpha()==255);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.render.ColorDefinition} and testing the use of Color constructors
   */
  public void testColorDefinitionSetRGBA()
  {
    {
    ColorDefinition color=new ColorDefinition("");
    color.setValue(new Color(89,3,45,231));
    assertTrue(color.getValue().getRed()==89);
    assertTrue(color.getValue().getGreen()==3);
    assertTrue(color.getValue().getBlue()==45);
    assertTrue(color.getValue().getAlpha()==231);
    color.setValue(new Color(34,157,201,49));
    assertTrue(color.getValue().getRed()==34);
    assertTrue(color.getValue().getGreen()==157);
    assertTrue(color.getValue().getBlue()==201);
    assertTrue(color.getValue().getAlpha()==49);
    color.setValue(new Color(21,155,21));
    assertTrue(color.getValue().getRed()==21);
    assertTrue(color.getValue().getGreen()==155);
    assertTrue(color.getValue().getBlue()==21);
    assertTrue(color.getValue().getAlpha()==255);
    color.setValue(new Color(253,92,177));
    assertTrue(color.getValue().getRed()==253);
    assertTrue(color.getValue().getGreen()==92);
    assertTrue(color.getValue().getBlue()==177);
    assertTrue(color.getValue().getAlpha()==255);
  }
   }
}
