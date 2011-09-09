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
package org.sbml.jsbml.ext.spatial;


/**
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class SpatialSymbolReference extends NamedSpatialElement implements
		SpatialParameterQualifier {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -8906622500258765056L;
	
	/**
	 * 
	 */
	public SpatialSymbolReference() {
		super();
	}

	/**
	 * @param level
	 * @param version
	 */
	public SpatialSymbolReference(int level, int version) {
		super(level, version);
	}

	/**
	 * @param sb
	 */
	public SpatialSymbolReference(SpatialSymbolReference sb) {
		super(sb);
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public SpatialSymbolReference clone() {
		return new SpatialSymbolReference(this);
	}

}
