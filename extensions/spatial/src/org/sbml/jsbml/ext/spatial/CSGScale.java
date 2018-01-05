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
 * @author Piero Dalle Pezze
 * @since 1.0
 */
public class CSGScale extends CSGTransformation {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(CSGScale.class);

  /**
   * 
   */
  private static final long serialVersionUID = 7021488698800177528L;

  /**
   * 
   */
  private Double scaleX;
  /**
   * 
   */
  private Double scaleY;
  /**
   * 
   */
  private Double scaleZ;

  /**
   * 
   */
  public CSGScale() {
    super();
  }


  /**
   * @param csgt
   */
  public CSGScale(CSGScale csgt) {
    super(csgt);
    if (csgt.isSetScaleX()) {
      setScaleX(csgt.getScaleX());
    }
    if (csgt.isSetScaleY()) {
      setScaleY(csgt.getScaleY());
    }
    if (csgt.isSetScaleZ()) {
      setScaleZ(csgt.getScaleZ());
    }
  }


  /**
   * @param level
   * @param version
   */
  public CSGScale(int level, int version) {
    super(level, version);
  }


  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public CSGScale(String id, int level, int version) {
    super(id, level, version);
  }


  @Override
  public CSGScale clone() {
    return new CSGScale(this);
  }


  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      CSGScale csgt = (CSGScale) object;

      equal &= csgt.isSetScaleX() == isSetScaleX();
      if (equal && isSetScaleX()) {
        equal &= csgt.getScaleX() == getScaleX();
      }

      equal &= csgt.isSetScaleY() == isSetScaleY();
      if (equal && isSetScaleY()) {
        equal &= csgt.getScaleY() == getScaleY();
      }

      equal &= csgt.isSetScaleZ() == isSetScaleZ();
      if (equal && isSetScaleZ()) {
        equal &= csgt.getScaleZ() == getScaleZ();
      }
    }
    return equal;
  }


  /**
   * Returns the value of scaleX
   *
   * @return the value of scaleX
   */
  public double getScaleX() {
    if (isSetScaleX()) {
      return scaleX;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.scaleX, this);
  }


  /**
   * Returns whether scaleX is set
   *
   * @return whether scaleX is set
   */
  public boolean isSetScaleX() {
    return scaleX != null;
  }


  /**
   * Sets the value of scaleX
   * @param scaleX
   */
  public void setScaleX(double scaleX) {
    Double oldScaleX = this.scaleX;
    this.scaleX = scaleX;
    firePropertyChange(SpatialConstants.scaleX, oldScaleX, this.scaleX);
  }


  /**
   * Unsets the variable scaleX
   *
   * @return {@code true}, if scaleX was set before,
   *         otherwise {@code false}
   */
  public boolean unsetScaleX() {
    if (isSetScaleX()) {
      Double oldScaleX = scaleX;
      scaleX = null;
      firePropertyChange(SpatialConstants.scaleX, oldScaleX, scaleX);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of scaleY
   *
   * @return the value of scaleY
   */
  public double getScaleY() {
    if (isSetScaleY()) {
      return scaleY;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.scaleY, this);
  }


  /**
   * Returns whether scaleY is set
   *
   * @return whether scaleY is set
   */
  public boolean isSetScaleY() {
    return scaleY != null;
  }


  /**
   * Sets the value of scaleY
   * @param scaleY
   */
  public void setScaleY(double scaleY) {
    Double oldScaleY = this.scaleY;
    this.scaleY = scaleY;
    firePropertyChange(SpatialConstants.scaleY, oldScaleY, this.scaleY);
  }


  /**
   * Unsets the variable scaleY
   *
   * @return {@code true}, if scaleY was set before,
   *         otherwise {@code false}
   */
  public boolean unsetScaleY() {
    if (isSetScaleY()) {
      Double oldScaleY = scaleY;
      scaleY = null;
      firePropertyChange(SpatialConstants.scaleY, oldScaleY, scaleY);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of scaleZ
   *
   * @return the value of scaleZ
   */
  public double getScaleZ() {
    if (isSetScaleZ()) {
      return scaleZ;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.scaleZ, this);
  }


  /**
   * Returns whether scaleZ is set
   *
   * @return whether scaleZ is set
   */
  public boolean isSetScaleZ() {
    return scaleZ != null;
  }


  /**
   * Sets the value of scaleZ
   * @param scaleZ
   */
  public void setScaleZ(double scaleZ) {
    Double oldScaleZ = this.scaleZ;
    this.scaleZ = scaleZ;
    firePropertyChange(SpatialConstants.scaleZ, oldScaleZ, this.scaleZ);
  }


  /**
   * Unsets the variable scaleZ
   *
   * @return {@code true}, if scaleZ was set before,
   *         otherwise {@code false}
   */
  public boolean unsetScaleZ() {
    if (isSetScaleZ()) {
      Double oldScaleZ = scaleZ;
      scaleZ = null;
      firePropertyChange(SpatialConstants.scaleZ, oldScaleZ, scaleZ);
      return true;
    }
    return false;
  }


  @Override
  public int hashCode() {
    final int prime = 1777;
    int hashCode = super.hashCode();
    if (isSetScaleX()) {
      hashCode += prime * getScaleX();
    }
    if (isSetScaleY()) {
      hashCode += prime * getScaleY();
    }
    if (isSetScaleZ()) {
      hashCode += prime * getScaleZ();
    }
    return hashCode;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetScaleX()) {
      attributes.put(SpatialConstants.shortLabel + ":scaleX", scaleX.toString());
    }
    if (isSetScaleY()) {
      attributes.put(SpatialConstants.shortLabel + ":scaleY", scaleY.toString());
    }
    if (isSetScaleZ()) {
      attributes.put(SpatialConstants.shortLabel + ":scaleZ", scaleZ.toString());
    }
    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.scaleX)) {
        try {
          setScaleX(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.scaleX, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.scaleY)) {
        try {
          setScaleY(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.scaleY, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.scaleZ)) {
        try {
          setScaleZ(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.scaleZ, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getElementName()
   */
  @Override
  public String getElementName() {
    return SpatialConstants.csgScale;
  }

}
