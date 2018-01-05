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
 * This class represents the priority element in SBML.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * 
 */
public class Priority extends AbstractMathContainer implements UniqueSId {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 391689890391156873L;

  /**
   * 
   */
  public Priority() {
    super();
  }

  /**
   * @param math
   * @param level
   * @param version
   */
  public Priority(ASTNode math, int level, int version) {
    super(math, level, version);
    if (isSetLevel() && (getLevel() < 3)) {
      throw new IllegalArgumentException("Cannot create Priority element with Level < 3.");
    }
  }

  /**
   * @param level
   * @param version
   */
  public Priority(int level, int version) {
    super(level, version);
    if (isSetLevel() && (getLevel() < 3)) {
      throw new IllegalArgumentException("Cannot create Priority element with Level < 3.");
    }
  }

  /**
   * @param sb
   */
  public Priority(Priority sb) {
    super(sb);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#clone()
   */
  @Override
  public Priority clone() {
    return new Priority(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getParent()
   */
  @Override
  public Event getParent() {
    return (Event) super.getParent();
  }

}
