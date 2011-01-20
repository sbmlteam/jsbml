/*
 * $Id$
 * $URL$
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
 * A variable of the system is a {@link Quantity} whose value may change during
 * a simulation. To decide whether it has to stay constant, this interface
 * provides the necessary methods.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @date 2010-04-20
 */
public interface Variable extends Quantity {

	/**
	 * 
	 * @return the constant boolean of this variable.
	 */
	public boolean getConstant();

	/**
	 * 
	 * @return the constant value if it is set, false otherwise.
	 */
	public boolean isConstant();

	/**
	 * 
	 * @return true if the constant boolean of this Variable is not null.
	 */
	public boolean isSetConstant();

	/**
	 * Sets the constant boolean of this Variable.
	 * 
	 * @param constant
	 */
	public void setConstant(boolean constant);

	/**
	 * With this method the constant property of this variable will be set to an
	 * undefined state.
	 */
	public void unsetConstant();
}
