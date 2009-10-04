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
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.sbml.jsbml;

/**
 * 
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public class SpeciesReference extends SimpleSpeciesReference {

	private double stoichiometry;
	private StoichiometryMath stoichiometryMath;

	/**
	 * 
	 * @param spec
	 */
	public SpeciesReference(Species spec) {
		super(spec);
		initDefaults();
	}

	/**
	 * 
	 * @param speciesReference
	 */
	public SpeciesReference(SpeciesReference speciesReference) {
		super(speciesReference);
		if (speciesReference.isSetStoichiometryMath())
			setStoichiometryMath(speciesReference.getStoichiometryMath().clone());
		else setStoichiometry(speciesReference.getStoichiometry());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#clone()
	 */
	// @Override
	public SpeciesReference clone() {
		return new SpeciesReference(this);
	}

	/**
	 * 
	 * @return
	 */
	public double getStoichiometry() {
		return stoichiometry;
	}

	/**
	 * 
	 * @return
	 */
	public StoichiometryMath getStoichiometryMath() {
		return stoichiometryMath;
	}

	/**
	 * 
	 */
	// @Override
	public void initDefaults() {
		stoichiometry = 1;
		stoichiometryMath = null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetStoichiometryMath() {
		return stoichiometryMath != null;
	}

	/**
	 * 
	 * @param stoichiometry
	 */
	public void setStoichiometry(double stoichiometry) {
		this.stoichiometry = stoichiometry;
		if (isSetStoichiometryMath())
			stoichiometryMath = null;
		stateChanged();
	}

	/**
	 * 
	 * @param math
	 */
	public void setStoichiometryMath(StoichiometryMath math) {
		this.stoichiometryMath = math;
		this.stoichiometryMath.parentSBMLObject = this;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		boolean equal = super.equals(o);
		if (o instanceof SpeciesReference) {
			SpeciesReference sr = (SpeciesReference) o;
			if ((sr.isSetStoichiometryMath() && !isSetStoichiometryMath())
					|| (!sr.isSetStoichiometryMath() && isSetStoichiometryMath()))
				return false;
			if (sr.isSetStoichiometryMath() && isSetStoichiometryMath())
				equal &= sr.getStoichiometryMath().equals(stoichiometryMath);
			else
				equal &= sr.getStoichiometry() == stoichiometry;
			return equal;
		} else
			equal = false;
		return equal;
	}

}
