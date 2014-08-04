/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2014  jointly by the following organizations: 
 * 1. The University of Tuebingen, Germany 
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
 * 3. The California Institute of Technology, Pasadena, CA, USA 
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 6. The University of Toronto, Toronto, ON, Canada
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation. A copy of the license agreement is provided 
 * in the file named "LICENSE.txt" included with this software distribution 
 * and also available online as <http://sbml.org/Software/JSBML/License>. 
 * ---------------------------------------------------------------------------- 
 */
package org.sbml.jsbml.math.compiler;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.math.ASTCnRealNode;
import org.sbml.jsbml.math.ASTNode2;
import org.sbml.jsbml.math.ASTPowerNode;
import org.sbml.jsbml.math.ASTRelationalOperatorNode;
import org.sbml.jsbml.util.StringTools;


/**
 * This class creates C-like infix formula {@link String}s that represent the
 * content of {@link ASTNode2}s. These can be used to save equations in SBML with
 * older than Level 2.
 * 
 * @author Alexander D&ouml;rr
 * @author Andreas Dr&auml;ger
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date Aug 2, 2014
 */
public class FormulaCompiler extends StringTools implements ASTNode2Compiler {
  
  /**
   * Basic method which links several elements with a mathematical operator.
   * All empty StringBuffer object are excluded.
   * 
   * @param operator
   * @param elements
   * @return
   */
  private static final StringBuffer arith(char operator, Object... elements) {
    List<Object> vsb = new Vector<Object>();
    for (Object sb : elements) {
      if (sb != null && sb.toString().length() > 0) {
        vsb.add(sb);
      }
    }
    StringBuffer equation = new StringBuffer();
    if (vsb.size() > 0) {
      equation.append(vsb.get(0));
    }
    Character op = Character.valueOf(operator);
    for (int count = 1; count < vsb.size(); count++) {
      append(equation, op, vsb.get(count));
    }
    return equation;
  }

  /**
   * 
   * @param sb
   * @return
   */
  public static final StringBuffer brackets(Object sb) {
    return concat(Character.valueOf('('), sb, Character.valueOf(')'));
  }

  /**
   * Tests whether the String representation of the given object contains any
   * arithmetic symbols and if the given object is already sorrounded by
   * brackets.
   * 
   * @param something
   * @return True if either brackets are set around the given object or the
   *         object does not contain any symbols such as +, -, *, /.
   */
  private static boolean containsArith(Object something) {
    boolean arith = false;
    String d = something.toString();
    if (d.length() > 0) {
      char c;
      for (int i = 0; (i < d.length()) && !arith; i++) {
        c = d.charAt(i);
        arith = ((c == '+') || (c == '-') || (c == '*') || (c == '/'));
      }
    }
    return arith;
  }

  /**
   * Returns the difference of the given elements as StringBuffer.
   * 
   * @param subtrahents
   * @return
   */
  public static final StringBuffer diff(Object... subtrahents) {
    if (subtrahents.length == 1) {
      return brackets(concat(Character.valueOf('-'), subtrahents));
    }
    return brackets(arith('-', subtrahents));
  }

  /**
   * Returns a fraction with the given elements as numerator and denominator.
   * 
   * @param numerator
   * @param denominator
   * @return
   */
  public static final StringBuffer frac(Object numerator, Object denominator) {
    return brackets(arith('/',
      (containsArith(numerator) ? brackets(numerator) : numerator),
      containsArith(denominator) ? brackets(denominator)
        : denominator));
  }

  /**
   * Returns the id of a PluginSpeciesReference object's belonging species as
   * an object of type StringBuffer.
   * 
   * @param ref
   * @return
   */
  protected static final StringBuffer getSpecies(SpeciesReference ref) {
    return new StringBuffer(ref.getSpecies());
  }

  /**
   * Returns the value of a PluginSpeciesReference object's stoichiometry
   * either as a double or, if the stoichiometry has an integer value, as an
   * int object.
   * 
   * @param ref
   * @return
   */
  protected static final double getStoichiometry(SpeciesReference ref) {
    double stoich = ref.getStoichiometry();
    return stoich;
  }

