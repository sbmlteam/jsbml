/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2011 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml;

import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * This simple implementation of the interfaces
 * {@link NamedSBaseWithDerivedUnit} and {@link SBaseWithUnit} defines elements
 * that can be addressed by their identifier and are or can be associated with a
 * defined {@link Unit} or {@link UnitDefinition}. Derived elements from this
 * class might be directly or indirectly associated with some value, i.e., the
 * value might be derived by evaluating some expression in form of an
 * {@link ASTNode}, or it might be directly defined as an attribute.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-12-16
 * @since 0.8
 * @version $Rev$
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
		if (nsbu.isSetUnits()) {
			setUnits(new String(nsbu.getUnits()));
		} else {
			unitsID = nsbu.unitsID == null ? null : new String(nsbu.unitsID);
		}
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
	 * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equals = super.equals(object);
		if (equals) {
			AbstractNamedSBaseWithUnit v = (AbstractNamedSBaseWithUnit) object;
			equals &= v.isSetUnits() == isSetUnits();
			if (equals && isSetUnits()) {
				equals &= v.getUnits().equals(getUnits());
			}
		}
		return equals;
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
		String derivedUnits = getDerivedUnits();
		Model model = getModel();
		if ((model != null) && (derivedUnits.length() > 0)) {
			return model.getUnitDefinition(derivedUnits);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnits()
	 */
	public String getDerivedUnits() {
		if (isSetUnits()) {
			return unitsID;
		}
		String predef = getPredefinedUnitID(); 
		return predef != null ? predef : "";
	}

	/**
	 * Returns the predefined unit identifier for this data type with the
	 * current level/version combination.
	 * 
	 * @return an identifier of a unit in the containing {@link Model}. This can
	 *         be one of the predefined unit identifiers if there are any.
	 */
	public abstract String getPredefinedUnitID();

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
		String unitsId = this.unitsID;
		if (isSetUnits()) {
			if (Unit.isUnitKind(unitsId, getLevel(), getVersion())) {
				UnitDefinition ud = new UnitDefinition(unitsId + "_base", getLevel(),
						getVersion());
				ud.addUnit(Unit.Kind.valueOf(unitsId.toUpperCase()));
				return ud;
			}
		} else {
			unitsId = getPredefinedUnitID();
		}
		Model model = getModel();
		return model == null ? null : model.getUnitDefinition(unitsId);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 859;
		int hashCode = super.hashCode();
		if (isSetUnits()) {
			hashCode += prime * getUnits().hashCode();
		}
		return hashCode;
	}
	
	/**
	 * Checks whether or not a given identifier for a {@link Kind} or
	 * {@link UnitDefinition} equals a predefined unit identifier for this type.
	 * 
	 * @param unitsID the identifier to be checked.
	 * @return true if the given identifier equals the unit definition
	 *         identifier that is predefined under the Level/Version combination
	 *         for this data type.
	 */
	public boolean isPredefinedUnitsID(String unitsID) {
		if (unitsID != null) {
			String predefID = getPredefinedUnitID();
			return (predefID != null) && unitsID.equals(predefID);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithUnit#isSetUnits()
	 */
	public boolean isSetUnits() {
		return (unitsID != null) /*&& !isPredefinedUnitsID(unitsID)*/;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithUnit#isSetUnitsInstance()
	 */
	public boolean isSetUnitsInstance() {
		if (isSetUnits()) {
			if (Unit.isUnitKind(this.unitsID, getLevel(), getVersion())) {
				return true;
			}
			Model model = getModel();
			return model == null ? false : model
					.getUnitDefinition(this.unitsID) != null;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.Unit.Kind)
	 */
	public void setUnits(Kind unitKind) {
		setUnits(unitKind.toString().toLowerCase());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithUnit#setUnits(java.lang.String)
	 */
	public void setUnits(String units) {
		if ((units != null) && (units.trim().length() == 0)) {
			units = null; // If we pass the empty String or null, the value is reset.
		}
		
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
			firePropertyChange(TreeNodeChangeEvent.units, oldUnits, unitsID);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.Unit)
	 */
	@SuppressWarnings("deprecation")
	public void setUnits(Unit unit) {
		if ((unit.getExponent() != 1) || (unit.getScale() != 0)
				|| (unit.getMultiplier() != 1d) || (unit.getOffset() != 0d)) {
			StringBuilder sb = new StringBuilder();
			sb.append('_');
			sb.append(unit.getMultiplier());
			sb.append('_');
			sb.append(unit.getScale());
			sb.append('_');
			sb.append(unit.getKind().toString());
			sb.append('_');
			sb.append(unit.getExponent());
		  UnitDefinition ud = new UnitDefinition(unit.getKind().toString(),
	        getLevel(), getVersion());
	    ud.addUnit(unit);
			ud.setId(sb.toString());
			Model m = getModel();
			if (m != null) {
				m.addUnitDefinition(ud);
			}
		  setUnits(ud);
		} else {
      // must be a base unit
      setUnits(unit.getKind().toString().toLowerCase());
    }
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
