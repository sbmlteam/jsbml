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
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.fbc;

import java.util.Map;

import org.sbml.jsbml.ListOf;

/**
 * @author Nicolas Rodriguez
 * @since 1.0
 * @version $Rev$
 */
public class ListOfObjectives extends ListOf<Objective> {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 8684683156123793632L;

  /**
   * 
   */
  private String activeObjective; 

  /**
   * 
   */
  public ListOfObjectives() {
    super();
  }

  /**
   * @param level
   * @param version
   */
  public ListOfObjectives(int level, int version) {
    super(level, version);
  }

  /**
   * @param listOf
   */
  public ListOfObjectives(ListOfObjectives listOf) {
    super(listOf);

    // copy attribute
    if (listOf.isSetActiveObjective()) {
      setActiveObjective(listOf.getActiveObjective());
    }
  }

  /**
   * Returns the value of activeObjective
   *
   * @return the value of activeObjective
   */
  public String getActiveObjective() {
    if (isSetActiveObjective()) {
      return activeObjective;
    }

    return null;
  }

  /**
   * Returns whether activeObjective is set 
   *
   * @return whether activeObjective is set 
   */
  public boolean isSetActiveObjective() {
    return this.activeObjective != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ListOf#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) 
    {
      isAttributeRead = true;

      if (attributeName.equals(FBCConstants.activeObjective)) {
        setActiveObjective(value);
      }
      else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }

  /**
   * Sets the value of activeObjective
   */
  public void setActiveObjective(String activeObjective) {
    String oldActiveObjective = this.activeObjective;
    this.activeObjective = activeObjective;
    firePropertyChange(FBCConstants.activeObjective, oldActiveObjective, this.activeObjective);
  }


  /**
   * Unsets the variable activeObjective 
   *
   * @return {@code true}, if activeObjective was set before, 
   *         otherwise {@code false}
   */
  public boolean unsetActiveObjective() {
    if (isSetActiveObjective()) {
      String oldActiveObjective = this.activeObjective;
      this.activeObjective = null;
      firePropertyChange(FBCConstants.activeObjective, oldActiveObjective, this.activeObjective);
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

    if (isSetActiveObjective()) {
      attributes.put(FBCConstants.shortLabel + ':' +
        FBCConstants.activeObjective, getActiveObjective());
    }

    return attributes;
  }

}
