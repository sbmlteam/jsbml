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
package org.sbml.jsbml.ext.arrays.compiler;

import java.util.ArrayList;
import java.util.List;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.Quantity;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Variable;
import org.sbml.jsbml.ext.arrays.Dimension;
import org.sbml.jsbml.util.compilers.ASTNodeCompiler;
import org.sbml.jsbml.util.compilers.ASTNodeValue;

/**
 * This compiler is used to check if an {@link ASTNode} object contains only
 * constant values or {@link Dimension} ids.
 * 
 * @author Leandro Watanabe
 * @since 1.0
 */
public class StaticallyComputableCompiler implements ASTNodeCompiler {

  /**
   * This model gives context to the ASTNode being evaluated.
   */
  private final Model model;

  /**
   * This list contains ids that can be included in the math.
   */
  private List<String> constantIds;

  /**
   * Indicates if constantIds has been initialized.
   */
  boolean isSetConstantIds;

  /**
   * Constructs a new StaticallyComputableCompiler object
   * 
   * @param model
   */
  public StaticallyComputableCompiler(Model model) {
    this.model = model;
  }

  /**
   * Add an id to the list
   * 
   * @param id
   */
  public void addConstantId(String id) {
    if (!isSetConstantIds) {
      constantIds = new ArrayList<String>();
    }
    constantIds.add(id);
    isSetConstantIds = true;
  }

