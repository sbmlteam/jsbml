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

import java.io.FileNotFoundException;

import javax.swing.tree.TreeNode;
import javax.xml.stream.XMLStreamException;

import org.apache.log4j.Logger;
import org.junit.Assert;
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
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.Index;
import org.sbml.jsbml.ext.arrays.flattening.ArraysFlattening;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;


/**
 * Tests the arrays flattening algorithm.
 * 
 * @author Leandro Watanabe
 * @since 1.0
 */
public class FlatteningTest {

  /**
   * 
   */
  private static final transient Logger logger = Logger.getLogger(FlatteningTest.class);

  /**
   * Flattens a model that contain a species array of size 0.
   * 
   */
  @Test
  public void sizeZeroTest() {
    try {
      SBMLDocument document = new SBMLDocument(3,1);

      Model model = document.createModel();

      Species spec = model.createSpecies("s");

      Parameter param = model.createParameter("n");

      param.setConstant(true);

      param.setValue(0);

      ArraysSBasePlugin arraysSBasePlugin = new ArraysSBasePlugin(spec);

      spec.addExtension(ArraysConstants.shortLabel, arraysSBasePlugin);

      Dimension dim = arraysSBasePlugin.createDimension("i");

      dim.setArrayDimension(0);

      dim.setSize("n");

      SBMLDocument flattened = ArraysFlattening.convert(document);

      assertTrue(flattened.getModel().getSpeciesCount() == 0);

    } catch (SBMLException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * Flattens a model that contain a 2D species array of size 2x2.
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
   * Flattens a model that contain a 1D parameter array with related {@link InitialAssignment} and {@link AssignmentRule} array.
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
      ArraysSBasePlugin arraysSBasePluginX = new ArraysSBasePlugin(X);
      X.addExtension(ArraysConstants.shortLabel, arraysSBasePluginX);

      Dimension dimX = new Dimension("i");
      dimX.setSize(n.getId());
      dimX.setArrayDimension(0);
      arraysSBasePluginX.addDimension(dimX);
      
      // arrayed initial assignment
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

      String docStr = new SBMLWriter().writeSBMLToString(doc);

      if (logger.isDebugEnabled()) {
        logger.debug("testFlatteningRule, document before convert:\n" + docStr);
        System.out.println("\n-------------------------------------------");
      }

      SBMLDocument flattened = ArraysFlattening.convert(doc);

      docStr = new SBMLWriter().writeSBMLToString(flattened);

      if (logger.isDebugEnabled()) {
        logger.debug("testFlatteningRule, document after convert:\n" + docStr);
        System.out.println("\n-------------------------------------------");
      }

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

      String docStr = new SBMLWriter().writeSBMLToString(doc);

      if (logger.isDebugEnabled()) {
        logger.debug("testReaction, document before convert:\n" + docStr);
        System.out.println("\n-------------------------------------------");
      }

      SBMLDocument flattened = ArraysFlattening.convert(doc);

      docStr = new SBMLWriter().writeSBMLToString(flattened);

      if (logger.isDebugEnabled()) {
        logger.debug("testReaction, document after convert:\n" + docStr);
        System.out.println("\n-------------------------------------------");
      }

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

      String docStr = new SBMLWriter().writeSBMLToString(doc);

      if (logger.isDebugEnabled()) {
        logger.debug("testEvent, document before convert:\n" + docStr);
        System.out.println("\n-------------------------------------------");
      }

      SBMLDocument flattened = ArraysFlattening.convert(doc);

      docStr = new SBMLWriter().writeSBMLToString(flattened);

      if (logger.isDebugEnabled()) {
        logger.debug("testEvent, document after convert:\n" + docStr);
        System.out.println("\n-------------------------------------------");
      }

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



      String docStr = new SBMLWriter().writeSBMLToString(doc);

      if (logger.isDebugEnabled()) {
        logger.debug("testEventAssignmentWithParentDimension, document before convert:\n" + docStr);
        System.out.println("\n-------------------------------------------");
      }

      SBMLDocument flattened = ArraysFlattening.convert(doc);

      docStr = new SBMLWriter().writeSBMLToString(flattened);

      if (logger.isDebugEnabled()) {
        logger.debug("testEventAssignmentWithParentDimension, document after convert:\n" + docStr);
        System.out.println("\n-------------------------------------------");
      }

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

      String docStr = new SBMLWriter().writeSBMLToString(flattened);

      if (logger.isDebugEnabled()) {
        logger.debug("testBioModelFlattening, document after convert:\n" + docStr);
      }

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

      String docStr = new SBMLWriter().writeSBMLToString(doc);

      if (logger.isDebugEnabled()) {
        logger.debug("testSpeciesReference, document before convert:\n" + docStr);
        System.out.println("\n-------------------------------------------");
      }

      SBMLDocument flattened = ArraysFlattening.convert(doc);

      docStr = new SBMLWriter().writeSBMLToString(flattened);

      if (logger.isDebugEnabled()) {
        logger.debug("testSpeciesReference, document after convert:\n" + docStr);
        System.out.println("\n-------------------------------------------");
      }

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

      String docStr = new SBMLWriter().writeSBMLToString(doc);

      if (logger.isDebugEnabled()) {
        logger.debug("testSpeciesReferenceImplicitDim, document before convert:\n" + docStr);
        System.out.println("\n-------------------------------------------");
      }

      SBMLDocument flattened = ArraysFlattening.convert(doc);

      docStr = new SBMLWriter().writeSBMLToString(flattened);

      if (logger.isDebugEnabled()) {
        logger.debug("testSpeciesReferenceImplicitDim, document after convert:\n" + docStr);
        System.out.println("\n-------------------------------------------");
      }

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

      String docStr = new SBMLWriter().writeSBMLToString(doc);

      if (logger.isDebugEnabled()) {
        logger.debug("testUniqueID, document before convert:\n" + docStr);
        System.out.println("\n-------------------------------------------");
      }

      SBMLDocument flattened = ArraysFlattening.convert(doc);

      docStr = new SBMLWriter().writeSBMLToString(flattened);

      if (logger.isDebugEnabled()) {
        logger.debug("testUniqueID, document after convert:\n" + docStr);
        System.out.println("\n-------------------------------------------");
      }

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
      String docStr = new SBMLWriter().writeSBMLToString(doc);

      if (logger.isDebugEnabled()) {
        logger.debug("SubModel, document before convert:\n" + docStr);
      }

      SBMLDocument flattened = ArraysFlattening.convert(doc);

      docStr = new SBMLWriter().writeSBMLToString(flattened);

      if (logger.isDebugEnabled()) {
        logger.debug("SubModel, document after convert:\n" + docStr);
        System.out.println("\n-------------------------------------------");
      }

      doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/SubSubModel.xml"));

      docStr = new SBMLWriter().writeSBMLToString(doc);

      if (logger.isDebugEnabled()) {
        logger.debug("SubSubModel, document before convert:\n" + docStr);
      }

      flattened = ArraysFlattening.convert(doc);

      docStr = new SBMLWriter().writeSBMLToString(flattened);

      if (logger.isDebugEnabled()) {
        logger.debug("SubSubModel, document after convert:\n" + docStr);
      }

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

      String docStr = new SBMLWriter().writeSBMLToString(doc);

      if (logger.isDebugEnabled()) {
        logger.debug("testFBC, document before convert:\n" + docStr);
      }

      SBMLDocument flattened = ArraysFlattening.convert(doc);

      docStr = new SBMLWriter().writeSBMLToString(flattened);

      if (logger.isDebugEnabled()) {
        logger.debug("testFBC, document after convert:\n" + docStr);
      }

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

      String docStr = new SBMLWriter().writeSBMLToString(doc);

      if (logger.isDebugEnabled()) {
        logger.debug("testFBC, document before convert:\n" + docStr);
      }

      SBMLDocument flattened = ArraysFlattening.convert(doc);

      docStr = new SBMLWriter().writeSBMLToString(flattened);

      if (logger.isDebugEnabled()) {
        logger.debug("testFBC, document after convert:\n" + docStr);
      }

    } catch (XMLStreamException e) {
      assertTrue(false);
    }

  }

