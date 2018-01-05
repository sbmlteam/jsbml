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

import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.Event;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.SBMLError;
import org.sbml.jsbml.SBMLReader;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.Trigger;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.Index;
import org.sbml.jsbml.ext.arrays.util.ArraysMath;
import org.sbml.jsbml.ext.arrays.validator.ArraysValidator;
import org.sbml.jsbml.ext.arrays.validator.constraints.ArraysMathCheck;
import org.sbml.jsbml.text.parser.ParseException;


/**
 * Test cases for validation.
 * 
 * @author Leandro Watanabe
 * @since 1.0
 */
public class ArraysValidationTest {


  /**
   * Test validation where a dimension has non-constant size.
   */
  @Test
  public void constantParameterTest() {

    SBMLDocument doc = new SBMLDocument(3,1);

    Model model = doc.createModel();

    Species spec = new Species();

    Parameter param = new Parameter("n");

    param.setConstant(false);

    param.setValue(10);

    model.addSpecies(spec);

    model.addParameter(param);

    ArraysSBasePlugin arraysSBasePlugin = new ArraysSBasePlugin(spec);

    spec.addExtension(ArraysConstants.shortLabel, arraysSBasePlugin);

    Dimension dim = new Dimension();

    dim.setArrayDimension(0);

    dim.setSize("n");

    arraysSBasePlugin.addDimension(dim);

    assertTrue(ArraysValidator.validate(doc).size() != 0);


  }

  /**
   * Test whether the validator reports an error if something
   * that cannot have dimension has one.
   */
  @Test
  public void sbaseWithDimensionCheck() {

    SBMLDocument doc = new SBMLDocument(3,1);

    Model model = doc.createModel();

    Event event = new Event();

    Delay delay = new Delay();

    Trigger trigger = new Trigger();

    trigger.setMath(new ASTNode("true"));
    try {
      delay.setMath(ASTNode.parseFormula("3"));
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }

    event.setDelay(delay);

    event.setTrigger(trigger);

    model.addEvent(event);

    Parameter param = new Parameter("n");

    param.setConstant(true);

    param.setValue(10);

    model.addParameter(param);

    ArraysSBasePlugin arraysSBasePlugin = new ArraysSBasePlugin(delay);

    delay.addExtension(ArraysConstants.shortLabel, arraysSBasePlugin);

    Dimension dim = new Dimension();

    dim.setArrayDimension(0);

    dim.setSize("n");

    arraysSBasePlugin.addDimension(dim);

    assertTrue(ArraysValidator.validate(doc).size() != 0);

  }

  /**
   * Test validation where there is a dimension with array dimension 2
   * but not 0 and 1.
   */
  @Test
  public void arrayDimensionTest() {

    SBMLDocument doc = new SBMLDocument(3,1);

    Model model = doc.createModel();

    Species spec = new Species();

    Parameter param = new Parameter("n");

    param.setConstant(true);

    param.setValue(10);

    model.addSpecies(spec);

    model.addParameter(param);

    ArraysSBasePlugin arraysSBasePlugin = new ArraysSBasePlugin(spec);

    spec.addExtension(ArraysConstants.shortLabel, arraysSBasePlugin);

    Dimension dim = new Dimension();

    dim.setArrayDimension(2);

    dim.setSize("n");

    arraysSBasePlugin.addDimension(dim);

    assertTrue(ArraysValidator.validate(doc).size() != 0);

  }

