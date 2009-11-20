/*
 * $Id: Unit.java 38 2009-11-05 15:50:38Z niko-rodrigue $
 * $URL: https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/src/org/sbml/jsbml/Unit.java $
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

import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TextFormula;

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * @date 2009-08-31
 */
public class Unit extends AbstractSBase {

	public Unit(){
		super();
	}
	
	/**
	 * @author Andreas Dr&auml;ger <a
	 *         href="mailto:andreas.draeger@uni-tuebingen.de">
	 *         andreas.draeger@uni-tuebingen.de</a>
	 * @date 2009-08-31
	 */
	public static enum Kind {
		/**
		 * The ampere unit
		 */
		AMPERE,
		/**
		 * The becquerel unit.
		 */
		BECQUEREL,
		/**
		 * The candela unit.
		 */
		CANDELA,
		/**
		 * The Celsius unit.
		 */
		CELSIUS,
		/**
		 * The coulomb unit.
		 */
		COULOMB,
		/**
		 * A pseudo-unit indicating a dimensionless quantity. (This is in fact
		 * defined in the SBML specification.)
		 */
		DIMENSIONLESS,
		/**
		 * The farad unit.
		 */
		FARAD,
		/**
		 * The gram unit.
		 */
		GRAM,
		/**
		 * The gray unit.
		 */
		GRAY,
		/**
		 * The henry unit.
		 */
		HENRY,
		/**
		 * The hertz unit.
		 */
		HERTZ,
		/**
		 * Marker used by libSBML to indicate an invalid or unset unit.
		 */
		INVALID,
		/**
		 * A pseudo-unit representing a single 'thing'. (This is in fact defined
		 * in the SBML specification.)
		 */
		ITEM,
		/**
		 * The joule unit.
		 */
		JOULE,
		/**
		 * The katal unit.
		 */
		KATAL,
		/**
		 * The kelvin unit.
		 */
		KELVIN,
		/**
		 * The kilogram unit.
		 */
		KILOGRAM,
		/**
		 * Alternate spelling of litre.
		 */
		LITER,
		/**
		 * The litre unit.
		 */
		LITRE,
		/**
		 * The lumen unit.
		 */
		LUMEN,
		/**
		 * The lux unit.
		 */
		LUX,
		/**
		 * Alternate spelling of metre.
		 */
		METER,
		/**
		 * The metre unit.
		 */
		METRE,
		/**
		 * The mole unit.
		 */
		MOLE,
		/**
		 * The newton unit.
		 */
		NEWTON,
		/**
		 * The ohm unit.
		 */
		OHM,
		/**
		 * The pascal unit.
		 */
		PASCAL,
		/**
		 * The radian unit.
		 */
		RADIAN,
		/**
		 * The second unit.
		 */
		SECOND,
		/**
		 * The siemens unit.
		 */
		SIEMENS,
		/**
		 * The sievert unit.
		 */
		SIEVERT,
		/**
		 * The steradian unit.
		 */
		STERADIAN,
		/**
		 * The tesla unit.
		 */
		TESLA,
		/**
		 * The volt unit.
		 */
		VOLT,
		/**
		 * The watt unit.
		 */
		WATT,
		/**
		 * The weber unit.
		 */
		WEBER;

		/**
		 * Tests whether the both given unit kinds are equivalent, i.e., it also
		 * considers METRE and METER and LITRE and LITER.
		 * 
		 * @param kind1
		 * @param kind2
		 * @return
		 */
		public static boolean areEquivalent(Kind kind1, Kind kind2) {
			return kind1 == kind2 || (kind1 == METER && kind2 == METRE)
					|| (kind2 == METER && kind1 == METRE)
					|| (kind1 == LITER && kind2 == LITRE)
					|| (kind2 == LITER && kind1 == LITRE);
		}

