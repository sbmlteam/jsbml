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

import org.junit.Test;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.AssignmentRule;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLDocument;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.ext.arrays.ArraysConstants;
import org.sbml.jsbml.ext.arrays.ArraysSBasePlugin;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.ext.arrays.compiler.ArraysCompiler;
import org.sbml.jsbml.ext.arrays.compiler.VectorCompiler;
import org.sbml.jsbml.text.parser.ParseException;
import org.sbml.jsbml.util.compilers.ASTNodeValue;


/**
 * Test cases for the arrays compiler
 * 
 * @author Leandro Watanabe
 * @since 1.0
 */
public class CompilerTest {

  /**
   * 
   */
  @Test
  public void arithTest() {
    try {
      ArraysCompiler compiler = new ArraysCompiler();

      ASTNode plus = ASTNode.parseFormula("1+2+3+4");
      ASTNodeValue result = plus.compile(compiler);
      assertTrue(result.isNumber());
      assertTrue(result.toInteger() == 10);

      ASTNode minus = ASTNode.parseFormula("5-3-1");
      result = minus.compile(compiler);
      assertTrue(result.isNumber());
      assertTrue(result.toInteger() == 1);

      ASTNode times = ASTNode.parseFormula("10*10*10");
      result = times.compile(compiler);
      assertTrue(result.isNumber());
      assertTrue(result.toInteger() == 1000);

      ASTNode divide = ASTNode.parseFormula("8/2/2");
      result = divide.compile(compiler);
      assertTrue(result.isNumber());
      assertTrue(result.toInteger() == 2);

    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void inequalityTest() {
    try {
      ArraysCompiler compiler = new ArraysCompiler();

      ASTNode lt = ASTNode.parseFormula("6 < 3");
      ASTNodeValue result = lt.compile(compiler);
      assertTrue(result.isBoolean());
      assertTrue(!result.toBoolean());

      ASTNode leq = ASTNode.parseFormula("3 <= 3");
      result = leq.compile(compiler);
      assertTrue(result.isBoolean());
      assertTrue(result.toBoolean());

    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void opsWithIdTest() {
    try {
      ArraysCompiler compiler = new ArraysCompiler();
      compiler.addValue("i", 3);
      ASTNode function = ASTNode.parseFormula("i*3-1");
      ASTNodeValue result = function.compile(compiler);
      assertTrue(result.isNumber());
      assertTrue(result.toInteger() == 8);


    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void callableSBaseTest() {
    try {
      SBMLDocument doc = new SBMLDocument(3,1);
      Model m = doc.createModel();
      Parameter p = m.createParameter("p");
      p.setValue(4);
      Parameter q = m.createParameter("q");
      q.setValue(3);
      AssignmentRule r = m.createAssignmentRule();
      r.setMath(ASTNode.parseFormula("q*3-1"));
      r.setVariable("p");

      ArraysCompiler compiler = new ArraysCompiler();
      assertTrue(r.getMath().compile(compiler).isNumber());
      System.out.println("CompilerTest - callableSBaseTest - compiled value = " + r.getMath().compile(compiler).toInteger());
      assertTrue(r.getMath().compile(compiler).toInteger() == 8);

    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testVectorMath() {
    try {
      Model model = new Model();
      ASTNode vector = ASTNode.parseFormula("6+3 + { { {1,2}, {3,4} }, { {5,6}, {7,8} } } + 1 + { { {9,10}, {11,12} }, { {13,14}, {15,16} } }");

      VectorCompiler compiler = new VectorCompiler(model);
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().equals("{{{20,22},{24,26}},{{28,30},{32,34}}}"));

    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testVectorAbs() {
    try {
      Model model = new Model();
      ASTNode vector = ASTNode.parseFormula("{{{{abs(-1)}}}}");
      VectorCompiler compiler = new VectorCompiler(model);
      vector.compile(compiler);

      assertTrue(compiler.getNode().toFormula().equals("{{{{1}}}}"));


    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testVectorDiffDims() {
    try {
      Model model = new Model();
      ASTNode vector = ASTNode.parseFormula("{1,2} + {1}");
      VectorCompiler compiler = new VectorCompiler(model);
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().equals("unknown"));
      vector = ASTNode.parseFormula("{1} + {1,2}");
      compiler = new VectorCompiler(model);
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().equals("unknown"));
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testVectorUMinus() {
    try {
      Model model = new Model();
      ASTNode vector = ASTNode.parseFormula("-{{1,2,3}}");
      VectorCompiler compiler = new VectorCompiler(model);
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().equals("{{-1,-2,-3}}"));
      vector = ASTNode.parseFormula("-(1)");
      compiler = new VectorCompiler(model);
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().equals("-1"));
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testVectorMinus() {
    try {
      Model model = new Model();
      ASTNode vector = ASTNode.parseFormula("{{2,4,6}} - {{1,2,3}} - 1");
      VectorCompiler compiler = new VectorCompiler(model);
      vector.compile(compiler);
      System.out.println("testVectorMinus - formula = " + compiler.getNode().toFormula());
      assertTrue(compiler.getNode().toFormula().equals("{{0,1,2}}"));

      vector = ASTNode.parseFormula("6 - {{2, 3}}");
      compiler = new VectorCompiler(model);
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().equals("{{4,3}}"));
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testSBaseToVector() {
    try {
      SBMLDocument doc = new SBMLDocument(3,1);
      Model model = doc.createModel();
      Species s = model.createSpecies("s");
      s.setValue(1);
      Parameter p = model.createParameter("p");
      p.setValue(2);
      ArraysSBasePlugin arraysPlugin = new ArraysSBasePlugin(s);
      s.addPlugin(ArraysConstants.shortLabel, arraysPlugin);
      Dimension x = arraysPlugin.createDimension("x");
      x.setArrayDimension(0);
      x.setSize("p");
      Dimension y = arraysPlugin.createDimension("y");
      y.setArrayDimension(1);
      y.setSize("p");
      Dimension z = arraysPlugin.createDimension("z");
      z.setArrayDimension(2);
      z.setSize("p");
      ASTNode vector = ASTNode.parseFormula("s");
      VectorCompiler compiler = new VectorCompiler(model);
      vector.compile(compiler);
      //System.out.println(compiler.getNode());
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testFactorial() {
    try {
      Model model = new Model();
      ASTNode vector = ASTNode.parseFormula("factorial(5)");
      VectorCompiler compiler = new VectorCompiler(model);
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().equals("120"));
      vector = ASTNode.parseFormula("{{factorial(5),factorial(5),factorial(5)}}");
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().equals("{{120,120,120}}"));

    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testBinaryOp() {
    try {
      Model model = new Model();
      ASTNode vector = ASTNode.parseFormula("eq(5,3+2)");
      VectorCompiler compiler = new VectorCompiler(model);
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().equals("1"));
      vector = ASTNode.parseFormula("eq(5, {{1,3,5}})");
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().equals("{{0,0,1}}"));
      compiler = new VectorCompiler(model, true);
      vector = ASTNode.parseFormula("eq(5, {{1,3,5}})");
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().replaceAll(" ", "").equals("{{5==1,5==3,5==5}}") ||
        compiler.getNode().toFormula().replaceAll(" ", "").equals("{{1==5,3==5,5==5}}"));
      vector = ASTNode.parseFormula("eq(X, {{{X},{Y},{0}}})");
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().replaceAll(" ", "").equals("{{{X==X},{Y==X},{0==X}}}") ||
        compiler.getNode().toFormula().replaceAll(" ", "").equals("{{{X==X},{X==Y},{X==0}}}"));
      vector = ASTNode.parseFormula("eq({{X}}, {{X}})");
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().replaceAll(" ", "").equals("{{X==X}}"));
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testSpecRefToVector() {
    try {
      SBMLDocument doc = new SBMLDocument(3,1);
      Model model = doc.createModel();
      Species s = model.createSpecies("s");
      s.setValue(1);
      Parameter p = model.createParameter("p");
      p.setValue(2);
      Reaction r = model.createReaction("r");
      SpeciesReference ref = r.createReactant(s);
      ref.setId("S");

      ArraysSBasePlugin arraysPlugin = new ArraysSBasePlugin(r);
      r.addPlugin(ArraysConstants.shortLabel, arraysPlugin);
      Dimension x = arraysPlugin.createDimension("x");
      x.setArrayDimension(0);
      x.setSize("p");

      arraysPlugin = new ArraysSBasePlugin(ref);
      ref.addPlugin(ArraysConstants.shortLabel, arraysPlugin);
      x = arraysPlugin.createDimension("x");
      x.setArrayDimension(0);
      x.setSize("p");

      ASTNode vector = ASTNode.parseFormula("S");
      VectorCompiler compiler = new VectorCompiler(model, true);
      vector.compile(compiler);

      assertTrue(compiler.getNode().toFormula().equals("{{S_0_0,S_0_1},{S_1_0,S_1_1}}"));
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testInequalityOp() {
    try {
      Model model = new Model();
      ASTNode vector = ASTNode.parseFormula("lt(5,3+2)");
      VectorCompiler compiler = new VectorCompiler(model);
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().equals("0"));
      vector = ASTNode.parseFormula("lt(2, 3)");
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().equals("1"));
      vector = ASTNode.parseFormula("lt(2, {{1,3,5}})");
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().equals("{{0,1,1}}"));
      compiler = new VectorCompiler(model, true);
      vector = ASTNode.parseFormula("lt(5, {{1,3,5}})");
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().replaceAll(" ", "").equals("{{5<1,5<3,5<5}}"));
      vector = ASTNode.parseFormula("lt({{{X},{Y},{0}}}, X)");
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().replaceAll(" ", "").equals("{{{X<X},{Y<X},{0<X}}}"));
      vector = ASTNode.parseFormula("{{1},{2}} < {{X},{Y}}");
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().replaceAll(" ", "").equals("{{1<X},{2<Y}}"));
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }


  /**
   * 
   */
  @Test
  public void testUMinusOp() {
    try {
      Model model = new Model();
      ASTNode vector = ASTNode.parseFormula("-{1,2,3,4,5}");
      VectorCompiler compiler = new VectorCompiler(model);
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().equals("{-1,-2,-3,-4,-5}"));
      compiler = new VectorCompiler(model, true);
      vector = ASTNode.parseFormula("-{{X,Y}}");
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().replaceAll(" ", "").equals("{{-X,-Y}}"));
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testAbsOp() {
    try {
      Model model = new Model();
      ASTNode vector = ASTNode.parseFormula("abs({-1,2,-3,4,-5})");
      VectorCompiler compiler = new VectorCompiler(model);
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().equals("{1,2,3,4,5}"));
      vector = ASTNode.parseFormula("abs(-1)");
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().replaceAll(" ", "").equals("1"));
      compiler = new VectorCompiler(model, true);
      vector = ASTNode.parseFormula("abs({{-X,Y}})");
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().replaceAll(" ", "").equals("{{abs(-X),abs(Y)}}"));
      vector = ASTNode.parseFormula("abs(-X)");
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().replaceAll(" ", "").equals("abs(-X)"));
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testBooleanOp() {
    //    try {
    //      Model model = new Model();
    //      ASTNode vector = ASTNode.parseFormula("1 && {0,1}");
    //      VectorCompiler compiler = new VectorCompiler(model);
    //      vector.compile(compiler);
    //      assertTrue(compiler.getNode().toFormula().equals("{0,1}"));
    //      compiler = new VectorCompiler(model, true);
    //      vector = ASTNode.parseFormula("{a,b,c} && {d,e,f} ");
    //      vector.compile(compiler);
    //      assertTrue(compiler.getNode().toFormula().replaceAll(" ", "").equals("{a&&d,b&&e,c&&f}"));
    //    } catch (ParseException e) {
    //      assertTrue(false);
    //      e.printStackTrace();
    //    }
  }

  /**
   * 
   */
  @Test
  public void testFunctDef() {
    try {
      SBMLDocument doc = new SBMLDocument(3,1);
      Model model = doc.createModel();
      FunctionDefinition x = model.createFunctionDefinition("func");
      ASTNode math = new ASTNode(ASTNode.Type.LAMBDA);
      math.addChild(new ASTNode("x"));
      math.addChild(new ASTNode("y"));
      math.addChild(ASTNode.parseFormula("x+y"));
      x.setMath(math);
      ASTNode vector = ASTNode.parseFormula("func(1,2)");
      VectorCompiler compiler = new VectorCompiler(model);
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().equals("3"));
      math = new ASTNode(ASTNode.Type.LAMBDA);
      math.addChild(new ASTNode("x"));
      math.addChild(new ASTNode("x"));
      x.setMath(math);
      vector = ASTNode.parseFormula("func(1)");
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().equals("1"));
      compiler = new VectorCompiler(model, true);
      vector = ASTNode.parseFormula("func({1,2,3}+{0,0,0})");
      vector.compile(compiler);
      assertTrue(compiler.getNode().getChild(0).toFormula().equals("{1,2,3}"));
      vector = ASTNode.parseFormula("func(X)");
      Parameter n = new Parameter("n");
      model.addParameter(n);
      n.setValue(2);
      Parameter X = new Parameter("X");
      X.setValue(1);
      model.addParameter(X);
      ArraysSBasePlugin arraysSBasePluginX = new ArraysSBasePlugin(X);
      X.addExtension(ArraysConstants.shortLabel, arraysSBasePluginX);
      Dimension dimX = arraysSBasePluginX.createDimension("i");
      dimX.setSize(n.getId());
      dimX.setArrayDimension(0);
      vector.compile(compiler);
      assertTrue(compiler.getNode().getChild(0).toFormula().equals("{X_0,X_1}"));
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

  /**
   * 
   */
  @Test
  public void testPiecewise() {
    try {
      Model model = new Model();
      ASTNode vector = ASTNode.parseFormula("piecewise(1,1)");
      VectorCompiler compiler = new VectorCompiler(model);
      vector.compile(compiler);
      assertTrue(compiler.getNode().toFormula().equals("1"));
      compiler = new VectorCompiler(model, true);
      vector = ASTNode.parseFormula("piecewise({ 1, 2 } , { x > 5, y > 5 }, { 0, 0 })");
      vector.compile(compiler);
      System.out.println("CompilerTest - testPieceWise - formula = " + compiler.getNode().toFormula().replaceAll(" ", ""));
      assertTrue(compiler.getNode().toFormula().replaceAll(" ", "").equals("{piecewise(1,x>5,0),piecewise(2,y>5,0)}"));
      //assertTrue(compiler.getNode().toFormula().replaceAll(" ", "").equals("{piecewise(1,(x>5),0),piecewise(2,(y>5),0)}")); // TODO - might fail again if we correct the formula compiler
      vector = ASTNode.parseFormula("piecewise({ 1, 2 } , { x > 5, y > 5 }, 0)");
      vector.compile(compiler);
      //assertTrue(compiler.getNode().toFormula().replaceAll(" ", "").equals("{piecewise(1,(x>5),0),piecewise(2,(y>5),0)}"));
      assertTrue(compiler.getNode().toFormula().replaceAll(" ", "").equals("{piecewise(1,x>5,0),piecewise(2,y>5,0)}"));
    } catch (ParseException e) {
      assertTrue(false);
      e.printStackTrace();
    }
  }

}