  /**
   * Test validation when index references an invalid attribute
   */
  @Test
  public void refAttributeTest() {

    SBMLDocument doc = new SBMLDocument(3,1);
    Model model = doc.createModel();

    Parameter n = new Parameter("n");
    n.setValue(10);
    n.setConstant(true);
    model.addParameter(n);

    Parameter X = new Parameter("X");

    model.addParameter(X);
    ArraysSBasePlugin arraysSBasePluginX = new ArraysSBasePlugin(X);

    X.addExtension(ArraysConstants.shortLabel, arraysSBasePluginX);

    Dimension dimX = new Dimension("i");
    dimX.setSize(n.getId());
    dimX.setArrayDimension(0);

    arraysSBasePluginX.addDimension(dimX);

    Parameter Y = new Parameter("Y");

    model.addParameter(Y);
    ArraysSBasePlugin arraysSBasePluginY = new ArraysSBasePlugin(Y);

    Y.addExtension(ArraysConstants.shortLabel, arraysSBasePluginY);
    Dimension dimY = new Dimension("i");
    dimY.setSize(n.getId());
    dimY.setArrayDimension(0);

    arraysSBasePluginY.addDimension(dimY);

    AssignmentRule rule = new AssignmentRule();
    model.addRule(rule);
    rule.setMetaId("rule");

    ArraysSBasePlugin arraysSBasePluginRule = new ArraysSBasePlugin(rule);
    rule.addExtension(ArraysConstants.shortLabel, arraysSBasePluginRule);

    Dimension dimRule = new Dimension("i");
    dimRule.setSize(n.getId());
    dimRule.setArrayDimension(0);
    arraysSBasePluginRule.addDimension(dimRule);

    Index indexRule = new Index();
    indexRule.setArrayDimension(0);
    indexRule.setReferencedAttribute("variables");
    ASTNode indexMath = new ASTNode();

    indexMath = ASTNode.diff(new ASTNode(9), new ASTNode("i"));
    indexRule.setMath(indexMath);
    arraysSBasePluginRule.addIndex(indexRule);

    rule.setVariable("Y");
    ASTNode ruleMath;
    try {
      ruleMath = ASTNode.parseFormula("selector(X, i)");
      rule.setMath(ruleMath);
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }

    assertTrue(ArraysValidator.validate(doc).size() != 0);
  }

//  /**
//   * Test validation constraint for vector. Make sure that it catches errors when the vector is rigged and
//   * doesn't do anything when the model is valid.
//   */
//  @Test
//  public void vectorTest() {
//    try {
//      // Test invalid vector of vectors
//      Model m = new Model();
//      Index index = new Index();
//      ASTNode n;
//      String formula = "{ { { },{ } }, { { },{ } }, { { },{ { } } } }";
//      FormulaParser parser = new FormulaParser(new StringReader(formula));
//      n = parser.parse();
//      index.setMath(n);
//      ArraysMathCheck check = new ArraysMathCheck(m, index);
//      check.check();
//      assertTrue(check.getListOfErrors().size() == 1);
//
//      //Test invalid vector of vectors
//      formula = "{ { { { } },{ } }, { { },{ } }, { { },{ } } }";
//      parser = new FormulaParser(new StringReader(formula));
//      n = parser.parse();
//      index.setMath(n);
//      check = new ArraysMathCheck(m, index);
//      check.check();
//      assertTrue(check.getListOfErrors().size() == 1);
//
//      //Test invalid vector of vectors
//      formula = "{ {{}}, {} }";
//      parser = new FormulaParser(new StringReader(formula));
//      n = parser.parse();
//      index.setMath(n);
//      check = new ArraysMathCheck(m, index);
//      check.check();
//      assertTrue(check.getListOfErrors().size() == 1);
//
//      //Test invalid vector of vectors
//      formula = "{ {  }, { }, { { { { } } } } }";
//      parser = new FormulaParser(new StringReader(formula));
//      n = parser.parse();
//      index.setMath(n);
//      check = new ArraysMathCheck(m, index);
//      check.check();
//      assertTrue(check.getListOfErrors().size() == 1);
//
//      //Test valid vector of vectors
//      formula = "{ { { },{ } }, { { },{ } }, { { },{ } } }";
//      parser = new FormulaParser(new StringReader(formula));
//      n = parser.parse();
//      index.setMath(n);
//      check = new ArraysMathCheck(m, index);
//      check.check();
//      assertTrue(check.getListOfErrors().size() == 0);
//
//      // Test valid vector of vectors and sbase
//      Species s = m.createSpecies("s");
//      Parameter p = m.createParameter("p");
//      p.setConstant(true);
//      p.setValue(2);
//      ArraysSBasePlugin arraysSBasePlugin = new ArraysSBasePlugin(s);
//      s.addExtension(ArraysConstants.shortLabel, arraysSBasePlugin);
//      Dimension dim0 = arraysSBasePlugin.createDimension();
//      dim0.setArrayDimension(0);
//      dim0.setSize("p");
//      formula = "{ { { },{ } }, { { },{ } }, s }";
//      parser = new FormulaParser(new StringReader(formula));
//      n = parser.parse();
//      index.setMath(n);
//      check = new ArraysMathCheck(m, index);
//      check.check();
//      assertTrue(check.getListOfErrors().size() == 0);
//
//      // Test invalid vector of vectors and sbase
//      p.setValue(3);
//      parser = new FormulaParser(new StringReader(formula));
//      n = parser.parse();
//      index.setMath(n);
//      check = new ArraysMathCheck(m, index);
//      check.check();
//      assertTrue(check.getListOfErrors().size() == 1);
//      //TODO: check error code
//      //assertTrue(check.getListOfErrors().get(0).getCode() == 00000);
//      assertTrue(check.getListOfErrors().get(0).getPackage().equals("arrays"));
//
//
//    } catch (ParseException e) {
//      assertTrue(false);
//      e.printStackTrace();
//    }
//  }

