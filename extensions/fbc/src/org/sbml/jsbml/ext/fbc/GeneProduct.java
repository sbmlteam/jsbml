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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.fbc;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * Defines a single gene product.
 *
 * <p>Introduced to FBC in version 2.<p/>
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.1
 */
public class GeneProduct extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -6117717488126260095L;

  /**
   * 
   */
  private String label;

  /**
   * 
   */
  private String associatedSpecies;


  /**
   * Returns the value of {@link #associatedSpecies}.
   *
   * @return the value of {@link #associatedSpecies}.
   */
  public String getAssociatedSpecies() {
    //TODO: if variable is boolean, create an additional "isVar"
    //TODO: return primitive data type if possible (e.g., int instead of Integer)
    if (isSetAssociatedSpecies()) {
      return associatedSpecies;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(FBCConstants.associatedSpecies, this);
  }


  /**
   * Returns whether {@link #associatedSpecies} is set.
   *
   * @return whether {@link #associatedSpecies} is set.
   */
  public boolean isSetAssociatedSpecies() {
    return associatedSpecies != null;
  }


  /**
   * Sets the value of associatedSpecies
   *
   * @param associatedSpecies the value of associatedSpecies to be set.
   */
  public void setAssociatedSpecies(String associatedSpecies) {
    String oldAssociatedSpecies = this.associatedSpecies;
    this.associatedSpecies = associatedSpecies;
    firePropertyChange(FBCConstants.associatedSpecies, oldAssociatedSpecies, this.associatedSpecies);
  }


  /**
   * Unsets the variable associatedSpecies.
   *
   * @return {@code true} if associatedSpecies was set before, otherwise {@code false}.
   */
  public boolean unsetAssociatedSpecies() {
    if (isSetAssociatedSpecies()) {
      String oldAssociatedSpecies = associatedSpecies;
      associatedSpecies = null;
      firePropertyChange(FBCConstants.associatedSpecies, oldAssociatedSpecies, associatedSpecies);
      return true;
    }
    return false;
  }

  /**
   * Creates a new {@link GeneProduct} instance.
   */
  public GeneProduct() {
    super();
    initDefaults();
  }

  /**
   * Creates a new {@link GeneProduct} instance.
   * 
   * @param nsb the instance to clone
   */
  public GeneProduct(GeneProduct nsb) {
    super(nsb);
  }

  /**
   * Creates a new {@link GeneProduct} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public GeneProduct(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Creates a new {@link GeneProduct} instance.
   * 
   * @param id the id
   */
  public GeneProduct(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a new {@link GeneProduct} instance.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   */
  public GeneProduct(String id, int level, int version) {
    super(id, level, version);
    initDefaults();
  }

  /**
   * Creates a new {@link GeneProduct} instance.
   * 
   * @param id the id
   * @param name the name
   * @param level the SBML level
   * @param version the SBML version
   */
  public GeneProduct(String id, String name, int level, int version) {
    super(id, name, level, version);
    initDefaults();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public GeneProduct clone() {
    return new GeneProduct(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = FBCConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (!super.equals(obj)) {
      return false;
    }
    GeneProduct other = (GeneProduct) obj;
    if (label == null) {
      if (other.label != null) {
        return false;
      }
    } else if (!label.equals(other.label)) {
      return false;
    }
    return true;
  }

  /**
   * Returns the value of {@link #label}
   *
   * @return the value of {@link #label}
   */
  public String getLabel() {
    if (isSetLabel()) {
      return label;
    }
    return "";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((label == null) ? 0 : label.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return true;
  }

  /**
   * Returns whether {@link #label} is set
   *
   * @return whether {@link #label} is set
   */
  public boolean isSetLabel() {
    return label != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(FBCConstants.geneProductIdentifier) || attributeName.equals(FBCConstants.label)) {
        setLabel(value);
      } else if (attributeName.equals(FBCConstants.associatedSpecies)) {
        setAssociatedSpecies(value);
      } else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }

  /**
   * Sets the value of {@link #label}
   * @param label
   */
  public void setLabel(String label) {
    String oldLabel = this.label;
    this.label = label;
    firePropertyChange(FBCConstants.label, oldLabel, this.label);
  }

  /**
   * Unsets the variable {@link #label}
   *
   * @return {@code true}, if {@link #label} was set before,
   *         otherwise {@code false}
   */
  public boolean unsetLabel() {
    if (isSetLabel()) {
      String oldLabel = label;
      label = null;
      firePropertyChange(FBCConstants.label, oldLabel, label);
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
      attributes.put(FBCConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(FBCConstants.shortLabel + ":name", getName());
    }
    if (isSetLabel()) {
      attributes.put(FBCConstants.shortLabel + ":" + FBCConstants.label, getLabel());
    }
    if (isSetAssociatedSpecies()) {
      attributes.put(FBCConstants.shortLabel + ":" + FBCConstants.associatedSpecies, getAssociatedSpecies());
    }

    return attributes;
  }

}
