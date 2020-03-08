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
import org.sbml.jsbml.ext.render.RelAbsVector;


/**
 * @author Ibrahim Vazirabad
 * @since 1.0
 */
public class ImageTest {
  private final static double TOLERANCE = 1e-5;
  
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
   * Test method for {@link Image#getHeight()}.
   */
  @Test
  public void testGetHeight() {
    Image image=new Image();
    RelAbsVector height = new RelAbsVector(12);
    image.setHeight(height);
    assertEquals(height, image.getHeight());
  }


  /**
   * Test method for {@link Image#isSetHeight()}.
   */
  @Test
  public void testIsSetHeight() {
    Image image=new Image();
    RelAbsVector height = new RelAbsVector(12);
    image.setHeight(height);
    assertTrue(image.isSetHeight());
  }


  /**
   * Test method for {@link Image#setHeight(double)}.
   */
  @Test
  public void testSetHeight() {
    Image image=new Image();
    RelAbsVector height = new RelAbsVector(12);
    image.setHeight(height);
    assertEquals(height, image.getHeight());
  }


  /**
   * Test method for {@link Image#getHref()}.
   */
  @Test
  public void testGetHref() {
    Image image=new Image();
    String hyperlink="http://sbml.org/Main_Page";
    image.setHref(hyperlink);
    assertEquals(hyperlink, image.getHref());
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
    RelAbsVector width = new RelAbsVector(10);
    image.setWidth(width);
    assertEquals(width,image.getWidth());
  }


  /**
   * Test method for {@link Image#isSetWidth()}.
   */
  @Test
  public void testIsSetWidth() {
    Image image=new Image();
    RelAbsVector width = new RelAbsVector(132);
    image.setWidth(width);
    assertTrue(image.isSetWidth());
  }


  /**
   * Test method for {@link Image#setWidth(double)}.
   */
  @Test
  public void testSetWidth() {
    Image image=new Image();
    RelAbsVector width = new RelAbsVector(10);
    image.setWidth(width);
    assertEquals(width,image.getWidth());
  }


  /**
   * Test method for {@link Image#getX()}.
   */
  @Test
  public void testGetX() {
    Image image=new Image();
    image.setX(new RelAbsVector(0.02d));
    assertEquals(image.getX().getAbsoluteValue(),0.02d, TOLERANCE);
  }


  /**
   * Test method for {@link Image#isSetX()}.
   */
  @Test
  public void testIsSetX() {
    Image image=new Image();
    image.setX(new RelAbsVector(0.02d));
    assertTrue(image.isSetX());
  }


  /**
   * Test method for {@link Image#setX(double)}.
   */
  @Test
  public void testSetX() {
    Image image=new Image();
    image.setX(new RelAbsVector(0.02d));
    assertEquals(image.getX().getAbsoluteValue(),0.02d, TOLERANCE);
  }


  /**
   * Test method for {@link Image#getY()}.
   */
  @Test
  public void testGetY() {
    Image image=new Image();
    image.setY(new RelAbsVector(0.02d));
    assertEquals(image.getY(), new RelAbsVector(0.02d));
  }


  /**
   * Test method for {@link Image#isSetY()}.
   */
  @Test
  public void testIsSetY() {
    Image image=new Image();
    image.setY(new RelAbsVector(0.02d));
    assertTrue(image.isSetY());
  }


  /**
   * Test method for {@link Image#setY(double)}.
   */
  @Test
  public void testSetY() {
    Image image=new Image();
    image.setY(new RelAbsVector(0.02d));
    assertEquals(image.getY(),new RelAbsVector(0.02d));
  }


  /**
   * Test method for {@link Image#getZ()}.
   */
  @Test
  public void testGetZ() {
    Image image=new Image();
    image.setZ(new RelAbsVector(0.02d));
    assertEquals(image.getZ(),new RelAbsVector(0.02d));
  }


  /**
   * Test method for {@link Image#isSetZ()}.
   */
  @Test
  public void testIsSetZ() {
    Image image=new Image();
    image.setZ(new RelAbsVector(0.02d));
    assertTrue(image.isSetZ());
  }


  /**
   * Test method for {@link Image#setZ(double)}.
   */
  @Test
  public void testSetZ() {
    Image image=new Image();
    image.setZ(new RelAbsVector(0.02d));
    assertTrue(image.isSetZ());
  }
}
