/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
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
 * @version $Rev$
 * @since 1.1
 */
public class DenotedSpeciesTypeComponentIndex extends AbstractSBase {
  
  
  /**
   * 
   */
  private String speciesTypeComponentIndex;
  
  
  /**
   * Creates an DenotedSpeciesTypeComponentIndex instance 
   */
  public DenotedSpeciesTypeComponentIndex() {
    super();
    initDefaults();
  }


  /**
   * Creates a DenotedSpeciesTypeComponentIndex instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public DenotedSpeciesTypeComponentIndex(int level, int version) {
    super(level, version);
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public DenotedSpeciesTypeComponentIndex(DenotedSpeciesTypeComponentIndex obj) {
    super(obj);

    // SpeciesTypeComponentIndex
    if (obj.isSetSpeciesTypeComponentIndex()) {
      setSpeciesTypeComponentIndex(obj.getSpeciesTypeComponentIndex());
    }
  }


  /**
   * clones this class
   */
  public DenotedSpeciesTypeComponentIndex clone() {
    return new DenotedSpeciesTypeComponentIndex(this);
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
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 5669;
    int result = super.hashCode();
    result = prime
      * result
      + ((speciesTypeComponentIndex == null) ? 0
        : speciesTypeComponentIndex.hashCode());
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
    DenotedSpeciesTypeComponentIndex other = (DenotedSpeciesTypeComponentIndex) obj;
    if (speciesTypeComponentIndex == null) {
      if (other.speciesTypeComponentIndex != null) {
        return false;
      }
    } else if (!speciesTypeComponentIndex.equals(other.speciesTypeComponentIndex)) {
      return false;
    }
    return true;
  }


  /**
   * Returns the value of {@link #speciesTypeComponentIndex}.
   *
   * @return the value of {@link #speciesTypeComponentIndex}.
   */
  public String getSpeciesTypeComponentIndex() {
    if (isSetSpeciesTypeComponentIndex()) {
      return speciesTypeComponentIndex;
    }

    return null;
  }


  /**
   * Returns whether {@link #speciesTypeComponentIndex} is set.
   *
   * @return whether {@link #speciesTypeComponentIndex} is set.
   */
  public boolean isSetSpeciesTypeComponentIndex() {
    return speciesTypeComponentIndex != null;
  }


  /**
   * Sets the value of speciesTypeComponentIndex
   *
   * @param speciesTypeComponentIndex the value of speciesTypeComponentIndex to be set.
   */
  public void setSpeciesTypeComponentIndex(String speciesTypeComponentIndex) {
    String oldSpeciesTypeComponentIndex = this.speciesTypeComponentIndex;
    this.speciesTypeComponentIndex = speciesTypeComponentIndex;
    firePropertyChange(MultiConstants.speciesTypeComponentIndex, oldSpeciesTypeComponentIndex, this.speciesTypeComponentIndex);
  }


  /**
   * Unsets the variable speciesTypeComponentIndex.
   *
   * @return {@code true} if speciesTypeComponentIndex was set before, otherwise {@code false}.
   */
  public boolean unsetSpeciesTypeComponentIndex() {
    if (isSetSpeciesTypeComponentIndex()) {
      String oldSpeciesTypeComponentIndex = this.speciesTypeComponentIndex;
      this.speciesTypeComponentIndex = null;
      firePropertyChange(MultiConstants.speciesTypeComponentIndex, oldSpeciesTypeComponentIndex, this.speciesTypeComponentIndex);
      return true;
    }
    return false;
  }
  
  
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetSpeciesTypeComponentIndex()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.speciesTypeComponentIndex, getSpeciesTypeComponentIndex());
    }
    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(MultiConstants.speciesTypeComponentIndex)) {
        setSpeciesTypeComponentIndex(value);
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "DenotedSpeciesTypeComponentIndex [speciesTypeComponentIndex="
      + speciesTypeComponentIndex + "]";
  }
  
}
