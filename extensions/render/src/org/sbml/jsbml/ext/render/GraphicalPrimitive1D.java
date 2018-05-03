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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class GraphicalPrimitive1D extends Transformation2D {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 3705246334810811216L;
  /**
   * 
   */
  protected String stroke;
  /**
   * 
   */
  protected List<Short> strokeDashArray;
  /**
   * 
   */
  protected Double strokeWidth;


  /**
   * @return the value of strokeDashArray
   */
  public List<Short> getStrokeDashArray() {
    if (!isSetStrokeDashArray()) {
      strokeDashArray = new ArrayList<Short>();
    }
    return strokeDashArray;
  }


  /**
   * @return whether strokeDashArray is set
   */
  public boolean isSetStrokeDashArray() {
    return strokeDashArray != null;
  }

  /**
   * 
   * @param strokeDashArray
   */
  public void setStrokeDashArray(Short[] strokeDashArray) {
    setStrokeDashArray(Arrays.asList(strokeDashArray));
  }

  /**
   * Set the value of strokeDashArray
   * 
   * @param strokeDashArray
   */
  public boolean setStrokeDashArray(List<Short> strokeDashArray) {
    List<Short> oldStrokeDashArray = this.strokeDashArray;
    this.strokeDashArray = strokeDashArray;
    firePropertyChange(RenderConstants.strokeDashArray, oldStrokeDashArray, this.strokeDashArray);
    return strokeDashArray != oldStrokeDashArray;
  }


  /**
   * Unsets the variable strokeDashArray
   * @return {@code true}, if strokeDashArray was set before,
   *         otherwise {@code false}
   */
  public boolean unsetStrokeDashArray() {
    return setStrokeDashArray((List<Short>) null);
  }

  /**
   * Creates an GraphicalPrimitive1D instance
   */
  public GraphicalPrimitive1D() {
    super();
    initDefaults();
  }

  /**
   * Creates an GraphicalPrimitive1D instance
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public GraphicalPrimitive1D(int level, int version) {
    super(level, version);
    initDefaults();
  }


  /**
   * Clone constructor
   * @param obj
   */
  public GraphicalPrimitive1D(GraphicalPrimitive1D obj) {
    super(obj);
    stroke = obj.stroke;
    strokeWidth = obj.strokeWidth;

    if (obj.isSetStrokeDashArray()) {
      for (int i = 0; i < obj.getStrokeDashArray().size(); i++) {
        addStrokeDash(new Short(obj.getStrokeDash(i)));
      }
    }
  }

  /**
   * 
   * @param s
   * @return
   */
  public boolean addStrokeDash(Short s) {
    return getStrokeDashArray().add(s);
  }


  /**
   * 
   * @param i
   * @return
   */
  public Short getStrokeDash(int i) {
    return isSetStrokeDashArray() ? strokeDashArray.get(i) : null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Transformation2D#clone()
   */
  @Override
  public GraphicalPrimitive1D clone() {
    return new GraphicalPrimitive1D(this);
  }



  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3181;
    int result = super.hashCode();
    result = prime * result + ((stroke == null) ? 0 : stroke.hashCode());
    if (isSetStrokeDashArray()) {
      result = prime * result + strokeDashArray.hashCode();
    }
    result = prime * result
        + ((strokeWidth == null) ? 0 : strokeWidth.hashCode());
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
    GraphicalPrimitive1D other = (GraphicalPrimitive1D) obj;
    if (stroke == null) {
      if (other.stroke != null) {
        return false;
      }
    } else if (!stroke.equals(other.stroke)) {
      return false;
    }
    if ((isSetStrokeDashArray() != other.isSetStrokeDashArray())
        || (isSetStrokeDashArray() && !getStrokeDashArray().equals(other.getStrokeDashArray()))) {
      return false;
    }
    if (strokeWidth == null) {
      if (other.strokeWidth != null) {
        return false;
      }
    } else if (!strokeWidth.equals(other.strokeWidth)) {
      return false;
    }
    return true;
  }

  /**
   * @return the value of stroke
   */
  public String getStroke() {
    if (isSetStroke()) {
      return stroke;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.stroke, this);
  }

  /**
   * @return the value of strokeWidth
   */
  public Double getStrokeWidth() {
    if (isSetStrokeWidth()) {
      return strokeWidth;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.strokeWidth, this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Transformation2D#initDefaults()
   */
  @Override
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;
  }

  /**
   * @return whether stroke is set
   */
  public boolean isSetStroke() {
    return stroke != null;
  }

  /**
   * @return whether strokeWidth is set
   */
  public boolean isSetStrokeWidth() {
    return strokeWidth != null;
  }
  /**
   * Set the value of stroke
   * @param stroke
   */
  public void setStroke(String stroke) {
    String oldStroke = this.stroke;
    this.stroke = stroke;
    firePropertyChange(RenderConstants.stroke, oldStroke, this.stroke);
  }

  /**
   * Set the value of strokeWidth
   * @param strokeWidth
   */
  public void setStrokeWidth(double strokeWidth) {
    Double oldStrokeWidth = this.strokeWidth;
    this.strokeWidth = strokeWidth;
    firePropertyChange(RenderConstants.strokeWidth, oldStrokeWidth, this.strokeWidth);
  }

  /**
   * Unsets the variable stroke
   * @return {@code true}, if stroke was set before,
   *         otherwise {@code false}
   */
  public boolean unsetStroke() {
    if (isSetStroke()) {
      String oldStroke = stroke;
      stroke = null;
      firePropertyChange(RenderConstants.stroke, oldStroke, stroke);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable strokeWidth
   * @return {@code true}, if strokeWidth was set before,
   *         otherwise {@code false}
   */
  public boolean unsetStrokeWidth() {
    if (isSetStrokeWidth()) {
      Double oldStrokeWidth = strokeWidth;
      strokeWidth = null;
      firePropertyChange(RenderConstants.strokeWidth, oldStrokeWidth, strokeWidth);
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

    if (isSetStroke()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.stroke,
        getStroke());
    }
    if (isSetStrokeDashArray()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.strokeDashArray,
        XMLTools.encodeArrayShortToString(getStrokeDashArray().toArray(new Short[0])));
    }
    if (isSetStrokeWidth()) {
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.strokeWidth,
        getStrokeWidth().toString().toLowerCase());
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

      if (attributeName.equals(RenderConstants.stroke)) {
        setStroke(value);
      }
      else if (attributeName.equals(RenderConstants.strokeDashArray)) {
        setStrokeDashArray(XMLTools.decodeStringToArrayShort(value));
      }
      else if (attributeName.equals(RenderConstants.strokeWidth)) {
        setStrokeWidth(StringTools.parseSBMLDouble(value));
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
