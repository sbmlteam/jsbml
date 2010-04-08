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
 * The base class for each SBML element with an optional id and name.
 * 
 * @author Andreas Dr&auml;ger
 * @author rodrigue
 * @author marine
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public abstract class AbstractNamedSBase extends AbstractSBase implements
		NamedSBase {

	/**
	 * id of the SBML component (can be optional depending on the level and
	 * version). Matches the id attribute of an element in a SBML file.
	 */
	private String id;
	/**
	 * name of the SBML component (can be optional depending on the level and
	 * version). Matches the name attribute of an element in a SBML file.
	 */
	private String name;

	/**
	 * Creates an AbctractNamedSBase. By default, id and name are null.
	 */
	public AbstractNamedSBase() {
		super();
		id = null;
		name = null;
	}

	/**
	 * Creates an AbctractNamedSBase from a level and version. By default, id
	 * and name are null.
	 * 
	 * @param level
	 * @param version
	 */
	public AbstractNamedSBase(int level, int version) {
		this();
		setLevel(level);
		setVersion(version);
	}

	/**
	 * Creates an AbctractNamedSBase from a given AbstractNamedSBase.
	 * 
	 * @param nsb
	 */
	public AbstractNamedSBase(AbstractNamedSBase nsb) {
		super(nsb);
		this.id = nsb.isSetId() ? new String(nsb.getId()) : null;
		this.name = nsb.isSetName() ? new String(nsb.getName()) : null;
	}

	/**
	 * Creates an AbctractNamedSBase from an id, level and version.
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public AbstractNamedSBase(String id, int level, int version) {
		this(id, null, level, version);
	}

	/**
	 * Creates an AbctractNamedSBase from an id, name, level and version.
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public AbstractNamedSBase(String id, String name, int level, int version) {
		this(level, version);
		this.id = id != null ? new String(id) : null;
		this.name = name != null ? new String(name) : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof NamedSBase) {
			boolean equals = super.equals(o);
			NamedSBase nsb = (NamedSBase) o;
			equals &= nsb.isSetId() == isSetId();
			if (nsb.isSetId() && isSetId()) {
				equals &= nsb.getId().equals(getId());
			}
			equals &= nsb.isSetName() == isSetName();
			if (equals && nsb.isSetName()) {
				equals &= nsb.getName().equals(getName());
			}
			return equals;
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
		return isSetName() ? this.name : "";
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
	 * @see org.sbml.jsbml.element.NamedSBase#setId(java.lang.String)
	 */
	public void setId(String id) {
		if (id != null && id.trim().length() == 0) {
			this.id = null;
		} else {
			this.id = id;
		}

		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#unsetName()
	 */
	public void unsetName() {
		this.name = null;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#unsetId()
	 */
	public void unsetId() {
		this.id = null;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#setName(java.lang.String)
	 */
	public void setName(String name) {
		if (name != null && name.trim().length() == 0) {
			this.name = null;
		} else {
			this.name = name;
		}

		if (!isSetId() && level == 1) {
			this.id = name;
		}
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
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);

		// TODO : we should probably be careful there and check if there is a
		// prefix set before reading the id or name
		// as there are not defined at the level of the SBase on the SBML
		// specifications and some packages might define them in their own
		// namespace.

		if (!isAttributeRead) {
			if (attributeName.equals("id") && getLevel() > 1) {
				this.setId(value);
				return true;
			} else if (attributeName.equals("name")) {
				this.setName(value);
				if (isSetLevel() && getLevel() == 1) {
					this.setId(value);
				}
				return true;
			}
			return false;
		}

		return isAttributeRead;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#writeAttributes()
	 */
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();

		if (isSetId() && getLevel() > 1) {
			attributes.put("id", getId());
		}
		if (isSetName()) {
			attributes.put("name", getName());
		}

		return attributes;
	}
}
