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
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class RenderPoint extends RenderCurveSegment {

  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 6792387139122188270L;

  /**
   * 
   */
  private Boolean absoluteX;
  /**
   * 
   */
  private Boolean absoluteY;
  /**
   * 
   */
  private Boolean absoluteZ;

  /**
   * 
   */
  private Double x;
  /**
   * 
   */
  private Double y;
  /**
   * 
   */
  private Double z;

  /**
   * Creates a new {@link RenderPoint} instance
   */
  public RenderPoint() {
    super();
    initDefaults();
  }

  /**
   * Clone constructor
   * 
   * @param obj the {@link RenderPoint} instance to clone
   */
  public RenderPoint(RenderPoint obj) {
    super(obj);
    absoluteX = obj.absoluteX;
    absoluteY = obj.absoluteY;
    absoluteZ = obj.absoluteZ;
    x = obj.x;
    y = obj.y;
    z = obj.z;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public RenderPoint clone() {
    return new RenderPoint(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getElementName()
   */
  @Override
  public String getElementName() {
    return RenderConstants.renderPoint;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#getX()
   */
  @Override
  public double getX() {
    if (isSetX()) {
      return x;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.x, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#getY()
   */
  @Override
  public double getY() {
    if (isSetY()) {
      return y;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.y, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#getZ()
   */
  @Override
  public double getZ() {
    if (isSetZ()) {
      return z;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.z, this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;
    
    setType(Type.RENDER_POINT);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#isAbsoluteX()
   */
  @Override
  public boolean isAbsoluteX() {
    if (isSetAbsoluteX()) {
      return absoluteX;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteX, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#isAbsoluteY()
   */
  @Override
  public boolean isAbsoluteY() {
    if (isSetAbsoluteY()) {
      return absoluteY;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteY, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#isAbsoluteZ()
   */
  @Override
  public boolean isAbsoluteZ() {
    if (isSetAbsoluteZ()) {
      return absoluteZ;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.absoluteZ, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#isSetAbsoluteX()
   */
  @Override
  public boolean isSetAbsoluteX() {
    return absoluteX != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#isSetAbsoluteY()
   */
  @Override
  public boolean isSetAbsoluteY() {
    return absoluteY != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#isSetAbsoluteZ()
   */
  @Override
  public boolean isSetAbsoluteZ() {
    return absoluteZ != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#isSetX()
   */
  @Override
  public boolean isSetX() {
    return x != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#isSetY()
   */
  @Override
  public boolean isSetY() {
    return y != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#isSetZ()
   */
  @Override
  public boolean isSetZ() {
    return z != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#setAbsoluteX(java.lang.Boolean)
   */
  @Override
  public void setAbsoluteX(boolean absoluteX) {
    Boolean oldAbsoluteX = this.absoluteX;
    this.absoluteX = absoluteX;
    firePropertyChange(RenderConstants.absoluteX, oldAbsoluteX, this.absoluteX);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#setAbsoluteY(java.lang.Boolean)
   */
  @Override
  public void setAbsoluteY(boolean absoluteY) {
    Boolean oldAbsoluteY = this.absoluteY;
    this.absoluteY = absoluteY;
    firePropertyChange(RenderConstants.absoluteY, oldAbsoluteY, this.absoluteY);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#setAbsoluteZ(java.lang.Boolean)
   */
  @Override
  public void setAbsoluteZ(boolean absoluteZ) {
    Boolean oldAbsoluteZ = this.absoluteZ;
    this.absoluteZ = absoluteZ;
    firePropertyChange(RenderConstants.absoluteZ, oldAbsoluteZ, this.absoluteZ);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#setX(java.lang.Double)
   */
  @Override
  public void setX(double x) {
    Double oldX = this.x;
    this.x = x;
    firePropertyChange(RenderConstants.x, oldX, this.x);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#setY(java.lang.Double)
   */
  @Override
  public void setY(double y) {
    Double oldY = this.y;
    this.y = y;
    firePropertyChange(RenderConstants.y, oldY, this.y);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#setZ(java.lang.Double)
   */
  @Override
  public void setZ(double z) {
    Double oldZ = this.z;
    this.z = z;
    firePropertyChange(RenderConstants.z, oldZ, this.z);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#unsetAbsoluteX()
   */
  @Override
  public boolean unsetAbsoluteX() {
    if (isSetAbsoluteX()) {
      Boolean oldAbsoluteX = absoluteX;
      absoluteX = null;
      firePropertyChange(RenderConstants.absoluteX, oldAbsoluteX, absoluteX);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#unsetAbsoluteY()
   */
  @Override
  public boolean unsetAbsoluteY() {
    if (isSetAbsoluteY()) {
      Boolean oldAbsoluteY = absoluteY;
      absoluteY = null;
      firePropertyChange(RenderConstants.absoluteY, oldAbsoluteY, absoluteY);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#unsetAbsoluteZ()
   */
  @Override
  public boolean unsetAbsoluteZ() {
    if (isSetAbsoluteZ()) {
      Boolean oldAbsoluteZ = absoluteZ;
      absoluteZ = null;
      firePropertyChange(RenderConstants.absoluteZ, oldAbsoluteZ, absoluteZ);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#unsetX()
   */
  @Override
  public boolean unsetX() {
    if (isSetX()) {
      Double oldX = x;
      x = null;
      firePropertyChange(RenderConstants.x, oldX, x);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#unsetY()
   */
  @Override
  public boolean unsetY() {
    if (isSetY()) {
      Double oldY = y;
      y = null;
      firePropertyChange(RenderConstants.y, oldY, y);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderPoint#unsetZ()
   */
  @Override
  public boolean unsetZ() {
    if (isSetZ()) {
      Double oldZ = z;
      z = null;
      firePropertyChange(RenderConstants.z, oldZ, z);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    
    if (isSetX()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.x,
        XMLTools.positioningToString(getX(), isAbsoluteX()));
    }
    if (isSetY()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.y,
        XMLTools.positioningToString(getY(), isAbsoluteY()));
    }
    if (isSetZ()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.z,
        XMLTools.positioningToString(getZ(), isAbsoluteZ()));
    }
    return attributes;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    
    if (!isAttributeRead) {
      isAttributeRead = true;
      
      if (attributeName.equals(RenderConstants.x)) {
        setX(XMLTools.parsePosition(value));
        setAbsoluteX(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.y)) {
        setY(XMLTools.parsePosition(value));
        setAbsoluteY(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.z)) {
        setZ(XMLTools.parsePosition(value));
        setAbsoluteZ(XMLTools.isAbsolutePosition(value));
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
    final int prime = 3187;
    int result = super.hashCode();
    result = prime * result + ((absoluteX == null) ? 0 : absoluteX.hashCode());
    result = prime * result + ((absoluteY == null) ? 0 : absoluteY.hashCode());
    result = prime * result + ((absoluteZ == null) ? 0 : absoluteZ.hashCode());
    result = prime * result + ((x == null) ? 0 : x.hashCode());
    result = prime * result + ((y == null) ? 0 : y.hashCode());
    result = prime * result + ((z == null) ? 0 : z.hashCode());
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
    RenderPoint other = (RenderPoint) obj;
    if (absoluteX == null) {
      if (other.absoluteX != null) {
        return false;
      }
    } else if (!absoluteX.equals(other.absoluteX)) {
      return false;
    }
    if (absoluteY == null) {
      if (other.absoluteY != null) {
        return false;
      }
    } else if (!absoluteY.equals(other.absoluteY)) {
      return false;
    }
    if (absoluteZ == null) {
      if (other.absoluteZ != null) {
        return false;
      }
    } else if (!absoluteZ.equals(other.absoluteZ)) {
      return false;
    }
    if (x == null) {
      if (other.x != null) {
        return false;
      }
    } else if (!x.equals(other.x)) {
      return false;
    }
    if (y == null) {
      if (other.y != null) {
        return false;
      }
    } else if (!y.equals(other.y)) {
      return false;
    }
    if (z == null) {
      if (other.z != null) {
        return false;
      }
    } else if (!z.equals(other.z)) {
      return false;
    }
    return true;
  }

  
}
