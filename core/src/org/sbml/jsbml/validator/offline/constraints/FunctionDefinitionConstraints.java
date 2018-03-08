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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;
import org.sbml.jsbml.validator.offline.constraints.helper.DuplicatedMathValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.SBOValidationConstraints;
import org.sbml.jsbml.validator.offline.constraints.helper.UnknownAttributeValidationFunction;
import org.sbml.jsbml.validator.offline.constraints.helper.ValidationTools;
import org.sbml.jsbml.xml.parsers.MathMLStaxParser;;

/**
 * 
 * @author Roman
 * @since 1.2
 */
public class FunctionDefinitionConstraints
extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) {
    // TODO Auto-generated method stub

  }


  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {
    switch (category) {
    case GENERAL_CONSISTENCY:

      if (level > 1) {
        set.add(CORE_99301);
        set.add(CORE_99302);
      }

      if (level == 2) {
        addRangeToSet(set, CORE_20301, CORE_20305);
      } else if (level == 3) {
        set.add(CORE_20301);
        addRangeToSet(set, CORE_20303, CORE_20307);
      }
      break;
    case IDENTIFIER_CONSISTENCY:
      break;
    case MATHML_CONSISTENCY:
      break;
    case MODELING_PRACTICE:
      break;
    case OVERDETERMINED_MODEL:
      break;
    case SBO_CONSISTENCY:
      if ((level == 2 && version > 1) || level > 2)
      {
        set.add(CORE_10702);
      }
      break;
    case UNITS_CONSISTENCY:
      break;
    }
  }


  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<FunctionDefinition> func = null;

    switch (errorCode) {

    case CORE_10702:
      return SBOValidationConstraints.isMathematicalExpression;

    case CORE_20301:
      func = new ValidationFunction<FunctionDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, FunctionDefinition fd) {
          boolean success = true;

          if (ctx.getLevel() > 1 && fd.isSetMath()) {
            ASTNode math = fd.getMath();
            int nbSemantics = ((fd.isSetUserObjects() && fd.getUserObject(MathMLStaxParser.JSBML_SEMANTICS_COUNT) != null) ? ((Number) fd.getUserObject(MathMLStaxParser.JSBML_SEMANTICS_COUNT)).intValue() : 0);
            
            if (ctx.isLevelAndVersionLessThan(2, 3)) {
              // must be a lambda

              if (!math.isLambda() || math.isSemantics() || nbSemantics > 0) {
                success = false;
              }

            } else {
              // Only applies to non lambdas in l2v3 and later.
              if (!math.isLambda()) {
                if (math.isSemantics()) {
                  if (math.getNumChildren() == 1) {
                    if (!math.getChild(0).isLambda()) {
                      success = false;
                    }
                  } else {
                    success = false;
                  }
                } else {
                  success = false;
                }
              }
            }
          }

          return success;
        }
      };
      break;

    case CORE_20302:
      func = new ValidationFunction<FunctionDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, FunctionDefinition fd) {

          Model m = fd.getModel();
          if (m != null) {
            List<String> definedFunctions = new ArrayList<String>();

            for (int i = 0; i < m.getFunctionDefinitionCount(); i++) {
              FunctionDefinition def = m.getFunctionDefinition(i);

              // Collect all functions which are defined before this one
              if (def.getId() != fd.getId()) {
                definedFunctions.add(def.getId());

              } else {
                break;
              }
            }

            if (fd.getBody() == null) {
              return true;
            }

            Queue<ASTNode> queue = new LinkedList<ASTNode>();

            queue.offer(fd.getBody());

            while (queue.size() > 0) {
              ASTNode node = queue.poll();

              // Checks if the node is a function and if so, if it was declared
              // before
              if (node.getType() == Type.FUNCTION
                  && !definedFunctions.contains(node.getName())) {
                return false;
              }

              // Add all the children to the queue to check them
              for (ASTNode n : node.getListOfNodes()) {

                // Maybe unnecessary check?
                if (n != null) {
                  queue.offer(n);
                }
              }
            }

          }
          return true;
        }
      };
      break;

    case CORE_20303:
      func = new ValidationFunction<FunctionDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, FunctionDefinition fd) {

          Model m = fd.getModel();

          if (fd.isSetMath() && fd.getBody() != null) {
            Queue<ASTNode> queue = new LinkedList<ASTNode>();

            queue.offer(fd.getBody());

            while (!queue.isEmpty()) {
              ASTNode node = queue.poll();

              // No node can refer to this function def
              if (node.getType() == Type.FUNCTION) {

                if (node.getName().equals(fd.getId()))
                {
                  return false;
                }
                else if (m != null)
                {
                  // Recursion test
                  FunctionDefinition def = m.getFunctionDefinition(node.getName());

                  if (def != null && def.isSetMath())
                  {
                    queue.offer(def.getMath());
                  }
                }
              }


              // Add all children to the queue
              for (ASTNode n : node.getListOfNodes()) {
                if (n != null) {
                  queue.offer(n);
                }
              }
            }
          }

          return true;
        }
      };
      break;

    case CORE_20304:
      func = new ValidationFunction<FunctionDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, FunctionDefinition fd) {
          ASTNode body = fd.getBody();
          
          if (body != null)
          {
            List<ASTNode> vars = body.getListOfNodes(ValidationTools.FILTER_IS_NAME);
            
            for (ASTNode var : vars)
            {
              String name = var.getName();
              
              if (name == null)
              {
                name = "";
              }
              
              // If not a argument
              if (fd.getArgument(name) == null)
              {
                if (var.getType() == Type.NAME_TIME)
                {
                  // Name time is allowed before
                  if (ctx.isLevelAndVersionGreaterThan(2, 2)){
                    return false;
                  }
                }
                else
                {
                  return false;
                }
              }
            }
            
            // Check for delay symbol
            if (ctx.isLevelAndVersionEqualTo(2, 5) || ctx.isLevelAndVersionGreaterThan(3, 1))
            {
              vars = body.getListOfNodes(ValidationTools.FILTER_IS_FUNCTION);
              
              for (ASTNode node:vars)
              {
                if (node.getType() == Type.FUNCTION_DELAY)
                {
                  return false;
                }
                else if (ctx.isLevelAndVersionGreaterThan(3, 1) && node.getType() == Type.FUNCTION_RATE_OF) {
                  return false;
                }
              }
            }
          }
          return true;
        }
      };
      break;

    case CORE_20305:
      func = new ValidationFunction<FunctionDefinition>() {


        @Override
        public boolean check(ValidationContext ctx, FunctionDefinition fd) {

          /*
           * need to look at the special case where the body of the lambda function
           * contains only one of the bvar elements
           * eg
           *  <lambda>
           *    <bvar> <ci> v </ci> </bvar>
           *    <ci> v </ci>
           *  </lambda>
           *
           * OR
           * it contains the csymbol time
           * eg
           *  <lambda>
           *    <csymbol encoding="text" 
           *    definitionURL="http://www.sbml.org/sbml/symbols/time"> 
           *    time </csymbol>
           *  </lambda>
           *
           */
          ASTNode body = fd.getBody();

          // No body - no service
          if (body == null || !fd.isSetMath())
          {
            return true;
          }

          if (body.isName() && body.getNumChildren() == 0)
          {
            for (int i = 0; i < fd.getArgumentCount(); i++)
            {
              ASTNode arg = fd.getArgument(i);

              if (arg != null && arg.isString() &&
                  arg.getName() != null && 
                  body.getName() != null)
              {
               
                if (arg.getName().equals(body.getName()))
                {
                  return true;
                }
              }

            }

            if (fd.getNumArguments() == 0)
            {
              if (body.getType() == Type.NAME_TIME)
              {
                return true;
              }
            }
          }

          return body.isBoolean() || 
              body.isNumber() ||
              body.isFunction() || 
              body.isOperator() || body.getType() == Type.CONSTANT_PI || body.getType() == Type.CONSTANT_E;
        }
      };
      break;

    case CORE_20306:
      func = new DuplicatedMathValidationFunction<FunctionDefinition>();
      break;

    case CORE_20307:
      func = new UnknownAttributeValidationFunction<FunctionDefinition>() {
        
        @Override
        public boolean check(ValidationContext ctx, FunctionDefinition fd) {
          // id is a mandatory attribute
          if (!fd.isSetId()) {
            return false;
          }
          return super.check(ctx, fd);
        }
      };
      break;

    case CORE_99301:
      func = new ValidationFunction<FunctionDefinition>() {


        @Override
        public boolean check(ValidationContext ctx, FunctionDefinition fd) {

          ASTNode body = fd.getBody();

          if (fd.isSetMath() && body != null)
          {
            for (ASTNode n : body.getListOfNodes())
            {
              if (n != null) {
                List<? extends TreeNode> timeNodes = n.filter(new Filter() {
                  
                  @Override
                  public boolean accepts(Object o) {
                    if (o instanceof ASTNode) {
                      ASTNode node = (ASTNode) o;
                      
                      // NAME_TIME is found
                      if (node.getType() == Type.NAME_TIME)
                      {
                        return true;
                      }
                    }
                    
                    return false;
                  }
                }, false, true);
                
                // NAME_TIME not allowed
                if (timeNodes != null && timeNodes.size() > 0) {
                  return false;
                }
              }
            }
          }

          return true;
        }
      };
      break;

    case CORE_99302:
      func = new ValidationFunction<FunctionDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, FunctionDefinition fd) {

          ASTNode math = fd.getMath();
          
          if (math != null && math.isLambda())
          {
            ASTNode body = fd.getBody();  
            return body != null;
          }

          return true;
        }
      };
      break;
    }

    return func;
  }
}
