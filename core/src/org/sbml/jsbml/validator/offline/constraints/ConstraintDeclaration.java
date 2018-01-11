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

package org.sbml.jsbml.validator.offline.constraints;

import java.util.Set;

import org.sbml.jsbml.SBMLError.SEVERITY;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;

/**
 * Interface for constraint declarations.
 * 
 * <p>If you want to provide
 * constraints for a custom class, consider to inherit from
 * {@link AbstractConstraintDeclaration} instead. This abstract class has
 * already a working implementation of most of the methods and supports caching.</p>
 * 
 * @see AbstractConstraintDeclaration
 * @author Roman
 * @author rodrigue
 * @since 1.2
 */
public interface ConstraintDeclaration {

  /**
   * Creates all the constraints which are needed to validate the categories
   * in the given level and version of SBML.
   * 
   * @param level the sbml level
   * @param version the sbml version
   * @param categories the check categories
   * @param context the validation context
   * @return A {@link ConstraintGroup} with at least 1 member or
   *         <code>null</code> if no constraint was loaded
   * @see #createConstraints(int, int, CHECK_CATEGORY, ValidationContext)
   */
  public <T> ConstraintGroup<T> createConstraints(int level,
    int version, CHECK_CATEGORY[] categories, ValidationContext context);


  /**
   * Creates all the constraints which are needed to validate this category
   * in the given level and version of SBML.
   * 
   * @param level the sbml level
   * @param version the sbml version
   * @param category the check category
   * @param context the validation context
   * @return A {@link ConstraintGroup} with at least 1 member or
   *         <code>null</code> if no constraint was loaded
   * @see #createConstraints(int, int, CHECK_CATEGORY[], ValidationContext)
   */
  public AnyConstraint<?> createConstraints(int level, int version,
    CHECK_CATEGORY category, ValidationContext context);


  /**
   * Creates all the constraints which are needed to validate the attribute
   * in the given level and version of SBML. This should only be error codes
   * which refer to a error with severities {@link SEVERITY#ERROR} or
   * {@link SEVERITY#FATAL}.
   * 
   * @param level the sbml level
   * @param version the sbml version
   * @param attributeName the attribute name
   * @param context the validation context
   * @return A {@link ConstraintGroup} with at least 1 member or
   *         <code>null</code> if no constraint was loaded
   * @see #createConstraints(int, int, CHECK_CATEGORY[], ValidationContext)
   */
  public <T> ConstraintGroup<T> createConstraints(int level,
    int version, String attributeName, ValidationContext context);


  /**
   * Adds all error codes which are needed for a check in this category to the
   * set.
   * 
   * @param set the {@link Set} of error codes for the given category.
   * @param level the sbml level
   * @param version the sbml version
   * @param category the category to check
   * @param context the validation context
   */
  public void addErrorCodesForCheck(Set<Integer> set, int level,
    int version, CHECK_CATEGORY category, ValidationContext context);

  /**
   * Adds all error codes which are needed for a check the attribute to the
   * set.
   * 
   * @param set the {@link Set} of error codes for the given category.
   * @param level the sbml level
   * @param version the sbml version
   * @param attributeName the attribute name
   * @param context the validation context
   */
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context);

  /**
   * Creates the constraints with the given error codes.
   * 
   * @param errorCodes an array of error codes
   * @param context the validation context
   * @return A {@link ConstraintGroup} with at least 1 member or
   *         <code>null</code> if no constraint was loaded
   * @see #createConstraint(int, ValidationContext)
   */
  public <T> ConstraintGroup<T> createConstraints(int[] errorCodes, ValidationContext context);


  /**
   * Creates the constraint with the given error code.
   * 
   * @param errorCode the error code
   * @param context the validation context
   * @return @link AnyConstraint} or
   *         <code>null</code> if no constraint was loaded
   */
  public <T> AnyConstraint<T> createConstraint(int errorCode, ValidationContext context);
  
  /**
   * Returns the {@link ValidationFunction} of the error code, if it's defined
   * in this {@link ConstraintDeclaration}.
   * 
   * @param errorCode the error code
   * @param context the validation context
   * @return the {@link ValidationFunction} or <code>null</code> if not defined
   *         in this {@link ConstraintDeclaration}
   */
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context);

}
