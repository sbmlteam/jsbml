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

import org.junit.Test;
import org.sbml.jsbml.ext.render.Image;


/**
 * @author Ibrahim Vazirabad
 * @since 1.0
 */
public class ImageTest {

  /**
   * Test method for {@link Image#getAllowsChildren()}.
   */
  @Test
  public void testGetAllowsChildren() {
    assertTrue(!new Image().getAllowsChildren());
  }


  /**
   * Test method for {@link Image#getChildCount()}.
   */
  @Test
  public void testGetChildCount() {
    Image image=new Image();
    assertEquals("childCountError",image.getChildCount(),0);
  }

  /**
   * Test method for {@link Image#isAbsoluteHeight()}.
   */
  @Test
  public void testIsAbsoluteHeight() {
    Image image=new Image();
    image.setAbsoluteHeight(false);
    assertTrue(!image.isAbsoluteHeight());
  }


  /**
   * Test method for {@link Image#isSetAbsoluteHeight()}.
   */
  @Test
  public void testIsSetAbsoluteHeight() {
    Image image=new Image();
    assertTrue(!image.isSetAbsoluteHeight());
    image.setAbsoluteHeight(true);
    assertTrue(image.isSetAbsoluteHeight());
  }


  /**
   * Test method for {@link Image#setAbsoluteHeight(boolean)}.
   */
  @Test
  public void testSetAbsoluteHeight() {
    Image image=new Image();
    image.setAbsoluteHeight(true);
    assertEquals(image.isAbsoluteHeight(),true);
  }


  /**
   * Test method for {@link Image#isAbsoluteWidth()}.
   */
  @Test
  public void testIsAbsoluteWidth() {
    Image image=new Image();
    image.setAbsoluteWidth(false);
    assertTrue(!image.isAbsoluteWidth());
  }


  /**
   * Test method for {@link Image#isSetAbsoluteWidth()}.
   */
  @Test
  public void testIsSetAbsoluteWidth() {
    Image image=new Image();
    assertTrue(!image.isSetAbsoluteWidth());
    image.setAbsoluteWidth(true);
    assertTrue(image.isSetAbsoluteWidth());
  }


  /**
   * Test method for {@link Image#setAbsoluteWidth(boolean)}.
   */
  @Test
  public void testSetAbsoluteWidth() {
    Image image=new Image();
    image.setAbsoluteWidth(true);
    assertEquals(image.isAbsoluteWidth(),true);
  }


  /**
   * Test method for {@link Image#isAbsoluteX()}.
   */
  @Test
  public void testIsAbsoluteX() {
    Image image=new Image();
    image.setAbsoluteX(true);
    assertTrue(image.isAbsoluteX());
  }


  /**
   * Test method for {@link Image#isSetAbsoluteX()}.
   */
  @Test
  public void testIsSetAbsoluteX() {
    Image image=new Image();
    image.setAbsoluteX(true);
    assertTrue(image.isSetAbsoluteX());
  }


  /**
   * Test method for {@link Image#setAbsoluteX(boolean)}.
   */
  @Test
  public void testSetAbsoluteX() {
    Image image=new Image();
    image.setAbsoluteX(true);
    assertEquals("AbsoluteVarError",true,image.isAbsoluteX());
  }


  /**
   * Test method for {@link Image#isAbsoluteY()}.
   */
  @Test
  public void testIsAbsoluteY() {
    Image image=new Image();
    image.setAbsoluteY(true);
    assertTrue(image.isAbsoluteY());
  }


  /**
   * Test method for {@link Image#isSetAbsoluteY()}.
   */
  @Test
  public void testIsSetAbsoluteY() {
    Image image=new Image();
    image.setAbsoluteY(true);
    assertTrue(image.isSetAbsoluteY());
  }


  /**
   * Test method for {@link Image#setAbsoluteY(boolean)}.
   */
  @Test
  public void testSetAbsoluteY() {
    Image image=new Image();
    image.setAbsoluteY(true);
    assertEquals("AbsoluteVarError",true,image.isAbsoluteY());
  }


  /**
   * Test method for {@link Image#isAbsoluteZ()}.
   */
  @Test
  public void testIsAbsoluteZ() {
    Image image=new Image();
    image.setAbsoluteZ(true);
    assertTrue(image.isAbsoluteZ());
  }


  /**
   * Test method for {@link Image#isSetAbsoluteZ()}.
   */
  @Test
  public void testIsSetAbsoluteZ() {
    Image image=new Image();
    image.setAbsoluteZ(true);
    assertTrue(image.isSetAbsoluteZ());
  }


