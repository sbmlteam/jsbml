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

import org.sbml.jsbml.Model;
import org.sbml.jsbml.NamedSBase;

/**
 * Abstract super class for all kinds of glyphs that graphically represent an
 * instance of {@link NamedSBase}.
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 */
public abstract class NamedSBaseGlyph extends GraphicalObject {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 3542638384361924654L;

	/**
	 * The identifier of the {@link NamedSBase} represented by this
	 * {@link GraphicalObject}.
	 */
	private String sbaseID;
	
	/**
	 * 
	 */
	public NamedSBaseGlyph() {
		super();
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public NamedSBaseGlyph(int level, int version) {
		super(level, version);
	}

	/**
	 * 
	 * @param glyp
	 */
	public NamedSBaseGlyph(NamedSBaseGlyph glyph) {
		super(glyph);
		if (glyph.isSetNamedSBase()) {
			setName(glyph.getNamedSBase());
		}
	}
	
	/**
	 * @param id
	 */
	public NamedSBaseGlyph(String id) {
		super(id);
	}
	
	/**
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public NamedSBaseGlyph(String id, int level, int version) {
		super(id, level, version);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.ext.layout.GraphicalObject#clone()
	 */
	@Override
	public abstract NamedSBaseGlyph clone();

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		boolean equal = super.equals(object);
		if (equal) {
			NamedSBaseGlyph nsg = (NamedSBaseGlyph) object;
			equal &= isSetNamedSBase() == nsg.isSetNamedSBase();
			if (equal && isSetNamedSBase()) {
				equal &= getNamedSBase().equals(nsg.getNamedSBase());
			}
		}
		return equal;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getNamedSBase() {
		return isSetNamedSBase() ? sbaseID : "";
	}
	
	/**
	 * 
	 * @return
	 */
	public NamedSBase getNamedSBaseInstance() {
		Model model = getModel();
		return isSetNamedSBase() && (model != null) ? model.findNamedSBase(getNamedSBase()) : null;
	}
	
	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 983;
		int result = super.hashCode();
		result = prime * result + (isSetNamedSBase() ? 0 : sbaseID.hashCode());
		return result;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetNamedSBase() {
		return (sbaseID != null) && (sbaseID.length() > 0);
	}
	
	/**
	 * 
	 * @param namedSBase
	 */
	public void setNamedSBase(NamedSBase namedSBase) {
	  setNamedSBase(namedSBase.getId());
	}

	/**
	 * 
	 * @param sbase
	 */
	public void setNamedSBase(String sbase) {
		setNamedSBase(sbase, "namedSBase");
	}

	/**
	 * 
	 * @param sbase
	 * @param type
	 */
	void setNamedSBase(String sbase, String type) {
		String oldSBase = this.sbaseID;
		this.sbaseID = sbase;
		firePropertyChange(type, oldSBase, this.sbaseID);
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#toString()
	 */
	@Override
	public String toString() {
		return getElementName() + " [" + (isSetNamedSBase() ? getNamedSBase() : "") + ']';
	}
	
	/**
	 * 
	 */
	public void unsetNamedSBase() {
		setNamedSBase((String) null);
	}

}
