/*
 * $Id: ASTNodeValue.java 191 2010-04-22 07:33:11Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/ASTNodeValue.java $
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
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
package org.sbml.jsbml;

import java.io.IOException;

import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.xml.parsers.MathMLParser;
import org.w3c.dom.Node;

/**
 * When interpreting {@link ASTNode}s, the {@link ASTNodeCompiler} takes
 * elements of this class as arguments and performs its operations on it. Hence,
 * this class represents the union of all possible types to which an abstract
 * syntax tree can be evaluated, i.e., {@link Boolean},
 * {@link NamedSBaseWithDerivedUnit}, {@link Number}, or {@link String}. This
 * class does not define what to do with these values or how to perform any
 * operations on it. It is just the container of a value. The type of this value
 * tells the {@link ASTNodeCompiler} which operation was performed to obtain the
 * value stored in this object.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-04-30
 */
public class ASTNodeValue {

	/**
	 * An {@link ASTNodeCompiler} that is needed in the case that this
	 * {@link ASTNodeValue} contains a derivative of
	 * {@link NamedSBaseWithDerivedUnit} as value to translate this value into a
	 * double. If the compiler does not convert instances of
	 * {@link NamedSBaseWithDerivedUnit} to double numbers, a
	 * {@link ClassCastException} will be thrown..
	 */
	private ASTNodeCompiler compiler;
	/**
	 * The level of the underlying {@link SBMLDocument}.
	 */
	private int level;
	/**
	 * The type of the underlying {@link ASTNode}.
	 */
	private Type type;
	/**
	 * Flag to indicate whether the underlying {@link ASTNode} was an unary
	 * node.
	 */
	private boolean uFlag;
	/**
	 * The unit associated to the value of this object.
	 */
	private UnitDefinition unit;
	/**
	 * The actual value of this element. This is an {@link Object}, but indeed
	 * we restrict the possibilities to store only certain types here.
	 */
	private Object value;
	/**
	 * The version of the underlying {@link SBMLDocument}.
	 */
	private int version;

	/**
	 * 
	 */
	public ASTNodeValue(ASTNodeCompiler compiler) {
		type = Type.UNKNOWN;
		value = null;
		unit = null;
		uFlag = true;
		this.compiler = compiler;
	}

	/**
	 * 
	 * @param value
	 */
	public ASTNodeValue(boolean value, ASTNodeCompiler compiler) {
		this(compiler);
		setValue(Boolean.valueOf(value));
	}

	/**
	 * 
	 * @param value
	 */
	public ASTNodeValue(double value, ASTNodeCompiler compiler) {
		this(compiler);
		setValue(Double.valueOf(value));
	}

	/**
	 * 
	 * @param value
	 */
	public ASTNodeValue(float value, ASTNodeCompiler compiler) {
		this(compiler);
		setValue(Float.valueOf(value));
	}

	/**
	 * 
	 * @param value
	 */
	public ASTNodeValue(int value, ASTNodeCompiler compiler) {
		this(compiler);
		setValue(Integer.valueOf(value));
	}

	/**
	 * 
	 * @param value
	 */
	public ASTNodeValue(NamedSBaseWithDerivedUnit value,
			ASTNodeCompiler compiler) {
		this(compiler);
		setValue(value);
	}

	/**
	 * 
	 * @param node
	 * @param compiler
	 */
	public ASTNodeValue(Node node, ASTNodeCompiler compiler) {
		this(compiler);
		setValue(node);
	}

	/**
	 * 
	 * @param value
	 */
	public ASTNodeValue(Number value, ASTNodeCompiler compiler) {
		this(compiler);
		setValue(value);
	}

	/**
	 * 
	 * @param value
	 */
	public ASTNodeValue(String value, ASTNodeCompiler compiler) {
		this(compiler);
		setValue(value);
	}

	/**
	 * 
	 * @param type
	 */
	ASTNodeValue(Type type, ASTNodeCompiler compiler) {
		this(compiler);
		setType(type);
	}

	/**
	 * 
	 * @param unit
	 */
	public ASTNodeValue(UnitDefinition unit, ASTNodeCompiler compiler) {
		this(compiler);
		setUnit(unit);
	}

