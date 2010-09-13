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

import java.util.HashMap;

import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.compilers.TextFormula;

/**
 * Represents the unit XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @date 2009-08-31
 * 
 * @opt attributes
 * @opt types
 * @opt visibility
 */
public class Unit extends AbstractSBase {

	/**
	 * @author Andreas Dr&auml;ger
	 * @date 2009-08-31
	 */
	public static enum Kind {
		/**
		 * The ampere unit.
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
		 * Marker used to indicate an invalid or not yet set unit.
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
		 * This method is equivalent to converting the {@link String} to a
		 * {@link Kind} and then calling its {@link #isDefinedIn} method. Only
		 * entirely upper or entirely lower case {@link String}s are valid
		 * attributes here.
		 * 
		 * @param unitKind
		 * @param level
		 * @param version
		 * @return
		 */
		// TODO : check that it works for the default units.
		public static boolean isValidUnitKindString(String unitKind, int level,
				int version) {
			Kind uk = null;
			if ((unitKind != null) && (unitKind.length() > 0)) {
				try {
					// We need to do that as our enum is upper case and sbml
					// kind are lower case in the SBML XML representation.
					uk = Kind.valueOf(unitKind.toUpperCase());
				} catch (IllegalArgumentException exc) {
				}
			}

			if (uk == null) {
				return false;
			}
			return uk.isDefinedIn(level, version);
		}

		/**
		 * Returns the name of this unit kind.
		 * 
		 * @return
		 */
		public String getName() {
			if (this == CELSIUS) {
				return "degree "
						+ StringTools.firstLetterUpperCase(toString()
								.toLowerCase());
			}
			if (this == DIMENSIONLESS || this == GRAM || this == ITEM
					|| this == INVALID || this == KILOGRAM || this == LUX
					|| this == METER || this == METRE || this == MOLE
					|| this == SECOND) {
				return toString().toLowerCase();
			} else {
				return StringTools.firstLetterUpperCase(toString()
						.toLowerCase());
			}
		}

		/**
		 * 
		 * @return the formula symbol of this unit kind in uni-code notation.
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
			return (((level == 1 && (version == 1 || version == 2))
					|| (level == 2 && (1 <= version && version <= 4)) || level == 3
					&& version == 1) && ((this == AMPERE || this == BECQUEREL
					|| this == CANDELA || this == COULOMB
					|| this == DIMENSIONLESS || this == FARAD || this == GRAM
					|| this == GRAY || this == HENRY || this == HERTZ
					|| this == ITEM || this == JOULE || this == KATAL
					|| this == KELVIN || this == KILOGRAM || this == LITRE
					|| this == LUMEN || this == LUX || this == METRE
					|| this == MOLE || this == NEWTON || this == OHM
					|| this == PASCAL || this == RADIAN || this == SECOND
					|| this == SIEMENS || this == SIEVERT || this == STERADIAN
					|| this == TESLA || this == VOLT || this == WATT || this == WEBER)
					|| (level == 1 && (version == 1 || version == 2)
							&& this == CELSIUS || this == LITER || this == METER) || (level == 2
					&& version == 1 && this == CELSIUS)));
		}
	}

	/**
	 * Checks whether the given {@link Unit} and the {@link Unit} represented by
	 * the given {@link String} are equivalent.
	 * 
	 * @param unit
	 * @param units
	 * @return
	 * @see #areEquivalent(Unit, Unit)
	 */
	public static boolean areEquivalent(Unit unit, String units) {
		return areEquivalent(unit, new Unit(units, unit.getLevel(), unit
				.getVersion()));
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
	 * Returns a UnitDefinition object which contains the argument Unit
	 * converted to the appropriate SI unit.
	 * 
	 * @param unit
	 *            the Unit object to convert to SI
	 * @return a UnitDefinition object containing the SI unit.
	 */
	public static UnitDefinition convertToSI(Unit unit) {
		double mult = unit.getMultiplier();
		double exp = unit.getExponent();
		int scale = unit.getScale();
		int l = unit.getLevel();
		int v = unit.getVersion();
		UnitDefinition ud = new UnitDefinition(l, v);

		switch (unit.getKind()) {
		case AMPERE:
			/* Ampere is the SI unit of current */
			ud.addUnit(new Unit(mult, scale, Kind.AMPERE, exp, l, v));
			break;

		case BECQUEREL:
		case HERTZ:
			/* 1 Becquerel = 1 sec^-1 */
			/* 1 Hertz = 1 sec^-1 */
			ud.addUnit(new Unit(Math.pow(mult, -1d), scale, Kind.SECOND, -exp,
					l, v));
			break;

		case CANDELA:
			/* Candela is the SI unit of luminous intensity */
			ud.addUnit(new Unit(mult, scale, Kind.CANDELA, exp, l, v));
			break;

		case CELSIUS:
			/* 1 °Celsius = 1 Kelvin + 273.15 */
			Unit newUnit = new Unit(mult, scale, Kind.KELVIN, exp, l, v);
			newUnit.setOffset(273.15);
			ud.addUnit(newUnit);
			break;

		case COULOMB:
			/* 1 Coulomb = 1 Ampere * second */
			ud.addUnit(new Unit(mult, scale, Kind.AMPERE, exp, l, v));
			ud.addUnit(new Unit(mult, scale, Kind.SECOND, exp, l, v));
			break;

		case DIMENSIONLESS:
		case ITEM:
		case RADIAN:
		case STERADIAN:
			/* all dimensionless */
			ud.addUnit(new Unit(mult, scale, Kind.DIMENSIONLESS, exp, l, v));
			break;

		case FARAD:
			/* 1 Farad = 1 m^-2 kg^-1 s^4 A^2 */
			ud.addUnit(new Unit(Math.sqrt(mult), scale, Kind.AMPERE, 2 * exp,
					l, v));
			ud.addUnit(new Unit(1d, scale, Kind.KILOGRAM, -exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.METRE, -2 * exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.SECOND, 4 * exp, l, v));
			break;

		case GRAM:
			/* 1 gram = 0.001 Kg */
			ud.addUnit(new Unit(0.001 * mult, scale, Kind.KILOGRAM, exp, l, v));
			break;

		case GRAY:
		case SIEVERT:
			/* 1 Gray = 1 m^2 sec^-2 */
			/* 1 Sievert = 1 m^2 sec^-2 */
			ud.addUnit(new Unit(mult, scale, Kind.METRE, 2 * exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-2) * exp, l, v));
			break;

		case HENRY:
			/* 1 Henry = 1 m^2 kg s^-2 A^-2 */
			ud.addUnit(new Unit(1d / Math.sqrt(mult), scale, Kind.AMPERE, (-2)
					* exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.KILOGRAM, exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.METRE, 2 * exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-2) * exp, l, v));
			break;

		case JOULE:
			/* 1 joule = 1 m^2 kg s^-2 */
			ud.addUnit(new Unit(mult, scale, Kind.KILOGRAM, exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.METRE, 2 * exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-2) * exp, l, v));
			break;

