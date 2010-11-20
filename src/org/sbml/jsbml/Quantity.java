/*
 * $Id: Quantity.java 173 2010-04-09 06:32:34Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/Quantity.java $
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
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
package org.sbml.jsbml;

/**
 * A quantity is an element that represents a value with an associated unit that
 * can be addressed through the identifier or name attribute of this element.
 * Both the value and the unit may be directly declared by the quantity or may
 * have to be derived.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @date 2010-04-20
 */
public interface Quantity extends NamedSBaseWithDerivedUnit {

	/**
	 * Returns the value of this {@link Quantity}.
	 * 
	 * In {@link Compartment}s the value is its size, in {@link Species} the
	 * value defines its initial amount or concentration, and in
	 * {@link Parameter}s and {@link LocalParameter}s this returns the value
	 * attribute from SBML.
	 * 
	 * @return the value
	 */
	public double getValue();

	/**
	 * Returns true if the value of this {@link Quantity} is set.
	 * 
	 * @return true if the value of this {@link Quantity} is set.
	 */
	public boolean isSetValue();

	/**
	 * Sets the value of this {@link Quantity}.
	 * 
	 * Note that the meaning of the value can be different in all derived
	 * classes. In {@link Compartment}s the value defines its size. In
	 * {@link Species} the value describes either the initial amount or the
	 * initial concentration. Only the class {@link Parameter} and
	 * {@link LocalParameter} really define a value attribute with this name.
	 * 
	 * @param value
	 *            the value to set
	 */
	public void setValue(double value);

	/**
	 * Unsets the value of this {@link Quantity}.
	 * 
	 */
	public void unsetValue();

}
