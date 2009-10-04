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
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * 
 */
public class Compartment extends Symbol {

	/**
	 * 
	 */
	private CompartmentType compartmentType;
	/**
	 * 
	 */
	private boolean constant;
	/**
	 * 
	 */
	private Compartment outside;
	/**
	 * 
	 */
	private short spatialDimensions;

	/**
	 * 
	 * @param compartment
	 */
	public Compartment(Compartment compartment) {
		super(compartment);
		this.compartmentType = compartment.getCompartmentTypeInstance();
		this.spatialDimensions = compartment.getSpatialDimensions();
		this.outside = compartment.getOutsideInstance();
		this.constant = compartment.getConstant();
		setValue(compartment.getSize());
	}

	public Compartment(String id, int level, int version) {
		super(id, level, version);
		initDefaults();
	}

	public Compartment(String id, String name, int level, int version) {
		super(id, name, level, version);
		initDefaults();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.SBase#clone()
	 */
	// @Override
	public Compartment clone() {
		return new Compartment(this);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.NamedSBase#equals(java.lang.Object)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof Compartment) {
			boolean equal = super.equals(o);
			Compartment c = (Compartment) o;
			equal &= c.getConstant() == getConstant();
			equal &= c.isSetOutside() == isSetOutside();
			if (c.isSetOutside() && isSetOutside())
				equal &= c.getOutside().equals(getOutside());
			equal &= c.isSetCompartmentType() == isSetCompartmentType();
			if (c.isSetCompartmentType() && isSetCompartmentType())
				equal &= c.getCompartmentType().equals(getCompartmentType());
			equal &= c.getSize() == getSize();
			equal &= c.getSpatialDimensions() == getSpatialDimensions();
			return equal;
		}
		return false;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getCompartmentType() {
		return isSetCompartmentType() ? compartmentType.getId() : "";
	}
	/**
	 * 
	 * @return
	 */
	public CompartmentType getCompartmentTypeInstance() {
		return compartmentType;
	}
	/**
	 * 
	 * @return
	 */
	public boolean getConstant() {
		return isConstant();
	}

	/**
	 * 
	 * @return
	 */
	public String getOutside() {
		return isSetOutside() ? outside.getId() : "";
	}

	/**
	 * 
	 * @return
	 */
	public Compartment getOutsideInstance() {
		return outside;
	}

	/**
	 * 
	 * @return
	 */
	public double getSize() {
		return getValue();
	}

	/**
	 * 
	 * @return
	 */
	public short getSpatialDimensions() {
		return spatialDimensions;
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

	/**
	 *(For SBML Level 1) Get the volume of this Compartment
	 * 
	 * This method is identical to getSize(). In SBML Level 1, compartments are
	 * always three-dimensional constructs and only have volumes, whereas in
	 * SBML Level 2, compartments may be other than three-dimensional and
	 * therefore the 'volume' attribute is named 'size' in Level 2. LibSBML
	 * provides both getSize() and getVolume() for easier compatibility between
	 * SBML Levels.
	 * 
	 * @return
	 */
	public double getVolume() {
		return getSize();
	}

	/**
	 * 
	 */
	public void initDefaults() {
		compartmentType = null;
		spatialDimensions = 3;
		constant = true;
		outside = null;
		setSize(Double.NaN);
	}

	/**
	 * 
	 * @return
	 */
	public boolean isConstant() {
		return constant;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isSetCompartmentType() {
		return compartmentType != null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetOutside() {
		return outside != null;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetSize() {
		return !Double.isNaN(getSize());
	}

	/*
	 * (non-Javadoc)
	 * @see org.sbml.jsbml.Symbol#isSetUnits()
	 */
	public boolean isSetUnits() {
		return super.isSetUnits();
	}

	/**
	 * 
	 * @param compartmentType
	 */
	public void setCompartmentType(CompartmentType compartmentType) {
		this.compartmentType = compartmentType;
	}

	/**
	 * 
	 * @param constant
	 */
	public void setConstant(boolean constant) {
		this.constant = constant;
	}

	/**
	 * 
	 * @param outside
	 */
	public void setOutside(Compartment outside) {
		this.outside = outside;
	}

	/**
	 * 
	 * @param size
	 */
	public void setSize(double size) {
		setValue(size);
	}

	/**
	 * 
	 * @param spatialDimensions
	 */
	public void setSpatialDimensions(short spatialDimensions) {
		if (spatialDimensions >= 0 && spatialDimensions <= 3)
			this.spatialDimensions = spatialDimensions;
		else
			throw new IllegalArgumentException(
					"Spatial dimensions must be between [0, 3].");
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
}
