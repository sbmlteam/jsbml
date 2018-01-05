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
 * @author Alex-Thomas
 * @author Piero Dalle Pezze
 * @since 1.0
 */
public class CSGTranslation extends CSGTransformation {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(CSGTranslation.class);

  /**
   * 
   */
  private static final long serialVersionUID = 7030963917812170311L;
  /**
   * 
   */
  private Double translateX;
  /**
   * 
   */
  private Double translateY;
  /**
   * 
   */
  private Double translateZ;

  /**
   * 
   */
  public CSGTranslation() {
    super();
  }


  /**
   * @param csgt
   */
  public CSGTranslation(CSGTranslation csgt) {
    super(csgt);
    if (csgt.isSetTranslateX()) {
      setTranslateX(csgt.getTranslateX());
    }
    if (csgt.isSetTranslateY()) {
      setTranslateY(csgt.getTranslateY());
    }
    if (csgt.isSetTranslateZ()) {
      setTranslateZ(csgt.getTranslateZ());
    }
  }


  /**
   * @param level
   * @param version
   */
  public CSGTranslation(int level, int version) {
    super(level, version);
  }


  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public CSGTranslation(String id, int level, int version) {
    super(id, level, version);
  }


  @Override
  public CSGTranslation clone() {
    return new CSGTranslation(this);
  }


  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      CSGTranslation csgt = (CSGTranslation) object;

      equal &= csgt.isSetTranslateX() == isSetTranslateX();
      if (equal && isSetTranslateX()) {
        equal &= csgt.getTranslateX() == getTranslateX();
      }

      equal &= csgt.isSetTranslateY() == isSetTranslateY();
      if (equal && isSetTranslateY()) {
        equal &= csgt.getTranslateY() == getTranslateY();
      }

      equal &= csgt.isSetTranslateZ() == isSetTranslateZ();
      if (equal && isSetTranslateZ()) {
        equal &= csgt.getTranslateZ() == getTranslateZ();
      }
    }
    return equal;
  }


  /**
   * Returns the value of translateX
   *
   * @return the value of translateX
   */
  public double getTranslateX() {
    if (isSetTranslateX()) {
      return translateX;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.translateX, this);
  }


  /**
   * Returns whether translateX is set
   *
   * @return whether translateX is set
   */
  public boolean isSetTranslateX() {
    return translateX != null;
  }


  /**
   * Sets the value of translateX
   * @param translateX
   */
  public void setTranslateX(double translateX) {
    Double oldTranslateX = this.translateX;
    this.translateX = translateX;
    firePropertyChange(SpatialConstants.translateX, oldTranslateX, this.translateX);
  }


  /**
   * Unsets the variable translateX
   *
   * @return {@code true}, if translateX was set before,
   *         otherwise {@code false}
   */
  public boolean unsetTranslateX() {
    if (isSetTranslateX()) {
      Double oldTranslateX = translateX;
      translateX = null;
      firePropertyChange(SpatialConstants.translateX, oldTranslateX, translateX);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of translateY
   *
   * @return the value of translateY
   */
  public double getTranslateY() {
    if (isSetTranslateY()) {
      return translateY;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.translateY, this);
  }


  /**
   * Returns whether translateY is set
   *
   * @return whether translateY is set
   */
  public boolean isSetTranslateY() {
    return translateY != null;
  }


  /**
   * Sets the value of translateY
   * @param translateY
   */
  public void setTranslateY(double translateY) {
    Double oldTranslateY = this.translateY;
    this.translateY = translateY;
    firePropertyChange(SpatialConstants.translateY, oldTranslateY, this.translateY);
  }


  /**
   * Unsets the variable translateY
   *
   * @return {@code true}, if translateY was set before,
   *         otherwise {@code false}
   */
  public boolean unsetTranslateY() {
    if (isSetTranslateY()) {
      Double oldTranslateY = translateY;
      translateY = null;
      firePropertyChange(SpatialConstants.translateY, oldTranslateY, translateY);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of translateZ
   *
   * @return the value of translateZ
   */
  public double getTranslateZ() {
    if (isSetTranslateZ()) {
      return translateZ;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.translateZ, this);
  }


  /**
   * Returns whether translateZ is set
   *
   * @return whether translateZ is set
   */
  public boolean isSetTranslateZ() {
    return translateZ != null;
  }


  /**
   * Sets the value of translateZ
   * @param translateZ
   */
  public void setTranslateZ(double translateZ) {
    Double oldTranslateZ = this.translateZ;
    this.translateZ = translateZ;
    firePropertyChange(SpatialConstants.translateZ, oldTranslateZ, this.translateZ);
  }


  /**
   * Unsets the variable translateZ
   *
   * @return {@code true}, if translateZ was set before,
   *         otherwise {@code false}
   */
  public boolean unsetTranslateZ() {
    if (isSetTranslateZ()) {
      Double oldTranslateZ = translateZ;
      translateZ = null;
      firePropertyChange(SpatialConstants.translateZ, oldTranslateZ, translateZ);
      return true;
    }
    return false;
  }


  @Override
  public int hashCode() {
    final int prime = 1693;
    int hashCode = super.hashCode();
    if (isSetTranslateX()) {
      hashCode += prime * getTranslateX();
    }
    if (isSetTranslateY()) {
      hashCode += prime * getTranslateY();
    }
    if (isSetTranslateZ()) {
      hashCode += prime * getTranslateZ();
    }
    return hashCode;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetTranslateX()) {
      attributes.put(SpatialConstants.shortLabel + ":translateX", translateX.toString());
    }
    if (isSetTranslateY()) {
      attributes.put(SpatialConstants.shortLabel + ":translateY", translateY.toString());
    }
    if (isSetTranslateZ()) {
      attributes.put(SpatialConstants.shortLabel + ":translateZ", translateZ.toString());
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
      if (attributeName.equals(SpatialConstants.translateX)) {
        try {
          setTranslateX(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.translateX, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.translateY)) {
        try {
          setTranslateY(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.translateY, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.translateZ)) {
        try {
          setTranslateZ(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.translateZ, getElementName()));
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
    return SpatialConstants.csgTranslation;
  }

}
