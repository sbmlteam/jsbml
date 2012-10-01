/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------
 * This file is part of the SysBio API library.
 *
 * Copyright (C) 2009-2012 by the University of Tuebingen, Germany.
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation. A copy of the license
 * agreement is provided in the file named "LICENSE.txt" included with
 * this software distribution and also available online as
 * <http://www.gnu.org/licenses/lgpl-3.0-standalone.html>.
 * ---------------------------------------------------------------------
 */

package de.zbit.sbml.layout;

/**
 * interface for the different types of entity pool nodes
 * - unspecified entity
 * - simple chemical
 * - macromolecule
 * - source/sink
 * 
 * @author Mirjam Gutekunst
 * @version $Rev: 136 $
 */
public interface SBGNNode {
	
	/**
	 * method for drawing an entity pool node with the specified position and
	 * size of a {@link BoundingBox}
	 * 
	 * @param x coordinate of the point of a BoundingBox
	 * @param y coordinate of the point of a BoundingBox
	 * @param z coordinate of the point of a BoundingBox
	 * @param width of a BoundingBox
	 * @param height of a BoundingBox
	 * @param depth of a BoundingBox
	 * @return String with the commands to draw the node
	 */
	public String draw(double x, double y, double z,
			double width, double height, double depth);
	
	/**
	 * method to set the boolean clone marker value to true if the clone marker
	 * value is true, the glyph of the species should be drawn with a clone
	 * marker
	 */
	public void setCloneMarker();
	
	/**
	 * @return boolean value of the clone marker variable
	 */
	public boolean isSetCloneMarker();
	
}
