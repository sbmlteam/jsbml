/*
 * $Id$
 * $URL$
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

import java.util.HashMap;

/**
 * The base class for {@link Compartment}, {@link Species}, {@link Parameter}. A
 * symbol is a {@link Variable} of the system that declares a unit and a value,
 * i.e., neither its {@link Unit} or {@link UnitDefinition}, nor its value are
 * derived. A Symbol defines both fields. Furthermore, a Symbol may vary through
 * a simulation and can be accessed within the model through its identifier and
 * name attributes.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * @author Nicolas Rodriguez
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 * @has 0..1 units 1 UnitDefinition
 */
public abstract class Symbol extends QuantityWithDefinedUnit implements
		Variable {

	/**
	 * The constant attribute of this variable.
	 */
	protected Boolean constant;
	protected boolean isSetConstant = false;

	/**
	 * Creates a Symbol instance. By default, value, unitsID, constant are null.
	 */
	public Symbol() {
		super();
		this.constant = null;
	}

	/**
	 * Creates a Symbol instance from a level and version. By default, value,
	 * unitsID, constant are null.
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public Symbol(int level, int version) {
		super(level, version);
		this.constant = null;
	}

	/**
	 * 
	 * @param quantity
	 */
	public Symbol(QuantityWithDefinedUnit quantity) {
		super(quantity);
		this.constant = null;
	}

	/**
	 * Creates a Symbol instance from an id, level and version. By default,
	 * value, unitsID, constant are null.
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public Symbol(String id, int level, int version) {
		this(id, null, level, version);
	}

	/**
	 * Creates a Symbol instance from an id, name, level and version. By
	 * default, value, unitsID, constant are null.
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public Symbol(String id, String name, int level, int version) {
		super(id, name, level, version);
		this.constant = null;
	}

	/**
	 * Creates a Symbol instance from a given Symbol.
	 * 
	 * @param nsb
	 */
	public Symbol(Symbol nsb) {
		super(nsb);
		this.constant = nsb.isSetConstant() ? new Boolean(nsb.getConstant())
				: null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.QuantityWithDefinedUnit#clone()
	 */
	@Override
	public abstract Symbol clone();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.AbstractNamedSBase#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if (o instanceof Symbol) {
			boolean equal = super.equals(o);
			Symbol v = (Symbol) o;
			equal &= v.isSetConstant() && isSetConstant();
			equal &= v.getConstant() == getConstant();
			return equal;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Variable#getConstant()
	 */
	public boolean getConstant() {
		return isSetConstant() ? this.constant.booleanValue() : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#getParentSBMLObject()
	 */
	@Override
	public Model getParentSBMLObject() {
		return (Model) parentSBMLObject;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Variable#isConstant()
	 */
	public boolean isConstant() {
		return isSetConstant() ? constant : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Variable#isSetConstant()
	 */
	public boolean isSetConstant() {
		return isSetConstant;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		return isAttributeRead;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Variable#setConstant(boolean)
	 */
	public void setConstant(boolean constant) {
		this.constant = Boolean.valueOf(constant);
		isSetConstant = true;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Variable#unsetConstant()
	 */
	public void unsetConstant() {
		this.constant = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		return attributes;
	}
}
