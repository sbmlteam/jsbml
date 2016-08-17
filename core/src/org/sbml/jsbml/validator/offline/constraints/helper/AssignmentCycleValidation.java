package org.sbml.jsbml.validator.offline.constraints.helper;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Assignment;
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.ValidationFunction;


public class AssignmentCycleValidation
  implements ValidationFunction<SBase> {
  
  private Set<String> visited = new HashSet<String>();
  private Queue<SBase> toCheck = new LinkedList<SBase>();
  
  @Override
  public boolean check(ValidationContext ctx, SBase sb) {
    Model m = sb.getModel();
    
    if (m != null)
    {
      visited.clear();
      String currentId = getId(sb);
      System.out.println("Testing " + currentId);
      
      if (currentId != null && !currentId.isEmpty())
      {
        System.out.println("Children");
        // Collect the children
        checkChildren(m, sb);
        
        while (!toCheck.isEmpty())
        {
          SBase child = toCheck.poll();
          
          // Referred to this id?
          String childId = getId(child);
          
          // If this child wasn't visited yet
          if (childId != null && visited.add(childId)){
            
            System.out.println("Check " + childId);
            // Check if we are back at the first SBase
            if (childId.equals(currentId))
            {
              return false;
            }
            
            // Else check the children
            checkChildren(m, child);
          }
        }
      }
    }
    
    
    return true;
  }
  
  private String getId(SBase sb)
  {
    if (sb instanceof Reaction)
    {
      return ((Reaction) sb).getId();
    }
    else if (sb instanceof Assignment)
    {
      return ((Assignment) sb).getVariable();
    }
    else if (sb instanceof Species)
    {
      return ((Species) sb).getCompartment();
    }
    
    return null;
  }
  
  private void checkChildren(Model m, SBase sb)
  {
    if (sb instanceof ExplicitRule)
    {
      checkChildren(m, (ExplicitRule) sb);
    }
    else if (sb instanceof Reaction)
    {
      checkChildren(m, (Reaction) sb);
    }
    else if (sb instanceof InitialAssignment)
    {
      checkChildren(m, (InitialAssignment) sb);
    }
  }
  
  private void checkChildren(Model m, ExplicitRule r)
  {
    if (r.isSetMath())
    {
      checkChildren(m, r.getMath());
    }
  }
  
  // Adds all children of ia to the queue which could refer to the root
  private void checkChildren(Model m, InitialAssignment ia)
  {
    if (ia.isSetMath())
    {
      checkChildren(m, ia.getMath());
    }
   
  }
  
  private void checkChildren(Model m, Reaction r)
  {
    if (r.isSetKineticLaw())
    {
      KineticLaw kl = r.getKineticLaw();
      
      if (kl.isSetMath())
      {
        checkChildren(m, kl.getMath());
      }
    }
  }
  
  private void checkChildren(Model m, ASTNode math)
  {
    System.out.println("Looking for children");
    Queue<ASTNode> children = new LinkedList<ASTNode>();
    
    children.add(math);
    
    
    while (!children.isEmpty())
    {
      ASTNode node = children.poll();
      
      if (node.isName())
      {
        System.out.println("Node is a name " + node.getName());
        // If one of the nodes refer to Reaction, InitalAssignment or Rule
        String name = (node.getName() != null) ? node.getName() : "";
        
        SBase child = m.getReaction(name);
        
        // Not a Reaction?
        if (child == null)
        {
          child = m.getInitialAssignment(name);
          
          // Not InitialAssignment?
          if (child == null)
          {
            Rule r = m.getRule(name);
            if (r != null && r.isAssignment())
            {
              child = r;
            }
            else
            {
              Species s = m.getSpecies(name);
              
              if (s != null && !s.hasOnlySubstanceUnits())
              {
                child = s;
              }
            }

          }
        }
        
        
        if (child != null)
        {
          System.out.println("Found Child");
          toCheck.add(child);
        }
      }
      
      children.addAll(node.getListOfNodes());
    }
  }

}
