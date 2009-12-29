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

package org.sbml.jsbml;

/**
 * Base class for all the SBML components with an id and a name (optional or
 * not).
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public interface NamedSBase extends SBase {

	/**
	 * 
	 * @return the id of the element if it is set, an empty string otherwise.
	 */
	public String getId();

	/**
	 * 
	 * @return the name of the element if it is set, an empty string otherwise.
	 */
	public String getName();

	/**
	 * 
	 * @return true if the id is not null.
	 */
	public boolean isSetId();

	/**
	 * 
	 * @return true if the name is not null.
	 */
	public boolean isSetName();

	/**
	 * sets the id value with 'id'
	 * 
	 * @param id
	 */
	public void setId(String id);

	/**
	 * sets the name value with 'name'. If level is 1, sets automatically the id
	 * to 'name'
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * sets the name value to null.
	 */
	public void unsetName();

	/**
	 * sets the id value to null.
	 */
	public void unsetId();
}
