/*
 * $Id$
 * $URL$
 *
 *
 *==================================================================================
 * Copyright (c) 2009 the copyright is held jointly by the individual
 * authors. See the file AUTHORS for the list of authors.
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

/**
 * Represents the algebraicRule XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * 
 */
public class AlgebraicRule extends Rule {

	/**
	 * Creates an AlgebraicRule instance.
	 */
	public AlgebraicRule() {
		super();
	}

	/**
	 * Creates an AlgebraicRule instance from math, level and version.
	 * 
	 * @param math
	 * @param level
	 * @param version
	 */
	public AlgebraicRule(ASTNode math, int level, int version) {
		super(math, level, version);
	}

	/**
	 * Creates an AlgebraicRule instance from level and version.
	 * 
	 * @param level
	 * @param version
	 */
	public AlgebraicRule(int level, int version) {
		super(level, version);
	}

	/**
	 * Creates an AlgebraicRule instance from a given MathContainer instance.
	 * 
	 * @param sb
	 */
	public AlgebraicRule(MathContainer sb) {
		super(sb);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.MathContainer#clone()
	 */
	// @Override
	public AlgebraicRule clone() {
		return new AlgebraicRule(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#isCompartmentVolume()
	 */
	@Override
	public boolean isCompartmentVolume() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#isParameter()
	 */
	@Override
	public boolean isParameter() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#isScalar()
	 */
	@Override
	public boolean isScalar() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#isSpeciesConcentration()
	 */
	@Override
	public boolean isSpeciesConcentration() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);

		return isAttributeRead;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.Rule#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();

		return attributes;
	}

}
