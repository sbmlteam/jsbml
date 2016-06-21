package org.sbml.jsbml.validator.factory;

import org.sbml.jsbml.validator.constraints.AnyConstraint;

public interface ConstraintBuilder
{
    public AnyConstraint<?> createConstraint(int id);
}
