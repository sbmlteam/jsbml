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

import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.Species;

/**
 * This is a special {@link NameFilter} that allows users to search for a
 * {@link SimpleSpeciesReference} that refers to a {@link Species} with the
 * given identifier attribute. The boolean switch {@link #filterForSpecies} that
 * can be changed using the {@link #setFilterForSpecies(boolean)} method decides
 * whether this {@link SpeciesReferenceFilter} should use the given identifier
 * to filter for the actual {@link SimpleSpeciesReference} or for the referenced
 * {@link Species}.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class SpeciesReferenceFilter extends NameFilter {

  /**
   * Decides whether to filter for the identifier of the referenced
   * {@link Species} or if to use id and name to filter for the instance of
   * {@link SimpleSpeciesReference} itself.
   */
  private boolean filterForSpecies = false;

  /**
   * Creates a new {@link SpeciesReferenceFilter} with undefined properties.
   */
  public SpeciesReferenceFilter() {
    super();
  }

  /**
   * Creates a new {@link SpeciesReferenceFilter} that only accepts instances of
   * {@link SpeciesReference} pointing to the id of the given {@link Species}.
   * 
   * @param species
   *        the {@link Species} of interest.
   */
  public SpeciesReferenceFilter(Species species) {
    this(species.getId(), species.getName());
    setFilterForSpecies(true);
  }

  /**
   * @param id
   *        the identifier of a {@link Species} or {@link SpeciesReference} we
   *        are interested in. Whether we accept the id of a {@link Species} or
   *        a {@link SpeciesReference} depends on the flag that can be defined
   *        with {@link #setFilterForSpecies(boolean)}.
   * @see #setFilterForSpecies(boolean)
   */
  public SpeciesReferenceFilter(String id) {
    super(id);
  }

  /**
   * @param id
   *        the identifier of a {@link Species} or {@link SpeciesReference} we
   *        are interested in. Whether we accept the id of a {@link Species} or
   *        a {@link SpeciesReference} depends on the flag that can be defined
   *        with {@link #setFilterForSpecies(boolean)}.
   * @param name
   *        the name of the element we are interested in.
   * @see #setFilterForSpecies(boolean)
   */
  public SpeciesReferenceFilter(String id, String name) {
    super(id, name);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.filters.Filter#accepts(java.lang.Object)
   */
  @Override
  public boolean accepts(Object o) {
    if (!filterForSpecies) {
      return super.accepts(o);
    }
    if (o instanceof SimpleSpeciesReference) {
      SimpleSpeciesReference specRef = (SimpleSpeciesReference) o;
      if (specRef.isSetSpecies() && (id != null)
          && specRef.getSpecies().equals(id)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @return the filterForSpecies
   */
  public boolean isFilterForSpecies() {
    return filterForSpecies;
  }

  /**
   * @param filterForSpecies
   *            the filterForSpecies to set
   */
  public void setFilterForSpecies(boolean filterForSpecies) {
    this.filterForSpecies = filterForSpecies;
  }

}
