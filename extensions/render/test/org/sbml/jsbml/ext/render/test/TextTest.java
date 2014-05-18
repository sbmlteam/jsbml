/*
 * $Id:  TextTest.java 1733 May 14, 2014 7:24:04 PM yvazirabad $
 * $URL: https://svn.code.sf.net/p/jsbml/code/trunk/extensions/render/test/org/sbml/jsbml/ext/render/test/TextTest.java $
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
import org.sbml.jsbml.ext.render.FontFamily;
import org.sbml.jsbml.ext.render.Text;
import org.sbml.jsbml.ext.render.TextAnchor;
import org.sbml.jsbml.ext.render.VTextAnchor;


/**
 * @author Ibrahim Vazirabad
 * @version $Rev 1733$
 * @since 1.0
 * @date May 14, 2014
 */
public class TextTest {

  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#getFontFamily()}.
   */
  @Test
  public void testGetFontFamily() {
    FontFamily fontType=FontFamily.MONOSPACE;
    Text textType=new Text();
    textType.setFontFamily(fontType);
    assertEquals("getFontFamily",fontType,textType.getFontFamily());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#getFontSize()}.
   */
  @Test
  public void testGetFontSize() {
  short fontSize=18;
  Text textType=new Text();
  textType.setFontSize(fontSize);
  assertEquals("getFontSizeError",fontSize,textType.getFontSize());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#getTextAnchor()}.
   */
  @Test
  public void testGetTextAnchor() {
    TextAnchor anchor=TextAnchor.START;
    Text textType=new Text();
    textType.setTextAnchor(anchor);
    assertEquals("getTextAnchorError",anchor,textType.getTextAnchor());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#getVTextAnchor()}.
   */
  @Test
  public void testGetVTextAnchor() {
    VTextAnchor anchor=VTextAnchor.BOTTOM;
    Text textType=new Text();
    textType.setVTextAnchor(anchor);
    assertEquals("getTextAnchorError",anchor,textType.getVTextAnchor());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#getX()}.
   */
  @Test
  public void testGetX() {
    Text textType=new Text();
    textType.setX(0.02d);
    assertEquals(textType.getX(),0.02d,0.0001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#getY()}.
   */
  @Test
  public void testGetY() {
    Text textType=new Text();
    textType.setY(0.02d);
    assertEquals(textType.getY(),0.02d,0.0001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#getZ()}.
   */
  @Test
  public void testGetZ() {
    Text textType=new Text();
    textType.setZ(0.02d);
    assertEquals(textType.getZ(),0.02d,0.0001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#isSetAbsoluteX()}.
   */
  @Test
  public void testIsSetAbsoluteX() {
    Text textType=new Text();
    textType.setAbsoluteX(true);
    assertTrue(textType.isSetAbsoluteX());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#isSetAbsoluteY()}.
   */
  @Test
  public void testIsSetAbsoluteY() {
    Text textType=new Text();
    textType.setAbsoluteY(true);
    assertTrue(textType.isSetAbsoluteY());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#isSetAbsoluteZ()}.
   */
  @Test
  public void testIsSetAbsoluteZ() {
    Text textType=new Text();
    textType.setAbsoluteZ(true);
    assertTrue(textType.isSetAbsoluteZ());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#isSetFontFamily()}.
   */
  @Test
  public void testIsSetFontFamily() {
    FontFamily fontType=FontFamily.MONOSPACE;
    Text textType=new Text();
    textType.setFontFamily(fontType);
    assertTrue(textType.isSetFontFamily());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#isSetFontSize()}.
   */
  @Test
  public void testIsSetFontSize() {
    short fontSize=18;
    Text textType=new Text();
    textType.setFontSize(fontSize);
    assertTrue(textType.isSetFontSize());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#isSetFontStyleItalic()}.
   */
  @Test
  public void testIsSetFontStyleItalic() {
    Text textType=new Text();
    assertTrue(!textType.isSetFontStyleItalic());
    textType.setFontStyleItalic(true);
    assertTrue(textType.isSetFontStyleItalic());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#isSetFontWeightBold()}.
   */
  @Test
  public void testIsSetFontWeightBold() {
    Text textType=new Text();
    assertTrue(!textType.isSetFontWeightBold());
    textType.setFontWeightBold(true);
    assertTrue(textType.isSetFontWeightBold());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#isSetTextAnchor()}.
   */
  @Test
  public void testIsSetTextAnchor() {
    Text textType=new Text();
    TextAnchor anchor=TextAnchor.MIDDLE;
    assertTrue(!textType.isSetTextAnchor());
    textType.setTextAnchor(anchor);
    assertTrue(textType.isSetTextAnchor());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#isSetVTextAnchor()}.
   */
  @Test
  public void testIsSetVTextAnchor() {
    Text textType=new Text();
    VTextAnchor anchor=VTextAnchor.BOTTOM;
    assertTrue(!textType.isSetVTextAnchor());
    textType.setVTextAnchor(anchor);
    assertTrue(textType.isSetVTextAnchor());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#isSetX()}.
   */
  @Test
  public void testIsSetX() {
    Text textType=new Text();
    textType.setX(0.02d);
    assertTrue(textType.isSetX());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#isSetY()}.
   */
  @Test
  public void testIsSetY() {
    Text textType=new Text();
    textType.setY(0.02d);
    assertTrue(textType.isSetY());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#isSetZ()}.
   */
  @Test
  public void testIsSetZ() {
    Text textType=new Text();
    textType.setZ(0.02d);
    assertTrue(textType.isSetZ());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#setAbsoluteX(java.lang.Boolean)}.
   */
  @Test
  public void testSetAbsoluteX() {
    Text textType=new Text();
    textType.setAbsoluteX(true);
    assertEquals("AbsoluteVarError",true,textType.isAbsoluteX());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#setAbsoluteY(java.lang.Boolean)}.
   */
  @Test
  public void testSetAbsoluteY() {
    Text textType=new Text();
    textType.setAbsoluteY(true);
    assertEquals("AbsoluteVarError",true,textType.isAbsoluteY());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#setAbsoluteZ(java.lang.Boolean)}.
   */
  @Test
  public void testSetAbsoluteZ() {
    Text textType=new Text();
    textType.setAbsoluteZ(true);
    assertTrue(textType.isAbsoluteZ());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#setFontFamily(org.sbml.jsbml.ext.render.FontFamily)}.
   */
  @Test
  public void testSetFontFamily() {
    FontFamily fontType=FontFamily.MONOSPACE;
    Text textType=new Text();
    textType.setFontFamily(fontType);
    assertEquals("setFontFamilyError",textType.getFontFamily(),fontType);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#setFontSize(short)}.
   */
  @Test
  public void testSetFontSize() {
    short fontSize=19;
    Text textType=new Text();
    textType.setFontSize(fontSize);
    assertEquals("setFontSizeError",textType.getFontSize(),fontSize);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#setFontStyleItalic(boolean)}.
   */
  @Test
  public void testSetFontStyleItalic() {
    Text textType=new Text();
    textType.setFontStyleItalic(true);
    assertTrue(textType.isFontStyleItalic());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#setFontWeightBold(boolean)}.
   */
  @Test
  public void testSetFontWeightBold() {
    Text textType=new Text();
    textType.setFontWeightBold(true);
    assertTrue(textType.isFontWeightBold());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#setTextAnchor(org.sbml.jsbml.ext.render.TextAnchor)}.
   */
  @Test
  public void testSetTextAnchor() {
    Text textType=new Text();
    TextAnchor anchor=TextAnchor.END;
    textType.setTextAnchor(anchor);
    assertEquals("textAnchorError",anchor,textType.getTextAnchor());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#setVTextAnchor(org.sbml.jsbml.ext.render.VTextAnchor)}.
   */
  @Test
  public void testSetVTextAnchor() {
    Text textType=new Text();
    VTextAnchor anchor=VTextAnchor.BOTTOM;
    textType.setVTextAnchor(anchor);
    assertEquals("VTextAnchorError",anchor,textType.getVTextAnchor());
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#setX(java.lang.Double)}.
   */
  @Test
  public void testSetX() {
    Text textType=new Text();
    textType.setX(0.02d);
    assertEquals(textType.getX(),0.02d,0.0001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#setY(java.lang.Double)}.
   */
  @Test
  public void testSetY() {
    Text textType=new Text();
    textType.setY(0.02d);
    assertEquals(textType.getY(),0.02d,0.0001d);
  }


  /**
   * Test method for {@link org.sbml.jsbml.ext.render.Text#setZ(java.lang.Double)}.
   */
  @Test
  public void testSetZ() {
    Text textType=new Text();
    textType.setZ(0.02d);
    assertEquals(textType.getZ(),0.02d,0.0001d);
  }
}
