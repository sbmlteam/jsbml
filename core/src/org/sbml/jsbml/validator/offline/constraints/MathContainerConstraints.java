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
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.Rule;
import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.StoichiometryMath;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.ValidationTools;

/**
 * @author Roman
 * @since 1.2
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
    CHECK_CATEGORY category, ValidationContext context) {

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
    int version, String attributeName, ValidationContext context) {
    // TODO Auto-generated method stub

  }


  /*
   * (non-Javadoc)
   * @see
   * org.sbml.jsbml.validator.offline.constraints.AbstractConstraintDeclaration#
   * getValidationFunction(int)
   */
  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<MathContainer> func = null;

    switch (errorCode) {
    case CORE_10217: {
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
    }
      
    case CORE_99505: {
      func = new AbstractValidationFunction<MathContainer>() {
        
        @Override
        public boolean check(ValidationContext ctx, MathContainer mc) {
          
          if (mc instanceof FunctionDefinition) {
            // this rule does not apply to the content of a FunctionDefinition ? 
            return true;
          }
          
          if (mc.isSetMath()) // TODO - could be done inside the ValidationUnitscompiler
          {
            Model m = mc.getModel();
            Queue<ASTNode> toCheck = new LinkedList<ASTNode>();
            
            toCheck.offer(mc.getMath());
            
            // System.out.println("DEBUG 99505 0 - element = " + mc.getElementName() + " - " + mc.getMetaId());
            
            while(!toCheck.isEmpty())
            {
              ASTNode node = toCheck.poll();

              // System.out.println("DEBUG 99505 00 - node = " + node.toSimpleString());
              
              if (node.isNumber())
              {

                // System.out.println("DEBUG 99505 1 - element = " + mc.getElementName() + " - " + mc.getMath().toFormula() + " - " + (mc instanceof KineticLaw ? ((Reaction) mc.getParent()).getId() : "") 
                //    + " - node = " + node.getReal() + " " + node.isSetUnits());

                if (!node.isSetUnits())
                {
                  // TODO - create proper error messages
                  // System.out.println("DEBUG 99505 1 - element = " + mc.getElementName() + " - " + mc.getMath().toFormula() + " - " + (mc instanceof KineticLaw ? ((Reaction) mc.getParent()).getId() : ""));
                  ValidationConstraint.logError(ctx, CORE_99505, mc, mc.getElementName(), ValidationTools.printASTNodeAsFormula(mc.getMath()));
                  return false;
                }
              }
              else if (node.isName())
              {
                // Checking if it is a local parameter
                if (mc instanceof KineticLaw)
                {
                  KineticLaw kl = (KineticLaw) mc;
                  LocalParameter lp = kl.getLocalParameter(node.getName());
                  if (lp != null)
                  {
                    if (!lp.isSetUnits()) {
                      // System.out.println("DEBUG 99505 2 - element = " + mc.getElementName() + " - " + mc.getMath().toFormula() + " - " + (mc instanceof KineticLaw ? ((Reaction) mc.getParent()).getId() : ""));
                      ValidationConstraint.logError(ctx, CORE_99505, mc, mc.getElementName(), ValidationTools.printASTNodeAsFormula(mc.getMath()));
                      return false;
                    }
                  }
                }

                // If we arrive here, it means it was not a LocalParameter
                Parameter p = m.getParameter(node.getName());

                if (p != null)
                {
                  if (!p.isSetUnits()) 
                  { 
                    // System.out.println("DEBUG 99505 3 - element = " + mc.getElementName() + " - " + mc.getMath().toFormula() + " - " + (mc instanceof KineticLaw ? ((Reaction) mc.getParent()).getId() : ""));
                    ValidationConstraint.logError(ctx, CORE_99505, mc, mc.getElementName(), ValidationTools.printASTNodeAsFormula(mc.getMath()));
                    return false;
                  }
                }
              }
            
              if (node.getChildCount() > 0) {
                toCheck.addAll(node.getListOfNodes());
                // System.out.println("DEBUG 99505 4 - toCheck size = " + toCheck.size());
              }
            }
          }
 
          return true;
        }
      };
      break;
    }
    }

    return func;
  }

}
