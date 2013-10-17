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
 * 4. The University of California, San Diego, La Jolla, CA, USA
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
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Florian Mittag
 * @version $Rev$
 * @since 1.0
 * @date $Date$
 */
public class Input extends AbstractNamedSBase implements UniqueNamedSBase, CallableSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long     serialVersionUID = -3370025650545068132L;
  /**
   * 
   */
  private String                qualitativeSpecies;
  /**
   * 
   */
  private InputTransitionEffect transitionEffect;
  /**
   * 
   */
  private Integer               thresholdLevel;
  /**
   * 
   */
  private Sign                  sign;


  /**
   * 
   */
  public Input() {
    super();
    initDefaults();
  }

  /**
   * @param id
   */
  public Input(String id) {
    super(id);
    initDefaults();
  }


  /**
   * 
   * @param id
   * @param qualitativeSpecies
   * @param transitionEffect
   */
  public Input(String id, QualitativeSpecies qualitativeSpecies, InputTransitionEffect transitionEffect) {
    this(id);
    setQualitativeSpecies(qualitativeSpecies.getId());
    setTransitionEffect(transitionEffect);
  }
   
  /**
   * 
   * @param level
   * @param version
   */
  public Input(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * @param id
   * @param level
   * @param version
   */
  public Input(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public Input(String id, String name, int level, int version) {
    super(id, name, level, version);
    // TODO: replace level/version check with call to helper method
    if (getLevelAndVersion().compareTo(Integer.valueOf(3), Integer.valueOf(1)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  public Input(Input in) {
    super(in);
    
    if (in.isSetQualitativeSpecies()) {
      setQualitativeSpecies(in.getQualitativeSpecies());
    }
    if (in.isSetSign()) {
      setSign(in.getSign());
    }
    if (in.isSetThresholdLevel()) {
      setThresholdLevel(in.getThresholdLevel());
    }
    if (in.isSetTransitionEffect()) {
      setTransitionEffect(in.getTransitionEffect());
    }
  }
  
  /**
   * 
   */
  public void initDefaults() {
    addNamespace(QualConstant.namespaceURI);
    qualitativeSpecies = null;
    transitionEffect = null;
    thresholdLevel = null;
    sign = null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  public AbstractSBase clone() {
    return new Input(this);
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

  /**
   * 
   * @return
   */
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


  /* (non-Javadoc)
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

  /**
   * 
   * @return
   */
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

  /**
   * 
   * @return
   */
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

  /**
   * 
   * @return
   */
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

  /* (non-Javadoc)
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
      equals &= i.isSetSign() == isSetSign();
      if (equals && isSetSign()) {
        equals &= (i.getSign() == getSign());
      }
    }
    return equals;
  }


  /* (non-Javadoc)
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
    if (isSetSign()) {
      hashCode += prime * getSign().hashCode();
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
      if (attributeName.equals(QualConstant.qualitativeSpecies)) {
        setQualitativeSpecies(value);
      } else if (attributeName.equals(QualConstant.thresholdLevel)) {
        setThresholdLevel(StringTools.parseSBMLInt(value));
      } else if (attributeName.equals(QualConstant.sign)) {
    	  try {
    		  setSign(Sign.valueOf(value));
    	  } catch (Exception e) {
    		  throw new SBMLException("Could not recognized the value '" + value
    				  + "' for the attribute " + QualConstant.sign
    				  + " on the 'input' element.");
    	  }
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetId()) {
      attributes.remove("id");
      attributes.put(QualConstant.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(QualConstant.shortLabel + ":name", getName());
    }
    if (isSetQualitativeSpecies()) {
      attributes.put(QualConstant.shortLabel + ':'
        + QualConstant.qualitativeSpecies, getQualitativeSpecies());
    }
    if (isSetThresholdLevel()) {
      attributes.put(QualConstant.shortLabel + ':'
        + QualConstant.thresholdLevel, Integer.toString(getThresholdLevel()));
    }
    if (isSetTransitionEffect()) {
      attributes.put(QualConstant.shortLabel + ':'
        + QualConstant.transitionEffect, getTransitionEffect().toString());
    }
    if (isSetSign()) {
      attributes.put(QualConstant.shortLabel + ':'
        + QualConstant.sign, getSign().toString());
    }
    return attributes;
  }

public boolean containsUndeclaredUnits() {
	return false;
}

public UnitDefinition getDerivedUnitDefinition() {
	return null; // return Dimensionless here ??
	// TODO : ask Sarah if the qual specs say anything about that
}

public String getDerivedUnits() {
	return null; // see comment above
}
}
