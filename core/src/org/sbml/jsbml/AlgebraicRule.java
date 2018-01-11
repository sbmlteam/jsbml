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

import org.sbml.jsbml.validator.ModelOverdeterminedException;
import org.sbml.jsbml.validator.OverdeterminationValidator;

/**
 * Represents the algebraicRule XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class AlgebraicRule extends Rule {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -3952858495318158175L;

  /**
   * Creates an AlgebraicRule instance.
   */
  public AlgebraicRule() {
    super();
  }

  /**
   * Creates an AlgebraicRule instance from a given {@link AlgebraicRule}
   * instance.
   * 
   * @param ar an AlgebraicRule to clone.
   */
  public AlgebraicRule(AlgebraicRule ar) {
    super(ar);
  }

  /**
   * Creates an AlgebraicRule instance from math, level and version.
   * 
   * @param math the math of the AlgebraicRule
   * @param level the SBML level
   * @param version the SBML version
   */
  public AlgebraicRule(ASTNode math, int level, int version) {
    super(math, level, version);
  }

  /**
   * Creates an AlgebraicRule instance from level and version.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public AlgebraicRule(int level, int version) {
    super(level, version);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.Rule#clone()
   */
  @Override
  public AlgebraicRule clone() {
    return new AlgebraicRule(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.Rule#isCompartmentVolume()
   */
  @Override
  public boolean isCompartmentVolume() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.Rule#isParameter()
   */
  @Override
  public boolean isParameter() {
    return false;
  }

  /**
   * Provides a convenient way to obtain the variable whose amount
   * is determined by this rule. However, you should better directly use the
   * {@link OverdeterminationValidator} instead of calling this method. Each
   * time you call this method, the bipartite matching between all
   * {@link MathContainer}s in the model and all
   * {@link NamedSBaseWithDerivedUnit}s will be executed again, leading to a
   * probably high computational effort.
   * 
   * An example of how to use the {@link OverdeterminationValidator} is given
   * here:
   * 
   * <pre class="brush:java">
   * int ruleIndex = 0; // some arbitrary algebraic rule
   * AlgebraicRule ar = model.getRule(ruleIndex);
   * OverdeterminationValidator odv = new OverdeterminationValidator(model);
   * Variable variable;
   * if (!odv.isOverdetermined()) {
   * 	variable = (Variable) odv.getMatching().get(ar);
   *  System.out.printf("Rule %d determines Variable %s.", ruleIndex, variable.getId());
   * }
   * </pre>
   * 
   * @return The {@link Variable} determined by this {@link AlgebraicRule}
   * @throws ModelOverdeterminedException
   *             if the model containing this {@link Rule} is over determined.
   * @throws NullPointerException
   *             Calling this method requires that this {@link Rule} is
   *             already part of a {@link Model}. If you just created a
   *             {@link Rule} and didn't add it to a {@link Model} you'll
   *             receive a {@link NullPointerException}.
   */
  public Variable getDerivedVariable() throws ModelOverdeterminedException {
    OverdeterminationValidator odv = new OverdeterminationValidator(
      getModel());
    if (odv.isOverdetermined()) {
      throw new ModelOverdeterminedException();
    }
    return (Variable) odv.getMatching().get(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.Rule#isSpeciesConcentration()
   */
  @Override
  public boolean isSpeciesConcentration() {
    return false;
  }

}
