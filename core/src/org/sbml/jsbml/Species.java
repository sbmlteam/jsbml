/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.xml.parsers.AbstractReaderWriter;

/**
 * Represents the species XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @since 0.8
 */
public class Species extends Symbol implements CompartmentalizedSBase {

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(Species.class);

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
   * @deprecated Only valid for SBML Level 2 Versions 1 and 2.
   */
  @Deprecated
  private String spatialSizeUnitsID;

  /**
   * Represents the 'speciesType' attribute of a Species element.
   */
  @Deprecated
  private String speciesTypeID;

  /**
   * Creates a Species instance. 
   * 
   * <p>By default, the charge, {@link #compartmentID},
   * speciesTypeID, conversionFactorID, hasOnlySubstanceUnits,
   * boundaryCondition are {@code null}.</p>
   */
  public Species() {
    super();
    initDefaults();
  }

  /**
   * Creates a Species instance from a level and version. 
   * 
   * <p>By default, the
   * charge, {@link #compartmentID}, speciesTypeID, conversionFactorID,
   * hasOnlySubstanceUnits, boundaryCondition are {@code null}.</p>
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public Species(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Creates a Species instance from a Species.
   * 
   * @param species the {@link Species} instance to be cloned. 
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
      spatialSizeUnitsID = species.spatialSizeUnitsID == null ? null : new String(species.spatialSizeUnitsID);
    }
    if (species.isSetConversionFactor()) {
      setConversionFactor(species.conversionFactorID);
    }
    if (species.isSetSpeciesType()) {
      setSpeciesType(species.getSpeciesType());
    }
  }

  /**
   * Creates a new {@link Species} instance with the given id.
   * 
   * @param id the species id
   */
  public Species(String id) {
    this();
    setId(id);
  }

