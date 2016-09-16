/*
 * 
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

import java.util.Enumeration;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.ValidationContext;

/**
 * Class used to always add a {@link ValidationFunction} that is used to recursively validate the jsbml tree structure.
 * 
 * @author Roman
 * @since 1.2
 * @date 04.08.2016
 */
public class TreeNodeConstraints extends AbstractConstraintDeclaration
  implements CoreSpecialErrorCodes {

  /**
   * Log4j logger
   */
  protected static final transient Logger logger = Logger.getLogger(TreeNodeConstraints.class);

  @Override
  public void addErrorCodesForAttribute(Set<Integer> set, int level, int version, String attributeName) {
    // no constraint for attributes
  }


  @Override
  public void addErrorCodesForCheck(Set<Integer> set, int level, int version,
    CHECK_CATEGORY category) 
  {
    // always adding a ValidationFunction that is used to recursively validate the jsbml tree structure
    set.add(ID_VALIDATE_TREE_NODE);
  }


  @Override
  public ValidationFunction<?> getValidationFunction(int errorCode) {
    ValidationFunction<TreeNode> func = null;

    switch (errorCode) {
    case ID_VALIDATE_TREE_NODE:
      func = new ValidationFunction<TreeNode>() {

        @Override
        public boolean check(ValidationContext ctx, TreeNode t) {

          // Only applies if recursiv validation is turned on
          if (!ctx.getValidateRecursivly()) {
            return true;
          }

          boolean success = true;
          Enumeration<?> children = t.children();
          // ConstraintFactory factory = ConstraintFactory.getInstance();

          if (logger.isDebugEnabled()) {
            logger.debug("Found Tree " + t.getChildCount() + " " + children.hasMoreElements());
          }
          
          AnyConstraint<Object> root = ctx.getRootConstraint();
          Class<?> type = ctx.getConstraintType();

          while (children.hasMoreElements()) {
            Object child = children.nextElement();

            if (logger.isDebugEnabled()) {
              logger.debug("Child '" + (child instanceof SBase ? ((SBase) child).getElementName() : child.getClass().getSimpleName()) + "'");
              logger.debug("Child = '" + child + "'");
            }

            if (child != null) {
              ctx.loadConstraints(child.getClass());
              success = ctx.validate(child, false) && success;
            }

          }
          
          // TODO - go through the SBasePlugins

          ctx.setRootConstraint(root, type);

          return success;
        }
      };
    }

    return func;
  }
}
