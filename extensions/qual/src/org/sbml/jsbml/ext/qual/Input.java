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
package org.sbml.jsbml.ext.qual;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.CallableSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SpeciesReference;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.parsers.AbstractReaderWriter;

/**
 * Each {@link Input} refers to a {@link QualitativeSpecies} that participates
 * in the corresponding {@link Transition}. In Petri nets, these are the input
 * places of the transition. In logical models, they are the regulators of the
 * species whose behavior is defined by the {@link Transition}.
 * 
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Florian Mittag
 * @since 1.0
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
   * Creates a new {@link Input} instance.
   */
  public Input() {
    super();
    initDefaults();
  }

  /**
   * Creates a new {@link Input} instance.
   * 
   * @param id the id to be set.
   */
  public Input(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a new {@link Input} instance.
   * 
   * @param id the id to be set.
   * @param qualitativeSpecies the {@link QualitativeSpecies} that is linked to this {@link Input}.
   * @param transitionEffect the transition effect.
   */
  public Input(String id, QualitativeSpecies qualitativeSpecies, InputTransitionEffect transitionEffect) {
    this(id);
    setQualitativeSpecies(qualitativeSpecies.getId());
    setTransitionEffect(transitionEffect);
  }

  /**
   * Creates a new {@link Input} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public Input(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Creates a new {@link Input} instance.
   * 
   * @param id the id to be set.
   * @param level the SBML level
   * @param version the SBML version
   */
  public Input(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a new {@link Input} instance.
   * 
   * @param id the id to be set.
   * @param name the name to be set.
   * @param level the SBML level
   * @param version the SBML version
   */
  public Input(String id, String name, int level, int version) {
    super(id, name, level, version);

    if (getLevelAndVersion().compareTo(Integer.valueOf(3), Integer.valueOf(1)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  /**
   * Creates a new {@link Input} instance cloned from the given {@link Input}.
   * 
   * @param in the {@link Input} instance to clone.
   */
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
    setPackageVersion(-1);
    packageName = QualConstants.shortLabel;
    qualitativeSpecies = null;
    transitionEffect = null;
    thresholdLevel = null;
    sign = null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public Input clone() {
    return new Input(this);
  }


  /**
   * The sign of type {@link Sign} can be used as an indication as to whether the contribution
   * of this {@link Input} is positive, negative, both (dual) or unknown. This enables a model
   * to distinguish between stimulation and inhibition and can facilitate interpretation of the
   * model without the mathematics. The sign is particularly used for visualization purposes and
   * has no impact on the mathematical interpretation. This attribute is optional.
   * 
   * @param sign
   *        the sign to set
   */
  public void setSign(Sign sign) {
    Sign oldSign = this.sign;
    this.sign = sign;
    firePropertyChange(QualConstants.sign, oldSign, this.sign);
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
      throw new PropertyUndefinedError(QualConstants.sign, this);
    }
  }


  /**
   * @return {@code true} if unset the sign attribute was successful
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
  @Override
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
      throw new PropertyUndefinedError(QualConstants.qualitativeSpecies, this);
    }
  }
  
  public QualitativeSpecies getQualitativeSpeciesInstance() {
    if (isSetQualitativeSpecies()) {
      Model model = getModel();
      if (model != null) {
        if (model.getSBaseById(getQualitativeSpecies()) instanceof QualitativeSpecies) {
          return (QualitativeSpecies) model.getSBaseById(getQualitativeSpecies());
        }
      }
    }
    return null;
  }


  /**
   * The required attribute qualitativeSpecies is used to identify the {@link QualitativeSpecies}
   * that is the input of this {@link Transition}. The attribute's value must be the identifier
   * of an existing {@link QualitativeSpecies} object in the {@link Model}. This attribute is
   * comparable with the species attribute on the {@link SpeciesReference} element.
   * 
   * @param qualitativeSpecies
   *        the qualitativeSpecies to set
   */
  public void setQualitativeSpecies(String qualitativeSpecies) {
    String oldQualitativeSpecies = this.qualitativeSpecies;
    this.qualitativeSpecies = qualitativeSpecies;
    firePropertyChange(QualConstants.qualitativeSpecies,
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
      throw new PropertyUndefinedError(QualConstants.transitionEffect, this);
    }
  }


  /**
   * Each {@link Input} has a required attribute transitionEffect of type {@link InputTransitionEffect}
   * which describes how the {@link QualitativeSpecies} referenced by the {@link Input} is affected by
   * the {@link Transition}.
   * 
   * It should be noted that in logical models the transitionEffect is always set to "none", while in
   * Petri nets, it can be set to "none" (indicating a read arc) or to "consumption".
   * 
   * @param transitionEffect
   *        the transitionEffect to set
   */
  public void setTransitionEffect(InputTransitionEffect transitionEffect) {
    InputTransitionEffect oldTransitionEffect = this.transitionEffect;
    this.transitionEffect = transitionEffect;
    firePropertyChange(QualConstants.transitionEffect, oldTransitionEffect,
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
    return thresholdLevel != null;
  }


  /**
   * @return the thresholdLevel
   */
  public int getThresholdLevel() {
    if (isSetThresholdLevel()) {
      return thresholdLevel.intValue();
    }
    throw new PropertyUndefinedError(QualConstants.thresholdLevel, this);
  }


  /**
   * The thresholdLevel is a non-negative integer that can be used to set the threshold level of
   * the particular input. This attribute relates to the contribution of this input required for
   * the transition to take place.
   * 
   * In logical regulatory models, it refers to the threshold level that must be reached or exceeded
   * in order for the regulation to take place, while in a Petri net, it refers to the number of
   * tokens required to enable the transition (weight of the arc connecting the input place to the
   * transition). Whether the level of a {@link QualitativeSpecies} should reach or exceed the
   * thresholdLevel in order for the {@link Transition} to occur will be encoded in the math elements
   * of the {@link FunctionTerm}s listed for the given {@link Transition}.
   * 
   * The thresholdLevel is used by the {@link FunctionTerm}s associated with the containing
   * {@link Transition} to determine the applicable resultLevel that should be applied.  The id of the
   * {@link Input} represents this value and can be used in the math element of a {@link FunctionTerm}.
   * When defined, this attribute should be coherent with the content of the {@link FunctionTerm}, i.e.
   * if a number is used in the {@link FunctionTerm} to compare the current level of a species, this
   * number must correspond to the thresholdLevel of the corresponding {@link Input}. Since a number
   * can be used within the {@link FunctionTerm} to represent the thresholdLevel of an {@link Input}
   * it is not compulsory to use this attribute to specify the value. A missing thresholdLevel attribute
   * merely implies that the threshold is incorporated into the {@link FunctionTerm} using a number.
   * 
   * @param thresholdLevel
   *        the thresholdLevel to set
   */
  public void setThresholdLevel(int thresholdLevel) {
    Integer oldThresholdLevel = this.thresholdLevel;
    this.thresholdLevel = thresholdLevel;
    firePropertyChange(QualConstants.thresholdLevel, oldThresholdLevel,
      this.thresholdLevel);
  }


  /**
   * @return {@code true} if unset the threholdLevel attribute was successful
   */
  public boolean unsetThresholdLevel() {
    if (isSetThresholdLevel()) {
      Integer oldThresholdLevel = thresholdLevel;
      thresholdLevel = null;
      firePropertyChange(QualConstants.thresholdLevel, oldThresholdLevel,
        thresholdLevel);
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
      if (attributeName.equals(QualConstants.qualitativeSpecies)) {
        setQualitativeSpecies(value);
      } else if (attributeName.equals(QualConstants.thresholdLevel)) {
    	  try {
    		  setThresholdLevel(StringTools.parseSBMLInt(value));
    	  } catch (IllegalArgumentException e) {
      	AbstractReaderWriter.processInvalidAttribute(attributeName, null, value, prefix, this);
      }
      } else if (attributeName.equals(QualConstants.sign)) {
        try {
          setSign(Sign.valueOf(value));
        } catch (IllegalArgumentException e) {
        	AbstractReaderWriter.processInvalidAttribute(attributeName, null, value, prefix, this);
		}
      } else if (attributeName.equals(QualConstants.transitionEffect)) {
        try {
          setTransitionEffect(InputTransitionEffect.valueOf(value));
        } catch (IllegalArgumentException e) {
        	AbstractReaderWriter.processInvalidAttribute(attributeName, null, value, prefix, this);
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
      attributes.put(QualConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(QualConstants.shortLabel + ":name", getName());
    }
    if (isSetQualitativeSpecies()) {
      attributes.put(QualConstants.shortLabel + ':'
        + QualConstants.qualitativeSpecies, getQualitativeSpecies());
    }
    if (isSetThresholdLevel()) {
      attributes.put(QualConstants.shortLabel + ':'
        + QualConstants.thresholdLevel, Integer.toString(getThresholdLevel()));
    }
    if (isSetTransitionEffect()) {
      attributes.put(QualConstants.shortLabel + ':'
        + QualConstants.transitionEffect, getTransitionEffect().toString());
    }
    if (isSetSign()) {
      attributes.put(QualConstants.shortLabel + ':'
        + QualConstants.sign, getSign().toString());
    }
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#containsUndeclaredUnits()
   */
  @Override
  public boolean containsUndeclaredUnits() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnitDefinition()
   */
  @Override
  public UnitDefinition getDerivedUnitDefinition() {
    return null; // return Dimensionless here ??
    // TODO: ask Sarah if the qual specs say anything about that
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnits()
   */
  @Override
  public String getDerivedUnits() {
    return null; // see comment above
  }

}
