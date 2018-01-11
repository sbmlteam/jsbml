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

import java.util.HashSet;
import java.util.Set;

import org.sbml.jsbml.validator.offline.ValidationContext;

/**
 * Groups together a set of constraints.
 * 
 * @author Roman
 *
 * @param <T> the type of object to check
 * @since 1.2
 */
public class ConstraintGroup<T> extends AbstractConstraint<T> {

  /**
   * 
   */
  private Set<AnyConstraint<T>> constraints = new HashSet<AnyConstraint<T>>();


  /**
   * Creates a new {@link ConstraintGroup} instance.
   */
  public ConstraintGroup() {
    super(CoreSpecialErrorCodes.ID_GROUP);
  }


  @Override
  public boolean check(ValidationContext context, T object) {
    context.willValidate(this, object);

    boolean success = true;
    for (AnyConstraint<T> c : this.constraints) { 
      if (c != null) {        
        success = c.check(context, object) && success;
      }
    }
    context.didValidate(this, object, success);

    return success;
  }


  /**
   * Adds a constraint to the group. 
   * 
   * <p>The constraints are collected in a
   * {@link Set}, so every constraint can only be added once.</p>
   * 
   * @param c the constraint to add
   */
  public void add(AnyConstraint<T> c) {

    if (c != null) {
      constraints.add(c);
    }
  }


  /**
   * Removes the constraint from the group.
   * 
   * @param constraint the constraint to remove
   * @return {@code true} if constraint was in this group and was removed
   */
  public boolean remove(AnyConstraint<T> constraint) {
    return constraints.remove(constraint);
  }


  /**
   * Checks if the errorCode is in the group.
   * 
   * <p>
   * Notice that every {@link ConstraintGroup} uses the same error code
   * {@link CoreSpecialErrorCodes#ID_GROUP}. Use
   * {@link #contains(AnyConstraint)} instead.</p>
   * 
   * @param errorCode the error code to search for
   * @return {@code true} if the error code is in the group
   * @see #contains(AnyConstraint)
   */
  public boolean contains(int errorCode) {
    for (AnyConstraint<T> con : constraints) {
      if (con.getErrorCode() == errorCode) {
        return true;
      }
    }
    return false;
  }


  /**
   * Checks if the constraint is in the group.
   * 
   * @param c  the constraint to search for
   * @return {@code true} if the constraint is in the group
   */
  public boolean contains(AnyConstraint<T> c) {
    return constraints.contains(c);
  }


  /**
   * Returns the members of the group.
   * 
   * @return the members of the group as an array.
   */
  public AnyConstraint<T>[] getConstraints() {
    @SuppressWarnings("unchecked")
    AnyConstraint<T>[] constraints = this.constraints.toArray(new AnyConstraint[this.constraints.size()]);
    
    return constraints;
  }
  
  /**
   * Returns the number of constraints in this group
   * 
   * @return the number of constraints in this group
   */
  public int getConstraintsCount()
  {
    return constraints.size();
  }
 
}
