/*
 * $Id: Symbol.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/Symbol.java $
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

package org.sbml.jsbml.element;

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * @date 2009-08-31
 */
public abstract class Symbol extends AbstractNamedSBase {

	/**
	 * The constant attribute of this variable.
	 */
	private boolean constant;
	/**
	 * The unit attribute of this variable.
	 */
	private UnitDefinition units;
	/**
	 * The unit attribute of this variable.
	 */
	private String unitsID;
	/**
	 * The size, initial amount or concentration, or the actual value of this
	 * variable.
	 */
	private double value;

	/**
	 * 
	 */
	public Symbol() {
		super();
		this.units = null;
		this.value = Double.NaN;
		this.units = null;
		this.unitsID = null;
	}

	
	/**
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public Symbol(String id, int level, int version) {
		super(id, level, version);
		this.units = null;
		this.value = Double.NaN;
	}

	/**
	 * @param id
	 * @param name
	 */
	public Symbol(String id, String name, int level, int version) {
		super(id, name, level, version);
		this.units = null;
		this.value = Double.NaN;
	}

	/**
	 * @param nsb
	 */
	public Symbol(Symbol nsb) {
		super(nsb);
		this.units = nsb.isSetUnits() ? nsb.getUnitsInstance() : null;
		this.value = nsb.getValue();
		this.constant = nsb.getConstant();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.AbstractNamedSBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof Symbol) {
			boolean equal = super.equals(o);
			Symbol v = (Symbol) o;
			equal &= v.getConstant() == getConstant();
			equal &= v.isSetUnits() == isSetUnits();
			if (v.isSetUnits() && isSetUnits())
				equal &= v.getUnits().equals(getUnits());
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean getConstant() {
		return isConstant();
	}

	/**
	 * 
	 * @return
	 */
	String getUnits() {
		return isSetUnitsID() ? unitsID : null;
	}

	/**
	 * 
	 * @return
	 */
	UnitDefinition getUnitsInstance() {
		return units;
	}

	/**
	 * Returns the value of this variable. In Compartments the value is its
	 * size, in Species the value defines its initial amount or concentration,
	 * and in Parameters this returns the value attribute from SBML.
	 * 
	 * @return the value
	 */
	double getValue() {
		return value;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isConstant() {
		return constant;
	}

	/**
	 * 
	 * @return
	 */
	boolean isSetUnits() {
		return units != null;
	}
	
	/**
	 * 
	 * @return
	 */
	boolean isSetUnitsID() {
		return unitsID != null;
	}

	/**
	 * 
	 * @return
	 */
	boolean isSetValue() {
		return !Double.isNaN(value);
	}

	/**
	 * 
	 * @param constant
	 */
	public void setConstant(boolean constant) {
		this.constant = constant;
		stateChanged();
	}

	/**
	 * 
	 * @param unit
	 */
	void setUnits(Unit unit) {
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
	 * 
	 * @param unitKind
	 */
	void setUnits(Unit.Kind unitKind) {
		setUnits(new Unit(unitKind, getLevel(), getVersion()));
	}

	/**
	 * Set the unit attribute of this variable to the given unit definition.
	 * 
	 * @param units
	 */
	void setUnits(UnitDefinition units) {
		this.units = units;
		stateChanged();
	}
	
	/**
	 * 
	 * @param units
	 */
	void setUnitsID(String units) {
		this.unitsID = units;
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
	void setValue(double value) {
		this.value = value;
		stateChanged();
	}

	/**
	 * 
	 */
	public void unsetValue() {
		value = Double.NaN;
		stateChanged();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		if (attributeName.equals("units")){
			this.setUnitsID(value);
			return true;
		}
		else if (attributeName.equals("constant")){
			if (value.equals("true")){
				this.setConstant(true);
				return true;
			}
			else if (value.equals("false")){
				this.setConstant(false);
				return true;
			}
		}
		
		return isAttributeRead;
	}

}
