package org.sbml.jsbml.validator.constraints;

import org.sbml.jsbml.validator.ValidationContext;

public interface ValidationFunction<T>
{
  public boolean check(ValidationContext ctx, T t);
}
