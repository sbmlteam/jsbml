package org.sbml.jsbml.validator.constraint;


import org.sbml.jsbml.validator.ValidationContext;

public class ValidationConstraint<T> implements AnyConstraint<T> {
  
  private int id;
  private ValidationFunction<T> func;
 
  public ValidationConstraint(int id, ValidationFunction<T> func) {
    this.id = id;
    this.func = func;
  }

 
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ValidationConstraint<?>)
    {
      return ((ValidationConstraint<?>) obj).getID() == this.id;
    }
    return false;
  }
  
  /**
   * The ID of the constraint
   * @return ID
   */
  public int getID() 
  {
    return this.id;
  }

  @Override
  public void check(ValidationContext context, T t) {
    // TODO Auto-generated method stub
    
    if(!this.func.check(context, t))
    {
      context.logBrokenConstraint(this.id);
    }
  }
}
