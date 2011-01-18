/*
 * $Id:  SBaseWithDefinedUnit.java 10:26:46 draeger $
 * $URL: SBaseWithDefinedUnit.java $
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
package org.sbml.jsbml;

import org.sbml.jsbml.Unit.Kind;

/**
 * Defines a kind of {@link SBase} that is equipped with a defined unit. This
 * means, instances of this interface have a derived unit and a defined unit and
 * provide methods to manipulate the kind of {@link Unit} or
 * {@link UnitDefinition} associated with this data type.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-12-15
 */
public interface SBaseWithUnit extends SBaseWithDerivedUnit {

	/**
	 * 
	 * @return the unitsID of this {@link SBaseWithUnit}. The empty
	 *         {@link String} if it is not set.
	 */
	public String getUnits();

	/**
	 * 
	 * @return The UnitDefinition instance which has the {@link #unitsID} of
	 *         this {@link SBaseWithUnit} as id. Null if it doesn't
	 *         exist. In case that the unit of this {@link SBaseWithUnit}
	 *         represents a base {@link Unit}, a new {@link UnitDefinition} will
	 *         be created and returned by this method. This new
	 *         {@link UnitDefinition} will only contain the one unit represented
	 *         by the unit identifier in this {@link SBaseWithUnit}. Note
	 *         that the corresponding model will not contain this
	 *         {@link UnitDefinition}. The identifier of this new
	 *         {@link UnitDefinition} will be set to the same value as the name
	 *         of the base {@link Unit}.
	 */
	public UnitDefinition getUnitsInstance();

	/**
	 * 
	 * @return true if the unitsID of this element is not null.
	 */
	public boolean isSetUnits();

	/**
	 * Checks whether the element referenced by the <code>unitsID</code>
	 * attribute of this {@link SBaseWithUnit} is either an existing base
	 * {@link Unit}, i.e., a simple {@link Unit} that can directly be addressed
	 * via its {@link Kind} element, or the identifier of an existing
	 * {@link UnitDefinition} in the {@link Model} to which this
	 * {@link SBaseWithUnit} belongs.
	 * 
	 * @return true if either a {@link UnitDefinition}, which has the
	 *         <code>unitsID</code> of this {@link SBaseWithUnit}, can be
	 *         found in the {@link Model} to which this
	 *         {@link SBaseWithUnit} belongs, or if this element's
	 *         <code>unitsID</code> as a corresponding base {@link Unit} for the
	 *         given Level/Version combination.
	 */
	public boolean isSetUnitsInstance();

	/**
	 * Sets the unitsID of this {@link SBaseWithUnit}. Only valid unit
	 * kind names or identifiers of already existing {@link UnitDefinition}s are
	 * allowed arguments of this function.
	 * 
	 * @param units
	 *            the identifier of an already existing {@link UnitDefinition}
	 *            or an {@link Unit.Kind} identifier for the current
	 *            level/version combination of this unit. Passing a null value
	 *            to this method is equivalent to calling {@link #unsetUnits()}.
	 */
	public void setUnits(String units);

	/**
	 * Sets the {@link Unit} of this {@link SBaseWithUnit}.
	 * 
	 * @param unit
	 */
	public void setUnits(Unit unit);

	/**
	 * Sets the unit of this {@link SBaseWithUnit}.
	 * 
	 * A new unit will be created base on this kind.
	 * 
	 * @param unitKind
	 */
	public void setUnits(Unit.Kind unitKind);

	/**
	 * Set the unit attribute of this {@link SBaseWithUnit} to the given
	 * unit definition.
	 * 
	 * @param units
	 */
	public void setUnits(UnitDefinition units);

	/**
	 * Sets the unitsID of this {@link SBaseWithUnit} to null.
	 */
	public void unsetUnits();

}
