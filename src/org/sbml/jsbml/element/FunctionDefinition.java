/*
 * $Id: FunctionDefinition.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/FunctionDefinition.java $
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

package org.sbml.jsbml.element;

import java.util.HashMap;

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public class FunctionDefinition extends MathContainer implements NamedSBase {

	/**
	 * 
	 */
	private String id;
	/**
	 * optional
	 */
	private String name;

	/**
	 * 
	 */
	public FunctionDefinition() {
		super();
		this.id = null;
		this.name = null;
	}
	
	/**
	 * @param sb
	 */
	public FunctionDefinition(FunctionDefinition sb) {
		super(sb);
	}

	/**
	 * 
	 * @param id
	 */
	public FunctionDefinition(String id, int level, int version) {
		super(level, version);
		this.id = id;
	}

	/**
	 * 
	 * @param id
	 * @param lambda
	 */
	public FunctionDefinition(String id, ASTNode lambda, int level, int version) {
		super(lambda, level, version);
		if (!lambda.isLambda())
			throw new IllegalArgumentException(
					"Math element must be of type Lambda.");
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.MathContainer#clone()
	 */
	// @Override
	public FunctionDefinition clone() {
		return new FunctionDefinition(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.MathContainer#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof FunctionDefinition) {
			boolean equal = super.equals(o);
			FunctionDefinition fd = (FunctionDefinition) o;
			equal &= fd.getId().equals(getId());
			equal &= fd.isSetName() == isSetName();
			if (fd.isSetName() && isSetName())
				equal &= fd.getName().equals(getName());
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.NamedSBase#getId()
	 */
	public String getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.NamedSBase#getName()
	 */
	public String getName() {
		return isSetName() ? name : "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.NamedSBase#isSetId()
	 */
	public boolean isSetId() {
		return id != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.NamedSBase#isSetName()
	 */
	public boolean isSetName() {
		return name != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.MathContainer#setFormula(java.lang.String)
	 */
	// @Override
	public void setFormula(String formula) {
		ASTNode math = ASTNode.parseFormula(formula);
		if (!math.isLambda())
			throw new IllegalArgumentException(
					"Math element must be of type Lambda.");
		setMath(math);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.NamedSBase#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.MathContainer#setMath(org.sbml.ASTNode)
	 */
	// @Override
	public void setMath(ASTNode math) {
		if (!math.isLambda())
			throw new IllegalArgumentException(
					"Math element must be of type Lambda.");
		super.setMath(math.clone());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#toString()
	 */
	// @Override
	public String toString() {
		if (isSetName() && getName().length() > 0)
			return name;
		if (isSetId())
			return id;
		String name = getClass().getName();
		return name.substring(name.lastIndexOf('.') + 1);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.NamedSBase#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
		stateChanged();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Rule#isSpeciesConcentration()
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
	
		return isAttributeRead;
	}

	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		
		return attributes;
	}

	public void unsetId() {
		this.id = null;
	}

	public void unsetName() {
		this.name = null;
	}
}
