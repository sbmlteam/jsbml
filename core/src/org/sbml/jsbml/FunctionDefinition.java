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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml;

import java.text.MessageFormat;

/**
 * Represents the functionDefinition XML element of a SBML file. Since
 * {@link FunctionDefinition}s were introduced to SBML in Level 2, this
 * class must not be used for models in Level 1.
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @since 0.8
 * 
 */
public class FunctionDefinition extends AbstractMathContainer implements
CallableSBase, UniqueNamedSBase, NamedSBase {

  /**
   * Error message to indicate that an incorrect {@link ASTNode.Type} has been passed
   * to a method.
   */
  private static final String ILLEGAL_ASTNODE_TYPE_MSG = "Math element is expected to be of type {0}, but given is {1}.";
  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 5103621145642898899L;

  /**
   * Creates a FunctionDefinition instance. By default, id and name are {@code null}.
   */
  public FunctionDefinition() {
    super();
  }

  /**
   * Creates a FunctionDefinition instance from a given FunctionDefinition.
   * 
   * @param sb the {@link FunctionDefinition} to clone
   */
  public FunctionDefinition(FunctionDefinition sb) {
    super(sb);

    if (sb.isSetId()) {
      setId(sb.getId());
    }
    if (sb.isSetName()) {
      setName(sb.getName());
    }
  }

  /**
   * Creates a FunctionDefinition instance from a level and version. By
   * default, name is {@code null}.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public FunctionDefinition(int level, int version) {
    super(level, version);
    if (getLevel() < 2) {
      throw new IllegalArgumentException(MessageFormat.format(
        "Cannot create a {0} with Level = {1,number,integer}.", getElementName(),
        getLevel()));
    }
  }

  /**
   * Creates a FunctionDefinition instance from an id, ASTNode, level and
   * version. By default, name is {@code null}. If the ASTNode is not of type lambda,
   * an IllegalArgumentException is thrown.
   * 
   * @param id the function definition id
   * @param lambda the ASTNode representing the function math
   * @param level the SBML level
   * @param version the SBML version
   */
  public FunctionDefinition(String id, ASTNode lambda, int level, int version) {
    super(lambda, level, version);
    if (!lambda.isLambda()) {
      throw new IllegalArgumentException(MessageFormat.format(
        ILLEGAL_ASTNODE_TYPE_MSG,
        ASTNode.Type.LAMBDA, lambda.getType()));
    }
    if (id != null) {
      setId(id);
    }
  }

  /**
   * Creates a FunctionDefinition instance from an id, level and version.
   * 
   * @param id the function definition id
   * @param level the SBML level
   * @param version the SBML version
   */
  public FunctionDefinition(String id, int level, int version) {
    super(level, version);
    if (id != null) {
      setId(id);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#clone()
   */
  @Override
  public FunctionDefinition clone() {
    return new FunctionDefinition(this);
  }

  /**
   * Get the nth argument to this function.
   * 
   * Callers should first find out the number of arguments to the function by
   * calling {@link #getArgumentCount()}.
   * 
   * @param n
   *            an integer index for the argument sought.
   * @return the nth argument (bound variable) passed to this
   *         {@link FunctionDefinition}.
   */
  public ASTNode getArgument(int n) {
    if (getArgumentCount() < n) {
      throw new IndexOutOfBoundsException(String.format(
        "No such argument with index {0,number,integer}.", n));
    }
    return getMath().getChild(n);
  }

  /**
   * Get the argument named name to this {@link FunctionDefinition}.
   * 
   * @param name
   *            the exact name (case-sensitive) of the sought-after argument
   * @return the argument (bound variable) having the given name, or null if
   *         no such argument exists.
   */
  public ASTNode getArgument(String name) {
    ASTNode arg = null;
    for (int i = 0; i < getArgumentCount(); i++) {
      arg = getArgument(i);
      if (arg.isString() && arg.getName().equals(name)) {
        return arg;
      }
    }
    return null;
  }

  /**
   * Get the mathematical expression that is the body of this
   * {@link FunctionDefinition} object.
   * 
   * @return the body of this {@link FunctionDefinition} as an Abstract Syntax
   *         Tree, or null if no body is defined.
   */
  public ASTNode getBody() {
    return isSetMath() ? getMath().getRightChild() : null;
  }

  /**
   * Get the number of arguments (bound variables) taken by this
   * {@link FunctionDefinition}.
   * 
   * @return the number of arguments (bound variables) that must be passed to
   *         this {@link FunctionDefinition}.
   * @libsbml.deprecated use {@link #getArgumentCount()}
   */
  public int getNumArguments() {
    return getArgumentCount();
  }

  /**
   * Get the number of arguments (bound variables) taken by this
   * {@link FunctionDefinition}.
   * 
   * @return the number of arguments (bound variables) that must be passed to
   *         this {@link FunctionDefinition}.
   */
  public int getArgumentCount() {
    return isSetMath() && (getMath().getChildCount() > 1)
        ? getMath().getChildCount() - 1 : 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getParent()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ListOf<FunctionDefinition> getParent() {
    return (ListOf<FunctionDefinition>) super.getParent();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractMathContainer#setMath(org.sbml.jsbml.ASTNode)
   */
  @Override
  public void setMath(ASTNode math) {

    if (!math.isLambda()) {
      if (!isReadingInProgress()) {
        throw new IllegalArgumentException(MessageFormat.format(
            ILLEGAL_ASTNODE_TYPE_MSG, ASTNode.Type.LAMBDA, math.getType()));
      }
    }
    super.setMath(math);
  }

}
