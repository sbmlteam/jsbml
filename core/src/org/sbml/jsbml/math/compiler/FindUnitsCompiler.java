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
import org.sbml.jsbml.math.ASTNode2;
import org.sbml.jsbml.util.compilers.ASTNodeCompiler;

/**
 * Finds if an ASTNode is using any units attribute.
 * 
 * This {@link ASTNodeCompiler} is basically empty, only methods related to
 * 'cn' elements are implemented
 * 
 * @author Nicolas Rodriguez
 * @author Victor Kofia
 * @since 1.0
 */
public class FindUnitsCompiler implements ASTNode2Compiler {

  /**
   * 
   */
  private boolean isUnitsDefined = false;
  /**
   * 
   */
  private final ASTNode2Value<String> dummyValue = new ASTNode2Value<String>("dummy", null);

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
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#abs(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> abs(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#and(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> and(List<ASTNode2> values) throws SBMLException {
    for (ASTNode2 value : values) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arccos(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccos(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arccosh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccosh(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arccot(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccot(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arccoth(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccoth(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arccsc(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccsc(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arccsch(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arccsch(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arcsec(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arcsec(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arcsech(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arcsech(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arcsin(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arcsin(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arcsinh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arcsinh(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arctan(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arctan(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#arctanh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> arctanh(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#ceiling(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> ceiling(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#compile(org.sbml.jsbml.Compartment)
   */
  @Override
  public <T> ASTNode2Value<String> compile(Compartment c) {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#compile(double, int, java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> compile(double mantissa, int exponent, String units) {

    if ((units != null) && (units.length() > 0)) {
      isUnitsDefined  = true;
      throw new SBMLException("Stopping the recursion, a units has been found and the SBML namespace is needed.");
    }

    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#compile(double, java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> compile(double real, String units) {

    if ((units != null) && (units.length() > 0)) {
      isUnitsDefined  = true;
      throw new SBMLException("Stopping the recursion, a units has been found and the SBML namespace is needed.");
    }

    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#compile(int, java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> compile(int integer, String units) {

    if ((units != null) && (units.length() > 0)) {
      isUnitsDefined  = true;
      throw new SBMLException("Stopping the recursion, a units has been found and the SBML namespace is needed.");
    }

    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#compile(org.sbml.jsbml.CallableSBase)
   */
  @Override
  public <T> ASTNode2Value<String> compile(CallableSBase variable) throws SBMLException {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#compile(java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> compile(String name) {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#cos(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> cos(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#cosh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> cosh(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#cot(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> cot(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#coth(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> coth(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#csc(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> csc(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#csch(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> csch(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#delay(java.lang.String, org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2, java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> delay(String delayName, ASTNode2 x, ASTNode2 delay) throws SBMLException {

    // TODO : to check but I don't think that units are allowed in this case.
    // There are allowed only on the cn elements.
    x.compile(this);
    delay.compile(this);

    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#eq(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> eq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    left.compile(this);
    right.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#exp(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> exp(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#factorial(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> factorial(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#floor(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> floor(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#frac(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> frac(ASTNode2 numerator, ASTNode2 denominator)
      throws SBMLException {
    numerator.compile(this);
    denominator.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#frac(int, int)
   */
  @Override
  public <T> ASTNode2Value<String> frac(int numerator, int denominator)
      throws SBMLException {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#function(org.sbml.jsbml.FunctionDefinition, java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> function(FunctionDefinition functionDefinition,
    List<ASTNode2> args) throws SBMLException {
    for (ASTNode2 value : args) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#function(java.lang.String, java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> function(String functionDefinitionName,
    List<ASTNode2> args) throws SBMLException {
    for (ASTNode2 value : args) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#geq(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> geq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    left.compile(this);
    right.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#getConstantAvogadro(java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> getConstantAvogadro(String name) {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#getConstantE()
   */
  @Override
  public <T> ASTNode2Value<String> getConstantE() {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#getConstantFalse()
   */
  @Override
  public <T> ASTNode2Value<String> getConstantFalse() {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#getConstantPi()
   */
  @Override
  public <T> ASTNode2Value<String> getConstantPi() {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#getConstantTrue()
   */
  @Override
  public <T> ASTNode2Value<String> getConstantTrue() {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#getNegativeInfinity()
   */
  @Override
  public <T> ASTNode2Value<String> getNegativeInfinity() throws SBMLException {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#getPositiveInfinity()
   */
  @Override
  public <T> ASTNode2Value<String> getPositiveInfinity() {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#gt(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> gt(ASTNode2 left, ASTNode2 right) throws SBMLException {
    left.compile(this);
    right.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#lambda(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> lambda(List<ASTNode2> values) throws SBMLException {
    for (ASTNode2 value : values) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#leq(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> leq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    left.compile(this);
    right.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#ln(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> ln(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#log(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> log(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#log(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> log(ASTNode2 base, ASTNode2 value) throws SBMLException {
    value.compile(this);
    base.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#lt(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> lt(ASTNode2 left, ASTNode2 right) throws SBMLException {
    left.compile(this);
    right.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#minus(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> minus(List<ASTNode2> values) throws SBMLException {
    for (ASTNode2 value : values) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#neq(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> neq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    left.compile(this);
    right.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#not(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> not(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#or(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> or(List<ASTNode2> values) throws SBMLException {
    for (ASTNode2 value : values) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#piecewise(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> piecewise(List<ASTNode2> values) throws SBMLException {
    for (ASTNode2 value : values) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#plus(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> plus(List<ASTNode2> values) throws SBMLException {
    for (ASTNode2 value : values) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#pow(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> pow(ASTNode2 base, ASTNode2 exponent)
      throws SBMLException {
    base.compile(this);
    exponent.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#root(org.sbml.jsbml.ASTNode2, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> root(ASTNode2 rootExponent, ASTNode2 radiant)
      throws SBMLException {
    rootExponent.compile(this);
    radiant.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#root(double, org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> root(double rootExponent, ASTNode2 radiant)
      throws SBMLException {
    radiant.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#sec(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sec(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#sech(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sech(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#sin(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sin(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#sinh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sinh(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#sqrt(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> sqrt(ASTNode2 radiant) throws SBMLException {
    radiant.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#symbolTime(java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> symbolTime(String time) {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#tan(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> tan(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#tanh(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> tanh(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#times(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> times(List<ASTNode2> values) throws SBMLException {
    for (ASTNode2 value : values) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#uMinus(org.sbml.jsbml.ASTNode2)
   */
  @Override
  public <T> ASTNode2Value<String> uMinus(ASTNode2 value) throws SBMLException {
    value.compile(this);
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#unknownValue()
   */
  @Override
  public <T> ASTNode2Value<String> unknownValue() throws SBMLException {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#xor(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> xor(List<ASTNode2> values) throws SBMLException {
    for (ASTNode2 value : values) {
      value.compile(this);
    }
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#selector(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> selector(List<ASTNode2> value) throws SBMLException {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compilers.ASTNode2Compiler#vector(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> vector(List<ASTNode2> nodes) throws SBMLException {
    return dummyValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.compiler.ASTNode2Compiler#function(java.lang.Object, java.util.List)
   */
  @Override
  public <T> ASTNode2Value<?> function(T functionDefinitionName,
    List<ASTNode2> args) throws SBMLException {
    // TODO Auto-generated method stub
    return null;
  }

}
