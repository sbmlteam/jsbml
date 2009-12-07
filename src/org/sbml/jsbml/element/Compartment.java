/*
 * $Id: Compartment.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/Compartment.java $
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
 * @author Andreas Dr&auml;ger 
 * @author rodrigue
 * @author marine
 * 
 */
public class Compartment extends Symbol {

	/**
	 * 
	 */
	private String compartmentTypeID;
	/**
	 * 
	 */
	private boolean constant;
	/**
	 * 
	 */
	private String outsideID;
	/**
	 * 
	 */
	private short spatialDimensions;

	/**
	 * 
	 */
	public Compartment() {
		super();
		this.compartmentTypeID = null;
		this.outsideID = null;
	}
	
	/**
	 * 
	 * @param compartment
	 */
	public Compartment(Compartment compartment) {
		super(compartment);
		this.compartmentTypeID = compartment.getCompartmentType();
		this.spatialDimensions = compartment.getSpatialDimensions();
		this.outsideID = compartment.getOutside();
		this.constant = compartment.getConstant();
		setValue(compartment.getSize());
	}

	public Compartment(int level, int version) {
		super(level, version);
		if (getLevel() < 3) {
			initDefaults();
		}
	}
	
	public Compartment(String id, int level, int version) {
		super(id, level, version);
		if (getLevel() < 3) {
			initDefaults();
		}
	}

