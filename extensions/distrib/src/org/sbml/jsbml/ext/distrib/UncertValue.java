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
import org.sbml.jsbml.SBaseWithUnit;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.TreeNodeChangeEvent;


/**
 * @author rodrigue
 * @since 1.4
 */
public class UncertValue extends AbstractDistrictSBase implements SBaseWithUnit {

  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // TODO - implements units methods, XML attributes, equals and hashcode
  
  /**
   * 
   */
  Double value;
  
  /**
   * 
   */
  String var;
  
  /**
   * 
   */
  String units;
  
  
  
  /**
   * 
   */
  public UncertValue() {
  }


  /**
   * @param level
   * @param version
   */
  public UncertValue(int level, int version) {
    super(level, version);
  }


  /**
   * @param sb
   */
  public UncertValue(SBase sb) {
    super(sb);
  }


  /**
   * @param id
   */
  public UncertValue(String id) {
    super(id);
  }


  /**
   * @param id
   * @param level
   * @param version
   */
  public UncertValue(String id, int level, int version) {
    super(id, level, version);
  }


  /**
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public UncertValue(String id, String name, int level, int version) {
    super(id, name, level, version);
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
   * Returns the value of {@link #value}.
   *
   * @return the value of {@link #value}.
   */
  public double getValue() {

    if (isSetValue()) {
      return value;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.value, this);
  }


  /**
   * Returns whether {@link #value} is set.
   *
   * @return whether {@link #value} is set.
   */
  public boolean isSetValue() {
    return this.value != null;
  }


  /**
   * Sets the value of value
   *
   * @param value the value of value to be set.
   */
  public void setValue(double value) {
    double oldValue = this.value;
    this.value = value;
    firePropertyChange(DistribConstants.value, oldValue, this.value);
  }


  /**
   * Unsets the variable value.
   *
   * @return {@code true} if value was set before, otherwise {@code false}.
   */
  public boolean unsetValue() {
    if (isSetValue()) {
      double oldValue = this.value;
      this.value = null;
      firePropertyChange(DistribConstants.value, oldValue, this.value);
      return true;
    }
    
    return false;
  }
  
  
  /**
   * Returns the value of {@link #var}.
   *
   * @return the value of {@link #var}.
   */
  public String getVar() {
    if (isSetVar()) {
      return var;
    }

    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.var, this);
  }


  /**
   * Returns whether {@link #var} is set.
   *
   * @return whether {@link #var} is set.
   */
  public boolean isSetVar() {
    return this.var != null;
  }


  /**
   * Sets the value of var
   *
   * @param var the value of var to be set.
   */
  public void setVar(String var) {
    String oldVar = this.var;
    this.var = var;
    firePropertyChange(DistribConstants.var, oldVar, this.var);
  }


  /**
   * Unsets the variable var.
   *
   * @return {@code true} if var was set before, otherwise {@code false}.
   */
  public boolean unsetVar() {
    if (isSetVar()) {
      String oldVar = this.var;
      this.var = null;
      firePropertyChange(DistribConstants.var, oldVar, this.var);
      return true;
    }
    return false;
  }

  
  /**
   * Returns the value of {@link #units}.
   *
   * @return the value of {@link #units}.
   */
  public String getUnits() {

    if (isSetUnits()) {
      return units;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(TreeNodeChangeEvent.units, this);
  }


  /**
   * Returns whether {@link #units} is set.
   *
   * @return whether {@link #units} is set.
   */
  public boolean isSetUnits() {
    return this.units != null;
  }


  /**
   * Sets the value of units
   *
   * @param units the value of units to be set.
   */
  public void setUnits(String units) {
    String oldUnits = this.units;
    this.units = units;
    firePropertyChange(TreeNodeChangeEvent.units, oldUnits, this.units);
  }


  /**
   * Unsets the variable units.
   *
   * @return {@code true} if units was set before, otherwise {@code false}.
   */
  public void unsetUnits() {
    if (isSetUnits()) {
      String oldUnits = this.units;
      this.units = null;
      firePropertyChange(TreeNodeChangeEvent.units, oldUnits, this.units);
    }
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#containsUndeclaredUnits()
   */
  @Override
  public boolean containsUndeclaredUnits() {
    // TODO 
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnitDefinition()
   */
  @Override
  public UnitDefinition getDerivedUnitDefinition() {
    // TODO 
    return null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithDerivedUnit#getDerivedUnits()
   */
  @Override
  public String getDerivedUnits() {
    // TODO 
    return null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#getUnitsInstance()
   */
  @Override
  public UnitDefinition getUnitsInstance() {
    // TODO 
    return null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#isSetUnitsInstance()
   */
  @Override
  public boolean isSetUnitsInstance() {
    // TODO 
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.Unit)
   */
  @Override
  public void setUnits(Unit unit) {
    // TODO 
    
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.Unit.Kind)
   */
  @Override
  public void setUnits(Kind unitKind) {
    // TODO 
    
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.SBaseWithUnit#setUnits(org.sbml.jsbml.UnitDefinition)
   */
  @Override
  public void setUnits(UnitDefinition units) {
    // TODO 
    
  }
}
