/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2011 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.util.compilers;

import java.util.HashMap;
import java.util.List;

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
import org.sbml.jsbml.util.Maths;

/**
 * Derives the units from mathematical operations.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-05-20
 * @since 0.8
 * @version $Rev$
 */
public class UnitsCompiler implements ASTNodeCompiler {

	/**
	 * SBML level field
	 */
	private int level;
	/**
	 * SBML version field
	 */
	private int version;
	/**
	 * The model associated to this compiler.
	 */
	private Model model;
	/**
	 * Necessary for function definitions to remember the units of the argument
	 * list.
	 */
	private HashMap<String, ASTNodeValue> namesToUnits;

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
		this.namesToUnits = new HashMap<String, ASTNodeValue>();
	}

	/**
	 * 
	 * @param model
	 */
	public UnitsCompiler(Model model) {
		this(model.getLevel(), model.getVersion());
		this.model = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#abs(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue abs(ASTNode value) throws SBMLException {
		ASTNodeValue v = new ASTNodeValue(this);
		if (value.isDifference() || value.isSum() || value.isUMinus()
				|| value.isNumber()) {
			v.setValue(Double.valueOf(Math.abs(value.compile(this).toDouble())));
		}
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#and(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue and(List<ASTNode> values) throws SBMLException {
		ASTNodeValue value = dimensionless();
		boolean val = true;
		for (ASTNode v : values) {
			val &= v.compile(this).toBoolean();
		}
		value.setValue(val);
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccos(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccos(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Math.acos(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccosh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccosh(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Maths.arccosh(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccot(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccot(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Maths.arccot(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccoth(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccoth(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Maths.arccoth(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccsc(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccsc(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Maths.arccsc(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccsch(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccsch(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Maths.arccsch(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsec(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsec(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Maths.arcsec(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsech(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsech(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Maths.arcsech(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsin(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsin(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Math.asin(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsinh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsinh(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Maths.arcsinh(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arctan(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arctan(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Math.atan(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arctanh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arctanh(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Maths.arctanh(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#ceiling(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue ceiling(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		v.setValue(Math.ceil(v.toDouble()));

		return v;
	}

	/**
	 * Compile boolean values
	 * 
	 * @param b
	 * @return
	 */
	public ASTNodeValue compile(boolean b) {
		UnitDefinition ud = new UnitDefinition(level, version);
		ud.addUnit(Kind.DIMENSIONLESS);
		ASTNodeValue value = new ASTNodeValue(b, this);
		value.setUnits(ud);
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(org.sbml.jsbml.Compartment)
	 */
	public ASTNodeValue compile(Compartment c) {
		return compile((CallableSBase) c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(double, int,
	 * java.lang.String)
	 */
	public ASTNodeValue compile(double mantissa, int exponent, String units) {
		return compile(mantissa * Math.pow(10, exponent), units);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(double, java.lang.String)
	 */
	public ASTNodeValue compile(double real, String units) {
		ASTNodeValue v = new ASTNodeValue(real, this);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(int, java.lang.String)
	 */
	public ASTNodeValue compile(int integer, String units) {
		return compile((double) integer, units);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.util.compilers.ASTNodeCompiler#compile(org.sbml.jsbml.
	 * CallableSBase)
	 */
	public ASTNodeValue compile(CallableSBase variable) {
		ASTNodeValue value = new ASTNodeValue(variable, this);
		if (variable instanceof Quantity) {
			Quantity q = (Quantity) variable;
			if (q.isSetValue()) {
				value.setValue(Double.valueOf(q.getValue()));
			}
		}
		value.setUnits(variable.getDerivedUnitDefinition());
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(java.lang.String)
	 */
	public ASTNodeValue compile(String name) {
		if (namesToUnits.containsKey(name)) {
			return namesToUnits.get(name);
		}
		return new ASTNodeValue(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cos(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue cos(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Math.cos(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cosh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue cosh(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Math.cosh(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cot(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue cot(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Maths.cot(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#coth(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue coth(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Maths.coth(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#csc(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue csc(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Maths.csc(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#csch(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue csch(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Maths.csch(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#delay(java.lang.String,
	 * org.sbml.jsbml.ASTNodeValue, double, java.lang.String)
	 */
	public ASTNodeValue delay(String delayName, ASTNode x, ASTNode delay,
			String units) throws SBMLException {
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

		return new ASTNodeValue(value, this);
	}

	/**
	 * Creates a dimensionless unit definition object encapsulated in an
	 * ASTNodeValue.
	 * 
	 * @return
	 */
	private ASTNodeValue dimensionless() {
		UnitDefinition ud = new UnitDefinition(level, version);
		ud.addUnit(Unit.Kind.DIMENSIONLESS);
		return new ASTNodeValue(ud, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#equal(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue eq(ASTNode left, ASTNode right) throws SBMLException {
		ASTNodeValue v = dimensionless(), leftvalue, rightvalue;
		leftvalue = left.compile(this);
		rightvalue = right.compile(this);
		unifyUnits(leftvalue, rightvalue);

		v.setValue(leftvalue.toDouble() == rightvalue.toDouble());

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#exp(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue exp(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());

		return pow(getConstantE(), v);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#factorial(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue factorial(ASTNode value) throws SBMLException {

		ASTNodeValue v = new ASTNodeValue(Maths.factorial((int) Math
				.round(value.compile(this).toDouble())), this);
		if (value.isSetUnits()) {
			v.setUnits(value.getUnitsInstance());
		} else {
			v.setLevel(level);
			v.setVersion(version);
		}
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#floor(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue floor(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		v.setValue(Math.floor(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#frac(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue frac(ASTNode numerator, ASTNode denominator)
			throws SBMLException {
		UnitDefinition ud = numerator.compile(this).getUnits().clone();
		UnitDefinition denom = denominator.compile(this).getUnits().clone();
		setLevelAndVersion(ud);
		setLevelAndVersion(denom);
		ud.divideBy(denom);
		ASTNodeValue value = new ASTNodeValue(ud, this);
		value.setValue(numerator.compile(this).toDouble()
				/ denominator.compile(this).toDouble());
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#frac(int, int)
	 */
	public ASTNodeValue frac(int numerator, int denominator) {
		ASTNodeValue value = new ASTNodeValue(
				new UnitDefinition(level, version), this);
		value.setValue(((double) numerator) / ((double) denominator));
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#function(org.sbml.jsbml.FunctionDefinition
	 * , org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue function(FunctionDefinition function, List<ASTNode> args)
			throws SBMLException {
		ASTNode lambda = function.getMath();
		HashMap<String, ASTNodeValue> argValues = new HashMap<String, ASTNodeValue>();
		for (int i = 0; i < args.size(); i++) {
			argValues.put(lambda.getChild(i).compile(this).toString(), args
					.get(i).compile(this));

		}
		try {
			this.namesToUnits = argValues;
			ASTNodeValue value = lambda.getRightChild().compile(this);
			this.namesToUnits.clear();
			return value;
		} catch (SBMLException e) {
			return new ASTNodeValue(this);
		}
	}

	public ASTNodeValue function(String functionDefinitionName,
			List<ASTNode> args) throws SBMLException {
		// TODO : Not sure what to do
		return new ASTNodeValue(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#greaterEqual(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue geq(ASTNode left, ASTNode right) throws SBMLException {
		ASTNodeValue v = dimensionless(), leftvalue, rightvalue;
		leftvalue = left.compile(this);
		rightvalue = right.compile(this);
		unifyUnits(leftvalue, rightvalue);

		v.setValue(leftvalue.toDouble() >= rightvalue.toDouble());

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantAvogadro(java.lang.String)
	 */
	public ASTNodeValue getConstantAvogadro(String name) {
		ASTNodeValue value = new ASTNodeValue(Maths.AVOGADRO, this);
		UnitDefinition perMole = new UnitDefinition(level, version);
		perMole.setLevel(level);
		perMole.setId("per_mole");
		perMole.addUnit(new Unit(Kind.MOLE, -1, level, version));
		value.setUnits(perMole);
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantE()
	 */
	public ASTNodeValue getConstantE() {
		ASTNodeValue v = dimensionless();
		v.setValue(Math.E);
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantFalse()
	 */
	public ASTNodeValue getConstantFalse() {
		ASTNodeValue v = dimensionless();
		v.setValue(false);
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantPi()
	 */
	public ASTNodeValue getConstantPi() {
		ASTNodeValue v = dimensionless();
		v.setValue(Math.PI);
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantTrue()
	 */
	public ASTNodeValue getConstantTrue() {
		ASTNodeValue v = dimensionless();
		v.setValue(true);
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getNegativeInfinity()
	 */
	public ASTNodeValue getNegativeInfinity() {
		return compile(Double.NEGATIVE_INFINITY, Unit.Kind.DIMENSIONLESS
				.toString().toLowerCase());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getPositiveInfinity()
	 */
	public ASTNodeValue getPositiveInfinity() {
		return compile(Double.POSITIVE_INFINITY, Unit.Kind.DIMENSIONLESS
				.toString().toLowerCase());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#greaterThan(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue gt(ASTNode left, ASTNode right) throws SBMLException {
		ASTNodeValue v = dimensionless(), leftvalue, rightvalue;
		leftvalue = left.compile(this);
		rightvalue = right.compile(this);
		unifyUnits(leftvalue, rightvalue);

		v.setValue(leftvalue.toDouble() > rightvalue.toDouble());

		return v;
	}

	/**
	 * Creates an invalid unit definition encapsulated in an ASTNodeValue.
	 * 
	 * @return
	 */
	private ASTNodeValue invalid() {
		UnitDefinition ud = new UnitDefinition(level, version);
		ud.addUnit(new Unit(level, version));
		return new ASTNodeValue(ud, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#lambda(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue lambda(List<ASTNode> values) throws SBMLException {
		for (int i = 0; i < values.size() - 1; i++) {
			namesToUnits.put(values.get(i).toString(),
					values.get(i).compile(this));
		}
		return new ASTNodeValue(values.get(values.size() - 1).compile(this)
				.getUnits(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#lessEqual(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue leq(ASTNode left, ASTNode right) throws SBMLException {
		ASTNodeValue v = dimensionless(), leftvalue, rightvalue;
		leftvalue = left.compile(this);
		rightvalue = right.compile(this);
		unifyUnits(leftvalue, rightvalue);

		v.setValue(leftvalue.toDouble() <= rightvalue.toDouble());

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#ln(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue ln(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Maths.ln(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#log(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue log(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Maths.log(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#log(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue log(ASTNode number, ASTNode base) throws SBMLException {
		ASTNodeValue v = number.compile(this);
		ASTNodeValue b = base.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		checkForDimensionlessOrInvalidUnits(b.getUnits());
		v.setValue(Maths.log(v.toDouble(), b.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#lessThan(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue lt(ASTNode left, ASTNode right) throws SBMLException {
		ASTNodeValue v = dimensionless(), leftvalue, rightvalue;
		leftvalue = left.compile(this);
		rightvalue = right.compile(this);
		unifyUnits(leftvalue, rightvalue);

		v.setValue(leftvalue.toDouble() < rightvalue.toDouble());

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#minus(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue minus(List<ASTNode> values) throws SBMLException {
		ASTNodeValue value = new ASTNodeValue(this);
		if (values.size() == 0) {
			return value;
		}
		int i = 0;
		ASTNodeValue compiledvalues[] = new ASTNodeValue[values.size()];
		for (ASTNode node : values) {
			compiledvalues[i++] = node.compile(this);
		}
		value.setValue(Integer.valueOf(0));
		UnitDefinition ud = new UnitDefinition(this.level, this.version);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#notEqual(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue neq(ASTNode left, ASTNode right) throws SBMLException {
		ASTNodeValue v = dimensionless(), leftvalue, rightvalue;
		leftvalue = left.compile(this);
		rightvalue = right.compile(this);
		unifyUnits(leftvalue, rightvalue);

		v.setValue(leftvalue.toDouble() != rightvalue.toDouble());

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#not(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue not(ASTNode value) throws SBMLException {
		ASTNodeValue v = dimensionless();
		v.setValue(!value.compile(this).toBoolean());
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#or(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue or(List<ASTNode> values) throws SBMLException {
		ASTNodeValue v = dimensionless();
		v.setValue(false);
		for (ASTNode value : values) {
			if (value.compile(this).toBoolean()) {
				v.setValue(true);
				break;
			}
		}
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#piecewise(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue piecewise(List<ASTNode> values) throws SBMLException {
		int i = 0;

		ASTNodeValue compiledvalues[] = new ASTNodeValue[values.size()];
		for (ASTNode node : values) {
			compiledvalues[i++] = node.compile(this);
		}
		if (values.size() > 2) {
			ASTNodeValue node = compiledvalues[0];

			for (i = 2; i < values.size(); i += 2) {
				if (!UnitDefinition.areEquivalent(node.getUnits(),
						compiledvalues[i].getUnits())) {
					throw new IllegalArgumentException(
							new UnitException(
									String.format(
											"Units of some return values in a piecewise function do not match. Given %s and %s.",
											UnitDefinition.printUnits(node
													.getUnits()),
											UnitDefinition
													.printUnits(compiledvalues[i]
															.getUnits()))));

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#plus(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue plus(List<ASTNode> values) throws SBMLException {
		ASTNodeValue value = new ASTNodeValue(this);

		if (values.size() == 0) {
			return value;
		}

		int i = 0;
		ASTNodeValue compiledvalues[] = new ASTNodeValue[values.size()];
		for (ASTNode node : values) {
			compiledvalues[i++] = node.compile(this);
		}

		value.setValue(Integer.valueOf(0));
		UnitDefinition ud = new UnitDefinition(this.level, this.version);
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
	 * This method tries to unify the units of two ASTNodeValues so that they
	 * have the same units and their value thus is also adjusted. If the units
	 * of both ASTNodeValues are not compatible, an exception is thrown.
	 * 
	 * @param left
	 * @param right
	 * @return
	 */
	private void unifyUnits(ASTNodeValue left, ASTNodeValue right)
			throws SBMLException {
		if (UnitDefinition.areCompatible(left.getUnits(), right.getUnits())) {

			if (!left.getUnits().isInvalid() || !right.getUnits().isInvalid()) {
				left.getUnits().simplify();
				right.getUnits().simplify();
				int mean, scale1, scale2;
				double v1 = left.toNumber().doubleValue(), v2 = right
						.toNumber().doubleValue();
				for (int i = 0; i < left.getUnits().getNumUnits(); i++) {
					Unit u1 = left.getUnits().getUnit(i);
					Unit u2 = right.getUnits().getUnit(i);
					if ((u1.getMultiplier() != 0d)
							&& (u2.getMultiplier() != 0d)) {

						mean = (Math.abs(u1.getScale()) + Math.abs(u2
								.getScale())) / 2;

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
							v1 = v1
									* Math.pow(10.0, -scale1 * u1.getExponent());
							v2 = v2
									* Math.pow(10.0, -scale2 * u2.getExponent());

						} else {
							v1 = v1 * Math.pow(10.0, scale1 * u1.getExponent());
							v2 = v2 * Math.pow(10.0, scale2 * u2.getExponent());
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#pow(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue pow(ASTNode base, ASTNode exponent)
			throws SBMLException {
		if (exponent.isSetUnits()) {
			checkForDimensionlessOrInvalidUnits(exponent.getUnitsInstance());
		}
		if (exponent.isNumber()) {
			if (!(exponent.isInteger() || exponent.isRational())) {
				checkForDimensionlessOrInvalidUnits(base.getUnitsInstance());
			}
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
	private ASTNodeValue pow(ASTNodeValue base, ASTNodeValue exponent)
			throws SBMLException {
		double exp = Double.NaN, v;
		v = exponent.toDouble();
		exp = v == 0 ? 0 : 1 / v;
		if (exp == 0) {
			UnitDefinition ud = new UnitDefinition(level, version);
			ud.addUnit(Kind.DIMENSIONLESS);
			ASTNodeValue value = new ASTNodeValue(ud, this);
			value.setValue(Integer.valueOf(1));
			return value;
		}
		if (!Double.isNaN(exp)) {
			return root(exp, base);
		}

		return new ASTNodeValue(this);
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

		if (units.getNumUnits() == 1) {
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
					new UnitException(
							String.format(
									"An invalid or dimensionless unit is required but given is %s.",
									illegal)));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.util.compilers.ASTNodeCompiler#root(org.sbml.jsbml.ASTNode
	 * , org.sbml.jsbml.ASTNode)
	 */
	public ASTNodeValue root(ASTNode rootExponent, ASTNode radiant)
			throws SBMLException {
		if (rootExponent.isSetUnits()) {
			checkForDimensionlessOrInvalidUnits(rootExponent.getUnitsInstance());
		}
		if (rootExponent.isNumber()) {

			if (!(rootExponent.isInteger() || rootExponent.isRational())) {
				checkForDimensionlessOrInvalidUnits(rootExponent
						.getUnitsInstance());
			}

			return root(rootExponent.compile(this).toDouble(), radiant);
		}

		return new ASTNodeValue(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#root(double,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue root(double rootExponent, ASTNode radiant)
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
	private ASTNodeValue root(double rootExponent, ASTNodeValue radiant)
			throws SBMLException {
		UnitDefinition ud = radiant.getUnits().clone();
		for (Unit u : ud.getListOfUnits()) {
			if (((u.getExponent() / rootExponent) % 1.0) != 0.0) {
				throw new IllegalArgumentException(
						new UnitException(
								String.format(
										"Can not perform power or root operation due to incompatibility with a unit exponent. Given %s and %s.",
										u.getExponent(), rootExponent)));
			}
			
			if (!(u.isDimensionless() || u.isInvalid())) {
				u.setExponent(u.getExponent() / rootExponent);
			}

		}
		ASTNodeValue value = new ASTNodeValue(ud, this);
		value.setValue(Double.valueOf(Math.pow(radiant.toDouble(),
				1 / rootExponent)));
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sec(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sec(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Maths.sec(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sech(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sech(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sin(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sin(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Math.sin(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sinh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sinh(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Math.sinh(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sqrt(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sqrt(ASTNode value) throws SBMLException {
		return root(2d, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#symbolTime(java.lang.String)
	 */
	public ASTNodeValue symbolTime(String time) {
		UnitDefinition ud = UnitDefinition.time(level, version);
		if ((ud == null) && (model != null)) {
			ud = model.getTimeUnitsInstance();
			if (ud == null) {
				ud = model.getUnitDefinition(time);
			}
		}
		if (ud == null) {
			ud = invalid().getUnits();
		}
		ASTNodeValue value = new ASTNodeValue(ud, this);
		value.setValue(Double.valueOf(1d));
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#tan(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue tan(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Math.tan(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#tanh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue tanh(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		checkForDimensionlessOrInvalidUnits(v.getUnits());
		v.setValue(Math.tanh(v.toDouble()));

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#times(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue times(List<ASTNode> values) throws SBMLException {
		UnitDefinition ud = new UnitDefinition(level, version);
		UnitDefinition v;
		double d = 1d;

		for (ASTNode value : values) {
			ASTNodeValue av = value.compile(this);
			v = av.getUnits().clone();
			setLevelAndVersion(v);
			ud.multiplyWith(v);
			d *= av.toDouble();
		}
		ASTNodeValue value = new ASTNodeValue(ud, this);
		value.setValue(Double.valueOf(d));

		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#uiMinus(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue uMinus(ASTNode value) throws SBMLException {
		ASTNodeValue v = value.compile(this);
		v.setValue(-v.toDouble());

		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#unknownValue()
	 */
	public ASTNodeValue unknownValue() {
		return invalid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#xor(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue xor(List<ASTNode> values) throws SBMLException {
		ASTNodeValue value = dimensionless();
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

}
