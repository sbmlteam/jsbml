package org.sbml.jsbml.validator.offline.constraints;

import java.util.HashSet;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.validator.offline.ValidationContext;

public class OutsideCycleValidationFunction implements ValidationFunction<Compartment> {
  HashSet<Compartment> outsideSet =  new HashSet<Compartment>();
  
  @Override
  public boolean check(ValidationContext ctx, Compartment c) {
    // Clears set
    
    
    Compartment com = c;
    
    while(com != null && com.isSetOutside())
    {
      outsideSet.clear();
      // add returns false if the compartment is already in the set
      if(!outsideSet.add(com))
      {
        return false;
      }
      
      com = com.getOutsideInstance();
    }
    
    return true;
  }
  
  
  
}
