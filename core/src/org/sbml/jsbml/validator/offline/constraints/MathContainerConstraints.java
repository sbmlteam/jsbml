/*
 * $IdMathContainerConstraints.java 18:59:35 roman $
 * $URLMathContainerConstraints.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2017 jointly by the following organizations:
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
package org.sbml.jsbml.validator.offline.constraints;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.Delay;
import org.sbml.jsbml.EventAssignment;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.InitialAssignment;
import org.sbml.jsbml.KineticLaw;
import org.sbml.jsbml.LocalParameter;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.StoichiometryMath;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.ValidationTools;

/**
 * @author Roman
 * @since 1.2
 * @date 07.08.2016
 */
@SuppressWarnings("deprecation")
public class MathContainerConstraints extends AbstractConstraintDeclaration {

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#
   * addErrorCodesForCheck(java.util.Set, int, int,
   * org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY)
   */
  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category) {

    switch (category) {
    case GENERAL_CONSISTENCY:
      break;
    case IDENTIFIER_CONSISTENCY:
      break;
    case MATHML_CONSISTENCY:
      if (level > 1) {
        set.add(CORE_10217);
      }

      break;
    case MODELING_PRACTICE:
      break;
    case OVERDETERMINED_MODEL:
      break;
    case SBO_CONSISTENCY:
      break;
    case UNITS_CONSISTENCY:
      set.add(CORE_99505);
      break;
    }

  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.validator.offline.constraints.ConstraintDeclaration#
   * addErrorCodesForAttribute(java.util.Set, int, int, java.lang.String)
   */
  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName) {
    // TODO Auto-generated method stub

  }


  /*
   * (non-Javadoc)
   * @see
   * org.sbml.jsbml.validator.offline.constraints.AbstractConstraintDeclaration#
   * getValidationFunction(int)
   */
  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode) {
    ValidationFunction<MathContainer> func = null;

    switch (errorCode) {
    case CORE_10217:
      func = new ValidationFunction<MathContainer>() {

 
        @Override
        public boolean check(ValidationContext ctx, MathContainer mc) {
  
          if (mc.isSetMath()) {
            // ASTNode must return a number
            if (mc instanceof KineticLaw || mc instanceof StoichiometryMath
              || mc instanceof SimpleSpeciesReference
              || mc instanceof InitialAssignment || mc instanceof Delay
              || mc instanceof EventAssignment || mc instanceof Rule) {
              
              return ValidationTools.getDataType(mc.getMath()) == ValidationTools.DT_NUMBER;
            }
          }

          return true;
        }
      };
      break;
      
    case CORE_99505:
      func = new ValidationFunction<MathContainer>() {
        
        
        @Override
        public boolean check(ValidationContext ctx, MathContainer mc) {
          
          if (mc.isSetMath())
          {
            Model m = mc.getModel();
            Queue<ASTNode> toCheck = new LinkedList<ASTNode>();
            
            toCheck.offer(mc.getMath());
            
            while(!toCheck.isEmpty())
            {
              ASTNode node = toCheck.poll();
              if (node.isLiteral())
              {
                if (!node.isSetUnits())
                {
                  return false;
                }
              }
              else if (node.isName())
              {
                Parameter p = m.getParameter(node.getName());
                
                if (p == null || !p.isSetUnits())
                {
                  // Could be a arg of a FunctionDefinition
                  if (mc instanceof FunctionDefinition)
                  {
                    FunctionDefinition fd = (FunctionDefinition) mc;
                    
                    if (fd.getArgument(node.getName()) == null)
                    {
                      return false;
                    }
                  }
                  // Or a local parameter
                  else if (mc instanceof KineticLaw)
                  {
                    KineticLaw kl = (KineticLaw) mc;
                    LocalParameter lp = kl.getLocalParameter(node.getName());
                    if (lp == null || !lp.isSetUnits())
                    {
                      return false;
                    }
                  }
                  else 
                  { 
                    return false;
                  }
                }
              }
              
              toCheck.addAll(node.getListOfNodes());
            }
          }
          
 
          return true;
        }
      };
      break;
    }

    return func;
  }

}
