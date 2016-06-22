package org.sbml.jsbml.validator.constraints;

public interface ConstraintBuilder
{
    public AnyConstraint<?> createConstraint(int id);
}
