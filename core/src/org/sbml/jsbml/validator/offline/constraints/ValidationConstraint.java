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

import org.apache.log4j.Logger;
import org.sbml.jsbml.validator.offline.ValidationContext;

public class ValidationConstraint<T> extends AbstractConstraint<T> {

  protected static transient Logger logger =
      Logger.getLogger(ValidationConstraint.class);
  private ValidationFunction<T>     func;


  public ValidationConstraint(int id, ValidationFunction<T> func) {
    this.id = id;
    this.func = func;
  }


  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ValidationConstraint<?>) {
      return ((ValidationConstraint<?>) obj).getId() == this.id;
    }
    return false;
  }


  @Override
  public boolean check(ValidationContext context, T t) {
    // TODO Auto-generated method stub
    boolean passed = true;
    logger.debug("Validate constraint " + this.id);
    if (this.func != null) {
      passed = func.check(context, t);
    }
    context.didValidate(this, t, passed);

    return passed;
  }
}