	/**
	 * Flag to indicate whether or not units have been fully declared for this
	 * value.
	 * 
	 * @return
	 */
	public boolean containsUndeclaredUnits() {
		return !isSetUnit() || (getUnit().getNumUnits() == 0);
	}

	/**
	 * Returns the level of the SBML document that was the basis to create this
	 * {@link ASTNodeValue}.
	 * 
	 * @return
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * 
	 * @return
	 */
	public Type getType() {
		return type;
	}

	/**
	 * 
	 * @return
	 */
	public UnitDefinition getUnit() {
		if (!isSetUnit()) {
			unit = new UnitDefinition();
			unit.addUnit(new Unit());
		}
		return unit;
	}

	/**
	 * 
	 * @return
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isBoolean() {
		return (value != null) && (value instanceof Boolean);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isDifference() {
		return type == Type.MINUS;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isNamedSBaseWithDerivedUnit() {
		return (value != null) && (value instanceof NamedSBaseWithDerivedUnit);
	}

	/**
	 * Checks whether the value encapsulated in this {@link ASTNodeValue} is an
	 * instance of {@link Node}.
	 * 
	 * @return
	 */
	public boolean isNode() {
		if (value == null) {
			return false;
		}
		return value instanceof Node;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isNumber() {
		return (value != null) && (value instanceof Number);
	}

	/**
	 * Test if this value can be evaluated to a {@link UnitDefinition}
	 * 
	 * @return
	 */
	public boolean isSetUnit() {
		return unit != null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isString() {
		return (value != null) && (value instanceof String);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSum() {
		return type == Type.PLUS;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isUMinus() {
		return isDifference() && uFlag;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isUnary() {
		return uFlag;
	}

	/**
	 * 
	 * @return
	 */
	public String printValueAndUnit() {
		StringBuilder sb = new StringBuilder();
		if (value != null) {
			sb.append(value.toString());
		} else {
			sb.append('?');
		}
		sb.append(' ');
		sb.append(UnitDefinition.printUnits(getUnit(), true));
		return sb.toString();
	}

	/**
	 * @param level
	 *            the level to set
	 */
	void setLevel(int level) {
		this.level = level;
	}

	/**
	 * 
	 * @param type
	 */
	void setType(Type type) {
		this.type = type;
	}

	/**
	 * 
	 * @param uiFlag
	 */
	void setUIFlag(boolean uiFlag) {
		this.uFlag = uiFlag;
	}

	/**
	 * 
	 * @param unit
	 */
	public void setUnit(UnitDefinition unit) {
		this.unit = unit.simplify();
	}

	/**
	 * 
	 * @param value
	 */
	public void setValue(boolean value) {
		setValue(Boolean.valueOf(value));
	}

	/**
	 * 
	 * @param value
	 */
	public void setValue(Boolean value) {
		this.value = value;
	}

	/**
	 * 
	 * @param d
	 */
	public void setValue(double d) {
		setValue(Double.valueOf(d));
	}

	/**
	 * 
	 * @param i
	 */
	public void setValue(int i) {
		setValue(Integer.valueOf(i));
	}

	/**
	 * 
	 * @param l
	 */
	public void setValue(long l) {
		setValue(Long.valueOf(l));
	}

	/**
	 * 
	 * @param value
	 */
	public void setValue(NamedSBaseWithDerivedUnit value) {
		this.value = value;
	}

	/**
	 * 
	 * @param value
	 */
	public void setValue(Node value) {
		this.value = value;
	}

	/**
	 * 
	 * @param value
	 */
	public void setValue(Number value) {
		this.value = value;
	}

	/**
	 * 
	 * @param s
	 */
	public void setValue(short s) {
		setValue(Short.valueOf(s));
	}

	/**
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	void setVersion(int version) {
		this.version = version;
	}

	/**
	 * Analog to the toDouble() method but this method tries to evaluate the
	 * value contained in this object to a boolean value. In case of numbers,
	 * one is considered true, all other values represent false.
	 * 
	 * @return A boolean representing the value of this container. Note that if
	 *         the value is an instance of {@link NamedSBaseWithDerivedUnit}, it
	 *         can only be converted into a boolean value if the
	 *         {@link ASTNodeCompiler} associated with this object compiles this
	 *         value to an {@link ASTNodeValue} that contains a boolean or at
	 *         least a {@link Number} value.
	 * @throws SBMLException 
	 */
	public boolean toBoolean() throws SBMLException {
		if (isBoolean()) {
			return ((Boolean) getValue()).booleanValue();
		}
		if (isNamedSBaseWithDerivedUnit()) {
			ASTNodeValue value = compiler
					.compile((NamedSBaseWithDerivedUnit) getValue());
			if (value.isBoolean()) {
				return ((Boolean) value.getValue()).booleanValue();
			} else if (value.isNumber()) {
				return 1 == ((Number) value.getValue()).doubleValue();
			}
		}
		if (isNumber()) {
			return 1 == ((Number) getValue()).doubleValue();
		}
		return Boolean.parseBoolean(toString());
	}

	/**
	 * 
	 * @return
	 * @throws SBMLException 
	 */
	public double toDouble() throws SBMLException {
		return toNumber().doubleValue();
	}

	/**
	 * 
	 * @return
	 * @throws SBMLException 
	 */
	public float toFloat() throws SBMLException {
		return toNumber().floatValue();
	}

	/**
	 * This method is analog to the toDouble method but tries to convert this
	 * value into an integer.
	 * 
	 * @param compiler
	 * @return
	 * @throws SBMLException 
	 */
	public int toInteger() throws SBMLException {
		return toNumber().intValue();
	}

	/**
	 * 
	 * @return
	 * @throws SBMLException 
	 */
	public long toLong() throws SBMLException {
		return toNumber().longValue();
	}

	/**
	 * 
	 * @return
	 */
	public NamedSBaseWithDerivedUnit toNamedSBaseWithDerivedUnit() {
		if (isNamedSBaseWithDerivedUnit()) {
			return (NamedSBaseWithDerivedUnit) getValue();
		}
		if (isString()) {
			// actually no way to to obtain the namedSBase from a model
			// because no reference to the model is stored here.
			ASTNodeValue value = compiler.compile(toString());
			if (value.isNamedSBaseWithDerivedUnit()) {
				return (NamedSBaseWithDerivedUnit) value;
			}
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public Node toNode() {
		if (isNode()) {
			return (Node) value;
		}
		return null;
	}

	/**
	 * Tries to convert the value contained in this object into a double number.
	 * 
	 * @return The number value represented by this given {@link ASTNodeValue}.
	 *         In case this {@link ASTNodeValue} contains an instance of
	 *         {@link Boolean}, zero is returned for false and one for true. If
	 *         the value is null or cannot be converted to any number,
	 *         {@link Double.NaN} will be returned. Note that if the value of
	 *         this container is an instance of
	 *         {@link NamedSBaseWithDerivedUnit}, the value can only be
	 *         converted to a number if the compiler associated with this
	 *         {@link ASTNodeValue} compiles this
	 *         {@link NamedSBaseWithDerivedUnit} to an {@link ASTNodeValue} that
	 *         contains a {@link Number}.
	 * @throws SBMLException 
	 */
	public Number toNumber() throws SBMLException {
		if (isNumber()) {
			return (Number) getValue();
		}
		if (isBoolean()) {
			return Double.valueOf(((Boolean) getValue()).booleanValue() ? 1d
					: 0d);
		}
		if (isNamedSBaseWithDerivedUnit()) {
			ASTNodeValue value = compiler
					.compile((NamedSBaseWithDerivedUnit) getValue());
			if (value.isNumber()) {
				return ((Number) value.getValue()).doubleValue();
			}
		}
		if (isString()) {
			return Double.valueOf(toString());
		}
		return Double.valueOf(Double.NaN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (isNode()) {
			try {
				return MathMLParser.toMathML(MathMLParser
						.createMathMLDocumentFor(toNode(), getLevel()), true,
						true, 2);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SBMLException e) {
				e.printStackTrace();
			}
		}
		return (value != null) ? value.toString() : super.toString();
	}

	/**
	 * Removes the unit of this element, i.e., the unit will become invalid.
	 */
	public void unsetUnit() {
		unit = null;
	}
}
