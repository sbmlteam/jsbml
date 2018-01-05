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
package org.sbml.jsbml.xml.stax;

import java.util.Map;
import java.util.TreeMap;

/**
 * An SBMLObjectForXML is an object to store the localName, prefix, namespace
 * URI, attributes and text of a SBML component we want to write.
 * 
 * @author marine
 * @since 0.8
 */
public class SBMLObjectForXML {

  /**
   * Represents the namespace URI of a SBML component to write.
   */
  private String namespace;
  /**
   * Represents the prefix of a SBML component to write.
   */
  private String prefix;
  /**
   * Represents the localName of a SBML component to write.
   */
  private String name;
  /**
   * Contains the XML attributes of a SBML component to write.
   */
  private Map<String, String> attributes;
  /**
   * Represents the text of a SBML component to write.
   */
  private String characters;

  /**
   * Adds an attribute to this attributes HashMap.
   * 
   * @param attributes
   *            the attributes to set
   */
  public void addAttributes(Map<String, String> attributes) {
    getAttributes().putAll(attributes);
  }

  /**
   * Sets all the variable of this object to {@code null}.
   */
  public void clear() {
    attributes = null;
    characters = null;
    name = null;
    namespace = null;
    prefix = null;
  }

  /**
   * @return the attributes
   */
  public Map<String, String> getAttributes() {
    if (attributes == null) {
      attributes = new TreeMap<String, String>();
    }
    return attributes;
  }

  /**
   * @return the characters
   */
  public String getCharacters() {
    return characters;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @return the namespace
   */
  public String getNamespace() {
    return namespace;
  }

  /**
   * @return the prefix
   */
  public String getPrefix() {
    return prefix;
  }

  /**
   * 
   * @return {@code true} if the attributes of this object is not {@code null}.
   */
  public boolean isSetAttributes() {
    return attributes != null;
  }

  /**
   * 
   * @return {@code true} if the characters of this object is not {@code null}.
   */
  public boolean isSetCharacters() {
    return characters != null;
  }

  /**
   * 
   * @return {@code true} if the name of this object is not {@code null}.
   */
  public boolean isSetName() {
    return name != null;
  }

  /**
   * 
   * @return {@code true} if the namespace of this Object is not null;
   */
  public boolean isSetNamespace() {
    return namespace != null;
  }

  /**
   * 
   * @return {@code true} if the prefix of this Object is not {@code null}.
   */
  public boolean isSetPrefix() {
    return prefix != null;
  }

  /**
   * @param characters
   *            the characters to set
   */
  public void setCharacters(String characters) {
    this.characters = characters;
  }

  /**
   * @param name
   *            the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @param namespace
   *            the namespace to set
   */
  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  /**
   * @param prefix
   *            the prefix to set
   */
  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "SBMLObjectForXML [attributes=" + attributes + ", name=" + name
        + ", namespace=" + namespace + ", prefix=" + prefix + "]";
  }

}
