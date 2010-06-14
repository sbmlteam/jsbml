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

import org.sbml.jsbml.ASTNodeCompiler;
import org.sbml.jsbml.ASTNodeValue;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBaseWithDerivedUnit;
import org.sbml.jsbml.Quantity;
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
public class UnitCompiler implements ASTNodeCompiler {

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
	public UnitCompiler() {
		this(-1, -1);
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public UnitCompiler(int level, int version) {
		this.level = level;
		this.version = version;
		this.namesToUnits = new HashMap<String, UnitDefinition>();
	}

	/**
	 * 
	 * @param model
	 */
	public UnitCompiler(Model model) {
		this(model.getLevel(), model.getVersion());
		this.model = model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#abs(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue abs(ASTNodeValue value) {
		if (value.isDifference() || value.isSum() || value.isUMinus()
				|| value.isNumber()) {
			value.setValue(Double.valueOf(Math.abs(value.toDouble())));
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#and(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue and(ASTNodeValue... values) {
		ASTNodeValue value = dimensionless();
		boolean val = true;
		for (ASTNodeValue v : values) {
			val &= v.toBoolean();
		}
		value.setValue(val);
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccos(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccos(ASTNodeValue value) {
		return new ASTNodeValue(Math.acos(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccosh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccosh(ASTNodeValue value) {
		return new ASTNodeValue(Maths.arccosh(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccot(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccot(ASTNodeValue value) {
		return new ASTNodeValue(Maths.arccot(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccoth(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccoth(ASTNodeValue value) {
		return new ASTNodeValue(Maths.arccoth(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccsc(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccsc(ASTNodeValue value) {
		return new ASTNodeValue(Maths.arccsc(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccsch(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccsch(ASTNodeValue value) {
		return new ASTNodeValue(Maths.arccsch(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsec(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsec(ASTNodeValue value) {
		return new ASTNodeValue(Maths.arcsec(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsech(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsech(ASTNodeValue value) {
		return new ASTNodeValue(Maths.arcsech(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsin(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsin(ASTNodeValue value) {
		return new ASTNodeValue(Math.asin(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsinh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsinh(ASTNodeValue value) {
		return new ASTNodeValue(Maths.arcsinh(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arctan(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arctan(ASTNodeValue value) {
		return new ASTNodeValue(Math.atan(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arctanh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arctanh(ASTNodeValue value) {
		return new ASTNodeValue(Maths.arctanh(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#ceiling(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue ceiling(ASTNodeValue value) {
		value.setValue(Double.valueOf(Math.ceil(value.toDouble())));
		return value;
	}

	/**
	 * Checks whether all units in the given array are identical.
	 * 
	 * @param values
	 * @return An empty unit definition if not identical, or the common unit
	 *         definition otherwise.
	 */
	private ASTNodeValue checkIdentical(ASTNodeValue... values) {
		if (values.length == 0) {
			return new ASTNodeValue(this);
		}
		int i;
		ASTNodeValue value = new ASTNodeValue(this);
		if (values[0].isNumber()) {
			i = values.length - 1;
			while (i >= 0 && values[i].isNumber()) {
				i--;
			}
			values[0].setUnit(values[i].getUnit());
			/*
			 * Necessary to remember in higher recursion steps that this was
			 * just a number. The actual value does not matter.
			 */
			value.setValue(Integer.valueOf(0));
		}
		UnitDefinition ud = values[0].getUnit();
		for (i = 1; i < values.length; i++) {
			if (values[i].isNumber()) {
				values[i].setUnit(values[i - 1].getUnit());
			} else if (!UnitDefinition.areEquivalent(ud, values[i].getUnit())) {
				ud.clear();
				break;
			}
		}
		if (ud.getNumUnits() > 0) {
			value.setUnit(ud);
		}
		return value;
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
		value.setUnit(ud);
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
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(double)
	 */
	public ASTNodeValue compile(double real) {
		return new ASTNodeValue(real, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(int)
	 */
	public ASTNodeValue compile(int integer) {
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
		value.setUnit(variable.getDerivedUnitDefinition());
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(java.lang.String)
	 */
	public ASTNodeValue compile(String name) {
		return new ASTNodeValue(namesToUnits.get(name), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cos(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue cos(ASTNodeValue value) {
		return new ASTNodeValue(Math.cos(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cosh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue cosh(ASTNodeValue value) {
		return new ASTNodeValue(Math.cosh(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cot(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue cot(ASTNodeValue value) {
		return new ASTNodeValue(Maths.cot(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#coth(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue coth(ASTNodeValue value) {
		return new ASTNodeValue(Maths.coth(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#csc(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue csc(ASTNodeValue value) {
		return new ASTNodeValue(Maths.csc(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#csch(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue csch(ASTNodeValue value) {
		return new ASTNodeValue(Maths.csch(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#delay(org.sbml.jsbml.ASTNodeValue,
	 * double)
	 */
	public ASTNodeValue delay(ASTNodeValue x, double d) {
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
	public ASTNodeValue eq(ASTNodeValue left, ASTNodeValue right) {
		ASTNodeValue v = dimensionless();
		v.setValue(left.toDouble() == right.toDouble());
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#exp(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue exp(ASTNodeValue value) {
		return pow(getConstantE(), value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#factorial(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue factorial(ASTNodeValue value) {
		if (value.isNumber()) {
			double d = value.toDouble();
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
	public ASTNodeValue floor(ASTNodeValue value) {
		return new ASTNodeValue(Math.floor(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#frac(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue frac(ASTNodeValue numerator, ASTNodeValue denominator) {
		UnitDefinition ud = numerator.getUnit().clone();
		UnitDefinition denom = denominator.getUnit().clone();
		setLevelAndVersion(ud);
		setLevelAndVersion(denom);
		ud.divideBy(denom);
		ASTNodeValue value = new ASTNodeValue(ud, this);
		value.setValue(numerator.toDouble() / denominator.toDouble());
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
	public ASTNodeValue function(FunctionDefinition function,
			ASTNodeValue... args) {
		for (ASTNodeValue arg : args) {
			this.namesToUnits.put(arg.toString(), arg.getUnit());
		}
		return new ASTNodeValue(function.getMath().deriveUnit(), this);
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
		return compile(Double.NEGATIVE_INFINITY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getPositiveInfinity()
	 */
	public ASTNodeValue getPositiveInfinity() {
		return compile(Double.POSITIVE_INFINITY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#greaterEqual(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue ge(ASTNodeValue left, ASTNodeValue right) {
		ASTNodeValue value = dimensionless();
		value.setValue(left.toDouble() >= right.toDouble());
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#greaterThan(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue gt(ASTNodeValue left, ASTNodeValue right) {
		ASTNodeValue value = dimensionless();
		value.setValue(left.toDouble() > right.toDouble());
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
	public ASTNodeValue lambda(ASTNodeValue... values) {
		for (int i = 0; i < values.length - 1; i++) {
			namesToUnits.put(values[i].toString(), values[i].getUnit());
		}
		return new ASTNodeValue(values[values.length - 1].getUnit(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#lessEqual(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue le(ASTNodeValue left, ASTNodeValue right) {
		ASTNodeValue value = dimensionless();
		value.setValue(left.toDouble() <= right.toDouble());
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#lessThan(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue lt(ASTNodeValue left, ASTNodeValue right) {
		ASTNodeValue value = dimensionless();
		value.setValue(left.toDouble() < right.toDouble());
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#ln(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue ln(ASTNodeValue value) {
		return new ASTNodeValue(Maths.ln(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#log(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue log(ASTNodeValue value) {
		return new ASTNodeValue(Maths.log(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#log(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue log(ASTNodeValue number, ASTNodeValue base) {
		return new ASTNodeValue(Maths.log(number.toDouble(), base.toDouble()),
				this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#minus(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue minus(ASTNodeValue... values) {
		ASTNodeValue value = checkIdentical(values);
		if (values.length > 0) {
			value.setValue(values[0].toNumber());
			for (int i = 1; i < values.length; i++) {
				value.setValue(Double.valueOf(value.toDouble()
						- values[i].toNumber().doubleValue()));
			}
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#not(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue not(ASTNodeValue value) {
		ASTNodeValue v = dimensionless();
		v.setValue(!value.toBoolean());
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#notEqual(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue ne(ASTNodeValue left, ASTNodeValue right) {
		ASTNodeValue v = dimensionless();
		v.setValue(left.toDouble() != right.toDouble());
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#or(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue or(ASTNodeValue... values) {
		ASTNodeValue v = dimensionless();
		v.setValue(false);
		for (ASTNodeValue value : values) {
			if (value.toBoolean()) {
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
	public ASTNodeValue piecewise(ASTNodeValue... values) {
		ASTNodeValue[] varray = new ASTNodeValue[values.length / 2];
		for (int i = 0, j = 0; i < values.length; i += 2, j++) {
			varray[j] = values[i];
		}
		int i;
		for (i = 1; i < values.length - 1; i += 2) {
			if (values[i].toBoolean()) {
				return new ASTNodeValue(values[i - 1].toDouble(), this);
			}
		}
		ASTNodeValue value = checkIdentical(varray);
		value.setValue(Double.valueOf(values[i - 1].toDouble()));

		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#plus(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue plus(ASTNodeValue... values) {
		ASTNodeValue value = checkIdentical(values);
		for (ASTNodeValue v : values) {
			value.setValue(Double.valueOf(value.toDouble()
					+ v.toNumber().doubleValue()));
		}
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#pow(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue pow(ASTNodeValue base, ASTNodeValue exponent) {
		double exp = Double.NaN, v;
		if (exponent.isNumber()) {
			v = exponent.toDouble();
			exp = v == 0 ? 0 : 1 / v;
		} else {
			if (exponent.getUnit().getNumUnits() == 1) {
				System.out.println(exponent.getUnit().getUnit(0).getKind());
				Kind kind = exponent.getUnit().getUnit(0).getKind();
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
	public ASTNodeValue root(ASTNodeValue rootExponent, ASTNodeValue radiant) {
		if (rootExponent.isNumber()) {
			return root(rootExponent.toDouble(), radiant);
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
	public ASTNodeValue root(double rootExponent, ASTNodeValue radiant) {
		UnitDefinition ud = radiant.getUnit().clone();
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
	public ASTNodeValue sec(ASTNodeValue value) {
		return new ASTNodeValue(Maths.sec(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sech(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sech(ASTNodeValue value) {
		return new ASTNodeValue(Maths.sech(value.toDouble()), this);
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
	public ASTNodeValue sin(ASTNodeValue value) {
		return new ASTNodeValue(Math.sin(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sinh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sinh(ASTNodeValue value) {
		return new ASTNodeValue(Math.sinh(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sqrt(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sqrt(ASTNodeValue value) {
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
			ud = invalid().getUnit();
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
	public ASTNodeValue tan(ASTNodeValue value) {
		return new ASTNodeValue(Math.tan(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#tanh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue tanh(ASTNodeValue value) {
		return new ASTNodeValue(Math.tanh(value.toDouble()), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#times(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue times(ASTNodeValue... values) {
		UnitDefinition ud = new UnitDefinition(level, version);
		UnitDefinition v;
		double d = 1d;
		for (ASTNodeValue value : values) {
			v = value.getUnit().clone();
			setLevelAndVersion(v);
			ud.multiplyWith(v);
			d *= value.toDouble();
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
	public ASTNodeValue uiMinus(ASTNodeValue value) {
		value.setValue(Double.valueOf(-value.toDouble()));
		return value;
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
	public ASTNodeValue xor(ASTNodeValue... values) {
		ASTNodeValue value = dimensionless();
		boolean v = false;
		for (int i = 0; i < values.length; i++) {
			if (values[i].toBoolean()) {
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
