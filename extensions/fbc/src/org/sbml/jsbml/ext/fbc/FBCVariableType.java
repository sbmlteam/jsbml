/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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
package org.sbml.jsbml.ext.fbc;

/**
 * The type of a variable of a {@link FluxObjective} or a
 * user defined constraint component, introduced in FBC version 3: a
 * linear variable or a quadratic one (the product of two variables, or the
 * square of one).
 * 
 * @since 1.7
 */
public enum FBCVariableType {

  /**
   * A linear variable.
   */
  LINEAR("linear"),

  /**
   * A quadratic variable.
   */
  QUADRATIC("quadratic");

  /**
   * The value of the variable type in SBML.
   */
  private final String sbmlName;

  /**
   * @param sbmlName the value of the variable type in SBML.
   */
  private FBCVariableType(String sbmlName) {
    this.sbmlName = sbmlName;
  }

  /**
   * Returns the {@link FBCVariableType} of the given SBML value.
   * 
   * @param value the value of a {@code fbc:variableType} attribute,
   *        {@code linear} or {@code quadratic} (the case is ignored).
   * @return the {@link FBCVariableType} of the given value.
   * @throws IllegalArgumentException if the value is {@code null} or not a
   *         variable type.
   */
  public static FBCVariableType fromString(String value) {
    if (value != null) {
      for (FBCVariableType type : values()) {
        if (type.sbmlName.equalsIgnoreCase(value.trim())) {
          return type;
        }
      }
    }
    throw new IllegalArgumentException("Unknown fbc variable type: " + value);
  }

  /**
   * Returns the value of this variable type in SBML ({@code linear} or
   * {@code quadratic}).
   * 
   * @see java.lang.Enum#toString()
   */
  @Override
  public String toString() {
    return sbmlName;
  }

}
