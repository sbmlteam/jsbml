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

package org.sbml.jsbml.validator.offline.factory;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.AbstractConstraintDeclaration;
import org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration;
import org.sbml.jsbml.validator.offline.constraints.ConstraintGroup;

/**
 * Factory to be able to create {@link ConstraintGroup} to contains all constraints associated with
 * one class or one attribute in one class.
 * 
 * <p>We scan all the interfaces and all the superclass of the class in order to be sure to include
 * all the needed constraints. This factory is basically creating via reflection some {@link ConstraintDeclaration}
 * instances which it use to create the {@link ConstraintGroup}.</p>
 * 
 * @see AbstractConstraintDeclaration#getInstance(String)
 * @see ConstraintDeclaration#createConstraints(int, int, CHECK_CATEGORY[], ValidationContext)
 * @see ConstraintDeclaration#createConstraints(int, int, String, ValidationContext)
 * @see ConstraintDeclaration#addErrorCodesForCheck(Set, int, int, CHECK_CATEGORY, ValidationContext)
 * @see ConstraintDeclaration#addErrorCodesForAttribute(Set, int, int, String, ValidationContext)
 * @author Roman
 * @author rodrigue
 * @since 1.2
 */
public class ConstraintFactory {

  /**
   * Log4j logger
   */
  protected static final transient Logger logger = Logger.getLogger(ConstraintFactory.class);

  /**
   * Shared singleton instance
   */
  private static ConstraintFactory        instance;


  /**
   * Returns the {@link ConstraintFactory} singleton instance.
   * 
   * @return the {@link ConstraintFactory} singleton instance.
   */
  public static ConstraintFactory getInstance() {
    if (ConstraintFactory.instance == null) {
      ConstraintFactory.instance = new ConstraintFactory();
    }
    return ConstraintFactory.instance;
  }


  /**
   * Gets all the constraints for one class.
   * 
   * @param clazz the class to get constraints for
   * @param ctx the validation context
   * @return all the constraints for one class.
   */
  public <T> ConstraintGroup<T> getConstraintsForClass(Class<?> clazz, ValidationContext ctx) {

    return this.getConstraintsForClass(clazz, ctx.getCheckCategories(),
      ctx.getLevel(), ctx.getVersion(), ctx);
  }


  /**
   * Gets all the constraints for one class and some {@link CHECK_CATEGORY}s.
   * 
   * @param clazz the class to get constraints for
   * @param categories the array of categories to consider to create the constraints
   * @param level the SBML level
   * @param version the SBML version
   * @param context the validation context
   * @return all the constraints for one class and some {@link CHECK_CATEGORY}s.
   */
  public <T> ConstraintGroup<T> getConstraintsForClass(Class<?> clazz,
    CHECK_CATEGORY[] categories, int level, int version, ValidationContext context) 
  {
    Set<Class<?>> set = new HashSet<Class<?>>();

    return getConstraintsForClass(clazz, categories, level, version, set, context);
  }


  /**
   * Gets all the constraints for one class and some {@link CHECK_CATEGORY}s.
   * 
   * <p>Returns {@code null} if the class was already in the {@code collectedClasses} set.</p>
   * 
   * @param clazz the class to get constraints for
   * @param categories the set of categories to consider to create the constraints
   * @param level the SBML level
   * @param version the SBML version
   * @param collectedClasses the set of classes that we already created constraints for
   * @param context the validation context
   * @return all the constraints for one class and some {@link CHECK_CATEGORY}s, regrouped inside one {@link ConstraintGroup} 
   *         or null if the class was already in the {@code collectedClasses} set.
   */
  private <T> ConstraintGroup<T> getConstraintsForClass(Class<?> clazz,
    CHECK_CATEGORY[] categories, int level, int version, Set<Class<?>> collectedClasses, ValidationContext context) 
  {

    if (collectedClasses.contains(clazz)) {
      // Already collected
      return null;
    } else {
      collectedClasses.add(clazz);
    }

    ConstraintGroup<T> group = new ConstraintGroup<T>();
    
    // TODO - once we create one ConstraintGroup per Category, regroup the constraints of the different classes into the same CategoryConstraintGroup 

    for (Class<?> inf : clazz.getInterfaces()) {
      ConstraintGroup<T> c = getConstraintsForClass(inf, categories, level, version, collectedClasses, context);

      if (c != null) {
        group.add(c);
      } 
    }

    Class<?> superclass = clazz.getSuperclass();
    if (superclass != null) {

      ConstraintGroup<T> c = getConstraintsForClass(superclass, categories, level, version, collectedClasses, context);

      if (c != null) {
        group.add(c);
      } 
    }

    ConstraintDeclaration declaration = AbstractConstraintDeclaration.getInstance(clazz.getSimpleName());

    if (logger.isDebugEnabled()) {
      logger.debug("ConstraintFactory - trying to get the constraints for class '" + clazz.getSimpleName() + "'");
    }
    
    if (declaration != null) {

      ConstraintGroup<T> c = declaration.createConstraints(level, version, categories, context);
      group.add(c);
    }

    return (group.getConstraintsCount() > 0) ? group : null;
  }


  /**
   * Returns all constraints necessary to validate the attribute of the specified class. 
   * 
   * @param clazz the class to get constraints for
   * @param attributeName the attribute name to get constraint for
   * @param level the SBML level
   * @param version the SBML version
   * @param context the validation context
   * @return all constraints necessary to validate the attribute of the specified class. 
   */
  public <T> ConstraintGroup<T> getConstraintsForAttribute(Class<?> clazz, String attributeName,
     int level, int version, ValidationContext context) 
  {
    Set<Class<?>> set = new HashSet<Class<?>>();

    return getConstraintsForAttribute(clazz, attributeName, level, version, set, context);
  }
  
  /**
   * Returns all constraints necessary to validate the attribute of the specified class. 
   * 
   * <p>Returns {@code null} if the class was already in the {@code collectedClasses} set.</p> 
   * 
   * @param clazz the class to get constraints for
   * @param attributeName the attribute name to get constraint for
   * @param level the SBML level
   * @param version the SBML version
   * @param collectedClasses the set of classes that we already created constraints for
   * @param context the validation context
   * @return all constraints necessary to validate the attribute of the specified class. 
   *         or null if the class was already in the {@code collectedClasses} set.
   */
  public <T> ConstraintGroup<T> getConstraintsForAttribute(Class<?> clazz, String attributeName,
     int level, int version, Set<Class<?>> collectedClasses, ValidationContext context) 
  {
    if (collectedClasses.contains(clazz)) {
      // Already collected
      return null;
    } else {
      collectedClasses.add(clazz);
    }

    ConstraintGroup<T> group = new ConstraintGroup<T>();

    for (Class<?> inf : clazz.getInterfaces()) {
      ConstraintGroup<T> c = getConstraintsForAttribute(inf, attributeName, level, version, collectedClasses, context);

      group.add(c);
    }

    Class<?> superclass = clazz.getSuperclass();

    if (superclass != null) {

      ConstraintGroup<T> c = getConstraintsForAttribute(superclass, attributeName, level, version, collectedClasses, context);

      group.add(c);
    }

    ConstraintDeclaration declaration = AbstractConstraintDeclaration.getInstance(clazz.getSimpleName());

    if (declaration != null) {

      ConstraintGroup<T> c = declaration.createConstraints(level, version, attributeName, context);
      group.add(c);
    }

    return (group.getConstraintsCount() > 0) ? group : null;
  }

}
