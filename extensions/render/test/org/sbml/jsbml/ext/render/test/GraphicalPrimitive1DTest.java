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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sbml.jsbml.ext.render.GraphicalPrimitive1D;


/**
 * @author Ibrahim Vazirabad
 * @version 1771
 * @since 1.0
 */
public class GraphicalPrimitive1DTest {

  /**
   * Test method for {@link GraphicalPrimitive1D#isSetStrokeDashArray()}.
   */
  @Test
  public void testIsSetStrokeDashArray() {
    GraphicalPrimitive1D gp1D=new GraphicalPrimitive1D();
    assertTrue(!gp1D.isSetStrokeDashArray());
    Short[] tmpV=new Short[10];
    tmpV[0]=3;
    tmpV[1]=7;
    tmpV[2]=2;
    tmpV[3]=9;
    gp1D.setStrokeDashArray(tmpV);
    assertTrue(gp1D.getStrokeDashArray().get(0) == 3);
    assertTrue(gp1D.getStrokeDashArray().get(1) == 7);
    assertTrue(gp1D.getStrokeDashArray().get(2) == 2);
    assertTrue(gp1D.getStrokeDashArray().get(3) == 9);
    assertTrue(gp1D.isSetStrokeDashArray());
  }


  /**
   * Test method for {@link GraphicalPrimitive1D#setStrokeDashArray(java.lang.Short[])}.
   */
  @Test
  public void testSetStrokeDashArray() {
    GraphicalPrimitive1D gp1D=new GraphicalPrimitive1D();
    Short[] tmpV=new Short[10];
    tmpV[0] = 3;
    tmpV[1] = 7;
    tmpV[2] = 2;
    tmpV[3] = 9;
    gp1D.setStrokeDashArray(tmpV);
    assertArrayEquals(tmpV, gp1D.getStrokeDashArray().toArray(new Short[0]));
  }


  /**
   * Test method for {@link GraphicalPrimitive1D#getStroke()}.
   */
  @Test
  public void testGetStroke() {
    GraphicalPrimitive1D gp1D=new GraphicalPrimitive1D();
    assertTrue(!gp1D.isSetStroke());
    String stroke="stroke";
    gp1D.setStroke(stroke);
    assertEquals("StrokeError",stroke,gp1D.getStroke());
  }


  /**
   * Test method for {@link GraphicalPrimitive1D#getStrokeWidth()}.
   */
  @Test
  public void testGetStrokeWidth() {
    GraphicalPrimitive1D gp1D=new GraphicalPrimitive1D();
    double strokeWidth=18d;
    gp1D.setStrokeWidth(strokeWidth);
    assertEquals(strokeWidth,gp1D.getStrokeWidth(),.00000001d);
  }


  /**
   * Test method for {@link GraphicalPrimitive1D#isSetStroke()}.
   */
  @Test
  public void testIsSetStroke() {
    GraphicalPrimitive1D gp1D=new GraphicalPrimitive1D();
    assertTrue(!gp1D.isSetStroke());
    String stroke="stroke";
    gp1D.setStroke(stroke);
    assertTrue(gp1D.isSetStroke());
  }


  /**
   * Test method for {@link GraphicalPrimitive1D#isSetStrokeWidth()}.
   */
  @Test
  public void testIsSetStrokeWidth() {
    GraphicalPrimitive1D gp1D=new GraphicalPrimitive1D();
    assertTrue(!gp1D.isSetStrokeWidth());
    double strokeWidth=18d;
    gp1D.setStrokeWidth(strokeWidth);
    assertTrue(gp1D.isSetStrokeWidth());
  }


  /**
   * Test method for {@link GraphicalPrimitive1D#setStroke(java.lang.String)}.
   */
  @Test
  public void testSetStroke() {
    GraphicalPrimitive1D gp1D=new GraphicalPrimitive1D();
    String stroke="stroke";
    gp1D.setStroke(stroke);
    assertEquals(stroke,gp1D.getStroke());
  }


  /**
   * Test method for {@link GraphicalPrimitive1D#setStrokeWidth(double)}.
   */
  @Test
  public void testSetStrokeWidth() {
    GraphicalPrimitive1D gp1D=new GraphicalPrimitive1D();
    assertTrue(!gp1D.isSetStrokeWidth());
    double strokeWidth=18d;
    gp1D.setStrokeWidth(strokeWidth);
    assertEquals(strokeWidth,gp1D.getStrokeWidth(),.00000001d);
  }
}
