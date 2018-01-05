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
package org.sbml.jsbml.ext.spatial;

import java.text.MessageFormat;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public abstract class GeometryDefinition extends AbstractSpatialNamedSBase {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(GeometryDefinition.class);

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 211166389798247646L;

  /**
   * 
   */
  private Boolean isActive;

  /**
   * 
   */
  public GeometryDefinition() {
    super();
  }

  /**
   * @param gd
   */
  public GeometryDefinition(GeometryDefinition gd) {
    super(gd);
    if (gd.isSetIsActive()) {
      setIsActive(gd.getIsActive());
    }
  }


  /**
   * @param level
   * @param version
   */
  public GeometryDefinition(int level, int version) {
    super(level, version);
  }


  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public GeometryDefinition(String id, int level, int version) {
    super(id, level, version);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      GeometryDefinition gd = (GeometryDefinition) object;
      equal &= gd.isSetIsActive() == isSetIsActive();
      if (equal && isSetIsActive()) {
        equal &= gd.getIsActive() == getIsActive();
      }
    }
    return equal;
  }

  /**
   * Returns the value of isActive
   *
   * @return the value of isActive
   */
  public Boolean getIsActive() {
    if (isSetIsActive()) {
      return isActive;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.isActive, this);
  }

  /**
   * @return
   */
  public boolean isActive() {
    return getIsActive();
  }

  /**
   * Returns whether isActive is set
   *
   * @return whether isActive is set
   */
  public boolean isSetIsActive() {
    return isActive != null;
  }

  /**
   * Sets the value of isActive
   * @param isActive
   */
  public void setIsActive(boolean isActive) {
    Boolean oldIsActive = this.isActive;
    this.isActive = isActive;
    firePropertyChange(SpatialConstants.isActive, oldIsActive, this.isActive);
  }

  /**
   * Unsets the variable isActive
   *
   * @return {@code true}, if isActive was set before,
   *         otherwise {@code false}
   */
  public boolean unsetIsActive() {
    if (isSetIsActive()) {
      Boolean oldIsActive = isActive;
      isActive = null;
      firePropertyChange(SpatialConstants.isActive, oldIsActive, isActive);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1559;
    int hashCode = super.hashCode();
    if (isSetIsActive()) {
      hashCode += prime * getIsActive().hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetIsActive()) {
      attributes.remove("isActive");
      attributes.put(SpatialConstants.shortLabel + ":isActive", getIsActive().toString());
    }
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.isActive)) {
        try {
          setIsActive(StringTools.parseSBMLBoolean(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.isActive, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