		case KATAL:
			/* 1 katal = 1 mol * s^-1 */
			ud.addUnit(new Unit(mult, scale, Kind.MOLE, exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.SECOND, -exp, l, v));
			break;

		case KELVIN:
			/* Kelvin is the SI unit of temperature */
			ud.addUnit(new Unit(mult, scale, Kind.KELVIN, exp, l, v));
			break;

		case KILOGRAM:
			/* Kilogram is the SI unit of mass */
			ud.addUnit(new Unit(mult, scale, Kind.KILOGRAM, exp, l, v));
			break;

		case LITER:
		case LITRE:
			/* 1 litre = 0.001 m^3 = (0.1 m)^3 */
			ud.addUnit(new Unit(Math.pow(0.001 * mult, 1d / 3d), scale,
					Kind.METRE, 3 * exp, l, v));
			break;

		case LUMEN:
			/* 1 Lumen = 1 Candela */
			ud.addUnit(new Unit(mult, scale, Kind.CANDELA, exp, l, v));
			break;

		case LUX:
			/* 1 Lux = 1 Candela * m^-2 */
			ud.addUnit(new Unit(mult, scale, Kind.CANDELA, exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.METRE, (-2) * exp, l, v));
			break;

		case METER:
		case METRE:
			/* metre is the SI unit of length */
			ud.addUnit(new Unit(mult, scale, Kind.METRE, exp, l, v));
			break;

		case MOLE:
			/* mole is the SI unit of substance */
			ud.addUnit(new Unit(mult, scale, Kind.MOLE, exp, l, v));
			break;

		case NEWTON:
			/* 1 newton = 1 m kg s^-2 */
			ud.addUnit(new Unit(mult, scale, Kind.KILOGRAM, exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.METRE, exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-2) * exp, l, v));
			break;

