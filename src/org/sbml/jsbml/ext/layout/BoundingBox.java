/*
 * $Id$
 * $URL$
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
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

package org.sbml.jsbml.ext.layout;

import org.sbml.jsbml.AbstractNamedSBase;

public class BoundingBox extends AbstractNamedSBase {

	// TODO : may be use directly java objects ??! See if we need metaid, notes,
	// annotation for those.

	/**
	 * 
	 */
	private Point point;
	/**
	 * 
	 */
	private Dimensions dimensions;

	/**
	 * 
	 */
	public BoundingBox() {
	}

	/**
	 * 
	 * @param boundingBox
	 */
	public BoundingBox(BoundingBox boundingBox) {
		super(boundingBox);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public BoundingBox(int level, int version) {
		super(level, version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public BoundingBox clone() {
		return new BoundingBox(this);
	}

	/**
	 * 
	 * @return
	 */
	public Dimensions getDimensions() {
		return dimensions;
	}

	/**
	 * 
	 * @return
	 */
	public Point getPoint() {
		return point;
	}

	/**
	 * 
	 * @param dimensions
	 */
	public void setDimensions(Dimensions dimensions) {
		this.dimensions = dimensions;
	}

	/**
	 * 
	 * @param point
	 */
	public void setPoint(Point point) {
		this.point = point;
	}
}
