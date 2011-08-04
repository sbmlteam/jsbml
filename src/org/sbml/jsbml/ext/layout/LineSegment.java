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
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class LineSegment extends AbstractNamedSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -5085246314333062152L;
	/**
	 * 
	 */
	private Point end;
	/**
	 * 
	 */
	private Point start;

	/**
	 * 
	 */
	public LineSegment() {

	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public LineSegment(int level, int version) {
		super(level, version);
	}

	/**
	 * 
	 * @param lineSegment
	 */
	public LineSegment(LineSegment lineSegment) {
		super(lineSegment);
		if (lineSegment.isSetStart()) {
			this.start = lineSegment.getStart().clone();
		}
		if (lineSegment.isSetEnd()) {
			this.end = lineSegment.getEnd().clone();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public LineSegment clone() {
		return new LineSegment(this);
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
		if (isSetStart()) {
			if (pos == index) {
				return getStart();
			}
			pos++;
		}
		if (isSetEnd()) {
			if (pos == index) {
				return getEnd();
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
		if (isSetStart()) {
			count++;
		}
		if (isSetEnd()) {
			count++;
		}
		return count;
	}

	/**
	 * 
	 * @return
	 */
	public Point getEnd() {
		return end;
	}

	/**
	 * 
	 * @return
	 */
	public Point getStart() {
		return start;
	}

	/**
	 * @return
	 */
	public boolean isSetEnd() {
		return end != null;
	}

	/**
	 * @return
	 */
	public boolean isSetStart() {
		return start != null;
	}
	
	/**
	 * 
	 * @param end
	 */
	public void setEnd(Point end) {
		this.end = end;
	}
	
	/**
	 * 
	 * @param start
	 */
	public void setStart(Point start) {
		this.start = start;
	}
}
