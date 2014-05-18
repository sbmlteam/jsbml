/*
 * $Id:  ColorDefinitionTest.java 1733 May 15, 2014 10:40:13 PM yvazirabad $
 * $URL: https://svn.code.sf.net/p/jsbml/code/trunk/extensions/render/test/org/sbml/jsbml/ext/render/test/ColorDefinitionTest.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014  jointly by the following organizations:
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
 * @version $Rev 1733$
 * @since 1.0
 * @date May 15, 2014
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
}
