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

import org.sbml.jsbml.Assignment;

/**
 * This filter only accepts instances of {@link Assignment} with the variable as
 * given in the constructor of this object.
 * 
 * @author rodrigue
 * @author Andreas Dr&auml;ger
 *  
 */
public class AssignmentVariableFilter implements Filter {

	/**
	 * The desired identifier for NamedSBases to be acceptable.
	 */
	String id;

	/**
	 * 
	 */
	public AssignmentVariableFilter() {
		this(null);
	}

	/**
	 * 
	 * @param id
	 */
	public AssignmentVariableFilter(String id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.util.Filter#fulfilsProperty(java.lang.Object)
	 */
	public boolean accepts(Object o) {
		if (o instanceof Assignment) {
			Assignment er = (Assignment) o;
			if (er.isSetVariable() && (id != null) && er.getVariable().equals(id)) {
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
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}
