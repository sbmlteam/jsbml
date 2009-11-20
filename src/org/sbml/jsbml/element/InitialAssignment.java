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

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * @date 2009-08-31
 */
public class InitialAssignment extends MathContainer {

	private String symbolID;
	
	private Symbol symbol;

	/**
	 * 
	 */
	public InitialAssignment() {
		super();
		this.symbol = null;
		this.symbolID = null;
	}

	
	/**
	 * @param sb
	 */
	public InitialAssignment(InitialAssignment sb) {
		super(sb);
		this.symbol = sb.getSymbolInstance();
	}

	/**
	 * Takes level and version from the given symbol.
	 * @param symbol
	 */
	public InitialAssignment(Symbol symbol) {
		super(symbol.getLevel(), symbol.getVersion());
		this.symbol = symbol;
	}

	/**
	 * @param math
	 */
	public InitialAssignment(Symbol symbol, ASTNode math, int level, int version) {
		super(math, level, version);
		this.symbol = symbol;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.MathContainer#clone()
	 */
	// @Override
	public InitialAssignment clone() {
		return new InitialAssignment(this);
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbolID;
	}

	/**
	 * @return the symbol
	 */
	public Symbol getSymbolInstance() {
		return symbol;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetSymbol() {
		return symbol != null;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetSymbolID() {
		return symbolID != null;
	}

	/**
	 * @param symbol
	 *            the symbol to set
	 */
	public void setSymbol(String symbol) {
		Symbol nsb = getModel().findSymbol(symbol);
		if (nsb == null)
			throw new IllegalArgumentException(
					"Only the id of an existing Species, Compartments, or Parameters allowed as symbols");
		setSymbol(nsb);
	}
	
	/**
	 * @param symbol
	 *            the symbol to set
	 */
	public void setSymbolID(String symbol) {
		this.symbolID = symbol;
	}

	/**
	 * @param symbol
	 *            the symbol to set
	 */
	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Rule#isSpeciesConcentration()
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		if (!isAttributeRead){
			if (attributeName.equals("symbol")){
				this.setSymbolID(value);
				return true;
			}
		}
		return isAttributeRead;
	}
}
