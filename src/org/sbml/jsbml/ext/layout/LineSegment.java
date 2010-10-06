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

/**
 * @author
 */
public class LineSegment extends AbstractNamedSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -5085246314333062152L;
	/**
	 * 
	 */
	private Point start;
	/**
	 * 
	 */
	private Point end;

	/**
	 * 
	 */
	public LineSegment() {

	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public LineSegment(int level, int version) {
		super(level, version);
	}

	/**
	 * 
	 * @param lineSegment
	 */
	public LineSegment(LineSegment lineSegment) {
		super(lineSegment);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public LineSegment clone() {
		return new LineSegment(this);
	}

	/**
	 * 
	 * @return
	 */
	public Point getEnd() {
		return end;
	}

	/**
	 * 
	 * @return
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * 
	 * @param end
	 */
	public void setEnd(Point end) {
		this.end = end;
	}

	/**
	 * 
	 * @param start
	 */
	public void setStart(Point start) {
		this.start = start;
	}
}
