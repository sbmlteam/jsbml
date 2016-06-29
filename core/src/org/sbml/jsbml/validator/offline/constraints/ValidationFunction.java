package org.sbml.jsbml.validator.offline.constraints;

import org.sbml.jsbml.validator.offline.ValidationContext;

public interface ValidationFunction<T> {

  public boolean check(ValidationContext ctx, T t);
}
