/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2022 jointly by the following organizations:
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
import org.sbml.jsbml.xml.parsers.AbstractReaderWriter;

/**
 * @author Alex Thomas
 * @author Piero Dalle Pezze
 * @since 1.0
 */
public class CSGRotation extends CSGTransformation {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(CSGRotation.class);

  /**
   * 
   */
  private static final long serialVersionUID = -4799604235135248586L;
  /**
   * 
   */
  private Double rotateAxisX;
  /**
   * 
   */
  private Double rotateAxisY;
  /**
   * 
   */
  private Double rotateAxisZ;

  /**
   * 
   */
  private Double rotateAngleInRadians;

  /**
   * 
   */
  public CSGRotation() {
    super();
  }

  /**
   * @param csgt
   */
  public CSGRotation(CSGRotation csgt) {
    super(csgt);
    if (csgt.isSetRotateX()) {
      setRotateX(csgt.getRotateX());
    }
    if (csgt.isSetRotateY()) {
      setRotateY(csgt.getRotateY());
    }
    if (csgt.isSetRotateZ()) {
      setRotateZ(csgt.getRotateZ());
    }
    if (csgt.isSetRotateAngleInRadians()) {
      setRotateAngleInRadians(csgt.getRotateAngleInRadians());
    }
  }

  /**
   * @param level
   * @param version
   */
  public CSGRotation(int level, int version) {
    super(level, version);
  }


  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public CSGRotation(String id, int level, int version) {
    super(id, level, version);
  }


  @Override
  public CSGRotation clone() {
    return new CSGRotation(this);
  }


  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      CSGRotation csgt = (CSGRotation) object;

      equal &= csgt.isSetRotateX() == isSetRotateX();
      if (equal && isSetRotateX()) {
        equal &= csgt.getRotateX() == getRotateX();
      }

      equal &= csgt.isSetRotateY() == isSetRotateY();
      if (equal && isSetRotateY()) {
        equal &= csgt.getRotateY() == getRotateY();
      }

