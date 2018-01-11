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
   * 
   */
  private Double width;
  /**
   * 
   */
  private Double height;
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
  private Boolean absoluteWidth;
  /**
   * 
   */
  private Boolean absoluteHeight;
  /**
   * 
   */
  private Double rx;
  /**
   * 
   */
  private Double ry;
  /**
   * 
   */
  private Boolean absoluteRx;
  /**
   * 
   */
  private Boolean absoluteRy;

  /**
   * @return the value of rx
   */
  public double getRx() {
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
  public void setRx(double rx) {
    Double oldRx = this.rx;
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
      Double oldRx = rx;
      rx = null;
      firePropertyChange(RenderConstants.rx, oldRx, rx);
      return true;
    }
    return false;
  }

  /**
   * @return the value of ry
   */
  public double getRy() {
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
  public void setRy(double ry) {
    Double oldRy = this.ry;
    this.ry = ry;
    firePropertyChange(RenderConstants.ry, oldRy, this.ry);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#isAbsoluteX()
   */
  @Override
  public boolean isAbsoluteX() {
    if (isSetAbsoluteX()) {
      return absoluteX;
    }

    return true; // TODO - we probably need to store all coordinate as String as they can contain arithmetic operation !!
  }

  /**
   * @return whether absoluteX is set
   */
  @Override
  public boolean isSetAbsoluteX() {
    return absoluteX != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setAbsoluteX(boolean)
   */
  @Override
  public void setAbsoluteX(boolean absoluteX) {
    Boolean oldAbsoluteX = this.absoluteX;
    this.absoluteX = absoluteX;
    firePropertyChange(RenderConstants.absoluteX, oldAbsoluteX, this.absoluteX);
  }

  /**
   * Unsets the variable absoluteX
   * @return {@code true}, if absoluteX was set before,
   *         otherwise {@code false}
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

  /**
   * @return the value of absoluteY
   */
  @Override
  public boolean isAbsoluteY() {
    if (isSetAbsoluteY()) {
      return absoluteY;
    }

    return true;
  }

  /**
   * @return whether absoluteY is set
   */
  @Override
  public boolean isSetAbsoluteY() {
    return absoluteY != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setAbsoluteY(boolean)
   */
  @Override
  public void setAbsoluteY(boolean absoluteY) {
    Boolean oldAbsoluteY = this.absoluteY;
    this.absoluteY = absoluteY;
    firePropertyChange(RenderConstants.absoluteY, oldAbsoluteY, this.absoluteY);
  }

  /**
   * Unsets the variable absoluteY
   * @return {@code true}, if absoluteY was set before,
   *         otherwise {@code false}
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

  /**
   * @return the value of absoluteZ
   */
  @Override
  public boolean isAbsoluteZ() {
    if (isSetAbsoluteZ()) {
      return absoluteZ;
    }

    return true;
  }

  /**
   * @return whether absoluteZ is set
   */
  @Override
  public boolean isSetAbsoluteZ() {
    return absoluteZ != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Point3D#setAbsoluteZ(boolean)
   */
  @Override
  public void setAbsoluteZ(boolean absoluteZ) {
    Boolean oldAbsoluteZ = this.absoluteZ;
    this.absoluteZ = absoluteZ;
    firePropertyChange(RenderConstants.absoluteZ, oldAbsoluteZ, this.absoluteZ);
  }

  /**
   * Unsets the variable absoluteZ
   * @return {@code true}, if absoluteZ was set before,
   *         otherwise {@code false}
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

  /**
   * @return the value of absoluteRx
   */
  public boolean isAbsoluteRx() {
    if (isSetAbsoluteRx()) {
      return absoluteRx;
    }

    return true;
  }

  /**
   * @return whether absoluteRx is set
   */
  public boolean isSetAbsoluteRx() {
    return absoluteRx != null;
  }

  /**
   * Set the value of absoluteRx
   * @param absoluteRx
   */
  public void setAbsoluteRx(boolean absoluteRx) {
    Boolean oldAbsoluteRx = this.absoluteRx;
    this.absoluteRx = absoluteRx;
    firePropertyChange(RenderConstants.absoluteRx, oldAbsoluteRx, this.absoluteRx);
  }

  /**
   * Unsets the variable absoluteRx
   * @return {@code true}, if absoluteRx was set before,
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteRx() {
    if (isSetAbsoluteRx()) {
      Boolean oldAbsoluteRx = absoluteRx;
      absoluteRx = null;
      firePropertyChange(RenderConstants.absoluteRx, oldAbsoluteRx, absoluteRx);
      return true;
    }
    return false;
  }

  /**
   * @return the value of absoluteRy
   */
  public boolean isAbsoluteRy() {
    if (isSetAbsoluteRy()) {
      return absoluteRy;
    }

    return true;
  }

  /**
   * @return whether absoluteRy is set
   */
  public boolean isSetAbsoluteRy() {
    return absoluteRy != null;
  }

  /**
   * Set the value of absoluteRy
   * @param absoluteRy
   */
  public void setAbsoluteRy(boolean absoluteRy) {
    Boolean oldAbsoluteRy = this.absoluteRy;
    this.absoluteRy = absoluteRy;
    firePropertyChange(RenderConstants.absoluteRy, oldAbsoluteRy, this.absoluteRy);
  }

  /**
   * Unsets the variable absoluteRy
   * @return {@code true}, if absoluteRy was set before,
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteRy() {
    if (isSetAbsoluteRy()) {
      Boolean oldAbsoluteRy = absoluteRy;
      absoluteRy = null;
      firePropertyChange(RenderConstants.absoluteRy, oldAbsoluteRy, absoluteRy);
      return true;
    }
    return false;
  }

  /**
   * @return the value of absoluteHeight
   */
  public boolean isAbsoluteHeight() {
    if (isSetAbsoluteHeight()) {
      return absoluteHeight;
    }

    return true;
  }

  /**
   * @return whether absoluteHeight is set
   */
  public boolean isSetAbsoluteHeight() {
    return absoluteHeight != null;
  }

  /**
   * Set the value of absoluteHeight
   * @param absoluteHeight
   */
  public void setAbsoluteHeight(boolean absoluteHeight) {
    Boolean oldAbsoluteHeight = this.absoluteHeight;
    this.absoluteHeight = absoluteHeight;
    firePropertyChange(RenderConstants.absoluteHeight, oldAbsoluteHeight, this.absoluteHeight);
  }

  /**
   * Unsets the variable absoluteHeight
   * @return {@code true}, if absoluteHeight was set before,
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteHeight() {
    if (isSetAbsoluteHeight()) {
      Boolean oldAbsoluteHeight = absoluteHeight;
      absoluteHeight = null;
      firePropertyChange(RenderConstants.absoluteHeight, oldAbsoluteHeight, absoluteHeight);
      return true;
    }
    return false;
  }

  /**
   * @return the value of absoluteWidth
   */
  public boolean isAbsoluteWidth() {
    if (isSetAbsoluteWidth()) {
      return absoluteWidth;
    }

    return true;
  }

  /**
   * @return whether absoluteWidth is set
   */
  public boolean isSetAbsoluteWidth() {
    return absoluteWidth != null;
  }

  /**
   * Set the value of absoluteWidth
   * @param absoluteWidth
   */
  public void setAbsoluteWidth(boolean absoluteWidth) {
    Boolean oldAbsoluteWidth = this.absoluteWidth;
    this.absoluteWidth = absoluteWidth;
    firePropertyChange(RenderConstants.absoluteWidth, oldAbsoluteWidth, this.absoluteWidth);
  }

  /**
   * Unsets the variable absoluteWidth
   * @return {@code true}, if absoluteWidth was set before,
   *         otherwise {@code false}
   */
  public boolean unsetAbsoluteWidth() {
    if (isSetAbsoluteWidth()) {
      Boolean oldAbsoluteWidth = absoluteWidth;
      absoluteWidth = null;
      firePropertyChange(RenderConstants.absoluteWidth, oldAbsoluteWidth, absoluteWidth);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable ry
   * @return {@code true}, if ry was set before,
   *         otherwise {@code false}
   */
  public boolean unsetRy() {
    if (isSetRy()) {
      Double oldRy = ry;
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
  public double getX() {
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
  public void setX(double x) {
    Double oldX = this.x;
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
      Double oldX = x;
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
  public double getY() {
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
  public void setY(double y) {
    Double oldY = this.y;
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
      Double oldY = y;
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
  public double getZ() {
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
  public void setZ(double z) {
    Double oldZ = this.z;
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
      Double oldZ = z;
      z = null;
      firePropertyChange(RenderConstants.z, oldZ, z);
      return true;
    }
    return false;
  }

  /**
   * @return the value of height
   */
  public double getHeight() {
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
  public void setHeight(double height) {
    Double oldHeight = this.height;
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
      Double oldHeight = height;
      height = null;
      firePropertyChange(RenderConstants.height, oldHeight, height);
      return true;
    }
    return false;
  }

  /**
   * @return the value of width
   */
  public double getWidth() {
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
  public void setWidth(double width) {
    Double oldWidth = this.width;
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
      Double oldWidth = width;
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
    absoluteX = obj.absoluteX;
    absoluteY = obj.absoluteY;
    absoluteZ = obj.absoluteZ;
    absoluteHeight = obj.absoluteHeight;
    absoluteWidth = obj.absoluteWidth;
    absoluteRx = obj.absoluteRx;
    absoluteRy = obj.absoluteRy;
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
        XMLTools.positioningToString(getX(), isAbsoluteX()));
    }
    if (isSetY()) {
      attributes.remove(RenderConstants.y);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.y,
        XMLTools.positioningToString(getY(), isAbsoluteY()));
    }
    if (isSetZ()) {
      attributes.remove(RenderConstants.z);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.z,
        XMLTools.positioningToString(getZ(), isAbsoluteZ()));
    }
    if (isSetWidth()) {
      attributes.remove(RenderConstants.width);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.width,
        XMLTools.positioningToString(getWidth(), isAbsoluteWidth()));
    }
    if (isSetHeight()) {
      attributes.remove(RenderConstants.height);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.height,
        XMLTools.positioningToString(getHeight(), isAbsoluteHeight()));
    }
    if (isSetRx()) {
      attributes.remove(RenderConstants.rx);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.rx,
        XMLTools.positioningToString(getRx(), isAbsoluteRx()));
    }
    if (isSetRy()) {
      attributes.remove(RenderConstants.ry);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.ry,
        XMLTools.positioningToString(getRy(), isAbsoluteRy()));
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
      else if (attributeName.equals(RenderConstants.width)) {
        setWidth(XMLTools.parsePosition(value));
        setAbsoluteWidth(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.height)) {
        setHeight(XMLTools.parsePosition(value));
        setAbsoluteHeight(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.rx)) {
        setRx(XMLTools.parsePosition(value));
        setAbsoluteRx(XMLTools.isAbsolutePosition(value));
      }
      else if (attributeName.equals(RenderConstants.ry)) {
        setRy(XMLTools.parsePosition(value));
        setAbsoluteRy(XMLTools.isAbsolutePosition(value));
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
    result = prime * result
        + ((absoluteHeight == null) ? 0 : absoluteHeight.hashCode());
    result = prime * result
        + ((absoluteRx == null) ? 0 : absoluteRx.hashCode());
    result = prime * result
        + ((absoluteRy == null) ? 0 : absoluteRy.hashCode());
    result = prime * result
        + ((absoluteWidth == null) ? 0 : absoluteWidth.hashCode());
    result = prime * result + ((absoluteX == null) ? 0 : absoluteX.hashCode());
    result = prime * result + ((absoluteY == null) ? 0 : absoluteY.hashCode());
    result = prime * result + ((absoluteZ == null) ? 0 : absoluteZ.hashCode());
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
    if (absoluteHeight == null) {
      if (other.absoluteHeight != null) {
        return false;
      }
    } else if (!absoluteHeight.equals(other.absoluteHeight)) {
      return false;
    }
    if (absoluteRx == null) {
      if (other.absoluteRx != null) {
        return false;
      }
    } else if (!absoluteRx.equals(other.absoluteRx)) {
      return false;
    }
    if (absoluteRy == null) {
      if (other.absoluteRy != null) {
        return false;
      }
    } else if (!absoluteRy.equals(other.absoluteRy)) {
      return false;
    }
    if (absoluteWidth == null) {
      if (other.absoluteWidth != null) {
        return false;
      }
    } else if (!absoluteWidth.equals(other.absoluteWidth)) {
      return false;
    }
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
