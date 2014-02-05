/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
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
 *
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 */
public class MathChanged extends AbstractNamedSBase {

  /**
   * The viableWithoutChange attribute is required, and is of type boolean. The attribute should be used to indicate
   * whether an interpreter that does not understand the package from the changedBy attribute would have an interpretable
   * version of the mathematics for the given component in a model. This can be established using two criteria:
   * “Complete” and ”Workable”. Obviously, if the math being interpreted is incomplete, the viableWithoutChange
   * attribute must be “false”. If the math is complete, then whether the solution is “workable” requires a judgment
   * call on the part of the modeler: if the modeler feels that the alternative version makes sense in an alternative context,
   * they may set the attribute value to “true”; conversely, if they feel that the resulting model component makes no
   * sense, even if technically “complete”, then they should set the attribute value to “false”.
   */
  private Boolean viableWithoutChange;

  /**
   * The changedBy attribute is required, and is of type string. The attribute must be set to the namespace URI of the
   * SBML Level 3 package that redefines or alters the mathematical semantics of the parent object. In other words, if
   * the mathematical semantics of a given component C in a model is changed by the use of a Level 3 package P, then C
   * can be given a MathChanged child, and its attribute changedBy should be P’s namespace URI.
   */
  private String changedBy;

  /**
   * Creates an MathChanged instance 
   */
  public MathChanged() {
    super();
    initDefaults();
  }

  /**
   * Creates a {@link MathChanged} instance with an id. 
   * 
   * @param id
   */
  public MathChanged(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a {@link MathChanged} instance with a level and version. 
   * 
   * @param level
   * @param version
   */
  public MathChanged(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Creates a {@link MathChanged} instance with an id, level, and version. 
   * 
   * @param id
   * @param level
   * @param version
   */
  public MathChanged(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a {@link MathChanged} instance with an id, name, level, and version. 
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public MathChanged(String id, String name, int level, int version) {
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
   */
  public MathChanged(MathChanged obj) {
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
  public MathChanged clone() {
    return new MathChanged(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    // TODO : get the correct namespace from the SBMLdocument, otherwise don't set it yet.
    setNamespace(ReqConstants.namespaceURI);
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
    return this.changedBy != null;
  }

  /**
   * Sets the value of changedBy
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
      String oldChangedBy = this.changedBy;
      this.changedBy = null;
      firePropertyChange(ReqConstants.changedBy, oldChangedBy, this.changedBy);
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
    return this.viableWithoutChange != null;
  }

  /**
   * Sets the value of viableWithoutChange
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
      Boolean oldViableWithoutChange = this.viableWithoutChange;
      this.viableWithoutChange = null;
      firePropertyChange(ReqConstants.viableWithoutChange, oldViableWithoutChange, this.viableWithoutChange);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
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
