package org.sbml.jsbml.validator.offline.constraints;


public abstract class AbstractConstraint<T> implements AnyConstraint<T> {

  protected int id = CoreSpecialErrorCodes.ID_DO_NOT_CACHE;

  @Override
  public int getId() {
    // TODO Auto-generated method stub
    return this.id;
  }
}
