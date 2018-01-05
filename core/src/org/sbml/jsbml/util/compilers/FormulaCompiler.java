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
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.util.compilers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.util.StringTools;

/**
 * This class creates C-like infix formula {@link String}s that represent the
 * content of {@link ASTNode}s. These can be used to save equations in SBML with
 * older than Level 2.
 *
 * @author Alexander D&ouml;rr
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */

/*
 * // TODO: check if we can improve the writing of brackets or wrote a method
 * to minimized the bracket, they are few differences with libSBML both way
 * anyway.
 *
 * BIOMODEL 162:
 *
 *
 * KineticLaw (ER_leak_fluxD) MathContainer: infix formula output differ. JSBML
 * formula:
 * (-ERDensity_D_ERM*vL*(1+-0.00166112956810631*Ca_D_Cytosol*1/(0.00166112956810631
 * *Ca_D_ER)))*ERM*1*1/KMOLE // False, should put - instead of +- ? libSBML
 * formula:-(ERDensity_D_ERM*vL*(1+-(0.00166112956810631*Ca_D_Cytosol*(1/(
 * 0.00166112956810631*Ca_D_ER)))))*ERM*1*(1/KMOLE) // False
 */

public class FormulaCompiler extends StringTools implements ASTNodeCompiler {

  /**
   * 
   */
  protected String FORMULA_ARGUMENT_SEPARATOR = " ";

  /**
   * All inverse trigonometric functions may be defined in the infix either using 'arc' as a prefix or simply 'a'
   */
  protected String INVERSE_TRIGONOMETRIC_PREFIX = "a";

