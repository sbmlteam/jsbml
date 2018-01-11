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
 * Represents the assignmentRule XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @since 0.8
 */
public class AssignmentRule extends ExplicitRule {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 683172080347613789L;

  /**
   * Creates an AssignmentRule instance. By default, the variableID is {@code null}.
   */
  public AssignmentRule() {
    super();
  }

  /**
   * Creates a cloned AssignmentRule instance from a given AssignmentRule.
   * 
   * @param ar the AssignmentRule to clone.
   */
  public AssignmentRule(AssignmentRule ar) {
    super(ar);
  }

  /**
   * Creates a cloned AssignmentRule instance from a given {@link ExplicitRule}.
   * 
   * @param rule the AssignmentRule to clone.
   */
  public AssignmentRule(ExplicitRule rule) {
    super(rule);
  }

  /**
   * Creates an AssignmentRule instance from a given math, level and version.
   * 
   * @param math the math
   * @param level the SBML level
   * @param version the SBML version
   */
  public AssignmentRule(ASTNode math, int level, int version) {
    super(math, level, version);
  }

  /**
   * Creates an AssignmentRule instance from a given math and Parameter.
   * 
   * @param math the math
   * @param parameter the parameter
   */
  public AssignmentRule(ASTNode math, Parameter parameter) {
    this(parameter, math);
  }

  /**
   * Creates an AssignmentRule instance from a given math and {@link Variable}.
   * 
   * @param math the math
   * @param variable the variable
   */
  public AssignmentRule(ASTNode math, Variable variable) {
    this(variable, math);
  }

  /**
   * Creates an AssignmentRule instance with the given level and version.
   * 
   * @param level
   *            the SBML level
   * @param version
   *            the SBML version
   */
  public AssignmentRule(int level, int version) {
    super(level, version);
  }

  /**
   * Creates an AssignmentRule instance from a given Parameter.
   * 
   * @param parameter the parameter
   */
  public AssignmentRule(Parameter parameter) {
    super(parameter);
  }

  /**
   * Creates an AssignmentRule instance from a given math and Parameter.
   * 
   * @param parameter the parameter
   * @param math the math
   */
  public AssignmentRule(Parameter parameter, ASTNode math) {
    super(parameter, math);
  }

  /**
   * Creates an AssignmentRule instance from a given variable. Takes level and
   * version from the variable.
   * 
   * @param variable the {@link Variable}
   */
  public AssignmentRule(Variable variable) {
    super(variable);
  }

  /**
   * Creates an AssignmentRule instance from a given variable and math. Takes
   * level and version from the variable.
   * 
   * @param variable the {@link Variable}
   * @param math the math
   */
  public AssignmentRule(Variable variable, ASTNode math) {
    super(variable, math);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ExplicitRule#clone()
   */
  @Override
  public AssignmentRule clone() {
    return new AssignmentRule(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getElementName()
   */
  @Override
  public String getElementName() {
    if (getLevel() == 1) {
      if (isSpeciesConcentration()) {
        switch (getVersion()) {
        case 1:
          return "specieConcentrationRule";
        case 2:
          return "speciesConcentrationRule";
        default:
          break;
        }
      } else if (isCompartmentVolume()) {
        return "compartmentVolumeRule";
      } else if (isParameter()) {
        return "parameterRule";
      }
    }
    return super.getElementName();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ExplicitRule#isScalar()
   */
  @Override
  public boolean isScalar() {
    return true;
  }

}
