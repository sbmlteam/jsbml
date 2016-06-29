package org.sbml.jsbml.validator.offline;

import org.sbml.jsbml.validator.offline.constraints.AnyConstraint;

public interface ValidationListener {

  abstract public void willValidate(ValidationContext ctx, AnyConstraint<?> c,
    Object o);


  abstract public void didValidate(ValidationContext ctx, AnyConstraint<?> c,
    Object o, boolean success);
}
