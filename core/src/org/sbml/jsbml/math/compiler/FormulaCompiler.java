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
import java.util.Arrays;
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
 * @author Andreas Dr&auml;ger
 * @author Victor Kofia
 * @since 1.0
 */
public class FormulaCompiler extends StringTools implements ASTNode2Compiler {

  /**
   * Decides whether prefix or infix notation should be used for the logical
   * operations "and", "or", and "xor".
   */
  private boolean prefixNotationInLogicalOperations = false;

  /**
   * @return the prefixNotationInLogicalOperations
   */
  public boolean isPrefixNotationInLogicalOperations() {
    return prefixNotationInLogicalOperations;
  }

  /**
   * @param prefixNotationInLogicalOperations the prefixNotationInLogicalOperations to set
   */
  public void setPrefixNotationInLogicalOperations(
    boolean prefixNotationInLogicalOperations) {
    this.prefixNotationInLogicalOperations = prefixNotationInLogicalOperations;
  }

  /**
   * Basic method which links several elements with a mathematical operator.
   * All empty StringBuffer object are excluded.
   *
   * @param operator
   * @param elements
   * @return
   */
  private static final <T> StringBuffer arith(char operator, T... elements) {
    List<T> vsb = new Vector<T>();
    for (T sb : elements) {
      if ((sb != null) && (sb.toString().length() > 0)) {
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
   * @return {@code true} if either brackets are set around the given object or the
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
      containsArith(denominator) ? brackets(denominator) : denominator));
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
   * @throws NumberFormatException
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
  public static final <T> StringBuffer times(T... factors) {
    return arith('*', factors);
  }

  /**
   * Returns the given vector as StringBuffer.
   *
   * @param elements
   * @return
   */
  protected static final <T> StringBuffer vector(T... elements) {
    List<T> vsb = new Vector<T>();
    for (T sb : elements) {
      if ((sb != null) && (sb.toString().length() > 0)) {
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#abs(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> abs(ASTNode2 node) throws SBMLException {
    return function("abs", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#and(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> and(List<ASTNode2> nodes) throws SBMLException {
    if (isPrefixNotationInLogicalOperations()) {
      return function("and", nodes);
    }
    return logicalOperation(" && ", nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arccos(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccos(ASTNode2 node) throws SBMLException {
    return function("acos", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arccosh(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccosh(ASTNode2 node) throws SBMLException {
    return function("acosh", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arccot(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccot(ASTNode2 node) throws SBMLException {
    return function("acot", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arccoth(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccoth(ASTNode2 node) throws SBMLException {
    return function("acoth", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arccsc(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccsc(ASTNode2 node) throws SBMLException {
    return function("acsc", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arccsch(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccsch(ASTNode2 node) throws SBMLException {
    return function("acsch", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arcsec(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arcsec(ASTNode2 node) throws SBMLException {
    return function("asec", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arcsech(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arcsech(ASTNode2 node) throws SBMLException {
    return function("asech", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arcsin(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arcsin(ASTNode2 node) throws SBMLException {
    return function("asin", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arcsinh(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arcsinh(ASTNode2 node) throws SBMLException {
    return function("asinh", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arctan(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arctan(ASTNode2 node) throws SBMLException {
    return function("atan", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#arctanh(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arctanh(ASTNode2 node) throws SBMLException {
    return function("atanh", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#ceiling(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> ceiling(ASTNode2 node) throws SBMLException {
    return function("ceil", node);
  }

  /**
   * Creates brackets if needed.
   *
   * @param node
   * @return
   * @throws SBMLException
   */
  protected <T> ASTNode2Value<String> checkBrackets(ASTNode2 node) throws SBMLException {
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
    return new ASTNode2Value<String>(term, this);
  }

  /**
   * Creates brackets if needed.
   *
   * @param nodes
   * @return
   * @throws SBMLException
   */
  protected <T> ASTNode2Value<String> checkDenominatorBrackets(ASTNode2 nodes) throws SBMLException {
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
    return new ASTNode2Value<String>(term, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#compile(org.sbml.jsbml.CallableSBase)
   */
  @Override
  public <T> ASTNode2Value<String> compile(CallableSBase variable) {
    return new ASTNode2Value<String>(variable.getId(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#compile(org.sbml.jsbml.Compartment)
   */
  @Override
  public <T> ASTNode2Value<String> compile(Compartment c) {
    return new ASTNode2Value<String>(c.getId(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#compile(double, int, java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> compile(double mantissa, int exponent, String units) {
    if (exponent == 0) {
      return new ASTNode2Value<String>(StringTools.toString(Locale.ENGLISH, mantissa), this);
    }
    return new ASTNode2Value<String>(concat(
      (new DecimalFormat(StringTools.REAL_FORMAT,
        new DecimalFormatSymbols(Locale.ENGLISH)))
        .format(mantissa), "E", exponent).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#compile(double, java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> compile(double real, String units) {
    return new ASTNode2Value<String>(toString(Locale.ENGLISH, real), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#compile(int, java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<Integer> compile(int integer, String units) {
    return new ASTNode2Value<Integer>(integer, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#compile(java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> compile(String name) {
    return new ASTNode2Value<String>(name, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#cos(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> cos(ASTNode2 node) throws SBMLException {
    return function("cos", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#cosh(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> cosh(ASTNode2 node) throws SBMLException {
    return function("cosh", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#cot(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> cot(ASTNode2 node) throws SBMLException {
    return function("cot", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#coth(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> coth(ASTNode2 node) throws SBMLException {
    return function("coth", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#csc(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> csc(ASTNode2 node) throws SBMLException {
    return function("csc", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#csch(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> csch(ASTNode2 node) throws SBMLException {
    return function("csch", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#delay(java.lang.String, org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> delay(String delayName, ASTNode2 x, ASTNode2 y) throws SBMLException {
    return new ASTNode2Value<String>(concat(delayName, "(", x.compile(this), ", ",
      y.compile(this), ")").toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#eq(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> eq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value<String>(relation(left, " == ", right).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#exp(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> exp(ASTNode2 node) throws SBMLException {
    return function("exp", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#factorial(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> factorial(ASTNode2 node) {
    return new ASTNode2Value<String>(append(brackets(node.toFormula()), Character.valueOf('!')).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#floor(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> floor(ASTNode2 node) throws SBMLException {
    return function("floor", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#frac(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> frac(ASTNode2 numerator, ASTNode2 denominator)
      throws SBMLException {
    return new ASTNode2Value<String>(
        concat(checkBrackets(numerator),
          Character.valueOf('/'),
          checkDenominatorBrackets(denominator)).toString(),
          this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#frac(int, int)
   */
  @Override
  public <T> ASTNode2Value<String> frac(int numerator, int denominator) {
    return new ASTNode2Value<String>(concat(
      numerator < 0 ? brackets(compile(numerator, null)) : compile(
        numerator, null),
        Character.valueOf('/'),
        denominator < 0 ? brackets(compile(denominator, null))
          : compile(denominator, null)).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#function(org.sbml.jsbml.FunctionDefinition, java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> function(FunctionDefinition func, List<ASTNode2> nodes)
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
  protected <T> ASTNode2Value<String> function(String name, ASTNode2... nodes)
      throws SBMLException {
    List<ASTNode2> l = null;
    if (nodes != null) {
      l = Arrays.asList(nodes);
    }
    return function(name, l);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#function(java.lang.String, java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> function(String name,
    List<ASTNode2> l) throws SBMLException {
    return new ASTNode2Value<String>(concat(name, brackets(lambdaBody(l))).toString(), this);
  }

  /**
   *
   * @param name
   * @param nodes
   * @return
   * @throws SBMLException
   */
  @Override
  public <T> ASTNode2Value<String> function(T name, List<ASTNode2> nodes)
      throws SBMLException {
    return new ASTNode2Value<String>(concat(name, brackets(lambdaBody(nodes))).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#geq(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> geq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value<String>(relation(left, " >= ", right).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#getConstantAvogadro(java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> getConstantAvogadro(String name) {
    return new ASTNode2Value<String>(name, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#getConstantE()
   */
  @Override
  public <T> ASTNode2Value<String> getConstantE() {
    return new ASTNode2Value<String>(Character.toString('e'), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#getConstantFalse()
   */
  @Override
  public <T> ASTNode2Value<String> getConstantFalse() {
    return new ASTNode2Value<String>(Boolean.FALSE.toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#getConstantPi()
   */
  @Override
  public <T> ASTNode2Value<String> getConstantPi() {
    return new ASTNode2Value<String>("pi", this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#getConstantTrue()
   */
  @Override
  public <T> ASTNode2Value<String> getConstantTrue() {
    return new ASTNode2Value<String>(Boolean.TRUE.toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#getNegativeInfinity()
   */
  @Override
  public <T> ASTNode2Value<String> getNegativeInfinity() {
    return new ASTNode2Value<String>(StringTools.toString(Locale.ENGLISH, Double.NEGATIVE_INFINITY), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#getPositiveInfinity()
   */
  @Override
  public <T> ASTNode2Value<String> getPositiveInfinity() {
    return new ASTNode2Value<String>(StringTools.toString(Locale.ENGLISH, Double.POSITIVE_INFINITY), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#gt(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> gt(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value<String>(relation(left, " > ", right).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#lambda(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> lambda(List<ASTNode2> nodes) throws SBMLException {
    return new ASTNode2Value<String>(StringTools.concat("lambda",
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
    StringBuilder lambda = new StringBuilder();
    if (nodes != null) {
      for (int i = 0; i < nodes.size(); i++) {
        if (i > 0) {
          lambda.append(", ");
        }
        lambda.append(nodes.get(i).compile(this));
      }
    }
    return lambda.toString();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#leq(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> leq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value<String>(relation(left, " <= ", right).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#ln(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> ln(ASTNode2 node) throws SBMLException {
    return function("log", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#log(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> log(ASTNode2 node) throws SBMLException {
    return function("log10", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#log(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> log(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return function("log", left, right);
  }

  /**
   *
   * @param operator
   * @param nodes
   * @return
   * @throws SBMLException
   */
  protected <T> ASTNode2Value<String> logicalOperation(String operator, List<ASTNode2> nodes)
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
    return new ASTNode2Value<String>(value.toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#lt(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> lt(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value<String>(relation(left, " < ", right).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#minus(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> minus(List<ASTNode2> nodes) throws SBMLException {
    if (nodes.size() == 0) {
      return new ASTNode2Value<String>("", this);
    }

    StringBuffer minus = new StringBuffer();

    minus.append(nodes.get(0).toFormula());

    for (int i = 1; i < nodes.size(); i++) {
      if (i > 0) {
        minus.append('-');
      }
      minus.append(checkBrackets(nodes.get(i)));
    }
    return new ASTNode2Value<String>(minus.toString(), this);

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#neq(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> neq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value<String>(relation(left, " != ", right).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#not(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> not(ASTNode2 node) throws SBMLException {
    return function("!", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#or(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> or(List<ASTNode2> nodes) throws SBMLException {
    if (isPrefixNotationInLogicalOperations()) {
      return function("or", nodes);
    }
    return logicalOperation(" || ", nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#piecewise(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> piecewise(List<ASTNode2> nodes) throws SBMLException {
    return function("piecewise", nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#plus(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> plus(List<ASTNode2> nodes) throws SBMLException {
    StringBuffer plus = new StringBuffer();
    if (nodes.size() == 0) {
      return new ASTNode2Value<String>("", this);
    }

    plus.append(nodes.get(0).toFormula());

    for (int i = 1; i < nodes.size(); i++) {
      plus.append('+');

      plus.append(checkBrackets(nodes.get(i)));

    }
    return new ASTNode2Value<String>(plus.toString(), this);

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#pow(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> pow(ASTNode2 left, ASTNode2 right) throws SBMLException {
    return new ASTNode2Value<String>(pow(left.compile(this), right.compile(this)).toString(), this);
  }

  /**
   *
   * @param left
   * @param symbol
   * @param right
   * @return
   * @throws SBMLException
   */
  protected <T> ASTNode2Value<String> relation(ASTNode2 left, String symbol, ASTNode2 right)
      throws SBMLException {

    return new ASTNode2Value<String>(concat((left instanceof ASTRelationalOperatorNode) ? brackets(left) : left.toFormula(), symbol,
      (right instanceof ASTRelationalOperatorNode) ? brackets(right) : right.toFormula()).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#root(org.sbml.jsbml.math.ASTNode2, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> root(ASTNode2 rootExponent, ASTNode2 radicand)
      throws SBMLException {
    // Writing the root function as '(radiant)^(1/(rootExponent))'
    // TODO: need to reduce the number of parenthesis when possible
    return new ASTNode2Value<String>(StringTools.concat(Character.valueOf('('),
      radicand.compile(this), Character.valueOf(')'), "^", "(1/(",
      rootExponent.compile(this), "))").toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#root(double, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> root(double rootExponent, ASTNode2 radicand)
      throws SBMLException {
    // Writing the root function as '(radiant)^(1/rootExponent)'

    return new ASTNode2Value<String>(StringTools.concat(Character.valueOf('('),
      radicand.compile(this), Character.valueOf(')'), "^", "(1/",
      rootExponent, ")").toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#sec(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sec(ASTNode2 node) throws SBMLException {
    return function("sec", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#sech(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sech(ASTNode2 node) throws SBMLException {
    return function("sech", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#selector(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> selector(List<ASTNode2> nodes) throws SBMLException {
    Object n[] = new ASTNode2Value[nodes.size()];
    for (int i = 0; i < nodes.size(); i++) {
      ASTNode2 ast = nodes.get(i);
      n[i] = ast.compile(this);
    }

    return new ASTNode2Value<String>(selector(n).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#sin(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sin(ASTNode2 node) throws SBMLException {
    return function("sin", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#sinh(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sinh(ASTNode2 node) throws SBMLException {
    return function("sinh", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#sqrt(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sqrt(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value<String>(StringTools.concat(Character.valueOf('('),
      node.compile(this), Character.valueOf(')'), "^", "(0.5)").toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#symbolTime(java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> symbolTime(String time) {
    return new ASTNode2Value<String>(time, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#tan(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> tan(ASTNode2 node) throws SBMLException {
    return function("tan", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#tanh(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> tanh(ASTNode2 node) throws SBMLException {
    return function("tanh", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#times(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> times(List<ASTNode2> nodes) throws SBMLException {
    ASTNode2Value<?> n[] = new ASTNode2Value<?>[nodes.size()];
    for (int i = 0; i < nodes.size(); i++) {
      ASTNode2 ast = nodes.get(i);
      n[i] = new ASTNode2Value<String>(checkBrackets(ast).toString(), this);
    }
    return new ASTNode2Value<String>(times(n).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#uMinus(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> uMinus(ASTNode2 node) throws SBMLException {
    return new ASTNode2Value<String>(concat(Character.valueOf('-'),
      checkBrackets(node)).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#unknownValue()
   */
  @Override
  public <T> ASTNode2Value<String> unknownValue() throws SBMLException {
    throw new SBMLException(
        "cannot write unknown syntax tree nodes to a formula String");
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNode2Compiler#vector(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> vector(List<ASTNode2> nodes) throws SBMLException {
    ASTNode2Value<?> n[] = new ASTNode2Value<?>[nodes.size()];
    for (int i = 0; i < nodes.size(); i++) {
      ASTNode2 ast = nodes.get(i);
      n[i] = ast.compile(this);
    }

    return new ASTNode2Value<String>(vector(n).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#xor(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> xor(List<ASTNode2> nodes) throws SBMLException {
    if (isPrefixNotationInLogicalOperations()) {
      return function("xor", nodes);
    }
    return logicalOperation(" XOR ", nodes); // using upper case character because 'xor' lower case is only allowed in the prefix notation.
  }

}
