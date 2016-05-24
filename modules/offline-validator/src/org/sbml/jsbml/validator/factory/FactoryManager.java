package org.sbml.jsbml.validator.factory;

import java.util.List;

public interface FactoryManager 
{
  /**
   * Returns a list with all the IDs which are needed to
   * validate a object of given class.
   * 
   * @param c
   * @param category
   * @return
   */
  abstract public List<Integer> getIdsForClass(Class<?> clazz, CheckCategory category);
}
