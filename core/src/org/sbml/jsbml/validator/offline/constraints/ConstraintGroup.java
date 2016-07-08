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

import java.util.ArrayList;
import java.util.List;

import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.factory.ConstraintFactory;

public class ConstraintGroup<T> extends AbstractConstraint<T> {

  private List<AnyConstraint<T>> constraints =
      new ArrayList<AnyConstraint<T>>();


  public ConstraintGroup() {
    super(CoreSpecialErrorCodes.ID_GROUP);
  }


  @Override
  public boolean check(ValidationContext context, T t) {
    context.willValidate(this, t);

    boolean success = true;
    for (AnyConstraint<T> c : this.constraints) {
      if (c != null) {
        success = c.check(context, t) && success;
      }
    }
    context.didValidate(this, t, success);

    return success;
  }


  /**
   * Adds a constraint to the group.
   * 
   * @param c
   */
  public void add(AnyConstraint<T> c) {

    if (c != null) {
      this.constraints.add(c);
    }
  }


  /**
   * Removes the constraint with the id from the group.
   * 
   * @param id
   * @return the constraint or null if the constraint didn't was in group
   */
  public AnyConstraint<T> remove(int id) {
    if (id == CoreSpecialErrorCodes.ID_DO_NOT_CACHE
        || id == CoreSpecialErrorCodes.ID_GROUP) {
      return null;
    }
    for (int i = 0; i < this.constraints.size(); i++) {
      AnyConstraint<T> con = this.constraints.get(i);
      if (con.getErrorCode() == id) {
        this.constraints.remove(i);
        return con;
      }
    }
    return null;
  }


  public AnyConstraint<T> removeAt(int i) {
    if (i < this.constraints.size()) {
      return this.constraints.remove(i);
    }
    return null;
  }


  /**
   * @param id
   * @return
   */
  public boolean contains(int id) {
    for (AnyConstraint<T> con : this.constraints) {
      if (con.getErrorCode() == id) {
        return true;
      }
    }
    return false;
  }


  /**
   * Returns the member of the group.
   * 
   * @return
   */
  public AnyConstraint<T>[] getConstraints() {
    @SuppressWarnings("unchecked")
    AnyConstraint<T>[] constraints =
    this.constraints.toArray(new AnyConstraint[this.constraints.size()]);
    return constraints;
  }
}
