/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2011 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.ext.layout;

import org.sbml.jsbml.AbstractNamedSBase;

/**
 * @author
 * @since 0.8
 * @version $Rev$
 */
public class Point extends AbstractNamedSBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7464572763198848890L;
	/**
	 * 
	 */
	private double x;
	/**
	 * 
	 */
	private double y;
	/**
	 * 
	 */
	private double z;

	/**
	 * 
	 */
	public Point() {

	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public Point(int level, int version) {
		super(level, version);
	}

	/**
	 * 
	 * @param point
	 */
	public Point(Point point) {
		super(point);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	public Point clone() {
		return new Point(this);
	}

	/**
	 * 
	 * @return
	 */
	public double getX() {
		return x;
	}

	/**
	 * 
	 * @return
	 */
	public double getY() {
		return y;
	}

	/**
	 * 
	 * @return
	 */
	public double getZ() {
		return z;
	}

	/**
	 * 
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * 
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * 
	 * @param z
	 */
	public void setZ(double z) {
		this.z = z;
	}
}
