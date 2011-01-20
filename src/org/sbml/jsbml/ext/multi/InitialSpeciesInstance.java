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

package org.sbml.jsbml.ext.multi;

import java.util.HashMap;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.util.StringTools;

/**
 * @author
 */
public class InitialSpeciesInstance extends AbstractNamedSBase {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 6619677758810986153L;
	/**
	 * 
	 */
	private Double initialProportion;
	/**
	 * 
	 */
	private String selectorID;

	/**
	 * 
	 */
	public InitialSpeciesInstance() {
		this.selectorID = null;
		this.initialProportion = null;
	}

	/**
	 * 
	 * @param in
	 */
	public InitialSpeciesInstance(InitialSpeciesInstance in) {
		this.setSelector(in.getSelector());
		this.setInitialProportion(in.getInitialProportion());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public InitialSpeciesInstance clone() {
		return new InitialSpeciesInstance(this);
	}

	/**
	 * 
	 * @return
	 */
	public double getInitialProportion() {
		return isSetInitialProportion() ? initialProportion : 0;
	}

	/**
	 * 
	 * @return
	 */
	public String getSelector() {
		return isSetSelector() ? this.selectorID : "";
	}

	/**
	 * 
	 * @return
	 */
	public Selector getSelectorInstance() {
		// TODO extend model to have the listOfSelector and the appropriate
		// methods
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetInitialProportion() {
		return this.initialProportion != null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetSelector() {
		return this.selectorID != null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetSelectorInstance() {
		// TODO extend Model to add the listOfSelector and the appropriate
		// methods
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isReadAttribute = super.readAttribute(attributeName, prefix,
				value);

		if (!isReadAttribute) {
			if (attributeName.equals("initialProportion")) {
				this.initialProportion = StringTools.parseSBMLDouble(value);

				return true;
			} else if (attributeName.equals("selector")) {
				this.selectorID = value;

				return true;
			}
		}

		return isReadAttribute;
	}

	/**
	 * 
	 * @param initialProportion
	 */
	public void setInitialProportion(double initialProportion) {
		this.initialProportion = initialProportion;
	}

	/**
	 * 
	 * @param selector
	 */
	public void setSelector(Selector selector) {
		this.selectorID = selector.isSetId() ? selector.getId() : "";
	}

	/**
	 * 
	 * @param selectorID
	 */
	public void setSelector(String selectorID) {
		this.selectorID = selectorID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractNamedSBase#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = new HashMap<String, String>();

		if (isSetInitialProportion()) {
			attributes.put("initialProportion", Double
					.toString(getInitialProportion()));
		}
		if (isSetSelector()) {
			attributes.put("selector", getSelector());
		}

		return attributes;
	}
}
