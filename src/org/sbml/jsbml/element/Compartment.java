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
 * Represents the compartment XML element of a SBML file.
 * @author Andreas Dr&auml;ger 
 * @author rodrigue
 * @author marine
 * 
 */
public class Compartment extends Symbol {

	/**
	 * Represents the compartmentType XML attribute of a compartment element. It matches a compartmentType id in the model.
	 */
	@Deprecated
	private String compartmentTypeID;
	/**
	 * Represents the outside XML attribute of a compartment element. It matches a compartment id in the model instance.
	 */
	@Deprecated
	private String outsideID;
	/**
	 * Represents the spatialDimensions XML attribute of a compartment element.
	 */
	private Short spatialDimensions;

	/**
	 * Creates a Compartment instance. By default, if the level is set and is superior or equal to 3, sets the compartmentType, outsideID and spatialDimension to null.
	 */
	public Compartment() {
		super();
		this.compartmentTypeID = null;
		this.outsideID = null;
		this.spatialDimensions = null;
		if (isSetLevel() && getLevel() < 3) {
			initDefaults();
		}
	}
	
	/**
	 * Creates a Compartment instance from a given compartment. 
	 * @param compartment
	 */
	public Compartment(Compartment compartment) {
		super(compartment);
		if (compartment.isSetCompartmentType()){
			this.compartmentTypeID = new String(compartment.getCompartmentType());
		}
		if (compartment.isSetSpatialDimensions()){
			this.spatialDimensions = new Short(compartment.getSpatialDimensions());
		}
		if (compartment.isSetOutside()){
			this.outsideID = new String(compartment.getOutside());
		}
		if (compartment.isSetSize()){
			setValue(compartment.getSize());
		}
	}

	/**
	 * Creates a Compartment instance from a level and version. By default, if the level is set and is superior or equal to 3, sets the compartmentType, outsideID and spatialDimension to null.
	 * @param level
	 * @param version
	 */
	public Compartment(int level, int version) {
		super(level, version);
		this.compartmentTypeID = null;
		this.outsideID = null;
		this.spatialDimensions = null;
		if (isSetLevel() && getLevel() < 3) {
			initDefaults();
		}
	}
	
	/**
	 * Creates a Compartment instance from an id, level and version. By default, sets the compartmentType, outsideID and spatialDimension to null.
	 * @param id
	 * @param level
	 * @param version
	 */
	public Compartment(String id, int level, int version) {
		super(id, level, version);
		this.compartmentTypeID = null;
		this.outsideID = null;
		this.spatialDimensions = null;
		if (isSetLevel() && getLevel() < 3) {
			initDefaults();
		}
	}

	/**
	 * Creates a Compartment instance from an id, name, level and version. By default, if the level is set and is superior or equal to 3, sets the compartmentType, outsideID and spatialDimension to null.
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public Compartment(String id, String name, int level, int version) {
		super(id, name, level, version);
		this.compartmentTypeID = null;
		this.outsideID = null;
		this.spatialDimensions = null;
		if (isSetLevel() && getLevel() < 3) {
			initDefaults();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#clone()
	 */
	// @Override
	public Compartment clone() {
		return new Compartment(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof Compartment) {
			boolean equal = super.equals(o);
			Compartment c = (Compartment) o;
			equal &= c.getConstant() == getConstant();
			equal &= c.isSetOutside() == isSetOutside();
			if (c.isSetOutside() && isSetOutside()){
				equal &= c.getOutside().equals(getOutside());
			}
			equal &= c.isSetCompartmentType() == isSetCompartmentType();
			if (c.isSetCompartmentType() && isSetCompartmentType()){
				equal &= c.getCompartmentType().equals(getCompartmentType());
			}
			equal &= c.getSize() == getSize();
			equal &= c.getSpatialDimensions() == getSpatialDimensions();
			return equal;
		}
		return false;
	}

	/**
	 * 
	 * @return the compartmentType id of this compartment. Return an empty String if it is not set.
	 */
	public String getCompartmentType() {
		return isSetCompartmentType() ? this.compartmentTypeID : "";
	}

	/**
	 * 
	 * @return the compartmentType instance in Model for this compartment compartmentTypeID. Return null
	 * if there is no compartmentType instance which matches this id.
	 */
	public CompartmentType getCompartmentTypeInstance() {
		if (getModel() != null){
			return getModel().getCompartmentType(this.compartmentTypeID);
		}
		return null;
	}

	/**
	 * 
	 * @return the outsideID of this compartment. Return "" if it is not set. 
	 */
	public String getOutside() {
		return isSetOutside() ? outsideID : "";
	}

