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
 * An Abstract Syntax Tree (AST) node representing a logical operator
 * in a mathematical expression.
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTLogicalOperatorNode extends ASTFunction {

  /**
   * 
   */
  private static final long serialVersionUID = -4435213506053944443L;

  /**
   * Creates a new {@link ASTLogicalOperatorNode}.
   */
  public ASTLogicalOperatorNode() {
    super();
  }
  
  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTLogicalOperatorNode}.
   * 
   * @param node
   *            the {@link ASTLogicalOperatorNode} to be copied.
   */
  public ASTLogicalOperatorNode(ASTLogicalOperatorNode node) {
    super(node);
  }

  /**
   * Creates a new {@link ASTLogicalOperatorNode} of type {@link Type}.
   */
  public ASTLogicalOperatorNode(Type type) {
    super();
    setType(type);
  }
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#clone()
   */
  @Override
  public ASTLogicalOperatorNode clone() {
    return new ASTLogicalOperatorNode(this);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNodeValue compile(ASTNode2Compiler compiler) {
    ASTNodeValue value = null;
    switch(getType()) {
    case LOGICAL_AND:
      value = compiler.and(getChildren());
      value.setUIFlag(getChildCount() <= 1);
      break;
    case LOGICAL_XOR:
      value = compiler.xor(getChildren());
      value.setUIFlag(getChildCount() <= 1);
      break;
    case LOGICAL_OR:
      value = compiler.or(getChildren());
      value.setUIFlag(getChildCount() <= 1);
      break;
    case LOGICAL_NOT:
      value = compiler.not(getChildAt(0));
      break;
    default: // UNKNOWN:
      value = compiler.unknownValue();
      break;
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
    builder.append("ASTLogicalOperatorNode [listOfNodes=");
    builder.append(listOfNodes);
    builder.append(", parentSBMLObject=");
    builder.append(parentSBMLObject);
    builder.append(", strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append(", id=");
    builder.append(id);
    builder.append(", style=");
    builder.append(style);
    builder.append(", listOfListeners=");
    builder.append(listOfListeners);
    builder.append(", parent=");
    builder.append(parent);
    builder.append("]");
    return builder.toString();
  }

}
