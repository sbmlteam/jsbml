/*
 * $Id: ListOfObjectives.java 2170 2015-04-01 10:04:01Z niko-rodrigue $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/extensions/fbc/src/org/sbml/jsbml/ext/fbc/ListOfObjectives.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
package org.sbml.jsbml.ext.fbc;

import java.util.Map;

import org.sbml.jsbml.ListOf;

/**
 * Unlike other SBML {@link ListOf} classes, {@link ListOfObjectives} adds an additional
 * required attribute activeObjective.
 * @author Nicolas Rodriguez
 * @since 1.0
 * @version $Rev: 2170 $
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
    initDefaults();
  }

  /**
   * @param level
   * @param version
   */
  public ListOfObjectives(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * @param listOf
   */
  public ListOfObjectives(ListOfObjectives listOf) {
    this((ListOf<Objective>) listOf);

    // copy attribute
    if (listOf.isSetActiveObjective()) {
      setActiveObjective(listOf.getActiveObjective());
    }
  }

  /**
   * 
   * @param listOf
   */
  public ListOfObjectives(ListOf<Objective> listOf) {
    super(listOf);
  }

  /**
   * 
   */
  private void initDefaults() {
    setNamespace(FBCConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
    setPackageVersion(-1);
    packageName = FBCConstants.shortLabel;
    setSBaseListType(ListOf.Type.other);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ListOf#clone()
   */
  @Override
  public ListOf<Objective> clone() {
    return new ListOfObjectives(this);
  }

  /**
   * Returns the value of activeObjective
   *
   * @return the value of activeObjective
   */
  public String getActiveObjective() {
    return isSetActiveObjective() ? activeObjective : "";
  }

  @Override
  public String getElementName() {
    return FBCConstants.listOfObjectives;
  }

  /**
   * Returns whether activeObjective is set
   *
   * @return whether activeObjective is set
   */
  public boolean isSetActiveObjective() {
    return activeObjective != null;
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
   * This attribute refers to an id of an existing {@link Objective}. This attribute
   * exists so that multiple objectives are defined in a single model, the model will
   * always be well-defined, i.e. there is a single objective function which defines
   * a single optimum.
   * 
   * Sets the value of activeObjective
   * @param activeObjective
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
      String oldActiveObjective = activeObjective;
      activeObjective = null;
      firePropertyChange(FBCConstants.activeObjective, oldActiveObjective, activeObjective);
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
