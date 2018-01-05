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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.xml;

import java.io.Serializable;
import java.util.ArrayList;

import org.sbml.jsbml.JSBML;

/**
 * Representation of the attributes on an XML node.
 * <p>
 * <em style='color: #555'>
 * This class of objects is defined by jsbml only and has no direct
 * equivalent in terms of SBML components.  This class is not prescribed by
 * the SBML specifications, although it is used to implement features
 * defined in SBML.
 * </em>
 * 
 * <p>
 * @since 0.8
 */
public class XMLAttributes implements Serializable {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 6602965086251691073L;
  /**
   * 
   */
  ArrayList<XMLTriple> attributeNames = new ArrayList<XMLTriple>();
  /**
   * 
   */
  ArrayList<String> attributeValues = new ArrayList<String>();

  // XMLErrorLog errorLog;

  /**
   * Equality comparison method for XMLAttributes.
   *
   * @param sb a reference to an object to which the current object
   * instance will be compared
   *
   * @return {@code true} if {@code sb} refers to the same underlying
   * native object as this one, {@code false} otherwise
   */
  @Override
  public boolean equals(Object sb)
  {
    if (sb instanceof XMLAttributes) {
      XMLAttributes xmlAttributes = (XMLAttributes) sb;

      for (XMLTriple xmlTriple : attributeNames) {
        if (!xmlAttributes.hasAttribute(xmlTriple)) {
          return false;
        }
        String value = getValue(xmlTriple);
        String value2 = xmlAttributes.getValue(xmlTriple);
        if (value == null && value2 != null) {
          return false;
        }
        if (value != null && (! value.equals(value2))) {
          return false;
        }
      }

      return true;
    }

    return false;
  }

  /**
   * Returns a hashcode for this XMLAttributes object.
   *
   * @return a hash code usable by Java methods that need them.
   */
  @Override
  public int hashCode()
  {
    int hashcode = 433;

    for (int i = 0; i < attributeNames.size(); i++) {

      if (attributeNames.get(i) != null) {
        hashcode += attributeNames.get(i).hashCode();
      }
      if (attributeValues.get(i) != null) {
        hashcode += attributeValues.get(i).hashCode();
      }
    }

    return hashcode;
  }


  /**
   * Creates a new empty {@link XMLAttributes} set.
   */
  public XMLAttributes() {

  }


  /**
   * Copy constructor; creates a copy of this {@link XMLAttributes} set.
   * 
   * @param orig the {@link XMLAttributes} to copy.
   */
  public XMLAttributes(XMLAttributes orig) {

    for (int i = 0; i < orig.attributeNames.size(); i++) {

      attributeNames.add(orig.attributeNames.get(i).clone());
      attributeValues.add(new String(orig.attributeValues.get(i)));
    }

  }


  /**
   * Creates and returns a deep copy of this {@link XMLAttributes} set.
   * <p>
   * @return a (deep) copy of this {@link XMLAttributes} set.
   */
  @Override
  public XMLAttributes clone() {
    return new XMLAttributes(this);
  }


  /**
   * Adds an attribute (a name/value pair) to this {@link XMLAttributes} set optionally
   * with a prefix and URI defining a namespace.
   * <p>
   * @param name a string, the local name of the attribute.
   * @param value a string, the value of the attribute.
   * @param namespaceURI a string, the namespace URI of the attribute.
   * @param prefix a string, the prefix of the namespace
   * <p>
   * @return integer value indicating success/failure of the
   * function.   The possible values
   * returned by this function are:
   * <ul>
   * <li> {@link JSBML#OPERATION_SUCCESS}
   * </ul>
   * <p>
   * @jsbml.note if local name with the same namespace URI already exists in this
   * attribute set, its value and prefix will be replaced.
   * <p>
   */
  public int add(String name, String value, String namespaceURI, String prefix) {

    attributeNames.add(new XMLTriple(name, namespaceURI, prefix));
    attributeValues.add(value);

    return JSBML.OPERATION_SUCCESS;
  }


