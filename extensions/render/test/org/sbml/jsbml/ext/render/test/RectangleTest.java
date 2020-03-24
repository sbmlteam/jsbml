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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sbml.jsbml.ext.render.Rectangle;
import org.sbml.jsbml.ext.render.RelAbsVector;


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
    rectangle.setRx(new RelAbsVector(d));
    assertEquals(rectangle.getRx().getAbsoluteValue(),d,0.0001d);
  }


  /**
   * Test method for {@link Rectangle#isSetRx()}.
   */
  public void testIsSetRx() {
    Rectangle rectangle=new Rectangle();
    double d=0.02d;
    rectangle.setRx(new RelAbsVector(d));
    assertTrue(rectangle.isSetRx());
  }


  /**
   * Test method for {@link Rectangle#setRx(double)}.
   */
  @Test
  public void testSetRx() {
    Rectangle rectangle=new Rectangle();
    double d=0.02d;
    rectangle.setRx(new RelAbsVector(d));
    assertEquals(rectangle.getRx(), new RelAbsVector(d));
  }


  /**
   * Test method for {@link Rectangle#getRy()}.
   */
  @Test
  public void testGetRy() {
    Rectangle rectangle=new Rectangle();
    double d=0.02d;
    rectangle.setRy(new RelAbsVector(d));
    assertEquals(rectangle.getRy().getAbsoluteValue(),d,0.0001d);
  }


  /**
   * Test method for {@link Rectangle#isSetRy()}.
   */
  @Test
  public void testIsSetRy() {
    Rectangle rectangle=new Rectangle();
    double d=0.02d;
    rectangle.setRy(new RelAbsVector(d));
    assertTrue(rectangle.isSetRy());
  }


  /**
   * Test method for {@link Rectangle#setRy(double)}.
   */
  @Test
  public void testSetRy() {
    Rectangle rectangle=new Rectangle();
    double d=0.02d;
    rectangle.setRy(new RelAbsVector(d));
    assertEquals(rectangle.getRy(), new RelAbsVector(d));
  }

  /**
   * Test method for {@link Rectangle#getX()}.
   */
  @Test
  public void testGetX() {
    Rectangle rectangle=new Rectangle();
    rectangle.setX(new RelAbsVector(0.02d));
    assertEquals(rectangle.getX().getAbsoluteValue(),0.02d,0.0001d);
  }


  /**
   * Test method for {@link Rectangle#isSetX()}.
   */
  @Test
  public void testIsSetX() {
    Rectangle rectangle=new Rectangle();
    rectangle.setX(new RelAbsVector(0.02d));
    assertTrue(rectangle.isSetX());
  }


  /**
   * Test method for {@link Rectangle#setX(double)}.
   */
  @Test
  public void testSetX() {
    Rectangle rectangle=new Rectangle();
    rectangle.setX(new RelAbsVector(0.02d));
    assertEquals(rectangle.getX().getAbsoluteValue(),0.02d,0.0001d);
  }


  /**
   * Test method for {@link Rectangle#getY()}.
   */
  @Test
  public void testGetY() {
    Rectangle rectangle=new Rectangle();
    rectangle.setY(new RelAbsVector(0.02d));
    assertEquals(rectangle.getY().getAbsoluteValue(),0.02d,0.0001d);
  }


  /**
   * Test method for {@link Rectangle#isSetY()}.
   */
  @Test
  public void testIsSetY() {
    Rectangle rectangle=new Rectangle();
    rectangle.setY(new RelAbsVector(0.02d));
    assertTrue(rectangle.isSetY());
  }


  /**
   * Test method for {@link Rectangle#setY(double)}.
   */
  @Test
  public void testSetY() {
    Rectangle rectangle=new Rectangle();
    rectangle.setY(new RelAbsVector(0.02d));
    assertEquals(rectangle.getY().getAbsoluteValue(),0.02d,0.0001d);
  }


  /**
   * Test method for {@link Rectangle#getZ()}.
   */
  @Test
  public void testGetZ() {
    Rectangle rectangle=new Rectangle();
    rectangle.setZ(new RelAbsVector(0.02d));
    assertEquals(rectangle.getZ().getAbsoluteValue(),0.02d,0.0001d);
  }


  /**
   * Test method for {@link Rectangle#isSetZ()}.
   */
  @Test
  public void testIsSetZ() {
    Rectangle rectangle=new Rectangle();
    rectangle.setZ(new RelAbsVector(0.02d));
    assertTrue(rectangle.isSetZ());
  }


  /**
   * Test method for {@link Rectangle#setZ(double)}.
   */
  @Test
  public void testSetZ() {
    Rectangle rectangle=new Rectangle();
    rectangle.setZ(new RelAbsVector(0.02d));
    assertEquals(rectangle.getZ().getAbsoluteValue(),0.02d,0.0001d);
  }


  /**
   * Test method for {@link Rectangle#getHeight()}.
   */
  @Test
  public void testGetHeight() {
    Rectangle rectangle=new Rectangle();
    double height=12d;
    rectangle.setHeight(new RelAbsVector(height));
    assertEquals(height,rectangle.getHeight().getAbsoluteValue(),.00001d);
  }


  /**
   * Test method for {@link Rectangle#isSetHeight()}.
   */
  @Test
  public void testIsSetHeight() {
    Rectangle rectangle=new Rectangle();
    double height=12d;
    rectangle.setHeight(new RelAbsVector(height));
    assertTrue(rectangle.isSetHeight());
  }


  /**
   * Test method for {@link Rectangle#setHeight(double)}.
   */
  @Test
  public void testSetHeight() {
    Rectangle rectangle=new Rectangle();
    double height=12d;
    rectangle.setHeight(new RelAbsVector(height));
    assertEquals(height,rectangle.getHeight().getAbsoluteValue(),.00001d);
  }


  /**
   * Test method for {@link Rectangle#getWidth()}.
   */
  @Test
  public void testGetWidth() {
    Rectangle rectangle=new Rectangle();
    double width=10d;
    rectangle.setWidth(new RelAbsVector(width));
    assertEquals(width,rectangle.getWidth().getAbsoluteValue(),.00001d);
  }


  /**
   * Test method for {@link Rectangle#isSetWidth()}.
   */
  @Test
  public void testIsSetWidth() {
    Rectangle rectangle=new Rectangle();
    double width=132d;
    rectangle.setWidth(new RelAbsVector(width));
    assertTrue(rectangle.isSetWidth());
  }


  /**
   * Test method for {@link Rectangle#setWidth(double)}.
   */
  @Test
  public void testSetWidth() {
    Rectangle rectangle=new Rectangle();
    double width=10d;
    rectangle.setWidth(new RelAbsVector(width));
    assertEquals(width,rectangle.getWidth().getAbsoluteValue(),.00001d);
  }
  
  @Test
  public void testGetSetRatio() {
    Rectangle rectangle = new Rectangle();
    rectangle.setRatio(2.87d);
    assertEquals(rectangle.getRatio(), 2.87d, 1e-7);
  }
  
  @Test
  public void testIsSetRatio() {
    Rectangle rectangle = new Rectangle();
    assertFalse(rectangle.isSetRatio());
    rectangle.setRatio(2.87d);
    assertTrue(rectangle.isSetRatio());
  }
  
  @Test
  public void testUnsetRatio() {
    Rectangle rectangle = new Rectangle();
    assertFalse(rectangle.isSetRatio());
    rectangle.setRatio(2.87d);
    assertTrue(rectangle.isSetRatio());
    rectangle.unsetRatio();
    assertFalse(rectangle.isSetRatio());
  }
}
