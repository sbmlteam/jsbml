/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2012 jointly by the following organizations:
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

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.util.TreeNodeChangeEvent;

/**
 * 
 * @author Nicolas Rodriguez
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public class CompartmentGlyph extends NamedSBaseGlyph {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = -831178362695634919L;

	/**
	 * 
	 */
	public CompartmentGlyph() {
		super();
	}
	
	/**
	 * 
	 * @param compartmentGlyph
	 */
	public CompartmentGlyph(CompartmentGlyph compartmentGlyph) {
		super(compartmentGlyph);
	}
	
	/**
	 * 
	 * @param level
	 * @param version
	 */
	public CompartmentGlyph(int level, int version) {
		super(level, version);
	}
	
	/**
	 * 
	 * @param id
	 */
	public CompartmentGlyph(String id) {
		super(id);
	}

	/**
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public CompartmentGlyph(String id, int level, int version) {
		super(id, level, version);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#clone()
	 */
	public CompartmentGlyph clone() {
		return new CompartmentGlyph(this);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCompartment() {
		return getNamedSBase();
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.NamedSBaseGlyph#getNamedSBaseInstance()
	 */
	@Override
	public Compartment getNamedSBaseInstance() {
		return (Compartment) super.getNamedSBaseInstance();
	}

	/**
	 * @return
	 */
	public boolean isSetCompartment() {
		return isSetNamedSBase();
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		
		if (!isAttributeRead) {
		
			if (attributeName.equals(LayoutConstant.compartment)) {	
				setCompartment(value);
			} else {
				return false;
			}
		
			return true;
		}
		
		return isAttributeRead;
	}
	
	/**
	 * 
	 * @param compartment
	 */
	public void setCompartment(Compartment compartment) {
		setCompartment(compartment.getId());
	}
	
	/**
	 * 
	 * @param compartment
	 */
	public void setCompartment(String compartment) {
		setNamedSBase(compartment, TreeNodeChangeEvent.compartment);
	}
	
	/**
	 * 
	 */
	public void unsetCompartment() {
		unsetNamedSBase();
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();
		
		if (isSetCompartment()) {
			attributes.put(LayoutConstant.shortLabel + ":"
					+ LayoutConstant.compartment, getCompartment());
		}

		return attributes;
	}

}
