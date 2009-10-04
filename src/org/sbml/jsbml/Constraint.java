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

}
