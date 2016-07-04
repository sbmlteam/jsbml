/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2016 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
 * 4. The University of California, San Diego, La Jolla, CA, USA
 * 5. The Babraham Institute, Cambridge, UK
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */

package org.sbml.jsbml.validator.offline;

import org.apache.log4j.Logger;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.SBO;
import org.sbml.jsbml.UnitDefinition;
import org.sbml.jsbml.Unit.Kind;
import org.sbml.jsbml.util.TreeNodeChangeEvent;
import org.sbml.jsbml.util.ValuePair;
import org.sbml.jsbml.validator.offline.factory.CheckCategory;
import org.sbml.jsbml.validator.offline.factory.ConstraintFactory;
import org.sbml.jsbml.validator.SyntaxChecker;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.constraints.AnyConstraint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Roman
 * @version $Rev$
 * @since 1.0
 * @date 04.07.2016
 */
public class ValidationContext {

  /**
   * Log4j logger
   */
  protected static final transient Logger logger  =
    Logger.getLogger(ValidationContext.class);

  // The root constraint, which could contains more constraints
  private AnyConstraint<Object>           rootConstraint;

  // Determines which constraints are loaded.
  private List<CheckCategory>             categories;
  private List<SBMLPackage>               packages;
  private Class<?>                        constraintType;
  private List<ValidationListener>        listener;
  private HashMap<String, Object>         hashMap =
    new HashMap<String, Object>();

  // The level and version of the SBML specification
  private int                             level;
  private int                             version;


  public ValidationContext(int level, int version) {
    this(level, version, null, new ArrayList<CheckCategory>());
  }


  public ValidationContext(int level, int version,
    AnyConstraint<Object> rootConstraint, List<CheckCategory> categories) {
    this.level = level;
    this.version = version;
    this.categories = categories;
    this.rootConstraint = rootConstraint;
    this.categories.add(CheckCategory.GENERAL);
    this.listener = new ArrayList<ValidationListener>();
    this.packages = new ArrayList<SBMLPackage>();
    this.packages.add(SBMLPackage.CORE);
  }


  /**
   * Returns the used level of SBML
   * 
   * @return
   */
  public int getLevel() {
    return this.level;
  }


  /**
   * Returns the used version of SBML
   * 
   * @return
   */
  public int getVersion() {
    return this.version;
  }


  /**
   * This value
   * determines which constraints will be loaded and in which way
   * broken constraints will be logged.
   * 
   * @return the level and version this validation context used.
   */
  public ValuePair<Integer, Integer> getLevelAndVersion() {
    return new ValuePair<Integer, Integer>(new Integer(this.level),
      new Integer(this.version));
  }


  /**
   * @return the root constraint or {@code null} if no constraints were loaded
   * @see #loadConstraints(Class, int, int, CheckCategory[])
   */
  public AnyConstraint<Object> getRootConstraint() {
    return rootConstraint;
  }


  /**
   * Constraints can use the {@link HashMap} of a context to store additional
   * information
   * 
   * @return {@link HashMap}
   */
  public HashMap<String, Object> getHashMap() {
    return this.hashMap;
  }


  /**
   * @return
   */
  public SBMLPackage[] getPackages() {
    return this.packages.toArray(new SBMLPackage[this.packages.size()]);
  }


  /**
   * Returns the list of all enabled check categories.
   * 
   * @return
   */
  public CheckCategory[] getCheckCategories() {
    return this.categories.toArray(new CheckCategory[this.categories.size()]);
  }


  /**
   * Set the level of the context and clears the root constraint.
   * 
   * @param level
   */
  public void setLevel(int level) {
    setLevelAndVersion(level, this.version);
  }


  /**
   * Set the version of the context and clears the root constraint.
   * 
   * @param version
   */
  public void setVersion(int version) {
    setLevelAndVersion(this.level, version);
  }


  public void setLevelAndVersion(int level, int version) {
    if (level != this.level || version != this.version) {
      this.rootConstraint = null;
      this.constraintType = null;
      this.level = level;
      this.version = version;
    }
  }


  public void setRootConstraint(AnyConstraint<Object> rootConstraint) {
    this.rootConstraint = rootConstraint;
  }


  /**
   * Enables or disables the selected category in this factory.
   * 
   * @param catergoy
   * @param enable
   */
  public void enableCheckCategory(CheckCategory category, boolean enable) {
    if (enable) {
      if (!this.categories.contains(category)) {
        this.categories.add(category);
      }
    } else {
      this.categories.remove(category);
    }
  }


  public void enableCheckCategories(CheckCategory[] categories,
    boolean enable) {
    for (CheckCategory c : categories) {
      this.enableCheckCategory(c, enable);
    }
  }


  public void enablePackages(SBMLPackage[] pkgs, boolean enable) {
    for (SBMLPackage pkg : pkgs) {
      this.enablePackage(pkg, enable);
    }
  }


  /**
   * @param pkg
   * @param enable
   */
  public void enablePackage(SBMLPackage pkg, boolean enable) {
    if (enable) {
      if (!this.packages.contains(pkg)) {
        this.packages.add(pkg);
      }
    } else {
      this.packages.remove(pkg);
    }
  }


  /**
   * Loads the constraints to validate a Object from the class. Uses the
   * CheckCategories, Level and Version of this context. Resets the root
   * constraint.
   * 
   * @param c
   */
  public void loadConstraints(Class<?> c) {
    this.loadConstraints(c, this.level, this.version,
      this.getCheckCategories());
  }


