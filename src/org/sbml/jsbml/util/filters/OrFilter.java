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


/**
 * This filter accepts an item if at least one of its filters accepts the given
 * item.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-05-26
 */
public class OrFilter extends AndFilter {

	/**
	 * 
	 */
	public OrFilter() {
		super();
	}

	/**
	 * @param filters
	 */
	public OrFilter(Filter... filters) {
		super(filters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.util.AndFilter#accepts(java.lang.Object)
	 */
	@Override
	public boolean accepts(Object o) {
		for (Filter f : getFilters()) {
			if (f.accepts(o)) {
				return true;
			}
		}
		return false;
	}
}
