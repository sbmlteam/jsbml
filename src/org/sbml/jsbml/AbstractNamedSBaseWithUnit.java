/*
 * $Id:  AbstrtactNamedSBaseWithUnit.java 09:04:30 draeger $
 * $URL: AbstrtactNamedSBaseWithUnit.java $
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
package org.sbml.jsbml;

import org.sbml.jsbml.Unit.Kind;

/**
 * @author Andreas Dr&auml;ger
 * @date 2010-12-16
 */
public abstract class AbstractNamedSBaseWithUnit extends AbstractNamedSBase
		implements NamedSBaseWithDerivedUnit, SBaseWithUnit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3611229078069091891L;

	/**
	 * The unit attribute of this variable.
	 */
	protected String unitsID;

	/**
	 * 
	 */
	public AbstractNamedSBaseWithUnit() {
		this(null, null, -1, -1);
	}

	/**
	 * @param nsbu
	 */
	public AbstractNamedSBaseWithUnit(AbstractNamedSBaseWithUnit nsbu) {
		super(nsbu);
		setUnits(nsbu.isSetUnits() ? new String(nsbu.getUnits()) : null);
	}

	/**
	 * @param level
	 * @param version
	 */
	public AbstractNamedSBaseWithUnit(int level, int version) {
		this(null, null, level, version);
	}

	/**
	 * @param id
	 */
	public AbstractNamedSBaseWithUnit(String id) {
		this(id, null, -1, -1);
	}

	/**
	 * @param id
	 * @param level
	 * @param version
	 */
	public AbstractNamedSBaseWithUnit(String id, int level, int version) {
		this(id, null, level, version);
	}

	/**
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public AbstractNamedSBaseWithUnit(String id, String name, int level,
			int version) {
		super(id, name, level, version);
		this.unitsID = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	public abstract AbstractNamedSBaseWithUnit clone();

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
	 * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof AbstractNamedSBaseWithUnit) {
			boolean equal = super.equals(o);
			AbstractNamedSBaseWithUnit v = (AbstractNamedSBaseWithUnit) o;
			equal &= v.isSetUnits() == isSetUnits();
			if (v.isSetUnits() && isSetUnits()) {
				equal &= v.getUnits().equals(getUnits());
			}
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithUnit#getUnits()
	 */
	public String getUnits() {
		return isSetUnits() ? unitsID : "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithUnit#getUnitsInstance()
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
	 * @see org.sbml.jsbml.SBaseWithUnit#isSetUnits()
	 */
	public boolean isSetUnits() {
		return unitsID != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithUnit#isSetUnitsInstance()
	 */
	public boolean isSetUnitsInstance() {
		if (Unit.isUnitKind(this.unitsID, getLevel(), getVersion())) {
			return true;
		}
		Model model = getModel();
		return model == null ? false
				: model.getUnitDefinition(this.unitsID) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.Unit.Kind)
	 */
	public void setUnits(Kind unitKind) {
		setUnits(new Unit(unitKind, getLevel(), getVersion()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithUnit#setUnits(java.lang.String)
	 */
	public void setUnits(String units) {
		String oldUnits = this.unitsID;
		if (units == null) {
			unitsID = null;
		} else {
			units = units.trim();
			boolean illegalArgument = false;
			if (units.length() == 0) {
				illegalArgument = true;
			} else {
				Model model = getModel();
				if ((model == null)
						|| (Kind.isValidUnitKindString(units, getLevel(),
								getVersion()))) {
					unitsID = units;
				} else if ((model != null)
						&& (model.getUnitDefinition(units) != null)) {
					unitsID = units;
				} else {
					illegalArgument = true;
				}
			}
			if (illegalArgument) {
				throw new IllegalArgumentException(String.format(
						JSBML.ILLEGAL_UNIT_EXCEPTION_MSG, units));
			}
		}
		if (oldUnits != unitsID) {
			firePropertyChange(SBaseChangedEvent.units, oldUnits, unitsID);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.Unit)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.UnitDefinition)
	 */
	public void setUnits(UnitDefinition units) {
		if (units != null) {
			setUnits(units.getId());
		} else {
			unsetUnits();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithUnit#unsetUnits()
	 */
	public void unsetUnits() {
		setUnits((String) null);
	}

}
