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
package org.sbml.jsbml.util.filters;

import org.sbml.jsbml.SimpleSpeciesReference;
import org.sbml.jsbml.Species;

/**
 * This is a special {@link NameFilter} that allows to search for a
 * {@link SimpleSpeciesReference} that refers to a {@link Species} with the
 * given identifier attribute. The boolean switch {@link #filterForSpecies} that
 * can be changed using the {@link #setFilterForSpecies(boolean)} method decides
 * whether this {@link SpeciesReferenceFilter} should use the given identifier
 * to filter for the actual {@link SimpleSpeciesReference} or for the referenced
 * {@link Species}.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-11-10
 */
public class SpeciesReferenceFilter extends NameFilter {

	/**
	 * Decides whether to filter for the identifier of the referenced
	 * {@link Species} or if to use id and name to filter for the instance of
	 * {@link SimpleSpeciesReference} itself.
	 */
	private boolean filterForSpecies = false;

	/**
	 * 
	 */
	public SpeciesReferenceFilter() {
		super();
	}

	/**
	 * 
	 * @param id
	 */
	public SpeciesReferenceFilter(String id) {
		super(id);
	}

	/**
	 * 
	 * @param id
	 * @param name
	 */
	public SpeciesReferenceFilter(String id, String name) {
		super(id, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.util.filters.Filter#accepts(java.lang.Object)
	 */
	@Override
	public boolean accepts(Object o) {
		if (!filterForSpecies) {
			return super.accepts(o);
		}
		if (o instanceof SimpleSpeciesReference) {
			SimpleSpeciesReference specRef = (SimpleSpeciesReference) o;
			if (specRef.isSetSpecies() && (id != null)
					&& specRef.getSpecies().equals(id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the filterForSpecies
	 */
	public boolean isFilterForSpecies() {
		return filterForSpecies;
	}

	/**
	 * @param filterForSpecies
	 *            the filterForSpecies to set
	 */
	public void setFilterForSpecies(boolean filterForSpecies) {
		this.filterForSpecies = filterForSpecies;
	}

}
