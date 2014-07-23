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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.filters.Filter;


/**
 * An Abstract Syntax Tree (AST) node representing a function
 * in a mathematical expression.
 * 
 * @author Victor Kofia
 * @version $Rev$
 * @since 1.0
 * @date May 30, 2014
 */
public class ASTFunction extends AbstractASTNode {

  /**
   * 
   */
  private static final long serialVersionUID = -6517879717740815227L;
  
  /**
   * A {@link Logger} for this class.
   */
  private static transient final Logger logger = Logger.getLogger(ASTFunction.class);

  /**
   * Sets the Parent of the node and its children to the given value
   *
   * @param node the orphan node
   * @param parent the parent
   */
  static void setParentSBMLObject(ASTNode2 node, MathContainer parent) {
    //TODO: notify listeners only one time
    node.setParent(parent);
    setParentSBMLObject(node, parent, 0);
  }

  /**
   * Sets the parent of the node and its children to the given value
   *
   * @param node the orphan node
   * @param parent the parent
   * @param depth the current depth in the {@link ASTNode2} tree.
   *            It is just here for testing purposes to track the depth in the tree
   *            during the process.
   */
  protected static void setParentSBMLObject(ASTNode2 node, MathContainer parent,
    int depth) {
    //TODO: notify listeners only one time. parent may have already notified. 
  }

  /**
   * Child nodes.
   */
  protected List<ASTNode2> listOfNodes;

  /**
   * The container that holds this ASTFunctionNode.
   */
  protected MathContainer parentSBMLObject;
  
  /**
   * The name of the MathML element represented by this
   * {@link ASTFunction}.
   */
  private String name;

  /**
   * Creates a new {@link ASTFunction} that lacks a pointer
   * to its containing {@link MathContainer}.
   */
  public ASTFunction() {
    super();
    setParentSBMLObject(null);
    listOfNodes = null;
    setName(null);
    setType(Type.FUNCTION);
    initDefaults();
  }
  
  /**
   * Copy constructor; Creates a deep copy of the given {@link ASTFunction}.
   * 
   * @param other
   *            the {@link ASTFunction} to be copied.
   */
  public ASTFunction(ASTFunction other) {
    super(other);
    if (other.isSetName()) {
      setName(other.getName());    
    }
    initDefaults();
    if (other.getChildCount() > 0) {
      if (!isSetList()) {
        listOfNodes = new ArrayList<ASTNode2>();
      } else {
        for (int i = 0; i < other.getChildCount(); i++) {
          ASTNode2 child = other.getListOfNodes().get(i);
          ASTNode2 c = (ASTNode2) child.clone();
          c.setParent(this);
          listOfNodes.add(c);
        }
      }
    }
  }

  /**
   * Creates a new {@link ASTFunction} with a pointer
   * to the specified {@link MathContainer}.
   */
  public ASTFunction(MathContainer container) {
    super(container);
    listOfNodes = null;
    setType(Type.FUNCTION);
    initDefaults();
  }

