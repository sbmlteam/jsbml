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
package org.sbml.jsbml.ext.layout;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.ResourceManager;
import org.sbml.jsbml.util.StringTools;

/**
 * The representation of a point in the "layout" package.
 * 
 * <p>A {@link Point} is specified via the required attributes 'x', 'y' and an optional
 * attribute 'z', all of which are of type double. If the attribute z is not
 * specified, the object is a two dimensional object. The {@link Point} class also has
 * an optional attribute id of type SId. While not used in the {@link Layout} package,
 * it can be used by programs to refer to the elements.
 * 
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @author Clemens Wrzodek
 * @since 1.0
 */
public class Point extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * 
   */
  private static final long serialVersionUID = -7464572763198848890L;

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(Point.class);

  /**
   * the x coordinate
   */
  private double x;

  /**
   * the y coordinate
   */
  private double y;

  /**
   * the z coordinate
   */
  private double z;

  /**
   * The name to be used when writing this point to XML.
   */
  private String xmlElementName;

  /**
   * Creates a new {@link Point} instance with all coordinates set to {@link Double#NaN}.
   * 
   */
  public Point() {
    super();
    initDefaults();
  }

  /**
   * Creates a new {@link Point} instance with the given x and y coordinates. z is set to {@link Double#NaN}.
   * 
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public Point(double x, double y) {
    this();
    this.x = x;
    this.y = y;
    z = Double.NaN;
  }

  /**
   * Creates a new {@link Point} instance with the given coordinates.
   * 
   * @param x the x coordinate
   * @param y the y coordinate
   * @param z the z coordinate
   */
  public Point(double x, double y, double z) {
    this();
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Creates a new {@link Point} instance with the given coordinates and level and version.
   * 
   * @param x the x coordinate
   * @param y the y coordinate
   * @param z the z coordinate
   * @param level the SBML level
   * @param version the SBML version
   */
  public Point(double x, double y, double z, int level, int version) {
    this(level, version);
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Creates a new {@link Point} instance with the given level and version,  all coordinates are set to {@link Double#NaN}.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public Point(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Creates a new {@link Point} instance with all attributes cloned from the given {@link Point}.
   * 
   * @param point the {@link Point} instance to clone.
   */
  public Point(Point point) {
    super(point);
    clonePointAttributes(point, this);
    setElementName(point.getElementName());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public Point clone() {
    return new Point(this);
  }

  /**
   * Clones the x, y and z coordinates from point to cloned.
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

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = LayoutConstants.shortLabel;

    x = y = z = Double.NaN;
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getElementName()
   */
  @Override
  public String getElementName() {
    return xmlElementName;
  }

  /**
   * Gets the x coordinate.
   * 
   * <p>Returns {@link Double#NaN} if {@link #isSetX()}
   * return false.
   * 
   * @return the x coordinate.
   */
  public double getX() {
    return x;
  }

  /**
   * Gets the y coordinate.
   * 
   * <p>Returns {@link Double#NaN} if {@link #isSetY()}
   * return false.
   * 
   * @return the y coordinate.
   */
  public double getY() {
    return y;
  }

  /**
   * Gets the z coordinate.
   * 
   * <p>Returns {@link Double#NaN} if {@link #isSetZ()}
   * return false.
   * 
   * @return the z coordinate.
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
   * Returns {@code true} if the x coordinate is set.
   * 
   * @return {@code true} if the x coordinate is set.
   */
  public boolean isSetX() {
    return !Double.isNaN(x);
  }

  /**
   * Returns {@code true} if the y coordinate is set.
   * 
   * @return {@code true} if the y coordinate is set.
   */
  public boolean isSetY() {
    return !Double.isNaN(y);
  }

  /**
   * Returns {@code true} if the z coordinate is set.
   * 
   * @return {@code true} if the z coordinate is set.
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
        Double valueDouble = StringTools.parseSBMLDouble(value); 
            
        // If the value is NaN, we could have encountered a NumberFormatExpection
        // So we parse it again to be sure as StringTools.parseSBMLDouble does not propagate the exception.
        if (valueDouble.isNaN()) {
          try {
            Double.parseDouble(value);
          } catch (NumberFormatException e) {
            // The value is a wrong double so don't set it and return false so that it is put in the unknown attributes
            return false;
          }    
        }
        
        setX(valueDouble);
      }
      else if (attributeName.equals(LayoutConstants.y))
      {
        Double valueDouble = StringTools.parseSBMLDouble(value); 
        
        // If the value is NaN, we could have encountered a NumberFormatExpection
        // So we parse it again to be sure as StringTools.parseSBMLDouble does not propagate the exception.
        if (valueDouble.isNaN()) {
          try {
            Double.parseDouble(value);
          } catch (NumberFormatException e) {
            // The value is a wrong double so don't set it and return false so that it is put in the unknown attributes
            return false;
          }    
        }
        
        setY(valueDouble);
      }
      else if (attributeName.equals(LayoutConstants.z))
      {
        Double valueDouble = StringTools.parseSBMLDouble(value); 
        
        // If the value is NaN, we could have encountered a NumberFormatExpection
        // So we parse it again to be sure as StringTools.parseSBMLDouble does not propagate the exception.
        if (valueDouble.isNaN()) {
          try {
            Double.parseDouble(value);
          } catch (NumberFormatException e) {
            // The value is a wrong double so don't set it and return false so that it is put in the unknown attributes
            return false;
          }    
        }
        
        setZ(valueDouble);
      }
      else {
        return false;
      }
    }

    return isAttributeRead;
  }

  /**
   * Sets the name to be used when writing this {@link Point} to XML.
   * 
   * <p>This method should only be used internally.
   * 
   * @param elementName the xml element name.
   */
  void setElementName(String elementName) {
    xmlElementName = elementName;
  }

  /**
   * Sets the x coordinate.
   * 
   * <p>The general {@link Point} class specifies the x, y, and z (optional) attributes
   * which defines the graphical location with respect to the coordinate system
   * of the {@link Layout} extension. The x attribute is required.
   * 
   * @param x the x coordinate to be set.
   */
  public void setX(double x) {
    Double oldX = this.x;
    this.x = x;
    firePropertyChange(LayoutConstants.x, oldX, this.x);
  }

  /**
   * Sets the y coordinate.
   * 
   * <p>The y attribute is required. For a more general description of the dimension
   * attributes, see {@link #setX(double)}.
   * 
   * @param y the y coordinate to be set.
   */
  public void setY(double y) {
    Double oldY = this.y;
    this.y = y;
    firePropertyChange(LayoutConstants.y, oldY, this.y);
  }

  /**
   * Sets the z coordinate.
   * 
   * <p>The z attribute is optional. The layout is 2-dimensional if the z attribute is
   * not specified.
   * 
   * @param z the z coordinate to be set.
   */
  public void setZ(double z) {
    Double oldZ = this.z;
    this.z = z;
    firePropertyChange(LayoutConstants.z, oldZ, this.z);
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

  /**
   * Gets the x coordinate.
   * 
   * @return the x coordinate.
   * @see #getX()
   * @libsbml.deprecated
   */
  public double x() {
    return getX();
  }

  /**
   * Gets the y coordinate.
   * 
   * @return the y coordinate.
   * @see #getY()
   * @libsbml.deprecated
   */
  public double y() {
    return getY();
  }

  /**
   * Gets the z coordinate.
   * 
   * @return the z coordinate.
   * @see #getZ()
   * @libsbml.deprecated
   */
  public double z() {
    return getZ();
  }

}
