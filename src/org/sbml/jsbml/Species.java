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
 * Represents the species XML element of a SBML file.
 * @author Andreas Dr&auml;ger
 * @author marine 
 */
public class Species extends Symbol {

	/**
	 * Represents the 'conversionFactor' attribute of a Species element.
	 */
	private String conversionFactorID;
	
	/**
	 * Represents the 'spatialSizeUnits' attribute of a Species element.
	 */
	@Deprecated
	private String spatialSizeUnitsID;
	
	/**
	 * Represents the 'speciesType' attribute of a Species element.
	 */
	@Deprecated
	private String speciesTypeID;
	/**
	 * Represents the 'compartment' attribute of a Species element.
	 */
	private String compartmentID;
	/**
	 * true means initial amount is set. false means that an initial
	 * concentration is set.
	 */
	private boolean amountOrConcentration;
	/**
	 * Represents the 'hasOnlySubstanceUnits' attribute of a Species element.
	 */
	private Boolean hasOnlySubstanceUnits;
	/**
	 * Represents the 'boundaryCondition' attribute of a Species element.
	 */
	private Boolean boundaryCondition;
	/**
	 * Represents the 'charge' attribute of a Species element.
	 */
	@Deprecated
	private Integer charge;

	/**
	 * Boolean value to test if the charge has been set.
	 */
	private boolean isSetCharge;

	/**
	 * Creates a Species instance. By default, the charge, compartmentID, speciesTypeID, conversionFactorID,
	 * hasOnlySubstanceUnits, boundaryCondition are null.
	 */
	public Species() {
		super();
		this.charge = null;
		this.compartmentID = null;
		this.speciesTypeID = null;
		this.conversionFactorID = null;
		this.hasOnlySubstanceUnits = null;
		this.boundaryCondition = null;
		this.spatialSizeUnitsID = null;
		
		if (isSetLevel() && getLevel() < 3) {
			initDefaults();
		}
	}

	
	/**
	 * Creates a Species instance from a Species.
	 * @param species
	 */
	public Species(Species species) {
		super(species);
		if (species.isSetBoundaryCondition()){
			this.boundaryCondition = species.getBoundaryCondition();
		}
		else {
			this.boundaryCondition = null;
		}
		if (species.isSetCharge()){
			this.charge = Integer.valueOf(species.getCharge());
		}
		else {
			this.charge = null;
		}
		if (species.isSetCompartment()){
			this.compartmentID = new String(species.getCompartment());
		}
		else {
			this.compartmentID = null;
		}
		if (species.isSetSubstanceUnits()){
			setSubstanceUnits(new String(species.getCompartment()));
		}
		if (species.isSetHasOnlySubstanceUnits()){
			this.hasOnlySubstanceUnits = new Boolean(species.getHasOnlySubstanceUnits());
		}
		else {
			this.hasOnlySubstanceUnits = null;
		}
		if (species.isSetInitialAmount()){
			setInitialAmount(new Double(species.getInitialAmount()));
		}
		else if (species.isSetInitialConcentration()){
			setInitialConcentration(new Double(species.getInitialConcentration()));
		}
		else {
			setValue(Double.NaN);
		}
		if (species.isSetConstant()){
			setConstant(new Boolean(species.getConstant()));
		}
		if (species.isSetSpatialSizeUnits()){
			this.spatialSizeUnitsID = new String(species.getSpatialSizeUnits());
		}
		else {
			this.spatialSizeUnitsID = null;
		}
	}

