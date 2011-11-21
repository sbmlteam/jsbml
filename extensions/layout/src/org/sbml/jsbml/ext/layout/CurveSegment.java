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

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * @author Sebastian Fr&oum;hlich
 * @since 1.0
 * @version $Rev$
 */
public class CurveSegment extends CubicBezier {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -5085246314333062152L;

	/**
	 * 
	 */
	private String type;

	/**
	 * 
	 */
	private Logger logger = Logger.getLogger(CurveSegment.class);

	/**
	 * 
	 */
	public CurveSegment() {
	  super();
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public CurveSegment(int level, int version) {
		super(level, version);
	}

	/**
	 * 
	 * @param lineSegment
	 */
	public CurveSegment(CurveSegment lineSegment) {
		super(lineSegment);
		if (lineSegment.isSetStart()) {
			this.start = lineSegment.getStart().clone();
		}
		if (lineSegment.isSetEnd()) {
			this.end = lineSegment.getEnd().clone();
		}
		if (lineSegment.isSetType()) {
			this.type = lineSegment.getType();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public CurveSegment clone() {
		return new CurveSegment(this);
	}

	/*
	 * (non-Javadoc)
	 * 
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
	 * 
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
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 */
	public void setType(String type) {
		String oldType = this.type;
		this.type = type;
		firePropertyChange(TreeNodeChangeEvent.type, oldType, this.type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
	 */
	public boolean isIdMandatory() {
		// TODO Auto-generated method stub
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
	 * @return
	 */
	public boolean isSetType() {
		return type != null;
	}

	/**
	 * 
	 * @param end
	 */
	public void setEnd(Point end) {
		if (this.end != null) {
			this.end.fireNodeRemovedEvent();
		}
		this.end = end;
		registerChild(this.end);
	}

	/**
	 * 
	 * @param start
	 */
	public void setStart(Point start) {
		if (this.start != null) {
			this.start.fireNodeRemovedEvent();
		}
		this.start = start;
		registerChild(this.start);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractMathContainer#readAttribute(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		logger.debug("reading CurveSegment: " + prefix + " : " + attributeName);
		if (!isAttributeRead) {
			isAttributeRead = true;
			if ((prefix.equals("xsi") || prefix.equals(""))
					&& attributeName.equals("type")) {
				setType(value);
				return true;
			}
		}
		return isAttributeRead;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();
		logger.debug("process attributes of CurveSegment");
		logger.debug("isSetType: " + isSetType());
		if (isSetType()) {
			attributes.put("xsi:type", getType());
		}
		return attributes;
	}

}
