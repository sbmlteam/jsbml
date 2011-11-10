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

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.parsers.QualParser;

/**
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Florian Mittag
 * @version $Rev$
 * @since 1.0
 * @date 29.09.2011
 */
public class Input extends AbstractNamedSBase implements UniqueNamedSBase{

  /**
   * Generated serial version identifier.
   */
  private static final long     serialVersionUID = -3370025650545068132L;
  private String                qualitativeSpecies;
  private InputTransitionEffect transitionEffect;
  private Integer               thresholdLevel;
  private String                thresholdSymbol;
  private Sign                  sign;


  public Input() {
    super();
    initDefaults();
  }


  public Input(String id, InputTransitionEffect consumption) {
    super(id);
    setTransitionEffect(consumption);
  }
   
  /**
   * 
   * @param level
   * @param version
   */
  public Input(int level, int version){
    super(level, version);
    if (getLevelAndVersion().compareTo(Integer.valueOf(3), Integer.valueOf(1)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }
  
  public void initDefaults() {
    addNamespace(QualParser.getNamespaceURI());
    qualitativeSpecies = null;
    transitionEffect = null;
    thresholdLevel = null;
    thresholdSymbol = null;
    sign = null;
  }


  @Override
  public AbstractSBase clone() {
    return null;
  }


  /**
   * @param sign
   *        the sign to set
   */
  public void setSign(Sign sign) {
    Sign oldSign = this.sign;
    this.sign = sign;
    firePropertyChange(QualConstant.sign, oldSign, this.sign);
  }


  public boolean isSetSign() {
    return sign != null;
  }


  /**
   * @return the sign
   */
  public Sign getSign() {
    if (isSetSign()) {
      return sign;
    } else {
      throw new PropertyUndefinedError(QualConstant.sign, this);
    }
  }


  /**
   * @return true if unset the sign attribute was successful
   */
  public boolean unsetSign() {
    if (isSetSign()) {
      setSign(null);
      return true;
    } else {
      return false;
    }
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
      throw new PropertyUndefinedError(QualConstant.qualitativeSpecies, this);
    }
  }


  /**
   * @param qualitativeSpecies
   *        the qualitativeSpecies to set
   */
  public void setQualitativeSpecies(String qualitativeSpecies) {
    String oldQualitativeSpecies = this.qualitativeSpecies;
    this.qualitativeSpecies = qualitativeSpecies;
    firePropertyChange(QualConstant.qualitativeSpecies,
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
      throw new PropertyUndefinedError(QualConstant.transitionEffect, this);
    }
  }


  /**
   * @param transitionEffect
   *        the transitionEffect to set
   */
  public void setTransitionEffect(InputTransitionEffect transitionEffect) {
    InputTransitionEffect oldTransitionEffect = this.transitionEffect;
    this.transitionEffect = transitionEffect;
    firePropertyChange(QualConstant.transitionEffect, oldTransitionEffect,
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
    throw new PropertyUndefinedError(QualConstant.thresholdLevel, this);
  }


  /**
   * @param thresholdLevel
   *        the thresholdLevel to set
   */
  public void setThresholdLevel(int thresholdLevel) {
    Integer oldThresholdLevel = this.thresholdLevel;
    this.thresholdLevel = thresholdLevel;
    firePropertyChange(QualConstant.thresholdLevel, oldThresholdLevel,
      this.thresholdLevel);
  }


  /**
   * @return true if unset the threholdLevel attribute was successful
   */
  public boolean unsetThresholdLevel() {
    if (isSetThresholdLevel()) {
      Integer oldThresholdLevel = this.thresholdLevel;
      this.thresholdLevel = null;
      firePropertyChange(QualConstant.thresholdLevel, oldThresholdLevel,
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
    firePropertyChange(QualConstant.thresholdSymbol, oldSymbol,
      this.thresholdSymbol);
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
      Input i = (Input) object;
      equals &= i.isSetQualitativeSpecies() == isSetQualitativeSpecies();
      if (equals && isSetQualitativeSpecies()) {
        equals &= (i.getQualitativeSpecies().equals(getQualitativeSpecies()));
      }
      equals &= i.isSetTransitionEffect() == isSetTransitionEffect();
      if (equals && isSetTransitionEffect()) {
        equals &= (i.getTransitionEffect().equals(getTransitionEffect()));
      }
      equals &= i.isSetThresholdLevel() == isSetThresholdLevel();
      if (equals && isSetThresholdLevel()) {
        equals &= i.getThresholdLevel() == getThresholdLevel();
      }
      equals &= i.isSetThresholdSymbol() == isSetThresholdSymbol();
      if (equals && isSetThresholdSymbol()) {
        equals &= i.getThresholdSymbol().equals(getThresholdSymbol());
      }
      equals &= i.isSetSign() == isSetSign();
      if (equals && isSetSign()) {
        equals &= (i.getSign() == getSign());
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
    if (isSetTransitionEffect()) {
      hashCode += prime * getTransitionEffect().hashCode();
    }
    if (isSetThresholdLevel()) {
      hashCode += prime * getThresholdLevel();
    }
    if (isSetThresholdSymbol()) {
      hashCode += prime * getThresholdSymbol().hashCode();
    }
    if (isSetSign()) {
      hashCode += prime * getSign().hashCode();
    }
    return hashCode;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(QualConstant.qualitativeSpecies)) {
        setQualitativeSpecies(value);
      } else if (attributeName.equals(QualConstant.thresholdLevel)) {
        setThresholdLevel(StringTools.parseSBMLInt(value));
      } else if (attributeName.equals(QualConstant.thresholdSymbol)) {
        setThresholdSymbol(value);
      } else if (attributeName.equals(QualConstant.sign)) {
        setSign(Sign.valueOf(attributeName));
      } else if (attributeName.equals(QualConstant.transitionEffect)) {
        try {
          setTransitionEffect(InputTransitionEffect.valueOf(value));
        } catch (Exception e) {
          throw new SBMLException("Could not recognized the value '" + value
            + "' for the attribute " + QualConstant.transitionEffect
            + " on the 'input' element.");
        }
      } else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetId()) {
      attributes.remove("id");
      attributes.put(QualParser.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(QualParser.shortLabel + ":name", getName());
    }
    if (isSetQualitativeSpecies()) {
      attributes.put(QualParser.shortLabel + ":"
        + QualConstant.qualitativeSpecies, getQualitativeSpecies());
    }
    if (isSetThresholdLevel()) {
      attributes.put(QualParser.shortLabel + ":"
        + QualConstant.thresholdLevel, Integer.toString(getThresholdLevel()));
    }
    if (isSetThresholdSymbol()) {
      attributes.put(QualParser.shortLabel + ":"
        + QualConstant.thresholdSymbol, getThresholdSymbol());
    }
    if (isSetTransitionEffect()) {
      attributes.put(QualParser.shortLabel + ":"
        + QualConstant.transitionEffect, getTransitionEffect().toString());
    }
    return attributes;
  }
}
