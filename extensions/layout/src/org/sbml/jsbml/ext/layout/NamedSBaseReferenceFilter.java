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
package org.sbml.jsbml.ext.layout;

import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.util.filters.NameFilter;
import org.sbml.jsbml.util.filters.SpeciesReferenceFilter;

/**
 * This is a special {@link NameFilter} that allows users to search for a
 * {@link AbstractReferenceGlyph} that refers to a {@link NamedSBase} with the
 * given identifier attribute. The boolean switch {@link #filterForReference} that
 * can be changed using the {@link #setFilterForReference(boolean)} method decides
 * whether this {@link NamedSBaseReferenceFilter} should use the given identifier
 * to filter for the actual {@link AbstractReferenceGlyph} or for the referenced
 * {@link NamedSBase}.
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class NamedSBaseReferenceFilter extends NameFilter {

  /**
   * Decides whether to filter for the identifier of the referenced
   * {@link NamedSBase} or if to use id and name to filter for the instance of
   * {@link AbstractReferenceGlyph} itself.
   */
  private boolean filterForReference = false;

  /**
   * Creates a new {@link SpeciesReferenceFilter} with undefined properties.
   */
  public NamedSBaseReferenceFilter() {
    super();
  }

  /**
   * Creates a new {@link NamedSBaseReferenceFilter} that only accepts instances of
   * {@link AbstractReferenceGlyph} pointing to the id of the given {@link NamedSBase}.
   * 
   * @param glyph
   *        the {@link NamedSBase} of interest.
   */
  public NamedSBaseReferenceFilter(NamedSBase glyph) {
    this(glyph.getId(), glyph.getName());
    setFilterForReference(true);
  }

  /**
   * @param id
   *        the identifier of a {@link NamedSBase} or {@link AbstractReferenceGlyph} we
   *        are interested in. Whether we accept the id of a {@link NamedSBase} or
   *        a {@link AbstractReferenceGlyph} depends on the flag that can be defined
   *        with {@link #setFilterForReference(boolean)}.
   * @see #setFilterForReference(boolean)
   */
  public NamedSBaseReferenceFilter(String id) {
    super(id);
  }

  /**
   * @param id
   *        the identifier of a {@link NamedSBase} or {@link AbstractReferenceGlyph} we
   *        are interested in. Whether we accept the id of a {@link NamedSBase} or
   *        a {@link AbstractReferenceGlyph} depends on the flag that can be defined
   *        with {@link #setFilterForReference(boolean)}.
   * @param name
   *        the name of the element we are interested in.
   * @see #setFilterForReference(boolean)
   */
  public NamedSBaseReferenceFilter(String id, String name) {
    super(id, name);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.filters.Filter#accepts(java.lang.Object)
   */
  @Override
  public boolean accepts(Object o) {
    if (!filterForReference) {
      return super.accepts(o);
    }
    if (o instanceof AbstractReferenceGlyph) {
      AbstractReferenceGlyph specRef = (AbstractReferenceGlyph) o;
      String id = getId();
      if (specRef.isSetReference() && (id != null)
          && specRef.getReference().equals(id)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @return the filterForSpecies
   */
  public boolean isFilterForSpecies() {
    return filterForReference;
  }

  /**
   * @param filterForSpecies
   *            the filterForSpecies to set
   */
  public void setFilterForReference(boolean filterForSpecies) {
    filterForReference = filterForSpecies;
  }

}
