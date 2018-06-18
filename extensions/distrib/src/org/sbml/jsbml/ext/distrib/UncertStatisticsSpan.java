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
package org.sbml.jsbml.ext.distrib;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBase;

/**
 * @author rodrigue
 * @since 1.4
 */
public class UncertStatisticsSpan extends AbstractDistrictSBase {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // TODO - implements XML attributes, equals and hashcode

  /**
   * 
   */
  private String varLower;
  
  /**
   * 
   */
  private Double valueLower;
  
  /**
   * 
   */
  private String varUpper;
  
  /**
   * 
   */
  private Double valueUpper;
  
  /**
   * 
   */
  public UncertStatisticsSpan() {
    super();
  }

  /**
   * @param level
   * @param version
   */
  public UncertStatisticsSpan(int level, int version) {
    super(level, version);
  }

  /**
   * @param sb
   */
  public UncertStatisticsSpan(SBase sb) {
    super(sb);
  }

  /**
   * @param id
   * @param level
   * @param version
   */
  public UncertStatisticsSpan(String id, int level, int version) {
    super(id, level, version);
  }

  /**
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public UncertStatisticsSpan(String id, String name, int level, int version) {
    super(id, name, level, version);
  }

  /**
   * @param id
   */
  public UncertStatisticsSpan(String id) {
    super(id);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public AbstractSBase clone() {
    // TODO Auto-generated method stub
    return null;
  }


  /**
   * Returns the value of {@link #varLower}.
   *
   * @return the value of {@link #varLower}.
   */
  public String getVarLower() {
    if (isSetVarLower()) {
      return varLower;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.varLower, this);
  }


  /**
   * Returns whether {@link #varLower} is set.
   *
   * @return whether {@link #varLower} is set.
   */
  public boolean isSetVarLower() {
    return this.varLower != null;
  }


  /**
   * Sets the value of varLower
   *
   * @param varLower the value of varLower to be set.
   */
  public void setVarLower(String varLower) {
    String oldVarLower = this.varLower;
    this.varLower = varLower;
    firePropertyChange(DistribConstants.varLower, oldVarLower, this.varLower);
  }


  /**
   * Unsets the variable varLower.
   *
   * @return {@code true} if varLower was set before, otherwise {@code false}.
   */
  public boolean unsetVarLower() {
    if (isSetVarLower()) {
      String oldVarLower = this.varLower;
      this.varLower = null;
      firePropertyChange(DistribConstants.varLower, oldVarLower, this.varLower);
      return true;
    }
    return false;
  }

  
  /**
   * Returns the value of {@link #valueLower}.
   *
   * @return the value of {@link #valueLower}.
   */
  public double getValueLower() {
    if (isSetValueLower()) {
      return valueLower;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.valueLower, this);
  }


  /**
   * Returns whether {@link #valueLower} is set.
   *
   * @return whether {@link #valueLower} is set.
   */
  public boolean isSetValueLower() {
    return this.valueLower != null;
  }


  /**
   * Sets the value of valueLower
   *
   * @param valueLower the value of valueLower to be set.
   */
  public void setValueLower(double valueLower) {
    double oldValueLower = this.valueLower;
    this.valueLower = valueLower;
    firePropertyChange(DistribConstants.valueLower, oldValueLower, this.valueLower);
  }


  /**
   * Unsets the variable valueLower.
   *
   * @return {@code true} if valueLower was set before, otherwise {@code false}.
   */
  public boolean unsetValueLower() {
    if (isSetValueLower()) {
      double oldValueLower = this.valueLower;
      this.valueLower = null;
      firePropertyChange(DistribConstants.valueLower, oldValueLower, this.valueLower);
      return true;
    }
    return false;
  }
  
  
  /**
   * Returns the value of {@link #varUpper}.
   *
   * @return the value of {@link #varUpper}.
   */
  public String getVarUpper() {
    if (isSetVarUpper()) {
      return varUpper;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.varUpper, this);
  }


  /**
   * Returns whether {@link #varUpper} is set.
   *
   * @return whether {@link #varUpper} is set.
   */
  public boolean isSetVarUpper() {
    return this.varUpper != null;
  }


  /**
   * Sets the value of varUpper
   *
   * @param varUpper the value of varUpper to be set.
   */
  public void setVarUpper(String varUpper) {
    String oldVarUpper = this.varUpper;
    this.varUpper = varUpper;
    firePropertyChange(DistribConstants.varUpper, oldVarUpper, this.varUpper);
  }


  /**
   * Unsets the variable varUpper.
   *
   * @return {@code true} if varUpper was set before, otherwise {@code false}.
   */
  public boolean unsetVarUpper() {
    if (isSetVarUpper()) {
      String oldVarUpper = this.varUpper;
      this.varUpper = null;
      firePropertyChange(DistribConstants.varUpper, oldVarUpper, this.varUpper);
      return true;
    }
    return false;
  }
  
  
  /**
   * Returns the value of {@link #valueUpper}.
   *
   * @return the value of {@link #valueUpper}.
   */
  public double getValueUpper() {
    if (isSetValueUpper()) {
      return valueUpper;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.valueUpper, this);
  }


  /**
   * Returns whether {@link #valueUpper} is set.
   *
   * @return whether {@link #valueUpper} is set.
   */
  public boolean isSetValueUpper() {
    return this.valueUpper != null;
  }


  /**
   * Sets the value of valueUpper
   *
   * @param valueUpper the value of valueUpper to be set.
   */
  public void setValueUpper(double valueUpper) {
    double oldValueUpper = this.valueUpper;
    this.valueUpper = valueUpper;
    firePropertyChange(DistribConstants.valueUpper, oldValueUpper, this.valueUpper);
  }


  /**
   * Unsets the variable valueUpper.
   *
   * @return {@code true} if valueUpper was set before, otherwise {@code false}.
   */
  public boolean unsetValueUpper() {
    if (isSetValueUpper()) {
      double oldValueUpper = this.valueUpper;
      this.valueUpper = null;
      firePropertyChange(DistribConstants.valueUpper, oldValueUpper, this.valueUpper);
      return true;
    }
    return false;
  }
  
  
}
