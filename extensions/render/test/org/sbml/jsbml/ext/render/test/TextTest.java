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
import org.sbml.jsbml.ext.render.Text;
import org.sbml.jsbml.ext.render.HTextAnchor;
import org.sbml.jsbml.ext.render.VTextAnchor;


/**
 * @author Ibrahim Vazirabad
 * @since 1.0
 */
public class TextTest {

  /**
   * Test method for {@link Text#getFontFamily()}.
   */
  @Test
  public void testGetFontFamily() {
    FontFamily fontType=FontFamily.MONOSPACE;
    Text textType=new Text();
    assertTrue(!textType.isSetFontFamily());
    textType.setFontFamily(fontType);
    assertEquals("getFontFamily",fontType,textType.getFontFamily());
  }


  /**
   * Test method for {@link Text#getFontSize()}.
   */
  @Test
  public void testGetFontSize() {
    short fontSize=18;
    Text textType=new Text();
    assertTrue(!textType.isSetFontSize());
    textType.setFontSize(fontSize);
    assertEquals("getFontSizeError",fontSize,textType.getFontSize());
  }


  /**
   * Test method for {@link Text#getTextAnchor()}.
   */
  @Test
  public void testGetTextAnchor() {
    HTextAnchor anchor=HTextAnchor.START;
    Text textType=new Text();
    assertTrue(!textType.isSetTextAnchor());
    textType.setTextAnchor(anchor);
    assertEquals("getTextAnchorError",anchor,textType.getTextAnchor());
  }


  /**
   * Test method for {@link Text#getVTextAnchor()}.
   */
  @Test
  public void testGetVTextAnchor() {
    VTextAnchor anchor=VTextAnchor.BOTTOM;
    Text textType=new Text();
    textType.setVTextAnchor(anchor);
    assertEquals("getTextAnchorError",anchor,textType.getVTextAnchor());
  }


