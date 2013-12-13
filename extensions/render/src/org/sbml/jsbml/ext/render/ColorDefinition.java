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
package org.sbml.jsbml.ext.render;

import java.awt.Color;
import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
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
public class ColorDefinition extends AbstractNamedSBase {

  /**
   * 
   */
  private static final long serialVersionUID = 8904459123022343452L;
  
  private Color value;
	
  /**
   * Creates an ColorDefinition instance 
   */
  public ColorDefinition() {
    super();
    initDefaults();
  }


  /**
   * Creates a ColorDefinition instance with an id. 
   * 
   * @param id
   */
  public ColorDefinition(String id) {
    super(id);
    initDefaults();
  }
  
  /**
   * Creates a ColorDefinition instance with an id. 
   * 
   * @param id
   */
  public ColorDefinition(String id, Color value) {
    super(id);
    this.value = value;
    initDefaults();
  }


  /**
   * Creates a ColorDefinition instance with a level and version. 
   * 
   * @param level
   * @param version
   */
  public ColorDefinition(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a ColorDefinition instance with an id, level, and version. 
   * 
   * @param id
   * @param level
   * @param version
   */
  public ColorDefinition(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a ColorDefinition instance with an id, name, level, and version. 
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public ColorDefinition(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(Integer.valueOf(RenderConstants.MIN_SBML_LEVEL),
      Integer.valueOf(RenderConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public ColorDefinition(ColorDefinition obj) {
    super(obj);
    value = obj.value;
  }


  /**
   * clones this class
   */
  public ColorDefinition clone() {
    return new ColorDefinition(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(RenderConstants.namespaceURI);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  public boolean isIdMandatory() {
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
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = 0;
    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public SBase getChildAt(int childIndex) {
    return null;
  }
  
  
  
  /**
   * @return the value of value
   */
  public Color getValue() {
    if (isSetValue()) {
      return value;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.value, this);
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
   * @return {@code true}, if value was set before, 
   *         otherwise {@code false}
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
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetValue()) {
      attributes.remove(RenderConstants.value);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.value,
        XMLTools.encodeColorToString(getValue()));
    }
    return attributes;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(RenderConstants.value)) {
        setValue(XMLTools.decodeStringToColor(value));
      }
      // END TODO
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
