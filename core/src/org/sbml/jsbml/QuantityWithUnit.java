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
package org.sbml.jsbml;

import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * This object represents an element with identifier and name, a value, and a
 * defined unit. In particular, this class defines methods to access and
 * manipulate the value and the unit properties of an element within a model.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @since 0.8
 */
public abstract class QuantityWithUnit extends AbstractNamedSBaseWithUnit
implements Quantity {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -9088772458214208160L;

  /**
   * a boolean to help knowing is the value as been set by the user or is the
   * default one.
   */
  private boolean isSetValue = false;
  /**
   * The size, initial amount or concentration, or the actual value of this
   * variable.
   */
  /*
   * Visibility modified to allow children classes to set a default value
   * without having the isSetValue returning true.
   */
  protected Double value = Double.NaN;

  /**
   * 
   */
  public QuantityWithUnit() {
    super();
  }

  /**
   * 
   * @param level
   * @param version
   */
  public QuantityWithUnit(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * 
   * @param qwdu
   */
  public QuantityWithUnit(QuantityWithUnit qwdu) {
    super(qwdu);
    if (qwdu.isSetValue()) {
      value = new Double(qwdu.getValue());
      isSetValue = true;
    } else {
      value = qwdu.value == null ? null : new Double(qwdu.value);
    }
  }

  /**
   * 
   * @param id
   */
  public QuantityWithUnit(String id) {
    this();
    setId(id);
  }

  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public QuantityWithUnit(String id, int level, int version) {
    super(id, null, level, version);
  }

  /**
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public QuantityWithUnit(String id, String name, int level,
    int version) {
    super(id, name, level, version);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#clone()
   */
  @Override
  public abstract QuantityWithUnit clone();

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      QuantityWithUnit v = (QuantityWithUnit) object;
      if (!(Double.isNaN(v.getValue()) && Double.isNaN(getValue()))) {
        equals &= v.getValue() == getValue();
      } else {
        equals &= (Double.isNaN(v.getValue()) && Double.isNaN(getValue()));
      }
    }
    return equals;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Quantity#getValue()
   */
  @Override
  public double getValue() {
    return value != null ? value : 0d;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 907;
    int hashCode = super.hashCode();
    if (isSetValue()) {
      hashCode += prime * value.hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Quantity#isSetValue()
   */
  @Override
  public boolean isSetValue() {
    return isSetValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Quantity#setValue(double)
   */
  @Override
  public void setValue(double value) {
    Double oldValue = this.value;
    this.value = value;
    isSetValue = true;
    if (value != oldValue) {
      firePropertyChange(TreeNodeChangeEvent.value, oldValue, value);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Quantity#unsetValue()
   */
  @Override
  public void unsetValue() {
    if (!Double.isNaN(value)) {
      Double oldValue = value;
      value = Double.NaN;
      isSetValue = false;
      firePropertyChange(TreeNodeChangeEvent.value, oldValue, value);
    }
  }

}
