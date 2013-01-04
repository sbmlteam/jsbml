/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
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

import java.util.Locale;
import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 1.0
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
	  addNamespace(LayoutConstants.namespaceURI);
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
   * @param width
   * @param height
   * @param depth
   * @param level
   * @param version
   */
  public Dimensions(double width, double height, double depth, int level, int version) {
    this(level, version);
    this.width = width;
    this.height = height;
    this.depth = depth;
  }
  
  /**
	 * 
	 * @param level
	 * @param version
	 */
  public Dimensions(int level, int version) {
	  super(level, version);
	  addNamespace(LayoutConstants.namespaceURI);
	}
  
  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public Dimensions(String id, int level, int version) {
    super(id, level, version);
    addNamespace(LayoutConstants.namespaceURI);
  }
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public Dimensions clone() {
		return new Dimensions(this);
	}


	/* (non-Javadoc)
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

	/* (non-Javadoc)
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

	/* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  public boolean isIdMandatory() {
    return false;
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

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);

		if(!isAttributeRead)
		{
		
			//isAttributeRead = true;			
			if(attributeName.equals(LayoutConstants.width))
			{	
				setWidth(StringTools.parseSBMLDouble(value));
			}
			else if(attributeName.equals(LayoutConstants.height))
			{	
				setHeight(StringTools.parseSBMLDouble(value));
			}
			else if(attributeName.equals(LayoutConstants.depth))
			{	
				setDepth(StringTools.parseSBMLDouble(value));
			} 
			else
			{
				return false;
			}
		
			return true;
		}
		
		return isAttributeRead;
	}

	/**
	 * 
	 * @param depth
	 */
	public void setDepth(double depth) {
		Double oldDepth = this.depth;
		this.depth = depth;
		firePropertyChange(LayoutConstants.depth, oldDepth, this.depth);
	}
	
	/**
	 * 
	 * @param height
	 */
	public void setHeight(double height) {
		Double oldHeight = this.height;
		this.height = height;
		firePropertyChange(LayoutConstants.height, oldHeight, this.height);
	}

  /**
	 * 
	 * @param width
	 */
	public void setWidth(double width) {
		Double oldWidth = this.width;
		this.width = width;
		firePropertyChange(LayoutConstants.width, oldWidth, this.width);
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetId()) {
			attributes.remove("id");
			attributes.put(LayoutConstants.shortLabel + ":id", getId());
		}
		
		if (isSetDepth()) {
			attributes.put(LayoutConstants.shortLabel + ':'
					+ LayoutConstants.depth, StringTools.toString(Locale.ENGLISH, depth));
		}
		if (isSetHeight()) {
			attributes.put(LayoutConstants.shortLabel + ':'
					+ LayoutConstants.height, StringTools.toString(Locale.ENGLISH, height));
		}
		if (isSetWidth()) {
			attributes.put(LayoutConstants.shortLabel + ':'
					+ LayoutConstants.width, StringTools.toString(Locale.ENGLISH, width));
		}

		return attributes;
	}

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return super.toString() + " [width=" + width + ", height=" + height + ", depth=" + depth + ']';
  }

}
