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
import org.sbml.jsbml.ext.render.GraphicalPrimitive2D;


/**
 * @author Ibrahim Vazirabad
 * @version 1771
 * @since 1.0
 */
public class GraphicalPrimitive2DTest {

  /**
   * Test method for {@link org.sbml.jsbml.ext.render.GraphicalPrimitive2D#getFill()}.
   */
  @Test
  public void testGetFill() {
    GraphicalPrimitive2D gp2D=new GraphicalPrimitive2D();
    String fill="black";
    gp2D.setFill(fill);
    assertEquals("FillError",fill,gp2D.getFill());
    fill="#45b232";
    gp2D.setFill(fill);
    assertEquals("FillError",fill,gp2D.getFill());
    gp2D.unsetFill();
    assertTrue(!gp2D.isSetFill());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.GraphicalPrimitive2D#isSetFill()}.
   */
  @Test
  public void testIsSetFill() {
    GraphicalPrimitive2D gp2D=new GraphicalPrimitive2D();
    assertTrue(!gp2D.isSetFill());
    String fill="#45b232";
    gp2D.setFill(fill);
    assertTrue(gp2D.isSetFill());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.GraphicalPrimitive2D#setFill(java.lang.String)}.
   */
  @Test
  public void testSetFill() {
    GraphicalPrimitive2D gp2D=new GraphicalPrimitive2D();
    String fill="#45b232";
    gp2D.setFill(fill);
    assertEquals("FillError",fill,gp2D.getFill());
    assertTrue(gp2D.isSetFill());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.GraphicalPrimitive2D#getFillRule()}.
   */
  @Test
  public void testGetFillRule() {
    GraphicalPrimitive2D gp2D=new GraphicalPrimitive2D();
    GraphicalPrimitive2D.FillRule fillRule=GraphicalPrimitive2D.FillRule.EVENODD;
    gp2D.setFillRule(fillRule);
    assertEquals("FillRuleErrorEVENODD",fillRule,gp2D.getFillRule());
    fillRule=GraphicalPrimitive2D.FillRule.NONZERO;
    gp2D.setFillRule(fillRule);
    assertEquals("FillRuleErrorNONZERO",fillRule,gp2D.getFillRule());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.GraphicalPrimitive2D#isSetFillRule()}.
   */
  @Test
  public void testIsSetFillRule() {
    GraphicalPrimitive2D gp2D=new GraphicalPrimitive2D();
    GraphicalPrimitive2D.FillRule fillRule=GraphicalPrimitive2D.FillRule.EVENODD;
    gp2D.setFillRule(fillRule);
    assertTrue(gp2D.isSetFillRule());
    gp2D.unsetFillRule();
    assertTrue(!gp2D.isSetFillRule());
    fillRule=GraphicalPrimitive2D.FillRule.NONZERO;
    gp2D.setFillRule(fillRule);
    assertTrue(gp2D.isSetFillRule());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.GraphicalPrimitive2D#setFillRule(org.sbml.jsbml.ext.render.GraphicalPrimitive2D.FillRule)}.
   */
  @Test
  public void testSetFillRule() {
    GraphicalPrimitive2D gp2D=new GraphicalPrimitive2D();
    GraphicalPrimitive2D.FillRule fillRule=GraphicalPrimitive2D.FillRule.EVENODD;
    gp2D.setFillRule(fillRule);
    assertEquals("FillRuleErrorEVENODD",fillRule,gp2D.getFillRule());
    fillRule=GraphicalPrimitive2D.FillRule.NONZERO;
    gp2D.setFillRule(fillRule);
    assertEquals("FillRuleErrorNONZERO",fillRule,gp2D.getFillRule());
  }
}
