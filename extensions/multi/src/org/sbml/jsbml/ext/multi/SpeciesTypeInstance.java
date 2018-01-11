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
package org.sbml.jsbml.ext.multi;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.Species;

/**
 * Provides a way to construct {@link MultiSpeciesType}s and {@link Species} with multiple components.
 * 
 * <p> A speciesType can contain a list of instances of other speciesTypes which can also have their own
 * speciesTypeInstances, so the complete structure of a speciesType can be like a tree. A speciesType can not
 * contain an instance of any other speciesType that already contains the instance of it. In other words, circular references 
 * are not allowed when constructing speciesTypes. For example, if a speciesType 'A' contains the instance of
 * another speciesType 'B', 'B' must not contain the instance of 'A' anywhere in the complete structure of 'B'.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class SpeciesTypeInstance extends AbstractNamedSBase { //  implements UniqueNamedSBase - local to MultiSpeciesType ??

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
  
  
  
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 6089;
    int result = super.hashCode();
    result = prime * result + ((compartmentReference == null) ? 0
        : compartmentReference.hashCode());
    result = prime * result
        + ((speciesType == null) ? 0 : speciesType.hashCode());
    return result;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    SpeciesTypeInstance other = (SpeciesTypeInstance) obj;
    if (compartmentReference == null) {
      if (other.compartmentReference != null) {
        return false;
      }
    } else if (!compartmentReference.equals(other.compartmentReference)) {
      return false;
    }
    if (speciesType == null) {
      if (other.speciesType != null) {
        return false;
      }
    } else if (!speciesType.equals(other.speciesType)) {
      return false;
    }
    return true;
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
      else if (attributeName.equals(MultiConstants.compartmentReference)) {
        setCompartmentReference(value);
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }
}
