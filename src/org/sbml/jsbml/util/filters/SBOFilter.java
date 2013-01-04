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
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.util.filters;

import org.sbml.jsbml.SBO;
import org.sbml.jsbml.SBase;

/**
 * A {@link Filter} that accepts only instances of {@link SBase} whose SBO term
 * field is set to a certain value of interest.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-05-26
 * @since 0.8
 * @version $Rev$
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
			if (sbase.isSetSBOTerm() && (SBO.isChildOf(sbase.getSBOTerm(), term))) {
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
