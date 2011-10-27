/*
 * $Id$
 * $URL:
 * https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/extensions/qual/src
 * /org/sbml/jsbml/ext/qual/Input.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2011 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
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
 * @author Florian Mittag
 * @version $$Rev$$
 * @since 0.8
 * @date 29.09.2011
 */
public class Input extends AbstractNamedSBase {

  /**
   * 
   */
  private static final long     serialVersionUID = -3370025650545068132L;
  private String                qualitativeSpecies;
  private InputTransitionEffect transitionEffect;
  private Integer               thresholdLevel;
  private String                thresholdSymbol;


  @Override
  public AbstractSBase clone() {
    return null;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  public boolean isIdMandatory() {
    return false;
  }


  /**
   * @return true
   */
  public boolean isQualitativeSpeciesMandatory() {
    return true;
  }


  /**
   * @return if the qualitative species is set
   */
  public boolean isSetQualitativeSpecies() {
    return qualitativeSpecies != null;
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


  /**
   * @return if the qualitative species was unset
   */
  public boolean unsetQualitativeSpecies() {
    if (qualitativeSpecies != null) {
      setQualitativeSpecies(null);
      return true;
    }
    return false;
  }


  public boolean isTransitionEffectMandatory() {
    return false;
  }


  /**
   * @return if the transitionEffect is set
   */
  public boolean isSetTransitionEffect() {
    return transitionEffect != null;
  }


  /**
   * @return the transitionEffect
   */
  public InputTransitionEffect getTransitionEffect() {
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
  public void setTransitionEffect(InputTransitionEffect transitionEffect) {
    InputTransitionEffect oldTransitionEffect = this.transitionEffect;
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
  public boolean isThresholdLevelMandatory() {
    return false;
  }


  public boolean isSetThresholdLevel() {
    return this.thresholdLevel != null;
  }


  /**
   * @return the thresholdLevel
   */
  public int getThresholdLevel() {
    if (isSetThresholdLevel()) {
      return thresholdLevel.intValue();
    }
    throw new PropertyUndefinedError(QualChangeEvent.thresholdLevel, this);
  }


  /**
   * @param thresholdLevel
   *        the thresholdLevel to set
   */
  public void setThresholdLevel(int thresholdLevel) {
    Integer oldThresholdLevel = this.thresholdLevel;
    this.thresholdLevel = thresholdLevel;
    firePropertyChange(QualChangeEvent.thresholdLevel, oldThresholdLevel,
      this.thresholdLevel);
  }


  /**
   * @return true if unset the threholdLevel attribute was successful
   */
  public boolean unsetThresholdLevel() {
    if (isSetThresholdLevel()) {
      Integer oldThresholdLevel = this.thresholdLevel;
      this.thresholdLevel = null;
      firePropertyChange(QualChangeEvent.thresholdLevel, oldThresholdLevel,
        this.thresholdLevel);
      return true;
    } else {
      return false;
    }
  }

  
  
  /**
   * @return false
   */
  public boolean isThresholdSymbolMandatory() {
    return false;
  }

  /**
   * @return if thresholdSymbol attribute is set
   */
  public boolean isSetThresholdSymbol() {
    return thresholdSymbol != null;
  }

  /**
   * @return the thresholdSymbol
   */
  public String getThresholdSymbol() {
    return isSetThresholdSymbol() ? thresholdSymbol : "";
  }


  /**
   * @param thresholdSymbol
   *        the thresholdSymbol to set
   */
  public void setThresholdSymbol(String thresholdSymbol) {
    String oldSymbol = this.thresholdSymbol;
    this.thresholdSymbol = thresholdSymbol;
    firePropertyChange(QualChangeEvent.thresholdSymbol, oldSymbol, this.thresholdSymbol);
  }

  /**
   * @return true if the unset of the thresholdSymbol attribute was successful
   */
  public boolean unsetThresholdSymbol() {
    if (isSetThresholdSymbol()) {
      setThresholdSymbol(null);
      return true;
    }
    return false;
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
    final int prime = 491;
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
