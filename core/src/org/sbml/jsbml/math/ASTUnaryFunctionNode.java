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
import java.util.List;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.math.compiler.ASTNode2Value;
import org.sbml.jsbml.math.compiler.FormulaCompiler;
import org.sbml.jsbml.math.compiler.LaTeXCompiler;
import org.sbml.jsbml.math.compiler.MathMLXMLStreamCompiler;

/**
 * An Abstract Syntax Tree (AST) node representing a function with only one
 * parameter
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTUnaryFunctionNode extends ASTFunction {

  /**
   * 
   */
  private static final long serialVersionUID = -8088831456874690229L;

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTUnaryFunctionNode.class);

  /**
   * Creates a new {@link ASTUnaryFunctionNode}.
   */
  public ASTUnaryFunctionNode() {
    super();
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTUnaryFunctionNode}.
   * 
   * @param node
   *            the {@link ASTUnaryFunctionNode} to be copied.
   */
  public ASTUnaryFunctionNode(ASTUnaryFunctionNode node) {
    super(node);
  }

  /**
   * Creates a new {@link ASTUnaryFunctionNode} of type {@link Type}
   * @param type
   */
  public ASTUnaryFunctionNode(Type type) {
    super();
    setType(type);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#addChild(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public void addChild(ASTNode2 child) {
    if (isSetChild()) {
      if (isStrict()) {
        throw new IndexOutOfBoundsException("max child limit exceeded");
      }
      logger.debug("Max child limit exceeded. To add more children " +
          "to ASTUnaryFunctionNode set strictness to false.");
    }
    if (! isSetList())  {
      listOfNodes = new ArrayList<ASTNode2>(1);
    }
    listOfNodes.add(child);
    ASTFactory.setParentSBMLObject(child, parentSBMLObject);
    child.setParent(this);
    child.fireNodeAddedEvent();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#clone()
   */
  @Override
  public ASTUnaryFunctionNode clone() {
    return new ASTUnaryFunctionNode(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler) {
    ASTNode2Value<?> value = null;
    switch(getType()) {
    case FUNCTION_ABS:
      value = compiler.abs(getChild());
      break;
    case FUNCTION_CEILING:
      value = compiler.ceiling(getChild());
      break;
    case FUNCTION_EXP:
      value = compiler.exp(getChild());
      break;
    case FUNCTION_FACTORIAL:
      value = compiler.factorial(getChild());
      break;
    case FUNCTION_FLOOR:
      value = compiler.floor(getChild());
      break;
    default: // UNKNOWN:
      value = compiler.unknownValue();
      break;
    }
    return processValue(value);
  }

  /**
   * Get the child of this node
   * 
   * @return {@link ASTNode2} child
   */
  public ASTNode2 getChild() {
    return getChildAt(0);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#getChildAt(int)
   */
  @Override
  public ASTNode2 getChildAt(int childIndex) {
    if (!isSetList() || (isSetList() && isStrict() && childIndex > 1)) {
      throw new IndexOutOfBoundsException(childIndex + " < child count");
    }
    return listOfNodes.get(childIndex);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#insertChild(int, org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public void insertChild(int n, ASTNode2 newChild) {
    if (isSetChild()) {
      if (isStrict()) {
        throw new IndexOutOfBoundsException("Max child limit exceeded");
      }
      logger.debug("Max child limit exceeded");
    }
    if (! isSetList()) {
      listOfNodes = new ArrayList<ASTNode2>(1);
    }
    listOfNodes.add(n, newChild);
    ASTFactory.setParentSBMLObject(newChild, parentSBMLObject);
    newChild.setParent(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#isAllowableType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public boolean isAllowableType(Type type) {
    if (type != null) {
      switch(type) {
      case FUNCTION_ABS:
      case FUNCTION_CEILING:
      case FUNCTION_EXP:
      case FUNCTION_FACTORIAL:
      case FUNCTION_FLOOR:
        return true;
      default:
        break;
      }
    }
    return false;
  }

  /**
   * Returns true iff the child of this node has been set
   * 
   * @return boolean
   */
  public boolean isSetChild() {
    return getChildCount() > 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#prependChild(org.sbml.jsbml.math.ASTNode2)
   */
  @Override
  public void prependChild(ASTNode2 child) {
    if (isSetChild()) {
      if (isStrict()) {
        throw new IndexOutOfBoundsException("max child limit exceeded");
      }
      logger.debug("Max child limit exceeded. To add more children " +
          "to ASTUnaryFunctionNode set strictness to false.");
    }
    if (! isSetList()) {
      listOfNodes = new ArrayList<ASTNode2>(1);
    }
    listOfNodes.add(0, child);
    ASTFactory.setParentSBMLObject(child, parentSBMLObject);
    child.setParent(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#removeChild(int)
   */
  @Override
  public boolean removeChild(int n) {
    if ((! isSetChild()) || (isStrict() && isSetChild() && n > 0)) {
      return false;
    }
    if (! isSetList()) {
      listOfNodes = new ArrayList<ASTNode2>(1);
    }
    ASTNode2 removed = listOfNodes.remove(n);
    removed.unsetParentSBMLObject();
    removed.fireNodeRemovedEvent();
    return true;
  }

  /**
   * Set the child of this node
   * 
   * @param child
   */
  public void setChild(ASTNode2 child) {
    if (isSetChild()) {
      replaceChild(0, child);
    } else {
      addChild(child);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTFunction#swapChildren(org.sbml.jsbml.math.ASTFunction)
   */
  @Override
  public void swapChildren(ASTFunction that) {
    if (isStrict() && that.getChildCount() > 1) {
      throw new IndexOutOfBoundsException("max child limit exceeded");
    }
    if (that.getChildCount() > 1) {
      logger.debug("Max child limit exceeded. To add more children " +
          "to ASTUnaryFunctionNode set strictness to false.");
    }
    List<ASTNode2> swap = that.listOfNodes;
    that.listOfNodes = listOfNodes;
    listOfNodes = swap;
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
