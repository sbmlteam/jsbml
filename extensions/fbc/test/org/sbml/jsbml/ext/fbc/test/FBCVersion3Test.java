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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.ext.fbc.FBCConstants;
import org.sbml.jsbml.ext.fbc.FBCModelPlugin;
import org.sbml.jsbml.ext.fbc.FBCVariableType;
import org.sbml.jsbml.ext.fbc.FluxObjective;
import org.sbml.jsbml.ext.fbc.Objective;
import org.sbml.jsbml.ext.fbc.UserDefinedConstraint;
import org.sbml.jsbml.ext.fbc.UserDefinedConstraintComponent;
import org.sbml.jsbml.xml.parsers.FBCParser;

/**
 * Tests the elements and attributes of FBC version 3.
 * 
 * @since 1.7
 */
public class FBCVersion3Test {

  /**
   * The fbc version 3 model written and validated by libSBML 5.21.
   */
  static final String LIBSBML_MODEL = "/org/sbml/jsbml/xml/test/data/fbc/fbc_v3_example_L3V1_fbcV3.xml";

  /**
   * Reads the fbc version 3 model written by libSBML.
   * 
   * @return the document
   */
  static SBMLDocument readLibsbmlModel() throws XMLStreamException {
    return SBMLReader.read(FBCVersion3Test.class.getResourceAsStream(LIBSBML_MODEL));
  }

  /**
   * @param doc a document
   * @return the fbc plugin of the model of the document
   */
  static FBCModelPlugin fbc(SBMLDocument doc) {
    return (FBCModelPlugin) doc.getModel().getPlugin(FBCConstants.shortLabel);
  }

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

  /**
   * Asserts the attributes of a {@link UserDefinedConstraintComponent}.
   */
  private static void assertComponent(UserDefinedConstraintComponent component, String id,
    String coefficient, String variable, String variable2, FBCVariableType variableType) {
    assertEquals(id, component.getId());
    assertEquals(coefficient, component.getCoefficient());
    assertEquals(variable, component.getVariable());
    assertEquals(variable2 != null, component.isSetVariable2());
    if (variable2 != null) {
      assertEquals(variable2, component.getVariable2());
    }
    assertEquals(variableType, component.getVariableType());
  }

