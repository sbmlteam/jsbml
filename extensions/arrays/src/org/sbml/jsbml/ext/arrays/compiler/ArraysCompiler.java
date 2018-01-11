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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Quantity;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.util.Maths;
import org.sbml.jsbml.util.compilers.ASTNodeCompiler;
import org.sbml.jsbml.util.compilers.ASTNodeValue;

/**
 * This compiler is used to evaluate an {@link ASTNode} object to a real number.
 * 
 * @author Leandro Watanabe
 * @since 1.0
 */
public class ArraysCompiler implements ASTNodeCompiler{

  /**
   * This maps an id to a value.
   */
  private Map<String, Double> idToValue;

  /**
   * Constructs an ArraysCompiler object.
   */
  public ArraysCompiler() {
    idToValue = new HashMap<String, Double>();
  }

  /**
   * Clone constructor
   * @param obj the instance to clone
   */
  public ArraysCompiler(ArraysCompiler obj) {
    idToValue = new HashMap<String,Double>(obj.idToValue);
  }


  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public ArraysCompiler clone(){
    return new ArraysCompiler(this);
  }

  /**
   * Returns {@code true}, if idToValue contains at least one element.
   *
   * @return {@code true}, if idToValue contains at least one element,
   *         otherwise {@code false}.
   */
  public boolean isSetidToValue() {
    if ((idToValue == null) || idToValue.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * Returns the idToValue Map. Creates it if it is not already existing.
   *
   * @return the idToValue Map.
   */
  public Map<String, Double> getMapIdToValue() {
    if (!isSetidToValue()) {
      idToValue = new HashMap<String, Double>();
    }
    return idToValue;
  }


  /**
   * Sets the given map. If idToValue
   * was defined before and contains some elements, they are all unset.
   *
   * @param idToValue
   */
  public void setidToValue(Map<String, Double> idToValue) {
    unsetidToValue();
    this.idToValue = idToValue;
  }


  /**
   * Returns {@code true}, if idToValue contain at least one element,
   *         otherwise {@code false}.
   *
   * @return {@code true}, if idToValue contain at least one element,
   *         otherwise {@code false}.
   */
  public boolean unsetidToValue() {
    if (isSetidToValue()) {
      idToValue = null;
      return true;
    }
    return false;
  }


  /**
   * Adds a new string,double pair to the idToValue.
   * <p>The idToValue is initialized if necessary.
   *
   * @param name the element name to add to the map
   * @param value the value of the element to add to the map
   * @return as specified by {@link Map#put}
   */
  public Double addValue(String name, double value) {
    return getMapIdToValue().put(name, value);
  }

  /**
   * Removes an element from the idToValue.
   *
   * @param id the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed.
   */
  public double removeField(String id) {
    if (isSetidToValue()) {
      return getMapIdToValue().remove(id);
    }
    return -1;
  }

  /**
   * Gets an element from the idToValue, with the given id.
   *
   * @param name the id of the element to get.
   * @return the value of the element from the idToValue with the given id.
   */
  public double getValue(String name) {
    if (isSetidToValue()) {
      return getMapIdToValue().get(name);
    }
    return -1;
  }

  /**
   * Returns the number of values in this {@link ArraysCompiler}.
   * @return
   */
  public int getFieldCount() {
    return isSetidToValue() ? getMapIdToValue().size() : 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#abs(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue abs(ASTNode value) throws SBMLException {

    ASTNodeValue result = value.compile(this);
    if (result.isNumber()) {
      return new ASTNodeValue(Math.abs(result.toDouble()), this);
    } else {
      return unknownValue();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#and(java.util.List)
   */
  @Override
  public ASTNodeValue and(List<ASTNode> values) throws SBMLException {
    boolean result;
    if (values.size() > 0) {
      result = values.get(0).compile(this).toBoolean();
      for (int i = 1; i < values.size(); ++i) {
        result &= values.get(i).compile(this).toBoolean();
      }

      return new ASTNodeValue(result, this);
    }

    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccos(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccos(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Math.acos(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccosh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccosh(ASTNode value) throws SBMLException {
    ASTNodeValue result = value.compile(this);
    if (result.isNumber()) {
      return new ASTNodeValue(Maths.arccosh(result.toDouble()), this);
    } else {
      return unknownValue();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccot(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccot(ASTNode value) throws SBMLException {
    ASTNodeValue result = value.compile(this);
    if (result.isNumber()) {
      return new ASTNodeValue(Maths.arccot(result.toDouble()), this);
    } else {
      return unknownValue();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccoth(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccoth(ASTNode value) throws SBMLException {
    ASTNodeValue result = value.compile(this);
    if (result.isNumber()) {
      return new ASTNodeValue(Maths.arccoth(result.toDouble()), this);
    } else {
      return unknownValue();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccsc(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccsc(ASTNode value) throws SBMLException {
    ASTNodeValue result = value.compile(this);
    if (result.isNumber()) {
      return new ASTNodeValue(Maths.arccsc(result.toDouble()), this);
    } else {
      return unknownValue();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arccsch(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arccsch(ASTNode value) throws SBMLException {
    ASTNodeValue result = value.compile(this);
    if (result.isNumber()) {
      return new ASTNodeValue(Maths.arccsch(result.toDouble()), this);
    } else {
      return unknownValue();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsec(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsec(ASTNode value) throws SBMLException {
    ASTNodeValue result = value.compile(this);
    if (result.isNumber()) {
      return new ASTNodeValue(Maths.arcsec(result.toDouble()), this);
    } else {
      return unknownValue();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsech(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsech(ASTNode value) throws SBMLException {
    ASTNodeValue result = value.compile(this);
    if (result.isNumber()) {
      return new ASTNodeValue(Maths.arcsech(result.toDouble()), this);
    } else {
      return unknownValue();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsin(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsin(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Math.asin(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arcsinh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arcsinh(ASTNode value) throws SBMLException {
    ASTNodeValue result = value.compile(this);
    if (result.isNumber()) {
      return new ASTNodeValue(Maths.arcsinh(result.toDouble()), this);
    } else {
      return unknownValue();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arctan(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arctan(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Math.atan(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#arctanh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue arctanh(ASTNode value) throws SBMLException {
    ASTNodeValue result = value.compile(this);
    if (result.isNumber()) {
      return new ASTNodeValue(Maths.arctanh(result.toDouble()), this);
    } else {
      return unknownValue();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#ceiling(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue ceiling(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Math.ceil(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(org.sbml.jsbml.Compartment)
   */
  @Override
  public ASTNodeValue compile(Compartment c) {
    return new ASTNodeValue(c.getValue(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(double, int, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(double mantissa, int exponent, String units) {
    ASTNode value = new ASTNode(mantissa, exponent);

    return value.compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(double, java.lang.String)
   */
  @Override
  public ASTNodeValue compile(double real, String units) {
    return new ASTNodeValue(real, this);
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
  public ASTNodeValue compile(CallableSBase variable) throws SBMLException {
    if (variable instanceof Quantity) {
      Quantity quantity = (Quantity) variable;
      return new ASTNodeValue(quantity.getValue(), this);
    }

    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(java.lang.String)
   */
  @Override
  public ASTNodeValue compile(String name) {
    if (idToValue.containsKey(name)) {
      return new ASTNodeValue(idToValue.get(name), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cos(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cos(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Math.cos(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cosh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cosh(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Math.cosh(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#cot(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue cot(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Maths.cot(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#coth(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue coth(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Maths.coth(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#csc(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue csc(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Maths.csc(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#csch(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue csch(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Maths.csch(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#delay(java.lang.String, org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode, java.lang.String)
   */
  @Override
  public ASTNodeValue delay(String delayName, ASTNode x, ASTNode delay,
    String timeUnits) throws SBMLException {
    // TODO Auto-generated method stub
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#eq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue eq(ASTNode left, ASTNode right) throws SBMLException {
    ASTNodeValue leftValue = left.compile(this);
    ASTNodeValue rightValue = right.compile(this);
    if (leftValue.isNumber() && rightValue.isNumber()) {
      return new ASTNodeValue(leftValue.toDouble() == rightValue.toDouble(), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#exp(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue exp(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Math.exp(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#factorial(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue factorial(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Maths.factorial(nodeValue.toInteger()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#floor(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue floor(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Math.floor(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#frac(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue frac(ASTNode numerator, ASTNode denominator)
      throws SBMLException {
    ASTNodeValue numValue = numerator.compile(this);
    ASTNodeValue demValue = denominator.compile(this);
    if (numerator.isNumber() && denominator.isNumber()) {
      return new ASTNodeValue(numValue.toDouble() / demValue.toDouble(), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#frac(int, int)
   */
  @Override
  public ASTNodeValue frac(int numerator, int denominator) throws SBMLException {
    return new ASTNodeValue(numerator/denominator, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#function(org.sbml.jsbml.FunctionDefinition, java.util.List)
   */
  @Override
  public ASTNodeValue function(FunctionDefinition functionDefinition,
    List<ASTNode> args) throws SBMLException {
    // TODO Auto-generated method stub
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#function(java.lang.String, java.util.List)
   */
  @Override
  public ASTNodeValue function(String functionDefinitionName, List<ASTNode> args)
      throws SBMLException {
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#geq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue geq(ASTNode left, ASTNode right) throws SBMLException {
    ASTNodeValue leftValue = left.compile(this);
    ASTNodeValue rightValue = right.compile(this);
    if (leftValue.isNumber() && rightValue.isNumber()) {
      return new ASTNodeValue(leftValue.toDouble() >= rightValue.toDouble(), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantAvogadro(java.lang.String)
   */
  @Override
  public ASTNodeValue getConstantAvogadro(String name) {
    // TODO Auto-generated method stub
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getConstantE()
   */
  @Override
  public ASTNodeValue getConstantE() {
    return new ASTNodeValue(Math.E, this);
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
    return new ASTNodeValue(Math.PI, this);
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
    return new ASTNodeValue(Double.NEGATIVE_INFINITY, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#getPositiveInfinity()
   */
  @Override
  public ASTNodeValue getPositiveInfinity() {
    return new ASTNodeValue(Double.POSITIVE_INFINITY, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#gt(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue gt(ASTNode left, ASTNode right) throws SBMLException {
    ASTNodeValue leftValue = left.compile(this);
    ASTNodeValue rightValue = right.compile(this);
    if (leftValue.isNumber() && rightValue.isNumber()) {
      return new ASTNodeValue(leftValue.toDouble() > rightValue.toDouble(), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#lambda(java.util.List)
   */
  @Override
  public ASTNodeValue lambda(List<ASTNode> values) throws SBMLException {
    // TODO Auto-generated method stub
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#leq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue leq(ASTNode left, ASTNode right) throws SBMLException {
    ASTNodeValue leftValue = left.compile(this);
    ASTNodeValue rightValue = right.compile(this);
    if (leftValue.isNumber() && rightValue.isNumber()) {
      return new ASTNodeValue(leftValue.toDouble() <= rightValue.toDouble(), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#ln(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue ln(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Math.log(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#log(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue log(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Math.log10(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#log(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue log(ASTNode base, ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    ASTNodeValue nodeBase = base.compile(this);
    if (nodeValue.isNumber() && nodeBase.isNumber()) {
      return new ASTNodeValue(Maths.log(nodeValue.toDouble(), nodeBase.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#lt(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue lt(ASTNode left, ASTNode right) throws SBMLException {
    ASTNodeValue leftValue = left.compile(this);
    ASTNodeValue rightValue = right.compile(this);
    if (leftValue.isNumber() && rightValue.isNumber()) {
      return new ASTNodeValue(leftValue.toDouble() < rightValue.toDouble(), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#minus(java.util.List)
   */
  @Override
  public ASTNodeValue minus(List<ASTNode> values) throws SBMLException {
    double result = 0;
    if (values.size() > 0) {
      ASTNodeValue astNodeValue = values.get(0).compile(this);
      result = astNodeValue.toDouble();
    }
    for (int i = 1; i < values.size(); ++i) {
      ASTNodeValue astNodeValue = values.get(i).compile(this);
      result -= astNodeValue.toDouble();
    }
    return new ASTNodeValue(result, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#neq(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue neq(ASTNode left, ASTNode right) throws SBMLException {
    ASTNodeValue leftValue = left.compile(this);
    ASTNodeValue rightValue = right.compile(this);
    if (leftValue.isNumber() && rightValue.isNumber()) {
      return new ASTNodeValue(leftValue.toDouble() != rightValue.toDouble(), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#not(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue not(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    return new ASTNodeValue(nodeValue.toBoolean(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#or(java.util.List)
   */
  @Override
  public ASTNodeValue or(List<ASTNode> values) throws SBMLException {
    boolean result;
    if (values.size() > 0) {
      result = values.get(0).compile(this).toBoolean();
      for (int i = 1; i < values.size(); ++i) {
        result |= values.get(i).compile(this).toBoolean();
      }

      return new ASTNodeValue(result, this);
    }

    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#piecewise(java.util.List)
   */
  @Override
  public ASTNodeValue piecewise(List<ASTNode> values) throws SBMLException {
    // TODO Auto-generated method stub
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#plus(java.util.List)
   */
  @Override
  public ASTNodeValue plus(List<ASTNode> values) throws SBMLException {
    double result = 0;
    for (ASTNode value : values) {
      ASTNodeValue astNodeValue = value.compile(this);
      if (astNodeValue.isNumber()) {
        result += astNodeValue.toDouble();
      } else {
        return unknownValue();
      }

    }
    return new ASTNodeValue(result, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#pow(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue pow(ASTNode base, ASTNode exponent) throws SBMLException {
    ASTNodeValue astNodeBase = base.compile(this);
    ASTNodeValue astNodeExp = exponent.compile(this);
    if (astNodeBase.isNumber() && astNodeExp.isNumber()) {
      double result = Math.pow(astNodeBase.toDouble(), astNodeExp.toDouble());
      return new ASTNodeValue(result, this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#root(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue root(ASTNode rootExponent, ASTNode radiant)
      throws SBMLException {
    ASTNodeValue astNodeRad = radiant.compile(this);
    ASTNodeValue astNodeExp = rootExponent.compile(this);
    if (astNodeRad.isNumber() && astNodeExp.isNumber()) {
      double result = Maths.root(astNodeRad.toDouble(), astNodeExp.toDouble());
      return new ASTNodeValue(result, this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#root(double, org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue root(double rootExponent, ASTNode radiant)
      throws SBMLException {
    return root(new ASTNode(rootExponent), radiant);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sec(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sec(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Maths.sec(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sech(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sech(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Maths.sech(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#selector(java.util.List)
   */
  @Override
  public ASTNodeValue selector(List<ASTNode> nodes) throws SBMLException {
    ASTNode object = nodes.get(0);
    ASTNode result;
    if (object.isVector()) {
      result = object;
      for (int i = 1; i < nodes.size(); ++i) {
        int index = (int) nodes.get(i).compile(this).toDouble();
        result = result.getChild(index);

      }
      return result.compile(this);
    } else if (object.isVariable()) {
      compile(object.getVariable());
    }
    else if (object.isString()) {
      compile(object.getName());
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sin(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sin(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Math.sin(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sinh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sinh(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Math.sinh(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#sqrt(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue sqrt(ASTNode radiant) throws SBMLException {
    ASTNodeValue nodeRad = radiant.compile(this);
    if (nodeRad.isNumber()) {
      return new ASTNodeValue(Math.sqrt(nodeRad.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#symbolTime(java.lang.String)
   */
  @Override
  public ASTNodeValue symbolTime(String time) {
    if (idToValue.containsKey(time)) {
      return new ASTNodeValue(idToValue.get(time), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#tan(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue tan(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Math.tan(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#tanh(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue tanh(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(Math.tanh(nodeValue.toDouble()), this);
    }
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#times(java.util.List)
   */
  @Override
  public ASTNodeValue times(List<ASTNode> values) throws SBMLException {
    double result = 0;
    if (values.size() > 0) {
      ASTNodeValue astNodeValue = values.get(0).compile(this);
      result = astNodeValue.toDouble();
    }
    for (int i = 1; i < values.size(); ++i) {
      ASTNodeValue astNodeValue = values.get(i).compile(this);
      result *= astNodeValue.toDouble();
    }
    return new ASTNodeValue(result, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#uMinus(org.sbml.jsbml.ASTNode)
   */
  @Override
  public ASTNodeValue uMinus(ASTNode value) throws SBMLException {
    ASTNodeValue nodeValue = value.compile(this);
    if (nodeValue.isNumber()) {
      return new ASTNodeValue(-nodeValue.toDouble(), this);
    } else {
      return unknownValue();
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#unknownValue()
   */
  @Override
  public ASTNodeValue unknownValue() throws SBMLException {
    return new ASTNodeValue(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#vector(java.util.List)
   */
  @Override
  public ASTNodeValue vector(List<ASTNode> nodes) throws SBMLException {
    // TODO Auto-generated method stub
    return unknownValue();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#xor(java.util.List)
   */
  @Override
  public ASTNodeValue xor(List<ASTNode> values) throws SBMLException {
    boolean result;
    if (values.size() > 0) {
      result = values.get(0).compile(this).toBoolean();
      for (int i = 1; i < values.size(); ++i) {
        result ^= values.get(i).compile(this).toBoolean();
      }

      return new ASTNodeValue(result, this);
    }

    return unknownValue();
  }

  @Override
  public ASTNodeValue max(List<ASTNode> values) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ASTNodeValue min(List<ASTNode> values) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ASTNodeValue quotient(List<ASTNode> values) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ASTNodeValue rem(List<ASTNode> values) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ASTNodeValue implies(List<ASTNode> values) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ASTNodeValue getRateOf(ASTNode name) {
    // TODO Auto-generated method stub
    return null;
  }


}
