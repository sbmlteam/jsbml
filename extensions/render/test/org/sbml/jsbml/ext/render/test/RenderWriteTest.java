/*
 *
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

import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.Test;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.ext.layout.LayoutConstants;
import org.sbml.jsbml.ext.layout.LayoutModelPlugin;
import org.sbml.jsbml.ext.layout.SpeciesGlyph;
import org.sbml.jsbml.ext.render.HTextAnchor;
import org.sbml.jsbml.ext.render.LocalRenderInformation;
import org.sbml.jsbml.ext.render.LocalStyle;
import org.sbml.jsbml.ext.render.Rectangle;
import org.sbml.jsbml.ext.render.RenderConstants;
import org.sbml.jsbml.ext.render.RenderGraphicalObjectPlugin;
import org.sbml.jsbml.ext.render.RenderGroup;
import org.sbml.jsbml.ext.render.RenderLayoutPlugin;
import org.sbml.jsbml.ext.render.RenderListOfLayoutsPlugin;
import org.sbml.jsbml.ext.render.Text;
import org.sbml.jsbml.ext.render.Transformation2D;
import org.sbml.jsbml.ext.render.VTextAnchor;

/**
 * @author Florian Mittag
 * @author Andreas Dr&auml;ger
 * @author David Vetter
 * @since 0.8
 */
public class RenderWriteTest {

  /**
   * @param args
   * @throws XMLStreamException
   * @throws SBMLException
   */
  public static void main(String[] args) throws SBMLException, XMLStreamException {
    SBMLDocument doc = new SBMLDocument(3, 1);
    Model m = doc.createModel("m1");
    LayoutModelPlugin elm = new LayoutModelPlugin(m);
    m.addExtension(LayoutConstants.getNamespaceURI(m.getLevel(), m.getVersion()), elm);
    Layout l1 = elm.createLayout("l1");
    l1.createCompartmentGlyph("test");
    RenderLayoutPlugin rlp = new RenderLayoutPlugin(l1);
    rlp.createLocalRenderInformation("info1");
    l1.addExtension(RenderConstants.namespaceURI, rlp);

    SBMLWriter.write(doc, System.out, ' ', (short) 2);
  }
  
  
  
  private SBMLDocument document;
  private Layout layout;
  private ListOf<Layout> listOfLayouts;
  private SBMLWriter writer;
  
  @Before
  public void provideSBMLDocument() {
    writer = new SBMLWriter(' ', (short) 2);
    
    document = new SBMLDocument(3, 1);
    Model model = document.createModel("m0");
    LayoutModelPlugin elm = new LayoutModelPlugin(model);
    model.addExtension(
      LayoutConstants.getNamespaceURI(model.getLevel(), model.getVersion()),
      elm);
    layout = elm.createLayout("l0");
    layout.createCompartmentGlyph("test");
    listOfLayouts = elm.getListOfLayouts();
  }

  
  /**
   * Checks that no information w.r.t. {@link RenderLayoutPlugin}s is lost when
   * writing and then reading an {@link SBMLDocument}
   * 
   * @throws SBMLException
   * @throws XMLStreamException
   */
  @Test
  public void testWriteReadLayoutPlugin() throws SBMLException, XMLStreamException {
    RenderLayoutPlugin rlp = new RenderLayoutPlugin(layout);
    rlp.createLocalRenderInformation("info1");
    layout.addExtension(RenderConstants.namespaceURI, rlp);
    
    String written = writer.writeSBMLToString(document);
    SBMLDocument read = SBMLReader.read(written);
    LayoutModelPlugin readELM =
      (LayoutModelPlugin) read.getModel()
                              .getPlugin(LayoutConstants.namespaceURI);
    assertFalse(readELM == null);
    assertEquals(rlp,
      (RenderLayoutPlugin) readELM.getLayout(0)
                                  .getPlugin(RenderConstants.namespaceURI));
  }
  
  
  /**
   * Checks that no information w.r.t. {@link RenderListOfLayoutsPlugin}s is
   * lost when writing and then reading an {@link SBMLDocument}
   * 
   * @throws SBMLException
   * @throws XMLStreamException
   */
  @Test
  public void testWriteReadListOfLayoutsPlugin() throws SBMLException, XMLStreamException {
    RenderListOfLayoutsPlugin rlolp = new RenderListOfLayoutsPlugin(listOfLayouts);
    rlolp.createGlobalRenderInformation("global");
    listOfLayouts.addExtension(RenderConstants.namespaceURI, rlolp);
    
    String written = writer.writeSBMLToString(document);
    System.out.println("LoLs-Plugin\n" + written + "\n");
    SBMLDocument read = SBMLReader.read(written);
    System.out.println("Read-in\n" + writer.writeSBMLToString(read) + "\n\n");
    LayoutModelPlugin readELM =
      (LayoutModelPlugin) read.getModel()
                              .getPlugin(LayoutConstants.namespaceURI);
    assertFalse(readELM == null);
    // TODO: found problem: globalRenderInformation is counted as a Layout! That
    // is wrong and causes errors when trying to access the layouts in the list
    ListOf list = readELM.getListOfLayouts();
    System.out.println(list.get(0));
    System.out.println(list.get(1));
    assertEquals(rlolp,
      (RenderListOfLayoutsPlugin) readELM.getListOfLayouts().getPlugin(
        RenderConstants.namespaceURI));
  }
  
  
  /**
   * Checks that no information w.r.t. {@link RenderGraphicalObjectPlugin}s is
   * lost when writing and then reading an {@link SBMLDocument}
   * 
   * @throws SBMLException
   * @throws XMLStreamException
   */
  @Test
  public void testWriteReadGraphicalObjectPlugin() throws SBMLException, XMLStreamException {
    SpeciesGlyph sg = layout.createSpeciesGlyph("sg1");
    RenderGraphicalObjectPlugin rgop = new RenderGraphicalObjectPlugin(sg);
    // cf. Render-specification p. 14 
    rgop.setObjectRole("SBO-0000285-clone");
    sg.addExtension(RenderConstants.namespaceURI, rgop);
    
    String written = writer.writeSBMLToString(document);
    SBMLDocument read = SBMLReader.read(written);
    LayoutModelPlugin readELM =
      (LayoutModelPlugin) read.getModel()
                              .getPlugin(LayoutConstants.namespaceURI);
    assertFalse(readELM == null);
    assertEquals(rgop,
      (RenderGraphicalObjectPlugin) readELM.getLayout(0).getSpeciesGlyph("sg1")
                                           .getPlugin(
                                             RenderConstants.namespaceURI));
  }
  
