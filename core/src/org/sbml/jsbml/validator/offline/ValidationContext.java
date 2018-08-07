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

package org.sbml.jsbml.validator.offline;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.util.ValuePair;
import org.sbml.jsbml.validator.SBMLValidator.CHECK_CATEGORY;
import org.sbml.jsbml.validator.offline.constraints.AnyConstraint;
import org.sbml.jsbml.validator.offline.constraints.ConstraintGroup;
import org.sbml.jsbml.validator.offline.factory.ConstraintFactory;

/**
 * A {@link ValidationContext} object is used to perform offline validation.
 * 
 * <p>
 * To prepare the context for the validation use the
 * {@link ValidationContext#loadConstraints(Class)} method, which will loads all
 * the required constraints to validate a object from this class. By default the
 * context will try to validate recursive by searching for the {@link TreeNode}
 * interface. This behavior can be changed with
 * {@link ValidationContext#setValidateRecursively(boolean)}.</p>
 * <p>
 * To start the validation process call
 * {@link ValidationContext#validate(Object)} with a object which is a instance
 * of the class for which the constraints were loaded. If not constraints were
 * loaded or the object doesn't match the class, no validation process is
 * started.</p>
 * <p>
 * To track the validation process in real-time you can add a
 * {@link ValidationListener} to this context by using the
 * {@link ValidationContext#addValidationListener(ValidationListener)} method.</p>
 * <p>
 * The level and version parameter determine which SBML specifications are used.
 * For more informations look up <a href="http://www.sbml.org"> sbml.org </a>
 * </p>
 * 
 * @author Roman
 * @since 1.2
 */
public class ValidationContext {

  /**
   * Log4j logger
   */
  protected static final transient Logger logger = Logger.getLogger(ValidationContext.class);

  /**
   * The root constraint, which could contains more constraints (will be in general a {@link ConstraintGroup})
   */
  private AnyConstraint<Object>           rootConstraint;

  /**
   * Determines which constraints are loaded.
   */
  private Set<CHECK_CATEGORY>             categories;
  /**
   * Which class the {@code rootConstraint} were generated for
   */
  private Class<?>                        constraintType;
  /**
   * The set of validation listeners
   */
  private Set<ValidationListener>         listenerSet;
  /**
   * A map where any objects can be stored during validation
   */
  private Map<String, Object>         hashMap = new HashMap<String, Object>();

  // The level and version of the SBML specification
  /**
   * The SBML level
   */
  private int                             level;
  /**
   * The SBML version
   */
  private int                             version;
  /**
   * Is the validation done recursively or not
   */
  private boolean                         recursiv = true;

  // TODO - package version for all packages - if not given, take latest version ?
  private HashMap<String, Integer> packageVersions = new HashMap<String, Integer>();


  /**
   * Creates a new {@link ValidationContext} for the given SBML level and version.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public ValidationContext(int level, int version) {
    this(level, version, null, new HashSet<CHECK_CATEGORY>());
  }


  /**
   * Creates a new {@link ValidationContext} for the given SBML level and version.
   * 
   * @param level the SBML level
   * @param version the SBML version
   * @param rootConstraint the root constraint for the validation
   * @param categories the {@link CHECK_CATEGORY}s
   */
  public ValidationContext(int level, int version, AnyConstraint<Object> rootConstraint, Set<CHECK_CATEGORY> categories) {
    this.level = level;
    this.version = version;
    this.categories = categories;
    this.rootConstraint = rootConstraint;
    
    // TODO - if SBase
    this.categories.add(CHECK_CATEGORY.GENERAL_CONSISTENCY); // TODO - why adding this one all the time ?
    listenerSet = new HashSet<ValidationListener>();
  }


  /**
   * Adds a {@link ValidationListener} to this context.
   * 
   * <p> A {@link ValidationContext} can have
   * multiple listeners and every attached listener will receive the events from
   * the validation. </p>
   * 
   * @param listener the listener to add
   */
  public void addValidationListener(ValidationListener listener) {
    if (!listenerSet.contains(listener)) {
      listenerSet.add(listener);
    }
  }


  /**
   * Clears the loaded constraints
   */
  public void clear() {
    rootConstraint = null;
    constraintType = null;
  }

