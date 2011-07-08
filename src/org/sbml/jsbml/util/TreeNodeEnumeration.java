/*
 * $Id:  TreeNodeEnumeration.java 17:42:43 draeger $
 * $URL: TreeNodeEnumeration.java $
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
package org.sbml.jsbml.util;

import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.swing.tree.TreeNode;

/**
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 0.8
 * @date 08.07.2011
 */
public class TreeNodeEnumeration implements Enumeration<TreeNode> {

	/**
	 * Total number of children in this enumeration.
	 */
	private int childCount;

	/**
	 * Current position in the list of children.
	 */
	private int index;
	
	/**
	 * The {@link TreeNode} instance over whose children we want to iterate.
	 */
	private TreeNode node;
	/**
	 * Initializes the {@link Enumeration} for single use.
	 * 
	 * @param node
	 *            over whose children we want to iterate.
	 */
	public TreeNodeEnumeration(TreeNode node) {
		this.node = node;
		this.childCount = node.getChildCount();
		this.index = 0;
	}

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
				return node.getChildAt(index++);
			}
		}
		throw new NoSuchElementException("Enumeration");
	}
}
