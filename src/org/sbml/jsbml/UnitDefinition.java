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
public class UnitDefinition extends AbstractNamedSBase {

	/**
	 * Predefined unit for area.
	 */
	public static final UnitDefinition AREA = getPredefinedUnit("area");

	/**
	 * Predefined unit for length.
	 */
	public static final UnitDefinition LENGTH = getPredefinedUnit("length");

	/**
	 * Predefined unit for substance.
	 */
	public static final UnitDefinition SUBSTANCE = getPredefinedUnit("substance");

	/**
	 * Predefined unit for time.
	 */
	public static final UnitDefinition TIME = getPredefinedUnit("time");

	/**
	 * Predefined unit for volume.
	 */
	public static final UnitDefinition VOLUME = getPredefinedUnit("volume");

	/**
	 * 
	 * @param id
	 * @return
	 */
	private static final UnitDefinition getPredefinedUnit(String id) {
		id = id.toLowerCase();
		Unit u = new Unit(2, 4);
		UnitDefinition ud = new UnitDefinition(id, 2, 4);
		String name = "Predefined unit ";
		if (id.equals("substance")) {
			u.setKind(Unit.Kind.MOLE);
			name += "mole";
		} else if (id.equals("volume")) {
			u.setKind(Unit.Kind.LITRE);
			name += "litre";
		} else if (id.equals("area")) {
			u.setKind(Unit.Kind.METRE);
			u.setExponent(2);
			name += "square metre";
		} else if (id.equals("length")) {
			u.setKind(Unit.Kind.METRE);
			name += "metre";
		} else if (id.equals("time")) {
			u.setKind(Unit.Kind.SECOND);
			u.setSBOTerm(345);
			ud.setSBOTerm(345);
			name += "second";
		} else
			throw new IllegalArgumentException(
					"no predefined unit available for " + id);
		ud.setName(name);
		ud.addUnit(u);
		return ud;
	}

	/**
	 * 
	 */
	private ListOf<Unit> listOfUnits;

	/**
	 * 
	 * @param id
	 */
	public UnitDefinition(String id, int level, int version) {
		super(id, level, version);
		initDefaults();
	}

	/**
	 * 
	 * @param id
	 * @param name
	 */
	public UnitDefinition(String id, String name, int level, int version) {
		super(id, name, level, version);
		initDefaults();
	}

	/**
	 * 
	 * @param nsb
	 */
	public UnitDefinition(UnitDefinition nsb) {
		super(nsb);
		listOfUnits = nsb.getListOfUnits().clone();
		setThisAsParentSBMLObject(listOfUnits);
	}

	/**
	 * 
	 * @param u
	 */
	public void addUnit(Unit u) {
		listOfUnits.add(u);
		u.parentSBMLObject = this;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.AbstractSBase#clone()
	 */
	// @Override
	public UnitDefinition clone() {
		return new UnitDefinition(this);
	}

	/**
	 * Devides this unit definition by the second unit definition.
	 * @param definition
	 */
	public UnitDefinition divideBy(UnitDefinition definition) {
		for (Unit u : definition.getListOfUnits()) {
			Unit unit = u.clone();
			unit.setExponent(u.getExponent() * -1);
			addUnit(unit);
		}
		return this;
	}

	/**
	 * 
	 * @return
	 */
	public ListOf<Unit> getListOfUnits() {
		return listOfUnits;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumUnits() {
		return listOfUnits.size();
	}

	/**
	 * 
	 */
	private void initDefaults() {
		listOfUnits = new ListOf<Unit>(getLevel(), getVersion());
		setThisAsParentSBMLObject(listOfUnits);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isVariantOfArea() {
		if (listOfUnits.size() == 1)
			return listOfUnits.getFirst().isVariantOfArea();
		return false;
	}

	/**
	 * Convenience function for testing if a given unit definition is a variant
	 * of the predefined unit identifier 'length'.
	 * 
	 * @param two
	 * @return true if this UnitDefinition is a variant of the predefined unit
	 *         length, meaning metres with only abritrary variations in scale or
	 *         multiplier values; false otherwise.
	 */
	public boolean isVariantOfLength() {
		if (listOfUnits.size() == 1)
			return listOfUnits.getFirst().isVariantOfLength();
		return false;
	}

	/**
	 * Convenience function for testing if a given unit definition is a variant
	 * of the predefined unit identifier 'substance'.
	 * 
	 * @return true if this UnitDefinition is a variant of the predefined unit
	 *         substance, meaning moles or items (and grams or kilograms from
	 *         SBML Level 2 Version 2 onwards) with only abritrary variations in
	 *         scale or multiplier values; false otherwise.
	 */
	public boolean isVariantOfSubstance() {
		if (listOfUnits.size() == 1)
			return listOfUnits.getFirst().isVariantOfSubstance();
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isVariantOfSubstancePerArea() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isVariantOfSubstancePerLength() {
		if (listOfUnits.size() == 2) {
			if (listOfUnits.get(0).isVariantOfSubstance()) {
				Unit two = listOfUnits.get(1).clone();
				two.setExponent(two.getExponent() * -1);
				return two.isVariantOfLength();
			} else if (listOfUnits.get(1).isVariantOfSubstance()) {
				Unit one = listOfUnits.get(0).clone();
				one.setExponent(one.getExponent() * -1);
				return one.isVariantOfLength();
			}
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isVariantOfSubstancePerVolume() {
		if (listOfUnits.size() == 2) {
			if (listOfUnits.get(0).isVariantOfSubstance()) {
				Unit two = listOfUnits.get(1).clone();
				two.setExponent(two.getExponent() * -1);
				return two.isVariantOfVolume();
			} else if (listOfUnits.get(1).isVariantOfSubstance()) {
				Unit one = listOfUnits.get(0).clone();
				one.setExponent(one.getExponent() * -1);
				return one.isVariantOfVolume();
			}
		}
		return false;
	}

	/**
	 * Convenience function for testing if a given unit definition is a variant
	 * of the predefined unit identifier 'volume'.
	 * 
	 * @return true if this UnitDefinition is a variant of the predefined unit
	 *         volume, meaning litre or cubic metre with only abritrary
	 *         variations in scale or multiplier values; false otherwise.
	 */
	public boolean isVariantOfVolume() {
		if (listOfUnits.size() == 1)
			return listOfUnits.getFirst().isVariantOfVolume();
		return false;
	}

	/**
	 * 
	 * @param listOfUnits
	 */
	public void setListOfUnits(ListOf<Unit> listOfUnits) {
		this.listOfUnits = listOfUnits;
		setThisAsParentSBMLObject(listOfUnits);
		stateChanged();
	}

}
