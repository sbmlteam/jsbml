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

import java.util.HashSet;
import java.util.Set;

/**
 * A {@link Filter} that accepts an item only if all of its internal filters
 * also accept the given item.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class AndFilter implements Filter {

  /**
   * A set of filters whose conditions have to be satisfied to accept an item.
   */
  private Set<Filter> filters;

  /**
   * 
   */
  public AndFilter() {
    setFilters(new HashSet<Filter>());
  }

  /**
   * 
   * @param filters
   */
  public AndFilter(Filter... filters) {
    this();
    for (Filter f : filters) {
      this.filters.add(f);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.Filter#accepts(java.lang.Object)
   */
  @Override
  public boolean accepts(Object o) {
    for (Filter f : filters) {
      if (!f.accepts(o)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 
   * @param filter
   * @return
   */
  public boolean addFilter(Filter filter) {
    return filters.add(filter);
  }

  /**
   * 
   * @return
   */
  public Set<Filter> getFilters() {
    return filters;
  }

  /**
   * 
   * @param filter
   * @return
   */
  public boolean removeFilter(Filter filter) {
    return filters.remove(filter);
  }

  /**
   * 
   * @param filters
   */
  public void setFilters(Set<Filter> filters) {
    this.filters = filters;
  }

}
