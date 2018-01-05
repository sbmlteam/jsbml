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
 * Represents the modifierSpeciesReference XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 */
public class ModifierSpeciesReference extends SimpleSpeciesReference {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 6033910247622532704L;

  /**
   * Creates a {@link ModifierSpeciesReference} instance.
   */
  public ModifierSpeciesReference() {
    super();
  }

  /**
   * Creates a {@link ModifierSpeciesReference} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public ModifierSpeciesReference(int level, int version) {
    super(level, version);
  }

  /**
   * Creates a {@link ModifierSpeciesReference} instance from a given
   * {@link ModifierSpeciesReference}.
   * 
   * @param modifierSpeciesReference
   */
  public ModifierSpeciesReference(
    ModifierSpeciesReference modifierSpeciesReference) {
    super(modifierSpeciesReference);
  }

  /**
   * Creates a {@link ModifierSpeciesReference} instance from a given {@link Species}.
   * 
   * @param species
   */
  public ModifierSpeciesReference(Species species) {
    super(species);
  }

  /**
   * Creates a {@link ModifierSpeciesReference} instance.
   * 
   * @param id the modifier SId
   */
  public ModifierSpeciesReference(String id) {
    super(id);
  }

  /**
   * Creates a {@link ModifierSpeciesReference} instance.
   * 
   * @param id the modifier SId
   * @param level the SBML level
   * @param version the SBML version
   */
  public ModifierSpeciesReference(String id, int level, int version) {
    super(id, level, version);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#clone()
   */
  @Override
  public ModifierSpeciesReference clone() {
    return new ModifierSpeciesReference(this);
  }

}
