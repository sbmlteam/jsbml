/*
 * $Id: SimpleSpeciesReference.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/SimpleSpeciesReference.java $
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

package org.sbml.jsbml.element;

import java.util.HashMap;

/**
 * @author <a href="mailto:simon.schaefer@uni-tuebingen.de">Simon
 *         Sch&auml;fer</a>
 * 
 */
public abstract class SimpleSpeciesReference extends Symbol {

	/**
	 * 
	 */
	private Species species;
	
	/**
	 * 
	 */
	private String speciesID;


	/**
	 * 
	 */
	public SimpleSpeciesReference() {
		super();
		this.species = null;
		this.speciesID = null;
		
	}

	
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
	 * 
	 * @param ssr
	 */
	public SimpleSpeciesReference(Species s) {
		super(s);
		
		this.species = s;
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
		return speciesID;
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
	 * @return
	 */
	public boolean isSetSpeciesID() {
		return speciesID != null;
	}

	/**
	 * 
	 * @param spec
	 */
	public void setSpecies(Species spec) {
		this.species = spec;
		stateChanged();
	}
	
	/**
	 * 
	 * @param spec
	 */
	public void setSpeciesID(String spec) {
		this.speciesID = spec;
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
		if (getSpeciesInstance() != null){
			return getSpeciesInstance().toString();
		}
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		if (!isAttributeRead){
			if (attributeName.equals("species")){
				this.setSpeciesID(value);
			}
		}
		return isAttributeRead;
	}
	
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		
		if (isSetSpeciesID()){
			attributes.put("species", getSpecies());
		}
		
		return attributes;
	}
}
