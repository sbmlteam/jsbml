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
 * Each {@link Output} refers to a {@link QualitativeSpecies} that participates
 * in (is affected by) the corresponding {@link Transition}. In Petri net models
 * these are the output places of the transition. In a logical model, a
 * {@link QualitativeSpecies} should be referenced in at most one listOfOutputs,
 * (that of the {@link Transition} defining the evolution of this species). When
 * a {@link Transition} has several outputs, it is because the referenced
 * species share the same regulators and the same logical rules.
 * 
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @author Florian Mittag
 * @since 1.0
 */
public class Output extends AbstractNamedSBase implements UniqueNamedSBase, CallableSBase{

  /**
   * Generated serial version identifier.
   */
  private static final long      serialVersionUID = -6392002023918667156L;
  /**
   * 
   */
  private String                 qualitativeSpecies;
  /**
   * 
   */
  private OutputTransitionEffect transitionEffect;
  /**
   * 
   */
  private Integer                outputLevel;

  /**
   * Creates a new {@link Output} instance.
   */
  public Output() {
    super();
    initDefaults();
  }

  /**
   * Creates a new {@link Output} instance.
   * 
   * @param id the id
   */
  public Output(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a new {@link Output} instance.
   * 
   * @param qualitativeSpecies the {@link QualitativeSpecies} linked to this {@link Output}.
   * @param transitionEffect the transition effect
   */
  public Output(QualitativeSpecies qualitativeSpecies, OutputTransitionEffect transitionEffect) {
    this(null, qualitativeSpecies, transitionEffect);
  }

  /**
   * Creates a new {@link Output} instance.
   * 
   * @param id the id
   * @param qualitativeSpecies the {@link QualitativeSpecies} linked to this {@link Output}.
   * @param transitionEffect the transition effect
   */
  public Output(String id, QualitativeSpecies qualitativeSpecies, OutputTransitionEffect transitionEffect) {
    this(id);
    setQualitativeSpecies(qualitativeSpecies.getId());
    setTransitionEffect(transitionEffect);
  }

  /**
   * Creates a new {@link Output} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public Output(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a new {@link Output} instance.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   */
  public Output(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a new {@link Output} instance.
   * 
   * @param id the id
   * @param name the name
   * @param level the SBML level
   * @param version the SBML version
   */
  public Output(String id, String name, int level, int version) {
    super(id, name, level, version);

    if (getLevelAndVersion().compareTo(Integer.valueOf(3), Integer.valueOf(1)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  /**
   * Creates a new {@link Output} instance.
   * 
   * @param out the {@link Output} instance to be cloned
   */
  public Output(Output out) {
    super(out);

    if (out.isSetQualitativeSpecies()) {
      setQualitativeSpecies(new String(out.getQualitativeSpecies()));
    }
    if (out.isSetTransitionEffect()) {
      setTransitionEffect(out.getTransitionEffect());
    }
    if (out.isSetOutputLevel()) {
      setOutputLevel(new Integer(out.getOutputLevel()));
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
    outputLevel = null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public Output clone() {
    return new Output(this);
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
   * 
   * @return
   */
  public boolean isSetQualitativeSpecies() {
    return qualitativeSpecies != null;
  }


  /**
   * Returns the qualitativeSpecies
   * 
   * @return the qualitativeSpecies
   * @throws PropertyUndefinedError if {@link #isSetQualitativeSpecies()} return {@code false}.
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
   * that is the output of this {@link Transition}. The attribute's value must be the identifier
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
   * 
   * @return
   */
  public boolean unsetQualitativeSpecies() {
    if (isSetQualitativeSpecies()) {
      setQualitativeSpecies(null);
      return true;
    }
    return false;
  }


  /**
   * @return false
   */
  public boolean isTransitionEffectMandatory() {
    return true;
  }


  /**
   * 
   * @return
   */
  public boolean isSetTransitionEffect() {
    return transitionEffect != null;
  }


  /**
   * Returns the transitionEffect
   * 
   * @return the transitionEffect
   * @throws PropertyUndefinedError if {@link #isSetTransitionEffect()} return {@code false}.
   */
  public OutputTransitionEffect getTransitionEffect() {
    if (isSetTransitionEffect()) {
      return transitionEffect;
    }
    throw new PropertyUndefinedError(QualConstants.transitionEffect, this);
  }


  /**
   * Each {@link Output} has a required attribute transitionEffect of type {@link OutputTransitionEffect}
   * which describes how the {@link QualitativeSpecies} referenced by the {@link Output} is affected by
   * the {@link Transition}.
   * 
   * In logical models the transitionEffect is set to "assignmentLevel" whilst in standard Petri nets it
   * is set to "production". It is envisioned that to encode High Level Petri nets it will be necessary
   * to allow the use of "assignmentLevel" as an {@link OutputTransitionEffect}; however considering the
   * implications of this is left to future versions of the specification.
   * 
   * @param transitionEffect
   *        the transitionEffect to set
   */
  public void setTransitionEffect(OutputTransitionEffect transitionEffect) {
    OutputTransitionEffect oldTransitionEffect = this.transitionEffect;
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
  public boolean isOutputLevelMandatory() {
    return false;
  }


  /**
   * 
   * @return
   */
  public boolean isSetOutputLevel() {
    return outputLevel != null;
  }


  /**
   * Returns the outputLevel.
   * 
   * @return the outputLevel
   * @throws PropertyUndefinedError if {@link #isSetOutputLevel()} return {@code false}.
   */
  public int getOutputLevel() {
    if (isSetOutputLevel()) {
      return outputLevel.intValue();
    }
    throw new PropertyUndefinedError(QualConstants.outputLevel, this);
  }


  /**
   * The outputLevel is a non-negative integer used along with the transitionEffect to specify
   * the effect of the {@link Transition} on the corresponding {@link QualitativeSpecies}. It
   * does not specify the result of a {@link Transition}; this is done by using the resultLevel
   * attribute on a {@link FunctionTerm}. However, in Petri nets, it relates to the weight of the
   * arc connecting the {@link Transition} to the output place and may be multiplied by the
   * resultLevel in a "production" situation. In logical models there is no interpretation of the
   * outputLevel attribute as the outcome of a {@link Transition} is always an assignment to
   * the resultLevel defined by the {@link FunctionTerm}.
   * 
   * The outputLevel attribute is optional since if the transitionEffect is set to "assignmentLevel"
   * (as in logical models), it has no meaning. However, where the transitionEffect of the
   * {@link Output} is set to "production" (as in Petri net models) the resulting level of the
   * {@link QualitativeSpecies} is the resultLevel from the appropriate {@link FunctionTerm} multiplied
   * by the outputLevel. Since there are no default values in SBML Level 3, when the transitionEffect
   * is set to "production" the outputLevel attribute must have a value.
   * 
   * @param level
   *        the level to set
   */
  public void setOutputLevel(int level) {
    Integer oldLevel = outputLevel;
    outputLevel = level;
    firePropertyChange(QualConstants.outputLevel, oldLevel, outputLevel);
  }


  /**
   * 
   * @return
   */
  public boolean unsetOutputLevel() {
    if (isSetOutputLevel()) {
      Integer oldLevel = outputLevel;
      outputLevel = null;
      firePropertyChange(QualConstants.outputLevel, oldLevel, outputLevel);
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
      Output o = (Output) object;
      equals &= o.isSetQualitativeSpecies() == isSetQualitativeSpecies();
      if (equals && isSetQualitativeSpecies()) {
        equals &= (o.getQualitativeSpecies().equals(getQualitativeSpecies()));
      }
      equals &= o.isSetTransitionEffect() == isSetTransitionEffect();
      if (equals && isSetTransitionEffect()) {
        equals &= (o.getTransitionEffect().equals(getTransitionEffect()));
      }
      equals &= o.isSetOutputLevel() == isSetOutputLevel();
      if (equals && isSetOutputLevel()) {
        equals &= o.getLevel()==getLevel();
      }
    }
    return equals;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 991;
    int hashCode = super.hashCode();
    if (isSetQualitativeSpecies()) {
      hashCode += prime * getQualitativeSpecies().hashCode();
    }
    if (isSetTransitionEffect()) {
      hashCode += prime * getTransitionEffect().hashCode();
    }
    if (isSetOutputLevel()) {
      hashCode += prime * getLevel();
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
      } else if (attributeName.equals(QualConstants.outputLevel)) {
        try {
          setOutputLevel(StringTools.parseSBMLInt(value));
        } catch (IllegalArgumentException e) {
          AbstractReaderWriter.processInvalidAttribute(attributeName, null, value, prefix, this);
        }
      } else if (attributeName.equals(QualConstants.transitionEffect)) {
        try {
          setTransitionEffect(OutputTransitionEffect.valueOf(value));
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
      attributes.put(QualConstants.shortLabel+ ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(QualConstants.shortLabel+ ":name", getName());
    }
    if (isSetQualitativeSpecies()) {
      attributes.put(QualConstants.shortLabel+ ':' + QualConstants.qualitativeSpecies, getQualitativeSpecies());
    }
    if (isSetOutputLevel()) {
      attributes.put(QualConstants.shortLabel+ ':' + QualConstants.outputLevel, Integer.toString(getOutputLevel()));
    }
    if (isSetTransitionEffect()) {
      attributes.put(QualConstants.shortLabel+ ':' + QualConstants.transitionEffect, getTransitionEffect().toString());
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
