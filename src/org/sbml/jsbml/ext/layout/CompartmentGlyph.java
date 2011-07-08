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

/**
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * @version $Rev$
 */
public class CompartmentGlyph extends GraphicalObject {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -831178362695634919L;
	/**
	 * 
	 */
	private String compartment;

	/**
	 * 
	 */
	public CompartmentGlyph() {
	}

	/**
	 * 
	 * @param compartmentGlyph
	 */
	public CompartmentGlyph(CompartmentGlyph compartmentGlyph) {
		super(compartmentGlyph);
		if (compartmentGlyph.isSetCompartment()) {
			this.compartment = new String(compartmentGlyph.getCompartment());
		}
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public CompartmentGlyph(int level, int version) {
		super(level, version);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#clone()
	 */
	@Override
	public CompartmentGlyph clone() {
		return new CompartmentGlyph(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (o instanceof CompartmentGlyph) {
			CompartmentGlyph cg = (CompartmentGlyph) o;
			boolean equals = super.equals(cg);
			equals &= isSetCompartment() && cg.isSetCompartment();
			if (equals && isSetCompartment()) {
				equals &= getCompartment().equals(cg.getCompartment());
			}
			return equals;
		}
		return false;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCompartment() {
		return compartment;
	}

	/**
	 * @return
	 */
	public boolean isSetCompartment() {
		return (compartment != null) && (compartment.length() > 0);
	}
	
	/**
	 * 
	 * @param compartment
	 */
	public void setCompartment(String compartment) {
		this.compartment = compartment;
	}
}
