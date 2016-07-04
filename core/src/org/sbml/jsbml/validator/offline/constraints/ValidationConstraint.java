package org.sbml.jsbml.validator.offline.constraints;

import org.apache.log4j.Logger;
import org.sbml.jsbml.validator.offline.ValidationContext;

public class ValidationConstraint<T> extends AbstractConstraint<T> {

  protected static transient Logger logger =
    Logger.getLogger(ValidationConstraint.class);
  private ValidationFunction<T>     func;


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
  public boolean check(ValidationContext context, T t) {
    // TODO Auto-generated method stub
    boolean passed = true;
    logger.debug("Validate constraint " + this.id);
    if (this.func != null) {
      passed = func.check(context, t);
    }
    context.didValidate(this, t, passed);
    
    return passed;
  }
}
