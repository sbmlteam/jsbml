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
 * The base class for the {@link AlgebraicRule}, {@link RateRule},
 * {@link AssignmentRule}.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * 
 */
public abstract class Rule extends AbstractMathContainer implements UniqueSId {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -8151628772496225902L;

  /**
   * Creates a Rule instance.
   */
  public Rule() {
    super();
  }

  /**
   * Creates a Rule instance from an id, level and version.
   * 
   * @param math
   * @param level
   * @param version
   */
  public Rule(ASTNode math, int level, int version) {
    super(math, level, version);
  }

  /**
   * Creates a Rule instance from a level and version.
   * 
   * @param level
   * @param version
   */
  public Rule(int level, int version) {
    super(level, version);
  }

  /**
   * Creates a new {@link Rule} instance from a given {@link Rule}.
   * 
   * @param sb
   */
  public Rule(Rule sb) {
    super(sb);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#clone()
   */
  @Override
  public abstract Rule clone();

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getParent()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ListOf<Rule> getParent() {
    return (ListOf<Rule>) super.getParent();
  }

  /**
   * 
   * @return {@code true} if this Rule is an {@link AlgebraicRule} instance.
   */
  public boolean isAlgebraic() {
    return this instanceof AlgebraicRule;
  }

  /**
   * 
   * @return {@code true} if this {@link Rule} is an {@link AssignmentRule}
   * instance.
   */
  public boolean isAssignment() {
    return this instanceof AssignmentRule;
  }

  /**
   * (SBML Level 1 only) Predicate returning true or false depending on
   * whether this Rule is an CompartmentVolumeRule.
   * 
   * @return {@code true} if this Rule is a CompartmentVolumeRule, {@code false} otherwise.
   */
  public abstract boolean isCompartmentVolume();

  /**
   * (SBML Level 1 only) Predicate returning true or false depending on
   * whether this Rule is an ParameterRule.
   * 
   * @return {@code true} if this Rule is a ParameterRule, {@code false} otherwise.
   */
  public abstract boolean isParameter();

  /**
   * 
   * @return {@code true} if this {@link Rule} is a {@link RateRule} instance.
   */
  public boolean isRate() {
    return this instanceof RateRule;
  }

  /**
   * (SBML Level 1 only) Predicate returning {@code true} or {@code false} depending on
   * whether this {@link Rule} is an SpeciesConcentrationRule.
   * 
   * @return {@code true} if this Rule is a SpeciesConcentrationRule, {@code false} otherwise.
   */
  public abstract boolean isSpeciesConcentration();

}
