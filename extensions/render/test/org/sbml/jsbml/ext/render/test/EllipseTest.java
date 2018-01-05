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
import org.sbml.jsbml.ext.render.Ellipse;


/**
 * @author Ibrahim Vazirabad
 * @since 1.0
 */
public class EllipseTest {

  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#getCx()}.
   */
  @Test
  public void testGetCx() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setCx(d);
    assertEquals(ellipse.getCx(),d,0.00000001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isSetCx()}.
   */
  @Test
  public void testIsSetCx() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setCx(d);
    assertTrue(ellipse.isSetCx());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#setCx(double)}.
   */
  @Test
  public void testSetCx() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setCx(d);
    assertTrue(Double.compare(ellipse.getCx(),d)==0);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#getCy()}.
   */
  @Test
  public void testGetCy() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setCy(d);
    assertEquals(ellipse.getCy(),d,0.00000001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isSetCy()}.
   */
  @Test
  public void testIsSetCy() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setCy(d);
    assertTrue(ellipse.isSetCy());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#setCy(java.lang.Double)}.
   */
  @Test
  public void testSetCy() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setCy(d);
    assertTrue(Double.compare(ellipse.getCy(),d)==0);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#getCz()}.
   */
  @Test
  public void testGetCz() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setCz(d);
    assertEquals(ellipse.getCz(),d,0.00000001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isSetCz()}.
   */
  @Test
  public void testIsSetCz() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setCz(d);
    assertTrue(Double.compare(ellipse.getCz(),d)==0);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#setCz(java.lang.Double)}.
   */
  @Test
  public void testSetCz() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setCz(d);
    assertTrue(Double.compare(ellipse.getCz(),0.02d)==0);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#getRx()}.
   */
  @Test
  public void testGetRx() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setRx(d);
    assertEquals(ellipse.getRx(),d,0.00000001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isSetRx()}.
   */
  @Test
  public void testIsSetRx() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setRx(d);
    assertTrue(ellipse.isSetRx());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#setRx(java.lang.Double)}.
   */
  @Test
  public void testSetRx() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setRx(d);
    assertTrue(Double.compare(ellipse.getRx(),0.02d)==0);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#getRy()}.
   */
  @Test
  public void testGetRy() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setRy(d);
    assertEquals(ellipse.getRy(),d,0.00000001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isSetRy()}.
   */
  @Test
  public void testIsSetRy() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setRy(d);
    assertTrue(ellipse.isSetRy());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#setRy(java.lang.Double)}.
   */
  @Test
  public void testSetRy() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setRy(d);
    assertTrue(Double.compare(ellipse.getRy(),0.02d)==0);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isAbsoluteCx()}.
   */
  @Test
  public void testIsAbsoluteCx() {
    Ellipse ellipse=new Ellipse();
    ellipse.setAbsoluteCx(false);
    assertTrue(!ellipse.isAbsoluteCx());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isSetAbsoluteCx()}.
   */
  @Test
  public void testIsSetAbsoluteCx() {
    Ellipse ellipse=new Ellipse();
    ellipse.setAbsoluteCx(false);
    assertTrue(ellipse.isSetAbsoluteCx());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#setAbsoluteCx(boolean)}.
   */
  @Test
  public void testSetAbsoluteCx() {
    Ellipse ellipse=new Ellipse();
    ellipse.setAbsoluteCx(false);
    assertTrue(!ellipse.isAbsoluteCx());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isAbsoluteCy()}.
   */
  @Test
  public void testIsAbsoluteCy() {
    Ellipse ellipse=new Ellipse();
    ellipse.setAbsoluteCy(false);
    assertTrue(!ellipse.isAbsoluteCy());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isSetAbsoluteCy()}.
   */
  @Test
  public void testIsSetAbsoluteCy() {
    Ellipse ellipse=new Ellipse();
    ellipse.setAbsoluteCy(false);
    assertTrue(ellipse.isSetAbsoluteCy());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#setAbsoluteCy(boolean)}.
   */
  @Test
  public void testSetAbsoluteCy() {
    Ellipse ellipse=new Ellipse();
    ellipse.setAbsoluteCy(false);
    assertTrue(!ellipse.isAbsoluteCy());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isAbsoluteCz()}.
   */
  @Test
  public void testIsAbsoluteCz() {
    Ellipse ellipse=new Ellipse();
    ellipse.setAbsoluteCz(false);
    assertTrue(!ellipse.isAbsoluteCz());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isSetAbsoluteCz()}.
   */
  @Test
  public void testIsSetAbsoluteCz() {
    Ellipse ellipse=new Ellipse();
    ellipse.setAbsoluteCz(false);
    assertTrue(ellipse.isSetAbsoluteCz());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#setAbsoluteCz(boolean)}.
   */
  @Test
  public void testSetAbsoluteCz() {
    Ellipse ellipse=new Ellipse();
    ellipse.setAbsoluteCz(false);
    assertTrue(!ellipse.isAbsoluteCz());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isAbsoluteRx()}.
   */
  @Test
  public void testIsAbsoluteRx() {
    Ellipse ellipse=new Ellipse();
    ellipse.setAbsoluteRx(false);
    assertTrue(!ellipse.isAbsoluteRx());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isSetAbsoluteRx()}.
   */
  @Test
  public void testIsSetAbsoluteRx() {
    Ellipse ellipse=new Ellipse();
    ellipse.setAbsoluteRx(false);
    assertTrue(ellipse.isSetAbsoluteRx());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#setAbsoluteRx(boolean)}.
   */
  @Test
  public void testSetAbsoluteRx() {
    Ellipse ellipse=new Ellipse();
    ellipse.setAbsoluteRx(false);
    assertTrue(!ellipse.isAbsoluteRx());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isAbsoluteRy()}.
   */
  @Test
  public void testIsAbsoluteRy() {
    Ellipse ellipse=new Ellipse();
    ellipse.setAbsoluteRy(true);
    assertTrue(ellipse.isAbsoluteRy());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isSetAbsoluteRy()}.
   */
  @Test
  public void testIsSetAbsoluteRy() {
    Ellipse ellipse=new Ellipse();
    ellipse.setAbsoluteRy(false);
    assertTrue(ellipse.isSetAbsoluteRy());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#setAbsoluteRy(boolean)}.
   */
  @Test
  public void testSetAbsoluteRy() {
    Ellipse ellipse=new Ellipse();
    ellipse.setAbsoluteRy(false);
    assertTrue(!ellipse.isAbsoluteRy());
  }
}
