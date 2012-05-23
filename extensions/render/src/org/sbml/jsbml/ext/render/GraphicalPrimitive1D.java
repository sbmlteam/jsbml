/*
 * $Id$
 * $URL$
 *
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2012 jointly by the following organizations: 
 * 1. The University of Tuebingen, Germany 
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
 * 3. The California Institute of Technology, Pasadena, CA, USA 
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

import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBase;


/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 08.05.2012
 */
public class GraphicalPrimitive1D extends Transformation2D {
	public static final int MIN_SBML_LEVEL = 3;
	public static final int MIN_SBML_VERSION = 1;
	protected ColorDefinition stroke;
	protected Short[] strokeDashArray;
  protected Integer strokeWidth;

  
  /**
   * @return the value of strokeDashArray
   */
  public Short[] getStrokeDashArray() {
    if (isSetStrokeDashArray()) {
      return strokeDashArray;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.strokeDashArray, this);
  }


  /**
   * @return whether strokeDashArray is set 
   */
  public boolean isSetStrokeDashArray() {
    return this.strokeDashArray != null;
  }


  /**
   * Set the value of strokeDashArray
   */
  public void setStrokeDashArray(Short[] strokeDashArray) {
    Short[] oldStrokeDashArray = this.strokeDashArray;
    this.strokeDashArray = strokeDashArray;
    firePropertyChange(RenderConstants.strokeDashArray, oldStrokeDashArray, this.strokeDashArray);
  }


  /**
   * Unsets the variable strokeDashArray 
   * @return <code>true</code>, if strokeDashArray was set before, 
   *         otherwise <code>false</code>
   */
  public boolean unsetStrokeDashArray() {
    if (isSetStrokeDashArray()) {
      Short[] oldStrokeDashArray = this.strokeDashArray;
      this.strokeDashArray = null;
      firePropertyChange(RenderConstants.strokeDashArray, oldStrokeDashArray, this.strokeDashArray);
      return true;
    }
    return false;
  }

  /**
   * Creates an GraphicalPrimitive1D instance 
   */
  public GraphicalPrimitive1D() {
    super();
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public GraphicalPrimitive1D(GraphicalPrimitive1D obj) {
    super();
    stroke = obj.stroke;
    strokeWidth = obj.strokeWidth;
    strokeDashArray = obj.strokeDashArray;
  }


  /**
   * clones this class
   */
  public GraphicalPrimitive1D clone() {
    return new GraphicalPrimitive1D(this);
  }

  
  @Override
  public boolean getAllowsChildren() {
    return false;
  }


  @Override
  public SBase getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(childIndex + " < 0");
    }
    int pos = 0;
    throw new IndexOutOfBoundsException(MessageFormat.format(
      "Index {0,number,integer} >= {1,number,integer}", childIndex,
      +((int) Math.min(pos, 0))));
  }


  @Override
  public int getChildCount() {
    return 0;
  }


  /**
   * @return the value of stroke
   */
  public ColorDefinition getStroke() {
    if (isSetStroke()) {
      return stroke;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.stroke, this);
  }
  
  /**
   * @return the value of strokeWidth
   */
  public int getStrokeWidth() {
    if (isSetStrokeWidth()) {
      return strokeWidth;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.strokeWidth, this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    addNamespace(RenderConstants.namespaceURI);
  }


  /**
   * @return whether stroke is set 
   */
  public boolean isSetStroke() {
    return this.stroke != null;
  }

  /**
   * @return whether strokeWidth is set 
   */
  public boolean isSetStrokeWidth() {
    return this.strokeWidth != null;
  }
  /**
   * Set the value of stroke
   */
  public void setStroke(ColorDefinition stroke) {
    ColorDefinition oldStroke = this.stroke;
    this.stroke = stroke;
    firePropertyChange(RenderConstants.stroke, oldStroke, this.stroke);
  }
	
  /**
   * Set the value of strokeWidth
   */
  public void setStrokeWidth(Integer strokeWidth) {
    Integer oldStrokeWidth = this.strokeWidth;
    this.strokeWidth = strokeWidth;
    firePropertyChange(RenderConstants.strokeWidth, oldStrokeWidth, this.strokeWidth);
  }

  /**
   * Unsets the variable stroke 
   * @return <code>true</code>, if stroke was set before, 
   *         otherwise <code>false</code>
   */
  public boolean unsetStroke() {
    if (isSetStroke()) {
      ColorDefinition oldStroke = this.stroke;
      this.stroke = null;
      firePropertyChange(RenderConstants.stroke, oldStroke, this.stroke);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable strokeWidth 
   * @return <code>true</code>, if strokeWidth was set before, 
   *         otherwise <code>false</code>
   */
  public boolean unsetStrokeWidth() {
    if (isSetStrokeWidth()) {
      Integer oldStrokeWidth = this.strokeWidth;
      this.strokeWidth = null;
      firePropertyChange(RenderConstants.strokeWidth, oldStrokeWidth, this.strokeWidth);
      return true;
    }
    return false;
  }
	
}
