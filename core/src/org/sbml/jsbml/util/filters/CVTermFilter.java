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

import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.CVTerm.Qualifier;

/**
 * This filter accepts only instances of {@link CVTerm} with a certain content
 * or instances of {@link SBase} that are annotated with appropriate
 * {@link CVTerm} objects.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class CVTermFilter implements Filter {

  /**
   * 
   */
  private Qualifier qualifier;
  /**
   * 
   */
  private String pattern;

  /**
   * 
   */
  public CVTermFilter() {
    this(null, null);
  }

  /**
   * 
   * @param qualifier
   */
  public CVTermFilter(Qualifier qualifier) {
    this(qualifier, null);
  }

  /**
   * 
   * @param qualifier
   * @param pattern
   */
  public CVTermFilter(Qualifier qualifier, String pattern) {
    this.qualifier = qualifier;
    this.pattern = pattern;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.Filter#accepts(java.lang.Object)
   */
  @Override
  public boolean accepts(Object o) {
    if (o instanceof CVTerm) {
      CVTerm cvt = (CVTerm) o;
      if (qualifier != null) {
        if (cvt.isBiologicalQualifier()
            && (cvt.getBiologicalQualifierType() == qualifier)) {
          return pattern != null ? cvt.filterResources(pattern)
            .size() > 0 : true;
        } else if (cvt.isModelQualifier()
            && cvt.getModelQualifierType() == qualifier) {
          return pattern != null ? cvt.filterResources(pattern)
            .size() > 0 : true;
        }
      } else if (pattern != null) {
        return cvt.filterResources(pattern).size() > 0;
      }
    } else if (o instanceof SBase) {
      SBase sbase = (SBase) o;
      if (qualifier != null) {
        if (pattern != null) {
          if (sbase.filterCVTerms(qualifier, pattern).size() > 0) {
            return true;
          }
        } else if (sbase.filterCVTerms(qualifier).size() > 0) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * @return the pattern
   */
  public String getPattern() {
    return pattern;
  }

  /**
   * @return the qualifier
   */
  public Qualifier getQualifier() {
    return qualifier;
  }

  /**
   * @param pattern
   *            the pattern to set
   */
  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  /**
   * @param qualifier
   *            the qualifier to set
   */
  public void setQualifier(Qualifier qualifier) {
    this.qualifier = qualifier;
  }

}
