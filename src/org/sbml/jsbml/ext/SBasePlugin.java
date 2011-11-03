package org.sbml.jsbml.ext;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Map;

import org.sbml.jsbml.SBase;

/**
 * Defines the methods necessary for an SBase Plugin. When a SBML level 3 is extending 
 * one of the core SBML elements with additional attributes or child elements, a {@link SBasePlugin}
 * is created to serve as a place holder for there new attributes or elements.
 * 
 * 
 * @author rodrigue
 *
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
  SBase getChildAt(int childIndex);
	
  /**
   * Returns the number of children <code>TreeNode</code>s the receiver
   * contains.
   */
  int getChildCount();
  
  /**
   * Returns the index of <code>node</code> in the receivers children.
   * If the receiver does not contain <code>node</code>, -1 will be
   * returned.
   */
  int getIndex(SBase node);
  
  /**
   * Returns true if the receiver allows children.
   */
  boolean getAllowsChildren();

  /**
   * Returns true if the receiver is a leaf.
   */
  boolean isLeaf();

  /**
   * Returns the children of the receiver as an <code>Enumeration</code>.
   */
  Enumeration<SBase> children();
	
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
