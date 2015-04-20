/*
 * $Id: QualitativeSpecies.java 2180 2015-04-08 15:48:28Z niko-rodrigue $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/extensions/qual/src/org/sbml/jsbml/ext/qual/QualitativeSpecies.java $
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
package org.sbml.jsbml.ext.qual;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.StringTools;

/**
 * Similarly to the {@link Species} in SBML, the components of qualitative models refer to pools
 * of entities that are considered indistinguishable and are each located in a specific
 * {@link Compartment}. However, here components are characterised by their qualitative influences
 * rather than by taking part in reactions. Therefore, we define the {@link QualitativeSpecies}
 * element to represent such pools of entities.
 * 
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Clemens Wrzodek
 * @version $Rev: 2180 $
 * @since 1.0
 * @date $Date: 2015-04-08 11:48:28 -0400 (Wed, 08 Apr 2015) $
 */
public class QualitativeSpecies extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long     serialVersionUID = -6048861420699176889L;
  /**
   * 
   */
  private String compartment;
  /**
   * 
   */
  private Boolean constant;             // TODO: extends/implements the jsbml interface
  // that has the constant attribute.
  /**
   * 
   */
  private Integer initialLevel;
  /**
   * 
   */
  private Integer maxLevel;

  /**
   * 
   */
  public QualitativeSpecies() {
    super();
    initDefaults();
  }

  /**
   * 
   * @param id
   */
  public QualitativeSpecies(String id) {
    super(id);
    initDefaults();
  }

  /**
   * 
   * @param level
   * @param version
   */
  public QualitativeSpecies(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public QualitativeSpecies(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public QualitativeSpecies(String id, String name, int level, int version) {
    super(id, name, level, version);

    if (getLevelAndVersion().compareTo(Integer.valueOf(3), Integer.valueOf(1)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  /**
   * Copy constructor that clones all values from the input
   * {@code species} that are also available in
   * {@link QualitativeSpecies}.
   * <p>You should consider setting a new id and meta_id afterwards
   * to avoid duplicate identifiers.
   * @param species
   */
  public QualitativeSpecies(Species species) {
    super(species);
    initDefaults();

    if (species.isSetCompartment()) {
      compartment = species.getCompartment();
    }
    if (species.isSetConstant()) {
      constant = species.getConstant();
    }

    /* initialLevel, maxLevel and listOfSymbolicValues
     * are only for qual species.
     */
  }

  /**
   * Copy constructor that clones all variables of
   * {@code qualSpecies}.
   * @param qualSpecies
   */
  public QualitativeSpecies(QualitativeSpecies qualSpecies) {
    super(qualSpecies);

    if (qualSpecies.isSetCompartment()) {
      setCompartment(qualSpecies.getCompartment());
    }
    if (qualSpecies.isSetConstant()) {
      setConstant(qualSpecies.getConstant());
    }
    if (qualSpecies.isSetInitialLevel()) {
      setInitialLevel(qualSpecies.getInitialLevel());
    }
    if (qualSpecies.isSetMaxLevel()) {
      setMaxLevel(qualSpecies.getMaxLevel());
    }
  }

  /**
   * 
   */
  public void initDefaults() {
    setNamespace(QualConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
    setPackageVersion(-1);
    packageName = QualConstants.shortLabel;
    compartment = null;
    constant = null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public QualitativeSpecies clone() {
    return new QualitativeSpecies(this);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return true;
  }

  /**
   * 
   * @return true
   */
  public boolean isCompartmentMandatory() {
    return true;
  }

  /**
   * @return if compartment attribute is set
   */
  public boolean isSetCompartment() {
    return compartment != null;
  }

  /**
   * @return the compartment
   */
  public String getCompartment() {
    return isSetCompartment() ? compartment : "";
  }

  /**
   * Sets the compartment attribute.
   * 
   * <p>The required attribute compartment is used to identify the {@link Compartment} in which
   * the {@link QualitativeSpecies} is located. The attribute's value must be the identifier
   * of an existing {@link Compartment} object in the model. This attribute is comparable
   * with the compartment attribute on the {@link Species} element.
   * 
   * @param compartment
   *        the compartment to set
   */
  public void setCompartment(String compartment) {
    String oldCompartment = this.compartment;
    if ((compartment == null) || (compartment.length() == 0)) {
      this.compartment = null;
    } else {
      this.compartment = compartment;
    }
    firePropertyChange(QualConstants.compartment, oldCompartment,
      this.compartment);
  }

  /**
   * 
   * @param compartment
   */
  public void setCompartment(Compartment compartment) {
    if (compartment != null) {
      setCompartment(compartment.getId());
    } else {
      unsetCompartment();
    }
  }


  /**
   * @return {@code true} if the unset of the compartment attribute was successful
   */
  public boolean unsetCompartment() {
    if (isSetCompartment()) {
      compartment = null;
      return true;
    }
    return false;
  }


  /**
   * @return true
   */
  public boolean isBoundaryConditionMandatory() {
    return false;
  }

  /**
   * @return true
   */
  public boolean isSetConstantMandatory() {
    return true;
  }

  /**
   * 
   * @return
   */
  public boolean isSetConstant() {
    return constant != null;
  }


  /**
   * @return the constant
   */
  public boolean getConstant() {
    if (isSetConstant()) {
      return constant.booleanValue();
    }
    throw new PropertyUndefinedError(QualConstants.constant, this);
  }


  /**
   * Sets the constant attribute.
   * 
   * <p>The required attribute constant, of type boolean, is used to indicate that the level of the
   * {@link QualitativeSpecies} is fixed or can be varied. This attribute is comparable with
   * the constant attribute on the {@link Species} element.
   * 
   * Typically, in a regulatory or influence graph a {@link QualitativeSpecies} may receive no
   * interaction and if so, would appear only as an {@link Input} in the {@link Model} and have
   * the value of the constant attribute set to "true". In other influence graphs or in Petri net
   * models a {@link QualitativeSpecies} may occur as an {@link Input} whose level is changed by
   * the Transition and would have constant set to "false".  The nature of changes to a
   * {@link QualitativeSpecies} resulting from a {@link Transition} is also recorded using the
   * transitionEffect attribute on the {@link Input} and may be set to "none" to indicate there
   * is no change. This duplication of information provides a means of validating the modeller's
   * intent and also allows entities on the borders of a system to be easily identified.
   * 
   * @param constant
   *        the constant to set
   */
  public void setConstant(boolean constant) {
    Boolean oldConstant = this.constant;
    this.constant = constant;
    firePropertyChange(QualConstants.constant, oldConstant, this.constant);
  }


  /**
   * @return {@code true} if the unset of the constant attribute was successful
   */
  public boolean unsetConstant() {
    if (isSetConstant()) {
      boolean oldConstant = constant;
      constant = null;
      firePropertyChange(QualConstants.constant, oldConstant, constant);
      return true;
    } else {
      return false;
    }
  }


  /**
   * @return false
   */
  public boolean isInitialLevelMandatory() {
    return false;
  }


  /**
   * 
   * @return
   */
  public boolean isSetInitialLevel() {
    return initialLevel != null;
  }


  /**
   * @return the initialLevel
   */
  public int getInitialLevel() {
    if (isSetInitialLevel()) {
      return initialLevel.intValue();
    }
    throw new PropertyUndefinedError(QualConstants.initialLevel, this);
  }


  /**
   * Sets the initialLevel attribute.
   * 
   * <p>The initialLevel is a non-negative integer that defines the initial level of the
   * {@link QualitativeSpecies} in its {@link Compartment}. This attribute is optional
   * but cannot exceed the value of the maxLevel attribute, if both are set.
   * 
   * @param initialLevel
   *        the initialLevel to set
   */
  public void setInitialLevel(int initialLevel) {
    Integer oldInitialLevel = this.initialLevel;
    this.initialLevel = initialLevel;
    firePropertyChange(QualConstants.initialLevel, oldInitialLevel,
      this.initialLevel);

    //TODO: Should there be a test to see if this value is greater than the maxLevel attribute, if it's set?
  }


  /**
   * @return {@code true} if unset initialLevel attribute was successful
   */
  public boolean unsetInitialLevel() {
    if (isSetInitialLevel()) {
      Integer oldInitialLevel = initialLevel;
      initialLevel = null;
      firePropertyChange(QualConstants.initialLevel, oldInitialLevel,
        initialLevel);
      return true;
    } else {
      return false;
    }
  }


  /**
   * @return false
   */
  public boolean isMaxLevelMandatory() {
    return false;
  }


  /**
   * 
   * @return
   */
  public boolean isSetMaxLevel() {
    return maxLevel != null;
  }


  /**
   * @return the maxLevel
   */
  public int getMaxLevel() {
    if (isSetMaxLevel()) {
      return maxLevel.intValue();
    }
    throw new PropertyUndefinedError(QualConstants.maxLevel, this);
  }


  /**
   * Sets the maxLevel attribute.
   * 
   * <p>The maxLevel is a non-negative integer that sets the maximal level of the {@link QualitativeSpecies}.
   * This attribute is optional but when set, the level of the {@link QualitativeSpecies} must not exceed
   * this value at any point in a simulation.
   * 
   * In logical models, the maxLevel must be coherent with the resultLevel values in the function terms
   * defined for the corresponding transition, i.e. the {@link Model} must not contain a {@link FunctionTerm}
   * that attempts to set a level that exceeds this value.
   * 
   * In Petri nets, this attribute is meant to define place capacities. Hence, a {@link Transition} is not
   * enabled if the value resulting from its firing would exceed the maxLevel of one of its output places.
   * The attribute is not required and even if explicitly stated, the restriction imposed by place capacities
   * in a Petri net model must be encapsulated within the math element of the {@link FunctionTerm} elements.
   * 
   * This attribute can also be used to indicate the range of possible levels for a
   * {@link QualitativeSpecies} whose constant attribute is true. This may seem a little contradictory,
   * since if the constant attribute is true then the level associated with the {@link QualitativeSpecies}
   * cannot vary. However, it provides additional information regarding the possible levels particularly
   * in the case where no initialLevel has been set.
   * 
   * @param maxLevel
   *        the maxLevel to set
   */
  public void setMaxLevel(int maxLevel) {
    Integer oldMaxLevel = this.maxLevel;
    this.maxLevel = maxLevel;
    firePropertyChange(QualConstants.maxLevel, oldMaxLevel, this.maxLevel);
  }


  /**
   * @return {@code true} if unset maxLevel attribute was successful
   */
  public boolean unsetMaxLevel() {
    if (isSetMaxLevel()) {
      Integer oldMaxLevel = maxLevel;
      maxLevel = null;
      firePropertyChange(QualConstants.maxLevel, oldMaxLevel, maxLevel);
      return true;
    } else {
      return false;
    }
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.sbml.jsbml.element.MathContainer#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      QualitativeSpecies qs = (QualitativeSpecies) object;
      equals &= qs.isSetConstant() == isSetConstant();
      if (equals && isSetConstant()) {
        equals &= (qs.getConstant()==getConstant());
      }
      equals &= qs.isSetCompartment() == isSetCompartment();
      if (equals && isSetCompartment()) {
        equals &= qs.getCompartment().equals(getCompartment());
      }
      equals &= qs.isSetInitialLevel() == isSetInitialLevel();
      if (equals && isSetInitialLevel()) {
        equals &= qs.getInitialLevel()==getInitialLevel();
      }
      equals &= qs.isSetMaxLevel() == isSetMaxLevel();
      if (equals && isSetMaxLevel()) {
        equals &= qs.getMaxLevel() == getMaxLevel();
      }
    }
    return equals;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 971;
    int hashCode = super.hashCode();
    if (isSetConstant()) {
      hashCode += prime + (getConstant() ? 1 : -1);
    }
    if (isSetCompartment()) {
      hashCode += prime * getCompartment().hashCode();
    }
    if (isSetInitialLevel()) {
      hashCode += prime * getInitialLevel();
    }
    if (isSetMaxLevel()) {
      hashCode += prime * getMaxLevel();
    }

    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {

    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(QualConstants.constant)) {
        setConstant(StringTools.parseSBMLBoolean(value));
      } else if (attributeName.equals(QualConstants.compartment)) {
        setCompartment(value);
      } else if (attributeName.equals(QualConstants.initialLevel)) {
        setInitialLevel(StringTools.parseSBMLInt(value));
      } else if (attributeName.equals(QualConstants.maxLevel)) {
        setMaxLevel(StringTools.parseSBMLInt(value));
      } else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(QualConstants.shortLabel+ ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(QualConstants.shortLabel+ ":name", getName());
    }
    if (isSetConstant()) {
      attributes.put(QualConstants.shortLabel+ ':' + QualConstants.constant, Boolean.toString(getConstant()));
    }
    if (isSetCompartment()) {
      attributes.put(QualConstants.shortLabel+ ':' + QualConstants.compartment, getCompartment());
    }
    if (isSetInitialLevel()) {
      attributes.put(QualConstants.shortLabel+ ':' + QualConstants.initialLevel, Integer.toString(getInitialLevel()));
    }
    if (isSetMaxLevel()) {
      attributes.put(QualConstants.shortLabel+ ':' + QualConstants.maxLevel, Integer.toString(getMaxLevel()));
    }

    return attributes;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "QualitativeSpecies [compartment = " + compartment + ", constant = "
      + constant + ", initialLevel = " + initialLevel + ", maxLevel = " + maxLevel
      + ", id = " + getId() + ", name = " + getName() + "]";
  }
  
  

}
