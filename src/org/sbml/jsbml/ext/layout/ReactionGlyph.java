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

import org.sbml.jsbml.ListOf;

/**
 * @author
 */
public class ReactionGlyph extends GraphicalObject {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 8770691813350594995L;

	/**
	 * 
	 */
	private String reaction;

	/**
	 * 
	 */
	private ListOf<SpeciesReferencesGlyph> listOfSpeciesReferencesGlyph = new ListOf<SpeciesReferencesGlyph>();

	/**
	 * 
	 */
	private Curve curve;

	/**
	 * 
	 */
	public ReactionGlyph() {

	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public ReactionGlyph(int level, int version) {
		super(level, version);
	}

	/**
	 * 
	 * @param reactionGlyph
	 */
	public ReactionGlyph(ReactionGlyph reactionGlyph) {
		super(reactionGlyph);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#clone()
	 */
	@Override
	public ReactionGlyph clone() {
		return new ReactionGlyph(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return super.equals(o);
	}

	/**
	 * 
	 * @return
	 */
	public String getReaction() {
		return reaction;
	}

	/**
	 * 
	 * @param reaction
	 */
	public void setReaction(String reaction) {
		this.reaction = reaction;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractNamedSBase#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
}