  /**
   * Reads all fbc version 3 elements and attributes of the libSBML model.
   */
  @Test
  public void readLibsbml() throws XMLStreamException {
    SBMLDocument doc = readLibsbmlModel();
    assertEquals(FBCConstants.namespaceURI_L3V1V3, doc.getSBMLDocumentNamespaces().get("xmlns:fbc"));
    Model model = doc.getModel();
    FBCModelPlugin fbc = fbc(doc);

    // elements of fbc version 2
    assertEquals(3, fbc.getGeneProductCount());
    assertEquals("A", fbc.getGeneProduct("g1").getAssociatedSpecies());
    assertEquals(2, fbc.getObjectiveCount());
    assertEquals("obj_linear", fbc.getActiveObjective());
    assertEquals("zero", ((org.sbml.jsbml.ext.fbc.FBCReactionPlugin) model.getReaction("RGLX")
        .getPlugin(FBCConstants.shortLabel)).getLowerFluxBound());
    assertTrue(((org.sbml.jsbml.ext.fbc.FBCReactionPlugin) model.getReaction("RGLX")
        .getPlugin(FBCConstants.shortLabel)).isSetGeneProductAssociation());

    // variable types of the flux objectives
    FluxObjective linear = fbc.getObjective(0).getListOfFluxObjectives().get(0);
    assertEquals("RGDP", linear.getReaction());
    assertEquals(FBCVariableType.LINEAR, linear.getVariableType());
    FluxObjective quadratic = fbc.getObjective(1).getListOfFluxObjectives().get(0);
    assertEquals("RGLX", quadratic.getReaction());
    assertEquals(1d, quadratic.getCoefficient(), 0d);
    assertEquals(FBCVariableType.QUADRATIC, quadratic.getVariableType());

    // user defined constraints
    assertTrue(fbc.isSetListOfUserDefinedConstraints());
    assertEquals(3, fbc.getUserDefinedConstraintCount());

    UserDefinedConstraint uc1 = fbc.getUserDefinedConstraint(0);
    assertSame(uc1, fbc.getUserDefinedConstraint("uc1"));
    assertEquals("uc1", uc1.getId());
    assertEquals("RGLX - RBTK = 5", uc1.getName());
    assertEquals("meta_uc1", uc1.getMetaId());
    assertEquals("five", uc1.getLowerBound());
    assertEquals("five", uc1.getUpperBound());
    assertSame(model.getParameter("five"), uc1.getLowerBoundInstance());
    assertSame(model.getParameter("five"), uc1.getUpperBoundInstance());
    assertTrue(uc1.isSetAnnotation());
    assertEquals(2, uc1.getUserDefinedConstraintComponentCount());
    assertComponent(uc1.getUserDefinedConstraintComponent(0), "uc1_c1", "one", "RGLX", null, FBCVariableType.LINEAR);
    assertComponent(uc1.getUserDefinedConstraintComponent(1), "uc1_c2", "negone", "RBTK", null, FBCVariableType.LINEAR);
    assertSame(model.getParameter("one"), uc1.getUserDefinedConstraintComponent(0).getCoefficientInstance());
    assertSame(model.getReaction("RGLX"), uc1.getUserDefinedConstraintComponent(0).getVariableInstance());
    assertNull(uc1.getUserDefinedConstraintComponent(0).getVariable2Instance());

    UserDefinedConstraint uc2 = fbc.getUserDefinedConstraint("uc2");
    assertFalse(uc2.isSetName());
    assertEquals("two", uc2.getLowerBound());
    assertEquals("inf", uc2.getUpperBound());
    assertTrue(Double.isInfinite(uc2.getUpperBoundInstance().getValue()));
    assertComponent(uc2.getUserDefinedConstraintComponent(0), "uc2_c1", "two", "p1var", null, FBCVariableType.LINEAR);
    assertComponent(uc2.getUserDefinedConstraintComponent(1), "uc2_c2", "negone", "RGDP", null, FBCVariableType.LINEAR);
    // a variable can be a parameter
    assertSame(model.getParameter("p1var"), uc2.getUserDefinedConstraintComponent(0).getVariableInstance());

    UserDefinedConstraint uc3 = fbc.getUserDefinedConstraint("uc3");
    assertEquals(1, uc3.getUserDefinedConstraintComponentCount());
    UserDefinedConstraintComponent uc3c1 = uc3.getUserDefinedConstraintComponent(0);
    assertComponent(uc3c1, "uc3_c1", "one", "RGLX", "RBTK", FBCVariableType.QUADRATIC);
    assertSame(model.getReaction("RBTK"), uc3c1.getVariable2Instance());

    // the ids are registered in the model
    assertSame(uc3, model.findNamedSBase("uc3"));
    assertSame(uc3c1, model.findNamedSBase("uc3_c1"));

    // the tree
    assertSame(fbc.getListOfUserDefinedConstraints(), fbc.getChildAt(fbc.getChildCount() - 1));
    assertSame(uc1.getListOfUserDefinedConstraintComponents(), uc1.getChildAt(uc1.getChildCount() - 1));
    assertSame(uc1, uc1.getUserDefinedConstraintComponent(0).getParent().getParent());
  }

  /**
   * Writing the libSBML model and reading it back gives an equal document;
   * the user defined constraints are written after the objectives and gene
   * products, as libSBML does.
   * 
   * <p>The written XML is read by libSBML 5.21 (python-libsbml-experimental)
   * without errors (checked with {@code libsbml.readSBMLFromString(xml)} and
   * {@code checkConsistency()}): the only messages are the unit warnings of
   * the libSBML model itself.</p>
   */
  @Test
  public void writeAndReadLibsbml() throws XMLStreamException {
    SBMLDocument doc = readLibsbmlModel();
    String xml = new SBMLWriter().writeSBMLToString(doc);
    assertTrue(xml, xml.contains("xmlns:fbc=\"" + FBCConstants.namespaceURI_L3V1V3 + "\""));
    int objectives = xml.indexOf("<fbc:listOfObjectives");
    int geneProducts = xml.indexOf("<fbc:listOfGeneProducts");
    int constraints = xml.indexOf("<fbc:listOfUserDefinedConstraints");
    assertTrue(xml, (0 < objectives) && (objectives < geneProducts) && (geneProducts < constraints));
    assertTrue(xml, xml.contains("<fbc:userDefinedConstraintComponent"));
    assertTrue(xml, xml.contains("fbc:variable2=\"RBTK\""));
    assertTrue(xml, xml.contains("fbc:lowerBound=\"two\""));

    SBMLDocument read = SBMLReader.read(xml);
    assertEquals(doc.getModel(), read.getModel());
    assertEquals(fbc(doc).getListOfUserDefinedConstraints(), fbc(read).getListOfUserDefinedConstraints());
    assertEquals(xml, new SBMLWriter().writeSBMLToString(read));

    // a changed component makes the models differ
    fbc(read).getUserDefinedConstraint("uc3").getUserDefinedConstraintComponent(0).unsetVariable2();
    assertFalse(doc.getModel().equals(read.getModel()));
  }

