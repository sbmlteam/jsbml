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

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * 
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class BoundingBox extends AbstractNamedSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -6371039558611201798L;
	
	// TODO : may be use directly java objects ??! See if we need metaid, notes,
	// annotation for those.

	/**
	 * 
	 */
	private Dimensions dimensions;
	
	// TODO : may be use directly java objects ??! See if we need metaid, notes,
	// annotation for those.
	/**
	 * 
	 * id of BoundingBox-Object
	 */
	private String id;
	/**
	 * 
	 */
	private Point point;

	/**
	 * 
	 */
	public BoundingBox() {
	}

	/**
	 * 
	 * @param boundingBox
	 */
	public BoundingBox(BoundingBox boundingBox) {
		super(boundingBox);
		if (boundingBox.isSetDimensions()) {
			this.dimensions = boundingBox.getDimensions().clone();
		}
		if (boundingBox.isSetId()) {
			this.id = new String(boundingBox.getId());
		}
		if (boundingBox.isSetPoint()) {
			this.point = boundingBox.getPoint().clone();
		}
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public BoundingBox(int level, int version) {
		super(level, version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public BoundingBox clone() {
		return new BoundingBox(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// Check all child elements recursively in super class first:
		boolean equals = super.equals(object);
		if (equals) {
			// Cast is possible because super class checks the class attributes
			BoundingBox bb = (BoundingBox) object;
			equals &= bb.getId().equals(getId());
		}
		return equals;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		int count = super.getChildCount(), pos = 0;
		if (index < count) {
			return super.getChildAt(index);
		} else {
			index -= count;
		}
		if (isSetPoint()) {
			if (pos == index) {
				return getPoint();
			}
			pos++;
		}
		if (isSetDimensions()) {
			if (pos == index) {
				return getDimensions();
			}
			pos++;
		}
		throw new IndexOutOfBoundsException(String.format("Index %d >= %d",
				index, +((int) Math.min(pos, 0))));
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		int count = super.getChildCount();
		if (isSetPoint()) {
			count++;
		}
		if (isSetDimensions()) {
			count++;
		}
		return count;
	}

	/**
	 * 
	 * @return
	 */
	public Dimensions getDimensions() {
		return dimensions;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#getId()
	 */
	@Override
	public String getId()
	{
		return id;
	}
	
	/**
	 * 
	 * @return
	 */
	public Point getPoint() {
		return point;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 7;
		int hashCode = super.hashCode();
		hashCode += prime * getId().hashCode();
		return hashCode;
	}
	
	/* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  public boolean isIdMandatory() {
    // TODO Auto-generated method stub
    return false;
  }
	
	/**
	 * @return
	 */
	public boolean isSetDimensions() {
		return dimensions != null;
	}

	/**
	 * @return
	 */
	public boolean isSetPoint() {
		return point != null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = false; // = super.readAttribute(attributeName, prefix,
		//		value);		
		if (!isAttributeRead) {
			isAttributeRead = true;
			if (attributeName.equals("id")) {
				this.setId(value);
				
			}
		}
		return isAttributeRead;
	}
	
	/**
	 * 
	 * @param dimensions
	 */
	public void setDimensions(Dimensions dimensions) {
		if(this.dimensions != null){
			Dimensions oldValue = this.dimensions;
			this.dimensions = null;
			oldValue.fireNodeRemovedEvent();
		}
		this.dimensions = dimensions;
		registerChild(this.dimensions);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#setId(java.lang.String)
	 */
	@Override
	public void setId(String id)
	{
		String oldId = this.id;
		this.id = id;
		firePropertyChange(TreeNodeChangeEvent.id, oldId, this.id);
	}

  /**
	 * 
	 * @param point
	 */
	public void setPoint(Point point) {
		Point oldValue = this.point;
		this.point = point;
		if(oldValue != null){
			oldValue.fireNodeRemovedEvent();
		}
		if(this.point != null){
			this.point.fireNodeAddedEvent();
		}
	}

}
