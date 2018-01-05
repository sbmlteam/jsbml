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
import org.sbml.jsbml.ext.render.RenderCubicBezier;


/**
 * @author Ibrahim Vazirabad
 * @version 1771
 * @since 1.0
 */
public class RenderCubicBezierTest {

  /**
   * Test method for {@link RenderCubicBezier#getAllowsChildren()}.
   */
  @Test
  public void testGetAllowsChildren() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    assertTrue(!bezier.getAllowsChildren());
  }


  /**
   * Test method for {@link RenderCubicBezier#getChildCount()}.
   */
  @Test
  public void testGetChildCount() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    assertEquals("BezierError",0,bezier.getChildCount());
  }

  /**
   * Test method for {@link RenderCubicBezier#getX1()}.
   */
  @Test
  public void testGetX1() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    assertTrue(!bezier.isSetX1());
    bezier.setX1(0.02d);
    assertEquals(bezier.getX1(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link RenderCubicBezier#getX2()}.
   */
  @Test
  public void testGetX2() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    assertTrue(!bezier.isSetX2());
    bezier.setX2(0.02d);
    assertEquals(bezier.getX2(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link RenderCubicBezier#getY1()}.
   */
  @Test
  public void testGetY1() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    assertTrue(!bezier.isSetY1());
    bezier.setY1(0.02d);
    assertEquals(bezier.getY1(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link RenderCubicBezier#getY2()}.
   */
  @Test
  public void testGetY2() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    assertTrue(!bezier.isSetY2());
    bezier.setY2(0.02d);
    assertEquals(bezier.getY2(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link RenderCubicBezier#getZ1()}.
   */
  @Test
  public void testGetZ1() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setZ1(0.02d);
    assertTrue(bezier.isSetZ1());
    assertEquals(bezier.getZ1(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link RenderCubicBezier#getZ2()}.
   */
  @Test
  public void testGetZ2() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setZ2(0.02d);
    assertTrue(bezier.isSetZ2());
    assertEquals(bezier.getZ2(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link RenderCubicBezier#isAbsoluteX1()}.
   */
  @Test
  public void testIsAbsoluteX1() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setAbsoluteX1(false);
    assertTrue(!bezier.isAbsoluteX1());
    bezier.setAbsoluteX1(true);
    assertTrue(bezier.isAbsoluteX1());
  }


  /**
   * Test method for {@link RenderCubicBezier#isAbsoluteX2()}.
   */
  @Test
  public void testIsAbsoluteX2() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setAbsoluteX2(false);
    assertTrue(!bezier.isAbsoluteX2());
    bezier.setAbsoluteX2(true);
    assertTrue(bezier.isAbsoluteX2());
  }


  /**
   * Test method for {@link RenderCubicBezier#isAbsoluteY1()}.
   */
  @Test
  public void testIsAbsoluteY1() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setAbsoluteY1(false);
    assertTrue(!bezier.isAbsoluteY1());
    bezier.setAbsoluteY1(true);
    assertTrue(bezier.isAbsoluteY1());
  }


  /**
   * Test method for {@link RenderCubicBezier#isAbsoluteY2()}.
   */
  @Test
  public void testIsAbsoluteY2() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setAbsoluteY2(false);
    assertTrue(!bezier.isAbsoluteY2());
    bezier.setAbsoluteY2(true);
    assertTrue(bezier.isAbsoluteY2());
  }


  /**
   * Test method for {@link RenderCubicBezier#isAbsoluteZ1()}.
   */
  @Test
  public void testIsAbsoluteZ1() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setAbsoluteZ1(false);
    assertTrue(!bezier.isAbsoluteZ1());
    bezier.setAbsoluteZ1(true);
    assertTrue(bezier.isAbsoluteZ1());
  }


  /**
   * Test method for {@link RenderCubicBezier#isAbsoluteZ2()}.
   */
  @Test
  public void testIsAbsoluteZ2() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setAbsoluteZ2(false);
    assertTrue(!bezier.isAbsoluteZ2());
    bezier.setAbsoluteZ2(true);
    assertTrue(bezier.isAbsoluteZ2());
  }


  /**
   * Test method for {@link RenderCubicBezier#isSetAbsoluteX1()}.
   */
  @Test
  public void testIsSetAbsoluteX1() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setAbsoluteX1(false);
    assertTrue(bezier.isSetAbsoluteX1());
  }


  /**
   * Test method for {@link RenderCubicBezier#isSetAbsoluteX2()}.
   */
  @Test
  public void testIsSetAbsoluteX2() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setAbsoluteX2(false);
    assertTrue(bezier.isSetAbsoluteX2());
  }


  /**
   * Test method for {@link RenderCubicBezier#isSetAbsoluteY1()}.
   */
  @Test
  public void testIsSetAbsoluteY1() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setAbsoluteY1(false);
    assertTrue(bezier.isSetAbsoluteY1());
  }


  /**
   * Test method for {@link RenderCubicBezier#isSetAbsoluteY2()}.
   */
  @Test
  public void testIsSetAbsoluteY2() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setAbsoluteY2(false);
    assertTrue(bezier.isSetAbsoluteY2());
  }


  /**
   * Test method for {@link RenderCubicBezier#isSetAbsoluteZ1()}.
   */
  @Test
  public void testIsSetAbsoluteZ1() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setAbsoluteZ1(false);
    assertTrue(bezier.isSetAbsoluteZ1());
  }


  /**
   * Test method for {@link RenderCubicBezier#isSetAbsoluteZ2()}.
   */
  @Test
  public void testIsSetAbsoluteZ2() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setAbsoluteZ2(false);
    assertTrue(bezier.isSetAbsoluteZ2());
  }


  /**
   * Test method for {@link RenderCubicBezier#isSetX1()}.
   */
  @Test
  public void testIsSetX1() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    double d=0.02d;
    bezier.setX1(d);
    assertTrue(bezier.isSetX1());
  }


  /**
   * Test method for {@link RenderCubicBezier#isSetX2()}.
   */
  @Test
  public void testIsSetX2() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    double d=0.02d;
    bezier.setX2(d);
    assertTrue(bezier.isSetX2());
  }


  /**
   * Test method for {@link RenderCubicBezier#isSetY1()}.
   */
  @Test
  public void testIsSetY1() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    double d=0.02d;
    bezier.setY1(d);
    assertTrue(bezier.isSetY1());
  }


  /**
   * Test method for {@link RenderCubicBezier#isSetY2()}.
   */
  @Test
  public void testIsSetY2() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    double d=0.02d;
    bezier.setY2(d);
    assertTrue(bezier.isSetY2());
  }


  /**
   * Test method for {@link RenderCubicBezier#isSetZ1()}.
   */
  @Test
  public void testIsSetZ1() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    double d=0.02d;
    bezier.setZ1(d);
    assertTrue(bezier.isSetZ1());
  }


  /**
   * Test method for {@link RenderCubicBezier#isSetZ2()}.
   */
  @Test
  public void testIsSetZ2() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    double d=0.02d;
    bezier.setZ2(d);
    assertTrue(bezier.isSetZ2());
  }


  /**
   * Test method for {@link RenderCubicBezier#setAbsoluteX1(boolean)}.
   */
  @Test
  public void testSetAbsoluteX1() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setAbsoluteX1(false);
    assertTrue(!bezier.isAbsoluteX1());
  }


  /**
   * Test method for {@link RenderCubicBezier#setAbsoluteX2(boolean)}.
   */
  @Test
  public void testSetAbsoluteX2() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setAbsoluteX2(false);
    assertTrue(!bezier.isAbsoluteX2());
  }


  /**
   * Test method for {@link RenderCubicBezier#setAbsoluteY1(boolean)}.
   */
  @Test
  public void testSetAbsoluteY1() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setAbsoluteY1(false);
    assertTrue(!bezier.isAbsoluteY1());
  }


  /**
   * Test method for {@link RenderCubicBezier#setAbsoluteY2(boolean)}.
   */
  @Test
  public void testSetAbsoluteY2() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setAbsoluteY2(false);
    assertTrue(!bezier.isAbsoluteY2());
  }


  /**
   * Test method for {@link RenderCubicBezier#setAbsoluteZ1(boolean)}.
   */
  @Test
  public void testSetAbsoluteZ1() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setAbsoluteZ1(false);
    assertTrue(!bezier.isAbsoluteZ1());
  }


  /**
   * Test method for {@link RenderCubicBezier#setAbsoluteZ2(boolean)}.
   */
  @Test
  public void testSetAbsoluteZ2() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setAbsoluteZ2(false);
    assertTrue(!bezier.isAbsoluteZ2());
  }


  /**
   * Test method for {@link RenderCubicBezier#setX1(double)}.
   */
  @Test
  public void testSetX1() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    assertTrue(!bezier.isSetX1());
    bezier.setX1(0.02d);
    assertEquals(bezier.getX1(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link RenderCubicBezier#setX2(double)}.
   */
  @Test
  public void testSetX2() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    assertTrue(!bezier.isSetX2());
    bezier.setX2(0.02d);
    assertEquals(bezier.getX2(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link RenderCubicBezier#setY1(double)}.
   */
  @Test
  public void testSetY1() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    assertTrue(!bezier.isSetY1());
    bezier.setY1(0.02d);
    assertEquals(bezier.getY1(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link RenderCubicBezier#setY2(double)}.
   */
  @Test
  public void testSetY2() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    assertTrue(!bezier.isSetY2());
    bezier.setY2(0.02d);
    assertEquals(bezier.getY2(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link RenderCubicBezier#setZ1(double)}.
   */
  @Test
  public void testSetZ1() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setZ1(0.02d);
    assertTrue(bezier.isSetZ1());
    assertEquals(bezier.getZ1(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link RenderCubicBezier#setZ2(double)}.
   */
  @Test
  public void testSetZ2() {
    RenderCubicBezier bezier=new RenderCubicBezier();
    bezier.setZ2(0.02d);
    assertTrue(bezier.isSetZ2());
    assertEquals(bezier.getZ2(),0.02d,0.00000001d);
  }
}
