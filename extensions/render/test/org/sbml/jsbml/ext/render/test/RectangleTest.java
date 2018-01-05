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
import org.sbml.jsbml.ext.render.Rectangle;


/**
 * @author Ibrahim Vazirabad
 * @since 1.0
 */
public class RectangleTest {

  /**
   * Test method for {@link Rectangle#getChildCount()}.
   */
  @Test
  public void testGetChildCount() {
    Rectangle rectangle=new Rectangle();
    assertEquals("childCountError",0,rectangle.getChildCount());
  }

  /**
   * Test method for {@link Rectangle#getRx()}.
   */
  @Test
  public void testGetRx() {
    Rectangle rectangle=new Rectangle();
    double d=0.02d;
    rectangle.setRx(d);
    assertEquals(rectangle.getRx(),d,0.0001d);
  }


  /**
   * Test method for {@link Rectangle#isSetRx()}.
   */
  public void testIsSetRx() {
    Rectangle rectangle=new Rectangle();
    double d=0.02d;
    rectangle.setRx(d);
    assertTrue(rectangle.isSetRx());
  }


  /**
   * Test method for {@link Rectangle#setRx(double)}.
   */
  @Test
  public void testSetRx() {
    Rectangle rectangle=new Rectangle();
    double d=0.02d;
    rectangle.setRx(d);
    assertTrue(Double.compare(rectangle.getRx(),0.02d)==0);
  }


  /**
   * Test method for {@link Rectangle#getRy()}.
   */
  @Test
  public void testGetRy() {
    Rectangle rectangle=new Rectangle();
    double d=0.02d;
    rectangle.setRy(d);
    assertEquals(rectangle.getRy(),d,0.0001d);
  }


  /**
   * Test method for {@link Rectangle#isSetRy()}.
   */
  @Test
  public void testIsSetRy() {
    Rectangle rectangle=new Rectangle();
    double d=0.02d;
    rectangle.setRy(d);
    assertTrue(rectangle.isSetRy());
  }


  /**
   * Test method for {@link Rectangle#setRy(double)}.
   */
  @Test
  public void testSetRy() {
    Rectangle rectangle=new Rectangle();
    double d=0.02d;
    rectangle.setRy(d);
    assertTrue(Double.compare(rectangle.getRy(),0.02d)==0);
  }


