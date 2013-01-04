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

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * 
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @author Clemens Wrzodek
 * @since 1.0
 * @version $Rev$
 */
public class GraphicalObject extends AbstractNamedSBase implements UniqueNamedSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 7587814013754302901L;
	/**
	 * 
	 */
	private BoundingBox boundingBox;

	/**
	 * 
	 */
	public GraphicalObject() {
		super();
		addNamespace(LayoutConstants.namespaceURI);
	}

	/**
	 * 
	 * @param graphicalObject
	 */
	public GraphicalObject(GraphicalObject graphicalObject) {
		super(graphicalObject);
		setBoundingBox(graphicalObject.getBoundingBox());
	}
	
	/**
	 * 
	 * @param level
	 * @param version
	 */
	public GraphicalObject(int level, int version) {
		super(level, version);
		addNamespace(LayoutConstants.namespaceURI);
	}

  /**
	 * @param id
	 */
	public GraphicalObject(String id) {
		super(id);
		addNamespace(LayoutConstants.namespaceURI);
	}


	/**
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public GraphicalObject(String id, int level, int version) {
		super(id, level, version);
		addNamespace(LayoutConstants.namespaceURI);
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public GraphicalObject clone() {
		return new GraphicalObject(this);
	}
	
	/**
	 * Creates and sets a {@link BoundingBox} for this object.
	 * @return {@link BoundingBox}.
	 */
	public BoundingBox createBoundingBox() {
	  BoundingBox bb = new BoundingBox();
	  setBoundingBox(bb);
	  return bb;
	}

	/**
	 * 
	 * @param dimensions
	 * @return
	 */
	public BoundingBox createBoundingBox(Dimensions dimensions) {
	  BoundingBox bb = createBoundingBox();
	  bb.setDimensions(dimensions);
	  return bb;
	}

	/**
	 * Creates and sets a {@link BoundingBox} for this object, with the
	 * given parameters for {@link Dimensions}.
	 * @param width
	 * @param height
	 * @param depth
	 * @return {@link BoundingBox}.
	 */
	public BoundingBox createBoundingBox(double width, double height, double depth) {
		BoundingBox bb = createBoundingBox();
		bb.createDimensions(width, height, depth);
		return bb;
	}
		
	/**
	 * Creates and sets a {@link BoundingBox} for this object, with the
	 * given parameters for {@link Dimensions} and {@link Point}.
	 * @param width
	 * @param height
	 * @param depth
	 * @param x
	 * @param y
	 * @param z
	 * @return {@link BoundingBox}.
	 */
	public BoundingBox createBoundingBox(double width, double height, double depth,
	  double x, double y, double z) {
	  BoundingBox bb = createBoundingBox(width, height, depth);
	  bb.createPosition(x, y, z);
	  return bb;
	}

	/* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      GraphicalObject graphicalObject = (GraphicalObject) object;
      equals &= graphicalObject.isSetBoundingBox() == isSetBoundingBox();
      if (equals && isSetBoundingBox()) {
        equals &= graphicalObject.getBoundingBox().equals(getBoundingBox());
      }
    }
    return equals;
  }

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
	 */
	@Override
	public boolean getAllowsChildren() {
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
	
	/* (non-Javadoc)
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
		if (isSetBoundingBox()) {
			if (pos == index) {
				return getBoundingBox();
			}
			pos++;
		}
		throw new IndexOutOfBoundsException(MessageFormat.format(
		  "Index {0,number,integer} >= {1,number,integer}",
			index, +((int) Math.min(pos, 0))));
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getChildCount()
	 */
	@Override
	public int getChildCount() {
		int count = super.getChildCount();
		if (isSetBoundingBox()) {
			count++;
		}
		return count;
	}

	/* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 941;
    int hashCode = super.hashCode();
    if (isSetBoundingBox()) {
      hashCode += prime * getBoundingBox().hashCode();
    }
    return hashCode;
  }

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
	 */
	public boolean isIdMandatory() {
		// See Layout Extension (April 25, 2005) page 9, SId use="required"
		return true;
	}
	
	/**
	 * @return
	 */
	public boolean isSetBoundingBox() {
		return boundingBox != null;
	}
	
	/**
	 * 
	 * @param boundingBox
	 */
	public void setBoundingBox(BoundingBox boundingBox) {
		unsetBoundingBox();
		this.boundingBox = boundingBox;
		registerChild(this.boundingBox);
	}
	
	/**
	 * 
	 */
	public void unsetBoundingBox() {
		if(isSetBoundingBox()){
			BoundingBox oldValue = this.boundingBox;
			this.boundingBox = null;
			oldValue.fireNodeRemovedEvent();
		}
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
		
		return attributes;
	}

}
