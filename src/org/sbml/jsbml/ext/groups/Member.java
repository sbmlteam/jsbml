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

package org.sbml.jsbml.ext.groups;

import java.util.Map;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.SBaseChangedEvent;

/**
 * 
 * @author 
 *
 */
public class Member extends AbstractSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 1726020714284762330L;
	/**
	 * 
	 */
	protected String symbol; 
	
	/**
	 * 
	 */
	public Member() {
		
	}
	
	/**
	 * 
	 * @param level
	 * @param version
	 */
	public Member(int level, int version) {
		super(level, version);
	}
	
	/**
	 * 
	 * @param member
	 */
	public Member(Member member) {
		// TODO
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public Member clone() {
		return new Member(this);
	}

	/**
	 * 
	 * @return
	 */
	public String getSymbol() {
		return symbol == null ? "" : symbol;
	}

	/**
	 * 
	 * @return
	 */
	public SBase getSymbolInstance() {
		if (getModel() == null) {
			return null;
		}
		
		// TODO or remove method
		return null; // getModel().getCompartment(this.compartmentID);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetSymbol() {
		return symbol != null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		
		if (!isAttributeRead) {
			if (attributeName.equals("symbol")) {
				this.symbol = value;
				return true;
			}
		}
		return isAttributeRead;
	}
	
	/**
	 * 
	 * @param symbol
	 */
	public void setSymbol(NamedSBase symbol) {
		setSymbol(symbol != null ? symbol.getId() : null);
	}
	
	/**
	 * 
	 * @param symbolId
	 */
	public void setSymbol(String symbolId) {
		String oldSymbol = this.symbol;
		if ((symbolId != null) && (symbolId.trim().length() == 0)) {
			this.symbol = null;
		} else {
			this.symbol = symbolId;
		}
		firePropertyChange(SBaseChangedEvent.symbol, oldSymbol, this.symbol);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#toString()
	 */
	@Override
	public String toString() {
		// TODO
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetSymbol()) {
			attributes.put("symbol", symbol);
		}
		
		return attributes;
	}
}
