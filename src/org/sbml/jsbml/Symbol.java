/*
 *  SBMLsqueezer creates rate equations for reactions in SBML files
 *  (http://sbml.org).
 *  Copyright (C) 2009 ZBIT, University of Tübingen, Andreas Dräger
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.sbml.jsbml;

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
	 * The size, initial amount or concentration, or the actual value of this
	 * variable.
	 */
	private double value;

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
		return isSetUnits() ? units.getId() : "";
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
		setUnits(new Unit(unitKind, getLevel(), getLevel()));
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
	}

}