		/**
		 * Returns the name of this unit kind.
		 * 
		 * @return
		 */
		public String getName() {
			if (this == CELSIUS)
				return "degree "
						+ StringTools.firstLetterUpperCase(toString()
								.toLowerCase());
			if (this == DIMENSIONLESS || this == GRAM || this == ITEM
					|| this == INVALID || this == KILOGRAM || this == LUX
					|| this == METER || this == METRE || this == MOLE
					|| this == SECOND)
				return toString().toLowerCase();
			else
				return StringTools.firstLetterUpperCase(toString()
						.toLowerCase());
		}

		/**
		 * Returns the formula symbol of this unit kind in uni code notation.
		 * 
		 * @return
		 */
		public String getSymbol() {
			switch (this) {
			case AMPERE:
				return "A";
			case BECQUEREL:
				return "Bq";
			case CANDELA:
				return "cd";
			case CELSIUS:
				return "\u00B0C";
			case COULOMB:
				return "C";
			case FARAD:
				return "F";
			case GRAM:
				return "g";
			case GRAY:
				return "Gy";
			case HENRY:
				return "H";
			case HERTZ:
				return "Hz";
			case JOULE:
				return "J";
			case KATAL:
				return "kat";
			case KELVIN:
				return "K";
			case KILOGRAM:
				return "kg";
			case LITER:
				return "l";
			case LITRE:
				return "l";
			case LUMEN:
				return "lm";
			case LUX:
				return "lx";
			case METER:
				return "m";
			case METRE:
				return "m";
			case MOLE:
				return "mol";
			case NEWTON:
				return "N";
			case OHM:
				return "\u03A9";
			case PASCAL:
				return "Pa";
			case RADIAN:
				return "rad";
			case SECOND:
				return "s";
			case SIEMENS:
				return "S";
			case SIEVERT:
				return "Sv";
			case STERADIAN:
				return "sr";
			case TESLA:
				return "T";
			case VOLT:
				return "V";
			case WATT:
				return "W";
			case WEBER:
				return "Wb";
			default: // DIMENSIONLESS, ITEM, INVALID:
				return toString().toLowerCase();
			}
		}

		/**
		 * Tests whether this kind of unit is defined in the given level and
		 * version of SBML.
		 * 
		 * @param level
		 * @param version
		 * @return
		 */
		public boolean isDefinedIn(int level, int version) {
			if (level == 1) {
				if (version == 1) {
					// TODO
				} else if (version == 2) {
					return this == AMPERE || this == BECQUEREL
							|| this == CANDELA || this == CELSIUS
							|| this == COULOMB || this == DIMENSIONLESS
							|| this == FARAD || this == GRAM || this == GRAY
							|| this == HENRY || this == HERTZ || this == ITEM
							|| this == JOULE || this == KATAL || this == KELVIN
							|| this == KILOGRAM || this == LITER
							|| this == LITRE || this == LUMEN || this == LUX
							|| this == METER || this == METRE || this == MOLE
							|| this == NEWTON || this == OHM || this == PASCAL
							|| this == RADIAN || this == SECOND
							|| this == SIEMENS || this == SIEVERT
							|| this == STERADIAN || this == TESLA
							|| this == VOLT || this == WATT || this == WEBER;
				}
			} else if (level == 2) {
				switch (version) {
				case 1:
					// TODO
					break;
				case 2:
					// TODO
					break;
				case 3:
					// TODO
					break;
				case 4:
					// TODO
					break;
				default:
					break;
				}
			} else if (level == 3) {
				if (version == 1) {
					// TODO
				}
			}
			return false;
		}
	}

	/**
	 * <p>
	 * Predicate returning true or false depending on whether Unit objects are
	 * equivalent.
	 * </p>
	 * <p>
	 * Two Unit objects are considered to be equivalent if their 'kind' and
	 * 'exponent' attributes are equal. (Contrast this to the method
	 * areIdentical(Unit unit1, Unit unit2), which compares Unit objects with
	 * respect to all attributes, not just the kind and exponent.)
	 * </p>
	 * 
	 * @param unit1
	 *            the first Unit object to compare
	 * @param unit2
	 *            the second Unit object to compare
	 * @return if the 'kind' and 'exponent' attributes of unit1 are identical to
	 *         the kind and exponent attributes of unit2, false otherwise.
	 */
	public static boolean areEquivalent(Unit unit1, Unit unit2) {
		return Kind.areEquivalent(unit1.getKind(), unit2.getKind())
				&& unit1.getExponent() == unit2.getExponent();
	}