		case OHM:
			/* 1 ohm = 1 m^2 kg s^-3 A^-2 */
			ud.addUnit(new Unit(1d / Math.sqrt(mult), scale, Kind.AMPERE, (-2)
					* exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.KILOGRAM, exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.METRE, 2 * exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-3) * exp, l, v));
			break;

		case PASCAL:
			/* 1 pascal = 1 m^-1 * kg s^-2 */
			ud.addUnit(new Unit(mult, scale, Kind.KILOGRAM, exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.METRE, -exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-2) * exp, l, v));
			break;

		case SECOND:
			/* second is the SI unit of time */
			ud.addUnit(new Unit(mult, scale, Kind.SECOND, exp, l, v));
			break;

		case SIEMENS:
			/* 1 Siemens = 1 m^-2 * kg^-1 * s^3 * A^2 */
			ud.addUnit(new Unit(Math.sqrt(mult), scale, Kind.AMPERE, 2 * exp,
					l, v));
			ud.addUnit(new Unit(1d, scale, Kind.KILOGRAM, -exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.METRE, (-2) * exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.SECOND, 3 * exp, l, v));
			break;

		case TESLA:
			/* 1 tesla = 1 kg * s^-2 * A^-1 */
			ud.addUnit(new Unit(1d / mult, scale, Kind.AMPERE, -exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.KILOGRAM, exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-2) * exp, l, v));
			break;

		case VOLT:
			/* 1 volt = 1 m^2 * kg * s^-3 * A^-1 */
			ud.addUnit(new Unit(1d / mult, scale, Kind.AMPERE, -exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.KILOGRAM, exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.METRE, 2 * exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-3) * exp, l, v));
			break;

		case WATT:
			/* 1 Watt = 1 m^2 * kg * s^-3 */
			ud.addUnit(new Unit(mult, scale, Kind.KILOGRAM, exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.METRE, 2 * exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-3) * exp, l, v));
			break;

		case WEBER:
			/* 1 Weber = 1 m^2 * kg * s^-2 * A^-1 */
			ud.addUnit(new Unit(1d / mult, scale, Kind.AMPERE, -exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.KILOGRAM, exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.METRE, 2 * exp, l, v));
			ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-2) * exp, l, v));
			break;

		case INVALID:
			break;
		default:
			break;
		}
		return ud.simplify();
	}

	/**
	 * Predicate to test whether a given string is the name of a predefined SBML
	 * unit.
	 * 
	 * @param name
	 *            a string to be tested against the predefined unit names
	 * @param level
	 *            the Level of SBML for which the determination should be made.
	 *            This is necessary because there are a few small differences in
	 *            allowed units between SBML Level 1 and Level 2.
	 * @return true, if name is one of the five SBML predefined unit identifiers
	 *         ('substance', 'volume', 'area', 'length' or 'time'), false
	 *         otherwise. The predefined unit identifiers 'length' and 'area'
	 *         were added in Level 2 Version 1
	 * @deprecated use {@link #isPredefined(String, int)}
	 */
	@Deprecated
	public static boolean isBuiltIn(String name, int level) {
		return isPredefined(name, level);
	}

	/**
	 * 
	 * @param name
	 * @param level
	 * @return
	 */
	public static boolean isPredefined(String name, int level) {
		if ((level < 3)
				&& (name.equals("substance") || name.equals("volume")
						|| name.equals("time") || (level == 2 && (name
						.equals("length") || name.equals("area"))))) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param kind
	 * @param level
	 * @param version
	 * @return
	 */
	public static boolean isUnitKind(Kind kind, int level, int version) {
		try {
			return kind.isDefinedIn(level, version);
		} catch (Exception e) {
			return false;
		}
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
		if ((name == null) || UnitDefinition.isPredefined(name, level)) {
			// predefined units are always unit definitions.
			return false;
		}
		Kind kind = null;
		try {
			kind = Kind.valueOf(name.toUpperCase());
		} catch (IllegalArgumentException exc) {
			return false;
		}
		return isUnitKind(kind, level, version);

	}

	/**
	 * Returns true if the <code>unit</code> is a valid unit kind name or an
	 * identifier of an existing {@link UnitDefinition}.
	 * 
	 * If either the unit or model are null, it will return false.
	 * 
	 * @param unit
	 *            the identifier of a {@link UnitDefinition} or a valid
	 *            {@link Unit.Kind} identifier for the current level/version
	 *            combination of the model.
	 * @param model
	 *            the model where to look for the <code>unit</code>.
	 * 
	 * @return true if the unit is a valid unit kind name or an identifier of an
	 *         existing {@link UnitDefinition}.
	 */
	public static boolean isValidUnit(Model model, String unit) {
		boolean isValidUnit = false;

		if (unit != null && model != null) {
			unit = unit.trim();
			if (unit.length() > 0) {
				if (Kind.isValidUnitKindString(unit, model.getLevel(), model
						.getVersion())) {
					isValidUnit = true;
				} else if (model.getUnitDefinition(unit) != null) {
					isValidUnit = true;
				}
			}
		}

		return isValidUnit;
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
				|| unit1.isDimensionless() || unit2.isDimensionless()) {
			// let' get rid of this offset if there is any...
			double m1 = unit1.getOffset() / Math.pow(10, unit1.getScale())
					+ unit1.getMultiplier();
			double m2 = unit2.getOffset() / Math.pow(10, unit2.getScale())
					+ unit2.getMultiplier();
			int s1 = unit1.getScale(), s2 = unit2.getScale();
			double e1 = unit1.getKind() == Kind.DIMENSIONLESS
					&& unit1.getExponent() != 1 ? 1 : unit1.getExponent();
			double e2 = unit2.getKind() == Kind.DIMENSIONLESS
					&& unit2.getExponent() != 1 ? 1 : unit2.getExponent();
			unit1.setOffset(0);
			unit1.setMultiplier(Math.pow(m1, e1) * Math.pow(m2, e2));
			unit1.setScale((int) Math.round(s1 * e1 + s2 * e2));
			if (Kind.areEquivalent(unit1.getKind(), unit2.getKind())) {
				unit1.setExponent(e1 + e2);
				if (unit1.getExponent() != 0) {
					unit1.setMultiplier(Math.pow(unit1.getMultiplier(),
							1 / unit1.getExponent()));
					unit1.setScale((int) Math.round(unit1.getScale()
							/ unit1.getExponent()));
				}
			} else if (e1 != 0) {
				unit1.setMultiplier(Math.pow(unit1.getMultiplier(),
						1 / (double) e1));
				unit1.setScale((int) Math
						.round((unit1.getScale() / (double) e1)));
			}
			if (unit1.getExponent() == 0) {
				unit1.setExponent(1);
				unit1.setKind(Kind.DIMENSIONLESS);
			}
			if (unit1.getKind() == Kind.METER) {
				unit1.setKind(Kind.METRE);
			} else if (unit1.getKind() == Kind.LITER) {
				unit1.setKind(Kind.LITRE);
			}
		} else {
			throw new IllegalArgumentException(
					"Units can only be merged if both have the same kind attribute or if one of them is dimensionless.");
		}
	}

	/**
	 * Manipulates the attributes of the Unit to express the unit with the value
	 * of the scale attribute reduced to zero.
	 * 
	 * For example, 1 millimetre can be expressed as a Unit with kind= 'metre'
	 * multiplier='1' scale='-3' exponent='1'. It can also be expressed as a
	 * Unit with kind='metre' multiplier='0.001' scale='0' exponent='1'.
	 * 
	 * @param unit
	 *            the Unit object to manipulate.
	 */
	public static void removeScale(Unit unit) {
		unit.removeScale();
	}

	/**
	 * Represents the 'exponent' XML attribute of an unit element.
	 */
	private Double exponent;
	/**
	 * 
	 */
	private boolean isSetExponent;

	/**
	 * 
	 */
	private boolean isSetMultiplier;

	/**
	 * 
	 */
	private boolean isSetOffset;
	/**
	 * 
	 */
	private boolean isSetScale;

	/**
	 * Represents the 'kind' XML attribute of an unit element.
	 */
	private Kind kind;
	/**
	 * Represents the 'multiplier' XML attribute of an unit element.
	 */
	private Double multiplier;

	/**
	 * Represents the 'offset' XML attribute of an unit element.
	 */
	@Deprecated
	private Double offset;

	/**
	 * Represents the 'scale' XML attribute of an unit element.
	 */
	private Integer scale;

	/**
	 * Creates a Unit instance. If the level is set and is superior or equal to
	 * 3 the multiplier, scale, kind and exponent are null.
	 */
	public Unit() {
		super();
		initDefaults();
	}

	/**
	 * Creates a Unit instance from a multiplier, scale, kind and exponent. The
	 * offset is null.
	 * 
	 * @param multiplier
	 * @param scale
	 * @param kind
	 * @param exponent
	 * @param level
	 * @param version
	 */
	public Unit(double multiplier, int scale, Kind kind, double exponent,
			int level, int version) {
		super(level, version);

		// Using the set method to have the isSet properly set to true.
		setMultiplier(multiplier);
		setScale(scale);
		setKind(kind);
		setExponent(exponent);
		this.offset = null;
	}

	/**
	 * Creates a Unit instance from a level and version. If the level is set and
	 * is superior or equal to 3 the multiplier, scale, kind, offset and
	 * exponent are null.
	 * 
	 * @param level
	 * @param version
	 */
	public Unit(int level, int version) {
		super(level, version);
		initDefaults();
	}

	/**
	 * Creates a Unit instance from a scale, kind, exponent, level and version.
	 * 
	 * @param scale
	 * @param kind
	 * @param exponent
	 * @param level
	 * @param version
	 */
	public Unit(int scale, Kind kind, double exponent, int level, int version) {
		this(1, scale, kind, exponent, level, version);
		isSetMultiplier = false;
	}

	/**
	 * Creates a Unit instance from a scale, kind, level and version.
	 * 
	 * @param scale
	 * @param kind
	 * @param level
	 * @param version
	 */
	public Unit(int scale, Kind kind, int level, int version) {
		this(scale, kind, 1d, level, version);
		isSetExponent = false;
	}

	/**
	 * Creates a Unit instance from a kind, exponent, level and version.
	 * 
	 * @param kind
	 * @param exponent
	 * @param level
	 * @param version
	 */
	public Unit(Kind kind, double exponent, int level, int version) {
		this(0, kind, exponent, level, version);
		isSetScale = false;
	}

	/**
	 * Creates a Unit instance from a kind, level and version. If the level is
	 * set and is superior or equal to 3 the multiplier, scale, offset and
	 * exponent are null.
	 * 
	 * @param kind
	 */
	public Unit(Kind kind, int level, int version) {
		super(level, version);
		if (!isUnitKind(kind, level, version)) {
			throw new IllegalArgumentException(String.format(
					"%s undefined for level %s version %s", kind, level,
					version));
		}
		initDefaults();
		setKind(kind);
	}

	/**
	 * 
	 * @param units
	 * @param level
	 * @param version
	 */
	public Unit(String units, int level, int version) {
		this(Unit.Kind.valueOf(units), level, version);
	}

	/**
	 * Creates a Unit instance from a given Unit.
	 * 
	 * @param unit
	 */
	public Unit(Unit unit) {
		super(unit);
		initDefaults();
		exponent = unit.exponent != null ? Double.valueOf(unit.getExponent())
				: null;
		multiplier = unit.multiplier != null ? new Double(unit.getMultiplier())
				: null;
		offset = unit.offset != null ? new Double(unit.getOffset()) : null;
		scale = unit.scale != null ? Integer.valueOf(unit.getScale()) : null;
		kind = unit.isSetKind() ? unit.getKind() : Kind.INVALID;
		isSetExponent = unit.isSetExponent;
		isSetMultiplier = unit.isSetMultiplier;
		isSetOffset = unit.isSetOffset;
		isSetScale = unit.isSetScale;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.AbstractSBase#clone()
	 */
	@Override
	public Unit clone() {
		return new Unit(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#equals(Object o)
	 */
	// @Override
	public boolean equals(Object o) {
		if (o instanceof Unit) {
			Unit u = (Unit) o;
			boolean equal = super.equals(o);
			equal &= getMultiplier() == u.getMultiplier();
			equal &= getScale() == u.getScale();
			equal &= getExponent() == u.getExponent();
			equal &= getOffset() == u.getOffset();
			equal &= getKind() == u.getKind();
			return equal;
		}
		return false;
	}

	/**
	 * 
	 * @return the exponent of this Unit if it is set, 1 otherwise.
	 */
	public double getExponent() {
		return isSetExponent() ? exponent : 1d;
	}

	/**
	 * 
	 * @return the kind of this Unit if it is set, null otherwise.
	 * 
	 */
	public Kind getKind() {
		return isSetKind() ? kind : Kind.INVALID;
	}

	/**
	 * 
	 * @return the multiplier of this Unit if it is set, 1 otherwise.
	 */
	public double getMultiplier() {
		return isSetMultiplier() ? multiplier : 1;
	}

	/**
	 * 
	 * @return the offset of this Unit if it is set, 0 otherwise.
	 */
	@Deprecated
	public double getOffset() {
		return isSetOffset() ? offset : 0;
	}

	/**
	 * 
	 * @return This method returns the prefix of this unit, for instance, "m"
	 *         for milli, if the scale is -3.
	 */

	public String getPrefix() {
		if (!isDimensionless()) {
			switch (getScale()) {
			case 24:
				return Character.valueOf('Y').toString();
			case 21:
				return Character.valueOf('Z').toString();
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
			case -21:
				return Character.valueOf('z').toString();
			case -24:
				return Character.valueOf('y').toString();
			default:
				break;
			}
		}
		return String.format("10^(%d)", getScale());
	}

	/**
	 * 
	 * @return the prefix of this unit, for instance, "m" for milli, if the
	 *         scale is -3.
	 */
	public String getPrefixAsWord() {
		if (!isDimensionless()) {
			switch (getScale()) {
			case 24:
				return "yotta";
			case 21:
				return "zetta";
			case 18:
				return "exa";
			case 15:
				return "peta";
			case 12:
				return "tera";
			case 9:
				return "giga";
			case 6:
				return "mega";
			case 3:
				return "kilo";
			case 2:
				return "hecto";
			case 1:
				return "deca";
			case 0:
				break;
			case -1:
				return "deci";
			case -2:
				return "centi";
			case -3:
				return "milli";
			case -6:
				return "micro";
			case -9:
				return "nano";
			case -12:
				return "pico";
			case -15:
				return "femto";
			case -18:
				return "atto";
			case -21:
				return "zepto";
			case -24:
				return "yocto";
			default:
				break;
			}
		}
		return "";
	}

	/**
	 * 
	 * @return the scale of this Unit if it is set, 0 otherwise.
	 */
	public int getScale() {
		return isSetScale() ? scale : 0;
	}

	/**
	 * Predicate returning true or false depending on whether all the required
	 * attributes for this Unit object have been set.
	 * 
	 * @return a boolean value indicating whether all the required elements for
	 *         this object have been defined.
	 */
	public boolean hasRequiredAttributes() {
		return isSetKind();
	}

	/**
	 * Initializes the attributes of this Unit (except for 'kind') to their
	 * defaults values.
	 * 
	 * The default values are as follows:
	 * <ul>
	 * <li>exponent = 1</li>
	 * <li>scale = 0</li>
	 * <li>multiplier = 1.0</li>
	 * </ul>
	 * The 'kind' attribute is left unchanged.
	 */
	public void initDefaults() {
		kind = Kind.INVALID;
		if (getLevel() < 3) {
			exponent = Double.valueOf(1d);
			scale = Integer.valueOf(0);
			multiplier = new Double(1);
			offset = new Double(0);
		} else {
			exponent = null;
			offset = null;
			multiplier = null;
			scale = null;
		}
		isSetExponent = false;
		isSetScale = false;
		isSetMultiplier = false;
		isSetOffset = false;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind ampere.
	 * 
	 * @return
	 */
	public boolean isAmpere() {
		return kind == Kind.AMPERE;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind becquerel.
	 * 
	 * @return
	 */
	public boolean isBecquerel() {
		return kind == Kind.BECQUEREL;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind candela
	 * 
	 * @return
	 */
	public boolean isCandela() {
		return kind == Kind.CANDELA;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind Celsius
	 * 
	 * @return
	 */
	public boolean isCelsius() {
		return kind == Kind.CELSIUS;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind coulomb.
	 * 
	 * @return
	 */
	public boolean isCoulomb() {
		return kind == Kind.COULOMB;
	}

	/**
	 * Predicate for testing whether this Unit is of the {@link Kind}
	 * {@link DIMENSIONLESS}. A unit is also dimensionless if it does not
	 * declare an offset and at the same time its exponent is zero. In this case
	 * the unit represents a dimensionless quantity.
	 * 
	 * @return True if this unit represents a dimensionless quantity, i.e., its
	 *         {@link Kind} is {@link DIMENSIONLESS} or offset = exponent = 0
	 */
	public boolean isDimensionless() {
		return kind == Kind.DIMENSIONLESS
				|| (getOffset() == 0 && getExponent() == 0);
	}

	/**
	 * Predicate for testing whether this Unit is of the kind farad
	 * 
	 * @return
	 */
	public boolean isFarad() {
		return kind == Kind.FARAD;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind gram
	 * 
	 * @return
	 */
	public boolean isGram() {
		return kind == Kind.GRAM;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind gray
	 * 
	 * @return
	 */
	public boolean isGray() {
		return kind == Kind.GRAY;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind henry
	 * 
	 * @return
	 */
	public boolean isHenry() {
		return kind == Kind.HENRY;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind hertz
	 * 
	 * @return
	 */
	public boolean isHertz() {
		return kind == Kind.HERTZ;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind invalid.
	 * 
	 * @return
	 */
	public boolean isInvalid() {
		return kind == Kind.INVALID;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind item
	 * 
	 * @return
	 */
	public boolean isItem() {
		return kind == Kind.ITEM;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind Joule
	 * 
	 * @return
	 */
	public boolean isJoule() {
		return kind == Kind.JOULE;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind katal
	 * 
	 * @return
	 */
	public boolean isKatal() {
		return kind == Kind.KATAL;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind kelvin
	 * 
	 * @return
	 */
	public boolean isKelvin() {
		return kind == Kind.KELVIN;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind kilogram
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
	 * Predicate for testing whether this Unit is of the kind lumen
	 * 
	 * @return
	 */
	public boolean isLumen() {
		return kind == Kind.LUMEN;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind lux
	 * 
	 * @return
	 */
	public boolean isLux() {
		return kind == Kind.LUX;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind metre
	 * 
	 * @return
	 */
	public boolean isMetre() {
		return kind == Kind.METRE || kind == Kind.METER;
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
	 * Predicate for testing whether this Unit is of the kind newton
	 * 
	 * @return
	 */
	public boolean isNewton() {
		return kind == Kind.NEWTON;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind ohm
	 * 
	 * @return
	 */
	public boolean isOhm() {
		return kind == Kind.OHM;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind pascal
	 * 
	 * @return
	 */
	public boolean isPascal() {
		return kind == Kind.PASCAL;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind radian
	 * 
	 * @return
	 */
	public boolean isRadian() {
		return kind == Kind.RADIAN;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind second
	 * 
	 * @return
	 */
	public boolean isSecond() {
		return kind == Kind.SECOND;
	}

	/**
	 * 
	 * @return true if the exponent of this Unit is not null.
	 */
	public boolean isSetExponent() {
		return isSetExponent && (exponent != null);
	}

	/**
	 * Predicate to test whether the 'kind' attribute of this Unit has been set.
	 * 
	 * @return
	 */
	public boolean isSetKind() {
		return (kind != null) && (kind != Kind.INVALID);
	}

	/**
	 * 
	 * @return true if the multiplier of this Unit is not null.
	 */
	public boolean isSetMultiplier() {
		return isSetMultiplier && (multiplier != null);
	}

	/**
	 * 
	 * @return
	 */
	@Deprecated
	public boolean isSetOffset() {
		return isSetOffset && (offset != null);
	}

	/**
	 * 
	 * @return true if the scale of this Unit is not null.
	 */
	public boolean isSetScale() {
		return isSetScale && (scale != null);
	}

	/**
	 * Predicate for testing whether this Unit is of the kind siemens
	 * 
	 * @return
	 */
	public boolean isSiemens() {
		return kind == Kind.SIEMENS;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind sievert
	 * 
	 * @return
	 */
	public boolean isSievert() {
		return kind == Kind.SIEVERT;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind steradian
	 * 
	 * @return
	 */
	public boolean isSteradian() {
		return kind == Kind.STERADIAN;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind tesla
	 * 
	 * @return
	 */
	public boolean isTesla() {
		return kind == Kind.TESLA;
	}

	/**
	 * 
	 * @return true if this Unit is a variant of area.
	 */
	public boolean isVariantOfArea() {
		Kind kind = getKind();
		return kind == Kind.METER || kind == Kind.METRE && getOffset() == 0
				&& getExponent() == 2;
	}

	/**
	 * 
	 * @return true if this Unit is a variant of length.
	 */
	public boolean isVariantOfLength() {
		Kind kind = getKind();
		return kind == Kind.METER || kind == Kind.METRE && getOffset() == 0
				&& getExponent() == 1;
	}

	/**
	 * 
	 * @return true if this Unit is a variant of substance.
	 */
	public boolean isVariantOfSubstance() {
		Kind kind = getKind();
		if (kind == Kind.MOLE
				|| kind == Kind.ITEM
				|| (((level == 2 && version > 1) || level > 2) && (kind == Kind.GRAM || isKilogram()))) {
			return getOffset() == 0 && getExponent() == 1;
		}
		return false;
	}

	/**
	 * 
	 * @return true if this Unit is a variant of volume.
	 */
	public boolean isVariantOfVolume() {
		Kind kind = getKind();
		if (kind == Kind.LITER || kind == Kind.LITRE) {
			return getOffset() == 0 && getExponent() == 1;
		}
		if (kind == Kind.METER || kind == Kind.METRE) {
			return getOffset() == 0 && getExponent() == 3;
		}
		return false;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind volt
	 * 
	 * @return
	 */
	public boolean isVolt() {
		return kind == Kind.VOLT;
	}

	/**
	 *Predicate for testing whether this Unit is of the kind watt
	 * 
	 * @return
	 */
	public boolean isWatt() {
		return kind == Kind.WATT;
	}

	/**
	 * Predicate for testing whether this Unit is of the kind weber
	 * 
	 * @return
	 */
	public boolean isWeber() {
		return kind == Kind.WEBER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	@Override
	public boolean readAttribute(String attributeName, String prefix,
			String value) {
		boolean isAttributeRead = super.readAttribute(attributeName, prefix,
				value);

		if (!isAttributeRead) {
			if (attributeName.equals("kind")) {
				try {
					Kind kind = Kind.valueOf(value.toUpperCase());
					this.setKind(kind);
					return true;
				} catch (Exception e) {
					return false;
				}
			} else if (attributeName.equals("exponent")) {
				this.setExponent(Integer.parseInt(value));
			} else if (attributeName.equals("scale")) {
				this.setScale(Integer.parseInt(value));
			} else if (attributeName.equals("multiplier") && getLevel() > 1) {
				this.setMultiplier(Double.parseDouble(value));
			} else if (attributeName.equals("offset") && getLevel() > 1) {
				this.setOffset(Double.parseDouble(value));
			}
		}
		return isAttributeRead;
	}

	/**
	 * Manipulates the attributes of the Unit to express the unit with the value
	 * of the scale attribute reduced to zero.
	 * 
	 * For example, 1 millimetre can be expressed as a Unit with kind= 'metre'
	 * multiplier='1' scale='-3' exponent='1'. It can also be expressed as a
	 * Unit with kind='metre' multiplier='0.001' scale='0' exponent='1'.
	 */
	public void removeScale() {
		setMultiplier(getMultiplier() * Math.pow(10, getScale()));
		setScale(0);
	}

	/**
	 * Sets the exponent of this Unit
	 * 
	 * @param exponent
	 */
	public void setExponent(double exponent) {
		isSetExponent = true;
		this.exponent = Double.valueOf(exponent);
		stateChanged();
	}

	/**
	 * Sets the kind of this Unit
	 * 
	 * @param kind
	 */
	public void setKind(Kind kind) {
		this.kind = kind != null ? kind : Kind.INVALID;
		stateChanged();
	}

	/**
	 * Sets the multiplier of this Unit
	 * 
	 * @param multiplier
	 */
	public void setMultiplier(double multiplier) {
		isSetMultiplier = true;
		this.multiplier = multiplier;
		stateChanged();
	}

	/**
	 * Sets the offset of this Unit
	 * 
	 * @param offset
	 */
	@Deprecated
	public void setOffset(double offset) {
		isSetOffset = true;
		this.offset = Double.valueOf(offset);
		stateChanged();
	}

	/**
	 * Sets the scale of this Unit
	 * 
	 * @param scale
	 */
	public void setScale(int scale) {
		isSetScale = true;
		this.scale = scale;
		stateChanged();
	}

	/**
	 * Produces a text formula representation of this unit.
	 */
	@Override
	public String toString() {
		StringBuffer times = new StringBuffer();
		if (getMultiplier() != 0) {
			if (getMultiplier() != 1) {
				times.append(StringTools.toString(getMultiplier()));
			}
			StringBuffer pow = new StringBuffer();
			pow.append(kind != null ? kind.getSymbol() : "undefined");
			String prefix = getPrefix();
			if (prefix.length() > 0 && !prefix.startsWith("10")) {
				pow.insert(0, prefix);
			} else if (getScale() != 0) {
				pow = TextFormula.times(TextFormula.pow(Integer.valueOf(10),
						getScale()), pow);
			}
			times = TextFormula.times(times, pow);
		}
		if (offset != null && offset.doubleValue() != 0) {
			times = TextFormula.sum(StringTools.toString(offset.doubleValue()),
					times);
		}
		return TextFormula.pow(times, Double.valueOf(getExponent())).toString();
	}

	/**
	 * 
	 */
	public void unsetExponent() {
		exponent = null;
		isSetExponent = false;
		stateChanged();
	}

	/**
	 * 
	 */
	public void unsetKind() {
		kind = Kind.INVALID;
		stateChanged();
	}

	/**
	 * 
	 */
	public void unsetMultiplier() {
		multiplier = null;
		isSetMultiplier = false;
		stateChanged();
	}

	/**
	 * 
	 */
	@Deprecated
	public void unsetOffset() {
		offset = null;
		isSetOffset = false;
		stateChanged();
	}

	/**
	 * 
	 */
	public void unsetScale() {
		scale = null;
		isSetScale = false;
		stateChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName,
	 * String prefix, String value)
	 */
	@Override
	public HashMap<String, String> writeXMLAttributes() {
		HashMap<String, String> attributes = super.writeXMLAttributes();

		if (isSetKind()) {
			attributes.put("kind", getKind().toString().toLowerCase());
		}
		if (isSetExponent()) {
			attributes.put("exponent", Double.toString(getExponent()));
		}
		if (isSetScale()) {
			attributes.put("scale", Integer.toString(getScale()));
		}
		if (isSetMultiplier() && (getLevel() > 1)) {
			attributes.put("multiplier", Double.toString(getMultiplier()));
		}
		if (isSetOffset() && (getLevel() > 1)) {
			attributes.put("offset", Double.toString(getOffset()));
		}
		return attributes;
	}
}
