/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2011 jointly by the following organizations:
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
	 * Creates a {@link Constraint} instance. By default, the message is null.
	 */
	public Constraint() {
		super();
		this.message = null;
	}

	/**
	 * Creates a {@link Constraint} instance from an {@link ASTNode}, a level and a version. By
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
	 * Creates a {@link Constraint} instance from a given {@link Constraint}.
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
	 * Creates a {@link Constraint} instance from a level and a version. By default, the
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
	 * Returns the message of this {@link Constraint}. Returns null if the
	 *         message is not set.
	 * 
	 * @return the message of this {@link Constraint}. Returns null if the
	 *         message is not set.
	 */
	public XMLNode getMessage() {
		return isSetMessage() ? message : null;
	}

	/**
	 * Returns the message of this {@link Constraint} as an XML {@link String}.
	 * 
	 * @return  the message of this {@link Constraint} as an XML {@link String}.
	 */
	public String getMessageString() {
		return message.toXMLString();
	}

	/**
	 * Returns true if the message of this {@link Constraint} is not null.
	 * 
	 * @return true if the message of this {@link Constraint} is not null.
	 */
	public boolean isSetMessage() {
		return message != null;
	}

	/**
	 * Sets the message of this {@link Constraint} to 'message'.
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
	 * Sets the message of this {@link Constraint} to 'message'.
	 * 
	 * @param message
	 *            : the message to set
	 */
	public void setMessage(String message) {
		XMLNode oldMessage = this.message;
		this.message = XMLNode.convertStringToXMLNode(message);
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
