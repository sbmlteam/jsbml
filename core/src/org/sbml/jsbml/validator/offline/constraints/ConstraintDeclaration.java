/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2016 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.validator.offline.constraints;

import java.util.Set;

import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;

/**
 * Basic interface for constraint declarations. If you want to provide
 * constraints for a custom class, consider to inherit from
 * {@link AbstractConstraintDeclaration} instead. This abstract class has
 * already a working implementation of most of the methods and supports caching
 * 
 * @see AbstractConstraintDeclaration
 * 
 * @author Roman
 * @since 1.2
 * @date 04.08.2016
 */
public interface ConstraintDeclaration {

  /**
   * Creates all the constraints which are needed to validate the categories
   * in the given level and version of SBML.
   * 
   * @param level
   * @param version
   * @param categories
   * @return A {@link ConstraintGroup} with at least 1 member or
   *         <code>null</code> if no constraint was loaded
   * @see #createConstraints(int, int, CHECK_CATEGORY)
   */
  abstract public <T> ConstraintGroup<T> createConstraints(int level,
    int version, CHECK_CATEGORY[] categories);


  /**
   * Creates all the constraints which are needed to validate this category
   * in the given level and version of SBML.
   * 
   * @param level
   * @param version
   * @param categories
   * @return A {@link ConstraintGroup} with at least 1 member or
   *         <code>null</code> if no constraint was loaded
   * @see #createConstraints(int, int, CHECK_CATEGORY[])
   */
  abstract public AnyConstraint<?> createConstraints(int level, int version,
    CHECK_CATEGORY category);


  /**
   * Creates all the constraints which are needed to validate the attribute
   * in the given level and version of SBML.
   * 
   * @param level
   * @param version
   * @param categories
   * @return A {@link ConstraintGroup} with at least 1 member or
   *         <code>null</code> if no constraint was loaded
   * @see #createConstraints(int, int, CHECK_CATEGORY[])
   */
  abstract public AnyConstraint<?> createConstraints(int level, int version,
    String attributeName);


  /**
   * Adds all error codes which are needed for a check in this category to the
   * set.
   * 
   * @param set
   * @param level
   * @param version
   * @param category
   */
  abstract public void addErrorCodesForCheck(Set<Integer> set, int level,
    int version, CHECK_CATEGORY category);


  /**
   * Adds all error codes which are needed for a check the attribute to the
   * set.
   * 
   * @param set
   * @param level
   * @param version
   * @param category
   */
  abstract public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName);


  /**
   * Tries to create the constraints with the given error codes
   * 
   * @param errorCodes
   * @return A {@link ConstraintGroup} with at least 1 member or
   *         <code>null</code> if no constraint was loaded
   * @see #createConstraint(int)
   */
  abstract public <T> ConstraintGroup<T> createConstraints(int[] errorCodes);


  /**
   * Tries to create the constraint with the given error code
   * 
   * @param errorCode
   * @return @link AnyConstraint} or
   *         <code>null</code> if no constraint was loaded
   * @see #createConstraint(int)
   */
  abstract public <T> AnyConstraint<T> createConstraint(int errorCode);
}
