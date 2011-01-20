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
 * This type represents an SBase object that is associated to a unit. This may
 * be a directly defined unit or a unit that has to be derived by evaluating
 * other elements within this object.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @date 2010-04-20
 */
public interface SBaseWithDerivedUnit extends SBase {

	/**
	 * Returns <code>true</code> or <code>false</code> depending on whether this
	 * {@link SBaseWithDerivedUnit} refers to elements such as parameters or
	 * numbers with undeclared units.
	 * 
	 * A return value of true indicates that the <code>UnitDefinition</code>
	 * returned by {@see getDerivedUnitDefinition()} may not accurately
	 * represent the units of the expression.
	 * 
	 * @return <code>true</code> if the math expression of this {@link SBaseWithDerivedUnit}
	 *         includes parameters/numbers with undeclared units,
	 *         <code>false</code> otherwise.
	 */
	public boolean containsUndeclaredUnits();

	/**
	 * This method derives the unit of this quantity and tries to identify an
	 * equivalent {@link UnitDefinition} within the corresponding {@link Model}.
	 * If no equivalent unit definition can be found, a new unit definition will
	 * be created that is not part of the model but represents the unit of this
	 * quantity. If it is not possible to derive a unit for this quantity, null
	 * will be returned.
	 * 
	 * @return
	 */
	public UnitDefinition getDerivedUnitDefinition();

	/**
	 * This method derives the unit of this quantity. If the model that contains
	 * this quantity already contains a unit that is equivalent to the derived
	 * unit, the corresponding identifier will be returned. In case that the
	 * unit cannot be derived or that no equivalent unit exists within the
	 * model, or if the model has not been defined yet, null will be returned.
	 * In case that this quantity represents a basic {@link Unit.Kind} this
	 * method will return the {@link String} representation of this
	 * {@link Unit.Kind}.
	 * 
	 * @return
	 */
	public String getDerivedUnits();

}
