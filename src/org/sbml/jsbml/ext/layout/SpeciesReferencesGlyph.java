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

package org.sbml.jsbml.ext.layout;

import org.sbml.jsbml.AbstractNamedSBase;

/**
 * @author
 */
public class SpeciesReferencesGlyph extends AbstractNamedSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -8810905237933499989L;
	/**
	 * 
	 */
	private SpeciesGlyph speciesGlyph;
	/**
	 * 
	 */
	private String speciesReference;
	/**
	 * 
	 */
	private SpeciesReferenceRole role;
	/**
	 * 
	 */
	private Curve curve;

	/**
	 * 
	 */
	public SpeciesReferencesGlyph() {
		super();
	}
	
	public SpeciesReferencesGlyph(String id) {
		super(id);
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public SpeciesReferencesGlyph(int level, int version) {
		super(level, version);
	}

	/**
	 * 
	 * @param speciesReferencesGlyph
	 */
	public SpeciesReferencesGlyph(SpeciesReferencesGlyph speciesReferencesGlyph) {
		super(speciesReferencesGlyph);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public SpeciesReferencesGlyph clone() {
		return new SpeciesReferencesGlyph(this);
	}
}