  /**
   * 
   */
  @Test
  public void testIndexMath() {
    SBMLDocument doc;
    try {
      doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/example.xml"));
      Model model = doc.getModel();
      AssignmentRule rule = (AssignmentRule) model.getRule("Y");
      ArraysMathCheck check = new ArraysMathCheck(model, rule);
      check.check();
      assertTrue(check.getListOfErrors().size() == 0);
      Parameter p = model.createParameter("x");
      p.setConstant(false);
      p.setValue(5);
      rule.setMath(ASTNode.parseFormula("X[3+p]"));
      check = new ArraysMathCheck(model, rule);
      check.check();
      assertTrue(check.getListOfErrors().size() == 1);

      rule.setMath(ASTNode.parseFormula("X[i+5]"));
      check = new ArraysMathCheck(model, rule);
      check.check();
      assertTrue(check.getListOfErrors().size() == 1);

    } catch (XMLStreamException e) {
      assertTrue(false);
      e.printStackTrace();
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }

  }

  /**
   * Test validation where there is a dimension with array dimension 2
   * but not 0 and 1.
   */
  @Test
  public void staticallyComputableTest() {

    SBMLDocument doc;
    try {
      doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/example.xml"));
      Model model = doc.getModel();
      AssignmentRule rule = (AssignmentRule) model.getRule("Y");
      ArraysMathCheck check = new ArraysMathCheck(model, rule);
      check.check();
      assertTrue(check.getListOfErrors().size() == 0);
      Parameter p = model.createParameter("x");
      p.setConstant(false);
      p.setValue(5);
      rule.setMath(ASTNode.parseFormula("X[3+p]"));
      Index index = new Index();
      index.setMath(ASTNode.parseFormula("X[3+d0]"));
      ArraysMath.isStaticallyComputable(model, index, new String[]{"d0"});
    } catch (XMLStreamException e) {
      assertTrue(false);
      e.printStackTrace();
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    } catch (NullPointerException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * Test validation where there is a dimension with array dimension 2
   * but not 0 and 1.
   */
  @Test
  public void isStaticallyComputableWithIdListTest() {
    try {
      SBMLDocument doc = new SBMLDocument(3,1);
      Model model = doc.createModel();
      String[] ids = new String[]{"i","j"};
      Index index = new Index();
      index.setMath(ASTNode.parseFormula("i"));
      assertTrue(ArraysMath.isStaticallyComputable(model, index, ids));
    } catch (ParseException e) {
      assertTrue(false);
    }
  }

  /**
   * Check if validation detects when index math goes out of bounds.
   */
  @Test
  public void checkIndexBounds() {
    try {
      SBMLDocument doc = new SBMLDocument(3,1);
      Model m = doc.createModel();
      AssignmentRule r = m.createAssignmentRule();

      r.setVariable("p");
      r.setMath(ASTNode.parseFormula("3"));

      Parameter p = m.createParameter("p");
      p.setValue(3);
      ArraysSBasePlugin paramPlugin = new ArraysSBasePlugin(p);
      p.addExtension(ArraysConstants.shortLabel, paramPlugin);
      Dimension pDim = paramPlugin.createDimension("i");
      pDim.setSize("n");
      pDim.setArrayDimension(0);

      Parameter size = m.createParameter("n");
      size.setConstant(true);
      size.setValue(10);

      ArraysSBasePlugin plugin = new ArraysSBasePlugin(r);
      r.addExtension(ArraysConstants.shortLabel, plugin);
      r.setMath(ASTNode.parseFormula("3"));

      Dimension dim = plugin.createDimension("i");
      dim.setSize("n");
      dim.setArrayDimension(0);

      Index index = plugin.createIndex();
      index.setArrayDimension(0);
      index.setReferencedAttribute("variable");
      index.setMath(ASTNode.parseFormula("10-i"));

      assertTrue(!ArraysMath.evaluateIndexBounds(m, index));
      index.setMath(ASTNode.parseFormula("9-i"));
      assertTrue(ArraysMath.evaluateIndexBounds(m, index));

    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * Check if validation detects when the LHS and the RHS doesn't agree in dimension sizes.
   */
  @Test
  public void checkVectorMathConsistency() {
    try {
      SBMLDocument doc = new SBMLDocument(3,1);
      Model m = doc.createModel();
      AssignmentRule r = m.createAssignmentRule();
      r.setVariable("p");
      r.setMath(ASTNode.parseFormula("{{1,2,3},{1,2,3},{1,2,3}}"));

      Parameter p = m.createParameter("p");
      p.setValue(3);
      ArraysSBasePlugin paramPlugin = new ArraysSBasePlugin(p);
      p.addExtension(ArraysConstants.shortLabel, paramPlugin);

      Dimension pDim0 = paramPlugin.createDimension("i");
      pDim0.setSize("n");
      pDim0.setArrayDimension(0);
      Dimension pDim1 = paramPlugin.createDimension("j");
      pDim1.setSize("n");
      pDim1.setArrayDimension(1);

      Parameter size = m.createParameter("n");
      size.setConstant(true);
      size.setValue(3);


      ArraysMathCheck check = new ArraysMathCheck(m, r);
      check.check();

      assertTrue(ArraysMath.checkVectorMath(m, r));
      assertTrue(ArraysMath.checkVectorAssignment(m, r));

      r.setMath(ASTNode.parseFormula("{{1,2,3},{1,2,3},{1,2,3}, {1,2,3}}"));
      check = new ArraysMathCheck(m, r);
      check.check();
      assertTrue(!ArraysMath.checkVectorAssignment(m, r));

    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testReactionModel() {
    SBMLDocument doc;
    try {
      doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/reactionArrayTest.xml"));
      assertTrue(ArraysValidator.validate(doc).size() == 0);
    } catch (XMLStreamException e) {
      assertTrue(false);
    }
  }

  /**
   * 
   */
  @Test
  public void testCompModel() {
    SBMLDocument doc;
    try {
      doc = SBMLReader.read(ArraysWriteTest.class.getResourceAsStream("/org/sbml/jsbml/xml/test/data/arrays/SubModel.xml"));
      List<SBMLError> errors = ArraysValidator.validate(doc, true);
      int count = 0;
      for (int i = 0; i < errors.size(); i++)
      {
        if (errors.get(i).getCode() == -1) {
          count++;
        }
      }
      assertTrue(count == 1);
    } catch (XMLStreamException e) {
      assertTrue(false);
    }
  }
}
