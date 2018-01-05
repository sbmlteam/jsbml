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
 * 6. Boston University, Boston, MA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.dyn;

import java.text.MessageFormat;
import java.util.Map;
import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * @author Harold G&oacute;mez
 * @since 1.0
 */
public class SpatialComponent extends AbstractNamedSBase implements
UniqueNamedSBase {

  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 3081752496996673423L;

  /**
   * Identifies individual components of spatial location, orientation or
   * force vector of an object
   */
  private SpatialKind spatialIndex;

  /**
   * Stores the Id of a variable element defined in the model
   */
  private String variable;

  /**
   * Empty constructor
   */
  public SpatialComponent() {
    super();
    initDefaults();
  }

  /**
   * Initializes custom Class attributes
   * */
  private void initDefaults() {
    setPackageVersion(-1);
    packageName = DynConstants.shortLabel;
    variable = null;
    spatialIndex = null;
  }

  /**
   * Constructor
   * 
   * @param level
   * @param version
   */
  public SpatialComponent(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Constructor
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public SpatialComponent(String id, String name, int level, int version) {
    super(id, name, level, version);
    initDefaults();
  }

  /**
   * Constructor
   * 
   * @param spatialComponent
   */
  public SpatialComponent(SpatialComponent spatialComponent) {
    super(spatialComponent);

    if (spatialComponent.isSetSpatialIndex()) {
      setSpatialIndex(spatialComponent.getSpatialIndex());
    }
    if (spatialComponent.isSetVariable()) {
      setVariable(spatialComponent.getVariable());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public AbstractSBase clone() {
    return new SpatialComponent(this);
  }

  /**
   * Returns the value of spatialIndex
   * 
   * @return the value of spatialIndex
   */
  public SpatialKind getSpatialIndex() {
    if (isSetSpatialIndex()) {
      return spatialIndex;
    }
    return null;
  }

  /**
   * Returns whether spatialIndex is set
   * 
   * @return whether spatialIndex is set
   */
  public boolean isSetSpatialIndex() {
    return spatialIndex != null;
  }

  /**
   * Sets the value of spatialIndex
   * 
   * @param spatialIndex
   */
  public void setSpatialIndex(SpatialKind spatialIndex) {
    SpatialKind oldSpatialIndex = this.spatialIndex;
    this.spatialIndex = spatialIndex;
    firePropertyChange(DynConstants.spatialIndex, oldSpatialIndex,
      this.spatialIndex);
  }

  /**
   * Unsets the variable spatialIndex
   * 
   * @return {@code true}, if spatialIndex was set before, otherwise
   *         {@code false}
   */
  public boolean unsetSpatialIndex() {
    if (isSetSpatialIndex()) {
      SpatialKind oldSpatialIndex = spatialIndex;
      spatialIndex = null;
      firePropertyChange(DynConstants.spatialIndex, oldSpatialIndex,
        spatialIndex);
      return true;
    }
    return false;
  }

  /**
   * Returns the value of variable
   *
   * @return the value of variable
   */
  public String getVariable() {
    if (isSetVariable()) {
      return variable;
    }
    return null;
  }

  /**
   * Returns whether variable is set
   *
   * @return whether variable is set
   */
  public boolean isSetVariable() {
    return variable != null;
  }

  /**
   * Sets the value of {@link #variable}
   * @param variable
   */
  public void setVariable(String variable) {
    String oldVariable = this.variable;
    this.variable = variable;
    firePropertyChange(DynConstants.variable, oldVariable, this.variable);
  }

  /**
   * Unsets the variable field
   *
   * @return {@code true}, if variable was set before, otherwise {@code false}
   */
  public boolean unsetField() {
    if (isSetVariable()) {
      String oldVariable = variable;
      variable = null;
      firePropertyChange(DynConstants.variable, oldVariable,
        variable);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!super.equals(object)) {
      return false;
    }
    if (getClass() != object.getClass()) {
      return false;
    }
    SpatialComponent other = (SpatialComponent) object;
    if (variable == null) {
      if (other.variable != null) {
        return false;
      }
    } else if (!variable.equals(other.variable)) {
      return false;
    }
    if (spatialIndex == null) {
      if (other.spatialIndex != null) {
        return false;
      }
    } else if (!spatialIndex.equals(other.spatialIndex)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3467;
    int result = super.hashCode();
    result = prime * result + ((variable == null) ? 0 : variable.hashCode());
    result = prime * result
        + ((spatialIndex == null) ? 0 : spatialIndex.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetVariable()) {
      attributes.remove("variable");
      attributes.put(DynConstants.shortLabel + ":" + DynConstants.variable,
        variable);
    }
    if (isSetSpatialIndex()) {
      attributes.remove("spatialIndex");
      attributes.put(DynConstants.shortLabel + ":"
          + DynConstants.spatialIndex, spatialIndex.toString());
    }
    if (isSetId()) {
      attributes.remove("id");
      attributes.put(DynConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(DynConstants.shortLabel + ":name", getName());
    }
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);
    try {
      if (!isAttributeRead) {
        if (attributeName.equals(DynConstants.variable)) {
          setVariable(value);
          return true;
        }
        if (attributeName.equals(DynConstants.spatialIndex)) {
          setSpatialIndex(SpatialKind.valueOf(value));
          return true;
        }
      }
    } catch (Exception e) {
      MessageFormat.format(
        DynConstants.bundle.getString("COULD_NOT_READ_SPATIALCOMPONENT"), value,
        attributeName.equals(DynConstants.variable)?DynConstants.variable:DynConstants.spatialIndex);
    }

    return isAttributeRead;
  }
}
