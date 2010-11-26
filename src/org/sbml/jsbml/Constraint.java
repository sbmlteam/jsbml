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

import org.sbml.jsbml.xml.XMLNode;


/**
 * Represents the constraint XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * @author rodrigue
 */
public class Constraint extends AbstractMathContainer {

	/**
	 * Generated serial version identifier.
	 */
	private static final long serialVersionUID = 7396734926596485200L;

	/**
	 * Represents the subnode message of a constraint element.
	 */
	private XMLNode message;

	/**
	 * Creates a Constraint instance. By default, the message is null.
	 */
	public Constraint() {
		super();
		this.message = null;
	}

	/**
	 * Creates a Constraint instance from an ASTNode, a level and a version. By
	 * default, the message is null.
	 * 
	 * @param math
	 * @param level
	 * @param version
	 */
	public Constraint(ASTNode math, int level, int version) {
		super(math, level, version);
		message = null;
	}

	/**
	 * Creates a Constraint instance from a given Constraint.
	 * 
	 * @param sb
	 */
	public Constraint(Constraint sb) {
		super(sb);
		if (sb.isSetMessage()) {
			this.message = new XMLNode(sb.getMessage());
		}
	}

	/**
	 * Creates a Constraint instance from a level and a version. By default, the
	 * message is null.
	 * 
	 * @param level
	 * @param version
	 */
	public Constraint(int level, int version) {
		super(level, version);
		this.message = null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractMathContainer#clone()
	 */
	public Constraint clone() {
		return new Constraint(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.NamedSBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof Constraint) {
			boolean equal = super.equals(o);
			Constraint c = (Constraint) o;
			equal &= c.isSetMessage() == isSetMessage();
			if (equal && isSetMessage()) {
				equal &= c.getMessage().equals(getMessage());
			}
			return equal;
		}
		return false;
	}

	/**
	 * @return the message of this Constraint. Return null if the
	 *         message is not set.
	 */
	public XMLNode getMessage() {
		return isSetMessage() ? message : null;
	}

	/**
	 * 
	 * @return
	 */
	public String getMessageString() {
		return message.toXMLString();
	}

	/**
	 * 
	 * @return true if the message of this Constraint is not null.
	 */
	public boolean isSetMessage() {
		return message != null;
	}

	/**
	 * Sets the message of this Constraint to 'message'.
	 * 
	 * @param message
	 *            : the message to set
	 */
	public void setMessage(XMLNode message) {
		XMLNode oldMessage = this.message;
		this.message = message;
		firePropertyChange(SBaseChangedEvent.message, oldMessage, message);
	}

	/**
	 * Sets the message of this Constraint to 'message'.
	 * 
	 * @param message
	 *            : the message to set
	 */
	public void setMessage(String message) {
		XMLNode oldMessage = this.message;
		
		// TODO : this.message = message;
		
		firePropertyChange(SBaseChangedEvent.message, oldMessage, message);
	}

	/**
	 * Sets the message of this {@link Constraint} to null.
	 */
	public void unsetMessage() {
		setMessage((XMLNode) null);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.AbstractSBase#getParent()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ListOf<Constraint> getParent() {
		return (ListOf<Constraint>) super.getParent();
	}
}
