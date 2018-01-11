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
package org.sbml.jsbml.ext.render;

import java.util.Map;

import org.sbml.jsbml.PropertyUndefinedError;

/**
 * 
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class RenderCubicBezier extends RenderPoint {

  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = -2426257418589249467L;
  /**
   * 
   */
  private Boolean absoluteX1;
  /**
   * 
   */
  private Boolean absoluteY1;
  /**
   * 
   */
  private Boolean absoluteZ1;
  /**
   * 
   */
  private Boolean absoluteX2;
  /**
   * 
   */
  private Boolean absoluteY2;
  /**
   * 
   */
  private Boolean absoluteZ2;
  /**
   * 
   */
  private Double x1;
  /**
   * 
   */
  private Double y1;
  /**
   * 
   */
  private Double z1;
  /**
   * 
   */
  private Double x2;
  /**
   * 
   */
  private Double y2;
  /**
   * 
   */
  private Double z2;

  /**
   * Creates an RenderCubicBezier instance
   */
  public RenderCubicBezier() {
    super();
    initDefaults();
  }

  /**
   * Clone constructor
   * 
   * @param obj the {@link RenderCubicBezier} instance to clone
   */
  public RenderCubicBezier(RenderCubicBezier obj) {
    super(obj);
    absoluteX1 = obj.absoluteX1;
    absoluteY1 = obj.absoluteY1;
    absoluteZ1 = obj.absoluteZ1;
    absoluteX2 = obj.absoluteX2;
    absoluteY2 = obj.absoluteY2;
    absoluteZ2 = obj.absoluteZ2;
    x1 = obj.x1;
    y1 = obj.y1;
    z1 = obj.z1;
    x2 = obj.x2;
    y2 = obj.y2;
    z2 = obj.z2;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#clone()
   */
  @Override
  public RenderCubicBezier clone() {
    return new RenderCubicBezier(this);
  }

  /**
   * @return the value of x1
   */
  public double getX1() {
    if (isSetX1()) {
      return x1;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.basepoint1_x, this);
  }

  /**
   * @return the value of x2
   */
  public double getX2() {
    if (isSetX2()) {
      return x2;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.basepoint2_x, this);
  }

  /**
   * @return the value of y1
   */
  public double getY1() {
    if (isSetY1()) {
      return y1;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.basepoint1_y, this);
  }

  /**
   * @return the value of y2
   */
  public double getY2() {
    if (isSetY2()) {
      return y2;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.basepoint2_y, this);
  }

  /**
   * @return the value of z1
   */
  public double getZ1() {
    if (isSetZ1()) {
      return z1;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.basepoint1_z, this);
  }

  /**
   * @return the value of z2
   */
  public double getZ2() {
    if (isSetZ2()) {
      return z2;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.basepoint2_z, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#initDefaults()
   */
  @Override
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;

    setType(Type.RENDER_POINT);
  }

  /**
   * @return the value of absoluteX1
   */
  public boolean isAbsoluteX1() {
    if (isSetAbsoluteX1()) {
      return absoluteX1;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteX1, this);
  }

  /**
   * @return the value of absoluteX2
   */
  public boolean isAbsoluteX2() {
    if (isSetAbsoluteX2()) {
      return absoluteX2;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteX2, this);
  }

  /**
   * @return the value of absoluteY1
   */
  public boolean isAbsoluteY1() {
    if (isSetAbsoluteY1()) {
      return absoluteY1;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteY1, this);
  }

  /**
   * @return the value of absoluteY2
   */
  public boolean isAbsoluteY2() {
    if (isSetAbsoluteY2()) {
      return absoluteY2;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteY2, this);
  }

  /**
   * @return the value of absoluteZ1
   */
  public boolean isAbsoluteZ1() {
    if (isSetAbsoluteZ1()) {
      return absoluteZ1;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteZ1, this);
  }

  /**
   * @return the value of absoluteZ2
   */
  public boolean isAbsoluteZ2() {
    if (isSetAbsoluteZ2()) {
      return absoluteZ2;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteZ2, this);
  }

  /**
   * @return whether absoluteX1 is set
   */
  public boolean isSetAbsoluteX1() {
    return absoluteX1 != null;
  }

  /**
   * @return whether absoluteX2 is set
   */
  public boolean isSetAbsoluteX2() {
    return absoluteX2 != null;
  }

  /**
   * @return whether absoluteY1 is set
   */
  public boolean isSetAbsoluteY1() {
    return absoluteY1 != null;
  }

  /**
   * @return whether absoluteY2 is set
   */
  public boolean isSetAbsoluteY2() {
    return absoluteY2 != null;
  }

  /**
   * @return whether absoluteZ1 is set
   */
  public boolean isSetAbsoluteZ1() {
    return absoluteZ1 != null;
  }

  /**
   * @return whether absoluteZ2 is set
   */
  public boolean isSetAbsoluteZ2() {
    return absoluteZ2 != null;
  }

  /**
   * @return whether x1 is set
   */
  public boolean isSetX1() {
    return x1 != null;
  }

  /**
   * @return whether x2 is set
   */
  public boolean isSetX2() {
    return x2 != null;
  }

  /**
   * @return whether y1 is set
   */
  public boolean isSetY1() {
    return y1 != null;
  }

  /**
   * @return whether y2 is set
   */
  public boolean isSetY2() {
    return y2 != null;
  }

  /**
   * @return whether z1 is set
   */
  public boolean isSetZ1() {
    return z1 != null;
  }

  /**
   * @return whether z2 is set
   */
  public boolean isSetZ2() {
    return z2 != null;
  }

  /**
   * Set the value of absoluteX1
   * @param absoluteX1
   */
  public void setAbsoluteX1(boolean absoluteX1) {
    Boolean oldAbsoluteX1 = this.absoluteX1;
    this.absoluteX1 = absoluteX1;
    firePropertyChange(RenderConstants.absoluteX1, oldAbsoluteX1, this.absoluteX1);
  }

  /**
   * Set the value of absoluteX2
   * @param absoluteX2
   */
  public void setAbsoluteX2(boolean absoluteX2) {
    Boolean oldAbsoluteX2 = this.absoluteX2;
    this.absoluteX2 = absoluteX2;
    firePropertyChange(RenderConstants.absoluteX2, oldAbsoluteX2, this.absoluteX2);
  }

  /**
   * Set the value of absoluteY1
   * @param absoluteY1
   */
  public void setAbsoluteY1(boolean absoluteY1) {
    Boolean oldAbsoluteY1 = this.absoluteY1;
    this.absoluteY1 = absoluteY1;
    firePropertyChange(RenderConstants.absoluteY1, oldAbsoluteY1, this.absoluteY1);
  }

  /**
   * Set the value of absoluteY2
   * @param absoluteY2
   */
  public void setAbsoluteY2(boolean absoluteY2) {
    Boolean oldAbsoluteY2 = this.absoluteY2;
    this.absoluteY2 = absoluteY2;
    firePropertyChange(RenderConstants.absoluteY2, oldAbsoluteY2, this.absoluteY2);
  }

  /**
   * Set the value of absoluteZ1
   * @param absoluteZ1
   */
  public void setAbsoluteZ1(boolean absoluteZ1) {
    Boolean oldAbsoluteZ1 = this.absoluteZ1;
    this.absoluteZ1 = absoluteZ1;
    firePropertyChange(RenderConstants.absoluteZ1, oldAbsoluteZ1, this.absoluteZ1);
  }

  /**
   * Set the value of absoluteZ2
   * @param absoluteZ2
   */
  public void setAbsoluteZ2(boolean absoluteZ2) {
    Boolean oldAbsoluteZ2 = this.absoluteZ2;
    this.absoluteZ2 = absoluteZ2;
    firePropertyChange(RenderConstants.absoluteZ2, oldAbsoluteZ2, this.absoluteZ2);
  }

  /**
   * Set the value of x1
   * @param x1
   */
  public void setX1(double x1) {
    Double oldX1 = this.x1;
    this.x1 = x1;
    firePropertyChange(RenderConstants.basepoint1_x, oldX1, this.x1);
  }

  /**
   * Set the value of x2
   * @param x2
   */
  public void setX2(double x2) {
    Double oldX2 = this.x2;
    this.x2 = x2;
    firePropertyChange(RenderConstants.basepoint2_x, oldX2, this.x2);
  }

  /**
   * Set the value of y1
   * @param y1
   */
  public void setY1(double y1) {
    Double oldY1 = this.y1;
    this.y1 = y1;
    firePropertyChange(RenderConstants.basepoint1_y, oldY1, this.y1);
  }

  /**
   * Set the value of y2
   * @param y2
   */
  public void setY2(double y2) {
    Double oldY2 = this.y2;
    this.y2 = y2;
    firePropertyChange(RenderConstants.basepoint2_y, oldY2, this.y2);
  }

  /**
   * Set the value of z1
   * @param z1
   */
  public void setZ1(double z1) {
    Double oldZ1 = this.z1;
    this.z1 = z1;
    firePropertyChange(RenderConstants.basepoint1_z, oldZ1, this.z1);
  }

  /**
   * Set the value of z2
   * @param z2
   */
  public void setZ2(double z2) {
    Double oldZ2 = this.z2;
    this.z2 = z2;
    firePropertyChange(RenderConstants.basepoint2_z, oldZ2, this.z2);
  }

  /**
   * Unsets the variable absoluteX1
   * @return {@code true}, if absoluteX1 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteX1() {
    if (isSetAbsoluteX1()) {
      Boolean oldAbsoluteX1 = absoluteX1;
      absoluteX1 = null;
      firePropertyChange(RenderConstants.absoluteX1, oldAbsoluteX1, absoluteX1);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable absoluteX2
   * @return {@code true}, if absoluteX2 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteX2() {
    if (isSetAbsoluteX2()) {
      Boolean oldAbsoluteX2 = absoluteX2;
      absoluteX2 = null;
      firePropertyChange(RenderConstants.absoluteX2, oldAbsoluteX2, absoluteX2);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable absoluteY1
   * @return {@code true}, if absoluteY1 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteY1() {
    if (isSetAbsoluteY1()) {
      Boolean oldAbsoluteY1 = absoluteY1;
      absoluteY1 = null;
      firePropertyChange(RenderConstants.absoluteY1, oldAbsoluteY1, absoluteY1);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable absoluteY2
   * @return {@code true}, if absoluteY2 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteY2() {
    if (isSetAbsoluteY2()) {
      Boolean oldAbsoluteY2 = absoluteY2;
      absoluteY2 = null;
      firePropertyChange(RenderConstants.absoluteY2, oldAbsoluteY2, absoluteY2);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable absoluteZ1
   * @return {@code true}, if absoluteZ1 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteZ1() {
    if (isSetAbsoluteZ1()) {
      Boolean oldAbsoluteZ1 = absoluteZ1;
      absoluteZ1 = null;
      firePropertyChange(RenderConstants.absoluteZ1, oldAbsoluteZ1, absoluteZ1);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable absoluteZ2
   * @return {@code true}, if absoluteZ2 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteZ2() {
    if (isSetAbsoluteZ2()) {
      Boolean oldAbsoluteZ2 = absoluteZ2;
      absoluteZ2 = null;
      firePropertyChange(RenderConstants.absoluteZ2, oldAbsoluteZ2, absoluteZ2);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable x1
   * @return {@code true}, if x1 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetX1() {
    if (isSetX1()) {
      Double oldX1 = x1;
      x1 = null;
      firePropertyChange(RenderConstants.x1, oldX1, x1);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable x2
   * @return {@code true}, if x2 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetX2() {
    if (isSetX2()) {
      Double oldX2 = x2;
      x2 = null;
      firePropertyChange(RenderConstants.basepoint2_x, oldX2, x2);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable y1
   * @return {@code true}, if y1 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetY1() {
    if (isSetY1()) {
      Double oldY1 = y1;
      y1 = null;
      firePropertyChange(RenderConstants.basepoint1_y, oldY1, y1);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable y2
   * @return {@code true}, if y2 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetY2() {
    if (isSetY2()) {
      Double oldY2 = y2;
      y2 = null;
      firePropertyChange(RenderConstants.basepoint2_y, oldY2, y2);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable z1
   * @return {@code true}, if z1 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetZ1() {
    if (isSetZ1()) {
      Double oldZ1 = z1;
      z1 = null;
      firePropertyChange(RenderConstants.basepoint1_z, oldZ1, z1);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable z2
   * @return {@code true}, if z2 was set before,
   *         otherwise {@code false}
   */
  public boolean unsetZ2() {
    if (isSetZ2()) {
      Double oldZ2 = z2;
      z2 = null;
      firePropertyChange(RenderConstants.basepoint2_z, oldZ2, z2);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    
    if (isSetX1()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.basepoint1_x,
        XMLTools.positioningToString(getX1(), isAbsoluteX1()));
    }
    if (isSetX2()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.basepoint2_x,
        XMLTools.positioningToString(getX2(), isAbsoluteX2()));
    }
    if (isSetY1()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.basepoint1_y,
        XMLTools.positioningToString(getY1(), isAbsoluteY1()));
    }
    if (isSetY2()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.basepoint2_y,
        XMLTools.positioningToString(getY2(), isAbsoluteY2()));
    }
    if (isSetZ1()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.basepoint1_z,
        XMLTools.positioningToString(getZ1(), isAbsoluteZ1()));
    }
    if (isSetZ2()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.basepoint2_z,
        XMLTools.positioningToString(getZ2(), isAbsoluteZ2()));
    }
    return attributes;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(RenderConstants.basepoint1_x)) {
        setX1(XMLTools.parsePosition(value));
        setAbsoluteX1(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.basepoint2_x)) {
        setX2(XMLTools.parsePosition(value));
        setAbsoluteX2(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.basepoint1_y)) {
        setY1(XMLTools.parsePosition(value));
        setAbsoluteY1(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.basepoint2_y)) {
        setY2(XMLTools.parsePosition(value));
        setAbsoluteY2(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.basepoint1_z)) {
        setZ1(XMLTools.parsePosition(value));
        setAbsoluteZ1(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.basepoint2_z)) {
        setZ2(XMLTools.parsePosition(value));
        setAbsoluteZ2(XMLTools.isAbsolutePosition(value));
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3181;
    int result = super.hashCode();
    result = prime * result
        + ((absoluteX1 == null) ? 0 : absoluteX1.hashCode());
    result = prime * result
        + ((absoluteX2 == null) ? 0 : absoluteX2.hashCode());
    result = prime * result
        + ((absoluteY1 == null) ? 0 : absoluteY1.hashCode());
    result = prime * result
        + ((absoluteY2 == null) ? 0 : absoluteY2.hashCode());
    result = prime * result
        + ((absoluteZ1 == null) ? 0 : absoluteZ1.hashCode());
    result = prime * result
        + ((absoluteZ2 == null) ? 0 : absoluteZ2.hashCode());
    result = prime * result + ((x1 == null) ? 0 : x1.hashCode());
    result = prime * result + ((x2 == null) ? 0 : x2.hashCode());
    result = prime * result + ((y1 == null) ? 0 : y1.hashCode());
    result = prime * result + ((y2 == null) ? 0 : y2.hashCode());
    result = prime * result + ((z1 == null) ? 0 : z1.hashCode());
    result = prime * result + ((z2 == null) ? 0 : z2.hashCode());
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
    RenderCubicBezier other = (RenderCubicBezier) obj;
    if (absoluteX1 == null) {
      if (other.absoluteX1 != null) {
        return false;
      }
    } else if (!absoluteX1.equals(other.absoluteX1)) {
      return false;
    }
    if (absoluteX2 == null) {
      if (other.absoluteX2 != null) {
        return false;
      }
    } else if (!absoluteX2.equals(other.absoluteX2)) {
      return false;
    }
    if (absoluteY1 == null) {
      if (other.absoluteY1 != null) {
        return false;
      }
    } else if (!absoluteY1.equals(other.absoluteY1)) {
      return false;
    }
    if (absoluteY2 == null) {
      if (other.absoluteY2 != null) {
        return false;
      }
    } else if (!absoluteY2.equals(other.absoluteY2)) {
      return false;
    }
    if (absoluteZ1 == null) {
      if (other.absoluteZ1 != null) {
        return false;
      }
    } else if (!absoluteZ1.equals(other.absoluteZ1)) {
      return false;
    }
    if (absoluteZ2 == null) {
      if (other.absoluteZ2 != null) {
        return false;
      }
    } else if (!absoluteZ2.equals(other.absoluteZ2)) {
      return false;
    }
    if (x1 == null) {
      if (other.x1 != null) {
        return false;
      }
    } else if (!x1.equals(other.x1)) {
      return false;
    }
    if (x2 == null) {
      if (other.x2 != null) {
        return false;
      }
    } else if (!x2.equals(other.x2)) {
      return false;
    }
    if (y1 == null) {
      if (other.y1 != null) {
        return false;
      }
    } else if (!y1.equals(other.y1)) {
      return false;
    }
    if (y2 == null) {
      if (other.y2 != null) {
        return false;
      }
    } else if (!y2.equals(other.y2)) {
      return false;
    }
    if (z1 == null) {
      if (other.z1 != null) {
        return false;
      }
    } else if (!z1.equals(other.z1)) {
      return false;
    }
    if (z2 == null) {
      if (other.z2 != null) {
        return false;
      }
    } else if (!z2.equals(other.z2)) {
      return false;
    }
    return true;
  }

  
}
