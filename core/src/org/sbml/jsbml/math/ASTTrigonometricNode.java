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
 * An Abstract Syntax Tree (AST) node representing a trigonometric function
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTTrigonometricNode extends ASTUnaryFunctionNode {

  /**
   * 
   */
  private static final long serialVersionUID = 8375728473620884311L;

  /**
   * Creates a new {@link ASTTrigonometricNode}.
   */
  public ASTTrigonometricNode() {
    super();
  }
  
  /**
   * Creates a new {@link ASTTrigonometricNode} of type {@link Type}.
   */
  public ASTTrigonometricNode(Type type) {
    super();
    setType(type);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTTrigonometricNode}.
   * 
   * @param node
   *            the {@link ASTTrigonometricNode} to be copied.
   */
  public ASTTrigonometricNode(ASTTrigonometricNode node) {
    super(node);
  }
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTUnaryFunctionNode#clone()
   */
  @Override
  public ASTTrigonometricNode clone() {
    return new ASTTrigonometricNode(this);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNodeValue compile(ASTNode2Compiler compiler) {
    ASTNodeValue value = null;
    switch(getType()) {
    case FUNCTION_SEC:
      value = compiler.sec(getChild());
      break;    
    case FUNCTION_ARCCOS:
      value = compiler.arccos(getChild());
      break;
    case FUNCTION_ARCCOT:
      value = compiler.arccot(getChild());
      break;
    case FUNCTION_ARCCSC:
      value = compiler.arccsc(getChild());
      break;
    case FUNCTION_ARCSEC:
      value = compiler.arcsec(getChild());
      break;
    case FUNCTION_ARCSIN:
      value = compiler.arcsin(getChild());
      break;
    case FUNCTION_ARCTAN:
      value = compiler.arctan(getChild());
      break;
    case FUNCTION_COS:
      value = compiler.cos(getChild());
      break;
    case FUNCTION_COT:
      value = compiler.cot(getChild());
      break;
    case FUNCTION_CSC:
      value = compiler.csc(getChild());
      break;
    case FUNCTION_SIN:
      value = compiler.sin(getChild());
      break;
    case FUNCTION_TAN:
      value = compiler.tan(getChild());
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
    builder.append("ASTTrigonometricNode [listOfNodes=");
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
