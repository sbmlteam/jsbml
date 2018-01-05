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

package org.sbml.jsbml.validator.offline;

import org.sbml.jsbml.validator.offline.constraints.AnyConstraint;

/**
 * Tracks the progress of a the validation.
 * 
 * @see ValidationContext#addValidationListener(ValidationListener)
 * @author Roman
 * @since 1.2
 */
public interface ValidationListener {

  /**
   * Invoked before the constraint will validate the object.
   * 
   * @param ctx the context which controls the validation
   * @param c a constraint
   * @param o the object which will be checked
   */
  abstract public void willValidate(ValidationContext ctx, AnyConstraint<?> c, Object o);


  /**
   * Invoked after the constraint has validated the object.
   * 
   * @param ctx the context which controls the validation
   * @param c a constraint
   * @param o the object which was checked
   * @param success is {@code false} when the constraint was broken
   */
  abstract public void didValidate(ValidationContext ctx, AnyConstraint<?> c,
    Object o, boolean success);
}