  /**
   * Calls the {@link ValidationListener#didValidate(ValidationContext, AnyConstraint, Object, boolean)}
   * method on all the listeners.
   * 
   * <p>Will be called every time after a constraints finished the checks.</p>
   * 
   * @param constraint the constraint that has been validated
   * @param o the object on which the constraint that has been validated
   * @param success {@code true} if the constraint was successful, {@code false} if an error was detected.
   */
  public final void didValidate(AnyConstraint<?> constraint, Object o, boolean success) {
    for (ValidationListener l : listenerSet) {
      l.didValidate(this, constraint, o, success);
    }
  }


  /**
   * Enables or disables the selected category.
   * 
   * <p> The enabled categories determines which constraints will be loaded in
   * {@link #loadConstraints(Class, int, int, CHECK_CATEGORY[])}. This function
   * won't change the root constraint.</p>
   * 
   * @param category the category to enable or disable
   * @param enable a boolean to tell if we enable ({@code true}) or disable ({@code false}) the category
   * @see #loadConstraints(Class, int, int, CHECK_CATEGORY[])
   */
  public void enableCheckCategory(CHECK_CATEGORY category, boolean enable) {
    if (enable) {

      categories.add(category);

    } else {
      categories.remove(category);
    }
  }


  /**
   * Calls {@link #enableCheckCategory(CHECK_CATEGORY, boolean)} for every
   * {@link CHECK_CATEGORY} in the array.
   * 
   * @param categories the categories to enable or disable
   * @param enable a boolean to tell if we enable ({@code true}) or disable ({@code false}) the categories
   * @see CHECK_CATEGORY
   */
  public void enableCheckCategories(CHECK_CATEGORY[] categories, boolean enable) {
    for (CHECK_CATEGORY c : categories) {
      enableCheckCategory(c, enable);
    }
  }


  /**
   * Loads the constraints to validate a Object from the class.
   * 
   * <p>Uses the
   * CheckCategories, level and version of this context. Resets the root
   * constraint.</p>
   * 
   * @param clazz the class to load constraint for
   */
  public void loadConstraints(Class<?> clazz) {
    ConstraintFactory factory = ConstraintFactory.getInstance();
    ConstraintGroup<Object> group = factory.getConstraintsForClass(clazz, this);

    setRootConstraint(group, clazz);
  }


  /**
   * Loads the constraints and sets the level and version.
   * 
   * @param clazz the class to load constraint for
   * @param level the SBML level
   * @param version the SBML version
   * @see #loadConstraints(Class)
   */
  public void loadConstraints(Class<?> clazz, int level, int version) {
    setLevelAndVersion(level, version);
    loadConstraints(clazz);
  }


  /**
   * Loads the constraints to validate a Object from the given class.
   * 
   *  <p>Sets, as well, the level and version and the enabled categories
   *  of the {@link ValidationContext}.</p>
   * 
   * @param clazz the class to load constraint for
   * @param level the SBML level
   * @param version the SBML version
   * @param categories the categories to enable
   * @see #loadConstraints(Class)
   */
  public void loadConstraints(Class<?> clazz, int level, int version, CHECK_CATEGORY[] categories) {
    setLevelAndVersion(level, version);
    this.categories.clear();
    enableCheckCategories(categories, true);
    loadConstraints(clazz);
  }


  /**
   * Validates a single attribute. The object must have set the new value
   * already.
   * 
   * @param clazz the class to load constraint for
   * @param attributeName the attribute name to load constraint for
   */
  public void loadConstraintsForAttribute(Class<?> clazz, String attributeName) {
    ConstraintFactory factory = ConstraintFactory.getInstance();

    AnyConstraint<Object> c = factory.getConstraintsForAttribute(clazz,
      attributeName, level, version, this);

    setRootConstraint(c, clazz);
  }


  /**
   * Returns all the enabled check categories.
   * 
   * @return all the enabled check categories.
   */
  public CHECK_CATEGORY[] getCheckCategories() {
    return categories.toArray(new CHECK_CATEGORY[categories.size()]);
  }


  /**
   * Gets the {@link Class} on which the root constraint is typed.
   * 
   * @return the {@link Class} on which the root constraint is typed.
   */
  public Class<?> getConstraintType() {
    return constraintType;
  }


  /**
   * Gets the validation objects {@link HashMap}.
   * 
   * <p>Constraints can use this {@link HashMap} to store additional
   * information if needed.</p>
   * 
   * <p> Notice that this method doesn't return a copy, but a reference to the
   * actual instance.</p>
   * 
   * @return the validation objects {@link HashMap}.
   */
  public Map<String, Object> getHashMap() {
    return hashMap;
  }


