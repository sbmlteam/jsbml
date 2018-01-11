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

import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * Defines a deletion operation to
 * be applied when a submodel instantiates a model definition.
 * 
 * <p>Deletions may be
 * useful in hierarchical model composition scenarios for various reasons. For
 * example, some components in a submodel may be redundant in the composed
 * model, perhaps because the same features are implemented in a different way
 * in the new model.</p>
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class Deletion extends AbstractNamedSBaseRef implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -568486978934971953L;

  /**
   * Creates an Deletion instance
   */
  public Deletion() {
    super();
    initDefaults();
  }

  /**
   * Creates a new {@link Deletion} instance with an id.
   * 
   * @param id the id
   */
  public Deletion(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a Deletion instance with a level and version.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public Deletion(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Creates a Deletion instance with an id, level, and version.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   */
  public Deletion(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a Deletion instance with an id, name, level, and version.
   * 
   * @param id the id
   * @param name the name
   * @param level the SBML level
   * @param version the SBML version
   */
  public Deletion(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(
      Integer.valueOf(CompConstants.MIN_SBML_LEVEL),
      Integer.valueOf(CompConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  /**
   * Clone constructor
   * 
   * @param obj the instance to clone
   */
  public Deletion(Deletion obj) {
    super(obj);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.comp.SBaseRef#clone()
   */
  @Override
  public Deletion clone() {
    return new Deletion(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = CompConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
  }

}
