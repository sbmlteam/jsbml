package org.sbml.jsbml.validator.constraint;

import java.util.Optional;

import org.sbml.jsbml.validator.ValidationContext;

public class ValidationConstraint<T> implements AnyConstraint<T> {
  
  private int id_;
  private ValidationFunction<T> func_;
 
  public ValidationConstraint(int id, ValidationFunction<T> func) {
    this.id_ = id;
    this.func_ = func;
  }

 
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ValidationConstraint<?>)
    {
      return ((ValidationConstraint) obj).getID() == this.id_;
    }
    return false;
  }
  
  /**
   * The ID of the constraint
   * @return ID
   */
  public int getID() 
  {
    return this.id_;
  }

  @Override
  public void check(ValidationContext context, T t) {
    // TODO Auto-generated method stub
    
    if(!this.func_.check(context, t))
    {
      context.logBrokenConstraint(this.id_);
    }
  }
  
  private void logErrorMessage()
  {
    
  }
}
