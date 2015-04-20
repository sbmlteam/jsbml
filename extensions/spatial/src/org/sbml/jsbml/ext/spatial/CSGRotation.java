/*
 * $Id: CSGRotation.java 2109 2015-01-05 04:50:45Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/extensions/spatial/src/org/sbml/jsbml/ext/spatial/CSGRotation.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2015 jointly by the following organizations:
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

import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Alex Thomas
 * @version $Rev: 2109 $
 * @since 1.0
 * @date Jan 20, 2014
 */
public class CSGRotation extends CSGTransformation {

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
    if (csgt.isSetRotateAxisX()) {
      rotateAxisX = new Double(csgt.getRotateAxisX());
    }
    if (csgt.isSetRotateAxisY()) {
      rotateAxisY = new Double(csgt.getRotateAxisY());
    }
    if (csgt.isSetRotateAxisZ()) {
      rotateAxisZ = new Double(csgt.getRotateAxisZ());
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

      equal &= csgt.isSetRotateAxisX() == isSetRotateAxisX();
      if (equal && isSetRotateAxisX()) {
        equal &= csgt.getRotateAxisX() == getRotateAxisX();
      }

      equal &= csgt.isSetRotateAxisY() == isSetRotateAxisY();
      if (equal && isSetRotateAxisY()) {
        equal &= csgt.getRotateAxisY() == getRotateAxisY();
      }

      equal &= csgt.isSetRotateAxisZ() == isSetRotateAxisZ();
      if (equal && isSetRotateAxisZ()) {
        equal &= csgt.getRotateAxisZ() == getRotateAxisZ();
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
  public double getRotateAxisX() {
    if (isSetRotateAxisX()) {
      return rotateAxisX;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.rotateAxisX, this);
  }


  /**
   * Returns whether rotateAxisX is set
   *
   * @return whether rotateAxisX is set
   */
  public boolean isSetRotateAxisX() {
    return rotateAxisX != null;
  }


  /**
   * Sets the value of rotateAxisX
   * @param rotateAxisX
   */
  public void setRotateAxisX(double rotateAxisX) {
    double oldRotateAxisX = this.rotateAxisX;
    this.rotateAxisX = rotateAxisX;
    firePropertyChange(SpatialConstants.rotateAxisX, oldRotateAxisX, this.rotateAxisX);
  }


  /**
   * Unsets the variable rotateAxisX
   *
   * @return {@code true}, if rotateAxisX was set before,
   *         otherwise {@code false}
   */
  public boolean unsetRotateAxisX() {
    if (isSetRotateAxisX()) {
      double oldRotateAxisX = rotateAxisX;
      rotateAxisX = null;
      firePropertyChange(SpatialConstants.rotateAxisX, oldRotateAxisX, rotateAxisX);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of rotateAxisY
   *
   * @return the value of rotateAxisY
   */
  public double getRotateAxisY() {
    if (isSetRotateAxisY()) {
      return rotateAxisY;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.rotateAxisY, this);
  }


  /**
   * Returns whether rotateAxisY is set
   *
   * @return whether rotateAxisY is set
   */
  public boolean isSetRotateAxisY() {
    return rotateAxisY != null;
  }


  /**
   * Sets the value of rotateAxisY
   * @param rotateAxisY
   */
  public void setRotateAxisY(double rotateAxisY) {
    double oldRotateAxisY = this.rotateAxisY;
    this.rotateAxisY = rotateAxisY;
    firePropertyChange(SpatialConstants.rotateAxisY, oldRotateAxisY, this.rotateAxisY);
  }


  /**
   * Unsets the variable rotateAxisY
   *
   * @return {@code true}, if rotateAxisY was set before,
   *         otherwise {@code false}
   */
  public boolean unsetRotateAxisY() {
    if (isSetRotateAxisY()) {
      double oldRotateAxisY = rotateAxisY;
      rotateAxisY = null;
      firePropertyChange(SpatialConstants.rotateAxisY, oldRotateAxisY, rotateAxisY);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of rotateAxisZ
   *
   * @return the value of rotateAxisZ
   */
  public double getRotateAxisZ() {
    if (isSetRotateAxisZ()) {
      return rotateAxisZ;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.rotateAxisZ, this);
  }


  /**
   * Returns whether rotateAxisZ is set
   *
   * @return whether rotateAxisZ is set
   */
  public boolean isSetRotateAxisZ() {
    return rotateAxisZ != null;
  }


  /**
   * Sets the value of rotateAxisZ
   * @param rotateAxisZ
   */
  public void setRotateAxisZ(double rotateAxisZ) {
    double oldRotateAxisZ = this.rotateAxisZ;
    this.rotateAxisZ = rotateAxisZ;
    firePropertyChange(SpatialConstants.rotateAxisZ, oldRotateAxisZ, this.rotateAxisZ);
  }


  /**
   * Unsets the variable rotateAxisZ
   *
   * @return {@code true}, if rotateAxisZ was set before,
   *         otherwise {@code false}
   */
  public boolean unsetRotateAxisZ() {
    if (isSetRotateAxisZ()) {
      double oldRotateAxisZ = rotateAxisZ;
      rotateAxisZ = null;
      firePropertyChange(SpatialConstants.rotateAxisZ, oldRotateAxisZ, rotateAxisZ);
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
    double oldRotateAngleInRadians = this.rotateAngleInRadians;
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
      double oldRotateAngleInRadians = rotateAngleInRadians;
      rotateAngleInRadians = null;
      firePropertyChange(SpatialConstants.rotateAngleInRadians, oldRotateAngleInRadians, rotateAngleInRadians);
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    final int prime = 313;//Change this prime number
    int hashCode = super.hashCode();
    if (isSetRotateAxisX()) {
      hashCode += prime * getRotateAxisX();
    }
    if (isSetRotateAxisY()) {
      hashCode += prime * getRotateAxisY();
    }
    if (isSetRotateAxisZ()) {
      hashCode += prime * getRotateAxisZ();
    }
    if (isSetRotateAngleInRadians()) {
      hashCode += prime * getRotateAngleInRadians();
    }
    return hashCode;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetRotateAxisX()) {
      attributes.remove("rotateAxisX");
      attributes.put(SpatialConstants.shortLabel + ":rotateAxisX", String.valueOf(getRotateAxisX()));
    }
    if (isSetRotateAxisY()) {
      attributes.remove("rotateAxisY");
      attributes.put(SpatialConstants.shortLabel + ":rotateAxisY", String.valueOf(getRotateAxisY()));
    }
    if (isSetRotateAxisZ()) {
      attributes.remove("rotateAxisZ");
      attributes.put(SpatialConstants.shortLabel + ":rotateAxisZ", String.valueOf(getRotateAxisZ()));
    }
    if (isSetRotateAngleInRadians()) {
      attributes.remove("rotateAngleInRadians");
      attributes.put(SpatialConstants.shortLabel + ":rotateAngleInRadians", String.valueOf(getRotateAngleInRadians()));
    }
    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.rotateAxisX)) {
        try {
          setRotateAxisX(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.rotateAxisX);
        }
      }
      else if (attributeName.equals(SpatialConstants.rotateAxisY)) {
        try {
          setRotateAxisY(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.rotateAxisY);
        }
      }
      else if (attributeName.equals(SpatialConstants.rotateAxisZ)) {
        try {
          setRotateAxisZ(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.rotateAxisZ);
        }
      }
      else if (attributeName.equals(SpatialConstants.rotateAngleInRadians)) {
        try {
          setRotateAngleInRadians(StringTools.parseSBMLDouble(value));
        } catch (Exception e) {
          MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ"), value, SpatialConstants.rotateAngleInRadians);
        }
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
    StringBuilder builder = new StringBuilder();
    builder.append("CSGRotation [rotateAxisX=");
    builder.append(rotateAxisX);
    builder.append(", rotateAxisY=");
    builder.append(rotateAxisY);
    builder.append(", rotateAxisZ=");
    builder.append(rotateAxisZ);
    builder.append(", rotateAngleInRadians=");
    builder.append(rotateAngleInRadians);
    builder.append("]");
    return builder.toString();
  }


}

