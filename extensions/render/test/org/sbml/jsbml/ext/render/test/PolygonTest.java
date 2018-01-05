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
import org.sbml.jsbml.ext.render.Polygon;
import org.sbml.jsbml.ext.render.RenderCubicBezier;
import org.sbml.jsbml.ext.render.RenderPoint;


/**
 * @author Ibrahim Vazirabad
 * @since 1.0
 */
public class PolygonTest {

  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Polygon#getAllowsChildren()}.
   */
  @Test
  public void testGetAllowsChildren() {
    Polygon polygon=new Polygon();
    assertTrue(polygon.getAllowsChildren());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Polygon#getChildCount()}.
   */
  @Test
  public void testGetChildCount() {
    Polygon polygon=new Polygon();
    assertEquals("PolygonError",0,polygon.getChildCount());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Polygon#getChildAt(int)}.
   */
  @Test
  public void testGetChildAtInt() {
    Polygon polygon=new Polygon();
    ListOf<RenderPoint> list=new ListOf<RenderPoint>();
    RenderPoint point=new RenderPoint();
    point.setX(.01d);
    point.setY(.01d);
    point.setZ(.01d);
    list.add(point);
    polygon.setListOfElements(list);
    assertEquals("Error",list,polygon.getChildAt(0));
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Polygon#getListOfElements()}.
   */
  @Test
  public void testGetListOfElements() {
    Polygon polygon=new Polygon();
    ListOf<RenderPoint> list=new ListOf<RenderPoint>();
    RenderPoint point=new RenderPoint();
    point.setX(.01d);
    point.setY(.01d);
    point.setZ(.01d);
    list.add(point);
    polygon.setListOfElements(list);
    assertEquals("ElementError",point,polygon.getListOfElements().get(0));
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Polygon#isSetListOfElements()}.
   */
  @Test
  public void testIsSetListOfElements() {
    Polygon polygon=new Polygon();
    ListOf<RenderPoint> list=new ListOf<RenderPoint>();
    RenderPoint point=new RenderPoint();
    point.setX(.01d);
    point.setY(.01d);
    point.setZ(.01d);
    list.add(point);
    polygon.setListOfElements(list);
    assertTrue(polygon.isSetListOfElements());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Polygon#setListOfElements(org.sbml.jsbml.ListOf)}.
   */
  @Test
  public void testSetListOfElements() {
    ListOf<RenderPoint> list=new ListOf<RenderPoint>();
    RenderPoint point=new RenderPoint();
    point.setX(.01d);
    point.setY(.01d);
    point.setZ(.01d);
    list.add(point);
    Polygon polygon=new Polygon();
    assertTrue(!polygon.isSetListOfElements());
    polygon.setListOfElements(list);
    assertTrue(polygon.isSetListOfElements());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Polygon#addElement(org.sbml.jsbml.ext.render.RenderPoint)}.
   */
  @Test
  public void testAddElement()
  {
    Polygon polygon=new Polygon();
    ListOf<RenderPoint> list=new ListOf<RenderPoint>();
    polygon.setListOfElements(list);
    System.out.println(polygon.getChildCount());
    assertTrue(polygon.getListOfElements().getChildCount() == 0);
    RenderPoint rP=new RenderPoint();
    assertTrue(rP != null);
    assertTrue(polygon.addElement(rP));
    assertTrue(polygon.getListOfElements().getChildCount() == 1);
    assertTrue(polygon.getListOfElements().get(0).equals(rP));
    RenderPoint rP2 = new RenderPoint();
    assertTrue (rP2 != null);
    assertTrue(polygon.addElement(rP2));
    assertTrue(polygon.getListOfElements().getChildCount() == 2);
    RenderCubicBezier pCB = new RenderCubicBezier();
    assertTrue(pCB != null);
    assertTrue(polygon.addElement(pCB));
    assertTrue(polygon.getListOfElements().getChildCount() == 3);
    assertTrue(polygon.getListOfElements().get(1).equals(rP));
  }
}
