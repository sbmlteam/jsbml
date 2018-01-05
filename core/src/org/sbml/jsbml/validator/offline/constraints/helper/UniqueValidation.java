/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.validator.offline.constraints.helper;

import java.util.HashSet;
import java.util.Set;

import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.ValidationFunction;

/**
 * Helper class which checks if all objects are unique.
 * 
 * @author Roman
 * @since 1.2
 */
public abstract class UniqueValidation<T, U> implements ValidationFunction<T>{

  Set<U> ids = new HashSet<U>();
  
  @Override
  public boolean check(ValidationContext ctx, T t) {
    int n = getNumObjects(ctx, t);
    
    for (int i = 0; i < n; i++)
    {
      U id = getNextObject(ctx, t, i);
      
      // If id not null, but already in set
      if (id != null && !ids.add(id))
      {
        if (id instanceof String) {
          String idStr = (String) id;
          
          if (idStr.trim().length() == 0) {
            continue;
          }
        }
        
        ids.clear(); // TODO - going through the list each time, having a map and going through the list only once would be much better. 
        return false; // TODO - how do we provides the id and the elements that had the duplicated id ?
      }

    }
    
    ids.clear();
    return true;
  }
  
  /**
   * Returns the total number of objects which should be tested.
   * This function is only called once in the beginning of the check.
   * 
   * @param ctx
   * @param t
   * @return the number of objects to compare.
   */
  abstract public int getNumObjects(ValidationContext ctx, T t);
  
  
  /**
   * Returns the n-th object that should be tested.
   * 
   * @param ctx
   * @param t
   * @param n
   * @return the n-th object
   */
  abstract public U getNextObject(ValidationContext ctx, T t, int n);
}