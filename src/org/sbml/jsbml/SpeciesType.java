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

import java.util.HashMap;

/**
 * Represents the speciesType XML element of a SBML file. It is @deprecated
 * since level 3.
 * 
 * @author Andreas Dr&auml;ger
 * @deprecated
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public class SpeciesType extends AbstractNamedSBase {

	/**
	 * Creates a SpeciesType instance.
	 */
	@Deprecated
	public SpeciesType() {
		super();
	}

	/**
	 * Creates a SpeciesType instance from a given SpeciesType.
	 * 
	 * @param nsb
	 */
	@Deprecated
	public SpeciesType(SpeciesType nsb) {
		super(nsb);
	}

	/**
	 * Creates a SpeciesType instance from an id, level and version.
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	@Deprecated
	public SpeciesType(String id, int level, int version) {
		super(id, level, version);
	}

	/**
	 * Creates a SpeciesType instance from an id, name, level and version.
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	@Deprecated
	public SpeciesType(String id, String name, int level, int version) {
		super(id, name, level, version);
	}

	/**
	 * Creates a SpeciesType instance from a level and version.
	 * 
	 * @param level
	 * @param version
	 */
	@Deprecated
	public SpeciesType(int level, int version) {
		super(level, version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	// @Override
	@Deprecated
	public SpeciesType clone() {
		return new SpeciesType(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);

		return isAttributeRead;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();

		return attributes;
	}
}