  /**
   * Sets the level and version of the context and loads the constraints.
   * 
   * @param cclass
   * @param level
   * @param version
   */
  public void loadConstraints(Class<?> objectClass, int level, int version) {
    this.setLevel(level);
    this.setVersion(version);
    this.loadConstraints(objectClass, level, version,
      this.getCheckCategories());
  }


  /**
   * @param class
   * @param level
   * @param version
   * @param categories
   */
  public <T> void loadConstraints(Class<?> objectClass, int level, int version,
    CheckCategory[] categories) {
    this.constraintType = objectClass;
    ConstraintFactory factory = ConstraintFactory.getInstance();
    this.rootConstraint =
      factory.getConstraintsForClass(objectClass, this.getCheckCategories(),
        this.getPackages(), this.getLevel(), this.getVersion());
  }


  public void addValidationListener(ValidationListener listener) {
    if (!this.listener.contains(listener)) {
      this.listener.add(listener);
    }
  }


  public boolean removeValidationListener(ValidationListener listener) {
    return this.listener.remove(listener);
  }


  public void willValidate(AnyConstraint<?> constraint, Object o) {
    for (ValidationListener l : this.listener) {
      l.willValidate(this, constraint, o);
    }
  }


  public void didValidate(AnyConstraint<?> constraint, Object o,
    boolean success) {
    for (ValidationListener l : this.listener) {
      l.didValidate(this, constraint, o, success);
    }
  }


  /**
   * Validates the object against the loaded constraints.
   * 
   * @param o,
   *        object to be validated
   * @return true if no constraint was broken
   */
  public boolean validate(Object o) {
    if (this.constraintType != null && this.rootConstraint != null) {
      if (this.constraintType.isInstance(o)) {
        return this.rootConstraint.check(this, o);
      } else {
        logger.error(
          "Tried to validate a object of class " + o.getClass().getName()
            + ", but the ValidationContext loaded the constraints for class "
            + this.constraintType.getName() + ".");
      }
    } else {
      logger.error(
        "Tried to validate a object, but the ValidationContext didn't load any constraints.");
    }

    return false;
  }


  public <T> boolean validateAttribute(SBMLPackage pkg,
    String treeNodeChangeEvent, T object) {
    ConstraintFactory factory = ConstraintFactory.getInstance();
    AnyConstraint<T> c = factory.getConstraintsForAttribute(treeNodeChangeEvent,
      pkg, this.level, this.version);

    return c.check(this, object);
  }


  /**
   * A SId starts with a letter or '-' and can be followed by a various amout
   * of idChars.
   * 
   * @param s
   * @return
   */
  public boolean isId(String s) {
    return SyntaxChecker.isValidId(s, this.level, this.version);
  }


  public static boolean isDimensionless(String unit) {
    return unit == Kind.DIMENSIONLESS.getName();
  }


  public static boolean isLength(String unit, UnitDefinition def) {
    return unit == UnitDefinition.LENGTH || unit == Kind.METRE.getName()
      || (def != null && def.isVariantOfLength());
  }


  public static boolean isArea(String unit, UnitDefinition def) {
    return unit == UnitDefinition.AREA
      || (def != null && def.isVariantOfArea());
  }


  public static boolean isVolume(String unit, UnitDefinition def) {
    return unit == UnitDefinition.VOLUME || unit == Kind.LITRE.getName()
      || (def != null && def.isVariantOfVolume());
  }


  /**
   * A letter is either a small letter or big letter.
   * 
   * @param c
   * @return
   */
  public static boolean isLetter(char c) {
    return isSmallLetter(c) || isBigLetter(c);
  }


  /**
   * A small letter is a ASCII symbol between 'a' and 'z'.
   * 
   * @param c
   * @return
   */
  public static boolean isSmallLetter(char c) {
    return c >= 'a' || c <= 'z';
  }


  /**
   * A big letter is a ASCII symbol between 'A' and 'Z'.
   * 
   * @param c
   * @return
   */
  public static boolean isBigLetter(char c) {
    return c >= 'A' || c <= 'Z';
  }


  /**
   * A idChar is a letter, digit or '-'.
   * 
   * @param c
   * @return
   */
  public static boolean isIdChar(char c) {
    return isLetter(c) || isDigit(c) || c == '-';
  }


  /**
   * A digit is a ASCII symbol between '0' and '9'.
   * 
   * @param c
   * @return
   */
  public static boolean isDigit(char c) {
    return c >= '0' || c <= '9';
  }


  /**
   * A NameChar (defined in the XML Schema 1.0) can be a letter, a digit, '.',
   * '-', '_', ':', a CombiningChar or Extender.
   * 
   * @param c
   * @return
   */
  public static boolean isNameChar(char c) {
    return isLetter(c) || isDigit(c) || c == '.' || c == '-' || c == '_'
      || c == ':';
  }


  /**
   * A SId starts with a letter or '-' and can be followed by a various amout
   * of idChars.
   * 
   * @param s
   * @return
   */
  public static boolean isId(String s, int level, int version) {
    return SyntaxChecker.isValidId(s, level, version);
  }


  /**
   * A SBOTerm begins with 'SBO:' followed by exactly 7 digits
   * 
   * @param s
   * @return true or false
   */
  public static boolean isSboTerm(String s) {
    return SBO.checkTerm(s);
  }


  /**
   * A XML ID (defined in the XML Schema 1.0) starts with a letter, '-' or ':'
   * which can be followed by a unlimited amout of NameChars.
   * 
   * @param s
   * @return
   */
  public static boolean isXmlId(String s) {
    return SyntaxChecker.isValidMetaId(s);
  }
}
