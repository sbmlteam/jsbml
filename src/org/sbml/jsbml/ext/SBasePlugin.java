package org.sbml.jsbml.ext;

import java.util.Map;

import org.sbml.jsbml.util.TreeNodeWithChangeSupport;

/**
 * Defines the methods necessary for an SBase Plugin. When a SBML level 3 is extending 
 * one of the core SBML elements with additional attributes or child elements, a {@link SBasePlugin}
 * is created to serve as a place holder for there new attributes or elements.
 * 
 * 
 * @author rodrigue
 *
 */
public interface SBasePlugin extends TreeNodeWithChangeSupport {

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
	 * Returns a {@link Map} containing the XML attributes of this object.
	 * 
	 * @return a {@link Map} containing the XML attributes of this object.
	 */
	public Map<String, String> writeXMLAttributes();
	
	public boolean equals(Object obj);
	
	public int hashCode();
	
	public Object clone();
}
