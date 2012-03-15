/*
 * $Id$
 * $URL$
 *
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2012 jointly by the following organizations: 
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
package org.sbml.jsbml.ext;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.SBase;

/**
 * Defines the methods necessary for an SBase Plugin. When a SBML level 3 is extending 
 * one of the core SBML elements with additional attributes or child elements, a {@link SBasePlugin}
 * is created to serve as a place holder for there new attributes or elements.
 * 
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 */
public interface SBasePlugin extends Cloneable, Serializable {

	/**
	 * Reads and sets the attribute if it is know from this {@link SBasePlugin}.
	 * 
	 * @param attributeName
	 *            : localName of the XML attribute
	 * @param prefix
	 *            : prefix of the XML attribute
	 * @param value
	 *            : value of the XML attribute
	 * @return true if the attribute has been successfully read.
	 */
	public boolean readAttribute(String attributeName, String prefix,
			String value);

	/**
   * Returns the child <code>TreeNode</code> at index 
   * <code>childIndex</code>.
   */
  public TreeNode getChildAt(int childIndex);
	
  /**
   * Returns the number of children <code>TreeNode</code>s the receiver
   * contains.
   */
  public int getChildCount();
  
  /**
   * Returns the index of <code>node</code> in the receivers children.
   * If the receiver does not contain <code>node</code>, -1 will be
   * returned.
   */
  public int getIndex(SBase node);
  
  /**
   * Returns true if the receiver allows children.
   */
  public boolean getAllowsChildren();

  /**
   * Returns true if the receiver is a leaf.
   */
  public boolean isLeaf();

  /**
   * Returns the children of the receiver as an <code>Enumeration</code>.
   */
  public Enumeration<TreeNode> children();
	
	/**
	 * Returns a {@link Map} containing the XML attributes of this object.
	 * 
	 * @return a {@link Map} containing the XML attributes of this object.
	 */
	public Map<String, String> writeXMLAttributes();
	
	public boolean equals(Object obj);
	
	public int hashCode();
	
	public SBasePlugin clone();
}
