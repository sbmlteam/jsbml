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

import java.util.Set;

import org.sbml.jsbml.Assignment;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;

/**
 * 
 *
 */
public abstract class AssignmentConstraints
  extends AbstractConstraintDeclaration {

  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category, ValidationContext context) {

    switch (category) {
    case GENERAL_CONSISTENCY:
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
  public void addErrorCodesForAttribute(Set<Integer> set, int level,
    int version, String attributeName, ValidationContext context) {
    // TODO Auto-generated method stub

  }


  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode, ValidationContext context) {
    ValidationFunction<Assignment> func = null;

    switch (errorCode) {
//    case CORE_20906:
//      func = new ValidationFunction<Assignment>() {
//
//        private Filter                      isName  = new Filter() {
//
//                                                      @Override
//                                                      public boolean accepts(
//                                                        Object o) {
//                                                        ASTNode n = (ASTNode) o;
//                                                        return n.isName();
//                                                      }
//                                                    };
//
//        Map<String, CycleDetectionTreeNode> nodeMap =
//          new HashMap<String, CycleDetectionTreeNode>();
//        Queue<Object>                   queue   =
//          new LinkedList<Object>();
//
//
//        @Override
//        public boolean check(ValidationContext ctx, Assignment a) {
//
//          queue.offer(a);
//
//          while (!queue.isEmpty()) {
//            Object obj = queue.poll();
//            CycleDetectionTreeNode node = getNode(as);
//
//          }
//
//          queue.clear();
//          nodeMap.clear();
//          return false;
//        }
//
//
//        private boolean addChildren(CycleDetectionTreeNode node, Assignment a) {
//
//        }
//
//
//        private boolean addChildrenOfRule(Model m, CycleDetectionTreeNode node,
//          ExplicitRule er) {
//          
//          
//          if (er.isSetMath())
//          {
//            for (ASTNode var:er.getMath().getListOfNodes(isName))
//            {
//              String name = var.getName();
//          
//              Reaction reac = m.getReaction(name);
//              Assignment child;
//              
//              if (reac != null)
//              {
//                
//              }
//              
//              
//            }
//          }
//          
//          return true;
//        }
//
//
//        private CycleDetectionTreeNode getNode(Object o) {
//          String name = getName(o);
//          CycleDetectionTreeNode node = nodeMap.get(name);
//
//          if (node == null) {
//            node = new CycleDetectionTreeNode(name);
//            nodeMap.put(name, node);
//          }
//
//          return node;
//        }
//
//
//        private String getName(Object o) {
//          if (o instanceof ExplicitRule) {
//            return ((ExplicitRule) o).getVariable();
//          } else if (o instanceof InitialAssignment) {
//            return ((InitialAssignment) o).getSymbol();
//          } else if (o instanceof Reaction) {
//            return ((Reaction) o).getId();
//          }
//
//          return "";
//        }
//      };
//
//      break;

    default:
      break;
    }

    return func;
  }

}