  /**
   * String used to represent the exponentiale constant
   */
  protected String EXPONENTIALE = "exponentiale";

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
      append(equation, op, vsb.get(count)); // TODO - could be 'append(equation, " ", op, " ", vsb.get(count));' to add a space between the operator and the arguments. If we change it, we have many tests to update.
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
   * arithmetic symbols and if the given object is already surrounded by
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
        || b.contains("/") || b.contains("^") || b.contains("|") || b.contains("&") 
        || b.contains("<") || b.contains("=") || b.contains(">")) {
      basis = brackets(basis);
    }
    String e = exponent.toString();
    if (e.contains("*") || e.contains("-") || e.contains("+")
        || e.contains("/") || e.contains("^") || b.contains("|") || b.contains("&") 
        || b.contains("<") || b.contains("=") || b.contains(">")) {
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
   * Returns the given selector as {@link StringBuffer}.
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
  public static final StringBuffer times(Object... factors) {
    return arith('*', factors);
  }

  /**
   * Returns the given vector as {@link StringBuffer}.
   *
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#abs(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue abs(ASTNode node) throws SBMLException {
    return function("abs", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#and(java.util.List)
   */
  @Override
  public ASTNodeValue and(List<ASTNode> nodes) throws SBMLException {
    return logicalOperation(" && ", nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccos(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccos(ASTNode node) throws SBMLException {
    return function(INVERSE_TRIGONOMETRIC_PREFIX  + "cos", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccosh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccosh(ASTNode node) throws SBMLException {
    return function(INVERSE_TRIGONOMETRIC_PREFIX  + "cosh", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccot(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccot(ASTNode node) throws SBMLException {
    return function(INVERSE_TRIGONOMETRIC_PREFIX  + "cot", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccoth(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccoth(ASTNode node) throws SBMLException {
    return function(INVERSE_TRIGONOMETRIC_PREFIX  + "coth", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccsc(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccsc(ASTNode node) throws SBMLException {
    return function(INVERSE_TRIGONOMETRIC_PREFIX  + "csc", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccsch(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccsch(ASTNode node) throws SBMLException {
    return function(INVERSE_TRIGONOMETRIC_PREFIX  + "csch", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsec(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsec(ASTNode node) throws SBMLException {
    return function(INVERSE_TRIGONOMETRIC_PREFIX  + "sec", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsech(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsech(ASTNode node) throws SBMLException {
    return function(INVERSE_TRIGONOMETRIC_PREFIX  + "sech", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsin(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsin(ASTNode node) throws SBMLException {
    return function(INVERSE_TRIGONOMETRIC_PREFIX  + "sin", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsinh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsinh(ASTNode node) throws SBMLException {
    return function(INVERSE_TRIGONOMETRIC_PREFIX  + "sinh", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arctan(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arctan(ASTNode node) throws SBMLException {
    return function(INVERSE_TRIGONOMETRIC_PREFIX  + "tan", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arctanh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arctanh(ASTNode node) throws SBMLException {
    return function(INVERSE_TRIGONOMETRIC_PREFIX  + "tanh", node);
  }

  /* (non-Javadoc)
   * @seeorg.sbml.jsbml.util.compilers.ASTNodeCompiler#ceiling(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue ceiling(ASTNode node) throws SBMLException {
    return function("ceil", node);
  }

  /**
   * Creates brackets if needed.
   *
   * @param node
   * @return
   * @throws SBMLException
   */
  protected String checkBrackets(ASTNode node) throws SBMLException {
    String term = node.compile(this).toString();

    if (node.isSum() || node.isDifference() || node.isUMinus() 
        || node.isRelational() || node.isLogical()) 
    {
      term = brackets(term).toString();
    } else if (node.isReal()) {
      if (node.getReal() < 0d) {
        term = brackets(term).toString();
      }
    }

    return term;
  }

  /**
   * Creates brackets if needed.
   *
   * @param node
   * @return
   * @throws SBMLException
   */
  protected String checkDenominatorBrackets(ASTNode node) throws SBMLException {
    if ((node.getType() == Type.POWER) && (node.getChildCount() > 1)
        && node.getRightChild().toString().equals("1")) {
      return checkDenominatorBrackets(node.getLeftChild());
    }
    String term = node.compile(this).toString();
    
    if (node.isSum() || node.isDifference() || node.isUMinus()
        || (node.getType() == Type.TIMES) || node.getType() == Type.DIVIDE
        || node.isRelational() || node.isLogical())
    {
      term = brackets(term).toString();
    }
    return term;
  }

  /**
   * Creates brackets around the given ASTNode if it is not a simple number or String 
   * or a function (meaning the method {@link ASTNode#isFunction()} returns true).
   *
   * @param node the {@link ASTNode} to compile to String
   * @return a String representing the given ASTNode
   * @throws SBMLException - if any error occurs while going through the ASTNode
   */
  protected String checkArgumentBrackets(ASTNode node) throws SBMLException {

    String term = node.compile(this).toString();

    if ((node.isNumber() || node.isString() || node.isFunction()) 
        && (! (node.getType() == ASTNode.Type.FUNCTION_POWER || node.getType() == ASTNode.Type.FUNCTION_REM)))
    {
      return term;
    }

    // for node.isRelational() and node.isLogical(), we want to put brackets 
    
    return term = brackets(term).toString();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(org.sbml.jsbml.Compartment)
   */
  @Override
  public ASTNodeValue compile(Compartment c) {
    return new ASTNodeValue(c.getId(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(double, int, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(double mantissa, int exponent, String units) {
    if (exponent == 0) {
      return new ASTNodeValue(mantissa, this);
    }

    return new ASTNodeValue(concat(
      (new DecimalFormat(StringTools.REAL_FORMAT,
        new DecimalFormatSymbols(Locale.ENGLISH)))
        .format(mantissa), "E", exponent).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(double, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(double real, String units) {
    return new ASTNodeValue(toString(Locale.ENGLISH, real), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(int, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(int integer, String units) {
    return new ASTNodeValue(integer, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(org.sbml.jsbml.CallableSBase)
   */
  @Override
  public ASTNodeValue compile(CallableSBase variable) {
    return new ASTNodeValue(variable.getId(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(java.lang.String)
   */
  @Override
  public ASTNodeValue compile(String name) {
    return new ASTNodeValue(name, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cos(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cos(ASTNode node) throws SBMLException {
    return function("cos", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cosh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cosh(ASTNode node) throws SBMLException {
    return function("cosh", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cot(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cot(ASTNode node) throws SBMLException {
    return function("cot", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#coth(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue coth(ASTNode node) throws SBMLException {
    return function("coth", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#csc(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue csc(ASTNode node) throws SBMLException {
    return function("csc", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#csch(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue csch(ASTNode node) throws SBMLException {
    return function("csch", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#delay(java.lang.String, org.sbml.jsbml.ASTNode, double, java.lang.String)
   */
  @Override
  public ASTNodeValue delay(String delayName, ASTNode x, ASTNode y,
    String timeUnits) throws SBMLException {
    return new ASTNodeValue(concat("delay(", x.compile(this), ", ",
      y.compile(this), ")").toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#eq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue eq(ASTNode left, ASTNode right) throws SBMLException {
    return new ASTNodeValue(relation(left, " == ", right), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#exp(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue exp(ASTNode node) throws SBMLException {
    return function("exp", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#factorial(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue factorial(ASTNode node) {
    return new ASTNodeValue(append(brackets(node), Character.valueOf('!')).toString(), this); // jsbml 0.8 output
    //return function("factorial", node); // libSBML output
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#floor(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue floor(ASTNode node) throws SBMLException {
    return function("floor", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#frac(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue frac(ASTNode numerator, ASTNode denominator)
      throws SBMLException {
    return new ASTNodeValue(
      concat(checkBrackets(numerator),
        Character.valueOf('/'),
        checkDenominatorBrackets(denominator)).toString(),
        this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#frac(int, int)
   */
  @Override
  public ASTNodeValue frac(int numerator, int denominator) {
    return new ASTNodeValue(concat(
      numerator < 0 ? brackets(compile(numerator, null)) : compile(
        numerator, null),
        Character.valueOf('/'),
        denominator < 0 ? brackets(compile(denominator, null))
          : compile(denominator, null)).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#function(org.sbml.jsbml.FunctionDefinition, java.util.List)
   */
  @Override
  public ASTNodeValue function(FunctionDefinition func, List<ASTNode> nodes)
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
  protected ASTNodeValue function(String name, ASTNode... nodes)
      throws SBMLException {
    ArrayList<ASTNode> l = new ArrayList<ASTNode>();
    for (ASTNode node : nodes) {
      l.add(node);
    }
    return new ASTNodeValue(concat(name, brackets(lambdaBody(l))).toString(), this);
  }

  /**
  * Compiles a function with one argument and where we don't want to put the argument
  * between brackets if it is a number or an id. 
  * 
  * @param name the name of the function, can also be just a sign, like not ('!') or '<='.
  * @param node the single argument to the function
  * @return a new {@link ASTNodeValue} that contain the String representation of the function.
  * @throws SBMLException if an error is detected while going through the {@link ASTNode}
  */
 protected ASTNodeValue functionNotAlwaysBrackets(String name, ASTNode node)
     throws SBMLException 
 {
   if (node.isName() || node.isNumber()) {
     return new ASTNodeValue(concat(name, node.compile(this)).toString(), this);
   }
   
   return new ASTNodeValue(concat(name, brackets(node.compile(this))).toString(), this);
 }

  /**
   *
   * @param name
   * @param nodes
   * @return
   * @throws SBMLException
   */
  @Override
  public ASTNodeValue function(String name, List<ASTNode> nodes)
      throws SBMLException
  {
    return new ASTNodeValue(concat(name, brackets(lambdaBody(nodes)))
      .toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#geq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue geq(ASTNode left, ASTNode right) throws SBMLException {
    return new ASTNodeValue(relation(left, " >= ", right), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantAvogadro(java.lang.String)
   */
  @Override
  public ASTNodeValue getConstantAvogadro(String name) {
    return new ASTNodeValue("avogadro", this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantE()
   */
  @Override
  public ASTNodeValue getConstantE() {
    return new ASTNodeValue(EXPONENTIALE, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantFalse()
   */
  @Override
  public ASTNodeValue getConstantFalse() {
    return new ASTNodeValue(false, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantPi()
   */
  @Override
  public ASTNodeValue getConstantPi() {
    return new ASTNodeValue("pi", this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantTrue()
   */
  @Override
  public ASTNodeValue getConstantTrue() {
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getNegativeInfinity()
   */
  @Override
  public ASTNodeValue getNegativeInfinity() {
    return new ASTNodeValue("-INF", this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getPositiveInfinity()
   */
  @Override
  public ASTNodeValue getPositiveInfinity() {
    return new ASTNodeValue("INF", this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#gt(org.sbml.jsbml.ASTNode,  org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue gt(ASTNode left, ASTNode right) throws SBMLException {
    return new ASTNodeValue(relation(left, " > ", right), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#lambda(java.util.List)
   */
  @Override
  public ASTNodeValue lambda(List<ASTNode> nodes) throws SBMLException {
    return new ASTNodeValue(StringTools.concat("lambda",
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
  protected String lambdaBody(List<ASTNode> nodes) throws SBMLException {
    StringBuffer lambda = new StringBuffer();
    for (int i = 0; i < nodes.size(); i++) {
      if (i > 0) {
        lambda.append(", ");
      }
      lambda.append(nodes.get(i).compile(this));
    }
    return lambda.toString();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#leq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue leq(ASTNode left, ASTNode right) throws SBMLException {
    return new ASTNodeValue(relation(left, " <= ", right), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#ln(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue ln(ASTNode node) throws SBMLException {
    return function("ln", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#log(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue log(ASTNode node) throws SBMLException {
    return function("log10", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#log(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue log(ASTNode left, ASTNode right) throws SBMLException {
    return function("log", left, right);
  }

  /**
   *
   * @param operator
   * @param nodes
   * @return
   * @throws SBMLException
   */
  protected ASTNodeValue logicalOperation(String operator, List<ASTNode> nodes)
      throws SBMLException {
    StringBuffer value = new StringBuffer();
    boolean first = true;
    
    if ((nodes != null) && (nodes.size() >= 2)) {
      for (ASTNode node : nodes) {
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
    } else {
      return logicalOperationFunction(operator, nodes);
    }
    
    return new ASTNodeValue(value.toString(), this);
  }
  
  

  protected ASTNodeValue logicalOperationFunction(String operator, List<ASTNode> nodes) {
    if (operator.contains("||")) {
      operator = "or";
    } else if (operator.contains("&&")) {
      operator = "and";
    }
    
    return function(operator, nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#lt(org.sbml.jsbml.ASTNode,  org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue lt(ASTNode left, ASTNode right) throws SBMLException {
    return new ASTNodeValue(relation(left, " < ", right), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#minus(java.util.List)
   */
  @Override
  public ASTNodeValue minus(List<ASTNode> nodes) throws SBMLException {
    if (nodes.size() == 0) {
      return new ASTNodeValue("", this);
    }

    StringBuffer minus = new StringBuffer();

    minus.append(checkBrackets(nodes.get(0)));

    for (int i = 1; i < nodes.size(); i++) {
      if (i > 0) {
        minus.append('-');
      }
      minus.append(checkBrackets(nodes.get(i)));
    }
    return new ASTNodeValue(minus.toString(), this);

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#neq(org.sbml.jsbml.ASTNode,  org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue neq(ASTNode left, ASTNode right) throws SBMLException {
    return new ASTNodeValue(relation(left, " != ", right), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#not(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue not(ASTNode node) throws SBMLException {
    return functionNotAlwaysBrackets("!", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#or(java.util.List)
   */
  @Override
  public ASTNodeValue or(List<ASTNode> nodes) throws SBMLException {
    return logicalOperation(" || ", nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#piecewise(java.util.List)
   */
  @Override
  public ASTNodeValue piecewise(List<ASTNode> nodes) throws SBMLException {
    return function("piecewise", nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#plus(java.util.List)
   */
  @Override
  public ASTNodeValue plus(List<ASTNode> nodes) throws SBMLException {
    StringBuffer plus = new StringBuffer();
    if (nodes.size() == 0) {
      return new ASTNodeValue("", this);
    }

    if (nodes.get(0).isSum()) {
      plus.append(nodes.get(0));
    } else {
      plus.append(checkBrackets(nodes.get(0)));
    }
    

    for (int i = 1; i < nodes.size(); i++) {
      plus.append('+');

      if (nodes.get(i).isSum()) {
        plus.append(nodes.get(i));
      } else {
        plus.append(checkBrackets(nodes.get(i)));
      }
    }
    return new ASTNodeValue(plus.toString(), this);

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#pow(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue pow(ASTNode left, ASTNode right) throws SBMLException {
    return new ASTNodeValue(arith('^', checkArgumentBrackets(left), checkArgumentBrackets(right)).toString(), this);
  }

  /**
   *
   * @param left
   * @param symbol
   * @param right
   * @return
   * @throws SBMLException
   */
  protected String relation(ASTNode left, String symbol, ASTNode right)
      throws SBMLException {

    return (new StringBuffer().append(checkArgumentBrackets(left))
      .append(FORMULA_ARGUMENT_SEPARATOR).append(symbol).append(FORMULA_ARGUMENT_SEPARATOR)
      .append(checkArgumentBrackets(right))).toString();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#root(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue root(ASTNode rootExponent, ASTNode radiant)
      throws SBMLException
  {
    // Writing the root function as '(radiant)^(1/(rootExponent))'
    // TODO: need to reduce the number of parenthesis when possible

    return new ASTNodeValue(StringTools.concat(Character.valueOf('('),
      radiant.compile(this), Character.valueOf(')'), "^", "(1/(",
      rootExponent.compile(this), "))").toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#root(double, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue root(double rootExponent, ASTNode radiant)
      throws SBMLException {
    // Writing the root function as '(radiant)^(1/rootExponent)'

    return new ASTNodeValue(StringTools.concat(Character.valueOf('('),
      radiant.compile(this), Character.valueOf(')'), "^", "(1/",
      rootExponent, ")").toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sec(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sec(ASTNode node) throws SBMLException {
    return function("sec", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sech(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sech(ASTNode node) throws SBMLException {
    return function("sech", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sin(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sin(ASTNode node) throws SBMLException {
    return function("sin", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sinh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sinh(ASTNode node) throws SBMLException {
    return function("sinh", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sqrt(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sqrt(ASTNode node) throws SBMLException {
    return new ASTNodeValue(StringTools.concat(Character.valueOf('('),
      node.compile(this), Character.valueOf(')'), "^", "(0.5)").toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#symbolTime(java.lang.String)
   */
  @Override
  public ASTNodeValue symbolTime(String time) {
    return new ASTNodeValue(time, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#tan(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue tan(ASTNode node) throws SBMLException {
    return function("tan", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#tanh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue tanh(ASTNode node) throws SBMLException {
    return function("tanh", node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#times(java.util.List)
   */
  @Override
  public ASTNodeValue times(List<ASTNode> nodes) throws SBMLException {
    Object n[] = new ASTNodeValue[nodes.size()];
    for (int i = 0; i < nodes.size(); i++) {
      ASTNode ast = nodes.get(i);
      n[i] = new ASTNodeValue(checkBrackets(ast).toString(), this);
    }
    return new ASTNodeValue(times(n).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#uMinus(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue uMinus(ASTNode node) throws SBMLException {
    return new ASTNodeValue(concat(Character.valueOf('-'),
      checkBrackets(node)).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#unknownValue()
   */
  @Override
  public ASTNodeValue unknownValue() throws SBMLException {
    throw new SBMLException(
        "cannot write unknown syntax tree nodes to a formula String");
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#xor(java.util.List)
   */
  @Override
  public ASTNodeValue xor(List<ASTNode> nodes) throws SBMLException {
    return logicalOperation(" xor ", nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#selector(java.util.List)
   */
  @Override
  public ASTNodeValue selector(List<ASTNode> nodes) throws SBMLException {
    Object n[] = new ASTNodeValue[nodes.size()];
    for (int i = 0; i < nodes.size(); i++) {
      ASTNode ast = nodes.get(i);
      n[i] = ast.compile(this);
    }

    return new ASTNodeValue(selector(n).toString(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#vector(java.util.List)
   */
  @Override
  public ASTNodeValue vector(List<ASTNode> nodes) throws SBMLException {
    Object n[] = new ASTNodeValue[nodes.size()];
    for (int i = 0; i < nodes.size(); i++) {
      ASTNode ast = nodes.get(i);
      n[i] = ast.compile(this);
    }

    return new ASTNodeValue(vector(n).toString(), this);
  }

  @Override
  public ASTNodeValue max(List<ASTNode> values) {
    return function("max", values);
  }

  @Override
  public ASTNodeValue min(List<ASTNode> values) {
    return function("min", values);
  }

  @Override
  public ASTNodeValue quotient(List<ASTNode> values) {
    return function("quotient", values);
  }

  @Override
  public ASTNodeValue rem(List<ASTNode> values) {
    return function("rem", values); // TODO - output it as '%' ?
  }

  @Override
  public ASTNodeValue implies(List<ASTNode> values) {
    return function("implies", values);
  }

  @Override
  public ASTNodeValue getRateOf(ASTNode nameAST) {
    return new ASTNodeValue("rateOf(" + nameAST.getName() + ")", this);
  }

}
