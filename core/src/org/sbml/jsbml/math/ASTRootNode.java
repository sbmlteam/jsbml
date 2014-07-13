/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2014  jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 6. The University of Toronto, Toronto, ON, Canada
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.math;

import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.util.compilers.ASTNodeValue;


/**
 * An Abstract Syntax Tree (AST) node representing a root function
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTRootNode extends ASTBinaryFunctionNode {

  /**
   * 
   */
  private static final long serialVersionUID = -8803559492792642628L;

  /**
   * Creates a new {@link ASTRootNode}.
   */
  public ASTRootNode() {
    super();
    setType(Type.FUNCTION_ROOT);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTRootNode}.
   * 
   * @param node
   *            the {@link ASTRootNode} to be copied.
   */
  public ASTRootNode(ASTRootNode node) {
    super(node);
    setType(Type.FUNCTION_ROOT);
  }
  
  /**
   * Creates a new {@link ASTRootNode} with the specified radicand and
   * rootExponent.
   * 
   * @param rootExponent {@link ASTNode2}
   * @param radicand {@link ASTNode2}
   */
  public ASTRootNode(ASTNode2 rootExponent, ASTNode2 radicand) {
    super();
    setLeftChild(rootExponent);
    setRightChild(radicand);
    setType(Type.FUNCTION_ROOT);
  }

  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTUnaryFunctionNode#clone()
   */
  @Override
  public ASTRootNode clone() {
    return new ASTRootNode(this);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNodeValue compile(ASTNode2Compiler compiler) {
    ASTNodeValue value = null;
    ASTNode2 left = getLeftChild();
    if (getChildCount() == 2) {
      if (left instanceof ASTCnIntegerNode) {
        int leftValue = ((ASTCnIntegerNode)left).getInteger();
        if (leftValue == 2) {
          value = compiler.sqrt(getRightChild());
        } else {
          value = compiler.root(leftValue, getRightChild());
        }
      } else if (left instanceof ASTCnRealNode) {
        double leftValue = ((ASTCnRealNode)left).getReal();
        if (leftValue == 2d) {
          value = compiler.sqrt(getRightChild());
        } else {
          value = compiler.root(leftValue, getRightChild());
        }
      } else {
        value = compiler.root(left, getRightChild());
      }
    } else if (getChildCount() == 1) {
      value = compiler.sqrt(getRightChild());
    } else {
      value = compiler.root(left, getRightChild());
    }
    value.setType(getType());
    MathContainer parent = getParentSBMLObject();
    if (parent != null) {
      value.setLevel(parent.getLevel());
      value.setVersion(parent.getVersion());
    }
    return value;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTSqrtNode [strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }

}
