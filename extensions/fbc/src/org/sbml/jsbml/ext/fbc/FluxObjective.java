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
package org.sbml.jsbml.ext.fbc;

import java.util.Map;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.xml.parsers.FBCParser;

/**
 * 
 * @author
 * @version $Rev$
 * @since 1.0
 * @date 27.10.2011
 */
public class FluxObjective extends AbstractSBase {

	/**
   * 
   */
  private static final long serialVersionUID = 246449689493121713L;
  
  private String reaction;
	private double coefficient;
	
	private boolean isSetCoefficient = false;
	
	@Override
	public AbstractSBase clone() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @return the reaction
	 */
	public String getReaction() {
		return reaction;
	}
	/**
	 * @param reaction the reaction to set
	 */
	public void setReaction(String reaction) {
		this.reaction = reaction;
	}
	/**
	 * @return the coefficient
	 */
	public double getCoefficient() {
		return coefficient;
	}
	/**
	 * @param coefficient the coefficient to set
	 */
	public void setCoefficient(double coefficient) {
		this.coefficient = coefficient;
		isSetCoefficient = true;
	}
	
	public boolean isSetCoefficient() {
		return isSetCoefficient;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		
		if (!isAttributeRead) {
			isAttributeRead = true;

			if (attributeName.equals("reaction")) {
				setReaction(value);
			} else if (attributeName.equals("coefficient")) {
				setCoefficient(StringTools.parseSBMLDouble(value));
			} else {
				isAttributeRead = false;
			}
			
		}
		
		return isAttributeRead;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
		Map<String, String> attributes = super.writeXMLAttributes();

		if (reaction != null) {
			attributes.put(FBCParser.shortLabel+ ":reaction", getReaction());			
		}
		if (isSetCoefficient()) {
			attributes.put(FBCParser.shortLabel+ ":coefficient", StringTools.toString(getCoefficient()));
		}
		
		return attributes;
	}

}
