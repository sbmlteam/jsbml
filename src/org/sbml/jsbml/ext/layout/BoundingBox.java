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
 * 
 * @author
 * @since 0.8
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
	private Point point;
	/**
	 * 
	 */
	private Dimensions dimensions;

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
		// TODO Auto-generated constructor stub
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

	/**
	 * 
	 * @return
	 */
	public Dimensions getDimensions() {
		return dimensions;
	}

	/**
	 * 
	 * @return
	 */
	public Point getPoint() {
		return point;
	}

	/**
	 * 
	 * @param dimensions
	 */
	public void setDimensions(Dimensions dimensions) {
		this.dimensions = dimensions;
	}

	/**
	 * 
	 * @param point
	 */
	public void setPoint(Point point) {
		this.point = point;
	}
}
