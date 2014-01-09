/*
 * $Id$
 * $URL$
 * ---------------------------------------------------------------------
 * This file is part of the SysBio API library.
 *
 * Copyright (C) 2009-2014 by the University of Tuebingen, Germany.
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
 * @author Jakob Matthes
 * @version $Rev$
 */
public abstract class ProcessNode<T> implements SBGNReactionNode<T> {
	
	// process nodes do not have a clone marker
	private final boolean cloneMarker = false;
	
	/**
	 *  Position where the curve of the substrate ends at the process node
	 */
	private Point pointOfContactToSubstrate;
	
	/**
	 * Position where the curve to the product begins at the process node
	 */
	private Point pointOfContactToProduct;
	
	/* (non-Javadoc)
	 * @see de.zbit.sbml.layout.SBGNNode#setCloneMarker()
	 */
	public void setCloneMarker() {
		// do nothing because process nodes have no clone marker
	}
	
	/* (non-Javadoc)
	 * @see de.zbit.sbml.layout.SBGNNode#isSetCloneMarker()
	 */
	public boolean isSetCloneMarker() {
		return cloneMarker;
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.zbit.sbml.layout.SBGNReactionNode#drawLineSegment(org.sbml.jsbml.ext.layout.LineSegment, double, org.sbml.jsbml.ext.layout.Point)
	 */
	public abstract T drawLineSegment(LineSegment lineSegment, double rotationAngle, Point rotationCenter);
	
	/*
	 * (non-Javadoc)
	 * @see de.zbit.sbml.layout.SBGNReactionNode#draw(double, double, double, double, double, double, double, org.sbml.jsbml.ext.layout.Point)
	 */
	public abstract T draw(double x, double y, double z, double width, 
			double height, double depth, double rotationAngle, Point rotationCenter);

	/*
	 * (non-Javadoc)
	 * @see de.zbit.sbml.layout.SBGNReactionNode#setPointOfContactToSubstrate(org.sbml.jsbml.ext.layout.Point)
	 */
	public void setPointOfContactToSubstrate(Point pointOfContactToSubstrate) {
		this.pointOfContactToSubstrate = pointOfContactToSubstrate;
	}

	/*
	 * (non-Javadoc)
	 * @see de.zbit.sbml.layout.SBGNReactionNode#getPointOfContactToSubstrate()
	 */
	public Point getPointOfContactToSubstrate() {
		return pointOfContactToSubstrate;
	}

	/*
	 * (non-Javadoc)
	 * @see de.zbit.sbml.layout.SBGNReactionNode#setPointOfContactToProduct(org.sbml.jsbml.ext.layout.Point)
	 */
	public void setPointOfContactToProduct(Point pointOfContactToProduct) {
		this.pointOfContactToProduct = pointOfContactToProduct;
	}

	/*
	 * (non-Javadoc)
	 * @see de.zbit.sbml.layout.SBGNReactionNode#getPointOfContactToProduct()
	 */
	public Point getPointOfContactToProduct() {
		return pointOfContactToProduct;
	}

	/*
	 * (non-Javadoc)
	 * @see de.zbit.sbml.layout.SBGNReactionNode#getLineWidth()
	 */
	public abstract double getLineWidth();

	/*
	 * (non-Javadoc)
	 * @see de.zbit.sbml.layout.SBGNReactionNode#setLineWidth(double)
	 */
	public abstract void setLineWidth(double lineWidth);
	
}
