package org.sbml.jsbml.validator.offline.constraints;

import org.sbml.jsbml.validator.offline.ValidationContext;

public abstract class AbstractConstraint<T> implements AnyConstraint<T> {

    protected int id = CoreSpecialErrorCodes.ID_DO_NOT_CACHE;
    
    
    @Override
    abstract public void check(ValidationContext context, T t);

    @Override
    public int getId() {
	// TODO Auto-generated method stub
	return this.id;
    }
}
