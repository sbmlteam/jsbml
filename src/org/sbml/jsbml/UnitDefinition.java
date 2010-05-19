/*
 * $Id$
 * $URL$
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

package org.sbml.jsbml;

import java.util.HashSet;
import java.util.Set;

import org.sbml.jsbml.ListOf.Type;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.util.StringTools;

/**
 * Represents the unitDefinition XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author marine
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 * @composed 1..* ListOf 1 Unit
 */
public class UnitDefinition extends AbstractNamedSBase {

	/**
	 * Predefined unit for area.
	 */
	public static final UnitDefinition area(int level, int version) {
		return getPredefinedUnit("area", level, version);
	}

	/**
	 * <p>
	 * Predicate returning true or false depending on whether two UnitDefinition
	 * objects are equivalent.
	 * </p>
	 * <p>
	 * For the purposes of performing this comparison, two UnitDefinition
	 * objects are considered equivalent when they contain equivalent list of
	 * Unit objects. Unit objects are in turn considered equivalent if they
	 * satisfy the predicate Unit.areEquivalent(). The predicate tests a subset
	 * of the objects's attributes.
	 * </p>
	 * 
	 * @param ud1
	 *            the first UnitDefinition object to compare
	 * @param ud2
	 *            the second UnitDefinition object to compare
	 * @return true if all the Unit objects in ud1 are equivalent to the Unit
	 *         objects in ud2, false otherwise.
	 * @see areIdentical
	 * @see Unit.areEquivalent
	 */
	public static boolean areEquivalent(UnitDefinition ud1, UnitDefinition ud2) {
		UnitDefinition ud1clone = ud1.clone().simplify();
		UnitDefinition ud2clone = ud2.clone().simplify();
		if (ud1clone.getNumUnits() == ud2clone.getNumUnits()) {
			boolean equivalent = true;
			for (int i = 0; i < ud1clone.getNumUnits(); i++) {
				equivalent &= Unit.areEquivalent(ud1clone.getUnit(i), ud2clone
						.getUnit(i));
			}
			return equivalent;
		}
		return false;
	}

	/**
	 * <p>
	 * Predicate returning true or false depending on whether two UnitDefinition
	 * objects are identical.
	 * </p>
	 * <p>
	 * For the purposes of performing this comparison, two UnitDefinition
	 * objects are considered identical when they contain identical lists of
	 * Unit objects. Pairs of Unit objects in the lists are in turn considered
	 * identical if they satisfy the predicate Unit.areIdentical(). The
	 * predicate compares every attribute of the Unit objects.
	 * </p>
	 * 
	 * @param ud1
	 *            the first UnitDefinition object to compare
	 * @param ud2
	 *            the second UnitDefinition object to compare
	 * @return true if all the Unit objects in ud1 are identical to the Unit
	 *         objects of ud2, false otherwise.
	 */
	public static boolean areIdentical(UnitDefinition ud1, UnitDefinition ud2) {
		UnitDefinition ud1clone = ud1.clone().simplify();
		UnitDefinition ud2clone = ud2.clone().simplify();
		if (ud1clone.getNumUnits() == ud2clone.getNumUnits()) {
			boolean identical = true;
			for (int i = 0; i < ud1clone.getNumUnits(); i++) {
				identical &= Unit.areIdentical(ud1clone.getUnit(i), ud2clone
						.getUnit(i));
			}
			return identical;
		}
		return false;
	}

