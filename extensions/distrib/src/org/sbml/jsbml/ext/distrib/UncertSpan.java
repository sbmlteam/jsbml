/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2019 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The Babraham Institute, Cambridge, UK
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
import org.sbml.jsbml.util.StringTools;

/**
 * The {@link UncertSpan} class defines a span of values that define an uncertainty statistic such as confidence interval or
 * range. 
 * 
 * <p>It inherits from {@link UncertParameter}, and adds four optional attributes, varLower and varUpper, of type String, 
 * and valueLower and valueUpper, of type double. Exactly one of the attributes varLower and valueLower may be
 * defined, and exactly one of the attributes varUpper and valueUpper may be defined. If no attributes are defined,
 * the parameters of the span are undefined. If only one attribute is defined (one of the upper or lower attributes), that
 * aspect of the span is defined, and the other end is undefined. The span is fully defined if two attributes (one lower
 * and one upper) are defined.</p>
 * 
 * <p>The value of the lower attribute (whichever is defined) must be lesser or equal to the value of the upper attribute
 * (whichever is defined), at the initial conditions of the model. The Uncertainty element cannot affect the core
 * mathematics of an SBML model, but if it is used in a mathematical context during simulation of the model, this
 * restriction on the attribute values must be maintained, or the UncertSpan object as a whole will be undefined.
 * Like the units attribute on an UncertParameter , the units attribute is provided if valueUpper and/or valueLower
 * is defined. The units on both the upper and lower ends of the span must match each other, if defined. The units for
 * span ends defined by reference may be obtained from the referenced SBML element.</p>
 * 
 * @author rodrigue
 * @since 1.5
 */
public class UncertSpan extends UncertParameter {

  /**
   * 
   */
  private Double valueLower;
  
  /**
   * 
   */
  private Double valueUpper;
  
  /**
   * 
   */
  private String varLower;
  
  /**
   * 
   */
  private String varUpper;

  /**
   * Creates an UncertSpan instance 
   */
  public UncertSpan() {
    super();
    initDefaults();
  }

  /**
   * Creates a UncertSpan instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public UncertSpan(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a UncertSpan instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public UncertSpan(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Creates a UncertSpan instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public UncertSpan(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a UncertSpan instance with an id, name, level, and version.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public UncertSpan(String id, String name, int level, int version) {
    super(id, name, level, version);
    initDefaults();
  }

  /**
   * Clone constructor
   */
  public UncertSpan(UncertSpan obj) {
    super(obj);

    if (obj.isSetValueLower()) {
      setValueLower(obj.getValueLower());
    }
    if (obj.isSetValueUpper()) {
      setValueUpper(obj.getValueUpper());
    }
    if (obj.isSetVarLower()) {
      setVarLower(obj.getVarLower());
    }
    if (obj.isSetValueUpper()) {
      setVarUpper(obj.getVarUpper());
    }
    
  }

  /**
   * clones this class
   */
  public UncertSpan clone() {
    return new UncertSpan(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(DistribConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
    packageName = DistribConstants.shortLabel;
    setPackageVersion(-1);
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

    throw new PropertyUndefinedError(DistribConstants.valueLower, this);
  }

  /**
   * Returns whether {@link #valueLower} is set.
   *
   * @return whether {@link #valueLower} is set.
   */
  public boolean isSetValueLower() {
    return valueLower != null;
  }

  /**
   * Sets the value of valueLower
   *
   * @param valueLower the value of valueLower to be set.
   */
  public void setValueLower(double valueLower) {
    Double oldValueLower = this.valueLower;
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
      Double oldValueLower = this.valueLower;
      this.valueLower = null;
      firePropertyChange(DistribConstants.valueLower, oldValueLower, this.valueLower);
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

    throw new PropertyUndefinedError(DistribConstants.valueUpper, this);
  }

  /**
   * Returns whether {@link #valueUpper} is set.
   *
   * @return whether {@link #valueUpper} is set.
   */
  public boolean isSetValueUpper() {
    return valueUpper != null;
  }

  /**
   * Sets the value of valueUpper
   *
   * @param valueUpper the value of valueUpper to be set.
   */
  public void setValueUpper(double valueUpper) {
    Double oldValueUpper = this.valueUpper;
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
      Double oldValueUpper = this.valueUpper;
      this.valueUpper = null;
      firePropertyChange(DistribConstants.valueUpper, oldValueUpper, this.valueUpper);
      return true;
    }
    return false;
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
    
    return null;
  }

  /**
   * Returns whether {@link #varLower} is set.
   *
   * @return whether {@link #varLower} is set.
   */
  public boolean isSetVarLower() {
    return varLower != null;
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
   * Returns the value of {@link #varUpper}.
   *
   * @return the value of {@link #varUpper}.
   */
  public String getVarUpper() {
    if (isSetVarUpper()) {
      return varUpper;
    }
    
    return null;
  }

  /**
   * Returns whether {@link #varUpper} is set.
   *
   * @return whether {@link #varUpper} is set.
   */
  public boolean isSetVarUpper() {
    return varUpper != null;
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

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 839;
    int result = super.hashCode();
    result = prime * result
        + ((valueLower == null) ? 0 : valueLower.hashCode());
    result = prime * result
        + ((valueUpper == null) ? 0 : valueUpper.hashCode());
    result = prime * result + ((varLower == null) ? 0 : varLower.hashCode());
    result = prime * result + ((varUpper == null) ? 0 : varUpper.hashCode());
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
    UncertSpan other = (UncertSpan) obj;
    if (valueLower == null) {
      if (other.valueLower != null) {
        return false;
      }
    } else if (!valueLower.equals(other.valueLower)) {
      return false;
    }
    if (valueUpper == null) {
      if (other.valueUpper != null) {
        return false;
      }
    } else if (!valueUpper.equals(other.valueUpper)) {
      return false;
    }
    if (varLower == null) {
      if (other.varLower != null) {
        return false;
      }
    } else if (!varLower.equals(other.varLower)) {
      return false;
    }
    if (varUpper == null) {
      if (other.varUpper != null) {
        return false;
      }
    } else if (!varUpper.equals(other.varUpper)) {
      return false;
    }
    return true;
  }
  
  
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetValueLower()) {
      attributes.put(DistribConstants.shortLabel + ":" + DistribConstants.valueLower, valueLower.toString());
    }
    if (isSetValueUpper()) {
      attributes.put(DistribConstants.shortLabel + ":" + DistribConstants.valueUpper, valueUpper.toString());
    }
    if (isSetVarLower()) {
      attributes.put(DistribConstants.shortLabel + ":" + DistribConstants.varLower, varLower);
    }
    if (isSetVarUpper()) {
      attributes.put(DistribConstants.shortLabel + ":" + DistribConstants.varUpper, varUpper);
    }    
    
    return attributes;
  }

  @Override
  public boolean readAttribute(String attributeName, String prefix,
      String value) {

    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(DistribConstants.valueLower)) {
        setValueLower(StringTools.parseSBMLDouble(value));
      }
      else if (attributeName.equals(DistribConstants.valueUpper)) {
        setValueUpper(StringTools.parseSBMLDouble(value));
      }
      else if (attributeName.equals(DistribConstants.varUpper)) {
        setVarUpper(value);
      }
      else if (attributeName.equals(DistribConstants.varLower)) {
        setVarLower(value);
      }
      else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }
  
}
