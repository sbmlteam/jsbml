/*
 * $Id: Parameter.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/Parameter.java $
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

package org.sbml.jsbml.element;

import java.util.HashMap;

/**
 * Represents the 'parameter' XML element of a SBML file.
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * @author marine
 */
public class Parameter extends Symbol {

	/**
	 * Creates a Parameter instance.
	 */
	public Parameter() {
		super();
		if (isSetLevel() && getLevel() < 3){
			initDefaults();
		}
	}
	
	/**
	 * Creates a Parameter instance from a given Parameter.
	 * @param p
	 */
	public Parameter(Parameter p) {
		super(p);
	}

	/**
	 * Creates a Parameter instance from an id, level and version.
	 * @param id
	 * @param level
	 * @param version
	 */
	public Parameter(String id, int level, int version) {
		super(id, level, version);
		if (isSetLevel() && getLevel() < 3){
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
	 * @return the constant Boolean of this Parameter. Can be null if it is not set.
	 */
	public boolean getConstant() {
		return isConstant();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.Symbol#getUnits()
	 */
	public String getUnits() {
		return super.getUnits();
	}

	/*
	 * (non-Javadoc)
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
		setValue(null);
		setConstant(new Boolean(true));
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.Symbol#isSetUnits()
	 */
	public boolean isSetUnitsInstance() {
		return super.isSetUnitsInstance();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.Symbol#isSetUnits()
	 */
	public boolean isSetUnits() {
		return super.isSetUnits();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Symbol#isSetValue()
	 */
	// @Override
	public boolean isSetValue() {
		return super.isSetValue();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.Symbol#setUnits(org.sbml.jsbml.Unit)
	 */
	public void setUnits(Unit unit) {
		super.setUnits(unit);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.Symbol#setUnits(org.sbml.jsbml.Unit.Kind)
	 */
	public void setUnits(Unit.Kind unitKind) {
		super.setUnits(unitKind);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.Symbol#setUnits(org.sbml.jsbml.UnitDefinition)
	 */
	public void setUnits(UnitDefinition units) {
		super.setUnits(units);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Symbol#setValue(double)
	 */
	// @Override
	public void setValue(Double value) {
		super.setValue(value);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		if (attributeName.equals("value")){
			this.setValue(Double.parseDouble(value));
			return true;
		}
		else if (attributeName.equals("units")){
			this.setUnits(value);
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
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		
		if (isSetValue()){
			attributes.put("value", Double.toString(getValue()));
		}
		if (isSetUnits()){
			attributes.put("units", getUnits());
		}
		if (isSetConstant()){
			attributes.put("constant", Boolean.toString(getConstant()));
		}
		
		return attributes;
	}
}
