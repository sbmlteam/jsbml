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
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.CVTerm.Qualifier;
import org.sbml.jsbml.ListOf.Type;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.util.StringTools;
import org.sbml.jsbml.util.TreeNodeChangeListener;
import org.sbml.jsbml.util.ValuePair;

/**
 * Represents the unitDefinition XML element of a SBML file.
 * 
 * @author Andreas Dr&auml;ger
 * @author Marine Dumousseau
 * @since 0.8
 * 
 */
public class UnitDefinition extends AbstractNamedSBase {

  /**
   * Identifier of the (for SBML Level 2) predefined {@link UnitDefinition}
   * {@code area}.
   */
  public static final String            AREA             = "area";
  /**
   * Identifier of the (for SBML Level 3) {@link UnitDefinition}
   * {@code extent}.
   */
  public static final String            EXTENT           = "extent";
  /**
   * Identifier of the (for SBML Level 2) predefined {@link UnitDefinition}
   * {@code length}.
   */
  public static final String            LENGTH           = "length";
  /**
   * The logger for this class.
   */
  private static final transient Logger logger           =
      Logger.getLogger(UnitDefinition.class);
  /**
   * Generated serial version identifier.
   */
  public static final long              serialVersionUID =
      -4705380036260408123L;
  /**
   * Identifier of the (for the SBML Levels 1 and 2) predefined
   * {@link UnitDefinition} {@code substance}.
   */
  public static final String            SUBSTANCE        = "substance";
  /**
   * Identifier of the (for the SBML Levels 1 and 2) predefined
   * {@link UnitDefinition} {@code time}.
   */
  public static final String            TIME             = "time";
  /**
   * Identifier of the (for some SBML Levels 1 and 2) predefined
   * {@link UnitDefinition} {@code volume}.
   */
  public static final String            VOLUME           = "volume";

  /**
   * The suffix used in {@link UnitDefinition} objects that represent a base
   * {@link Unit.Kind} with offset = 0, multiplier = 1, scale = 0, and exponent
   * = 1. Since SBML does not allow that the identifier of a
   * {@link UnitDefinition} equals the name of a base unit, this suffix is used
   * to internally store simple units in form of a composite
   * {@link UnitDefinition}.
   */
  public static final String            BASE_UNIT_SUFFIX = "_base";


  /**
   * Returns the predefined unit for area.
   * 
   * @param level the SBML level
   * @param version the SBML version
   * @return the predefined unit for area.
   */
  public static final UnitDefinition area(int level, int version) {
    return getPredefinedUnit(AREA, level, version);
  }


  /**
   * <p>
   * Returns {@code true} or {@code false} depending on
   * whether two UnitDefinition objects are compatible.
   * </p>
   * <p>
   * For the purposes of performing this comparison, two
   * {@link UnitDefinition} objects are considered compatible when they
   * contain compatible list of {@link Unit} objects. This means two
   * {@link UnitDefinition} objects are compatible if both satisfy the method
   * {@link #areEquivalent(UnitDefinition, UnitDefinition)} or one of both has
   * {@link Kind#INVALID} as {@link Unit.Kind}
   * 
   * @param ud1
   *        the first {@link UnitDefinition} object to compare
   * @param ud2
   *        the second {@link UnitDefinition} object to compare
   * @return {@code true} if all the {@link Unit} objects in ud1 are
   *         compatible to the {@link Unit} objects in ud2, {@code false}
   *         otherwise.
   * @see #areIdentical(UnitDefinition, UnitDefinition)
   * @see Unit#areEquivalent(Unit, Unit)
   */
  public static boolean areCompatible(UnitDefinition ud1, UnitDefinition ud2) {
    return areEquivalent(ud1, ud2) || ud2.isInvalid() || ud1.isInvalid();
  }


  /**
   * Checks whether the given {@link UnitDefinition} and the
   * {@link UnitDefinition} or {@link Unit} represented by the given
   * {@link String} are equivalent.
   * 
   * @param ud a {@link UnitDefinition}
   * @param units a String representing either a {@link UnitDefinition} id or a unit {@link Kind}.
   * @return {@code true} if both units are equivalent
   * @see #areEquivalent(UnitDefinition, UnitDefinition)
   * @see Unit#areEquivalent(Unit, Unit)
   */
  public static boolean areEquivalent(UnitDefinition ud, String units) {
    UnitDefinition ud2 = ud.getModel().getUnitDefinition(units);
    if (ud2 != null) {
      return areEquivalent(ud, ud2);
    } else if (ud.isUnitKind()
        && Unit.isUnitKind(units, ud.getLevel(), ud.getVersion())) {
      return Unit.areEquivalent(ud.getUnit(0), units);
    }

    return false;
  }


  /**
   * <p>
   * Returns true or false depending on whether two
   * {@link UnitDefinition} objects are equivalent.
   * </p>
   * <p>
   * For the purposes of performing this comparison, two {@link UnitDefinition}
   * objects are considered equivalent when they contain equivalent list of
   * {@link Unit} objects. {@link Unit} objects are in turn considered
   * equivalent if they satisfy the predicate
   * {@link Unit#areEquivalent(Unit, Unit)}. The predicate tests a subset of the
   * objects's attributes.
   * </p>
   * 
   * @param ud1
   *        the first {@link UnitDefinition} object to compare
   * @param ud2
   *        the second {@link UnitDefinition} object to compare
   * @return {@code true} if all the Unit objects in ud1 are equivalent to
   *         the {@link Unit} objects in ud2, {@code false} otherwise.
   * @see #areIdentical(UnitDefinition, UnitDefinition)
   * @see Unit#areEquivalent(Unit, String)
   */
  public static boolean areEquivalent(UnitDefinition ud1, UnitDefinition ud2) {
    UnitDefinition ud1clone = ud1.clone().convertToSIUnits();
    UnitDefinition ud2clone = ud2.clone().convertToSIUnits();
    
    if (ud1clone.getUnitCount() == ud2clone.getUnitCount()) {
      boolean equivalent = true;
      for (int i = 0; i < ud1clone.getUnitCount(); i++) {
        equivalent &=
            Unit.areEquivalent(ud1clone.getUnit(i), ud2clone.getUnit(i));
      }
      return equivalent;
    }
    return false;
  }


