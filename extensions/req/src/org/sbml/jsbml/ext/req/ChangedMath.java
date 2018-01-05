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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.req;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.PropertyUndefinedError;

/**
 * Declares which package has modified the value or the mathematical meaning of
 * an {@link SBase} and in which way.
 * <p>
 * Elements with mathematical meaning may have a ChangedMath child when a
 * package alters the value or meaning of that symbol. As an example, a Submodel
 * from the Hierarchical Model Composition package may contain an Event or Rule
 * that assigns new values to a parameter. Because an interpreter that did not
 * understand submodels would not catch this change, the Hierarchical Model
 * Composition package can be seen to change the math of that element, and it
 * would be appropriate to denote this by adding a ChangedMath child to the
 * affected parameter.
 * <p>
 * Similarly, models that use the proposed Spatial Processes package can change
 * the meaning of a Compartment by turning it into a bounded object with an size
 * implied by those boundaries (and how they change over time), instead of using
 * the element's size attribute. Spatial Processes elements may also change a
 * Species to be spatially defined, and therefore represent different values
 * depending on what coordinates in space are under consideration. Affected
 * compartments and species could be given ChangedMath children to denote this
 * fact.
 * <p>
 * Elements with Math children may also be changed by the addition of package
 * elements. Some packages may instruct the modeler to disregard the Math and to
 * use some other construct instead. For example, the proposed Distributions
 * package adds a new child to FunctionDefinition, which replaces the old
 * mathematics with a new set of mathematics returning a draw from a random
 * distribution (something impossible with Math).
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class ChangedMath extends AbstractNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 2446721938903003641L;

  /**
   * The viableWithoutChange attribute is required, and is of type boolean. The
   * attribute should be used to indicate whether an interpreter that does not
   * understand the package from the changedBy attribute would have an
   * interpretable version of the mathematics for the given component in a
   * model. This can be established using two criteria: &ldquo;Complete&rdquo;
   * and &ldquo;Workable&rdquo;. Obviously, if the math being interpreted is
   * incomplete, the viableWithoutChange attribute must be {@code false}.
   * If the math is complete, then whether the solution is
   * &ldquo;workable&rdquo; requires a judgment call on the part of the modeler:
   * if the modeler feels that the alternative version makes sense in an
   * alternative context, they may set the attribute value to {@code true};
   * conversely, if they feel that the resulting model component makes no sense,
   * even if technically &ldquo;complete&rdquo;, then they should set the
   * attribute value to {@code false}.
   */
  private Boolean viableWithoutChange;

  /**
   * The changedBy attribute is required, and is of type string. The attribute
   * must be set to the namespace URI of the SBML Level 3 package that redefines
   * or alters the mathematical semantics of the parent object. In other words,
   * if the mathematical semantics of a given component C in a model is changed
   * by the use of a Level 3 package P, then C can be given a ChangedMath child,
   * and its attribute changedBy should be P's namespace URI.
   */
  private String changedBy;

  /**
   * Creates an ChangedMath instance
   */
  public ChangedMath() {
    super();
    initDefaults();
  }

  /**
   * Creates a {@link ChangedMath} instance with an id.
   * 
   * @param id
   */
  public ChangedMath(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a {@link ChangedMath} instance with a level and version.
   * 
   * @param level
   * @param version
   */
  public ChangedMath(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Creates a {@link ChangedMath} instance with an id, level, and version.
   * 
   * @param id
   * @param level
   * @param version
   */
  public ChangedMath(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a {@link ChangedMath} instance with an id, name, level, and version.
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public ChangedMath(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(
      Integer.valueOf(ReqConstants.MIN_SBML_LEVEL),
      Integer.valueOf(ReqConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  /**
   * Clone constructor
   * @param obj
   */
  public ChangedMath(ChangedMath obj) {
    super(obj);

    if (obj.isSetChangedBy()) {
      setChangedBy(obj.getChangedBy());
    }
    if (obj.isSetViableWithoutChange()) {
      setViableWithoutChange(obj.getViableWithoutChange());
    }
  }

  /**
   * clones this class
   */
  @Override
  public ChangedMath clone() {
    return new ChangedMath(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = ReqConstants.shortLabel;
  }


  @Override
  public boolean isIdMandatory() {
    return false;
  }



  /**
   * Returns the value of changedBy
   *
   * @return the value of changedBy
   */
  public String getChangedBy() {

    if (isSetChangedBy()) {
      return changedBy;
    }

    return null;
  }

  /**
   * Returns whether changedBy is set
   *
   * @return whether changedBy is set
   */
  public boolean isSetChangedBy() {
    return changedBy != null;
  }

  /**
   * Sets the value of changedBy
   * @param changedBy
   */
  public void setChangedBy(String changedBy) {
    String oldChangedBy = this.changedBy;
    this.changedBy = changedBy;
    firePropertyChange(ReqConstants.changedBy, oldChangedBy, this.changedBy);
  }

  /**
   * Unsets the variable changedBy
   *
   * @return {@code true}, if changedBy was set before,
   *         otherwise {@code false}
   */
  public boolean unsetChangedBy() {
    if (isSetChangedBy()) {
      String oldChangedBy = changedBy;
      changedBy = null;
      firePropertyChange(ReqConstants.changedBy, oldChangedBy, changedBy);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of viableWithoutChange
   *
   * @return the value of viableWithoutChange
   * @throws PropertyUndefinedError - if the returned value of {@link #isSetViableWithoutChange()} is false
   */
  public boolean getViableWithoutChange() {

    if (isSetViableWithoutChange()) {
      return viableWithoutChange;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(ReqConstants.viableWithoutChange, this);
  }

  /**
   * Returns the value of viableWithoutChange
   *
   * @return the value of viableWithoutChange
   * @throws PropertyUndefinedError - if the returned value of {@link #isSetViableWithoutChange()} is false
   */
  public boolean isViableWithoutChange() {
    return getViableWithoutChange();
  }


  /**
   * Returns whether viableWithoutChange is set
   *
   * @return whether viableWithoutChange is set
   */
  public boolean isSetViableWithoutChange() {
    return viableWithoutChange != null;
  }

  /**
   * Sets the value of viableWithoutChange
   * @param viableWithoutChange
   */
  public void setViableWithoutChange(boolean viableWithoutChange) {
    Boolean oldViableWithoutChange = this.viableWithoutChange;
    this.viableWithoutChange = viableWithoutChange;
    firePropertyChange(ReqConstants.viableWithoutChange, oldViableWithoutChange, this.viableWithoutChange);
  }

  /**
   * Unsets the variable viableWithoutChange
   *
   * @return {@code true}, if viableWithoutChange was set before,
   *         otherwise {@code false}
   */
  public boolean unsetViableWithoutChange() {
    if (isSetViableWithoutChange()) {
      Boolean oldViableWithoutChange = viableWithoutChange;
      viableWithoutChange = null;
      firePropertyChange(ReqConstants.viableWithoutChange, oldViableWithoutChange, viableWithoutChange);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(ReqConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(ReqConstants.shortLabel + ":name", getName());
    }
    if (isSetChangedBy()) {
      attributes.put(ReqConstants.shortLabel + ":" + ReqConstants.changedBy, getChangedBy());
    }
    if (isSetViableWithoutChange()) {
      attributes.put(ReqConstants.shortLabel + ":" + ReqConstants.viableWithoutChange, viableWithoutChange.toString());
    }

    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {

    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(ReqConstants.changedBy)) {
        setChangedBy(value);
      }
      else if (attributeName.equals(ReqConstants.viableWithoutChange))
      {
        setViableWithoutChange(Boolean.valueOf(value));
      }
      else
      {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
