/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
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
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * Represents the species XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
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

		if (species.isSetBoundaryCondition()) {
			setBoundaryCondition(species.getBoundaryCondition());
		} else {
			boundaryCondition = species.boundaryCondition == null ? null : new Boolean(species.boundaryCondition);
		}
		if (species.isSetCharge()) {
			setCharge(species.getCharge());
		} else {
			charge = species.charge == null ? null : new Integer(species.charge);
		}
		if (species.isSetCompartment()) {
			setCompartment(new String(species.getCompartment()));
		}
		if (species.isSetSubstanceUnits()) {
			setSubstanceUnits(new String(species.getSubstanceUnits()));
		}
		if (species.isSetHasOnlySubstanceUnits()) {
			setHasOnlySubstanceUnits(new Boolean(
					species.getHasOnlySubstanceUnits()));
		} else {
			hasOnlySubstanceUnits = species.hasOnlySubstanceUnits == null ? null : new Boolean(species.hasOnlySubstanceUnits);
		}
		if (species.isSetInitialAmount()) {
			setInitialAmount(new Double(species.getInitialAmount()));
		} else if (species.isSetInitialConcentration()) {
			setInitialConcentration(new Double(species.getInitialConcentration()));
		}		
		if (species.isSetSpatialSizeUnits()) {
			setSpatialSizeUnits(new String(species.getSpatialSizeUnits()));
		} else {
			this.spatialSizeUnitsID = species.spatialSizeUnitsID == null ? null : new String(species.spatialSizeUnitsID);
		}
		if (species.isSetConversionFactor()) {
			setConversionFactor(species.conversionFactorID);
		}
		if (species.isSetSpeciesType()) {
			setSpeciesType(species.getSpeciesType());
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
	 * @see org.sbml.jsbml.Symbol#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equals = super.equals(object);
		if (equals) {
			Species s = (Species) object;
			equals &= s.getBoundaryCondition() == getBoundaryCondition();
			equals &= s.getHasOnlySubstanceUnits() == getHasOnlySubstanceUnits();
			equals &= s.getCharge() == getCharge();
			
			equals &= s.isSetSpeciesType() == isSetSpeciesType();
			if (equals && isSetSpeciesType()) {
				equals &= s.getSpeciesType().equals(getSpeciesType());
			}
			equals &= s.isSetCompartment() == isSetCompartment();
			if (equals && isSetCompartment()) {
				equals &= s.getCompartment().equals(getCompartment());
			}
			
			equals &= s.isSetInitialAmount() == isSetInitialAmount();
			equals &= s.isSetInitialConcentration() == isSetInitialConcentration();
			// value is already checked by super class.
			
			equals &= s.isSetSpatialSizeUnits() == isSetSpatialSizeUnits();
			if (equals && isSetSpatialSizeUnits()) {
				equals &= s.getSpatialSizeUnits().equals(getSpatialSizeUnits());
			}
		}
		return equals;
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

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#getDerivedUnitDefinition()
	 */
	@Override
	public UnitDefinition getDerivedUnitDefinition() {
		UnitDefinition specUnit = super.getDerivedUnitDefinition();
		Model model = getModel();
		if ((specUnit == null) && (getLevel() > 2) && (model != null) && (model.isSetSubstanceUnits())) {
			// According to SBML specification of Level 3 Version 1, page 44, lines 20-22:
			specUnit = model.getSubstanceUnitsInstance();
		}
		Compartment compartment = getCompartmentInstance();
		if ((specUnit != null) && !hasOnlySubstanceUnits() && (compartment != null)
				&& (0d < compartment.getSpatialDimensions())) {
			UnitDefinition sizeUnit; // = getSpatialSizeUnitsInstance();
			if ((model != null) && isSetSpatialSizeUnits()) {
				sizeUnit = model.getUnitDefinition(getSpatialSizeUnits());
			} else {
				sizeUnit = compartment.getDerivedUnitDefinition();
			}
			if (sizeUnit != null) {
				UnitDefinition derivedUD = specUnit.clone().divideBy(sizeUnit);
				derivedUD.setId(derivedUD.getId() + "_per_" + sizeUnit.getId());
				if (derivedUD.isSetName()) {
					derivedUD.setName(derivedUD.getName() + " per "
							+ (sizeUnit.isSetName() ? sizeUnit.getName() : sizeUnit.getId()));
				}
				/* 
				 * If possible, let's return an equivalent unit that is already part of the model
				 * rather than returning some newly created UnitDefinition:
				 */
				// TODO: There might not be another unit with the same id, but that has an identical listOfUnits. This should be checked here!
				if (model != null) {
					UnitDefinition ud = model.getUnitDefinition(derivedUD.getId());
					if ((ud != null) && (UnitDefinition.areEquivalent(ud, derivedUD))) {
						return ud;
					}
				}
				return derivedUD;
			}
		}
		return specUnit;
	}

	/*
	 * (non-Javadoc)
	 * 
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
	 * @return the initialConcentration of this {@link Species} if it has been
	 *         set, o otherwise.
	 */
	public double getInitialConcentration() {
		if (isSetInitialConcentration()) {
			return getValue();
		}
		return Double.NaN;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#getPredefinedUnitID()
	 */
	public String getPredefinedUnitID() {
		int level = getLevel();
		if (level < 3) {
//			if (level == 2) {
//				Compartment compartment = getCompartmentInstance();
//				if ((compartment != null)
//						&& (compartment.getSpatialDimensions() > 0d)) {
//					return null;
//				}
//			}
			/* L1V1: default "substance"; also "volume" or any base unit
			 * L1V2: default "substance"; also any base unit
			 */
			return "substance";
		}
		return null;
	}

	/**
	 * If determined, this method first checks the explicitly set spatial size
	 * units of this {@link Species}. If no such value is defined, it will
	 * return the units of the surrounding {@link Compartment}. Only if this is
	 * also not possible, an empty {@link String} will be returned.
	 * 
	 * @return the spatialSizeUnits of this {@link Species}.
	 */
	public String getSpatialSizeUnits() {
		if (isSetSpatialSizeUnits()) {
			return spatialSizeUnitsID;
		}
		Compartment c = getCompartmentInstance();
		return c != null ? c.getUnits() : "";
	}

	/**
	 * Determines the spatial units of this {@link Species}. If the spatial
	 * units have been set explicitly using {@link #spatialSizeUnitsID} the
	 * corresponding {@link UnitDefinition} from the {@link Model} to which this
	 * {@link Species} belongs will be returned. Otherwise, the size unit from
	 * the surrounding {@link Compartment} of this {@link Species} will be
	 * returned. If this also fails, <code>null</code> will be returned.
	 * 
	 * @return The {@link UnitDefinition} instance which as the
	 *         {@link #spatialSizeUnitsID} of this {@link Species} as id or the
	 *         size unit of the surrounding {@link Compartment}.
	 *         <code>Null</code> if it doesn't exist.
	 */
	public UnitDefinition getSpatialSizeUnitsInstance() {
		if (isSetSpatialSizeUnits()) {
			Model model = getModel();
			return model != null ? model
					.getUnitDefinition(this.spatialSizeUnitsID) : null;
		}
		Compartment compartment = getCompartmentInstance();
		return compartment != null ? compartment.getUnitsInstance() : null;
	}

	/**
	 * 
	 * @return the speciesTypeID of this {@link Species}. The empty String if it
	 *         is not set.
	 * @deprecated Only valid for SBML Level 2 Versions 2, 3, and 4.
	 */
	@Deprecated
	public String getSpeciesType() {
		return isSetSpeciesType() ? this.speciesTypeID : "";
	}

	/**
	 * 
	 * @return the SpeciesType instance which has the speciesTypeID of this
	 *         Species as id. Null if it doesn't exist.
	 * @deprecated Only valid for SBML Level 2 Versions 2, 3, and 4.
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

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Symbol#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 997;
		int hashCode = super.hashCode();
		hashCode += prime * Boolean.valueOf(amount).hashCode();
		if (isSetBoundaryCondition()) {
			hashCode += prime * boundaryCondition.hashCode();
		}
		if (isSetHasOnlySubstanceUnits()) {
			hashCode += prime * hasOnlySubstanceUnits.hashCode();
		}
		if (isSetCharge()) {
			hashCode += prime * charge.hashCode();
		}
		if (isSetSpeciesType()) {
			hashCode += prime * getSpeciesType().hashCode();
		}
		if (isSetCompartment()) {
			hashCode += prime * getCompartment().hashCode();
		}
		if (isSetSpatialSizeUnits()) {
			hashCode += prime * getSpatialSizeUnits().hashCode();
		}
		return hashCode;
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
	 * Initializes the default values using the current Level/Version configuration.
	 */
	public void initDefaults() {
		initDefaults(getLevel(), getVersion());
	}
	
	/**
	 * Initializes the default values of this Species.
	 */
	public void initDefaults(int level, int version) {
		amount = true;
		unitsID = null;
		if (level < 3) {
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
	 * @deprecated Only valid for SBML Level 2 Versions 1 and 2.
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
	@Deprecated
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

			if (getLevel() == 1) {
				if (attributeName.equals("units")) {
					setUnits(value);
					return true;
				}
			} else {
				if (attributeName.equals("initialConcentration")) {
					setInitialConcentration(StringTools.parseSBMLDouble(value));
					return true;
				} else if (attributeName.equals("substanceUnits")) {
					setUnits(value);
					return true;
				}
			}

			if (getLevel() > 1) {
				if (attributeName.equals("hasOnlySubstanceUnits")) {
					setHasOnlySubstanceUnits(StringTools
							.parseSBMLBoolean(value));
					return true;
				}
				if (attributeName.equals("constant")) {
					setConstant(StringTools.parseSBMLBoolean(value));
					return true;
				}
			}

			if (getLevel() == 2) {

				if (getVersion() < 3) {
					if (attributeName.equals("spatialSizeUnits")) {
						setSpatialSizeUnits(value);
						return true;
					}
				}

				if (getVersion() > 1) {
					if (attributeName.equals("speciesType")) {
						setSpeciesType(value);
						return true;
					}
				}
			}

			if (getLevel() < 3) {
				if (attributeName.equals("charge")) {
					setCharge(StringTools.parseSBMLInt(value));
					return true;
				}
			}

			if (getLevel() == 3) {
				if (attributeName.equals("conversionFactor")) {
					setConversionFactor(value);
					return true;
				}
			}

			if (attributeName.equals("compartment")) {
				setCompartment(value);
				return true;
			} else if (attributeName.equals("initialAmount")) {
				setInitialAmount(StringTools.parseSBMLDouble(value));
				return true;
			} else if (attributeName.equals("boundaryCondition")) {
				setBoundaryCondition(StringTools.parseSBMLBoolean(value));
				return true;

			}
		}

		return isAttributeRead;
	}

	/**
	 * Sets the boundaryCondition Boolean.
	 * 
	 * @param boundaryCondition
	 */
	public void setBoundaryCondition(boolean boundaryCondition) {
		Boolean oldBoundaryCondition = this.boundaryCondition;
		this.boundaryCondition = boundaryCondition;
		isSetBoundaryCondition = true;
		firePropertyChange(TreeNodeChangeEvent.boundaryCondition,
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
	 * @throws PropertyNotAvailableException if Level >= 3.
	 */
	@Deprecated
	public void setCharge(int charge) {
		if (3 <= getLevel()) {
			throw new PropertyNotAvailableException(TreeNodeChangeEvent.charge, this);
		}
		Integer oldCharge = this.charge;
		this.charge = Integer.valueOf(charge);
		isSetCharge = true;
		firePropertyChange(TreeNodeChangeEvent.charge, oldCharge, this.charge);
	}

	/**
	 * Sets the compartmentID of this {@link Species} to the id of
	 * 'compartment'.
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
			compartment = null; // If we pass the empty String or null, the
								// value is reset.
		}
		if ((compartment == null) || checkIdentifier(compartment)) {
			String oldCompartment = this.compartmentID;
			if ((compartment != null) && (compartment.trim().length() == 0)) {
				this.compartmentID = null;
			} else {
				this.compartmentID = compartment;
			}
			firePropertyChange(TreeNodeChangeEvent.compartment, oldCompartment,
					compartmentID);
		}
	}

	/**
	 * Sets the conversionFactorID of this {@link Species} to the id of
	 * 'conversionFactor'. This is only possible if Level >= 3.
	 * 
	 * @param conversionFactor
	 */
	public void setConversionFactor(Parameter conversionFactor) {
		setConversionFactor(conversionFactor != null ? conversionFactor.getId()
				: null);
	}

	/**
	 * Sets the conversionFactorID of this {@link Species} to
	 * 'conversionFactorID'. This is only possible if Level >= 3.
	 * 
	 * @param conversionFactorID
	 * @throws PropertyNotAvailableException if Level < 3.
	 */
	public void setConversionFactor(String conversionFactorID) {
		if (getLevel() < 3) {
			throw new PropertyNotAvailableException(
					TreeNodeChangeEvent.conversionFactor, this);
		}
		String oldConversionFactor = this.conversionFactorID;
		if ((conversionFactorID != null)
				&& (conversionFactorID.trim().length() == 0)) {
			this.conversionFactorID = null;
		} else {
			this.conversionFactorID = conversionFactorID;
		}
		firePropertyChange(TreeNodeChangeEvent.conversionFactor,
				oldConversionFactor, conversionFactorID);
	}

	/**
	 * Sets hasOnlySubstanceUnits Boolean
	 * 
	 * @param hasOnlySubstanceUnits
	 * @throws PropertyNotAvailableException if Level < 2.
	 */
	public void setHasOnlySubstanceUnits(boolean hasOnlySubstanceUnits) {
		if (getLevel() < 2) {
			throw new PropertyNotAvailableException(
					TreeNodeChangeEvent.hasOnlySubstanceUnits, this);
		}
		Boolean oldHasOnlySubstanceUnits = this.hasOnlySubstanceUnits;
		this.hasOnlySubstanceUnits = Boolean.valueOf(hasOnlySubstanceUnits);
		isSetHasOnlySubstanceUnits = true;
		firePropertyChange(TreeNodeChangeEvent.hasOnlySubstanceUnits,
				oldHasOnlySubstanceUnits, this.hasOnlySubstanceUnits);
	}

	/**
	 * Sets the initialAmount of this {@link Species}.
	 * 
	 * @param initialAmount
	 */
	public void setInitialAmount(double initialAmount) {
		if (!this.amount) {
			this.amount = true;
			firePropertyChange(TreeNodeChangeEvent.initialAmount, Boolean.FALSE,
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
		if (this.amount) {
			this.amount = false;
			firePropertyChange(TreeNodeChangeEvent.initialAmount, Boolean.TRUE,
					Boolean.FALSE);
		}
		setValue(initialConcentration);
	}

	/**
	 * Sets the spatialSizeUnitsID of this {@link Species} to
	 * 'spatialSizeUnits'.
	 * 
	 * @param spatialSizeUnits
	 * @deprecated This property is only valid for SBML Level 2 Versions 1 and
	 *             2.
	 * @throws PropertyNotAvailableException
	 *             for inapropriate Level/Version combinations.
	 */
	@Deprecated
	public void setSpatialSizeUnits(String spatialSizeUnits) {
		if ((getLevel() != 2) && ((1 != getVersion()) || (2 != getVersion()))) {
			throw new PropertyNotAvailableException(
					TreeNodeChangeEvent.spatialSizeUnits, this);
		}
		String oldSpatialSizeUnits = this.spatialSizeUnitsID;
		if ((spatialSizeUnits != null)
				&& (spatialSizeUnits.trim().length() == 0)) {
			this.spatialSizeUnitsID = null;
		} else {
			this.spatialSizeUnitsID = spatialSizeUnits;
		}
		firePropertyChange(TreeNodeChangeEvent.spatialSizeUnits,
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
		setSpatialSizeUnits(spatialSizeUnits != null ? spatialSizeUnits.getId()
				: null);
	}

	/**
	 * Sets the speciesTypeID of this {@link Species} to the id of
	 * 'speciesType'.
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
	 * @throws PropertyNotAvailableException
	 *             for inapropriate Level/Version combinations.
	 */
	@Deprecated
	public void setSpeciesType(String speciesType) {
		if ((getLevel() != 2) || (getLevel() == 2 && getVersion() == 1)) {
			throw new PropertyNotAvailableException(TreeNodeChangeEvent.speciesType,
					this);
		}
		if ((speciesType == null) || (speciesType.trim().length() == 0)
				|| checkIdentifier(speciesType)) {
			String oldSpeciesType = this.speciesTypeID;
			this.speciesTypeID = ((speciesType != null) && (speciesType.trim()
					.length() == 0)) ? null : speciesType;
			firePropertyChange(TreeNodeChangeEvent.speciesType, oldSpeciesType,
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
	 * 
	 * @deprecated
	 */
	@Deprecated
	public void unsetCharge() {
		Integer oldCharge = charge;
		charge = null;
		isSetCharge = false;
		firePropertyChange(TreeNodeChangeEvent.charge, oldCharge, this.charge);
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
	 * 
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
			attributes.put("initialAmount",
					StringTools.toString(en, getInitialAmount()));
		}
		if (isSetBoundaryCondition()) {
			attributes.put("boundaryCondition",
					Boolean.toString(getBoundaryCondition()));
		}

		if (1 < getLevel()) {
			if (isSetInitialConcentration() && !isSetInitialAmount()) {
				attributes.put("initialConcentration",
						StringTools.toString(en, getInitialConcentration()));
			}
			if (isSetSubstanceUnits()) {
				attributes.put("substanceUnits", getSubstanceUnits());
			}
			if (isSetHasOnlySubstanceUnits()) {
				attributes.put("hasOnlySubstanceUnits",
						Boolean.toString(getHasOnlySubstanceUnits()));
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
			if (getVersion() >= 2) {
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
