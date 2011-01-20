/*
 * $Id$
 * $URL$
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

import org.sbml.jsbml.CVTerm;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.CVTerm.Qualifier;

/**
 * This filter accepts only instances of {@link CVTerm} with a certain content
 * or instances of {@link SBase} that are annotated with appropriate
 * {@link CVTerm} objects.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-05-19
 * 
 */
public class CVTermFilter implements Filter {

	/**
	 * 
	 */
	private Qualifier qualifier;
	/**
	 * 
	 */
	private String pattern;

	/**
	 * 
	 */
	public CVTermFilter() {
		this(null, null);
	}

	/**
	 * 
	 * @param qualifier
	 */
	public CVTermFilter(Qualifier qualifier) {
		this(qualifier, null);
	}

	/**
	 * 
	 * @param qualifier
	 * @param pattern
	 */
	public CVTermFilter(Qualifier qualifier, String pattern) {
		this.qualifier = qualifier;
		this.pattern = pattern;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.util.Filter#accepts(java.lang.Object)
	 */
	public boolean accepts(Object o) {
		if (o instanceof CVTerm) {
			CVTerm cvt = (CVTerm) o;
			if (qualifier != null) {
				if (cvt.isBiologicalQualifier()
						&& (cvt.getBiologicalQualifierType() == qualifier)) {
					return pattern != null ? cvt.filterResources(pattern)
							.size() > 0 : true;
				} else if (cvt.isModelQualifier()
						&& cvt.getModelQualifierType() == qualifier) {
					return pattern != null ? cvt.filterResources(pattern)
							.size() > 0 : true;
				}
			} else if (pattern != null) {
				return cvt.filterResources(pattern).size() > 0;
			}
		} else if (o instanceof SBase) {
			SBase sbase = (SBase) o;
			if (qualifier != null) {
				if (pattern != null) {
					if (sbase.filterCVTerms(qualifier, pattern).size() > 0) {
						return true;
					}
				} else if (sbase.filterCVTerms(qualifier).size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * @return the qualifier
	 */
	public Qualifier getQualifier() {
		return qualifier;
	}

	/**
	 * @param pattern
	 *            the pattern to set
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * @param qualifier
	 *            the qualifier to set
	 */
	public void setQualifier(Qualifier qualifier) {
		this.qualifier = qualifier;
	}

}
