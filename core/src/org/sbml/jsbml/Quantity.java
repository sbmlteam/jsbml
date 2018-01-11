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

/**
 * A quantity is an element that represents a value with an associated unit that
 * can be addressed through the identifier or name attribute of this element.
 * Both the value and the unit may be directly declared by the quantity or may
 * have to be derived.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @since 0.8
 */
public interface Quantity extends CallableSBase {

  /**
   * Returns the value of this {@link Quantity}.
   * 
   * In {@link Compartment}s the value is its size, in {@link Species} the
   * value defines its initial amount or concentration, and in
   * {@link Parameter}s and {@link LocalParameter}s this returns the value
   * attribute from SBML.
   * 
   * @return the value
   */
  public double getValue();

  /**
   * Returns {@code true} if the value of this {@link Quantity} is set.
   * 
   * @return {@code true} if the value of this {@link Quantity} is set.
   */
  public boolean isSetValue();

  /**
   * Sets the value of this {@link Quantity}.
   * 
   * Note that the meaning of the value can be different in all derived
   * classes. In {@link Compartment}s the value defines its size. In
   * {@link Species} the value describes either the initial amount or the
   * initial concentration. Only the class {@link Parameter} and
   * {@link LocalParameter} really define a value attribute with this name.
   * 
   * @param value
   *            the value to set
   */
  public void setValue(double value);

  /**
   * Unsets the value of this {@link Quantity}.
   * 
   */
  public void unsetValue();

}
