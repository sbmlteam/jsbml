/*
 * $Id$
 * $URL$
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

import java.util.HashMap;

/**
 * Represents the speciesReference XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * @author Nicolas Rodriguez
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public class SpeciesReference extends SimpleSpeciesReference implements
		Variable {

	/**
	 * Represents the 'constant' XML attribute of this SpeciesReference.
	 */
	private Boolean constant;
	/**
	 * Represents the 'denominator' XML attribute of this SpeciesReference.
	 */
	private Integer denominator;
	/**
	 * 
	 */
	private boolean isSetConstant;
	/**
	 * Boolean value to know if the SpeciesReference denominator has been set.
	 */
	private boolean isSetDenominator;
	/**
	 * 
	 */
	private boolean isSetStoichiometry;
	/**
	 * Represents the 'stoichiometry' XML attribute of this SpeciesReference.
	 */
	private Double stoichiometry;
	/**
	 * Contains the MathML expression for the stoichiometry of this
	 * SpeciesReference.
	 */
	private StoichiometryMath stoichiometryMath;

	/**
	 * Creates a SpeciesReference instance. By default, if the level is superior
	 * or equal to 3, the constant, stoichiometryMath and stoichiometry are
	 * null.
	 * 
	 * @param spec
	 */
	public SpeciesReference() {
		super();
		initDefaults();
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public SpeciesReference(int level, int version) {
		super(level, version);
		initDefaults();
	}

	/**
	 * Creates a SpeciesReference instance from a Species. By default, if the
	 * level is superior or equal to 3, the constant, stoichiometryMath and
	 * stoichiometry are null.
	 * 
	 * @param speciesReference
	 */
	public SpeciesReference(Species species) {
		super(species);
		initDefaults();
	}

	/**
	 * Creates a SpeciesReference instance from a given SpeciesReference.
	 * 
	 * @param speciesReference
	 */
	public SpeciesReference(SpeciesReference speciesReference) {
		super(speciesReference);
		if (speciesReference.isSetStoichiometryMath()) {
			setStoichiometryMath(speciesReference.getStoichiometryMath()
					.clone());
		}
		if (speciesReference.isSetStoichiometry()) {
			setStoichiometry(new Double(speciesReference.getStoichiometry()));
		}
		if (speciesReference.isSetConstant()) {
			setConstant(new Boolean(speciesReference.getConstant()));
		}
		if (speciesReference.isSetDenominator) {
			setDenominator(new Integer(speciesReference.getDenominator()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#clone()
	 */
	@Override
	public SpeciesReference clone() {
		return new SpeciesReference(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof SpeciesReference) {
			SpeciesReference sr = (SpeciesReference) o;
			boolean equal = super.equals(o);
			if ((sr.isSetStoichiometryMath() && !isSetStoichiometryMath())
					|| (!sr.isSetStoichiometryMath() && isSetStoichiometryMath())) {
				return false;
			} else if (sr.isSetStoichiometryMath() && isSetStoichiometryMath()) {
				equal &= sr.getStoichiometryMath().equals(stoichiometryMath);
			}
			if ((sr.isSetStoichiometry() && !isSetStoichiometry())
					|| (!sr.isSetStoichiometry() && isSetStoichiometry())) {
				return false;
			} else if (sr.isSetStoichiometry() && isSetStoichiometry()) {
				equal &= sr.getStoichiometry() == stoichiometry;
			}
			if ((sr.isSetConstant() && !isSetConstant())
					|| (!sr.isSetConstant() && isSetConstant())) {
				return false;
			} else if (sr.isSetConstant() && isSetConstant()) {
				equal &= sr.getConstant() == constant;
			}
			if ((sr.isSetDenominator() && !isSetDenominator())
					|| (!sr.isSetDenominator() && isSetDenominator())) {
				return false;
			} else if (sr.isSetDenominator() && isSetDenominator()) {
				equal &= sr.getDenominator() == denominator;
			}
			return equal;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.State#getConstant()
	 */
	public boolean getConstant() {
		return isSetConstant() ? constant : false;
	}

	/**
	 * 
	 * @return the denominator value if it is set, 1 otherwise
	 */
	public int getDenominator() {
		return isSetDenominator ? denominator : 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Quantity#getDerivedUnitInstance()
	 */
	public UnitDefinition getDerivedUnitDefinition() {
		if (isSetStoichiometryMath()) {
			return stoichiometryMath.getDerivedUnitDefinition();
		}

		UnitDefinition ud = new UnitDefinition(getLevel(), getVersion());
		ud.addUnit(new Unit(Unit.Kind.DIMENSIONLESS, getLevel(), getVersion()));
		return ud;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Quantity#getDerivedUnit()
	 */
	public String getDerivedUnits() {
		if (isSetStoichiometryMath()) {
			return stoichiometryMath.getDerivedUnits();
		}
		return Unit.Kind.DIMENSIONLESS.toString();
	}

	/**
	 * 
	 * @return the stoichiometry value of this SpeciesReference if it is set, 0
	 *         otherwise.
	 */
	public double getStoichiometry() {
		return isSetStoichiometry() ? stoichiometry : 0;
	}

	/**
	 * 
	 * @return the stoichiometryMath of this SpeciesReference. Can be null if
	 *         the stoichiometryMath is not set.
	 */
	public StoichiometryMath getStoichiometryMath() {
		return stoichiometryMath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Quantity#getValue()
	 */
	public double getValue() {
		return getStoichiometry();
	}

	/**
	 * Initialises the default values of this SpeciesReference.
	 */
	public void initDefaults() {
	  // See http://sbml.org/Community/Wiki/SBML_Level_3_Core/Reaction_changes/Changes_to_stoichiometry
		if (getLevel() > 2) {
			setConstant(true);
			setStoichiometry(1d);
			denominator = Integer.valueOf(1);
		} else {
			isSetConstant = false;
			isSetDenominator = false;
			isSetStoichiometry = false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.State#isConstant()
	 */
	public boolean isConstant() {
		return isSetConstant() ? constant : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.State#isSetConstant()
	 */
	public boolean isSetConstant() {
		return isSetConstant;
	}

	/**
	 * 
	 * @return true if the denominator is not null.
	 */
	public boolean isSetDenominator() {
		return this.denominator != null;
	}

	/**
	 * 
	 * @return true if the stoichiometry of this SpeciesReference is not null.
	 */
	public boolean isSetStoichiometry() {
		return isSetStoichiometry;
	}

	/**
	 * 
	 * @return true if the stoichiometryMath of this SpeciesReference is not
	 *         null.
	 */
	public boolean isSetStoichiometryMath() {
		return stoichiometryMath != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Quantity#isSetValue()
	 */
	public boolean isSetValue() {
		return isSetStoichiometry();
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

		if (!isAttributeRead) {
			if (attributeName.equals("stochiometry")) {
				this.setStoichiometry(Double.parseDouble(value));
				return true;
			} else if (attributeName.equals("constant")) {
				if (value.equals("true")) {
					setConstant(true);
					return true;
				} else if (value.equals("false")) {
					setConstant(false);
					return true;
				}
			} else if (attributeName.equals("denominator")) {
				setDenominator(denominator);
				return true;
			}
		}

		return isAttributeRead;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.State#setConstant(boolean)
	 */
	public void setConstant(boolean constant) {
		this.constant = Boolean.valueOf(constant);
		isSetConstant = true;
	}

	/**
	 * Sets the denominator of this SpeciesReference.
	 * 
	 * @param denominator
	 */
	public void setDenominator(int denominator) {
		this.denominator = denominator;
		isSetDenominator = true;
		stateChanged();
	}

	/**
	 * Sets the stoichiometry of this SpeciesReference.
	 * 
	 * @param stoichiometry
	 */
	public void setStoichiometry(double stoichiometry) {
		this.stoichiometry = stoichiometry;
		if (isSetStoichiometryMath()) {
			stoichiometryMath = null;
		}
		if (Double.isNaN(stoichiometry)) {
			isSetStoichiometry = false;
		} else {
			isSetStoichiometry = true;
		}

		stateChanged();
	}

	/**
	 * Sets the stoichiometryMath of this SpeciesReference.
	 * 
	 * @param math
	 */
	public void setStoichiometryMath(StoichiometryMath math) {
		this.stoichiometryMath = math;
		setThisAsParentSBMLObject(this.stoichiometryMath);
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Quantity#setValue(double)
	 */
	public void setValue(double value) {
		setStoichiometry(value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Variable#unsetConstant()
	 */
	public void unsetConstant() {
		this.constant = null;
	}

	/**
	 * Unsets the stoichiometry property of this element.
	 */
	public void unsetStoichiometry() {
		this.stoichiometry = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Quantity#unsetValue()
	 */
	public void unsetValue() {
		unsetStoichiometry();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();

		if (isSetStoichiometry()) {
			attributes
					.put("stoichiometry", Double.toString(getStoichiometry()));
		}
		if (isSetConstant()) {
			attributes.put("constant", Boolean.toString(getConstant()));
		}
		if (isSetDenominator() && level == 1) {
			attributes.put("denominator", Integer.toString(getDenominator()));
		}

		return attributes;
	}
}
