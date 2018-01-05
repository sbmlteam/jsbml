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
package org.sbml.jsbml;

/**
 * Represents the speciesType XML element of a SBML file. It is deprecated
 * since level 3 and not defined in SBML before Level 2 Version 2.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
 */
@Deprecated
public class SpeciesType extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 7341517738480127866L;

  /**
   * Creates a SpeciesType instance.
   */
  @Deprecated
  public SpeciesType() {
    super();
    initDefaults();
  }

  /**
   * Creates a SpeciesType instance from a level and version.
   * 
   * @param level
   * @param version
   */
  @Deprecated
  public SpeciesType(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Creates a SpeciesType instance from a given SpeciesType.
   * 
   * @param nsb
   */
  @Deprecated
  public SpeciesType(SpeciesType nsb) {
    super(nsb);
  }

  /**
   * 
   * @param id
   * @deprecated
   */
  @Deprecated
  public SpeciesType(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a SpeciesType instance from an id, level and version.
   * 
   * @param id
   * @param level
   * @param version
   */
  @Deprecated
  public SpeciesType(String id, int level, int version) {
    super(id, level, version);
    initDefaults();
  }

  /**
   * Creates a SpeciesType instance from an id, name, level and version.
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  @Deprecated
  public SpeciesType(String id, String name, int level, int version) {
    super(id, name, level, version);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  @Deprecated
  public SpeciesType clone() {
    return new SpeciesType(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getParent()
   */
  @SuppressWarnings("unchecked")
  @Override
  @Deprecated
  public ListOf<SpeciesType> getParent() {
    return (ListOf<SpeciesType>) super.getParent();
  }

  /**
   * Initializes the default settings of this class. In this case, it checks if
   * this type can actually be used in the defined SBML Level/Version
   * combination.
   */
  private void initDefaults() {
    if (isSetLevelAndVersion() &&
        ((getLevelAndVersion().compareTo(Integer.valueOf(2), Integer.valueOf(2)) < 0) ||
            (getLevelAndVersion().compareTo(Integer.valueOf(3), Integer.valueOf(1)) >= 0))) {
      throw new SBMLTypeUndefinedException(SpeciesType.class, getLevel(),
        getVersion());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  @Deprecated
  public boolean isIdMandatory() {
    return true;
  }

}
