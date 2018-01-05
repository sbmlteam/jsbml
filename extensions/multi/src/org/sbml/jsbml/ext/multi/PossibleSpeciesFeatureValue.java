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
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * Each state feature ({@link SpeciesFeatureType}) also requires the definition of all
 * the possible values it can take. A feature is
 * not always a boolean property, but can carry any number of
 * {@link PossibleSpeciesFeatureValue}. A {@link PossibleSpeciesFeatureValue} is identified by an id and an
 * optional name.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class PossibleSpeciesFeatureValue extends AbstractNamedSBase  implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -8059031235221209834L;

  /**
   * 
   */
  private String numericValue;

  /**
   * Creates an PossibleSpeciesFeatureValue instance
   */
  public PossibleSpeciesFeatureValue() {
    super();
    initDefaults();
  }


  /**
   * Creates a PossibleSpeciesFeatureValue instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public PossibleSpeciesFeatureValue(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a PossibleSpeciesFeatureValue instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public PossibleSpeciesFeatureValue(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a PossibleSpeciesFeatureValue instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public PossibleSpeciesFeatureValue(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a PossibleSpeciesFeatureValue instance with an id, name, level, and version.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public PossibleSpeciesFeatureValue(String id, String name, int level,
    int version) {
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
  public PossibleSpeciesFeatureValue(PossibleSpeciesFeatureValue obj) {
    super(obj);

    // numericalValue
    if (obj.isSetNumericValue()) {
      setNumericValue(obj.getNumericValue());
    }
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public AbstractSBase clone() {
    return new PossibleSpeciesFeatureValue(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
  }

  /**
   * 
   */
  public void initDefaults() {
    packageName = MultiConstants.shortLabel;
    setPackageVersion(-1);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 4547;
    int result = super.hashCode();
    result = prime * result
        + ((numericValue == null) ? 0 : numericValue.hashCode());
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
    PossibleSpeciesFeatureValue other = (PossibleSpeciesFeatureValue) obj;
    if (numericValue == null) {
      if (other.numericValue != null) {
        return false;
      }
    } else if (!numericValue.equals(other.numericValue)) {
      return false;
    }
    return true;
  }


  /**
   * Returns the value of {@link #numericValue}.
   *
   * @return the value of {@link #numericValue}.
   */
  public String getNumericValue() {
    if (isSetNumericValue()) {
      return numericValue;
    }

    return null;
  }


  /**
   * Returns whether {@link #numericValue} is set.
   *
   * @return whether {@link #numericValue} is set.
   */
  public boolean isSetNumericValue() {
    return numericValue != null;
  }


  /**
   * Sets the value of numericValue
   *
   * @param numericValue the value of numericValue to be set.
   */
  public void setNumericValue(String numericValue) {
    String oldNumericValue = this.numericValue;
    this.numericValue = numericValue;
    firePropertyChange(MultiConstants.numericValue, oldNumericValue, this.numericValue);
  }


  /**
   * Unsets the variable numericValue.
   *
   * @return {@code true} if numericValue was set before, otherwise {@code false}.
   */
  public boolean unsetNumericValue() {
    if (isSetNumericValue()) {
      String oldNumericValue = numericValue;
      numericValue = null;
      firePropertyChange(MultiConstants.numericValue, oldNumericValue, numericValue);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
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

    if (isSetNumericValue()) {
      attributes.put(MultiConstants.shortLabel+ ":" + MultiConstants.numericValue, getNumericValue());
    }

    return attributes;
  }

  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(MultiConstants.numericValue)) {
        setNumericValue(value);
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }
}
