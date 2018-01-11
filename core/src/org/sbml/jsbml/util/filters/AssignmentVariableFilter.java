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

import org.sbml.jsbml.Assignment;

/**
 * This filter only accepts instances of {@link Assignment} with the variable as
 * given in the constructor of this object.
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class AssignmentVariableFilter implements Filter {

  /**
   * The desired identifier for NamedSBases to be acceptable.
   */
  String id;

  /**
   * 
   */
  public AssignmentVariableFilter() {
    this(null);
  }

  /**
   * 
   * @param id
   */
  public AssignmentVariableFilter(String id) {
    this.id = id;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.Filter#fulfilsProperty(java.lang.Object)
   */
  @Override
  public boolean accepts(Object o) {
    if (o instanceof Assignment) {
      Assignment er = (Assignment) o;
      if (er.isSetVariable() && (id != null) && er.getVariable().equals(id)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

}
