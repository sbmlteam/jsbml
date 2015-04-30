/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
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


public class CompartmentReference extends AbstractNamedSBase {

  private String compartment; 
  
  /**
   * Creates an CompartmentReference instance 
   */
  public CompartmentReference() {
    super();
    initDefaults();
  }


  /**
   * Creates a CompartmentReference instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public CompartmentReference(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a CompartmentReference instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public CompartmentReference(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a CompartmentReference instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public CompartmentReference(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a CompartmentReference instance with an id, name, level, and version.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public CompartmentReference(String id, String name, int level, int version) {
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
   */
  public CompartmentReference(CompartmentReference obj) {
    super(obj);
    
    // compartment
    if (obj.isSetCompartment()) {
      setCompartment(obj.getCompartment());
    }
  }


  /**
   * clones this class
   */
  public CompartmentReference clone() {
    return new CompartmentReference(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(MultiConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
    packageName = MultiConstants.shortLabel;
    setPackageVersion(-1);
  }
  

  @Override
  public boolean isIdMandatory() {
    return false;
  }

  
  /**
   * Returns the value of {@link #compartment}.
   *
   * @return the value of {@link #compartment}.
   */
  public String getCompartment() {
    if (isSetCompartment()) {
      return compartment;
    }

    return null;
  }


  /**
   * Returns whether {@link #compartment} is set.
   *
   * @return whether {@link #compartment} is set.
   */
  public boolean isSetCompartment() {
    return compartment != null;
  }


  /**
   * Sets the value of compartment
   *
   * @param compartment the value of compartment to be set.
   */
  public void setCompartment(String compartment) {
    String oldCompartment = this.compartment;
    this.compartment = compartment;
    firePropertyChange(MultiConstants.compartment, oldCompartment, this.compartment);
  }


  /**
   * Unsets the variable compartment.
   *
   * @return {@code true} if compartment was set before, otherwise {@code false}.
   */
  public boolean unsetCompartment() {
    if (isSetCompartment()) {
      String oldCompartment = this.compartment;
      this.compartment = null;
      firePropertyChange(MultiConstants.compartment, oldCompartment, this.compartment);
      return true;
    }
    return false;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(MultiConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(MultiConstants.shortLabel + ":name", getName());
    }

    if (isSetCompartment()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.compartment, getCompartment());
    }
    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(MultiConstants.compartment)) {
        setCompartment(value);
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
