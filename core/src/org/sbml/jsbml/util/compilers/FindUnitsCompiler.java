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
import org.sbml.jsbml.SBMLException;

/**
 * Finds if an ASTNode is using any units attribute.
 * 
 * This {@link ASTNodeCompiler} is basically empty, only methods related to
 * 'cn' elements are implemented
 * 
 * @author Nicolas Rodriguez
 * @since 0.8
 *
 */
public class FindUnitsCompiler implements ASTNodeCompiler {

  /**
   * 
   */
  private boolean isUnitsDefined = false;
  /**
   * 
   */
  private final ASTNodeValue dummyValue = new ASTNodeValue("dummy", null);

  /**
   * @return
   */
  public boolean isUnitsDefined() {
    return isUnitsDefined;
  }

  /**
   * 
   */
  public void reset() {
    isUnitsDefined = false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#abs(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue abs(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#and(java.util.List)
   */
  @Override
  public ASTNodeValue and(List<ASTNode> values) throws SBMLException {
    for (ASTNode value : values) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccos(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccos(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccosh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccosh(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccot(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccot(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccoth(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccoth(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccsc(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccsc(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccsch(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccsch(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsec(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsec(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsech(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsech(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsin(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsin(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsinh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsinh(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arctan(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arctan(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arctanh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arctanh(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#ceiling(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue ceiling(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(org.sbml.jsbml.Compartment)
   */
  @Override
  public ASTNodeValue compile(Compartment c) {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(double, int, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(double mantissa, int exponent, String units) {

    if ((units != null) && (units.length() > 0)) {
      isUnitsDefined  = true;
      throw new SBMLException("Stopping the recursion, a units has been found and the SBML namespace is needed.");
    }

    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(double, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(double real, String units) {

    if ((units != null) && (units.length() > 0)) {
      isUnitsDefined  = true;
      throw new SBMLException("Stopping the recursion, a units has been found and the SBML namespace is needed.");
    }

    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(int, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(int integer, String units) {

    if ((units != null) && (units.length() > 0)) {
      isUnitsDefined  = true;
      throw new SBMLException("Stopping the recursion, a units has been found and the SBML namespace is needed.");
    }

    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(org.sbml.jsbml.CallableSBase)
   */
  @Override
  public ASTNodeValue compile(CallableSBase variable) throws SBMLException {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(java.lang.String)
   */
  @Override
  public ASTNodeValue compile(String name) {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cos(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cos(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cosh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cosh(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cot(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cot(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#coth(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue coth(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#csc(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue csc(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#csch(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue csch(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#delay(java.lang.String, org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode, java.lang.String)
   */
  @Override
  public ASTNodeValue delay(String delayName, ASTNode x, ASTNode delay,
    String timeUnits) throws SBMLException {

    // TODO : to check but I don't think that units are allowed in this case.
    // There are allowed only on the cn elements.
    x.compile(this);
    delay.compile(this);

    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#eq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue eq(ASTNode left, ASTNode right) throws SBMLException {
    left.compile(this);
    right.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#exp(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue exp(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#factorial(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue factorial(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#floor(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue floor(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#frac(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue frac(ASTNode numerator, ASTNode denominator)
      throws SBMLException {
    numerator.compile(this);
    denominator.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#frac(int, int)
   */
  @Override
  public ASTNodeValue frac(int numerator, int denominator)
      throws SBMLException {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#function(org.sbml.jsbml.FunctionDefinition, java.util.List)
   */
  @Override
  public ASTNodeValue function(FunctionDefinition functionDefinition,
    List<ASTNode> args) throws SBMLException {
    for (ASTNode value : args) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#function(java.lang.String, java.util.List)
   */
  @Override
  public ASTNodeValue function(String functionDefinitionName,
    List<ASTNode> args) throws SBMLException {
    for (ASTNode value : args) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#geq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue geq(ASTNode left, ASTNode right) throws SBMLException {
    left.compile(this);
    right.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantAvogadro(java.lang.String)
   */
  @Override
  public ASTNodeValue getConstantAvogadro(String name) {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantE()
   */
  @Override
  public ASTNodeValue getConstantE() {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantFalse()
   */
  @Override
  public ASTNodeValue getConstantFalse() {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantPi()
   */
  @Override
  public ASTNodeValue getConstantPi() {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantTrue()
   */
  @Override
  public ASTNodeValue getConstantTrue() {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getNegativeInfinity()
   */
  @Override
  public ASTNodeValue getNegativeInfinity() throws SBMLException {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getPositiveInfinity()
   */
  @Override
  public ASTNodeValue getPositiveInfinity() {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#gt(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue gt(ASTNode left, ASTNode right) throws SBMLException {
    left.compile(this);
    right.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#lambda(java.util.List)
   */
  @Override
  public ASTNodeValue lambda(List<ASTNode> values) throws SBMLException {
    for (ASTNode value : values) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#leq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue leq(ASTNode left, ASTNode right) throws SBMLException {
    left.compile(this);
    right.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#ln(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue ln(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#log(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue log(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#log(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue log(ASTNode base, ASTNode value) throws SBMLException {
    value.compile(this);
    base.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#lt(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue lt(ASTNode left, ASTNode right) throws SBMLException {
    left.compile(this);
    right.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#minus(java.util.List)
   */
  @Override
  public ASTNodeValue minus(List<ASTNode> values) throws SBMLException {
    for (ASTNode value : values) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#neq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue neq(ASTNode left, ASTNode right) throws SBMLException {
    left.compile(this);
    right.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#not(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue not(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#or(java.util.List)
   */
  @Override
  public ASTNodeValue or(List<ASTNode> values) throws SBMLException {
    for (ASTNode value : values) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#piecewise(java.util.List)
   */
  @Override
  public ASTNodeValue piecewise(List<ASTNode> values) throws SBMLException {
    for (ASTNode value : values) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#plus(java.util.List)
   */
  @Override
  public ASTNodeValue plus(List<ASTNode> values) throws SBMLException {
    for (ASTNode value : values) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#pow(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue pow(ASTNode base, ASTNode exponent)
      throws SBMLException {
    base.compile(this);
    exponent.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#root(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue root(ASTNode rootExponent, ASTNode radiant)
      throws SBMLException {
    rootExponent.compile(this);
    radiant.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#root(double, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue root(double rootExponent, ASTNode radiant)
      throws SBMLException {
    radiant.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sec(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sec(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sech(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sech(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sin(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sin(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sinh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sinh(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sqrt(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sqrt(ASTNode radiant) throws SBMLException {
    radiant.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#symbolTime(java.lang.String)
   */
  @Override
  public ASTNodeValue symbolTime(String time) {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#tan(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue tan(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#tanh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue tanh(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#times(java.util.List)
   */
  @Override
  public ASTNodeValue times(List<ASTNode> values) throws SBMLException {
    for (ASTNode value : values) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#uMinus(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue uMinus(ASTNode value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#unknownValue()
   */
  @Override
  public ASTNodeValue unknownValue() throws SBMLException {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#xor(java.util.List)
   */
  @Override
  public ASTNodeValue xor(List<ASTNode> values) throws SBMLException {
    for (ASTNode value : values) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#selector(java.util.List)
   */
  @Override
  public ASTNodeValue selector(List<ASTNode> values) throws SBMLException {
    function((String) null, values);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#vector(java.util.List)
   */
  @Override
  public ASTNodeValue vector(List<ASTNode> values) throws SBMLException {
    function((String) null, values);
    return dummyValue;
  }

  @Override
  public ASTNodeValue max(List<ASTNode> values) {
    function((String) null, values);
    return dummyValue;
  }

  @Override
  public ASTNodeValue min(List<ASTNode> values) {
    function((String) null, values);
    return dummyValue;
  }

  @Override
  public ASTNodeValue quotient(List<ASTNode> values) {
    function((String) null, values);
    return dummyValue;
  }

  @Override
  public ASTNodeValue rem(List<ASTNode> values) {
    function((String) null, values);
    return dummyValue;
  }

  @Override
  public ASTNodeValue implies(List<ASTNode> values) {
    function((String) null, values);
    return dummyValue;
  }

  @Override
  public ASTNodeValue getRateOf(ASTNode nameAST) {
    return dummyValue;
  }

}
