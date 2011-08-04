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

/**
 * 
 * @author Nicolas Rodriguez
 * @author Sebastian Fr&ouml;lich
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class GraphicalObject extends AbstractNamedSBase {

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
	}

	/**
	 * @param id
	 */
	public GraphicalObject(String id) {
		super(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public GraphicalObject clone() {
		return new GraphicalObject(this);
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
		if (isSetBoundingBox()) {
			if (pos == index) {
				return getBoundingBox();
			}
			pos++;
		}
		throw new IndexOutOfBoundsException(String.format("Index %d >= %d",
				index, +((int) Math.min(pos, 0))));
	}

	/*
	 * (non-Javadoc)
	 * 
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
		this.boundingBox = boundingBox;
	}
}
