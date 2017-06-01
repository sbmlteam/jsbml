/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2017 jointly by the following organizations:
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
package org.sbml.jsbml.ext.distrib;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;

/**
 * 
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class DistribInput extends AbstractNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -5444621543802854220L;
  /**
   * 
   */
  private Integer index;

  /**
   * Creates an DistribInput instance
   */
  public DistribInput() {
    super();
    initDefaults();
  }


  /**
   * Creates a DistribInput instance with an id.
   * 
   * @param id
   */
  public DistribInput(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a DistribInput instance with a level and version.
   * 
   * @param level
   * @param version
   */
  public DistribInput(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a DistribInput instance with an id, level, and version.
   * 
   * @param id
   * @param level
   * @param version
   */
  public DistribInput(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a DistribInput instance with an id, name, level, and version.
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public DistribInput(String id, String name, int level, int version) {
    super(id, name, level, version);
    initDefaults();
  }


  /**
   * Clone constructor
   * @param obj
   */
  public DistribInput(DistribInput obj) {
    super(obj);

    if (obj.isSetIndex()) {
      setIndex(obj.getIndex());
    }
  }


  /**
   * clones this class
   */
  @Override
  public DistribInput clone() {
    return new DistribInput(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = DistribConstants.shortLabel;
  }


  /**
   * Returns the value of index
   *
   * @return the value of index
   */
  public int getIndex() {
    if (isSetIndex()) {
      return index;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(DistribConstants.index, this);
  }


  /**
   * Returns whether index is set
   *
   * @return whether index is set
   */
  public boolean isSetIndex() {
    return index != null;
  }


  /**
   * Sets the value of index
   * @param index
   */
  public void setIndex(int index) {
    Integer oldIndex = this.index;
    this.index = index;
    firePropertyChange(DistribConstants.index, oldIndex, this.index);
  }


  /**
   * Unsets the variable index
   *
   * @return {@code true}, if index was set before,
   *         otherwise {@code false}
   */
  public boolean unsetIndex() {
    if (isSetIndex()) {
      Integer oldIndex = index;
      index = null;
      firePropertyChange(DistribConstants.index, oldIndex, index);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 5261;
    int result = super.hashCode();
    result = prime * result + ((index == null) ? 0 : index.hashCode());
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
    DistribInput other = (DistribInput) obj;
    if (index == null) {
      if (other.index != null) {
        return false;
      }
    } else if (!index.equals(other.index)) {
      return false;
    }
    return true;
  }

  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetIndex()) {
      attributes.put(DistribConstants.shortLabel + ":" + DistribConstants.index, index.toString());
    }
    if (isSetId()) {
      attributes.remove("id");
      attributes.put(DistribConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(DistribConstants.shortLabel + ":name", getName());
    }

    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(DistribConstants.index)) {
        setIndex(StringTools.parseSBMLInt(value));
      } else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }

  @Override
  public boolean isIdMandatory() {
    return true;
  }

}
