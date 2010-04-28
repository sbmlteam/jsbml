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

public class CubicBezier extends LineSegment {

	private Point basePoint1;
	private Point basePoint2;
	
	public CubicBezier() {
		
	}
	
	public CubicBezier(int level, int version) {
		super(level, version);
	}

	
	public Point getBasePoint1() {
		return basePoint1;
	}
	public void setBasePoint1(Point basePoint1) {
		this.basePoint1 = basePoint1;
	}
	public Point getBasePoint2() {
		return basePoint2;
	}
	public void setBasePoint2(Point basePoint2) {
		this.basePoint2 = basePoint2;
	}
	
	
}
