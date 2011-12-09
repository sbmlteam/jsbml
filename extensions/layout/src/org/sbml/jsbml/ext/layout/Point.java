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

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @author Clemens Wrzodek
 * @since 1.0
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
		addNamespace(LayoutConstant.namespaceURI);
		x = y = z = Double.NaN;
	}



	/**
	 * 
	 * @param point
	 */
	public Point(Point point) {
		super(point);
		clonePointAttributes(point, this);
	}

	public Point(double x, double y, double z) {
		this();
		setX(x);
		setY(y);
		setZ(z);
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	public Point clone() {
		return new Point(this);
	}
	
	protected void clonePointAttributes(Point point, Point cloned) {

		if (point.isSetX()) {
			cloned.setX(point.getX());
		}
		if (point.isSetY()) {
			cloned.setY(point.getY());
		}
		if (point.isSetZ()) {
			cloned.setZ(point.getZ());
		}
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
		final int prime = 947;
		int hashCode = super.hashCode();
		hashCode += prime * Double.valueOf(x).hashCode();
		hashCode += prime * Double.valueOf(y).hashCode();
		hashCode += prime * Double.valueOf(z).hashCode();
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
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);

		if (!isAttributeRead) {
			
			isAttributeRead = true;
		
			if (attributeName.equals(LayoutConstant.x)) {
				setX(StringTools.parseSBMLDouble(value));
			}
			else if(attributeName.equals(LayoutConstant.y))
			{
				setY(StringTools.parseSBMLDouble(value));
			}
			else if(attributeName.equals(LayoutConstant.z))
			{
				setZ(StringTools.parseSBMLDouble(value));
			}
			else {
				return false;
			}
		}
		
		return isAttributeRead;
	}

	/**
	 * 
	 * @param x
	 */
	public void setX(double x) {
		Double oldX = this.x;
		this.x = x;
		firePropertyChange(LayoutConstant.x, oldX, this.x);
	}
	
	/**
	 * 
	 * @param y
	 */
	public void setY(double y) {
		Double oldY = this.y;
		this.y = y;
		firePropertyChange(LayoutConstant.y, oldY, this.y);
	}

  /**
	 * 
	 * @param z
	 */
	public void setZ(double z) {
		Double oldZ = this.z;
		this.z = z;
		firePropertyChange(LayoutConstant.z, oldZ, this.z);
	}



	@Override
	public String toString() {
		return "Point [" + x + ", " + y + ", " + z + "]";
	}

	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetId()) {
			attributes.remove("id");
			attributes.put(LayoutConstant.shortLabel + ":id", getId());
		}
		
		if (isSetX()) {
			attributes.put(LayoutConstant.shortLabel + ":"
					+ LayoutConstant.x, StringTools.toString(x));
		}
		if (isSetY()) {
			attributes.put(LayoutConstant.shortLabel + ":"
					+ LayoutConstant.y, StringTools.toString(y));
		}
		if (isSetZ()) {
			attributes.put(LayoutConstant.shortLabel + ":"
					+ LayoutConstant.z, StringTools.toString(z));
		}

		return attributes;
	}



	public boolean isIdMandatory() {
		return false;
	}


}
