/*
 * $Id:  SBOFilter.java 16:02:16 draeger $
 * $URL: SBOFilter.java $
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

import org.sbml.jsbml.SBase;

/**
 * A {@link Filter} that accepts only instances of {@link SBase} whose SBO term
 * field is set to a certain value of interest.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-05-26
 */
public class SBOFilter implements Filter {

	/**
	 * The SBO term of interest.
	 */
	private int term;

	/**
	 * Generates a new Filter for SBO terms but with an invalid SBO term as
	 * filter criterion.
	 */
	public SBOFilter() {
		this(-1);
	}

	/**
	 * Creates an SBO term filter with the given term as filter criterion.
	 * 
	 * @param term
	 *            The term of interest.
	 */
	public SBOFilter(int term) {
		this.term = term;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.util.Filter#accepts(java.lang.Object)
	 */
	public boolean accepts(Object o) {
		if (o instanceof SBase) {
			SBase sbase = (SBase) o;
			if (sbase.isSetSBOTerm() && (sbase.getSBOTerm() == term)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the term
	 */
	public int getTerm() {
		return term;
	}

	/**
	 * @param term
	 *            the term to set
	 */
	public void setTerm(int term) {
		this.term = term;
	}

}
