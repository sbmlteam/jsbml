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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Quantity;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.math.ASTArithmeticOperatorNode;
import org.sbml.jsbml.math.ASTCnIntegerNode;
import org.sbml.jsbml.math.ASTCnNumberNode;
import org.sbml.jsbml.math.ASTCnRationalNode;
import org.sbml.jsbml.math.ASTLambdaFunctionNode;
import org.sbml.jsbml.math.ASTMinusNode;
import org.sbml.jsbml.math.ASTNode2;
import org.sbml.jsbml.util.Maths;
import org.sbml.jsbml.util.StringTools;

/**
 * Derives the units from mathematical operations.
 * 
 * @author Andreas Dr&auml;ger
 * @author Victor Kofia
 * @since 0.8
 */
public class UnitsCompiler implements ASTNode2Compiler {

  /**
   * SBML level field
   */
  private final int level;
  /**
   * SBML version field
   */
  private final int version;
  /**
   * The model associated to this compiler.
   */
  private Model model;
  /**
   * Necessary for function definitions to remember the units of the argument
   * list.
   */
  private HashMap<String, ASTNode2Value<?>> namesToUnits;

  /**
   * 
   */
  public UnitsCompiler() {
    this(-1, -1);
  }

  /**
   * 
   * @param level
   * @param version
   */
  public UnitsCompiler(int level, int version) {
    this.level = level;
    this.version = version;
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    namesToUnits = new HashMap<String, ASTNode2Value<?>>();
  }