	/**
	 * Creates a Species instance from a level and verison. By default, the charge, compartmentID, speciesTypeID, conversionFactorID,
	 * hasOnlySubstanceUnits, boundaryCondition are null.
	 * @param level
	 * @param version
	 */
	public Species(int level, int version) {
		super(level, version);
		this.charge = null;
		this.compartmentID = null;
		this.speciesTypeID = null;
		this.conversionFactorID = null;
		this.hasOnlySubstanceUnits = null;
		this.boundaryCondition = null;
		this.spatialSizeUnitsID = null;
		if (isSetLevel() && getLevel() < 3) {
			initDefaults();
		}
	}
	/**
	 * Creates a Species instance from a level and verison. By default, the charge, compartmentID, speciesTypeID, conversionFactorID,
	 * hasOnlySubstanceUnits, boundaryCondition are null. 
	 * @param id
	 * @param level
	 * @param version
	 */
	public Species(String id, int level, int version) {
		super(id, level, version);
		this.charge = null;
		this.compartmentID = null;
		this.speciesTypeID = null;
		this.conversionFactorID = null;
		this.hasOnlySubstanceUnits = null;
		this.boundaryCondition = null;
		this.spatialSizeUnitsID = null;
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
	public Species clone() {
		return new Species(this);
	}
	
	/**
	 * 
	 * @return true if the boundaryCondition of this Species is not null.
	 */
	public boolean isSetBoundaryCondition(){
		return this.boundaryCondition != null;
	}
	
	/**
	 * 
	 * @return true if the spatialSizeUnits of this Species is not null.
	 */
	@Deprecated
	public boolean isSetSpatialSizeUnits(){
		return this.spatialSizeUnitsID != null;
	}
	
	/**
	 * 
	 * @return true if the hasOnlySubstanceUnits of this Species is not null.
	 */
	public boolean isSetHasOnlySubstanceUnits(){
		return this.hasOnlySubstanceUnits != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof Species) {
			boolean equal = super.equals(o);
			Species s = (Species) o;
			equal &= s.getBoundaryCondition() == getBoundaryCondition();
			equal &= s.getHasOnlySubstanceUnits() == getHasOnlySubstanceUnits();
			equal &= s.getCharge() == getCharge();
			equal &= s.isSetCompartment() == isSetCompartment();
			equal &= s.isSetSpeciesType() == isSetSpeciesType();

			if (equal && isSetSpeciesType()){
				equal &= s.getSpeciesType().equals(getSpeciesType());
			}
			if (equal && isSetCompartment()){
				equal &= s.getCompartment().equals(
						getCompartment());
			}
			equal &= s.isSetInitialAmount() == isSetInitialAmount();
			if (equal && isSetInitialAmount()){
				equal &= s.getInitialAmount() == getInitialAmount();
			}
			else if (equal
					&& isSetInitialConcentration()){
				equal &= s.getInitialConcentration() == getInitialConcentration();
			}
			equal &= s.isSetSpatialSizeUnits() == isSetSpatialSizeUnits();

			if (equal && isSetSpatialSizeUnits()){
				equal &= s.getSpatialSizeUnits().equals(getSpatialSizeUnits());
			}
			return equal;
		}
		return false;
	}

	/**
	 * 
	 * @return the boundaryCondition Boolean of this Species.
	 */
	public boolean getBoundaryCondition() {
		return isSetBoundaryCondition() ? boundaryCondition : false;
	}
	
	/**
	 * 
	 * @return the spatialSizeUnits of this Species.
	 */
	@Deprecated
	public String getSpatialSizeUnits() {
		return isSetSpatialSizeUnits() ? spatialSizeUnitsID : "";
	}

	/**
	 * 
	 * @return the charge value of this Species if it is set, 0 otherwise.
	 */
	@Deprecated
	public int getCharge() {
		return isSetCharge() ? this.charge : 0;
	}

	/**
	 * 
	 * @return the compartmentID of this Species. The empty String if it is not set.
	 */
	public String getCompartment() {
		return isSetCompartment() ? compartmentID : "";
	}

	/**
	 * 
	 * @return The Compartment instance which as the compartmentID of this Species as id. Null if it doesn't exist.
	 */
	public Compartment getCompartmentInstance() {
		if (getModel() == null){
			return null;
		}
		return getModel().getCompartment(this.compartmentID);
	}
	
