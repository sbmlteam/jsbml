package org.sbml.jsbml.validator.offline.constraints;

public interface ConstraintBuilder
{
    public AnyConstraint<?> createConstraint(int id);
}
