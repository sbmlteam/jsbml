/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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
package org.sbml.jsbml.ext.qual;

/**
 * The transitionOutputEffect is an enumeration of values used to indicate the effect of an
 * {@link Output} {@link Transition} within the system. The possible values are production
 * and assignmentLevel.
 * 
 * @author Nicolas Rodriguez
 * @author Clemens Wrzodek
 * @since 1.0
 */
public enum OutputTransitionEffect {
  /**
   * The level of the {@link QualitativeSpecies} is set to the resultLevel of the
   * selected term.
   */
  assignmentLevel,
  /**
   * The level of the {@link QualitativeSpecies} is increased by the resultLevel of the
   * selected term possibly modified by the level of the {@link Output}.
   */
  production
}
