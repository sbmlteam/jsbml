/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
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

import java.util.LinkedHashMap;

import org.sbml.jsbml.JSBML;

/** 
 * Representation of XML Namespaces.
 * <p>
 * <em style='color: #555'>
This class of objects is defined by jsbml only and has no direct
equivalent in terms of SBML components.  This class is not prescribed by
the SBML specifications, although it is used to implement features
defined in SBML.
</em>

 * <p>
 * This class serves to organize functionality for tracking XML namespaces
 * in a document or data stream.  The namespace declarations are stored as
 * a list of pairs of XML namespace URIs and prefix strings.  These
 * correspond to the parts of a namespace declaration on an XML element.
 * For example, in the following XML fragment,
 * <div class='fragment'><pre>
&lt;annotation&gt;
    &lt;mysim:nodecolors xmlns:mysim='urn:lsid:mysim.org'
         mysim:bgcolor='green' mysim:fgcolor='white'/&gt;
&lt;/annotation&gt;
</pre></div>
 * there is one namespace declaration.  Its URI is
 * {@code urn:lsid:mysim.org} and its prefix is {@code mysim}.
 * This pair could be stored as one item in an {@link XMLNamespaces} list.
 * <p>
 * {@link XMLNamespaces} provides various methods for manipulating the list of
 * prefix-URI pairs.  Individual namespaces stored in a given XMLNamespace
 * object instance can be retrieved based on their index using
 * {@link XMLNamespaces#getPrefix(int index)}, or by their characteristics such as
 * their URI or position in the list.
 * @since 0.8
 * @version $Rev$
 */
public class XMLNamespaces {
	
	/**
	 * HashMap<Prefix, URI>
	 */
	LinkedHashMap<String, String> namespaces = new LinkedHashMap<String, String>();
	
  /**
   * Equality comparison method for XMLNamespaces.
   *
   * @param sb a reference to an object to which the current object
   * instance will be compared
   *
   * @return {@code true} if {@code sb} refers to the same underlying 
   * native object as this one, {@code false} otherwise
   */
  public boolean equals(Object sb)
  {
	  if (sb instanceof XMLNamespaces) {
		  XMLNamespaces namespaces2 = (XMLNamespaces) sb;

		  for (String prefix : namespaces.keySet()) {
			  if (!getURI(prefix).equals(namespaces2.getURI(prefix))) {
				  return false;
			  }
		  }
		  
		  return true;
	  }

	  return false;
  }

