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
package org.sbml.jsbml.ext.comp;

import org.sbml.jsbml.NamedSBase;

/**
 * This abstract class is used by Deletion and Port classes.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public abstract class AbstractNamedSBaseRef extends SBaseRef implements NamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -7590217205832827913L;

  /**
   * Creates an {@link AbstractNamedSBaseRef}. By default, id and name are {@code null}.
   */
  public AbstractNamedSBaseRef() {
    super();
    initDefaults();
  }

  /**
   * Creates an {@link AbstractNamedSBaseRef} from a given {@link AbstractNamedSBaseRef}.
   * 
   * @param nsb an {@code AbstractNamedSBase} object to clone
   */
  public AbstractNamedSBaseRef(AbstractNamedSBaseRef nsb) {
    super(nsb);
    initDefaults();
  }

  /**
   * Creates an {@link AbstractNamedSBaseRef} from a level and version. By default, id
   * and name are {@code null}.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public AbstractNamedSBaseRef(int level, int version) {
    this();
    setLevel(level);
    setVersion(version);
  }

  /**
   * Creates an {@link AbstractNamedSBaseRef} with the given identifier.
   * 
   * @param id the id of this {@code AbstractNamedSBase}
   */
  public AbstractNamedSBaseRef(String id) {
    this();
    setId(id);
  }

  /**
   * Creates an {@link AbstractNamedSBaseRef} from an id, level and version.
   * 
   * @param id the id of this {@code AbstractNamedSBase}
   * @param level the SBML level
   * @param version the SBML version
   */
  public AbstractNamedSBaseRef(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates an {@link AbstractNamedSBaseRef} from an id, name, level and version.
   * 
   * @param id the id of this {@code AbstractNamedSBase}
   * @param name the name of this {@code AbstractNamedSBase}
   * @param level the SBML level
   * @param version the SBML version
   */
  public AbstractNamedSBaseRef(String id, String name, int level, int version) {
    this(level, version);
    setId(id);
    setName(name);
  }

  /**
   * Initializes the default values using the namespace.
   */
  @Override
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = CompConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 829;
    int hashCode = super.hashCode();
    return prime * hashCode;
  }

}
