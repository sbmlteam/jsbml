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
package org.sbml.jsbml.ext.fbc;

import java.util.Map;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * {@link ListOfObjectives} adds an additional required attribute: 'activeObjective'.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
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
   * Creates a new {@link ListOfObjectives} instance.
   */
  public ListOfObjectives() {
    super();
    initDefaults();
  }

  /**
   * Creates a new {@link ListOfObjectives} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public ListOfObjectives(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Creates a new {@link ListOfObjectives} instance.
   * 
   * @param listOf the instance to clone
   */
  public ListOfObjectives(ListOfObjectives listOf) {
    this((ListOf<Objective>) listOf);

    // copy attribute
    if (listOf.isSetActiveObjective()) {
      setActiveObjective(listOf.getActiveObjective());
    }
  }

  /**
   * Creates a new {@link ListOfObjectives} instance.
   * 
   * @param listOf the instance to clone
   */
  public ListOfObjectives(ListOf<Objective> listOf) {
    super(listOf);
  }

  /**
   * 
   */
  private void initDefaults() {
    setPackageVersion(-1);
    packageName = FBCConstants.shortLabel;
    setSBaseListType(ListOf.Type.other);
    setOtherListName(FBCConstants.listOfObjectives);
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

  /**
   * Returns the active {@link Objective}.
   * 
   * @return the active {@link Objective} or null if none can be found.
   */
  public Objective getActiveObjectiveInstance() {
    return firstHit(new NameFilter(getActiveObjective()));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ListOf#getElementName()
   */
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
   * Sets the active objective.
   * 
   * <p>This attribute refers to an id of an existing {@link Objective}. This attribute
   * exists so that if multiple objectives are defined in a single model, the model will
   * always be well-defined, i.e., there is a single objective function which defines
   * a single optimum.</p>
   * 
   * @param activeObjective the value of {@link #activeObjective}
   */
  public void setActiveObjective(String activeObjective) {
    String oldActiveObjective = this.activeObjective;
    this.activeObjective = activeObjective;
    firePropertyChange(FBCConstants.activeObjective, oldActiveObjective, this.activeObjective);
  }

  /**
   * Sets the value of {@link #activeObjective} to the id of the given {@link Objective}.
   * 
   * @param activeObjective an {@link Objective} that will be set as the active objective.
   * @see #setActiveObjective(String)
   */
  public void setActiveObjective(Objective activeObjective) {
    setActiveObjective(activeObjective.getId());
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
