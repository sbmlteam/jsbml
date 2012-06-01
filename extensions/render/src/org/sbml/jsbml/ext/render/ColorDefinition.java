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

import java.awt.Color;

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
public class ColorDefinition extends AbstractSBase {

  /**
   * 
   */
  private static final long serialVersionUID = 8904459123022343452L;
  
  private String id;
  private Color value;
	

  /**
   * Creates a ColorDefinition instance with an id and a color value 
   * 
   * @param id
   * @param value
   */
  public ColorDefinition(String id, Color value) {
    this.id = id;
    this.value = value;
  }


  /**
   * Creates a ColorDefinition instance with an id, color value, level, and version. 
   * 
   * @param id
   * @param value
   * @param level
   * @param version
   */
  public ColorDefinition(String id, Color value, int level, int version) {
    super(level, version);
    if (getLevelAndVersion().compareTo(Integer.valueOf(RenderConstants.MIN_SBML_LEVEL),
      Integer.valueOf(RenderConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    this.id = id;
    this.value = value;
  }


  /**
   * Clone constructor
   */
  public ColorDefinition(ColorDefinition obj) {
    super(obj);
    this.id = obj.id;
    this.value = obj.value;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  //@Override
  public ColorDefinition clone() {
    return new ColorDefinition(this);
  }
  
  /**
   * @return the value of id
   */
  public String getId(){
    return this.id;
  }
  
  /**
   * Set the value of id
   */
  public void setId(String id){
    String oldId = this.id;
    this.id = id;
    firePropertyChange(RenderConstants.id, oldId, this.id);
  }
  
  
  /**
   * @return the value of value
   */
  public Color getValue() {
    if (isSetValue()) {
      Color val = value;
      return val;
    } else {
      return null;
    }
  }


  /**
   * @return whether value is set 
   */
  public boolean isSetValue() {
    return this.value != null;
  }


  /**
   * Set the value of value
   */
  public void setValue(Color value) {
    Color oldValue = this.value;
    this.value = value;
    firePropertyChange(RenderConstants.value, oldValue, this.value);
  }


  /**
   * Unsets the variable value 
   * @return <code>true</code>, if value was set before, 
   *         otherwise <code>false</code>
   */
  public boolean unsetValue() {
    if (isSetValue()) {
      Color oldValue = this.value;
      this.value = null;
      firePropertyChange(RenderConstants.value, oldValue, this.value);
      return true;
    }
    return false;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#toString()
   */
   //@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
