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

import org.junit.Test;
import org.sbml.jsbml.ext.render.Text;
import org.sbml.jsbml.ext.render.FontFamily;
import org.sbml.jsbml.ext.render.HTextAnchor;
import org.sbml.jsbml.ext.render.RelAbsVector;
import org.sbml.jsbml.ext.render.VTextAnchor;


/**
 * @author Ibrahim Vazirabad
 * @author Onur &Oumlzel
 * @since 1.0
 */
public class TextTest {

  /**
   * Test method for {@link Text#getFontFamily()} with {@link String} as the type of the font.
   */
  @Test
  public void testGetFontFamily() {
    String fontType= "monospace";
    Text textType=new Text();
    assertTrue(!textType.isSetFontFamily());
    textType.setFontFamily(fontType);
    assertEquals("getFontFamily",fontType,textType.getFontFamily());
  }
  
  /**
   * Test method for {@link Text#getFontFamily()} with {@link FontFamily} type as the type of the font.
   */
  @Test
  public void testGetFontFamilyWithEnum() {
    FontFamily fontType= FontFamily.MONOSPACE;
    Text textType=new Text();
    assertTrue(!textType.isSetFontFamily());
    textType.setFontFamily(fontType.toString());
    assertEquals("getFontFamily",fontType.name().toLowerCase() ,textType.getFontFamily());
  }

  /**
   * Test method for {@link Text#getFontSize()}.
   */
  @Test
  public void testGetFontSize() {
    RelAbsVector fontSize=new RelAbsVector(18);
    Text textType=new Text();
    assertTrue(!textType.isSetFontSize());
    textType.setFontSize(fontSize);
    assertEquals("getFontSizeError",fontSize,textType.getFontSize());
  }
  
  /**
   * Test method for {@link Text#getText()}
   */
  @Test
  public void testGetText() {
    Text textType = new Text();
    assertFalse(textType.isSetText());
    textType.setText("...");
    assertEquals("getTextError", "...", textType.getText());
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
    textType.setX(new RelAbsVector(0.02d));
    assertEquals(textType.getX().getAbsoluteValue(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link Text#getY()}.
   */
  @Test
  public void testGetY() {
    Text textType=new Text();
    assertTrue(!textType.isSetY());
    textType.setY(new RelAbsVector(0.02d));
    assertEquals(textType.getY().getAbsoluteValue(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link Text#getZ()}.
   */
  @Test
  public void testGetZ() {
    Text textType=new Text();
    assertTrue(!textType.isSetZ());
    textType.setZ(new RelAbsVector(0.02d));
    assertEquals(textType.getZ().getAbsoluteValue(),0.02d,0.00000001d);
  }

  /**
   * Test method for {@link Text#isSetFontFamily()} with {@link String} as the type of the font.
   */
  @Test
  public void testIsSetFontFamily() {
    String fontType= "monospace";
    Text textType=new Text();
    assertTrue(!textType.isSetFontFamily());
    textType.setFontFamily(fontType);
    assertTrue(textType.isSetFontFamily());
  }
  
  /**
   * Test method for {@link Text#isSetFontFamily()} with {@link FontFamily} type as the type of the font.
   */
  @Test
  public void testIsSetFontFamilyWithEnum() {
    FontFamily fontType = FontFamily.MONOSPACE;
    Text textType = new Text();
    assertTrue(!textType.isSetFontFamily());
    textType.setFontFamily(fontType.toString());
    assertTrue(textType.isSetFontFamily());
  }


  /**
   * Test method for {@link Text#isSetFontSize()}.
   */
  @Test
  public void testIsSetFontSize() {
    RelAbsVector fontSize=new RelAbsVector(18);
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
   * Test method for {@link Text#isSetText()}
   */
  @Test
  public void testIsSetText() {
    Text textType = new Text();
    assertFalse(textType.isSetText());
    textType.setText("");
    // Deviation from libSBML!
    assertTrue("isSetTextError: behaves like libSBML", textType.isSetText());
    
    textType.setText("Some nonempty string");
    assertTrue("isSetTextError",textType.isSetText());
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
    textType.setX(new RelAbsVector(0.02d));
    assertTrue(textType.isSetX());
  }


  /**
   * Test method for {@link Text#isSetY()}.
   */
  @Test
  public void testIsSetY() {
    Text textType=new Text();
    textType.setY(new RelAbsVector(0.02d));
    assertTrue(textType.isSetY());
  }


  /**
   * Test method for {@link Text#isSetZ()}.
   */
  @Test
  public void testIsSetZ() {
    Text textType=new Text();
    textType.setZ(new RelAbsVector(0.02d));
    assertTrue(textType.isSetZ());
  }
  
  /**
   * Test method for {@link Text#setFontFamily(FontFamily)} with {@link String} as the type of the font..
   */
  @Test
  public void testSetFontFamily() {
    String fontType= "monospace";
    Text textType=new Text();
    assertTrue(!textType.isSetFontFamily());
    textType.setFontFamily(fontType);
    assertEquals("setFontFamilyError",textType.getFontFamily(),fontType);
  }
  
  /**
   * Test method for {@link Text#setFontFamily(FontFamily)} with {@link FontFamily} type as the type of the font.
   */
  @Test
  public void testSetFontFamilyWithEnum() {
    FontFamily fontType= FontFamily.MONOSPACE;
    Text textType=new Text();
    assertTrue(!textType.isSetFontFamily());
    textType.setFontFamily(fontType.toString());
    assertEquals("setFontFamilyError", textType.getFontFamily(), fontType.name().toLowerCase());
  }


  /**
   * Test method for {@link Text#setFontSize(short)}.
   */
  @Test
  public void testSetFontSize() {
    RelAbsVector fontSize=new RelAbsVector(19);
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
   * Tests behaviour of {@link Text#setText(String)} for usual arguments (non-null Strings)
   */
  @Test
  public void testSetText() {
    Text textType = new Text();
    textType.setText("Glc");
    assertEquals("setTextError", "Glc", textType.getText());
    textType.setText("");
    assertEquals("setTextError", "", textType.getText());
  }

  
  /**
   * Tests behaviour for setting the text to {@code null} via
   * {@link Text#setText(String)} instead of {@link Text#unsetText()}
   */
  @Test
  public void testSetTextToNull() {
    Text textType = new Text();
    assertFalse(textType.isSetText());
    textType.setText("Not null");
    assertTrue(textType.isSetText());
    textType.setText(null);
    assertFalse(textType.isSetText());
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
    textType.setX(new RelAbsVector(0.02d));
    assertEquals(textType.getX().getAbsoluteValue(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link Text#setY(double)}.
   */
  @Test
  public void testSetY() {
    Text textType=new Text();
    assertTrue(!textType.isSetY());
    textType.setY(new RelAbsVector(0.02d));
    assertEquals(textType.getY().getAbsoluteValue(),0.02d,0.00000001d);
  }


  /**
   * Test method for {@link Text#setZ(double)}.
   */
  @Test
  public void testSetZ() {
    Text textType=new Text();
    assertTrue(!textType.isSetZ());
    textType.setZ(new RelAbsVector(0.02d));
    assertEquals(textType.getZ().getAbsoluteValue(),0.02d,0.00000001d);
  }
  
  /**
   * Test method for {@link Text#unsetText()}
   */
  @Test
  public void testUnsetText() {
    Text textType = new Text();
    assertFalse(textType.isSetText());
    textType.setText("ATP");
    assertTrue(textType.isSetText());
    textType.unsetText();
    assertFalse(textType.isSetText());
  }
}
