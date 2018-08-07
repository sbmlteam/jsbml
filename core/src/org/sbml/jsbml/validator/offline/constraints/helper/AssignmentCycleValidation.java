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
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.Assignment;
import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.ExplicitRule;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.RateRule;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.AbstractValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.ValidationConstraint;
import org.sbml.jsbml.validator.offline.factory.SBMLErrorCodes;


/**
 * Validates rule {@link SBMLErrorCodes#CORE_20906}.
 * 
 * <p>There must not be circular dependencies in the combined set of InitialAssignment , AssignmentRule
 * and KineticLaw objects in a model. Each of these constructs has the effect of assigning a value to
 * an identifier (i.e., the identifier given in the attribute symbol in InitialAssignment , the attribute
 * variable in AssignmentRule , and the attribute id on the KineticLaw ’s enclosing Reaction ). Each
 * of these constructs computes the value using a mathematical formula. The formula for a given
 * identifier cannot make reference to a second identifier whose own definition depends directly or
 * indirectly on the first identifier. (References: SBML L3V1 Section 4.9.5; SBML L3V2 Section 4.9.5.)</p>
 * 
 * @author Roman
 * @author rodrigue
 * @since 1.2
 */
public class AssignmentCycleValidation
  extends AbstractValidationFunction<SBase> {

  /**
   * Key to store a set of ids in the {@link ValidationContext} so that we do not report several times the same cycle.
   */
  public static final String ASSIGNMENT_CYCLE_VALIDATION_FOUND_CYCLE_IDS = "AssignmentCycleValidation.foundCycleIds";
  
  /**
   * A {@link Logger} for this class.
   */
  private static transient final Logger logger = Logger.getLogger(AssignmentCycleValidation.class);
  /**
   * A static boolean to avoid many unnecessary calls to {@link Logger#isDebugEnabled()}.
   */
  private static transient final boolean isDebugEnabled = logger.isDebugEnabled();

  @Override
  public boolean check(ValidationContext ctx, SBase sb) {
    
    if (sb instanceof RateRule) {
      // We do not need to test RateRule
      return true;
    }
    
    Model m = sb.getModel();
    boolean check = true;
    
    if (m != null)
    {
      Set<String> visited = new HashSet<String>();
      boolean isCompartment = sb instanceof Assignment && ((Assignment) sb).getVariableInstance() instanceof Compartment;
      String currentId = getRelatedId(sb, isCompartment);
      Queue<SBase> toCheck = new LinkedList<SBase>();
      
      // A Set that contains all the ids implicated in a cycle, so that we do not report several times the same cycle.
      @SuppressWarnings("unchecked")
      Set<String> foundCycleIds = (Set<String>) ctx.getHashMap().get(ASSIGNMENT_CYCLE_VALIDATION_FOUND_CYCLE_IDS);
      
      if (foundCycleIds == null) {
        foundCycleIds = new HashSet<String>();
        ctx.getHashMap().put(ASSIGNMENT_CYCLE_VALIDATION_FOUND_CYCLE_IDS, foundCycleIds);
      }

      if (isDebugEnabled) {
        logger.debug("Testing " + currentId);
      }
      
      if (currentId != null && !currentId.isEmpty())
      {
        if (foundCycleIds.contains(currentId)) {
          // cycle already found and reported
          return true;
        }
        
        SBase previousChild = sb;
        String previousId = currentId;
        
        // Collect the children
        checkChildren(m, sb, toCheck);
        
        while (!toCheck.isEmpty())
        {
          SBase child = toCheck.poll();
          
          // Referred to this id?
          String childId = getRelatedId(child, isCompartment);
          
          // If this child wasn't visited yet
          if (childId != null && visited.add(childId)){
            
            if (isDebugEnabled) {
              logger.debug("Checking " + childId);
            }
            
            // Check if we are back at the first SBase
            if (childId.equals(currentId))
            {
              if (isDebugEnabled) {
                logger.debug("Found an assignment cycle with '" + childId + "'");
              }
              String relatedId1 = sb instanceof InitialAssignment ? "symbol" : (sb instanceof Reaction ? "id" : "variable"); // TODO - Can it be Species ? 
              String relatedId2 = previousChild instanceof InitialAssignment ? "symbol" : (previousChild instanceof Reaction ? "id" : "variable");
              foundCycleIds.add(currentId);
              foundCycleIds.add(previousId);
              
              // using different messages for different configuration
              if (isCompartment && child instanceof Species) {
                ValidationConstraint.logErrorWithPostmessageCode(ctx, SBMLErrorCodes.CORE_20906, SBMLErrorCodes.CORE_20906 + "_COMP", sb, sb.getElementName(), currentId, child.getId());
                
              } else if (previousId != null && previousId.equals(currentId)) {
                
                String formula = "";
                
                if (sb instanceof Reaction) {
                  formula = ValidationTools.printASTNodeAsFormula(((Reaction) sb).getKineticLaw().getMath());
                } else if (sb instanceof MathContainer) {
                  formula = ValidationTools.printASTNodeAsFormula(((MathContainer) sb).getMath());
                }
                
                ValidationConstraint.logErrorWithPostmessageCode(ctx, SBMLErrorCodes.CORE_20906, SBMLErrorCodes.CORE_20906 + "_SELF", sb, sb.getElementName(), relatedId1, currentId, formula);
                
              } else { 
                ValidationConstraint.logError(ctx, SBMLErrorCodes.CORE_20906, sb, sb.getElementName(), relatedId1, currentId, previousChild.getElementName(), relatedId2, previousId);
              }

              check = false; // do not return straight away to be able to detect other potential cycle ?
            }
            
            // Else check the children
            previousChild = child;
            previousId = childId;
            
            checkChildren(m, child, toCheck);
          }
        }
      }
    }
    
    return check;
  }
  
  /**
   * Returns an id depending of the type of SBase given. If it is a {@link Reaction}, returns reaction.id, 
   * if it is an {@link Assignment}, returns assignment.variable (symbol or variable attribute), if it is 
   * a {@link Species}, returns species.compartment. For any other type of {@link SBase}, returns null. 
   * 
   * @param sb an {@link SBase}
   * @return an id depending of the type of SBase given. 
   */
  private String getRelatedId(SBase sb, boolean isCompartment)
  {
    if (sb instanceof Reaction)
    {
      return ((Reaction) sb).getId();
    }
    else if (sb instanceof Assignment)
    {
      return ((Assignment) sb).getVariable();
    }
    else if (sb instanceof Species && isCompartment)
    {
      return ((Species) sb).getCompartment();
    }
    
    return null;
  }
  
  /**
   * 
   * @param m
   * @param sb
   */
  private void checkChildren(Model m, SBase sb, Queue<SBase> toCheck)
  {
    if (sb instanceof ExplicitRule || sb instanceof InitialAssignment)
    {
      checkChildren(m, (MathContainer) sb, toCheck);
    }
    else if (sb instanceof Reaction)
    {
      checkChildren(m, (Reaction) sb, toCheck);
    }    
  }
  
  /**
   * 
   * @param m
   * @param r
   */
  private void checkChildren(Model m, MathContainer r, Queue<SBase> toCheck)
  {
    if (r.isSetMath())
    {
      checkChildren(m, r.getMath(), toCheck);
    }
  }
  
  /**
   * 
   * @param m
   * @param r
   */
  private void checkChildren(Model m, Reaction r, Queue<SBase> toCheck)
  {
    if (r.isSetKineticLaw())
    {
      KineticLaw kl = r.getKineticLaw();
      
      if (kl.isSetMath())
      {
        checkChildren(m, kl.getMath(), toCheck);
      }
    }
  }
  
  /**
   * 
   * @param m
   * @param math
   */
  private void checkChildren(Model m, ASTNode math, Queue<SBase> toCheck)
  {
    if (isDebugEnabled) {
      logger.debug("Looking for ASTNode NAME");
    }
    Queue<ASTNode> children = new LinkedList<ASTNode>();
    
    children.add(math);
    
    while (!children.isEmpty())
    {
      ASTNode node = children.poll();
      
      if (node.getType() == Type.NAME)
      {
        if (isDebugEnabled) {
          logger.debug("Node is a name " + node.getName());
        }
        
        // If one of the nodes refer to Reaction, InitalAssignment or Rule
        String id = (node.getName() != null) ? node.getName() : "";
        
        SBase child = m.isSetListOfReactions() ? m.getReaction(id) : null;
        
        // Not a Reaction?
        if (child == null)
        {
          child = m.isSetListOfInitialAssignments() ? m.getInitialAssignmentBySymbol(id) : null;
          
          // Not InitialAssignment?
          if (child == null)
          {
            Rule r = m.isSetListOfRules() ? m.getRuleByVariable(id) : null;
            if (r != null && r.isAssignment())
            {
              child = r;
            }
            else
            {
              Species s = m.isSetListOfSpecies() ? m.getSpecies(id) : null;
              
              if (s != null && !s.hasOnlySubstanceUnits())
              {
                child = s;
              }
            }
          }
        }
        
        if (child != null)
        {
          if (isDebugEnabled) {
            logger.debug("Found Child");
          }
          toCheck.add(child);
        }
      }
      
      children.addAll(node.getListOfNodes());
    }
  }

}
