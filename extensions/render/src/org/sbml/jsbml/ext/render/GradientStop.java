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

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.LevelVersionError;


/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 08.05.2012
 */
public class GradientStop extends AbstractSBase { 
  /**
   * 
   */
  private static final long serialVersionUID = 7400974339251884133L;
  
  private Double offset;
  private ColorDefinition stopColor;


  /**
   * Creates a GradientStop instance with an offset and a color. 
   * 
   * @param offset
   * @param color
   */
  public GradientStop(Double offset, ColorDefinition stopColor) {
    this.offset = offset;
    this.stopColor = stopColor;
  }


  /**
   * Creates a GradientStop instance with an offset, color, level, and version. 
   * 
   * @param offset
   * @param color
   * @param level
   * @param version
   */
  public GradientStop(Double offset, ColorDefinition stopColor, int level, int version) {
    super(level, version);
    if (getLevelAndVersion().compareTo(Integer.valueOf(MIN_SBML_LEVEL),
      Integer.valueOf(MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    this.offset = offset;
    this.stopColor = stopColor;
  }


  /**
   * Clone constructor
   */
  public GradientStop(GradientStop obj) {
    super(obj);
    this.offset = obj.offset;
    this.stopColor = obj.stopColor;
  }


  /**
   * clones this class
   */
  public GradientStop clone() {
    return new GradientStop(this);
  }
  
  
  /**
   * @return the value of offset
   */
  public Double getOffset() {
      return offset;
  }


  /**
   * Set the value of offset
   */
  public void setOffset(Double offset) {
    //int oldOffset = this.offset;
    this.offset = offset;
    //TODO
    //firePropertyChange(constant_class.offset, oldOffset, this.offset);
  }
  
  
  /**
   * @return the value of stopColor
   */
  public ColorDefinition getStopColor() {
    return stopColor;
  }

  /**
   * Set the value of stopColor
   */
  public void setStopColor(ColorDefinition stopColor) {
    //ColorDefinition oldStopColor = this.stopColor;
    this.stopColor = stopColor;
    //TODO
    //firePropertyChange(constant_class.stopColor, oldStopColor, this.stopColor);
  }

  public static final int MIN_SBML_LEVEL = 3;
  public static final int MIN_SBML_VERSION = 1;

	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}
