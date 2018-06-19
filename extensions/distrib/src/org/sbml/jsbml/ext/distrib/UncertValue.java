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

import java.util.Map;

import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBaseWithUnit;
import org.sbml.jsbml.Unit;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeEvent;


/**
 * The {@link UncertValue} class provides two optional attributes, exactly one of which must be defined. 
 * 
 * <p>The 'value' attribute
 * (of type double) is used when the UncertValue represents a particular number, and the 'var' attribute (of type
 * UncertIdRef) is used when the UncertValue represents a referenced element with mathematical meaning. In the
 * context of a FunctionDefinition, this can only reference a DistribInput, as no SId from the Model may be referenced
 * from within a FunctionDefinition. In other contexts, it may reference the SId of any element with mathematical
 * meaning.</p>
 * 
 * <p>The optional units attribute may be used to indicate the units of the 'value' attribute. As such, it may only be defined
 * if the UncertValue has a defined 'value' attribute, and not if it has a defined 'var' attribute. (In the latter case, the units
may be derived from the referenced element.)
 *
 * @author rodrigue
 * @since 1.4
 */
public class UncertValue extends AbstractDistrictSBase implements SBaseWithUnit {

  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // TODO - implements units methods
  
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
   * Creates a new instance of {@link UncertValue}
   * 
   */
  public UncertValue() {
    super();
  }


  /**
   * Creates a new instance of {@link UncertValue}
   * 
   * @param level
   * @param version
   */
  public UncertValue(int level, int version) {
    super(level, version);
  }


  /**
   * Creates a new instance of {@link UncertValue}
   * 
   * @param sb
   */
  public UncertValue(UncertValue sb) {
    super(sb);

    if (sb.isSetValue()) {
      setValue(sb.getValue());
    }
    if (sb.isSetVar()) {
      setVar(sb.getVar());
    }
    if (sb.isSetUnits()) {
      setUnits(sb.getUnits());
    }
  }


  /**
   * Creates a new instance of {@link UncertValue}
   * 
   * @param id
   * @param level
   * @param version
   */
  public UncertValue(String id, int level, int version) {
    super(id, level, version);
  }


  /**
   * Creates a new instance of {@link UncertValue}
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public UncertValue(String id, String name, int level, int version) {
    super(id, name, level, version);
  }


  /**
   * Creates a new instance of {@link UncertValue}
   * 
   * @param id
   */
  public UncertValue(String id) {
    super(id);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public UncertValue clone() {
    return new UncertValue(this);
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


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3209;
    int result = super.hashCode();
    result = prime * result + ((units == null) ? 0 : units.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    result = prime * result + ((var == null) ? 0 : var.hashCode());
    return result;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    UncertValue other = (UncertValue) obj;
    if (units == null) {
      if (other.units != null) {
        return false;
      }
    } else if (!units.equals(other.units)) {
      return false;
    }
    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }
    if (var == null) {
      if (other.var != null) {
        return false;
      }
    } else if (!var.equals(other.var)) {
      return false;
    }
    return true;
  }
  
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.distrib.AbstractDistrictSBase#writeXMLAttributes()
   */
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetValue()) {
      attributes.put(DistribConstants.shortLabel + ":" + DistribConstants.value, value.toString());
    }
    if (isSetVar()) {
      attributes.put(DistribConstants.shortLabel + ":" + DistribConstants.var, getVar());
    }
    if (isSetUnits()) {
      attributes.put(DistribConstants.shortLabel + ":" + TreeNodeChangeEvent.units, getUnits());
    }
    
    return attributes;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(DistribConstants.value)) {
        setValue(StringTools.parseSBMLDouble(value));
      }
      else if (attributeName.equals(DistribConstants.var)) {
        setVar(value);
      }
      else if (attributeName.equals(TreeNodeChangeEvent.units)) {
        setUnits(value);
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }
}
