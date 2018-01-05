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

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBase;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class GradientStop extends AbstractSBase {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 7400974339251884133L;


  /**
   * 
   */
  private Double offset;
  /**
   * 
   */
  private String stopColor;


  /**
   * Creates a GradientStop instance with an offset and a color.
   * 
   * @param offset
   * @param stopColor
   */
  public GradientStop(Double offset, String stopColor) {
    super();
    initDefaults();

    this.offset = offset;
    this.stopColor = stopColor;
  }

  /**
   * Creates a GradientStop instance with an offset, color, level, and version.
   * 
   * @param offset
   * @param stopColor
   * @param level
   * @param version
   */
  public GradientStop(Double offset, String stopColor, int level, int version) {
    super(level, version);
    if (getLevelAndVersion().compareTo(Integer.valueOf(RenderConstants.MIN_SBML_LEVEL),
      Integer.valueOf(RenderConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
    this.offset = offset;
    this.stopColor = stopColor;
  }

  /**
   * Clone constructor
   * @param obj
   */
  public GradientStop(GradientStop obj) {
    super(obj);
    offset = obj.offset;
    stopColor = obj.stopColor;
  }

  /**
   * 
   */
  public GradientStop() {
    super();
    initDefaults();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public GradientStop clone() {
    return new GradientStop(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3191;
    int result = super.hashCode();
    result = prime * result + ((offset == null) ? 0 : offset.hashCode());
    result = prime * result + ((stopColor == null) ? 0 : stopColor.hashCode());
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
    GradientStop other = (GradientStop) obj;
    if (offset == null) {
      if (other.offset != null) {
        return false;
      }
    } else if (!offset.equals(other.offset)) {
      return false;
    }
    if (stopColor == null) {
      if (other.stopColor != null) {
        return false;
      }
    } else if (!stopColor.equals(other.stopColor)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
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
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    return 0;
  }

  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getElementName()
   */
  @Override
  public String getElementName() {
    return RenderConstants.stop;
  }

  /**
   * @return the value of offset
   */
  public double getOffset() {
    if (isSetOffset()) {
      return offset;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.offset, this);
  }

  /**
   * @return the value of stopColor
   */
  public String getStopColor() {
    if (isSetStopColor()) {
      return stopColor;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.stopColor, this);
  }

  /**
   * @return whether offset is set
   */
  public boolean isSetOffset() {
    return offset != null;
  }

  /**
   * @return whether stopColor is set
   */
  public boolean isSetStopColor() {
    return stopColor != null;
  }

  /**
   * Set the value of offset
   * @param offset
   */
  public void setOffset(double offset) {
    Double oldOffset = this.offset;
    this.offset = offset;
    firePropertyChange(RenderConstants.offset, oldOffset, this.offset);
  }

  /**
   * Set the value of stopColor
   * @param stopColor
   */
  public void setStopColor(String stopColor) {
    String oldStopColor = this.stopColor;
    this.stopColor = stopColor;
    firePropertyChange(RenderConstants.stopColor, oldStopColor, this.stopColor);
  }

  /**
   * Unsets the variable offset
   * @return {@code true}, if offset was set before,
   *         otherwise {@code false}
   */
  public boolean unsetOffset() {
    if (isSetOffset()) {
      Double oldOffset = offset;
      offset = null;
      firePropertyChange(RenderConstants.offset, oldOffset, offset);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable stopColor
   * @return {@code true}, if stopColor was set before,
   *         otherwise {@code false}
   */
  public boolean unsetStopColor() {
    if (isSetStopColor()) {
      String oldStopColor = stopColor;
      stopColor = null;
      firePropertyChange(RenderConstants.stopColor, oldStopColor, stopColor);
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
    if (isSetOffset()) {
      attributes.remove(RenderConstants.offset);
      attributes.put(RenderConstants.shortLabel + ":" + RenderConstants.offset,
        XMLTools.positioningToString(getOffset(), false));
    }
    if (isSetStopColor()) {
      attributes.remove(RenderConstants.stopColor);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.stopColor,
        getStopColor());
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
      if (attributeName.equals(RenderConstants.offset)) {
        setOffset(XMLTools.parsePosition(value));
      }
      else if (attributeName.equals(RenderConstants.stopColor)) {
        setStopColor(value);
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