	/**
	 * This method returns the predefined unit with the given identifier for the
	 * specified level and version combination or null if either for the given
	 * combination of level and version there is no such predefined unit or the
	 * identifier is not one of those belonging to the group of predefined unit
	 * definitions.
	 * 
	 * @param id
	 *            one of the values
	 *            <ul>
	 *            <li>substance</li>
	 *            <li>volume</li>
	 *            <li>area</li>
	 *            <li>length</li>
	 *            <li>time</li>
	 *            </ul>
	 * @param level
	 *            a number greater than zero.
	 * @param version
	 *            a number greater than zero.
	 * @return The predefined unit definition with the given identifier for the
	 *         specified level version combination or null if no such predefined
	 *         unit exists.
	 */
	private static final UnitDefinition getPredefinedUnit(String id, int level,
			int version) {
		if (level < 3) {
			id = id.toLowerCase();
			Unit u = new Unit(level, version);
			UnitDefinition ud = new UnitDefinition(id, level, version);
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
			} else {
				return null;
			}
			ud.setName("Predefined unit " + id);
			ud.addUnit(u);
			return ud;
		}
		return null;
	}

	/**
	 * Test if the given unit is a predefined unit.
	 * 
	 * @param ud
	 * @deprecated use isPredefined
	 */
	@Deprecated
	public static boolean isBuiltIn(UnitDefinition ud) {
		return isPredefined(ud);
	}

	/**
	 * Test if the given unit is a predefined unit.
	 * 
	 * @param ud
	 * @return
	 */
	public static boolean isPredefined(UnitDefinition ud) {
		if (ud.getLevel() > 2) {
			return false;
		}
		if (ud.getNumUnits() == 1) {
			UnitDefinition predef = getPredefinedUnit(ud.getId(),
					ud.getLevel(), ud.getVersion());
			if ((predef != null) && Unit.isBuiltIn(ud.getId(), ud.getLevel())) {
				return ud.equals(predef);
			}
		}
		return false;
	}

	/**
	 * Predefined unit for length.
	 */
	public static final UnitDefinition length(int level, int version) {
		return getPredefinedUnit("length", level, version);
	}

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
		StringBuilder sb = new StringBuilder();
		if (ud == null) {
			sb.append("null");
		} else {
			for (int i = 0; i < ud.getNumUnits(); i++) {
				Unit unit = ud.getUnit(i);
				if (i > 0) {
					sb.append('*'); // multiplication dot \u22c5.
				}
				if (compact) {
					sb.append(unit.toString());
				} else {
					sb.append(unit.getKind().getName().toLowerCase());
					sb.append(String.format(
							" (exponent = %d, multiplier = %s, scale = %d)",
							unit.getExponent(), StringTools.toString(unit
									.getMultiplier()), unit.getScale()));
				}
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
		if (1 < ud.getNumUnits()) {
			ListOf<Unit> orig = ud.getListOfUnits();
			ListOf<Unit> units = new ListOf<Unit>(ud.getLevel(), ud
					.getVersion());
			units.setSBaseListType(Type.listOfUnits);
			orig.removeAllSBaseChangeListeners();
			units.add(orig.remove(orig.size() - 1));
			int i, j;
			for (i = orig.size() - 1; i >= 0; i--) {
				Unit u = orig.remove(i);
				j = 0;
				while (j < units.size()
						&& 0 < u.getKind().compareTo(units.get(j).getKind()))
					j++;
				units.add(j, u);
			}
			ud.setListOfUnits(units);
		}
	}

	/**
	 * Predefined unit for substance.
	 */
	public static final UnitDefinition substance(int level, int version) {
		return getPredefinedUnit("substance", level, version);
	}

	/**
	 * Predefined unit for time.
	 */
	public static final UnitDefinition time(int level, int version) {
		return getPredefinedUnit("time", level, version);
	}

	/**
	 * Predefined unit for volume.
	 */
	public static final UnitDefinition volume(int level, int version) {
		return getPredefinedUnit("volume", level, version);
	}

	/**
	 * Represents the 'listOfUnit' XML subelement of a UnitDefinition.
	 */
	private ListOf<Unit> listOfUnits;

	/**
	 * Creates an UnitDefinition instance. By default, the listOfUnit is null.
	 */
	public UnitDefinition() {
		super();
		this.listOfUnits = null;
		if (isSetLevel() && getLevel() < 3) {
			initDefaults();
		}
	}

	/**
	 * 
	 * @param level
	 * @param version
	 */
	public UnitDefinition(int level, int version) {
		super(level, version);
		initDefaults();
	}

	/**
	 * Creates an UnitDefinition instance from an id, level and version. By
	 * default, the listOfUnit is null.
	 * 
	 * @param id
	 * @param level
	 * @param version
	 */
	public UnitDefinition(String id, int level, int version) {
		super(id, level, version);
		initDefaults();
	}

	/**
	 * Creates an UnitDefinition instance from an id, level and version. By
	 * default, the listOfUnit is null.
	 * 
	 * @param id
	 * @param name
	 * @param level
	 * @param version
	 */
	public UnitDefinition(String id, String name, int level, int version) {
		super(id, name, level, version);
		initDefaults();
	}

	/**
	 * Creates an UnitDefinition instance from a given UnitDefinition.
	 * 
	 * @param nsb
	 */
	public UnitDefinition(UnitDefinition nsb) {
		super(nsb);
		initDefaults();
		if (nsb.isSetListOfUnits()) {
			setListOfUnits((ListOf<Unit>) nsb.getListOfUnits().clone());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.sbml.jsbml.AbstractSBase#addChangeListener(org.sbml.jsbml.
	 * SBaseChangedListener)
	 */
	@Override
	public void addChangeListener(SBaseChangedListener l) {
		super.addChangeListener(l);
		if (!isSetListOfUnits()) {
			initListOfUnits();
		}
		listOfUnits.addChangeListener(l);
	}

	/**
	 * Adds an Unit to this UnitDefinition.
	 * 
	 * @param u
	 */
	public void addUnit(Unit u) {
		if (!isSetListOfUnits()) {
			initListOfUnits();
		}
		setThisAsParentSBMLObject(u);
		listOfUnits.add(u);
		u.parentSBMLObject = this;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.AbstractSBase#clone()
	 */
	@Override
	public UnitDefinition clone() {
		return new UnitDefinition(this);
	}

	/**
	 * This method converts this unit definition to a
	 */
	public void convertToSIUnits() {
		UnitDefinition ud[] = new UnitDefinition[getNumUnits()];
		Set<SBaseChangedListener> listeners = new HashSet<SBaseChangedListener>(
				getSetOfSBaseChangeListeners());
		removeAllSBaseChangeListeners();
		for (int i = ud.length - 1; i >= 0; i--) {
			ud[i] = Unit.convertToSI(removeUnit(i));
		}
		for (UnitDefinition u : ud) {
			getListOfUnits().addAll(u.getListOfUnits());
		}
		simplify();
		addAllChangeListeners(listeners);
		stateChanged();
	}

	/**
	 * 
	 * @return
	 */
	public Unit createUnit() {
		return createUnit(Kind.INVALID);
	}

	/**
	 * 
	 * @param kind
	 * @return
	 */
	public Unit createUnit(Kind kind) {
		Unit unit = new Unit(kind, getLevel(), getVersion());
		addUnit(unit);

		return unit;
	}

	/**
	 * Devides this unit definition by the second unit definition.
	 * 
	 * @param definition
	 */
	public UnitDefinition divideBy(UnitDefinition definition) {
		if (definition.isSetListOfUnits()) {
			for (Unit unit1 : definition.getListOfUnits()) {
				Unit unit = unit1.clone();
				unit.setExponent(unit1.getExponent() * -1);
				addUnit(unit);
			}
		}
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#equals(Object o)
	 */
	public boolean equals(Object o) {
		if (o instanceof UnitDefinition) {
			UnitDefinition u = (UnitDefinition) o;
			boolean equal = super.equals(o);
			equal &= isSetListOfUnits() == u.isSetListOfUnits();
			if (equal && isSetListOfUnits()) {
				equal &= getListOfUnits().equals(u.getListOfUnits());
			}
			return equal;
		}
		return false;
	}

	/**
	 * 
	 * @return the listOfUnits of this UnitDefinition. Can be null if it is not
	 *         set.
	 */
	public ListOf<Unit> getListOfUnits() {
		return listOfUnits;
	}

	/**
	 * 
	 * @return the number of Unit.
	 */
	public int getNumUnits() {
		return isSetListOfUnits() ? listOfUnits.size() : 0;
	}

	/**
	 * Returns a specific Unit instance belonging to this UnitDefinition.
	 * 
	 * @param i
	 *            an integer, the index of the Unit to be returned.
	 * @return the ith Unit of this UnitDefinition
	 */
	public Unit getUnit(int i) {
		return getListOfUnits().get(i);
	}

	/**
	 * Initializes the default values of this UnitDefinition.
	 */
	public void initDefaults() {
		if (!isSetListOfUnits()) {
			initListOfUnits();
			setThisAsParentSBMLObject(listOfUnits);
		}
	}

	/**
	 * 
	 */
	private void initListOfUnits() {
		this.listOfUnits = new ListOf<Unit>(getLevel(), getVersion());
		setThisAsParentSBMLObject(this.listOfUnits);
		this.listOfUnits.setSBaseListType(Type.listOfUnits);
	}

	/**
	 * This method tests if this unit definition is a predefined unit.
	 * 
	 * @return
	 * @deprecated use isPredefined()
	 */
	@Deprecated
	public boolean isBuiltIn() {
		return isBuiltIn(this);
	}

	/**
	 * This method tests if this unit definition is a predefined unit.
	 * 
	 * @return
	 */
	public boolean isPredefined() {
		return isPredefined(this);
	}

	/**
	 * 
	 * @return true if the listOfUnits is not null.
	 */
	public boolean isSetListOfUnits() {
		return this.listOfUnits != null;
	}

	/**
	 * 
	 * @return true if this UnitDefinition is a variant of Area
	 */
	public boolean isVariantOfArea() {
		if (isSetListOfUnits()) {
			if (listOfUnits.size() == 1) {
				Unit unit = listOfUnits.get(0);
				return unit.isVariantOfArea();
			}
		}
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
		if (isSetListOfUnits()) {
			if (listOfUnits.size() == 1) {
				Unit unit = listOfUnits.get(0);
				return unit.isVariantOfLength();
			}
		}
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
		if (isSetListOfUnits()) {
			if (listOfUnits.size() == 1) {
				Unit unit = listOfUnits.get(0);
				return unit.isVariantOfSubstance();
			}
		}
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isVariantOfSubstancePerArea() {
		if (isSetListOfUnits()) {
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
		}
		return false;
	}

	/**
	 * 
	 * @return true if this UnitDefinition is a variant of substance per length.
	 */
	public boolean isVariantOfSubstancePerLength() {
		if (isSetListOfUnits()) {
			if (listOfUnits.size() == 2) {
				Unit unit = listOfUnits.get(0);
				Unit unit2 = listOfUnits.get(1);
				if (unit.isVariantOfSubstance()) {
					Unit two = listOfUnits.get(1).clone();
					two.setExponent(two.getExponent() * -1);
					return two.isVariantOfLength();
				} else if (unit2.isVariantOfSubstance()) {
					Unit one = listOfUnits.get(0).clone();
					one.setExponent(one.getExponent() * -1);
					return one.isVariantOfLength();
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @return true if this UnitDefinition is a variant of substance per volume.
	 */
	public boolean isVariantOfSubstancePerVolume() {
		if (isSetListOfUnits()) {
			if (listOfUnits.size() == 2) {
				Unit unit = listOfUnits.get(0);
				Unit unit2 = listOfUnits.get(1);
				if (unit.isVariantOfSubstance()) {
					Unit two = listOfUnits.get(1).clone();
					two.setExponent(-two.getExponent());
					return two.isVariantOfVolume();
				} else if (unit2.isVariantOfSubstance()) {
					Unit one = listOfUnits.get(0).clone();
					one.setExponent(-one.getExponent());
					return one.isVariantOfVolume();
				}
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
		if (isSetListOfUnits()) {
			if (listOfUnits.size() == 1) {
				Unit unit = listOfUnits.get(0);
				return unit.isVariantOfVolume();
			}
		}
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
		if (isSetListOfUnits()) {
			for (Unit unit : definition.getListOfUnits()) {
				addUnit(unit.clone());
			}
		}
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
		if (isSetListOfUnits()) {
			Unit u;
			for (int i = listOfUnits.size() - 1; i >= 0; i--) {
				u = listOfUnits.get(i);
				u.setExponent(u.getExponent() * exponent);
				if (u.getExponent() == 0) {
					listOfUnits.remove(i);
				}
			}
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
		if (isSetListOfUnits()) {
			Unit u = listOfUnits.remove(i);
			if (u != null) {
				u.sbaseRemoved();
			}
			return u;
		}
		return null;
	}

	/**
	 * Sets the listOfUnits of this UnitDefinition. Automatically sets the
	 * parentSBML object of the list to this UnitDefinition instance.
	 * 
	 * @param listOfUnits
	 */
	public void setListOfUnits(ListOf<Unit> listOfUnits) {
		this.listOfUnits = listOfUnits;
		setThisAsParentSBMLObject(this.listOfUnits);
		this.listOfUnits.setSBaseListType(ListOf.Type.listOfUnits);
		stateChanged();
	}

	/**
	 * Simplifies the UnitDefinition so that any Unit objects occurring within
	 * the ListOfUnits occurs only once.
	 * 
	 * @return a pointer to the simplified unit definition.
	 */
	public UnitDefinition simplify() {
		if (isSetListOfUnits()) {
			reorder(this);
			for (int i = getNumUnits() - 2; i >= 0; i--) {
				Unit u = getUnit(i); // current unit
				Unit s = getUnit(i + 1); // successor unit
				if (Unit.Kind.areEquivalent(u.getKind(), s.getKind())
						|| u.isDimensionless() || s.isDimensionless()) {
					Unit.merge(u, removeUnit(i + 1));
					if (u.isDimensionless() && i == 0 && getNumUnits() > 1) {
						Unit.merge(getUnit(i + 1), removeUnit(i));
					}
				}
			}
		}
		return this;
	}
}
