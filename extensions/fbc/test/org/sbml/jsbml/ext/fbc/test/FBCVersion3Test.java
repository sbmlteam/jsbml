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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.fbc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.FBCVariableType;
import org.sbml.jsbml.ext.fbc.FluxObjective;
import org.sbml.jsbml.ext.fbc.Objective;
import org.sbml.jsbml.xml.parsers.FBCParser;

/**
 * Tests the elements and attributes of FBC version 3.
 * 
 * @since 1.7
 */
public class FBCVersion3Test {

  /**
   * Creates a document with the given fbc namespace and a model with two
   * reactions and an objective with a flux objective per reaction.
   * 
   * @param fbcNamespace the namespace of the fbc package
   * @return the document
   */
  static SBMLDocument createDocument(String fbcNamespace) {
    SBMLDocument doc = new SBMLDocument(3, 1);
    doc.enablePackage(fbcNamespace);
    Model model = doc.createModel("m");
    model.createCompartment("c").setConstant(true);
    for (String rId : new String[] {"r1", "r2"}) {
      Reaction reaction = model.createReaction(rId);
      reaction.setReversible(false);
      reaction.setFast(false);
    }
    FBCModelPlugin fbc = (FBCModelPlugin) model.getPlugin(FBCConstants.shortLabel);
    fbc.setStrict(false);
    Objective objective = fbc.createObjective("obj", Objective.Type.MAXIMIZE);
    fbc.setActiveObjective(objective);
    objective.createFluxObjective(null, null, 1d, "r1");
    objective.createFluxObjective(null, null, 2d, "r2");
    return doc;
  }

  /**
   * Writes the document to a String and reads it back.
   * 
   * @param doc the document
   * @return the document read from the written XML
   */
  static SBMLDocument writeAndRead(SBMLDocument doc) throws XMLStreamException {
    return SBMLReader.read(new SBMLWriter().writeSBMLToString(doc));
  }

  /**
   * The namespace of version 3 is known, new documents still use version 2.
   */
  @Test
  public void namespace() {
    String v3 = "http://www.sbml.org/sbml/level3/version1/fbc/version3";
    assertEquals(v3, FBCConstants.namespaceURI_L3V1V3);
    assertTrue(FBCConstants.namespaces.contains(v3));
    assertEquals(v3, FBCConstants.getNamespaceURI(3, 1, 3));
    assertEquals(v3, new FBCParser().getNamespaceFor(3, 1, 3));
    assertTrue(new FBCParser().getPackageNamespaces().contains(v3));
    assertEquals(FBCConstants.namespaceURI_L3V1V2, FBCConstants.namespaceURI);
    assertEquals(FBCConstants.namespaceURI_L3V1V2, FBCConstants.getNamespaceURI(3, 1));

    // enabling the package with its short label enables version 2
    SBMLDocument doc = new SBMLDocument(3, 1);
    doc.enablePackage(FBCConstants.shortLabel);
    assertTrue(doc.isPackageEnabled(FBCConstants.namespaceURI_L3V1V2));
    assertFalse(doc.isPackageEnabled(FBCConstants.namespaceURI_L3V1V3));
  }

  /**
   * {@link FBCVariableType#fromString(String)} and {@link FBCVariableType#toString()}.
   */
  @Test
  public void variableType() {
    assertEquals(FBCVariableType.LINEAR, FBCVariableType.fromString("linear"));
    assertEquals(FBCVariableType.QUADRATIC, FBCVariableType.fromString("quadratic"));
    assertEquals("linear", FBCVariableType.LINEAR.toString());
    assertEquals("quadratic", FBCVariableType.QUADRATIC.toString());
    try {
      FBCVariableType.fromString("cubic");
      fail("cubic is no variable type");
    } catch (IllegalArgumentException exc) {
      // expected
    }
  }

  /**
   * The variable type of a flux objective is written with the fbc prefix and
   * read back.
   */
  @Test
  public void fluxObjectiveVariableType() throws XMLStreamException {
    SBMLDocument doc = createDocument(FBCConstants.namespaceURI_L3V1V3);
    Objective objective = ((FBCModelPlugin) doc.getModel().getPlugin(FBCConstants.shortLabel)).getObjective(0);
    FluxObjective fo1 = objective.getListOfFluxObjectives().get(0);
    assertFalse(fo1.isSetVariableType());
    assertNull(fo1.getVariableType());
    fo1.setVariableType(FBCVariableType.LINEAR);
    objective.getListOfFluxObjectives().get(1).setVariableType("quadratic");

    String xml = new SBMLWriter().writeSBMLToString(doc);
    assertTrue(xml, xml.contains("xmlns:fbc=\"" + FBCConstants.namespaceURI_L3V1V3 + "\""));
    assertTrue(xml, xml.contains("fbc:variableType=\"linear\""));
    assertTrue(xml, xml.contains("fbc:variableType=\"quadratic\""));

    SBMLDocument read = writeAndRead(doc);
    Objective readObjective = ((FBCModelPlugin) read.getModel().getPlugin(FBCConstants.shortLabel)).getObjective(0);
    assertEquals(FBCVariableType.LINEAR, readObjective.getListOfFluxObjectives().get(0).getVariableType());
    assertEquals(FBCVariableType.QUADRATIC, readObjective.getListOfFluxObjectives().get(1).getVariableType());
    assertEquals(objective, readObjective);
    assertEquals(objective, objective.clone());

    FluxObjective clone = fo1.clone();
    assertEquals(FBCVariableType.LINEAR, clone.getVariableType());
    assertTrue(clone.unsetVariableType());
    assertFalse(clone.unsetVariableType());
    assertFalse(clone.equals(fo1));
  }

  /**
   * A document of version 2 without variable types is written without them.
   */
  @Test
  public void version2Unchanged() throws XMLStreamException {
    SBMLDocument doc = createDocument(FBCConstants.namespaceURI_L3V1V2);
    String xml = new SBMLWriter().writeSBMLToString(doc);
    assertTrue(xml, xml.contains("xmlns:fbc=\"" + FBCConstants.namespaceURI_L3V1V2 + "\""));
    assertFalse(xml, xml.contains("variableType"));
    assertEquals(xml, new SBMLWriter().writeSBMLToString(writeAndRead(doc)));
  }

}
