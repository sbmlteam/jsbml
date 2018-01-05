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

import java.util.Locale;
import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.StringTools;

/**
 * The {@link FluxObjective} class is a relatively simple container for a model
 * variable weighted by a signed linear coefficient.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class FluxObjective extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * 
   */
  private static final long serialVersionUID = 246449689493121713L;

  /**
   * 
   */
  private double coefficient;
  /**
   * 
   */
  private boolean isSetCoefficient = false;
  /**
   * 
   */
  private String reaction;

  /**
   * Creates an FluxObjective instance
   */
  public FluxObjective() {
    super();
    initDefaults();
  }

  /**
   * Creates a FluxObjective instance with a level and version.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public FluxObjective(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Creates a FluxObjective instance with an id.
   * 
   * @param id the id
   */
  public FluxObjective(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a FluxObjective instance with an id, level, and version.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   */
  public FluxObjective(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a FluxObjective instance with an id, name, level, and version.
   * 
   * @param id the id
   * @param name the name
   * @param level the SBML level
   * @param version the SBML version
   */
  public FluxObjective(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(
      Integer.valueOf(FBCConstants.MIN_SBML_LEVEL),
      Integer.valueOf(FBCConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  /**
   * Clone constructor
   * 
   * @param obj the instance to clone
   */
  public FluxObjective(FluxObjective obj) {
    super(obj);

    if (obj.isSetReaction()) {
      setReaction(obj.getReaction());
    }
    if (obj.isSetCoefficient()) {
      setCoefficient(obj.getCoefficient());
    }
  }

  /**
   * clones this class
   */
  @Override
  public FluxObjective clone() {
    return new FluxObjective(this);
  }



  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 2203;
    int result = super.hashCode();
    long temp;
    temp = Double.doubleToLongBits(coefficient);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((reaction == null) ? 0 : reaction.hashCode());
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
    FluxObjective other = (FluxObjective) obj;
    if (Double.doubleToLongBits(coefficient) != Double.doubleToLongBits(other.coefficient)) {
      return false;
    }
    if (reaction == null) {
      if (other.reaction != null) {
        return false;
      }
    } else if (!reaction.equals(other.reaction)) {
      return false;
    }
    return true;
  }

  /**
   * Returns the value of coefficient
   *
   * @return the value of coefficient
   */
  public double getCoefficient() {
    if (isSetCoefficient()) {
      return coefficient;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(FBCConstants.coefficient, this);
  }

  /**
   * Returns the value of reaction
   *
   * @return the value of reaction
   */
  public String getReaction() {
    return isSetReaction() ? reaction : "";
  }

  /**
   * Returns the {@link Reaction} associated with this {@link FluxObjective}.
   * 
   * @return the {@link Reaction} associated with this {@link FluxObjective} or null if the reaction attribute is not a valid reaction id.
   */
  public Reaction getReactionInstance() {
    if (isSetReaction()) {
      Model m = getModel();
      return (m != null) ? m.getReaction(getReaction()) : null;
    }
    return null;
  }

  /**
   * Returns whether reaction is set and is a valid {@link Reaction} in the model.
   * 
   * @return whether reaction is set and is a valid {@link Reaction} in the model.
   */
  public boolean isSetReactionInstance() {
    return getReactionInstance() != null;
  }

  /**
   * Returns whether reaction is set
   *
   * @return whether reaction is set
   */
  public boolean isSetReaction() {
    return reaction != null;
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = FBCConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return true;
  }

  /**
   * Returns whether coefficient is set
   *
   * @return whether coefficient is set
   */
  public boolean isSetCoefficient() {
    return isSetCoefficient;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals("reaction")) {
        setReaction(value);
      } else if (attributeName.equals("coefficient")) {
        setCoefficient(StringTools.parseSBMLDouble(value));
      } else {
        isAttributeRead = false;
      }

    }

    return isAttributeRead;
  }

  /**
   * The coefficient attribute takes the value of the coefficient that the FluxObjective
   * takes in the context of the enclosing {@link Objective} class.
   * 
   * @param coefficient the coefficient to set
   */
  public void setCoefficient(double coefficient) {
    double oldCoefficient = this.coefficient;
    this.coefficient = coefficient;
    isSetCoefficient = true;
    firePropertyChange(FBCConstants.coefficient, oldCoefficient, this.coefficient);
  }

  /**
   * Sets the {@link Reaction} that this {@link FluxObjective} is associated with in the context
   * of the enclosing {@link Objective} class.
   * 
   * @param reaction the reaction to set
   */
  public void setReaction(Reaction reaction) {
    setReaction(reaction.getId());
  }

  /**
   * Sets the reaction that this {@link FluxObjective} is associated with in the context
   * of the enclosing {@link Objective} class.
   * 
   * @param reaction the reaction to set
   */
  public void setReaction(String reaction) {
    String oldReaction = this.reaction;
    this.reaction = reaction;
    firePropertyChange(FBCConstants.reaction, oldReaction, reaction);
  }

  /**
   * Unsets the variable coefficient
   *
   * @return {@code true}, if coefficient was set before,
   *         otherwise {@code false}
   */
  public boolean unsetCoefficient() {
    if (isSetCoefficient()) {
      double oldCoefficient = coefficient;
      coefficient = Double.NaN;
      isSetCoefficient = false;
      firePropertyChange(FBCConstants.coefficient, oldCoefficient, coefficient);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable reaction
   *
   * @return {@code true}, if reaction was set before,
   *         otherwise {@code false}
   */
  public boolean unsetReaction() {
    if (isSetReaction()) {
      String oldReaction = reaction;
      reaction = null;
      firePropertyChange(FBCConstants.reaction, oldReaction, reaction);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetReaction()) {
      attributes.put(FBCConstants.shortLabel+ ":reaction", getReaction());
    }
    if (isSetCoefficient()) {
      attributes.put(FBCConstants.shortLabel+ ":" + FBCConstants.coefficient,
        StringTools.toString(Locale.ENGLISH, getCoefficient()));
    }
    if (isSetId()) {
      attributes.remove("id");
      attributes.put(FBCConstants.shortLabel + ":id", getId());
    }
    if (isSetName())
    {
      attributes.remove("name");
      attributes.put(FBCConstants.shortLabel + ":name", getName());
    }

    return attributes;
  }

}