  /**
   * Adds an attribute (a name/value pair) to this {@link XMLAttributes} set optionally
   * with a prefix and URI defining a namespace.
   * <p>
   * @param name a string, the local name of the attribute.
   * @param value a string, the value of the attribute.
   * @param namespaceURI a string, the namespace URI of the attribute.
   * <p>
   * @return integer value indicating success/failure of the
   * function.   The possible values
   * returned by this function are:
   * <ul>
   * <li> {@link JSBML#OPERATION_SUCCESS}
   * </ul>
   * <p>
   * @jsbml.note if local name with the same namespace URI already exists in this
   * attribute set, its value and prefix will be replaced.
   * <p>
   */
  public int add(String name, String value, String namespaceURI) {

    attributeNames.add(new XMLTriple(name, namespaceURI, null));
    attributeValues.add(value);

    return JSBML.OPERATION_SUCCESS;

  }


  /**
   * Adds an attribute (a name/value pair) to this {@link XMLAttributes} set optionally
   * with a prefix and URI defining a namespace.
   * <p>
   * @param name a string, the local name of the attribute.
   * @param value a string, the value of the attribute.
   * <p>
   * @return integer value indicating success/failure of the
   * function.   The possible values
   * returned by this function are:
   * <ul>
   * <li> {@link JSBML#OPERATION_SUCCESS}
   * </ul>
   * <p>
   * @jsbml.note if local name with the same namespace URI already exists in this
   * attribute set, its value and prefix will be replaced.
   * <p>
   */
  public int add(String name, String value) {
    // TODO - always check the name to see if it contain ':'. If yes set local name and prefix separately.

    attributeNames.add(new XMLTriple(name, null, null));
    attributeValues.add(value);

    return JSBML.OPERATION_SUCCESS;

  }


  /**
   * Adds an attribute with the given {@link XMLTriple}/value pair to this {@link XMLAttributes} set.
   * <p>
   * @jsbml.note if local name with the same namespace URI already exists in this attribute set,
   * its value and prefix will be replaced.
   * <p>
   * @param triple an {@link XMLTriple}, the XML triple of the attribute.
   * @param value a string, the value of the attribute.
   * <p>
   * @return integer value indicating success/failure of the
   * function.   The possible values
   * returned by this function are:
   * <ul>
   * <li> {@link JSBML#OPERATION_SUCCESS}
   * </ul>
   */
  public int add(XMLTriple triple, String value) {

    attributeNames.add(triple);
    attributeValues.add(value);

    return JSBML.OPERATION_SUCCESS;

  }


  /**
   * Removes an attribute with the given index from this {@link XMLAttributes} set.
   * <p>
   * @param n an integer the index of the resource to be deleted
   * @return integer value indicating success/failure of the
   * function.   The possible values
   * returned by this function are:
   * <ul>
   * <li> {@link JSBML#OPERATION_SUCCESS}
   * <li> {@link JSBML#INDEX_EXCEEDS_SIZE}
   * </ul>
   */
  public int removeResource(int n) {

    if (n < 0 || n >= attributeNames.size()) {
      return JSBML.INDEX_EXCEEDS_SIZE;
    }

    attributeNames.remove(n);
    attributeValues.remove(n);

    return JSBML.OPERATION_SUCCESS;

  }


  /**
   * Removes an attribute with the given index from this {@link XMLAttributes} set.
   * (This function is an alias of XMLAttributes.removeResource()).
   * <p>
   * @param n an integer the index of the resource to be deleted
   * <p>
   * @return integer value indicating success/failure of the
   * function.   The possible values
   * returned by this function are:
   * <ul>
   * <li> {@link JSBML#OPERATION_SUCCESS}
   * <li> {@link JSBML#INDEX_EXCEEDS_SIZE}
   * </ul>
   */
  public int remove(int n) {

    return removeResource(n);
  }


  /**
   * Removes an attribute with the given local name and namespace URI from
   * this {@link XMLAttributes} set.
   * <p>
   * @param name   a string, the local name of the attribute.
   * @param uri    a string, the namespace URI of the attribute.
   * <p>
   * @return integer value indicating success/failure of the
   * function.   The possible values
   * returned by this function are:
   * <ul>
   * <li> {@link JSBML#OPERATION_SUCCESS}
   * <li> {@link JSBML#OPERATION_FAILED}
   * </ul>
   */
  public int remove(String name, String uri) {

    int index = getIndex(name, uri);

    if (index != -1) {
      removeResource(index);
    } else {
      return JSBML.OPERATION_FAILED;
    }

    return JSBML.OPERATION_SUCCESS;

  }


