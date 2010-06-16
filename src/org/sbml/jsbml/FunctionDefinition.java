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
 * Represents the functionDefinition XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public class FunctionDefinition extends MathContainer implements
		NamedSBaseWithDerivedUnit {

	/**
	 * Represents the "id" attribute of a functionDefinition element.
	 */
	private String id;
	/**
	 * Represents the "name" attribute of a functionDefinition element.
	 */
	private String name;

	/**
	 * Creates a FunctionDefinition instance. By default, id and name are null.
	 */
	public FunctionDefinition() {
		super();
		this.id = null;
		this.name = null;
	}

	/**
	 * Creates a FunctionDefinition instance from a given FunctionDefinition.
	 * 
	 * @param sb
	 */
	public FunctionDefinition(FunctionDefinition sb) {
		super(sb);
		if (sb.isSetId()) {
			this.id = new String(sb.getId());
		} else {
			this.id = null;
		}
		if (isSetName()) {
			this.name = new String(sb.getName());
		} else {
			this.name = null;
		}
	}

	/**
	 * Creates a FunctionDefinition instance from an id, level and version. By
	 * default, name is null.
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public FunctionDefinition(int level, int version) {
		super(level, version);
	}

	/**
	 * Creates a FunctionDefinition instance from an id, ASTNode, level and
	 * version. By default, name is null. If the ASTNode is not of type lambda,
	 * an IllegalArgumentException is thrown.
	 * 
	 * @param id
	 * @param lambda
	 * @param level
	 * @param version
	 */
	public FunctionDefinition(String id, ASTNode lambda, int level, int version) {
		super(lambda, level, version);
		if (!lambda.isLambda()) {
			throw new IllegalArgumentException(
					"Math element must be of type Lambda.");
		}
		if (id != null) {
			this.id = new String(id);
		} else {
			this.id = null;
		}
		this.name = null;
	}

	/**
	 * 
	 * @param id
	 */
	public FunctionDefinition(String id, int level, int version) {
		super(level, version);
		if (id != null) {
			this.id = new String(id);
		} else {
			this.id = null;
		}
		this.name = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.MathContainer#clone()
	 */
	// @Override
	public FunctionDefinition clone() {
		return new FunctionDefinition(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.MathContainer#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof FunctionDefinition) {
			boolean equal = super.equals(o);
			FunctionDefinition fd = (FunctionDefinition) o;
			if ((!fd.isSetId() && isSetId()) || (fd.isSetId() && !isSetId())) {
				return false;
			}
			if (fd.isSetId() && isSetId()) {
				equal &= fd.getId().equals(getId());
			}

			if ((!fd.isSetName() && isSetName())
					|| (fd.isSetName() && !isSetName())) {
				return false;
			}
			if (fd.isSetName() && isSetName()) {
				equal &= fd.getName().equals(getName());
			}
			return equal;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#getId()
	 */
	public String getId() {
		return isSetId() ? this.id : "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#getName()
	 */
	public String getName() {
		return isSetName() ? name : "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#isSetId()
	 */
	public boolean isSetId() {
		return id != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#isSetName()
	 */
	public boolean isSetName() {
		return name != null;
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
			if (attributeName.equals("id") && getLevel() > 1) {
				setId(value);
			} else if (attributeName.equals("name") && getLevel() > 1) {
				setName(value);
			}
		}

		return isAttributeRead;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.MathContainer#setFormula(java.lang.String)
	 */
	// @Override
	public void setFormula(String formula) {
		ASTNode math = ASTNode.parseFormula(formula);
		if (!math.isLambda()) {
			throw new IllegalArgumentException(
					"Math element must be of type Lambda.");
		}
		setMath(math);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.MathContainer#setMath(org.sbml.ASTNode)
	 */
	@Override
	public void setMath(ASTNode math) {
		if (!math.isLambda()) {
			throw new IllegalArgumentException(
					"math element must be of type Lambda");
		}
		super.setMath(math);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#toString()
	 */
	// @Override
	public String toString() {
		if (isSetName() && getName().length() > 0) {
			return name;
		}
		if (isSetId()) {
			return id;
		}
		String name = getClass().getName();
		return name.substring(name.lastIndexOf('.') + 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#unsetId()
	 */
	public void unsetId() {
		this.id = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#unsetName()
	 */
	public void unsetName() {
		this.name = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();

		if (isSetId() && getLevel() > 1) {
			attributes.put("id", getId());
		}
		if (isSetName() && getLevel() > 1) {
			attributes.put("name", getName());
		}

		return attributes;
	}
}