	/**
	 * 
	 * @return The UnitDefinition instance which as the spatialSizeUnitsID of this Species as id. Null if it doesn't exist.
	 */
	public UnitDefinition getSpatialSizeUnitsInstance() {
		if (getModel() == null){
			return null;
		}
		return getModel().getUnitDefinition(this.spatialSizeUnitsID);
	}

	/**
	 * 
	 * @return the hasOnlySubstanceUnits Boolean of this Species.
	 */
	public boolean getHasOnlySubstanceUnits() {
		return isSetHasOnlySubstanceUnits() ? this.hasOnlySubstanceUnits : false;
	}

	/**
	 * 
	 * @return the initialAmount of this Species if it has been set, o otherwise.
	 */
	public double getInitialAmount() {
		if (isSetInitialAmount()) {
			return getValue();
		}
		return 0;
	}

	/**
	 * 
	 * @return the initialConcentration of this Species if it has been set, o otherwise.
	 */
	public double getInitialConcentration() {
		if (isSetInitialConcentration()) {
			return getValue();
		}
		
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#getParentSBMLObject()
	 */
	// @Override
	public Model getParentSBMLObject() {
		return (Model) parentSBMLObject;
	}

	/**
	 * 
	 * @return the speciesTypeID of this Species. The empty String if it is not set.
	 */
	@Deprecated
	public String getSpeciesType() {
		return isSetSpeciesType() ? this.speciesTypeID : "";
	}

	/**
	 * 
	 * @return the SpeciesType instance which has the speciesTypeID of this Species as id. Null if it doesn't exist.
	 */
	@Deprecated
	public SpeciesType getSpeciesTypeInstance() {
		if (getModel() == null){
			return null;
		}
		return getModel().getSpeciesType(this.speciesTypeID);
	}

	/**
	 * 
	 * @return the substanceUnitsID of this Species.
	 */
	public String getSubstanceUnits() {
		return getUnits();
	}

	/**
	 * 
	 * @return The UnitsDefinition instance which has the substanceUnistID of this Species as id. 
	 */
	public UnitDefinition getSubstanceUnitsInstance() {
		return getUnitsInstance();
	}

	/**
	 * Initialises the default values of this Species.
	 */
	public void initDefaults() {
		charge = Integer.valueOf(0);
		amountOrConcentration = new Boolean(true);
		hasOnlySubstanceUnits = new Boolean(false);
		boundaryCondition = new Boolean(false);
		setConstant(new Boolean(false));
	}

	/**
	 * 
	 * @return the value of the boundaryCondition Boolean if it is set, false otherwise.
	 */
	public boolean isBoundaryCondition() {
		return isSetBoundaryCondition() ? boundaryCondition : false;
	}

	/**
	 * 
	 * @return the value of the hasOnlySubstanceUnits Boolean if it is set, false otherwise.
	 */
	public boolean isHasOnlySubstanceUnits() {
		return isSetHasOnlySubstanceUnits() ? hasOnlySubstanceUnits : false;
	}

	/**
	 * 
	 * @return true if the charge of this Species if not null.
	 */
	public boolean isSetCharge() {
		return isSetCharge;
	}

	/**
	 * 
	 * @return true if the Compartment instance which has the compartmentID of this Species as id is not null.
	 */
	public boolean isSetCompartmentInstance() {
		if (getModel() == null){
			return false;
		}
		return getModel().getCompartment(this.compartmentID) != null;
	}
	
	/**
	 * 
	 * @return true if the compartmentID of this Species is not null.
	 */
	public boolean isSetCompartment() {
		return compartmentID != null;
	}

	/**
	 * 
	 * @return Returns true if an initial amount has been set for this species.
	 */
	public boolean isSetInitialAmount() {
		return amountOrConcentration && isSetValue();
	}

	/**
	 * 
	 * @return Returns true if an initial concentration has been set for this
	 *         species.
	 */
	public boolean isSetInitialConcentration() {
		return !amountOrConcentration && isSetValue();
	}

	/**
	 * 
	 * @return true if the SpeciesType instance which has the speciesTypeID of this Species as id is not null.
	 */
	@Deprecated
	public boolean isSetSpeciesTypeInstance() {
		if (getModel() == null){
			return false;
		}
		return getModel().getSpeciesType(this.speciesTypeID) != null;
	}
	
	/**
	 * 
	 * @return true if the speciesTypeID of this Species is not null.
	 */
	public boolean isSetSpeciesType() {
		return speciesTypeID != null;
	}

	/**
	 * 
	 * @return true if the UnitDefinition which has the substanceUnitsID of this Species as id is not null.
	 */
	public boolean isSetSubstanceUnitsInstance() {
		return isSetUnitsInstance();
	}
	
	/**
	 * 
	 * @return true if the UnitDefinition which has the spatialSizeUnitsID of this Species as id is not null.
	 */
	public boolean isSetSpatialSizeUnitsInstance() {
		if (getModel() == null){
			return false;
		}
		return getModel().getUnitDefinition(this.spatialSizeUnitsID) != null;
	}
	
	/**
	 * 
	 * @return true if the substanceUnitsID of this species is not null.
	 */
	public boolean isSetSubstanceUnits() {
		return isSetUnits();
	}
	
	/**
	 * 
	 * @return true if the conversionFactorID of this Species is not null.
	 */
	public boolean isSetConversionFactor() {
		return conversionFactorID != null;
	}
	
	/**
	 * 
	 * @return true if the Parameter which has the conversionFactorID of this Species as id is not null.
	 */
	public boolean isSetConversionFactorInstance() {
		if (getModel() == null){
			return false;
		}
		return getModel().getParameter(this.conversionFactorID) != null;
	}


	/**
	 * Sets the boundaryCondition Boolean.
	 * @param boundaryCondition
	 */
	public void setBoundaryCondition(Boolean boundaryCondition) {
		this.boundaryCondition = boundaryCondition;
		stateChanged();
	}

	/**
	 * Sets the charge of this Species.
	 * @param charge
	 */
	public void setCharge(Integer charge) {
		this.charge = charge;
		isSetCharge = true;
		stateChanged();
	}

	/**
	 * Sets the compartmentID of this Species to the id of 'compartment'.
	 * @param compartment
	 */
	public void setCompartment(Compartment compartment) {
		this.compartmentID = compartment != null ? compartment.getId() : null;
		stateChanged();
	}
	
	/**
	 * Sets the compartmentID of this Species to 'compartment'.
	 * @param compartment
	 */
	public void setCompartment(String compartment) {
		if (compartment != null && compartment.trim().length() == 0) {
			this.compartmentID = null;
		} else {
			this.compartmentID = compartment;
		}
		stateChanged();
	}
	
	/**
	 * Sets the spatialSizeUnitsID of this Species to the id of 'spatialSizeUnits'.
	 * @param spatialSizeUnits
	 */
	public void setSpatialSizeUnits(UnitDefinition spatialSizeUnits) {
		this.spatialSizeUnitsID = spatialSizeUnits != null ? spatialSizeUnits.getId() : null;
		stateChanged();
	}
	
	/**
	 * Sets the spatialSizeUnitsID of this Species to 'spatialSizeUnits'.
	 * @param spatialSizeUnits
	 */
	public void setSpatialSizeUnits(String spatialSizeUnits) {
		if (spatialSizeUnits != null && spatialSizeUnits.trim().length() == 0) {
			this.spatialSizeUnitsID = null;
		} else {
			this.spatialSizeUnitsID = spatialSizeUnits;
		}
		stateChanged();
	}

	/**
	 * Sets hasOnlySubstanceUnits Boolean
	 * @param hasOnlySubstanceUnits
	 */
	public void setHasOnlySubstanceUnits(Boolean hasOnlySubstanceUnits) {
		this.hasOnlySubstanceUnits = hasOnlySubstanceUnits;
		stateChanged();
	}

	/**
	 * Sets the initialAmount of this Species.
	 * @param initialAmount
	 */
	public void setInitialAmount(double initialAmount) {
		setValue(initialAmount);
		amountOrConcentration = true;
	}

	/**
	 * Sets the initialConcentration of this Species.
	 * @param initialConcentration
	 */
	public void setInitialConcentration(double initialConcentration) {
		setValue(initialConcentration);
		amountOrConcentration = false;
	}

	/**
	 * Sets the speciesTypeID of this Species to the id of 'speciesType'.
	 * @param speciesType
	 */
	@Deprecated
	public void setSpeciesType(SpeciesType speciesType) {
		this.speciesTypeID = speciesType != null ? speciesType.getId() : null;
		stateChanged();
	}

	/**
	 * Sets the speciesTypeID of this Species to 'speciesType'.
	 * @param speciesType
	 */
	public void setSpeciesType(String speciesType) {
		if (speciesType != null && speciesType.trim().length() == 0) {
			speciesType = null;
		} else {
			this.speciesTypeID = speciesType;
		}
		stateChanged();
	}

	/**
	 * Sets the substanceUnitsID to 'unit'.
	 * @param unit
	 */
	public void setSubstanceUnits(String unit) {
		super.setUnits(unit);
	}

	/**
	 * Sets the substanceUnits.
	 * @param unit
	 */
	public void setSubstanceUnits(Unit unit) {
		super.setUnits(unit);
	}

	/**
	 * Sets the substanceUnits.
	 * @param unitKind
	 */
	public void setSubstanceUnits(Unit.Kind unitKind) {
		super.setUnits(unitKind);
	}

	/**
	 * Sets the substanceUnitsID to the id of 'units'.
	 * @param units
	 */
	public void setSubstanceUnits(UnitDefinition units) {
		super.setUnits(units);
	}
	
	/**
	 * Sets the conversionFactorID of this Species to the id of 'conversionFactor'.
	 * @param conversionFactor
	 */
	public void setConversionFactor(Parameter conversionFactor) {
		this.conversionFactorID = conversionFactor != null ? conversionFactor.getId() : null;
		stateChanged();
	}

	/**
	 * 
	 * @return the Parameter instance which has the conversionFactorID of this Species as id, null if it doesn't exist.
	 */
	public Parameter getConversionFactorInstance() {
		if (getModel() == null){
			return null;
		}
		return getModel().getParameter(this.conversionFactorID);
	}
	
	/**
	 * Sets the conversionFactorID of this Species to 'conversionFactorID'.
	 * @param conversionFactorID
	 */
	public void setConversionFactor(String conversionFactorID) {
		if (conversionFactorID != null && conversionFactorID.trim().length() == 0) {
			this.conversionFactorID = null;
		} else {
			this.conversionFactorID = conversionFactorID;
		}
		stateChanged();
	}

	/**
	 * 
	 * @return the conversionFactorID of this Species.
	 */
	public String getConversionFactor() {
		return conversionFactorID;
	}
	
	/**
	 * Unsets the initialAmount of this Species.
	 */
	public void unsetInitialAmount(){
		unsetValue();
		amountOrConcentration = false;
	}
	
	/**
	 * Unsets the initialConcentration of this Species.
	 */
	public void unsetInitialConcentration(){
		unsetValue();
		amountOrConcentration = true;
	}
	
	/**
	 * Unsets the substanceUnits of this Species.
	 */
	public void unsetSubstanceUnits(){
		super.unsetUnits();
	}
	
	/**
	 * Unsets the conversionFactorID of this Species.
	 */
	public void unsetConversionFactor(){
		this.conversionFactorID = null;
	}
	
	/**
	 * Unsets the charge of this Species
	 */
	public void unsetCharge() {
		charge = null;
		isSetCharge = false;
	}
	
	/**
	 * Unsets the spatialSizeUnits of this Species
	 */
	public void unsetSpatialSizeUnits() {
		spatialSizeUnitsID = null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)(
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		if (!isAttributeRead){
			if (attributeName.equals("compartment")){
				this.setCompartment(value);
			}
			else if (attributeName.equals("units") ){
				this.setUnits(value);
				return true;
			}
			else if (attributeName.equals("spatialDimensions") && getLevel() == 2 && (getVersion() == 1 || getVersion() == 2)){
				this.setSpatialSizeUnits(value);
				return true;
			}
			else if (attributeName.equals("initialAmount")){
				this.setInitialAmount(Double.parseDouble(value));
			}
			else if (attributeName.equals("initialConcentration") && getLevel() > 1){
				this.setInitialConcentration(Double.parseDouble(value));
			}
			else if (attributeName.equals("substanceUnits") && getLevel() > 1){
				this.setUnits(value);
			}
			else if (attributeName.equals("hasOnlySubstanceUnits") && getLevel() > 1){
				if (value.equals("true")){
					this.setHasOnlySubstanceUnits(true);
					return true;
				}
				else if (value.equals("false")){
					this.setHasOnlySubstanceUnits(false);
					return true;
				}
			}
			else if (attributeName.equals("boundaryCondition")){
				if (value.equals("true")){
					this.setBoundaryCondition(true);
					return true;
				}
				else if (value.equals("false")){
					this.setBoundaryCondition(false);
					return true;
				}
			}
			else if (attributeName.equals("conversionFactor")  && getLevel() == 3){
				this.setConversionFactor(value);
				return true;
			}
			else if (attributeName.equals("charge") && isSetLevel() && getLevel() < 3){
				this.setCharge(Integer.parseInt(value));
				return true;
			}
			else if (attributeName.equals("speciesType") && ((getLevel() == 2 && getVersion() >= 2) || getLevel() == 3)){
				this.setSpeciesType(value);
				return true;
			}
			else if (attributeName.equals("constant")  && getLevel() > 1){
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
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes(
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		
		if (isSetCompartment()){
			attributes.put("compartment", getCompartment());
		}
		if (isSetSpatialSizeUnits() && getLevel() == 2 && (getVersion() == 1 || getVersion() == 2)){
			attributes.put("spatialSizeUnits", getSpatialSizeUnits());
		}
		if (isSetInitialAmount()){
			attributes.put("initialAmount", Double.toString(getInitialAmount()));
		}
		if (isSetInitialConcentration()  && getLevel() > 1){
			attributes.put("initialConcentration", Double.toString(getInitialConcentration()));
		}
		if (isSetSubstanceUnits()  && getLevel() > 1){
			attributes.put("substanceUnits", getSubstanceUnits());
		}
		if (isSetHasOnlySubstanceUnits()  && getLevel() > 1){
			attributes.put("hasOnlySubstanceUnits", Boolean.toString(getHasOnlySubstanceUnits()));
		}
		if (isSetConstant()  && getLevel() > 1){
			attributes.put("constant", Boolean.toString(getConstant()));
		}
		if (isSetBoundaryCondition()){
			attributes.put("boundaryCondition", Boolean.toString(getBoundaryCondition()));
		}
		if (isSetConversionFactor()  && getLevel() == 3){
			attributes.put("conversionFactor", getConversionFactor());
		}
		if (isSetCharge()  && isSetLevel() && getLevel() < 3){
			attributes.put("charge", Integer.toString(getCharge()));
		}
		if (isSetSpeciesType() && ((getLevel() == 2 && getVersion() >= 2) || getLevel() == 3)){
			attributes.put("speciesType", getSpeciesType());
		}
		
		return attributes;
	}
}
