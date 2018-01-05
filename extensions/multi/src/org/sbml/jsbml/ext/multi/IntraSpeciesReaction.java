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
package org.sbml.jsbml.ext.multi;

import org.sbml.jsbml.Reaction;


/**
 *
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class IntraSpeciesReaction extends Reaction {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 7164677471117306392L;

  /**
   * Creates an IntraSpeciesReaction instance
   */
  public IntraSpeciesReaction() {
    super();
    initDefaults();
  }


  /**
   * Creates a IntraSpeciesReaction instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public IntraSpeciesReaction(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a IntraSpeciesReaction instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public IntraSpeciesReaction(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a IntraSpeciesReaction instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public IntraSpeciesReaction(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a IntraSpeciesReaction instance with an id, name, level, and version.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public IntraSpeciesReaction(String id, String name, int level, int version) {
    super(id, level, version);
    setName(name);
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public IntraSpeciesReaction(IntraSpeciesReaction obj) {
    super(obj);
  }


  /**
   * clones this class
   */
  @Override
  public IntraSpeciesReaction clone() {
    return new IntraSpeciesReaction(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  @Override
  public void initDefaults() {
    packageName = MultiConstants.shortLabel;
    setPackageVersion(-1);
  }

}
