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

import org.sbml.jsbml.AbstractMathContainer;
import org.sbml.jsbml.PropertyUndefinedError;

/**
 * @author Nicolas Rodriguez
 * @author Finja B&uuml;chel
 * @version $Rev$
 * @since 1.0
 * @date 29.09.2011
 */
public class FunctionTerm extends AbstractMathContainer {

	/**
   * 
   */
  private static final long serialVersionUID = -3456373304133826017L;
  
  private Integer resultLevel;
	private String resultSymbol;
	
	private Double temporisationValue;
	private TemporisationMath temporisationMath;
	
	private boolean defaultTerm;
	
	@Override
	public AbstractMathContainer clone() {
		return null;
	}

	/**
	 * 
	 * @return false
	 */
	public boolean isResultLevelMandatory(){
	  return false;
	}
	
	public boolean isSetResultLevel(){
	  return this.resultLevel!= null;
	}
	
	/**
   * @return the level
   */
  public int getResultLevel() {
    if (isSetResultLevel()) {
      return resultLevel.intValue();
    } else {
      throw new PropertyUndefinedError(QualChangeEvent.resultLevel, this);
    }
  }


  /**
   * @param resultLevel
   *        the resultLevel to set
   */
  public void setResultLevel(int resultLevel) {
    Integer oldResultLevel = this.resultLevel;
    this.resultLevel = resultLevel;
    firePropertyChange(QualChangeEvent.resultLevel, oldResultLevel, this.resultLevel);
  }


  public boolean unsetResultLevel() {
    if (isSetResultLevel()) {
      Integer oldResultLevel = this.resultLevel;
      this.resultLevel = null;
      firePropertyChange(QualChangeEvent.resultLevel, oldResultLevel, this.resultLevel);
      return true;
    } else {
      return false;
    }
  }

  /**
   * 
   * @return true
   */
  public boolean isResultSymbolMandatory(){
    return true;
  }
  
  /**
   * @return if boundaryCondition attribute is set
   */
  public boolean isSetResultSymbol() {
    return resultSymbol != null;
  }


  /**
   * @return the resultSymbol
   */
  public String getResultSymbol() {
    return isSetResultSymbol() ? resultSymbol : "";
  }


  /**
   * @param resultSymbol
   *        the resultSymbol to set
   */
  public void setResultSymbol(String resultSymbol) {
    String oldresultSymbol = this.resultSymbol;
    if ((resultSymbol == null) || (resultSymbol.length() == 0)) {
      this.resultSymbol = null;
    } else {
      this.resultSymbol = resultSymbol;
    }
    firePropertyChange(QualChangeEvent.resultSymbol, oldresultSymbol,
      this.resultSymbol);
  }


  /**
   * @return true if the unset of the resultSymbol attribute was successful
   */
  public boolean unsetResultSymbol() {
    if (resultSymbol != null) {
      setResultSymbol(null);
      return true;
    }
    return false;
  }

  /**
   * 
   * @return false
   */
  public boolean isTemporisationValueMandatory(){
    return false;
  }
  
  public boolean isSetTemporisationValue(){
    return this.temporisationValue!= null;
  }
  
  /**
   * @return the level
   */
  public double getTemporisationValue() {
    if (isSetTemporisationValue()) {
      return temporisationValue.doubleValue();
    } else {
      throw new PropertyUndefinedError(QualChangeEvent.temporisationValue, this);
    }
  }


  /**
   * @param level
   *        the remporisationValue to set
   */
  public void setTemporisationValue(double tempValue) {
    Double oldTempValue = this.temporisationValue;
    this.temporisationValue = tempValue;
    firePropertyChange(QualChangeEvent.temporisationValue, oldTempValue, this.temporisationValue);
  }


  public boolean unsetTemporisationValue() {
    if (isSetResultLevel()) {
      Double oldTempValue = this.temporisationValue;
      this.temporisationValue = null;
      firePropertyChange(QualChangeEvent.temporisationValue, oldTempValue, this.temporisationValue);
      return true;
    } else {
      return false;
    }
  }
	
  /**
   * @return false
   */
  public boolean isTemporisationMathMandatory(){
    return false;
  }
  
  public boolean isSetTemporisationMath(){
    return this.temporisationMath!=null;
  }
  
  /**
	 * @return the temporisationMath
	 */
	public TemporisationMath getTemporisationMath() {
	  if(isSetTemporisationMath()){
	    return temporisationMath;
	  } else{
	    throw new PropertyUndefinedError(QualChangeEvent.temporisationMath, this);
	  }
	}

	/**
	 * @param temporisationMath the temporisationMath to set
	 */
	public void setTemporisationMath(TemporisationMath temporisationMath) {
		TemporisationMath oldTM = this.temporisationMath;
	  this.temporisationMath = temporisationMath;
	  firePropertyChange(QualChangeEvent.temporisationMath, oldTM, this.temporisationMath);
	}

	/**
	 * 
	 * @return true if the unset of the attribute temporisationMath was successful
	 */
	public boolean unsetTemporisationMath(){
	  if(isSetTemporisationMath()){
	    setTemporisationMath(null);
	    return true;
	  } else {
	    return false;
	  }
	  
	}
	
//=========================================================================================
	
	/**
	 * @return the defaultTerm
	 */
	public boolean isDefaultTerm() {
		return defaultTerm;
	}

	/**
	 * @param defaultTerm the defaultTerm to set
	 */
	public void setDefaultTerm(boolean defaultTerm) {
		this.defaultTerm = defaultTerm;
	}
	
//=========================================================================================
	
	
	
}
