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
 * Contains the MathML expression of the stoichiometry.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @deprecated Since SBML Level 3 Version 1 use {@link AssignmentRule} with
 *             instances of {@link SpeciesReference} as {@link Variable}
 *             instead.
 */
@Deprecated
public class StoichiometryMath extends AbstractMathContainer {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -7070065639669486763L;

  /**
   * Creates a {@link StoichiometryMath} instance.
   */
  @Deprecated
  public StoichiometryMath() {
    super();
  }

  /**
   * Creates a {@link StoichiometryMath} instance from a level and version.
   * 
   * @param level
   * @param version
   */
  @Deprecated
  public StoichiometryMath(int level, int version) {
    super(level, version);
  }

  /**
   * Creates a {@link StoichiometryMath} instance from a given
   * {@link StoichiometryMath}.
   * 
   * @param stoichiometryMath
   */
  @Deprecated
  public StoichiometryMath(StoichiometryMath stoichiometryMath) {
    super(stoichiometryMath);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#clone()
   */
  @Override
  @Deprecated
  public StoichiometryMath clone() {
    return new StoichiometryMath(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getParent()
   */
  @Override
  @Deprecated
  public SpeciesReference getParent() {
    return (SpeciesReference) super.getParent();
  }

}