      equal &= csgt.isSetRotateZ() == isSetRotateZ();
      if (equal && isSetRotateZ()) {
        equal &= csgt.getRotateZ() == getRotateZ();
      }
      equal &= csgt.isSetRotateAngleInRadians() == isSetRotateAngleInRadians();
      if (equal && isSetRotateAngleInRadians()) {
        equal &= csgt.getRotateAngleInRadians() == getRotateAngleInRadians();
      }
    }
    return equal;
  }


  /**
   * Returns the value of rotateAxisX
   *
   * @return the value of rotateAxisX
   */
  public double getRotateX() {
    if (isSetRotateX()) {
      return rotateAxisX;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.rotateX, this);
  }


  /**
   * Returns whether rotateAxisX is set
   *
   * @return whether rotateAxisX is set
   */
  public boolean isSetRotateX() {
    return rotateAxisX != null;
  }


  /**
   * Sets the value of rotateAxisX
   * @param rotateAxisX
   */
  public void setRotateX(double rotateAxisX) {
    Double oldRotateAxisX = this.rotateAxisX;
    this.rotateAxisX = rotateAxisX;
    firePropertyChange(SpatialConstants.rotateX, oldRotateAxisX, this.rotateAxisX);
  }


  /**
   * Unsets the variable rotateAxisX
   *
   * @return {@code true}, if rotateAxisX was set before,
   *         otherwise {@code false}
   */
  public boolean unsetRotateX() {
    if (isSetRotateX()) {
      Double oldRotateAxisX = rotateAxisX;
      rotateAxisX = null;
      firePropertyChange(SpatialConstants.rotateX, oldRotateAxisX, rotateAxisX);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of rotateAxisY
   *
   * @return the value of rotateAxisY
   */
  public double getRotateY() {
    if (isSetRotateY()) {
      return rotateAxisY;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.rotateY, this);
  }


  /**
   * Returns whether rotateAxisY is set
   *
   * @return whether rotateAxisY is set
   */
  public boolean isSetRotateY() {
    return rotateAxisY != null;
  }


  /**
   * Sets the value of rotateAxisY
   * @param rotateAxisY
   */
  public void setRotateY(double rotateAxisY) {
    Double oldRotateAxisY = this.rotateAxisY;
    this.rotateAxisY = rotateAxisY;
    firePropertyChange(SpatialConstants.rotateY, oldRotateAxisY, this.rotateAxisY);
  }


  /**
   * Unsets the variable rotateAxisY
   *
   * @return {@code true}, if rotateAxisY was set before,
   *         otherwise {@code false}
   */
  public boolean unsetRotateY() {
    if (isSetRotateY()) {
      Double oldRotateAxisY = rotateAxisY;
      rotateAxisY = null;
      firePropertyChange(SpatialConstants.rotateY, oldRotateAxisY, rotateAxisY);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of rotateAxisZ
   *
   * @return the value of rotateAxisZ
   */
  public double getRotateZ() {
    if (isSetRotateZ()) {
      return rotateAxisZ;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.rotateZ, this);
  }


  /**
   * Returns whether rotateAxisZ is set
   *
   * @return whether rotateAxisZ is set
   */
  public boolean isSetRotateZ() {
    return rotateAxisZ != null;
  }


  /**
   * Sets the value of rotateAxisZ
   * @param rotateAxisZ
   */
  public void setRotateZ(double rotateAxisZ) {
    Double oldRotateAxisZ = this.rotateAxisZ;
    this.rotateAxisZ = rotateAxisZ;
    firePropertyChange(SpatialConstants.rotateZ, oldRotateAxisZ, this.rotateAxisZ);
  }


  /**
   * Unsets the variable rotateAxisZ
   *
   * @return {@code true}, if rotateAxisZ was set before,
   *         otherwise {@code false}
   */
  public boolean unsetRotateZ() {
    if (isSetRotateZ()) {
      Double oldRotateAxisZ = rotateAxisZ;
      rotateAxisZ = null;
      firePropertyChange(SpatialConstants.rotateZ, oldRotateAxisZ, rotateAxisZ);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of rotateAngleInRadians
   *
   * @return the value of rotateAngleInRadians
   */
  public double getRotateAngleInRadians() {
    if (isSetRotateAngleInRadians()) {
      return rotateAngleInRadians;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.rotateAngleInRadians, this);
  }


  /**
   * Returns whether rotateAngleInRadians is set
   *
   * @return whether rotateAngleInRadians is set
   */
  public boolean isSetRotateAngleInRadians() {
    return rotateAngleInRadians != null;
  }


  /**
   * Sets the value of rotateAngleInRadians
   * @param rotateAngleInRadians
   */
  public void setRotateAngleInRadians(double rotateAngleInRadians) {
    Double oldRotateAngleInRadians = this.rotateAngleInRadians;
    this.rotateAngleInRadians = rotateAngleInRadians;
    firePropertyChange(SpatialConstants.rotateAngleInRadians, oldRotateAngleInRadians, this.rotateAngleInRadians);
  }


  /**
   * Unsets the variable rotateAngleInRadians
   *
   * @return {@code true}, if rotateAngleInRadians was set before,
   *         otherwise {@code false}
   */
  public boolean unsetRotateAngleInRadians() {
    if (isSetRotateAngleInRadians()) {
      Double oldRotateAngleInRadians = rotateAngleInRadians;
      rotateAngleInRadians = null;
      firePropertyChange(SpatialConstants.rotateAngleInRadians, oldRotateAngleInRadians, rotateAngleInRadians);
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    final int prime = 1787;
    int hashCode = super.hashCode();
    if (isSetRotateX()) {
      hashCode += prime * getRotateX();
    }
    if (isSetRotateY()) {
      hashCode += prime * getRotateY();
    }
    if (isSetRotateZ()) {
      hashCode += prime * getRotateZ();
    }
    if (isSetRotateAngleInRadians()) {
      hashCode += prime * getRotateAngleInRadians();
    }
    return hashCode;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetRotateX()) {
      attributes.put(SpatialConstants.shortLabel + ":rotateX", rotateAxisX.toString());
    }
    if (isSetRotateY()) {
      attributes.put(SpatialConstants.shortLabel + ":rotateY", rotateAxisY.toString());
    }
    if (isSetRotateZ()) {
      attributes.put(SpatialConstants.shortLabel + ":rotateZ", rotateAxisZ.toString());
    }
    if (isSetRotateAngleInRadians()) {
      attributes.put(SpatialConstants.shortLabel + ":rotateAngleInRadians", rotateAngleInRadians.toString());
    }
    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.rotateX)) {
        try {
          setRotateX(StringTools.parseSBMLDoubleStrict(value));
        } catch (Exception e) {
          AbstractReaderWriter.processInvalidAttribute(attributeName, null, value, prefix, this);
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.rotateX, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.rotateY)) {
        try {
          setRotateY(StringTools.parseSBMLDoubleStrict(value));
        } catch (Exception e) {
          AbstractReaderWriter.processInvalidAttribute(attributeName, null, value, prefix, this);
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.rotateY, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.rotateZ)) {
        try {
          setRotateZ(StringTools.parseSBMLDoubleStrict(value));
        } catch (Exception e) {
          AbstractReaderWriter.processInvalidAttribute(attributeName, null, value, prefix, this);
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.rotateZ, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.rotateAngleInRadians)) {
        try {
          setRotateAngleInRadians(StringTools.parseSBMLDoubleStrict(value));
        } catch (Exception e) {
          AbstractReaderWriter.processInvalidAttribute(attributeName, null, value, prefix, this);
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.rotateAngleInRadians, getElementName()));
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
    return SpatialConstants.csgRotation;
  }

}
