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
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.FunctionDefinition;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;;

public class FunctionDefinitionConstraints
extends AbstractConstraintDeclaration {

  @Override
  public AnyConstraint<?> createConstraints(int level, int version,
    CHECK_CATEGORY category) {

    Set<Integer> set = new HashSet<Integer>();

    switch (category) {
    case GENERAL_CONSISTENCY:
      if (version == 2)
      {
        addRangeToSet(set, CORE_20301, CORE_20305);
      }
      else if (version == 3)
      {
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

    return createConstraints(convertToArray(set));
  }


  @Override
  public AnyConstraint<?> createConstraints(int level, int version,
    String attributeName) {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  @SuppressWarnings("deprecation")
  public ValidationFunction<?> getValidationFunction(int errorCode) {
    ValidationFunction<FunctionDefinition> func = null;

    switch (errorCode) {
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

    }

    return func;
  }
}
