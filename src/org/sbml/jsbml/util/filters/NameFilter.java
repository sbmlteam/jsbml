/*
 * $Id: MathMLComnpiler.java 97 2009-12-10 09:08:54Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/util/MathMLComnpiler.java $
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

import org.sbml.jsbml.NamedSBase;

/**
 * This filter only accepts instances of {@link NamedSBase} with the name as
 * given in the constructor of this object.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-05-19
 * 
 */
public class NameFilter implements Filter {

	/**
	 * The desired identifier for NamedSBases to be acceptable.
	 */
	private String id;
	/**
	 * The desired name for NamedSBases to be acceptable.
	 */
	private String name;

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

	/*
	 * (non-Javadoc)
	 * 
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
