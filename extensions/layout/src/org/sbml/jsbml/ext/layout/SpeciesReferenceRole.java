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

import org.sbml.jsbml.SBO;

/**
 * Enumeration that defines the different SpeciesReferenceRoles that are encoded by
 * the {@link Layout} package.
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public enum SpeciesReferenceRole {
  /**
   * 
   */
  ACTIVATOR,
  /**
   * 
   */
  INHIBITOR,
  /**
   * 
   */
  MODIFIER,
  /**
   * 
   */
  PRODUCT,
  /**
   * 
   */
  SIDEPRODUCT,
  /**
   * 
   */
  SIDESUBSTRATE,
  /**
   * 
   */
  SUBSTRATE,
  /**
   * 
   */
  UNDEFINED;

  /**
   * 
   * @return
   */
  public int toSBOterm() {
    switch (this) {
    case ACTIVATOR:
      return SBO.getActivator(); // 459 = stimulator
    case INHIBITOR:
      return SBO.getInhibitor(); // 20 = inhibitor
    case MODIFIER:
      return SBO.getModifier(); // 19 = modifier
    case PRODUCT:
      return SBO.getProduct(); // 11 = product
    case SIDEPRODUCT:
      return SBO.getSideProduct(); // 603 = side product
    case SIDESUBSTRATE:
      return SBO.getSideSubstrate(); // 604 = side substrate
    case SUBSTRATE:
      return SBO.getReactant(); // 10 = reactant
    case UNDEFINED:
      return SBO.getParticipantRole(); // 3 = participant role
    default:
      return -1; // invalid
    }
  }

  /**
   * 
   * @param sboTerm
   * @return the {@link SpeciesReferenceRole} for the given SBO term
   *         identifier.
   */
  public static SpeciesReferenceRole valueOf(int sboTerm) {
    if (SBO.isChildOf(sboTerm, ACTIVATOR.toSBOterm())) {
      return ACTIVATOR;
    } else if (SBO.isChildOf(sboTerm, INHIBITOR.toSBOterm())) {
      return INHIBITOR;
    } else if (SBO.isChildOf(sboTerm, MODIFIER.toSBOterm())) {
      return MODIFIER;
    } else if (SBO.isChildOf(sboTerm, SIDEPRODUCT.toSBOterm())) {
      return SIDEPRODUCT;
    } else if (SBO.isChildOf(sboTerm, PRODUCT.toSBOterm())) {
      return PRODUCT;
    } else if (SBO.isChildOf(sboTerm, SIDESUBSTRATE.toSBOterm())) {
      return SIDESUBSTRATE;
    } else if (SBO.isChildOf(sboTerm, SUBSTRATE.toSBOterm())) {
      return SUBSTRATE;
    }
    return UNDEFINED;
  }

}
