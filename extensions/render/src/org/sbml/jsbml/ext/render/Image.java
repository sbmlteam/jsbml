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
  private RelAbsVector x;
  /**
   * 
   */
  private RelAbsVector y;
  /**
   * 
   */
  private RelAbsVector z;
  /**
   * 
   */
  private RelAbsVector width;
  /**
   * 
   */
  private RelAbsVector height;
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

    z = new RelAbsVector(0d);
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3137;
    int result = super.hashCode();
    
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
   * @return the value of x
   */
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
  public boolean isSetX() {
    return x != null;
  }

  /**
   * Set the value of x
   * @param x
   */
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
  public boolean isSetY() {
    return y != null;
  }

  /**
   * Set the value of y
   * @param y
   */
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
  public boolean isSetZ() {
    return z != null;
  }

  /**
   * Set the value of z
   * @param z
   */
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
  public boolean unsetZ() {
    if (isSetZ()) {
      RelAbsVector oldZ = z;
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
        x.getCoordinate());
    }
    if (isSetY()) {
      attributes.remove(RenderConstants.y);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.y,
        y.getCoordinate());
    }
    if (isSetZ()) {
      attributes.remove(RenderConstants.z);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.z,
        z.getCoordinate());
    }
    if (isSetWidth()) {
      attributes.remove(RenderConstants.width);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.width,
        width.getCoordinate());
    }
    if (isSetHeight()) {
      attributes.remove(RenderConstants.height);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.height,
        width.getCoordinate());
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
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
