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

import org.sbml.jsbml.AbstractSBase;

/**
 * 
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class SpeciesFeatureValue extends AbstractSBase {

  /**
   * Serial version identifier.
   */
  private static final long serialVersionUID = 1L;
  /**
   * 
   */
  private String value;


  /**
   * Creates an SpeciesFeatureValue instance
   */
  public SpeciesFeatureValue() {
    super();
    initDefaults();
  }


  /**
   * Creates a SpeciesFeatureValue instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public SpeciesFeatureValue(int level, int version) {
    super(level, version);
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public SpeciesFeatureValue(SpeciesFeatureValue obj) {
    super(obj);

    // copy all class attributes
    if (obj.isSetValue()) {
      setValue(obj.getValue());
    }
  }


  /**
   * clones this class
   */
  @Override
  public SpeciesFeatureValue clone() {
    return new SpeciesFeatureValue(this);
  }


  /**
   * Initializes the default values using the namespace.
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
    final int prime = 6113;
    int result = super.hashCode();
    result = prime * result + ((value == null) ? 0 : value.hashCode());
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
    SpeciesFeatureValue other = (SpeciesFeatureValue) obj;
    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }
    return true;
  }


  /**
   * Returns the value of {@link #value}.
   *
   * @return the value of {@link #value}.
   */
  public String getValue() {
    if (isSetValue()) {
      return value;
    }

    return null;
  }


  /**
   * Returns whether {@link #value} is set.
   *
   * @return whether {@link #value} is set.
   */
  public boolean isSetValue() {
    return value != null;
  }


  /**
   * Sets the value of value
   *
   * @param value the value of value to be set.
   */
  public void setValue(String value) {
    String oldValue = this.value;
    this.value = value;
    firePropertyChange(MultiConstants.value, oldValue, this.value);
  }


  /**
   * Unsets the variable value.
   *
   * @return {@code true} if value was set before, otherwise {@code false}.
   */
  public boolean unsetValue() {
    if (isSetValue()) {
      String oldValue = value;
      value = null;
      firePropertyChange(MultiConstants.value, oldValue, value);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value)
  {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {

      if (attributeName.equals(MultiConstants.value)) {
        setValue(value);
        isAttributeRead = true;
      }
    }

    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetValue()) {
      attributes.put(MultiConstants.shortLabel + ':' + MultiConstants.value, getValue());
    }

    return attributes;
  }

}
