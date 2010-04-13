/*
 * $Id: AbstractNamedSBase.java 173 2010-04-09 06:32:34Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/AbstractNamedSBase.java $
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
 * A quantity is an element that represents a value with an associated unit.
 * Both the value and the unit may be directly declared by the quantity or may
 * have to be derived.
 * 
 * @author Andreas Dr&auml;ger
 * 
 */
public interface Quantity extends SBase {

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