  /**
   * <p>
   * Returns {@code true} or {@code false} depending on
   * whether two {@link UnitDefinition} objects are identical.
   * </p>
   * <p>
   * For the purposes of performing this comparison, two {@link UnitDefinition}
   * objects are considered identical when they contain identical lists of
   * {@link Unit} objects. Pairs of {@link Unit} objects in the lists are in
   * turn considered identical if they satisfy the predicate
   * {@link Unit#areIdentical(Unit, Unit)}. The predicate compares every
   * attribute of the {@link Unit} objects.
   * </p>
   * 
   * @param ud1
   *        the first {@link UnitDefinition} object to compare
   * @param ud2
   *        the second {@link UnitDefinition} object to compare
   * @return {@code true} if all the {@link Unit} objects in ud1 are
   *         identical to the {@link Unit} objects of ud2, {@code false}
   *         otherwise.
   */
  public static boolean areIdentical(UnitDefinition ud1, UnitDefinition ud2) {
    UnitDefinition ud1clone = ud1.clone().simplify();
    UnitDefinition ud2clone = ud2.clone().simplify();
    if (ud1clone.getUnitCount() == ud2clone.getUnitCount()) {
      boolean identical = true;
      for (int i = 0; (i < ud1clone.getUnitCount()) && identical; i++) {
        identical &=
            Unit.areIdentical(ud1clone.getUnit(i), ud2clone.getUnit(i));
      }
      return identical;
    }
    return false;
  }


  /**
   * Returns the predefined unit with the given identifier for the
   * specified level and version combination or null if either for the given
   * combination of level and version there is no such predefined unit or the
   * identifier is not one of those belonging to the group of predefined unit
   * definitions.
   * 
   * @param id
   *        one of the values
   *        <ul>
   *        <li>substance</li>
   *        <li>volume</li>
   *        <li>area</li>
   *        <li>length</li>
   *        <li>time</li>
   *        <li>any of the basic kind</li>
   *        </ul>
   * @param level
   *        a number greater than zero.
   * @param version
   *        a number greater than zero.
   * @return The predefined unit definition with the given identifier for the
   *         specified level version combination or null if no such predefined
   *         unit exists.
   */
  public static final UnitDefinition getPredefinedUnit(String id, int level,
    int version) {

    if (id == null) {
      logger.warn("Cannot create predefined unit object with id = null.");
      return null;
    } else if (!(isPredefined(id, level)
        || (Unit.isUnitKind(id, level, version)))) {
      logger.warn(MessageFormat.format(
        "No such predefined unit ''{0}'' in SBML Level {1,number,integer}.", id,
        level));
      return null;
    }

    id = id.toLowerCase();
    Unit u = new Unit();
    // prevent exception when loading invalid model
    u.putUserObject(JSBML.ALLOW_INVALID_SBML, Boolean.TRUE);
    u.setLevel(level);
    u.setVersion(version);

    // explicitly set default values:
    u.initDefaults(2, 4, true);

    if (id.equals(SUBSTANCE)) {
      u.setKind(Kind.MOLE);
    } else if (id.equals(VOLUME)) {
      u.setKind(Kind.LITRE);
    } else if (id.equals(AREA)) {
      u.setKind(Kind.METRE);
      u.setExponent(2d);
    } else if (id.equals(LENGTH)) {
      u.setKind(Kind.METRE);
    } else if (id.equals(TIME)) {
      u.setKind(Kind.SECOND);
    } else {
      Kind kind = null;
      try {
        kind = Kind.valueOf(id.toUpperCase());
      } catch (IllegalArgumentException exc) {
        logger.warn(MessageFormat.format(
          "No such unit kind ''{0}'' in SBML Level {1,number,integer} Version {2,number,integer}",
          id, level, version));
        return null;
      }
      u.setKind(kind);
    }
    if ((level > 1) && (version > 1)) {
      String resource = u.getKind().getUnitOntologyResource();
      if (resource != null) {
        // metaid will be created upon necessity.
        u.addCVTerm(new CVTerm(Qualifier.BQB_IS, resource));
      }
    }
    String name = " unit " + id;
    if (!Unit.isPredefined(id, level)) {
      id += UnitDefinition.BASE_UNIT_SUFFIX;
      name = "Base" + name;
    } else {
      name = "Predefined" + name;
    }
    UnitDefinition ud = new UnitDefinition(id);
    // prevent exception when loading invalid model
    ud.putUserObject(JSBML.ALLOW_INVALID_SBML, Boolean.TRUE);
    ud.setLevel(level);
    ud.setVersion(version);

    ud.setName(name);
    ud.addUnit(u);
    
    u.removeUserObject(JSBML.ALLOW_INVALID_SBML);
    ud.removeUserObject(JSBML.ALLOW_INVALID_SBML);
    
    return ud;
  }


  /**
   * Tests if the given unit is a predefined unit, meaning it is one of the five SBML predefined
   * unit identifiers ('substance', 'volume', 'area', 'length' or 'time').
   * 
   * @param ud a unit definition
   * @return {@code true} if the given unit is a predefined unit.
   * @deprecated use {@link #isPredefined()}
   */
  @Deprecated
  public static boolean isBuiltIn(UnitDefinition ud) {
    return isPredefined(ud);
  }


  /**
   * Tests if the given String is a predefined unit, meaning it is one of the five SBML predefined
   * unit identifiers ('substance', 'volume', 'area', 'length' or 'time').
   * 
   * @param name a unit String
   * @param level the SBML level
   * @return {@code true} if the given unit is a predefined unit.
   * @see Unit#isPredefined(String, int)
   */
  public static boolean isPredefined(String name, int level) {
    return Unit.isPredefined(name, level);
  }


  /**
   * Tests if the given unit is a predefined unit, meaning it is one of the five SBML predefined
   * unit identifiers ('substance', 'volume', 'area', 'length' or 'time').
   * 
   * @param ud a unit definition
   * @return {@code true} if the given unit is a predefined unit.
   */
  public static boolean isPredefined(UnitDefinition ud) {
    if (ud.getLevel() > 2) {
      return false;
    }
    if (ud.getUnitCount() == 1) {
      UnitDefinition predef =
          getPredefinedUnit(ud.getId(), ud.getLevel(), ud.getVersion());
      if ((predef != null) && Unit.isPredefined(ud.getId(), ud.getLevel())) {
        return ud.equals(predef);
      }
    }
    return false;
  }


