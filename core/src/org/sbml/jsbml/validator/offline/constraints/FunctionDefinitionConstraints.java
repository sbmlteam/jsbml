/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2016 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
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

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.util.filters.Filter;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;;

/**
 * 
 * @author Roman
 * @since 1.2
 * @date 04.08.2016
 */
public class FunctionDefinitionConstraints
extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName) {
    // TODO Auto-generated method stub

  }


  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category) {
    switch (category) {
    case GENERAL_CONSISTENCY:

      if (level > 1) {
        set.add(CORE_10214);
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
      break;
    case UNITS_CONSISTENCY:
      break;
    }
  }


  @Override
  @SuppressWarnings("deprecation")
  public ValidationFunction<?> getValidationFunction(int errorCode) {
    ValidationFunction<FunctionDefinition> func = null;

    switch (errorCode) {
    case CORE_10214:
      func = new ValidationFunction<FunctionDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, FunctionDefinition fd) {

          Queue<ASTNode> queue = new LinkedList<ASTNode>();
          Model m = fd.getModel();
          ASTNode node = fd.getBody();

          if (m == null) {
            return true;
          }

          while (node != null) {
            if (node.isFunction()) {
              // Checks if the function exists
              if (m.getFunctionDefinition(node.getName()) == null) {
                return false;
              }
            }

            for (ASTNode n : node.getListOfNodes()) {
              if (n != null) {
                queue.offer(n);
              }
            }

            node = queue.poll();
          }

          return true;
        }
      };
    case CORE_20301:
      func = new ValidationFunction<FunctionDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, FunctionDefinition fd) {
          boolean success = true;

          if (ctx.getLevel() > 1 && fd.isSetMath()) {
            ASTNode math = fd.getMath();

            if (ctx.isLevelAndVersionLessThan(2, 3)) {
              // must be a lambda
              if (!math.isLambda() || math.isSemantics()) {
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
              if (node.isFunction()
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
    case CORE_20303:
      func = new ValidationFunction<FunctionDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, FunctionDefinition fd) {

          if (fd.getBody() != null) {
            Queue<ASTNode> queue = new LinkedList<ASTNode>();

            queue.offer(fd.getBody());

            while (queue.size() > 0) {
              ASTNode node = queue.poll();

              // No node can refer to this function def
              if (node.isFunction() && node.getName() == fd.getId()) {
                return false;
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

    case CORE_20304:
      func = new ValidationFunction<FunctionDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, FunctionDefinition fd) {

          ASTNode body = fd.getBody();

          if (body != null) {

            List<ASTNode> vars = body.getListOfNodes(new Filter() {

              @Override
              public boolean accepts(Object o) {
                ASTNode n = (ASTNode) o;
                return n.isVariable();
              }
            });

            for (ASTNode n : vars) {
              String name = (n.getName() != null) ? n.getName() : "";

              // Variable must refer to a argument

              if (fd.getArgument(name) == null) {

                /* if this is the csymbol time - technically it is allowed 
                 * in L2v1 and L2v2
                 */
                if (n.getType() == Type.NAME_TIME)
                {
                  if (ctx.isLevelAndVersionGreaterThan(2, 2))
                  {
                    return false;
                  }
                }
                return false;
              }
            }

            // In this case the type FUNCTION_DELAY is permitted
            if (ctx.isLevelAndVersionEqualTo(2, 5) || 
                ctx.isLevelAndVersionGreaterThan(3, 1))
            {
              vars = body.getListOfNodes(new Filter() {


                @Override
                public boolean accepts(Object o) {
                  ASTNode node = (ASTNode) o;

                  return node.isFunction();
                }
              });

              for (ASTNode n:vars)
              {
                if (n.getType() == Type.FUNCTION_DELAY)
                {
                  return false;
                }
              }
            }

          }

          return true;
        }
      };

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

          boolean specialCase = false;

          ASTNode body = fd.getBody();

          // No body - no service
          if (body == null)
          {
            return true;
          }

          if (body.getNumChildren() == 0)
          {
            for (int i = 0; i < fd.getArgumentCount(); i++)
            {
              ASTNode arg = fd.getArgument(i);

              if (arg != null && 
                  arg.getName() != null && 
                  body.getName() != null)
              {
                if (arg.getName() == fd.getName())
                {
                  specialCase = true;
                  break;
                }
              }

            }

            if (fd.getNumArguments() == 0)
            {
              if (body.getType() == Type.NAME_TIME)
              {
                specialCase = true;
              }
            }
          }

          return specialCase || 
              body.isBoolean() || 
              body.isNumber() ||
              body.isFunction() || 
              body.isOperator();
        }
      };

    case CORE_99301:
      func = new ValidationFunction<FunctionDefinition>() {


        @Override
        public boolean check(ValidationContext ctx, FunctionDefinition fd) {

          ASTNode body = fd.getBody();

          if (fd.isSetMath() && body != null)
          {
            for (ASTNode n : body.getListOfNodes())
            {
              // NAME_TIME not allowed
              if (n.getType() == Type.NAME_TIME)
              {
                return false;
              }
            }
          }

          return true;
        }
      };

    case CORE_99302:
      func = new ValidationFunction<FunctionDefinition>() {

        @Override
        public boolean check(ValidationContext ctx, FunctionDefinition fd) {

          ASTNode body = fd.getBody();

          if (fd.isSetMath() && fd.getMath().isLambda())
          {
            return body != null;
          }

          return true;
        }
      };

    }

    return func;
  }
}