	public Compartment(String id, String name, int level, int version) {
		super(id, name, level, version);
		if (getLevel() < 3) {
			initDefaults();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#clone()
	 */
	// @Override
	public Compartment clone() {
		return new Compartment(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.NamedSBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof Compartment) {
			boolean equal = super.equals(o);
			Compartment c = (Compartment) o;
			equal &= c.getConstant() == getConstant();
			equal &= c.isSetOutside() == isSetOutside();
			if (c.isSetOutside() && isSetOutside())
				equal &= c.getOutside().equals(getOutside());
			equal &= c.isSetCompartmentType() == isSetCompartmentType();
			if (c.isSetCompartmentType() && isSetCompartmentType())
				equal &= c.getCompartmentType().equals(getCompartmentType());
			equal &= c.getSize() == getSize();
			equal &= c.getSpatialDimensions() == getSpatialDimensions();
			return equal;
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public String getCompartmentType() {
		return this.compartmentTypeID;
	}

	/**
	 * 
	 * @return
	 */
	public CompartmentType getCompartmentTypeInstance() {
		if (getModel() != null){
			return getModel().getCompartmentType(this.compartmentTypeID);
		}
		return null;
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
	public String getOutside() {
		return isSetOutside() ? outsideID : "";
	}

	/**
	 * 
	 * @return
	 */
	public Compartment getOutsideInstance() {
		if (getModel() != null){
			return getModel().getCompartment(this.outsideID);
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public double getSize() {
		return getValue();
	}

	/**
	 * 
	 * @return
	 */
	public short getSpatialDimensions() {
		return spatialDimensions;
	}


	/**
	 *(For SBML Level 1) Get the volume of this Compartment
	 * 
	 * This method is identical to getSize(). In SBML Level 1, compartments are
	 * always three-dimensional constructs and only have volumes, whereas in
	 * SBML Level 2, compartments may be other than three-dimensional and
	 * therefore the 'volume' attribute is named 'size' in Level 2. LibSBML
	 * provides both getSize() and getVolume() for easier compatibility between
	 * SBML Levels.
	 * 
	 * @return
	 */
	public double getVolume() {
		return getSize();
	}

	/**
	 * 
	 */
	public void initDefaults() {
		compartmentTypeID = null;
		spatialDimensions = 3;
		constant = true;
		outsideID = null;
		value = 1.0;
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
	public boolean isSetCompartmentType() {
		return this.compartmentTypeID != null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetCompartmentTypeID() {
		return compartmentTypeID != null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetOutside() {
		return this.outsideID != null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetOutsideID() {
		return outsideID != null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetSize() {
		return isSetValue();
	}


	/**
	 * <p>
	 * (For SBML Level 1) Predicate returning true or false depending on whether
	 * this Compartment's 'volume' attribute has been set.
	 * </p>
	 * <p>
	 * Some words of explanation about the set/unset/isSet methods: SBML Levels
	 * 1 and 2 define certain attributes on some classes of objects as optional.
	 * This requires an application to be careful about the distinction between
	 * two cases: (1) a given attribute has never been set to a value, and
	 * therefore should be assumed to have the SBML-defined default value, and
	 * (2) a given attribute has been set to a value, but the value happens to
	 * be an empty string. LibSBML supports these distinctions by providing
	 * methods to set, unset, and query the status of attributes that are
	 * optional. The methods have names of the form setAttribute(...),
	 * unsetAttribute(), and isSetAttribute(), where Attribute is the the name
	 * of the optional attribute in question.
	 * </p>
	 * <p>
	 * This method is similar but not identical to isSetSize(). The latter
	 * should not be used in the context of SBML Level 1 models because this
	 * method (isSetVolume()) performs extra processing to take into account the
	 * difference in default values between SBML Levels 1 and 2.
	 * </p>
	 * 
	 * @return true if the 'volume' attribute ('size' in L2) of this Compartment
	 *         has been set, false otherwise.
	 * @see isSetSize
	 * @note In SBML Level 1, a compartment's volume has a default value ( 1.0)
	 *       and therefore this method will always return true. In Level 2, a
	 *       compartment's size (the equivalent of SBML Level 1's 'volume') is
	 *       optional and has no default value, and therefore may or may not be
	 *       set.
	 */
	public boolean isSetVolume() {
		return isSetSize();
	}

	/**
	 * 
	 * @param compartmentType
	 */
	public void setCompartmentType(CompartmentType compartmentType) {
		this.compartmentTypeID = compartmentType != null ? compartmentType.getId() : null;
		stateChanged();
	}
	
	/**
	 * 
	 * @param compartmentTypeID
	 */
	public void setCompartmentType(String compartmentTypeID) {
		this.compartmentTypeID = compartmentTypeID;
		stateChanged();
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
	 * @param outside
	 */
	public void setOutside(Compartment outside) {
		this.outsideID = outside != null ? outside.getId() : null;
		stateChanged();
	}
	
	/**
	 * 
	 * @param outside
	 */
	public void setOutside(String outside) {
		if (outside != null && outside.trim().length() == 0) {
			this.outsideID = null;
		} else {
			this.outsideID = outside;
		}
		
		stateChanged();
	}

	/**
	 * 
	 * @param size
	 */
	public void setSize(double size) {
		setValue(size);
		stateChanged();
	}

	/**
	 * 
	 * @param spatialDimensions
	 */
	public void setSpatialDimensions(short spatialDimensions) {
		if (spatialDimensions >= 0 && spatialDimensions <= 3)
			this.spatialDimensions = spatialDimensions;
		else
			throw new IllegalArgumentException(
					"Spatial dimensions must be between [0, 3].");
	}


	/**
	 * <p>
	 * Sets the 'volume' attribute (or 'size' in SBML Level 2) of this
	 * Compartment.
	 * </p>
	 * <p>
	 * Some words of explanation about the set/unset/isSet methods: SBML Levels
	 * 1 and 2 define certain attributes on some classes of objects as optional.
	 * This requires an application to be careful about the distinction between
	 * two cases: (1) a given attribute has never been set to a value, and
	 * therefore should be assumed to have the SBML-defined default value, and
	 * (2) a given attribute has been set to a value, but the value happens to
	 * be an empty string. LibSBML supports these distinctions by providing
	 * methods to set, unset, and query the status of attributes that are
	 * optional. The methods have names of the form setAttribute(...),
	 * unsetAttribute(), and isSetAttribute(), where Attribute is the the name
	 * of the optional attribute in question.
	 * </p>
	 * <p>
	 * This method is identical to setVolume() and is provided for compatibility
	 * between SBML Level 1 and Level 2.
	 * </p>
	 * 
	 * @param value
	 *            a double representing the volume of this compartment instance
	 *            in whatever units are in effect for the compartment.
	 */
	public void setVolume(double value) {
		setSize(value);
	}

	/**
	 * <p>
	 * Unsets the value of the 'size' attribute of this Compartment.
	 * </p>
	 * <p>
	 * Some words of explanation about the set/unset/isSet methods: SBML Levels
	 * 1 and 2 define certain attributes on some classes of objects as optional.
	 * This requires an application to be careful about the distinction between
	 * two cases: (1) a given attribute has never been set to a value, and
	 * therefore should be assumed to have the SBML-defined default value, and
	 * (2) a given attribute has been set to a value, but the value happens to
	 * be an empty string. LibSBML supports these distinctions by providing
	 * methods to set, unset, and query the status of attributes that are
	 * optional. The methods have names of the form setAttribute(...),
	 * unsetAttribute(), and isSetAttribute(), where Attribute is the the name
	 * of the optional attribute in question.
	 * </p>
	 */
	public void unsetSize() {
		unsetValue();
	}

	/**
	 * <p>
	 * (For SBML Level 1) Unsets the value of the 'volume' attribute of this
	 * Compartment.
	 * </p>
	 * <p>
	 * Some words of explanation about the set/unset/isSet methods: SBML Levels
	 * 1 and 2 define certain attributes on some classes of objects as optional.
	 * This requires an application to be careful about the distinction between
	 * two cases: (1) a given attribute has never been set to a value, and
	 * therefore should be assumed to have the SBML-defined default value, and
	 * (2) a given attribute has been set to a value, but the value happens to
	 * be an empty string. LibSBML supports these distinctions by providing
	 * methods to set, unset, and query the status of attributes that are
	 * optional. The methods have names of the form setAttribute(...),
	 * unsetAttribute(), and isSetAttribute(), where Attribute is the the name
	 * of the optional attribute in question.
	 * </p>
	 * <p>
	 * In SBML Level 1, a Compartment volume has a default value (1.0) and
	 * therefore should always be set. In Level 2, 'size' is optional with no
	 * default value and as such may or may not be set.
	 * </p>
	 */
	public void unsetVolume() {
		unsetSize();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Rule#isSpeciesConcentration()
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		if (!isAttributeRead){
			if (attributeName.equals("spatialDimensions")){
				this.setSpatialDimensions(Short.parseShort(value));
				return true;
			}
			else if (attributeName.equals("units")){
				this.setUnits(value);
				return true;
			}
			else if (attributeName.equals("size")){
				this.setSize(Double.parseDouble(value));
				return true;
			}
			else if (attributeName.equals("compartmentType")){
				this.setCompartmentType(value);
				return true;
			}
			else if (attributeName.equals("outside")){
				this.setOutside(value);
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
		}
		return isAttributeRead;
	}
	
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		
		attributes.put("spatialDimension", Short.toString(getSpatialDimensions()));

		if (isSetSize()){
			attributes.put("size", Double.toString(getSize()));
		}
		if (isSetCompartmentTypeID()){
			attributes.put("compartmentType", getCompartmentType());
		}
		if (isSetOutsideID()){
			attributes.put("outside", outsideID);
		}
		if (constant){
			attributes.put("constant", "true");
		}
		else {
			attributes.put("constant", "false");
		}
		if (isSetUnitsID()){
			attributes.put("units", getUnits());
		}
		return attributes;
	}

	public void unsetCompartmentType() {
		compartmentTypeID = null;
	}

	public void setSpatialDimensions(int i) {
		setSpatialDimensions((short) spatialDimensions);
	}
}