  /**
   * Returns the basis to the power of the exponent as StringBuffer. Several
   * special cases are treated.
   * 
   * @param basis
   * @param exponent
   * @return
   */
  public static final StringBuffer pow(Object basis, Object exponent) {
    try {
      if (Double.parseDouble(exponent.toString()) == 0f) {
        return new StringBuffer("1");
      }
      if (Double.parseDouble(exponent.toString()) == 1f) {
        return basis instanceof StringBuffer ? (StringBuffer) basis
          : new StringBuffer(basis.toString());
      }
    } catch (NumberFormatException exc) {
    }
    String b = basis.toString();
    if (b.contains("*") || b.contains("-") || b.contains("+")
        || b.contains("/") || b.contains("^")) {
      basis = brackets(basis);
    }
    String e = exponent.toString();
    if (e.contains("*") || e.contains("-") || e.contains("+")
        || e.contains("/") || e.contains("^")) {
      exponent = brackets(e);
    }
    return arith('^', basis, exponent);
  }

  /**
   * Returns the exponent-th root of the basis as StringBuffer.
   * 
   * @param exponent
   * @param basis
   * @return
   * @throws IllegalFormatException
   *             If the given exponent represents a zero.
   */
  public static final StringBuffer root(Object exponent, Object basis)
      throws NumberFormatException {
    if (Double.parseDouble(exponent.toString()) == 0f) {
      throw new NumberFormatException(
          "Cannot extract a zeroth root of anything");
    }
    if (Double.parseDouble(exponent.toString()) == 1f) {
      return new StringBuffer(basis.toString());
    }
    return concat("root(", exponent, Character.valueOf(','), basis,
      Character.valueOf(')'));
  }

  /**
   * Returns the given selector as StringBuffer.
   * 
   * @param operator
   * @param elements
   * @return
   */
  protected static final StringBuffer selector(Object... elements) {
    List<Object> vsb = new Vector<Object>();
    for (Object sb : elements) {
      if (sb != null && sb.toString().length() > 0) {
        vsb.add(sb);
      }
    }
    StringBuffer equation = new StringBuffer();

    if (vsb.size() > 0) {
      equation.append(vsb.get(0));
    }
    for (int count = 1; count < vsb.size(); count++) {
      append(equation, "[", vsb.get(count), "]");
    }
    return equation;
  }
  
  /**
   * 
   * @param basis
   * @return
   */
  public static final StringBuffer sqrt(Object basis) {
    try {
      return root(Integer.valueOf(2), basis);
    } catch (IllegalFormatException e) {
      return pow(basis, frac(Integer.valueOf(1), Integer.valueOf(2)));
    }
  }

  /**
   * Returns the sum of the given elements as StringBuffer.
   * 
   * @param summands
   * @return
   */
  public static final StringBuffer sum(Object... summands) {
    return brackets(arith('+', summands));
  }

  /**
   * Returns the product of the given elements as StringBuffer.
   * 
   * @param factors
   * @return
   */
  public static final StringBuffer times(Object... factors) {
    return arith('*', factors);
  }
  
