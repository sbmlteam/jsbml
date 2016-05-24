package org.sbml.jsbml.validator.constraint;

import java.util.Optional;

import org.sbml.jsbml.validator.ValidationContext;

public interface AnyConstraint<T> 
{
  /**
   * Checks if the object conforms to the 
   * specific rule in the given context
   * @param context
   * @param t
   * @return test passed
   */
  abstract public void check(ValidationContext context, T t);
}
