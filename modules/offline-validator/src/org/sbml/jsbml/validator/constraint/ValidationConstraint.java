package org.sbml.jsbml.validator.constraint;

import org.sbml.jsbml.validator.ValidationContext;

public class ValidationConstraint<T> extends AbstractConstraint<T> {

    private ValidationFunction<T> func;

    public ValidationConstraint(int id, ValidationFunction<T> func) {
	this.id = id;
	this.func = func;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj instanceof ValidationConstraint<?>) {
	    return ((ValidationConstraint<?>) obj).getId() == this.id;
	}
	return false;
    }

    @Override
    public void check(ValidationContext context, T t) {
	// TODO Auto-generated method stub

	context.didValidate(this, t, this.func.check(context, t));
    }
}
