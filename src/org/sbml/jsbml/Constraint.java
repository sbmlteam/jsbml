/*
 * $Id: Constraint.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/Constraint.java $
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
 * Represents the constraint XML element of a SBML file.
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 * @author marine
 * 
 */
public class Constraint extends MathContainer {

	/**
	 * Represents the subnode message of a constraint element.
	 */
	private String message;
	
	/**
	 * contains the message of this Constraint as a StringBuffer.
	 */
	private StringBuffer messageBuffer;

	/**
	 * Creates a Constraint instance. By default, the message and messageBuffer are null.
	 */
	public Constraint() {
		super();
		this.message = null;
		this.setMessageBuffer(null);
	}
	
	/**
	 * Creates a Constraint instance from a level and a version. By default, the message and messageBuffer are null.
	 * @param level
	 * @param version
	 */
	public Constraint(int level, int version) {
		super(level, version);
		this.message = null;
		this.messageBuffer = null;
	}

	/**
	 * Creates a Constraint instance from an ASTNode, a level and a version. By default, the message and messageBuffer are null.
	 * @param math
	 * @param level
	 * @param version
	 */
	public Constraint(ASTNode math, int level, int version) {
		super(math, level, version);
		message = null;
		this.messageBuffer = null;
	}

	/**
	 * Creates a Constraint instance from a given Constraint.
	 * @param sb
	 */
	public Constraint(Constraint sb) {
		super(sb);
		if (sb.isSetMessage()){
			this.message = new String(sb.getMessage());
		}
		if (sb.isSetMessageBuffer()){
			this.messageBuffer = new StringBuffer(sb.getMessageBuffer());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.MathContainer#clone()
	 */
	// @Override
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
			if (equal && isSetMessage()){
				equal &= c.getMessage().equals(getMessage());
			}
			equal &= c.isSetMessageBuffer() == isSetMessageBuffer();
			if (equal && isSetMessageBuffer()){
				equal &= c.getMessageBuffer().equals(getMessageBuffer());
			}
			return equal;
		}
		return false;
	}

	/**
	 * @return the message of this Constraint. Return an empty String if the message is not set.
	 */
	public String getMessage() {
		return isSetMessage() ? message : "";
	}

	/**
	 * Sets the message of this Constraint to 'message'.
	 * @param message : the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
		stateChanged();
	}

	/**
	 * 
	 * @return true if the message of this Constraint is not null.
	 */
	public boolean isSetMessage() {
		return message != null;
	}
	
	/**
	 * Sets the messageBuffer of this Constraint to 'messageBuffer'.
	 * @param messageBuffer
	 */
	public void setMessageBuffer(StringBuffer messageBuffer) {
		this.messageBuffer = messageBuffer;
		stateChanged();
	}

	/**
	 * 
	 * @return ths messageBuffer of this Constraint.
	 */
	public StringBuffer getMessageBuffer() {
		return messageBuffer;
	}
	
	/**
	 * 
	 * @return true if the messageBuffer of this Constraint is not null.
	 */
	public boolean isSetMessageBuffer(){
		return this.messageBuffer != null;
	}
	
	/**
	 * Sets the message of this Constraint to null.
	 */
	public void unsetMessage(){
		this.message = null;
	}
	
	/**
	 * sets the messageBuffer of this Constraint to null.
	 */
	public void unsetMessageBuffer(){
		this.messageBuffer = null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		return isAttributeRead;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		
		return attributes;
	}
}
