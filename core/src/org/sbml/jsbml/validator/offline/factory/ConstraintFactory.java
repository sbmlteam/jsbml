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

package org.sbml.jsbml.validator.offline.factory;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.validator.offline.SBMLPackage;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.AbstractConstraintList;
import org.sbml.jsbml.validator.offline.constraints.AnyConstraint;
import org.sbml.jsbml.validator.offline.constraints.ConstraintGroup;
import org.sbml.jsbml.validator.offline.constraints.CoreSpecialErrorCodes;

public class ConstraintFactory {

  /**
   * Log4j logger
   */
  protected static final transient Logger                          logger =
      Logger.getLogger(ConstraintFactory.class);

  /**
   * Caches the constraints with SoftReferences
   */
  private static HashMap<Integer, SoftReference<AnyConstraint<?>>> cache;
  private static ConstraintFactory                                 instance;


  /**
   * Returns a instance.
   * 
   * @return
   */
  public static ConstraintFactory getInstance() {
    if (ConstraintFactory.instance == null) {
      ConstraintFactory.instance = new ConstraintFactory();
    }
    return ConstraintFactory.instance;
  }


  protected ConstraintFactory() {
    if (ConstraintFactory.cache == null) {
      ConstraintFactory.cache =
          new HashMap<Integer, SoftReference<AnyConstraint<?>>>(24);
    }
  }


  /**
   * Returns a new constraint with the rule of the ID.
   * 
   * @param errorCode
   * @return
   */
  public static AnyConstraint<?> createConstraint(int errorCode) {
    String pkgName;
    // The id of special constraint are negative, in order to retrieve
    // the SBMLPackage, the absolut value is needed.
    SBMLPackage pkg = SBMLPackage.getPackageForError(Math.abs(errorCode));
    pkgName = StringTools.firstLetterUpperCase(pkg.toString());
    ConstraintBuilder b = AbstractConstraintBuilder.getInstance(pkgName);
    return (b != null) ? b.createConstraint(errorCode) : null;
  }


  /**
   * @param o
   * @param category
   * @return
   */
  public <T> ConstraintGroup<T> getConstraintsForClass(Class<?> clazz,
    CheckCategory category, SBMLPackage[] packages, int level, int version) {

    Set<Integer> set = AbstractConstraintList.getErrorCodesForClass(clazz,
      category, packages, level, version);

    Integer[] array = set.toArray(new Integer[set.size()]);

    ConstraintGroup<T> group = getConstraints(array);

    // Add constraints for interfaces
    for (Class<?> ifc : clazz.getInterfaces()) {
      AnyConstraint<T> con =
          this.getConstraintsForClass(ifc, category, packages, level, version);

      if (con != null) {
        if (group == null) {
          group = new ConstraintGroup<T>();
        }

        group.add(con);
      }

    }

    // constraints for the next superclass (if not java.lang.Object)
    if (!clazz.equals(Object.class)) {
      AnyConstraint<T> con = this.getConstraintsForClass(clazz.getSuperclass(),
        category, packages, level, version);

      if (con != null) {
        if (group == null) {
          group = new ConstraintGroup<T>();
        }

        group.add(con);
      }
    }

    return group;
  }


  public <T> ConstraintGroup<T> getConstraintsForClass(Class<?> clazz,
    ValidationContext ctx) {
    return this.getConstraintsForClass(clazz, ctx.getCheckCategories(),
      ctx.getPackages(), ctx.getLevel(), ctx.getVersion());
  }


  /**
   * @param clazz
   * @param category
   * @param pkgs
   * @return
   */
  public <T> ConstraintGroup<T> getConstraintsForClass(Class<?> clazz,
    CheckCategory[] categories, SBMLPackage[] packages, int level,
    int version) {

    ConstraintGroup<T> group = new ConstraintGroup<T>();
    // checks if package checking is enabled
    SBMLPackage[] pkgs = {SBMLPackage.CORE};
    for (CheckCategory cat : categories) {
      if (cat == CheckCategory.PACKAGE) {
        pkgs = packages;
        break;
      }
    }
    for (CheckCategory check : categories) {
      AnyConstraint<T> c =
          this.getConstraintsForClass(clazz, check, pkgs, level, version);
      group.add(c);
    }

    return group;
  }


  /**
   * Returns the constraint with the ID. The IDs of a constraint is
   * identically to the error code it will log on failure.
   * 
   * @param errorCode
   * @return
   */
  public AnyConstraint<?> getConstraint(int errorCode) {
    AnyConstraint<?> c = getConstraintFromCache(errorCode);
    if (c == null) {
      c = createConstraint(errorCode);
      this.addToCache(errorCode, c);
    }
    return c;
  }


  /**
   * Returns a {@link ConstraintGroup} with at least 1 member or <code>null</code> if
   * no constraints could be created.
   * 
   * @param errorCodes
   * @return A {@link ConstraintGroup} with all the constraints
   */
  public <T> ConstraintGroup<T> getConstraints(Integer[] errorCodes) {
    ConstraintGroup<T> group = null;

    for (int id : errorCodes) {
      @SuppressWarnings("unchecked")
      AnyConstraint<T> c = (AnyConstraint<T>) this.getConstraint(id);
      if (c != null) {
        // Init group if necassary
        if (group == null) {
          group = new ConstraintGroup<T>();
        }

        group.add(c);
      }
    }
    // Returns a group with at least 1 member or null
    return group;
  }


  /**
   * @param attributeName
   * @param clazz
   * @param pkg
   * @param level
   * @param version
   * @return
   */
  public <T> ConstraintGroup<T> getConstraintsForAttribute(String attributeName,
    Class<?> clazz, SBMLPackage pkg, int level, int version) {
    if (pkg == null) {
      pkg = SBMLPackage.CORE;
    }

    // Error codes for this class
    Set<Integer> errorCodes = AbstractConstraintList.getErrorCodesForAttribute(
      attributeName, clazz, pkg, level, version);

    Integer[] array = errorCodes.toArray(new Integer[errorCodes.size()]);
    ConstraintGroup<T> group = getConstraints(array);

    // Try all interfaces
    for (Class<?> iface : clazz.getInterfaces()) {
      ConstraintGroup<T> c =
          getConstraintsForAttribute(attributeName, iface, pkg, level, version);

      if (group == null) {
        group = c;
      } else {
        group.add(c);
      }
    }

    // If there's no rule for any interface of clazz, try his super clazz
    // clazz (if not Object)
    if (!clazz.equals(Object.class)) {
      ConstraintGroup<T> c = getConstraintsForAttribute(attributeName,
        clazz.getSuperclass(), pkg, level, version);

      if (group == null) {
        group = c;
      } else {
        group.add(c);
      }
    }

    return group;
  }


  private AnyConstraint<?> getConstraintFromCache(int errorCode) {
    Integer key = new Integer(errorCode);
    SoftReference<AnyConstraint<?>> ref = ConstraintFactory.cache.get(key);
    if (ref != null) {
      AnyConstraint<?> c = (ref.get());
      // If the constraint was cleared, the reference in the
      // HashMap can be removed.
      if (c == null) {
        ConstraintFactory.cache.remove(key);
      }
      return c;
    }
    return null;
  }


  private void addToCache(Integer id, AnyConstraint<?> constraint) {
    if (constraint == null || id == CoreSpecialErrorCodes.ID_DO_NOT_CACHE
        || id == CoreSpecialErrorCodes.ID_GROUP) {
      return;
    }
    SoftReference<AnyConstraint<?>> ref =
        new SoftReference<AnyConstraint<?>>(constraint);
    ConstraintFactory.cache.put(new Integer(id), ref);
  }
}
