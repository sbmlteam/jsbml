/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.libsbml;

/**
 * 
 * 
 * @author Nicolas Rodriguez
 *
 */
public class Species extends org.sbml.jsbml.Species {

    /**
     * Generated serial version identifier
     */
    private static final long serialVersionUID = -7877680831398332396L;

	/**
	 * 
	 */
	public Species() { // needed ??
		super();
	}

	/**
	 * @param level
	 * @param version
	 */
	public Species(int level, int version) {
		super(level, version);
	}

	/**
	 * @param species
	 */
	public Species(Species species) {
		super(species);
	}
	
	/**
	 * @param species
	 */
	public Species(org.sbml.jsbml.Species species) {
		super(species);
	}


	// TODO: missing constructor with namespaces
	
	public Species cloneObject() {
		return new Species(this);
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.Species#clone()
	 */
	@Override
	public Species clone() {
		return new Species(this);
	}
	
	public void delete() {
		
	}
	
}
