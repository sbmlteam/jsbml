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

import org.sbml.jsbml.SBase;

/**
 * This filter only accepts instances of {@link SBase} with the metaid as
 * given in the constructor of this object.
 * 
 * @author rodrigue
 * @since 1.2
 */
public class MetaIdFilter implements Filter {

  /**
   * The desired identifier for SBases to be acceptable.
   */
  String metaid;

  /**
   * Creates a new instance of {@link MetaIdFilter}.
   */
  public MetaIdFilter() {
    this(null);
  }

  /**
   * Creates a new instance of {@link MetaIdFilter}.
   * 
   * @param metaid the metaid to search for
   */
  public MetaIdFilter(String metaid) {
    this.metaid = metaid;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.Filter#fulfilsProperty(java.lang.Object)
   */
  @Override
  public boolean accepts(Object o) {
    if (o instanceof SBase) {
      SBase sbase = (SBase) o;
      if (sbase.isSetMetaId() && (metaid != null) && sbase.getMetaId().equals(metaid)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the metaid that will be searched for by the filter.
   * 
   * @return the metaid that will be searched for by the filter.
   */
  public String getMetaId() {
    return metaid;
  }

  /**
   * Sets the metaid that will be searched for by the filter.
   * 
   * @param metaid the metaid to set
   */
  public void setMetaId(String metaid) {
    this.metaid = metaid;
  }

}
