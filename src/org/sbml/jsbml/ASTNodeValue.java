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

import org.sbml.jsbml.ASTNode.Type;

/**
 * When interpreting {@link ASTNode}s, the {@link ASTNodeCompiler} takes
 * elements of this class as arguments and performs its operations on it. Hence,
 * this class represents the union of all possible types to which an abstract
 * syntax tree can be evaluated, i.e., {@link Boolean},
 * {@link NamedSBaseWithDerivedUnit}, {@link Number}, or {@link String}. This class
 * does not define what to do with these values or how to perform any operations
 * on it. It is just the container of a value. The type of this value tells the
 * {@link ASTNodeCompiler} which operation was performed to obtain the value
 * stored in this object.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-04-30
 */
public class ASTNodeValue {

	/**
	 * 
	 */
	private Type type;
	/**
	 * 
	 */
	private Object value;
	/**
	 * 
	 */
	private boolean uiFlag;

	/**
	 * 
	 */
	public ASTNodeValue() {
		type = Type.UNKNOWN;
		value = null;
		uiFlag = true;
	}

	/**
	 * 
	 * @param value
	 */
	public ASTNodeValue(boolean value) {
		this();
		setValue(Boolean.valueOf(value));
	}

	/**
	 * 
	 * @param value
	 */
	public ASTNodeValue(Boolean value) {
		this();
		setValue(value);
	}

	/**
	 * 
	 * @param value
	 */
	public ASTNodeValue(double value) {
		this();
		setValue(Double.valueOf(value));
	}

	/**
	 * 
	 * @param value
	 */
	public ASTNodeValue(float value) {
		this();
		setValue(Float.valueOf(value));
	}

	/**
	 * 
	 * @param value
	 */
	public ASTNodeValue(int value) {
		this();
		setValue(Integer.valueOf(value));
	}

	/**
	 * 
	 * @param value
	 */
	public ASTNodeValue(NamedSBaseWithDerivedUnit value) {
		this();
		setValue(value);
	}

	/**
	 * 
	 * @param value
	 */
	public ASTNodeValue(Number value) {
		this();
		setValue(value);
	}

	/**
	 * 
	 * @param value
	 */
	public ASTNodeValue(String value) {
		this();
		setValue(value);
	}

	/**
	 * 
	 * @param type
	 */
	ASTNodeValue(Type type) {
		this();
		setType(type);
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
	public Object getValue() {
		return value;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isBoolean() {
		return (value != null) && (value instanceof Boolean);
	}

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
	 * 
	 * @return
	 */
	public boolean isNumber() {
		return (value != null) && (value instanceof Number);
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
		return isDifference() && uiFlag;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isUnary() {
		return uiFlag;
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
		this.uiFlag = uiFlag;
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
	 * @param value
	 */
	public void setValue(NamedSBaseWithDerivedUnit value) {
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
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return (value != null) ? value.toString() : super.toString();
	}
}
