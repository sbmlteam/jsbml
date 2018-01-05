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
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.math.compiler.ASTNode2Value;
import org.sbml.jsbml.math.compiler.FormulaCompiler;
import org.sbml.jsbml.math.compiler.LaTeXCompiler;
import org.sbml.jsbml.math.compiler.MathMLXMLStreamCompiler;


/**
 * An Abstract Syntax Tree (AST) node representing a trigonometric function
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTTrigonometricNode extends ASTUnaryFunctionNode {

  /**
   * 
   */
  private static final long serialVersionUID = 8375728473620884311L;

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTTrigonometricNode.class);

  /**
   * Creates a new {@link ASTTrigonometricNode}.
   */
  public ASTTrigonometricNode() {
    super();
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

  /**
   * Creates a new {@link ASTTrigonometricNode} of type {@link Type}.
   * @param type
   */
  public ASTTrigonometricNode(Type type) {
    super();
    setType(type);
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
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler) {
    ASTNode2Value<?> value = null;
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
    return processValue(value);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#isAllowableType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public boolean isAllowableType(Type type) {
    if (type != null) {
      switch(type) {
      case FUNCTION_SEC:
      case FUNCTION_ARCCOS:
      case FUNCTION_ARCCOT:
      case FUNCTION_ARCCSC:
      case FUNCTION_ARCSEC:
      case FUNCTION_ARCSIN:
      case FUNCTION_ARCTAN:
      case FUNCTION_COS:
      case FUNCTION_COT:
      case FUNCTION_CSC:
      case FUNCTION_SIN:
      case FUNCTION_TAN:
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
