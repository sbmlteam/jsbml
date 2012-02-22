/*
 * $Id$
 * $URL$
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

package org.sbml.jsbml.xml;

import java.util.ArrayList;

import org.sbml.jsbml.AbstractTreeNode;
import org.sbml.jsbml.JSBML;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * Representation of a token in an XML stream.
 * <p>
 * <em style='color: #555'>
 * This class of objects is defined by jsbml only and has no direct
 * equivalent in terms of SBML components.  This class is not prescribed by
 * the SBML specifications, although it is used to implement features
 * defined in SBML.
 * </em>
 * 
 * <p>
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public abstract class XMLToken extends AbstractTreeNode {

	/**
	 * Generated serial version identifier
	 */
	private static final long serialVersionUID = -3501521107595952650L;

	protected XMLAttributes attributes = new XMLAttributes();
	
	protected StringBuffer characters = new StringBuffer();
	
	protected long column = 0;

	protected boolean isEndElement = false;

	protected boolean isEOF = false;
	protected boolean isStartElement = false; 
	
	protected boolean isText = false;
	protected long line = 0;
	protected XMLNamespaces namespaces = new XMLNamespaces();
	
	/**
	  * the XMLTripe (name, uri and prefix) of this XML element.
	  * 
	  */
	protected XMLTriple triple = new XMLTriple();
	
	/**
	 * Creates a new empty {@link XMLToken}.
	 */
	public XMLToken() {
		super();
	}

	/**
	 * Creates a text {@link XMLToken}.
	 * <p>
	 * @param chars a string, the text to be added to the {@link XMLToken}
	 */
	public XMLToken(String chars) {
		this(chars, 0, 0);
	}


	/**
	 * Creates a text {@link XMLToken}.
	 * <p>
	 * @param chars a string, the text to be added to the {@link XMLToken}
	 * @param line a long integer, the line number (default = 0).
	 */
	public XMLToken(String chars, long line) {
		this(chars, line, 0);
	}


	/**
	 * Creates a text {@link XMLToken}.
	 * <p>
	 * @param chars a {@link String}, the text to be added to the {@link XMLToken}
	 * @param line a long integer, the line number (default = 0).
	 * @param column a long integer, the column number (default = 0).
	 * <p>
	 */
	public XMLToken(String chars, long line, long column) {
		this();
		append(chars);
		isText = true;
		this.line = line;
		this.column = column;		
	}


	/**
	 * Copy constructor; creates a copy of this {@link XMLToken}.
	 */
	public XMLToken(XMLToken orig) {
		this();
		if (orig.isSetParent()) {
			this.parent = orig.getParent();
		}
		if (orig.triple != null) {
			triple = orig.triple.clone();
		}
		if (orig.attributes != null) {
			attributes = orig.attributes.clone();
		}
		if (orig.namespaces != null) {
			namespaces = orig.namespaces.clone();
		}
		line = orig.line;
		column = orig.column;
		if (orig.characters != null) {
			characters.append(orig.getCharacters());
		}
		isText = orig.isText;
		isStartElement = orig.isStartElement;
		isEndElement = orig.isEndElement;
		isEOF = orig.isEOF;
	}


	/**
	 * Creates an end element {@link XMLToken}.
	 * <p>
	 * @param triple {@link XMLTriple}.
	 * <p>
	 */
	public XMLToken(XMLTriple triple) {
		this(triple, 0, 0);
	}


	/**
	 * Creates an end element {@link XMLToken}.
	 * <p>
	 * @param triple {@link XMLTriple}.
	 * @param line a long integer, the line number (default = 0).
	 * <p>
	 */
	public XMLToken(XMLTriple triple, long line) {
		this(triple, line, 0);
	}


	/**
	 * Creates an end element {@link XMLToken}.
	 * <p>
	 * @param triple {@link XMLTriple}.
	 * @param line a long integer, the line number (default = 0).
	 * @param column a long integer, the column number (default = 0).
	 * <p>
	 */
	public XMLToken(XMLTriple triple, long line, long column) {
		this();
		this.triple = triple;
		this.line = line;
		this.column = column;		
		isEndElement = true;
		
	}


	/**
	 * Creates a start element {@link XMLToken} with the given set of attributes.
	 * <p>
	 * @param triple {@link XMLTriple}.
	 * @param attributes {@link XMLAttributes}, the attributes to set.
	 * <p>
	 */
	public XMLToken(XMLTriple triple, XMLAttributes attributes) {
		this(triple, attributes, null, 0, 0);
	}


	/**
	 * Creates a start element {@link XMLToken} with the given set of attributes.
	 * <p>
	 * @param triple {@link XMLTriple}.
	 * @param attributes {@link XMLAttributes}, the attributes to set.
	 * @param line a long integer, the line number (default = 0).
	 * <p>
	 */
	public XMLToken(XMLTriple triple, XMLAttributes attributes, long line) {
		this(triple, attributes, null, line, 0);
	}


	/**
	 * Creates a start element {@link XMLToken} with the given set of attributes.
	 * <p>
	 * @param triple {@link XMLTriple}.
	 * @param attributes {@link XMLAttributes}, the attributes to set.
	 * @param line a long integer, the line number (default = 0).
	 * @param column a long integer, the column number (default = 0).
	 * <p>
	 */
	public XMLToken(XMLTriple triple, XMLAttributes attributes, long line, long column) {
		this(triple, attributes, null, line, column);
	}


	/**
	 * Creates a start element {@link XMLToken} with the given set of attributes and
	 * namespace declarations.
	 * <p>
	 * @param triple {@link XMLTriple}.
	 * @param attributes {@link XMLAttributes}, the attributes to set.
	 * @param namespaces {@link XMLNamespaces}, the namespaces to set.
	 * <p>
	 */
	public XMLToken(XMLTriple triple, XMLAttributes attributes, XMLNamespaces namespaces) {
		this(triple, attributes, namespaces, 0, 0);
	}


	/**
	 * Creates a start element {@link XMLToken} with the given set of attributes and
	 * namespace declarations.
	 * <p>
	 * @param triple {@link XMLTriple}.
	 * @param attributes {@link XMLAttributes}, the attributes to set.
	 * @param namespaces {@link XMLNamespaces}, the namespaces to set.
	 * @param line a long integer, the line number (default = 0).
	 * <p>
	 */
	public XMLToken(XMLTriple triple, XMLAttributes attributes, XMLNamespaces namespaces, long line) {
		this(triple, attributes, namespaces, line, 0);
	}

	/**
	 * Creates a start element {@link XMLToken} with the given set of attributes and
	 * namespace declarations.
	 * <p>
	 * @param triple {@link XMLTriple}.
	 * @param attributes {@link XMLAttributes}, the attributes to set.
	 * @param namespaces {@link XMLNamespaces}, the namespaces to set.
	 * @param line a long integer, the line number (default = 0).
	 * @param column a long integer, the column number (default = 0).
	 * <p>
	 * 
	 */
	public XMLToken(XMLTriple triple, XMLAttributes attributes, XMLNamespaces namespaces, long line, long column) {
		this();
		this.triple = triple;
		this.attributes = attributes;
		this.namespaces = namespaces;
		this.line = line;
		this.column = column;
		isStartElement = true;
	}

	/**
	 * Adds an attribute to the attribute set in this {@link XMLToken} optionally 
	 * with a prefix and URI defining a namespace.
	 * Nothing will be done if this {@link XMLToken} is not a start element.
	 * <p>
	 * @param name a string, the local name of the attribute.
	 * @param value a string, the value of the attribute.
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 * <li> OPERATION_FAILED
	 * <p>
	 * @jsbml.note if local name with the same namespace URI already exists in the
	 * attribute set, its value and prefix will be replaced.
	 * <p>
	 */
	public int addAttr(String name, String value) {
		if (!isStartElement) {
			return JSBML.OPERATION_FAILED;
		}
		
		String oldValue = attributes.getValue(name);
		int success = attributes.add(name, value);
		if (success == JSBML.OPERATION_SUCCESS) {
			firePropertyChange(name, oldValue, value);
		} 
		return success;
	}


	/**
	 * Adds an attribute to the attribute set in this {@link XMLToken} optionally 
	 * with a prefix and URI defining a namespace.
	 * Nothing will be done if this {@link XMLToken} is not a start element.
	 * <p>
	 * @param name a string, the local name of the attribute.
	 * @param value a string, the value of the attribute.
	 * @param namespaceURI a string, the namespace URI of the attribute.
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 * <li> OPERATION_FAILED
	 * <p>
	 * @jsbml.note if local name with the same namespace URI already exists in the
	 * attribute set, its value and prefix will be replaced.
	 * <p>
	 */
	public int addAttr(String name, String value, String namespaceURI) {
		
		if (!isStartElement) {
			return JSBML.OPERATION_FAILED;
		}
		
		String oldValue = attributes.getValue(name);
		int success = attributes.add(name, value, namespaceURI);
		if (success == JSBML.OPERATION_SUCCESS) {
			firePropertyChange(name, oldValue, value);
		}
		return success;
	}


	/**
	 * Adds an attribute to the attribute set in this {@link XMLToken} optionally 
	 * with a prefix and URI defining a namespace.
	 * Nothing will be done if this {@link XMLToken} is not a start element.
	 * <p>
	 * @param name a string, the local name of the attribute.
	 * @param value a string, the value of the attribute.
	 * @param namespaceURI a string, the namespace URI of the attribute.
	 * @param prefix a string, the prefix of the namespace
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 * <li> OPERATION_FAILED
	 * <p>
	 * @jsbml.note if local name with the same namespace URI already exists in the
	 * attribute set, its value and prefix will be replaced.
	 * <p>
	 */
	public int addAttr(String name, String value, String namespaceURI, String prefix) {
		if (!isStartElement) {
			return JSBML.OPERATION_FAILED;
		}
		String oldValue = attributes.getValue(name);
		int success = attributes.add(name, value, namespaceURI, prefix);
		if (success == JSBML.OPERATION_SUCCESS) {
			firePropertyChange(name, oldValue, value);
		} 
		return success;
	}


	/**
	 * Adds an attribute with the given {@link XMLTriple}/value pair to the attribute set
	 * in this {@link XMLToken}.
	 * Nothing will be done if this {@link XMLToken} is not a start element.
	 * <p>
	 * @jsbml.note if local name with the same namespace URI already exists in the 
	 * attribute set, its value and prefix will be replaced.
	 * <p>
	 * @param triple an {@link XMLTriple}, the XML triple of the attribute.
	 * @param value a string, the value of the attribute.
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 * <li> OPERATION_FAILED
	 */
	public int addAttr(XMLTriple triple, String value) {
		
		if (!isStartElement) {
			return JSBML.OPERATION_FAILED;
		}

		String oldValue = attributes.getValue(triple.getName());
		int success = attributes.add(triple, value);
		if (success == JSBML.OPERATION_SUCCESS) {
			firePropertyChange(triple.getName(), oldValue, value);
		}
		return success;
	}


	/**
	 * Appends an XML namespace prefix and URI pair to this {@link XMLToken}.
	 * If there is an XML namespace with the given prefix in this {@link XMLToken}, 
	 * then the existing XML namespace will be overwritten by the new one.
	 * <p>
	 * Nothing will be done if this {@link XMLToken} is not a start element.
	 * <p>
	 * @param uri a string, the uri for the namespace
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 * <li> OPERATION_FAILED
	 */
	public int addNamespace(String uri) {
		
		if (!isStartElement) {
			return JSBML.OPERATION_FAILED;
		}
		
		String oldUri = null;
		if(namespaces.hasURI(uri)){
			oldUri = uri;
		}
		
		int success = namespaces.add(uri);
		if (success == JSBML.OPERATION_SUCCESS) {
			firePropertyChange(TreeNodeChangeEvent.namespace, oldUri, uri);
		}
		return success;
	}


	/**
	 * Appends an XML namespace prefix and URI pair to this {@link XMLToken}.
	 * If there is an XML namespace with the given prefix in this {@link XMLToken}, 
	 * then the existing XML namespace will be overwritten by the new one.
	 * <p>
	 * Nothing will be done if this {@link XMLToken} is not a start element.
	 * <p>
	 * @param uri a string, the uri for the namespace
	 * @param prefix a string, the prefix for the namespace
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 * <li> OPERATION_FAILED
	 */
	public int addNamespace(String uri, String prefix) {

		if (!isStartElement) {
			return JSBML.OPERATION_FAILED;
		}
		String oldUri = null;
		if(namespaces.hasURI(uri)){
			oldUri = uri;
		}else if(namespaces.hasPrefix(prefix)){
			oldUri = namespaces.getURI(prefix);
		}
		
		int success = namespaces.add(uri, prefix);
		if (success == JSBML.OPERATION_SUCCESS) {
			firePropertyChange(TreeNodeChangeEvent.namespace, oldUri, uri);
		}
		return success;
	}


	/**
	 * Appends characters to this XML text content.
	 */
	public void append(String chars) {
		if (characters == null) {
			characters = new StringBuffer();
		}
		String oldValue = characters.toString();
		characters.append(chars);
		this.firePropertyChange(TreeNodeChangeEvent.text, oldValue, characters.toString());
	}


	/**
	 * Clears (deletes) all attributes in this {@link XMLToken}.
	 * Nothing will be done if this {@link XMLToken} is not a start element.
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 * <li> OPERATION_FAILED
	 */
	public int clearAttributes() {
		
		if (!isStartElement) {
			return JSBML.OPERATION_FAILED;
		}
		ArrayList<XMLTriple> oldNames = this.attributes.attributeNames;
		ArrayList<String> oldValues = this.attributes.attributeValues;
		
		int success = attributes.clear();
		
		for(int i=0; i < this.attributes.getLength(); i++){
			this.firePropertyChange(oldNames.get(i).getName(), oldValues.get(i), null);
		}	
		return success;
	}


	/**
	 * Clears (deletes) all XML namespace declarations in the {@link XMLNamespaces} of
	 * this {@link XMLToken}.
	 * Nothing will be done if this {@link XMLToken} is not a start element.
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 * <li> OPERATION_FAILED
	 */
	public int clearNamespaces() {
		
		if (!isStartElement) {
			return JSBML.OPERATION_FAILED;
		}
		XMLNamespaces oldValues = namespaces;
		
		int success = namespaces.clear();
		
		for(int i=0; i < this.attributes.getLength(); i++){
			this.firePropertyChange(TreeNodeChangeEvent.namespace, oldValues.getURI(i), null);
		}	
		return success;
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public abstract XMLToken clone();


	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractTreeNode#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		XMLToken other = (XMLToken) obj;
		if (attributes == null) {
			if (other.attributes != null) {
				return false;
			}
		} else if (!attributes.equals(other.attributes)) {
			return false;
		}
		if (characters == null) {
			if (other.characters != null) {
				return false;
			}
		} else if (!characters.equals(other.characters)) {
			return false;			
		}
		if (column != other.column) {
			return false;
		}
		if (isEOF != other.isEOF) {
			return false;
		}
		if (isEndElement != other.isEndElement) {
			return false;
		}
		if (isStartElement != other.isStartElement) {
			return false;
		}
		if (isText != other.isText) {
			return false;
		}
		if (line != other.line) {
			return false;
		}
		if (namespaces == null) {
			if (other.namespaces != null) {
				return false;
			}
		} else if (!namespaces.equals(other.namespaces)) {
			return false;
		}
		if (triple == null) {
			if (other.triple != null) {
				return false;
			}
		} else if (!triple.equals(other.triple)) {
			return false;
		}
		
		return true;
	}


	/**
	 * Returns the attributes of this element.
	 * <p>
	 * @return the {@link XMLAttributes} of this XML element.
	 */
	public XMLAttributes getAttributes() {
		return attributes;
	}


	/**
	 * Returns the number of attributes in the attributes set.
	 * <p>
	 * @return the number of attributes in the attributes set in this {@link XMLToken}.
	 */
	public int getAttributesLength() {
		return attributes.getLength();
	}


	/**
	 * Returns the index of an attribute with the given local name.
	 * <p>
	 * @param name a string, the local name of the attribute.
	 * <p>
	 * @return the index of an attribute with the given local name, 
	 * or -1 if not present.
	 */
	public int getAttrIndex(String name) {
		return attributes.getIndex(name);
	}


	/**
	 * Returns the index of an attribute with the given local name and namespace URI.
	 * <p>
	 * @param name a string, the local name of the attribute.
	 * @param uri  a string, the namespace URI of the attribute.
	 * <p>
	 * @return the index of an attribute with the given local name and namespace URI, 
	 * or -1 if not present.
	 */
	public int getAttrIndex(String name, String uri) {
		return attributes.getIndex(name, uri);
	}


	/**
	 * Returns the index of an attribute with the given {@link XMLTriple}.
	 * <p>
	 * @param triple an {@link XMLTriple}, the XML triple of the attribute for which 
	 *        the index is required.
	 * <p>
	 * @return the index of an attribute with the given {@link XMLTriple}, or -1 if not present.
	 */
	public int getAttrIndex(XMLTriple triple) {
		return attributes.getIndex(triple);
	}


	/**
	 * Returns the local name of an attribute in the attributes set in this 
	 * {@link XMLToken} (by position).
	 * <p>
	 * @param index an integer, the position of the attribute whose local name 
	 * is required.
	 * <p>
	 * @return the local name of an attribute in this list (by position).  
	 * <p>
	 * @jsbml.note If index
	 * is out of range, an empty string will be returned.  Use hasAttr(index) 
	 * to test for the attribute existence.
	 */
	public String getAttrName(int index) {
		return attributes.getName(index);
	}


	/**
	 * Returns the prefix of an attribute in the attribute set in this 
	 * {@link XMLToken} (by position).
	 * <p>
	 * @param index an integer, the position of the attribute whose prefix is 
	 * required.
	 * <p>
	 * @return the namespace prefix of an attribute in the attribute set
	 * (by position).  
	 * <p>
	 * @jsbml.note If index is out of range, an empty string will be
	 * returned. Use hasAttr(index) to test for the attribute existence.
	 */
	public String getAttrPrefix(int index) {
		return attributes.getPrefix(index);
	}


	/**
	 * Returns the prefixed name of an attribute in the attribute set in this 
	 * {@link XMLToken} (by position).
	 * <p>
	 * @param index an integer, the position of the attribute whose prefixed 
	 * name is required.
	 * <p>
	 * @return the prefixed name of an attribute in the attribute set 
	 * (by position).  
	 * <p>
	 * @jsbml.note If index is out of range, an empty string will be
	 * returned.  Use hasAttr(index) to test for attribute existence.
	 */
	public String getAttrPrefixedName(int index) {
		return attributes.getPrefixedName(index);
	}


	/**
	 * Returns the namespace URI of an attribute in the attribute set in this 
	 * {@link XMLToken} (by position).
	 * <p>
	 * @param index an integer, the position of the attribute whose namespace 
	 * URI is required.
	 * <p>
	 * @return the namespace URI of an attribute in the attribute set (by position).
	 * <p>
	 * @jsbml.note If index is out of range, an empty string will be returned.  Use
	 * hasAttr(index) to test for attribute existence.
	 */
	public String getAttrURI(int index) {
		return attributes.getURI(index);
	}


	/**
	 * Returns the value of an attribute in the attribute set in this {@link XMLToken}  
	 * (by position).
	 * <p>
	 * @param index an integer, the position of the attribute whose value is 
	 * required.
	 * <p>
	 * @return the value of an attribute in the attribute set (by position).  
	 * <p>
	 * @jsbml.note If index
	 * is out of range, an empty string will be returned. Use hasAttr(index)
	 * to test for attribute existence.
	 */
	public String getAttrValue(int index) {
		return attributes.getValue(index);
	}


	/**
	 * Returns a value of an attribute with the given local name.
	 * <p>
	 * @param name a string, the local name of the attribute whose value is required.
	 * <p>
	 * @return The attribute value as a string.  
	 * <p>
	 * @jsbml.note If an attribute with the 
	 * given local name does not exist, an empty string will be 
	 * returned.  
	 * Use hasAttr(name, uri) to test for attribute existence.
	 */
	public String getAttrValue(String name) {
		return attributes.getValue(name);
	}


	/**
	 * Returns a value of an attribute with the given local name and namespace URI.
	 * <p>
	 * @param name a string, the local name of the attribute whose value is required.
	 * @param uri  a string, the namespace URI of the attribute.
	 * <p>
	 * @return The attribute value as a string.  
	 * <p>
	 * @jsbml.note If an attribute with the 
	 * given local name and namespace URI does not exist, an empty string will be 
	 * returned.  
	 * Use hasAttr(name, uri) to test for attribute existence.
	 */
	public String getAttrValue(String name, String uri) {
		return attributes.getValue(name, uri);
	}


	/**
	 * Returns a value of an attribute with the given {@link XMLTriple}.
	 * <p>
	 * @param triple an {@link XMLTriple}, the XML triple of the attribute whose 
	 *        value is required.
	 * <p>
	 * @return The attribute value as a string.  
	 * <p>
	 * @jsbml.note If an attribute with the
	 * given {@link XMLTriple} does not exist, an empty string will be returned.  
	 * Use hasAttr(triple) to test for attribute existence.
	 */
	public String getAttrValue(XMLTriple triple) {
		return attributes.getValue(triple);
	}


	/**
	 * Returns the text of this element.
	 * <p>
	 * @return the characters of this XML text.
	 */
	public String getCharacters() {
		return characters.toString();
	}


	/**
	 * Returns the column at which this {@link XMLToken} occurred in the input
	 * document or data stream.
	 * <p>
	 * @return the column at which this {@link XMLToken} occurred.
	 */
	public long getColumn() {
		return column;
	}


	/**
	 * Returns the line at which this {@link XMLToken} occurred in the input document
	 * or data stream.
	 * <p>
	 * @return the line at which this {@link XMLToken} occurred.
	 */
	public long getLine() {
		return line;
	}


	/**
	 * Returns the (unqualified) name of this XML element.
	 * <p>
	 * @return the (unqualified) name of this XML element.
	 */
	public String getName() {
		return triple.getName();
	}


	/**
	 * Returns the index of an XML namespace declaration by URI.
	 * <p>
	 * @param uri a string, uri of the required namespace.
	 * <p>
	 * @return the index of the given declaration, or -1 if not present.
	 */
	public int getNamespaceIndex(String uri) {
		return namespaces.getIndex(uri);
	}


	/**
	 * Returns the index of an XML namespace declaration by prefix.
	 * <p>
	 * @param prefix a string, prefix of the required namespace.
	 * <p>
	 * @return the index of the given declaration, or -1 if not present.
	 */
	public int getNamespaceIndexByPrefix(String prefix) {
		return namespaces.getIndexByPrefix(prefix);
	}


	/**
	 * Returns the prefix of an XML namespace declaration by position.
	 * <p>
	 * Callers should use getNamespacesLength() to find out how many 
	 * namespaces are stored in the {@link XMLNamespaces}.
	 * <p>
	 * @param index an integer, position of the required prefix.
	 * <p>
	 * @return the prefix of an XML namespace declaration in the {@link XMLNamespaces} 
	 * (by position).  
	 * <p>
	 * @jsbml.note If index is out of range, an empty string will be
	 * returned.
	 * <p>
	 * @see #getNamespacesLength()
	 */
	public String getNamespacePrefix(int index) {
		return namespaces.getPrefix(index);
	}


	/**
	 * Returns the prefix of an XML namespace declaration by its URI.
	 * <p>
	 * @param uri a string, the URI of the prefix being sought
	 * <p>
	 * @return the prefix of an XML namespace declaration given its URI.  
	 * <p>
	 * @jsbml.note If <code>uri</code> does not exist, an empty string will be returned.
	 */
	public String getNamespacePrefix(String uri) {
		return namespaces.getPrefix(uri);
	}


	/**
	 * Returns the XML namespace declarations for this XML element.
	 * <p>
	 * @return the XML namespace declarations for this XML element.
	 */
	public XMLNamespaces getNamespaces() {
		return namespaces;
	}


	/**
	 * Returns the number of XML namespaces stored in the {@link XMLNamespaces} 
	 * of this {@link XMLToken}.
	 * <p>
	 * @return the number of namespaces in this list.
	 */
	public int getNamespacesLength() {
		return namespaces == null ? 0 : namespaces.getLength();
	}


	/**
	 * Returns the URI of an XML namespace declaration for the empty prefix.
	 * <p>
	 * @return the URI of an XML namespace declaration for the empty prefix.
	 * <p>
	 * @jsbml.note If <code>prefix</code> does not exist, an empty string will be returned.
	 */
	public String getNamespaceURI() {
		return namespaces.getURI();
	}


	/**
	 * Returns the URI of an XML namespace declaration by its position.
	 * <p>
	 * @param index an integer, position of the required URI.
	 * <p>
	 * @return the URI of an XML namespace declaration in the {@link XMLNamespaces}
	 * (by position).  
	 * <p>
	 * @jsbml.note If <code>index</code> is out of range, an empty string will be
	 * returned.
	 * <p>
	 * @see #getNamespacesLength()
	 */
	public String getNamespaceURI(int index) {
		return namespaces.getURI(index);
	}


	/**
	 * Returns the URI of an XML namespace declaration by its prefix.
	 * <p>
	 * @param prefix a string, the prefix of the required URI
	 * <p>
	 * @return the URI of an XML namespace declaration given its prefix.  
	 * <p>
	 * @jsbml.note If <code>prefix</code> does not exist, an empty string will be returned.
	 */
	public String getNamespaceURI(String prefix) {
		return namespaces.getURI(prefix);
	}


	/**
	 * Returns the namespace prefix of this XML element.
	 * <p>
	 * @return the namespace prefix of this XML element.  
	 * <p>
	 * @jsbml.note If no prefix
	 * exists, an empty string will be return.
	 */
	public String getPrefix() {
		return triple.getPrefix();
	}


	/**
	 * Returns the namespace URI of this XML element.
	 * <p>
	 * @return the namespace URI of this XML element.
	 */
	public String getURI() {
		return triple.getURI();
	}


	/**
	 * Returns <code>true</code> or <code>false</code> depending on whether
	 * an attribute with the given index exists in the attribute set in this 
	 * {@link XMLToken}.
	 * <p>
	 * @param index an integer, the position of the attribute.
	 * <p>
	 * @return <code>true</code> if an attribute with the given index exists in the attribute 
	 * set in this {@link XMLToken}, <code>false</code> otherwise.
	 */
	public boolean hasAttr(int index) {
		return attributes.hasAttribute(index);
	}


	/**
	 * Returns <code>true</code> or <code>false</code> depending on whether
	 * an attribute with the given local name exists 
	 * in the attribute set in this {@link XMLToken}.
	 * <p>
	 * @param name a string, the local name of the attribute.
	 * <p>
	 * @return <code>true</code> if an attribute with the given local name
	 *  exists in the attribute set in this {@link XMLToken}, <code>false</code> otherwise.
	 */
	public boolean hasAttr(String name) {
		return attributes.hasAttribute(name);
	}


	/**
	 * Returns <code>true</code> or <code>false</code> depending on whether
	 * an attribute with the given local name and namespace URI exists 
	 * in the attribute set in this {@link XMLToken}.
	 * <p>
	 * @param name a string, the local name of the attribute.
	 * @param uri  a string, the namespace URI of the attribute.
	 * <p>
	 * @return <code>true</code> if an attribute with the given local name and namespace 
	 * URI exists in the attribute set in this {@link XMLToken}, <code>false</code> otherwise.
	 */
	public boolean hasAttr(String name, String uri) {
		return attributes.hasAttribute(name, uri);
	}


	/**
	 * Returns <code>true</code> or <code>false</code> depending on whether
	 * an attribute with the given XML triple exists in the attribute set in 
	 * this {@link XMLToken} 
	 * <p>
	 * @param triple an {@link XMLTriple}, the XML triple of the attribute 
	 * <p>
	 * @return <code>true</code> if an attribute with the given XML triple exists
	 * in the attribute set in this {@link XMLToken}, <code>false</code> otherwise.
	 * <p>
	 */
	public boolean hasAttr(XMLTriple triple) {
		return attributes.hasAttribute(triple);
	}


	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractTreeNode#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result
				+ ((characters == null) ? 0 : characters.hashCode());
		result = prime * result + (int) (column ^ (column >>> 32));
		result = prime * result + (isEOF ? 1231 : 1237);
		result = prime * result + (isEndElement ? 1231 : 1237);
		result = prime * result + (isStartElement ? 1231 : 1237);
		result = prime * result + (isText ? 1231 : 1237);
		result = prime * result + (int) (line ^ (line >>> 32));
		result = prime * result
				+ ((namespaces == null) ? 0 : namespaces.hashCode());
		result = prime * result + ((triple == null) ? 0 : triple.hashCode());
		return result;
	}


	/**
	 * Returns <code>true</code> or <code>false</code> depending on whether 
	 * an XML Namespace with the given uri/prefix pair is contained in the 
	 * {@link XMLNamespaces} of this {@link XMLToken}.
	 * <p>
	 * @param uri a string, the uri for the namespace
	 * @param prefix a string, the prefix for the namespace
	 * <p>
	 * @return <code>true</code> if an XML Namespace with the given uri/prefix pair is 
	 * contained in the {@link XMLNamespaces} of this {@link XMLToken},  <code>false</code> otherwise.
	 */
	public boolean hasNamespaceNS(String uri, String prefix) {
		return namespaces.hasNS(uri, prefix);
	}


	/**
	 * Returns <code>true</code> or <code>false</code> depending on whether 
	 * an XML Namespace with the given prefix is contained in the {@link XMLNamespaces} of
	 * this {@link XMLToken}.
	 * <p>
	 * @param prefix a string, the prefix for the namespace
	 * <p>
	 * @return <code>true</code> if an XML Namespace with the given URI is contained in the
	 * {@link XMLNamespaces} of this {@link XMLToken}, <code>false</code> otherwise.
	 */
	public boolean hasNamespacePrefix(String prefix) {
		return namespaces.hasPrefix(prefix);
	}


	/**
	 * Returns <code>true</code> or <code>false</code> depending on whether 
	 * an XML Namespace with the given URI is contained in the {@link XMLNamespaces} of
	 * this {@link XMLToken}.
	 * <p>
	 * @param uri a string, the uri for the namespace
	 * <p>
	 * @return <code>true</code> if an XML Namespace with the given URI is contained in the
	 * {@link XMLNamespaces} of this {@link XMLToken},  <code>false</code> otherwise.
	 */
	public boolean hasNamespaceURI(String uri) {
		return namespaces.hasURI(uri);
	}


	/**
	 * Returns <code>true</code> or <code>false</code> depending on whether 
	 * the attribute set in this {@link XMLToken} set is empty.
	 * <p>
	 * @return <code>true</code> if the attribute set in this {@link XMLToken} is empty, 
	 * <code>false</code> otherwise.
	 */
	public boolean isAttributesEmpty() {
		return attributes.isEmpty();
	}


	/**
	 * Returns <code>true</code> or <code>false</code> depending on whether 
	 * this {@link XMLToken} is an XML element.
	 * <p>
	 * @return <code>true</code> if this {@link XMLToken} is an XML element, <code>false</code> otherwise.
	 */
	public boolean isElement() {
		return !isText;
	}


	/**
	 * Returns <code>true</code> or <code>false</code> depending on whether 
	 * this {@link XMLToken} is an XML end element.
	 * <p>
	 * @return <code>true</code> if this {@link XMLToken} is an XML end element, <code>false</code> otherwise.
	 */
	public boolean isEnd() {
		return isEndElement;
	}


	/**
	 * Returns <code>true</code> or <code>false</code> depending on whether 
	 * this {@link XMLToken} is an XML end element for the given start element.
	 * <p>
	 * @param element {@link XMLToken}, element for which query is made.
	 * <p>
	 * @return <code>true</code> if this {@link XMLToken} is an XML end element for the given
	 * {@link XMLToken} start element, <code>false</code> otherwise.
	 */
	public boolean isEndFor(XMLToken element) {
		return isEnd() && element.isStart() && element.getName() == getName()
				&& element.getURI() == getURI();
	}


	/**
	 * Returns <code>true</code> or <code>false</code> depending on whether 
	 * this {@link XMLToken} is an end of file marker.
	 * <p>
	 * @return <code>true</code> if this {@link XMLToken} is an end of file (input) marker, <code>false</code>
	 * otherwise.
	 */
	public boolean isEOF() {
		return isEOF;
	}


	/**
	 * Returns <code>true</code> or <code>false</code> depending on whether 
	 * the {@link XMLNamespaces} of this {@link XMLToken} is empty.
	 * <p>
	 * @return <code>true</code> if the {@link XMLNamespaces} of this {@link XMLToken} is empty, 
	 * <code>false</code> otherwise.
	 */
	public boolean isNamespacesEmpty() {
		return namespaces == null ? true : namespaces.isEmpty();
	}


	/**
	 * Returns <code>true</code> or <code>false</code> depending on whether 
	 * this {@link XMLToken} is an XML start element.
	 * <p>
	 * @return <code>true</code> if this {@link XMLToken} is an XML start element, <code>false</code> otherwise.
	 */
	public boolean isStart() {
		return isStartElement;
	}


	/**
	 * Returns <code>true</code> or <code>false</code> depending on whether 
	 * this {@link XMLToken} is an XML text element.
	 * <p>
	 * @return <code>true</code> if this {@link XMLToken} is an XML text element, <code>false</code> otherwise.
	 */
	public boolean isText() {
		return isText;
	}


	/**
	 * Removes an attribute with the given index from the attribute set in
	 * this {@link XMLToken}.
	 * Nothing will be done if this {@link XMLToken} is not a start element.
	 * <p>
	 * @param n an integer the index of the resource to be deleted
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 * <li> OPERATION_FAILED
	 * <li> INDEX_EXCEEDS_SIZE
	 */
	public int removeAttr(int n) {
		
		if (!isStartElement) {
			return JSBML.OPERATION_FAILED;
		}
		String oldValue = attributes.getValue(n);
		String name = attributes.getName(n);
		int success = attributes.remove(n);
		this.firePropertyChange(name, oldValue, null);
		return success;
	}


	/**
	 * Removes an attribute with the given local name from 
	 * the attribute set in this {@link XMLToken}.
	 * Nothing will be done if this {@link XMLToken} is not a start element.
	 * <p>
	 * @param name   a string, the local name of the attribute.
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 * <li> OPERATION_FAILED
	 */
	public int removeAttr(String name) {
		
		if (!isStartElement) {
			return JSBML.OPERATION_FAILED;
		}

		String oldValue = attributes.getValue(name);
		int success = attributes.remove(name);
		this.firePropertyChange(name, oldValue, null);
		return success;
	}


	/**
	 * Removes an attribute with the given local name and namespace URI from 
	 * the attribute set in this {@link XMLToken}.
	 * Nothing will be done if this {@link XMLToken} is not a start element.
	 * <p>
	 * @param name   a string, the local name of the attribute.
	 * @param uri    a string, the namespace URI of the attribute.
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 * <li> OPERATION_FAILED
	 */
	public int removeAttr(String name, String uri) {
		
		if (!isStartElement) {
			return JSBML.OPERATION_FAILED;
		}

		String oldValue = attributes.getValue(name, uri);
		int success = attributes.remove(name, uri);
		this.firePropertyChange(name, oldValue, null);
		return success;
	}


	/**
	 * Removes an attribute with the given {@link XMLTriple} from the attribute set 
	 * in this {@link XMLToken}.  
	 * Nothing will be done if this {@link XMLToken} is not a start element.
	 * <p>
	 * @param triple an {@link XMLTriple}, the XML triple of the attribute.
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 * <li> OPERATION_FAILED
	 */
	public int removeAttr(XMLTriple triple) {
		
		if (!isStartElement) {
			return JSBML.OPERATION_FAILED;
		}
		
		String oldValue = attributes.getValue(triple);
		String name = triple.getName();
		int success = attributes.remove(triple);
		this.firePropertyChange(name, oldValue, null);
		return success;
	}


	/**
	 * Removes an XML Namespace stored in the given position of the {@link XMLNamespaces}
	 * of this {@link XMLToken}.
	 * Nothing will be done if this {@link XMLToken} is not a start element.
	 * <p>
	 * @param index an integer, position of the removed namespace.
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 * <li> OPERATION_FAILED
	 * <li> INDEX_EXCEEDS_SIZE
	 */
	public int removeNamespace(int index) {

		if (!isStartElement) {
			return JSBML.OPERATION_FAILED;
		}
		String oldValue = namespaces.getURI(index);
		int success = namespaces.remove(index);
		this.firePropertyChange(TreeNodeChangeEvent.namespace, oldValue, null);
		return success;
	}

	/**
	 * Removes an XML Namespace with the given prefix.
	 * Nothing will be done if this {@link XMLToken} is not a start element.
	 * <p>
	 * @param prefix a string, prefix of the required namespace.
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 * <li> OPERATION_FAILED
	 */
	public int removeNamespace(String prefix) {
		
		if (!isStartElement) {
			return JSBML.OPERATION_FAILED;
		}
		String oldValue = namespaces.getURI(prefix);
		int success = namespaces.remove(prefix);
		this.firePropertyChange(TreeNodeChangeEvent.namespace, oldValue, null);
		return success;
	}

	
	/**
	 * Sets an {@link XMLAttributes} to this {@link XMLToken}.
	 * Nothing will be done if this {@link XMLToken} is not a start element.
	 * <p>
	 * @param attributes {@link XMLAttributes} to be set to this {@link XMLToken}.
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 * <li> OPERATION_FAILED
	 * <p>
	 * @jsbml.note This function replaces the existing {@link XMLAttributes} with the new one.
	 */
	public int setAttributes(XMLAttributes attributes) {

		if (!isStartElement) {
			return JSBML.OPERATION_FAILED;
		}
		
		ArrayList<XMLTriple> names = this.attributes.attributeNames;
		ArrayList<String> values = this.attributes.attributeValues;
		for(int i=0; i < this.attributes.getLength(); i++){
			this.firePropertyChange(names.get(i).getName(), values.get(i), null);
		}	
		this.attributes = attributes;

		names = attributes.attributeNames;
		values = attributes.attributeValues;
		for(int i=0; i < attributes.getLength(); i++){
			this.firePropertyChange(names.get(i).getName(), null, values.get(i));
		}	
		return JSBML.OPERATION_SUCCESS;
	}


	/**
	 * @param chars the characters to set
	 */
	public void setCharacters(String chars) {
		String oldValue = characters.toString();
		characters = new StringBuffer();
		characters.append(chars);
		this.firePropertyChange(TreeNodeChangeEvent.text, oldValue, characters.toString());
	}

	/**
	 * Declares this XML start element is also an end element.
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 */
	public int setEnd() {

		isEndElement = true;
		
		return JSBML.OPERATION_SUCCESS;
	}


	/**
	 * Declares this {@link XMLToken} is an end-of-file (input) marker.
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 * <li> OPERATION_FAILED
	 */
	public int setEOF() {
		boolean oldValue = isEOF;
		isEOF = true;
		this.firePropertyChange(TreeNodeChangeEvent.isEOF, oldValue, isEOF);
		return JSBML.OPERATION_SUCCESS;
	}

	/**
	 * Sets an XMLnamespaces to this XML element.
	 * <p>
	 * Nothing will be done if this {@link XMLToken} is not a start element.
	 * <p>
	 * @param namespaces {@link XMLNamespaces} to be set to this {@link XMLToken}.
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 * <li> OPERATION_FAILED
	 * 
	 * <p>
	 * @jsbml.note This function replaces the existing {@link XMLNamespaces} with the new one.
	 */
	public int setNamespaces(XMLNamespaces namespaces) {
		
		if (!isStartElement) {
			return JSBML.OPERATION_FAILED;
		}
		
		XMLNamespaces values = this.namespaces;
		for(int i=0; i < this.attributes.getLength(); i++){
			this.firePropertyChange(TreeNodeChangeEvent.namespace, values.getURI(i), null);
		}
		
		this.namespaces = namespaces;
		
		values = this.namespaces;
		for(int i=0; i < this.attributes.getLength(); i++){
			this.firePropertyChange(TreeNodeChangeEvent.namespace, null, values.getURI(i));
		}
		return JSBML.OPERATION_SUCCESS;
	}


	/**
	 * Sets the XMLTripe (name, uri and prefix) of this XML element.
	 * <p>
	 * Nothing will be done if this XML element is a text node.
	 * @param triple {@link XMLTriple} to be added to this XML element.
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 * <li> OPERATION_FAILED
	 */
	public int setTriple(XMLTriple triple) {
		
		if (isText) {
			return JSBML.OPERATION_FAILED;
		}
		XMLTriple oldValue = this.triple;
		this.triple = triple;
		this.firePropertyChange(TreeNodeChangeEvent.xmlTriple, oldValue, triple);
		return JSBML.OPERATION_SUCCESS;
	}


	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "XMLToken [attributes=" + attributes + ", namespaces="
				+ namespaces + ", triple=" + triple + ", characters="
				+ characters + ", column=" + column + ", line=" + line
				+ ", isText=" + isText + ", isEndElement=" + isEndElement
				+ ", isStartElement=" + isStartElement + ", isEOF=" + isEOF
				+ "]";
	}


	/**
	 * Declares this XML start/end element is no longer an end element.
	 * <p>
	 * @return integer value indicating success/failure of the
	 * function.   The possible values
	 * returned by this function are:
	 * <li> OPERATION_SUCCESS
	 */
	public int unsetEnd() {

		isEndElement = false;
		
		return JSBML.OPERATION_SUCCESS;
	}

}

