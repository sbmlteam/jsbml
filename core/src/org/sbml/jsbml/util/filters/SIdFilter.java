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
import org.sbml.jsbml.UniqueSId;

/**
 * This filter only accepts instances of {@link SBase} that implements {@link UniqueSId}, 
 * with the id as given in the constructor of this object.
 * 
 * @author rodrigue
 * @since 1.2
 */
public class SIdFilter implements Filter {

  /**
   * The desired identifier for SBases to be acceptable.
   */
  String id;

  /**
   * Creates a new instance of {@link SIdFilter}.
   */
  public SIdFilter() {
    this(null);
  }

  /**
   * Creates a new instance of {@link SIdFilter}.
   * 
   * @param id the id to search for
   */
  public SIdFilter(String id) {
    this.id = id;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.Filter#fulfilsProperty(java.lang.Object)
   */
  @Override
  public boolean accepts(Object o) {
    if (o instanceof UniqueSId) {
      SBase sbase = (SBase) o;
      if (sbase.isSetId() && (id != null) && sbase.getId().equals(id)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the id that will be searched for by the filter.
   * 
   * @return the id that will be searched for by the filter.
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the id that will be searched for by the filter.
   * 
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

}
