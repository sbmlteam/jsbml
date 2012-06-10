/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
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

import java.util.Map;

import org.sbml.jsbml.Species;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @author Clemens Wrzodek
 * @since 1.0
 * @version $Rev$
 */
public class SpeciesGlyph extends NamedSBaseGlyph {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 1077785483575936434L;
	
	/**
	 * 
	 */
	public SpeciesGlyph() {
		super();
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
	}
	
	/**
	 * 
	 * @param id
	 */
	public SpeciesGlyph(String id) {
		super(id);
	}
	
	/**
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public SpeciesGlyph(String id, int level, int version) {
		super(id, level, version);
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.NamedSBaseGlyph#clone()
	 */
	public SpeciesGlyph clone() {
		return new SpeciesGlyph(this);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.NamedSBaseGlyph#getNamedSBaseInstance()
	 */
	@Override
	public Species getNamedSBaseInstance() {
		return (Species) super.getNamedSBaseInstance();
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSpecies() {
		return getNamedSBase();
	}

	/**
	 * @return the {@link #speciesId}
	 */
	public boolean isSetSpecies() {
		return isSetNamedSBase();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);

		if(!isAttributeRead)
		{
			if (attributeName.equals("species")) {
				setSpecies(value);
			}
			else
			{				
				return false;
			}
		}
			return true;
	}

	/**
	 * 
	 * @param species
	 */
	public void setSpecies(Species species) {
		setSpecies(species.getId());
	}
	
	/**
	 * 
	 * @param species
	 */
	public void setSpecies(String species) {
		setNamedSBase(species, TreeNodeChangeEvent.species);
	}
	
	/**
	 * 
	 */
	public void unsetSpecies() {
		unsetNamedSBase();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
	  Map<String, String> attributes = super.writeXMLAttributes();
	  
	  if (isSetId()) {
	    attributes.remove("id");
	    attributes.put(LayoutConstants.shortLabel + ":id", getId());
	  }
	  if (isSetSpecies()) {
	    attributes.put(LayoutConstants.shortLabel + ":species", getSpecies());
	  } 
	  
	  return attributes;
	}

}
