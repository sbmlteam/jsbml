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

import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBase;


/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class Rectangle extends GraphicalPrimitive2D implements Point3D {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = -4314411828208615411L;
  /**
   * 
   */
  private RelAbsVector x, y, z, width, height;
  
  /**
   * 
   */
  private RelAbsVector rx, ry;

  // TODO: missing ratio of type double!
  
  /**
   * @return the value of rx
   */
  public RelAbsVector getRx() {
    if (isSetRx()) {
      return rx;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.rx, this);
  }

  /**
   * @return whether rx is set
   */
  public boolean isSetRx() {
    return rx != null;
  }

  /**
   * Set the value of rx
   * @param rx
   */
  public void setRx(RelAbsVector rx) {
    RelAbsVector oldRx = this.rx;
    this.rx = rx;
    firePropertyChange(RenderConstants.rx, oldRx, this.rx);
  }

  /**
   * Unsets the variable rx
   * @return {@code true}, if rx was set before,
   *         otherwise {@code false}
   */
  public boolean unsetRx() {
    if (isSetRx()) {
      RelAbsVector oldRx = rx;
      rx = null;
      firePropertyChange(RenderConstants.rx, oldRx, rx);
      return true;
    }
    return false;
  }

  /**
   * @return the value of ry
   */
  public RelAbsVector getRy() {
    if (isSetRy()) {
      return ry;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.ry, this);
  }

  /**
   * @return whether ry is set
   */
  public boolean isSetRy() {
    return ry != null;
  }

  /**
   * Set the value of ry
   * @param ry
   */
  public void setRy(RelAbsVector ry) {
    RelAbsVector oldRy = this.ry;
    this.ry = ry;
    firePropertyChange(RenderConstants.ry, oldRy, this.ry);
  }

  //TODO - we probably need to store all coordinate as String as they can contain arithmetic operation !!
  // -> true, but use RelAbsVector instead

  /**
   * Unsets the variable ry
   * @return {@code true}, if ry was set before,
   *         otherwise {@code false}
   */
  public boolean unsetRy() {
    if (isSetRy()) {
      RelAbsVector oldRy = ry;
      ry = null;
      firePropertyChange(RenderConstants.ry, oldRy, ry);
      return true;
    }
    return false;
  }

  /**
   * @return the value of x
   */
  @Override
  public RelAbsVector getX() {
    if (isSetX()) {
      return x;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.x, this);
  }

  /**
   * @return whether x is set
   */
  @Override
  public boolean isSetX() {
    return x != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setX(double)
   */
  @Override
  public void setX(RelAbsVector x) {
    RelAbsVector oldX = this.x;
    this.x = x;
    firePropertyChange(RenderConstants.x, oldX, this.x);
  }

  /**
   * Unsets the variable x
   * @return {@code true}, if x was set before,
   *         otherwise {@code false}
   */
  @Override
  public boolean unsetX() {
    if (isSetX()) {
      RelAbsVector oldX = x;
      x = null;
      firePropertyChange(RenderConstants.x, oldX, x);
      return true;
    }
    return false;
  }

  /**
   * @return the value of y
   */
  @Override
  public RelAbsVector getY() {
    if (isSetY()) {
      return y;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.y, this);
  }

  /**
   * @return whether y is set
   */
  @Override
  public boolean isSetY() {
    return y != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setY(double)
   */
  @Override
  public void setY(RelAbsVector y) {
    RelAbsVector oldY = this.y;
    this.y = y;
    firePropertyChange(RenderConstants.y, oldY, this.y);
  }

  /**
   * Unsets the variable y
   * @return {@code true}, if y was set before,
   *         otherwise {@code false}
   */
  @Override
  public boolean unsetY() {
    if (isSetY()) {
      RelAbsVector oldY = y;
      y = null;
      firePropertyChange(RenderConstants.y, oldY, y);
      return true;
    }
    return false;
  }

  /**
   * @return the value of z
   */
  @Override
  public RelAbsVector getZ() {
    if (isSetZ()) {
      return z;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.z, this);
  }

  /**
   * @return whether z is set
   */
  @Override
  public boolean isSetZ() {
    return z != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setZ(double)
   */
  @Override
  public void setZ(RelAbsVector z) {
    RelAbsVector oldZ = this.z;
    this.z = z;
    firePropertyChange(RenderConstants.z, oldZ, this.z);
  }

  /**
   * Unsets the variable z
   * @return {@code true}, if z was set before,
   *         otherwise {@code false}
   */
  @Override
  public boolean unsetZ() {
    if (isSetZ()) {
      RelAbsVector oldZ = z;
      z = null;
      firePropertyChange(RenderConstants.z, oldZ, z);
      return true;
    }
    return false;
  }

  /**
   * @return the value of height
   */
  public RelAbsVector getHeight() {
    if (isSetHeight()) {
      return height;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.height, this);
  }

  /**
   * @return whether height is set
   */
  public boolean isSetHeight() {
    return height != null;
  }

  /**
   * Set the value of height
   * @param height
   */
  public void setHeight(RelAbsVector height) {
    RelAbsVector oldHeight = this.height;
    this.height = height;
    firePropertyChange(RenderConstants.height, oldHeight, this.height);
  }

  /**
   * Unsets the variable height
   * @return {@code true}, if height was set before,
   *         otherwise {@code false}
   */
  public boolean unsetHeight() {
    if (isSetHeight()) {
      RelAbsVector oldHeight = height;
      height = null;
      firePropertyChange(RenderConstants.height, oldHeight, height);
      return true;
    }
    return false;
  }

  /**
   * @return the value of width
   */
  public RelAbsVector getWidth() {
    if (isSetWidth()) {
      return width;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.width, this);
  }

  /**
   * @return whether width is set
   */
  public boolean isSetWidth() {
    return width != null;
  }

  /**
   * Set the value of width
   * @param width
   */
  public void setWidth(RelAbsVector width) {
    RelAbsVector oldWidth = this.width;
    this.width = width;
    firePropertyChange(RenderConstants.width, oldWidth, this.width);
  }

  /**
   * Unsets the variable width
   * @return {@code true}, if width was set before,
   *         otherwise {@code false}
   */
  public boolean unsetWidth() {
    if (isSetWidth()) {
      RelAbsVector oldWidth = width;
      width = null;
      firePropertyChange(RenderConstants.width, oldWidth, width);
      return true;
    }
    return false;
  }

  /**
   * Creates a new {@link Rectangle} instance.
   */
  public Rectangle() {
    super();
    initDefaults();
  }

  /**
   * Clone constructor
   * 
   * @param obj
   */
  public Rectangle(Rectangle obj) {
    super(obj);
    x = obj.x;
    y = obj.y;
    z = obj.z;
    rx = obj.rx;
    ry = obj.ry;
    height = obj.height;
    width = obj.width;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive2D#clone()
   */
  @Override
  public Rectangle clone() {
    return new Rectangle(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive2D#initDefaults()
   */
  @Override
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;
    
//    z = 0d;
//    rx = 0d;
//    ry = 0d;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#getChildCount()
   */
  @Override
  public int getChildCount() {
    return 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#getChildAt(int)
   */
  @Override
  public SBase getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(resourceBundle.getString("IndexSurpassesBoundsException"), childIndex, 0));
    }
    int pos = 0;
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), childIndex,
      Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetX()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.x,
        getX().getCoordinate());
    }
    if (isSetY()) {
      attributes.remove(RenderConstants.y);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.y,
        getY().getCoordinate());
    }
    if (isSetZ()) {
      attributes.remove(RenderConstants.z);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.z,
        getZ().getCoordinate());
    }
    if (isSetWidth()) {
      attributes.remove(RenderConstants.width);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.width,
        getWidth().getCoordinate());
    }
    if (isSetHeight()) {
      attributes.remove(RenderConstants.height);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.height,
        getHeight().getCoordinate());
    }
    if (isSetRx()) {
      attributes.remove(RenderConstants.rx);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.rx,
        getRx().getCoordinate());
    }
    if (isSetRy()) {
      attributes.remove(RenderConstants.ry);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.ry,
        getRy().getCoordinate());
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
      // TODO: catch Exception if Enum.valueOf fails, generate logger output
      if (attributeName.equals(RenderConstants.x)) {
        setX(new RelAbsVector(value));
      }
      else if (attributeName.equals(RenderConstants.y)) {
        setY(new RelAbsVector(value));
      }
      else if (attributeName.equals(RenderConstants.z)) {
        setZ(new RelAbsVector(value));
      }
      else if (attributeName.equals(RenderConstants.width)) {
        setWidth(new RelAbsVector(value));
      }
      else if (attributeName.equals(RenderConstants.height)) {
        setHeight(new RelAbsVector(value));
      }
      else if (attributeName.equals(RenderConstants.rx)) {
        setRx(new RelAbsVector(value));
      }
      else if (attributeName.equals(RenderConstants.ry)) {
        setRy(new RelAbsVector(value));
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
    final int prime = 3137;
    int result = super.hashCode();
    
    result = prime * result + ((height == null) ? 0 : height.hashCode());
    result = prime * result + ((rx == null) ? 0 : rx.hashCode());
    result = prime * result + ((ry == null) ? 0 : ry.hashCode());
    result = prime * result + ((width == null) ? 0 : width.hashCode());
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
    Rectangle other = (Rectangle) obj;
    if (height == null) {
      if (other.height != null) {
        return false;
      }
    } else if (!height.equals(other.height)) {
      return false;
    }
    if (rx == null) {
      if (other.rx != null) {
        return false;
      }
    } else if (!rx.equals(other.rx)) {
      return false;
    }
    if (ry == null) {
      if (other.ry != null) {
        return false;
      }
    } else if (!ry.equals(other.ry)) {
      return false;
    }
    if (width == null) {
      if (other.width != null) {
        return false;
      }
    } else if (!width.equals(other.width)) {
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
