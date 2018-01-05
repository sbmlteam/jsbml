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

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.math.compiler.ASTNode2Value;
import org.sbml.jsbml.math.compiler.FormulaCompiler;
import org.sbml.jsbml.math.compiler.LaTeXCompiler;
import org.sbml.jsbml.math.compiler.MathMLXMLStreamCompiler;


/**
 * An Abstract Syntax Tree (AST) node representing a MathML piecewise
 * element in a mathematical expression.
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTPiecewiseFunctionNode extends ASTFunction {

  /**
   * 
   */
  private static final long serialVersionUID = -8915335867694118907L;

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTPiecewiseFunctionNode.class);

  /**
   * Creates a new {@link ASTPiecewiseFunctionNode}.
   */
  public ASTPiecewiseFunctionNode() {
    super();
    setType(Type.FUNCTION_PIECEWISE);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTPiecewiseFunctionNode}.
   * 
   * @param node
   *            the {@link ASTPiecewiseFunctionNode} to be copied.
   */
  public ASTPiecewiseFunctionNode(ASTPiecewiseFunctionNode node) {
    super(node);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#clone()
   */
  @Override
  public ASTPiecewiseFunctionNode clone() {
    return new ASTPiecewiseFunctionNode(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler) {
    ASTNode2Value<?> value = null;
    value = compiler.piecewise(getChildren());
    value.setUIFlag(getChildCount() <= 1);
    return processValue(value);
  }

  /**
   * Get the number of piece elements in this {@link ASTPiecewiseFunctionNode}
   * 
   * @return Integer - the number of piece elements in this {@link ASTPiecewiseFunctionNode}
   */
  public int getPieceCount() {
    if (! isSetList()) {
      return 0;
    }
    int i = 0;
    for (ASTNode2 node : getListOfNodes()) {
      i = node.getType() == ASTNode.Type.CONSTRUCTOR_PIECE ? i + 1 : i;
    }
    return i;
  }

  /**
   * Returns True iff piecewise element contains an otherwise element
   * 
   * @return boolean hasOtherwise
   */
  public boolean hasOtherwise() {
    if (! isSetList()) {
      return false;
    }
    ASTNode2 node = null;
    for (int i = getChildCount() - 1 ; i > -1; i--) {
      node = listOfNodes.get(i);
      if (node.getType() == ASTNode.Type.CONSTRUCTOR_OTHERWISE) {
        return true;
      }
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#isAllowableType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public boolean isAllowableType(Type type) {
    return type == Type.FUNCTION_PIECEWISE;
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
   * @see org.sbml.jsbml.math.AbstractASTNode#toMathML()
   */
  @Override
  public String toMathML() {
    try {
      return MathMLXMLStreamCompiler.toMathML(this);
    } catch (RuntimeException e) {
      logger.error("Unable to create MathML");
      return null;
    }
  }

}
