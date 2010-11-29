/*
 * $Id: QuantityWithDefinedUnit.java 173 2010-04-09 06:32:34Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/QuantityWithDefinedUnit.java $
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

import org.sbml.jsbml.Unit.Kind;

/**
 * This object represents an element with identifier and name, a value, and a
 * defined unit. In particular, this class defines methods to access and
 * manipulate the value and the unit properties of an element within a model.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @date 2010-04-20
 */
public abstract class QuantityWithDefinedUnit extends AbstractNamedSBase
		implements Quantity {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -9088772458214208160L;
	/**
	 * a boolean to help knowing is the value as been set by the user or is the
	 * default one.
	 */
	private boolean isSetValue = false;
	/**
	 * The unit attribute of this variable.
	 */
	private String unitsID;

	/**
	 * The size, initial amount or concentration, or the actual value of this
	 * variable.
	 */
	// Visibility modified to allow children classes to set a default value without having the isSetValue returning true.
	protected Double value = Double.NaN;
	/**
	 * Error message for the case that an invalid unit identifier is to be added
	 * to this object.
	 */
	private static final String ILLEGAL_UNIT_EXCEPTION = "Cannot identify unit %s in the model. Only a valid unit kind or the identifier of an existing unit definition are allowed.";

	/**
	 * 
	 */
	public QuantityWithDefinedUnit() {
		super();
		this.unitsID = null;
	}
	
	/**
	 * 
	 * @param id
	 */
	public QuantityWithDefinedUnit(String id) {
		this();
		setId(id);
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public QuantityWithDefinedUnit(int level, int version) {
		this(null, null, level, version);
	}

	/**
	 * 
	 * @param qwdu
	 */
	public QuantityWithDefinedUnit(QuantityWithDefinedUnit qwdu) {
		super(qwdu);
		if (qwdu.isSetValue()) {
			this.value = new Double(qwdu.getValue());
			isSetValue = true;
		} else {
			this.value = null;
		}
		setUnits(qwdu.isSetUnits() ? new String(qwdu.getUnits()) : null);
	}

	/**
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public QuantityWithDefinedUnit(String id, int level, int version) {
		super(id, null, level, version);
	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public QuantityWithDefinedUnit(String id, String name, int level,
			int version) {
		super(id, name, level, version);
		this.unitsID = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBase#clone()
	 */
	public abstract QuantityWithDefinedUnit clone();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#containsUndeclaredUnits()
	 */
	public boolean containsUndeclaredUnits() {
		return !isSetUnits();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof QuantityWithDefinedUnit) {
			boolean equal = super.equals(o);
			QuantityWithDefinedUnit v = (QuantityWithDefinedUnit) o;
			equal &= v.isSetUnits() == isSetUnits();
			if (v.isSetUnits() && isSetUnits()) {
				equal &= v.getUnits().equals(getUnits());
			}
			if (!(Double.isNaN(v.getValue()) && Double.isNaN(getValue()))) {
				equal &= v.getValue() == getValue();
			} else {
				equal &= (Double.isNaN(v.getValue()) && Double
						.isNaN(getValue()));
			}
			return equal;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnitDefinition()
	 */
	public UnitDefinition getDerivedUnitDefinition() {
		if (isSetUnitsInstance()) {
			return getUnitsInstance();
		}
		UnitDefinition ud = new UnitDefinition(getLevel(), getVersion());
		Unit u = new Unit(getLevel(), getVersion());
		if (isSetUnits()) {
			u.setKind(Kind.valueOf(unitsID.toUpperCase()));
		}
		ud.addUnit(u);
		return ud;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnits()
	 */
	public String getDerivedUnits() {
		return getUnits();
	}

	/**
	 * 
	 * @return the unitsID of this {@link QuantityWithDefinedUnit}. The empty {@link String} if it is not set.
	 */
	public String getUnits() {
		return isSetUnits() ? unitsID : "";
	}

	/**
	 * 
	 * @return The UnitDefinition instance which has the unitsID of this Symbol
	 *         as id. Null if it doesn't exist. In case that the unit of this
	 *         Symbol represents a base unit, a new UnitDefinition will be
	 *         created and returned by this method. This new UnitDefintion will
	 *         only contain the one unit represented by the unit identifier in
	 *         this Symbol. Note that the corresponding model will not contain
	 *         this UnitDefinition. The identifier of this new UnitDefinition
	 *         will be set to the same value as the name of the base unit.
	 */
	public UnitDefinition getUnitsInstance() {
		if (Unit.isUnitKind(unitsID, getLevel(), getVersion())) {
			UnitDefinition ud = new UnitDefinition(unitsID, getLevel(),
					getVersion());
			ud.addUnit(Unit.Kind.valueOf(unitsID.toUpperCase()));
			return ud;
		}
		Model model = getModel();
		return model == null ? null : model.getUnitDefinition(unitsID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Quantity#getValue()
	 */
	public double getValue() {
		return value != null ? value : 0;
	}

	/**
	 * 
	 * @return true if the unitsID of this element is not null.
	 */
	public boolean isSetUnits() {
		return unitsID != null;
	}

	/**
	 * 
	 * @return true if the UnitDefinition which has the unitsID of this Symbol
	 *         as id is not null.
	 */
	public boolean isSetUnitsInstance() {
		Model model = getModel();
		return model == null ? false
				: model.getUnitDefinition(this.unitsID) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Quantity#isSetValue()
	 */
	public boolean isSetValue() {
		return isSetValue;
	}

	/**
	 * Sets the unitsID of this {@link QuantityWithDefinedUnit}. Only valid unit
	 * kind names or identifiers of already existing {@link UnitDefinition}s are
	 * allowed arguments of this function.
	 * 
	 * @param units
	 *            the identifier of an already existing {@link UnitDefinition}
	 *            or an {@link Unit.Kind} identifier for the current
	 *            level/version combination of this unit. Passing a null value
	 *            to this method is equivalent to calling {@link #unsetUnits()}.
	 */
	public void setUnits(String units) {
		
		if (units != null && units.trim().length() == 0) {
			unsetUnits();
			return;
		}
		
		boolean illegalArgument = false;
		String oldUnits = this.unitsID;
		if (units != null) {
			units = units.trim();
			if (units.length() > 0) {
				Model model = getModel();
				if ((model == null)
						|| (Kind.isValidUnitKindString(units, getLevel(),
								getVersion()))) {
					this.unitsID = units;
				} else if ((model != null)
						&& (model.getUnitDefinition(units) != null)) {
					this.unitsID = units;
				} else {
					illegalArgument = true;
				}
			} else {
				illegalArgument = true;
			}
		} else {
			unitsID = null;
		}
		if (illegalArgument) {
			throw new IllegalArgumentException(String.format(ILLEGAL_UNIT_EXCEPTION, units));
		}
		firePropertyChange(SBaseChangedEvent.units, oldUnits, unitsID);
	}

	/**
	 * Sets the {@link Unit} of this {@link QuantityWithDefinedUnit}.
	 * 
	 * @param unit
	 */
	@SuppressWarnings("deprecation")
	public void setUnits(Unit unit) {
		UnitDefinition ud = new UnitDefinition(unit.getKind().toString(),
				getLevel(), getVersion());
		ud.addUnit(unit);
		if ((unit.getExponent() != 1) || (unit.getScale() != 0)
				|| (unit.getMultiplier() != 1d) || (unit.getOffset() != 0d)) {
			StringBuilder sb = new StringBuilder();
			sb.append(unit.getMultiplier());
			sb.append('_');
			sb.append(unit.getScale());
			sb.append('_');
			sb.append(unit.getKind().toString());
			sb.append('_');
			sb.append(unit.getExponent());
			ud.setId(sb.toString());
			Model m = getModel();
			if (m != null) {
				m.addUnitDefinition(ud);
			}
		}
		setUnits(ud);
	}

	/**
	 * Sets the unit of this {@link QuantityWithDefinedUnit}.
	 * 
	 * A new unit will be created base on this kind.
	 * 
	 * @param unitKind
	 */
	public void setUnits(Unit.Kind unitKind) {
		setUnits(new Unit(unitKind, getLevel(), getVersion()));
	}

	/**
	 * Set the unit attribute of this {@link QuantityWithDefinedUnit} to the given unit definition.
	 * 
	 * @param units
	 */
	public void setUnits(UnitDefinition units) {
		setUnits(units != null ? units.getId() : null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Quantity#setValue(double)
	 */
	public void setValue(double value) {
		Double oldValue = this.value;
		this.value = value;
		if (!Double.isNaN(value)) {
			isSetValue = true;
		}
		firePropertyChange(SBaseChangedEvent.value, oldValue, value);
	}

	/**
	 * Sets the unitsID of this {@link QuantityWithDefinedUnit} to null.
	 */
	public void unsetUnits() {
		setUnits((String) null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Quantity#unsetValue()
	 */
	public void unsetValue() {
		Double oldValue = value;
		value = Double.NaN;
		isSetValue = false;
		firePropertyChange(SBaseChangedEvent.value, oldValue, value);
	}
}
