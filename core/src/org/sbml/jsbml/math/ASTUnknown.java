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

import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ASTNode.Type;
import org.sbml.jsbml.MathContainer;
import org.sbml.jsbml.math.compiler.ASTNode2Compiler;
import org.sbml.jsbml.math.compiler.ASTNode2Value;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.filters.Filter;

/**
 * An {@link ASTNode2} with specified neutral behavior. Serves as a
 * stand-in for {@link ASTNode2} when the empty constructor is called
 * in {@link ASTNode}.
 * 
 * {@link ASTUnknown} is a singleton and must be accessed through the
 * {@code getInstance()} method.
 * 
 * @author Victor Kofia
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class ASTUnknown implements ASTNode2 {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 7504401136695608469L;

  /**
   * 
   */
  private static ASTUnknown instance;

  /**
   * 
   * @return
   */
  public static ASTUnknown getInstance() {
    if (instance == null) {
      instance = new ASTUnknown();
    }
    return instance;
  }

  /**
   * Create a new {@link ASTUnknown} object. This constructor is set to
   * private in order to prevent more than one {@link ASTUnknown} from
   * being constructed.
   */
  private ASTUnknown() {
    super();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#addAllChangeListeners(java.util.Collection)
   */
  @Override
  public boolean addAllChangeListeners(
    Collection<TreeNodeChangeListener> listeners) {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#addTreeNodeChangeListener(org.sbml.jsbml.util.TreeNodeChangeListener)
   */
  @Override
  public void addTreeNodeChangeListener(TreeNodeChangeListener listener) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#addTreeNodeChangeListener(org.sbml.jsbml.util.TreeNodeChangeListener, boolean)
   */
  @Override
  public void addTreeNodeChangeListener(TreeNodeChangeListener listener,
    boolean recursive) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#children()
   */
  @Override
  public Enumeration<? extends TreeNode> children() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#clearUserObjects()
   */
  @Override
  public void clearUserObjects() {
    // TODO Auto-generated method stub

  }

  @Override
  public ASTNode2 clone() {
    return this;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#compile(org.sbml.jsbml.math.compiler.ASTNode2Compiler)
   */
  @Override
  public ASTNode2Value<?> compile(ASTNode2Compiler compiler) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#containsUserObjectKey(java.lang.Object)
   */
  @Override
  public boolean containsUserObjectKey(Object key) {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#filter(org.sbml.jsbml.util.filters.Filter)
   */
  @Override
  public List<? extends TreeNode> filter(Filter filter) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#filter(org.sbml.jsbml.util.filters.Filter, boolean)
   */
  @Override
  public List<? extends TreeNode> filter(Filter filter,
    boolean retainInternalNodes) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#filter(org.sbml.jsbml.util.filters.Filter, boolean, boolean)
   */
  @Override
  public List<? extends TreeNode> filter(Filter filter,
    boolean retainInternalNodes, boolean prune) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#fireNodeAddedEvent()
   */
  @Override
  public void fireNodeAddedEvent() {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#fireNodeRemovedEvent()
   */
  @Override
  public void fireNodeRemovedEvent() {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#firePropertyChange(java.lang.String, java.lang.Object, java.lang.Object)
   */
  @Override
  public void firePropertyChange(String propertyName, Object oldValue,
    Object newValue) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#getId()
   */
  @Override
  public String getId() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
   */
  @Override
  public int getIndex(TreeNode node) {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#getListOfTreeNodeChangeListeners()
   */
  @Override
  public List<TreeNodeChangeListener> getListOfTreeNodeChangeListeners() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#getMathMLClass()
   */
  @Override
  public String getMathMLClass() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getParent()
   */
  @Override
  public TreeNode getParent() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#getParentSBMLObject()
   */
  @Override
  public MathContainer getParentSBMLObject() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#getRoot()
   */
  @Override
  public TreeNode getRoot() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#getStyle()
   */
  @Override
  public String getStyle() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#getTreeNodeChangeListenerCount()
   */
  @Override
  public int getTreeNodeChangeListenerCount() {
    // TODO Auto-generated method stub
    return 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#getType()
   */
  @Override
  public Type getType() {
    return Type.UNKNOWN;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#getUserObject(java.lang.Object)
   */
  @Override
  public Object getUserObject(Object key) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#isAllowableType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public boolean isAllowableType(Type type) {
    return type == Type.UNKNOWN;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#isLeaf()
   */
  @Override
  public boolean isLeaf() {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#isRoot()
   */
  @Override
  public boolean isRoot() {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#isSetId()
   */
  @Override
  public boolean isSetId() {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#isSetMathMLClass()
   */
  @Override
  public boolean isSetMathMLClass() {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#isSetParent()
   */
  @Override
  public boolean isSetParent() {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#isSetParentSBMLObject()
   */
  @Override
  public boolean isSetParentSBMLObject() {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#isSetStyle()
   */
  @Override
  public boolean isSetStyle() {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#isSetType()
   */
  @Override
  public boolean isSetType() {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#isSetUserObjects()
   */
  @Override
  public boolean isSetUserObjects() {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#isStrict()
   */
  @Override
  public boolean isStrict() {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#putUserObject(java.lang.Object, java.lang.Object)
   */
  @Override
  public void putUserObject(Object key, Object userObject) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#removeAllTreeNodeChangeListeners()
   */
  @Override
  public void removeAllTreeNodeChangeListeners() {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#removeFromParent()
   */
  @Override
  public boolean removeFromParent() {
    // TODO Auto-generated method stub
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#removeTreeNodeChangeListener(org.sbml.jsbml.util.TreeNodeChangeListener)
   */
  @Override
  public void removeTreeNodeChangeListener(TreeNodeChangeListener listener) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#removeTreeNodeChangeListener(org.sbml.jsbml.util.TreeNodeChangeListener, boolean)
   */
  @Override
  public void removeTreeNodeChangeListener(TreeNodeChangeListener listener,
    boolean recursive) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#removeUserObject(java.lang.Object)
   */
  @Override
  public Object removeUserObject(Object key) {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#setId(java.lang.String)
   */
  @Override
  public void setId(String id) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#setMathMLClass(java.lang.String)
   */
  @Override
  public void setMathMLClass(String mathMLClass) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#setParent(javax.swing.tree.TreeNode)
   */
  @Override
  public void setParent(TreeNode astNode2) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#setParentSBMLObject(org.sbml.jsbml.MathContainer)
   */
  @Override
  public void setParentSBMLObject(MathContainer container) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#setStrictness(boolean)
   */
  @Override
  public void setStrictness(boolean strict) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#setStyle(java.lang.String)
   */
  @Override
  public void setStyle(String style) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#setType(java.lang.String)
   */
  @Override
  public void setType(String typeStr) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#setType(org.sbml.jsbml.ASTNode.Type)
   */
  @Override
  public void setType(Type type) {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#toFormula()
   */
  @Override
  public String toFormula() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#toLaTeX()
   */
  @Override
  public String toLaTeX() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#toMathML()
   */
  @Override
  public String toMathML() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.math.ASTNode2#unsetParentSBMLObject()
   */
  @Override
  public void unsetParentSBMLObject() {
    // TODO Auto-generated method stub

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.TreeNodeWithChangeSupport#userObjectKeySet()
   */
  @Override
  public Set<Object> userObjectKeySet() {
    // TODO Auto-generated method stub
    return null;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "ASTUnknown []";
  }

  @Override
  public void removeAllTreeNodeChangeListeners(boolean recursive) {
    // TODO Auto-generated method stub
  }

  @Override
  public boolean addAllChangeListeners(
    Collection<TreeNodeChangeListener> listeners, boolean recursive) {
    // TODO Auto-generated method stub
    return false;
  }

}