  /**
   * Returns a hashcode for this XMLNamespaces object.
   *
   * @return a hash code usable by Java methods that need them.
   */
  public int hashCode()
  {
	  int hashcode = 0;
	  
	  for (String prefix : namespaces.keySet()) {
		  hashcode += prefix.hashCode() + getURI(prefix).hashCode();
	  }

    return hashcode;
  }

  
  /**
   * Creates a new empty list of XML namespace declarations.
   */
 public XMLNamespaces() {
  }

  
  /**
   * Copy constructor; creates a copy of this {@link XMLNamespaces} list.
   * <p>
   * @param orig the {@link XMLNamespaces} object to copy
   */
 public XMLNamespaces(XMLNamespaces orig) {
	 
	 for (String prefix : orig.namespaces.keySet()) {
		 namespaces.put(new String(prefix), new String(orig.getURI(prefix)));
	 }

  }

  
  /**
   * Creates and returns a deep copy of this {@link XMLNamespaces} list.
   * <p>
   * @return a (deep) copy of this {@link XMLNamespaces} list.
   */
 public XMLNamespaces clone() {
    return new XMLNamespaces(this);
  }

  
  /**
   * Appends an XML namespace prefix and URI pair to this list of namespace
   * declarations.
   * <p>
   * An XMLNamespace object stores a list of pairs of namespaces and their
   * prefixes.  If there is an XML namespace with the given {@code uri} prefix
   * in this list, then its corresponding URI will be overwritten by the
   * new {@code uri}.  Calling programs could use one of the other {@link XMLNamespaces}
   * methods, such as
   * {@link XMLNamespaces#hasPrefix(String)}  and 
   * {@link XMLNamespaces#hasURI(String)}  to
   * inquire whether a given prefix and/or URI
   * is already present in this {@link XMLNamespaces} object.
   * <p>
   * @param uri a string, the uri for the namespace
   * @param prefix a string, the prefix for the namespace
   * <p>
   * @return integer value indicating success/failure of the
   * function.   The possible values
   * returned by this function are:
   * <li> OPERATION_SUCCESS
   * <p>
   */
 public int add(String uri, String prefix) {

	 namespaces.put(prefix, uri);
	 
	 return JSBML.OPERATION_SUCCESS;
  }

  
  /**
   * Appends an XML namespace URI with an empty prefix to this list of namespace
   * declarations.
   * <p>
   * An XMLNamespace object stores a list of pairs of namespaces and their
   * prefixes.  If there is an XML namespace with the given {@code uri} prefix
   * in this list, then its corresponding URI will be overwritten by the
   * new {@code uri}.  Calling programs could use one of the other {@link XMLNamespaces}
   * methods, such as
   * {@link XMLNamespaces#hasPrefix(String)}  and 
   * {@link XMLNamespaces#hasURI(String)}  to
   * inquire whether a given prefix and/or URI
   * is already present in this {@link XMLNamespaces} object.
   * <p>
   * @param uri a string, the uri for the namespace
   * @param prefix a string, the prefix for the namespace
   * <p>
   * @return integer value indicating success/failure of the
   * function.   The possible values
   * returned by this function are:
   * <li> OPERATION_SUCCESS
   * <p>
   */
 public int add(String uri) {

	 namespaces.put("", uri);
	 
	 return JSBML.OPERATION_SUCCESS;
  }

  
  /**
   * Removes an XML Namespace stored in the given position of this list.
   * <p>
   * @param index an integer, position of the namespace to remove.
   * <p>
   * @return integer value indicating success/failure of the
   * function.   The possible values
   * returned by this function are:
   * <li> OPERATION_SUCCESS
   * <li> INDEX_EXCEEDS_SIZE
   */
 public int remove(int index) {

	 if (index < 0 || index >= namespaces.size()) {
		 return JSBML.INDEX_EXCEEDS_SIZE;
	 }
	 
	 int i = 0;
	 for (String prefix : namespaces.keySet()) {
		 if (i == index) {
			 namespaces.remove(prefix);
			 break;
		 }
		 i++;
	 }

	 return JSBML.OPERATION_SUCCESS;
  }

  
  /**
   * Removes an XML Namespace with the given prefix.
   * <p>
   * @param prefix a string, prefix of the required namespace.
   * <p>
   * @return integer value indicating success/failure of the
   * function.   The possible values
   * returned by this function are:
   * <li> OPERATION_SUCCESS
   * <p>
   * @see #remove(int index)
   */
 public int remove(String prefix) {
	 
	 namespaces.remove(prefix);

	 return JSBML.OPERATION_SUCCESS;

  }

  
  /**
   * Clears (deletes) all XML namespace declarations in this {@link XMLNamespaces}
   * object.
   * <p>
   * @return integer value indicating success/failure of the
   * function.   The possible values
   * returned by this function are:
   * <li> OPERATION_SUCCESS
   * <p>
   * @see #remove(int index)
   */
 public int clear() {
	 
	 namespaces.clear();
	 
	 return JSBML.OPERATION_SUCCESS;

  }

  
  /**
   * Gets the index of an XML namespace declaration by URI.
   * <p>
   * An XMLNamespace object stores a list of pairs of namespaces and their
   * prefixes.  If this {@link XMLNamespaces} object contains a pair with the given
   * URI {@code uri}, this method returns its index in the list.
   * <p>
   * @param uri a string, the URI of the sought-after namespace.
   * <p>
   * @return the index of the given declaration, or {@code -1} if not
   * present.
   */
 public int getIndex(String uri) {

	 int index = -1;
	 
	 int i = 0;
	 for (String prefix : namespaces.keySet()) {
		 if (namespaces.get(prefix).equals(uri)) {
			 index = i;
			 break;
		 }
		 i++;
	 }

	 return index;

  }

  
  /**
   * Gets the index of an XML namespace declaration by prefix.
   * <p>
   * An XMLNamespace object stores a list of pairs of namespaces and their
   * prefixes.  If this {@link XMLNamespaces} object contains a pair with the given
   * prefix {@code prefix}, this method returns its index in the list.
   * <p>
   * @param prefix a string, the prefix string of the sought-after
   * namespace
   * <p>
   * @return the index of the given declaration, or {@code -1} if not
   * present.
   */
 public int getIndexByPrefix(String prefix) {

	 int index = -1;
	 
	 int i = 0;
	 for (String currentPrefix : namespaces.keySet()) {
		 if (currentPrefix.equals(prefix)) {
			 index = i;
			 break;
		 }
		 i++;
	 }

	 return index;

  }

  
  /**
   * Returns the total number of URI-and-prefix pairs stored in this
   * particular {@link XMLNamespaces} instance.
   * <p>
   * @return the number of namespaces in this list.
   */
 public int getLength() {

	 return namespaces.size();

  }

  
  /**
   * Gets the prefix of an XML namespace declaration by its position.
   * <p>
   * An XMLNamespace object stores a list of pairs of namespaces and their
   * prefixes.  This method returns the prefix of the {@code n}th
   * element in that list (if it exists).  Callers should use
   * XMLAttributes.getLength() first to find out how many namespaces are
   * stored in the list.
   * <p>
   * @param index an integer, position of the sought-after prefix
   * <p>
   * @return the prefix of an XML namespace declaration in this list (by
   * position), or an empty string if the {@code index} is out of range
   * <p>
   * @see #getLength()
   */
 public String getPrefix(int index) {

	 int i = 0;
	 for (String prefix : namespaces.keySet()) {
		 if (i == index) {
			 return prefix;
		 }
		 i++;
	 }

	 return "";

  }

  
  /**
   * Gets the prefix of an XML namespace declaration by its URI.
   * <p>
   * An XMLNamespace object stores a list of pairs of namespaces and their
   * prefixes.  This method returns the prefix for a pair that has the
   * given {@code uri}.
   * <p>
   * @param uri a string, the URI of the prefix being sought
   * <p>
   * @return the prefix of an XML namespace declaration given its URI, or
   * an empty string if no such {@code uri} exists in this {@link XMLNamespaces} object
   */
 public String getPrefix(String uri) {

	 for (String prefix : namespaces.keySet()) {
		 if (namespaces.get(prefix).equals(uri)) {
			 return prefix;
		 }
	 }

	 return "";

  }

  
  /**
   * Gets the URI of an XML namespace declaration by its position.
   * <p>
   * An XMLNamespace object stores a list of pairs of namespaces and their
   * prefixes.  This method returns the URI of the {@code n}th element
   * in that list (if it exists).  Callers should use
   * XMLAttributes.getLength() first to find out how many namespaces are
   * stored in the list.
   * <p>
   * @param index an integer, position of the required URI.
   * <p>
   * @return the URI of an XML namespace declaration in this list (by
   * position), or an empty string if the {@code index} is out of range.
   * <p>
   * @see #getLength()
   */
 public String getURI(int index) {

	 int i = 0;
	 for (String prefix : namespaces.keySet()) {
		 if (i == index) {
			 return namespaces.get(prefix);
		 }
		 i++;
	 }

	 return "";
  }

  
  /**
   * Gets the URI of an XML namespace declaration by its prefix.
   * <p>
   * An XMLNamespace object stores a list of pairs of namespaces and their
   * prefixes.  This method returns the namespace URI for a pair that has
   * the given {@code prefix}.
   * <p>
   * @param prefix a string, the prefix of the required URI
   * <p>
   * @return the URI of an XML namespace declaration having the given 
   * {@code prefix}, or an empty string if no such prefix-and-URI pair exists
   * in this {@link XMLNamespaces} object
   * 
   * @see #getURI()
   */
 public String getURI(String prefix) {

	 for (String currentPrefix : namespaces.keySet()) {
		 if (currentPrefix.equals(prefix)) {
			 return namespaces.get(currentPrefix);
		 }
	 }

	 return "";

  }

  
  /**
   * Gets the URI of an XML namespace declaration by the empty prefix.
   * <p>
   * An XMLNamespace object stores a list of pairs of namespaces and their
   * prefixes.  This method returns the namespace URI for a pair that has
   * the empty {@code prefix}.
   * <p>
   * @return the URI of an XML namespace declaration having the empty 
   * {@code prefix}, or an empty string if no such prefix-and-URI pair exists
   * in this {@link XMLNamespaces} object
   * 
   * @see #getURI()
   */
 public String getURI() {

	 for (String currentPrefix : namespaces.keySet()) {
		 if (currentPrefix.equals("")) {
			 return namespaces.get(currentPrefix);
		 }
	 }

	 return "";

  }

  
  /**
   * Returns {@code true} or {@code false} depending on whether this
   * {@link XMLNamespaces} list is empty.
   * <p>
   * @return {@code true} if this {@link XMLNamespaces} list is empty, {@code false} otherwise.
   */
 public boolean isEmpty() {

	 return namespaces.size() == 0;

  }

  
  /**
   * Returns {@code true} or {@code false} depending on whether an XML
   * Namespace with the given URI is contained in this {@link XMLNamespaces} list.
   * <p>
   * @param uri a string, the uri for the namespace
   * <p>
   * @return {@code true} if an XML Namespace with the given URI is contained in
   * this {@link XMLNamespaces} list, {@code false} otherwise.
   */
 public boolean hasURI(String uri) {

	 return namespaces.containsValue(uri);

  }

  
  /**
   * Returns {@code true} or {@code false} depending on whether an XML
   * Namespace with the given prefix is contained in this {@link XMLNamespaces}
   * list.
   * <p>
   * @param prefix a string, the prefix for the namespace
   * <p>
   * @return {@code true} if an XML Namespace with the given URI is contained in
   * this {@link XMLNamespaces} list, {@code false} otherwise.
   */
 public boolean hasPrefix(String prefix) {

	 return namespaces.containsKey(prefix);

  }

  
  /**
   * Returns {@code true} or {@code false} depending on whether an XML
   * Namespace with the given URI and prefix pair is contained in this
   * {@link XMLNamespaces} list.
   * <p>
   * @param uri a string, the URI for the namespace
   * @param prefix a string, the prefix for the namespace
   * <p>
   * @return {@code true} if an XML Namespace with the given uri/prefix pair is
   * contained in this {@link XMLNamespaces} list, {@code false} otherwise.
   */
 public boolean hasNS(String uri, String prefix) {

	 if (uri == null || prefix == null) {
		 return false;
	 }
	 String uri2 = namespaces.get(prefix); 
		 
	 if (uri.equals(uri2)) {
		 return true;
	 }
	 
	 return false;

  }

}

