/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2018 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.util.Maths;
import org.sbml.jsbml.util.ResourceManager;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.compilers.FormulaCompiler;

/**
 * Represents the unit XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @since 0.8
 * 
 */
public class Unit extends AbstractSBase implements UniqueSId {

  /**
   * This enumeration contains an exhaustive list of all available unit kinds
   * within all Levels and Versions of SBML. Note that some of these kinds are
   * not available in some Level/Version combinations, such as
   * {@link #AVOGADRO}, which has been defined in Level 3 for the first time,
   * or {@link #CELSIUS}, which has been removed from the specification of
   * SBML in Level 2 Version 4. This enum also provides helpful methods, for
   * instance, to check if two instances of {@link Kind} with different names
   * are still equivalent ({@link #areEquivalent(Kind, Kind)}), or to get the
   * formula symbol of the {@link Kind} ({@link #getSymbol()}).
   * 
   * @author Andreas Dr&auml;ger
   */
  public static enum Kind {
    /**
     * The ampere unit.
     */
    AMPERE,
    /**
     * This unit is DIMENSIONLESS multiplied with Avogadro's number.
     */
    AVOGADRO,
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
     * @deprecated use {@link #KELVIN} instead (1 Celsius = 1 K + 271.15)
     */
    @Deprecated
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
     * @deprecated use {@link #LITRE} instead.
     */
    @Deprecated
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
     * @deprecated use {@link #METRE} instead.
     */
    @Deprecated
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
     * Returns a set of {@link Kind} objects for the given level/version
     * combination of SBML.
     * 
     * @param level
     * @param version
     * @return A {@link Set} that only contains {@link Kind}s for the given
     *         level/version combination.
     */
    public static Set<Kind> getUnitKindsDefinedIn(int level, int version) {
      Kind[] kinds = {};
      switch (level) {
      case 1:
        switch (version) {
        case 1:
        case 2:
          kinds = new Kind[] { AMPERE, BECQUEREL, CANDELA, CELSIUS,
            COULOMB, DIMENSIONLESS, FARAD, GRAM, GRAY, HENRY,
            HERTZ, ITEM, JOULE, KATAL, KELVIN, KILOGRAM, LITER,
            LITRE, LUMEN, LUX, METER, METRE, MOLE, NEWTON, OHM,
            PASCAL, RADIAN, SECOND, SIEMENS, SIEVERT,
            STERADIAN, TESLA, VOLT, WATT, WEBER };
          break;
        default:
          break;
        }
        break;
      case 2:
        switch (version) {
        case 1:
          // Like Level 1 Version 2 without LITER and METER
          kinds = new Kind[] { AMPERE, BECQUEREL, CANDELA, CELSIUS,
            COULOMB, DIMENSIONLESS, FARAD, GRAM, GRAY, HENRY,
            HERTZ, ITEM, JOULE, KATAL, KELVIN, KILOGRAM, LITRE,
            LUMEN, LUX, METRE, MOLE, NEWTON, OHM, PASCAL,
            RADIAN, SECOND, SIEMENS, SIEVERT, STERADIAN, TESLA,
            VOLT, WATT, WEBER };
          break;
        case 2:
        case 3:
        case 4:
        case 5:
          // Like Level 2 Version 1 without CELSIUS
          kinds = new Kind[] { AMPERE, BECQUEREL, CANDELA, COULOMB,
            DIMENSIONLESS, FARAD, GRAM, GRAY, HENRY, HERTZ,
            ITEM, JOULE, KATAL, KELVIN, KILOGRAM, LITRE, LUMEN,
            LUX, METRE, MOLE, NEWTON, OHM, PASCAL, RADIAN,
            SECOND, SIEMENS, SIEVERT, STERADIAN, TESLA, VOLT,
            WATT, WEBER };
          break;
        default:
          break;
        }
        break;
      case 3:
        switch (version) {
        case 1:
        case 2:
          // like Level 2 Version 4 with additional AVOGADRO
          kinds = new Kind[] { AMPERE, AVOGADRO, BECQUEREL, CANDELA,
            COULOMB, DIMENSIONLESS, FARAD, GRAM, GRAY, HENRY,
            HERTZ, ITEM, JOULE, KATAL, KELVIN, KILOGRAM, LITRE,
            LUMEN, LUX, METRE, MOLE, NEWTON, OHM, PASCAL,
            RADIAN, SECOND, SIEMENS, SIEVERT, STERADIAN, TESLA,
            VOLT, WATT, WEBER };
          break;
        default:
          break;
        }
        break;
      default:
        break;
      }
      Set<Kind> set = new HashSet<Kind>();
      for (Kind k : kinds) {
        set.add(k);
      }
      return set;
    }

    /**
     * This method is equivalent to converting the {@link String} to a
     * {@link Kind} and then calling its {@link #isDefinedIn} method. Only
     * entirely upper or entirely lower case {@link String}s are valid
     * attributes here.
     * 
     * This method tests whether a given string corresponds to a predefined
     * {@link Unit.Kind} enumeration value.
     * 
     * To check whether a given {@link String} represents some predefined
     * {@link UnitDefinition}, please use the method
     * {@link Unit#isPredefined(String, int)}.
     * 
     * @param unitKind
     *            the unit string.
     * @param level
     *            the SBML level.
     * @param version
     *            the SBML version.
     * @return {@code true} if the given string is valid for the
     *         particular SBML level and version, {@code false} otherwise.
     * @see Unit#isPredefined(String, int)
     */
    public static boolean isValidUnitKindString(String unitKind, int level, int version) {
      if ((unitKind != null) && (unitKind.length() > 0)) {
        try {
          // We need to do that as our enum is upper case and sbml
          // kind are lower case in the SBML XML representation.
          Kind uk = Kind.valueOf(unitKind.toUpperCase()); // TODO - check how efficient this call is. Having a HashMap might be better
          return uk.isDefinedIn(level, version);
        } catch (IllegalArgumentException exc) {
          // logger.info("Unit.isValidUnitKindString - KindString = '" + unitKind + "'");
          if (logger.isDebugEnabled()) {
            logger.debug("isValidUnitKindString exception : " + exc.getMessage());
          }
        }
      }

      return false;
    }

    /**
     * Returns the name of this unit kind.
     * 
     * @return
     */
    public String getName() {
      if (this == CELSIUS) {
        return "degree " + StringTools.firstLetterUpperCase(toString().toLowerCase());
      }
      if (this == DIMENSIONLESS || this == GRAM || this == ITEM
          || this == INVALID || this == KILOGRAM || this == LUX || this == LUMEN
          || this == LITER || this == LITRE
          || this == METER || this == METRE || this == MOLE
          || this == SECOND) {
        return toString().toLowerCase();
      } else {
        return StringTools.firstLetterUpperCase(toString().toLowerCase());
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
      case OHM:  // upper case Omega character
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
      default: // AVOGADRO, DIMENSIONLESS, ITEM, INVALID:
        return toString().toLowerCase();
      }
    }

    /**
     * Creates a unit ontology identifier for this {@link Kind} if possible
     * and returns it. See <a href="http://obo.cvs.sourceforge.net/viewvc/obo/obo/ontology/phenotype/unit.obo">the Unit Ontology</a>
     * for more information.
     * 
     * @return the unit ontology identifier for this {@link Kind} or null if
     *         this {@link Kind} has no corresponding type in the unit
     *         ontology.
     * 
     * @see #getUnitOntologyNumber()
     */
    public String getUnitOntologyIdentifier() {
      int id = getUnitOntologyNumber();
      if (id > -1) {
        StringBuilder sb = new StringBuilder();
        sb.append("UO:");
        sb.append(id);
        while (sb.toString().length() < 10) {
          sb.insert(3, 0);
        }
        return sb.toString();
      }
      return null;
    }

    /**
     * Looks for the corresponding unit ontology resource for this
     * {@link Kind}. Please visit <a href="http://bioportal.bioontology.org/visualize/44519/?conceptid=UO%3A0000003">
     * http://bioportal.bioontology.org/visualize/44519/?conceptid=UO%3A0000003</a>
     * and <a href="http://www.ebi.ac.uk/miriam/main/datatypes/MIR:00000136">http://www.ebi.ac.uk/miriam/main/datatypes/MIR:00000136</a>
     * for more details.
     * 
     * @return the unit ontology number of this {@link Kind} or -1 if no
     *         entry exists for this {@link Kind} in the unit ontology.
     */
    public int getUnitOntologyNumber() {
      switch (this) {
      case AMPERE:
        return 11;
        // case AVOGADRO:
        // return "";
      case BECQUEREL:
        return 132;
      case CANDELA:
        return 14;
      case CELSIUS:
        return 27;
      case COULOMB:
        return 220;
      case DIMENSIONLESS:
        return 186;
        // case FARAD:
        // return "";
      case GRAM:
        return 21;
      case GRAY:
        return 134;
        // case HENRY:
        // return "";
      case HERTZ:
        return 106;
        // case INVALID:
        // return "";
        // case ITEM:
        // return "";
      case JOULE:
        return 112;
      case KATAL:
        return 120;
      case KELVIN:
        return 12;
      case KILOGRAM:
        return 9;
      case LITER:
      case LITRE:
        return 99;
      case LUMEN:
        return 118;
      case LUX:
        return 116;
      case METER:
      case METRE:
        return 8;
      case MOLE:
        return 13;
      case NEWTON:
        return 108;
        // case OHM:
        // return "";
      case PASCAL:
        return 110;
      case RADIAN:
        return 123;
      case SECOND:
        return 10;
      case SIEMENS:
        return 264;
      case SIEVERT:
        return 137;
      case STERADIAN:
        return 125;
      case TESLA:
        return 228;
      case VOLT:
        return 218;
      case WATT:
        return 114;
      case WEBER:
        return 226;
      default:
        return -1;
      }
    }

    /**
     * Creates a MIRIAM resource pointing to the entry in the unit ontology
     * corresponding to this {@link Kind}. If such an entry exists, this
     * method will return the {@link String}
     * {@code http://identifiers.org/unit/UO:} plus the number of the
     * resource filled to a seven-digit number by inserting leading zeros.
     * 
     * @return null if no corresponding entry exists in the unit ontology,
     *         otherwise a MIRIAM resource pointing to it.
     */
    public String getUnitOntologyResource() {
      String uo = getUnitOntologyIdentifier();
      if (uo != null) {
        return "http://identifiers.org/unit/" + uo;
        //return "urn:miriam:unit:" + uo.replace(":", "%3A");
      }
      return null;
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
      return getUnitKindsDefinedIn(level, version).contains(this);
    }
  }

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(Unit.class);

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -6335465287728562136L;

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
    return areEquivalent(unit, new Unit(1d, 0, Unit.Kind.valueOf(units), 1d, unit.getLevel(), unit.getVersion()));
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
   *         the kind and exponent attributes of unit2, {@code false} otherwise.
   */
  public static boolean areEquivalent(Unit unit1, Unit unit2) {
    return Kind.areEquivalent(unit1.getKind(), unit2.getKind())
        && (unit1.getExponent() == unit2.getExponent() || (Math.abs(unit1.getExponent() - unit2.getExponent()) < (unit1.getExponent() * 0.001)));
  } // TODO - check if we can use some of the Math or StrictMath operations when calculating exponent

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
   * @return {@code true} if all the attributes of unit1 are identical to the
   *         attributes of unit2, {@code false} otherwise.
   * @see #areEquivalent
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
      ud.addUnit(new Unit(Math.pow(mult, -1d), scale, Kind.SECOND, -exp, l, v));
      break;

    case CANDELA:
      /* Candela is the SI unit of luminous intensity */
      ud.addUnit(new Unit(mult, scale, Kind.CANDELA, exp, l, v));
      break;

    case CELSIUS:
      /* 1 degree Celsius = 1 Kelvin + 273.15 */
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
      ud.addUnit(new Unit(Math.sqrt(mult), scale, Kind.AMPERE, 2d * exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.KILOGRAM, -exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.METRE, -2d * exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.SECOND, 4d * exp, l, v));
      break;

    case GRAM:
      /* 1 gram = 0.001 Kg */
      ud.addUnit(new Unit(0.001d * mult, scale, Kind.KILOGRAM, exp, l, v));
      break;

    case GRAY:
    case SIEVERT:
      /* 1 Gray = 1 m^2 sec^-2 */
      /* 1 Sievert = 1 m^2 sec^-2 */
      ud.addUnit(new Unit(mult, scale, Kind.METRE, 2d * exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-2d) * exp, l, v));
      break;

    case HENRY:
      /* 1 Henry = 1 m^2 kg s^-2 A^-2 */
      ud.addUnit(new Unit(1d / Math.sqrt(mult), scale, Kind.AMPERE, (-2d)
        * exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.KILOGRAM, exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.METRE, 2d * exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-2d) * exp, l, v));
      break;

