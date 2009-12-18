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
 * Represents the 'parameter' XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 */
public class Parameter extends Symbol {

	/**
	 * Creates a Parameter instance.
	 */
	public Parameter() {
		super();
		if (isSetLevel() && getLevel() < 3) {
			initDefaults();
		}
	}

	/**
	 * Creates a Parameter instance from a given Parameter.
	 * 
	 * @param p
	 */
	public Parameter(Parameter p) {
		super(p);
	}

	/**
	 * Creates a Parameter instance from an id, level and version.
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public Parameter(int level, int version) {
		super(level, version);
		if (level < 3) {
			initDefaults();
		}
	}

	/**
	 * 
	 * @param id
	 */
	public Parameter(String id, int level, int version) {
		super(id, level, version);

		if (level < 3) {
			initDefaults();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#clone()
	 */
	// @Override
	public Parameter clone() {
		return new Parameter(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof Parameter)
			return super.equals(o);
		return false;
	}

	/**
	 * 
	 * @return the constant Boolean of this Parameter. Can be null if it is not
	 *         set.
	 */
	public boolean getConstant() {
		return isConstant();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Symbol#getUnits()
	 */
	public String getUnits() {
		return super.getUnits();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Symbol#getUnitsInstance()
	 */
	public UnitDefinition getUnitsInstance() {
		return super.getUnitsInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Symbol#getValue()
	 */
	// @Override
	public double getValue() {
		return super.getValue();
	}

	/**
	 * Initialises the default values of this Parameter.
	 */
	public void initDefaults() {
		value = Double.NaN;
		constant = Boolean.TRUE;
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

		if (attributeName.equals("value")) {
			this.setValue(Double.parseDouble(value));
			return true;
		} else if (attributeName.equals("units")) {
			this.setUnits(value);
			return true;
		} else if (attributeName.equals("constant") && getLevel() > 1) {
			if (Boolean.parseBoolean(value)) {
				this.setConstant(true);
				return true;
			} else if (value.equals("false")) {
				this.setConstant(false);
				return true;
			} else {
				// TODO: should an exception be thrown?
			}
		}
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

		if (isSetValue()) {
			attributes.put("value", Double.toString(getValue()));
		}
		if (isSetUnits()) {
			attributes.put("units", getUnits());
		}
		if (isSetConstant() && getLevel() > 1) {
			attributes.put("constant", Boolean.toString(getConstant()));
		}

		return attributes;
	}

	public void unsetUnits() {
		setUnits("");
	}
}
