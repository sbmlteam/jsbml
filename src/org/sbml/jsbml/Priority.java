/*
 * $Id:  Priority.java 09:13:47 draeger $
 * $URL: Priority.java $
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

/**
 * This class represents the priority element in SBML.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2010-10-21
 */
public class Priority extends AbstractMathContainer {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 391689890391156873L;

	/**
	 * 
	 */
	public Priority() {
		super();
	}

	/**
	 * @param math
	 * @param level
	 * @param version
	 */
	public Priority(ASTNode math, int level, int version) {
		super(math, level, version);
		if (isSetLevel() && (getLevel() < 3)) {
			throw new IllegalArgumentException("Cannot create Priority element with Level < 3.");
		}
	}

	/**
	 * @param level
	 * @param version
	 */
	public Priority(int level, int version) {
		super(level, version);
		if (isSetLevel() && (getLevel() < 3)) {
			throw new IllegalArgumentException("Cannot create Priority element with Level < 3.");
		}
	}

	/**
	 * @param sb
	 */
	public Priority(Priority sb) {
		super(sb);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractMathContainer#clone()
	 */
	@Override
	public Priority clone() {
		return new Priority(this);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getParent()
	 */
	@Override
	public Event getParent() {
		return (Event) super.getParent();
	}

}
