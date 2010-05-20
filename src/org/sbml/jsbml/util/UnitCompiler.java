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
package org.sbml.jsbml.util;

import java.util.HashMap;

import org.sbml.jsbml.ASTNodeCompiler;
import org.sbml.jsbml.ASTNodeValue;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBaseWithDerivedUnit;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.Unit.Kind;

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
		UnitDefinition ud = new UnitDefinition(level, version);
		if (value.isBoolean()) {
			ud.addUnit(new Unit(Kind.DIMENSIONLESS, level, version));
		} else if (value.isDifference() || value.isSum() || value.isUMinus()) {
			ud.setListOfUnits(value.getUnit().getListOfUnits());
		} else if (value.isNumber()) {

		} else if (value.isString()) {
			// TODO
		}
		return new ASTNodeValue(ud, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#and(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue and(ASTNodeValue... values) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccos(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccos(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccosh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccosh(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccot(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccot(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccoth(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccoth(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccsc(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccsc(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arccsch(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arccsch(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsec(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsec(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsech(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsech(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsin(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsin(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arcsinh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arcsinh(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arctan(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arctan(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#arctanh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue arctanh(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#ceiling(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue ceiling(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return null;
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
			return new ASTNodeValue(new UnitDefinition(level, version), this);
		}
		int i;
		ASTNodeValue value = new ASTNodeValue(this);
		UnitDefinition ud = values[0].getUnit();
		if (values[0].isNumber()) {
			i = values.length - 1;
			while (i >= 0 && values[i].isNumber()) {
				i--;
			}
			values[0].setUnit(values[i].getUnit());
			if (i == 0) {
				/*
				 * Necessary to remember in higher recursion steps that this was
				 * just a number. The actual value does not matter.
				 */
				value.setValue(Integer.valueOf(0));
			}
		}
		for (i = 1; i < values.length; i++) {
			if (values[i].isNumber()) {
				values[i].setUnit(values[i - 1].getUnit());
			} else if (!UnitDefinition.areEquivalent(ud, values[i].getUnit())) {
				ud.clear();
				break;
			}
		}
		return value;
	}

	/**
	 * Compile boolean values
	 * 
	 * @param b
	 * @return
	 */
	private ASTNodeValue compile(boolean b) {
		UnitDefinition ud = new UnitDefinition(level, version);
		ud.addUnit(new Unit(Kind.DIMENSIONLESS, level, version));
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
		ASTNodeValue value = new ASTNodeValue(real, this);
		value.setUnit(new UnitDefinition(level, version));
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(int)
	 */
	public ASTNodeValue compile(int integer) {
		ASTNodeValue value = new ASTNodeValue(integer, this);
		value.setUnit(new UnitDefinition(level, version));
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.sbml.jsbml.ASTNodeCompiler#compile(org.sbml.jsbml.
	 * NamedSBaseWithDerivedUnit)
	 */
	public ASTNodeValue compile(NamedSBaseWithDerivedUnit variable) {
		ASTNodeValue value = new ASTNodeValue(variable, this);
		UnitDefinition ud = variable.getDerivedUnitDefinition();
		value.setUnit(ud == null ? new UnitDefinition(level, version) : ud);
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#compile(java.lang.String)
	 */
	public ASTNodeValue compile(String name) {
		UnitDefinition ud = namesToUnits.get(name);
		if (ud == null) {
			ud = new UnitDefinition(level, version);
		}
		return new ASTNodeValue(ud, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cos(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue cos(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cosh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue cosh(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#cot(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue cot(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#coth(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue coth(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#csc(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue csc(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#csch(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue csch(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#delay(org.sbml.jsbml.ASTNodeValue,
	 * double)
	 */
	public ASTNodeValue delay(ASTNodeValue x, double d) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#equal(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue equal(ASTNodeValue left, ASTNodeValue right) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#exp(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue exp(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#factorial(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue factorial(ASTNodeValue value) {
		if (value.isNumber()) {
			return root(1 / value.toDouble(), value);
		}
		/*
		 * TODO: absolutely unclear how to derive the unit here.
		 */
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#floor(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue floor(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#frac(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue frac(ASTNodeValue left, ASTNodeValue right) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
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
		return compile(Math.E);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantFalse()
	 */
	public ASTNodeValue getConstantFalse() {
		return compile(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantPi()
	 */
	public ASTNodeValue getConstantPi() {
		return compile(Math.PI);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#getConstantTrue()
	 */
	public ASTNodeValue getConstantTrue() {
		return compile(true);
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
	public ASTNodeValue greaterEqual(ASTNodeValue left, ASTNodeValue right) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#greaterThan(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue greaterThan(ASTNodeValue left, ASTNodeValue right) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
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
	public ASTNodeValue lessEqual(ASTNodeValue left, ASTNodeValue right) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#lessThan(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue lessThan(ASTNodeValue left, ASTNodeValue right) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#ln(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue ln(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#log(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue log(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#log(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue log(ASTNodeValue left, ASTNodeValue right) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#minus(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue minus(ASTNodeValue... values) {
		return checkIdentical(values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#not(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue not(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#notEqual(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue notEqual(ASTNodeValue left, ASTNodeValue right) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#or(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue or(ASTNodeValue... values) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.sbml.jsbml.ASTNodeCompiler#piecewise(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue piecewise(ASTNodeValue... values) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#plus(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue plus(ASTNodeValue... values) {
		return checkIdentical(values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#pow(org.sbml.jsbml.ASTNodeValue,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue pow(ASTNodeValue base, ASTNodeValue exponent) {
		if (exponent.isNumber()) {
			return root(1 / exponent.toDouble(), base);
		}
		/*
		 * TODO: What to to if the exponent is not a number?
		 */
		return new ASTNodeValue(new UnitDefinition(level, version), this);
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
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#root(double,
	 * org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue root(double rootExponent, ASTNodeValue radiant) {
		UnitDefinition ud = radiant.getUnit().clone();
		/*
		 * TODO: This is a problem! The exponent can only be integer!
		 */
		for (Unit u : ud.getListOfUnits()) {
			u.setExponent((int) Math.round(u.getExponent() / rootExponent));
		}
		return new ASTNodeValue(ud, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sec(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sec(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sech(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sech(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sin(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sin(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#sinh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue sinh(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
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
				ud = new UnitDefinition(level, version);
			}
		}
		return new ASTNodeValue(ud, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#tan(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue tan(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#tanh(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue tanh(ASTNodeValue value) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#times(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue times(ASTNodeValue... values) {
		UnitDefinition ud = new UnitDefinition(level, version);
		for (ASTNodeValue value : values) {
			ud.multiplyWith(value.getUnit());
		}
		return new ASTNodeValue(ud, this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#uiMinus(org.sbml.jsbml.ASTNodeValue)
	 */
	public ASTNodeValue uiMinus(ASTNodeValue value) {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#unknownValue()
	 */
	public ASTNodeValue unknownValue() {
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ASTNodeCompiler#xor(org.sbml.jsbml.ASTNodeValue[])
	 */
	public ASTNodeValue xor(ASTNodeValue... values) {
		// TODO Auto-generated method stub
		return new ASTNodeValue(new UnitDefinition(level, version), this);
	}

}
