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

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.factory.ConstraintFactory;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorCodes;

/**
 * Abstract mother class for all constraints declarations.
 * ***IMPORTANT***
 * Every constraints declarations must provide the empty constructor!!!
 * 
 * @author Roman
 * @since 1.2
 * @date 02.08.2016
 */
public abstract class AbstractConstraintDeclaration
implements ConstraintDeclaration, SBMLErrorCodes {

  private static HashMap<String, SoftReference<ConstraintDeclaration>> instances_ =
      new HashMap<String, SoftReference<ConstraintDeclaration>>();

  /**
   * Caches the constraints with SoftReferences
   */
  private static HashMap<Integer, SoftReference<AnyConstraint<?>>>     cache      =
      new HashMap<Integer, SoftReference<AnyConstraint<?>>>();

  /**
   * Stores class names which didn't have a constraint declaration
   */
  private static Set<String> classBlacklist = new HashSet<String>();
  
  /**
   * Log4j logger
   */
  protected static final transient Logger                              logger     =
      Logger.getLogger(AbstractConstraintDeclaration.class);


  public static ConstraintDeclaration getInstance(String clazz) {
    
    if (classBlacklist.contains(clazz))
    {
      return null;
    }
    
    SoftReference<ConstraintDeclaration> ref = instances_.get(clazz);
    ConstraintDeclaration declaration = null;

    // Tries to retrieve declaration from cache
    if (ref != null) {
      declaration = ref.get();
    }

    // Create a new instance via reflection
    if (declaration == null) {
      String className = clazz + "ConstraintsDeclaration";

      try {
        @SuppressWarnings("unchecked")
        Class<ConstraintDeclaration> c =
        (Class<ConstraintDeclaration>) Class.forName(
          "org.sbml.jsbml.validator.offline.constraints." + className);

        declaration = c.newInstance();
        instances_.put(clazz,
          new SoftReference<ConstraintDeclaration>(declaration));
      } catch (Exception e) {
        logger.debug("Couldn't find ConstraintsDeclaration: " + className);
        classBlacklist.add(clazz);
      }
    }

    return declaration;
  }


  @Override
  public <T> ConstraintGroup<T> createConstraints(int[] errorCodes) {
    ConstraintGroup<T> group = new ConstraintGroup<T>();

    for (int errorCode : errorCodes) {
      @SuppressWarnings("unchecked")
      AnyConstraint<T> c = (AnyConstraint<T>) this.createConstraint(errorCode);

      if (c != null) {
        group.add(c);
      }
    }

    return (group.getConstraintsCount() > 0) ? group : null;
  }


  @Override
  public <T> ConstraintGroup<T> createConstraints(int level, int version,
    CHECK_CATEGORY[] categories) {
    ConstraintGroup<T> group = new ConstraintGroup<T>();

    for (CHECK_CATEGORY cat : categories) {
      @SuppressWarnings("unchecked")
      AnyConstraint<T> c =
      (AnyConstraint<T>) this.createConstraints(level, version, cat);

      if (c != null) {
        group.add(c);
      }
    }

    return (group.getConstraintsCount() > 0) ? group : null;
  }


  @Override
  public <T> AnyConstraint<T> createConstraint(int errorCode) {

    @SuppressWarnings("unchecked")
    AnyConstraint<T> c = (AnyConstraint<T>) this.getFromCache(errorCode);

    if (c == null) {
      @SuppressWarnings("unchecked")
      ValidationFunction<T> func =
      (ValidationFunction<T>) getValidationFunction(errorCode);

      c = (func != null) ? new ValidationConstraint<T>(errorCode, func) : null;

      this.addToCache(errorCode, c);
    }

    return c;
  }


  protected void addRangeToSet(Set<Integer> set, int from, int to) {
    for (int i = from; i <= to; i++) {
      set.add(i);
    }
  }


  protected int[] convertToArray(Set<Integer> set) {
    int[] out = new int[set.size()];
    Iterator<Integer> iter = set.iterator();

    for (int i = 0; i < out.length; i++) {
      out[i] = iter.next().intValue();
    }

    return out;
  }


  protected AnyConstraint<?> getFromCache(int errorCode) {
    Integer key = new Integer(errorCode);
    SoftReference<AnyConstraint<?>> ref =
        AbstractConstraintDeclaration.cache.get(key);
    if (ref != null) {
      AnyConstraint<?> c = (ref.get());
      // If the constraint was cleared, the reference in the
      // HashMap can be removed.
      if (c == null) {
        AbstractConstraintDeclaration.cache.remove(key);
      }
      return c;
    }
    return null;
  }


  protected void addToCache(Integer errorCode, AnyConstraint<?> constraint) {

    if (constraint == null || errorCode == CoreSpecialErrorCodes.ID_DO_NOT_CACHE
        || errorCode == CoreSpecialErrorCodes.ID_GROUP) {
      return;
    }

    SoftReference<AnyConstraint<?>> ref =
        new SoftReference<AnyConstraint<?>>(constraint);

    AbstractConstraintDeclaration.cache.put(new Integer(errorCode), ref);
  }
}
