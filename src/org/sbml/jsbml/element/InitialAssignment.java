/*
 * $Id: InitialAssignment.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/InitialAssignment.java $
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
 * Represents the initialAssignment XML element of a SBML file.
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * @author marine
 */
public class InitialAssignment extends MathContainer {

	/**
	 * Represents the 'symbol' XML attribute of an initialAssignmnent element. 
	 */
	private String symbolID;
	
	/**
	 * Creates an InitialAssignment instance. By default, symbolID is null.
	 */
	public InitialAssignment() {
		super();
		this.symbolID = null;
	}

	
	/**
	 * Creates an InitialAssignment instance from a given InitialAssignment.
	 * @param sb
	 */
	public InitialAssignment(InitialAssignment sb) {
		super(sb);
		if (sb.isSetSymbol()){
			this.symbolID = new String(sb.getSymbol());
		}
		else {
			this.symbolID = null;
		}
	}

	/**
	 * Creates an InitialAssignment instance from a Symbol.
	 * Takes level and version from the given symbol.
	 * @param symbol
	 */
	public InitialAssignment(Symbol symbol) {
		super(symbol.getLevel(), symbol.getVersion());
		if (symbol.isSetId()){
			this.symbolID = new String(symbol.getId());
		}
		else {
			this.symbolID = null;
		}
	}

	/**
	 * Creates an InitialAssignment from a Symbol, ASTNode, level and version.
	 * @param symbol
	 * @param math
	 * @param level
	 * @param version
	 */
	public InitialAssignment(Symbol symbol, ASTNode math, int level, int version) {
		super(math, level, version);
		if (symbol.isSetId()){
			this.symbolID = new String(symbol.getId());
		}
		else {
			this.symbolID = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.MathContainer#clone()
	 */
	// @Override
	public InitialAssignment clone() {
		return new InitialAssignment(this);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.MathContainer#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof InitialAssignment){
			InitialAssignment in = (InitialAssignment) o;
			
			boolean equals = super.equals(in);
			if (equals && in.isSetSymbol()){
				equals = getSymbol().equals(in.getSymbol());
			}
		}
		return false;
	}

	/**
	 * @return the symbolID of this InitialAssignment. Return an empty String if it is not set.
	 */
	public String getSymbol() {
		return isSetSymbol() ? symbolID : "";
	}

	/**
	 * @return the symbol instance which has the symbolID of this InitialAssignment as id. Return null
	 * if it doesn't exist.
	 */
	public Symbol getSymbolInstance() {
		if (getModel() == null){
			return null;
		}
		return getModel().findSymbol(this.symbolID);
	}

	/**
	 * 
	 * @return true if the symbol instance which has the symbolID of this InitialAssignment as id is not null.
	 */
	public boolean isSetSymbolInstance() {
		if (getModel() == null){
			return false;
		}
		return getModel().findSymbol(this.symbolID) != null;
	}
	
	/**
	 * 
	 * @return true if the symbolID of this InitialAssignment is not null.
	 */
	public boolean isSetSymbol() {
		return symbolID != null;
	}

	/**
	 * Sets the symbolID of this InitialAssignment to 'symbol'. If this symbolID doesn't match
	 * any Symbol id in Model (Compartment, Species, SpeciesReference or Parameter), an IllegalArgumentException
	 * is thrown.
	 * @param symbol: the symbol to set
	 */
	public void checkAndSetSymbol(String symbol) {
		Symbol nsb = null;
		if (getModel() != null){
			nsb = getModel().findSymbol(symbol);
		}

		if (nsb == null){
			throw new IllegalArgumentException(
			"Only the id of an existing Species, Compartments, or Parameters allowed as symbols");
		}
		setSymbol(nsb);
		stateChanged();
	}
	
	/**
	 * Sets the symbolID of this InitialAssignment to 'symbol'. 
	 * @param symbol: the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbolID = symbol;
		stateChanged();
	}

	/**
	 * Sets the symbolID of this InitialAssignment to the id of the Symbol 'symbol'.
	 * @param symbol: the symbol to set
	 */
	public void setSymbol(Symbol symbol) {
		this.symbolID = symbol != null ? symbol.getId() : null;
		stateChanged();
	}
	/**
	 * Sets the symbolID of this InitialAssignment to null.
	 */
	public void unsetSymbol(){
		this.symbolID = null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		if (!isAttributeRead){
			if (attributeName.equals("symbol")){
				this.setSymbol(value);
				return true;
			}
		}
		return isAttributeRead;
	}
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.element.SBase#writeXMLAttributes(
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();
		
		if (isSetSymbol()){
			attributes.put("symbol", getSymbol());
		}
		
		return attributes;
	}
}
