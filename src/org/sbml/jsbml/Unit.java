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
import org.sbml.jsbml.util.TextFormula;

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
		 * @return the formula symbol of this unit kind in uni code notation.
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

		/**
		 * This method is equivalent to calling isDefinedIn.
		 * 
		 * @param level
		 * @param version
		 * @return
		 */
		public boolean isValidUnitKindString(int level, int version) {
			return isDefinedIn(level, version);
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
	 * Returns a UnitDefinition object which contains the argument Unit
	 * converted to the appropriate SI unit.
	 * 
	 * @param unit
	 *            the Unit object to convert to SI
	 * @return a UnitDefinition object containing the SI unit.
	 */
	public static UnitDefinition convertToSI(Unit unit) {
		// The following code has simply been ported from libSBML 4.0.1.
		double newMultiplier;
		Unit.Kind uKind = unit.getKind();
		Unit newUnit = unit.clone();
		UnitDefinition ud = new UnitDefinition(unit.getLevel(), unit
				.getVersion());
		removeScale(newUnit);

		switch (uKind) {
		case AMPERE:
			/* Ampere is the SI unit of current */
			ud.addUnit(newUnit);
			break;
		case BECQUEREL:
		case HERTZ:
			/* 1 becquerel = 1 sec^-1 = (0.1 sec)^-1 */
			/* 1 hertz = 1 sec^-1 = (0.1 sec) ^-1 */
			newUnit.setKind(Unit.Kind.SECOND);
			newUnit.setExponent(newUnit.getExponent() * -1);
			/* hack to force multiplier to be double precision */
			newMultiplier = Math.pow(newUnit.getMultiplier(), -1d);

			// ossMultiplier << newMultiplier;
			// newMultiplier = strtod(ossMultiplier.str().c_str(), null);

			newUnit.setMultiplier(newMultiplier);
			ud.addUnit(newUnit);
			break;

		case CANDELA:
			/* candela is the SI unit of luminous intensity */
			ud.addUnit(newUnit);
			break;

		case CELSIUS:
			/* 1 celsius = 1 Kelvin + 273.15 */
			newUnit.setKind(Unit.Kind.KELVIN);
			newUnit.setOffset(273.15);
			ud.addUnit(newUnit);
			break;

		case COULOMB:
			/* 1 coulomb = 1 Ampere second */
			newUnit.setKind(Unit.Kind.AMPERE);
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.SECOND);
			newUnit.setExponent(unit.getExponent());
			newUnit.setMultiplier(1);
			ud.addUnit(newUnit);
			break;

		case DIMENSIONLESS:
		case ITEM:
		case RADIAN:
		case STERADIAN:
			/* all dimensionless */
			newUnit.setKind(Unit.Kind.DIMENSIONLESS);
			ud.addUnit(newUnit);
			break;

		case FARAD:
			/* 1 Farad = 1 m^-2 kg^-1 s^4 A^2 */
			newUnit.setKind(Unit.Kind.AMPERE);
			/* hack to force multiplier to be double precision */
			newMultiplier = Math.sqrt(newUnit.getMultiplier());

			// ossMultiplier << newMultiplier;
			// newMultiplier = strtod(ossMultiplier.str().c_str(), null);

			newUnit.setMultiplier(newMultiplier);
			newUnit.setExponent(2 * newUnit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.KILOGRAM);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(-1 * unit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.METRE);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(-2 * unit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.SECOND);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(4 * unit.getExponent());
			ud.addUnit(newUnit);
			break;

		case GRAM:
			/* 1 gram = 0.001 Kg */
			newUnit.setKind(Unit.Kind.KILOGRAM);
			newUnit.setMultiplier(0.001 * newUnit.getMultiplier());
			ud.addUnit(newUnit);
			break;

		case GRAY:
		case SIEVERT:
			/* 1 Gray = 1 m^2 sec^-2 */
			/* 1 Sievert = 1 m^2 sec^-2 */
			newUnit.setKind(Unit.Kind.METRE);
			/* hack to force multiplier to be double precision */
			newMultiplier = Math.sqrt(newUnit.getMultiplier());

			// ossMultiplier << newMultiplier;
			// newMultiplier = strtod(ossMultiplier.str().c_str(), null);

			newUnit.setMultiplier(newMultiplier);
			newUnit.setExponent(2 * newUnit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.SECOND);
			newUnit.setMultiplier(1d);
			newUnit.setExponent((-2) * unit.getExponent());
			ud.addUnit(newUnit);
			break;

		case HENRY:
			/* 1 Henry = 1 m^2 kg s^-2 A^-2 */
			newUnit.setKind(Unit.Kind.AMPERE);
			/* hack to force multiplier to be double precision */
			newMultiplier = (1d / Math.sqrt(newUnit.getMultiplier()));

			// ossMultiplier << newMultiplier;
			// newMultiplier = strtod(ossMultiplier.str().c_str(), null);

			newUnit.setMultiplier(newMultiplier);
			newUnit.setExponent(-2 * newUnit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.KILOGRAM);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(unit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.METRE);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(2 * unit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.SECOND);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(-2 * unit.getExponent());
			ud.addUnit(newUnit);
			break;

		case JOULE:
			/* 1 joule = 1 m^2 kg s^-2 */
			newUnit.setKind(Unit.Kind.KILOGRAM);
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.METRE);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(2 * unit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.SECOND);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(-2 * unit.getExponent());
			ud.addUnit(newUnit);
			break;

		case KATAL:
			/* 1 katal = 1 mol s^-1 */
			newUnit.setKind(Unit.Kind.MOLE);
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.SECOND);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(-1 * unit.getExponent());
			ud.addUnit(newUnit);
			break;

		case KELVIN:
			/* Kelvin is the SI unit of temperature */
			ud.addUnit(newUnit);
			break;

		case KILOGRAM:
			/* Kilogram is the SI unit of mass */
			ud.addUnit(newUnit);
			break;

		case LITER:
		case LITRE:
			/* 1 litre = 0.001 m^3 = (0.1 m)^3 */
			newUnit.setKind(Unit.Kind.METRE);
			newUnit.setExponent(newUnit.getExponent() * 3);
			/* hack to force multiplier to be double precision */
			newMultiplier = Math
					.pow((newUnit.getMultiplier() * 0.001), 1d / 3d);

			// ossMultiplier << newMultiplier;
			// newMultiplier = strtod(ossMultiplier.str().c_str(), null);

			newUnit.setMultiplier(newMultiplier);
			ud.addUnit(newUnit);
			break;

		case LUMEN:
			/* 1 lumen = 1 candela */
			newUnit.setKind(Unit.Kind.CANDELA);
			ud.addUnit(newUnit);
			break;

		case LUX:
			/* 1 lux = 1 candela m^-2 */
			newUnit.setKind(Unit.Kind.CANDELA);
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.METRE);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(-2 * unit.getExponent());
			ud.addUnit(newUnit);
			break;

		case METER:
		case METRE:
			/* metre is the SI unit of length */
			newUnit.setKind(Unit.Kind.METRE);
			ud.addUnit(newUnit);
			break;

		case MOLE:
			/* mole is the SI unit of substance */
			ud.addUnit(newUnit);
			break;

		case NEWTON:
			/* 1 newton = 1 m kg s^-2 */
			newUnit.setKind(Unit.Kind.KILOGRAM);
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.METRE);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(unit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.SECOND);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(-2 * unit.getExponent());
			ud.addUnit(newUnit);
			break;

		case OHM:
			/* 1 ohm = 1 m^2 kg s^-3 A^-2 */
			newUnit.setKind(Unit.Kind.AMPERE);
			/* hack to force multiplier to be double precision */
			newMultiplier = (1d / Math.sqrt(newUnit.getMultiplier()));

			// ossMultiplier << newMultiplier;
			// newMultiplier = strtod(ossMultiplier.str().c_str(), null);

			newUnit.setMultiplier(newMultiplier);
			newUnit.setExponent(-2 * newUnit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.KILOGRAM);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(unit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.METRE);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(2 * unit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.SECOND);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(-3 * unit.getExponent());
			ud.addUnit(newUnit);
			break;

		case PASCAL:
			/* 1 pascal = 1 m^-1 kg s^-2 */
			newUnit.setKind(Unit.Kind.KILOGRAM);
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.METRE);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(-1 * unit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.SECOND);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(-2 * unit.getExponent());
			ud.addUnit(newUnit);
			break;

		case SECOND:
			/* second is the SI unit of time */
			ud.addUnit(newUnit);
			break;

		case SIEMENS:
			/* 1 siemens = 1 m^-2 kg^-1 s^3 A^2 */
			newUnit.setKind(Unit.Kind.AMPERE);
			/* hack to force multiplier to be double precision */
			newMultiplier = Math.sqrt(newUnit.getMultiplier());

			// ossMultiplier << newMultiplier;
			// newMultiplier = strtod(ossMultiplier.str().c_str(), null);

			newUnit.setMultiplier(newMultiplier);
			newUnit.setExponent(2 * newUnit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.KILOGRAM);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(-1 * unit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.METRE);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(-2 * unit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.SECOND);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(3 * unit.getExponent());
			ud.addUnit(newUnit);
			break;

		case TESLA:
			/* 1 tesla = 1 kg s^-2 A^-1 */
			newUnit.setKind(Unit.Kind.AMPERE);
			/* hack to force multiplier to be double precision */
			newMultiplier = (1d / newUnit.getMultiplier());

			// ossMultiplier << newMultiplier;
			// newMultiplier = strtod(ossMultiplier.str().c_str(), null);

			newUnit.setMultiplier(newMultiplier);
			newUnit.setExponent(-1 * newUnit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.KILOGRAM);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(unit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.SECOND);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(-2 * unit.getExponent());
			ud.addUnit(newUnit);
			break;

		case VOLT:
			/* 1 volt = 1 m^2 kg s^-3 A^-1 */
			newUnit.setKind(Unit.Kind.AMPERE);
			/* hack to force multiplier to be double precision */
			newMultiplier = (1d / newUnit.getMultiplier());

			// ossMultiplier << newMultiplier;
			// newMultiplier = strtod(ossMultiplier.str().c_str(), null);

			newUnit.setMultiplier(newMultiplier);
			newUnit.setExponent(-1 * newUnit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.KILOGRAM);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(unit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.METRE);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(2 * unit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.SECOND);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(-3 * unit.getExponent());
			ud.addUnit(newUnit);
			break;

		case WATT:
			/* 1 watt = 1 m^2 kg s^-3 */
			newUnit.setKind(Unit.Kind.KILOGRAM);
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.METRE);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(2 * unit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.SECOND);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(-3 * unit.getExponent());
			ud.addUnit(newUnit);
			break;

		case WEBER:
			/* 1 weber = 1 m^2 kg s^-2 A^-1 */
			newUnit.setKind(Unit.Kind.AMPERE);
			/* hack to force multiplier to be double precision */
			newMultiplier = (1d / newUnit.getMultiplier());

			// ossMultiplier << newMultiplier;
			// newMultiplier = strtod(ossMultiplier.str().c_str(), null);

			newUnit.setMultiplier(newMultiplier);
			newUnit.setExponent(-1 * newUnit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.KILOGRAM);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(unit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.METRE);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(2 * unit.getExponent());
			ud.addUnit(newUnit);
			// newUnit = new Unit(uKind, unit.getExponent(), unit.getScale(),
			// unit.getMultiplier());
			newUnit.setKind(Unit.Kind.SECOND);
			newUnit.setMultiplier(1d);
			newUnit.setExponent(-2 * unit.getExponent());
			ud.addUnit(newUnit);
			break;

		case INVALID:
			break;
		default:
			break;
		}
		return ud;
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
	 * @return if name is one of the five SBML predefined unit identifiers
	 *         ('substance', 'volume', 'area', 'length' or 'time'), false
	 *         otherwise. The predefined unit identifiers 'length' and 'area'
	 *         were added in Level 2 Version 1
	 */
	public static boolean isBuiltIn(String name, long level) {
		if ((level < 3)
				&& (name.equals("substance") || name.equals("volume")
						|| name.equals("time") || (level == 2 && (name
						.equals("length") || name.equals("area"))))) {
			return true;
		}
		return false;
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
				|| unit1.isDimensionless() || unit2.isDimensionless()) {
			// let' get rid of this offset if there is any...
			double m1 = unit1.getOffset() / Math.pow(10, unit1.getScale())
					+ unit1.getMultiplier();
			double m2 = unit2.getOffset() / Math.pow(10, unit2.getScale())
					+ unit2.getMultiplier();
			int s1 = unit1.getScale(), s2 = unit2.getScale();
			int e1 = unit1.getKind() == Kind.DIMENSIONLESS
					&& unit1.getExponent() != 1 ? 1 : unit1.getExponent();
			int e2 = unit2.getKind() == Kind.DIMENSIONLESS
					&& unit2.getExponent() != 1 ? 1 : unit2.getExponent();
			unit1.setOffset(0);
			unit1.setMultiplier(Math.pow(m1, e1) * Math.pow(m2, e2));
			unit1.setScale(s1 * e1 + s2 * e2);
			if (Kind.areEquivalent(unit1.getKind(), unit2.getKind())) {
				unit1.setExponent(e1 + e2);
				if (unit1.getExponent() != 0) {
					unit1.setMultiplier(Math.pow(unit1.getMultiplier(),
							1 / unit1.getExponent()));
					unit1.setScale(unit1.getScale() / unit1.getExponent());
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
		double m = unit.getMultiplier() * Math.pow(10, unit.getScale());
		unit.setMultiplier(m);
		unit.setScale(0);
	}

	/**
	 * Represents the 'exponent' XML attribute of an unit element.
	 */
	private Integer exponent;
	/**
	 * 
	 */
	private boolean isSetExponent = false;

	/**
	 * Represents the 'kind' XML attribute of an unit element.
	 */
	private Kind kind;

	/**
	 * Represents the 'multiplier' XML attribute of an unit element.
	 */
	private Double multiplier;
	/**
	 * 
	 */
	private boolean isSetMultiplier = false;

	/**
	 * Represents the 'offset' XML attribute of an unit element.
	 */
	@Deprecated
	private Double offset;
	/**
	 * 
	 */
	private boolean isSetOffset = false;

	/**
	 * Represents the 'scale' XML attribute of an unit element.
	 */
	private Integer scale;
	/**
	 * 
	 */
	private boolean isSetScale = false;

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
	public Unit(double multiplier, int scale, Kind kind, int exponent,
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
	 * Creates a Unit instance from a scale, kind, level and version.
	 * 
	 * @param scale
	 * @param kind
	 * @param level
	 * @param version
	 */
	public Unit(int scale, Kind kind, int level, int version) {
		this(scale, kind, 1, level, version);
		isSetExponent = false;
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
	public Unit(int scale, Kind kind, int exponent, int level, int version) {
		this(1, scale, kind, exponent, level, version);
		isSetMultiplier = false;
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
		initDefaults();
		setKind(kind);
	}

	/**
	 * Creates a Unit instance from a kind, exponent, level and version.
	 * 
	 * @param kind
	 * @param exponent
	 * @param level
	 * @param version
	 */
	public Unit(Kind kind, int exponent, int level, int version) {
		this(0, kind, exponent, level, version);
		isSetScale = false;
	}

	/**
	 * Creates a Unit instance from a given Unit.
	 * 
	 * @param unit
	 */
	public Unit(Unit unit) {
		super(unit);
		if (unit.isSetExponent()) {
			this.exponent = Integer.valueOf(unit.getExponent());
		} else {
			this.exponent = null;
		}
		if (unit.isSetKind()) {
			this.kind = unit.getKind();
		} else {
			this.kind = Kind.INVALID;
		}
		if (unit.isSetMultiplier()) {
			this.multiplier = new Double(unit.getMultiplier());
		} else {
			this.multiplier = null;
		}
		if (unit.isSetOffset()) {
			this.offset = new Double(unit.getOffset());
		} else {
			this.offset = null;
		}
		if (unit.isSetScale()) {
			this.scale = Integer.valueOf(unit.getScale());
		} else {
			this.scale = null;
		}
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
	public int getExponent() {
		return isSetExponent() ? exponent : 1;
	}

	/**
	 * 
	 * @return the kind of this Unit if it is set, null otherwise.
	 * 
	 */
	// TODO : check if we should not return empty string "2, instead of null for
	// libsbml compatibility
	public Kind getKind() {
		return kind;
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
			exponent = Integer.valueOf(1);
			scale = Integer.valueOf(0);
			multiplier = new Double(1);
			offset = new Double(0);
		} else {
			exponent = null;
			offset = null;
			multiplier = null;
			scale = null;
		}
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
		return exponent != null;
	}

	/**
	 * Predicate to test whether the 'kind' attribute of this Unit has been set.
	 * 
	 * @return
	 */
	public boolean isSetKind() {
		return kind != null;
	}

	/**
	 * 
	 * @return true if the multiplier of this Unit is not null.
	 */
	public boolean isSetMultiplier() {
		return multiplier != null;
	}

	/**
	 * 
	 * @return
	 */
	@Deprecated
	public boolean isSetOffset() {
		return offset != null;
	}

	/**
	 * 
	 * @return true if the scale of this Unit is not null.
	 */
	public boolean isSetScale() {
		return scale != null;
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
		if (kind == Unit.Kind.LITER || kind == Unit.Kind.LITRE) {
			return getOffset() == 0 && getExponent() == 1;
		}
		if (kind == Unit.Kind.METER || kind == Unit.Kind.METRE) {
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
	 * Sets the exponent of this Unit
	 * 
	 * @param exponent
	 */
	public void setExponent(int exponent) {
		isSetExponent = true;
		this.exponent = exponent;
		stateChanged();
	}

	/**
	 * Sets the kind of this Unit
	 * 
	 * @param kind
	 */
	public void setKind(Kind kind) {
		if (kind != null) {
			this.kind = kind;
			stateChanged();
		} else {
			this.kind = Kind.INVALID;
		}
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
			if (getMultiplier() != 1)
				times.append(StringTools.toString(getMultiplier()));
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
		return TextFormula.pow(times, Integer.valueOf(getExponent()))
				.toString();
	}

	/**
	 * 
	 */
	public void unsetExponent() {
		exponent = null;
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
		stateChanged();
	}

	/**
	 * 
	 */
	@Deprecated
	public void unsetOffset() {
		offset = null;
		stateChanged();
	}

	/**
	 * 
	 */
	public void unsetScale() {
		scale = null;
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
			attributes.put("exponent", Integer.toString(getExponent()));
		}
		if (isSetScale()) {
			attributes.put("scale", Integer.toString(getScale()));
		}
		if (isSetMultiplier() && getLevel() > 1) {
			attributes.put("multiplier", Double.toString(getMultiplier()));
		}
		if (isSetOffset() && getLevel() > 1) {
			attributes.put("offset", Double.toString(getOffset()));
		}
		return attributes;
	}
}
