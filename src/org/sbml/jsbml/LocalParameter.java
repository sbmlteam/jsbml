/*
 * $Id: LocalParameter.java 175 2010-04-12 02:37:31Z andreas-draeger $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/LocalParameter.java $
 *
 * 
 *==================================================================================
 * Copyright (c) 2009 The jsbml team.
 *
 * This file is part of jsbml, the pure java SBML library. Please visit
 * http://sbml.org for more information about SBML, and http://jsbml.sourceforge.net/
 * to get the latest version of jsbml.
 *
 * jsbml is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * jsbml is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with jsbml.  If not, see <http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html>.
 *
 *===================================================================================
 *
 */
package org.sbml.jsbml;

import java.util.HashMap;

import org.sbml.jsbml.util.StringTools;

/**
 * A local parameter can only be used to specify a constant within a
 * {@link KineticLaw}.
 * 
 * @author Andreas Dr&auml;ger
 * @author Nicolas Rodriguez
 * @date 2010-04-20
 * 
 */
public class LocalParameter extends QuantityWithDefinedUnit {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 57994535283502018L;

	/**
	 * 
	 */
	public LocalParameter() {
		super();
	}
	
	/**
	 * @param level
	 * @param version
	 */
	public LocalParameter(int level, int version) {
		super(level, version);
	}

	/**
	 * @param lp
	 */
	public LocalParameter(LocalParameter lp) {
		super(lp);
	}

	/**
	 * Creates a new local parameter that will have the same properties than the
	 * given global parameter. However, the value of the constant attribute will
	 * be ignored because a local parameter can only be a constant quantity.
	 * 
	 * @param parameter
	 */
	public LocalParameter(Parameter parameter) {
		super(parameter);
	}

	/**
	 * 
	 * @param id
	 */
	public LocalParameter(String id) {
		super(id);
	}

	/**
	 * @param id
	 * @param level
	 * @param version
	 */
	public LocalParameter(String id, int level, int version) {
		super(id, level, version);
	}

	/**
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public LocalParameter(String id, String name, int level, int version) {
		super(id, name, level, version);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.QuantityWithDefinedUnit#clone()
	 */
	@Override
	public LocalParameter clone() {
		return new LocalParameter(this);
	}


	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getElementName()
	 */
	public String getElementName() {
		if (getLevel() < 3) {
			return "parameter";
		}
		return super.getElementName();
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
		if (attributeName.equals("value")) {
			this.setValue(StringTools.parseSBMLDouble(value));
			return true;
		} else if (attributeName.equals("units")) {
			this.setUnits(value);
			return true;
		}
		
		return isAttributeRead;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.Symbol#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();

		if (isSetValue()) {
			attributes.put("value", Double.toString(getValue()));
		}
		if (isSetUnits()) {
			attributes.put("units", getUnits());
		}

		return attributes;
	}

}
