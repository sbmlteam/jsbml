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

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorCodes;

/**
 * Abstract mother class for all constraints declarations.
 * 
 * <p>
 * ***IMPORTANT***
 * Every constraints declarations must provide the empty constructor!!!
 * </p>
 * 
 * @author Roman
 * @author rodrigue
 * @since 1.2
 */
public abstract class AbstractConstraintDeclaration
implements ConstraintDeclaration, SBMLErrorCodes {

  /**
   * Caches the {@link ConstraintDeclaration}s with SoftReferences
   */
  private static HashMap<String, SoftReference<ConstraintDeclaration>> instances     =
      new HashMap<String, SoftReference<ConstraintDeclaration>>();

  /**
   * Caches the constraints with SoftReferences
   */
  private static HashMap<String, SoftReference<AnyConstraint<?>>>     cache          =
      new HashMap<String, SoftReference<AnyConstraint<?>>>();

  /**
   * Stores class names which didn't have a constraint declaration
   */
  private static Set<String> classBlacklist = new HashSet<String>();

  /**
   * Log4j logger
   */
  protected static final transient Logger logger = Logger.getLogger(AbstractConstraintDeclaration.class);


  /**
   * Gets a {@link ConstraintDeclaration} corresponding to the given class name.
   * 
   * <p>A class has to exits in the package "org.sbml.jsbml.validator.offline.constraints"
   * and the class name has to be the given {@code className} + 'Constraints'.</p>
   * 
   * <p>If not corresponding constraint class is found, {@code null} will be returned
   * and the given {@code className} will be put into a black list so that we don't try
   * again to search a {@link ConstraintDeclaration} for this class.</p>
   * 
   * @param className a class name
   * @return a {@link ConstraintDeclaration} corresponding to the given class name or null.
   */
  public static ConstraintDeclaration getInstance(String className) {

    if (classBlacklist.contains(className)) {
      return null;
    }

    SoftReference<ConstraintDeclaration> ref = instances.get(className);
    ConstraintDeclaration declaration = null;

    // Tries to retrieve declaration from cache
    if (ref != null) {
      declaration = ref.get();
    }

    // Create a new instance via reflection
    if (declaration == null) {
      String constraintsClass = className + "Constraints";

      try {
        @SuppressWarnings("unchecked")
        Class<ConstraintDeclaration> c = (Class<ConstraintDeclaration>) Class.forName("org.sbml.jsbml.validator.offline.constraints." + constraintsClass);

        declaration = c.newInstance();
        instances.put(className, new SoftReference<ConstraintDeclaration>(declaration));
        
      } catch (Exception e) {
        if (logger.isDebugEnabled()) {
          logger.debug("Couldn't find ConstraintsDeclaration: " + constraintsClass);
        }
        classBlacklist.add(className);
      }
    }

    return declaration;
  }


  @Override
  public <T> ConstraintGroup<T> createConstraints(int[] errorCodes, ValidationContext context) {
    ConstraintGroup<T> group = new ConstraintGroup<T>();

    for (int errorCode : errorCodes) {
      @SuppressWarnings("unchecked")
      AnyConstraint<T> c = (AnyConstraint<T>) this.createConstraint(errorCode, context);

      if (c != null) {
        group.add(c);
      }
    }

    return (group.getConstraintsCount() > 0) ? group : null;
  }

  /**
   * Creates the constraints with the given error codes and regroup them into a {@link CategoryConstraintGroup}.
   * 
   * @param errorCodes an array of error codes
   * @param context the validation context
   * @return A {@link CategoryConstraintGroup} with at least 1 member or
   *         <code>null</code> if no constraint was loaded
   * @see #createConstraint(int, ValidationContext)
   */
  public <T> ConstraintGroup<T> createConstraints(int[] errorCodes, ValidationContext context, CHECK_CATEGORY category) {
    ConstraintGroup<T> group = new CategoryConstraintGroup<T>(category);

    for (int errorCode : errorCodes) {
      @SuppressWarnings("unchecked")
      AnyConstraint<T> c = (AnyConstraint<T>) this.createConstraint(errorCode, context);

      if (c != null) {
        group.add(c);
      }
    }

    return (group.getConstraintsCount() > 0) ? group : null;
  }


  @Override
  public AnyConstraint<?> createConstraints(int level, int version,
    CHECK_CATEGORY category, ValidationContext context) 
  {
    CHECK_CATEGORY[] cats = {category};
    return this.createConstraints(level, version, cats, context);
  }


  @Override
  public <T> ConstraintGroup<T> createConstraints(int level, int version,
    String attributeName, ValidationContext context) 
  {
    // No need to distinguish between check category for now for attribute
    
    Set<Integer> set = new HashSet<Integer>();

    this.addErrorCodesForAttribute(set, level, version, attributeName, context);

    int[] array = convertToArray(set);

    return this.createConstraints(array, context);
  }


  /* // code creating one CategoryConstraintGroup for each category but it is making the validation very, very slow. I am not yet sure why!
   
  @Override
  public <T> ConstraintGroup<T> createConstraints(int level, int version,
    CHECK_CATEGORY[] categories, ValidationContext context) 
  {
    ConstraintGroup<T> group = new ConstraintGroup<T>();
    Set<Integer> errorSet = new HashSet<Integer>();

    // we create one CategoryConstraintGroup per CHECK_CATEGORY, then create a global ConstraintGroup that contain the check category ConstraintGroups.    
    for (CHECK_CATEGORY cat : categories) {
      
      addErrorCodesForCheck(errorSet, level, version, cat, context);
      int[] errorArray = convertToArray(errorSet);
      
      ConstraintGroup<T> categoryGroup = createConstraints(errorArray, context, cat);
      
      if (categoryGroup != null) {
        group.add(categoryGroup);
      }
      
      // reset the error set for the next category
      errorSet.clear();
    }

    return (group.getConstraintsCount() > 0) ? group : null;
  }
   */

  
  //  Working code with only one level of ConstraintGroup
  @Override
  public <T> ConstraintGroup<T> createConstraints(int level, int version,
    CHECK_CATEGORY[] categories, ValidationContext context) 
  {
    Set<Integer> set = new HashSet<Integer>();

    for (CHECK_CATEGORY cat : categories) {
      this.addErrorCodesForCheck(set, level, version, cat, context);
    }
    int[] array = convertToArray(set);
    
    return this.createConstraints(array, context);
  }
      
      
  @Override
  public <T> AnyConstraint<T> createConstraint(int errorCode, ValidationContext context) {

    @SuppressWarnings("unchecked")
    AnyConstraint<T> c = (AnyConstraint<T>) this.getFromCache(errorCode);

    if (c == null) {
      @SuppressWarnings("unchecked")
      ValidationFunction<T> func = (ValidationFunction<T>) getValidationFunction(errorCode, context);

      c = (func != null) ? new ValidationConstraint<T>(errorCode, func) : null;

      this.addToCache(errorCode, c);
    }

    return c;
  }


  /**
   * Adds a range of integers to the given {@link Set}.
   * 
   * <p>Adds all the integers between {@code from} and {@code to}</p>
   * 
   * @param set the set where to add integers
   * @param from the first integer to be added 
   * @param to the last integer to be added
   */
  protected void addRangeToSet(Set<Integer> set, int from, int to) {
    for (int i = from; i <= to; i++) {
      set.add(i);
    }
  }


  /**
   * Converts the given {@link Set} into a java array.
   * 
   * @param set the set to convert
   * @return a java array that contains all the elements from the set.
   */
  protected int[] convertToArray(Set<Integer> set) {
    int[] out = new int[set.size()];
    Iterator<Integer> iter = set.iterator();

    for (int i = 0; i < out.length; i++) {
      out[i] = iter.next().intValue();
    }

    return out;
  }


  /**
   * Gets a constraint from the cache.
   * 
   * @param errorCode the error code
   * @return a constraint from the cache or null if a constraint is not found 
   * for the given error code or if the constraint was cleared from the cache 
   */
  protected AnyConstraint<?> getFromCache(int errorCode) {
    Integer key = new Integer(errorCode);
    String thisName = this.getClass().getSimpleName();
    String removeWord = "Constraints";
    
    String className = thisName.substring(0, thisName.length() - removeWord.length());

    
    SoftReference<AnyConstraint<?>> ref = AbstractConstraintDeclaration.cache.get(className + errorCode);
    
    if (ref != null) {
      AnyConstraint<?> c = (ref.get());
      // If the constraint was cleared, the reference in the
      // HashMap can be removed.
      if (c == null) {
        AbstractConstraintDeclaration.cache.remove(className + key); 
      }
      return c;
    }
    
    return null;
  }


  /**
   * Adds the given constraint to the cache.
   * 
   * <p>The name of the key in the cache is the class name
   * without 'Constraints' and the error code concatenated together.</p>
   * 
   * @param errorCode the error code
   * @param constraint the constraint to add to the cache
   */
  protected void addToCache(Integer errorCode, AnyConstraint<?> constraint) {

    if (constraint == null || errorCode < 0
        || errorCode == CoreSpecialErrorCodes.ID_GROUP) {
      return;
    }

    SoftReference<AnyConstraint<?>> ref = new SoftReference<AnyConstraint<?>>(constraint);
    
    String thisName = this.getClass().getSimpleName();
    String removeWord = "Constraints";
    
    String className = thisName.substring(0, thisName.length() - removeWord.length());
    
    AbstractConstraintDeclaration.cache.put(className + errorCode, ref);
  }
  
}
