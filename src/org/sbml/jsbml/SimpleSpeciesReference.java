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
 * @author <a href="mailto:simon.schaefer@uni-tuebingen.de">Simon
 *         Sch&auml;fer</a>
 * 
 */
public abstract class SimpleSpeciesReference extends AbstractNamedSBase {

	/**
	 * 
	 */
	private Species species;

	/**
	 * 
	 * @param ssr
	 */
	public SimpleSpeciesReference(SimpleSpeciesReference ssr) {
		super(ssr);
		if (ssr.isSetSpecies())
			this.species = ssr.getSpeciesInstance();
		else
			this.species = null;
	}

	/**
	 * Takes level and version from the species.
	 * 
	 * @param spec
	 */
	public SimpleSpeciesReference(Species spec) {
		super(spec.getLevel(), spec.getVersion());
		this.species = spec;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.NamedSBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		boolean equal = super.equals(o);
		if (o.getClass().getName().equals(getClass().getName())) {
			SimpleSpeciesReference ssr = (SimpleSpeciesReference) o;
			if ((!isSetSpecies() && ssr.isSetSpecies())
					|| (isSetSpecies() && !ssr.isSetSpecies()))
				return false;
			else if (isSetSpecies() && ssr.isSetSpecies())
				equal &= ssr.getSpeciesInstance().equals(species);
			return equal;
		} else
			equal = false;
		return equal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#getParentSBMLObject()
	 */
	// @Override
	public Reaction getParentSBMLObject() {
		return (Reaction) super.getParentSBMLObject();
	}

	/**
	 * 
	 * @return
	 */
	public String getSpecies() {
		return species.getId();
	}

	/**
	 * 
	 * @return
	 */
	public Species getSpeciesInstance() {
		return species;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetSpecies() {
		return species != null;
	}

	/**
	 * 
	 * @param spec
	 */
	public void setSpecies(Species spec) {
		this.species = spec;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.NamedSBase#toString()
	 */
	public String toString() {
		if (isSetName() && getName().length() > 0)
			return getName();
		if (isSetId())
			return getId();
		return getSpeciesInstance().toString();
	}
}
