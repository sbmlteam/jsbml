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

import org.apache.log4j.Logger;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.AbstractConstraintDeclaration;
import org.sbml.jsbml.validator.offline.constraints.AnyConstraint;
import org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration;
import org.sbml.jsbml.validator.offline.constraints.ConstraintGroup;

public class ConstraintFactory {

  /**
   * Log4j logger
   */
  protected static final transient Logger logger =
    Logger.getLogger(ConstraintFactory.class);

  /**
   * Shared singleton instance
   */
  private static ConstraintFactory        instance;


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


  public <T> ConstraintGroup<T> getConstraintsForClass(Class<?> clazz,
    ValidationContext ctx) {

    return this.getConstraintsForClass(clazz, ctx.getCheckCategories(),
      ctx.getLevel(), ctx.getVersion());
  }


  /**
   * @param clazz
   * @param category
   * @param pkgs
   * @return
   */
  public <T> ConstraintGroup<T> getConstraintsForClass(Class<?> clazz,
    CHECK_CATEGORY[] categories, int level, int version) {

    ConstraintDeclaration declaration =
      AbstractConstraintDeclaration.getInstance(clazz.getSimpleName());

    return declaration.createConstraints(level, version, categories);
  }


  /**
   * @param attributeName
   * @param clazz
   * @param pkg
   * @param level
   * @param version
   * @return
   */
  public AnyConstraint<?> getConstraintsForAttribute(String attributeName,
    Class<?> clazz, int level, int version) {

    ConstraintDeclaration dec =
      AbstractConstraintDeclaration.getInstance(clazz.getSimpleName());

    return dec.createConstraints(level, version, attributeName);
  }

}
