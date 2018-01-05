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
 * A {@link Dimensions} is specified via the required attributes width,
 * height and an optional attribute depth, all of which are of type double.
 * If the attribute depth is not specified, the object is a two dimensional object.
 * The width attribute of {@link Dimensions} specifies the size of the object
 * in the direction of the positive x axis, the height attribute specifies the
 * size of the object along the positive y axis and the depth attribute specifies
 * the size of the object along the positive z axis. All sizes for {@link Dimensions}
 * objects are positive values, and so the attributes are not allowed to take
 * negative values. The {@link Dimensions} class also has an optional attribute
 * id of type SId. While not used in the {@link Layout} package, it can be used by
 * programs to refer to the elements.
 * 
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class Dimensions extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -6114634391235520057L;

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(Dimensions.class);

  /**
   * 
   */
  private double depth;
  /**
   * 
   */
  private double height;
  /**
   * 
   */
  private double width;

  /**
   * 
   */
  public Dimensions() {
    initDefaults();
  }

  /**
   * 
   * @param dimensions
   */
  public Dimensions(Dimensions dimensions) {
    super(dimensions);
    depth = height = width = Double.NaN;

    if (dimensions.isSetDepth()) {
      setDepth(dimensions.getDepth());
    } else {
      depth = Double.NaN;
    }
    if (dimensions.isSetHeight()) {
      setHeight(dimensions.getHeight());
    } else {
      height = Double.NaN;
    }
    if (dimensions.isSetWidth()) {
      setWidth(dimensions.getWidth());
    } else {
      width = Double.NaN;
    }
  }

  /**
   * 
   * @param width
   * @param height
   * @param depth
   * @param level
   * @param version
   */
  public Dimensions(double width, double height, double depth, int level, int version) {
    this(level, version);
    this.width = width;
    this.height = height;
    this.depth = depth;
  }

  /**
   * 
   * @param level
   * @param version
   */
  public Dimensions(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public Dimensions(String id, int level, int version) {
    super(id, level, version);
    initDefaults();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public Dimensions clone() {
    return new Dimensions(this);
  }

  /**
   * 
   */
  private void initDefaults() {
    setPackageVersion(-1);
    packageName = LayoutConstants.shortLabel;

    depth = height = width = Double.NaN;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      Dimensions d = (Dimensions) object;
      equals &= d.isSetDepth() == isSetDepth();
      if (equals && isSetDepth()) {
        equals &= Double.valueOf(d.getDepth()).equals(Double.valueOf(getDepth()));
      }
      equals &= d.isSetHeight() == isSetHeight();
      if (equals && isSetHeight()) {
        equals &= Double.valueOf(d.getHeight()).equals(Double.valueOf(getHeight()));
      }
      equals &= d.isSetWidth() == isSetWidth();
      if (equals && isSetWidth()) {
        equals &=  Double.valueOf(d.getWidth()).equals(Double.valueOf(getWidth()));
      }
    }
    return equals;
  }

  /**
   * 
   * @return
   */
  public double getDepth() {
    return depth;
  }

  /**
   * 
   * @return
   */
  public double getHeight() {
    return height;
  }

  /**
   * 
   * @return
   */
  public double getWidth() {
    return width;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 941;
    int hashCode = super.hashCode();
    hashCode += prime * Double.valueOf(depth).hashCode();
    hashCode += prime * Double.valueOf(height).hashCode();
    hashCode += prime * Double.valueOf(width).hashCode();
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
  public boolean isSetDepth() {
    return !Double.isNaN(depth);
  }

  /**
   * @return
   */
  public boolean isSetHeight() {
    return !Double.isNaN(height);
  }

  /**
   * @return
   */
  public boolean isSetWidth() {
    return !Double.isNaN(width);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);

    if (!isAttributeRead)
    {

      //isAttributeRead = true;
      if (attributeName.equals(LayoutConstants.width))
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

        setWidth(valueDouble);
      }
      else if (attributeName.equals(LayoutConstants.height))
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

        setHeight(valueDouble);
      }
      else if (attributeName.equals(LayoutConstants.depth))
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

        setDepth(valueDouble);
      }
      else
      {
        return false;
      }

      return true;
    }

    return isAttributeRead;
  }

  /**
   * Depth is an optional attribute and specifies the size along the positive z axis.
   * 
   * @param depth
   */
  public void setDepth(double depth) {
    Double oldDepth = this.depth;
    this.depth = depth;
    firePropertyChange(LayoutConstants.depth, oldDepth, this.depth);
  }

  /**
   * Height is a required attribute and specifies the size along the positive y axis.
   * 
   * @param height
   */
  public void setHeight(double height) {
    Double oldHeight = this.height;
    this.height = height;
    firePropertyChange(LayoutConstants.height, oldHeight, this.height);
  }

  /**
   * Width is a required attribute and specifies the size along the positive x axis.
   * 
   * @param width
   */
  public void setWidth(double width) {
    Double oldWidth = this.width;
    this.width = width;
    firePropertyChange(LayoutConstants.width, oldWidth, this.width);
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

    if (isSetDepth()) {
      attributes.put(LayoutConstants.shortLabel + ':'
        + LayoutConstants.depth, StringTools.toString(Locale.ENGLISH, depth));
    }
    if (isSetHeight()) {
      attributes.put(LayoutConstants.shortLabel + ':'
        + LayoutConstants.height, StringTools.toString(Locale.ENGLISH, height));
    }
    if (isSetWidth()) {
      attributes.put(LayoutConstants.shortLabel + ':'
        + LayoutConstants.width, StringTools.toString(Locale.ENGLISH, width));
    }

    return attributes;
  }

}
