/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2013 jointly by the following organizations:
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
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.xml.parsers.GroupsParser;

/**
 * @author Nicolas Rodriguez
 * @author Clemens Wrzodek
 * @since 1.0
 * @version $Rev$
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
		super();
		initDefaults();
	}
	
	private void initDefaults() {
		addNamespace(GroupsParser.namespaceURI);		
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
		super(member);
		this.symbol = new String(member.getSymbol());
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
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
		
		SBase instance = getModel().getSpecies(symbol);
		if (instance == null) {
		  instance = getModel().getReaction(symbol);
		}
		if (instance == null) {
		  instance = getModel().getCompartment(symbol);
		}
		
		return instance;
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
		firePropertyChange(TreeNodeChangeEvent.symbol, oldSymbol, this.symbol);
	}

	/* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Member [symbol=" + symbol + "]";
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
			attributes.put(GroupsParser.shortLabel + ":symbol", symbol);
		}
		
		return attributes;
	}
	
}