  /**
   * Test method for {@link Text#getX()}.
   */
  @Test
  public void testGetX() {
    Text textType=new Text();
    assertTrue(!textType.isSetX());
    textType.setX(0.02d);
    assertEquals(textType.getX(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link Text#getY()}.
   */
  @Test
  public void testGetY() {
    Text textType=new Text();
    assertTrue(!textType.isSetY());
    textType.setY(0.02d);
    assertEquals(textType.getY(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link Text#getZ()}.
   */
  @Test
  public void testGetZ() {
    Text textType=new Text();
    assertTrue(!textType.isSetZ());
    textType.setZ(0.02d);
    assertEquals(textType.getZ(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link Text#isSetAbsoluteX()}.
   */
  @Test
  public void testIsSetAbsoluteX() {
    Text textType=new Text();
    assertTrue(!textType.isSetAbsoluteX());
    textType.setAbsoluteX(true);
    assertTrue(textType.isSetAbsoluteX());
  }


  /**
   * Test method for {@link Text#isSetAbsoluteY()}.
   */
  @Test
  public void testIsSetAbsoluteY() {
    Text textType=new Text();
    assertTrue(!textType.isSetAbsoluteY());
    textType.setAbsoluteY(true);
    assertTrue(textType.isSetAbsoluteY());
  }


  /**
   * Test method for {@link Text#isSetAbsoluteZ()}.
   */
  @Test
  public void testIsSetAbsoluteZ() {
    Text textType=new Text();
    assertTrue(!textType.isSetAbsoluteZ());
    textType.setAbsoluteZ(true);
    assertTrue(textType.isSetAbsoluteZ());
  }


  /**
   * Test method for {@link Text#isSetFontFamily()}.
   */
  @Test
  public void testIsSetFontFamily() {
    FontFamily fontType=FontFamily.MONOSPACE;
    Text textType=new Text();
    assertTrue(!textType.isSetFontFamily());
    textType.setFontFamily(fontType);
    assertTrue(textType.isSetFontFamily());
  }


  /**
   * Test method for {@link Text#isSetFontSize()}.
   */
  @Test
  public void testIsSetFontSize() {
    short fontSize=18;
    Text textType=new Text();
    assertTrue(!textType.isSetFontSize());
    textType.setFontSize(fontSize);
    assertTrue(textType.isSetFontSize());
  }


  /**
   * Test method for {@link Text#isSetFontStyleItalic()}.
   */
  @Test
  public void testIsSetFontStyleItalic() {
    Text textType=new Text();
    assertTrue(!textType.isSetFontStyleItalic());
    textType.setFontStyleItalic(true);
    assertTrue(textType.isSetFontStyleItalic());
  }


  /**
   * Test method for {@link Text#isSetFontWeightBold()}.
   */
  @Test
  public void testIsSetFontWeightBold() {
    Text textType=new Text();
    assertTrue(!textType.isSetFontWeightBold());
    textType.setFontWeightBold(true);
    assertTrue(textType.isSetFontWeightBold());
  }


  /**
   * Test method for {@link Text#isSetTextAnchor()}.
   */
  @Test
  public void testIsSetTextAnchor() {
    Text textType=new Text();
    HTextAnchor anchor=HTextAnchor.MIDDLE;
    assertTrue(!textType.isSetTextAnchor());
    textType.setTextAnchor(anchor);
    assertTrue(textType.isSetTextAnchor());
  }


  /**
   * Test method for {@link Text#isSetVTextAnchor()}.
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
   * Test method for {@link Text#isSetX()}.
   */
  @Test
  public void testIsSetX() {
    Text textType=new Text();
    textType.setX(0.02d);
    assertTrue(textType.isSetX());
  }


  /**
   * Test method for {@link Text#isSetY()}.
   */
  @Test
  public void testIsSetY() {
    Text textType=new Text();
    textType.setY(0.02d);
    assertTrue(textType.isSetY());
  }


  /**
   * Test method for {@link Text#isSetZ()}.
   */
  @Test
  public void testIsSetZ() {
    Text textType=new Text();
    textType.setZ(0.02d);
    assertTrue(textType.isSetZ());
  }


  /**
   * Test method for {@link Text#setAbsoluteX(boolean)}.
   */
  @Test
  public void testSetAbsoluteX() {
    Text textType=new Text();
    textType.setAbsoluteX(true);
    assertEquals("AbsoluteVarError",true,textType.isAbsoluteX());
  }


  /**
   * Test method for {@link Text#setAbsoluteY(boolean)}.
   */
  @Test
  public void testSetAbsoluteY() {
    Text textType=new Text();
    textType.setAbsoluteY(true);
    assertEquals("AbsoluteVarError",true,textType.isAbsoluteY());
  }


  /**
   * Test method for {@link Text#setAbsoluteZ(boolean)}.
   */
  @Test
  public void testSetAbsoluteZ() {
    Text textType=new Text();
    textType.setAbsoluteZ(true);
    assertTrue(textType.isAbsoluteZ());
  }


  /**
   * Test method for {@link Text#setFontFamily(FontFamily)}.
   */
  @Test
  public void testSetFontFamily() {
    FontFamily fontType=FontFamily.MONOSPACE;
    Text textType=new Text();
    assertTrue(!textType.isSetFontFamily());
    textType.setFontFamily(fontType);
    assertEquals("setFontFamilyError",textType.getFontFamily(),fontType);
  }


  /**
   * Test method for {@link Text#setFontSize(short)}.
   */
  @Test
  public void testSetFontSize() {
    short fontSize=19;
    Text textType=new Text();
    assertTrue(!textType.isSetFontSize());
    textType.setFontSize(fontSize);
    assertEquals("setFontSizeError",textType.getFontSize(),fontSize);
  }


  /**
   * Test method for {@link Text#setFontStyleItalic(boolean)}.
   */
  @Test
  public void testSetFontStyleItalic() {
    Text textType=new Text();
    assertTrue(!textType.isSetFontStyleItalic());
    textType.setFontStyleItalic(true);
    assertTrue(textType.isFontStyleItalic());
  }


  /**
   * Test method for {@link Text#setFontWeightBold(boolean)}.
   */
  @Test
  public void testSetFontWeightBold() {
    Text textType=new Text();
    textType.setFontWeightBold(true);
    assertTrue(textType.isFontWeightBold());
  }


  /**
   * Test method for {@link Text#setTextAnchor(HTextAnchor)}.
   */
  @Test
  public void testSetTextAnchor() {
    Text textType=new Text();
    HTextAnchor anchor=HTextAnchor.END;
    textType.setTextAnchor(anchor);
    assertEquals("textAnchorError",anchor,textType.getTextAnchor());
    anchor=HTextAnchor.MIDDLE;
    textType.setTextAnchor(anchor);
    assertEquals("textAnchorError",anchor,textType.getTextAnchor());
    anchor=HTextAnchor.START;
    textType.setTextAnchor(anchor);
    assertEquals("textAnchorError",anchor,textType.getTextAnchor());
  }


  /**
   * Test method for {@link Text#setVTextAnchor(VTextAnchor)}.
   */
  @Test
  public void testSetVTextAnchor() {
    Text textType=new Text();
    VTextAnchor anchor=VTextAnchor.BOTTOM;
    textType.setVTextAnchor(anchor);
    assertEquals("VTextAnchorError",anchor,textType.getVTextAnchor());
    anchor=VTextAnchor.MIDDLE;
    textType.setVTextAnchor(anchor);
    assertEquals("VTextAnchorError",anchor,textType.getVTextAnchor());
    anchor=VTextAnchor.TOP;
    textType.setVTextAnchor(anchor);
    assertEquals("VTextAnchorError",anchor,textType.getVTextAnchor());
  }


  /**
   * Test method for {@link Text#setX(double)}.
   */
  @Test
  public void testSetX() {
    Text textType=new Text();
    assertTrue(!textType.isSetX());
    textType.setX(0.02d);
    assertEquals(textType.getX(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link Text#setY(double)}.
   */
  @Test
  public void testSetY() {
    Text textType=new Text();
    assertTrue(!textType.isSetY());
    textType.setY(0.02d);
    assertEquals(textType.getY(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link Text#setZ(double)}.
   */
  @Test
  public void testSetZ() {
    Text textType=new Text();
    assertTrue(!textType.isSetZ());
    textType.setZ(0.02d);
    assertEquals(textType.getZ(),0.02d,0.00000001d);
  }
}
