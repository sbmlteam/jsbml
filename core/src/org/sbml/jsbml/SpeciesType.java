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

package org.sbml.jsbml;


/**
 * Represents the speciesType XML element of a SBML file. It is deprecated
 * since level 3 and not defined in SBML before Level 2 Version 2.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
 */
@Deprecated
public class SpeciesType extends AbstractNamedSBase implements UniqueNamedSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 7341517738480127866L;

	/**
	 * Creates a SpeciesType instance.
	 */
	@Deprecated
	public SpeciesType() {
		super();
	}

	/**
	 * Creates a SpeciesType instance from a level and version.
	 * 
	 * @param level
	 * @param version
	 */
	@Deprecated
	public SpeciesType(int level, int version) {
		super(level, version);
	}

	/**
	 * Creates a SpeciesType instance from a given SpeciesType.
	 * 
	 * @param nsb
	 */
	@Deprecated
	public SpeciesType(SpeciesType nsb) {
		super(nsb);
	}
	
	/**
	 * 
	 * @param id
	 * @deprecated
	 */
	@Deprecated
	public SpeciesType(String id) {
		super(id);
	}

	/**
	 * Creates a SpeciesType instance from an id, level and version.
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	@Deprecated
	public SpeciesType(String id, int level, int version) {
		super(id, level, version);
	}

	/**
	 * Creates a SpeciesType instance from an id, name, level and version.
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	@Deprecated
	public SpeciesType(String id, String name, int level, int version) {
		super(id, name, level, version);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Deprecated
	public SpeciesType clone() {
		return new SpeciesType(this);
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getParent()
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Deprecated
	public ListOf<SpeciesType> getParent() {
		return (ListOf<SpeciesType>) super.getParent();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
	 */
	@Deprecated
	public boolean isIdMandatory() {
		return true;
	}

}
