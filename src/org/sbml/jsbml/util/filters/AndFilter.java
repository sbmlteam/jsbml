/*
 * $Id:  AndFilter.java 15:49:32 draeger $
 * $URL: AndFilter.java $
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

import java.util.HashSet;
import java.util.Set;


/**
 * A {@link Filter} that accepts an item only if all of its internal filters
 * also accept the given item.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-05-26
 */
public class AndFilter implements Filter {

	/**
	 * A set of filters whose conditions have to be satisfied to accept an item.
	 */
	private Set<Filter> filters;

	/**
	 * 
	 */
	public AndFilter() {
		setFilters(new HashSet<Filter>());
	}

	/**
	 * 
	 * @param filters
	 */
	public AndFilter(Filter... filters) {
		this();
		for (Filter f : filters) {
			this.filters.add(f);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.util.Filter#accepts(java.lang.Object)
	 */
	public boolean accepts(Object o) {
		for (Filter f : filters) {
			if (!f.accepts(o)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param filter
	 * @return
	 */
	public boolean addFilter(Filter filter) {
		return filters.add(filter);
	}

	/**
	 * 
	 * @return
	 */
	public Set<Filter> getFilters() {
		return filters;
	}

	/**
	 * 
	 * @param filter
	 * @return
	 */
	public boolean removeFilter(Filter filter) {
		return filters.remove(filter);
	}

	/**
	 * 
	 * @param filters
	 */
	public void setFilters(Set<Filter> filters) {
		this.filters = filters;
	}

}
