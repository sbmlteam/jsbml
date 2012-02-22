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

package org.sbml.jsbml;

import java.util.Locale;
import java.util.Map;

import org.sbml.jsbml.util.StringTools;

/**
 * Represents a globally valid parameter in the model, i.e., a variable that
 * may change during a simulation or that provides a constant value.
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @author Nicolas Rodriguez
 * @since 0.8
 * @version $Rev$
 */
public class Parameter extends Symbol {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 3300762892435768291L;

	/**
	 * Creates a Parameter instance.
	 */
	public Parameter() {
		super();
		initDefaults();
	}
	
	/**
	 * Creates a Parameter instance from an id, level and version.
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public Parameter(int level, int version) {
		super(level, version);
		initDefaults();
	}

	/**
	 * This constructor allows the creation of a global {@link Parameter} based on a
	 * {@link LocalParameter}. It creates a new {@link Parameter} object that will have the
	 * same attributes than the {@link LocalParameter}. Its constant attribute will be
	 * set to true.
	 * 
	 * @param localParameter
	 */
	public Parameter(LocalParameter localParameter) {
		super(localParameter);
		if (getLevel() != 1) {
			// This is necessary because in Level 1 this attribute is not defined.
			setConstant(true);
		}
	}

	/**
	 * Creates a Parameter instance from a given Parameter.
	 * 
	 * @param p
	 */
	public Parameter(Parameter p) {
		super(p);		
	}

	/**
	 * 
	 * @param id
	 */
	public Parameter(String id) {
		this();
		setId(id);
	}

	/**
	 * 
	 * @param id
	 */
	public Parameter(String id, int level, int version) {
		super(id, level, version);
		initDefaults();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Symbol#clone()
	 */
	public Parameter clone() {
		return new Parameter(this);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractNamedSBaseWithUnit#getPredefinedUnitID()
	 */
	public String getPredefinedUnitID() {
		return null;
	}

	/**
	 * Initializes the default values of this {@link Parameter}, i.e., sets it to a
	 * constant variable with a NaN value.
	 */
	public void initDefaults(int level, int version) {
		value = Double.NaN;

		if (level > 1) {
			constant = true;
		}
	}
	
	/**
	 * Initializes the default values using the current Level/Version configuration.
	 */
	public void initDefaults() {
		initDefaults(getLevel(), getVersion());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Symbol#readAttribute(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);
		
		if (!isAttributeRead) {
			isAttributeRead = true;
		
			if (attributeName.equals("value")) {
				setValue(StringTools.parseSBMLDouble(value));
			} else if (attributeName.equals("units")) {
				setUnits(value);
			} else if (attributeName.equals("constant")) {
				setConstant(StringTools.parseSBMLBoolean(value));
			} else {
				isAttributeRead = false;
			}
		}

		return isAttributeRead;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Symbol#writeXMLAttributes()
	 */
	@Override
	public Map<String, String> writeXMLAttributes() {
	  Map<String, String> attributes = super.writeXMLAttributes();

		if (isSetValue()) {
			attributes.put("value", StringTools.toString(Locale.ENGLISH,
					getValue()));
		}
		if (isSetUnits()) {
			attributes.put("units", getUnits());
		}
		if (isSetConstant() && (getLevel() > 1)) {
			attributes.put("constant", Boolean.toString(getConstant()));
		}

		return attributes;
	}
}
