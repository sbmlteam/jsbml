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
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ext.render.RenderCurve;
import org.sbml.jsbml.ext.render.RenderPoint;


/**
 * @author Ibrahim Vazirabad
 * @since 1.0
 */
public class CurveTest {

  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderCurve#getStartHead()}.
   */
  @Test
  public void testGetStartHead() {
    RenderCurve curve=new RenderCurve();
    String position="position";
    curve.setStartHead(position);
    assertEquals("startHeadError",position,curve.getStartHead());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderCurve#isSetStartHead()}.
   */
  @Test
  public void testIsSetStartHead() {
    RenderCurve curve=new RenderCurve();
    String position="position";
    curve.setStartHead(position);
    assertTrue(curve.isSetStartHead());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderCurve#setStartHead(java.lang.String)}.
   */
  @Test
  public void testSetStartHead() {
    RenderCurve curve=new RenderCurve();
    String position="position";
    curve.setStartHead(position);
    assertEquals("startHeadError",position,curve.getStartHead());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderCurve#getEndHead()}.
   */
  @Test
  public void testGetEndHead() {
    RenderCurve curve=new RenderCurve();
    String position="position";
    curve.setEndHead(position);
    assertEquals("startHeadError",position,curve.getEndHead());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderCurve#isSetEndHead()}.
   */
  @Test
  public void testIsSetEndHead() {
    RenderCurve curve=new RenderCurve();
    String position="position";
    curve.setEndHead(position);
    assertTrue(curve.isSetEndHead());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderCurve#setEndHead(java.lang.String)}.
   */
  @Test
  public void testSetEndHead() {
    RenderCurve curve=new RenderCurve();
    String position="position";
    curve.setEndHead(position);
    assertEquals("startHeadError",position,curve.getEndHead());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderCurve#getListOfElements()}.
   */
  @Test
  public void testGetListOfElements() {
    RenderCurve curve=new RenderCurve();
    ListOf<RenderPoint> list=new ListOf<RenderPoint>();
    RenderPoint point=new RenderPoint();
    point.setX(.01d);
    point.setY(.01d);
    point.setZ(.01d);
    list.add(point);
    curve.setListOfElements(list);
    assertEquals("ElementError",point,curve.getListOfElements().get(0));
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderCurve#setListOfElements(org.sbml.jsbml.ListOf)}.
   */
  @Test
  public void testSetListOfElements() {
    ListOf<RenderPoint> list=new ListOf<RenderPoint>();
    RenderPoint point=new RenderPoint();
    point.setX(.01d);
    point.setY(.01d);
    point.setZ(.01d);
    list.add(point);
    RenderCurve curve=new RenderCurve();
    assertTrue(!curve.isSetListOfElements());
    curve.setListOfElements(list);
    assertTrue(curve.isSetListOfElements());
  }
}
