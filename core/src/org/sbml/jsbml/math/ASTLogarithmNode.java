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

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.math.compiler.ASTNode2Value;
import org.sbml.jsbml.math.compiler.FormulaCompiler;
import org.sbml.jsbml.math.compiler.LaTeXCompiler;
import org.sbml.jsbml.math.compiler.MathMLXMLStreamCompiler;


/**
 * An Abstract Syntax Tree (AST) node representing the logarithm function
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTLogarithmNode extends ASTBinaryFunctionNode {

  /**
   * 
   */
  private static final long serialVersionUID = 5350043898749220594L;

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTLogarithmNode.class);

  /**
   * Creates a new {@link ASTLogarithmNode}.
   */
  public ASTLogarithmNode() {
    super();
    setType(Type.FUNCTION_LOG);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTLogarithmNode}.
   * 
   * @param node
   *            the {@link ASTLogarithmNode} to be copied.
   */
  public ASTLogarithmNode(ASTLogarithmNode node) {
    super(node);
  }

  /**
   * Creates a new {@link ASTLogarithmNode} with base 10
   * and value {@link ASTNode2}
   * 
   * @param value {@link ASTNode2} - must not be {@code null}
   */
  public ASTLogarithmNode(ASTNode2 value) {
    this();
    addChild(value);
  }

  /**
   * Creates a new {@link ASTLogarithmNode} with base {@link ASTNode2}
   * and value {@link ASTNode2}
   * 
   * @param base {@link ASTNode2} - can be {@code null}; then a base of 10 will
   * be assumed.
   * @param value {@link ASTNode2} - must not be {@code null}
   */
  public ASTLogarithmNode(ASTNode2 base, ASTNode2 value) {
    this();
    if (base != null) {
      addChild(base);
      if (base.getType() == Type.CONSTANT_E) {
        setType(Type.FUNCTION_LN);
      }
    }
    addChild(value);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTBinaryFunctionNode#addChild(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public void addChild(ASTNode2 child) {
    if (! isSetList())  {
      listOfNodes = new ArrayList<ASTNode2>();
    }
    if (isStrict() && getChildCount() == 2) {
      throw new IndexOutOfBoundsException("max child limit exceeded");
    }
    if (getChildCount() >= 2) {
      logger.debug("Max child limit exceeded. To add more children " +
          "to ASTBinaryFunctionNode set strictness to false.");
    }
    listOfNodes.add(child);
    ASTFactory.setParentSBMLObject(child, parentSBMLObject);
    child.setParent(this);
    child.fireNodeAddedEvent();
    if (child.getType() == Type.CONSTANT_E && getChildCount() == 1) {
      setType(Type.FUNCTION_LN);
    }
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#clone()
   */
  @Override
  public ASTLogarithmNode clone() {
    return new ASTLogarithmNode(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler) {
    ASTNode2Value<?> value = null;
    if (getChildCount() == 2) {
      if (getType() == Type.FUNCTION_LOG) {
        value = compiler.log(getLeftChild(), getRightChild());
      } else {
        value = compiler.ln(getRightChild());
      }
    } else {
      value = compiler.log(getRightChild());
    }
    return processValue(value);
  }

  /**
   * Return the base of this {@link ASTLogarithmNode}
   * 
   * @return base {@link ASTNode2}
   */
  public ASTNode2 getBase() {
    if (getChildCount() > 1) {
      return getLeftChild();
    }
    return getType() == Type.FUNCTION_LOG ? new ASTCnIntegerNode(10) : getLeftChild();
  }

  /**
   * Return the value of this {@link ASTLogarithmNode}
   * 
   * @return value {@link ASTNode2}
   */
  public ASTNode2 getValue() {
    return getRightChild();
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTBinaryFunctionNode#insertChild(int, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public void insertChild(int n, ASTNode2 newChild) {
    if (newChild == null) {
      throw new NullPointerException();
    }
    if (! isSetList()) {
      listOfNodes = new ArrayList<ASTNode2>();
    }
    listOfNodes.add(n, newChild);
    ASTFactory.setParentSBMLObject(newChild, parentSBMLObject);
    newChild.setParent(this);
    if (n == 0 && newChild.getType() == Type.CONSTANT_E) {
      setType(Type.FUNCTION_LN);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#isAllowableType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public boolean isAllowableType(Type type) {
    return type == Type.FUNCTION_LOG || type == Type.FUNCTION_LN;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTBinaryFunctionNode#isSetLeftChild()
   */
  @Override
  public boolean isSetLeftChild() {
    switch(getType()) {
    case FUNCTION_LOG:
      return true;
    default:
      return getChildCount() > 0;
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTBinaryFunctionNode#isSetRightChild()
   */
  @Override
  public boolean isSetRightChild() {
    switch(getType()) {
    case FUNCTION_LOG:
      return getChildCount() > 0;
    default:
      return getChildCount() > 1;
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTBinaryFunctionNode#setLeftChild(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public void setLeftChild(ASTNode2 child) {
    super.setLeftChild(child);
    setType(child.getType() == Type.CONSTANT_E ? Type.FUNCTION_LN : Type.FUNCTION_LOG);
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