  /**
   * Returns the predefined unit for length.
   * 
   * @param level the SBML level
   * @param version the SBML version
   * @return the predefined unit for length.
   */
  public static final UnitDefinition length(int level, int version) {
    return getPredefinedUnit(LENGTH, level, version);
  }


  /**
   * Returns a string that expresses the unit definition represented by this
   * UnitDefinition object.
   * 
   * @param ud
   *        the UnitDefinition object
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
   *        the UnitDefinition object
   * @param compact
   *        boolean indicating whether the compact form should be used
   *        (defaults to false)
   * @return a string expressing the unit definition
   */
  public static String printUnits(UnitDefinition ud, boolean compact) {
    StringBuilder sb = new StringBuilder();
    if (ud == null) {
      sb.append("null");
    } else {
      for (int i = 0; i < ud.getUnitCount(); i++) {
        Unit unit = ud.getUnit(i);
        if (i > 0) {
          sb.append('*'); // multiplication dot \u22c5.
        }
        if (compact) {
          sb.append(unit.printUnit());
        } else {
          sb.append(unit.getKind().getName().toLowerCase());
          sb.append(MessageFormat.format(
            " (exponent = {0}, multiplier = {1}, scale = {2})",
            StringTools.toString(unit.getExponent()),
            StringTools.toString(unit.getMultiplier()), unit.getScale()));
        }
      }
    }
    return sb.toString();
  }


  /**
   * Orders alphabetically the {@link Unit} objects within the
   * {@link #listOfUnits} of a {@link UnitDefinition}.
   * 
   * @param ud
   *        the {@link UnitDefinition} object whose {@link Unit}s are to be
   *        reordered.
   */
  public static void reorder(UnitDefinition ud) {
    if (1 < ud.getUnitCount()) {
      ListOf<Unit> orig = ud.getListOfUnits();
      ListOf<Unit> units = new ListOf<Unit>(ud.getLevel(), ud.getVersion());
      units.setSBaseListType(Type.listOfUnits);
      orig.removeAllTreeNodeChangeListeners();
      units.add(orig.remove(orig.size() - 1));
      int i, j;
      for (i = orig.size() - 1; i >= 0; i--) {
        Unit u = orig.remove(i);
        j = 0;
        while ((j < units.size())
            && (0 < u.getKind().compareTo(units.get(j).getKind()))) {
          j++;
        }
        units.add(j, u);
      }
      ud.setListOfUnits(units);
    }
  }


  /**
   * Returns a simplified version of the given {@link UnitDefinition}.
   * 
   * @param ud a unit definition
   * @return a simplified version of the given {@link UnitDefinition}. In order
   *         to make sure that the original {@link UnitDefinition} is not
   *         changed, it is cloned before the simplification.
   */
  public static UnitDefinition simplify(UnitDefinition ud) {
    return ud.clone().simplify();
  }


  /**
   * Returns the predefined unit for substance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   * @return the predefined unit for substance.
   */
  public static final UnitDefinition substance(int level, int version) {
    return getPredefinedUnit(SUBSTANCE, level, version);
  }


  /**
   * Returns the predefined unit for time.
   * 
   * @param level the SBML level
   * @param version the SBML version
   * @return the predefined unit for time.
   */
  public static final UnitDefinition time(int level, int version) {
    return getPredefinedUnit(TIME, level, version);
  }


  /**
   * Returns the predefined unit for volume.
   * 
   * @param level the SBML level
   * @param version the SBML version
   * @return the predefined unit for volume.
   */
  public static final UnitDefinition volume(int level, int version) {
    return getPredefinedUnit(VOLUME, level, version);
  }

  /**
   * Represents the 'listOfUnit' XML sub-element of a UnitDefinition.
   */
  private ListOf<Unit> listOfUnits;


  /**
   * Creates an UnitDefinition instance. By default, the listOfUnit is null.
   */
  public UnitDefinition() {
    super();
  }


  /**
   * Creates a new {@link UnitDefinition} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public UnitDefinition(int level, int version) {
    super(level, version);
  }


  /**
   * Creates a new {@link UnitDefinition} instance.
   * 
   * @param id the unit definition id.
   */
  public UnitDefinition(String id) {
    super(id);
  }


  /**
   * Creates an {@link UnitDefinition} instance from an id, level and version. By
   * default, the listOfUnit is null.
   * 
   * @param id the unit definition id.
   * @param level the SBML level
   * @param version the SBML version
   */
  public UnitDefinition(String id, int level, int version) {
    super(id, level, version);
  }


  /**
   * Creates an {@link UnitDefinition} instance from an id, level and version. By
   * default, the listOfUnit is null.
   * 
   * @param id the unit definition id.
   * @param name the unit definition name.
   * @param level the SBML level
   * @param version the SBML version
   */
  public UnitDefinition(String id, String name, int level, int version) {
    super(id, name, level, version);
  }


  /**
   * Creates an {@link UnitDefinition} instance from a given UnitDefinition.
   * 
   * @param unitDefinition the {@link UnitDefinition} to clone.
   */
  public UnitDefinition(UnitDefinition unitDefinition) {
    super(unitDefinition);
    if (unitDefinition.isSetListOfUnits()) {
      setListOfUnits(unitDefinition.getListOfUnits().clone());
    }
  }


  /**
   * Adds a new {@link Unit} instance, created from the given unit {@link Kind}, to this
   * {@link UnitDefinition}.
   * 
   * @param unit the unit kind.
   */
  public void addUnit(String unit) {
    addUnit(Unit.Kind.valueOf(unit));
  }


  /**
   * Adds a {@link Unit} to this {@link UnitDefinition}.
   * 
   * @param u the unit to add.
   */
  public void addUnit(Unit u) {
    getListOfUnits().add(u);
  }


