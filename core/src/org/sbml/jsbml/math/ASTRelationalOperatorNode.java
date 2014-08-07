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

import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.math.compiler.ASTNode2Value;
import org.sbml.jsbml.math.compiler.FormulaCompiler;
import org.sbml.jsbml.math.compiler.LaTeXCompiler;


/**
 * An Abstract Syntax Tree (AST) node representing a relational
 * operator in a mathematical expression.
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTRelationalOperatorNode extends ASTFunction {

  /**
   * 
   */
  private static final long serialVersionUID = 5739652236362075869L;

  /**
   * Creates a new {@link ASTRelationalOperatorNode} without a pointer
   * to its containing {@link MathContainer}.
   */
  public ASTRelationalOperatorNode() {
    super();
  }
  
  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTRelationalOperatorNode}.
   * 
   * @param node
   *            the {@link ASTRelationalOperatorNode} to be copied.
   */
  public ASTRelationalOperatorNode(ASTRelationalOperatorNode node) {
    super(node);
  }

  /**
   * Creates a new {@link ASTRelationalOperatorNode} without a pointer
   * to its containing {@link MathContainer} but with the specified 
   * {@link Type}.
   */
  public ASTRelationalOperatorNode(Type type) {
    this();
    setType(type);
  }
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#clone()
   */
  @Override
  public ASTRelationalOperatorNode clone() {
    return new ASTRelationalOperatorNode(this);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler) {
    ASTNode2Value<?> value = null;
    switch(getType()) {
    case RELATIONAL_EQ:
      value = compiler.eq(getChildAt(0), getChildAt(getChildCount() - 1));
      break;
    case RELATIONAL_GEQ:
      value = compiler.geq(getChildAt(0), getChildAt(getChildCount() - 1));
      break;
    case RELATIONAL_GT:
      value = compiler.gt(getChildAt(0), getChildAt(getChildCount() - 1));
      break;
    case RELATIONAL_NEQ:
      value = compiler.neq(getChildAt(0), getChildAt(getChildCount() - 1));
      break;
    case RELATIONAL_LEQ:
      value = compiler.leq(getChildAt(0), getChildAt(getChildCount() - 1));
      break;
    case RELATIONAL_LT:
      value = compiler.lt(getChildAt(0), getChildAt(getChildCount() - 1));
      break;
    default: // UNKNOWN:
      value = compiler.unknownValue();
      break;
    }
    value.setType(getType());
    if (isSetParentSBMLObject()) {
      MathContainer parent = getParentSBMLObject();
      if (parent != null) {
        value.setLevel(parent.getLevel());
        value.setVersion(parent.getVersion());
      }      
    }
    return value;
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#isAllowableType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public boolean isAllowableType(Type type) {
    return type == Type.RELATIONAL_EQ || type == Type.RELATIONAL_GEQ
        || type == Type.RELATIONAL_GT || type == Type.RELATIONAL_LEQ
        || type == Type.RELATIONAL_LT || type == Type.RELATIONAL_NEQ;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#toFormula()
   */
  @Override
  public String toFormula() throws SBMLException {
    return compile(new FormulaCompiler()).toString();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.AbstractASTNode#toLaTeX()
   */
  @Override
  public String toLaTeX() throws SBMLException {
    return compile(new LaTeXCompiler()).toString();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTRelationalOperatorNode [listOfNodes=");
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
