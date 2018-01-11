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
 * This type represents an SBase object that is associated to a unit. This may
 * be a directly defined unit or a unit that has to be derived by evaluating
 * other elements within this object.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @since 0.8
 */
public interface SBaseWithDerivedUnit extends SBase {

  /**
   * Returns {@code true} or {@code false} depending on whether this
   * {@link SBaseWithDerivedUnit} refers to elements such as parameters or
   * numbers with undeclared units.
   * 
   * A return value of true indicates that the {@code UnitDefinition}
   * returned by {@link #getDerivedUnitDefinition()} may not accurately
   * represent the units of the expression.
   * 
   * @return {@code true} if the math expression of this {@link SBaseWithDerivedUnit}
   *         includes parameters/numbers with undeclared units,
   *         {@code false} otherwise.
   */
  public boolean containsUndeclaredUnits();

  /**
   * Derives the unit of this quantity and tries to identify an
   * equivalent {@link UnitDefinition} within the corresponding {@link Model}.
   * If no equivalent unit definition can be found, a new unit definition will
   * be created that is not part of the model but represents the unit of this
   * quantity. If it is not possible to derive a unit for this quantity, null
   * will be returned.
   * 
   * @return a {@link UnitDefinition} that represent the derived unit of this quantity, or null
   * if it is not possible to derive a unit.
   */
  public UnitDefinition getDerivedUnitDefinition();

  /**
   * Derives the unit of this quantity. If the model that contains
   * this quantity already contains a unit that is equivalent to the derived
   * unit, the corresponding identifier will be returned. In case that the
   * unit cannot be derived or that no equivalent unit exists within the
   * model, or if the model has not been defined yet, null will be returned.
   * In case that this quantity represents a basic {@link Unit.Kind} this
   * method will return the {@link String} representation of this
   * {@link Unit.Kind}.
   * 
   * @return  a {@link String} that represent the id of a {@link UnitDefinition}. This {@link UnitDefinition}
   * represent the derived unit of this quantity. If it is not possible to derive a unit for this quantity
   * or if no equivalent {@link UnitDefinition} can be found in the {@link Model}, null is returned.
   */
  public String getDerivedUnits();

}
