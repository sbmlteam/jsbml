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
 * 6. Marquette University, Milwaukee, WI USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.layout.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.ext.layout.GraphicalObject;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.ext.layout.ReactionGlyph;
import org.sbml.jsbml.ext.layout.TextGlyph;

/**
 * @author Ibrahim Vazirabad
 * @version $1713 $
 * @since 1.0
 */
public class TextGlyphTest {

  /**
   * Test method for {@link org.sbml.jsbml.ext.layout.TextGlyph#getGraphicalObject()}.
   */
  @Test
  public void testGetGraphicalObject() {
    TextGlyph test=new TextGlyph();
    test.setGraphicalObject("testName");
    assertEquals("Error",test.getGraphicalObject(),"testName");
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.layout.TextGlyph#getGraphicalObjectInstance()}.
   */
  @Test
  public void testGetGraphicalObjectInstance() {
    SBMLDocument d = new SBMLDocument(3,1);
    Model model = d.createModel("extensionModel");

    LayoutModelPlugin lModel = new LayoutModelPlugin(model);
    Layout layout = lModel.createLayout("layout");

    TextGlyph test=new TextGlyph();
    GraphicalObject go=new GraphicalObject("Graphics");
    test.setGraphicalObject(go);
    layout.addTextGlyph(test);
    layout.addGraphicalObject(go);
    model.addExtension(LayoutConstants.getNamespaceURI(model.getLevel(), model.getVersion()), lModel);
    assertEquals("InstanceError",test.getGraphicalObjectInstance().getId(),go.getId());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.layout.TextGlyph#getOriginOfText()}.
   */
  @Test
  public void testGetOriginOfText() {
    assertTrue(new TextGlyph().getOriginOfText() instanceof String);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.layout.TextGlyph#getOriginOfTextInstance()}.
   */
  @Test
  public void testGetOriginOfTextInstance() {
    SBMLDocument d = new SBMLDocument(3,1);
    Model model = d.createModel("extensionModel");

    LayoutModelPlugin lModel = new LayoutModelPlugin(model);
    Layout layout = lModel.createLayout("layout");

    TextGlyph test=new TextGlyph();
    ReactionGlyph rg=new ReactionGlyph("react_r1", model.getLevel(), model.getVersion());
    layout.addTextGlyph(test);
    layout.addReactionGlyph(rg);
    test.setOriginOfText(rg);
    model.addExtension(LayoutConstants.getNamespaceURI(model.getLevel(), model.getVersion()), lModel);
    assertEquals("InstanceError",test.getOriginOfTextInstance().getId(),rg.getId());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.layout.TextGlyph#getText()}.
   */
  @Test
  public void testGetText() {
    String reaction="reaction";
    TextGlyph test=new TextGlyph();
    test.setText(reaction);
    assertTrue(reaction.equals(test.getText()));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.layout.TextGlyph#isSetGraphicalObject()}.
   */
  @Test
  public void testIsSetGraphicalObject() {
    TextGlyph text=new TextGlyph();
    GraphicalObject go=new GraphicalObject("Graphics");
    text.setGraphicalObject(go);
    assertTrue(text.isSetGraphicalObject());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.layout.TextGlyph#isSetGraphicalObjectInstance()}.
   */
  @Test
  public void testIsSetGraphicalObjectInstance() {
    SBMLDocument d = new SBMLDocument(3,1);
    Model model = d.createModel("extensionModel");

    LayoutModelPlugin lModel = new LayoutModelPlugin(model);
    Layout layout = lModel.createLayout("layout");
    model.addExtension(LayoutConstants.getNamespaceURI(model.getLevel(), model.getVersion()), lModel);

    TextGlyph test=new TextGlyph();
    GraphicalObject go=new GraphicalObject("Graphics");
    test.setGraphicalObject(go);
    layout.addTextGlyph(test);
    layout.addGraphicalObject(go);
    assertTrue(test.isSetGraphicalObjectInstance());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.layout.TextGlyph#isSetOriginOfText()}.
   */
  @Test
  public void testIsSetOriginOfText() {
    TextGlyph test=new TextGlyph();
    test.setOriginOfText("TextGlyph");
    assertTrue(test.isSetOriginOfText());
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.layout.TextGlyph#isSetText()}.
   */
  @Test
  public void testIsSetText() {
    String text="TEXT";
    TextGlyph test=new TextGlyph();
    test.setText(text);
    assertTrue(text instanceof String);
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.layout.TextGlyph#setGraphicalObject(String)}.
   */
  @Test
  public void testSetGraphicalObject() {
    String str="GraphicalObject";
    TextGlyph text=new TextGlyph();
    GraphicalObject go=new GraphicalObject(str);
    text.setGraphicalObject(go);
    assertTrue(str.equals(text.getGraphicalObject()));

  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.layout.TextGlyph#setOriginOfText(NamedSBase)}.
   */
  @Test
  public void testSetOriginOfTextNamedSBase() {
    String str="Glyph2";
    TextGlyph glyph1=new TextGlyph("Glyph1");
    TextGlyph glyph2=new TextGlyph(str);
    glyph1.setOriginOfText(glyph2);
    assertTrue(str.equals(glyph1.getOriginOfText()));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.layout.TextGlyph#setOriginOfText(String)}.
   */
  @Test
  public void testSetOriginOfTextString() {
    String str="Reference";
    TextGlyph test=new TextGlyph();
    test.setOriginOfText(str);
    assertTrue(str.equals(test.getOriginOfText()));
  }

  /**
   * Test method for {@link org.sbml.jsbml.ext.layout.TextGlyph#setText(String)}.
   */
  @Test
  public void testSetText() {
    String text="TEXT";
    TextGlyph test=new TextGlyph();
    test.setText(text);
    assertTrue(text.equals(test.getText()));
  }
}
