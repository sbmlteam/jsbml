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

import org.sbml.jsbml.util.StringTools;

/**
 * Represents the a globally valid parameter in the model, i.e., a variable that
 * may change during a simulation or that provides a constant value.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * @author Nicolas Rodriguez
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public class Parameter extends Symbol {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 3300762892435768291L;

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
	 * This constructor allows the creation of a global parameter based on a
	 * local parameter. It creates a new Parameter object that will have the
	 * same attributes than the local parameter. Its constant attribute will be
	 * set to true.
	 * 
	 * @param localParameter
	 */
	public Parameter(LocalParameter localParameter) {
		super(localParameter);
		setConstant(true);
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
	 * @see org.sbml.jsbml.Symbol#clone()
	 */
	public Parameter clone() {
		return new Parameter(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Symbol#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		return (o instanceof Parameter) ? super.equals(o) : false;
	}

	/**
	 * Initializes the default values of this Parameter, i.e., sets it to a
	 * constant variable with a NaN value.
	 */
	public void initDefaults() {
		setValue(Double.NaN);
		setConstant(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Symbol#readAttribute(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		if (attributeName.equals("value")) {
			this.setValue(StringTools.parseSBMLDouble(value));
			return true;
		} else if (attributeName.equals("units")) {
			this.setUnits(value);
			return true;
		} else if (attributeName.equals("constant") && getLevel() > 1) {
				this.setConstant(StringTools.parseSBMLBoolean(value));
		}
		return isAttributeRead;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Symbol#writeXMLAttributes()
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
}
