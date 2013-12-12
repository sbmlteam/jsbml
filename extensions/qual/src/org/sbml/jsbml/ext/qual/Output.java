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
public class Output extends AbstractNamedSBase implements UniqueNamedSBase, CallableSBase{

  /**
   * Generated serial version identifier.
   */
  private static final long      serialVersionUID = -6392002023918667156L;
  private String                 qualitativeSpecies;
  private OutputTransitionEffect transitionEffect;
  private Integer                outputLevel;

  /**
   * 
   */
  public Output() {
	  super();
	  initDefaults();
  }

  /**
   * @param id
   */
  public Output(String id) {
    super(id);
    initDefaults();
  }
  
  /**
   * @param qualitativeSpecies
   */
  public Output(QualitativeSpecies qualitativeSpecies, OutputTransitionEffect transitionEffect) {
    this(null, qualitativeSpecies, transitionEffect);
  }

  /**
   * 
   * @param id
   * @param qualitativeSpecies
   * @param transitionEffect
   */
  public Output(String id, QualitativeSpecies qualitativeSpecies, OutputTransitionEffect transitionEffect) {
    this(id);
    setQualitativeSpecies(qualitativeSpecies.getId());
    setTransitionEffect(transitionEffect);
  }

  /**
   * 
   * @param level
   * @param version
   */
  public Output(int level, int version) {
	  this(null, null, level, version);
  }


  /**
   * @param id
   * @param level
   * @param version
   */
  public Output(String id, int level, int version) {
	  this(id, null, level, version);
  }

  /**
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public Output(String id, String name, int level, int version) {
	  super(id, name, level, version);
	  // TODO: replace level/version check with call to helper method
	  if (getLevelAndVersion().compareTo(Integer.valueOf(3), Integer.valueOf(1)) < 0) {
		  throw new LevelVersionError(getElementName(), level, version);
	  }
	  initDefaults();
  }

  public Output(Output out) {
    super(out);
    
    if (out.isSetQualitativeSpecies()) {
      setQualitativeSpecies(out.getQualitativeSpecies());
    }
    if (out.isSetTransitionEffect()) {
      setTransitionEffect(out.getTransitionEffect());
    }
    if (out.isSetOutputLevel()) {
      setOutputLevel(out.getOutputLevel());
    }
  }

  /**
   * 
   */
  public void initDefaults() {
     addNamespace(QualConstants.namespaceURI);
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
    return this.qualitativeSpecies != null;
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


  /**
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
    return this.transitionEffect != null;
  }


  /**
   * @return the transitionEffect
   */
  public OutputTransitionEffect getTransitionEffect() {
    if (isSetTransitionEffect()) {
      return transitionEffect;
    }
    throw new PropertyUndefinedError(QualConstants.transitionEffect, this);
  }


  /**
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
   * @return the outputLevel
   */
  public int getOutputLevel() {
    if (isSetOutputLevel()) {
      return outputLevel.intValue();
    }
    throw new PropertyUndefinedError(QualConstants.outputLevel, this);
  }


  /**
   * @param level
   *        the level to set
   */
  public void setOutputLevel(int level) {
    Integer oldLevel = this.outputLevel;
    this.outputLevel = level;
    firePropertyChange(QualConstants.outputLevel, oldLevel, this.outputLevel);
  }


  /**
   * 
   * @return
   */
  public boolean unsetOutputLevel() {
    if (isSetOutputLevel()) {
      Integer oldLevel = this.outputLevel;
      this.outputLevel = null;
      firePropertyChange(QualConstants.outputLevel, oldLevel, this.outputLevel);
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
			  setLevel(StringTools.parseSBMLInt(value));
		  } else if (attributeName.equals(QualConstants.transitionEffect)) {
			  try {
				  setTransitionEffect(OutputTransitionEffect.valueOf(value));
			  } catch (Exception e) {
				  throw new SBMLException("Could not recognized the value '" + value + "' for the attribute " + 
						  QualConstants.transitionEffect + " on the 'output' element.");
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
  public boolean containsUndeclaredUnits() {
		return false;
	}

  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnitDefinition()
   */
	public UnitDefinition getDerivedUnitDefinition() {
		return null; // return Dimensionless here ??
		// TODO: ask Sarah if the qual specs say anything about that
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnits()
	 */
	public String getDerivedUnits() {
		return null; // see comment above
	}
  
}
