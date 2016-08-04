package org.sbml.jsbml.validator.offline.constraints.unique;

import java.util.HashSet;
import java.util.Set;

import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.ValidationFunction;

/**
 * Helper class which checks if all IDs of NamedSBase objects are unique in all
 * of the given lists
 * 
 * @author Roman
 * @since 1.2
 * @date 05.08.2016
 */
public abstract class UniqueValidation<T, U> implements ValidationFunction<T>{

  Set<U> ids = new HashSet<U>();
  
  @Override
  public boolean check(ValidationContext ctx, T t) {
    int n = getNumObjects(ctx, t);
    
    for (int i = 0; i < n; i++)
    {
      U id = getNextObject(ctx, t, i);
      
      if (id != null && ids.contains(id))
      {
        ids.clear();
        return false;
      }
    }
    
    ids.clear();
    return true;
  }
  
  abstract public int getNumObjects(ValidationContext ctx, T t);
  abstract public U getNextObject(ValidationContext ctx, T t, int n);
}