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

import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.math.compiler.ASTNode2Value;
import org.sbml.jsbml.math.compiler.FormulaCompiler;
import org.sbml.jsbml.math.compiler.LaTeXCompiler;

/**
 * An Abstract Syntax Tree (AST) node representing a MathML qualifier element
 * in a mathematical expression.
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTQualifierNode extends ASTFunction {

  /**
   * 
   */
  private static final long serialVersionUID = 6765565477557495208L;

  /**
   * Creates a new {@link ASTQualifierNode}.
   */
  public ASTQualifierNode() {
    super();
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTQualifierNode}.
   * 
   * @param node
   *            the {@link ASTQualifierNode} to be copied.
   */
  public ASTQualifierNode(ASTQualifierNode node) {
    super(node);
  }

  /**
   * Creates a new {@link ASTQualifierNode} of the given type {@link Type}
   * @param type
   */
  public ASTQualifierNode(Type type) {
    super();
    setType(type);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#clone()
   */
  @Override
  public ASTQualifierNode clone() {
    return new ASTQualifierNode(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler) {
    ASTNode2Value<?> value = null;
    switch(getType()) {
    case CONSTRUCTOR_PIECE:
    case CONSTRUCTOR_OTHERWISE:
    case QUALIFIER_BVAR:
    case QUALIFIER_DEGREE:
    case QUALIFIER_LOGBASE:
      value = getChildAt(0).compile(compiler);
      break;
    default:
      value = compiler.unknownValue();
      break;
    }
    return processValue(value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#isAllowableType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public boolean isAllowableType(Type type) {
    if (type != null) {
      switch(type) {
      case QUALIFIER_BVAR:
      case QUALIFIER_DEGREE:
      case QUALIFIER_LOGBASE:
      case CONSTRUCTOR_PIECE:
      case CONSTRUCTOR_OTHERWISE:
        return true;
      default:
        break;
      }
    }
    return false;
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

}
