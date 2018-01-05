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
package org.sbml.jsbml.util;

import java.io.Serializable;
import java.util.Map.Entry;

/**
 * A general implementation of a 2-tuple, i.e., two arbitrary objects.
 * 
 * @author Andreas Dr&auml;ger
 * @param <L>
 * @param <V>
 * @since 1.0
 */
public class Pair<L, V> implements Cloneable, Entry<L, V>, Serializable {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 6483695107543561182L;

  /**
   * Static method to easily create a value pair of two given values.
   * 
   * @param left
   * @param right
   * @return
   */
  public static <L, V> Pair<L, V> of(L left, V right) {
    return new Pair<L, V>(left, right);
  }

  /**
   * Can be used for static import in your class to easily create {@link Pair}s
   * of arbitrary data types  with a simple method call, e.g.,
   * {@code pairOf(1, 2)}. This means, you can use
   * 
   * <pre class="brush:java">
   * import static org.sbml.jsbml.util.Pair.pairOf
   * </pre>
   * 
   * @param left
   * @param right
   * @return
   */
  public static <L, V> Pair<L, V> pairOf(L left, V right) {
    return of(left, right);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (isSetKey() ? 0 : l.hashCode());
    result = prime * result + (isSetValue() ? 0 : v.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    boolean equals = (object != null) && object.getClass().equals(getClass());
    if (equals) {
      Pair<?, ?> other = (Pair<?, ?>) object;
      equals &= isSetKey() == other.isSetKey();
      if (equals && isSetKey()) {
        equals &= getKey().equals(other.getKey());
      }
      equals &= isSetValue() == other.isSetValue();
      if (equals && isSetValue()) {
        equals &= getValue().equals(other.getValue());
      }
    }
    return equals;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  protected Object clone() throws CloneNotSupportedException {
    return new Pair<L, V>(this);
  }

  /**
   * Left value.
   */
  private L l;

  /**
   * Right value.
   */
  private V v;

  /**
   * 
   */
  public Pair() {
    this(null, null);
  }

  /**
   * 
   * @param l
   * @param v
   */
  public Pair(L l, V v) {
    super();
    setKey(l);
    setValue(v);
  }

  /**
   * 
   * @param pair
   */
  public Pair(Pair<L, V> pair) {
    l = pair.getKey();
    v = pair.getValue();
  }

  /**
   * 
   * @param l
   * @return
   */
  public L setKey(L l) {
    L key = getKey();
    this.l = l;
    return key;
  }

  /* (non-Javadoc)
   * @see java.util.Map.Entry#getKey()
   */
  @Override
  public L getKey() {
    return l;
  }

  /* (non-Javadoc)
   * @see java.util.Map.Entry#getValue()
   */
  @Override
  public V getValue() {
    return v;
  }

  /* (non-Javadoc)
   * @see java.util.Map.Entry#setValue(java.lang.Object)
   */
  @Override
  public V setValue(V value) {
    V v = getValue();
    this.v = value;
    return v;
  }

  /**
   * 
   * @return
   */
  public boolean isSetKey() {
    return l != null;
  }

  /**
   * 
   * @return
   */
  public boolean isSetValue() {
    return v != null;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return '[' + (isSetKey() ? getKey().toString() : "null") + ", "
        + (isSetValue() ? getValue().toString() : "null") + ']';
  }

}
