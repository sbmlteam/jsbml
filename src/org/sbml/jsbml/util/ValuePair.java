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
 * 4. The University of California, San Diego, La Jolla, CA, USA
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

/**
 * A pair of two values with type parameters. This data object is useful
 * whenever exactly two values are required for a specific task.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-09-01
 * @since 0.8
 * @version $Rev$
 */
public class ValuePair<L extends Comparable<? super L>, V extends Comparable<? super V>>
		implements Cloneable, Comparable<ValuePair<L, V>>, Serializable {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -4230267902609475128L;
	/**
	 * 
	 */
	private L l;
	/**
	 * 
	 */
	private V v;

	/**
	 * Creates a new {@link ValuePair} with both attributes set to {@code null}.
	 */
	public ValuePair() {
		this(null, null);
	}

	/**
	 * 
	 * @param l
	 * @param v
	 */
	public ValuePair(L l, V v) {
		this.setL(l);
		this.setV(v);
	}

	/**
	 * 
	 * @param valuePair
	 */
	public ValuePair(ValuePair<L, V> valuePair) {
		this.l = valuePair.getL();
		this.v = valuePair.getV();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ValuePair<L, V> clone() {
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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object object) {
		if (object.getClass().equals(getClass())) {
			try {
				ValuePair<L, V> v = (ValuePair<L, V>) object;
				boolean equal = isSetL() == v.isSetL();
				equal &= isSetV() == v.isSetV();
				if (equal && isSetL() && isSetV()) {
					equal &= v.getL().equals(getL()) && v.getV().equals(getV());
				}
				return equal;
			} catch (ClassCastException exc) {
				return false;
			}
		}
		return false;
	}

	/**
	 * @return the a
	 */
	public L getL() {
		return l;
	}

	/**
	 * @return the b
	 */
	public V getV() {
		return v;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return l.hashCode() + v.hashCode();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetL() {
		return l != null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetV() {
		return v != null;
	}

	/**
	 * @param l
	 *            the l to set
	 */
	public void setL(L l) {
		this.l = l;
	}

	/**
	 * @param v
	 *            the v to set
	 */
	public void setV(V v) {
		this.v = v;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("[%s, %s]", getL().toString(), getV().toString());
	}
}
