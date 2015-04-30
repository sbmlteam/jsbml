/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
package org.sbml.jsbml.ext.multi;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.StringTools;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 16.10.2013
 */
public class SpeciesTypeInstance extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 1775590492963078468L;

  /**
   * 
   */
  private String speciesType;

  /**
   * 
   */
  private String compartmentReference;
  
  /**
   * 
   */
  private Integer occur;
  
  
  /**
   * Creates an SpeciesTypeInstance instance 
   */
  public SpeciesTypeInstance() {
    super();
    initDefaults();
  }


  /**
   * Creates a SpeciesTypeInstance instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public SpeciesTypeInstance(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a SpeciesTypeInstance instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public SpeciesTypeInstance(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a SpeciesTypeInstance instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public SpeciesTypeInstance(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a SpeciesTypeInstance instance with an id, name, level, and version.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public SpeciesTypeInstance(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(
      Integer.valueOf(MultiConstants.MIN_SBML_LEVEL),
      Integer.valueOf(MultiConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public SpeciesTypeInstance(SpeciesTypeInstance obj) {
    super(obj);

    // copy all class attributes
    if (obj.isSetSpeciesType()) {
      setSpeciesType(obj.getSpeciesType());
    }
    if (obj.isSetCompartmentReference()) {
      setCompartmentReference(obj.getCompartmentReference());
    }
    if (obj.isSetOccur()) {
      setOccur(obj.getOccur());
    }
  }


  /**
   * clones this class
   */
  public SpeciesTypeInstance clone() {
    return new SpeciesTypeInstance(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(MultiConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
    packageName = MultiConstants.shortLabel;
    setPackageVersion(-1);
  }

  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return true;
  }

  
  
  /**
   * Returns the value of {@link #speciesType}.
   *
   * @return the value of {@link #speciesType}.
   */
  public String getSpeciesType() {
    if (isSetSpeciesType()) {
      return speciesType;
    }

    return null;
  }


  /**
   * Returns whether {@link #speciesType} is set.
   *
   * @return whether {@link #speciesType} is set.
   */
  public boolean isSetSpeciesType() {
    return speciesType != null;
  }


  /**
   * Sets the value of speciesType
   *
   * @param speciesType the value of speciesType to be set.
   */
  public void setSpeciesType(String speciesType) {
    String oldSpeciesType = this.speciesType;
    this.speciesType = speciesType;
    firePropertyChange(MultiConstants.speciesType, oldSpeciesType, this.speciesType);
  }


  /**
   * Unsets the variable speciesType.
   *
   * @return {@code true} if speciesType was set before, otherwise {@code false}.
   */
  public boolean unsetSpeciesType() {
    if (isSetSpeciesType()) {
      String oldSpeciesType = this.speciesType;
      this.speciesType = null;
      firePropertyChange(MultiConstants.speciesType, oldSpeciesType, this.speciesType);
      return true;
    }
    return false;
  }

  
  /**
   * Returns the value of {@link #compartmentReference}.
   *
   * @return the value of {@link #compartmentReference}.
   */
  public String getCompartmentReference() {
    if (isSetCompartmentReference()) {
      return compartmentReference;
    }

    return null;
  }


  /**
   * Returns whether {@link #compartmentReference} is set.
   *
   * @return whether {@link #compartmentReference} is set.
   */
  public boolean isSetCompartmentReference() {
    return compartmentReference != null;
  }


  /**
   * Sets the value of compartmentReference
   *
   * @param compartmentReference the value of compartmentReference to be set.
   */
  public void setCompartmentReference(String compartmentReference) {
    String oldCompartmentReference = this.compartmentReference;
    this.compartmentReference = compartmentReference;
    firePropertyChange(MultiConstants.compartmentReference, oldCompartmentReference, this.compartmentReference);
  }


  /**
   * Unsets the variable compartmentReference.
   *
   * @return {@code true} if compartmentReference was set before, otherwise {@code false}.
   */
  public boolean unsetCompartmentReference() {
    if (isSetCompartmentReference()) {
      String oldCompartmentReference = this.compartmentReference;
      this.compartmentReference = null;
      firePropertyChange(MultiConstants.compartmentReference, oldCompartmentReference, this.compartmentReference);
      return true;
    }
    return false;
  }
  
  
  /**
   * Returns the value of {@link #occur}.
   *
   * @return the value of {@link #occur}.
   */
  public int getOccur() {
    if (isSetOccur()) {
      return occur;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(MultiConstants.occur, this);
  }


  /**
   * Returns whether {@link #occur} is set.
   *
   * @return whether {@link #occur} is set.
   */
  public boolean isSetOccur() {
    return occur != null;
  }


  /**
   * Sets the value of occur
   *
   * @param occur the value of occur to be set.
   */
  public void setOccur(int occur) {
    Integer oldOccur = this.occur;
    this.occur = occur;
    firePropertyChange(MultiConstants.occur, oldOccur, this.occur);
  }


  /**
   * Unsets the variable occur.
   *
   * @return {@code true} if occur was set before, otherwise {@code false}.
   */
  public boolean unsetOccur() {
    if (isSetOccur()) {
      Integer oldOccur = this.occur;
      this.occur = null;
      firePropertyChange(MultiConstants.occur, oldOccur, this.occur);
      return true;
    }
    return false;
  }
  
  
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    
    if (isSetId()) {
      attributes.remove("id");
      attributes.put(MultiConstants.shortLabel+ ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(MultiConstants.shortLabel+ ":name", getName());
    }

    if (isSetSpeciesType()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.speciesType, getSpeciesType());
    }
    if (isSetCompartmentReference()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.compartmentReference, getCompartmentReference());
    }
    if (isSetOccur()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.occur, occur.toString());
    }

    
    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;
      
      if (attributeName.equals(MultiConstants.speciesType)) {
        setSpeciesType(value);
      }
      if (attributeName.equals(MultiConstants.compartmentReference)) {
        setCompartmentReference(value);
      }
      if (attributeName.equals(MultiConstants.occur)) {
        setOccur(StringTools.parseSBMLInt(value));
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }
}
