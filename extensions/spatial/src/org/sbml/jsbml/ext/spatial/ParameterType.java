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
package org.sbml.jsbml.ext.spatial;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.Parameter;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.Species;

/**
 * Top level class for all possible child of a {@link SpatialParameterPlugin}.
 * 
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class ParameterType extends AbstractSBase {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(ParameterType.class);

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 1359841900912406174L;


  /**
   * Only one spatialId, {@link SpatialSymbolReference}, or
   * variable, {@link ParameterType}, can be defined per
   * parameter.
   */

  /**
   * Refers to the id of any element defined in the Geometry of the model.
   *
   */
  private String spatialRef;

  /**
   * 
   */
  private String variable;
  
  
  /**
   * Creates a new {@link ParameterType} instance.
   */
  public ParameterType() {
    super();
    initDefaults();
  }

  /**
   * Creates a new {@link ParameterType} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public ParameterType(int level, int version) {
    super(level,version);
    initDefaults();
  }


  /**
   * Creates a new {@link ParameterType} instance.
   * 
   * @param ref the instance to clone
   */
  public ParameterType(ParameterType ref) {
    super(ref);
    if (ref.isSetSpatialRef()) {
      setSpatialRef(ref.getSpatialRef());
    }
    if (ref.isSetVariable()) {
      setVariable(ref.getVariable());
    }
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = SpatialConstants.shortLabel;
  }


  /**
   * Returns the {@link Geometry} instance.
   * 
   * @return the {@link Geometry} instance.
   */
  public Geometry getGeometryInstance() {

    Model model = getModel();

    SpatialModelPlugin m = (SpatialModelPlugin) model.getExtension(SpatialConstants.shortLabel);
    return m.getGeometry();
  }

  // TODO: add a method to get the correct element in geometry using the spatialRef id

  /**
   * Returns the spatial reference (id of any element defined in the Geometry of the model).
   * 
   * @return the spatial reference
   * @see #getSpatialRef()
   */
  public String getSpId() {
    return getSpatialRef();
  }

  /**
   * Returns the spatial reference (id of any element defined in the Geometry of the model).
   * 
   * @return the spatial reference
   */
  public String getSpatialRef() {
    return isSetSpId() ? spatialRef : "";
  }

  /**
   * Returns {@code true} is the spatial reference is set.
   * 
   * @return {@code true} is the spatial reference is set.
   * @see #isSetSpatialRef()
   */
  public boolean isSetSpId() {
    return isSetSpatialRef();
  }

  /**
   * Returns {@code true} is the spatial reference is set.
   * 
   * @return {@code true} is the spatial reference is set.
   */
  public boolean isSetSpatialRef() {
    return spatialRef != null;
  }

  /**
   * Sets the spatial reference
   * 
   * @param spatialRef the spatialRef to set
   * @see #setSpatialRef(String)
   */
  public void setSpId(String spatialRef) {
    setSpatialRef(spatialRef);
  }
  
  /**
   * Sets the spatial reference
   * 
   * @param spatialRef the spatialRef to set
   */
  public void setSpatialRef(String spatialRef) {
    String oldSpatialRef= this.spatialRef;
    this.spatialRef = spatialRef;
    firePropertyChange(SpatialConstants.spatialRef, oldSpatialRef, this.spatialRef);
  }
  
  
  /**
   * Returns the value of {@link #variable}.
   *
   * @return the value of {@link #variable}.
   */
  public String getVariable() {
    if (isSetVariable()) {
      return variable;
    }
    
    return "";
  }

  /**
   * Returns the {@link Species} or {@link Parameter} instance associated with {@link #variable}.
   *
   * @return an {@link SBase} associated with {@link #variable} or null.
   */
  public SBase getVariableInstance() {
    Model model = getModel();
    
    SBase sbase = model != null ? model.findUniqueSBase(variable) : null;
    return sbase;
  }
  
  /**
   * Returns the {@link Species} instance associated with {@link #variable}.
   *
   * @return a {@link Species} associated with {@link #variable} or null.
   */
  public Species getSpeciesInstance() {
    SBase sbase = getVariableInstance();
    
    if (sbase != null && sbase instanceof Species) {
      return (Species) sbase;
    }
    
    return null;
  }

  /**
   * Returns the value of {@link #variable}.
   *
   * @return the value of {@link #variable}.
   * @see #getVariable()
   */
  public String getSpeciesReference() {
    return getVariable();
  }
  
  /**
   * Returns the {@link Parameter} instance associated with {@link #variable}.
   *
   * @return a {@link Parameter} associated with {@link #variable} or null.
   */
  public Parameter getParameterInstance() {
    SBase sbase = getVariableInstance();
    
    if (sbase != null && sbase instanceof Parameter) {
      return (Parameter) sbase;
    }
    
    return null;
  }

  /**
   * Returns whether {@link #variable} is set.
   *
   * @return whether {@link #variable} is set.
   * @see #isSetVariable()
   */
  public boolean isSetSpeciesReference() {
    return isSetVariable();
  }

  /**
   * Returns whether {@link #variable} is set.
   *
   * @return whether {@link #variable} is set.
   */
  public boolean isSetVariable() {
    return variable != null;
  }

  /**
   * Sets the value of variable
   *
   * @param variable the value of variable to be set.
   * @see #setVariable(String)
   */
  public void setSpeciesReference(String variable) {
    setVariable(variable);
  }

  /**
   * Sets the value of variable
   *
   * @param variable the value of variable to be set.
   */
  public void setVariable(String variable) {
    String oldVariable = this.variable;
    this.variable = variable;
    firePropertyChange(SpatialConstants.variable, oldVariable, this.variable);
  }

  /**
   * Unsets the spatialRef attribute.
   *
   * @return {@code true} if spatialRef was set before, otherwise {@code false}.
   */
  public boolean unsetSpatialRef() {
    if (isSetSpatialRef()) {
      String oldSpatialRef = this.spatialRef;
      this.spatialRef = null;
      firePropertyChange(SpatialConstants.spatialRef, oldSpatialRef, this.spatialRef);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable attribute.
   *
   * @return {@code true} if variable was set before, otherwise {@code false}.
   */
  public boolean unsetVariable() {
    if (isSetVariable()) {
      String oldVariable = this.variable;
      this.variable = null;
      firePropertyChange(SpatialConstants.variable, oldVariable, this.variable);
      return true;
    }
    return false;
  }
  
  
  /**
   * Sets the value of variable
   *
   * @param variable the value of variable to be set.
   * @see #setVariable(String)
   */
  public void setSpeciesRef(String variable) {
    setVariable(variable);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#clone()
   */
  @Override
  public ParameterType clone() {
    return new ParameterType(this);
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    return null;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    return 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      ParameterType param = (ParameterType) object;
      if (param.isSetSpId()) {
        equal &= param.getSpId().equals(getSpId());
      }
      if (param.isSetVariable()) {
        equal &= param.getVariable().equals(getVariable());
      }
    }

    return equal;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1901;
    int hashCode = super.hashCode();
    if (isSetSpId()) {
      hashCode += prime * getSpId().hashCode();
    }
    if (isSetVariable()) {
      hashCode += prime * getVariable().hashCode();
    }
    
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetSpId()) {
      attributes.put(SpatialConstants.shortLabel + ":spatialRef", getSpId());
    }
    if (isSetVariable()) {
      attributes.put(SpatialConstants.shortLabel + ":variable",
        String.valueOf(getVariable()));
    }
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.spatialRef)) {
        try {
          setSpatialRef(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.spatialRef, getElementName()));
        }
      }
      else if (attributeName.equals(SpatialConstants.variable)) {
        try {
          setVariable(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.variable, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