  /**
   * Checks that no information w.r.t. {@link Text}s is lost when
   * writing and then reading an {@link SBMLDocument}
   * 
   * @throws SBMLException
   * @throws XMLStreamException
   */
  @Test
  public void testWriteReadText() throws SBMLException, XMLStreamException {
    RenderGroup group = new RenderGroup();
    Text questionmark = group.createText();
    questionmark.setFontSize((short) 10);
    questionmark.setFontFamily("monospace");
    questionmark.setTextAnchor(HTextAnchor.START);
    questionmark.setVTextAnchor(VTextAnchor.TOP);    
    questionmark.setX(0); questionmark.setAbsoluteX(true);
    questionmark.setY(0); questionmark.setAbsoluteY(true);
    questionmark.setText("?");
    
    LocalStyle style = new LocalStyle(group);
    style.setId("localstyle");
    
    RenderLayoutPlugin rlp = new RenderLayoutPlugin(layout);
    LocalRenderInformation lri = rlp.createLocalRenderInformation("info1");
    lri.addLocalStyle(style);
    
    layout.addExtension(RenderConstants.namespaceURI, rlp);
    
    String written = writer.writeSBMLToString(document);
    SBMLDocument read = SBMLReader.read(written);
    
    LayoutModelPlugin readELM =
        (LayoutModelPlugin) read.getModel()
                                .getPlugin(LayoutConstants.namespaceURI);
    assertFalse(readELM == null);
    // trainwreck to retrieve the text-object
    Text readText = (Text) ((RenderLayoutPlugin) readELM.getLayout(0).getPlugin(
      RenderConstants.namespaceURI)).getLocalRenderInformation(0)
                                    .getListOfLocalStyles().get(0).getGroup()
                                    .getElement(0);
    assertTrue("This test contains an error: questionmark's text need be set",
      questionmark.isSetText());
    assertTrue("The read-in text lacks its actual text", readText.isSetText());
    assertEquals(questionmark, readText);
  }
  
  @Test
  public void testWriteReadTransformation2D() throws SBMLException, XMLStreamException {
    RenderGroup group = new RenderGroup();
    Rectangle rectangle = group.createRectangle();
    rectangle.setHeight(20); rectangle.setAbsoluteHeight(true);
    rectangle.setWidth(10); rectangle.setAbsoluteWidth(true);
    rectangle.setX(2); rectangle.setAbsoluteX(true);
    rectangle.setY(0); rectangle.setAbsoluteY(true);
    
    rectangle.setTransform(new Double[] {new Double(0), new Double(1),
      new Double(-1), new Double(0), new Double(4), new Double(-2)});
    
    LocalStyle style = new LocalStyle(group);
    style.setId("localstyle");
    
    RenderLayoutPlugin rlp = new RenderLayoutPlugin(layout);
    LocalRenderInformation lri = rlp.createLocalRenderInformation("info1");
    lri.addLocalStyle(style);
    
    layout.addExtension(RenderConstants.namespaceURI, rlp);
    
    String written = writer.writeSBMLToString(document);
    SBMLDocument read = SBMLReader.read(written);
    
    LayoutModelPlugin readELM =
        (LayoutModelPlugin) read.getModel()
                                .getPlugin(LayoutConstants.namespaceURI);
    assertFalse(readELM == null);
    // trainwreck to retrieve the rectangle-object
    Transformation2D readTransformation =
      ((RenderLayoutPlugin) readELM.getLayout(0).getPlugin(
        RenderConstants.namespaceURI)).getLocalRenderInformation(0)
                                      .getListOfLocalStyles().get(0).getGroup()
                                      .getElement(0);
    assertEquals((Transformation2D) rectangle, readTransformation);
  }
}
