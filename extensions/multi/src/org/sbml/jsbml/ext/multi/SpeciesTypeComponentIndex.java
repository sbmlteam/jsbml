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
package org.sbml.jsbml.ext.multi;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;

/**
 * Provides a way to identify or index a component within a {@link MultiSpeciesType}.
 *
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class SpeciesTypeComponentIndex extends AbstractNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 8707971676849587247L;

  /**
   * 
   */
  private String component;

  /**
   * 
   */
  private String identifyingParent;


  /**
   * Creates an SpeciesTypeComponentIndex instance
   */
  public SpeciesTypeComponentIndex() {
    super();
    initDefaults();
  }


  /**
   * Creates a SpeciesTypeComponentIndex instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public SpeciesTypeComponentIndex(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a SpeciesTypeComponentIndex instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public SpeciesTypeComponentIndex(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a SpeciesTypeComponentIndex instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public SpeciesTypeComponentIndex(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a SpeciesTypeComponentIndex instance with an id, name, level, and version.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public SpeciesTypeComponentIndex(String id, String name, int level,
    int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(
      Integer.valueOf(MultiConstants.MIN_SBML_LEVEL),
      Integer.valueOf(MultiConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }


  /**
   * Clone constructor
   * 
   * @param obj the {@link SpeciesTypeComponentIndex} instance to clone
   */
  public SpeciesTypeComponentIndex(SpeciesTypeComponentIndex obj) {
    super(obj);

    // copy all class attributes
    if (obj.isSetComponent()) {
      setComponent(obj.getComponent());
    }
    if (obj.isSetIndentifyingParent()) {
      setIndentifyingParent(obj.getIndentifyingParent());
    }
  }


  /**
   * clones this class
   */
  @Override
  public SpeciesTypeComponentIndex clone() {
    return new SpeciesTypeComponentIndex(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    packageName = MultiConstants.shortLabel;
    setPackageVersion(-1);
  }




  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 5743;
    int result = super.hashCode();
    result = prime * result + ((component == null) ? 0 : component.hashCode());
    result = prime * result
        + ((identifyingParent == null) ? 0 : identifyingParent.hashCode());
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
    SpeciesTypeComponentIndex other = (SpeciesTypeComponentIndex) obj;
    if (component == null) {
      if (other.component != null) {
        return false;
      }
    } else if (!component.equals(other.component)) {
      return false;
    }
    if (identifyingParent == null) {
      if (other.identifyingParent != null) {
        return false;
      }
    } else if (!identifyingParent.equals(other.identifyingParent)) {
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
   * Returns the value of {@link #component}.
   *
   * @return the value of {@link #component}.
   */
  public String getComponent() {
    if (isSetComponent()) {
      return component;
    }

    return null;
  }


  /**
   * Returns whether {@link #component} is set.
   *
   * @return whether {@link #component} is set.
   */
  public boolean isSetComponent() {
    return component != null;
  }


  /**
   * Sets the value of component
   *
   * @param component the value of component to be set.
   */
  public void setComponent(String component) {
    String oldComponent = this.component;
    this.component = component;
    firePropertyChange(MultiConstants.component, oldComponent, this.component);
  }


  /**
   * Unsets the variable component.
   *
   * @return {@code true} if component was set before, otherwise {@code false}.
   */
  public boolean unsetComponent() {
    if (isSetComponent()) {
      String oldComponent = component;
      component = null;
      firePropertyChange(MultiConstants.component, oldComponent, component);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of {@link #identifyingParent}.
   *
   * @return the value of {@link #identifyingParent}.
   */
  public String getIndentifyingParent() {
    if (isSetIndentifyingParent()) {
      return identifyingParent;
    }

    return null;
  }


  /**
   * Returns whether {@link #identifyingParent} is set.
   *
   * @return whether {@link #identifyingParent} is set.
   */
  public boolean isSetIndentifyingParent() {
    return identifyingParent != null;
  }


  /**
   * Sets the value of identifyingParent
   *
   * @param identifyingParent the value of identifyingParent to be set.
   */
  public void setIndentifyingParent(String identifyingParent) {
    String oldIndentifyingParent = this.identifyingParent;
    this.identifyingParent = identifyingParent;
    firePropertyChange(MultiConstants.identifyingParent, oldIndentifyingParent, this.identifyingParent);
  }


  /**
   * Unsets the variable identifyingParent.
   *
   * @return {@code true} if identifyingParent was set before, otherwise {@code false}.
   */
  public boolean unsetIndentifyingParent() {
    if (isSetIndentifyingParent()) {
      String oldIndentifyingParent = identifyingParent;
      identifyingParent = null;
      firePropertyChange(MultiConstants.identifyingParent, oldIndentifyingParent, identifyingParent);
      return true;
    }
    return false;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(MultiConstants.shortLabel+ ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(MultiConstants.shortLabel+ ":name", getName());
    }

    if (isSetComponent()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.component, getComponent());
    }
    if (isSetIndentifyingParent()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.identifyingParent, getComponent());
    }

    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(MultiConstants.component)) {
        setComponent(value);
      }
      else if (attributeName.equals(MultiConstants.identifyingParent)) {
        setIndentifyingParent(value);
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
