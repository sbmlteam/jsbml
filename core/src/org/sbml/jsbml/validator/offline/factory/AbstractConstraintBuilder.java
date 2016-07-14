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

import org.apache.log4j.Logger;
import org.sbml.jsbml.validator.offline.constraints.ValidationConstraint;

public abstract class AbstractConstraintBuilder
implements ConstraintBuilder, SBMLErrorCodes {

  private static HashMap<String, SoftReference<ConstraintBuilder>> instances_ =
      new HashMap<String, SoftReference<ConstraintBuilder>>();
  /**
   * Log4j logger
   */
  protected static final transient Logger                          logger     =
      Logger.getLogger(AbstractConstraintBuilder.class);


  public static ConstraintBuilder getInstance(String pkgName) {
    SoftReference<ConstraintBuilder> ref = instances_.get(pkgName);
    ConstraintBuilder builder = null;
    if (ref != null) {
      builder = ref.get();
    }
    if (builder == null) {
      String className = pkgName + "ConstraintBuilder";
      try {
        @SuppressWarnings("unchecked")
        Class<ConstraintBuilder> c = (Class<ConstraintBuilder>) Class.forName(
          "org.sbml.jsbml.validator.offline.constraints." + className);
        builder = c.newInstance();
        instances_.put(pkgName, new SoftReference<ConstraintBuilder>(builder));
      } catch (Exception e) {
        logger.warn("Couldn't find ConstraintBuilder: " + className);
      }
    }
    return builder;
  }
}
