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
 * @version $Rev$
 * @since 1.0
 * @date 29.09.2011
 */
public class Output extends AbstractNamedSBase implements UniqueNamedSBase{

  /**
   * Generated serial version identifier.
   */
  private static final long      serialVersionUID = -6392002023918667156L;
  private String                 qualitativeSpecies;
  private OutputTransitionEffect transitionEffect;
  private Integer                outputLevel;

  public Output() {
	  super();
	  initDefaults();
  }

  public Output(String id, OutputTransitionEffect assignmentlevel) {
    super(id);
    setTransitionEffect(assignmentlevel);
  }

  /**
  * 
  * @param level
  * @param version
  */
 public Output(int level, int version){
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
   outputLevel = null;   
 }
  
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
      throw new PropertyUndefinedError(QualConstant.transitionEffect, this);
    }
  }


  /**
   * @param transitionEffect
   *        the transitionEffect to set
   */
  public void setTransitionEffect(OutputTransitionEffect transitionEffect) {
    OutputTransitionEffect oldTransitionEffect = this.transitionEffect;
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
  public boolean isOutputLevelMandatory() {
    return false;
  }


  public boolean isSetOutputLevel() {
    return outputLevel != null;
  }


  /**
   * @return the outputLevel
   */
  public int getOutputLevel() {
    if (isSetOutputLevel()) {
      return outputLevel.intValue();
    } else {
      throw new PropertyUndefinedError(QualConstant.outputLevel, this);
    }
  }


  /**
   * @param outputLevel
   *        the level to set
   */
  public void setOutputLevel(int level) {
    Integer oldLevel = this.outputLevel;
    this.outputLevel = level;
    firePropertyChange(QualConstant.outputLevel, oldLevel, this.outputLevel);
  }


  public boolean unsetOutputLevel() {
    if (isSetOutputLevel()) {
      Integer oldLevel = this.outputLevel;
      this.outputLevel = null;
      firePropertyChange(QualConstant.outputLevel, oldLevel, this.outputLevel);
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
    if (isSetTransitionEffect()){
      hashCode += prime * getTransitionEffect().hashCode();
    }
    if (isSetOutputLevel()) {
      hashCode += prime * getLevel();
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
		  } else if (attributeName.equals(QualConstant.outputLevel)) {
			  setLevel(StringTools.parseSBMLInt(value));
		  } else if (attributeName.equals(QualConstant.transitionEffect)) {
			  try {
				  setTransitionEffect(OutputTransitionEffect.valueOf(value));
			  } catch (Exception e) {
				  throw new SBMLException("Could not recognized the value '" + value + "' for the attribute " + 
						  QualConstant.transitionEffect + " on the 'output' element.");
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
		  attributes.put(QualParser.shortLabel+ ":id", getId());
	  }
	  if (isSetName()) {
		  attributes.remove("name");
		  attributes.put(QualParser.shortLabel+ ":name", getName());
	  }
	  if (isSetQualitativeSpecies()) {
		  attributes.put(QualParser.shortLabel+ ":"+QualConstant.qualitativeSpecies, getQualitativeSpecies());
	  }
	  if (isSetOutputLevel()) {
		  attributes.put(QualParser.shortLabel+ ":"+QualConstant.outputLevel, Integer.toString(getOutputLevel()));
	  }	  
	  if (isSetTransitionEffect()) {
		  attributes.put(QualParser.shortLabel+ ":"+QualConstant.transitionEffect, getTransitionEffect().toString());
	  }
	  
	  return attributes;
	}
  
}