  /**
   * Removes an attribute with the given local name from
   * this {@link XMLAttributes} set.
   * <p>
   * @param name   a string, the local name of the attribute.
   * <p>
   * @return integer value indicating success/failure of the
   * function.   The possible values
   * returned by this function are:
   * <ul>
   * <li> {@link JSBML#OPERATION_SUCCESS}
   * <li> {@link JSBML#OPERATION_FAILED}
   * </ul>
   */
  public int remove(String name) {

    int index = getIndex(name);

    if (index != -1) {
      removeResource(index);
    } else {
      return JSBML.OPERATION_FAILED;
    }

    return JSBML.OPERATION_SUCCESS;

  }


  /**
   * Removes an attribute with the given {@link XMLTriple} from this {@link XMLAttributes} set.
   * <p>
   * @param triple an {@link XMLTriple}, the XML triple of the attribute.
   * <p>
   * @return integer value indicating success/failure of the
   * function.   The possible values
   * returned by this function are:
   * <ul>
   * <li> {@link JSBML#OPERATION_SUCCESS}
   * </ul>
   */
  public int remove(XMLTriple triple) {

    int index = getIndex(triple);

    if (index != -1) {
      removeResource(index);
    } else {
      return JSBML.OPERATION_FAILED;
    }

    return JSBML.OPERATION_SUCCESS;

  }


  /**
   * Clears (deletes) all attributes in this {@link XMLAttributes} object.
   * <p>
   * @return integer value indicating success/failure of the
   * function.   The possible values
   * returned by this function are:
   * <ul>
   * <li> {@link JSBML#OPERATION_SUCCESS}
   * </ul>
   */
  public int clear() {

    attributeNames.clear();
    attributeValues.clear();

    return JSBML.OPERATION_SUCCESS;

  }


