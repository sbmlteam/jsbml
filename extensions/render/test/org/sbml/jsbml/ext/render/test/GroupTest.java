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
import org.sbml.jsbml.ext.render.FontFamily;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.HTextAnchor;
import org.sbml.jsbml.ext.render.VTextAnchor;


/**
 * @author Ibrahim Vazirabad
 * @since 1.0
 */
public class GroupTest {

  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#getAllowsChildren()}.
   */
  @Test
  public void testGetAllowsChildren() {
    assertTrue(new RenderGroup().getAllowsChildren());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#getChildCount()}.
   */
  @Test
  public void testGetChildCount() {
    RenderGroup g=new RenderGroup();
    assertEquals("childCountError",g.getChildCount(),0);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#getId()}.
   */
  @Test
  public void testGetId() {
    RenderGroup g=new RenderGroup();
    String id="newGroup";
    g.setId(id);
    assertEquals(id,g.getId());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#isSetId()}.
   */
  @Test
  public void testIsSetId() {
    RenderGroup g=new RenderGroup();
    String id="newGroup";
    g.setId(id);
    assertTrue(g.isSetId());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#setId(java.lang.String)}.
   */
  @Test
  public void testSetId() {
    RenderGroup g=new RenderGroup();
    String id="newGroup";
    g.setId(id);
    assertEquals(id,g.getId());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#getFontFamily()}.
   */
  @Test
  public void testGetFontFamily() {
    FontFamily fontType=FontFamily.MONOSPACE;
    RenderGroup g=new RenderGroup();
    assertTrue(!g.isSetFontFamily());
    g.setFontFamily(fontType);
    assertEquals("getFontFamily",fontType,g.getFontFamily());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#isSetFontFamily()}.
   */
  @Test
  public void testIsSetFontFamily() {
    FontFamily fontType=FontFamily.MONOSPACE;
    RenderGroup g=new RenderGroup();
    assertTrue(!g.isSetFontFamily());
    g.setFontFamily(fontType);
    assertTrue(g.isSetFontFamily());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#setFontFamily(org.sbml.jsbml.ext.render.FontFamily)}.
   */
  @Test
  public void testSetFontFamily() {
    FontFamily fontType=FontFamily.MONOSPACE;
    RenderGroup g=new RenderGroup();
    assertTrue(!g.isSetFontFamily());
    g.setFontFamily(fontType);
    assertEquals("setFontFamilyError",g.getFontFamily(),fontType);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#getFontSize()}.
   */
  @Test
  public void testGetFontSize() {
    short fontSize=18;
    RenderGroup g=new RenderGroup();
    assertTrue(!g.isSetFontSize());
    g.setFontSize(fontSize);
    assertEquals("getFontSizeError",Short.valueOf(fontSize),g.getFontSize());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#isSetFontSize()}.
   */
  @Test
  public void testIsSetFontSize() {
    short fontSize=18;
    RenderGroup g=new RenderGroup();
    assertTrue(!g.isSetFontSize());
    g.setFontSize(fontSize);
    assertTrue(g.isSetFontSize());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#setFontSize(java.lang.Short)}.
   */
  @Test
  public void testSetFontSize() {
    short fontSize=19;
    RenderGroup g=new RenderGroup();
    assertTrue(!g.isSetFontSize());
    g.setFontSize(fontSize);
    assertEquals("getFontSizeError",Short.valueOf(fontSize),g.getFontSize());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#isFontWeightBold()}.
   */
  @Test
  public void testIsFontWeightBold() {
    RenderGroup g=new RenderGroup();
    g.setFontWeightBold(true);
    assertTrue(g.isFontWeightBold());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#isSetFontWeightBold()}.
   */
  @Test
  public void testIsSetFontWeightBold() {
    RenderGroup g=new RenderGroup();
    g.setFontWeightBold(false);
    assertTrue(g.isSetFontWeightBold());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#setFontWeightBold(java.lang.Boolean)}.
   */
  @Test
  public void testSetFontWeightBold() {
    RenderGroup g=new RenderGroup();
    g.setFontWeightBold(true);
    assertTrue(g.isFontWeightBold());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#isFontStyleItalic()}.
   */
  @Test
  public void testIsFontStyleItalic() {
    RenderGroup g=new RenderGroup();
    g.setFontStyleItalic(true);
    assertTrue(g.isFontStyleItalic());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#isSetFontStyleItalic()}.
   */
  @Test
  public void testIsSetFontStyleItalic() {
    RenderGroup g=new RenderGroup();
    assertTrue(!g.isSetFontStyleItalic());
    g.setFontStyleItalic(true);
    assertTrue(g.isSetFontStyleItalic());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#setFontStyleItalic(java.lang.Boolean)}.
   */
  @Test
  public void testSetFontStyleItalic() {
    RenderGroup g=new RenderGroup();
    assertTrue(!g.isSetFontStyleItalic());
    g.setFontStyleItalic(false);
    assertTrue(g.isSetFontStyleItalic());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#getStartHead()}.
   */
  @Test
  public void testGetStartHead() {
    RenderGroup g=new RenderGroup();
    String startH="s1";
    g.setStartHead(startH);
    assertEquals(startH,g.getStartHead());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#isSetStartHead()}.
   */
  @Test
  public void testIsSetStartHead() {
    RenderGroup g=new RenderGroup();
    String startH="s1";
    g.setStartHead(startH);
    assertTrue(g.isSetStartHead());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#setStartHead(java.lang.String)}.
   */
  @Test
  public void testSetStartHead() {
    RenderGroup g=new RenderGroup();
    String startH="s1";
    g.setStartHead(startH);
    assertEquals(startH,g.getStartHead());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#getEndHead()}.
   */
  @Test
  public void testGetEndHead() {
    RenderGroup g=new RenderGroup();
    String endH="s2";
    g.setStartHead(endH);
    assertEquals(endH,g.getStartHead());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#isSetEndHead()}.
   */
  @Test
  public void testIsSetEndHead() {
    RenderGroup g=new RenderGroup();
    String endH="s2";
    g.setEndHead(endH);
    assertTrue(g.isSetEndHead());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#setEndHead(java.lang.String)}.
   */
  @Test
  public void testSetEndHead() {
    RenderGroup g=new RenderGroup();
    String endH="s2";
    g.setEndHead(endH);
    assertEquals(endH,g.getEndHead());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#getTextAnchor()}.
   */
  @Test
  public void testGetTextAnchor() {
    HTextAnchor anchor=HTextAnchor.START;
    RenderGroup g=new RenderGroup();
    assertTrue(!g.isSetTextAnchor());
    g.setTextAnchor(anchor);
    assertEquals("getTextAnchorError",anchor,g.getTextAnchor());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#isSetTextAnchor()}.
   */
  @Test
  public void testIsSetTextAnchor() {
    RenderGroup g=new RenderGroup();
    HTextAnchor anchor=HTextAnchor.MIDDLE;
    assertTrue(!g.isSetTextAnchor());
    g.setTextAnchor(anchor);
    assertTrue(g.isSetTextAnchor());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#setTextAnchor(org.sbml.jsbml.ext.render.HTextAnchor)}.
   */
  @Test
  public void testSetTextAnchor() {
    RenderGroup g=new RenderGroup();
    HTextAnchor anchor=HTextAnchor.END;
    g.setTextAnchor(anchor);
    assertEquals("textAnchorError",anchor,g.getTextAnchor());
    anchor=HTextAnchor.MIDDLE;
    g.setTextAnchor(anchor);
    assertEquals("textAnchorError",anchor,g.getTextAnchor());
    anchor=HTextAnchor.START;
    g.setTextAnchor(anchor);
    assertEquals("textAnchorError",anchor,g.getTextAnchor());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#getVTextAnchor()}.
   */
  @Test
  public void testGetVTextAnchor() {
    VTextAnchor anchor=VTextAnchor.BOTTOM;
    RenderGroup g=new RenderGroup();
    g.setVTextAnchor(anchor);
    assertEquals("getTextAnchorError",anchor,g.getVTextAnchor());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#isSetVTextAnchor()}.
   */
  @Test
  public void testIsSetVTextAnchor() {
    RenderGroup g=new RenderGroup();
    VTextAnchor anchor=VTextAnchor.BOTTOM;
    assertTrue(!g.isSetVTextAnchor());
    g.setVTextAnchor(anchor);
    assertTrue(g.isSetVTextAnchor());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.RenderGroup#setVTextAnchor(org.sbml.jsbml.ext.render.VTextAnchor)}.
   */
  @Test
  public void testSetVTextAnchor() {
    RenderGroup g=new RenderGroup();
    VTextAnchor anchor=VTextAnchor.BOTTOM;
    g.setVTextAnchor(anchor);
    assertEquals("VTextAnchorError",anchor,g.getVTextAnchor());
    anchor=VTextAnchor.MIDDLE;
    g.setVTextAnchor(anchor);
    assertEquals("VTextAnchorError",anchor,g.getVTextAnchor());
    anchor=VTextAnchor.TOP;
    g.setVTextAnchor(anchor);
    assertEquals("VTextAnchorError",anchor,g.getVTextAnchor());
  }
}
