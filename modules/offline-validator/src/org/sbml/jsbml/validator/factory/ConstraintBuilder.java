package org.sbml.jsbml.validator.factory;

import org.sbml.jsbml.validator.constraint.AnyConstraint;

public interface ConstraintBuilder
{
    public AnyConstraint<?> createConstraint(int id);
}
