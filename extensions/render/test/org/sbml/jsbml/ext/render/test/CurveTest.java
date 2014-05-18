/*
 * $Id:  CurveTest.java 1733 May 14, 2014 7:23:06 PM yvazirabad $
 * $URL: https://svn.code.sf.net/p/jsbml/code/trunk/extensions/render/test/org/sbml/jsbml/ext/render/test/CurveTest.java $
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
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.ext.render.Curve;
import org.sbml.jsbml.ext.render.RenderPoint;


/**
 * @author Ibrahim Vazirabad
 * @version $Rev 1733$
 * @since 1.0
 * @date May 14, 2014
 */
public class CurveTest {

  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Curve#getStartHead()}.
   */
  @Test
  public void testGetStartHead() {
    Curve curve=new Curve();
    String position="position";
    curve.setStartHead(position);
    assertEquals("startHeadError",position,curve.getStartHead());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Curve#isSetStartHead()}.
   */
  @Test
  public void testIsSetStartHead() {
    Curve curve=new Curve();
    String position="position";
    curve.setStartHead(position);
    assertTrue(curve.isSetStartHead());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Curve#setStartHead(java.lang.String)}.
   */
  @Test
  public void testSetStartHead() {
    Curve curve=new Curve();
    String position="position";
    curve.setStartHead(position);
    assertEquals("startHeadError",position,curve.getStartHead());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Curve#getEndHead()}.
   */
  @Test
  public void testGetEndHead() {
    Curve curve=new Curve();
    String position="position";
    curve.setEndHead(position);
    assertEquals("startHeadError",position,curve.getEndHead());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Curve#isSetEndHead()}.
   */
  @Test
  public void testIsSetEndHead() {
    Curve curve=new Curve();
    String position="position";
    curve.setEndHead(position);
    assertTrue(curve.isSetEndHead());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Curve#setEndHead(java.lang.String)}.
   */
  @Test
  public void testSetEndHead() {
    Curve curve=new Curve();
    String position="position";
    curve.setEndHead(position);
    assertEquals("startHeadError",position,curve.getEndHead());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Curve#getListOfElements()}.
   */
  @Test
  public void testGetListOfElements() {
    Curve curve=new Curve();
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
   * Test method for {@link org.sbml.jsbml.ext.render.Curve#setListOfElements(org.sbml.jsbml.ListOf)}.
   */
  @Test
  public void testSetListOfElements() {
    ListOf<RenderPoint> list=new ListOf<RenderPoint>();
    RenderPoint point=new RenderPoint();
    point.setX(.01d);
    point.setY(.01d);
    point.setZ(.01d);
    list.add(point);
    Curve curve=new Curve();
    assertTrue(!curve.isSetListOfElements());
    curve.setListOfElements(list);
    assertTrue(curve.isSetListOfElements());
  }
}
