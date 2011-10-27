/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2011 jointly by the following organizations:
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
package org.sbml.jsbml.ext.qual;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.PropertyUndefinedError;
/**
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @version $$Rev$$
 * @since 0.8
 * @date 29.09.2011
 */
public class Output extends AbstractNamedSBase {

  /**
   * 
   */
  private static final long      serialVersionUID = -6392002023918667156L;
  private String                 qualitativeSpecies;
  private OutputTransitionEffect transitionEffect;
  private Integer                level;


  @Override
  public AbstractSBase clone() {
    return null;
  }


  public boolean isIdMandatory() {
    return false;
  }


  /**
   * @return true
   */
  public boolean isQualitativeSpeciesMandatory() {
    return true;
  }


  public boolean isSetQualitativeSpecies() {
    return this.qualitativeSpecies != null;
  }


  /**
   * @return the qualitativeSpecies
   */
  public String getQualitativeSpecies() {
    if (isSetQualitativeSpecies()) {
      return qualitativeSpecies;
    } else {
      throw new PropertyUndefinedError(QualChangeEvent.qualitativeSpecies, this);
    }
  }


  /**
   * @param qualitativeSpecies
   *        the qualitativeSpecies to set
   */
  public void setQualitativeSpecies(String qualitativeSpecies) {
    String oldQualitativeSpecies = this.qualitativeSpecies;
    this.qualitativeSpecies = qualitativeSpecies;
    firePropertyChange(QualChangeEvent.qualitativeSpecies,
      oldQualitativeSpecies, this.qualitativeSpecies);
  }


  public boolean unsetQualitativeSpecies() {
    if (isSetQualitativeSpecies()) {
      setQualitativeSpecies(null);
      return true;
    } else {
      return false;
    }
  }


  /**
   * @return false
   */
  public boolean isTransitionEffectMandatory() {
    return true;
  }


  public boolean isSetTransitionEffect() {
    return this.transitionEffect != null;
  }


  /**
   * @return the transitionEffect
   */
  public OutputTransitionEffect getTransitionEffect() {
    if (isSetTransitionEffect()) {
      return transitionEffect;
    } else {
      throw new PropertyUndefinedError(QualChangeEvent.transitionEffect, this);
    }
  }


  /**
   * @param transitionEffect
   *        the transitionEffect to set
   */
  public void setTransitionEffect(OutputTransitionEffect transitionEffect) {
    OutputTransitionEffect oldTransitionEffect = this.transitionEffect;
    this.transitionEffect = transitionEffect;
    firePropertyChange(QualChangeEvent.transitionEffect, oldTransitionEffect,
      this.transitionEffect);
  }


  public boolean unsetTransitionEffect() {
    if (isSetTransitionEffect()) {
      setTransitionEffect(null);
      return true;
    } else {
      return false;
    }
  }


  /**
   * @return false
   */
  public boolean isLevelMandatory() {
    return false;
  }


  public boolean isSetLevel() {
    return level != null;
  }


  /**
   * @return the level
   */
  public int getLevel() {
    if (isSetLevel()) {
      return level.intValue();
    } else {
      throw new PropertyUndefinedError(QualChangeEvent.level, this);
    }
  }


  /**
   * @param level
   *        the level to set
   */
  public void setLevel(int level) {
    Integer oldLevel = this.level;
    this.level = level;
    firePropertyChange(QualChangeEvent.level, oldLevel, this.level);
  }


  public boolean unsetLevel() {
    if (isSetLevel()) {
      Integer oldLevel = this.level;
      this.level = null;
      firePropertyChange(QualChangeEvent.level, oldLevel, this.level);
      return true;
    } else {
      return false;
    }
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.element.MathContainer#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      Output o = (Output) object;
      equals &= o.isSetQualitativeSpecies() == isSetQualitativeSpecies();
      if (equals && isSetQualitativeSpecies()) {
        equals &= (o.getQualitativeSpecies().equals(getQualitativeSpecies()));
      }
    }
    return equals;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 991;
    int hashCode = super.hashCode();
    if (isSetQualitativeSpecies()) {
      hashCode += prime * getQualitativeSpecies().hashCode();
    }
    return hashCode;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.MathContainer#toString()
   */
  @Override
  public String toString() {
    String parentId = "";
    if (getParent() != null) {
      // Can happen in the clone constructor when using the
      // SimpleSBaseChangeListener
      // The super constructor is called before parent is initialized and
      // it is using the toString() method
      parentId = getParent().getMetaId();
    }
    return String.format("%s(%s)", getElementName(), parentId);
  }
}
