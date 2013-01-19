/*
 * $Id: SBGNReactionNode.java 15:00:04 Meike Aichele$
 * $URL$
 * ---------------------------------------------------------------------
 * This file is part of the SysBio API library.
 *
 * Copyright (C) 2009-2013 by the University of Tuebingen, Germany.
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

import org.sbml.jsbml.ext.layout.LineSegment;
import org.sbml.jsbml.ext.layout.Point;

/**
 * @author Meike Aichele
 *
 */
public interface SBGNReactionNode<T> extends SBGNNode<T> {

	/**
	 * 
	 * @param line1
	 * @param rotationAngle
	 * @param rotationPoint
	 * @return
	 */
	public T drawLineSegment(LineSegment line1, double rotationAngle,
			Point rotationPoint);
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param width
	 * @param height
	 * @param depth
	 * @param rotationAngle
	 * @param rotationCenter
	 * @return
	 */
	public T draw(double x, double y, double z, double width, 
			double height, double depth, double rotationAngle, Point rotationCenter);

	
	/**
	 * @param pointOfContactToSubstrate the pointOfContactToSubstrate to set
	 */
	public void setPointOfContactToSubstrate(Point pointOfContactToSubstrate); 
	
	/**
	 * @return the pointOfContactToSubstrate
	 */
	public Point getPointOfContactToSubstrate();

	/**
	 * @param pointOfContactToProduct the pointOfContactToProduct to set
	 */
	public void setPointOfContactToProduct(Point pointOfContactToProduct);

	/**
	 * @return the pointOfContactToProduct
	 */
	public Point getPointOfContactToProduct();
	
	/**
	 * @return the lineWidth
	 */
	public abstract double getLineWidth();

	/**
	 * @param lineWidth the lineWidth to set
	 */
	public abstract void setLineWidth(double lineWidth);
}