  /**
   * Returns the index of an attribute with the given name.
   * <p>
   * @jsbml.note A namespace bound to the name is not checked by this function.
   * Thus, if there are multiple attributes with the given local name and
   * different namespaces, the smallest index among those attributes will
   * be returned.  {@link XMLAttributes#getIndex(String name, String uri)} or
   * {@link XMLAttributes#getIndex(XMLTriple  triple)} should be used to get an index of an
   * attribute with the given local name and namespace.
   * <p>
   * @param name a string, the local name of the attribute for which the
   * index is required.
   * <p>
   * @return the index of an attribute with the given local name, or -1 if not present.
   */
  public int getIndex(String name) {

    int index = -1;

    for (int i = 0; i < attributeNames.size(); i++)
    {
      XMLTriple currentTriple = attributeNames.get(i);

      if (currentTriple.getName().equals(name)) {
        index = i;
        break;
      }
    }

    if (index != -1) {
      return index;
    }

    return -1;

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
  public int getIndex(String name, String uri) {

    // System.out.println("XMLAttributes : getIndex(String, String) : name = " + name);

    int index = -1;

    for (int i = 0; i < attributeNames.size(); i++)
    {
      XMLTriple currentTriple = attributeNames.get(i);

      if (currentTriple.getName().equals(name) && currentTriple.getURI().equals(uri)) {
        index = i;
        break;
      }
    }

    if (index != -1) {
      return index;
    }

    return -1;

  }


  /**
   * Returns the index of an attribute with the given {@link XMLTriple}.
   * <p>
   * @param triple an {@link XMLTriple}, the XML triple of the attribute for which
   *        the index is required.
   * <p>
   * @return the index of an attribute with the given {@link XMLTriple}, or -1 if not present.
   */
  public int getIndex(XMLTriple triple) {

    int index = attributeNames.indexOf(triple);

    if (index != -1) {
      return index;
    }

    return -1;

  }


  /**
   * Returns the number of attributes in the set.
   * <p>
   * @return the number of attributes in this {@link XMLAttributes} set.
   */
  public int getLength() {
    return attributeNames.size();
  }

  /**
   * 
   * @return
   * @see #getLength()
   */
  public int size() {
    return attributeNames.size();
  }

  /**
   * Returns the local name of an attribute in this {@link XMLAttributes} set (by position).
   * <p>
   * @param index an integer, the position of the attribute whose local name is
   * required.
   * <p>
   * @return the local name of an attribute in this list (by position).
   * <p>
   * @jsbml.note If index is out of range, an empty string will be returned.  Use
   * {@link XMLAttributes#hasAttribute(int index)} to test for the attribute
   * existence.
   */
  public String getName(int index) {

    if (index < 0 || index >= attributeNames.size()) {
      return "";
    }

    return attributeNames.get(index).getName();

  }


  /**
   * Returns the prefix of an attribute in this {@link XMLAttributes} set (by position).
   * <p>
   * @param index an integer, the position of the attribute whose prefix is
   * required.
   * <p>
   * @return the namespace prefix of an attribute in this list (by
   * position).
   * <p>
   * @jsbml.note If index is out of range, an empty string will be returned. Use
   * {@link XMLAttributes#hasAttribute(int index)} to test for the attribute
   * existence.
   */
  public String getPrefix(int index) {

    if (index < 0 || index >= attributeNames.size()) {
      return "";
    }

    return attributeNames.get(index).getPrefix();

  }


  /**
   * Returns the prefixed name of an attribute in this {@link XMLAttributes} set (by position).
   * <p>
   * @param index an integer, the position of the attribute whose prefixed
   * name is required.
   * <p>
   * @return the prefixed name of an attribute in this list (by
   * position).
   * <p>
   * @jsbml.note If index is out of range, an empty string will be returned.  Use
   * {@link XMLAttributes#hasAttribute(int index)} to test for attribute existence.
   */
  public String getPrefixedName(int index) {

    if (index < 0 || index >= attributeNames.size()) {
      return "";
    }

    XMLTriple triple = attributeNames.get(index);

    return triple.getPrefix().length() == 0 ? triple.getName() : triple.getPrefix() + ":" + triple.getName();

  }


  /**
   * Returns the namespace URI of an attribute in this {@link XMLAttributes} set (by position).
   * <p>
   * @param index an integer, the position of the attribute whose namespace URI is
   * required.
   * <p>
   * @return the namespace URI of an attribute in this list (by position).
   * <p>
   * @jsbml.note If index is out of range, an empty string will be returned.  Use
   * {@link XMLAttributes#hasAttribute(int index)} to test for attribute existence.
   */
  public String getURI(int index) {

    if (index < 0 || index >= attributeNames.size()) {
      return "";
    }

    return attributeNames.get(index).getURI();

  }


  /**
   * Returns the value of an attribute in this {@link XMLAttributes} set (by position).
   * <p>
   * @param index an integer, the position of the attribute whose value is
   * required.
   * <p>
   * @return the value of an attribute in the list (by position).
   * <p>
   * @jsbml.note If index is out of range, an empty string will be returned.  Use
   * {@link XMLAttributes#hasAttribute(int index)} to test for attribute existence.
   */
  public String getValue(int index) {

    if (index < 0 || index >= attributeNames.size()) {
      return "";
    }

    return attributeValues.get(index);

  }


  /**
   * Returns an attribute's value by name.
   * <p>
   * @param name a string, the local name of the attribute whose value is required.
   * <p>
   * @return The attribute value as a string.
   * <p>
   * @jsbml.note If an attribute with the given local name does not exist, an
   * empty string will be returned.  Use {@link XMLAttributes#hasAttribute(String name, String uri)}
   * to test for attribute existence.  A namespace bound to the local name
   * is not checked by this function.  Thus, if there are multiple
   * attributes with the given local name and different namespaces, the
   * value of an attribute with the smallest index among those attributes
   * will be returned.  {@link XMLAttributes#getValue(String name)} or
   * {@link XMLAttributes#getValue(XMLTriple  triple)} should be used to get a value of an
   * attribute with the given local name and namespace.
   */
  public String getValue(String name) {

    int index = getIndex(name);
    return index == -1 ? "" : attributeValues.get(index);
  }


  /**
   * Returns a value of an attribute with the given local name and namespace URI.
   * <p>
   * @param name a string, the local name of the attribute whose value is required.
   * @param uri  a string, the namespace URI of the attribute.
   * <p>
   * @return The attribute value as a string.
   * <p>
   * @jsbml.note If an attribute with the given local name and namespace URI does
   * not exist, an empty string will be returned.  Use
   * {@link XMLAttributes#hasAttribute(String name, String uri)}
   * to test for attribute existence.
   */
  public String getValue(String name, String uri) {

    int index = getIndex(name, uri);

    if (index == -1) {
      return "";
    }

    return attributeValues.get(index);

  }


  /**
   * Returns a value of an attribute with the given {@link XMLTriple}.
   * <p>
   * @param triple an {@link XMLTriple}, the XML triple of the attribute whose
   *        value is required.
   * <p>
   * @return The attribute value as a string.
   * <p>
   * @jsbml.note If an attribute with the given {@link XMLTriple} does not exist, an
   * empty string will be returned.  Use
   * {@link XMLAttributes#hasAttribute(XMLTriple  triple)} to test for attribute existence.
   */
  public String getValue(XMLTriple triple) {

    int index = getIndex(triple);

    if (index == -1) {
      return "";
    }

    return attributeValues.get(index);

  }


  /**
   * Returns {@code true} or {@code false} depending on whether
   * an attribute with the given index exists in this {@link XMLAttributes}.
   * <p>
   * @param index an integer, the position of the attribute.
   * <p>
   * @return {@code true} if an attribute with the given index exists in this
   * {@link XMLAttributes}, {@code false} otherwise.
   */
  public boolean hasAttribute(int index) {

    if (index >= 0 && index < attributeNames.size()) {
      return true;
    }

    return false;

  }


  /**
   * Returns {@code true} or {@code false} depending on whether
   * an attribute with the given local name and namespace URI exists in this
   * {@link XMLAttributes}.
   * <p>
   * @param name a string, the local name of the attribute.
   * @param uri  a string, the namespace URI of the attribute.
   * <p>
   * @return {@code true} if an attribute with the given local name and namespace
   * URI exists in this {@link XMLAttributes}, {@code false} otherwise.
   */
  public boolean hasAttribute(String name, String uri) {

    int index = getIndex(name, uri);

    if (index != -1) {
      return true;
    }

    return false;

  }


  /**
   * Returns {@code true} or {@code false} depending on whether
   * an attribute with the given local name exists in this
   * {@link XMLAttributes}.
   * <p>
   * @param name a string, the local name of the attribute.
   * <p>
   * @return {@code true} if an attribute with the given local name
   *  exists in this {@link XMLAttributes}, {@code false} otherwise.
   */
  public boolean hasAttribute(String name) {

    int index = getIndex(name);

    if (index != -1) {
      return true;
    }

    return false;

  }


  /**
   * Returns {@code true} or {@code false} depending on whether
   * an attribute with the given XML triple exists in this {@link XMLAttributes}.
   * <p>
   * @param triple an {@link XMLTriple}, the XML triple of the attribute
   * <p>
   * @return {@code true} if an attribute with the given XML triple exists in this
   * {@link XMLAttributes}, {@code false} otherwise.
   * <p>
   */
  public boolean hasAttribute(XMLTriple triple) {

    int index = getIndex(triple);

    if (index != -1) {
      return true;
    }

    return false;

  }


  /**
   * Returns {@code true} or {@code false} depending on whether
   * this {@link XMLAttributes} set is empty.
   * <p>
   * @return {@code true} if this {@link XMLAttributes} set is empty, {@code false} otherwise.
   */
  public boolean isEmpty() {

    return attributeNames.size() == 0;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder attributesStr = new StringBuilder(getClass().getSimpleName()).append('[');

    int i = 0;
    for (String value : attributeValues) {
      XMLTriple triple = attributeNames.get(i);
      if (i > 0) {
        attributesStr.append(", ");
      }
      attributesStr.append(triple).append('=').append(value);

      i++;
    };
    return attributesStr.append(']').toString();
  }

}