  /**
   * Adds a child to this node.
   * 
   * @param child
   *            the node to add as child.
   *            
   * @throws NullPointerException if the child is null          
   */
  public void addChild(ASTNode2 child) {
    if (child == null) {
      throw new NullPointerException();
    }
    if (! isSetList())  {
      listOfNodes = new ArrayList<ASTNode2>();
    } 
    listOfNodes.add(child);
    setParentSBMLObject(child, parentSBMLObject, 0);
    child.setParent(this);
    child.fireNodeAddedEvent();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#clone()
   */
  @Override
  public ASTFunction clone() {
    return new ASTFunction(this);
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
    ASTFunction other = (ASTFunction) obj;
    if (listOfNodes == null) {
      if (other.listOfNodes != null)
        return false;
    } else if (!listOfNodes.equals(other.listOfNodes))
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (parentSBMLObject == null) {
      if (other.parentSBMLObject != null)
        return false;
    } else if (!parentSBMLObject.equals(other.parentSBMLObject))
      return false;
    return true;
  }


  /**
   * Goes through the formula and identifies all global parameters that are
   * referenced by this rate equation.
   * 
   * @return all global parameters that are referenced by this rate equation.
   */
  public List<Parameter> findReferencedGlobalParameters() {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public ASTNode2 getChildAt(int childIndex) {
    if (!isSetList()) {
      throw new IndexOutOfBoundsException(childIndex + " < child count");
    }
    return listOfNodes.get(childIndex);
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    return isSetList() ? listOfNodes.size() : 0;
  }

  /**
   * Returns the list of children of the current ASTFunction.
   * 
   * @return the list of children of the current ASTFunction.
   */
  public List<ASTNode2> getChildren() {
    return getListOfNodes();
  }

  /**
   * Returns the list of children of the current ASTFunction.
   * 
   * @return the list of children of the current ASTFunction.
   */
  public List<ASTNode2> getListOfNodes() {
    return listOfNodes;
  }

  /**
   * Returns the list of children of the current ASTFunction that satisfy the
   * given filter.
   * 
   * @param filter
   * @return the list of children of the current ASTFunction that satisfy the
   *         given filter.
   */
  public List<ASTNode2> getListOfNodes(Filter filter) {
    if (isSetList()) {
      ArrayList<ASTNode2> filteredList = new ArrayList<ASTNode2>();
      for (ASTNode2 node : listOfNodes) {
        if (filter.accepts(node)) {
          filteredList.add(node);
        }
      }
      return filteredList;
    } else {
      return null;
    }
  }

  /**
   * Returns the name of the MathML element represented by
   * this {@link ASTFunction}
   * 
   * @return String name
   */
  public String getName() {
    if (isSetName()) {
      return name;
    }
    PropertyUndefinedError error = new PropertyUndefinedError("name", this);
    if (isStrict()) {
      throw error;
    }
    logger.warn(error);
    return "";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result
      + ((listOfNodes == null) ? 0 : listOfNodes.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result
      + ((parentSBMLObject == null) ? 0 : parentSBMLObject.hashCode());
    return result;
  }
  
  /**
   * Initializes the default values/attributes of the node.
   */
  private void initDefaults() {
    ASTNode2 old = this;
    if (! isSetList()) {
      listOfNodes = new ArrayList<ASTNode2>();
    } else {
      for (int i = listOfNodes.size() - 1; i >= 0; i--) {
        // This also removes the pointer from the previous child to this object, i.e., its previous parent node.
        ASTNode2 removed = listOfNodes.remove(i);
        removed.unsetParentSBMLObject();
        removed.fireNodeRemovedEvent();
      }
    }
    firePropertyChange(TreeNodeChangeEvent.initialValue, old, this);
  }

  /**
   * Inserts the given {@link ASTNode2} at point n in the list of children of this
   * {@link ASTNode2}. Inserting a child within an {@link ASTNode2} may result in an inaccurate
   * representation.
   * 
   * @param n
   *            long the index of the {@link ASTNode2} being added
   * @param newChild
   *            {@link ASTNode2} to insert as the n<sup>th</sup> child
   *            
   * @throws NullPointerException if the child is null      
   *     
   */
  public void insertChild(int n, ASTNode2 newChild) {
    if (newChild == null) {
      throw new NullPointerException();
    }
    if (! isSetList()) {
      listOfNodes = new ArrayList<ASTNode2>();
    }
    listOfNodes.add(n, newChild);
    setParentSBMLObject(newChild, parentSBMLObject, 0);
    newChild.setParent(this);
  }

  /**
   * Returns True iff listOfNodes has been set
   * 
   * @param null
   * @return boolean
   */
  protected boolean isSetList() {
    return listOfNodes != null;
  }

  /**
   * Returns True iff name has been set
   * 
   * @return boolean
   */
  public boolean isSetName() {
    return name != null;
  }
  
  /**
   * Adds the given node as a child of this {@link ASTFunction}. This method adds child
   * nodes from right to left.
   * 
   * @param child
   *            an {@code ASTNode2}
   *            
   * @throws NullPointerException if the child is null   
   *        
   */
  public void prependChild(ASTNode2 child) {
    if (child == null) {
      throw new NullPointerException();
    }
    if (! isSetList()) {
      listOfNodes = new ArrayList<ASTNode2>();
    }
    listOfNodes.add(0, child);
    setParentSBMLObject(child, parentSBMLObject, 0);
    child.setParent(this);
  }
  
  /**
   * Removes child n of this {@link ASTFunction}. Removing a child from an 
   * {@link ASTFunction} may result in an inaccurate representation.
   * 
   * @param n
   *            the index of the child to remove
   * @return boolean indicating the success or failure of the operation
   * 
   */
  public boolean removeChild(int n) {
    if (! isSetList()) {
      listOfNodes = new ArrayList<ASTNode2>();
    }
    if ((listOfNodes.size() > n) && (n >= 0)) {
      ASTNode2 removed = listOfNodes.remove(n);
      removed.unsetParentSBMLObject();
      removed.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Replaces occurrences of a name within this {@link ASTNode2} with the
   * name/value/formula represented by the second argument {@link ASTNode2}, e.g., if
   * the formula in this {@link ASTNode2} is x + y; bvar is x and arg is an {@link ASTNode2}
   * representing the real value 3 ReplaceArgument substitutes 3 for x within
   * this {@link ASTNode2}.
   * 
   * @param bvar
   *            a string representing the variable name to be substituted
   * @param arg
   *            an {@link ASTNode2} representing the name/value/formula to substitute
   *        
   */
  public void replaceArgument(String bvar, ASTNode2 arg) {
    if (! isSetList()) {
      listOfNodes = new ArrayList<ASTNode2>();
    }
    int n = 0;
    replaceChild(n, (ASTNode2) arg.clone());
  }

  /**
   * Replaces the n<sup>th</sup> child of this ASTNode2 with the given ASTNode2.
   * 
   * @param n
   *            long the index of the child to replace
   *            
   * @param newChild
   *            {@link ASTNode2} to replace the n<sup>th</sup> child
   *            
   * @return the element previously at the specified position or null if element
   *         in question is non-existent
   *       
   */
  public ASTNode2 replaceChild(int n, ASTNode2 newChild) {
    if (! isSetList()) {
      listOfNodes = new ArrayList<ASTNode2>();
    }
    // Removing the node at position n
    ASTNode2 child = null;
    child = getChildAt(n);
    removeChild(n);

    // Adding the new child at position n
    insertChild(n, newChild);
    // Return removed child
    return child;
  }

  /**
   * Set the name of the MathML element represented by
   * this {@link ASTFunction}
   * 
   * @param String name
   */
  public void setName(String name) {
    String old = this.name;
    this.name = name;
    firePropertyChange(TreeNodeChangeEvent.name, old, this.name);
  }

  /**
   * <p>
   * Swaps the children of this {@link ASTFunction} with the children of that
   * {@link ASTFunction}.
   * </p>
   * <p>
   * Unfortunately, when swapping child nodes, we have to recursively traverse
   * the entire subtrees in order to make sure that all pointers to the parent
   * SBML object are correct. However, this must only be done if the parent SBML
   * object of that differs from the one surrounding this node.
   * </p>
   * <p>
   * In any case, the pointer from each sub-node to its parent must be changed.
   * In contrast to other SBML elements, {@link ASTFunction}s have sub-nodes as
   * direct children, i.e., there is no child called 'ListOfNodes'. The
   * {@code setParent} method is also not recursive.
   * </p>
   * <p>
   * However, this might cause many calls to listeners.
   * </p>
   * 
   * @param that
   *        the other node whose children should be used to replace this
   *        node's children
   */
  public void swapChildren(ASTFunction that) {
    if (! isSetList()) {
      listOfNodes = new ArrayList<ASTNode2>();
    }
    List<ASTNode2> swap = that.listOfNodes;
    that.listOfNodes = listOfNodes;
    listOfNodes = swap;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ASTFunction [listOfNodes=");
    builder.append(listOfNodes);
    builder.append(", parentSBMLObject=");
    builder.append(parentSBMLObject);
    builder.append(", name=");
    builder.append(name);
    builder.append("]");
    return builder.toString();
  }

}
