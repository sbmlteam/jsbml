package org.sbml.jsbml.validator.constraints;

import org.sbml.jsbml.validator.ValidationContext;
import org.sbml.jsbml.validator.factory.ConstraintFactory;

public abstract class AbstractConstraint<T> implements AnyConstraint<T> {

    protected int id = ConstraintFactory.ID_DO_NOT_CACHE;
    
    
    @Override
    abstract public void check(ValidationContext context, T t);

    @Override
    public int getId() {
	// TODO Auto-generated method stub
	return this.id;
    }
}