    case JOULE:
      /* 1 joule = 1 m^2 kg s^-2 */
      ud.addUnit(new Unit(mult, scale, Kind.KILOGRAM, exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.METRE, 2d * exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-2d) * exp, l, v));
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
      // There was a change in the multiplier which is marked as a hack in libSBML and I 
      // don't think this is necessary.
      ud.addUnit(new Unit(mult, scale -3, Kind.METRE, 3d * exp, l, v));
      break;

    case LUMEN:
      /* 1 Lumen = 1 Candela */
      ud.addUnit(new Unit(mult, scale, Kind.CANDELA, exp, l, v));
      break;

    case LUX:
      /* 1 Lux = 1 Candela * m^-2 */
      ud.addUnit(new Unit(mult, scale, Kind.CANDELA, exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.METRE, (-2d) * exp, l, v));
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
      ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-2d) * exp, l, v));
      break;

    case OHM:
      /* 1 ohm = 1 m^2 kg s^-3 A^-2 */
      ud.addUnit(new Unit(1d / Math.sqrt(mult), scale, Kind.AMPERE, (-2d) * exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.KILOGRAM, exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.METRE, 2d * exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-3d) * exp, l, v));
      break;

    case PASCAL:
      /* 1 pascal = 1 m^-1 * kg s^-2 */
      ud.addUnit(new Unit(mult, scale, Kind.KILOGRAM, exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.METRE, -exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-2d) * exp, l, v));
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
      ud.addUnit(new Unit(1d, scale, Kind.METRE, (-2d) * exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.SECOND, 3d * exp, l, v));
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
      ud.addUnit(new Unit(1d, scale, Kind.METRE, 2d * exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-3d) * exp, l, v));
      break;

    case WATT:
      /* 1 Watt = 1 m^2 * kg * s^-3 */
      ud.addUnit(new Unit(mult, scale, Kind.KILOGRAM, exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.METRE, 2d * exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-3d) * exp, l, v));
      break;

    case WEBER:
      /* 1 Weber = 1 m^2 * kg * s^-2 * A^-1 */
      ud.addUnit(new Unit(1d / mult, scale, Kind.AMPERE, -exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.KILOGRAM, exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.METRE, 2d * exp, l, v));
      ud.addUnit(new Unit(1d, scale, Kind.SECOND, (-2d) * exp, l, v));
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
   *            a {@link String} to be tested against the predefined unit
   *            names
   * @param level
   *            the Level of SBML for which the determination should be made.
   *            This is necessary because there are a few small differences in
   *            allowed units between SBML Level 1 and Level 2.
   * @return {@code true}, if name is one of the five SBML predefined
   *         unit identifiers ('substance', 'volume', 'area', 'length' or
   *         'time'), {@code false} otherwise. The predefined unit
   *         identifiers 'length' and 'area' were added in Level 2 Version 1
   * @deprecated use {@link #isPredefined(String, int)}
   */
  @Deprecated
  public static boolean isBuiltIn(String name, int level) {
    return isPredefined(name, level);
  }

  /**
   * Predicate to test whether a given string is the name of a predefined SBML
   * unit.
   * 
   * @param name
   *            a {@link String} to be tested against the predefined unit
   *            names
   * @param level
   *            the Level of SBML for which the determination should be made.
   *            This is necessary because there are a few small differences in
   *            allowed units between SBML Level 1 and Level 2.
   * @return {@code true}, if name is one of the five SBML predefined
   *         unit identifiers ('substance', 'volume', 'area', 'length' or
   *         'time'), {@code false} otherwise. The predefined unit
   *         identifiers 'length' and 'area' were added in Level 2 Version 1
   */
  public static boolean isPredefined(String name, int level) {
    if (level < 3) {
      if ((level == 2)
          && (name.equals(UnitDefinition.AREA) ||
              name.equals(UnitDefinition.LENGTH))) {
        return true;
      }
      if (name.equals(UnitDefinition.SUBSTANCE)
          || name.equals(UnitDefinition.VOLUME)
          || name.equals(UnitDefinition.TIME)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Tests whether the given {@link UnitDefinition} belongs to the predefined
   * SBML units.
   * 
   * @param ud
   *        the {@link UnitDefinition} to test.
   * @return {@code true}, if name is one of the five SBML predefined
   *         unit identifiers ('substance', 'volume', 'area', 'length' or
   *         'time'), {@code false} otherwise. The predefined unit
   *         identifiers 'length' and 'area' were added in Level 2 Version 1
   * @see #isPredefined(String, int)
   */
  public static boolean isPredefined(UnitDefinition ud) {
    return isPredefined(ud.getId(), ud.getLevel());
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
   * @return {@code true} if name is a valid UnitKind, {@code false} otherwise
   * @jsbml.note The allowed unit names differ between SBML Levels 1 and 2 and again
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
   * Returns {@code true} if the {@code unit} is a valid unit kind name or an
   * identifier of an existing {@link UnitDefinition}.
   * 
   * If either the unit or model are null, it will return false.
   * 
   * @param unit
   *            the identifier of a {@link UnitDefinition} or a valid
   *            {@link Unit.Kind} identifier for the current level/version
   *            combination of the model.
   * @param model
   *            the model where to look for the {@code unit}.
   * 
   * @return {@code true} if the unit is a valid unit kind name or an identifier of an
   *         existing {@link UnitDefinition}.
   */
  public static boolean isValidUnit(Model model, String unit) {
    boolean isValidUnit = false;

    if ((unit != null) && (model != null)) {
      unit = unit.trim();
      if (unit.length() > 0) {
        if (Kind.isValidUnitKindString(unit, model.getLevel(), model
          .getVersion())) {
          isValidUnit = true;
        } else if (model.getUnitDefinition(unit) != null) {
          isValidUnit = true;
        }
      }
    } else if ((model == null) && (unit != null)) {
      isValidUnit = true;
    }

    return isValidUnit;
  }

  /**
   * Merges two {@link Unit} objects with the same 'kind' attribute value into a
   * single {@link Unit}.
   * 
   * @param unit1
   *            the first {@link Unit} object; the result of the operation is left as
   *            a new version of this {@link Unit}, modified in-place.
   * @param unit2
   *            the second {@link Unit} object to merge with the first
   */
  public static void merge(Unit unit1, Unit unit2) {
    Kind k1 = unit1.getKind();
    Kind k2 = unit2.getKind();
    boolean equivalent = Kind.areEquivalent(k1, k2);
    if (equivalent || unit1.isDimensionless() || unit2.isDimensionless()) {
      int s1 = unit1.getScale(), s2 = unit2.getScale();
      /*
       * Let's get rid of this offset if there is any...
       * 
       * We remove the offset by expressing it within a new multiplier,
       * m-prime.
       * 
       * m-prime = offset / 10^scale + multiplier
       * 
       * When inserting this again into the unit formula, the offset
       * vanishes:
       * 
       * ((offset + multiplier * 10^scale) * unit)^exponent
       * 
       * then becomes
       * 
       * (m-prime * 10^scale * unit)^exponent
       * 
       * This is possible because offset and multiplier are real double numbers.
       */
      double m1 = unit1.getOffset() / Math.pow(10, s1) + unit1.getMultiplier();
      double m2 = unit2.getOffset() / Math.pow(10, s2) + unit2.getMultiplier();
      double e1 = k1 == Kind.DIMENSIONLESS
          && unit1.getExponent() != 0d ? 0d : unit1.getExponent();
      double e2 = k2 == Kind.DIMENSIONLESS
          && unit2.getExponent() != 0d ? 0d : unit2.getExponent();
      if (unit1.getOffset() != 0d) {
        unit1.setOffset(0d);
      }

      double newScale = s1;
      double newMultiplier = m1;
      double newExponent = e1 + e2;

      /*
       * Note how we combine units:
       * ==========================
       * 
       * We have (m_1 * 10^{s_1} * u)^e_1 and (m_2 * 10^{s_2} * u)^e_2
       * 
       * m, s, e denoting multiplier, scale and exponent for each unit.
       * u is either of identical kind or dimensionless or invalid.
       * 
       * The merged unit is:
       * 
       * (m_1^{e_1/(e_1 + e_2)} * m_2^{e_2/(e_1 + e_2)} * 10^{(s_1 * e_1 + s_2 * e_2)/(e_1 + e_2)} * u)^{e_1 + e_2}
       * 
       * Special cases occur if s_1 or s_2 equal 0.
       * 
       * It is important to know that the scale must be an integer. Hence, if the
       * fraction (s_1 * e_1 + s_2 * e_2)/(e_1 + e_2) is not exactly an integer,
       * we have to merge the scale with the multiplier and set the scale to 0.
       * 
       * Also note that the exponent of a dimensionless unit must be one,
       * even if it is the result of a cancellation of two other units, i.e.,
       * it should actually be 0.
       */

      if (newExponent != 0d) {

        /*
         * Now that we know how the new exponent must look like we have to reset
         * exponents of dimensionless units as these are defined. Otherwise
         * scales and multipliers might get lost.
         */
        if (k1 == Kind.DIMENSIONLESS) {
          e1 = unit1.getExponent();
        }
        if (k2 == Kind.DIMENSIONLESS) {
          e2 = unit2.getExponent();
        }

        if (m1 != m2) {
          newMultiplier = Math.pow(Math.pow(m1, e1) * Math.pow(m2, e2), 1d / newExponent);
        }

        if (s1 != s2) {

          double ns = Double.NaN;

          if (s1 == 0) {
            ns = s2 * e2 / newExponent;
          } else if (s2 == 0) {
            ns = s1 * e1 / newExponent;
          } else if ((e1 != 0d) && (1 + e2 != 0d)) {
            // factored out e_1 from (s_1 * e_1 + s_2 * e_2)/(e_1 + e_2)
            // for a simpler computation:
            ns = (s1 + e2 * s2 / e1) / (1 + e2 / e1);
          }

          if (Maths.isInt(ns)) {
            newScale = ns;
          } else {
            /*
             * If rounding fails, we have to remove the scale and shift it to
             * the multiplier. Otherwise, it would be too inaccurate.
             *
             *
             * This is a bit ugly, but there's no other choice,
             * because the scale can only be an integer number. This
             * is the method from libSBML:
             */
            removeScale(unit1);
            removeScale(unit2);
            newScale = 0d;
            newExponent = unit1.getExponent() + unit2.getExponent();
            if (newExponent == 0d) {
              newMultiplier = 1d;
            } else if (unit1.getMultiplier() != unit2.getMultiplier()) {
              m1 = unit1.getMultiplier();
              m2 = unit2.getMultiplier();
              e1 = unit1.getExponent();
              e2 = unit2.getExponent();
              newMultiplier = Math.pow(Math.pow(m1, e1) * Math.pow(m2, e2), 1d / newExponent);
            }
          }
        }

        // Adapt unit kind if necessary
        if (k1 == Kind.METER) {
          unit1.setKind(Kind.METRE);
        } else if (k1 == Kind.LITER) {
          unit1.setKind(Kind.LITRE);
        }

      } else {
        /*
         * If the unit has become dimentionless, mark it accordingly
         * However, we must keep scale and multiplier even in dimensionless
         * units because these could become important in later operations.
         */
        newMultiplier = Math.pow(m1, e1) * Math.pow(m2, e2); // 1d;
        newScale = s1 * e1 + s2 * e2; // 0d;
        newExponent = 1d;
        unit1.setKind(Kind.DIMENSIONLESS);
      }

      unit1.setMultiplier(newMultiplier);
      unit1.setScale((int) newScale);
      unit1.setExponent(newExponent);

    } else if (k1.equals(Kind.INVALID) || k2.equals(Kind.INVALID)) {

      // The resulting units is always invalid
      unit1.setKind(Kind.INVALID);
      
      // TODO - just set all other attributes to their default for invalid units ?
      
      if (!k2.equals(Kind.INVALID)) {
        if (unit2.isSetOffset) {
          unit1.setOffset(unit2.getOffset());
        }
        unit1.setMultiplier(unit2.getMultiplier());
        unit1.setScale(unit2.getScale());
        unit1.setExponent(unit2.getExponent());
      }
    } else {
      throw new IllegalArgumentException(MessageFormat.format(
        "Cannot merge units with different kind properties {0} and {1}. Units can only be merged if both have the same kind attribute or if one of them is dimensionless.",
        k1, k2));
    }
    // Try to shift multipliers into the scale for easier mathematical treatment of the units:
    unit1.removeMultiplier();
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
   * @deprecated the offset attribute should no longer be used.
   */
  @Deprecated
  private Double offset;

  /**
   * Represents the 'scale' XML attribute of an unit element.
   */
  private Integer scale;

  /**
   * Creates a Unit instance. If the level is set and is superior or equal to
   * 3 the multiplier, scale, kind and exponent are {@code null}.
   */
  public Unit() {
    super();
    initDefaults();
  }

  /**
   * Creates a {@link Unit} instance from a multiplier, scale, kind and exponent. The
   * offset is {@code null}.
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
    
    if (level < 2 && multiplier != 1) {
      // allowing the multiplier to be set to something else than 1 for SBML level 1 to allow
      // the validation to be done when we convert the units to SI
      this.multiplier = multiplier;
      this.isSetMultiplier = true;
    } else {
      setMultiplier(multiplier);
    }
    setScale(scale);
    setKind(kind);
    setExponent(exponent);
    offset = null;
  }

  /**
   * Creates a {@link Unit} instance from a level and version. If the level is set and
   * is superior or equal to 3 the multiplier, scale, kind, offset and
   * exponent are {@code null}.
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
    this(1d, scale, kind, exponent, level, version);
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
   * exponent are {@code null}.
   * 
   * @param kind
   * @param level
   * @param version
   */
  public Unit(Kind kind, int level, int version) {
    super(level, version);
    if ((kind == null) || !isUnitKind(kind, level, version) && (kind != Kind.INVALID)) {
      throw new IllegalArgumentException(MessageFormat.format(
        "Unit kind {0} is undefined for SBML Level {1,number,integer} Version {2,number,integer}.",
        kind != null ? kind.toString() : "null", level, version));
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public Unit clone() {
    return new Unit(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equals = super.equals(object);
    if (equals) {
      Unit u = (Unit) object;
      equals &= getMultiplier() == u.getMultiplier();
      equals &= getScale() == u.getScale();
      equals &= getExponent() == u.getExponent();
      equals &= getOffset() == u.getOffset();
      equals &= getKind() == u.getKind();
    }
    return equals;
  }

  /**
   * 
   * @return the exponent of this {@link Unit} if it is set, 1 otherwise.
   */
  public double getExponent() {
    return isSetExponent() ? exponent : 1d;
  }

  /**
   * Returns the exponent of this {@link Unit}. This method is provided for
   * compatibility to libSBML only.
   * 
   * @return
   * @deprecated use {@link #getExponent()}
   */
  @Deprecated
  public double getExponentAsDouble() {
    return getExponent();
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
   * @return the multiplier of this {@link Unit} if it is set, 1 otherwise.
   */
  public double getMultiplier() {
    return isSetMultiplier() ? multiplier : 1d;
  }

  /**
   * 
   * @return the offset of this {@link Unit} if it is set, 0 otherwise.
   * @deprecated
   */
  @Deprecated
  public double getOffset() {
    return isSetOffset() ? offset : 0d;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getParent()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ListOf<Unit> getParent() {
    return (ListOf<Unit>) super.getParent();
  }

  /**
   * @return This method returns the abbreviated prefix of this {@link Unit},
   *         for instance, "m" for milli, i.e., if the scale is -3. In case
   *         that the {@link #scale} equals zero, an empty {@link String} is
   *         returned. If no defined prefix exists for the current {@link #scale},
   *         the {@link String} {@code 10^(%d)} is returned, where
   *         {@code %d}  denotes the {@link #scale}.
   * @see #getPrefixAsWord()
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
        return "";
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
    return String.format("%d^(%d)", 10, getScale());
  }

  /**
   * @return the prefix of this {@link Unit}, for instance, "milli" if the {@link #scale} is -3.
   *         In case that the {@link #scale} equals zero, an empty {@link String} is
   *         returned. If no defined prefix exists for the current {@link #scale},
   *         the {@link String} {@code 10^(%d)} is returned, where
   *         {@code %d}  denotes the {@link #scale}.
   * @see #getPrefix()
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
        return "";
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
    return String.format("%d^(%d)", 10, getScale());
  }

  /**
   * 
   * @return the scale of this Unit if it is set, 0 otherwise.
   */
  public int getScale() {
    return isSetScale() ? scale : 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 823;
    int hashCode = super.hashCode();
    hashCode += prime * Double.valueOf(getMultiplier()).hashCode();
    hashCode += prime * getScale();
    hashCode += prime * Double.valueOf(getExponent()).hashCode();
    hashCode += prime * Double.valueOf(getOffset()).hashCode();
    if (isSetKind()) {
      hashCode += prime * getKind().hashCode();
    }
    return hashCode;
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
   * Initializes the default values using the current Level/Version configuration.
   */
  public void initDefaults() {
    initDefaults(getLevel(), getVersion());
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
   * 
   * @param level
   * @param version
   */
  public void initDefaults(int level, int version) {
    initDefaults(level, version, false);
  }

  /**
   * @param level
   * @param version
   * @param explicit
   *        switch to decide if all properties that are set by this
   *        method should also be explicitly marked as being set. For instance,
   *        if this method is invoked for Level 2 Version 4, there is a default
   *        value for most properties. If a value is not explicitly defined, the
   *        default value is used and the XML serialization can omit this
   *        attribute. Note that if the level argument exceeds two, this boolean
   *        argument is ignored, because then there is no default and everything
   *        will be set to {@code null} by this method anyway. Only the offset
   *        will not be affected by the explicit parameter because this value
   *        is optional or not even defined in certain levels of SBML and can
   *        therefore always be omitted. It has been recognized that the offset
   *        field produces more problems if it is being explicitly defined, so
   *        here it is set to zero if the level surpasses 3, but it won't be
   *        written to SBML, unless it is modified later on by the user.
   */
  public void initDefaults(int level, int version, boolean explicit) {
    kind = Kind.INVALID;
    isSetOffset = false;
    if (level < 3) {
      setExponent(1d);
      setScale(0);
      setMultiplier(1d);
      offset = new Double(0d);
      isSetExponent = isSetScale = isSetMultiplier = explicit;
    } else {
      exponent = null;
      offset = null;
      multiplier = null;
      scale = null;
      isSetExponent = isSetScale = isSetMultiplier = false;
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
   * Predicate for testing whether this Unit is of the kind avogadro.
   * 
   * @return {@code true} if the kind of this Unit is avogadro, {@code false} otherwise.
   */
  public boolean isAvogadro() {
    return kind == Kind.AVOGADRO;
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
   * @deprecated {@link Kind#CELSIUS} should no longer be used.
   */
  @Deprecated
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
   * {@link Kind#DIMENSIONLESS}. A unit is also dimensionless if it does not
   * declare an offset and at the same time its exponent is zero. In this case
   * the unit represents a dimensionless quantity.
   * 
   * @return True if this unit represents a dimensionless quantity, i.e., its
   *         {@link Kind} is {@link Kind#DIMENSIONLESS} or offset = exponent = 0
   */
  public boolean isDimensionless() {
    return (kind == Kind.DIMENSIONLESS)
        || ((getOffset() == 0d) && (getExponent() == 0d));
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
   * @return {@code true} if the kind of this Unit is litre or 'liter', {@code false}
   *         otherwise.
   */
  public boolean isLitre() {
    return (kind == Kind.LITRE) || (kind == Kind.LITER);
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
    return (kind == Kind.METRE) || (kind == Kind.METER);
  }

  /**
   * Predicate for testing whether this Unit is of the kind mole.
   * 
   * @return {@code true} if the kind of this Unit is mole, {@code false} otherwise.
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
   * @return {@code true} if the exponent of this Unit is not {@code null}.
   */
  public boolean isSetExponent() {
    return isSetExponent && (exponent != null);
  }

  /**
   * Predicate to test whether the 'kind' attribute of this {@link Unit} has been set.
   * 
   * @return
   */
  public boolean isSetKind() {
    return (kind != null) && (kind != Kind.INVALID);
  }

  /**
   * 
   * @return {@code true} if the multiplier of this Unit is not {@code null}.
   */
  public boolean isSetMultiplier() {
    return isSetMultiplier && (multiplier != null);
  }

  /**
   * 
   * @return
   * @deprecated the offset attribute should no longer be used.
   */
  @Deprecated
  public boolean isSetOffset() {
    return isSetOffset && (offset != null);
  }

  /**
   * 
   * @return {@code true} if the scale of this Unit is not {@code null}.
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
   *  Returns {@code true} if this Unit is a variant of area, meaning of {@link Kind#METRE}
   *  with an exponent of 2.
   *  
   * @return {@code true} if this Unit is a variant of area.
   */
  public boolean isVariantOfArea() {
    Kind kind = getKind();
    return (kind == Kind.METER) || (kind == Kind.METRE)
        && (getOffset() == 0d) && (getExponent() == 2d);
  }

  /**
   * 
   * @return {@code true} if this Unit is a variant of length.
   */
  public boolean isVariantOfLength() {
    Kind kind = getKind();

    // Meter only allowed in Level 1 or -1 (level undefined)
    return (getLevel() < 2 && (kind == Kind.METER)) || (kind == Kind.METRE)
        && (getOffset() == 0d) && (getExponent() == 1d);
  }

  /**
   * Tests if this {@link Unit} is a variant
   * of 'substance'.
   * 
   * <p>Returns {@code true} if this Unit is a variant of 'substance',
   * meaning moles or items (and grams or kilograms from
   * SBML Level 2 Version 2 onwards, and avogadro from L3) with only arbitrary variations in
   * scale or multiplier values. From SBML L3V2, the exponent can be different from 1.
   * {@link Kind#DIMENSIONLESS} is also an allowed
   * value but is not tested here, you have to use {@link #isDimensionless()}
   * if you want to test for dimensionless.</p>
   *         
   * @return {@code true} if this {@link Unit} is a variant of substance.
   * @see #isDimensionless()
   */
  public boolean isVariantOfSubstance() {
    Kind kind = getKind();
    if ((kind == Kind.MOLE) || (kind == Kind.ITEM)
        || ((((getLevel() == 2) && (getVersion() > 1)) || (getLevel() > 2))
            && ((kind == Kind.GRAM) || isKilogram())) || (getLevel() > 2 && kind == Kind.AVOGADRO)) 
    {
      if (getLevelAndVersion().compareTo(3, 1) <= 0) {
        return (getOffset() == 0d) && (getExponent() == 1d);
      } else {
        return (getOffset() == 0d);
      }
    }
    
    // dimensionless is an allowed kind for substance but is not tested here
    
    return false;
  }

  /**
   * Tests if this {@link Unit} is a variant
   * of 'substance'.
   * 
   * <p>Returns {@code true} if this Unit is a variant of 'substance',
   * meaning moles or items (and grams or kilograms from
   * SBML Level 2 Version 2 onwards, and avogadro from L3) with only arbitrary variations in
   * scale or multiplier values. From SBML L3V1 release 2, the exponent can be different from 1 as we can combine
   * several units.
   * {@link Kind#DIMENSIONLESS} is also an allowed
   * value but is not tested here, you have to use {@link #isDimensionless()}
   * if you want to test for dimensionless.</p>
   *         
   * @return {@code true} if this {@link Unit} is a variant of substance.
   * @see #isDimensionless()
   */
  public boolean isVariantOfSubstance(boolean checkExponent) {
    Kind kind = getKind();
    
    if ((kind == Kind.MOLE) || (kind == Kind.ITEM)
        || kind == Kind.GRAM || isKilogram() || kind == Kind.AVOGADRO) 
    {
      if (checkExponent) {
        return (getOffset() == 0d) && (getExponent() == 1d);
      } else {
        return (getOffset() == 0d);
      }
    }
    
    // dimensionless is an allowed kind for substance but is not tested here
    
    return false;
  }

  /**
   * 
   * @return
   */
  public boolean isVariantOfTime() {
    return (getKind() == Kind.SECOND) && (getOffset() == 0d)
        && (getExponent() == 1d);
  }

  /**
   * 
   * @return {@code true} if this Unit is a variant of volume.
   */
  public boolean isVariantOfVolume() {
    Kind kind = getKind();
    if ((kind == Kind.LITER) || (kind == Kind.LITRE)) {
      return (getOffset() == 0d) && (getExponent() == 1d);
    }
    if ((kind == Kind.METER) || (kind == Kind.METRE)) {
      return (getOffset() == 0d) && (getExponent() == 3d);
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals("kind")) {
        try {
          Kind kind = Kind.valueOf(value.toUpperCase());
          setKind(kind);
        } catch (Exception e) {
          isAttributeRead = false;
        }
      } else if (attributeName.equals("exponent")) {
        setExponent(getLevel() < 3 ? StringTools.parseSBMLInt(value)
          : StringTools.parseSBMLDouble(value));
      } else if (attributeName.equals("scale")) {
        setScale(StringTools.parseSBMLInt(value));
      } else if (attributeName.equals("multiplier")) {
        setMultiplier(StringTools.parseSBMLDouble(value));
      } else if (attributeName.equals("offset")) {
        setOffset(StringTools.parseSBMLDouble(value));
      } else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

  /**
   * This method tries to remove the {@link #multiplier} of this unit.
   * Nothing will happen in the case that there is no {@link #multiplier}.
   * Otherwise, it first computes the decadic logarithm of the
   * {@link #multiplier}.
   * If this logarithm is either an integer or nearly an integer (with an
   * accepted
   * error of 10<sup>-15</sup>), it is added to the {@link #scale} and the
   * {@link #multiplier} is set to 1.
   * Note that it is not always possible to remove the {@link #multiplier}
   * without loosing information. In order to avoid computational errors,
   * nothing is done if the rounding error would be too large.
   * 
   * @return a pointer to this {@link Unit}.
   */
  public Unit removeMultiplier() {
    if (isSetMultiplier()) {
      double multiplier = getMultiplier();
      if (multiplier != 1d) {
        double exp = Math.log10(multiplier);
        if (Maths.isInt(exp)) {
          setScale(getScale() + ((int) exp));
          setMultiplier(1d);
        } else {
          long round = Math.round(exp);
          if (Math.abs(round - exp) < 1E-15) {
            // 1E-15 is an acceptable noise range due to the limitation of doubles to 17 decimal positions.
            setScale(getScale() + ((int) round));
            setMultiplier(1d);
          }
        }
      }
    }
    return this;
  }

  /**
   * We remove the offset by expressing it within a new {@link #multiplier},
   * m':
   * <p>
   * m' = {@link #offset} / 10^{@link #scale} + {@link #multiplier}
   * <p>
   * When inserting this again into the unit formula, the offset vanishes:
   * <p>
   * (({@link #offset} + {@link #multiplier} * 10^{@link #scale}) *
   * {@link #kind})^{@link #exponent}
   * <p>
   * then becomes
   * <p>
   * (0 + ({@link #offset} / 10^{@link #scale} + {@link #multiplier}) * 10^{@link #scale} * {@link #kind})^{@link #exponent} =
   * <p>
   * (m' * 10^{@link #scale} * {@link #kind})^{@link #exponent}
   * <p>
   * This is possible because offset and multiplier are real double numbers.
   * 
   * @return this {@link Unit} whose {@link #offset} and {@link #multiplier}
   *         might have been changed in case that there was an {@link #offset}
   *         defined that was different to zero.
   */
  public Unit removeOffset() {
    if (isSetOffset() && (getOffset() != 0d)) {
      setMultiplier(getOffset() / Math.pow(10, getScale()) + getMultiplier());
      setOffset(0d);
    }
    return this;
  }

  /**
   * Manipulates the attributes of the Unit to express the unit with the value
   * of the scale attribute reduced to zero.
   * 
   * For example, 1 millimetre can be expressed as a Unit with kind= 'metre'
   * multiplier='1' scale='-3' exponent='1'. It can also be expressed as a
   * Unit with kind='metre' multiplier='0.001' scale='0' exponent='1'.
   * 
   * @return
   */
  public Unit removeScale() {
    if (isSetScale() && (getScale() != 0)) {
      setMultiplier(getMultiplier() * Math.pow(10, getScale()));
      setScale(0);
    }
    return this;
  }

  /**
   * Sets the exponent of this {@link Unit}
   * 
   * @param exponent
   */
  public void setExponent(double exponent) {
    Double oldExponent = this.exponent;
    isSetExponent = true;
    this.exponent = Double.valueOf(exponent);
    firePropertyChange(TreeNodeChangeEvent.exponent, oldExponent, this.exponent);
  }

  /**
   * Sets the exponent of this {@link Unit}
   * 
   * @param exponent
   * @deprecated use {@link #setExponent(double)}
   */
  @Deprecated
  public void setExponent(int exponent) {
    setExponent((double) exponent);
  }

  /**
   * Sets the {@link Kind} of this {@link Unit}
   * 
   * @param kind
   */
  public void setKind(Kind kind) {
    Kind oldKind = this.kind;
    this.kind = (kind != null) ? kind : Kind.INVALID;
    firePropertyChange(TreeNodeChangeEvent.kind, oldKind, this.kind);
  }

  /**
   * Sets the multiplier of this {@link Unit}
   * 
   * @param multiplier
   * @throws PropertyNotAvailableException
   *             if Level &lt; 2 and the given {@code multiplier != 1}.
   */
  public void setMultiplier(double multiplier) {
    if ((getLevel() < 2) && (multiplier != 1d) && !isInvalidSBMLAllowed()) {
      // added the multiplier test != 1 to prevent error being reported when it is not necessary
      throw new PropertyNotAvailableException(TreeNodeChangeEvent.multiplier, this);
    }
    
    Double oldMultiplyer = this.multiplier;
    isSetMultiplier = true;
    this.multiplier = multiplier;
    firePropertyChange(TreeNodeChangeEvent.multiplier, oldMultiplyer, this.multiplier);
  }

  /**
   * Sets the offset of this {@link Unit}
   * 
   * @param offset
   * @deprecated Only defined for SBML Level 2 Version 1.
   * @throws PropertyNotAvailableException
   *             if Level/Version combination is not 2.1.
   */
  @Deprecated
  public void setOffset(double offset) {
    if ((getLevel() == 2) && (getVersion() == 1)) {
      Double oldOffset = this.offset;
      isSetOffset = true;
      this.offset = Double.valueOf(offset);
      firePropertyChange(TreeNodeChangeEvent.offset, oldOffset, this.offset);
    } else {
      throw new PropertyNotAvailableException(TreeNodeChangeEvent.offset, this);
    }
  }

  /**
   * Sets the scale of this Unit
   * 
   * @param scale
   */
  public void setScale(int scale) {
    Integer oldScale = this.scale;
    isSetScale = true;
    this.scale = scale;
    firePropertyChange(TreeNodeChangeEvent.scale, oldScale, scale);
  }

  /**
   * Produces a text formula representation of this unit.
   */
  public String printUnit() {
    StringBuffer times = new StringBuffer();
    if (getMultiplier() != 0d) {
      if (getMultiplier() != 1d) {
        times.append(StringTools.toString(getMultiplier()));
      }
      StringBuffer pow = new StringBuffer();
      pow.append(kind != null ? kind.getSymbol() : "undefined");
      String prefix = getPrefix();
      if ((prefix.length() > 0) && !prefix.startsWith("10")) {
        pow.insert(0, prefix);
      } else if (getScale() != 0) {
        pow = FormulaCompiler.times(FormulaCompiler.pow(Integer.valueOf(10),
          getScale()), pow);
      }
      times = FormulaCompiler.times(times, pow);
    }
    if ((offset != null) && (offset.doubleValue() != 0d)) {
      times = FormulaCompiler.sum(StringTools.toString(offset.doubleValue()),
        times);
    }
    return FormulaCompiler.pow(times, StringTools.toString(getExponent())).toString();
  }

  /**
   * 
   */
  public void unsetExponent() {
    Double oldExponent = exponent;
    exponent = null;
    isSetExponent = false;
    firePropertyChange(TreeNodeChangeEvent.exponent, oldExponent, exponent);
  }

  /**
   * 
   */
  public void unsetKind() {
    Kind oldKind = kind;
    kind = Kind.INVALID;
    firePropertyChange(TreeNodeChangeEvent.kind, oldKind, kind);
  }

  /**
   * 
   */
  public void unsetMultiplier() {
    Double oldMultipler = multiplier;
    multiplier = null;
    isSetMultiplier = false;
    firePropertyChange(TreeNodeChangeEvent.multiplier, oldMultipler,
      multiplier);
  }

  /**
   * @deprecated the offset attribute should no longer be used.
   */
  @Deprecated
  public void unsetOffset() {
    Double oldOffset = offset;
    offset = null;
    isSetOffset = false;
    firePropertyChange(TreeNodeChangeEvent.offset, oldOffset, offset);
  }

  /**
   * 
   */
  public void unsetScale() {
    Integer oldScale = scale;
    scale = null;
    isSetScale = false;
    firePropertyChange(TreeNodeChangeEvent.scale, oldScale, scale);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    int level = getLevel(), version = getVersion();
    Locale en = Locale.ENGLISH;

    if (isSetKind()) {
      attributes.put("kind", getKind().toString().toLowerCase());
    }
    if (isSetScale()) {
      attributes.put("scale", Integer.toString(getScale()));
    }
    if (1 < level) {
      if (isSetMultiplier()) {
        attributes.put("multiplier", StringTools.toString(en, getMultiplier()));
      }
    }
    if ((level == 2) && (version == 1)) {
      if (isSetOffset()) {
        attributes.put("offset", StringTools.toString(en, getOffset()));
      }
    }
    if (isSetExponent()) {
      if (2 < level) {
        attributes.put("exponent", StringTools.toString(en, getExponent()));
      } else {
        int exponent = (int) getExponent();
        attributes.put("exponent", Integer.toString((int) getExponent()));
        if (exponent - getExponent() != 0d) {
          logger.warn(MessageFormat.format(
            ResourceManager.getBundle("org.sbml.jsbml.resources.cfg.Messages").getString("LOSS_OF_INFORMATION_DUE_TO_ROUNDING"),
            getExponent(), exponent));
        }
      }
    }
    return attributes;
  }

}
