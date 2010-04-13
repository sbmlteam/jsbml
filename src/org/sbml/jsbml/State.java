/*
 * $Id: Symbol.java 173 2010-04-09 06:32:34Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/Symbol.java $
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
 * A state of the system is a quantity, i.e., a value with a unit that may
 * change during a simulation. To decide whether it has to stay constant, this
 * interface provides the necessary methods.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-04-13
 */
public interface State extends NamedSBase, Quantity {

	/**
	 * 
	 * @return the constant Boolean of this State.
	 */
	public boolean getConstant();

	/**
	 * 
	 * @return the constant value if it is set, false otherwise.
	 */
	public boolean isConstant();

	/**
	 * 
	 * @return true if the constant Boolean of this State is not null.
	 */
	public boolean isSetConstant();

	/**
	 * Sets the constant Boolean of this State.
	 * 
	 * @param constant
	 */
	public void setConstant(boolean constant);
}
