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
 * This filter only accepts instances of {@link SBase} with the id or name as
 * given in the constructor of this object.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class NameFilter implements Filter {

  /**
   * The desired identifier.
   */
  String id;
  /**
   * The desired name.
   */
  String name;

  /**
   * Creates a new {@link NameFilter} instance
   */
  public NameFilter() {
    this(null, null);
  }

  /**
   * Creates a new {@link NameFilter} instance with the given id to search for.
   * 
   * @param id the id to search for
   */
  public NameFilter(String id) {
    this(id, null);
  }

  /**
   * Creates a new {@link NameFilter} instance with the given id and/or name to search for.
   * 
   * @param id the id to search for (can be null, then we search only for name).
   * @param name the name to search for (can be null, then we search only for id)
   */
  public NameFilter(String id, String name) {
    this.id = id;
    this.name = name;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.Filter#fulfilsProperty(java.lang.Object)
   */
  @Override
  public boolean accepts(Object o) {
    if (o instanceof SBase) {
      SBase sbase = (SBase) o;
      
      if (sbase.isSetId() && (id != null) && sbase.getId().equals(id)) {
        return true;
      }
      if (sbase.isSetName() && (name != null) && sbase.getName().equals(name)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the id.
   * 
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * Returns the name.
   * 
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the id to search for.
   * 
   * @param id the id to set
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Sets the name to search for.
   * 
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

}
