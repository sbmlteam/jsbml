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

/*
 *     JSBML - The SBML Java Library - development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public License. This should
 * be distributed with the code. If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors. These should be listed in @author doc comments.
 *
 * For more information on the JSBML project and its aims,
 * or to join the jsbml-development mailing list, visit the 
 * home page at:
 *
 *      http://sbml.org
 *
 */
package org.sbml.jsbml;

/**
 * 
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * @date 2009-08-31
 */
public abstract class AbstractNamedSBase extends AbstractSBase implements
		NamedSBase {

	private String id;
	private String name;

	/**
	 * 
	 */
	public AbstractNamedSBase(int level, int version) {
		super(level, version);
		id = null;
		name = null;
	}

	/**
	 * 
	 * @param nsb
	 */
	public AbstractNamedSBase(AbstractNamedSBase nsb) {
		super(nsb);
		if (nsb.isSetId())
			this.id = new String(nsb.getId());
		if (nsb.isSetName())
			this.name = new String(nsb.getName());
	}

	/**
	 * 
	 * @param id
	 */
	public AbstractNamedSBase(String id, int level, int version) {
		super(level, version);
		this.id = new String(id);
		name = null;
	}

	/**
	 * 
	 * @param id
	 * @param name
	 */
	public AbstractNamedSBase(String id, String name, int level, int version) {
		super(level, version);
		this.id = new String(id);
		this.name = new String(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof NamedSBase) {
			boolean equals = super.equals(o);
			NamedSBase nsb = (NamedSBase) o;
			equals &= nsb.isSetId() == isSetId();
			if (nsb.isSetId() && isSetId())
				equals &= nsb.getId().equals(getId());
			equals &= nsb.isSetName() == isSetName();
			if (nsb.isSetName() && isSetName())
				equals &= nsb.getName().equals(getName());
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.NamedSBase#getId()
	 */
	public String getId() {
		return isSetId() ? id : "";
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
	 * @see org.sbml.NamedSBase#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
		stateChanged();
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
}
