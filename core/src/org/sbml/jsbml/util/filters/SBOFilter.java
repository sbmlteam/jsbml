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
package org.sbml.jsbml.util.filters;

import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SBase;

/**
 * A {@link Filter} that accepts only instances of {@link SBase} whose SBO term
 * field is set to a certain value of interest.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class SBOFilter implements Filter {

  /**
   * The SBO term of interest.
   */
  private int terms[];

  /**
   * Generates a new {@link Filter} for SBO terms but with an invalid SBO term
   * as filter criterion.
   */
  public SBOFilter() {
    this(null);
  }

  /**
   * Creates an SBO term filter with the given term as filter criterion.
   * 
   * @param terms
   *            The terms of interest.
   */
  public SBOFilter(int... terms) {
    this.terms = terms;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.Filter#accepts(java.lang.Object)
   */
  @Override
  public boolean accepts(Object o) {
    if ((terms != null) && (terms.length > 0) && (o instanceof SBase)) {
      SBase sbase = (SBase) o;
      for (int i = 0; sbase.isSetSBOTerm() && (i < terms.length); i++) {
        if (SBO.isChildOf(sbase.getSBOTerm(), terms[i])) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * @return the terms
   */
  public int[] getTerms() {
    return terms;
  }

  /**
   * @param terms
   *            the terms to set
   */
  public void setTerms(int... terms) {
    this.terms = terms;
  }

}
