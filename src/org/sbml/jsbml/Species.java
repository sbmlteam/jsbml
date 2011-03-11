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

import java.util.Locale;
import java.util.Map;

import org.sbml.jsbml.util.StringTools;

/**
 * Represents the species XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * @since 0.8
 * @version $Rev$
 */
public class Species extends Symbol {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 4427015656530890393L;

	/**
	 * True means initial amount is set. False means that an initial
	 * concentration is set.
	 */
	private boolean amount;

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
	 * Represents the 'compartment' attribute of a Species element.
	 */
	private String compartmentID;
	/**
	 * Represents the 'conversionFactor' attribute of a Species element.
	 */
	private String conversionFactorID;
	/**
	 * Represents the 'hasOnlySubstanceUnits' attribute of a Species element.
	 */
	private Boolean hasOnlySubstanceUnits;
	/**
	 * 
	 */
	private boolean isSetBoundaryCondition = false;
	/**
	 * Boolean value to test if the charge has been set.
	 */
	private boolean isSetCharge;
	/**
	 * 
	 */
	private boolean isSetHasOnlySubstanceUnits = false;
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
	 * Creates a Species instance. By default, the charge, compartmentID,
	 * speciesTypeID, conversionFactorID, hasOnlySubstanceUnits,
	 * boundaryCondition are null.
	 */
	public Species() {
		super();
		initDefaults();
	}
	
	/**
	 * Creates a Species instance from a level and version. By default, the
	 * charge, compartmentID, speciesTypeID, conversionFactorID,
	 * hasOnlySubstanceUnits, boundaryCondition are null.
	 * 
	 * @param level
	 * @param version
	 */
	public Species(int level, int version) {
		this(null, null, level, version);
	}
	
	/**
	 * Creates a Species instance from a Species.
	 * 
	 * @param species
	 */
	public Species(Species species) {
		super(species);
		initDefaults();
		if (species.isSetBoundaryCondition()) {
			setBoundaryCondition(species.getBoundaryCondition());
		}
		if (species.isSetCharge()) {
			setCharge(species.getCharge());
		}
		if (species.isSetCompartment()) {
			setCompartment(new String(species.getCompartment()));
		}
		if (species.isSetSubstanceUnits()) {
			setSubstanceUnits(new String(species.getSubstanceUnits()));
		}
		if (species.isSetHasOnlySubstanceUnits()) {
			setHasOnlySubstanceUnits(new Boolean(species
					.getHasOnlySubstanceUnits()));
		}
		if (species.isSetInitialAmount()) {
			setInitialAmount(new Double(species.getInitialAmount()));
		} else if (species.isSetInitialConcentration()) {
			setInitialConcentration(species.getInitialConcentration());
		} else {
			setValue(Double.NaN);
		}
		if (species.isSetConstant()) {
			setConstant(new Boolean(species.getConstant()));
		}
		if (species.isSetSpatialSizeUnits()) {
			setSpatialSizeUnits(new String(species.getSpatialSizeUnits()));
		} else {
			this.spatialSizeUnitsID = null;
		}
	}

	/**
	 * 
	 * @param id
	 */
	public Species(String id) {
		this();
		setId(id);
	}

