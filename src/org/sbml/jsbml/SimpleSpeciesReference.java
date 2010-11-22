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

import java.util.Map;

/**
 * The base class of {@link SpeciesReference} and
 * {@link ModifierSpeciesReference}.
 * 
 * @author Simon Sch&auml;fer
 * @author marine
 * @author Andreas Dr&auml;ger
 */
public abstract class SimpleSpeciesReference extends AbstractNamedSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -504780573593345060L;
	/**
	 * Represents the 'species' XML attribute.
	 */
	private String speciesID;

	/**
	 * Creates a SimpleSpeciesReference instance. By default, the speciesId is
	 * null.
	 */
	public SimpleSpeciesReference() {
		super();
		this.speciesID = null;
	}
	
	/**
	 * 
	 * @param id
	 */
	public SimpleSpeciesReference(String id) {
		super(id);
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public SimpleSpeciesReference(int level, int version) {
		this(null, level, version);
	}

	/**
	 * Creates a SimpleSpeciesReference instance from a given
	 * SimpleSpeciesReference.
	 * 
	 * @param ssr
	 */
	public SimpleSpeciesReference(SimpleSpeciesReference ssr) {
		super(ssr);
		this.speciesID = ssr.isSetSpecies() ? new String(ssr.getSpecies())
				: null;
	}

	/**
	 * Creates a SimpleSpeciesReference instance from a given Species.
	 * 
	 * @param ssr
	 */
	public SimpleSpeciesReference(Species s) {
		this(s.getLevel(), s.getVersion());
		this.speciesID = s.isSetId() ? new String(s.getId()) : null;
	}

	/**
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public SimpleSpeciesReference(String id, int level, int version) {
		this(id, null, level, version);
	}

	/**
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public SimpleSpeciesReference(String id, String name, int level, int version) {
		super(id, name, level, version);
		this.speciesID = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof SimpleSpeciesReference) {
			SimpleSpeciesReference ssr = (SimpleSpeciesReference) o;
			boolean equal = super.equals(o);
			if ((!isSetSpecies() && ssr.isSetSpecies())
					|| (isSetSpecies() && !ssr.isSetSpecies())) {
				return false;

			} else if (isSetSpecies() && ssr.isSetSpecies()) {
				equal &= ssr.getSpecies().equals(speciesID);
				// System.out.println("SimplespeciesReference : speciesRef =  "
				// + ssr.getSpecies() + ", " + speciesID);
			}
			return equal;
		}
		return false;
	}

	/**
	 * 
	 * @return the speciesID of this {@link Species}. The empty String if it is not set.
	 */
	public String getSpecies() {
		return isSetSpecies() ? speciesID : "";
	}

	/**
	 * 
	 * @return The Species instance which has the speciesID of this
	 *         SimpleSpeciesReference as id. Can be null if it doesn't exist.
	 */
	public Species getSpeciesInstance() {
		Model m = getModel();
		return m != null ? m.getSpecies(this.speciesID) : null;
	}

	/**
	 * 
	 * @return true if ths speciesID of this SimpleSpeciesReference is not null.
	 */
	public boolean isSetSpecies() {
		return speciesID != null;
	}

	/**
	 * 
	 * @return true if the Species instance which has the speciesID of this
	 *         SimpleSpeciesReference as id is not null.
	 */
	public boolean isSetSpeciesInstance() {
		Model m = getModel();
		return m != null ? m.getSpecies(this.speciesID) != null : false;
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

		if (!isAttributeRead) {
			if (attributeName.equals("species")
					&& (((getLevel() == 1) && (getVersion() == 2)) || (getLevel() > 1))) {
				setSpecies(value);
				return true;
			} else if (attributeName.equals("specie") && (getLevel() == 1)
					&& (getVersion() == 1)) {
				setSpecies(value);
				return true;
			}
		}
		return isAttributeRead;
	}

	/**
	 * Sets the speciesID to the id of the {@link Species} 'species'.
	 * 
	 * @param species
	 */
	public void setSpecies(Species species) {
		setSpecies((species != null) && (species.isSetId()) ? species.getId() : null);
	}

	/**
	 * Sets the speciesID to 'spec'.
	 * 
	 * @param species
	 */
	public void setSpecies(String species) {
		if (checkIdentifier(species)) {
			String oldSpecies = this.speciesID;
			speciesID = species;
			firePropertyChange(SBaseChangedEvent.species, oldSpecies, speciesID);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#toString()
	 */
	@Override
	public String toString() {
		if (isSetName() && getName().length() > 0)
			return getName();
		if (isSetId())
			return getId();
		if (isSetSpeciesInstance()) {
			return getSpeciesInstance().toString();
		}
		return super.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();
		if (isSetSpecies()) {
			if (((getLevel() == 1) && (getVersion() == 2)) || (getLevel() > 1)) {
				attributes.put("species", getSpecies());
			} else if ((getLevel() == 1) && (getVersion() == 1)) {
				attributes.put("specie", getSpecies());
			}
		}
		return attributes;
	}
}
