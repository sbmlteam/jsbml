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

package org.sbml.jsbml.element;

import java.util.HashMap;

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 * @date 2009-08-31
 * 
 */
public class Constraint extends MathContainer {

	/**
	 * 
	 */
	private String message;
	
	/**
	 * 
	 */
	private StringBuffer messageBuffer;

	/**
	 * 
	 */
	public Constraint() {
		super();
		this.message = null;
		this.setMessageBuffer(null);
	}
	
	/**
	 * 
	 * @param level
	 * @param version
	 */
	public Constraint(int level, int version) {
		super(level, version);
	}

	/**
	 * @param math
	 */
	public Constraint(ASTNode math, int level, int version) {
		super(math, level, version);
		message = null;
	}

	/**
	 * @param sb
	 */
	public Constraint(Constraint sb) {
		super(sb);
		this.message = new String(sb.getMessage());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.MathContainer#clone()
	 */
	// @Override
	public Constraint clone() {
		return new Constraint(this);
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return isSetMessage() ? message : "";
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
		stateChanged();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetMessage() {
		return message != null;
	}
	
	public void setMessageBuffer(StringBuffer messageBuffer) {
		this.messageBuffer = messageBuffer;
	}

	public StringBuffer getMessageBuffer() {
		return messageBuffer;
	}
	
	public boolean isSEtMessageBuffer(){
		return this.messageBuffer != null;
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
}
