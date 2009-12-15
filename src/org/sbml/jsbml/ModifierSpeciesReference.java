/*
 * $Id: ModifierSpeciesReference.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/ModifierSpeciesReference.java $
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

import java.util.HashMap;

/**
 * Represents the modifierSpeciesReference XML element of a SBML file.
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public class ModifierSpeciesReference extends SimpleSpeciesReference {

	/**
	 * Creates a ModifierSpeciesReference instance.
	 */
	public ModifierSpeciesReference() {
		super();
	}
	
	/**
	 * Creates a ModifierSpeciesReference instance from a given ModifierSpeciesReference.
	 * @param modifierSpeciesReference
	 */
	public ModifierSpeciesReference(
			ModifierSpeciesReference modifierSpeciesReference) {
		super(modifierSpeciesReference);
	}

	/**
	 * Creates a ModifierSpeciesReference instance from a given Species.
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
	// @Override
	public ModifierSpeciesReference clone() {
		return new ModifierSpeciesReference(this);
	}
	
	/*
	 * (non-Javadoc)
	 * org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		return isAttributeRead;
	}
	/*
	 * (non-Javadoc)
	 * org.sbml.jsbml.element.SBase#writeXMLAttributes() 
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		
		return attributes;
	}
}
