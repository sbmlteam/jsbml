package org.sbml.jsbml.validator.constraint;

import org.sbml.jsbml.validator.ValidationContext;

public interface ValidationFunction<T>
{
  public boolean check(ValidationContext ctx, T t);
}
