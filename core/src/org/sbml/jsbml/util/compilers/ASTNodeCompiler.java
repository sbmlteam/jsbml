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

import java.util.List;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.UnitDefinition;


/**
 * A compiler for abstract syntax trees. This compiler evaluates the values
 * represented by {@link ASTNode}s. It defines how to perform mathematical or
 * other operations on these data types. Recursion can be performed as follows:
 * 
 * <pre class="brush:java">
 *   public ASTNodeValue doSomeThing(ASTNode ast) {
 *     ...
 *     ASTNodeValue child = ast.compile(this);
 *     ...
 *     return new ASTNodeValue(doSomeThing(child), this);
 *   }
 * </pre>
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public interface ASTNodeCompiler {

  /**
   * The absolute value represented by the given {@link ASTNode}.
   * 
   * @param value
   *            Must be interpretable as a {@link Number}.
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue abs(ASTNode value) throws SBMLException;

  /**
   * 
   * @param values
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue and(List<ASTNode> values) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue arccos(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue arccosh(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue arccot(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue arccoth(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue arccsc(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue arccsch(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue arcsec(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue arcsech(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue arcsin(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue arcsinh(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue arctan(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue arctanh(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue ceiling(ASTNode value) throws SBMLException;

  /**
   * 
   * @param c
   * @return
   */
  public ASTNodeValue compile(Compartment c);

  /**
   * Creates an {@link ASTNodeValue} that represents a real number in
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
  public ASTNodeValue compile(double mantissa, int exponent, String units);

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
  public ASTNodeValue compile(double real, String units);

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
  public ASTNodeValue compile(int integer, String units);

  /**
   * 
   * @param variable
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue compile(CallableSBase variable)
      throws SBMLException;

  /**
   * A compiler will also have to deal with a name. The meaning of this can be
   * various. For instance, the name may refer to a {@link Species} in the
   * system. In case of numerical computation, the {@link ASTNodeCompiler}
   * must create an {@link ASTNodeValue} representing the current value of
   * this {@link Species}.
   * 
   * @param name
   * @return
   */
  public ASTNodeValue compile(String name);

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue cos(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue cosh(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue cot(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue coth(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue csc(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue csch(ASTNode value) throws SBMLException;

  /**
   * Evaluates delay functions.
   * 
   * @param delayName
   *            the name of this delay function.
   * @param x
   * @param delay
   *            an expression of a positive duration time (the amoutn of
   *            delay)
   * @param timeUnits
   *            the units for the delay.
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue delay(String delayName, ASTNode x, ASTNode delay,
    String timeUnits) throws SBMLException;

  /**
   * Equal.
   * 
   * @param left
   * @param right
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue eq(ASTNode left, ASTNode right) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue exp(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue factorial(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue floor(ASTNode value) throws SBMLException;

  /**
   * Fraction of two {@link ASTNode}s
   * 
   * @param numerator
   * @param denominator
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue frac(ASTNode numerator, ASTNode denominator)
      throws SBMLException;

  /**
   * A fraction of two int values.
   * 
   * @param numerator
   * @param denominator
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue frac(int numerator, int denominator)
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
  public ASTNodeValue function(FunctionDefinition functionDefinition,
    List<ASTNode> args) throws SBMLException;

  /**
   * 
   * @param functionDefinitionName
   * @param args
   *            Values to be inserted into the parameter list of the
   *            function.
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue function(String functionDefinitionName,
    List<ASTNode> args) throws SBMLException;

  /**
   * Greater equal.
   * 
   * @param left
   * @param right
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue geq(ASTNode left, ASTNode right) throws SBMLException;

  /**
   * Creates an {@link ASTNodeValue} that represent's Avogadro's number.
   * Optionally, the compiler may associate the given name with this number.
   * 
   * @param name
   *            An optional name for Avogadro's number.
   * @return
   */
  public ASTNodeValue getConstantAvogadro(String name);

  /**
   * 
   * @return
   */
  public ASTNodeValue getConstantE();

  /**
   * 
   * @return
   */
  public ASTNodeValue getConstantFalse();

  /**
   * 
   * @return
   */
  public ASTNodeValue getConstantPi();

  /**
   * 
   * @return
   */
  public ASTNodeValue getConstantTrue();

  /**
   * 
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue getNegativeInfinity() throws SBMLException;

  /**
   * 
   * @return
   */
  public ASTNodeValue getPositiveInfinity();

  /**
   * Greater than.
   * 
   * @param left
   * @param right
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue gt(ASTNode left, ASTNode right) throws SBMLException;

  /**
   * The body of a {@link FunctionDefinition}.
   * 
   * @param values
   *            Place holders for arguments.
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue lambda(List<ASTNode> values) throws SBMLException;

  /**
   * Less equal.
   * 
   * @param left
   * @param right
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue leq(ASTNode left, ASTNode right) throws SBMLException;

  /**
   * Natural logarithm.
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue ln(ASTNode value) throws SBMLException;

  /**
   * Logarithm of the given value to base 10.
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue log(ASTNode value) throws SBMLException;

  /**
   * Logarithm of the given value to the given base.
   * 
   * @param base
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue log(ASTNode base, ASTNode value) throws SBMLException;

  /**
   * Less than.
   * 
   * @param left
   * @param right
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue lt(ASTNode left, ASTNode right) throws SBMLException;

  /**
   * Subtraction.
   * 
   * @param values
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue minus(List<ASTNode> values) throws SBMLException;

  /**
   * Not equal.
   * 
   * @param left
   * @param right
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue neq(ASTNode left, ASTNode right) throws SBMLException;

  /**
   * 
   * @param value
   *            This value must be interpretable as a {@link Boolean}.
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue not(ASTNode value) throws SBMLException;

  /**
   * 
   * @param values
   *            These values must be interpretable as a {@link Boolean}.
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue or(List<ASTNode> values) throws SBMLException;

  /**
   * 
   * @param values
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue piecewise(List<ASTNode> values) throws SBMLException;

  /**
   * 
   * @param values
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue plus(List<ASTNode> values) throws SBMLException;

  /**
   * 
   * @param base
   * @param exponent
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue pow(ASTNode base, ASTNode exponent)
      throws SBMLException;

  /**
   * 
   * @param rootExponent
   * @param radiant
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue root(ASTNode rootExponent, ASTNode radiant)
      throws SBMLException;

  /**
   * 
   * @param rootExponent
   * @param radiant
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue root(double rootExponent, ASTNode radiant)
      throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue sec(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue sech(ASTNode value) throws SBMLException;

  /**
   * 
   * @param nodes
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue selector(List<ASTNode> nodes) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue sin(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue sinh(ASTNode value) throws SBMLException;

  /**
   * Square root.
   * 
   * @param radiant
   *            This value must be interpretable as a {@link Number}.
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue sqrt(ASTNode radiant) throws SBMLException;

  /**
   * The simulation time.
   * 
   * @param time
   *            The name of the time symbol.
   * @return An {@link ASTNodeValue} that represents the current time.
   */
  public ASTNodeValue symbolTime(String time);

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue tan(ASTNode value) throws SBMLException;

  /**
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue tanh(ASTNode value) throws SBMLException;

  /**
   * Product of all given {@link ASTNode}s.
   * 
   * @param values
   *            These values must be interpretable to {@link Number}.
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue times(List<ASTNode> values) throws SBMLException;

  /**
   * Unary minus, i.e., negation of the given {@link ASTNode}.
   * 
   * @param value
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue uMinus(ASTNode value) throws SBMLException;

  /**
   * Dealing with a malformed {@link ASTNode}.
   * 
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue unknownValue() throws SBMLException;

  /**
   * Dealing with a vector.
   * @param nodes
   * 
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue vector(List<ASTNode> nodes) throws SBMLException;

  /**
   * Exclusive or.
   * 
   * @param values
   *            It must be possible to evaluate the given values to
   *            {@link Boolean}.
   * @return
   * @throws SBMLException
   */
  public ASTNodeValue xor(List<ASTNode> values) throws SBMLException;

  /**
   * Returns the maximum of the values.
   * 
   * @param values
   * @return the maximum of the values.
   */
  public ASTNodeValue max(List<ASTNode> values);

  /**
   * Returns the minimum of the values.
   * 
   * @param values
   * @return the minimum of the values.
   */
  public ASTNodeValue min(List<ASTNode> values);

  /**
   * Returns the quotient of the division of the values.
   * 
   * @param values
   * @return the quotient of the values.
   * @throws SBMLException if there are not two and only two arguments
   * and it is a problem for the {@link ASTNodeCompiler}.
   */
  public ASTNodeValue quotient(List<ASTNode> values);

  /**
   * Returns the remainder of the division of the values.
   * 
   * @param values
   * @return the remainder of the division of the values.
   * @throws SBMLException if there are not two and only two arguments
   * and it is a problem for the {@link ASTNodeCompiler}.
   */
  public ASTNodeValue rem(List<ASTNode> values);

  /**
   * Returns the logical implies between two {@link ASTNode}.
   * 
   * <p>Truth table:
   *
   *<pre>
   * p   q  p→q
   * 
   * T   T   T
   * T   F   F
   * F   T   T
   * F   F   T
   *</pre>
   *
   * 
   * @param children
   * @return the logical implies between two {@link ASTNode}.
   * @throws SBMLException if there are not two and only two arguments
   * and it is a problem for the {@link ASTNodeCompiler}.
   */
  public ASTNodeValue implies(List<ASTNode> values);

  /**
   * Returns the instantaneous rate of change, with respect to time, of an entity in the model.
   * 
   * <p>It is a function that takes a single argument, an identifier of
   * type SId . The allowable identifiers for use with rateOf in SBML Level 3 Version 2 Core are restricted
   * to those of {@link Compartment}, {@link LocalParameter}, {@link Parameter}, {@link Species}, and {@link SpeciesReference} objects in the
   * enclosing model; in addition, SBML Level 3 packages may define entities with mathematical meaning
   * whose rateOf 's can be referenced. Note that rateOf is not allowed for {@link Reaction} objects, because their
   * identifiers already represent the rate of change of the reaction, and calculating second derivatives is
   * beyond the scope of this construct. Likewise, there is no sensible meaning to be given to the rateOf of
   * a FunctionDefinition, Event, Priority, Delay, or other SBML entities.</p>
   * 
   * @param astNode an ASTNode representing an identifier
   * @return the instantaneous rate of change, with respect to time, of an entity in the model.
   */
  public ASTNodeValue getRateOf(ASTNode astNode);

}
