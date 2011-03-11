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
 * @author
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
	private double width;
	/**
	 * 
	 */
	private double height;
	/**
	 * 
	 */
	private double depth;

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
		// TODO Auto-generated constructor stub
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

	/**
	 * 
	 * @param depth
	 */
	public void setDepth(double depth) {
		this.depth = depth;
	}

	/**
	 * 
	 * @param height
	 */
	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * 
	 * @param width
	 */
	public void setWidth(double width) {
		this.width = width;
	}
}
