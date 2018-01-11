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
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.math.compiler.ASTNode2Value;
import org.sbml.jsbml.math.compiler.FormulaCompiler;
import org.sbml.jsbml.math.compiler.LaTeXCompiler;
import org.sbml.jsbml.math.compiler.MathMLXMLStreamCompiler;
import org.sbml.jsbml.util.TreeNodeChangeEvent;


/**
 * An Abstract Syntax Tree (AST) node representing an Integer
 * in a mathematical expression
 * 
 * @author Victor Kofia
 * @since 1.0
 */
public class ASTCnIntegerNode extends ASTCnNumberNode<Integer> {

  /**
   * 
   */
  private static final long serialVersionUID = 5478874063299110266L;

  /**
   * A {@link Logger} for this class.
   */
  private static final Logger logger = Logger.getLogger(ASTCnIntegerNode.class);

  /**
   * Creates a new {@link ASTCnIntegerNode} that lacks a pointer
   * to its containing {@link MathContainer}.
   */
  public ASTCnIntegerNode() {
    super();
    setType(Type.INTEGER);
  }

  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTCnIntegerNode}.
   * 
   * @param node
   *            the {@link ASTCnIntegerNode} to be copied.
   */
  public ASTCnIntegerNode(ASTCnIntegerNode node) {
    super(node);
    setType(Type.INTEGER);
  }

  /**
   * Creates a new {@link ASTCnIntegerNode} with int value.
   * @param value
   */
  public ASTCnIntegerNode(int value) {
    this();
    setInteger(value);
  }

  @Override
  public ASTCnIntegerNode clone() {
    return new ASTCnIntegerNode(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler) {
    ASTNode2Value<?> value = isSetUnits() ? compiler.compile(getInteger(), getUnits().toString())
      : compiler.compile(getInteger(), null);
    return processValue(value);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ASTCnIntegerNode other = (ASTCnIntegerNode) obj;
    if (number == null) {
      if (other.number != null) {
        return false;
      }
    } else if (!number.equals(other.number)) {
      return false;
    }
    return true;
  }

  /**
   * Get the integer value of this node. Throws PropertyUndefinedError
   * if no integer value has been defined.
   * 
   * @return int integer
   * @throws PropertyUndefinedError
   */
  public int getInteger() {
    if (isSetInteger()) {
      return number;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("integer", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTCnNumberNode#isAllowableType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public boolean isAllowableType(Type type) {
    return type == Type.INTEGER;
  }

  /**
   * Returns True iff value has been set
   * 
   * @return boolean
   */
  public boolean isSetInteger() {
    return number != null;
  }

  /**
   * Set the value of this node
   * 
   * @param value
   */
  public void setInteger(int value) {
    Integer old = number;
    number = value;
    firePropertyChange(TreeNodeChangeEvent.number, old, number);
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