  /**
   * Returns the given vector as StringBuffer.
   * 
   * @param operator
   * @param elements
   * @return
   */
  protected static final StringBuffer vector(Object... elements) {
    List<Object> vsb = new Vector<Object>();
    for (Object sb : elements) {
      if (sb != null && sb.toString().length() > 0) {
        vsb.add(sb);
      }
    }
    StringBuffer equation = new StringBuffer();
    equation.append("{");
    
    if (vsb.size() > 0) {
      equation.append(vsb.get(0));
    }
    Character op = Character.valueOf(',');
    for (int count = 1; count < vsb.size(); count++) {
      append(equation, op, vsb.get(count));
    }
    equation.append("}");
    return equation;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#abs(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value abs(ASTNode2 node) throws SBMLException {
    return function("abs", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#and(java.util.List)
   */
  @Override
  public ASTNode2Value and(List<ASTNode2> nodes) throws SBMLException {
    return logicalOperation(" and ", nodes);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arccos(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value arccos(ASTNode2 node) throws SBMLException {
    return function("acos", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arccosh(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value arccosh(ASTNode2 node) throws SBMLException {
    return function("acosh", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arccot(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value arccot(ASTNode2 node) throws SBMLException {
    return function("acot", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arccoth(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value arccoth(ASTNode2 node) throws SBMLException {
    return function("acoth", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arccsc(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value arccsc(ASTNode2 node) throws SBMLException {
    return function("acsc", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arccsch(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value arccsch(ASTNode2 node) throws SBMLException {
    return function("acsch", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arcsec(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value arcsec(ASTNode2 node) throws SBMLException {
    return function("asec", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arcsech(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value arcsech(ASTNode2 node) throws SBMLException {
    return function("asech", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arcsin(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value arcsin(ASTNode2 node) throws SBMLException {
    return function("asin", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arcsinh(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value arcsinh(ASTNode2 node) throws SBMLException {
    return function("asinh", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arctan(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value arctan(ASTNode2 node) throws SBMLException {
    return function("atan", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arctanh(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value arctanh(ASTNode2 node) throws SBMLException {
    return function("atanh", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#ceiling(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value ceiling(ASTNode2 node) throws SBMLException {
    return function("ceil", node);
  }

  /**
   * Creates brackets if needed.
   * 
   * @param node
   * @return
   * @throws SBMLException
   */
  protected String checkBrackets(ASTNode2 node) throws SBMLException {
    String term = node.compile(this).toString();
    switch(node.getType()) {
    case SUM:
    case MINUS:
    term = brackets(term).toString();
    break;
    case REAL:
      if (((ASTCnRealNode)node).getReal() < 0d) {
        term = brackets(term).toString();
      }
      break;
    default:
      break;
    }
    return term;
  }

  /**
   * Creates brackets if needed.
   * 
   * @param nodes
   * @return
   * @throws SBMLException
   */
  protected String checkDenominatorBrackets(ASTNode2 nodes) throws SBMLException {
    if ((nodes.getType() == Type.POWER) && (nodes.getChildCount() > 1)
        && ((ASTPowerNode)nodes).getExponent().toString().equals("1")) {
      return checkDenominatorBrackets(nodes);
    }
    String term = nodes.compile(this).toString();
    switch(nodes.getType()) {
    case SUM:
    case MINUS:
    case TIMES:
      term = brackets(term).toString();
      break;
    default:
      break;
    }
    return term;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#compile(org.sbml.jsbml.Compartment)
   */
  @Override
  public ASTNode2Value compile(Compartment c) {
    return new ASTNode2Value(c.getId(), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#compile(double, int, java.lang.String)
   */
  @Override
  public ASTNode2Value compile(double mantissa, int exponent, String units) {
    if (exponent == 0) {
      return new ASTNode2Value(mantissa, this);
    }

    return new ASTNode2Value(concat(
      (new DecimalFormat(StringTools.REAL_FORMAT,
        new DecimalFormatSymbols(Locale.ENGLISH)))
        .format(mantissa), "E", exponent).toString(), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#compile(double, java.lang.String)
   */
  @Override
  public ASTNode2Value compile(double real, String units) {
    return new ASTNode2Value(toString(Locale.ENGLISH, real), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#compile(int, java.lang.String)
   */
  @Override
  public ASTNode2Value compile(int integer, String units) {
    return new ASTNode2Value(integer, this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#compile(org.sbml.jsbml.CallableSBase)
   */
  @Override
  public ASTNode2Value compile(CallableSBase variable) {
    return new ASTNode2Value(variable.getId(), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#compile(java.lang.String)
   */
  @Override
  public ASTNode2Value compile(String name) {
    return new ASTNode2Value(name, this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#cos(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value cos(ASTNode2 node) throws SBMLException {
    return function("cos", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#cosh(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value cosh(ASTNode2 node) throws SBMLException {
    return function("cosh", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#cot(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value cot(ASTNode2 node) throws SBMLException {
    return function("cot", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#coth(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value coth(ASTNode2 node) throws SBMLException {
    return function("coth", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#csc(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value csc(ASTNode2 node) throws SBMLException {
    return function("csc", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#csch(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value csch(ASTNode2 node) throws SBMLException {
    return function("csch", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#delay(java.lang.String, org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value delay(String delayName, ASTNode2 x, ASTNode2 y) throws SBMLException {
    return new ASTNode2Value(concat("delay(", x.compile(this), ", ",
      y.compile(this), ")").toString(), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#eq(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value eq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value(relation(left, " == ", right), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#exp(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value exp(ASTNode2 node) throws SBMLException {
    return function("exp", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#factorial(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value factorial(ASTNode2 node) {
    return new ASTNode2Value(append(brackets(node.toFormula()), Character.valueOf('!'))
      .toString(), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#floor(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value floor(ASTNode2 node) throws SBMLException {
    return function("floor", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#frac(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value frac(ASTNode2 numerator, ASTNode2 denominator)
      throws SBMLException {
    return new ASTNode2Value(
      concat(checkBrackets(numerator),
        Character.valueOf('/'),
        checkDenominatorBrackets(denominator)).toString(),
        this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#frac(int, int)
   */
  @Override
  public ASTNode2Value frac(int numerator, int denominator) {
    return new ASTNode2Value(concat(
      numerator < 0 ? brackets(compile(numerator, null)) : compile(
        numerator, null),
        Character.valueOf('/'),
        denominator < 0 ? brackets(compile(denominator, null))
          : compile(denominator, null)).toString(), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#function(org.sbml.jsbml.FunctionDefinition, java.util.List)
   */
  @Override
  public ASTNode2Value function(FunctionDefinition func, List<ASTNode2> nodes)
      throws SBMLException {
    return function(func.getId(), nodes);
  }

  /**
   * 
   * @param name
   * @param nodes
   * @return
   * @throws SBMLException
   */
  protected ASTNode2Value function(String name, ASTNode2... nodes)
      throws SBMLException {
    ArrayList<ASTNode2> l = new ArrayList<ASTNode2>();
    for (ASTNode2 node : nodes) {
      l.add(node);
    }
    return new ASTNode2Value(concat(name, brackets(lambdaBody(l)))
      .toString(), this);
  }

  /**
   * 
   * @param name
   * @param nodes
   * @return
   * @throws SBMLException
   */
  @Override
  public ASTNode2Value function(String name, List<ASTNode2> nodes)
      throws SBMLException
      {
    return new ASTNode2Value(concat(name, brackets(lambdaBody(nodes)))
      .toString(), this);
      }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#geq(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value geq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value(relation(left, " >= ", right), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#getConstantAvogadro(java.lang.String)
   */
  @Override
  public ASTNode2Value getConstantAvogadro(String name) {
    return new ASTNode2Value("avogadro", this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#getConstantE()
   */
  @Override
  public ASTNode2Value getConstantE() {
    return new ASTNode2Value(Character.toString('e'), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#getConstantFalse()
   */
  @Override
  public ASTNode2Value getConstantFalse() {
    return new ASTNode2Value(false, this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#getConstantPi()
   */
  @Override
  public ASTNode2Value getConstantPi() {
    return new ASTNode2Value("pi", this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#getConstantTrue()
   */
  @Override
  public ASTNode2Value getConstantTrue() {
    return new ASTNode2Value(true, this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#getNegativeInfinity()
   */
  @Override
  public ASTNode2Value getNegativeInfinity() {
    return new ASTNode2Value(Double.NEGATIVE_INFINITY, this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#getPositiveInfinity()
   */
  @Override
  public ASTNode2Value getPositiveInfinity() {
    return new ASTNode2Value(Double.POSITIVE_INFINITY, this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#gt(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value gt(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value(relation(left, " > ", right), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#lambda(java.util.List)
   */
  @Override
  public ASTNode2Value lambda(List<ASTNode2> nodes) throws SBMLException {
    return new ASTNode2Value(StringTools.concat("lambda",
      brackets(lambdaBody(nodes))).toString(), this);
  }

  /**
   * Creates the body of a lambda function, i.e., the argument list and the
   * actual mathematical operation, all comma separated and surrounded in
   * brackets.
   * 
   * @param nodes
   * @return
   * @throws SBMLException
   */
  protected String lambdaBody(List<ASTNode2> nodes) throws SBMLException {
    StringBuffer lambda = new StringBuffer();
    for (int i = 0; i < nodes.size(); i++) {
      if (i > 0) {
        lambda.append(", ");
      }
      lambda.append(nodes.get(i).compile(this));
    }
    return lambda.toString();
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#leq(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value leq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value(relation(left, " <= ", right), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#ln(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value ln(ASTNode2 node) throws SBMLException {
    return function("log", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#log(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value log(ASTNode2 node) throws SBMLException {
    return function("log10", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#log(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value log(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return function("log", left, right);
  }

  /**
   * 
   * @param operator
   * @param nodes
   * @return
   * @throws SBMLException
   */
  protected ASTNode2Value logicalOperation(String operator, List<ASTNode2> nodes)
      throws SBMLException {
    StringBuffer value = new StringBuffer();
    boolean first = true;
    for (ASTNode2 node : nodes) {
      if (!first) {
        value.append(operator);
      } else {
        first = false;
      }
      if (node.getChildCount() > 0) {
        append(value, Character.valueOf('('), node.compile(this)
          .toString(), Character.valueOf(')'));
      } else {
        value.append(node.compile(this).toString());
      }
    }
    return new ASTNode2Value(value.toString(), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#lt(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value lt(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value(relation(left, " < ", right), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#minus(java.util.List)
   */
  @Override
  public ASTNode2Value minus(List<ASTNode2> nodes) throws SBMLException {
    if (nodes.size() == 0) {
      return new ASTNode2Value("", this);
    }

    StringBuffer minus = new StringBuffer();

    minus.append(nodes.get(0).toFormula());

    for (int i = 1; i < nodes.size(); i++) {
      if (i > 0) {
        minus.append('-');
      }
      minus.append(checkBrackets(nodes.get(i)));
    }
    return new ASTNode2Value(minus.toString(), this);

  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#neq(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value neq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value(relation(left, " != ", right), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#not(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value not(ASTNode2 node) throws SBMLException {
    return function("not", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#or(java.util.List)
   */
  @Override
  public ASTNode2Value or(List<ASTNode2> nodes) throws SBMLException {
    return logicalOperation(" or ", nodes);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#piecewise(java.util.List)
   */
  @Override
  public ASTNode2Value piecewise(List<ASTNode2> nodes) throws SBMLException {
    return function("piecewise", nodes);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#plus(java.util.List)
   */
  @Override
  public ASTNode2Value plus(List<ASTNode2> nodes) throws SBMLException {
    StringBuffer plus = new StringBuffer();
    if (nodes.size() == 0) {
      return new ASTNode2Value("", this);
    }

    plus.append(nodes.get(0).toFormula());

    for (int i = 1; i < nodes.size(); i++) {
      plus.append('+');

      plus.append(checkBrackets(nodes.get(i)));

    }
    return new ASTNode2Value(plus.toString(), this);

  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#pow(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value pow(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value(pow(left.compile(this), right.compile(this)).toString(), this);
  }

  /**
   * 
   * @param left
   * @param symbol
   * @param right
   * @return
   * @throws SBMLException
   */
  protected String relation(ASTNode2 left, String symbol, ASTNode2 right)
      throws SBMLException {

    return concat((left instanceof ASTRelationalOperatorNode) ? brackets(left) : left.toFormula(), symbol,
      (right instanceof ASTRelationalOperatorNode) ? brackets(right) : right.toFormula()).toString();
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#root(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value root(ASTNode2 rootExponent, ASTNode2 radicand)
      throws SBMLException
      {
    // Writing the root function as '(radiant)^(1/(rootExponent))'
    // TODO: need to reduce the number of parenthesis when possible

    return new ASTNode2Value(StringTools.concat(Character.valueOf('('),
      radicand.compile(this), Character.valueOf(')'), "^", "(1/(",
      rootExponent.compile(this), "))").toString(), this);
      }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#root(double, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value root(double rootExponent, ASTNode2 radicand)
      throws SBMLException {
    // Writing the root function as '(radiant)^(1/rootExponent)'

    return new ASTNode2Value(StringTools.concat(Character.valueOf('('),
      radicand.compile(this), Character.valueOf(')'), "^", "(1/",
      rootExponent, ")").toString(), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#sec(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value sec(ASTNode2 node) throws SBMLException {
    return function("sec", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#sech(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value sech(ASTNode2 node) throws SBMLException {
    return function("sech", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#sin(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value sin(ASTNode2 node) throws SBMLException {
    return function("sin", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#sinh(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value sinh(ASTNode2 node) throws SBMLException {
    return function("sinh", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#sqrt(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value sqrt(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value(StringTools.concat(Character.valueOf('('),
      node.compile(this), Character.valueOf(')'), "^", "(0.5)").toString(), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#symbolTime(java.lang.String)
   */
  @Override
  public ASTNode2Value symbolTime(String time) {
    return new ASTNode2Value(time, this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#tan(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value tan(ASTNode2 node) throws SBMLException {
    return function("tan", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#tanh(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value tanh(ASTNode2 node) throws SBMLException {
    return function("tanh", node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#times(java.util.List)
   */
  @Override
  public ASTNode2Value times(List<ASTNode2> nodes) throws SBMLException {
    Object n[] = new ASTNode2Value[nodes.size()];
    for (int i = 0; i < nodes.size(); i++) {
      ASTNode2 ast = nodes.get(i);
      n[i] = new ASTNode2Value(checkBrackets(ast).toString(), this);
    }
    return new ASTNode2Value(times(n).toString(), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#uMinus(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value uMinus(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value(concat(Character.valueOf('-'),
      checkBrackets(node)).toString(), this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#unknownValue()
   */
  @Override
  public ASTNode2Value unknownValue() throws SBMLException {
    throw new SBMLException(
        "cannot write unknown syntax tree nodes to a formula String");
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#xor(java.util.List)
   */
  @Override
  public ASTNode2Value xor(List<ASTNode2> nodes) throws SBMLException {
    return logicalOperation(" xor ", nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#selector(java.util.List)
   */
  @Override
  public ASTNode2Value selector(List<ASTNode2> nodes) throws SBMLException {
    Object n[] = new ASTNode2Value[nodes.size()];
    for (int i = 0; i < nodes.size(); i++) {
      ASTNode2 ast = nodes.get(i);
      n[i] = ast.compile(this);
    }
    
    return new ASTNode2Value(selector(n).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#vector(java.util.List)
   */
  @Override
  public ASTNode2Value vector(List<ASTNode2> nodes) throws SBMLException {
    Object n[] = new ASTNode2Value[nodes.size()];
    for (int i = 0; i < nodes.size(); i++) {
      ASTNode2 ast = nodes.get(i);
      n[i] = ast.compile(this);
    }
    
    return new ASTNode2Value(vector(n).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#compile(double, double, java.lang.String)
   */
  @Override
  public ASTNode2Value compile(double mantissa, double exponent, String units) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#compile(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value compile(ASTNode2 node) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#frac(double, double)
   */
  @Override
  public ASTNode2Value frac(double numerator, double denominator)
    throws SBMLException {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#compile(int)
   */
  @Override
  public ASTNode2Value compile(int integer) {
    return new ASTNode2Value(integer, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#delay(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public ASTNode2Value delay(ASTNode2 x, ASTNode2 y) throws SBMLException {
    return new ASTNode2Value(concat("delay(", x.compile(this), ", ",
      y.compile(this), ")").toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#bvar(java.util.List)
   */
  @Override
  public ASTNode2Value bvar(List<ASTNode2> value) throws SBMLException {
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#otherwise(java.util.List)
   */
  @Override
  public ASTNode2Value otherwise(List<ASTNode2> children) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#piece(java.util.List)
   */
  @Override
  public ASTNode2Value piece(List<ASTNode2> children) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#degree(java.util.List)
   */
  @Override
  public ASTNode2Value degree(List<ASTNode2> children) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#logbase(java.util.List)
   */
  @Override
  public ASTNode2Value logbase(List<ASTNode2> children) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#compile(double)
   */
  @Override
  public ASTNode2Value compile(double real) {
    return new ASTNode2Value(toString(Locale.ENGLISH, real), this);
  }
  
}
