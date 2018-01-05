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

/**
 *
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class SpeciesFeatureChange extends AbstractNamedSBase {

  /**
   * Serial version identifier.
   */
  private static final long serialVersionUID = 1L;
  /**
   * 
   */
  private String reactantSpeciesFeature;

  /**
   * 
   */
  private String productSpeciesFeature;


  /**
   * Creates an SpeciesFeatureChange instance
   */
  public SpeciesFeatureChange() {
    super();
    initDefaults();
  }


  /**
   * Creates a SpeciesFeatureChange instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public SpeciesFeatureChange(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a SpeciesFeatureChange instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public SpeciesFeatureChange(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a SpeciesFeatureChange instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public SpeciesFeatureChange(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a SpeciesFeatureChange instance with an id, name, level, and version.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public SpeciesFeatureChange(String id, String name, int level, int version) {
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
  public SpeciesFeatureChange(SpeciesFeatureChange obj) {
    super(obj);

    // TODO: copy all class attributes

  }


  /**
   * clones this class
   */
  @Override
  public SpeciesFeatureChange clone() {
    return new SpeciesFeatureChange(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    packageName = MultiConstants.shortLabel;
    setPackageVersion(-1);
  }


  @Override
  public boolean isIdMandatory() {
    return false;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 6199;
    int result = super.hashCode();
    result = prime
        * result
        + ((productSpeciesFeature == null) ? 0 : productSpeciesFeature.hashCode());
    result = prime
        * result
        + ((reactantSpeciesFeature == null) ? 0
          : reactantSpeciesFeature.hashCode());
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
    SpeciesFeatureChange other = (SpeciesFeatureChange) obj;
    if (productSpeciesFeature == null) {
      if (other.productSpeciesFeature != null) {
        return false;
      }
    } else if (!productSpeciesFeature.equals(other.productSpeciesFeature)) {
      return false;
    }
    if (reactantSpeciesFeature == null) {
      if (other.reactantSpeciesFeature != null) {
        return false;
      }
    } else if (!reactantSpeciesFeature.equals(other.reactantSpeciesFeature)) {
      return false;
    }
    return true;
  }


  /**
   * Returns the value of {@link #reactantSpeciesFeature}.
   *
   * @return the value of {@link #reactantSpeciesFeature}.
   */
  public String getReactantSpeciesFeature() {
    if (isSetReactantSpeciesFeature()) {
      return reactantSpeciesFeature;
    }

    return null;
  }


  /**
   * Returns whether {@link #reactantSpeciesFeature} is set.
   *
   * @return whether {@link #reactantSpeciesFeature} is set.
   */
  public boolean isSetReactantSpeciesFeature() {
    return reactantSpeciesFeature != null;
  }


  /**
   * Sets the value of reactantSpeciesFeature
   *
   * @param reactantSpeciesFeature the value of reactantSpeciesFeature to be set.
   */
  public void setReactantSpeciesFeature(String reactantSpeciesFeature) {
    String oldReactantSpeciesFeature = this.reactantSpeciesFeature;
    this.reactantSpeciesFeature = reactantSpeciesFeature;
    firePropertyChange(MultiConstants.reactantSpeciesFeature, oldReactantSpeciesFeature, this.reactantSpeciesFeature);
  }


  /**
   * Unsets the variable reactantSpeciesFeature.
   *
   * @return {@code true} if reactantSpeciesFeature was set before, otherwise {@code false}.
   */
  public boolean unsetReactantSpeciesFeature() {
    if (isSetReactantSpeciesFeature()) {
      String oldReactantSpeciesFeature = reactantSpeciesFeature;
      reactantSpeciesFeature = null;
      firePropertyChange(MultiConstants.reactantSpeciesFeature, oldReactantSpeciesFeature, reactantSpeciesFeature);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of {@link #productSpeciesFeature}.
   *
   * @return the value of {@link #productSpeciesFeature}.
   */
  public String getProductSpeciesFeature() {
    if (isSetProductSpeciesFeature()) {
      return productSpeciesFeature;
    }

    return null;
  }


  /**
   * Returns whether {@link #productSpeciesFeature} is set.
   *
   * @return whether {@link #productSpeciesFeature} is set.
   */
  public boolean isSetProductSpeciesFeature() {
    return productSpeciesFeature != null;
  }


  /**
   * Sets the value of productSpeciesFeature
   *
   * @param productSpeciesFeature the value of productSpeciesFeature to be set.
   */
  public void setProductSpeciesFeature(String productSpeciesFeature) {
    String oldProductSpeciesFeature = this.productSpeciesFeature;
    this.productSpeciesFeature = productSpeciesFeature;
    firePropertyChange(MultiConstants.productSpeciesFeature, oldProductSpeciesFeature, this.productSpeciesFeature);
  }


  /**
   * Unsets the variable productSpeciesFeature.
   *
   * @return {@code true} if productSpeciesFeature was set before, otherwise {@code false}.
   */
  public boolean unsetProductSpeciesFeature() {
    if (isSetProductSpeciesFeature()) {
      String oldProductSpeciesFeature = productSpeciesFeature;
      productSpeciesFeature = null;
      firePropertyChange(MultiConstants.productSpeciesFeature, oldProductSpeciesFeature, productSpeciesFeature);
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

    if (isSetProductSpeciesFeature()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.productSpeciesFeature, getProductSpeciesFeature());
    }
    if (isSetReactantSpeciesFeature()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.reactantSpeciesFeature, getReactantSpeciesFeature());
    }

    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(MultiConstants.productSpeciesFeature)) {
        setProductSpeciesFeature(value);
      }
      else if (attributeName.equals(MultiConstants.reactantSpeciesFeature)) {
        setReactantSpeciesFeature(value);
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
