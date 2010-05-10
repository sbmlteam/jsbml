/*
 * $Id$
 * $URL$
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


/**
 * Represents the modifierSpeciesReference XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public class ModifierSpeciesReference extends SimpleSpeciesReference {

	/**
	 * Creates a ModifierSpeciesReference instance.
	 */
	public ModifierSpeciesReference() {
		super();
	}

	/**
	 * Creates a ModifierSpeciesReference instance from a given
	 * ModifierSpeciesReference.
	 * 
	 * @param modifierSpeciesReference
	 */
	public ModifierSpeciesReference(
			ModifierSpeciesReference modifierSpeciesReference) {
		super(modifierSpeciesReference);
	}

	/**
	 * Creates a ModifierSpeciesReference instance from a given Species.
	 * 
	 * @param modifierSpeciesReference
	 */
	public ModifierSpeciesReference(Species species) {
		super(species);
	}

	public ModifierSpeciesReference(int level, int version) {
		super(level, version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#clone()
	 */
	@Override
	public ModifierSpeciesReference clone() {
		return new ModifierSpeciesReference(this);
	}
}
