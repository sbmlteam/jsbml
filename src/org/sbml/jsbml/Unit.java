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

import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TextFormula;

/**
 * @author Andreas Dr&auml;ger <a
 *         href="mailto:andreas.draeger@uni-tuebingen.de">
 *         andreas.draeger@uni-tuebingen.de</a>
 * @date 2009-08-31
 */
public class Unit extends AbstractSBase {

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
		unit1.setExponent(e1 + e2);
		if (unit1.getKind() == Kind.METER)
			unit1.setKind(Kind.METRE);
		else if (unit1.getKind() == Kind.LITER)
			unit1.setKind(Kind.LITRE);
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
}