  /**
   * Adds a new {@link Unit} instance, with the given kind, to this {@link UnitDefinition}.
   * 
   * @param kind the unit kind.
   */
  public void addUnit(Unit.Kind kind) {
    addUnit(new Unit(1d, 0, kind, 1d, getLevel(), getVersion()));
  }


  /**
   * Removes all {@link Unit} elements from the list of Units in this object.
   */
  public void clear() {
    getListOfUnits().clear();
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.element.AbstractSBase#clone()
   */
  @Override
  public UnitDefinition clone() {
    return new UnitDefinition(this);
  }

  /**
   * Converts this unit definition to using only SI units and then call {@link #simplify()}.
   * 
   * @return itself for convenience
   */
  public UnitDefinition convertToSIUnits() {
    return convertToSIUnits(true);
  }
  
  /**
   * Converts this unit definition to using only SI units. It will call {@link #simplify()}
   * if {@code true} as been given as argument.
   * 
   * @param simplify whether to simplify the UnitDefinition or not after converting it to SI.
   * @return itself for convenience
   */
  public UnitDefinition convertToSIUnits(boolean simplify) {

    UnitDefinition ud[] = new UnitDefinition[getUnitCount()];    
    Set<TreeNodeChangeListener> listeners =
        new HashSet<TreeNodeChangeListener>(getListOfTreeNodeChangeListeners());
    removeAllTreeNodeChangeListeners();
    for (int i = ud.length - 1; i >= 0; i--) {
      ud[i] = Unit.convertToSI(removeUnit(i));
    }
    for (UnitDefinition u : ud) {
      for (Unit unit : u.getListOfUnits()) {
        unit.parent = null; // setting this to avoid a warning when adding the unit to the new ListOf
        getListOfUnits().add(unit);
        
        if (isInvalidSBMLAllowed()) {
          unit.putUserObject(JSBML.ALLOW_INVALID_SBML, Boolean.TRUE);
        }
      }
    }
    
    if (simplify) {
      simplify();
    }
    addAllChangeListeners(listeners);
    
    return this;
  }

  /**
   * Converts this unit definition to using only SI units or valid SBML substance units.
   * 
   * <p>It means that 'item' or 'gram' won't be transformed, it is in particular important for
   * 'item' as it get transformed into 'dimensionless' and it is not allowed in all level and version
   * where 'item' is allowed.</p>
   * 
   * @return itself for convenience
   */
  public UnitDefinition convertToSIAndSubstanceUnits() {
    UnitDefinition ud[] = new UnitDefinition[getUnitCount()];
    Set<TreeNodeChangeListener> listeners =
        new HashSet<TreeNodeChangeListener>(getListOfTreeNodeChangeListeners());
    removeAllTreeNodeChangeListeners();
    
    for (int i = ud.length - 1; i >= 0; i--) {
      Unit unit = removeUnit(i);
      
      if (unit.getKind() == Kind.ITEM || unit.getKind() == Kind.GRAM) {
        // We don't modify the unit
        UnitDefinition tempUD = new UnitDefinition(getLevel(), getVersion());
        tempUD.addUnit(unit);
        
        ud[i] = tempUD;
        
      } else {
        ud[i] = Unit.convertToSI(unit);
      }
    }
    for (UnitDefinition u : ud) {
      for (Unit unit : u.getListOfUnits()) {
        unit.parent = null; // setting this to avoid a warning when adding the unit to the new ListOf
        getListOfUnits().add(unit);
      }
    }
    simplify();
    addAllChangeListeners(listeners);
    
    return this;
  }


  
  /**
   * Creates a new {@link Unit} instance with the {@link Unit.Kind#INVALID} kind
   * and adds
   * it to this {@link UnitDefinition}.
   * 
   * @return a new {@link Unit} instance with the {@link Unit.Kind#INVALID}
   *         kind.
   * @see #createInvalidUnit()
   */
  public Unit createUnit() {
    return createInvalidUnit();
  }


  /**
   * Creates a new {@link Unit} instance with the {@link Unit.Kind#INVALID} kind
   * and adds
   * it to this {@link UnitDefinition}.
   * 
   * @return a new {@link Unit} instance with the {@link Unit.Kind#INVALID}
   *         kind.
   */
  public Unit createInvalidUnit() {
    return createUnit(Unit.Kind.INVALID);
  }


  /**
   * Creates a new {@link Unit} instance with the given {@link Unit.Kind} and
   * adds
   * it to this {@link UnitDefinition}.
   * 
   * @param kind
   *        the kind to be set in the new Unit.
   * @return a new {@link Unit} instance with the given {@link Unit.Kind}.
   */
  public Unit createUnit(Unit.Kind kind) {
    Unit unit = new Unit(1d, 0, kind, 1d, getLevel(), getVersion());
    addUnit(unit);

    return unit;
  }


  /**
   * Divides this unit definition by the second unit definition.
   * 
   * <p> The current {@link UnitDefinition} will be modified in the process.
   * 
   * @param definition a unit definition
   * @return the modified {@link UnitDefinition} instance for convenience
   */
  public UnitDefinition divideBy(UnitDefinition definition) {
    
    // Making sure to return an invalid unit if either the numerator or denominator is invalid.
    if (definition == null || definition.isInvalid()) {
      unsetListOfUnits();
      createInvalidUnit();
      return this;
    }
    if (isInvalid()) {
      return this;
    }
    
    // Avoid creation of not needed empty list:
    if (definition.isSetListOfUnits()) {
      for (Unit unit1 : definition.getListOfUnits()) {
        Unit unit = unit1.clone();
        if (!(unit.isDimensionless() || unit.isInvalid())) {
          unit.setExponent(-unit1.getExponent());
          
//          if (unit1.isSetScale()) {
//            unit.setScale(-unit1.getScale());
//          }
          // TODO - multiplier as well ? (1 / multiplier) 
          // TODO - check libsbml code for dividing units
        }
        boolean contains = false;
        for (int i = getUnitCount() - 1; (i >= 0) && !contains; i--) {
          Unit u = getUnit(i);
          if (Unit.Kind.areEquivalent(u.getKind(), unit.getKind())
              || u.isDimensionless() || unit.isDimensionless() || u.isInvalid()
              || unit.isInvalid()) {
            if (u.isDimensionless()) {
              Unit.merge(unit, removeUnit(i));
              break;
            } else {
              Unit.merge(u, unit);
              contains = true;
            }
          }
        }
        if (!contains) {
          unit.unsetMetaId();
          addUnit(unit);
        }
      }
    }
    return this;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }
    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    if (listOfUnits != null) {
      if (index == pos) {
        return getListOfUnits();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      +Math.min(pos, 0)));
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int children = super.getChildCount();
    if (listOfUnits != null) {
      children++;
    }
    return children;
  }


