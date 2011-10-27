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
 * @author Florian Mittag
 * @version $$Rev$$
 * @since 0.8
 * @date ${date}
 * ${tags}
 */
public class Input extends AbstractNamedSBase {

	private String qualitativeSpecies;
	
	private InputTransitionEffect transitionEffect;
	
	private Integer thresholdLevel;
	private String thresholdSymbol;
	
	@Override
	public AbstractSBase clone() {
		// TODO Auto-generated method stub
		return null;
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
   * @return true
   */
  public boolean isTransitionEffectMandatory() {
    return true;
  }
  
  /**
   * @return false 
   */
  public boolean isThresholdLevelMandatory() {
    return false;
  }

  /**
   * @return false
   */
  public boolean isThresholdSymbolMandatory() {
    return false;
  }

	/**
	 * @return the qualitativeSpecies
	 */
	public String getQualitativeSpecies() {
		return qualitativeSpecies;
	}

	/**
	 * @param qualitativeSpecies the qualitativeSpecies to set
	 */
	public void setQualitativeSpecies(String qualitativeSpecies) {
		this.qualitativeSpecies = qualitativeSpecies;
	}

	/**
	 * @return if the qualitative species is set
	 */
	public boolean isSetQualitativeSpecies() {
	  return qualitativeSpecies != null;
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
	 * @return the transitionEffect
	 */
	public InputTransitionEffect getTransitionEffect() {
		return transitionEffect;
	}

	/**
	 * @param transitionEffect the transitionEffect to set
	 */
	public void setTransitionEffect(InputTransitionEffect inputTransitionEffect) {
		this.transitionEffect = inputTransitionEffect;
	}

	/**
	 * @return if the transitionEffect is set
	 */
	public boolean isSetTransitionEffect() {
	  return transitionEffect != null;
	}

	/**
   * @return true if the unset of the transitionEffect attribute was successful
   */
  public boolean unsetTransitionEffect() {
    if (transitionEffect != null) {
      setTransitionEffect(null);
      return true;
    }
    return false;
  }
	
	/**
	 * @return the thresholdLevel
	 */
	public int getThresholdLevel() {
	  if (thresholdLevel == null) {
	    throw new PropertyUndefinedError(QualChangeEvent.thresholdLevel, this);
	  }
		return thresholdLevel;
	}

	/**
	 * @param thresholdLevel the thresholdLevel to set
	 */
	public void setThresholdLevel(int thresholdLevel) {
		this.thresholdLevel = thresholdLevel;
	}

	/**
	 * @return the thresholdSymbol
	 */
	public String getThresholdSymbol() {
		return thresholdSymbol;
	}

	/**
	 * @param thresholdSymbol the thresholdSymbol to set
	 */
	public void setThresholdSymbol(String thresholdSymbol) {
		this.thresholdSymbol = thresholdSymbol;
	}

	/**
   * @return if thresholdSymbol attribute is set
   */
  public boolean isSetThresholdSymbol() {
    return thresholdSymbol != null;
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
	
}
