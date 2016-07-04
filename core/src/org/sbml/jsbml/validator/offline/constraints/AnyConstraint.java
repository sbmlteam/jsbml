package org.sbml.jsbml.validator.offline.constraints;

import org.sbml.jsbml.validator.offline.ValidationContext;

public interface AnyConstraint<T> {

  /**
   * Checks if the object conforms to the
   * specific rule in the given context
   * 
   * @param context
   * @param t
   * @return test passed
   */
  abstract public boolean check(ValidationContext context, T t);


  abstract public int getId();
}
