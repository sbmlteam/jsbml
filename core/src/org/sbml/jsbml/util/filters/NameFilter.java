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

package org.sbml.jsbml.util.filters;

import org.sbml.jsbml.NamedSBase;

/**
 * This filter only accepts instances of {@link NamedSBase} with the name as
 * given in the constructor of this object.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-05-19
 * @since 0.8
 * @version $Rev$
 */
public class NameFilter implements Filter {

	/**
	 * The desired identifier for NamedSBases to be acceptable.
	 */
	String id;
	/**
	 * The desired name for NamedSBases to be acceptable.
	 */
	String name;

	/**
	 * 
	 */
	public NameFilter() {
		this(null, null);
	}

	/**
	 * 
	 * @param id
	 */
	public NameFilter(String id) {
		this(id, null);
	}

	/**
	 * 
	 * @param id
	 * @param name
	 */
	public NameFilter(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.util.Filter#fulfilsProperty(java.lang.Object)
	 */
	public boolean accepts(Object o) {
		if (o instanceof NamedSBase) {
			NamedSBase nsb = (NamedSBase) o;
			if (nsb.isSetId() && (id != null) && nsb.getId().equals(id)) {
				return true;
			}
			if (nsb.isSetName() && (name != null) && nsb.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
