/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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

import java.beans.PropertyChangeEvent;

import javax.swing.tree.TreeNode;

import org.junit.Test;
import org.sbml.jsbml.ext.render.Ellipse;
import org.sbml.jsbml.ext.render.RelAbsVector;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.TreeNodeRemovedEvent;


/**
 * @author Ibrahim Vazirabad
 * @since 1.0
 */
public class EllipseTest {

  private static final double TOLERANCE = 1e-8;
  
  @Test
  public void testEvents() {
    Ellipse ellipse = new Ellipse();
    new RelAbsVector("50-30%");
    ellipse.setCx(new RelAbsVector("50-30%"));
    final StringBuffer change = new StringBuffer();
    final String ARROW = " -> ";
    TreeNodeChangeListener listener = new TreeNodeChangeListener() {

      @Override
      public void propertyChange(PropertyChangeEvent evt) {
        change.delete(0, change.length());
        change.append(evt.getOldValue() + ARROW + evt.getNewValue());
      }

      @Override
      public void nodeAdded(TreeNode node) { }
      @Override
      public void nodeRemoved(TreeNodeRemovedEvent event) { }
    };
    ellipse.addTreeNodeChangeListener(listener);
    // Initially: nothing changed
    assertEquals("", change.toString());
    
    // This trivially should produce a property-change-event
    ellipse.setCx(new RelAbsVector("70-30%"));
    String expected = new RelAbsVector("50-30%") + ARROW + new RelAbsVector("70-30%");
    assertEquals(expected, change.toString());
    
    // This will fly under the radar
    ellipse.getCx().setAbsoluteValue(10d);
    
    // I.e. No change-event was registered!
    assertEquals(expected, change.toString());
    
  }
  
  
  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#getCx()}.
   */
  @Test
  public void testGetCx() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setCx(new RelAbsVector(d));
    assertEquals(ellipse.getCx().getAbsoluteValue(),d, TOLERANCE);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isSetCx()}.
   */
  @Test
  public void testIsSetCx() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setCx(new RelAbsVector(d));
    assertTrue(ellipse.isSetCx());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#setCx(double)}.
   */
  @Test
  public void testSetCx() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setCx(new RelAbsVector(d));
    assertEquals(ellipse.getCx(), new RelAbsVector(d));
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#getCy()}.
   */
  @Test
  public void testGetCy() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setCy(new RelAbsVector(d));
    assertEquals(ellipse.getCy().getAbsoluteValue(), d, TOLERANCE);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isSetCy()}.
   */
  @Test
  public void testIsSetCy() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setCy(new RelAbsVector(d));
    assertTrue(ellipse.isSetCy());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#setCy(java.lang.Double)}.
   */
  @Test
  public void testSetCy() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setCy(new RelAbsVector(d));
    assertEquals(ellipse.getCy(), new RelAbsVector(d));
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#getCz()}.
   */
  @Test
  public void testGetCz() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setCz(new RelAbsVector(d));
    assertEquals(ellipse.getCz().getAbsoluteValue(), d, TOLERANCE);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isSetCz()}.
   */
  @Test
  public void testIsSetCz() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setCz(new RelAbsVector(d));
    assertTrue(ellipse.isSetCz());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#setCz(java.lang.Double)}.
   */
  @Test
  public void testSetCz() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setCz(new RelAbsVector(d));
    assertEquals(ellipse.getCz(), new RelAbsVector(d));
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#getRx()}.
   */
  @Test
  public void testGetRx() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setRx(new RelAbsVector(d));
    assertEquals(ellipse.getRx().getAbsoluteValue(),d,TOLERANCE);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isSetRx()}.
   */
  @Test
  public void testIsSetRx() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setRx(new RelAbsVector(d));
    assertTrue(ellipse.isSetRx());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#setRx(java.lang.Double)}.
   */
  @Test
  public void testSetRx() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setRx(new RelAbsVector(d));
    assertEquals(ellipse.getRx(), new RelAbsVector(d));
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#getRy()}.
   */
  @Test
  public void testGetRy() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setRy(new RelAbsVector(d));
    assertEquals(ellipse.getRy().getAbsoluteValue(), d, TOLERANCE);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isSetRy()}.
   */
  @Test
  public void testIsSetRy() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setRy(new RelAbsVector(d));
    assertTrue(ellipse.isSetRy());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#setRy(java.lang.Double)}.
   */
  @Test
  public void testSetRy() {
    Ellipse ellipse=new Ellipse();
    double d=0.02d;
    ellipse.setRy(new RelAbsVector(d));
    assertEquals(ellipse.getRy(), new RelAbsVector(d));
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#setRatio(Double)}
   * and {@link org.sbml.jsbml.ext.render.Ellipse#getRatio()}.
   */
  @Test
  public void testGetSetRatio() {
    Ellipse ellipse=new Ellipse();
    ellipse.setRatio(1.4d);
    assertTrue(ellipse.isSetRatio());
    assertEquals(1.4d, ellipse.getRatio(), 1e-10);
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#isSetRatio()}
   */
  @Test
  public void testIsSetRatio() {
    Ellipse ellipse=new Ellipse();
    assertFalse(ellipse.isSetRatio());
    ellipse.setRatio(1.4d);
    assertTrue(ellipse.isSetRatio());
    ellipse.setRatio(0.582d);
    assertTrue(ellipse.isSetRatio());
  }
  
  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Ellipse#unsetRatio()}
   */
  @Test
  public void testUnsetRatio() {
    Ellipse ellipse=new Ellipse();
    assertFalse(ellipse.isSetRatio());
    ellipse.setRatio(1.4d);
    assertTrue(ellipse.isSetRatio());
    ellipse.unsetRatio();
    assertFalse(ellipse.isSetRatio());
    ellipse.setRatio(0.92d);
    assertTrue(ellipse.isSetRatio());
    ellipse.setRatio(null);
    assertFalse(ellipse.isSetRatio());
    ellipse.setRatio(Double.NaN);
    assertFalse(ellipse.isSetRatio());
  }
}
