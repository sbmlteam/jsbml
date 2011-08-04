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
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
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
		super();
		x = y = z = Double.NaN;
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
		if (point.isSetX()) {
			this.x = point.getX();
		}
		if (point.isSetY()) {
			this.y = point.getY();
		}
		if (point.isSetZ()) {
			this.z = point.getZ();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	public Point clone() {
		return new Point(this);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equals = super.equals(object);
		if (equals) {
			Point p = (Point) object;
			equals &= p.isSetX() == isSetX();
			if (equals && isSetX()) {
				equals &= Double.valueOf(p.getX()).equals(Double.valueOf(getX()));
			}
			equals &= p.isSetY() == isSetY();
			if (equals && isSetY()) {
				equals &= Double.valueOf(p.getY()).equals(Double.valueOf(getY()));
			}
			equals &= p.isSetZ() == isSetZ();
			if (equals && isSetZ()) {
				equals &= Double.valueOf(p.getZ()).equals(Double.valueOf(getZ()));
			}
		}
		return equals;
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

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 7;
		int hashCode = super.hashCode();
		hashCode += prime * Double.valueOf(getX()).hashCode();
		hashCode += prime * Double.valueOf(getY()).hashCode();
		hashCode += prime * Double.valueOf(getZ()).hashCode();
		return hashCode;
	}

	/**
	 * @return
	 */
	public boolean isSetX() {
		return !Double.isNaN(x);
	}

	/**
	 * @return
	 */
	public boolean isSetY() {
		return !Double.isNaN(y);
	}

	/**
	 * @return
	 */
	public boolean isSetZ() {
		return !Double.isNaN(z);
	}

	/**
	* 
	 * @param attributeName
	 * @param prefix
	 * @param value
	 * @return
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		//boolean isAttributeRead = super.readAttribute(attributeName, prefix,
		//		value);

		boolean isAttributeRead = true;
			if (attributeName.equals("x")) {
				try{
					setX(Double.valueOf(value));
				}
				catch(NumberFormatException e)
				{
					System.err.println("todo by somebody: ");
					e.printStackTrace();
				}
			}
			else if(attributeName.equals("y"))
			{
				try{
					setY(Double.valueOf(value));
				}
				catch(NumberFormatException e)
				{
					System.err.println("todo by somebody: ");
					e.printStackTrace();
				}
			}
			else if(attributeName.equals("z"))
			{
				try{
					setZ(Double.valueOf(value));
				}
				catch(NumberFormatException e)
				{
					System.err.println("todo by somebody: ");
					e.printStackTrace();
				}
			}
			return isAttributeRead;
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
