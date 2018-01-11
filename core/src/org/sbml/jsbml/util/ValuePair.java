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

/**
 * A pair of two values with type parameters. This data object is useful
 * whenever exactly two values are required for a specific task.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @param <L>
 * @param <V>
 */
public class ValuePair<L extends Comparable<? super L>, V extends Comparable<? super V>> extends Pair<L, V>
implements Comparable<ValuePair<L, V>> {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -4230267902609475128L;

  /**
   * Static method to easily create a value pair of two given values.
   * 
   * @param left
   * @param right
   * @return
   */
  public static <L extends Comparable<? super L>, V extends Comparable<? super V>> ValuePair<L, V> of(L left, V right) {
    return new ValuePair<L, V>(left, right);
  }

  /**
   * Can be used for static import, i.e. use
   * <pre class="brush:java">
   * import static org.sbml.jsbml.util.ValuePair.pairOf
   * </pre>
   * in your class to easily create {@link ValuePair}s of arbitrary data types
   * with a simple method call, e.g., {@code valuePairOf(1, 2)}.
   * 
   * @param left
   * @param right
   * @return
   */
  public static <L extends Comparable<? super L>, V extends Comparable<? super V>> ValuePair<L, V> valuePairOf(L left, V right) {
    return of(left, right);
  }

  /**
   * Creates a new {@link ValuePair} with both attributes set to {@code null}.
   */
  public ValuePair() {
    super();
  }

  /**
   * 
   * @param l
   * @param v
   */
  public ValuePair(L l, V v) {
    super(l, v);
  }

  /**
   * 
   * @param valuePair
   */
  public ValuePair(ValuePair<L, V> valuePair) {
    super(valuePair);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public ValuePair<L, V> clone() throws CloneNotSupportedException {
    return new ValuePair<L, V>(this);
  }

  /**
   * Convenient method to compare two values to this {@link ValuePair}.
   * 
   * @param l
   * @param v
   * @return a negative integer, zero, or a positive integer as this
   *         {@link ValuePair} is less than, equal to, or greater than the
   *         combination of the two given values.
   * @see #compareTo(ValuePair)
   */
  public int compareTo(L l, V v) {
    return compareTo(new ValuePair<L, V>(l, v));
  }

  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(ValuePair<L, V> v) {
    if (equals(v)) {
      return 0;
    }
    if (!isSetL()) {
      return Integer.MIN_VALUE;
    }
    if (!v.isSetL()) {
      return Integer.MAX_VALUE;
    }
    int comp = getL().compareTo(v.getL());
    if (comp == 0) {
      if (!isSetV()) {
        return -1;
      }
      if (!v.isSetV()) {
        return 1;
      }
      return getV().compareTo(v.getV());
    }
    return comp;
  }

  /**
   * @return the v
   */
  public V getV() {
    return getValue();
  }

  /**
   * 
   * @return
   */
  public boolean isSetL() {
    return isSetKey();
  }

  /**
   * 
   * @return
   */
  public boolean isSetV() {
    return isSetValue();
  }

  /**
   * @param l
   *            the l to set
   */
  public void setL(L l) {
    setKey(l);
  }

  /**
   * @param v
   *            the v to set
   */
  public void setV(V v) {
    setValue(v);
  }

  /**
   * @return the l
   */
  public L getL() {
    return getKey();
  }

}