	/**
	 * <p>
	 * Predicate returning true or false depending on whether two Unit objects
	 * are identical.
	 * </p>
	 * <p>
	 * Two Unit objects are considered to be identical if they match in all
	 * attributes. (Contrast this to the method areEquivalent(Unit unit1, Unit
	 * unit2), which compares Unit objects only with respect to certain
	 * attributes.)
	 * </p>
	 * 
	 * @param unit1
	 *            the first Unit object to compare
	 * @param unit2
	 *            the second Unit object to compare
	 * @return true if all the attributes of unit1 are identical to the
	 *         attributes of unit2, false otherwise.
	 * @see areEquivalent
	 */
	public static boolean areIdentical(Unit unit1, Unit unit2) {
		boolean identical = areEquivalent(unit1, unit2);
		identical &= unit1.getOffset() == unit2.getOffset();
		identical &= unit1.getMultiplier() == unit2.getMultiplier();
		return identical && unit1.getScale() == unit2.getScale();
	}

	/**
	 * <p>
	 * Predicate to test whether a given string is the name of a valid base unit
	 * in SBML (such as 'gram' or 'mole').
	 * </p>
	 * <p>
	 * This method exists because prior to SBML Level 2 Version 3, an
	 * enumeration called UnitKind was defined by SBML. This enumeration was
	 * removed in SBML Level 2 Version 3 and its values were folded into the
	 * space of values of a type called UnitSId. This method therefore has less
	 * significance in SBML Level 2 Version 3 and Level 2 Version 4, but remains
	 * for backward compatibility and support for reading models in older
	 * Versions of Level 2.
	 * </p>
	 * 
	 * @param name
	 *            a string to be tested
	 * @param level
	 *            an integer representing the SBML specification Level
	 * @param version
	 *            an integer representing the SBML specification Version
	 * @return true if name is a valid UnitKind, false otherwise
	 * @note The allowed unit names differ between SBML Levels 1 and 2 and again
	 *       slightly between Level 2 Versions 1 and 2.
	 */
	public static boolean isUnitKind(String name, int level, int version) {
		try {
			Kind kind = Kind.valueOf(name.toUpperCase());
			return kind.isDefinedIn(level, version);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Merges two Unit objects with the same 'kind' attribute value into a
	 * single Unit.
	 * 
	 * @param unit1
	 *            the first Unit object; the result of the operation is left as
	 *            a new version of this unit, modified in-place.
	 * @param unit2
	 *            the second Unit object to merge with the first
	 */
	public static void merge(Unit unit1, Unit unit2) {
		if (Kind.areEquivalent(unit1.getKind(), unit2.getKind())
				|| unit1.getKind() == Kind.DIMENSIONLESS
				|| unit2.getKind() == Kind.DIMENSIONLESS) {
			// let' get rid of this offset if there is any...
			double m1 = unit1.getOffset() / Math.pow(10, unit1.getScale())
					+ unit1.getMultiplier();
			double m2 = unit2.getOffset() / Math.pow(10, unit2.getScale())
					+ unit2.getMultiplier();
			int s1 = unit1.getScale(), s2 = unit2.getScale();
			int e1 = unit1.getExponent(), e2 = unit2.getExponent();
			unit1.setOffset(0);
			unit1.setMultiplier(Math.pow(m1, e1) * Math.pow(m2, e2));
			unit1.setScale(s1 * e1 + s2 * e2);
			if (Kind.areEquivalent(unit1.getKind(), unit2.getKind())) {
				unit1.setExponent(e1 + e2);
				if (unit1.getExponent() == 0) {
					unit1.setExponent(1);
					unit1.setKind(Kind.DIMENSIONLESS);
				}
			}
			if (unit1.getKind() == Kind.METER)
				unit1.setKind(Kind.METRE);
			else if (unit1.getKind() == Kind.LITER)
				unit1.setKind(Kind.LITRE);
		} else
			throw new IllegalArgumentException(
					"Units can only be merged if both have the same kind attribute or if one of them is dimensionless.");
	}

	/**
	 * 
	 */
	private int exponent;

	/**
	 * 
	 */
	private Kind kind;

	/**
	 * 
	 */
	private double multiplier;

	/**
	 * 
	 */
	private double offset;

	/**
	 * 
	 */
	private int scale;

	/**
	 * 
	 * @param multiplier
	 * @param scale
	 * @param kind
	 * @param exponent
	 * @param level
	 * @param version
	 */
	public Unit(double multiplier, int scale, Kind kind, int exponent,
			int level, int version) {
		super(level, version);
		this.multiplier = multiplier;
		this.scale = scale;
		this.kind = kind;
		this.exponent = exponent;
	}

	/**
	 * 
	 */
	public Unit(int level, int version) {
		super(level, version);
		initDefaults();
	}

	/**
	 * 
	 * @param scale
	 * @param kind
	 * @param level
	 * @param version
	 */
	public Unit(int scale, Kind kind, int level, int version) {
		this(scale, kind, 1, level, version);
	}

	/**
	 * 
	 * @param scale
	 * @param kind
	 * @param exponent
	 * @param level
	 * @param version
	 */
	public Unit(int scale, Kind kind, int exponent, int level, int version) {
		this(1, scale, kind, exponent, level, version);
	}

	/**
	 * 
	 * @param kind
	 */
	public Unit(Kind kind, int level, int version) {
		super(level, version);
		initDefaults();
		this.kind = kind;
	}

	/**
	 * 
	 * @param kind
	 * @param exponent
	 * @param level
	 * @param version
	 */
	public Unit(Kind kind, int exponent, int level, int version) {
		this(0, kind, exponent, level, version);
	}

	/**
	 * @param unit
	 */
	public Unit(Unit unit) {
		super(unit);
		this.exponent = unit.getExponent();
		this.kind = unit.getKind();
		this.multiplier = unit.getMultiplier();
		this.offset = unit.getOffset();
		this.scale = unit.getScale();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.AbstractSBase#clone()
	 */
	// @Override
	public Unit clone() {
		return new Unit(this);
	}

	/**
	 * 
	 * @return
	 */
	public int getExponent() {
		return exponent;
	}

	/**
	 * 
	 * @return
	 */
	public Kind getKind() {
		return kind;
	}

	/**
	 * 
	 * @return
	 */
	public double getMultiplier() {
		return multiplier;
	}

	/**
	 * 
	 * @return
	 */
	public double getOffset() {
		return offset;
	}

	/**
	 * This method returns the prefix of this unit, for instance, "m" for milli,
	 * if the scale is -3.
	 * 
	 * @return
	 */
	public String getPrefix() {
		if (!isDimensionless()) {
			switch (getScale()) {
			case 18:
				return Character.valueOf('E').toString();
			case 15:
				return Character.valueOf('P').toString();
			case 12:
				return Character.valueOf('T').toString();
			case 9:
				return Character.valueOf('G').toString();
			case 6:
				return Character.valueOf('M').toString();
			case 3:
				return Character.valueOf('k').toString();
			case 2:
				return Character.valueOf('h').toString();
			case 1:
				return "da";
			case 0:
				break;
			case -1:
				return Character.valueOf('d').toString();
			case -2:
				return Character.valueOf('c').toString();
			case -3:
				return Character.valueOf('m').toString();
			case -6:
				return Character.valueOf('\u03BC').toString();
			case -9:
				return Character.valueOf('n').toString();
			case -12:
				return Character.valueOf('p').toString();
			case -15:
				return Character.valueOf('f').toString();
			case -18:
				return Character.valueOf('a').toString();
			default:
				break;
			}
		}
		return "";
	}

	/**
	 * 
	 * @return
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * 
	 */
	public void initDefaults() {
		exponent = 1;
		scale = 0;
		multiplier = 1d;
		offset = 0d;
		kind = Kind.INVALID;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isDimensionless() {
		return kind == Kind.DIMENSIONLESS;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isKilogram() {
		return kind == Kind.KILOGRAM;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind litre.
	 * 
	 * @return true if the kind of this Unit is litre or 'liter', false
	 *         otherwise.
	 */
	public boolean isLitre() {
		return kind == Kind.LITRE || kind == Kind.LITER;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind mole.
	 * 
	 * @return true if the kind of this Unit is mole, false otherwise.
	 */
	public boolean isMole() {
		return kind == Kind.MOLE;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isSetOffset() {
		return offset != 0d;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isVariantOfArea() {
		Kind kind = getKind();
		return kind == Kind.METER || kind == Kind.METRE && getOffset() == 0
				&& getExponent() == 2;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isVariantOfLength() {
		Kind kind = getKind();
		return kind == Kind.METER || kind == Kind.METRE && getOffset() == 0
				&& getExponent() == 1;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isVariantOfSubstance() {
		Kind kind = getKind();
		if (kind == Kind.MOLE
				|| kind == Kind.ITEM
				|| (((level == 2 && version > 1) || level > 2) && (kind == Kind.GRAM || isKilogram())))
			return getOffset() == 0 && getExponent() == 1;
		return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isVariantOfVolume() {
		Kind kind = getKind();
		if (kind == Unit.Kind.LITER || kind == Unit.Kind.LITRE)
			return getOffset() == 0 && getExponent() == 1;
		if (kind == Unit.Kind.METER || kind == Unit.Kind.METRE)
			return getOffset() == 0 && getExponent() == 3;
		return false;
	}

	/**
	 * 
	 * @param exponent
	 */
	public void setExponent(int exponent) {
		this.exponent = exponent;
	}

	/**
	 * 
	 * @param kind
	 */
	public void setKind(Kind kind) {
		this.kind = kind;
		stateChanged();
	}

	/**
	 * 
	 * @param multiplier
	 */
	public void setMultiplier(double multiplier) {
		this.multiplier = multiplier;
		stateChanged();
	}

	/**
	 * 
	 * @param offset
	 */
	public void setOffset(double offset) {
		this.offset = offset;
	}

	/**
	 * 
	 * @param scale
	 */
	public void setScale(int scale) {
		this.scale = scale;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.AbstractSBase#toString()
	 */
	// @Override
	public String toString() {
		String mult;
		if (multiplier - ((int) multiplier) == 0)
			mult = Integer.toString((int) multiplier);
		else
			mult = Double.toString(multiplier);
		StringBuffer times = mult.equals("1") ? new StringBuffer()
				: new StringBuffer(mult);
		StringBuffer pow = new StringBuffer(kind.getSymbol());
		String prefix = getPrefix();
		if (prefix.length() > 0)
			pow.insert(0, prefix);
		else
			pow = TextFormula.times(TextFormula.pow(Integer.valueOf(10),
					Integer.valueOf(scale)), pow);
		times = TextFormula.times(times, pow);
		if (isSetOffset())
			times = TextFormula.sum(Double.toString(offset), times);
		return TextFormula.pow(times, Integer.valueOf(exponent)).toString();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix, String value){
		boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
		
		if (!isAttributeRead){
			if (attributeName.equals("kind")){
				try {
					Kind kind = Kind.valueOf(value.toUpperCase());
					this.setKind(kind);
					return true;
				} catch (Exception e) {
					return false;
				}
			}
			else if (attributeName.equals("exponent")){
				this.setExponent(Integer.parseInt(value));
			}
			else if (attributeName.equals("scale")){
				this.setScale(Integer.parseInt(value));
			}
			else if (attributeName.equals("multiplier")){
				this.setMultiplier(Double.parseDouble(value));
			}
		}
		return isAttributeRead;
	}
}