  /**
   * 
   * @param model
   */
  public UnitsCompiler(Model model) {
    this(model.getLevel(), model.getVersion());
    this.model = model;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#abs(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> abs(ASTNode2 value) throws SBMLException {
    ASTNode2Value<Double> v = new ASTNode2Value<Double>(this);
    if (value instanceof ASTArithmeticOperatorNode || value instanceof ASTMinusNode
        || value instanceof ASTCnNumberNode) {
      v.setValue(Double.valueOf(Math.abs(value.compile(this).toDouble())));
    }
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#and(org.sbml.jsbml.ASTNode2Value[])
   */
  @Override
  public <T> ASTNode2Value<Boolean> and(List<ASTNode2> values) throws SBMLException {
    ASTNode2Value<Boolean> value = dimensionless(Boolean.class);
    boolean val = true;
    for (ASTNode2 v : values) {
      val &= v.compile(this).toBoolean();
    }
    value.setValue(val);
    return value;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#arccos(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> arccos(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Math.acos(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#arccosh(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> arccosh(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Maths.arccosh(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#arccot(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> arccot(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Maths.arccot(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#arccoth(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> arccoth(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Maths.arccoth(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#arccsc(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> arccsc(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Maths.arccsc(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#arccsch(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> arccsch(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Maths.arccsch(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#arcsec(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> arcsec(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Maths.arcsec(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#arcsech(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> arcsech(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Maths.arcsech(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#arcsin(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> arcsin(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Math.asin(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#arcsinh(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> arcsinh(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Maths.arcsinh(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#arctan(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> arctan(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Math.atan(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#arctanh(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> arctanh(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Maths.arctanh(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#ceiling(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> ceiling(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    v.setValue(Math.ceil(v.toDouble()));
    return v;
  }

  /**
   * Compile boolean values
   * 
   * @param b
   * @return
   */
  public <T> ASTNode2Value<Boolean> compile(boolean b) {
    UnitDefinition ud = new UnitDefinition(level, version);
    ud.addUnit(Kind.DIMENSIONLESS);
    ASTNode2Value<Boolean> value = new ASTNode2Value<Boolean>(b, this);
    value.setUnits(ud);
    return value;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#compile(org.sbml.jsbml.Compartment)
   */
  @Override
  // TODO: Verify specific type T i.e. ASTNode2Value<CallableSBase>
  public <T> ASTNode2Value<CallableSBase> compile(Compartment c) {
    return compile((CallableSBase) c);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#compile(double, int, java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> compile(double mantissa, int exponent, String units) {
    return compile(mantissa * Math.pow(10, exponent), units);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#compile(double, java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<String> compile(double real, String units) {
    ASTNode2Value<String> v = new ASTNode2Value<String>(StringTools.toString(Locale.ENGLISH, real), this);
    UnitDefinition ud;
    if (Unit.Kind.isValidUnitKindString(units, level, version)) {
      ud = new UnitDefinition(level, version);
      ud.addUnit(Unit.Kind.valueOf(units.toUpperCase()));
      v.setUnits(ud);
    } else if (model != null) {
      ud = model.getUnitDefinition(units);
      if (ud != null) {
        v.setUnits(ud);
      }
    }
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#compile(int, java.lang.String)
   */
  @Override
  // TODO: Specify specific type T i.e. ASTNode2Value<Integer>
  public <T> ASTNode2Value compile(int integer, String units) {
    return compile((double) integer, units);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(org.sbml.jsbml.CallableSBase)
   */
  @Override
  public <T> ASTNode2Value<CallableSBase> compile(CallableSBase variable) {
    // TODO: Specify specific type T i.e. ASTNode2Value<CallableSBase>
    ASTNode2Value value = new ASTNode2Value<CallableSBase>(variable, this);
    if (variable instanceof Quantity) {
      Quantity q = (Quantity) variable;
      if (q.isSetValue()) {
        value.setValue(Double.valueOf(q.getValue()));
      }
    }
    value.setUnits(variable.getDerivedUnitDefinition());
    return value;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#compile(java.lang.String)
   */
  @Override
  // TODO: Specify specific type T i.e. ASTNode2Value<String>
  public <T> ASTNode2Value<?> compile(String name) {
    if (namesToUnits.containsKey(name)) {
      return namesToUnits.get(name);
    }
    return new ASTNode2Value<String>(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#cos(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> cos(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Math.cos(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#cosh(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> cosh(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Math.cosh(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#cot(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> cot(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Maths.cot(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#coth(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> coth(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Maths.coth(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#csc(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> csc(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Maths.csc(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#csch(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> csch(ASTNode2 value) throws SBMLException {
    // TODO: Specify specific type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Maths.csch(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#delay(java.lang.String, org.sbml.jsbml.ASTNode2Value, double, java.lang.String)
   */
  @Override
  // TODO: Specify specific type T i.e. ASTNode2Value<String>
  public <T> ASTNode2Value<?> delay(String delayName, ASTNode2 x, ASTNode2 delay)
      throws SBMLException {
    /*
     * This represents a delay function. The delay function has the form
     * delay(x, d), taking two MathML expressions as arguments. Its value is
     * the value of argument x at d time units before the current time.
     * There are no restrictions on the form of x. The units of the d
     * parameter are determined from the built-in time. The value of the d
     * parameter, when evaluated, must be numerical (i.e., a number in
     * MathML real, integer, or e-notation format) and be greater than or
     * equal to 0. (v2l4)
     */
    UnitDefinition value = x.compile(this).getUnits().clone();
    UnitDefinition time = delay.compile(this).getUnits().clone();
    if (model.getTimeUnitsInstance() != null) {
      if (!UnitDefinition.areEquivalent(model.getTimeUnitsInstance(),
        time)) {
        throw new IllegalArgumentException(
          new UnitException(
            String.format(
              "Units of time in a delay function do not match. Given %s and %s.",
              UnitDefinition.printUnits(model
                .getTimeUnitsInstance()),
                UnitDefinition.printUnits(time))));
      }
    }
    // not the correct value, need insight into time scale to return
    // the correct value
    return new ASTNode2Value(value, this);
  }

  /**
   * Creates a dimensionless unit definition object encapsulated in an
   * {@link ASTNode2Value}.
   * @param type The type of the dimensionless value, e.g., Boolean.class etc.
   * 
   * @return
   */
  private <T> ASTNode2Value<T> dimensionless(Class<T> type) {
    UnitDefinition ud = new UnitDefinition(level, version);
    ud.addUnit(Unit.Kind.DIMENSIONLESS);
    return new ASTNode2Value<T>(ud, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#equal(org.sbml.jsbml.ASTNode2Value, org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Boolean> eq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    ASTNode2Value<Boolean> v = dimensionless(Boolean.class);
    ASTNode2Value<?> leftvalue, rightvalue;
    leftvalue = left.compile(this);
    rightvalue = right.compile(this);
    unifyUnits(leftvalue, rightvalue);
    v.setValue(leftvalue.toDouble() == rightvalue.toDouble());
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#exp(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<?> exp(ASTNode2 value) throws SBMLException {
    ASTNode2Value<?> v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    return pow(getConstantE(), v);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#factorial(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Long> factorial(ASTNode2 value) throws SBMLException {
    // TODO: Perform unit test to verify type T (Long)
    ASTNode2Value<Long> v = new ASTNode2Value<Long>(Maths.factorial((int) Math
      .round(value.compile(this).toDouble())), this);
    if (value instanceof ASTCnNumberNode  && ((ASTCnNumberNode<?>)value).isSetUnits()) {
      v.setUnits(((ASTCnNumberNode<?>)value).getUnitsInstance());
    } else {
      v.setLevel(level);
      v.setVersion(version);
    }
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#floor(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> floor(ASTNode2 value) throws SBMLException {
    // TODO: Specify type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    v.setValue(Math.floor(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#frac(org.sbml.jsbml.ASTNode2Value, org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> frac(ASTNode2 numerator, ASTNode2 denominator)
      throws SBMLException {
    UnitDefinition ud = numerator.compile(this).getUnits().clone();
    UnitDefinition denom = denominator.compile(this).getUnits().clone();
    setLevelAndVersion(ud);
    setLevelAndVersion(denom);
    ud.divideBy(denom);
    ASTNode2Value<Double> value = new ASTNode2Value<Double>(ud, this);
    value.setValue(numerator.compile(this).toDouble()
      / denominator.compile(this).toDouble());
    return value;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#frac(int, int)
   */
  @Override
  public <T> ASTNode2Value<Double> frac(int numerator, int denominator) {
    ASTNode2Value<Double> value = new ASTNode2Value<Double>(
        new UnitDefinition(level, version), this);
    value.setValue(((double) numerator) / ((double) denominator));
    return value;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#function(org.sbml.jsbml.FunctionDefinition, org.sbml.jsbml.ASTNode2Value[])
   */
  @Override
  public <T> ASTNode2Value<String> function(FunctionDefinition function, List<ASTNode2> args)
      throws SBMLException {
    ASTLambdaFunctionNode lambda = (ASTLambdaFunctionNode) function.getMath().toASTNode2();
    HashMap<String, ASTNode2Value<?>> argValues = new HashMap<String, ASTNode2Value<?>>();
    for (int i = 0; i < args.size(); i++) {
      // TODO: Check getChildAt() method ...
      argValues.put(lambda.getChildAt(i).compile(this).toString(), args
        .get(i).compile(this));

    }
    try {
      namesToUnits = argValues;
      // TODO: Check getChildAt() method ...
      ASTNode2Value value = lambda.getChildAt(lambda.getChildCount() - 1).compile(this);
      namesToUnits.clear();
      return value;
    } catch (SBMLException e) {
      return new ASTNode2Value(this);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#function(java.lang.String, java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> function(String functionDefinitionName,
    List<ASTNode2> args) throws SBMLException {
    // TODO: Implement
    //return new ASTNode2Value(this);
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#greaterEqual(org.sbml.jsbml.ASTNode2Value, org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Boolean> geq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    ASTNode2Value<Boolean> v = dimensionless(Boolean.class);
    ASTNode2Value<?> leftvalue, rightvalue;
    leftvalue = left.compile(this);
    rightvalue = right.compile(this);
    unifyUnits(leftvalue, rightvalue);
    v.setValue(leftvalue.toDouble() >= rightvalue.toDouble());
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#getConstantAvogadro(java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<Double> getConstantAvogadro(String name) {
    // TODO: If there is a different value in a later SBML specification, this must be checked here.
    ASTNode2Value<Double> value = new ASTNode2Value<Double>(Maths.AVOGADRO_L3V1, this);
    UnitDefinition perMole = new UnitDefinition(level, version);
    perMole.setLevel(level);
    perMole.setId("per_mole");
    perMole.addUnit(new Unit(1d, 0, Kind.MOLE, -1d, level, version));
    value.setUnits(perMole);
    return value;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#getConstantE()
   */
  @Override
  public <T> ASTNode2Value<Double> getConstantE() {
    ASTNode2Value<Double> v = dimensionless(Double.class);
    v.setValue(Math.E);
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#getConstantFalse()
   */
  @Override
  public <T> ASTNode2Value<Boolean> getConstantFalse() {
    ASTNode2Value<Boolean> v = dimensionless(Boolean.class);
    v.setValue(false);
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#getConstantPi()
   */
  @Override
  public <T> ASTNode2Value<Double> getConstantPi() {
    ASTNode2Value<Double> v = dimensionless(Double.class);
    v.setValue(Math.PI);
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#getConstantTrue()
   */
  @Override
  public <T> ASTNode2Value<Boolean> getConstantTrue() {
    ASTNode2Value<Boolean> v = dimensionless(Boolean.class);
    v.setValue(true);
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#getNegativeInfinity()
   */
  @Override
  public <T> ASTNode2Value<String> getNegativeInfinity() {
    return compile(Double.NEGATIVE_INFINITY, Unit.Kind.DIMENSIONLESS
      .toString().toLowerCase());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#getPositiveInfinity()
   */
  @Override
  public <T> ASTNode2Value<String> getPositiveInfinity() {
    return compile(Double.POSITIVE_INFINITY, Unit.Kind.DIMENSIONLESS
      .toString().toLowerCase());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#greaterThan(org.sbml.jsbml.ASTNode2Value, org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Boolean> gt(ASTNode2 left, ASTNode2 right) throws SBMLException {
    ASTNode2Value<Boolean> v = dimensionless(Boolean.class);
    ASTNode2Value<?> leftvalue, rightvalue;
    leftvalue = left.compile(this);
    rightvalue = right.compile(this);
    unifyUnits(leftvalue, rightvalue);
    v.setValue(leftvalue.toDouble() > rightvalue.toDouble());
    return v;
  }

  /**
   * Creates an invalid unit definition encapsulated in an ASTNode2Value.
   * 
   * @return
   */
  private <T> ASTNode2Value<?> invalid() {
    UnitDefinition ud = new UnitDefinition(level, version);
    ud.addUnit(new Unit(level, version));
    return new ASTNode2Value(ud, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#lambda(org.sbml.jsbml.ASTNode2Value[])
   */
  @Override
  // TODO: Specify generic type T i.e. ASTNode2Value<?>
  public <T> ASTNode2Value<?> lambda(List<ASTNode2> values) throws SBMLException {
    for (int i = 0; i < values.size() - 1; i++) {
      namesToUnits.put(values.get(i).toString(),
        values.get(i).compile(this));
    }
    return new ASTNode2Value(values.get(values.size() - 1).compile(this)
      .getUnits(), this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#lessEqual(org.sbml.jsbml.ASTNode2Value, org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public ASTNode2Value<Boolean> leq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    ASTNode2Value<Boolean> v = dimensionless(Boolean.class);
    ASTNode2Value<?> leftvalue, rightvalue;
    leftvalue = left.compile(this);
    rightvalue = right.compile(this);
    unifyUnits(leftvalue, rightvalue);
    v.setValue(leftvalue.toDouble() <= rightvalue.toDouble());
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#ln(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> ln(ASTNode2 value) throws SBMLException {
    // TODO: Specify type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Maths.ln(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#log(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> log(ASTNode2 value) throws SBMLException {
    // TODO: Specify type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Maths.log(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#log(org.sbml.jsbml.ASTNode2Value, org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> log(ASTNode2 number, ASTNode2 base) throws SBMLException {
    // TODO: Specify type T i.e. ASTNode2Value<Double>
    ASTNode2Value v = number.compile(this);
    ASTNode2Value b = base.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    checkForDimensionlessOrInvalidUnits(b.getUnits());
    v.setValue(Maths.log(v.toDouble(), b.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#lessThan(org.sbml.jsbml.ASTNode2Value, org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  // TODO: Specify generic type T i.e. ASTNode2Value<?>
  public <T> ASTNode2Value<Boolean> lt(ASTNode2 left, ASTNode2 right) throws SBMLException {
    ASTNode2Value<Boolean> v = dimensionless(Boolean.class);
    ASTNode2Value<?> leftvalue, rightvalue;
    leftvalue = left.compile(this);
    rightvalue = right.compile(this);
    unifyUnits(leftvalue, rightvalue);
    v.setValue(leftvalue.toDouble() < rightvalue.toDouble());
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#minus(org.sbml.jsbml.ASTNode2Value[])
   */
  @Override
  // TODO: Specify generic type T i.e. ASTNode2Value<?>
  public <T> ASTNode2Value<?> minus(List<ASTNode2> values) throws SBMLException {
    ASTNode2Value value = new ASTNode2Value(this);
    if (values.size() == 0) {
      return value;
    }
    int i = 0;
    ASTNode2Value compiledvalues[] = new ASTNode2Value[values.size()];
    for (ASTNode2 node : values) {
      compiledvalues[i++] = node.compile(this);
    }
    value.setValue(Integer.valueOf(0));
    UnitDefinition ud = new UnitDefinition(level, version);
    ud.addUnit(Unit.Kind.INVALID);
    value.setUnits(ud);

    i = 0;

    while (i < compiledvalues.length) {
      value.setValue(Double.valueOf(value.toDouble()
        - compiledvalues[i].toNumber().doubleValue()));
      if (!compiledvalues[i].getUnits().isInvalid()) {
        value.setUnits(compiledvalues[i].getUnits());
        break;
      }
      i++;
    }

    for (int j = i + 1; j < compiledvalues.length; j++) {
      unifyUnits(value, compiledvalues[j]);
      value.setValue(Double.valueOf(value.toDouble()
        - compiledvalues[j].toNumber().doubleValue()));
    }

    return value;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#notEqual(org.sbml.jsbml.ASTNode2Value, org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public ASTNode2Value<Boolean> neq(ASTNode2 left, ASTNode2 right) throws SBMLException {
    ASTNode2Value<Boolean> v = dimensionless(Boolean.class);
    ASTNode2Value<?> leftvalue, rightvalue;
    leftvalue = left.compile(this);
    rightvalue = right.compile(this);
    unifyUnits(leftvalue, rightvalue);
    v.setValue(leftvalue.toDouble() != rightvalue.toDouble());
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#not(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Boolean> not(ASTNode2 value) throws SBMLException {
    ASTNode2Value<Boolean> v = dimensionless(Boolean.class);
    v.setValue(!value.compile(this).toBoolean());
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#or(org.sbml.jsbml.ASTNode2Value[])
   */
  @Override
  public <T> ASTNode2Value<Boolean> or(List<ASTNode2> values) throws SBMLException {
    ASTNode2Value<Boolean> v = dimensionless(Boolean.class);
    v.setValue(false);
    for (ASTNode2 value : values) {
      if (value.compile(this).toBoolean()) {
        v.setValue(true);
        break;
      }
    }
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#piecewise(org.sbml.jsbml.ASTNode2Value[])
   */
  @Override
  public <T> ASTNode2Value<?> piecewise(List<ASTNode2> values) throws SBMLException {
    int i = 0;
    ASTNode2Value<?> compiledvalues[] = new ASTNode2Value<?>[values.size()];
    for (ASTNode2 node : values) {
      compiledvalues[i++] = node.compile(this);
    }
    if (values.size() > 2) {
      ASTNode2Value<?> node = compiledvalues[0];
      for (i = 2; i < values.size(); i += 2) {
        if (!UnitDefinition.areEquivalent(node.getUnits(),
          compiledvalues[i].getUnits())) {
          throw new IllegalArgumentException(
            new UnitException(
              String.format(
                "Units of some return values in a piecewise function do not match. Given %s and %s.",
                UnitDefinition.printUnits(node.getUnits(), true),
                UnitDefinition.printUnits(compiledvalues[i].getUnits(), true))));
        }
      }
    }
    for (i = 1; i < compiledvalues.length - 1; i += 2) {
      if (compiledvalues[i].toBoolean()) {
        return compiledvalues[i - 1];
      }
    }
    return values.get(i - 1).compile(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#plus(org.sbml.jsbml.ASTNode2Value[])
   */
  @Override
  public <T> ASTNode2Value<?> plus(List<ASTNode2> values) throws SBMLException {
    ASTNode2Value value = new ASTNode2Value(this);

    if (values.size() == 0) {
      return value;
    }

    int i = 0;
    ASTNode2Value compiledvalues[] = new ASTNode2Value[values.size()];
    for (ASTNode2 node : values) {
      compiledvalues[i++] = node.compile(this);
    }

    value.setValue(Integer.valueOf(0));
    UnitDefinition ud = new UnitDefinition(level, version);
    ud.addUnit(Unit.Kind.INVALID);
    value.setUnits(ud);

    i = compiledvalues.length - 1;

    while (i >= 0) {
      value.setValue(Double.valueOf(value.toDouble()
        + compiledvalues[i].toNumber().doubleValue()));
      if (!compiledvalues[i].getUnits().isInvalid()) {
        value.setUnits(compiledvalues[i].getUnits());
        break;
      }
      i--;
    }

    for (int j = i - 1; j >= 0; j--) {
      unifyUnits(value, compiledvalues[j]);
      value.setValue(Double.valueOf(value.toDouble()
        + compiledvalues[j].toNumber().doubleValue()));

    }

    return value;
  }

  /**
   * This method tries to unify the units of two ASTNode2Values so that they
   * have the same units and their value thus is also adjusted. If the units
   * of both ASTNode2Values are not compatible, an exception is thrown.
   * 
   * @param left
   * @param right
   */
  private void unifyUnits(ASTNode2Value left, ASTNode2Value right)
      throws SBMLException {
    if (UnitDefinition.areCompatible(left.getUnits(), right.getUnits())) {

      if (!left.getUnits().isInvalid() || !right.getUnits().isInvalid()) {
        left.getUnits().simplify();
        right.getUnits().simplify();
        int mean, scale1, scale2;
        double v1 = left.toNumber().doubleValue(), v2 = right.toNumber().doubleValue();
        for (int i = 0; i < left.getUnits().getUnitCount(); i++) {
          Unit u1 = left.getUnits().getUnit(i);
          Unit u2 = right.getUnits().getUnit(i);
          if (((u1.getMultiplier() != u2.getMultiplier())
              && (u1.getScale() != u2.getScale()) && (u1.getExponent() != u2.getExponent()))
              && (u1.getMultiplier() != 0d) && (u2.getMultiplier() != 0d)) {

            mean = (Math.abs(u1.getScale()) + Math.abs(u2.getScale())) / 2;

            if (u1.getScale() > mean) {
              scale1 = Math.abs(u1.getScale()) - mean;
              scale2 = mean - u2.getScale();

            } else {
              scale2 = Math.abs(u2.getScale()) - mean;
              scale1 = mean - u1.getScale();
            }

            if (u1.getExponent() < 0) {
              scale1 = -scale1;
              scale2 = -scale2;
            }

            if (scale1 > mean) {
              v1 = v1 * Math.pow(10d, -scale1 * u1.getExponent());
              v2 = v2 * Math.pow(10d, -scale2 * u2.getExponent());

            } else {
              v1 = v1 * Math.pow(10d, scale1 * u1.getExponent());
              v2 = v2 * Math.pow(10d, scale2 * u2.getExponent());
            }

            if (u1.getMultiplier() > 1d) {
              v1 = v1 * u1.getMultiplier();
              u1.setMultiplier(1d);
            }

            if (u2.getMultiplier() > 1d) {
              v2 = v2 * u2.getMultiplier();
              u2.setMultiplier(1d);
            }

            u1.setScale(mean);
            u2.setScale(mean);

          }

        }
        left.setValue(v1);
        right.setValue(v2);
      }

    } else {
      throw new UnitException(
        String.format(
          "Cannot combine the units %s and %s in addition, subtraction, comparison or any equivalent operation.",
          UnitDefinition.printUnits(left.getUnits(), true),
          UnitDefinition.printUnits(right.getUnits(), true)));

    }

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#pow(org.sbml.jsbml.ASTNode2Value, org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<?> pow(ASTNode2 base, ASTNode2 exponent)
      throws SBMLException {
    if (exponent instanceof ASTCnNumberNode) {
      checkForDimensionlessOrInvalidUnits(((ASTCnNumberNode)exponent)
        .getUnitsInstance());
    }

    return pow(base.compile(this), exponent.compile(this));
  }

  /**
   * 
   * @param base
   * @param exponent
   * @return
   * @throws SBMLException
   */
  private <T> ASTNode2Value<?> pow(ASTNode2Value base, ASTNode2Value exponent)
      throws SBMLException {
    double exp = Double.NaN, v;
    v = exponent.toDouble();
    exp = v == 0d ? 0d : 1d / v;
    if (exp == 0d) {
      UnitDefinition ud = new UnitDefinition(level, version);
      ud.addUnit(Kind.DIMENSIONLESS);
      ASTNode2Value<Integer> value = new ASTNode2Value<Integer>(ud, this);
      value.setValue(Integer.valueOf(1));
      return value;
    }
    if (!Double.isNaN(exp)) {
      return root(exp, base);
    }
    // TODO:
    return new ASTNode2Value(this);
  }

  /**
   * Throws an {@link IllegalArgumentException} if the given units do not
   * represent a dimensionless or invalid unit.
   * 
   * @param units
   */
  private void checkForDimensionlessOrInvalidUnits(UnitDefinition units) {
    units.simplify();
    String illegal = null;
    if (units.getUnitCount() == 1) {
      Kind kind = units.getUnit(0).getKind();
      if ((kind != Kind.DIMENSIONLESS) && (kind != Kind.ITEM)
          && (kind != Kind.RADIAN) && (kind != Kind.STERADIAN)
          && (kind != Kind.INVALID)) {
        illegal = kind.toString();
      }
    } else {
      illegal = units.toString();
    }
    if (illegal != null) {
      throw new IllegalArgumentException(
        new UnitException(MessageFormat.format(
          "An invalid or dimensionless unit is required but given is {0}.",
          illegal)));
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#root(org.sbml.jsbml.ASTNode, org.sbml.jsbml.ASTNode)
   */
  @Override
  public <T> ASTNode2Value<Double> root(ASTNode2 rootExponent, ASTNode2 radiant)
      throws SBMLException {
    if (rootExponent instanceof ASTCnNumberNode) {
      if (!(rootExponent instanceof ASTCnIntegerNode
          || rootExponent instanceof ASTCnRationalNode)) {
        checkForDimensionlessOrInvalidUnits(((ASTCnNumberNode)rootExponent)
          .getUnitsInstance());
      }
      return root(rootExponent.compile(this).toDouble(), radiant);
    }
    return new ASTNode2Value<Double>(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#root(double, org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> root(double rootExponent, ASTNode2 radiant)
      throws SBMLException {
    return root(rootExponent, radiant.compile(this));
  }

  /**
   * 
   * @param rootExponent
   * @param radiant
   * @return
   * @throws SBMLException
   */
  private <T> ASTNode2Value<Double> root(double rootExponent, ASTNode2Value radiant)
      throws SBMLException {
    UnitDefinition ud = radiant.getUnits().clone();
    for (Unit u : ud.getListOfUnits()) {
      if ((((u.getExponent() / rootExponent) % 1d) != 0d) && !u.isDimensionless() && !u.isInvalid()) {
        new UnitException(MessageFormat.format(
          "Cannot perform power or root operation due to incompatibility with a unit exponent. Given are {0,number} as the exponent of the unit and {1,number} as the root exponent for the current computation.",
          u.getExponent(), rootExponent));
      }
      if (!(u.isDimensionless() || u.isInvalid())) {
        u.setExponent(u.getExponent() / rootExponent);
      }
    }
    ASTNode2Value<Double> value = new ASTNode2Value<Double>(ud, this);
    value.setValue(Double.valueOf(Math.pow(radiant.toDouble(), 1d / rootExponent)));
    return value;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#sec(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> sec(ASTNode2 value) throws SBMLException {
    // TODO: Type T needs to be specified i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Maths.sec(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#sech(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> sech(ASTNode2 value) throws SBMLException {
    // TODO: Type T needs to be specified i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Maths.sech(v.toDouble()));
    return v;
  }

  /**
   * Ensures that level and version combination of a unit are the same then
   * these that are defined here.
   * 
   * @param unit
   */
  private void setLevelAndVersion(UnitDefinition unit) {
    if ((unit.getLevel() != level) || (unit.getVersion() != version)) {
      unit.setLevel(level);
      unit.setVersion(version);
      unit.getListOfUnits().setLevel(level);
      unit.getListOfUnits().setVersion(version);
      for (Unit u : unit.getListOfUnits()) {
        u.setLevel(level);
        u.setVersion(version);
      }
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#sin(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public ASTNode2Value<?> sin(ASTNode2 value) throws SBMLException {
    // TODO: Type T needs to be specified i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Math.sin(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#sinh(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> sinh(ASTNode2 value) throws SBMLException {
    // TODO: Type T needs to be specified i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Math.sinh(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#sqrt(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> sqrt(ASTNode2 value) throws SBMLException {
    return root(2d, value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#symbolTime(java.lang.String)
   */
  @Override
  public <T> ASTNode2Value<Double> symbolTime(String time) {
    UnitDefinition ud;
    if (model != null) {
      ud = model.getTimeUnitsInstance();
      if (ud == null) {
        ud = model.getUnitDefinition(time);
      }
    } else {
      ud = UnitDefinition.time(level, version);
    }
    if (ud == null) {
      ud = invalid().getUnits();
    }
    ASTNode2Value<Double> value = new ASTNode2Value<Double>(ud, this);
    value.setValue(Double.valueOf(1d));
    return value;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#tan(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> tan(ASTNode2 value) throws SBMLException {
    // TODO: Type T needs to be specified i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Math.tan(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#tanh(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public <T> ASTNode2Value<Double> tanh(ASTNode2 value) throws SBMLException {
    // TODO: Type T needs to be specified i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    checkForDimensionlessOrInvalidUnits(v.getUnits());
    v.setValue(Math.tanh(v.toDouble()));
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#times(org.sbml.jsbml.ASTNode2Value[])
   */
  @Override
  public <T> ASTNode2Value<Double> times(List<ASTNode2> values) throws SBMLException {
    UnitDefinition ud = new UnitDefinition(level, version);
    UnitDefinition v;
    double d = 1d;
    for (ASTNode2 value : values) {
      ASTNode2Value<?> av = value.compile(this);
      v = av.getUnits().clone();
      setLevelAndVersion(v);
      ud.multiplyWith(v);
      d *= av.toDouble();
    }
    ASTNode2Value<Double> value = new ASTNode2Value(ud, this);
    value.setValue(Double.valueOf(d));

    return value;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#uiMinus(org.sbml.jsbml.ASTNode2Value)
   */
  @Override
  public ASTNode2Value<?> uMinus(ASTNode2 value) throws SBMLException {
    // TODO: Type T needs to be specified i.e. ASTNode2Value<Double>
    ASTNode2Value v = value.compile(this);
    v.setValue(-v.toDouble());
    return v;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#unknownValue()
   */
  @Override
  public <T> ASTNode2Value<?> unknownValue() {
    return invalid();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ASTNodeCompiler#xor(org.sbml.jsbml.ASTNode2Value[])
   */
  @Override
  public <T> ASTNode2Value<Boolean> xor(List<ASTNode2> values) throws SBMLException {
    ASTNode2Value<Boolean> value = dimensionless(Boolean.class);
    boolean v = false;
    for (int i = 0; i < values.size(); i++) {
      if (values.get(i).compile(this).toBoolean()) {
        if (v) {
          return getConstantFalse();
        } else {
          v = true;
        }
      }
    }
    value.setValue(Boolean.valueOf(v));
    return value;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#selector(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> selector(List<ASTNode2> nodes) throws SBMLException {
    return function("selector", nodes);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.compilers.ASTNodeCompiler#vector(java.util.List)
   */
  @Override
  public <T> ASTNode2Value<String> vector(List<ASTNode2> nodes) throws SBMLException {
    return function("vector", nodes);
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
