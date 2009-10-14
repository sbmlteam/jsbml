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

import org.sbml.jsbml.Unit.Kind;

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
	 * Returns a string that expresses the unit definition represented by this
	 * UnitDefinition object.
	 * 
	 * @param ud
	 *            the UnitDefinition object
	 * @return a string expressing the unit definition
	 */
	public static String printUnits(UnitDefinition ud) {
		return printUnits(ud, false);
	}

	/**
	 * Returns a string that expresses the unit definition represented by this
	 * UnitDefinition object.
	 * 
	 * @param ud
	 *            the UnitDefinition object
	 * @param compact
	 *            boolean indicating whether the compact form should be used
	 *            (defaults to false)
	 * @return a string expressing the unit definition
	 */
	public static String printUnits(UnitDefinition ud, boolean compact) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ud.getNumUnits(); i++) {
			Unit unit = ud.getUnit(i);
			if (i > 0)
				sb.append(' ');
			if (compact) {
				sb.append(unit.toString());
			} else {
				sb.append(unit.getKind().getName().toLowerCase());
				sb.append(" (exponent = ");
				sb.append(unit.getExponent());
				sb.append(", multiplier = ");
				sb.append(unit.getMultiplier());
				sb.append(", scale = ");
				sb.append(unit.getScale());
				sb.append(')');
			}
		}
		return sb.toString();
	}

	/**
	 * Orders alphabetically the Unit objects within the ListOfUnits of a
	 * UnitDefinition.
	 * 
	 * @param ud
	 *            the UnitDefinition object whose units are to be reordered.
	 */
	public static void reorder(UnitDefinition ud) {
		ListOf<Unit> units = new ListOf<Unit>(ud.getLevel(), ud.getVersion());
		ListOf<Unit> orig = ud.getListOfUnits();
		int i, j;
		for (i = orig.size() - 1; i >= 0; i--) {
			Unit u = orig.remove(i);
			for (j = 0; j < units.size()
					&& u.getKind().compareTo(units.get(j).getKind()) > 0; j++)
				;
			units.add(j, u);
		}
		ud.setListOfUnits(units);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	private static final UnitDefinition getPredefinedUnit(String id) {
		id = id.toLowerCase();
		Unit u = new Unit(2, 4);
		UnitDefinition ud = new UnitDefinition(id, 2, 4);
		if (id.equals("substance")) {
			u.setKind(Unit.Kind.MOLE);
		} else if (id.equals("volume")) {
			u.setKind(Unit.Kind.LITRE);
		} else if (id.equals("area")) {
			u.setKind(Unit.Kind.METRE);
			u.setExponent(2);
		} else if (id.equals("length")) {
			u.setKind(Unit.Kind.METRE);
		} else if (id.equals("time")) {
			u.setKind(Unit.Kind.SECOND);
			u.setSBOTerm(345);
			ud.setSBOTerm(345);
		} else
			throw new IllegalArgumentException(
					"no predefined unit available for " + id);
		ud.setName("Predefined unit " + id);
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
	 * 
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
	 * Returns a specific Unit instance belonging to this UnitDefinition.
	 * 
	 * @param i
	 *            an integer, the index of the Unit to be returned.
	 * @return the ith Unit of this UnitDefinition
	 */
	public Unit getUnit(int i) {
		return listOfUnits.get(i);
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
		if (listOfUnits.size() == 2) {
			if (getUnit(0).isVariantOfSubstance()) {
				Unit two = getUnit(1).clone();
				two.setExponent(two.getExponent() * -1);
				return two.isVariantOfArea();
			} else if (getUnit(1).isVariantOfSubstance()) {
				Unit one = getUnit(0).clone();
				one.setExponent(one.getExponent() * -1);
				return one.isVariantOfArea();
			}
		}
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
	 * Multiplies this unit with the given unit definition, i.e., adds a clone
	 * of each unit object in the list of units of the given definition to the
	 * list of this unit.
	 * 
	 * @param definition
	 * @return
	 */
	public UnitDefinition multiplyWith(UnitDefinition definition) {
		for (Unit u : definition.getListOfUnits())
			addUnit(u.clone());
		return this;
	}

	/**
	 * Raises this unit definition by the power of the given exponent, i.e., the
	 * exponents of every unit contained by this unit definition are multiplied
	 * with the given exponent.
	 * 
	 * @param exponent
	 */
	public void raiseByThePowerOf(int exponent) {
		Unit u;
		for (int i = listOfUnits.size() - 1; i >= 0; i--) {
			u = listOfUnits.get(i);
			u.setExponent(u.getExponent() + exponent);
			if (u.getExponent() == 0)
				listOfUnits.remove(i);
		}
	}

	/**
	 * Removes the nth Unit object from this UnitDefinition object and returns a
	 * pointer to it.
	 * 
	 * The caller owns the returned object and is responsible for deleting it.
	 * 
	 * @param i
	 *            the index of the Unit object to remove
	 * @return the Unit object removed. As mentioned above, the caller owns the
	 *         returned item. NULL is returned if the given index is out of
	 *         range.
	 */
	public Unit removeUnit(int i) {
		Unit u = listOfUnits.remove(i);
		if (u != null)
			u.sbaseRemoved();
		return u;
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

	/**
	 * Simplifies the UnitDefinition so that any Unit objects occurring within
	 * the ListOfUnits occurs only once.
	 * 
	 * @return a pointer to the simplified unit definition.
	 */
	public UnitDefinition simplify() {
		reorder(this);
		for (int i = getNumUnits() - 2; i >= 0; i--) {
			Unit u = getUnit(i); // current unit
			Unit.Kind s = getUnit(i + 1).getKind(); // successor
			if (u.getKind() == s
					|| (u.getKind() == Kind.METER && s == Kind.METRE)
					|| (u.getKind() == Kind.LITER && s == Kind.LITRE)) {
				Unit.merge(u, removeUnit(i + 1));
				if (u.getExponent() == 0)
					removeUnit(i);
			}
		}
		return this;
	}

	/**
	 * 
	 */
	private void initDefaults() {
		listOfUnits = new ListOf<Unit>(getLevel(), getVersion());
		setThisAsParentSBMLObject(listOfUnits);
	}
}