  /**
   * Removes an id from the list.
   * 
   * @param id
   * 
   * @return
   */
  public boolean removeConstantId(String id) {
    if (isSetConstantIds) {
      boolean success = constantIds.remove(id);
      if (constantIds.isEmpty()) {
        isSetConstantIds = false;
      }
      return success;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#abs(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue abs(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#and(java.util.List)
   */
  @Override
  public ASTNodeValue and(List<ASTNode> values) throws SBMLException {
    for (ASTNode value : values) {
      ASTNodeValue result = value.compile(this);
      if (!result.toBoolean()) {
        return new ASTNodeValue(false, this);
      }
    }
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccos(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccos(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccosh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccosh(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccot(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccot(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccoth(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccoth(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccsc(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccsc(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccsch(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccsch(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsec(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsec(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsech(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsech(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsin(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsin(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsinh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsinh(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arctan(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arctan(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arctanh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arctanh(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#ceiling(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue ceiling(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(org.sbml.jsbml.Compartment)
   */
  @Override
  public ASTNodeValue compile(Compartment c) {
    return new ASTNodeValue(c.isConstant(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(double, int, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(double mantissa, int exponent, String units) {
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(double, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(double real, String units) {
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(int, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(int integer, String units) {
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(org.sbml.jsbml.CallableSBase)
   */
  @Override
  public ASTNodeValue compile(CallableSBase variable) throws SBMLException {
    if (variable instanceof Variable) {
      Variable var = (Variable) variable;
      return new ASTNodeValue(var.isConstant(), this);
    }
    else if (variable instanceof Quantity) {
      return new ASTNodeValue(true, this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(java.lang.String)
   */
  @Override
  public ASTNodeValue compile(String name) {
    NamedSBase sbase = model.findNamedSBase(name);
    if (sbase != null) {
      if (sbase instanceof Variable) {
        Variable var = (Variable) sbase;
        return new ASTNodeValue(var.getConstant(), this);
      }
      return new ASTNodeValue(false, this);
    }

    if (isSetConstantIds) {
      if (constantIds.contains(name)) {
        return new ASTNodeValue(true, this);
      }
    }

    return new ASTNodeValue(false, this);

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cos(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cos(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cosh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cosh(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cot(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cot(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#coth(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue coth(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#csc(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue csc(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#csch(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue csch(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#delay(java.lang.String, org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode, java.lang.String)
   */
  @Override
  public ASTNodeValue delay(String delayName, ASTNode x, ASTNode delay,
    String timeUnits) throws SBMLException {
    return delay.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#eq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue eq(ASTNode left, ASTNode right) throws SBMLException {
    ASTNodeValue leftNode = left.compile(this);
    ASTNodeValue rightNode = right.compile(this);
    return new ASTNodeValue(leftNode.toBoolean() && rightNode.toBoolean(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#exp(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue exp(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#factorial(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue factorial(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#floor(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue floor(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#frac(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue frac(ASTNode numerator, ASTNode denominator)
      throws SBMLException {
    ASTNodeValue numNode = numerator.compile(this);
    ASTNodeValue denNode = denominator.compile(this);
    return new ASTNodeValue(numNode.toBoolean() && denNode.toBoolean(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#frac(int, int)
   */
  @Override
  public ASTNodeValue frac(int numerator, int denominator) throws SBMLException {
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#function(org.sbml.jsbml.FunctionDefinition, java.util.List)
   */
  @Override
  public ASTNodeValue function(FunctionDefinition functionDefinition,
    List<ASTNode> args) throws SBMLException {
    for (ASTNode value : args) {
      ASTNodeValue result = value.compile(this);
      if (!result.toBoolean()) {
        return new ASTNodeValue(false, this);
      }
    }
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#function(java.lang.String, java.util.List)
   */
  @Override
  public ASTNodeValue function(String functionDefinitionName, List<ASTNode> args)
      throws SBMLException {
    for (ASTNode value : args) {
      ASTNodeValue result = value.compile(this);
      if (!result.toBoolean()) {
        return new ASTNodeValue(false, this);
      }
    }
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#geq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue geq(ASTNode left, ASTNode right) throws SBMLException {
    ASTNodeValue leftNode = left.compile(this);
    ASTNodeValue rightNode = right.compile(this);
    return new ASTNodeValue(leftNode.toBoolean() && rightNode.toBoolean(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantAvogadro(java.lang.String)
   */
  @Override
  public ASTNodeValue getConstantAvogadro(String name) {
    return compile(name);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantE()
   */
  @Override
  public ASTNodeValue getConstantE() {
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantFalse()
   */
  @Override
  public ASTNodeValue getConstantFalse() {
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantPi()
   */
  @Override
  public ASTNodeValue getConstantPi() {
    return new ASTNodeValue(true, this);
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
  public ASTNodeValue getNegativeInfinity() throws SBMLException {
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getPositiveInfinity()
   */
  @Override
  public ASTNodeValue getPositiveInfinity() {
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#gt(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue gt(ASTNode left, ASTNode right) throws SBMLException {
    ASTNodeValue leftNode = left.compile(this);
    ASTNodeValue rightNode = right.compile(this);
    return new ASTNodeValue(leftNode.toBoolean() && rightNode.toBoolean(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#lambda(java.util.List)
   */
  @Override
  public ASTNodeValue lambda(List<ASTNode> values) throws SBMLException {
    for (ASTNode value : values) {
      ASTNodeValue result = value.compile(this);
      if (!result.toBoolean()) {
        return new ASTNodeValue(false, this);
      }
    }
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#leq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue leq(ASTNode left, ASTNode right) throws SBMLException {
    ASTNodeValue leftNode = left.compile(this);
    ASTNodeValue rightNode = right.compile(this);
    return new ASTNodeValue(leftNode.toBoolean() && rightNode.toBoolean(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#ln(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue ln(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#log(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue log(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#log(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue log(ASTNode base, ASTNode value) throws SBMLException {
    ASTNodeValue baseNode = base.compile(this);
    ASTNodeValue valueNode = value.compile(this);
    return new ASTNodeValue(baseNode.toBoolean() && valueNode.toBoolean(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#lt(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue lt(ASTNode left, ASTNode right) throws SBMLException {
    ASTNodeValue leftNode = left.compile(this);
    ASTNodeValue rightNode = right.compile(this);
    return new ASTNodeValue(leftNode.toBoolean() && rightNode.toBoolean(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#minus(java.util.List)
   */
  @Override
  public ASTNodeValue minus(List<ASTNode> values) throws SBMLException {
    for (ASTNode value : values) {
      ASTNodeValue result = value.compile(this);
      if (!result.toBoolean()) {
        return new ASTNodeValue(false, this);
      }
    }
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#neq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue neq(ASTNode left, ASTNode right) throws SBMLException {
    ASTNodeValue leftNode = left.compile(this);
    ASTNodeValue rightNode = right.compile(this);
    return new ASTNodeValue(leftNode.toBoolean() && rightNode.toBoolean(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#not(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue not(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#or(java.util.List)
   */
  @Override
  public ASTNodeValue or(List<ASTNode> values) throws SBMLException {
    for (ASTNode value : values) {
      ASTNodeValue result = value.compile(this);
      if (!result.toBoolean()) {
        return new ASTNodeValue(false, this);
      }
    }
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#piecewise(java.util.List)
   */
  @Override
  public ASTNodeValue piecewise(List<ASTNode> values) throws SBMLException {
    for (ASTNode value : values) {
      ASTNodeValue result = value.compile(this);
      if (!result.toBoolean()) {
        return new ASTNodeValue(false, this);
      }
    }
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#plus(java.util.List)
   */
  @Override
  public ASTNodeValue plus(List<ASTNode> values) throws SBMLException {
    for (ASTNode value : values) {
      ASTNodeValue result = value.compile(this);
      if (!result.toBoolean()) {
        return new ASTNodeValue(false, this);
      }
    }
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#pow(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue pow(ASTNode base, ASTNode exponent) throws SBMLException {
    ASTNodeValue baseNode = base.compile(this);
    ASTNodeValue expNode = exponent.compile(this);
    return new ASTNodeValue(baseNode.toBoolean() && expNode.toBoolean(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#root(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue root(ASTNode rootExponent, ASTNode radiant)
      throws SBMLException {
    ASTNodeValue rootNode = rootExponent.compile(this);
    ASTNodeValue radiantNode = radiant.compile(this);
    return new ASTNodeValue(rootNode.toBoolean() && radiantNode.toBoolean(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#root(double, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue root(double rootExponent, ASTNode radiant)
      throws SBMLException {
    ASTNodeValue radiantNode = radiant.compile(this);
    return new ASTNodeValue(radiantNode.toBoolean(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sec(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sec(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sech(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sech(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#selector(java.util.List)
   */
  @Override
  public ASTNodeValue selector(List<ASTNode> nodes) throws SBMLException {

    for (int i = 1; i < nodes.size(); ++i){
      ASTNode value = nodes.get(i);
      ASTNodeValue result = value.compile(this);
      if (!result.toBoolean()) {
        return new ASTNodeValue(false, this);
      }
    }
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sin(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sin(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sinh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sinh(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sqrt(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sqrt(ASTNode radiant) throws SBMLException {
    return radiant.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#symbolTime(java.lang.String)
   */
  @Override
  public ASTNodeValue symbolTime(String time) {
    return new ASTNodeValue(false, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#tan(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue tan(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#tanh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue tanh(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#times(java.util.List)
   */
  @Override
  public ASTNodeValue times(List<ASTNode> values) throws SBMLException {
    for (ASTNode value : values) {
      ASTNodeValue result = value.compile(this);
      if (!result.toBoolean()) {
        return new ASTNodeValue(false, this);
      }
    }
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#uMinus(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue uMinus(ASTNode value) throws SBMLException {
    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#unknownValue()
   */
  @Override
  public ASTNodeValue unknownValue() throws SBMLException {
    return new ASTNodeValue(false, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#vector(java.util.List)
   */
  @Override
  public ASTNodeValue vector(List<ASTNode> nodes) throws SBMLException {
    for (ASTNode value : nodes) {
      ASTNodeValue result = value.compile(this);
      if (!result.toBoolean()) {
        return new ASTNodeValue(false, this);
      }
    }
    return new ASTNodeValue(true, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#xor(java.util.List)
   */
  @Override
  public ASTNodeValue xor(List<ASTNode> values) throws SBMLException {
    for (ASTNode value : values) {
      ASTNodeValue result = value.compile(this);
      if (!result.toBoolean()) {
        return new ASTNodeValue(false, this);
      }
    }
    return new ASTNodeValue(true, this);
  }

  @Override
  public ASTNodeValue max(List<ASTNode> values) {
    return function((String) null, values);
  }

  @Override
  public ASTNodeValue min(List<ASTNode> values) {
    return function((String) null, values);
  }

  @Override
  public ASTNodeValue quotient(List<ASTNode> values) {
    return function((String) null, values);
  }

  @Override
  public ASTNodeValue rem(List<ASTNode> values) {
    return function((String) null, values);
  }

  @Override
  public ASTNodeValue implies(List<ASTNode> values) {
    return function((String) null, values);
  }

  @Override
  public ASTNodeValue getRateOf(ASTNode nameAST) {
    List<ASTNode> values = new ArrayList<ASTNode>();
    values.add(nameAST);
    
    return function("rateOf", values);
  }
}
