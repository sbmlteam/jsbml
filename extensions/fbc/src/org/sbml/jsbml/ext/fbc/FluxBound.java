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
package org.sbml.jsbml.ext.fbc;

import java.util.Locale;
import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.Reaction;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.UniqueNamedSBase;
import org.sbml.jsbml.util.StringTools;

/**
 * {@link FluxBound} is an FBC Version 1 class which holds a single (in)equality that provides
 * the maximum or minimum value that a reaction flux can obtain at steady state.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 * @deprecated Only defined in FBC Version 1.
 */
@Deprecated
public class FluxBound extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Allowable operations for flux bounds.
   * 
   * @author Andreas Dr&auml;ger
   * @since 1.0
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public enum Operation {
    /**
     * equal
     */
    EQUAL("equal"),
    /**
     * greaterEqual
     */
    GREATER_EQUAL("greaterEqual"),
    /**
     * lessEqual
     */
    LESS_EQUAL("lessEqual");

    /**
     * @param value
     * @return
     */
    public static Operation fromString(String value) {
      if (value == null) {
        throw new IllegalArgumentException();
      }

      for (Operation v : values()) {
        if (value.equalsIgnoreCase(v.id)) {
          return v;
        }
      }
      throw new IllegalArgumentException();
    }

    /**
     * SBML attribute name.
     */
    private String id;

    /**
     * Memorize the SBML attribute name as id.
     * 
     * @param id
     */
    private Operation(String id) {
      this.id = id;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
      return id;
    }

  }

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -8885319163985464653L;
  /**
   * 
   */
  private boolean isSetValue = false;
  /**
   * 
   */
  private Operation operation;
  /**
   * 
   */
  private String reaction;
  /**
   * 
   */
  private Double value;


  /**
   * Creates an instance of FluxBound.
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public FluxBound() {
    super();
    initDefaults();
  }

  /**
   * 
   * @param fb
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public FluxBound(FluxBound fb) {
    super(fb);

    if (fb.isSetOperation()) {
      setOperation(fb.getOperation());
    }
    if (fb.isSetReaction()) {
      setReaction(new String(fb.getReaction()));
    }
    if (fb.isSetValue()) {
      setValue(fb.getValue());
    }
  }

  /**
   * Creates a FluxBound instance with a level and version.
   * 
   * @param level
   * @param version
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public FluxBound(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Creates a FluxBound instance with an id.
   * 
   * @param id
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public FluxBound(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a {@link FluxBound} instance with an id, level, and version.
   * 
   * @param id
   * @param level
   * @param version
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public FluxBound(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a FluxBound instance with an id, name, level, and version.
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public FluxBound(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(
      Integer.valueOf(FBCConstants.MIN_SBML_LEVEL),
      Integer.valueOf(FBCConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  @Deprecated
  public FluxBound clone() {
    return new FluxBound(this);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  @Deprecated
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }

    FluxBound other = (FluxBound) obj;
    if (operation != other.operation) {
      return false;
    }
    if (reaction == null) {
      if (other.reaction != null) {
        return false;
      }
    } else if (!reaction.equals(other.reaction)) {
      return false;
    }
    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }
    return true;
  }

  /**
   * Returns the operation
   * 
   * @return the operation
   */
  @Deprecated
  public Operation getOperation() {
    return operation;
  }

  /**
   * Returns the reaction id
   * 
   * @return the reaction id
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public String getReaction() {
    return reaction;
  }

  /**
   * @return The {@link Reaction} to which this flux bound belongs.
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public Reaction getReactionInstance() {
    if (getModel() == null) {
      return null;
    }
    return getModel().getReaction(getReaction());
  }

  /**
   * Returns the value
   * 
   * @return the value
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public double getValue() {
    return isSetValue() ? value : Double.valueOf(0d);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  @Deprecated
  public int hashCode() {
    final int prime = 2029;
    int result = super.hashCode();
    result = prime * result + ((operation == null) ? 0 : operation.hashCode());
    result = prime * result + ((reaction == null) ? 0 : reaction.hashCode());
    result = prime * result + ((value == null) ? 0 : value.hashCode());
    return result;
  }

  /**
   * Initializes the default values using the namespace.
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = FBCConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  @Deprecated
  public boolean isIdMandatory() {
    return false;
  }

  /**
   * Returns whether operation is set
   *
   * @return whether operation is set
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public boolean isSetOperation() {
    return operation != null;
  }

  /**
   * Returns whether reaction is set
   *
   * @return whether reaction is set
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public boolean isSetReaction() {
    return reaction != null;
  }

  /**
   * @return {@code true} if a {@link Reaction} with the id given by
   *         {@link #getReaction} exists in the {@link Model}, {@code false}
   *         otherwise.
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public boolean isSetReactionInstance() {
    return getReactionInstance() != null;
  }

  /**
   * 
   * @return
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public boolean isSetValue() {
    return isSetValue;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#readAttribute(String attributeName, String prefix, String value)
   */
  @Override
  @Deprecated
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix,
      value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(FBCConstants.reaction)) {
        setReaction(value);
      } else if (attributeName.equals(FBCConstants.operation)) {
        try
        {
          setOperation(Operation.fromString(value));
        }
        catch (Exception e)
        {
          throw new SBMLException("Could not recognized the value '" + value
            + "' for the attribute " + FBCConstants.operation
            + " on the 'fluxBound' element.");
        }
        return true;
      } else if (attributeName.equals(FBCConstants.value)) {
        setValue(StringTools.parseSBMLDouble(value));
      } else {
        isAttributeRead = false;
      }

    }

    return isAttributeRead;
  }

  /**
   * 
   * @param operation  the operation to set
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public void setOperation(Operation operation) {
    Operation oldOperation = this.operation;
    this.operation = operation;
    firePropertyChange(FBCConstants.operation, oldOperation, operation);
  }

  /**
   * 
   * @param reaction
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public void setReaction(Reaction reaction) {
    setReaction(reaction.getId());
  }

  /**
   * Sets the the reaction id
   * 
   * @param reaction the reaction id to set
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public void setReaction(String reaction) {
    String oldReaction = this.reaction;
    this.reaction = reaction;
    firePropertyChange(FBCConstants.reaction, oldReaction, reaction);
  }

  /**
   * @param value the value to set
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public void setValue(double value) {
    Double oldValue = this.value;
    this.value = value;
    isSetValue = true;
    firePropertyChange(FBCConstants.value, oldValue, this.value);
  }

  /**
   * Unsets the variable operation
   *
   * @return {@code true}, if operation was set before,
   *         otherwise {@code false}
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public boolean unsetOperation() {
    if (isSetOperation()) {
      Operation oldOperation = operation;
      operation = null;
      firePropertyChange(FBCConstants.operation, oldOperation, operation);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable reaction
   *
   * @return {@code true}, if reaction was set before,
   *         otherwise {@code false}
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public boolean unsetReaction() {
    if (isSetReaction()) {
      String oldReaction = reaction;
      reaction = null;
      firePropertyChange(FBCConstants.reaction, oldReaction, reaction);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable value
   *
   * @return {@code true}, if value was set before,
   *         otherwise {@code false}
   * @deprecated Only defined in FBC version 1.
   */
  @Deprecated
  public boolean unsetValue() {
    if (isSetValue()) {
      double oldValue = value;
      value = null;
      firePropertyChange(FBCConstants.value, oldValue, value);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.element.SBase#writeXMLAttributes()
   */
  @Override
  @Deprecated
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (reaction != null) {
      attributes.put(FBCConstants.shortLabel + ':' + FBCConstants.reaction,
        getReaction());
    }
    if (operation != null) {
      attributes.put(FBCConstants.shortLabel + ':' + FBCConstants.operation,
        getOperation().toString());
    }
    if (isSetValue()) {
      attributes.put(FBCConstants.shortLabel + ':' + FBCConstants.value,
        StringTools.toString(Locale.ENGLISH, getValue()));
    }
    if (isSetId()) {
      attributes.remove("id");
      attributes.put(FBCConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(FBCConstants.shortLabel + ":name", getName());
    }

    return attributes;
  }

}
