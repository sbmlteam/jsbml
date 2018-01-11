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

import java.awt.Color;
import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.PropertyUndefinedError;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class ColorDefinition extends AbstractNamedSBase {

  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 8904459123022343452L;

  /**
   * 
   */
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
   * @param value
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
   * @param obj
   */
  public ColorDefinition(ColorDefinition obj) {
    super(obj);
    
    if (obj.isSetValue()) {
      setValue(obj.getValue());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public ColorDefinition clone() {
    return new ColorDefinition(this);
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
    final int prime = 3109;
    int result = super.hashCode();
    result = prime * result + ((value == null) ? 0 : value.hashCode());
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
    ColorDefinition other = (ColorDefinition) obj;
    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return true;
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
    return value != null;
  }

  /**
   * Set the value of value
   * @param value
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
      Color oldValue = value;
      value = null;
      firePropertyChange(RenderConstants.value, oldValue, value);
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
    
    if (isSetId()) {
      attributes.remove("id");
      attributes.put(RenderConstants.shortLabel + ":id", getId());
    }
    
    if (isSetValue()) {
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
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
