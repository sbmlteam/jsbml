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

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.ext.multi.util.ListOfSpeciesFeatureContent;

/**
 * Allows to regroup a set of {@link SpeciesFeature} that have a specific {@link Relation} between them.
 * 
 * <p>This class derives from {@link ListOf}<SpeciesFeature>.
 * 
 * @author rodrigue
 */
public class SubListOfSpeciesFeature extends ListOf<SpeciesFeature> implements UniqueNamedSBase, ListOfSpeciesFeatureContent {

  /**
   * 
   */
  private Relation relation;

  /**
   * 
   */
  private String component;
  
  
  /**
   * Creates an SubListOfSpeciesFeature instance 
   */
  public SubListOfSpeciesFeature() {
    super();
    initDefaults();
  }

  /**
   * Creates a SubListOfSpeciesFeature instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public SubListOfSpeciesFeature(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a SubListOfSpeciesFeature instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public SubListOfSpeciesFeature(int level, int version) {
    this(null, level, version);
  }

  /**
   * Creates a SubListOfSpeciesFeature instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public SubListOfSpeciesFeature(String id, int level, int version) {
    super(id, null, level, version);
    initDefaults();
  }

  /**
   * Clone constructor
   */
  public SubListOfSpeciesFeature(SubListOfSpeciesFeature obj) {
    super(obj);

    if (obj.isSetRelation()) {
      setRelation(obj.getRelation());
    }
    if (obj.isSetComponent()) {
      setComponent(obj.getComponent());
    }
  }

  /**
   * clones this class
   */
  public SubListOfSpeciesFeature clone() {
    return new SubListOfSpeciesFeature(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    packageName = MultiConstants.shortLabel;
    setPackageVersion(-1);
    setOtherListName(MultiConstants.subListOfSpeciesFeatures);
    setSBaseListType(Type.other);
  }

  
  /**
   * Returns the value of {@link #relation}.
   *
   * @return the value of {@link #relation}.
   */
  public Relation getRelation() {
    if (isSetRelation()) {
      return relation;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(MultiConstants.relation, this);
  }

  /**
   * Returns whether {@link #relation} is set.
   *
   * @return whether {@link #relation} is set.
   */
  public boolean isSetRelation() {
    return relation != null;
  }

  /**
   * Sets the value of relation
   *
   * @param relation the value of relation to be set.
   */
  public void setRelation(Relation relation) {
    Relation oldRelation = this.relation;
    this.relation = relation;
    firePropertyChange(MultiConstants.relation, oldRelation, this.relation);
  }

  /**
   * Unsets the variable relation.
   *
   * @return {@code true} if relation was set before, otherwise {@code false}.
   */
  public boolean unsetRelation() {
    if (isSetRelation()) {
      Relation oldRelation = this.relation;
      this.relation = null;
      firePropertyChange(MultiConstants.relation, oldRelation, this.relation);
      return true;
    }
    return false;
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
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(MultiConstants.component, this);
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
      String oldComponent = this.component;
      this.component = null;
      firePropertyChange(MultiConstants.component, oldComponent, this.component);
      return true;
    }
    return false;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 6043;
    int result = super.hashCode();
    result = prime * result + ((component == null) ? 0 : component.hashCode());
    result = prime * result + ((relation == null) ? 0 : relation.hashCode());
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
    SubListOfSpeciesFeature other = (SubListOfSpeciesFeature) obj;
    if (component == null) {
      if (other.component != null) {
        return false;
      }
    } else if (!component.equals(other.component)) {
      return false;
    }
    if (relation != other.relation) {
      return false;
    }
    return true;
  }

  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) { // TODO - do that only for the first version of the package!
      attributes.remove("id");
      attributes.put(MultiConstants.shortLabel + ":id", getId());
    }

    if (isSetRelation()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.relation, getRelation().toString());
    }
    if (isSetComponent()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.component, getComponent());
    }
    
    return attributes;
  }

  @Override
  public boolean readAttribute(String attributeName, String prefix,
      String value) {

    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(MultiConstants.relation)) 
      {
        try {
          setRelation(Relation.valueOf(value));
        } catch (Exception e) {
          throw new SBMLException("Could not recognized the value '" + value
            + "' for the attribute " + MultiConstants.relation
            + " on the '" + MultiConstants.subListOfSpeciesFeatures + "' element.");
        }
      }
      else if (attributeName.equals(MultiConstants.component))
      {
        setComponent(value);
      }
      else 
      {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }
}
