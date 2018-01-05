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
public class Image extends Transformation2D { // TODO - need to extends UniqueNamedSBase, NamedSBase
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 6031110715160806863L;
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
  private String href;

  /**
   * Creates an Image instance
   */
  public Image() {
    super();
    initDefaults();
  }

  /**
   * Creates a Image instance with an id.
   * 
   * @param id
   */
  public Image(String id) {
    super();
    initDefaults();
  }

  /**
   * Clone constructor
   * @param obj
   */
  public Image(Image obj) {
    super();
    absoluteHeight = obj.absoluteHeight;
    absoluteWidth = obj.absoluteWidth;
    absoluteX = obj.absoluteX;
    absoluteY = obj.absoluteY;
    absoluteZ = obj.absoluteZ;
    height = obj.height;
    href = obj.href;
    width = obj.width;
    x = obj.x;
    y = obj.y;
    z = obj.z;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Transformation2D#clone()
   */
  @Override
  public Image clone() {
    return new Image(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Transformation2D#initDefaults()
   */
  @Override
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;

    z = 0d;
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
        + ((absoluteWidth == null) ? 0 : absoluteWidth.hashCode());
    result = prime * result + ((absoluteX == null) ? 0 : absoluteX.hashCode());
    result = prime * result + ((absoluteY == null) ? 0 : absoluteY.hashCode());
    result = prime * result + ((absoluteZ == null) ? 0 : absoluteZ.hashCode());
    result = prime * result + ((height == null) ? 0 : height.hashCode());
    result = prime * result + ((href == null) ? 0 : href.hashCode());
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
    Image other = (Image) obj;
    if (absoluteHeight == null) {
      if (other.absoluteHeight != null) {
        return false;
      }
    } else if (!absoluteHeight.equals(other.absoluteHeight)) {
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
    if (href == null) {
      if (other.href != null) {
        return false;
      }
    } else if (!href.equals(other.href)) {
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Transformation#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Transformation#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = 0;
    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Transformation#getChildAt(int)
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
   * @return the value of absoluteX
   */
  public boolean isAbsoluteX() {
    if (isSetAbsoluteX()) {
      return absoluteX;
    }

    return true;
  }

  /**
   * @return whether absoluteX is set
   */
  public boolean isSetAbsoluteX() {
    return absoluteX != null;
  }

  /**
   * Set the value of absoluteX
   * @param absoluteX
   */
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
  public boolean isAbsoluteY() {
    if (isSetAbsoluteY()) {
      return absoluteY;
    }

    return true;
  }

  /**
   * @return whether absoluteY is set
   */
  public boolean isSetAbsoluteY() {
    return absoluteY != null;
  }

  /**
   * Set the value of absoluteY
   * @param absoluteY
   */
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
  public boolean isAbsoluteZ() {
    if (isSetAbsoluteZ()) {
      return absoluteZ;
    }

    return true;
  }

  /**
   * @return whether absoluteZ is set
   */
  public boolean isSetAbsoluteZ() {
    return absoluteZ != null;
  }

  /**
   * Set the value of absoluteZ
   * @param absoluteZ
   */
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
   * @return the value of href
   */
  public String getHref() {
    if (isSetHref()) {
      return href;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.href, this);
  }

  /**
   * @return whether href is set
   */
  public boolean isSetHref() {
    return href != null;
  }

  /**
   * Set the value of href
   * @param href
   */
  public void setHref(String href) {
    String oldHref = this.href;
    this.href = href;
    firePropertyChange(RenderConstants.href, oldHref, this.href);
  }

  /**
   * Unsets the variable href
   * @return {@code true}, if href was set before,
   *         otherwise {@code false}
   */
  public boolean unsetHref() {
    if (isSetHref()) {
      String oldHref = href;
      href = null;
      firePropertyChange(RenderConstants.href, oldHref, href);
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
   * @return the value of x
   */
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
  public boolean isSetX() {
    return x != null;
  }

  /**
   * Set the value of x
   * @param x
   */
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
  public boolean isSetY() {
    return y != null;
  }

  /**
   * Set the value of y
   * @param y
   */
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
  public boolean isSetZ() {
    return z != null;
  }

  /**
   * Set the value of z
   * @param z
   */
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
    if (isSetHref()) {
      attributes.remove(RenderConstants.href);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.href,
        getHref());
    }
    if (isSetX()) {
      attributes.remove(RenderConstants.x);
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
      if (attributeName.equals(RenderConstants.href)) {
        setHref(value);
      }
      else if (attributeName.equals(RenderConstants.x)) {
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
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
