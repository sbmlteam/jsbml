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

import java.text.MessageFormat;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * An Abstract Syntax Tree (AST) node representing an arithmetic
 * operator in a mathematical expression.
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTArithmeticOperatorNode extends ASTFunction {
  
  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTArithmeticOperatorNode.class);

  /**
   * Creates a new {@link ASTArithmeticOperatorNode}.
   */
  public ASTArithmeticOperatorNode() {
    super();
  }
  
  /**
   * Creates a new {@link ASTArithmeticOperatorNode} of type
   * {@link Type}.
   */
  public ASTArithmeticOperatorNode(Type type) {
    super();
    setType(type);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTArithmeticOperatorNode}.
   * 
   * @param node
   *            the {@link ASTArithmeticOperatorNode} to be copied.
   */
  public ASTArithmeticOperatorNode(ASTArithmeticOperatorNode node) {
    super(node);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#clone()
   */
  @Override
  public ASTArithmeticOperatorNode clone() {
    return new ASTArithmeticOperatorNode(this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#reduceToBinary()
   */
  @Override
  public void reduceToBinary() {
    return;
  }
  
  /**
   * Sets the value of this {@link ASTArithmeticOperatorNode} to the given character. If 
   * character is one of +, -, *, / or ^, the node type will be set accordingly. 
   * For all other characters, the node type will be set to UNKNOWN.
   * 
   * @param value
   *            the character value to which the node's value should be set.
   */
  public void setCharacter(char value) {
    Type oldValue = type;
    switch (value) {
    case '+':
      type = Type.PLUS;
      break;
    case '-':
      type = Type.MINUS;
      break;
    case '*':
      type = Type.TIMES;
      break;
    case '/':
      type = Type.DIVIDE;
      break;
    case '^':
      type = Type.POWER;
      break;
    default:
      type = Type.UNKNOWN;
      break;
    }
    firePropertyChange(TreeNodeChangeEvent.value, oldValue, type);
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTArithmeticNode [strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }

}
