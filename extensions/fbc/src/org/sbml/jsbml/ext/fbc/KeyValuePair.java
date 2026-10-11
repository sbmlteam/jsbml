/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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
package org.sbml.jsbml.ext.fbc;

import java.io.Serializable;
import java.util.Objects;

/**
 * A key-value pair of FBC version 3: a {@code keyValuePair} element of the
 * {@code listOfKeyValuePairs} in the annotation of an SBML element, in the
 * namespace {@link FBCConstants#KEY_VALUE_PAIR_NAMESPACE}.
 * 
 * <p>A {@link KeyValuePair} is an immutable value. The pairs of an element
 * are read and written with {@link KeyValuePairs}, the annotation stays the
 * only place they are stored in.</p>
 * 
 * @since 1.7
 * @see KeyValuePairs
 */
public final class KeyValuePair implements Serializable {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 2863117474306424785L;

  /**
   * The required key.
   */
  private final String key;

  /**
   * The optional value.
   */
  private final String value;

  /**
   * The optional URI that defines the key or value.
   */
  private final String uri;

  /**
   * The optional id.
   */
  private final String id;

  /**
   * The optional name.
   */
  private final String name;

  /**
   * Creates a {@link KeyValuePair} with a key only.
   * 
   * @param key the key, not {@code null}.
   */
  public KeyValuePair(String key) {
    this(key, null, null, null, null);
  }

  /**
   * Creates a {@link KeyValuePair} with a key and a value.
   * 
   * @param key the key, not {@code null}.
   * @param value the value, can be {@code null}.
   */
  public KeyValuePair(String key, String value) {
    this(key, value, null, null, null);
  }

  /**
   * Creates a {@link KeyValuePair} with a key, a value and a URI.
   * 
   * @param key the key, not {@code null}.
   * @param value the value, can be {@code null}.
   * @param uri the URI, can be {@code null}.
   */
  public KeyValuePair(String key, String value, String uri) {
    this(key, value, uri, null, null);
  }

  /**
   * Creates a {@link KeyValuePair}.
   * 
   * @param key the key, not {@code null}.
   * @param value the value, can be {@code null}.
   * @param uri the URI, can be {@code null}.
   * @param id the id, can be {@code null}.
   * @param name the name, can be {@code null}.
   * @throws NullPointerException if the key is {@code null}.
   */
  public KeyValuePair(String key, String value, String uri, String id, String name) {
    this.key = Objects.requireNonNull(key, "The key of a key-value pair is required.");
    this.value = value;
    this.uri = uri;
    this.id = id;
    this.name = name;
  }

  /**
   * @return the key.
   */
  public String getKey() {
    return key;
  }

  /**
   * @return the value, {@code null} if it is not set.
   */
  public String getValue() {
    return value;
  }

  /**
   * @return whether the value is set.
   */
  public boolean isSetValue() {
    return value != null;
  }

  /**
   * @return the URI, {@code null} if it is not set.
   */
  public String getUri() {
    return uri;
  }

  /**
   * @return whether the URI is set.
   */
  public boolean isSetUri() {
    return uri != null;
  }

  /**
   * @return the id, {@code null} if it is not set.
   */
  public String getId() {
    return id;
  }

  /**
   * @return whether the id is set.
   */
  public boolean isSetId() {
    return id != null;
  }

  /**
   * @return the name, {@code null} if it is not set.
   */
  public String getName() {
    return name;
  }

  /**
   * @return whether the name is set.
   */
  public boolean isSetName() {
    return name != null;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return Objects.hash(key, value, uri, id, name);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof KeyValuePair)) {
      return false;
    }
    KeyValuePair other = (KeyValuePair) obj;
    return key.equals(other.key) && Objects.equals(value, other.value)
        && Objects.equals(uri, other.uri) && Objects.equals(id, other.id)
        && Objects.equals(name, other.name);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "KeyValuePair [key=" + key + ", value=" + value + ", uri=" + uri
        + ", id=" + id + ", name=" + name + "]";
  }

}
