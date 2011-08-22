/*
 * $Id$
 * $URL$
 *
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2011 jointly by the following organizations: 
 * 1. The University of Tuebingen, Germany 
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
 * 3. The California Institute of Technology, Pasadena, CA, USA 
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation. A copy of the license agreement is provided 
 * in the file named "LICENSE.txt" included with this software distribution 
 * and also available online as <http://sbml.org/Software/JSBML/License>. 
 * ---------------------------------------------------------------------------- 
 */ 
package org.sbml.jsbml;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.util.StringTools;

/**
 * A basic implementation of the {@link TreeNode} interface.
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 0.8
 * @date 11.07.2011
 */
public abstract class AbstractTreeNode implements TreeNode, Serializable,
		Cloneable {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 8629109724566600238L;

	/**
	 * Searches the given child in the list of sub-nodes of the parent element.
	 * 
	 * @param parent
	 * @param child
	 * @return the index of the child in the parent's list of childs or -1 if no
	 *         such child can be found.
	 */
	@SuppressWarnings("unchecked")
	public static int indexOf(TreeNode parent, TreeNode child) {
		if (child == null) {
			throw new IllegalArgumentException("Argument is null.");
		}
		// linear search
		Enumeration<TreeNode> e = parent.children();
		for (int i = 0; e.hasMoreElements(); i++) {
			TreeNode elem = e.nextElement();
			if ((child == elem) || child.equals(elem)) {
				return i;
			}
		}
		// not found => node is not a child.
		return -1;
	}
	
	/**
	 * The parent element of this {@link Annotation}.
	 */
	protected TreeNode parent;

	/**
	 * 
	 */
	public AbstractTreeNode() {
		super();
		this.parent = null;
	}

	/**
	 * @param node
	 */
	public AbstractTreeNode(TreeNode node) {
		this();
		// the parent is not cloned and is left as null
		// The Object will get a parent set as soon as it is added/linked
		// again to a model somehow.		
		// this.parent = node.getParent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#children()
	 */
	public Enumeration<TreeNode> children() {
		return new Enumeration<TreeNode>() {
			/**
			 * Total number of children in this enumeration.
			 */
			private int childCount = getChildCount();
			/**
			 * Current position in the list of children.
			 */
			private int index;

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Enumeration#hasMoreElements()
			 */
			public boolean hasMoreElements() {
				return index < childCount;
			}

			/*
			 * (non-Javadoc)
			 * 
			 * @see java.util.Enumeration#nextElement()
			 */
			public TreeNode nextElement() {
				synchronized (this) {
					if (index < childCount) {
						return getChildAt(index++);
					}
				}
				throw new NoSuchElementException("Enumeration");
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public abstract TreeNode clone();

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// Check if the given object is a pointer to precisely the same object:
		if (super.equals(object)) {
			return true;
		}
		// Check if the given object is of identical class and not null: 
		if ((object == null) || (!getClass().equals(object.getClass()))) {
			return false;
		}
		// Check all child nodes recursively:
		if (object instanceof TreeNode) {
			TreeNode stn = (TreeNode) object;
			int childCount = getChildCount();
			boolean equal = stn.isLeaf() == isLeaf();
			/*
			 * This is not good because cloned AbstractTreeNodes may not point
			 * to the same parent as the original and would hence not be equal
			 * to the cloned object.
			 */
            //	equal &= ((stn.getParent() == null) && isRoot())
            //           || (stn.getParent() == getParent());
			equal &= stn.getChildCount() == childCount;
			if (equal && (childCount > 0)) {
				for (int i = 0; i < childCount; i++) {
					if (!stn.getChildAt(i).equals(getChildAt(i))) {
						return false;
					}
				}
			}
			return equal;
		}
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
	 */
	public int getIndex(TreeNode node) {
		return indexOf(this, node);
	}

	/**
	 * Returns the number of child elements of this {@link TreeNode}.
	 * 
	 * @return the number of children TreeNodes the receiver contains.
	 * @deprecated use {@link #getChildCount()}
	 */
	@Deprecated
	public int getNumChildren() {
		return getChildCount();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getParent()
	 */
	public TreeNode getParent() {
		return parent;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// A constant and arbitrary, sufficiently large prime number:
		final int prime = 769;
		/*
		 * This method is implemented as suggested in the JavaDoc API
		 * documentation of the List interface.
		 */
		
		// Compute the initial hashCode based on the name of the actual class.
		int hashCode = getClass().getName().hashCode();
		/*
		 * The following start wouldn't work because it will compute the
		 * hashCode from the address in memory of the object.
		 */
		// int hashCode = super.hashCode();
		
		// Recursively compute the hashCode for each child node:
		TreeNode child;
		for (int i = 0; i < getChildCount(); i++) {
			child = getChildAt(i);
			hashCode = prime * hashCode + (child == null ? 0 : child.hashCode());
		}
		
		return hashCode;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#isLeaf()
	 */
	public boolean isLeaf() {
		return getChildCount() == 0;
	}
	
	/**
	 * Opposite of {@link #isSetParent()}.
	 * 
	 * Returns <code>true</code> if this {@link AbstractTreeNode} is the root
	 * node of a tree, <code>false</code> otherwise.
	 * 
	 * @return <code>True</code> if this {@link AbstractTreeNode} is the root
	 *         node of a tree, <code>false</code> otherwise.
	 * 
	 * @see #isSetParent()
	 */
	public boolean isRoot() {
		return !isSetParent();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetParent() {
		return parent != null;
	}
	
	/**
	 * @param parent
	 *            the parent to set
	 */
	protected void setParent(TreeNode parent) {
		this.parent = parent;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return StringTools.firstLetterLowerCase(getClass().getSimpleName());
	}

}
