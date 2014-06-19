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

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.AbstractTreeNode;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.math.compiler.AbstractASTNodeCompiler;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.TreeNodeWithChangeSupport;
import org.sbml.jsbml.util.compilers.ASTNodeValue;


/**
 * A node in the Abstract Syntax Tree (AST) representation
 * of a mathematical expression.
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public abstract class AbstractASTNode extends AbstractTreeNode implements ASTNode2 {

  /**
   * A {@link Logger} for this class.
   */
  private static transient final Logger logger = Logger.getLogger(ASTNode.class);

  /**
   * The container that holds this AbstractASTNode.
   */
  protected MathContainer parentSBMLObject;

  /**
   * Specifies strictness. When true, ASTUnaryFunction and ASTBinaryFunction
   * nodes can only contain the specified # of children. When false, there is
   * a bit of leeway (i.e. ASTUnaryFunction can contain more than one child)
   * (not recommended).
   * 
   * @return boolean
   */
  protected boolean strict;

  /**
   * The type of this node
   */
  protected Type type;

  /**
   * Creates an empty {@link AbstractTreeNode}
   */
  public AbstractASTNode() {
    super();
    setType(null);
    setParentSBMLObject(null);
    setStrictness(true);
  }

  /**
   * Constructor for cloning {@link AbstractTreeNode}
   * 
   * @param ASTFunction astFunction
   */
  public AbstractASTNode(ASTNode2 ast) {
    super(ast);
    setType(ast.getType());
    setParentSBMLObject(ast.getParentSBMLObject());
    setStrictness(ast.isStrict());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.util.compilers.ASTNode2Compiler)
   */
  @Override
  public ASTNodeValue compile(AbstractASTNodeCompiler compiler) {
    // TODO: We can consider to create an AbstractASTNode2Compiler this time -> Needs further discussion. The compile function can be overloaded and hence very specific for certain sub-types of ASTNode2.
    return compiler.compile(this);
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public abstract ASTNode2 getChildAt(int childIndex);

  /**
   * This method is convenient when holding an object nested inside other
   * objects in an SBML model. It allows direct access to the
   * {@link MathContainer}; element containing it. From this
   * {@link MathContainer} even the overall {@link Model} can be accessed.
   * 
   * @return the parent SBML object.
   */
  @Override
  public MathContainer getParentSBMLObject() {
    return parentSBMLObject;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#getType()
   */
  @Override
  public Type getType() {
    return type;
  }

  /**
   * Specifies strictness. When true, ASTUnaryFunction and ASTBinaryFunction
   * nodes can only contain the specified # of children. When false, there is
   * a bit of leeway (i.e. ASTUnaryFunction can contain more than one child)
   * (not recommended).
   * 
   * @return boolean
   */
  @Override
  public boolean isStrict() {
    return strict;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#setParent(javax.swing.tree.TreeNode)
   */
  @Override
  public void setParent(TreeNode parent) {
    TreeNode oldValue = this.parent;
    this.parent = parent;
    if (parent instanceof TreeNodeWithChangeSupport) {
      addAllChangeListeners(((TreeNodeWithChangeSupport) parent).getListOfTreeNodeChangeListeners());
    }
    firePropertyChange(TreeNodeChangeEvent.parentSBMLObject, oldValue, this.parent);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#setParentSBMLObject(org.sbml.jsbml.MathContainer)
   */
  @Override
  public void setParentSBMLObject(MathContainer container) {
    for (int i = 0; i < getChildCount(); i++) {
      ASTNode2 child = getChildAt(i);
      child.setParentSBMLObject(container);
    }
    MathContainer oldParentSBMLObject = parentSBMLObject;
    parentSBMLObject = container;
    firePropertyChange(TreeNodeChangeEvent.parentSBMLObject, oldParentSBMLObject, parentSBMLObject);
  }

  /**
   * Set the strictness of this node
   * 
   * @param boolean strict
   * @return null
   */
  private void setStrictness(boolean strict) {
    this.strict = strict;
  }

  /**
   * Set the type of the MathML element represented by this ASTCnNumberNode
   * 
   * @param Type type
   */
  public void setType(Type type) {
    this.type = type;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#toFormula()
   */
  @Override
  public String toFormula() throws SBMLException {
    //TODO: Implement using a call to a specialized ASTNode2Compiler
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#toLaTeX()
   */
  @Override
  public String toLaTeX() throws SBMLException {
    //TODO: Implement using a call to a specialized ASTNode2Compiler
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#toMathML()
   */
  @Override
  public String toMathML() {
    //TODO: Implement using a call to a specialized ASTNode2Compiler
    return null;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("AbstractASTNode [strict=");
    builder.append(strict);
    builder.append(", type=");
    builder.append(type);
    builder.append("]");
    return builder.toString();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#unsetParentSBMLObject()
   */
  @Override
  public void unsetParentSBMLObject() {
    setParentSBMLObject(null);
  }

}