	/**
	 * 
	 * @return the compartment instance which matches the outsideID in Model. Return null if no compartment instance matches outsideID.
	 */
	public Compartment getOutsideInstance() {
		if (getModel() != null){
			return getModel().getCompartment(this.outsideID);
		}
		return null;
	}

	/**
	 * 
	 * @return the size of this compartment.
	 */
	public double getSize() {
		return getValue();
	}

	/**
	 * 
	 * @return the spatialDimensions of this compartment.
	 */
	public short getSpatialDimensions() {
		return isSetSpatialDimensions() ? spatialDimensions : 0;
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
	 * initialises the default values.
	 */
	public void initDefaults() {
		compartmentTypeID = null;
		spatialDimensions = 3;
		setConstant(new Boolean(true));
		outsideID = null;
		value = new Double(1.0);
	}

	/**
	 * 
	 * @return true if the compartmentType instance which matches the compartmentTypeID of this compartment is not null.
	 */
	public boolean isSetCompartmentTypeInstance() {
		if (getModel() == null){
			return false;
		}
		return getModel().getCompartmentType(this.compartmentTypeID) != null;
	}
	
	/**
	 * 
	 * @return true if the compartmentID of this compartment is not null.
	 */
	public boolean isSetCompartmentType() {
		return compartmentTypeID != null;
	}

	/**
	 * 
	 * @return true if the compartment instance which matches the outsideID of this compartment is not null.
	 */
	public boolean isSetOutsideInstance() {
		if (getModel() == null){
			return false;
		}
		return getModel().getCompartment(this.outsideID) != null;
	}
	
	/**
	 * 
	 * @return true if the outsideID of this compartment is not null.
	 */
	public boolean isSetOutside() {
		return outsideID != null;
	}

	/**
	 * 
	 * @return true if the size of this compartment is not null.
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
	 * @return true if the spatialDimensions of this compartment is not null.
	 */
	public boolean isSetSpatialDimensions(){
		return this.spatialDimensions != null;
	}

	/**
	 * Sets the compartmentTypeID value of this compartment to the id of 'compartmentType'
	 * @param compartmentType
	 */
	public void setCompartmentType(CompartmentType compartmentType) {
		this.compartmentTypeID = compartmentType != null ? compartmentType.getId() : null;
		stateChanged();
	}
	
	/**
	 * Sets the compartmentTypeID of this compartment to 'compartmentTypeID'
	 * @param compartmentTypeID
	 */
	public void setCompartmentType(String compartmentTypeID) {
		this.compartmentTypeID = compartmentTypeID;
		stateChanged();
	}

	/**
	 * Sets the outsideID of this compartment to the id of 'outside'.
	 * @param outside
	 */
	public void setOutside(Compartment outside) {
		this.outsideID = outside != null ? outside.getId() : null;
		stateChanged();
	}
	
	/**
	 * Sets the outsideID of this compartment to 'outsideID'.
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
	 * Sets the size of this compartment to 'size'.
	 * @param size
	 */
	public void setSize(double size) {
		setValue(size);
		stateChanged();
	}

	/**
	 * Sets the spatialDimensions of this compartment to 'spatialDimensiosn'.
	 * @param spatialDimensions
	 */
	public void setSpatialDimensions(short spatialDimensions) {
		if (spatialDimensions >= 0 && spatialDimensions <= 3){
			this.spatialDimensions = spatialDimensions;
		}
		else{
			throw new IllegalArgumentException(
			"Spatial dimensions must be between [0, 3].");
		}
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
	
	/**
	 * Sets the outsideID of this compartment to null.
	 */
	public void unsetOutside(){
		this.outsideID = null;
	}
	
	/**
	 * sets the spatialDimensions of this compartment to null.
	 */
	public void unsetSpatialDimensions(){
		this.spatialDimensions = null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
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
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		
		attributes.put("spatialDimension", Short.toString(getSpatialDimensions()));

		if (isSetSize()){
			attributes.put("size", Double.toString(getSize()));
		}
		if (isSetCompartmentType()){
			attributes.put("compartmentType", getCompartmentType());
		}
		if (isSetOutside()){
			attributes.put("outside", outsideID);
		}
		if (isSetConstant()){
			attributes.put("constant", getConstant().toString());
		}
		if (isSetUnitsID()){
			attributes.put("units", getUnits());
		}
		return attributes;
	}

	/**
	 * Sets the compartmentTypeID of this compartment to null.
	 */
	public void unsetCompartmentType() {
		compartmentTypeID = null;
	}

	/**
	 * Sets the spatialDimensions of this compartment to 'i'.
	 */
	public void setSpatialDimensions(int i) {
		setSpatialDimensions((short) i);
	}
}