  /**
   * Test method for {@link Image#setAbsoluteZ(boolean)}.
   */
  @Test
  public void testSetAbsoluteZ() {
    Image image=new Image();
    image.setAbsoluteZ(true);
    assertTrue(image.isAbsoluteZ());
  }


  /**
   * Test method for {@link Image#getHeight()}.
   */
  @Test
  public void testGetHeight() {
    Image image=new Image();
    double height=12d;
    image.setHeight(height);
    assertEquals(height,image.getHeight(),.00001d);
  }


  /**
   * Test method for {@link Image#isSetHeight()}.
   */
  @Test
  public void testIsSetHeight() {
    Image image=new Image();
    double height=12d;
    image.setHeight(height);
    assertTrue(image.isSetHeight());
  }


  /**
   * Test method for {@link Image#setHeight(double)}.
   */
  @Test
  public void testSetHeight() {
    Image image=new Image();
    double height=12d;
    image.setHeight(height);
    assertEquals(height,image.getHeight(),.00001d);
  }


  /**
   * Test method for {@link Image#getHref()}.
   */
  @Test
  public void testGetHref() {
    Image image=new Image();
    String hyperlink="http://sbml.org/Main_Page";
    image.setHref(hyperlink);
    assertEquals(hyperlink,image.getHref());
  }


  /**
   * Test method for {@link Image#isSetHref()}.
   */
  @Test
  public void testIsSetHref() {
    Image image=new Image();
    String hyperlink="http://sbml.org/Main_Page";
    image.setHref(hyperlink);
    assertTrue(image.isSetHref());
  }


  /**
   * Test method for {@link Image#setHref(java.lang.String)}.
   */
  @Test
  public void testSetHref() {
    Image image=new Image();
    String hyperlink="http://sbml.org/Main_Page";
    image.setHref(hyperlink);
    assertEquals(hyperlink,image.getHref());
  }


  /**
   * Test method for {@link Image#getWidth()}.
   */
  @Test
  public void testGetWidth() {
    Image image=new Image();
    double width=10d;
    image.setWidth(width);
    assertEquals(width,image.getWidth(),.00001d);
  }


  /**
   * Test method for {@link Image#isSetWidth()}.
   */
  @Test
  public void testIsSetWidth() {
    Image image=new Image();
    double width=132d;
    image.setWidth(width);
    assertTrue(image.isSetWidth());
  }


  /**
   * Test method for {@link Image#setWidth(double)}.
   */
  @Test
  public void testSetWidth() {
    Image image=new Image();
    double width=10d;
    image.setWidth(width);
    assertEquals(width,image.getWidth(),.00001d);
  }


  /**
   * Test method for {@link Image#getX()}.
   */
  @Test
  public void testGetX() {
    Image image=new Image();
    image.setX(0.02d);
    assertEquals(image.getX(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link Image#isSetX()}.
   */
  @Test
  public void testIsSetX() {
    Image image=new Image();
    image.setX(0.02d);
    assertTrue(image.isSetX());
  }


  /**
   * Test method for {@link Image#setX(double)}.
   */
  @Test
  public void testSetX() {
    Image image=new Image();
    image.setX(0.02d);
    assertEquals(image.getX(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link Image#getY()}.
   */
  @Test
  public void testGetY() {
    Image image=new Image();
    image.setY(0.02d);
    assertEquals(image.getY(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link Image#isSetY()}.
   */
  @Test
  public void testIsSetY() {
    Image image=new Image();
    image.setY(0.02d);
    assertTrue(image.isSetY());
  }


  /**
   * Test method for {@link Image#setY(double)}.
   */
  @Test
  public void testSetY() {
    Image image=new Image();
    image.setY(0.02d);
    assertEquals(image.getY(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link Image#getZ()}.
   */
  @Test
  public void testGetZ() {
    Image image=new Image();
    image.setZ(0.02d);
    assertEquals(image.getZ(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link Image#isSetZ()}.
   */
  @Test
  public void testIsSetZ() {
    Image image=new Image();
    image.setZ(0.02d);
    assertTrue(image.isSetZ());
  }


  /**
   * Test method for {@link Image#setZ(double)}.
   */
  @Test
  public void testSetZ() {
    Image image=new Image();
    image.setZ(0.02d);
    assertTrue(image.isSetZ());
  }
}
