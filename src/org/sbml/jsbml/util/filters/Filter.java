/*
 * $Id: Filter.java 231 2010-05-13 09:19:09Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/util/Filter.java $
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
 * A filter is a general interface that allows to check an object for a certain
 * property. An implementing class may check for the type of the object and then
 * check some of its field values.
 * 
 * @author Andreas Dr&auml;ger
 * @data 2010-05-19
 */
public interface Filter {

	/**
	 * This method checks whether the given object is of the correct type and
	 * has the desired properties set to be acceptable.
	 * 
	 * @param o
	 *            some object whose properties are to be checked.
	 * @return True if the object is sufficient to be acceptable or false if at
	 *         least one of its properties or its class name does not fit into
	 *         this filter criterion.
	 */
	public boolean accepts(Object o);

}
