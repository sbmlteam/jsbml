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
 * @since 1.0
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
	Point end;
	
	/**
	 * 
	 */
	Point start;

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

	/* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  public boolean isIdMandatory() {
    return false;
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
		if(this.end != null){
			this.end.fireNodeRemovedEvent();
		}
		if (!(end instanceof End)) {
			end = new End(end);
		}
		this.end = end;
		registerChild(this.end);
	}

  /**
	 * 
	 * @param start
	 */
	public void setStart(Point start) {
		if(this.start != null){
			this.start.fireNodeRemovedEvent();
		}
		if (!(start instanceof Start)) {
			start = new Start(start);
		}
		this.start = start;
		registerChild(this.start);
	}

	/**
	 * Creates, sets and returns a {@link Point}
	 *
	 * @return new {@link Point} object.
	 */
	public Point createStart() {
		Point p = new Start();
		setStart(p);
		return p;
	}
	
	/**
	 * Creates, sets and returns a {@link Point} based on the
	 * given values.
	 * @param x
	 * @param y
	 * @param z
	 * @return new {@link Point} object.
	 */
	public Point createStart(double x, double y, double z) {
		Point p = new Start();
		p.setX(x);
		p.setY(y);
		p.setZ(z);
		setStart(p);
		return p;
	}

	
	/**
	 * Creates, sets and returns a {@link Point}
	 *
	 * @return new {@link Point} object.
	 */
	public Point createEnd() {
		Point p = new End();
		setStart(p);
		return p;

	}
	

	/**
	 * Creates, sets and returns a {@link Point} based on the
	 * given values.
	 * @param x
	 * @param y
	 * @param z
	 * @return new {@link Point} object.
	 */
	public Point createEnd(double x, double y, double z) {
		Point p = new End();
		p.setX(x);
		p.setY(y);
		p.setZ(z);
		setEnd(p);
		return p;
	}

}
