/*
 *  SBMLsqueezer creates rate equations for reactions in SBML files
 *  (http://sbml.org).
 *  Copyright (C) 2009 ZBIT, University of Tübingen, Andreas Dräger
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.sbml.jsbml;

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * @date 2009-08-31
 */
public class InitialAssignment extends MathContainer {

	private Symbol symbol;

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
		return symbol.getId();
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
	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

}
