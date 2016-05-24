package org.sbml.jsbml.validator.constraint;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.sbml.jsbml.validator.ValidationContext;


public class ConstraintGroup<T> implements AnyConstraint<T> {

  private List<AnyConstraint<T>> constraints = new ArrayList<AnyConstraint<T>>();
  
  @Override
  public void check(ValidationContext context, T t) {
    
    for (AnyConstraint<T> c: this.constraints)
    {
      c.check(context, t);
    }
  }

  
  
  /**
   * Adds a constraint to the group.
   * @param c
   */
  public void add(AnyConstraint<T> c) {
    this.constraints.add(c);
  }
  
  
  
  /**
   * Returns the member of the group.
   * @return
   */
  public AnyConstraint<T>[] getConstraints()
  {
    AnyConstraint<T>[] constraints = (AnyConstraint<T>[])(this.constraints.toArray());
    return constraints;
  }
}
