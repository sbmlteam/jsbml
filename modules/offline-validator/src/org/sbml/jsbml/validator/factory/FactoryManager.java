package org.sbml.jsbml.validator.factory;

import java.util.List;

import org.sbml.jsbml.validator.SBMLPackage;

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
  abstract public List<Integer> getIdsForClass(Class<?> clazz, CheckCategory category, SBMLPackage[] packages);
  
}