  /**
   * 
   */
  @Test
  public void testLayout() {
    SBMLDocument doc;
    try {
      doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/layoutTest.xml"));

      String docStr = new SBMLWriter().writeSBMLToString(doc);

      if (logger.isDebugEnabled()) {
        logger.debug("testLayout, document before convert:\n" + docStr);
      }

      SBMLDocument flattened = ArraysFlattening.convert(doc);

      docStr = new SBMLWriter().writeSBMLToString(flattened);

      if (logger.isDebugEnabled()) {
        logger.debug("testLayout, document after convert:\n" + docStr);
      }

    } catch (XMLStreamException e) {
      assertTrue(false);
    }

  }

  //  @Test
  //  public void testMetaIdRef() {
  //    SBMLDocument doc;
  //    try {
  //      doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/xyz.xml"));
  //      SBMLWriter.write(doc, System.out, ' ', (short) 2);
  //      SBMLDocument flattened = ArraysFlattening.convert(doc);
  //
  //      SBMLWriter.write(flattened, System.out, ' ', (short) 2);
  //
  //    } catch (XMLStreamException e) {
  //      assertTrue(false);
  //    }
  //
  //  }

  /**
   * 
   */
  @Test
  public void getmodel() {
    SBMLDocument doc;
    try {
      doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/example04.xml"));

      String docStr = new SBMLWriter().writeSBMLToString(doc);

      if (logger.isDebugEnabled()) {
        logger.debug("getmodel, document before convert:\n" + docStr);
      }

      SBMLDocument flattened = ArraysFlattening.convert(doc);

      docStr = new SBMLWriter().writeSBMLToString(flattened);

      if (logger.isDebugEnabled()) {
        logger.debug("getmodel, document after convert:\n" + docStr);
      }

    } catch (XMLStreamException e) {
      assertTrue(false);
    }

  }

