/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2011 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.ext.layout;

import org.sbml.jsbml.util.SBaseChangeEvent;

/**
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class SpeciesGlyph extends GraphicalObject {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 1077785483575936434L;

	/**
	 * 
	 */
	private String id;
	
	/**
	 * 
	 */
	private String species;
	
	/**
	 * 
	 */
	public SpeciesGlyph() {

	}
	
	/**
	 * 
	 * @param level
	 * @param version
	 */
	public SpeciesGlyph(int level, int version) {
		super(level, version);
	}

	
	/**
	 * 
	 * @param speciesGlyph
	 */
	public SpeciesGlyph(SpeciesGlyph speciesGlyph) {
		super(speciesGlyph);
		if (speciesGlyph.isSetId()) {
			this.id = new String(speciesGlyph.getId());
		}
		if (speciesGlyph.isSetSpecies()) {
			this.species = new String(speciesGlyph.getSpecies());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#clone()
	 */
	@Override
	public SpeciesGlyph clone() {
		return new SpeciesGlyph(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equals = super.equals(object);
		if (equals) {
			SpeciesGlyph s = (SpeciesGlyph) object;
			equals &= s.isSetId() == isSetId();
			if (equals && isSetId()) {
				equals &= s.getId().equals(getId());
			}
			// This can lead to a cyclic check!
			// equals &= s.isSetSpecies() == isSetSpecies();
			// if (equals && isSetSpecies()) {
			// equals &= s.getSpecies().equals(getSpecies());
			// }
		}
		return equals;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#getId()
	 */
	@Override
	public String getId(){
		return this.id;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSpecies() {
		return species;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 977;
		int hashCode = super.hashCode();
		if (isSetId()) {
			hashCode += prime * getId().hashCode();
		}
		return hashCode;
	}

	/**
	 * @return
	 */
	public boolean isSetSpecies() {
		return species != null;
	}

	/**
	 * 
	 * @param attributeName
	 * @param prefix
	 * @param value
	 * @return
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		//boolean isAttributeRead = super.readAttribute(attributeName, prefix,
		//		value);

		//if(!isAttributeRead)
		{
			//isAttributeRead = true;
			if (attributeName.equals("species")) {
				setSpecies(value);
			}
			else if(attributeName.equals("id"))
			{				
				this.id = value;				
			}
		}
			return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#setId(java.lang.String)
	 */
	@Override
	public void setId(String id){
		this.id = id;
	}
	
	/**
	 * 
	 * @param species
	 */
	public void setSpecies(String species) {
		String oldSpecies = this.species;
		this.species = species;
		firePropertyChange(SBaseChangeEvent.species, oldSpecies, this.species);
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
