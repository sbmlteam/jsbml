/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
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

package org.sbml.jsbml;


/**
 * Represents the compartmentType XML element of a SBML file. It is deprecated
 * since level 3 and not defined in SBML before Level 2 Version 2.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 * @deprecated Only valid in SBML Level 2 for Versions 2 through 4.
 */
@Deprecated
public class CompartmentType extends AbstractNamedSBase implements
  UniqueNamedSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -2894837299868213399L;

	/**
	 * Creates a CompartmentType instance.
	 */
	@Deprecated
	public CompartmentType() {
		super();
	}

	/**
	 * Creates a CompartmentType instance from a given CompartmentType.
	 * 
	 * @param nsb
	 */
	@Deprecated
	public CompartmentType(CompartmentType nsb) {
		super(nsb);
	}

	/**
	 * Creates a CompartmentType instance from a level and version.
	 * 
	 * @param level
	 * @param version
	 */
	@Deprecated
	public CompartmentType(int level, int version) {
		super(level, version);
	}

	/**
	 * Creates a CompartmentType instance from an id.
	 * 
	 * @param id
	 * @deprecated
	 */
	@Deprecated
	public CompartmentType(String id) {
		super(id);
	}
	
	/**
	 * Creates a CompartmentType instance from an id, level and version.
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	@Deprecated
	public CompartmentType(String id, int level, int version) {
		super(id, level, version);
	}

	/**
	 * Creates a CompartmentType instance from an id, name, level and version.
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	@Deprecated
	public CompartmentType(String id, String name, int level, int version) {
		super(id, name, level, version);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.element.AbstractSBase#clone()
	 */
	@Deprecated
	public CompartmentType clone() {
		return new CompartmentType(this);
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getParent()
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Deprecated
	public ListOf<CompartmentType> getParent() {
		return (ListOf<CompartmentType>) super.getParent();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
	 */
	@Deprecated
	public boolean isIdMandatory() {
		return true;
	}

}
