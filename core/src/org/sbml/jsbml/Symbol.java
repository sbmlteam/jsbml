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
 * The base class for {@link Compartment}, {@link Species}, {@link Parameter}. A
 * symbol is a {@link Variable} of the system that declares a unit and a value,
 * i.e., neither its {@link Unit} or {@link UnitDefinition}, nor its value are
 * derived. A Symbol defines both fields. Furthermore, a Symbol may vary through
 * a simulation and can be accessed within the model through its identifier and
 * name attributes.
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @author Nicolas Rodriguez
 * @since 0.8
 */
public abstract class Symbol extends QuantityWithUnit implements
Variable {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 3061467418198640109L;

  /**
   * The constant attribute of this variable.
   */
  protected Boolean constant;

  /**
   * 
   */
  protected boolean isSetConstant = false;

  /**
   * Creates a Symbol instance. By default, value, unitsID, constant are {@code null}.
   */
  public Symbol() {
    super();
    constant = null;
  }

  /**
   * Creates a Symbol instance from a level and version. By default, value,
   * unitsID, constant are {@code null}.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public Symbol(int level, int version) {
    super(level, version);
    constant = null;
  }

  /**
   * Creates a Symbol instance.
   * 
   * @param quantity
   */
  public Symbol(QuantityWithUnit quantity) {
    super(quantity);
    constant = null;
  }

  /**
   * Creates a Symbol instance.
   * 
   * @param id
   */
  public Symbol(String id) {
    this();
    setId(id);
  }

  /**
   * Creates a Symbol instance from an id, level and version. By default,
   * value, unitsID, constant are {@code null}.
   * 
   * @param id
   * @param level
   * @param version
   */
  public Symbol(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a Symbol instance from an id, name, level and version. By
   * default, value, unitsID, constant are {@code null}.
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public Symbol(String id, String name, int level, int version) {
    super(id, name, level, version);
    constant = null;
  }

  /**
   * Creates a Symbol instance from a given Symbol.
   * 
   * @param nsb
   */
  public Symbol(Symbol nsb) {
    super(nsb);
    if (nsb.isSetConstant()) {
      setConstant(nsb.isConstant());
    } else {
      constant = nsb.constant == null ? null : new Boolean(nsb.constant);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.QuantityWithUnit#clone()
   */
  @Override
  public abstract Symbol clone();

  /* (non-Javadoc)
   * @see org.sbml.jsbml.QuantityWithUnit#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object o) {
    boolean equals = super.equals(o);
    if (equals) {
      Symbol v = (Symbol) o;
      equals &= v.isSetConstant() == isSetConstant();
      if (v.isSetConstant() && isSetConstant()) {
        equals &= v.isConstant() == isConstant();
      }
    }
    return equals;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Variable#getConstant()
   */
  @Override
  public boolean getConstant() {
    return isConstant();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.QuantityWithUnit#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 929;
    int hashCode = super.hashCode();
    if (isSetConstant()) {
      hashCode += prime * constant.hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Variable#isConstant()
   */
  @Override
  public boolean isConstant() {
    /*
     * Cannot use the method isSetConstant here as for level 2 we put a
     * value in constant but without using the setConstant method, so the
     * boolean isSetConstant is false and the value set, corresponding to
     * the default value in the specs, is never returned.
     */
    // TODO: check if they are some other cases like that !!
    return constant != null ? constant.booleanValue() : false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Variable#isSetConstant()
   */
  @Override
  public boolean isSetConstant() {
    return isSetConstant;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Variable#setConstant(boolean)
   */
  @Override
  public void setConstant(boolean constant) {
    if (getLevel() == 1) {
      throw new PropertyNotAvailableException(TreeNodeChangeEvent.constant, this);
    }
    Boolean oldConstant = this.constant;
    this.constant = Boolean.valueOf(constant);
    isSetConstant = true;
    firePropertyChange(TreeNodeChangeEvent.constant, oldConstant, constant);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Variable#unsetConstant()
   */
  @Override
  public void unsetConstant() {
    Boolean oldConstant = constant;
    constant = null;
    isSetConstant = false;
    firePropertyChange(TreeNodeChangeEvent.constant, oldConstant, constant);
  }

}
