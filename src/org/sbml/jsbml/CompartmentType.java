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
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * @date 2009-08-31
 */
public class CompartmentType extends AbstractNamedSBase {

	/**
	 * @param nsb
	 */
	public CompartmentType(CompartmentType nsb) {
		super(nsb);
	}

	/**
	 * @param id
	 */
	public CompartmentType(String id, int level, int version) {
		super(id, level, version);
	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public CompartmentType(String id, String name, int level, int version) {
		super(id, name, level, version);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.AbstractSBase#clone()
	 */
	// @Override
	public CompartmentType clone() {
		return new CompartmentType(this);
	}

}
