/*
 * $Id:  ValuePair.java 10:59:16 draeger $
 * $URL: ValuePair.java $
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */
package org.sbml.jsbml.util;

/**
 * A pair of two values with type parameters. This data object is useful
 * whenever exactly two values are required for a specific task.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-09-01
 */
public class ValuePair<S extends Comparable<S>, T extends Comparable<T>>
		implements Comparable<ValuePair<S, T>> {

	/**
	 * 
	 */
	private S a;
	/**
	 * 
	 */
	private T b;

	/**
	 * 
	 * @param a
	 * @param b
	 */
	public ValuePair(S a, T b) {
		this.setA(a);
		this.setB(b);
	}

	/**
	 * 
	 * @param valuePair
	 */
	public ValuePair(ValuePair<S, T> valuePair) {
		this.a = valuePair.getA();
		this.b = valuePair.getB();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ValuePair<S, T> clone() {
		return new ValuePair<S, T>(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(ValuePair<S, T> v) {
		if (equals(v)) {
			return 0;
		}
		if (!isSetA()) {
			return Integer.MIN_VALUE;
		}
		if (!v.isSetA()) {
			return Integer.MAX_VALUE;
		}
		int comp = getA().compareTo(v.getA());
		if (comp == 0) {
			if (!isSetB()) {
				return -1;
			}
			if (!v.isSetB()) {
				return 1;
			}
			return getB().compareTo(v.getB());
		}
		return comp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object o) {
		if (o instanceof ValuePair) {
			try {
				ValuePair<S, T> v = (ValuePair<S, T>) o;
				boolean equal = true;
				equal &= isSetA() == v.isSetA();
				equal &= isSetB() == v.isSetB();
				if (equal && isSetA() && isSetB()) {
					equal &= v.getA().equals(getA()) && v.getB().equals(getB());
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
	public S getA() {
		return a;
	}

	/**
	 * @return the b
	 */
	public T getB() {
		return b;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetA() {
		return a != null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetB() {
		return b != null;
	}

	/**
	 * @param a
	 *            the a to set
	 */
	public void setA(S a) {
		this.a = a;
	}

	/**
	 * @param b
	 *            the b to set
	 */
	public void setB(T b) {
		this.b = b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("[%s, %s]", getA(), getB());
	}
}
