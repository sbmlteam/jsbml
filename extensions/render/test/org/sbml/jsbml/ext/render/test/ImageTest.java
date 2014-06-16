/*
 * $Id$
 * $URL$
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

import org.junit.Test;
import org.sbml.jsbml.ext.render.Image;


/**
 * @author Ibrahim Vazirabad
 * @version $Rev$
 * @since 1.0
 * @date Jun 15, 2014
 */
public class ImageTest {

  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#getAllowsChildren()}.
   */
  @Test
  public void testGetAllowsChildren() {
    assertTrue(!new Image().getAllowsChildren());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#getChildCount()}.
   */
  @Test
  public void testGetChildCount() {
    Image image=new Image();
    assertEquals("childCountError",image.getChildCount(),0);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#isAbsoluteHeight()}.
   */
  @Test
  public void testIsAbsoluteHeight() {
    Image image=new Image();
    image.setAbsoluteHeight(false);
    assertTrue(!image.isAbsoluteHeight());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#isSetAbsoluteHeight()}.
   */
  @Test
  public void testIsSetAbsoluteHeight() {
    Image image=new Image();
    assertTrue(!image.isSetAbsoluteHeight());
    image.setAbsoluteHeight(true);
    assertTrue(image.isSetAbsoluteHeight());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#setAbsoluteHeight(java.lang.Boolean)}.
   */
  @Test
  public void testSetAbsoluteHeight() {
    Image image=new Image();
    image.setAbsoluteHeight(true);
    assertEquals(image.isAbsoluteHeight(),true);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#isAbsoluteWidth()}.
   */
  @Test
  public void testIsAbsoluteWidth() {
    Image image=new Image();
    image.setAbsoluteWidth(false);
    assertTrue(!image.isAbsoluteWidth());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#isSetAbsoluteWidth()}.
   */
  @Test
  public void testIsSetAbsoluteWidth() {
    Image image=new Image();
    assertTrue(!image.isSetAbsoluteWidth());
    image.setAbsoluteWidth(true);
    assertTrue(image.isSetAbsoluteWidth());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#setAbsoluteWidth(java.lang.Boolean)}.
   */
  @Test
  public void testSetAbsoluteWidth() {
    Image image=new Image();
    image.setAbsoluteWidth(true);
    assertEquals(image.isAbsoluteWidth(),true);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#isAbsoluteX()}.
   */
  @Test
  public void testIsAbsoluteX() {
    Image image=new Image();
    image.setAbsoluteX(true);
    assertTrue(image.isAbsoluteX());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#isSetAbsoluteX()}.
   */
  @Test
  public void testIsSetAbsoluteX() {
    Image image=new Image();
    image.setAbsoluteX(true);
    assertTrue(image.isSetAbsoluteX());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#setAbsoluteX(java.lang.Boolean)}.
   */
  @Test
  public void testSetAbsoluteX() {
    Image image=new Image();
    image.setAbsoluteX(true);
    assertEquals("AbsoluteVarError",true,image.isAbsoluteX());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#isAbsoluteY()}.
   */
  @Test
  public void testIsAbsoluteY() {
    Image image=new Image();
    image.setAbsoluteY(true);
    assertTrue(image.isAbsoluteY());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#isSetAbsoluteY()}.
   */
  @Test
  public void testIsSetAbsoluteY() {
    Image image=new Image();
    image.setAbsoluteY(true);
    assertTrue(image.isSetAbsoluteY());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#setAbsoluteY(java.lang.Boolean)}.
   */
  @Test
  public void testSetAbsoluteY() {
    Image image=new Image();
    image.setAbsoluteY(true);
    assertEquals("AbsoluteVarError",true,image.isAbsoluteY());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#isAbsoluteZ()}.
   */
  @Test
  public void testIsAbsoluteZ() {
    Image image=new Image();
    image.setAbsoluteZ(true);
    assertTrue(image.isAbsoluteZ());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#isSetAbsoluteZ()}.
   */
  @Test
  public void testIsSetAbsoluteZ() {
    Image image=new Image();
    image.setAbsoluteZ(true);
    assertTrue(image.isSetAbsoluteZ());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#setAbsoluteZ(java.lang.Boolean)}.
   */
  @Test
  public void testSetAbsoluteZ() {
    Image image=new Image();
    image.setAbsoluteZ(true);
    assertTrue(image.isAbsoluteZ());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#getHeight()}.
   */
  @Test
  public void testGetHeight() {
    Image image=new Image();
    double height=12d;
    image.setHeight(height);
    assertEquals(height,image.getHeight(),.00001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#isSetHeight()}.
   */
  @Test
  public void testIsSetHeight() {
    Image image=new Image();
    double height=12d;
    image.setHeight(height);
    assertTrue(image.isSetHeight());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#setHeight(java.lang.Double)}.
   */
  @Test
  public void testSetHeight() {
    Image image=new Image();
    double height=12d;
    image.setHeight(height);
    assertEquals(height,image.getHeight(),.00001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#getHref()}.
   */
  @Test
  public void testGetHref() {
    Image image=new Image();
    String hyperlink="http://sbml.org/Main_Page";
    image.setHref(hyperlink);
    assertEquals(hyperlink,image.getHref());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#isSetHref()}.
   */
  @Test
  public void testIsSetHref() {
    Image image=new Image();
    String hyperlink="http://sbml.org/Main_Page";
    image.setHref(hyperlink);
    assertTrue(image.isSetHref());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#setHref(java.lang.String)}.
   */
  @Test
  public void testSetHref() {
    Image image=new Image();
    String hyperlink="http://sbml.org/Main_Page";
    image.setHref(hyperlink);
    assertEquals(hyperlink,image.getHref());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#getWidth()}.
   */
  @Test
  public void testGetWidth() {
    Image image=new Image();
    double width=10d;
    image.setWidth(width);
    assertEquals(width,image.getWidth(),.00001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#isSetWidth()}.
   */
  @Test
  public void testIsSetWidth() {
    Image image=new Image();
    double width=132d;
    image.setWidth(width);
    assertTrue(image.isSetWidth());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#setWidth(java.lang.Double)}.
   */
  @Test
  public void testSetWidth() {
    Image image=new Image();
    double width=10d;
    image.setWidth(width);
    assertEquals(width,image.getWidth(),.00001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#getX()}.
   */
  @Test
  public void testGetX() {
    Image image=new Image();
    image.setX(0.02d);
    assertEquals(image.getX(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#isSetX()}.
   */
  @Test
  public void testIsSetX() {
    Image image=new Image();
    image.setX(0.02d);
    assertTrue(image.isSetX());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#setX(java.lang.Double)}.
   */
  @Test
  public void testSetX() {
    Image image=new Image();
    image.setX(0.02d);
    assertEquals(image.getX(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#getY()}.
   */
  @Test
  public void testGetY() {
    Image image=new Image();
    image.setY(0.02d);
    assertEquals(image.getY(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#isSetY()}.
   */
  @Test
  public void testIsSetY() {
    Image image=new Image();
    image.setY(0.02d);
    assertTrue(image.isSetY());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#setY(java.lang.Double)}.
   */
  @Test
  public void testSetY() {
    Image image=new Image();
    image.setY(0.02d);
    assertEquals(image.getY(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#getZ()}.
   */
  @Test
  public void testGetZ() {
    Image image=new Image();
    image.setZ(0.02d);
    assertEquals(image.getZ(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#isSetZ()}.
   */
  @Test
  public void testIsSetZ() {
    Image image=new Image();
    image.setZ(0.02d);
    assertTrue(image.isSetZ());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Image#setZ(java.lang.Double)}.
   */
  @Test
  public void testSetZ() {
    Image image=new Image();
    image.setZ(0.02d);
    assertTrue(image.isSetZ());
  }
}
