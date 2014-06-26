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
import org.sbml.jsbml.PropertyUndefinedError;
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
   * The style of this MathML element
   */
  protected String id;
  
  /**
   * The style of this MathML element
   */
  protected String style;

  /**
   * Creates an empty {@link AbstractTreeNode}
   */
  public AbstractASTNode() {
    super();
    setId(null);
    setType(Type.UNKNOWN);
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
    setId(ast.getId());
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
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (!super.equals(obj))
      return false;
    if (getClass() != obj.getClass())
      return false;
    AbstractASTNode other = (AbstractASTNode) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (parentSBMLObject == null) {
      if (other.parentSBMLObject != null)
        return false;
    } else if (!parentSBMLObject.equals(other.parentSBMLObject))
      return false;
    if (strict != other.strict)
      return false;
    if (style == null) {
      if (other.style != null)
        return false;
    } else if (!style.equals(other.style))
      return false;
    if (type != other.type)
      return false;
    return true;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public abstract ASTNode2 getChildAt(int childIndex);

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#getId()
   */
  @Override
  public String getId() {
    if (isSetId()) {
      return id;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("id", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return "";
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#getParentSBMLObject()
   */
  @Override
  public MathContainer getParentSBMLObject() {
    return parentSBMLObject;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#getStyle()
   */
  @Override
  public String getStyle() {
    if (isSetStyle()) {
      return style;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("style", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return "";
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#getType()
   */
  @Override
  public Type getType() {
      return type;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result
      + ((parentSBMLObject == null) ? 0 : parentSBMLObject.hashCode());
    result = prime * result + (strict ? 1231 : 1237);
    result = prime * result + ((style == null) ? 0 : style.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#isSetId()
   */
  @Override
  public boolean isSetId() {
    return id != null;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#isSetParentSBMLObject()
   */
  @Override
  public boolean isSetParentSBMLObject() {
    return id != null;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#isSetStyle()
   */
  @Override
  public boolean isSetStyle() {
    return style != null;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#isStrict()
   */
  @Override
  public boolean isStrict() {
    return strict;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#setId(java.lang.String)
   */
  @Override
  public void setId(String id) {
    String oldValue = this.id;
    this.id = id;
    firePropertyChange(TreeNodeChangeEvent.id, oldValue, id);
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
  
  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#setStyle(java.lang.String)
   */
  @Override
  public void setStyle(String style) {
    String oldValue = this.style;
    this.style = style;
    firePropertyChange(TreeNodeChangeEvent.style, oldValue, style);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#setType(java.lang.String)
   */
  @Override
  public void setType(String typeStr) {
    Type type = Type.getTypeFor(typeStr);
    setType(type);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#setType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public void setType(Type type) {
    Type oldValue = getType();
    this.type = type;
    firePropertyChange(TreeNodeChangeEvent.type, oldValue, type);
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#reduceToBinary()
   */
  public void reduceToBinary() {
    return;
  }

}