  /**
   * Returns the used level of SBML
   * 
   * @return a positive <code>int</code>
   * @see #getVersion()
   * @see #getLevelAndVersion()
   */
  public int getLevel() {
    return level;
  }


  /**
   * Returns the SBML level and version of the {@link ValidationContext}.
   * 
   * <p>This value determines which constraints will be loaded and in which way
   * broken constraints will be logged.</p>
   * 
   * @return the level and version this validation context used.
   * @see #getLevel()
   * @see #getVersion()
   */
  public ValuePair<Integer, Integer> getLevelAndVersion() {
    return new ValuePair<Integer, Integer>(new Integer(level), new Integer(version));
  }


  /**
   * Returns the root constraint from this context.
   * 
   * <p>The value may be
   * <code>null</code> if no constraint was loaded. The
   * {@link AnyConstraint#check(ValidationContext, Object)} method will be called on
   * the root constraint in {@link #validate(Object)}.</p>
   * 
   * @return the root constraint or {@code null} if no constraints were loaded
   * @see #loadConstraints(Class, int, int, CHECK_CATEGORY[])
   */
  public AnyConstraint<Object> getRootConstraint() {
    return rootConstraint;
  }


  /**
   * Returns the used version of SBML
   * 
   * @return a positive <code>int</code>
   * @see #getLevel()
   * @see #getLevelAndVersion()
   */
  public int getVersion() {
    return version;
  }


  /**
   * Returns {@code true} if the validation also validate every child of a
   * {@link TreeNode}.
   * 
   * @return {@code true} if the validation is done recursively.
   */
  public boolean getValidateRecursively() {
    return recursiv;
  }


  /**
   * Returns true if the given {@link CHECK_CATEGORY} is enabled in this {@link ValidationContext}.
   * 
   * @param checkCategory the check category
   * @return true if the given {@link CHECK_CATEGORY} is enabled in this {@link ValidationContext}.
   */
  public boolean isEnabledCategory(CHECK_CATEGORY checkCategory) {
    return categories.contains(checkCategory);
  }


  /**
   * Checks if the level and version of this context is less then the given
   * level and version.
   * 
   * <p>A level and version pair is less if either the level
   * is smaller or the level is equal but the version is smaller.</p>
   * 
   * @param level the SBML level
   * @param version the SBML version
   * @return {@code true} if the level and version of this context is less then the given
   * level and version.
   */
  public boolean isLevelAndVersionLessThan(int level, int version) {
    return (this.level < level)
        || (this.level == level && this.version < version);
  }


  /**
   * Checks if the level and version of this context is greater than the given
   * level and version.
   * 
   * <p>A level and version pair is greater if either the level
   * is greater or the level is equal but the version is greater.</p>
   * 
   * @param level the SBML level
   * @param version the SBML version
   * @return {@code true} if the level and version of this context is greater than the given
   * level and version.
   */
  public boolean isLevelAndVersionGreaterThan(int level, int version) {
    return (this.level > level)
        || (this.level == level && this.version > version);
  }


  /**
   * Checks if the level and version of this context are both the same as the
   * given values.
   * 
   * @param level the SBML level
   * @param version the SBML version
   * @return {@code true} if the level and version of this context are both the same as the
   * given values.
   */
  public boolean isLevelAndVersionEqualTo(int level, int version) {
    return this.level == level && this.version == version;
  }


  /**
   * Checks if the level and version of this context is greater or equal to the
   * given values.
   * 
   * @param level the SBML level
   * @param version the SBML version
   * @return {@code true} if the level and version of this context is greater or equal to the
   * given values.
   * @see #isLevelAndVersionGreaterThan(int, int)
   * @see #isLevelAndVersionEqualTo(int, int)
   */
  public boolean isLevelAndVersionGreaterEqualThan(int level, int version) {
    return isLevelAndVersionGreaterThan(level, version)
        || isLevelAndVersionEqualTo(level, version);
  }


  /**
   * Checks if the level and version of this context is lesser or equal to the
   * given values.
   * 
   * @param level the SBML level
   * @param version the SBML version
   * @return {@code true} if the level and version of this context is lesser or equal to the
   * given values.
   * @see #isLevelAndVersionLessThan(int, int)
   * @see #isLevelAndVersionEqualTo(int, int)
   */
  public boolean isLevelAndVersionLesserEqualThan(int level, int version) {
    return isLevelAndVersionLessThan(level, version)
        || isLevelAndVersionEqualTo(level, version);
  }


