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
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.multi;

import java.util.Map;

import org.sbml.jsbml.AbstractSBase;

/**
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 * @date 10.10.2013
 */
public class BindingSiteReference extends AbstractSBase {

	/**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -744301449365477023L;

  private String speciesTypeState;
	
	public BindingSiteReference() {
		super();
		initDefaults();
	}
	
	/**
	 * 
	 */
	public void initDefaults() {
		addNamespace(MultiConstant.namespaceURI);
	}

	@Override
	public AbstractSBase clone() {
		// TODO 
		return null;
	}

	/**
	 * Returns the speciesTypeState.
	 * 
	 * @return the speciesTypeState
	 */
	public String getSpeciesTypeState() {
		return speciesTypeState;
	}

	/**
	 * Sets the speciesTypeState.
	 * 
	 * @param speciesTypeState the speciesTypeState to set
	 */
	public void setSpeciesTypeState(String speciesTypeState) {
		this.speciesTypeState = speciesTypeState;
	}

	/**
	 * Returns {@code true} if the speciesTypeState is set.
	 * 
	 * @return {@code true} if the speciesTypeState is set.
	 */
	public boolean isSetSpeciesTypeState() {
		return speciesTypeState != null;
	}

	@Override
	public String toString() {
		// TODO 
		return null;
	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) 
	{
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

		if (!isAttributeRead) {

			if (attributeName.equals(MultiConstant.speciesTypeState)) {
				setSpeciesTypeState(value);
				isAttributeRead = true;
			} 
		}	  

		return isAttributeRead;

	}

	/* (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetSpeciesTypeState()) {
			attributes.put(MultiConstant.shortLabel + ':' + MultiConstant.speciesTypeState, getSpeciesTypeState());
		} 

		return attributes;
	}

	// TODO : removeXX unsetXX, equals, hashCode, ...
}
