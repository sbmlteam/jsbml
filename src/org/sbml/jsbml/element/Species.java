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

package org.sbml.jsbml.element;

import java.util.HashMap;

/**
 * 
 * @author Andreas Dr&auml;ger
 * 
 */
public class Species extends Symbol {

	/**
	 * 
	 */
	private String conversionFactorID;
	
	/**
	 * 
	 */
	private String speciesTypeID;
	/**
	 * 
	 */
	private String compartmentID;
	/**
	 * true means initial amount is set. false means that an initial
	 * concentration is set.
	 */
	private boolean amountOrConcentration;
	/**
	 * 
	 */
	private boolean hasOnlySubstanceUnits;
	/**
	 * 
	 */
	private boolean boundaryCondition;
	/**
	 * 
	 */
	private int charge;

	/**
	 * 
	 */
	public Species() {
		super();
		this.charge = 0;
		this.compartmentID = null;
		this.speciesTypeID = null;
		this.conversionFactorID = null;
	}

	
	/**
	 * 
	 * @param species
	 */
	public Species(Species species) {
		super(species);
		this.boundaryCondition = species.getBoundaryCondition();
		this.charge = species.getCharge();
		this.compartmentID = species.getCompartment();
		this.hasOnlySubstanceUnits = species.getHasOnlySubstanceUnits();
		if (species.isSetInitialAmount())
			setInitialAmount(species.getInitialAmount());
		else if (species.isSetInitialConcentration())
			setInitialConcentration(species.getInitialConcentration());
		else
			setValue(Double.NaN);
		setConstant(species.getConstant());
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public Species(int level, int version) {
		super(level, version);
		if (level < 3) {
			initDefaults();
		}
	}
	/**
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public Species(String id, int level, int version) {
		super(id, level, version);
		if (level < 3) {
			initDefaults();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#clone()
	 */
	// @Override
	public Species clone() {
		return new Species(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.NamedSBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof Species) {
			boolean equal = super.equals(o);
			Species s = (Species) o;
			equal &= s.getBoundaryCondition() == getBoundaryCondition();
			equal &= s.getHasOnlySubstanceUnits() == getHasOnlySubstanceUnits();
			equal &= s.getCharge() == getCharge();
			equal &= s.isSetCompartmentInstance() == isSetCompartmentInstance();
			equal &= s.isSetSpeciesType() == isSetSpeciesType();

			if (s.isSetSpeciesType() && isSetSpeciesType())
				equal &= s.getSpeciesType().equals(getSpeciesType());
			if (s.isSetCompartmentInstance() && isSetCompartmentInstance())
				equal &= s.getCompartmentInstance().equals(
						getCompartmentInstance());
			equal &= s.isSetInitialAmount() == isSetInitialAmount();
			if (s.isSetInitialAmount() && isSetInitialAmount())
				equal &= s.getInitialAmount() == getInitialAmount();
			else if (s.isSetInitialConcentration()
					&& isSetInitialConcentration())
				equal &= s.getInitialConcentration() == getInitialConcentration();
			return equal;
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean getBoundaryCondition() {
		return isBoundaryCondition();
	}

	/**
	 * 
	 * @return
	 */
	public int getCharge() {
		return charge != Integer.MIN_VALUE ? charge : 0;
	}

	/**
	 * 
	 * @return
	 */
	public String getCompartment() {
		return isSetCompartment() ? compartmentID : "";
	}

	/**
	 * 
	 * @return
	 */
	public Compartment getCompartmentInstance() {
		if (getModel() == null){
			return null;
		}
		return getModel().getCompartment(this.compartmentID);
	}

	/**
	 * 
	 * @return
	 */
	public boolean getHasOnlySubstanceUnits() {
		return isHasOnlySubstanceUnits();
	}

	/**
	 * 
	 * @return
	 */
	public double getInitialAmount() {
		if (isSetInitialAmount()) {
			return getValue();
		}
		return 0;
	}

	/**
	 * 
	 * @return
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
	 * @see org.sbml.SBase#getParentSBMLObject()
	 */
	// @Override
	public Model getParentSBMLObject() {
		return (Model) parentSBMLObject;
	}

	/**
	 * 
	 * @return
	 */
	public String getSpeciesType() {
		return this.speciesTypeID;
	}

	/**
	 * 
	 * @return
	 */
	public SpeciesType getSpeciesTypeInstance() {
		if (getModel() == null){
			return null;
		}
		return getModel().getSpeciesType(this.speciesTypeID);
	}

	/**
	 * 
	 * @return
	 */
	public String getSubstanceUnits() {
		return getUnits();
	}

	/**
	 * 
	 * @return
	 */
	public UnitDefinition getSubstanceUnitsInstance() {
		return getUnitsInstance();
	}

	/**
	 * 
	 */
	public void initDefaults() {
		charge = Integer.MIN_VALUE;
		amountOrConcentration = true;
		hasOnlySubstanceUnits = false;
		boundaryCondition = false;
		setConstant(false);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isBoundaryCondition() {
		return boundaryCondition;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isHasOnlySubstanceUnits() {
		return hasOnlySubstanceUnits;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetCharge() {
		return charge != Integer.MIN_VALUE;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetCompartmentInstance() {
		if (getModel() == null){
			return false;
		}
		return getModel().getCompartment(this.compartmentID) != null;
	}
	
	/**
	 * 
	 * @return Returns true if an initial amount has been set for this species.
	 */
	public boolean isSetCompartment() {
		return compartmentID != null;
	}

	/**
	 * 
	 * @return Returns true if an initial amount has been set for this species.
	 */
	public boolean isSetInitialAmount() {
		return amountOrConcentration && !Double.isNaN(getValue());
	}

	/**
	 * 
	 * @return Returns true if an initial concentration has been set for this
	 *         species.
	 */
	public boolean isSetInitialConcentration() {
		return !amountOrConcentration && !Double.isNaN(getValue());
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetSpeciesTypeInstance() {
		if (getModel() == null){
			return false;
		}
		return getModel().getSpeciesType(this.speciesTypeID) != null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetSpeciesType() {
		return speciesTypeID != null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetSubstanceUnits() {
		return isSetUnits();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetSubstanceUnitsID() {
		return isSetUnitsID();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetConversionFactor() {
		return conversionFactorID != null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetConversionFactorInstance() {
		if (getModel() == null){
			return false;
		}
		return getModel().getParameter(this.conversionFactorID) != null;
	}


	/**
	 * 
	 * @param boundaryCondition
	 */
	public void setBoundaryCondition(boolean boundaryCondition) {
		this.boundaryCondition = boundaryCondition;
		stateChanged();
	}

	/**
	 * 
	 * @param charge
	 */
	public void setCharge(int charge) {
		this.charge = charge;
		stateChanged();
	}

	/**
	 * 
	 * @param compartment
	 */
	public void setCompartment(Compartment compartment) {
		this.compartmentID = compartment != null ? compartment.getId() : null;
		stateChanged();
	}
	
	/**
	 * 
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
	 * 
	 * @param hasOnlySubstanceUnits
	 */
	public void setHasOnlySubstanceUnits(boolean hasOnlySubstanceUnits) {
		this.hasOnlySubstanceUnits = hasOnlySubstanceUnits;
		stateChanged();
	}

	/**
	 * 
	 * @param initialAmount
	 */
	public void setInitialAmount(double initialAmount) {
		setValue(initialAmount);
		amountOrConcentration = true;
	}

	/**
	 * 
	 * @param initialConcentration
	 */
	public void setInitialConcentration(double initialConcentration) {
		setValue(initialConcentration);
		amountOrConcentration = false;
	}

	/**
	 * 
	 * @param speciesType
	 */
	public void setSpeciesType(SpeciesType speciesType) {
		this.speciesTypeID = speciesType != null ? speciesType.getId() : null;
		stateChanged();
	}

	/**
	 * 
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
	 * 
	 * @param unit
	 */
	public void setSubstanceUnits(String unit) {
		super.setUnits(unit);
	}

	/**
	 * 
	 * @param unit
	 */
	public void setSubstanceUnits(Unit unit) {
		super.setUnits(unit);
	}

	/**
	 * 
	 * @param unitKind
	 */
	public void setSubstanceUnits(Unit.Kind unitKind) {
		super.setUnits(unitKind);
	}

	/**
	 * 
	 * @param units
	 */
	public void setSubstanceUnits(UnitDefinition units) {
		super.setUnits(units);
	}
	
	public void setConversionFactor(Parameter conversionFactor) {
		this.conversionFactorID = conversionFactor != null ? conversionFactor.getId() : null;
		stateChanged();
	}


	public Parameter getConversionFactorInstance() {
		if (getModel() == null){
			return null;
		}
		return getModel().getParameter(this.conversionFactorID);
	}


	public void setConversionFactor(String conversionFactorID) {
		if (conversionFactorID != null && conversionFactorID.trim().length() == 0) {
			this.conversionFactorID = null;
		} else {
			this.conversionFactorID = conversionFactorID;
		}
		stateChanged();
	}


	public String getConversionFactor() {
		return conversionFactorID;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		if (!isAttributeRead){
			if (attributeName.equals("compartment")){
				this.setCompartment(value);
			}
			else if (attributeName.equals("initialAmount")){
				this.setInitialAmount(Double.parseDouble(value));
			}
			else if (attributeName.equals("initialConcentration")){
				this.setInitialConcentration(Double.parseDouble(value));
			}
			else if (attributeName.equals("substanceUnits")){
				this.setUnits(value);
			}
			else if (attributeName.equals("hasOnlySubstanceUnits")){
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
			else if (attributeName.equals("conversionFactor")){
				this.setConversionFactor(value);
				return true;
			}
			else if (attributeName.equals("charge")){
				this.setCharge(Integer.parseInt(value));
				return true;
			}
			else if (attributeName.equals("speciesType")){
				this.setSpeciesType(value);
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
		}
		return isAttributeRead;
	}
	
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		
		if (isSetCompartment()){
			attributes.put("compartment", getCompartment());
		}
		if (isSetInitialAmount()){
			attributes.put("initialAmount", Double.toString(getInitialAmount()));
		}
		if (isSetInitialConcentration()){
			attributes.put("initialConcentration", Double.toString(getInitialConcentration()));
		}
		if (isSetSubstanceUnitsID()){
			attributes.put("substanceUnits", getSubstanceUnits());
		}
		if (hasOnlySubstanceUnits){
			attributes.put("hasOnlySubstanceUnits", "true");
		}
		else {
			attributes.put("hasOnlySubstanceUnits", "false");
		}
		if (isSetConstant()){
			attributes.put("constant", getConstant().toString());
		}
		if (boundaryCondition){
			attributes.put("boundaryCondition", "true");
		}
		else {
			attributes.put("boundaryCondition", "false");
		}
		if (isSetConversionFactor()){
			attributes.put("conversionFactor", getConversionFactor());
		}
		if (isSetCharge()){
			attributes.put("charge", Integer.toString(getCharge()));
		}
		if (isSetSpeciesType()){
			attributes.put("speciesType", getSpeciesType());
		}
		
		return attributes;
	}


	// TODO : implement
	public boolean isSetSpatialSizeUnits() {
		return false;
	}
}