  /**
   * Creates, clones, and changes user defined constraints.
   */
  @Test
  public void createUserDefinedConstraints() throws XMLStreamException {
    SBMLDocument doc = createDocument(FBCConstants.namespaceURI_L3V1V3);
    Model model = doc.getModel();
    Parameter lb = model.createParameter("lb");
    lb.setValue(0d);
    lb.setConstant(true);
    model.createParameter("ub").setConstant(true);
    model.createParameter("coef").setConstant(true);
    FBCModelPlugin fbc = fbc(doc);
    assertFalse(fbc.isSetListOfUserDefinedConstraints());
    assertEquals(0, fbc.getUserDefinedConstraintCount());

    UserDefinedConstraint constraint = fbc.createUserDefinedConstraint("con");
    constraint.setLowerBound("lb");
    constraint.setUpperBound("ub");
    UserDefinedConstraintComponent component = constraint.createUserDefinedConstraintComponent();
    component.setCoefficient("coef");
    component.setVariable("r1");
    component.setVariable2("r2");
    component.setVariableType(FBCVariableType.QUADRATIC);
    // a constraint without id
    UserDefinedConstraint noId = fbc.createUserDefinedConstraint();
    noId.setLowerBound("lb");
    noId.setUpperBound("ub");
    UserDefinedConstraintComponent linear = new UserDefinedConstraintComponent();
    linear.setCoefficient("coef");
    linear.setVariable("r1");
    linear.setVariableType("linear");
    assertTrue(noId.addUserDefinedConstraintComponent(linear));

    assertEquals(2, fbc.getUserDefinedConstraintCount());
    assertSame(lb, constraint.getLowerBoundInstance());
    assertSame(model.getParameter("ub"), constraint.getUpperBoundInstance());
    assertSame(model.getReaction("r2"), component.getVariable2Instance());
    assertNotNull(component.toString());
    assertTrue(constraint.toString(), constraint.toString().contains("lowerBound=lb"));

    UserDefinedConstraint clone = constraint.clone();
    assertEquals(constraint, clone);
    assertEquals(constraint.hashCode(), clone.hashCode());
    assertEquals(1, clone.getUserDefinedConstraintComponentCount());
    clone.getUserDefinedConstraintComponent(0).setVariableType(FBCVariableType.LINEAR);
    assertFalse(constraint.equals(clone));
    assertEquals(FBCVariableType.QUADRATIC, component.getVariableType());

    FBCModelPlugin fbcClone = fbc.clone();
    assertEquals(2, fbcClone.getUserDefinedConstraintCount());
    assertEquals(fbc.getListOfUserDefinedConstraints(), fbcClone.getListOfUserDefinedConstraints());
    assertEquals(model, model.clone());

    SBMLDocument read = writeAndRead(doc);
    assertEquals(model, read.getModel());
    UserDefinedConstraint readNoId = fbc(read).getUserDefinedConstraint(1);
    assertFalse(readNoId.isSetId());
    assertEquals(FBCVariableType.LINEAR, readNoId.getUserDefinedConstraintComponent(0).getVariableType());
    assertFalse(readNoId.getUserDefinedConstraintComponent(0).isSetVariable2());

    // unset and set lists
    ListOf<UserDefinedConstraintComponent> components = constraint.getListOfUserDefinedConstraintComponents();
    assertTrue(constraint.unsetListOfUserDefinedConstraintComponents());
    assertEquals(0, constraint.getUserDefinedConstraintComponentCount());
    constraint.setListOfUserDefinedConstraintComponents(components);
    assertEquals(1, constraint.getUserDefinedConstraintComponentCount());
    assertTrue(fbc.unsetListOfUserDefinedConstraints());
    assertFalse(fbc.isSetListOfUserDefinedConstraints());
    assertFalse(new SBMLWriter().writeSBMLToString(doc).contains("userDefinedConstraint"));
  }

}
