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
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class Dimensions extends AbstractNamedSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -6114634391235520057L;
	/**
	 * 
	 */
	private double depth;
	/**
	 * 
	 */
	private double height;
	/**
	 * 
	 */
	private double width;

	/**
	 * 
	 */
	public Dimensions() {
	}
	
	/**
	 * 
	 * @param dimensions
	 */
	public Dimensions(Dimensions dimensions) {
		super(dimensions);
		if (dimensions.isSetDepth()) {
			this.depth = dimensions.getDepth();
		}
		if (dimensions.isSetHeight()) {
			this.height = dimensions.getHeight();
		}
		if (dimensions.isSetWidth()) {
			this.width = dimensions.getWidth();
		}
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public Dimensions(int level, int version) {
		super(level, version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public Dimensions clone() {
		return new Dimensions(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equals = super.equals(object);
		if (equals) {
			Dimensions d = (Dimensions) object;
			equals &= d.isSetDepth() == isSetDepth();
			if (equals && isSetDepth()) {
				equals &= Double.valueOf(d.getDepth()).equals(Double.valueOf(getDepth()));
			}
			equals &= d.isSetHeight() == isSetHeight();
			if (equals && isSetHeight()) {
				equals &= Double.valueOf(d.getHeight()).equals(Double.valueOf(getHeight()));
			}
			equals &= d.isSetWidth() == isSetWidth();
			if (equals && isSetWidth()) {
				equals &=  Double.valueOf(d.getWidth()).equals(Double.valueOf(getWidth()));
			}
		}
		return equals;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getDepth() {
		return depth;
	}

	/**
	 * 
	 * @return
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * 
	 * @return
	 */
	public double getWidth() {
		return width;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 941;
		int hashCode = super.hashCode();
		hashCode += prime * Double.valueOf(depth).hashCode();
		hashCode += prime * Double.valueOf(height).hashCode();
		hashCode += prime * Double.valueOf(width).hashCode();
		return hashCode;
	}

	/**
	 * @return
	 */
	public boolean isSetDepth() {
		return !Double.isNaN(depth);
	}

	/**
	 * @return
	 */
	public boolean isSetHeight() {
		return !Double.isNaN(height);
	}

	/**
	 * @return
	 */
	public boolean isSetWidth() {
		return !Double.isNaN(width);
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

		//if(!isAttributeRead)
		{
		
			//isAttributeRead = true;			
			if(attributeName.equals("width"))
			{	
				try{
					this.width = Integer.parseInt(value);
					
				}catch(Exception e){e.printStackTrace();}
			}
			if(attributeName.equals("height"))
			{	
				try{
					this.height = Integer.parseInt(value);
					
				}catch(Exception e){e.printStackTrace();}
			}
		
			return true;
		}
		//return isAttributeRead;
	}

	/**
	 * 
	 * @param depth
	 */
	public void setDepth(double depth) {
		Double oldDepth = this.depth;
		this.depth = depth;
		firePropertyChange(TreeNodeChangeEvent.depth, oldDepth, this.depth);
	}

	/**
	 * 
	 * @param height
	 */
	public void setHeight(double height) {
		Double oldHeight = this.height;
		this.height = height;
		firePropertyChange(TreeNodeChangeEvent.height, oldHeight, this.height);
	}
	
	/**
	 * 
	 * @param width
	 */
	public void setWidth(double width) {
		Double oldWidth = this.width;
		this.width = width;
		firePropertyChange(TreeNodeChangeEvent.width, oldWidth, this.width);
	}

}
