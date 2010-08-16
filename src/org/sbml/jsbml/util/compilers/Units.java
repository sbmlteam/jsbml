/*
 * $Id:  UnitCompiler.java 14:40:27 draeger $
 * $URL: UnitCompiler.java $
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */
package org.sbml.jsbml.util.compilers;

import java.util.HashMap;
import java.util.List;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBaseWithDerivedUnit;
import org.sbml.jsbml.Quantity;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.util.Maths;

/**
 * Derives the units from mathematical operations.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-05-20
 */
public class Units implements ASTNodeCompiler {

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
	private HashMap<String, UnitDefinition> namesToUnits;

	/**
	 * 
	 */
	public Units() {
		this(-1, -1);
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public Units(int level, int version) {
		this.level = level;
		this.version = version;
		this.namesToUnits = new HashMap<String, UnitDefinition>();
	}

	/**
	 * 
	 * @param model
	 */
	public Units(Model model) {
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
			v
					.setValue(Double.valueOf(Math.abs(value.compile(this)
							.toDouble())));
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
		return new ASTNodeValue(Math.acos(value.compile(this).toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccosh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccosh(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Maths.arccosh(value.compile(this).toDouble()),
				this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccot(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccot(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Maths.arccot(value.compile(this).toDouble()),
				this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccoth(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccoth(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Maths.arccoth(value.compile(this).toDouble()),
				this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccsc(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccsc(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Maths.arccsc(value.compile(this).toDouble()),
				this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccsch(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccsch(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Maths.arccsch(value.compile(this).toDouble()),
				this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsec(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsec(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Maths.arcsec(value.compile(this).toDouble()),
				this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsech(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsech(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Maths.arcsech(value.compile(this).toDouble()),
				this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsin(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsin(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Math.asin(value.compile(this).toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsinh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsinh(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Maths.arcsinh(value.compile(this).toDouble()),
				this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arctan(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arctan(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Math.atan(value.compile(this).toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arctanh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arctanh(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Maths.arctanh(value.compile(this).toDouble()),
				this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#ceiling(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue ceiling(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Double.valueOf(Math.ceil(value.compile(this)
				.toDouble())), this);
	}

	private ASTNodeValue checkIdentical(ASTNodeValue... values) {
		ASTNodeValue value = new ASTNodeValue(this);
		if (values[0].isNumber()) {
			int i = values.length - 1;
			while (i >= 0 && values[i].isNumber()) {
				i--;
			}
			UnitDefinition ud = values[Math.max(0, i)].getUnits();
			values[0].setUnits(ud);
			/*
			 * Necessary to remember in higher recursion steps that this was
			 * just a number. The actual value does not matter.
			 */
			// TODO: save correct value.
			value.setValue(Integer.valueOf(0));
		}
		UnitDefinition ud = values[0].getUnits();
		if ((ud != null) && (ud.getNumUnits() > 0)) {
			for (int i = 1; i < values.length; i++) {
				if (values[i].isNumber()) {
					values[i].setUnits(values[i - 1].getUnits());
				} else if (!UnitDefinition.areEquivalent(ud, values[i]
						.getUnits())) {
					ud.clear();
					break;
				}
			}
			if (ud.getNumUnits() > 0) {
				value.setUnits(ud);
			}
		}
		return value;

	}

	/**
	 * Checks whether all units in the given array are identical.
	 * 
	 * @param values
	 * @return An empty unit definition if not identical, or the common unit
	 *         definition otherwise.
	 * @throws SBMLException
	 */
	private ASTNodeValue checkIdentical(List<ASTNode> values)
			throws SBMLException {
		ASTNodeValue value = new ASTNodeValue(this);
		if (values.size() == 0) {
			return value;
		}
		int i = 0;
		ASTNodeValue v[] = new ASTNodeValue[values.size()];
		for (ASTNode node : values) {
			v[i++] = node.compile(this);
		}
		return checkIdentical(v);
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
		return compile((NamedSBaseWithDerivedUnit) c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(double, int,
	 * java.lang.String)
	 */
	public ASTNodeValue compile(double mantissa, int exponent, String units) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(double, java.lang.String)
	 */
	public ASTNodeValue compile(double real, String units) {
		// TODO!
		return new ASTNodeValue(real, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(int, java.lang.String)
	 */
	public ASTNodeValue compile(int integer, String units) {
		// TODO!
		return new ASTNodeValue(integer, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.sbml.jsbml.ASTNodeCompiler#compile(org.sbml.jsbml.
	 * NamedSBaseWithDerivedUnit)
	 */
	public ASTNodeValue compile(NamedSBaseWithDerivedUnit variable) {
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
		UnitDefinition ud = namesToUnits.get(name);
		return ud != null ? new ASTNodeValue(ud, this) : new ASTNodeValue(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cos(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue cos(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Math.cos(value.compile(this).toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cosh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue cosh(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Math.cosh(value.compile(this).toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cot(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue cot(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Maths.cot(value.compile(this).toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#coth(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue coth(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Maths.coth(value.compile(this).toDouble()),
				this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#csc(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue csc(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Maths.csc(value.compile(this).toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#csch(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue csch(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Maths.csch(value.compile(this).toDouble()),
				this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#delay(java.lang.String,
	 * org.sbml.jsbml.ASTNodeValue, double, java.lang.String)
	 */
	public ASTNodeValue delay(String delayName, ASTNode x, ASTNode delay,
			String units) {
		// TODO!
		return symbolTime("time");
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
		ASTNodeValue v = dimensionless();
		v.setValue(left.compile(this).toDouble() == right.compile(this)
				.toDouble());
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#exp(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue exp(ASTNode value) throws SBMLException {
		return pow(getConstantE(), value.compile(this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#factorial(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue factorial(ASTNode value) throws SBMLException {
		if (value.isNumber()) {
			double d = value.compile(this).toDouble();
			ASTNodeValue v = root(1 / d, value);
			v.setValue(Maths.factorial(d));
			return v;
		}
		/*
		 * TODO: absolutely unclear how to derive the unit here.
		 */
		return new ASTNodeValue(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#floor(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue floor(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Math.floor(value.compile(this).toDouble()),
				this);
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
		for (ASTNode arg : args) {
			this.namesToUnits.put(arg.toString(), arg.compile(this).getUnits());
		}
		try {
			return new ASTNodeValue(function.getMath().deriveUnit(), this);
		} catch (SBMLException e) {
			return new ASTNodeValue(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#greaterEqual(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue geq(ASTNode left, ASTNode right) throws SBMLException {
		ASTNodeValue value = dimensionless();
		value.setValue(left.compile(this).toDouble() >= right.compile(this)
				.toDouble());
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantAvogadro(java.lang.String)
	 */
	public ASTNodeValue getConstantAvogadro(String name) {
		ASTNodeValue value = new ASTNodeValue(Maths.AVOGADRO, this);
		UnitDefinition perMole = new UnitDefinition();
		perMole.setLevel(level);
		perMole.setId("per_mole");
		// TODO: deal vith correct version!
		perMole.addUnit(new Unit(Kind.MOLE, -1, level, -1));
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
		ASTNodeValue value = dimensionless();
		value.setValue(left.compile(this).toDouble() > right.compile(this)
				.toDouble());
		return value;
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
			namesToUnits.put(values.get(i).toString(), values.get(i).compile(
					this).getUnits());
		}
		return new ASTNodeValue(values.get(values.size() - 1).compile(this)
				.getUnits(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#lambdaFunction(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue lambdaFunction(List<ASTNode> values) throws SBMLException {
		throw new SBMLException("Cannot calculate units of a FunctionDefinition");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#lessEqual(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue leq(ASTNode left, ASTNode right) throws SBMLException {
		ASTNodeValue value = dimensionless();
		value.setValue(left.compile(this).toDouble() <= right.compile(this)
				.toDouble());
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#ln(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue ln(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Maths.ln(value.compile(this).toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#log(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue log(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Maths.log(value.compile(this).toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#log(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue log(ASTNode number, ASTNode base) throws SBMLException {
		return new ASTNodeValue(Maths.log(number.compile(this).toDouble(), base
				.compile(this).toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#lessThan(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue lt(ASTNode left, ASTNode right) throws SBMLException {
		ASTNodeValue value = dimensionless();
		value.setValue(left.compile(this).toDouble() < right.compile(this)
				.toDouble());
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#minus(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue minus(List<ASTNode> values) throws SBMLException {
		ASTNodeValue value = checkIdentical(values);
		if (values.size() > 0) {
			value.setValue(values.get(0).compile(this).toNumber());
			for (int i = 1; i < values.size(); i++) {
				value
						.setValue(Double.valueOf(value.toDouble()
								- values.get(i).compile(this).toNumber()
										.doubleValue()));
			}
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
		ASTNodeValue v = dimensionless();
		v.setValue(left.compile(this).toDouble() != right.compile(this)
				.toDouble());
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
		ASTNodeValue[] varray = new ASTNodeValue[values.size() / 2
				+ ((values.size() % 2 == 0) ? 0 : 1)];
		int i, j;
		for (i = 0, j = 0; i < values.size(); i += 2, j++) {
			varray[j] = values.get(i).compile(this);
		}
		ASTNodeValue value = checkIdentical(varray);
		for (i = 1; i < values.size() - 1; i += 2) {
			if (values.get(i).compile(this).toBoolean()) {
				return new ASTNodeValue(values.get(i - 1).compile(this)
						.toDouble(), this);
			}
		}
		value.setValue(Double.valueOf(values.get(i - 1).compile(this)
				.toDouble()));

		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#plus(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue plus(List<ASTNode> values) throws SBMLException {
		ASTNodeValue value = checkIdentical(values);
		for (ASTNode v : values) {
			value.setValue(Double.valueOf(value.toDouble()
					+ v.compile(this).toNumber().doubleValue()));
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#pow(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue pow(ASTNode base, ASTNode exponent)
			throws SBMLException {
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
		if (exponent.isNumber()) {
			v = exponent.toDouble();
			exp = v == 0 ? 0 : 1 / v;
		} else {
			if (exponent.getUnits().getNumUnits() == 1) {
				System.out.println(exponent.getUnits().getUnit(0).getKind());
				Kind kind = exponent.getUnits().getUnit(0).getKind();
				// only if the kind is a dimensionless quantity we can proceed.
				if ((kind == Kind.DIMENSIONLESS) || (kind == Kind.ITEM)
						|| (kind == Kind.RADIAN) || (kind == Kind.STERADIAN)) {
					v = exponent.toDouble();
					exp = v == 0 ? 0 : 1 / v;
				}
			}
		}
		if (exp == 0) {
			UnitDefinition ud = new UnitDefinition(level, version);
			ud.addUnit(Kind.DIMENSIONLESS);
			return new ASTNodeValue(ud, this);
		}
		if (!Double.isNaN(exp)) {
			return root(exp, base);
		}
		/*
		 * TODO: What to to if the exponent is not a number?
		 */
		return new ASTNodeValue(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#root(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue root(ASTNode rootExponent, ASTNode radiant)
			throws SBMLException {
		if (rootExponent.isNumber()) {
			return root(rootExponent.compile(this).toDouble(), radiant);
		}
		/*
		 * TODO: Next problem! What to do with exponents that are no numbers?
		 */
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
			u.setExponent(u.getExponent() / rootExponent);
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
		return new ASTNodeValue(Maths.sec(value.compile(this).toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sech(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sech(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Maths.sech(value.compile(this).toDouble()),
				this);
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
		return new ASTNodeValue(Math.sin(value.compile(this).toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sinh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sinh(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Math.sinh(value.compile(this).toDouble()), this);
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
		return new ASTNodeValue(Math.tan(value.compile(this).toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#tanh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue tanh(ASTNode value) throws SBMLException {
		return new ASTNodeValue(Math.tanh(value.compile(this).toDouble()), this);
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
		return new ASTNodeValue(
				Double.valueOf(-value.compile(this).toDouble()), this);
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
