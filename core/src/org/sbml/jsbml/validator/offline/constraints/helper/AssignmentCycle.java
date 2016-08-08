//package org.sbml.jsbml.validator.offline.constraints.helper;
//
//import org.sbml.jsbml.ASTNode;
//import org.sbml.jsbml.Assignment;
//import org.sbml.jsbml.ExplicitRule;
//import org.sbml.jsbml.Model;
//import org.sbml.jsbml.Reaction;
//import org.sbml.jsbml.util.filters.Filter;
//import org.sbml.jsbml.validator.offline.ValidationContext;
//import org.sbml.jsbml.validator.offline.constraints.AbstractConstraint;
//import org.sbml.jsbml.validator.offline.constraints.ValidationFunction;
//
//
//abstract public class AssignmentCycle
//  implements ValidationFunction<Assignment> {
//  
//  
//  
//  @Override
//  public boolean check(ValidationContext ctx, Assignment a) {
//    String rootName;
//    
//    
//    return false;
//  }
//  
//  private boolean checkRule(String rootName, Model m, ExplicitRule er)
//  {
//    if (er.getVariable().equals(rootName))
//    {
//      return false;
//    }
//    else
//    {
//      
//      for (ASTNode node: er.getMath().getListOfNodes(isName))
//      {
//        String name = (node.getName() != null) ? node.getName() : "";
//  
//        
//        Reaction r = m.getReaction(name);
//        
//        Rule r = m.getRule(variable)
//        
//        if (r != null && )
//        {
//          
//        }
//      }
//    }
//    
//    return true;
//  }
//
//}