  /**
   * Test method for {@link Rectangle#isSetAbsoluteX()}.
   */
  @Test
  public void testIsSetAbsoluteX() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteX(true);
    assertTrue(rectangle.isSetAbsoluteX());
  }


  /**
   * Test method for {@link Rectangle#setAbsoluteX(boolean)}.
   */
  @Test
  public void testSetAbsoluteX() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteX(true);
    assertEquals("AbsoluteVarError",true,rectangle.isAbsoluteX());
  }


  /**
   * Test method for {@link Rectangle#isAbsoluteY()}.
   */
  @Test
  public void testIsAbsoluteY() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteY(true);
    assertTrue(rectangle.isAbsoluteY());
  }


  /**
   * Test method for {@link Rectangle#isSetAbsoluteY()}.
   */
  @Test
  public void testIsSetAbsoluteY() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteY(true);
    assertTrue(rectangle.isSetAbsoluteY());
  }


  /**
   * Test method for {@link Rectangle#setAbsoluteY(boolean)}.
   */
  @Test
  public void testSetAbsoluteY() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteY(true);
    assertEquals("AbsoluteVarError",true,rectangle.isAbsoluteY());
  }


  /**
   * Test method for {@link Rectangle#isAbsoluteZ()}.
   */
  @Test
  public void testIsAbsoluteZ() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteZ(true);
    assertTrue(rectangle.isAbsoluteZ());
  }


  /**
   * Test method for {@link Rectangle#isSetAbsoluteZ()}.
   */
  @Test
  public void testIsSetAbsoluteZ() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteZ(true);
    assertTrue(rectangle.isSetAbsoluteZ());
  }


  /**
   * Test method for {@link Rectangle#setAbsoluteZ(boolean)}.
   */
  @Test
  public void testSetAbsoluteZ() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteZ(true);
    assertTrue(rectangle.isAbsoluteZ());
  }


  /**
   * Test method for {@link Rectangle#isAbsoluteRx()}.
   */
  @Test
  public void testIsAbsoluteRx() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteRx(true);
    assertTrue(rectangle.isAbsoluteRx());
  }


  /**
   * Test method for {@link Rectangle#isSetAbsoluteRx()}.
   */
  @Test
  public void testIsSetAbsoluteRx() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteRx(false);
    assertTrue(rectangle.isSetAbsoluteRx());
  }


  /**
   * Test method for {@link Rectangle#setAbsoluteRx(boolean)}.
   */
  @Test
  public void testSetAbsoluteRx() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteRx(true);
    assertTrue(rectangle.isAbsoluteRx());
  }


  /**
   * Test method for {@link Rectangle#isAbsoluteRy()}.
   */
  @Test
  public void testIsAbsoluteRy() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteRy(true);
    assertTrue(rectangle.isAbsoluteRy());
  }


  /**
   * Test method for {@link Rectangle#isSetAbsoluteRy()}.
   */
  @Test
  public void testIsSetAbsoluteRy() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteRy(false);
    assertTrue(rectangle.isSetAbsoluteRy());
  }


  /**
   * Test method for {@link Rectangle#setAbsoluteRy(boolean)}.
   */
  @Test
  public void testSetAbsoluteRy() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteRy(true);
    assertTrue(rectangle.isAbsoluteRy());
  }


  /**
   * Test method for {@link Rectangle#isSetAbsoluteHeight()}.
   */
  @Test
  public void testIsSetAbsoluteHeight() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteHeight(false);
    assertTrue(rectangle.isSetAbsoluteHeight());
  }


  /**
   * Test method for {@link Rectangle#setAbsoluteHeight(boolean)}.
   */
  @Test
  public void testSetAbsoluteHeight() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteHeight(true);
    assertTrue(rectangle.isAbsoluteHeight());
  }


  /**
   * Test method for {@link Rectangle#isAbsoluteWidth()}.
   */
  @Test
  public void testIsAbsoluteWidth() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteWidth(false);
    assertTrue(!rectangle.isAbsoluteWidth());
  }


  /**
   * Test method for {@link Rectangle#isSetAbsoluteWidth()}.
   */
  @Test
  public void testIsSetAbsoluteWidth() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteWidth(false);
    assertTrue(rectangle.isSetAbsoluteWidth());
  }


  /**
   * Test method for {@link Rectangle#setAbsoluteWidth(boolean)}.
   */
  @Test
  public void testSetAbsoluteWidth() {
    Rectangle rectangle=new Rectangle();
    rectangle.setAbsoluteWidth(false);
    assertTrue(rectangle.isSetAbsoluteWidth());
  }


  /**
   * Test method for {@link Rectangle#getX()}.
   */
  @Test
  public void testGetX() {
    Rectangle rectangle=new Rectangle();
    rectangle.setX(0.02d);
    assertEquals(rectangle.getX(),0.02d,0.0001d);
  }


  /**
   * Test method for {@link Rectangle#isSetX()}.
   */
  @Test
  public void testIsSetX() {
    Rectangle rectangle=new Rectangle();
    rectangle.setX(0.02d);
    assertTrue(rectangle.isSetX());
  }


  /**
   * Test method for {@link Rectangle#setX(double)}.
   */
  @Test
  public void testSetX() {
    Rectangle rectangle=new Rectangle();
    rectangle.setX(0.02d);
    assertEquals(rectangle.getX(),0.02d,0.0001d);
  }


  /**
   * Test method for {@link Rectangle#getY()}.
   */
  @Test
  public void testGetY() {
    Rectangle rectangle=new Rectangle();
    rectangle.setY(0.02d);
    assertEquals(rectangle.getY(),0.02d,0.0001d);
  }


  /**
   * Test method for {@link Rectangle#isSetY()}.
   */
  @Test
  public void testIsSetY() {
    Rectangle rectangle=new Rectangle();
    rectangle.setY(0.02d);
    assertTrue(rectangle.isSetY());
  }


  /**
   * Test method for {@link Rectangle#setY(double)}.
   */
  @Test
  public void testSetY() {
    Rectangle rectangle=new Rectangle();
    rectangle.setY(0.02d);
    assertEquals(rectangle.getY(),0.02d,0.0001d);
  }


  /**
   * Test method for {@link Rectangle#getZ()}.
   */
  @Test
  public void testGetZ() {
    Rectangle rectangle=new Rectangle();
    rectangle.setZ(0.02d);
    assertEquals(rectangle.getZ(),0.02d,0.0001d);
  }


  /**
   * Test method for {@link Rectangle#isSetZ()}.
   */
  @Test
  public void testIsSetZ() {
    Rectangle rectangle=new Rectangle();
    rectangle.setZ(0.02d);
    assertTrue(rectangle.isSetZ());
  }


  /**
   * Test method for {@link Rectangle#setZ(double)}.
   */
  @Test
  public void testSetZ() {
    Rectangle rectangle=new Rectangle();
    rectangle.setZ(0.02d);
    assertEquals(rectangle.getZ(),0.02d,0.0001d);
  }


  /**
   * Test method for {@link Rectangle#getHeight()}.
   */
  @Test
  public void testGetHeight() {
    Rectangle rectangle=new Rectangle();
    double height=12d;
    rectangle.setHeight(height);
    assertEquals(height,rectangle.getHeight(),.00001d);
  }


  /**
   * Test method for {@link Rectangle#isSetHeight()}.
   */
  @Test
  public void testIsSetHeight() {
    Rectangle rectangle=new Rectangle();
    double height=12d;
    rectangle.setHeight(height);
    assertTrue(rectangle.isSetHeight());
  }


  /**
   * Test method for {@link Rectangle#setHeight(double)}.
   */
  @Test
  public void testSetHeight() {
    Rectangle rectangle=new Rectangle();
    double height=12d;
    rectangle.setHeight(height);
    assertEquals(height,rectangle.getHeight(),.00001d);
  }


  /**
   * Test method for {@link Rectangle#getWidth()}.
   */
  @Test
  public void testGetWidth() {
    Rectangle rectangle=new Rectangle();
    double width=10d;
    rectangle.setWidth(width);
    assertEquals(width,rectangle.getWidth(),.00001d);
  }


  /**
   * Test method for {@link Rectangle#isSetWidth()}.
   */
  @Test
  public void testIsSetWidth() {
    Rectangle rectangle=new Rectangle();
    double width=132d;
    rectangle.setWidth(width);
    assertTrue(rectangle.isSetWidth());
  }


  /**
   * Test method for {@link Rectangle#setWidth(double)}.
   */
  @Test
  public void testSetWidth() {
    Rectangle rectangle=new Rectangle();
    double width=10d;
    rectangle.setWidth(width);
    assertEquals(width,rectangle.getWidth(),.00001d);
  }
}
