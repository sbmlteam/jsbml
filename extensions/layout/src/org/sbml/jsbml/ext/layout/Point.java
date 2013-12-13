/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.layout;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.StringTools;

import de.zbit.util.ResourceManager;

/**
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @author Clemens Wrzodek
 * @since 1.0
 * @version $Rev$
 */
public class Point extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * 
   */
  private static final long serialVersionUID = -7464572763198848890L;

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(Point.class);

  /**
   * 
   */
  private double x;
  /**
   * 
   */
  private double y;
  /**
   * 
   */
  private double z;

  /**
   * 
   */
  public Point() {
    super();
    setNamespace(LayoutConstants.namespaceURI);
    x = y = z = Double.NaN;
  }

  /**
   * 
   * @param x
   * @param y
   * @param z
   */
  public Point(double x, double y) {
    this();
    this.x = x;
    this.y = y;
    z = Double.NaN;
  }

  /**
   * 
   * @param x
   * @param y
   * @param z
   */
  public Point(double x, double y, double z) {
    this();
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * 
   * @param x
   * @param y
   * @param z
   * @param level
   * @param version
   */
  public Point(double x, double y, double z, int level, int version) {
    this(level, version);
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * 
   * @param level
   * @param version
   */
  public Point(int level, int version) {
    super(level, version);
    setNamespace(LayoutConstants.namespaceURI);
    x = y = z = Double.NaN;
  }

  /**
   * 
   * @param point
   */
  public Point(Point point) {
    super(point);
    clonePointAttributes(point, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public Point clone() {
    return new Point(this);
  }

  /**
   * 
   * @param point
   * @param cloned
   */
  protected void clonePointAttributes(Point point, Point cloned) {
    if (point.isSetX()) {
      cloned.setX(point.getX());
    } else {
      cloned.setX(Double.NaN);
    }
    if (point.isSetY()) {
      cloned.setY(point.getY());
    } else {
      cloned.setY(Double.NaN);
    }
    if (point.isSetZ()) {
      cloned.setZ(point.getZ());
    } else {
      cloned.setZ(Double.NaN);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      Point p = (Point) object;
      equals &= p.isSetX() == isSetX();
      if (equals && isSetX()) {
        equals &= Double.valueOf(p.getX()).equals(Double.valueOf(getX()));
      }
      equals &= p.isSetY() == isSetY();
      if (equals && isSetY()) {
        equals &= Double.valueOf(p.getY()).equals(Double.valueOf(getY()));
      }
      equals &= p.isSetZ() == isSetZ();
      if (equals && isSetZ()) {
        equals &= Double.valueOf(p.getZ()).equals(Double.valueOf(getZ()));
      }
    }
    return equals;
  }

  /**
   * 
   * @return
   */
  public double getX() {
    return x;
  }

  /**
   * 
   * @return
   */
  public double getY() {
    return y;
  }

  /**
   * 
   * @return
   */
  public double getZ() {
    return z;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 947;
    int hashCode = super.hashCode();
    hashCode += prime * Double.valueOf(x).hashCode();
    hashCode += prime * Double.valueOf(y).hashCode();
    hashCode += prime * Double.valueOf(z).hashCode();
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
  }

  /**
   * @return
   */
  public boolean isSetX() {
    return !Double.isNaN(x);
  }

  /**
   * @return
   */
  public boolean isSetY() {
    return !Double.isNaN(y);
  }

  /**
   * @return
   */
  public boolean isSetZ() {
    return !Double.isNaN(z);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);

    if (!isAttributeRead) {

      isAttributeRead = true;

      if (attributeName.equals(LayoutConstants.x)) {
        setX(StringTools.parseSBMLDouble(value));
      }
      else if (attributeName.equals(LayoutConstants.y))
      {
        setY(StringTools.parseSBMLDouble(value));
      }
      else if (attributeName.equals(LayoutConstants.z))
      {
        setZ(StringTools.parseSBMLDouble(value));
      }
      else {
        return false;
      }
    }

    return isAttributeRead;
  }

  /**
   * 
   * @param x
   */
  public void setX(double x) {
    Double oldX = this.x;
    this.x = x;
    firePropertyChange(LayoutConstants.x, oldX, this.x);
  }

  /**
   * 
   * @param y
   */
  public void setY(double y) {
    Double oldY = this.y;
    this.y = y;
    firePropertyChange(LayoutConstants.y, oldY, this.y);
  }

  /**
   * 
   * @param z
   */
  public void setZ(double z) {
    Double oldZ = this.z;
    this.z = z;
    firePropertyChange(LayoutConstants.z, oldZ, this.z);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#toString()
   */
  @Override
  public String toString() {
    return "Point [" + x + ", " + y + ", " + z + ']';
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(LayoutConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      logger.warn(MessageFormat.format(
        ResourceManager.getBundle("org.sbml.jsbml.resources.cfg.Messages").getString("UNDEFINED_ATTRIBUTE"),
        "name", getLevel(), getVersion(), getElementName()));
      // TODO: This must be generally solved. Here we have an SBase with ID but without name!
    }

    if (isSetX()) {
      attributes.put(LayoutConstants.shortLabel + ':'
        + LayoutConstants.x, StringTools.toString(Locale.ENGLISH, x));
    }
    if (isSetY()) {
      attributes.put(LayoutConstants.shortLabel + ':'
        + LayoutConstants.y, StringTools.toString(Locale.ENGLISH, y));
    }
    if (isSetZ()) {
      attributes.put(LayoutConstants.shortLabel + ':'
        + LayoutConstants.z, StringTools.toString(Locale.ENGLISH, z));
    }

    return attributes;
  }

}
