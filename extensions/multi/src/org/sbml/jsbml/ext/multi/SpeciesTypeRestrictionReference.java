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

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.PropertyUndefinedError;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 16.10.2013
 */
public class SpeciesTypeRestrictionReference extends AbstractSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -8803949492166466113L;
  /**
   * 
   */
  private String speciesTypeRestriction;


  /**
   * @return the value of speciesTypeRestriction
   */
  public String getSpeciesTypeRestriction() {
    if (isSetSpeciesTypeRestriction()) {
      return speciesTypeRestriction;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(MultiConstants.speciesTypeRestriction, this);
  }

  /**
   * @return whether speciesTypeRestriction is set
   */
  public boolean isSetSpeciesTypeRestriction() {
    return speciesTypeRestriction != null;
  }

  /**
   * Set the value of speciesTypeRestriction
   * @param speciesTypeRestriction
   */
  public void setSpeciesTypeRestriction(String speciesTypeRestriction) {
    String oldSpeciesTypeRestriction = this.speciesTypeRestriction;
    this.speciesTypeRestriction = speciesTypeRestriction;
    firePropertyChange(MultiConstants.speciesTypeRestriction, oldSpeciesTypeRestriction, this.speciesTypeRestriction);
  }

  /**
   * Unsets the variable speciesTypeRestriction
   * @return {@code true}, if speciesTypeRestriction was set before,
   *         otherwise {@code false}
   */
  public boolean unsetSpeciesTypeRestriction() {
    if (isSetSpeciesTypeRestriction()) {
      String oldSpeciesTypeRestriction = speciesTypeRestriction;
      speciesTypeRestriction = null;
      firePropertyChange(MultiConstants.speciesTypeRestriction, oldSpeciesTypeRestriction, speciesTypeRestriction);
      return true;
    }
    return false;
  }


  @Override
  public String toString() {
    // TODO
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetSpeciesTypeRestriction()) {
      attributes.put(MultiConstants.shortLabel + ":speciesTypeRestriction",
        getSpeciesTypeRestriction());
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

      if (attributeName.equals(MultiConstants.speciesTypeRestriction)) {
        setSpeciesTypeRestriction(value);
      }
      else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }

  /**
   * Creates an SpeciesTypeRestrictionReference instance
   */
  public SpeciesTypeRestrictionReference() {
    super();
    initDefaults();
  }

  /**
   * Creates a SpeciesTypeRestrictionReference instance with a level and version.
   * 
   * @param level
   * @param version
   */
  public SpeciesTypeRestrictionReference(int level, int version) {
    super(level, version);
  }


  /**
   * Clone constructor
   * @param obj
   */
  public SpeciesTypeRestrictionReference(SpeciesTypeRestrictionReference obj) {
    super(obj);

    // TODO: copy all class attributes, e.g.:
    // bar = obj.bar;
  }

  /**
   * clones this class
   */
  @Override
  public SpeciesTypeRestrictionReference clone() {
    return new SpeciesTypeRestrictionReference(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(MultiConstants.namespaceURI);
  }

  /**
   * 
   */
  public static final int MIN_SBML_LEVEL = 3;
  /**
   * 
   */
  public static final int MIN_SBML_VERSION = 1;
}