  /**
   * Removes a {@link ValidationListener} from this context.
   * 
   * <p>Returns {@code true} if the listener was removed.</p>
   * 
   * @param listener the listener to remove
   * @return {@code true} if the listener was removed
   */
  public boolean removeValidationListener(ValidationListener listener) {
    return listenerSet.remove(listener);
  }


  /**
   * Set the SBML level of the {@link ValidationContext}.
   * 
   * @param level the SBML level
   * @see #setLevelAndVersion(int, int)
   */
  public void setLevel(int level) {
    setLevelAndVersion(level, version);
  }


  /**
   * Sets the level/version and clears the root constraint if one of these
   * values differs from the current values.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public void setLevelAndVersion(int level, int version) {
    if (level != this.level || version != this.version) {
      rootConstraint = null;
      constraintType = null;
      this.level = level;
      this.version = version;
    }
  }

  /**
   * 
   * @param packageShortName
   * @param packageVersion
   */
  public void setPackageVersion(String packageShortName, int packageVersion) {
    // TODO
  }

  /**
   * 
   * @param packageShortName
   * @param packageVersion
   */
  public void setPackageVersion(String packageNamespace) {
    // TODO    
  }

  /**
   * Sets the root constraints and the root constraint class type.
   * 
   * @param rootConstraint the root constraint
   * @param constraintType the constraint type
   */
  public void setRootConstraint(AnyConstraint<Object> rootConstraint, Class<?> constraintType) {

    if (logger.isDebugEnabled()) {
      logger.debug("Set type to " + constraintType.getSimpleName());
    }

    this.rootConstraint = rootConstraint;
    this.constraintType = constraintType;
  }


  /**
   * Sets the recursivity of the validation.
   * 
   * <p>If set to true, the validation context will try to validate also the child
   * of a {@link TreeNode} instance.</p>
   * 
   * @param recursive boolean to indicate if the validation should be recursive or not
   */
  public void setValidateRecursively(boolean recursive) {

    recursiv = recursive;

  }


  /**
   * Sets the version of the context and clears the root constraint.
   * 
   * @param version the SBML version
   */
  public void setVersion(int version) {
    setLevelAndVersion(level, version);
  }


  /**
   * Validates the object against the loaded constraints.
   * 
   * @param o object to be validated
   * @return true if no constraint was broken
   */
  public boolean validate(Object o) {
    return validate(o, true);
  }


  /**
   * Validates the object with the loaded constraints and clears the HashMap
   * afterwards if the clearMap is set to <code>true</code>.
   * 
   * @param o the object to validate
   * @param clearMap boolean to indicate if we need to clear the context after validation
   * @return <code>true</code> if the object passed successfully validation.
   */
  public boolean validate(Object o, boolean clearMap) {
    if (constraintType != null) {

      // If there's a type but no constraints, then no constraints exists for
      // this type.
      if (rootConstraint == null) {
        return true;
      }

      if (constraintType.isInstance(o)) {

        // Perform Validation and clears hashMap afterwards
        boolean check = rootConstraint.check(this, o);

        if (logger.isDebugEnabled()) {
          logger.debug("rooConstraint class = '" + rootConstraint.getClass().getSimpleName() + "' has " + ((ConstraintGroup<Object>) rootConstraint).getConstraintsCount() + " child.");
        }

        if (clearMap) {
          hashMap.clear();
        }

        return check;
      } else {

        logger.error("Tried to validate a object of class " + o.getClass().getName()
          + ", but the ValidationContext loaded the constraints for class "
          + constraintType.getName() + ".");
      }
    } else {
      logger.error("Tried to validate a object, but the ValidationContext didn't load any constraints.");
    }

    return false;
  }


  /**
   * Allows a {@link ValidationListener} to perform some operation before
   * a constraint is called.
   * 
   * <p>This method will be called every time before a constraints starts its tests.</p>
   * 
   * @param constraint the constraint that will be called
   * @param o the object on which the constraint will be called
   */
  public void willValidate(AnyConstraint<?> constraint, Object o) {
    for (ValidationListener l : listenerSet) {
      l.willValidate(this, constraint, o);
    }
  }

}
