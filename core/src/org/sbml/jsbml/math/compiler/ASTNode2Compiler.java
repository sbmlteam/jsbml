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

import java.util.List;

import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.math.ASTNode2;
import org.sbml.jsbml.util.compilers.ASTNodeCompiler;

/**
 * A compiler for abstract syntax trees. This compiler evaluates the values
 * represented by {@link ASTNode2}s. It defines how to perform mathematical or
 * other operations on these data types. Recursion can be performed as follows:
 * 
 * <pre class="brush:java">
 *   public <T> ASTNode2Value<?> doSomeThing(ASTNode2 ast) {
 *     ...
 *     ASTNode2Value child = ast.compile(this);
 *     ...
 *     return new ASTNode2Value(doSomeThing(child), this);
 *   }
 * </pre>
 * 
 * @author Victor Kofia
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public interface ASTNode2Compiler {

  /**
   * The absolute value represented by the given {@link ASTNode2}.
   * 
   * @param value
   *            Must be interpretable as a {@link Number}.
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> abs(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param values
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> and(List<ASTNode2> values) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> arccos(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> arccosh(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> arccot(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> arccoth(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> arccsc(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> arccsch(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> arcsec(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> arcsech(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> arcsin(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> arcsinh(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> arctan(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> arctanh(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> ceiling(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param c
   * @return
   */
  public <T> ASTNode2Value<?> compile(Compartment c);

  /**
   * Creates an {@link ASTNode2Value} that represents a real number in
   * scientific notation, i.e., mantissa * 10^exponent, and the given units.
   * 
   * @param mantissa
   *            The number to be multiplied with ten to the power of the given
   *            exponent.
   * @param exponent
   *            The exponent for the multiplier ten.
   * @param units
   *            The identifier of the units object associated with the number
   *            represented by this element. Can be null if no units have been
   *            defined.
   * @return
   */
  public <T> ASTNode2Value<?> compile(double mantissa, int exponent, String units);

  /**
   * 
   * @param real
   * @param units
   *            A String representing the {@link Unit} of the given number.
   *            This can be the identifier of a {@link UnitDefinition} in the
   *            model or a literal in {@link Kind}. Can be null if no units
   *            have been defined.
   * @return
   */
  public <T> ASTNode2Value<?> compile(double real, String units);

  /**
   * 
   * @param integer
   * @param units
   *            A String representing the {@link Unit} of the given number.
   *            This can be the identifier of a {@link UnitDefinition} in the
   *            model or a literal in {@link Kind}. Can be null if no units
   *            have been defined.
   * @return
   */
  public <T> ASTNode2Value<?> compile(int integer, String units);

  /**
   * 
   * @param variable
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> compile(CallableSBase variable)
      throws SBMLException;

  /**
   * A compiler will also have to deal with a name. The meaning of this can be
   * various. For instance, the name may refer to a {@link Species} in the
   * system. In case of numerical computation, the {@link ASTNodeCompiler}
   * must create an {@link ASTNode2Value} representing the current value of
   * this {@link Species}.
   * 
   * @param name
   * @return
   */
  public <T> ASTNode2Value<?> compile(String name);

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> cos(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> cosh(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> cot(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> coth(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> csc(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> csch(ASTNode2 value) throws SBMLException;

  /**
   * Evaluates delay functions.
   * 
   * @param delayName
   *            the name of this delay function.
   * @param x
   * @param delay
   *            an expression of a positive duration time (the amoutn of
   *            delay)
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> delay(String delayName, ASTNode2 x, ASTNode2 delay) throws SBMLException;

  /**
   * Equal.
   * 
   * @param left
   * @param right
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> eq(ASTNode2 left, ASTNode2 right) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> exp(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> factorial(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> floor(ASTNode2 value) throws SBMLException;

  /**
   * Fraction of two {@link ASTNode2}s
   * 
   * @param numerator
   * @param denominator
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> frac(ASTNode2 numerator, ASTNode2 denominator)
      throws SBMLException;

  /**
   * A fraction of two int values.
   * 
   * @param numerator
   * @param denominator
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> frac(int numerator, int denominator)
      throws SBMLException;

  /**
   * 
   * @param functionDefinition
   * @param args
   *            Values to be inserted into the parameter list of the
   *            function.
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> function(FunctionDefinition functionDefinition,
    List<ASTNode2> args) throws SBMLException;

  /**
   * 
   * @param functionDefinitionName
   * @param args
   *            Values to be inserted into the parameter list of the
   *            function.
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> function(T functionDefinitionName,
    List<ASTNode2> args) throws SBMLException;

  /**
   * Greater equal.
   * 
   * @param left
   * @param right
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> geq(ASTNode2 left, ASTNode2 right) throws SBMLException;

  /**
   * Creates an {@link ASTNode2Value} that represent's Avogadro's number.
   * Optionally, the compiler may associate the given name with this number.
   * 
   * @param name
   *            An optional name for Avogadro's number.
   * @return
   */
  public <T> ASTNode2Value<?> getConstantAvogadro(String name);

  /**
   * 
   * @return
   */
  public <T> ASTNode2Value<?> getConstantE();

  /**
   * 
   * @return
   */
  public <T> ASTNode2Value<?> getConstantFalse();

  /**
   * 
   * @return
   */
  public <T> ASTNode2Value<?> getConstantPi();

  /**
   * 
   * @return
   */
  public <T> ASTNode2Value<?> getConstantTrue();

  /**
   * 
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> getNegativeInfinity() throws SBMLException;

  /**
   * 
   * @return
   */
  public <T> ASTNode2Value<?> getPositiveInfinity();

  /**
   * Greater than.
   * 
   * @param left
   * @param right
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> gt(ASTNode2 left, ASTNode2 right) throws SBMLException;

  /**
   * The body of a {@link FunctionDefinition}.
   * 
   * @param values
   *            Place holders for arguments.
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> lambda(List<ASTNode2> values) throws SBMLException;

  /**
   * Less equal.
   * 
   * @param left
   * @param right
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> leq(ASTNode2 left, ASTNode2 right) throws SBMLException;

  /**
   * Natural logarithm.
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> ln(ASTNode2 value) throws SBMLException;

  /**
   * Logarithm of the given value to base 10.
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> log(ASTNode2 value) throws SBMLException;

  /**
   * Logarithm of the given value to the given base.
   * 
   * @param base
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> log(ASTNode2 base, ASTNode2 value) throws SBMLException;

  /**
   * Less than.
   * 
   * @param left
   * @param right
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> lt(ASTNode2 left, ASTNode2 right) throws SBMLException;

  /**
   * Subtraction.
   * 
   * @param values
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> minus(List<ASTNode2> values) throws SBMLException;

  /**
   * Not equal.
   * 
   * @param left
   * @param right
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> neq(ASTNode2 left, ASTNode2 right) throws SBMLException;

  /**
   * 
   * @param value
   *            This value must be interpretable as a {@link Boolean}.
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> not(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param values
   *            These values must be interpretable as a {@link Boolean}.
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> or(List<ASTNode2> values) throws SBMLException;

  /**
   * 
   * @param values
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> piecewise(List<ASTNode2> values) throws SBMLException;

  /**
   * 
   * @param values
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> plus(List<ASTNode2> values) throws SBMLException;

  /**
   * 
   * @param base
   * @param exponent
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> pow(ASTNode2 base, ASTNode2 exponent)
      throws SBMLException;

  /**
   * 
   * @param rootExponent
   * @param radiant
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> root(ASTNode2 rootExponent, ASTNode2 radiant)
      throws SBMLException;

  /**
   * 
   * @param rootExponent
   * @param radiant
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> root(double rootExponent, ASTNode2 radiant)
      throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> sec(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> sech(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param nodes
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> selector(List<ASTNode2> nodes) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> sin(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> sinh(ASTNode2 value) throws SBMLException;

  /**
   * Square root.
   * 
   * @param radiant
   *            This value must be interpretable as a {@link Number}.
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> sqrt(ASTNode2 radiant) throws SBMLException;

  /**
   * The simulation time.
   * 
   * @param time
   *            The name of the time symbol.
   * @return An {@link ASTNode2Value} that represents the current time.
   */
  public <T> ASTNode2Value<?> symbolTime(String time);

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> tan(ASTNode2 value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> tanh(ASTNode2 value) throws SBMLException;

  /**
   * Product of all given {@link ASTNode2}s.
   * 
   * @param values
   *            These values must be interpretable to {@link Number}.
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> times(List<ASTNode2> values) throws SBMLException;

  /**
   * Unary minus, i.e., negation of the given {@link ASTNode2}.
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> uMinus(ASTNode2 value) throws SBMLException;

  /**
   * Dealing with a malformed {@link ASTNode2}.
   * 
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> unknownValue() throws SBMLException;

  /**
   * Dealing with a vector.
   * @param nodes
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> vector(List<ASTNode2> nodes) throws SBMLException;

  /**
   * Exclusive or.
   * 
   * @param values
   *            It must be possible to evaluate the given values to
   *            {@link Boolean}.
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> xor(List<ASTNode2> values) throws SBMLException;

  /**
   * @param functionDefinitionName
   * @param args
   * @return
   * @throws SBMLException
   */
  public <T> ASTNode2Value<?> function(String functionDefinitionName, List<ASTNode2> args)
      throws SBMLException;

}
