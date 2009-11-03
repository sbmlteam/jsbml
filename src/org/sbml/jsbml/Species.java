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

/**
 * 
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public class Species extends Symbol {

	/**
	 * 
	 */
	private SpeciesType speciesType;
	/**
	 * 
	 */
	private Compartment compartment;
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
	 * @param species
	 */
	public Species(Species species) {
		super(species);
		this.boundaryCondition = species.getBoundaryCondition();
		this.charge = species.getCharge();
		this.compartment = species.getCompartmentInstance();
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
	 * @param id
	 * @param level
	 * @param version
	 */
	public Species(String id, int level, int version) {
		super(id, level, version);
		initDefaults();
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
			equal &= s.isSetCompartment() == isSetCompartment();
			equal &= s.isSetSpeciesType() == isSetSpeciesType();
			if (s.isSetSpeciesType() && isSetSpeciesType())
				equal &= s.getSpeciesType().equals(getSpeciesType());
			if (s.isSetCompartment() && isSetCompartment())
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
		return compartment.getId();
	}

	/**
	 * 
	 * @return
	 */
	public Compartment getCompartmentInstance() {
		return compartment;
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
		if (isSetInitialAmount())
			return getValue();
		return Double.NaN;
	}

	/**
	 * 
	 * @return
	 */
	public double getInitialConcentration() {
		if (isSetInitialConcentration())
			return getValue();
		return Double.NaN;
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
		return isSetSpeciesType() ? speciesType.getId() : "";
	}

	/**
	 * 
	 * @return
	 */
	public SpeciesType getSpeciesTypeInstance() {
		return speciesType;
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
	public boolean isSetCompartment() {
		return compartment != null;
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
	public boolean isSetSpeciesType() {
		return speciesType != null;
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
		this.compartment = compartment;
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
		this.speciesType = speciesType;
		this.speciesType.parentSBMLObject = this;
		stateChanged();
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
}