  /**
   * Creates a Species instance from a level and version. 
   * 
   * <p>By default, the
   * charge, {@link #compartmentID}, speciesTypeID, conversionFactorID,
   * hasOnlySubstanceUnits, boundaryCondition are {@code null}.</p>
   * 
   * @param id the species id
   * @param level the SBML level
   * @param version the SBML version
   */
  public Species(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a new {@link Species} instance with the given id.
   * 
   * @param id the species id
   * @param name the species name
   * @param level the SBML level
   * @param version the SBML version
   */
  public Species(String id, String name, int level, int version) {
    super(id, name, level, version);
    initDefaults();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Symbol#clone()
   */
  @Override
  public Species clone() {
    return new Species(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#containsUndeclaredUnits()
   */
  @Override
  public boolean containsUndeclaredUnits() {
    boolean undeclared = super.containsUndeclaredUnits();
    if (undeclared && (getLevel() > 2)) {
      Model model = getModel();
      if ((model != null) && model.isSetSubstanceUnits()) {
        // In Level 3 a species inherits substance units from its model.
        // If the model declares the default substance units, the units of each
        // species are also declared.
        return false;
      }
    }
    return undeclared;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Symbol#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      Species s = (Species) object;
      equals &= s.isSetBoundaryCondition() == isSetBoundaryCondition();
      if (equals && isSetBoundaryCondition()) {
        equals &= s.getBoundaryCondition() == getBoundaryCondition();
      }
      equals &= s.isSetHasOnlySubstanceUnits() == isSetHasOnlySubstanceUnits();
      if (equals && isSetHasOnlySubstanceUnits()) {
        equals &= s.getHasOnlySubstanceUnits() == getHasOnlySubstanceUnits();
      }
      equals &= s.isSetCharge() == isSetCharge();
      if (equals && isSetCharge()) {
        equals &= s.getCharge() == getCharge();
      }
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
      
      equals &= s.isSetConversionFactor() == isSetConversionFactor();
      if (equals && isSetConversionFactor()) {
        equals &= s.getConversionFactor().equals(getConversionFactor());
      }
    }
    
    return equals;
  }

  /**
   * Returns the boundaryCondition Boolean of this Species.
   * 
   * @return the boundaryCondition Boolean of this Species.
   */
  public boolean getBoundaryCondition() {
    return isSetBoundaryCondition() ? boundaryCondition : false;
  }

  /**
   * Returns the charge of this {@link Species}.
   * 
   * @return the charge value of this Species if it is set, 0 otherwise.
   */
  @Deprecated
  public int getCharge() {
    return isSetCharge() ? charge : 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#getCompartment()
   */
  @Override
  public String getCompartment() {
    return isSetCompartment() ? compartmentID : "";
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#getCompartmentInstance()
   */
  @Override
  public Compartment getCompartmentInstance() {
    if (isSetCompartment()) {
      Model model = getModel();
      if (model != null) {
        return model.getCompartment(compartmentID);
      }
    }
    return null;
  }

  /**
   * Returns the conversionFactor of this Species.
   * 
   * @return the conversionFactor of this Species.
   */
  public String getConversionFactor() {
    return conversionFactorID;
  }

  /**
   * Returns the conversionFactor of this Species as a {@link Parameter} instance.
   * 
   * @return the Parameter instance which has the conversionFactor of this
   *         Species as id, null if it doesn't exist.
   */
  public Parameter getConversionFactorInstance() {
    if (getModel() == null) {
      return null;
    }
    return getModel().getParameter(conversionFactorID);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#getDerivedUnitDefinition()
   */
  @Override
  public UnitDefinition getDerivedUnitDefinition() {
    // We cannot use 'super.getDerivedUnitDefinition()' because the method Species.getDerivedUnits() cannot be used in this case
    // as it return null if hasOnlySubstanceUnits is 'false' and we just want to have the species units for this method
    UnitDefinition specUnit = getDerivedSubstanceUnitDefinition();
    
    // For SBML level below 3, hasOnlySubstanceUnits has a default value and we are not getting it with the method hasOnlySubstanceUnits()
    // so we need to use directly the class variable
    if ((isSetHasOnlySubstanceUnits() || getLevel() < 3) && !hasOnlySubstanceUnits) {
      Compartment compartment = getCompartmentInstance();
      
      if (compartment != null && (compartment.isSetSpatialDimensions() || getLevel() < 3) && (0d == compartment.getSpatialDimensions())) {
        return specUnit;
      }
      
      if ((specUnit != null) && (compartment != null)) {
        UnitDefinition sizeUnit; // = getSpatialSizeUnitsInstance();
        Model model = getModel();
        
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
          if (model != null) {
            UnitDefinition ud = model.isSetListOfUnitDefinitions() ? model.findIdentical(derivedUD) : null;
            if (ud != null) {
              return ud;
            }
          }
          return derivedUD;
        }
      }
    }
    return specUnit;
  }


  /**
   * Returns the derived species substance units, never dividing by the compartment units.
   * 
   * @return the derived species substance units, never dividing by the compartment units.
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#getDerivedUnitDefinition()
   */
  public UnitDefinition getDerivedSubstanceUnitDefinition() {
    // We cannot use 'super.getDerivedUnitDefinition()' because the method Species.getDerivedUnits() cannot be used in this case
    // as it return null if hasOnlySubstanceUnits is 'false' and we just want to have the species units for this method
    UnitDefinition specUnit = null;
    Model model = getModel();
    
    if (isSetUnitsInstance()) {
      specUnit = getUnitsInstance();
    } else {
      String derivedUnits = super.getDerivedUnits();

      // System.out.println("Species - getDerivedUnits " + getElementName() + " = " + derivedUnits + " (unitsID = " + unitsID + ", isSetUnits = " + isSetUnits() + ")");

      if ((model != null) && (derivedUnits != null) && !derivedUnits.isEmpty()) {
        specUnit = model.getUnitDefinition(derivedUnits);
      }
    }

    if ((specUnit == null) && (getLevel() > 2) && (model != null) && (model.isSetSubstanceUnits())) {
      // According to SBML specification of Level 3 Version 1, page 44, lines 20-22:
      specUnit = model.getSubstanceUnitsInstance();
    }
    
    return specUnit;
  }

  /**
   * Derives the unit of this {@link Species}.
   * 
   * <p>If the model that contains
   * this {@link Species} already contains a unit that is equivalent to the derived
   * unit, the corresponding identifier will be returned. In case that the
   * unit cannot be derived or that no equivalent unit exists within the
   * model, or if the model has not been defined yet, null will be returned.
   * In case that this quantity represents a basic {@link Unit.Kind} this
   * method will return the {@link String} representation of this
   * {@link Unit.Kind}.</p>
   * 
   * <p>In the specific case of a {@link Species}, if {@code hasOnlySubstanceUnits} is set to {@code false}
   * the method will always return {@code null} so prefer to use the method {@link #getDerivedUnitDefinition()}
   * which will return a the right result in this case.</p>
   * 
   * @return  a {@link String} that represent the id of a {@link UnitDefinition}. This {@link UnitDefinition}
   * represent the derived unit of this quantity. If it is not possible to derive a unit for this quantity
   * or if no equivalent {@link UnitDefinition} can be found in the {@link Model}, null is returned. 
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#getDerivedUnits()
   */
  @Override
  public String getDerivedUnits() {
    if (getLevel() > 2 && !isSetHasOnlySubstanceUnits()) {
      // In this case, we have no way to guess the units
      return null;
    }
    if (isSetHasOnlySubstanceUnits() && !hasOnlySubstanceUnits()) {
      // In this case, we could search in the model for a UnitDefinition corresponding to the units of the Species divided by the units of the Compartment.
      return null;
    }

    Compartment c = getCompartmentInstance();
    if ((c != null) && c.isSetSpatialDimensions() && (c.getSpatialDimensions() == 0d)) {
      return null;
    }
    return super.getDerivedUnits();
  }

  /* (non-Javadoc)
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
   * Returns the hasOnlySubstanceUnits Boolean of this Species.
   * 
   * @return the hasOnlySubstanceUnits Boolean of this Species.
   */
  public boolean getHasOnlySubstanceUnits() {
    return hasOnlySubstanceUnits();
  }

  /**
   * Returns the initialAmount of this {@link Species}.
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
   * Returns the initialConcentration of this {@link Species}.
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#getPredefinedUnitID()
   */
  @Override
  public String getPredefinedUnitID() {
    int level = getLevel();
    if (level < 3) {
      return "substance";
    }
    return null;
  }

  /**
   * Returns the spatialSizeUnits of this {@link Species}.
   * 
   * <p>If determined, this method first checks the explicitly set spatial size
   * units of this {@link Species}. If no such value is defined, it will
   * return the units of the surrounding {@link Compartment}. Only if this is
   * also not possible, an empty {@link String} will be returned.</p>
   * 
   * @return the spatialSizeUnits of this {@link Species}.
   * @deprecated Only valid for SBML Level 2 Versions 1 and 2.
   */
  @Deprecated
  public String getSpatialSizeUnits() {
    if (isSetSpatialSizeUnits()) {
      return spatialSizeUnitsID;
    }
    Compartment c = getCompartmentInstance();
    return c != null ? c.getUnits() : "";
  }

  /**
   * Returns the spatialSizeUnits {@link UnitDefinition} instance of this {@link Species}.
   * 
   * <p>Determines the spatial units of this {@link Species}. If the spatial
   * units have been set explicitly using 'spatialSizeUnits' the
   * corresponding {@link UnitDefinition} from the {@link Model} to which this
   * {@link Species} belongs will be returned. Otherwise, the size unit from
   * the surrounding {@link Compartment} of this {@link Species} will be
   * returned. If this also fails, {@code null} will be returned.</p>
   * 
   * @return The {@link UnitDefinition} instance which as the
   *         'spatialSizeUnits' of this {@link Species} as id or the
   *         size unit of the surrounding {@link Compartment}.
   *         {@code null} if it doesn't exist.
   * @deprecated Only valid for SBML Level 2 Versions 1 and 2.
   */
  @Deprecated
  public UnitDefinition getSpatialSizeUnitsInstance() {
    if (isSetSpatialSizeUnits()) {
      Model model = getModel();
      return model != null ? model
        .getUnitDefinition(spatialSizeUnitsID) : null;
    }
    Compartment compartment = getCompartmentInstance();
    return compartment != null ? compartment.getUnitsInstance() : null;
  }

  /**
   * Returns the speciesType of this {@link Species}.
   * 
   * @return the speciesType of this {@link Species}. The empty String if it
   *         is not set.
   * @deprecated Only valid for SBML Level 2 Versions 2, 3, and 4.
   */
  @Deprecated
  public String getSpeciesType() {
    return isSetSpeciesType() ? speciesTypeID : "";
  }

  /**
   * Returns the speciesType of this {@link Species} as a {@link SpeciesType}.
   * 
   * @return the SpeciesType instance which has the speciesType of this
   *         Species as id. Null if it doesn't exist.
   * @deprecated Only valid for SBML Level 2 Versions 2, 3, and 4.
   */
  @Deprecated
  public SpeciesType getSpeciesTypeInstance() {
    if (getModel() == null) {
      return null;
    }
    return getModel().getSpeciesType(speciesTypeID);
  }

  /**
   * Returns the substanceUnits of this Species.
   * 
   * @return the substanceUnits of this Species.
   */
  public String getSubstanceUnits() {
    String units = getUnits();
    if ((units == null) && (getLevel() > 2)) {
      Model model = getModel();
      if ((model != null) && model.isSetSubstanceUnits()) {
        units = model.getSubstanceUnits();
      }
    }
    return units;
  }

  /**
   * Returns the substanceUnits of this Species as a {@link UnitDefinition} instance.
   * 
   * @return The UnitsDefinition instance which has the substanceUnist of
   *         this Species as id.
   */
  public UnitDefinition getSubstanceUnitsInstance() {
    UnitDefinition ud = getUnitsInstance();
    if ((ud == null) && (getLevel() > 2)) {
      Model model = getModel();
      if ((model != null) && model.isSetSubstanceUnits()) {
        ud = model.getSubstanceUnitsInstance();
      }
    }
    return ud;
  }

  /* (non-Javadoc)
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
    if (isSetConversionFactor()) {
      hashCode += prime * getConversionFactor().hashCode();
    }
    
    return hashCode;
  }

  /**
   * Returns the 'hasOnlySubstanceUnits' for this {@link Species}.
   * 
   * @return the 'hasOnlySubstanceUnits' for this {@link Species}.
   */
  public boolean hasOnlySubstanceUnits() {
    return isSetHasOnlySubstanceUnits() ? hasOnlySubstanceUnits
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
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public void initDefaults(int level, int version) {
    initDefaults(level, version, false);
  }

  /**
   * Initializes the default values of this Species.
   * 
   * @param level the SBML level
   * @param version the SBML version
   * @param explicit whether to set the values explicitly or not
   */
  public void initDefaults(int level, int version, boolean explicit) {
    amount = true;
    unitsID = null;
    if (level < 3) {
      hasOnlySubstanceUnits = new Boolean(false);
      boundaryCondition = new Boolean(false);
      constant = new Boolean(false);
      isSetHasOnlySubstanceUnits = isSetBoundaryCondition = isSetConstant = explicit;
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#isCompartmentMandatory()
   */
  @Override
  public boolean isCompartmentMandatory() {
    return true;
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
   * @return {@code true} if the {@link #boundaryCondition} of this
   *         {@link Species} is not {@code null}.
   */
  public boolean isSetBoundaryCondition() {
    return isSetBoundaryCondition;
  }

  /**
   * 
   * @return {@code true} if the charge of this Species if not {@code null}.
   */
  public boolean isSetCharge() {
    return isSetCharge;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#isSetCompartment()
   */
  @Override
  public boolean isSetCompartment() {
    return compartmentID != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#isSetCompartmentInstance()
   */
  @Override
  public boolean isSetCompartmentInstance() {
    return getCompartmentInstance() != null;
  }

  /**
   * 
   * @return {@code true} if the conversionFactorID of this Species is not {@code null}.
   */
  public boolean isSetConversionFactor() {
    return conversionFactorID != null;
  }

  /**
   * 
   * @return {@code true} if the Parameter which has the conversionFactorID of this
   *         Species as id is not {@code null}.
   */
  public boolean isSetConversionFactorInstance() {
    if (getModel() == null) {
      return false;
    }
    return getModel().getParameter(conversionFactorID) != null;
  }

  /**
   * 
   * @return {@code true} if the hasOnlySubstanceUnits of this Species is not {@code null}.
   */
  public boolean isSetHasOnlySubstanceUnits() {
    return isSetHasOnlySubstanceUnits;
  }

  /**
   * 
   * @return {@code true} if an initial amount has been set for this species.
   */
  public boolean isSetInitialAmount() {
    return amount && isSetValue();
  }

  /**
   * 
   * @return {@code true} if an initial concentration has been set for this
   *         species.
   */
  public boolean isSetInitialConcentration() {
    return !amount && isSetValue();
  }

  /**
   * @return {@code true} if the spatialSizeUnits of this {@link Species}
   *         is not {@code null}.
   * @deprecated Only valid for SBML Level 2 Versions 1 and 2.
   */
  @Deprecated
  public boolean isSetSpatialSizeUnits() {
    return spatialSizeUnitsID != null;
  }

  /**
   * @return {@code true} if the {@link UnitDefinition} which has the
   *         spatialSizeUnitsID of
   *         this {@link Species} as id is not {@code null}.
   * @deprecated Only valid for SBML Level 2 Versions 1 and 2.
   */
  @Deprecated
  public boolean isSetSpatialSizeUnitsInstance() {
    if (getModel() == null) {
      return false;
    }
    return getModel().getUnitDefinition(spatialSizeUnitsID) != null;
  }

  /**
   * 
   * @return {@code true} if the speciesTypeID of this Species is not {@code null}.
   */
  @Deprecated
  public boolean isSetSpeciesType() {
    return speciesTypeID != null;
  }

  /**
   * 
   * @return {@code true} if the SpeciesType instance which has the speciesTypeID of
   *         this Species as id is not {@code null}.
   */
  @Deprecated
  public boolean isSetSpeciesTypeInstance() {
    if (getModel() == null) {
      return false;
    }
    return getModel().getSpeciesType(speciesTypeID) != null;
  }

  /**
   * 
   * @return {@code true} if the substanceUnitsID of this species is not {@code null}.
   */
  public boolean isSetSubstanceUnits() {
    return isSetUnits();
  }

  /**
   * 
   * @return {@code true} if the UnitDefinition which has the substanceUnitsID of this
   *         Species as id is not {@code null}.
   */
  public boolean isSetSubstanceUnitsInstance() {
    return isSetUnitsInstance();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)(
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

      if (attributeName.equals("spatialSizeUnits")) {
        setSpatialSizeUnits(value);
        return true;
      }

      if (attributeName.equals("speciesType")) {
        setSpeciesType(value);
        return true;
      }

        if (attributeName.equals("charge")) {
          setCharge(StringTools.parseSBMLInt(value));
          return true;
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
   * @param boundaryCondition the boundary condition
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
   * @param charge the charge
   * @deprecated Only defined in SBML Level 1, Version 1 and 2, and Level 2
   *             Version 1. Since Level 2 Version 2 it has been marked as a
   *             deprecated property, but has been completely removed in SBML
   *             Level 3.
   * @throws PropertyNotAvailableException if Level &gt;= 3.
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#setCompartment(org.sbml.jsbml.Compartment)
   */
  @Override
  public boolean setCompartment(Compartment compartment) {
    if (compartment != null) {
      return setCompartment(compartment.getId());
    }
    return unsetCompartment();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#setCompartment(java.lang.String)
   */
  @Override
  public boolean setCompartment(String compartment) {
    if ((compartment != null) && (compartment.trim().length() == 0)) {
      compartment = null;
      // If we pass the empty String or null, the value is reset.
    }
    // TODO - do a call to the offline validator once all the species attribute checks are in place.
    
    if (!isReadingInProgress() && compartment != null) {
      // This method will throw IllegalArgumentException if the given id does not respect the SId syntax
      checkIdentifier(compartment);
    }
    
    String oldCompartment = compartmentID;
    compartmentID = compartment;
    firePropertyChange(TreeNodeChangeEvent.compartment, oldCompartment, compartmentID);
    
    return true;
  }

  /**
   * Sets the conversionFactor of this {@link Species} to the id of
   * 'conversionFactor'. This is only possible if Level &gt;= 3.
   * 
   * @param conversionFactor the conversion factor
   */
  public void setConversionFactor(Parameter conversionFactor) {
    setConversionFactor(conversionFactor != null ? conversionFactor.getId()
      : null);
  }

  /**
   * Sets the conversionFactor of this {@link Species} to
   * 'conversionFactorID'. This is only possible if Level &gt;= 3.
   * 
   * @param conversionFactorID the conversion factor
   * @throws PropertyNotAvailableException if Level &lt; 3.
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
   * @param hasOnlySubstanceUnits the has only substance units value to be set
   * @throws PropertyNotAvailableException if Level &lt; 2.
   */
  public void setHasOnlySubstanceUnits(boolean hasOnlySubstanceUnits) {
    if (!isReadingInProgress() && getLevel() < 2) {
      throw new PropertyNotAvailableException(
        TreeNodeChangeEvent.hasOnlySubstanceUnits, this);
    }
    if (!isReadingInProgress() && hasOnlySubstanceUnits && isSetSpatialSizeUnits()) {
      String ud = isSetUnitsInstance() ? UnitDefinition.printUnits(
        getSpatialSizeUnitsInstance(), true) : getSpatialSizeUnits();
        throw new SBMLException(MessageFormat.format(
          "Cannot define that species {0} with spatial size units {1} has only substance units.",
          toString(), ud));
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
   * @param initialAmount the initial amount
   */
  public void setInitialAmount(double initialAmount) {
    // store initialConcentration in user define object to allow validation of incorrect SBML files
    if (isReadingInProgress() && isSetInitialConcentration()) {
      // TODO - replace by processInvalidAttribute and change the corresponding rule(s)
      AbstractReaderWriter.processUnknownAttribute("initialConcentration", "", getInitialConcentration() + "", "", this);
    }
    if (!amount) {
      amount = true;
      firePropertyChange(TreeNodeChangeEvent.initialAmount, Boolean.FALSE,
        Boolean.TRUE);
    }
    setValue(initialAmount);
  }

  /**
   * Sets the initialConcentration of this {@link Species}.
   * 
   * @param initialConcentration the initial concentration
   */
  public void setInitialConcentration(double initialConcentration) {
    // store initialAmount in user define object to allow validation of incorrect SBML files
    if (isReadingInProgress() && isSetInitialAmount()) {
      // TODO - replace by processInvalidAttribute and change the corresponding rule(s)
      AbstractReaderWriter.processUnknownAttribute("initialAmount", "", getInitialAmount() + "", "", this);
    }
    if (amount) {
      amount = false;
      firePropertyChange(TreeNodeChangeEvent.initialAmount, Boolean.TRUE,
        Boolean.FALSE);
    }
    setValue(initialConcentration);
  }

  /**
   * Sets the spatialSizeUnitsID of this {@link Species} to
   * 'spatialSizeUnits'.
   * 
   * @param spatialSizeUnits the unit to set
   * @deprecated This property is only valid for SBML Level 2 Versions 1 and
   *             2.
   * @throws PropertyNotAvailableException
   *         for inappropriate Level/Version combinations.
   * @throws SBMLException
   *         in case that {@link #hasOnlySubstanceUnits()} is set to
   *         {@code true} or the spatial dimensions of the surrounding
   *         compartment are zero.
   */
  @Deprecated
  public void setSpatialSizeUnits(String spatialSizeUnits)
      throws PropertyNotAvailableException, SBMLException {
    // TODO - replace all the checks by a call to the offline validator once all the species attribute checks are in place.

    if (!isReadingInProgress() && (getLevel() != 2) && ((1 != getVersion()) || (2 != getVersion()))) {
      throw new PropertyNotAvailableException(
        TreeNodeChangeEvent.spatialSizeUnits, this);
    }
    /*
     * For the rules for semantic check of this attribute see specifications of
     * SBML Level 2 Version 1, page 20, and
     * SBML Level 2 Version 2, page 45, line 5
     */
    if (!isReadingInProgress() && hasOnlySubstanceUnits()) {
      throw new SBMLException(MessageFormat.format(
        "Cannot set spatial size units on species {0} because it has only substance units.",
        toString()));
    }
    Compartment c = getCompartmentInstance();
    if (!isReadingInProgress() && (c != null) && (c.getSpatialDimensions() == 0d)) {
      // Note that it is still possible to change the dimensions of the
      // compartment later on or to change the entire compartment.
      throw new SBMLException(MessageFormat.format(
        "Cannot set spatial size units on species {0} because its surrounding compartment has zero dimensions.",
        toString()));
    }
    
    // end of the checks and start of the simple setter
    String oldSpatialSizeUnits = spatialSizeUnitsID;
    if ((spatialSizeUnits != null) && (spatialSizeUnits.trim().length() == 0)) {
      spatialSizeUnitsID = null;
    } else {
      spatialSizeUnitsID = spatialSizeUnits;
    }
    firePropertyChange(TreeNodeChangeEvent.spatialSizeUnits, oldSpatialSizeUnits, spatialSizeUnitsID);
  }

  /**
   * Sets the spatialSizeUnitsID of this {@link Species} to the id of
   * 'spatialSizeUnits'.
   * 
   * @param spatialSizeUnits the unit to set
   * @deprecated Only valid for SBML Level 2 Versions 1 and 2.
   * @throws PropertyNotAvailableException
   *         for inappropriate Level/Version combinations.
   * @throws SBMLException
   *         in case that {@link #hasOnlySubstanceUnits()} is set to
   *         {@code true} or the spatial dimensions of the surrounding
   *         compartment are zero.
   */
  @Deprecated
  public void setSpatialSizeUnits(UnitDefinition spatialSizeUnits)
      throws PropertyNotAvailableException, SBMLException {
    setSpatialSizeUnits(spatialSizeUnits != null ? spatialSizeUnits.getId() : null);
  }

  /**
   * Sets the speciesTypeID of this {@link Species} to the id of
   * 'speciesType'.
   * 
   * @param speciesType the species type to set
   * @deprecated This property is only valid for SBML Level 2 from Version 2.
   */
  @Deprecated
  public void setSpeciesType(SpeciesType speciesType) {
    setSpeciesType(speciesType != null ? speciesType.getId() : null);
  }

  /**
   * Sets the speciesTypeID of this {@link Species} to 'speciesType'.
   * 
   * @param speciesType the species type to set
   * @deprecated Only valid in SBML Level 2 from Version 2.
   * @throws PropertyNotAvailableException
   *             for inappropriate Level/Version combinations.
   */
  @Deprecated
  public void setSpeciesType(String speciesType) {
    if ((getLevel() != 2) || ((getLevel() == 2) && (getVersion() == 1))) {
      throw new PropertyNotAvailableException(TreeNodeChangeEvent.speciesType,
        this);
    }
    if ((speciesType == null) || (speciesType.trim().length() == 0)
        || checkIdentifier(speciesType)) {
      String oldSpeciesType = speciesTypeID;
      speciesTypeID = ((speciesType != null) && (speciesType.trim()
          .length() == 0)) ? null : speciesType;
      firePropertyChange(TreeNodeChangeEvent.speciesType, oldSpeciesType,
        speciesTypeID);
    }
  }

  /**
   * Sets the substanceUnitsID to 'unit'.
   * 
   * @param unit the unit to set
   */
  public void setSubstanceUnits(String unit) {
    setUnits(unit);
  }

  /**
   * Sets the substanceUnits.
   * 
   * @param unit the unit to set
   */
  public void setSubstanceUnits(Unit unit) {
    setUnits(unit);
  }

  /**
   * Sets the substanceUnits.
   * 
   * @param unitKind the unit to set
   */
  public void setSubstanceUnits(Unit.Kind unitKind) {
    setUnits(unitKind);
  }

  /**
   * Sets the substanceUnitsID to the id of 'units'.
   * 
   * @param units the unit to set
   */
  public void setSubstanceUnits(UnitDefinition units) {
    setUnits(units);
  }

  /**
   * Unsets the charge of this Species
   * 
   * @deprecated  Only defined in SBML Level 1, Version 1 and 2, and Level 2
   *             Version 1. Since Level 2 Version 2 it has been marked as a
   *             deprecated property, but has been completely removed in SBML
   *             Level 3.
   */
  @Deprecated
  public void unsetCharge() {
    Integer oldCharge = charge;
    charge = null;
    isSetCharge = false;
    firePropertyChange(TreeNodeChangeEvent.charge, oldCharge, charge);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.CompartmentalizedSBase#unsetCompartment()
   */
  @Override
  public boolean unsetCompartment() {
    return setCompartment((String) null);
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
   * @deprecated Only valid for SBML Level 2 Versions 1 and 2.
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#writeXMLAttributes(
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    Locale en = Locale.ENGLISH;
    if (isSetCompartment()) {
      attributes.put(TreeNodeChangeEvent.compartment, getCompartment());
    }
    if (isSetInitialAmount()) {
      attributes.put(TreeNodeChangeEvent.initialAmount,
        StringTools.toString(en, getInitialAmount()));
    }
    if (isSetBoundaryCondition()) {
      attributes.put(TreeNodeChangeEvent.boundaryCondition,
        Boolean.toString(getBoundaryCondition()));
    }

    if (1 < getLevel()) {
      if (isSetInitialConcentration() && !isSetInitialAmount()) {
        attributes.put("initialConcentration",
          StringTools.toString(en, getInitialConcentration()));
      }
      if (isSetSubstanceUnits()) {
        attributes.put(TreeNodeChangeEvent.substanceUnits, getSubstanceUnits());
      }
      if (isSetHasOnlySubstanceUnits()) {
        attributes.put(TreeNodeChangeEvent.hasOnlySubstanceUnits,
          Boolean.toString(getHasOnlySubstanceUnits()));
      }
      if (isSetConstant()) {
        attributes.put(TreeNodeChangeEvent.constant, Boolean.toString(getConstant()));
      }
    }
    if (getLevel() < 3) {
      if (isSetCharge) {
        attributes.put(TreeNodeChangeEvent.charge, Integer.toString(getCharge()));
      }
    }
    if (getLevel() == 2) {
      if ((getVersion() == 1) || (getVersion() == 2)) {
        if (isSetSpatialSizeUnits()) {
          if (hasOnlySubstanceUnits()) {
            logger.warn(MessageFormat.format(
              "Attribute spatialSizeUnits got lost because species {0} has only substance units.",
              toString()));
          } else if (isSetCompartmentInstance() && (getCompartmentInstance().getSpatialDimensions() == 0d)) {
            logger.warn(MessageFormat.format(
              "Attribute spatialSizeUnits got lost because species {0} is surrounded by a compartment of zero dimensions.",
              toString()));
          } else {
            attributes.put("spatialSizeUnits", getSpatialSizeUnits());
          }
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
