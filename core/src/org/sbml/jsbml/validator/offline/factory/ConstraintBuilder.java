package org.sbml.jsbml.validator.offline.factory;

import org.sbml.jsbml.validator.offline.constraints.AnyConstraint;

public interface ConstraintBuilder {

  public AnyConstraint<?> createConstraint(int id);
}