  //  @Test
  //  public void testASTNodeType()
  //  {
  //    SBMLDocument doc = new SBMLDocument(3,1);
  //    Model model = doc.createModel("foo");
  //    FunctionDefinition function = model.createFunctionDefinition("uniform");
  //    Constraint c = model.createConstraint();
  //    try {
  //      function.setMath(ASTNode.parseFormula("lambda(a,b,(a+b)/2)"));
  //      c.setMath(ASTNode.parseFormula("uniform(0,1) > 0.5"));
  //    }
  //    catch (ParseException e1) {
  //      assertTrue(false);
  //    }
  //    System.out.println(c.getMath().toString());
  //    doc = ArraysFlattening.convert(model.getSBMLDocument());
  //    assertTrue(c.getMath().getType() == doc.getModel().getConstraint(0).getMath().getType());
  //  }

  /**
   * 
   */
  @Test
  public void testTime() {
    SBMLDocument doc;
    try {
      doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/and.xml"));

      String docStr = new SBMLWriter().writeSBMLToString(doc);

      if (logger.isDebugEnabled()) {
        logger.debug("testTime, document before convert:\n" + docStr);
      }

      SBMLDocument flattened = ArraysFlattening.convert(doc);

      docStr = new SBMLWriter().writeSBMLToString(flattened);

      if (logger.isDebugEnabled()) {
        logger.debug("testTime, document after convert:\n" + docStr);
      }

    } catch (XMLStreamException e) {
      assertTrue(false);
    }

  }

  /**
   * 
   */
  @Test
  public void toggleTime() {
    SBMLDocument doc;
    try {
      doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/toggleSwitch.xml"));

      String docStr = new SBMLWriter().writeSBMLToString(doc);

      if (logger.isDebugEnabled()) {
        logger.debug("toggleTime, document before convert:\n" + docStr);
      }

      SBMLDocument flattened = ArraysFlattening.convert(doc);

      docStr = new SBMLWriter().writeSBMLToString(flattened);

      if (logger.isDebugEnabled()) {
        logger.debug("toggleTime, document after convert:\n" + docStr);
      }

    } catch (XMLStreamException e) {
      assertTrue(false);
    }

  }
  
  /**
   * 
   */
  @Test
  public void complexBioModel() {
    SBMLDocument doc;
    try {
      doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/BIOMD0000000140.xml"));
      Model model = doc.getModel();

      SBMLDocument flattened = ArraysFlattening.convert(doc);
      Model flattenedModel = flattened.getModel();

      assert(model.equals(flattenedModel));

    } catch (XMLStreamException e) {
      assertTrue(false);
    }

  }

}

