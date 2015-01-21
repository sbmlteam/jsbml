/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2015 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 6. The University of Utah, Salt Lake City, UT, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.arrays.test;

import static org.junit.Assert.assertTrue;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.SBMLWriter;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.Index;
import org.sbml.jsbml.ext.arrays.flattening.ArraysFlattening;
import org.sbml.jsbml.text.parser.ParseException;


/**
 * @author Leandro Watanabe
 * @version $Rev$
 * @since 1.0
 * @date Jul 17, 2014
 */
public class FlatteningTest {

  /**
   * 
   */
  @Test
  public void simpleTest() {
    try {
      SBMLDocument document = new SBMLDocument(3,1);

      Model model = document.createModel();

      Species spec = model.createSpecies("s");

      Parameter param = model.createParameter("n");

      param.setConstant(true);

      param.setValue(2);

      ArraysSBasePlugin arraysSBasePlugin = new ArraysSBasePlugin(spec);

      spec.addExtension(ArraysConstants.shortLabel, arraysSBasePlugin);

      spec.addExtension(ArraysConstants.shortLabel, arraysSBasePlugin);

      Dimension dim = arraysSBasePlugin.createDimension("i");

      dim.setArrayDimension(0);

      dim.setSize("n");


      Dimension dim2 = arraysSBasePlugin.createDimension("j");

      dim2.setArrayDimension(1);

      dim2.setSize("n");

      SBMLDocument flattened = ArraysFlattening.convert(document);

      assertTrue(flattened.getModel().getSpeciesCount() == 4);

    } catch (SBMLException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testFlatteningRule() {
    try {
      SBMLDocument doc = new SBMLDocument(3,1);
      Model model = doc.createModel();

      Parameter n = new Parameter("n");
      n.setValue(10);
      n.setConstant(true);
      model.addParameter(n);

      Parameter X = new Parameter("X");
      X.setValue(1);
      model.addParameter(X);
      InitialAssignment ia = model.createInitialAssignment();
      ia.setVariable("X");
      ia.setMath(ASTNode.parseFormula("selector({1,2,3,4,5,6,7,8,9,10},i)"));
      ArraysSBasePlugin iaArraysPlugin = new ArraysSBasePlugin(ia);
      ia.addExtension(ArraysConstants.shortLabel, iaArraysPlugin);
      Dimension iaDim = iaArraysPlugin.createDimension("i");
      iaDim.setArrayDimension(0);
      iaDim.setSize("n");
      Index indX = iaArraysPlugin.createIndex();
      indX.setArrayDimension(0);
      indX.setReferencedAttribute("symbol");
      indX.setMath(new ASTNode("i"));

      Parameter Y = new Parameter("Y");
      ArraysSBasePlugin arraysSBasePluginX = new ArraysSBasePlugin(X);
      X.addExtension(ArraysConstants.shortLabel, arraysSBasePluginX);

      Dimension dimX = new Dimension("i");
      dimX.setSize(n.getId());
      dimX.setArrayDimension(0);
      arraysSBasePluginX.addDimension(dimX);


      model.addParameter(Y);
      Y.setValue(2);
      ArraysSBasePlugin arraysSBasePluginY = new ArraysSBasePlugin(Y);

      Y.addExtension(ArraysConstants.shortLabel, arraysSBasePluginY);
      Dimension dimY = new Dimension("i");
      dimY.setSize(n.getId());
      dimY.setArrayDimension(0);

      arraysSBasePluginY.addDimension(dimY);

      AssignmentRule rule = new AssignmentRule();
      model.addRule(rule);

      ArraysSBasePlugin arraysSBasePluginRule = new ArraysSBasePlugin(rule);
      rule.addExtension(ArraysConstants.shortLabel, arraysSBasePluginRule);

      Dimension dimRule = new Dimension("i");
      dimRule.setSize(n.getId());
      dimRule.setArrayDimension(0);
      arraysSBasePluginRule.addDimension(dimRule);


      Index indexRule = arraysSBasePluginRule.createIndex();
      indexRule.setArrayDimension(0);
      indexRule.setReferencedAttribute("variable");
      ASTNode indexMath = new ASTNode();

      indexMath = ASTNode.diff(new ASTNode(n), new ASTNode(1), new ASTNode("i"));
      indexRule.setMath(indexMath);

      rule.setVariable("Y");
      ASTNode ruleMath = ASTNode.parseFormula("selector(X, i)");

      rule.setMath(ruleMath);
      SBMLWriter.write(doc, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
      SBMLDocument flattened = ArraysFlattening.convert(doc);
      SBMLWriter.write(flattened, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
      assertTrue(flattened.getModel().getParameterCount() == 21);
      assertTrue(flattened.getModel().getRuleCount() == 10);
    } catch (SBMLException e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testReaction() {
    try {
      SBMLDocument doc = new SBMLDocument(3,1);
      Model model = doc.createModel();

      Parameter n = new Parameter("n");
      model.addParameter(n);
      n.setValue(2);

      Parameter X = new Parameter("X");
      model.addParameter(X);
      ArraysSBasePlugin arraysSBasePluginX = new ArraysSBasePlugin(X);
      X.addExtension(ArraysConstants.shortLabel, arraysSBasePluginX);
      Dimension dimX = arraysSBasePluginX.createDimension("i");
      dimX.setSize(n.getId());
      dimX.setArrayDimension(0);
      X.setValue(1);

      InitialAssignment ia = model.createInitialAssignment();
      ia.setVariable("X");
      ia.setMath(ASTNode.parseFormula("2"));

      ArraysSBasePlugin arraysSBasePluginAssignment = new ArraysSBasePlugin(ia);
      ia.addExtension(ArraysConstants.shortLabel, arraysSBasePluginAssignment);
      Index indX = arraysSBasePluginAssignment.createIndex();
      indX.setMath(ASTNode.parseFormula("1"));
      indX.setReferencedAttribute("symbol");
      indX.setArrayDimension(0);

      Species A = model.createSpecies("A");
      A.setValue(5);
      Species B = model.createSpecies("B");
      B.setValue(5);

      Reaction r = model.createReaction();
      r.createReactant(A);
      r.createProduct(B);
      r.setId("reaction");

      KineticLaw k = r.createKineticLaw();
      k.setMath(ASTNode.parseFormula("B - X*A"));
      ArraysSBasePlugin reactPlugin = new ArraysSBasePlugin(r);
      r.addExtension(ArraysConstants.shortLabel, reactPlugin);
      Dimension dim = reactPlugin.createDimension("i");
      dim.setSize(n.getId());
      dim.setArrayDimension(0);
      SBMLWriter.write(doc, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
      SBMLDocument flattened = ArraysFlattening.convert(doc);
      SBMLWriter.write(flattened, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
      assertTrue(flattened.getModel().getParameterCount() == 3);
      assertTrue(flattened.getModel().getReactionCount() == 2);
    } catch (SBMLException e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testEvent() {
    try {
      SBMLDocument doc = new SBMLDocument(3,1);
      Model model = doc.createModel();

      Parameter n = new Parameter("n");
      model.addParameter(n);
      n.setValue(3);

      Parameter X = new Parameter("X");
      X.setValue(1);
      model.addParameter(X);
      ArraysSBasePlugin arraysSBasePluginX = new ArraysSBasePlugin(X);
      X.addExtension(ArraysConstants.shortLabel, arraysSBasePluginX);
      Dimension dimX = arraysSBasePluginX.createDimension("i");
      dimX.setSize(n.getId());
      dimX.setArrayDimension(0);

      Event e = model.createEvent();
      e.setId("event");
      Delay delay = e.createDelay();
      delay.setMath(ASTNode.parseFormula("{1,2,3}[i]"));
      e.setDelay(delay);
      Trigger trigger = e.createTrigger();
      trigger.setMath(ASTNode.parseFormula("{true, true, true}[i]"));
      EventAssignment assign = e.createEventAssignment();
      assign.setMath(ASTNode.parseFormula("{1, 10, 100}[i]"));
      assign.setVariable("X");

      ArraysSBasePlugin eventAssignPlugin = new ArraysSBasePlugin(assign);
      assign.addExtension(ArraysConstants.shortLabel, eventAssignPlugin);
      Dimension dimAssign = eventAssignPlugin.createDimension("j");
      dimAssign.setSize(n.getId());
      dimAssign.setArrayDimension(0);
      Index ind = eventAssignPlugin.createIndex();
      ind.setReferencedAttribute("variable");
      ind.setArrayDimension(0);
      ind.setMath(ASTNode.parseFormula("j"));
      ArraysSBasePlugin eventPlugin = new ArraysSBasePlugin(e);
      e.addExtension(ArraysConstants.shortLabel, eventPlugin);
      Dimension dim = eventPlugin.createDimension("i");
      dim.setSize(n.getId());
      dim.setArrayDimension(0);


      SBMLWriter.write(doc, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
      SBMLDocument flattened = ArraysFlattening.convert(doc);
      SBMLWriter.write(flattened, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
      assertTrue(flattened.getModel().getEventCount() == 3);
      assertTrue(flattened.getModel().getEvent(0).getEventAssignmentCount() == 3);
    } catch (SBMLException e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testEventAssignmentWithParentDimension() {
    try {
      SBMLDocument doc = new SBMLDocument(3,1);
      Model model = doc.createModel();

      Parameter n = new Parameter("n");
      model.addParameter(n);
      n.setValue(3);

      Parameter X = new Parameter("X");
      X.setValue(1);
      model.addParameter(X);
      ArraysSBasePlugin arraysSBasePluginX = new ArraysSBasePlugin(X);
      X.addExtension(ArraysConstants.shortLabel, arraysSBasePluginX);
      Dimension dimX = arraysSBasePluginX.createDimension("i");
      dimX.setSize(n.getId());
      dimX.setArrayDimension(0);

      Event e = model.createEvent();
      e.setId("event");
      Delay delay = e.createDelay();
      delay.setMath(ASTNode.parseFormula("{1,2,3}[i]"));
      e.setDelay(delay);
      Trigger trigger = e.createTrigger();
      trigger.setMath(ASTNode.parseFormula("{true, true, true}[i]"));

      ArraysSBasePlugin eventPlugin = new ArraysSBasePlugin(e);
      e.addExtension(ArraysConstants.shortLabel, eventPlugin);
      Dimension dim = eventPlugin.createDimension("i");
      dim.setSize(n.getId());
      dim.setArrayDimension(0);

      EventAssignment assign = e.createEventAssignment();
      assign.setMath(ASTNode.parseFormula("{1, 10, 100}[i]"));
      assign.setVariable("X");
      ArraysSBasePlugin eventAssignPlugin = new ArraysSBasePlugin(assign);
      assign.addExtension(ArraysConstants.shortLabel, eventAssignPlugin);
      Index ind = eventAssignPlugin.createIndex();
      ind.setReferencedAttribute("variable");
      ind.setArrayDimension(0);
      ind.setMath(ASTNode.parseFormula("i"));



      SBMLWriter.write(doc, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
      SBMLDocument flattened = ArraysFlattening.convert(doc);
      SBMLWriter.write(flattened, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
      assertTrue(flattened.getModel().getEventCount() == 3);
      assertTrue(flattened.getModel().getEvent(0).getEventAssignmentCount() == 1);
    } catch (SBMLException e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testBioModelFlattening() {
    SBMLDocument doc;
    try {
      doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/BIOMD0000000012.xml"));
      SBMLDocument flattened = ArraysFlattening.convert(doc);

      SBMLWriter.write(flattened, System.out, ' ', (short) 2);

      assertTrue(doc.getModel().getCompartmentCount() == flattened.getModel().getCompartmentCount());
      assertTrue(doc.getModel().getSpeciesCount() == flattened.getModel().getSpeciesCount());
      assertTrue(doc.getModel().getParameterCount() == flattened.getModel().getParameterCount());
      assertTrue(doc.getModel().getReactionCount() == flattened.getModel().getReactionCount());
      assertTrue(doc.getModel().getRuleCount() == flattened.getModel().getRuleCount());
      assertTrue(doc.getModel().getFunctionDefinitionCount() == flattened.getModel().getFunctionDefinitionCount());
      assertTrue(doc.getModel().getConstraintCount() == flattened.getModel().getConstraintCount());
    } catch (XMLStreamException e) {
      assertTrue(false);
    }

  }

  /**
   * 
   */
  @Test
  public void testSpeciesReference() {
    try {
      SBMLDocument doc = new SBMLDocument(3,1);
      Model model = doc.createModel();
      Parameter n = new Parameter("n");
      model.addParameter(n);
      n.setValue(2);
      Parameter X = new Parameter("X");
      model.addParameter(X);
      X.setValue(1);
      Species A = model.createSpecies("A");
      A.setValue(5);
      ArraysSBasePlugin arraysSBasePluginA = new ArraysSBasePlugin(A);
      A.addExtension(ArraysConstants.shortLabel, arraysSBasePluginA);
      Dimension dimA = arraysSBasePluginA.createDimension("i");
      dimA.setSize(n.getId());
      dimA.setArrayDimension(0);
      Species B = model.createSpecies("B");
      B.setValue(5);
      ArraysSBasePlugin arraysSBasePluginB = new ArraysSBasePlugin(B);
      B.addExtension(ArraysConstants.shortLabel, arraysSBasePluginB);
      Dimension dimB = arraysSBasePluginB.createDimension("i");
      dimB.setSize(n.getId());
      dimB.setArrayDimension(0);
      Reaction r = model.createReaction();
      SpeciesReference a = r.createReactant(A);
      ArraysSBasePlugin arraysSBasePlugina = new ArraysSBasePlugin(a);
      a.addExtension(ArraysConstants.shortLabel, arraysSBasePlugina);
      Index inda = arraysSBasePlugina.createIndex();
      inda.setReferencedAttribute("species");
      inda.setArrayDimension(0);
      inda.setMath(ASTNode.parseFormula("1-i"));
      SpeciesReference b = r.createProduct(B);
      ArraysSBasePlugin arraysSBasePluginb = new ArraysSBasePlugin(b);
      b.addExtension(ArraysConstants.shortLabel, arraysSBasePluginb);
      Index indb = arraysSBasePluginb.createIndex();
      indb.setReferencedAttribute("species");
      indb.setArrayDimension(0);
      indb.setMath(ASTNode.parseFormula("i"));
      r.setId("reaction");
      KineticLaw k = r.createKineticLaw();
      k.setMath(ASTNode.parseFormula("B[i] - X*A[1-i]"));
      ArraysSBasePlugin reactPlugin = new ArraysSBasePlugin(r);
      r.addExtension(ArraysConstants.shortLabel, reactPlugin);
      Dimension dim = reactPlugin.createDimension("i");
      dim.setSize(n.getId());
      dim.setArrayDimension(0);
      SBMLWriter.write(doc, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
      SBMLDocument flattened = ArraysFlattening.convert(doc);
      SBMLWriter.write(flattened, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
      assertTrue(flattened.getModel().getSpeciesCount() == 4);
      assertTrue(flattened.getModel().findNamedSBase(flattened.getModel().getReaction(0).getReactant(0).getSpecies()) != null);
      assertTrue(flattened.getModel().findNamedSBase(flattened.getModel().getReaction(0).getProduct(0).getSpecies()) != null);
    } catch (SBMLException e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testSpeciesReferenceImplicitDim() {
    try {
      SBMLDocument doc = new SBMLDocument(3,1);
      Model model = doc.createModel();

      Parameter n = new Parameter("n");
      model.addParameter(n);
      n.setValue(2);

      Parameter X = new Parameter("X");
      model.addParameter(X);
      X.setValue(1);

      Species A = model.createSpecies("A");
      A.setValue(5);
      ArraysSBasePlugin arraysSBasePluginA = new ArraysSBasePlugin(A);
      A.addExtension(ArraysConstants.shortLabel, arraysSBasePluginA);
      Dimension dimA = arraysSBasePluginA.createDimension("i");
      dimA.setSize(n.getId());
      dimA.setArrayDimension(0);
      Species B = model.createSpecies("B");
      B.setValue(5);
      ArraysSBasePlugin arraysSBasePluginB = new ArraysSBasePlugin(B);
      B.addExtension(ArraysConstants.shortLabel, arraysSBasePluginB);
      Dimension dimB = arraysSBasePluginB.createDimension("i");
      dimB.setSize(n.getId());
      dimB.setArrayDimension(0);
      Reaction r = model.createReaction();
      ArraysSBasePlugin reactArraysPlugin = new ArraysSBasePlugin(r);
      r.addExtension(ArraysConstants.shortLabel, reactArraysPlugin);
      Dimension dim = reactArraysPlugin.createDimension("i");
      dim.setSize("n");
      dim.setArrayDimension(0);
      r.setId("reaction");
      KineticLaw k = r.createKineticLaw();
      k.setMath(ASTNode.parseFormula("B - X*A"));

      SpeciesReference a = r.createReactant(A);
      a.setId("a");
      ArraysSBasePlugin arraysSBasePlugina = new ArraysSBasePlugin(a);
      a.addExtension(ArraysConstants.shortLabel, arraysSBasePlugina);
      dim = arraysSBasePlugina.createDimension("j");
      dim.setSize("n");
      dim.setArrayDimension(0);
      Index inda = arraysSBasePlugina.createIndex();
      inda.setReferencedAttribute("species");
      inda.setArrayDimension(0);
      inda.setMath(ASTNode.parseFormula("j"));
      
      Index impa = arraysSBasePlugina.createIndex();
      impa.setReferencedAttribute("id");
      impa.setArrayDimension(0);
      impa.setMath(ASTNode.parseFormula("j"));
      
      SpeciesReference b = r.createProduct(B);
      b.setId("b");
      ArraysSBasePlugin arraysSBasePluginb = new ArraysSBasePlugin(b);
      b.addExtension(ArraysConstants.shortLabel, arraysSBasePluginb);
      dim = arraysSBasePluginb.createDimension("j");
      dim.setSize("n");
      dim.setArrayDimension(0);
      Index indb = arraysSBasePluginb.createIndex();
      indb.setReferencedAttribute("species");
      indb.setArrayDimension(0);
      indb.setMath(ASTNode.parseFormula("j"));
      Index impb = arraysSBasePluginb.createIndex();
      impb.setReferencedAttribute("id");
      impb.setArrayDimension(0);
      impb.setMath(ASTNode.parseFormula("j"));
      
      SBMLWriter.write(doc, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
      SBMLDocument flattened = ArraysFlattening.convert(doc);
      SBMLWriter.write(flattened, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");

      assertTrue(flattened.getModel().findNamedSBase(flattened.getModel().getReaction(0).getReactant(0).getSpecies()) != null);
      assertTrue(flattened.getModel().findNamedSBase(flattened.getModel().getReaction(0).getReactant(1).getSpecies()) != null);
      assertTrue(flattened.getModel().findNamedSBase(flattened.getModel().getReaction(0).getProduct(0).getSpecies())  != null);
      assertTrue(flattened.getModel().findNamedSBase(flattened.getModel().getReaction(0).getProduct(1).getSpecies())  != null);

    } catch (SBMLException e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testUniqueID() {
    try {
      SBMLDocument doc = new SBMLDocument(3,1);
      Model model = doc.createModel();

      Parameter n = new Parameter("n");
      n.setValue(2);
      n.setConstant(true);
      model.addParameter(n);

      model.createParameter("X_0");
      model.createParameter("X__0");
      model.createParameter("X___0");
      Parameter X = new Parameter("X");
      X.setValue(1);
      model.addParameter(X);
      InitialAssignment ia = model.createInitialAssignment();
      ia.setVariable("X");
      ia.setMath(ASTNode.parseFormula("i"));
      ArraysSBasePlugin iaArraysPlugin = new ArraysSBasePlugin(ia);
      ia.addExtension(ArraysConstants.shortLabel, iaArraysPlugin);
      Dimension iaDim = iaArraysPlugin.createDimension("i");
      iaDim.setArrayDimension(0);
      iaDim.setSize("n");
      Index indX = iaArraysPlugin.createIndex();
      indX.setArrayDimension(0);
      indX.setReferencedAttribute("symbol");
      indX.setMath(new ASTNode("i"));

      Parameter Y = new Parameter("Y");
      ArraysSBasePlugin arraysSBasePluginX = new ArraysSBasePlugin(X);
      X.addExtension(ArraysConstants.shortLabel, arraysSBasePluginX);

      Dimension dimX = new Dimension("i");
      dimX.setSize(n.getId());
      dimX.setArrayDimension(0);
      arraysSBasePluginX.addDimension(dimX);


      model.addParameter(Y);
      Y.setValue(2);
      ArraysSBasePlugin arraysSBasePluginY = new ArraysSBasePlugin(Y);

      Y.addExtension(ArraysConstants.shortLabel, arraysSBasePluginY);
      Dimension dimY = new Dimension("i");
      dimY.setSize(n.getId());
      dimY.setArrayDimension(0);

      arraysSBasePluginY.addDimension(dimY);

      AssignmentRule rule = new AssignmentRule();
      model.addRule(rule);

      ArraysSBasePlugin arraysSBasePluginRule = new ArraysSBasePlugin(rule);
      rule.addExtension(ArraysConstants.shortLabel, arraysSBasePluginRule);

      Dimension dimRule = new Dimension("i");
      dimRule.setSize(n.getId());
      dimRule.setArrayDimension(0);
      arraysSBasePluginRule.addDimension(dimRule);


      Index indexRule = arraysSBasePluginRule.createIndex();
      indexRule.setArrayDimension(0);
      indexRule.setReferencedAttribute("variable");
      ASTNode indexMath = new ASTNode();

      indexMath = ASTNode.diff(new ASTNode(n), new ASTNode(1), new ASTNode("i"));
      indexRule.setMath(indexMath);

      rule.setVariable("Y");
      ASTNode ruleMath = ASTNode.parseFormula("selector(X, i)");

      rule.setMath(ruleMath);
      SBMLWriter.write(doc, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
      SBMLDocument flattened = ArraysFlattening.convert(doc);
      SBMLWriter.write(flattened, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");

      assertTrue(flattened.getModel().findNamedSBase("X_0") != null);
      assertTrue(flattened.getModel().findNamedSBase("X__0") != null);
      assertTrue(flattened.getModel().findNamedSBase("X___0") != null);
      assertTrue(flattened.getModel().findNamedSBase("X____0") != null);
      assertTrue(flattened.getModel().findNamedSBase("X") == null);
    } catch (SBMLException e) {
      e.printStackTrace();
    } catch (XMLStreamException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  //TODO: automatic test
  /**
   * 
   */
  @Test
  public void testCompModel() {
    SBMLDocument doc;
    try {
      doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/SubModel.xml"));
      SBMLWriter.write(doc, System.out, ' ', (short) 2);
      SBMLDocument flattened = ArraysFlattening.convert(doc);
      System.out.println("\n-------------------------------------------");
      SBMLWriter.write(flattened, System.out, ' ', (short) 2);
      System.out.println("\n-------------------------------------------");
      doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/SubSubModel.xml"));
      SBMLWriter.write(doc, System.out, ' ', (short) 2);
      flattened = ArraysFlattening.convert(doc);
      System.out.println("\n-------------------------------------------");
      SBMLWriter.write(flattened, System.out, ' ', (short) 2);

    } catch (XMLStreamException e) {
      assertTrue(false);
    }

  }

  /**
   * 
   */
  @Test
  public void testFunctionDef() {
    SBMLDocument doc;
    try {
      doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/VoteModel.xml"));
      SBMLWriter.write(doc, System.out, ' ', (short) 2);
      SBMLDocument flattened = ArraysFlattening.convert(doc);

      SBMLWriter.write(flattened, System.out, ' ', (short) 2);

    } catch (XMLStreamException e) {
      assertTrue(false);
    }

  }

  /**
   * 
   */
  @Test
  public void testFBC() {
    SBMLDocument doc;
    try {
      doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/NEWFBC.xml"));
      SBMLWriter.write(doc, System.out, ' ', (short) 2);
      SBMLDocument flattened = ArraysFlattening.convert(doc);

      SBMLWriter.write(flattened, System.out, ' ', (short) 2);

    } catch (XMLStreamException e) {
      assertTrue(false);
    }

  }

  @Test
  public void testLayout() {
    SBMLDocument doc;
    try {
      doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/layoutTest.xml"));
      SBMLWriter.write(doc, System.out, ' ', (short) 2);
      SBMLDocument flattened = ArraysFlattening.convert(doc);
      
      SBMLWriter.write(flattened, System.out, ' ', (short) 2);

    } catch (XMLStreamException e) {
      assertTrue(false);
    }
   
  }
  
  @Test
  public void testMetaIdRef() {
    SBMLDocument doc;
    try {
      doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/xyz.xml"));
      SBMLWriter.write(doc, System.out, ' ', (short) 2);
      SBMLDocument flattened = ArraysFlattening.convert(doc);
      
      SBMLWriter.write(flattened, System.out, ' ', (short) 2);

    } catch (XMLStreamException e) {
      assertTrue(false);
    }
   
  }
}

