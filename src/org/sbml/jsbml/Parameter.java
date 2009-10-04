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
public class Parameter extends Symbol {

	/**
	 * 
	 * @param p
	 */
	public Parameter(Parameter p) {
		super(p);
	}

	/**
	 * 
	 * @param id
	 */
	public Parameter(String id, int level, int version) {
		super(id, level, version);
		initDefaults();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#clone()
	 */
	// @Override
	public Parameter clone() {
		return new Parameter(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof Parameter)
			return super.equals(o);
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean getConstant() {
		return isConstant();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Symbol#getUnits()
	 */
	public String getUnits() {
		return super.getUnits();
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Symbol#getUnitsInstance()
	 */
	public UnitDefinition getUnitsInstance() {
		return super.getUnitsInstance();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.Symbol#getValue()
	 */
	// @Override
	public double getValue() {
		return super.getValue();
	}

	/**
	 * 
	 */
	public void initDefaults() {
		setValue(Double.NaN);
		setConstant(true);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Symbol#isSetUnits()
	 */
	public boolean isSetUnits() {
		return super.isSetUnits();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.Symbol#isSetValue()
	 */
	// @Override
	public boolean isSetValue() {
		return super.isSetValue();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Symbol#setUnits(org.sbml.jsbml.Unit)
	 */
	public void setUnits(Unit unit) {
		super.setUnits(unit);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Symbol#setUnits(org.sbml.jsbml.Unit.Kind)
	 */
	public void setUnits(Unit.Kind unitKind) {
		super.setUnits(unitKind);
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Symbol#setUnits(org.sbml.jsbml.UnitDefinition)
	 */
	public void setUnits(UnitDefinition units) {
		super.setUnits(units);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jlibsbml.Symbol#setValue(double)
	 */
	// @Override
	public void setValue(double value) {
		super.setValue(value);
	}
}
