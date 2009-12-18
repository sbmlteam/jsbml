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
 * The base class for {@link SimpleSpeciesReference}, {@link Compartment},
 * {@link Species}, {@link Parameter}
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 */
public abstract class Symbol extends AbstractNamedSBase {

	/**
	 * The constant attribute of this variable.
	 */
	protected Boolean constant;
	/**
	 * The unit attribute of this variable.
	 */
	private String unitsID;
	/**
	 * The size, initial amount or concentration, or the actual value of this
	 * variable.
	 */
	protected Double value = Double.NaN;

	/**
	 * a boolean to help knowing is the value as been set by the user or is the
	 * default one.
	 */
	private boolean isSetValue = false;

	/**
	 * Creates a Symbol instance. By default, value, unitsID, constant are null.
	 */
	public Symbol() {
		super();
		this.unitsID = null;
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
		this.unitsID = null;
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
		super(id, level, version);
		this.unitsID = null;
		this.constant = null;
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
		this.unitsID = null;
		this.constant = null;
	}

	/**
	 * Creates a Symbol instance from a given Symbol.
	 * 
	 * @param nsb
	 */
	public Symbol(Symbol nsb) {
		super(nsb);

		if (nsb.isSetUnits()) {
			this.unitsID = new String(nsb.getUnits());
		} else {
			this.unitsID = null;
		}
		if (nsb.isSetValue()) {
			this.value = new Double(nsb.getValue());
			isSetValue = true;
		} else {
			this.value = null;
		}
		if (nsb.isSetConstant()) {
			this.constant = new Boolean(nsb.getConstant());
		} else {
			this.constant = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.AbstractNamedSBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		boolean equal = super.equals(o);

		if (o instanceof Symbol) {

			// System.out.println("Symbol : equals : super.equals = " + equal);

			Symbol v = (Symbol) o;
			equal &= v.getConstant() == getConstant();
			equal &= v.isSetUnits() == isSetUnits();

			if (v.isSetUnits() && isSetUnits()) {
				equal &= v.getUnits().equals(getUnits());
			}

			if (!(Double.isNaN(v.getValue()) && Double.isNaN(getValue()))) {
				equal &= v.getValue() == getValue();
			} else if (Double.isNaN(v.getValue()) && Double.isNaN(getValue())) {
				return true;
			} else {
				return false;
			}
		}

		return equal;
	}

	/**
	 * 
	 * @return the constant Boolean of this symbol.
	 */
	public boolean getConstant() {
		return isSetConstant() ? this.constant : false;
	}

	/**
	 * 
	 * @return the unitsID of this Symbol. The empty String if it is not set.
	 */
	public String getUnits() {
		return isSetUnits() ? unitsID : "";
	}

	/**
	 * 
	 * @return The UnitDefinition instance which has the unitsID of this Symbol
	 *         as id. Null if it doesn't exist.
	 */
	// TODO : we probably need to support the case where unitsID is one of the
	// predefined kind ?
	public UnitDefinition getUnitsInstance() {
		if (getModel() == null) {
			return null;
		}
		return getModel().getUnitDefinition(this.unitsID);
	}

	// TODO : check that it is correct in case the unit is one of the supported
	// kind directly.
	public UnitDefinition getDerivedUnitDefinition() {
		return getUnitsInstance();
	}

	/**
	 * Returns the value of this variable. In Compartments the value is its
	 * size, in Species the value defines its initial amount or concentration,
	 * and in Parameters this returns the value attribute from SBML.
	 * 
	 * @return the value
	 */
	public double getValue() {
		return value != null ? value : 0;
	}

	/**
	 * 
	 * @return the constant value if it is set, false otherwise.
	 */
	public boolean isConstant() {
		return isSetConstant() ? constant : false;
	}

	/**
	 * 
	 * @return true if the UnitDefinition which has the unitsID of this Symbol
	 *         as id is not null.
	 */
	public boolean isSetUnitsInstance() {
		if (getModel() == null) {
			return false;
		}
		return getModel().getUnitDefinition(this.unitsID) != null;
	}

	/**
	 * 
	 * @return true if the unitsID of this Symbol is not null.
	 */
	public boolean isSetUnits() {
		return unitsID != null;
	}

	/**
	 * 
	 * @return true if the value of this Symbol is set.
	 */
	public boolean isSetValue() {
		return isSetValue;
	}

	/**
	 * 
	 * @return true if the constant Boolean of this Symbol is not null.
	 */
	public boolean isSetConstant() {
		return this.constant != null;
	}

	/**
	 * Sets the constant Boolean of this Symbol.
	 * 
	 * @param constant
	 */
	public void setConstant(Boolean constant) {
		this.constant = constant;
		stateChanged();
	}

	/**
	 * Sets the Unit of this Symbol.
	 * 
	 * @param unit
	 */
	@SuppressWarnings("deprecation")
	public void setUnits(Unit unit) {
		UnitDefinition ud = new UnitDefinition(unit.getKind().toString(),
				getLevel(), getVersion());
		ud.addUnit(unit);
		if (unit.getExponent() != 1 || unit.getScale() != 0
				|| unit.getMultiplier() != 1d || unit.getOffset() != 0d) {
			StringBuilder sb = new StringBuilder();
			sb.append(unit.getMultiplier());
			sb.append('_');
			sb.append(unit.getScale());
			sb.append('_');
			sb.append(unit.getKind().toString());
			sb.append('_');
			sb.append(unit.getExponent());
			ud.setId(sb.toString());
			getModel().addUnitDefinition(ud);
		}
		setUnits(ud);
	}

	/**
	 * Sets the unit of this Symbol.
	 * 
	 * @param unitKind
	 */
	public void setUnits(Unit.Kind unitKind) {
		setUnits(new Unit(unitKind, getLevel(), getVersion()));
	}

	/**
	 * Set the unit attribute of this variable to the given unit definition.
	 * 
	 * @param units
	 */
	public void setUnits(UnitDefinition units) {
		this.unitsID = units != null ? units.getId() : null;
		stateChanged();
	}

	/**
	 * Sets the unitsID of this Symbol.
	 * 
	 * @param units
	 */
	public void setUnits(String units) {
		if (units != null && units.trim().length() == 0) {
			this.unitsID = null;
		} else {
			this.unitsID = units;
		}
		stateChanged();
	}

	/**
	 * Note that the meaning of the value can be different in all derived
	 * classes. In Compartments the value defines its size. In Species the value
	 * describes either the initial amount or the initial concentration. Only
	 * the class Parameter really defines a value attribute with this name.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setValue(double value) {
		this.value = value;
		if (!Double.isNaN(value)) {
			isSetValue = true;
		}

		stateChanged();
	}

	/**
	 * Sets the unitsID of this Symbol to null.
	 */
	public void unsetUnits() {
		this.unitsID = null;
	}

	/**
	 * Unsets the value of this Symbol.
	 */
	public void unsetValue() {
		value = Double.NaN;
		isSetValue = false;
		stateChanged();
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
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();

		return attributes;
	}
}