	/**
	 * Creates a Species instance from a level and verison. By default, the
	 * charge, compartmentID, speciesTypeID, conversionFactorID,
	 * hasOnlySubstanceUnits, boundaryCondition are null.
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public Species(String id, int level, int version) {
		this(id, null, level, version);
	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public Species(String id, String name, int level, int version) {
		super(id, name, level, version);
		initDefaults();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Symbol#clone()
	 */
	@Override
	public Species clone() {
		return new Species(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Symbol#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof Species) {
			boolean equal = super.equals(o);
			Species s = (Species) o;
			equal &= s.getBoundaryCondition() == getBoundaryCondition();
			equal &= s.getHasOnlySubstanceUnits() == getHasOnlySubstanceUnits();
			equal &= s.getCharge() == getCharge();
			equal &= s.isSetCompartment() == isSetCompartment();
			equal &= s.isSetSpeciesType() == isSetSpeciesType();

			if (equal && isSetSpeciesType()) {
				equal &= s.getSpeciesType().equals(getSpeciesType());
			}
			if (equal && isSetCompartment()) {
				equal &= s.getCompartment().equals(getCompartment());
			}
			equal &= s.isSetInitialAmount() == isSetInitialAmount();
			if (equal && isSetInitialAmount()) {
				equal &= s.getInitialAmount() == getInitialAmount();
			} else if (equal && isSetInitialConcentration()) {
				equal &= s.getInitialConcentration() == getInitialConcentration();
			}
			equal &= s.isSetSpatialSizeUnits() == isSetSpatialSizeUnits();

			if (equal && isSetSpatialSizeUnits()) {
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
	 * @return the charge value of this Species if it is set, 0 otherwise.
	 */
	@Deprecated
	public int getCharge() {
		return isSetCharge() ? this.charge : 0;
	}

	/**
	 * 
	 * @return the compartmentID of this Species. The empty String if it is not
	 *         set.
	 */
	public String getCompartment() {
		return isSetCompartment() ? compartmentID : "";
	}

	/**
	 * 
	 * @return The Compartment instance which as the compartmentID of this
	 *         Species as id. Null if it doesn't exist.
	 */
	public Compartment getCompartmentInstance() {
		if (getModel() == null) {
			return null;
		}
		return getModel().getCompartment(this.compartmentID);
	}

	/**
	 * 
	 * @return the conversionFactorID of this Species.
	 */
	public String getConversionFactor() {
		return conversionFactorID;
	}

	/**
	 * 
	 * @return the Parameter instance which has the conversionFactorID of this
	 *         Species as id, null if it doesn't exist.
	 */
	public Parameter getConversionFactorInstance() {
		if (getModel() == null) {
			return null;
		}
		return getModel().getParameter(this.conversionFactorID);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getElementName()
	 */
	@Override
	public String getElementName() {
		if ((getLevel() == 1) && (getVersion() == 1)) {
			return "specie";
		}
		return super.getElementName();
	}

	/**
	 * 
	 * @return the hasOnlySubstanceUnits Boolean of this Species.
	 */
	public boolean getHasOnlySubstanceUnits() {
		return hasOnlySubstanceUnits();
	}

	/**
	 * 
	 * @return the initialAmount of this Species if it has been set, o
	 *         otherwise.
	 */
	public double getInitialAmount() {
		if (isSetInitialAmount()) {
			return getValue();
		}
		return Double.NaN;
	}

	/**
	 * 
	 * @return the initialConcentration of this {@link Species} if it has been set, o
	 *         otherwise.
	 */
	public double getInitialConcentration() {
		if (isSetInitialConcentration()) {
			return getValue();
		}
		return Double.NaN;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#getPredefinedUnitID()
	 */
	public String getPredefinedUnitID() {
		if (getLevel() < 3) {
			return "substance";
		}
		return null;
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
	 * @return The UnitDefinition instance which as the spatialSizeUnitsID of
	 *         this Species as id. Null if it doesn't exist.
	 */
	public UnitDefinition getSpatialSizeUnitsInstance() {
		if (getModel() == null) {
			return null;
		}
		return getModel().getUnitDefinition(this.spatialSizeUnitsID);
	}

	/**
	 * 
	 * @return the speciesTypeID of this {@link Species}. The empty String if it is not
	 *         set.
	 */
	@Deprecated
	public String getSpeciesType() {
		return isSetSpeciesType() ? this.speciesTypeID : "";
	}

	/**
	 * 
	 * @return the SpeciesType instance which has the speciesTypeID of this
	 *         Species as id. Null if it doesn't exist.
	 */
	@Deprecated
	public SpeciesType getSpeciesTypeInstance() {
		if (getModel() == null) {
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
	 * @return The UnitsDefinition instance which has the substanceUnistID of
	 *         this Species as id.
	 */
	public UnitDefinition getSubstanceUnitsInstance() {
		return getUnitsInstance();
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasOnlySubstanceUnits() {
		return isSetHasOnlySubstanceUnits() ? this.hasOnlySubstanceUnits
				: false;
	}

	/**
	 * Initialises the default values of this Species.
	 */
	public void initDefaults() {
		amount = true;
		unitsID = null;
		if (getLevel() < 3) {
			hasOnlySubstanceUnits = new Boolean(false);
			boundaryCondition = new Boolean(false);
			constant = new Boolean(false);
		} else {
			hasOnlySubstanceUnits = null;
			boundaryCondition = null;
			constant = null;
		}
	}

	/**
	 * 
	 * @return the value of the boundaryCondition Boolean if it is set, false
	 *         otherwise.
	 */
	public boolean isBoundaryCondition() {
		return isSetBoundaryCondition() ? boundaryCondition : false;
	}

	/**
	 * 
	 * @return the value of the hasOnlySubstanceUnits Boolean if it is set,
	 *         false otherwise.
	 */
	public boolean isHasOnlySubstanceUnits() {
		return hasOnlySubstanceUnits();
	}

	/**
	 * 
	 * @return true if the boundaryCondition of this Species is not null.
	 */
	public boolean isSetBoundaryCondition() {
		return isSetBoundaryCondition;
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
	 * @return true if the compartmentID of this Species is not null.
	 */
	public boolean isSetCompartment() {
		return compartmentID != null;
	}

	/**
	 * 
	 * @return true if the Compartment instance which has the compartmentID of
	 *         this Species as id is not null.
	 */
	public boolean isSetCompartmentInstance() {
		if (getModel() == null) {
			return false;
		}
		return getModel().getCompartment(this.compartmentID) != null;
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
	 * @return true if the Parameter which has the conversionFactorID of this
	 *         Species as id is not null.
	 */
	public boolean isSetConversionFactorInstance() {
		if (getModel() == null) {
			return false;
		}
		return getModel().getParameter(this.conversionFactorID) != null;
	}

	/**
	 * 
	 * @return true if the hasOnlySubstanceUnits of this Species is not null.
	 */
	public boolean isSetHasOnlySubstanceUnits() {
		return isSetHasOnlySubstanceUnits;
	}

	/**
	 * 
	 * @return Returns true if an initial amount has been set for this species.
	 */
	public boolean isSetInitialAmount() {
		return amount && isSetValue();
	}

	/**
	 * 
	 * @return Returns true if an initial concentration has been set for this
	 *         species.
	 */
	public boolean isSetInitialConcentration() {
		return !amount && isSetValue();
	}

	/**
	 * 
	 * @return true if the spatialSizeUnits of this Species is not null.
	 */
	@Deprecated
	public boolean isSetSpatialSizeUnits() {
		return this.spatialSizeUnitsID != null;
	}

	/**
	 * 
	 * @return true if the UnitDefinition which has the spatialSizeUnitsID of
	 *         this Species as id is not null.
	 */
	public boolean isSetSpatialSizeUnitsInstance() {
		if (getModel() == null) {
			return false;
		}
		return getModel().getUnitDefinition(this.spatialSizeUnitsID) != null;
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
	 * @return true if the SpeciesType instance which has the speciesTypeID of
	 *         this Species as id is not null.
	 */
	@Deprecated
	public boolean isSetSpeciesTypeInstance() {
		if (getModel() == null) {
			return false;
		}
		return getModel().getSpeciesType(this.speciesTypeID) != null;
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
	 * @return true if the UnitDefinition which has the substanceUnitsID of this
	 *         Species as id is not null.
	 */
	public boolean isSetSubstanceUnitsInstance() {
		return isSetUnitsInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)(
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);

		if (!isAttributeRead) {
			isAttributeRead = true;
			
			if (attributeName.equals("compartment")) {
				setCompartment(value);
			} else if (attributeName.equals("initialAmount")) {
				setInitialAmount(StringTools.parseSBMLDouble(value));
			} else if (attributeName.equals("initialConcentration")
					&& getLevel() > 1) {
				setInitialConcentration(StringTools.parseSBMLDouble(value));
			} else if (attributeName.equals("substanceUnits") && getLevel() > 1) {
				setUnits(value);
			} else if (attributeName.equals("spatialSizeUnits")) {
				setSpatialSizeUnits(value);
			} else if (attributeName.equals("hasOnlySubstanceUnits")) {
				setHasOnlySubstanceUnits(StringTools.parseSBMLBoolean(value));
			} else if (attributeName.equals("boundaryCondition")) {
				setBoundaryCondition(StringTools.parseSBMLBoolean(value));
			} else if (attributeName.equals("conversionFactor")) {
				setConversionFactor(value);
			} else if (attributeName.equals("charge")) {
				setCharge(StringTools.parseSBMLInt(value));
			} else if (attributeName.equals("speciesType")) {
				setSpeciesType(value);
				return true;
			} else if (attributeName.equals("constant")) {
				setConstant(StringTools.parseSBMLBoolean(value));
			} else {
				isAttributeRead = false;
			}
		}
		
		return isAttributeRead;
	}

	/**
	 * Sets the boundaryCondition Boolean.
	 * 
	 * @param boundaryCondition
	 */
	public void setBoundaryCondition(Boolean boundaryCondition) {
		Boolean oldBoundaryCondition = this.boundaryCondition;
		this.boundaryCondition = boundaryCondition;
		isSetBoundaryCondition = true;
		firePropertyChange(SBaseChangedEvent.boundaryCondition,
				oldBoundaryCondition, this.boundaryCondition);
	}

	/**
	 * Sets the charge of this {@link Species}.
	 * 
	 * @param charge
	 * @deprecated Only defined in SBML Level 1, Version 1 and 2, and Level 2
	 *             Version 1. Since Level 2 Version 2 it has been marked as a
	 *             deprecated property, but has been completely removed in SBML
	 *             Level 3.
	 */
	@Deprecated
	public void setCharge(int charge) {
		if (3 <= getLevel()) {
			throw new PropertyNotAvailableError(SBaseChangedEvent.charge, this);
		}
		Integer oldCharge = this.charge;
		this.charge = Integer.valueOf(charge);
		isSetCharge = true;
		firePropertyChange(SBaseChangedEvent.charge, oldCharge, this.charge);
	}

	/**
	 * Sets the compartmentID of this {@link Species} to the id of 'compartment'.
	 * 
	 * @param compartment
	 */
	public void setCompartment(Compartment compartment) {
		if (compartment != null) {
			setCompartment(compartment.getId());
		} else {
			unsetCompartment();
		}
	}

	/**
	 * Sets the compartmentID of this {@link Species} to 'compartment'.
	 * 
	 * @param compartment
	 */
	public void setCompartment(String compartment) {
		if (compartment != null && compartment.trim().length() == 0) {
			compartment = null; // If we pass the empty String or null, the value is reset.
		}
		if ((compartment == null) || checkIdentifier(compartment)) {
			String oldCompartment = this.compartmentID;
			if ((compartment != null) && (compartment.trim().length() == 0)) {
				this.compartmentID = null;
			} else {
				this.compartmentID = compartment;
			}
			firePropertyChange(SBaseChangedEvent.compartment, oldCompartment, compartmentID);
		}
	}

	/**
	 * Sets the conversionFactorID of this {@link Species} to the id of
	 * 'conversionFactor'. This is only possible if Level >= 3.
	 * 
	 * @param conversionFactor
	 */
	public void setConversionFactor(Parameter conversionFactor) {
		setConversionFactor(conversionFactor != null ? conversionFactor
				.getId() : null);
	}

	/**
	 * Sets the conversionFactorID of this {@link Species} to 'conversionFactorID'.
	 * This is only possible if Level >= 3.
	 * 
	 * @param conversionFactorID
	 */
	public void setConversionFactor(String conversionFactorID) {
		if (getLevel() < 3) {
			throw new PropertyNotAvailableError(
					SBaseChangedEvent.conversionFactor, this);
		}
		String oldConversionFactor = this.conversionFactorID;
		if ((conversionFactorID != null)
				&& (conversionFactorID.trim().length() == 0)) {
			this.conversionFactorID = null;
		} else {
			this.conversionFactorID = conversionFactorID;
		}
		firePropertyChange(SBaseChangedEvent.conversionFactor,
				oldConversionFactor, conversionFactorID);
	}

	/**
	 * Sets hasOnlySubstanceUnits Boolean
	 * 
	 * @param hasOnlySubstanceUnits
	 */
	public void setHasOnlySubstanceUnits(boolean hasOnlySubstanceUnits) {
		if (getLevel() < 2) {
			throw new PropertyNotAvailableError(
					SBaseChangedEvent.hasOnlySubstanceUnits, this);
		}
		Boolean oldHasOnlySubstanceUnits = this.hasOnlySubstanceUnits;
		this.hasOnlySubstanceUnits = Boolean.valueOf(hasOnlySubstanceUnits);
		isSetHasOnlySubstanceUnits = true;
		firePropertyChange(SBaseChangedEvent.hasOnlySubstanceUnits,
				oldHasOnlySubstanceUnits, this.hasOnlySubstanceUnits);
	}

	/**
	 * Sets the initialAmount of this {@link Species}.
	 * 
	 * @param initialAmount
	 */
	public void setInitialAmount(double initialAmount) {
		boolean amount = this.amount;
		this.amount = true;
		if (!amount) {
			firePropertyChange(SBaseChangedEvent.initialAmount, Boolean.FALSE,
					Boolean.TRUE);
		}
		setValue(initialAmount);
	}

	/**
	 * Sets the initialConcentration of this {@link Species}.
	 * 
	 * @param initialConcentration
	 */
	public void setInitialConcentration(double initialConcentration) {
		boolean amount = this.amount;
		this.amount = false;
		if (amount) {
			firePropertyChange(SBaseChangedEvent.initialAmount, Boolean.TRUE,
					Boolean.FALSE);
		}
		setValue(initialConcentration);
	}

	/**
	 * Sets the spatialSizeUnitsID of this {@link Species} to 'spatialSizeUnits'.
	 * 
	 * @param spatialSizeUnits
	 * @deprecated This property is only valid for SBML Level 2 Versions 1 and 2.
	 */
	@Deprecated
	public void setSpatialSizeUnits(String spatialSizeUnits) {
		if ((getLevel() != 2) && ((1 != getVersion()) || (2 != getVersion()))) {
			throw new PropertyNotAvailableError(
					SBaseChangedEvent.spatialSizeUnits, this);
		}
		String oldSpatialSizeUnits = this.spatialSizeUnitsID;
		if ((spatialSizeUnits != null) && (spatialSizeUnits.trim().length() == 0)) {
			this.spatialSizeUnitsID = null;
		} else {
			this.spatialSizeUnitsID = spatialSizeUnits;
		}
		firePropertyChange(SBaseChangedEvent.spatialSizeUnits,
				oldSpatialSizeUnits, this.spatialSizeUnitsID);
	}

	/**
	 * Sets the spatialSizeUnitsID of this {@link Species} to the id of
	 * 'spatialSizeUnits'.
	 * 
	 * @param spatialSizeUnits
	 * @deprecated
	 */
	@Deprecated
	public void setSpatialSizeUnits(UnitDefinition spatialSizeUnits) {
		setSpatialSizeUnits(spatialSizeUnits != null ? spatialSizeUnits
				.getId() : null);
	}

	/**
	 * Sets the speciesTypeID of this {@link Species} to the id of 'speciesType'.
	 * 
	 * @param speciesType
	 * @deprecated
	 */
	@Deprecated
	public void setSpeciesType(SpeciesType speciesType) {
		setSpeciesType(speciesType != null ? speciesType.getId() : null);
	}

	/**
	 * Sets the speciesTypeID of this {@link Species} to 'speciesType'.
	 * 
	 * @param speciesType
	 * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
	 */
	@Deprecated
	public void setSpeciesType(String speciesType) {
		if ((getLevel() != 2) && ((getVersion() < 2) || (4 < getVersion()))) {
			throw new PropertyNotAvailableError(SBaseChangedEvent.speciesType,
					this);
		}
		if ((speciesType == null) || (speciesType.trim().length() == 0) || checkIdentifier(speciesType)) {
			String oldSpeciesType = this.speciesTypeID;
			speciesType = ((speciesType != null) && (speciesType.trim()
					.length() == 0)) ? null : speciesType;
			firePropertyChange(SBaseChangedEvent.speciesType, oldSpeciesType,
					this.speciesTypeID);
		}
	}

	/**
	 * Sets the substanceUnitsID to 'unit'.
	 * 
	 * @param unit
	 */
	public void setSubstanceUnits(String unit) {
		setUnits(unit);
	}

	/**
	 * Sets the substanceUnits.
	 * 
	 * @param unit
	 */
	public void setSubstanceUnits(Unit unit) {
		setUnits(unit);
	}

	/**
	 * Sets the substanceUnits.
	 * 
	 * @param unitKind
	 */
	public void setSubstanceUnits(Unit.Kind unitKind) {
		setUnits(unitKind);
	}

	/**
	 * Sets the substanceUnitsID to the id of 'units'.
	 * 
	 * @param units
	 */
	public void setSubstanceUnits(UnitDefinition units) {
		setUnits(units);
	}

	/**
	 * Unsets the charge of this Species
	 * @deprecated
	 */
	@Deprecated
	public void unsetCharge() {
		Integer oldCharge = charge;
		charge = null;
		isSetCharge = false;
		firePropertyChange(SBaseChangedEvent.charge, oldCharge, this.charge);
	}

	/**
	 * Remove the reference to a comparmtent.
	 */
	public void unsetCompartment() {
		setCompartment((String) null);
	}

	/**
	 * Unsets the conversionFactorID of this Species.
	 */
	public void unsetConversionFactor() {
		setConversionFactor((String) null);
	}

	/**
	 * Unsets the initialAmount of this Species.
	 */
	public void unsetInitialAmount() {
		amount = false;
		unsetValue();
	}

	/**
	 * Unsets the initialConcentration of this Species.
	 */
	public void unsetInitialConcentration() {
		amount = true;
		unsetValue();
	}

	/**
	 * Unsets the spatialSizeUnits of this Species
	 * @deprecated
	 */
	@Deprecated
	public void unsetSpatialSizeUnits() {
		setSpatialSizeUnits((String) null);
	}

	/**
	 * Unsets the substanceUnits of this Species.
	 */
	public void unsetSubstanceUnits() {
		unsetUnits();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes(
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();
		Locale en = Locale.ENGLISH;
		if (isSetCompartment()) {
			attributes.put("compartment", getCompartment());
		}
		if (isSetInitialAmount()) {
			attributes.put("initialAmount", StringTools.toString(en,
					getInitialAmount()));
		}
		if (isSetBoundaryCondition()) {
			attributes.put("boundaryCondition", Boolean
					.toString(getBoundaryCondition()));
		}

		if (1 < getLevel()) {
			if (isSetInitialConcentration() && !isSetInitialAmount()) {
				attributes.put("initialConcentration", StringTools.toString(en,
						getInitialConcentration()));
			}
			if (isSetSubstanceUnits()) {
				attributes.put("substanceUnits", getSubstanceUnits());
			}
			if (isSetHasOnlySubstanceUnits()) {
				attributes.put("hasOnlySubstanceUnits", Boolean
						.toString(getHasOnlySubstanceUnits()));
			}
			if (isSetConstant()) {
				attributes.put("constant", Boolean.toString(getConstant()));
			}
		}
		if (getLevel() < 3) {
			if (isSetCharge) {
				attributes.put("charge", Integer.toString(getCharge()));
			}
		}
		if (getLevel() == 2) {
			if ((getVersion() == 1) || (getVersion() == 2)) {
				if (isSetSpatialSizeUnits()) {
					attributes.put("spatialSizeUnits", getSpatialSizeUnits());
				}
			}
			if (getVersion() <= 2) {
				if (isSetSpeciesType()) {
					attributes.put("speciesType", getSpeciesType());
				}
			}
		} else if (getLevel() == 3) {
			if (isSetConversionFactor()) {
				attributes.put("conversionFactor", getConversionFactor());
			}
		}

		return attributes;
	}
}
