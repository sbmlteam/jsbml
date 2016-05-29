package org.sbml.jsbml.validator;

import org.sbml.jsbml.validator.constraint.AnyConstraint;

public interface ValidationListener 
{
    abstract public void willValidate(ValidationContext ctx, AnyConstraint<?> c, Object o);
    abstract public void didValidate(ValidationContext ctx, AnyConstraint<?> c, Object o, boolean success);
}
