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

import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 1.0
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
		addNamespace(LayoutConstant.namespaceURI);
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
	public boolean equals(Object object) {
		boolean equals = super.equals(object);
		if (equals) {
			CompartmentGlyph cg = (CompartmentGlyph) object;
			equals &= isSetCompartment() && cg.isSetCompartment();
			if (equals && isSetCompartment()) {
				equals &= getCompartment().equals(cg.getCompartment());
			}
		}
		return equals;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCompartment() {
		return compartment;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 983;
		int hashCode = super.hashCode();
		if (isSetCompartment()) {
			hashCode += prime * getCompartment().hashCode();
		}
		return hashCode;
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
		String oldCompartment = this.compartment;
		this.compartment = compartment;
		firePropertyChange(TreeNodeChangeEvent.compartment, oldCompartment, this.compartment);
	}
	
	/**
	 * 
	 * @param attributeName
	 * @param prefix
	 * @param value
	 * @return
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		
		if(!isAttributeRead)
		{
		
			if(attributeName.equals(LayoutConstant.compartment))
			{	
				setCompartment(value);
			}
			else
			{
				return false;
			}
		
			return true;
		}
		
		return isAttributeRead;
	}

	
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();
		
		if (isSetCompartment()) {
			attributes.put(LayoutConstant.shortLabel + ":"
					+ LayoutConstant.compartment, compartment);
		}

		return attributes;
	}


}