  /**
   * @return the listOfUnits of this UnitDefinition. Can be empty.
   */
  public ListOf<Unit> getListOfUnits() {
    if (listOfUnits == null) {
      listOfUnits = ListOf.newInstance(this, Unit.class);
      registerChild(listOfUnits);
    }
    return listOfUnits;
  }


  /**
   * @return the number of Unit.
   * @libsbml.deprecated use {@link #getUnitCount()}
   */
  public int getNumUnits() {
    return getUnitCount();
  }


  /**
   * @return the number of Unit.
   */
  public int getUnitCount() {
    return isSetListOfUnits() ? listOfUnits.size() : 0;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getParent()
   */
  @Override
  @SuppressWarnings("unchecked")
  public ListOf<UnitDefinition> getParent() {
    return (ListOf<UnitDefinition>) super.getParent();
  }


  /**
   * Returns a specific Unit instance belonging to this UnitDefinition.
   * 
   * @param i
   *        an integer, the index of the Unit to be returned.
   * @return the ith Unit of this UnitDefinition
   */
  public Unit getUnit(int i) {
    return getListOfUnits().get(i);
  }


  /**
   * Tests if this unit definition is a predefined unit.
   * 
   * @return {@code true} if this unit definition is a predefined unit.
   * @deprecated use {@link #isPredefined()}
   */
  @Deprecated
  public boolean isBuiltIn() {
    return isBuiltIn(this);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return true;
  }


  /**
   * Checks if this UnitDefinition is invalid.
   * 
   * @return {@code true} if this UnitDefinition is invalid.
   */
  public boolean isInvalid() {
    UnitDefinition ud = clone().simplify();

    if (ud.getUnitCount() == 1) {
      return ud.getUnit(0).isInvalid();
    }

    return false;

  }

  /**
   * Returns {@code true} if the listOfUnits is empty.
   * 
   * @return {@code true} if the listOfUnits is empty.
   */
  public boolean isListOfUnitsEmpty() {
    return (listOfUnits != null) && (listOfUnits.size() == 0);
  }

  /**
   * Tests if this unit definition is a predefined unit.
   * 
   * @return {@code true} if this unit definition is a predefined unit.
   */
  public boolean isPredefined() {
    return isPredefined(this);
  }


  /**
   * Returns {@code true} if the listOfUnits is not null and not empty.
   * 
   * @return {@code true} if the listOfUnits is not null and not empty.
   */
  public boolean isSetListOfUnits() {
    return (listOfUnits != null) && (listOfUnits.size() > 0);
  }


  /**
   * Tests whether this {@link UnitDefinition} contains
   * exactly one {@link Unit} that itself represents a {@link Kind}, i.e.,
   * multiplier = 1, exponent = 1, scale = 1. Note that this method requires
   * the level and version attributes of this {@link UnitDefinition} to be
   * set.
   * 
   * @return {@code true} if this {@link UnitDefinition} contains
   * exactly one {@link Unit} that itself represents a simple {@link Kind}.
   */
  public boolean isUnitKind() {
    if (getUnitCount() == 1) {
      return Unit.isUnitKind(getUnit(0).getKind(), getLevel(), getVersion());
    }
    return false;
  }


  /**
   * Returns {@code true} if this UnitDefinition is a variant of Area
   * 
   * @return {@code true} if this UnitDefinition is a variant of Area
   */
  public boolean isVariantOfArea() {
    boolean isVariantOfArea = false; 

    if (isSetListOfUnits()) {
      UnitDefinition ud = clone(); 
      
      if (isInvalidSBMLAllowed()) {
        ud.putUserObject(JSBML.ALLOW_INVALID_SBML, Boolean.TRUE);
      }
      
      ud.convertToSIUnits();

      if (ud.getUnitCount() == 1) {
        Unit unit = ud.getUnit(0);
        isVariantOfArea = unit.isVariantOfArea();
      }

      // in L3V1 a combination of dimensionless and metre is considered valid
      if (getLevelAndVersion().compareTo(3, 1) >= 0 && getUnitCount() > 1 && !isVariantOfArea) {

        ud = clone(); 
        
        if (isInvalidSBMLAllowed()) {
          ud.putUserObject(JSBML.ALLOW_INVALID_SBML, Boolean.TRUE);
        }
        
        ud.convertToSIUnits(false); // so that dimensionless is not lost if combined with metre.

        // in L3V1, it can be a combination of the area units so we can have more than one unit after simplify()
        isVariantOfArea = true;

        for (Unit unit : ud.getListOfUnits()) {

          isVariantOfArea &= (unit.isMetre() || (unit.getKind() == Kind.DIMENSIONLESS));
        }
      }
    }
    
    return isVariantOfArea;
  }


  /**
   * Tests if a given unit definition is a variant
   * of the predefined unit identifier 'dimensionless'.
   * 
   * @return {@code true} if this UnitDefinition is a variant of the predefined
   *         unit
   *         dimensionless; false otherwise.
   */
  public boolean isVariantOfDimensionless() {
    if (isSetListOfUnits()) {
      UnitDefinition ud = clone(); 
      
      if (isInvalidSBMLAllowed()) {
        ud.putUserObject(JSBML.ALLOW_INVALID_SBML, Boolean.TRUE);
      }
      
      ud.convertToSIAndSubstanceUnits();

      // if after the simplify() call no more units exists, it's dimensionless
      if (ud.getUnitCount() == 0) {
        return true;
      } else if (ud.getUnitCount() == 1) {
        return ud.getUnit(0).isDimensionless();
      }

      return false;
    }
    // If no listOfUnits is set, it's technically dimensionless
    return true;
  }


  /**
   * Tests if a given unit definition is a variant
   * of the predefined unit identifier 'length'.
   * 
   * @return {@code true} if this UnitDefinition is a variant of the predefined
   *         unit
   *         length, meaning metres with only arbitrary variations in scale or
   *         multiplier values; false otherwise.
   */
  public boolean isVariantOfLength() {
    boolean isVariantOfLength = false; 
    
    if (isSetListOfUnits()) {
      UnitDefinition ud = clone(); 
      
      if (isInvalidSBMLAllowed()) {
        ud.putUserObject(JSBML.ALLOW_INVALID_SBML, Boolean.TRUE);
      }
      
      ud.convertToSIUnits();

      if (ud.getUnitCount() == 1) {
        Unit unit = ud.getUnit(0);
        isVariantOfLength = unit.isVariantOfLength();
      }

      // in L3V1, it can be a combination of the length units so we can have more than one unit after simplify()
      if (getLevelAndVersion().compareTo(3, 1) >= 0 && getUnitCount() > 1 && !isVariantOfLength) {

        ud = clone(); 
        
        if (isInvalidSBMLAllowed()) {
          ud.putUserObject(JSBML.ALLOW_INVALID_SBML, Boolean.TRUE);
        }
        
        ud.convertToSIUnits(false);
        
        // in L3V1, it can be a combination of the length units so we can have more than one unit after simplify()
        isVariantOfLength = true;

        for (Unit unit : ud.getListOfUnits()) {

          isVariantOfLength &= unit.isMetre() ||  unit.getKind() == Kind.DIMENSIONLESS;
        }
      }
    }
    
    return isVariantOfLength;
  }


  /**
   * Tests if this {@link UnitDefinition} is a variant
   * of 'substance'.
   * 
   * @return {@code true} if this UnitDefinition is a variant of the predefined
   *         unit substance, meaning moles or items (and grams or kilograms from
   *         SBML Level 2 Version 2 onwards, and avogadro from L3) with only arbitrary variations in
   *         scale or multiplier values. From SBML L3V2, we can have a combination of the allowed units
   *         and the exponent can be different from 1. {@link Kind#DIMENSIONLESS} is also an allowed
   *         value for substance but is not tested here, you have to use {@link #isVariantOfDimensionless()}
   *         if you want to test for dimensionless. Returns @code{ false} otherwise.
   * @see #isVariantOfDimensionless()
   */
  public boolean isVariantOfSubstance() {
    boolean isVariantOfSubstance = false;

    if (isSetListOfUnits()) {
      UnitDefinition ud = clone(); 
      
      if (isInvalidSBMLAllowed()) {
        ud.putUserObject(JSBML.ALLOW_INVALID_SBML, Boolean.TRUE);
      }
      
      ud.convertToSIAndSubstanceUnits();

      if (ud.getUnitCount() == 1) {
        Unit unit = ud.getUnit(0);
        isVariantOfSubstance = unit.isVariantOfSubstance();
        
      } else if (getLevelAndVersion().compareTo(3, 1) >= 0  && getUnitCount() > 1 ) {
        
        // from L3V1, it can be a combination of the substance units so we can have more than one unit after simplify()
        isVariantOfSubstance = true;
        
        for (Unit unit : ud.getListOfUnits()) {
          
          isVariantOfSubstance &= unit.isVariantOfSubstance(false);
        }
      }
    }
    
    return isVariantOfSubstance;
  }


  /**
   * Tests if a given unit definition is a variant
   * of the predefined unit identifier 'substance per area'.
   * 
   * @return {@code true} if this UnitDefinition is a variant of the predefined
   *         unit 'substance per area'.
   */
  public boolean isVariantOfSubstancePerArea() {
    if (isSetListOfUnits()) {
      UnitDefinition ud = clone(); 
      
      if (isInvalidSBMLAllowed()) {
        ud.putUserObject(JSBML.ALLOW_INVALID_SBML, Boolean.TRUE);
      }
      
      ud.convertToSIAndSubstanceUnits();

      if (ud.getUnitCount() == 2) {
        if (ud.getUnit(0).isVariantOfSubstance()) {
          Unit two = ud.getUnit(1).clone();
          two.setExponent(-two.getExponent());
          return two.isVariantOfArea();
        } else if (ud.getUnit(1).isVariantOfSubstance()) {
          Unit one = ud.getUnit(0).clone();
          one.setExponent(-one.getExponent());
          return one.isVariantOfArea();
        }
      }
    }
    return false;
  }


  /**
   * Tests if this UnitDefinition is a variant of substance per
   *         length.
   * 
   * @return {@code true} if this UnitDefinition is a variant of substance per
   *         length.
   */
  public boolean isVariantOfSubstancePerLength() {
    if (isSetListOfUnits()) {
      UnitDefinition ud = clone(); 
      
      if (isInvalidSBMLAllowed()) {
        ud.putUserObject(JSBML.ALLOW_INVALID_SBML, Boolean.TRUE);
      }
      
      ud.convertToSIAndSubstanceUnits();

      if (ud.getUnitCount() == 2) {
        Unit unit = ud.getUnit(0);
        Unit unit2 = ud.getUnit(1);

        if (unit.isVariantOfSubstance()) {
          Unit two = unit2.clone();
          two.setExponent(-two.getExponent());
          return two.isVariantOfLength();
        } else if (unit2.isVariantOfSubstance()) {
          Unit one = unit.clone();
          one.setExponent(-one.getExponent());
          return one.isVariantOfLength();
        }
      }
    }
    return false;
  }


  /**
   * Tests if this UnitDefinition is a variant of substance per
   * time.
   * 
   * @return {@code true} if this UnitDefinition is a variant of substance per time.
   */
  public boolean isVariantOfSubstancePerTime() {
    if (isSetListOfUnits()) {
      UnitDefinition ud = clone(); 
      
      if (isInvalidSBMLAllowed()) {
        ud.putUserObject(JSBML.ALLOW_INVALID_SBML, Boolean.TRUE);
      }
      
      ud.convertToSIAndSubstanceUnits();

      if (ud.getUnitCount() == 2) {
        Unit unit1 = ud.getUnit(0);
        Unit unit2 = ud.getUnit(1);
        if (unit1.isVariantOfSubstance()) {
          Unit two = unit2.clone();
          two.setExponent(-two.getExponent());
          return two.isVariantOfTime();
        } else if (unit2.isVariantOfSubstance()) {
          Unit one = unit1.clone();
          one.setExponent(-one.getExponent());
          return one.isVariantOfTime();
        }
      }
    }
    return false;
  }


  /**
   * Tests if this UnitDefinition is a variant of substance per volume.
   * 
   * @return {@code true} if this UnitDefinition is a variant of substance per
   *         volume.
   */
  public boolean isVariantOfSubstancePerVolume() {
    if (isSetListOfUnits()) {
      UnitDefinition ud = clone(); 
      
      if (isInvalidSBMLAllowed()) {
        ud.putUserObject(JSBML.ALLOW_INVALID_SBML, Boolean.TRUE);
      }
      
      ud.convertToSIAndSubstanceUnits();

      if (ud.getUnitCount() == 2) {
        Unit unit = ud.getUnit(0);
        Unit unit2 = ud.getUnit(1);
        if (unit.isVariantOfSubstance()) {
          Unit two = unit2.clone();
          two.setExponent(-two.getExponent());
          return two.isVariantOfVolume();
        } else if (unit2.isVariantOfSubstance()) {
          Unit one = unit.clone();
          one.setExponent(-one.getExponent());
          return one.isVariantOfVolume();
        }
      }
    }
    return false;
  }


  /**
   * Tests if a given unit definition is a variant
   * of the predefined unit identifier 'time'.
   * 
   * @return {@code true} if this {@link UnitDefinition} is a variant of
   *         the predefined unit time, meaning second with only arbitrary
   *         variations in scale or multiplier values; {@code false}
   *         otherwise.
   */
  public boolean isVariantOfTime() {
    
    boolean isVariantOfTime = false;
    
    if (isSetListOfUnits()) {
      UnitDefinition ud = clone(); 
      
      if (isInvalidSBMLAllowed()) {
        ud.putUserObject(JSBML.ALLOW_INVALID_SBML, Boolean.TRUE);
      }
      
      ud.convertToSIUnits();

      if (ud.getUnitCount() == 1) {
        Unit unit = ud.getUnit(0);
        isVariantOfTime = unit.isVariantOfTime();
      } 

      if (getLevelAndVersion().compareTo(3, 1) >= 0  && getUnitCount() > 1  && !isVariantOfTime) {
        
        ud = clone(); 

        if (isInvalidSBMLAllowed()) {
          ud.putUserObject(JSBML.ALLOW_INVALID_SBML, Boolean.TRUE);
        }

        ud.convertToSIUnits(false);
        
        // from L3V1, it can be a combination of the time units so we can have more than one unit after simplify()
        isVariantOfTime = true;
        
        for (Unit unit : ud.getListOfUnits()) {
          
          isVariantOfTime &= unit.getKind() == Kind.SECOND || unit.getKind() == Kind.DIMENSIONLESS;
        }
      }
    }
    
    return isVariantOfTime;
  }


  /**
   * Tests if a given unit definition is a variant
   * of the predefined unit identifier 'volume'.
   * 
   * @return {@code true} if this {@link UnitDefinition} is a variant of
   *         the predefined unit volume, meaning litre or cubic metre with
   *         only arbitrary variations in scale or multiplier values;
   *         {@code false} otherwise.
   */
  public boolean isVariantOfVolume() {

    boolean isVariantOfVolume = false;

    if (isSetListOfUnits()) {
      UnitDefinition ud = clone(); 
      
      if (isInvalidSBMLAllowed()) {
        ud.putUserObject(JSBML.ALLOW_INVALID_SBML, Boolean.TRUE);
      }
      
      ud.convertToSIUnits();

      if (ud.getUnitCount() == 1) {
        Unit unit = ud.getUnit(0);
        isVariantOfVolume = unit.isVariantOfVolume();
      } 
      
      if (getLevelAndVersion().compareTo(3, 1) >= 0  && getUnitCount() > 1 && !isVariantOfVolume) {
        
        ud = clone(); 

        if (isInvalidSBMLAllowed()) {
          ud.putUserObject(JSBML.ALLOW_INVALID_SBML, Boolean.TRUE);
        }

        ud.convertToSIUnits(false);

        // from L3V1, it can be a combination of the volume units so we can have more than one unit after simplify()
        isVariantOfVolume = true;
        
        for (Unit unit : ud.getListOfUnits()) {
          Kind kind = unit.getKind();
          
          isVariantOfVolume &= (kind == Kind.LITER) || (kind == Kind.LITRE)
              || (kind == Kind.METER) || (kind == Kind.METRE);
        }
      }
    }
    
    return isVariantOfVolume;
  }


  /**
   * Multiplies this unit with the given unit definition, i.e., adds a clone
   * of each unit object in the list of units of the given definition to the
   * list of this unit.
   * 
   * @param definition a unit definition
   * @return the current (modified) {@link UnitDefinition} for convenience.
   */
  public UnitDefinition multiplyWith(UnitDefinition definition) {
    // Avoid creation of not needed empty list:
    if (definition.isSetListOfUnits()) {
      for (Unit unit : definition.getListOfUnits()) {
        boolean contains = false;
        for (int i = getUnitCount() - 1; (i >= 0) && !contains; i--) {
          Unit u = getUnit(i);
          if (Unit.Kind.areEquivalent(u.getKind(), unit.getKind())
              || u.isDimensionless() || unit.isDimensionless() || u.isInvalid()
              || unit.isInvalid()) {
            if (u.isDimensionless()) {
              unit = unit.clone();
              Unit.merge(unit, removeUnit(i));
              break;
            } else {
              Unit.merge(u, unit);
              contains = true;
            }
          }
        }
        if (!contains) {
          Unit u = unit.clone();
          u.unsetMetaId();
          addUnit(u);
        }
      }
    }
    return this;
  }


  /**
   * Raises this unit definition by the power of the given exponent, i.e., the
   * exponents of every unit contained by this unit definition are multiplied
   * with the given exponent.
   * 
   * @param exponent the exponent
   * @return a pointer to this {@link UnitDefinition}.
   */
  public UnitDefinition raiseByThePowerOf(double exponent) {
    if (isSetListOfUnits()) {
      Unit u;
      for (int i = listOfUnits.size() - 1; i >= 0; i--) {
        u = listOfUnits.get(i);
        u.setExponent(u.getExponent() * exponent);
        if (u.getExponent() == 0d) {
          listOfUnits.remove(i);
        }
      }
    }
    return this;
  }


  /**
   * Removes the nth {@link Unit} object from this {@link UnitDefinition} object
   * and returns a
   * pointer to it.
   * The caller owns the returned object and is responsible for deleting it.
   * 
   * @param i
   *        the index of the Unit object to remove
   * @return the {@link Unit} object removed. As mentioned above, the caller
   *         owns the
   *         returned item. {@code null} is returned if the given index is out
   *         of
   *         range.
   */
  public Unit removeUnit(int i) {
    if (isSetListOfUnits()) {
      // Note that ListOf will already notify listeners.
      Unit u = listOfUnits.remove(i);
      return u;
    }
    return null;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#setId(java.lang.String)
   */
  @Override
  public void setId(String id) {
    // This test should be performed in order to ensure valid identifiers
    // for Units:
    if (!isReadingInProgress()) {
      ValuePair<Integer, Integer> lv = getLevelAndVersion();
      if ((0 <= lv.compareTo(Integer.valueOf(2), Integer.valueOf(3)))
          && Unit.Kind.isValidUnitKindString(id, lv.getL().intValue(),
            lv.getV().intValue()))
      {
        throw new IllegalArgumentException(MessageFormat.format(
          "Cannot use the name {0} of a unit base kind as an identifier for a UnitDefinition.",
          id));
      }
    }
    super.setId(id);
  }


  /**
   * Sets the {@link #listOfUnits} of this {@link UnitDefinition}.
   * 
   * <p>Automatically sets the parent SBML object of the list to this
   * {@link UnitDefinition} instance.
   * 
   * @param listOfUnits the new list of {@link Unit}s.
   */
  public void setListOfUnits(ListOf<Unit> listOfUnits) {
    unsetListOfUnits();
    this.listOfUnits = listOfUnits;
    if ((this.listOfUnits != null)
        && (this.listOfUnits.getSBaseListType() != ListOf.Type.listOfUnits)) {
      this.listOfUnits.setSBaseListType(ListOf.Type.listOfUnits);
    }
    registerChild(this.listOfUnits);
  }


  /**
   * Simplifies the {@link UnitDefinition} so that any {@link Unit} objects
   * occurring within the {@link #listOfUnits} occurs only once. {@link Unit}s
   * of {@link Kind} {@link Kind#INVALID} are treated like
   * {@link Kind#DIMENSIONLESS} units and will therefore tend to disappear by
   * merging with other units.
   * 
   * @return a pointer to the simplified {@link UnitDefinition}.
   */
  public UnitDefinition simplify() {
    if (isSetListOfUnits()) {
      reorder(this);
      Unit u, s;
      // Merge units with equivalent or similar kinds if possible:
      for (int i = getUnitCount() - 2; i >= 0; i--) {
        u = getUnit(i); // current unit
        s = getUnit(i + 1); // successor unit
        if (Unit.Kind.areEquivalent(u.getKind(), s.getKind())
            || u.isDimensionless() || s.isDimensionless() || u.isInvalid()
            || s.isInvalid()) {
          if (s.isDimensionless()) {
            Unit.merge(u, removeUnit(i + 1));
          } else {
            Unit.merge(s, removeUnit(i));
          }
        }
      }

      // Remove units that have become dimensionless by merging with subsequent
      // units
      while ((getUnitCount() > 1)
          && (getUnit(0).getKind().equals(Kind.DIMENSIONLESS))) {
        u = removeUnit(0);
        Unit.merge(getUnit(0), u);
      }

      // TODO: Reorder all units first to have all those with a positive
      // exponent in the front!

      // Shift scales and multipliers to the front if possible:
      for (int i = getUnitCount() - 2; i >= 0; i--) {
        u = getUnit(i); // current unit
        s = getUnit(i + 1); // successor unit
        if (!Unit.Kind.areEquivalent(u.getKind(), s.getKind())
            && !u.isDimensionless() && !s.isDimensionless() && !u.isInvalid()
            && !s.isInvalid()) {
          // double m1 = u.getMultiplier();
          // double m2 = s.getMultiplier();
          int s1 = u.getScale();
          int s2 = s.getScale();
          double e1 = u.getExponent();
          double e2 = s.getExponent();
          double p1 = s1 * e1;
          double p2 = s2 * e2;
          // Try re-scaling by merging the scale of the second unit into the
          // first unit:
          if ((Math.signum(p1) != Math.signum(p2)) && (e1 != 0d)) {
            double newScale = s1 + p2 / e1;
            if (((i > 1) || ((s1 != 0) && (s2 != 0)))
                && (newScale - ((int) newScale) == 0)) {
              /*
               * Only re-scale if we can obtain an integer and if there is more
               * than two units in the list (otherwise it is not necessary to
               * simplify these units), i.e., loss-free rounding.
               */
              u.setScale((int) newScale);
              s.setScale(0);
            }
          }
          /*
           * Better do not try to remove the multiplier from the second unit
           * because this can lead to very strange numbers. Often multipliers
           * are strictly bound to one unit, for instance, 3600 * second (which
           * is hour). When shifting the multiplier to the next unit in the
           * front, the result might become very strange.
           */
        }
      }
    }
    return this;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#toString()
   */
  @Override
  public String toString() {
    if (isSetName()) {
      return getName();
    }
    return isSetListOfUnits() ? printUnits(this, true) : super.toString();
  }


  /**
   * Removes the {@link #listOfUnits} from this {@link UnitDefinition} and
   * notifies all registered instances of {@link TreeNodeChangeListener}.
   * 
   * @return {@code true} if calling this method lead to a change in this
   *         data structure.
   */
  public boolean unsetListOfUnits() {
    if (listOfUnits != null) {
      ListOf<Unit> oldListOfUnits = listOfUnits;
      listOfUnits = null;
      oldListOfUnits.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

}
