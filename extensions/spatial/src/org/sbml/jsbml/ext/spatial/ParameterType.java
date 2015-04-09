/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.Species;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 * @version $Rev$
 * @date 09.09.2011
 */
public class ParameterType extends AbstractSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 1359841900912406174L;


  /**
   * Only one spatialId, {@link SpatialSymbolReference}, or
   * speciesRef, {@link ParameterType}, can be defined per
   * parameter.
   */

  /**
   * Refers to SpIdRef in the documentation
   */
  private String spId;

  /**
   * Refers to species object that the parameter is associated with
   */
  private String speciesRef;

  /**
   * 
   */
  public ParameterType() {
    super();
    initDefaults();
  }

  /**
   * 
   * @param level
   * @param version
   */
  public ParameterType(int level, int version) {
    super(level,version);
    initDefaults();
  }


  /**
   * @param ref
   */
  public ParameterType(ParameterType ref) {
    super(ref);
    if (ref.isSetSpId()) {
      spId = new String(ref.getSpId());
    }
    if (ref.isSetSpeciesReference()) {
      speciesRef = new String(ref.getSpeciesReference());
    }
  }
  
  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(SpatialConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
    setPackageVersion(-1);
    packageName = SpatialConstants.shortLabel;
  }


  /**
   * 
   * @return
   */
  public Species getSpeciesInstance() {
    Model model = getModel();
    return model != null ? model.getSpecies(getSpeciesReference()) : null;
  }

  /**
   * 
   * @return
   */
  public Species getVariableInstance() {
    return getSpeciesInstance();
  }

  /**
   * @return
   */
  public Geometry getGeometryInstance() {

    Model model = getModel();

    SpatialModelPlugin m = (SpatialModelPlugin) model.getExtension(SpatialConstants.shortLabel);
    //TODO: Get the correct element in geometry using the spid
    return m.getGeometry();
  }

  /**
   * @return the variable
   */
  public String getSpId() {
    return isSetSpId() ? spId : "";
  }

  /**
   * @return
   */
  public String getSpeciesReference() {
    return isSetSpeciesReference() ? speciesRef : "";
  }

  /**
   * @return
   */
  public String getVariable() {
    return getSpeciesReference();
  }

  /**
   * @return
   */
  public boolean isSetSpeciesReference() {
    return speciesRef == null ? false : true;
  }

  /**
   * @return
   */
  public boolean isSetVariable() {
    return isSetSpeciesReference();
  }

  /**
   * 
   * @return
   */
  public boolean isSetSpId() {
    return spId != null;
  }

  /**
   * @return
   */
  public boolean isSetSpatialRef() {
    return spId != null;
  }

  /**
   * @param spId the variable to set
   */
  public void setSpId(String spId) {
    this.spId = spId;
    speciesRef = null;
  }

  /**
   * @param speciesRef
   */
  public void setSpeciesReference(String speciesRef) {
    this.speciesRef = speciesRef;

  }

  /**
   * @param speciesRef
   */
  public void setVariable(String speciesRef) {
    setSpeciesReference(speciesRef);
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
      if (param.isSetSpId() && param.isSetSpeciesReference()) {
        throw new SBMLException("Both SpId and SpIdRef cannot be set");
      } else {
        if (param.isSetSpId()) {
          equal &= param.getSpId().equals(getSpId());
        }
        else if (param.isSetSpeciesReference()){
          equal &= param.getSpeciesReference().equals(getSpeciesReference());
        }
      }
    }
    return equal;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ParameterType [spId=");
    builder.append(spId);
    builder.append(", speciesRef=");
    builder.append(speciesRef);
    builder.append("]");
    return builder.toString();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 983;//Change this prime number
    int hashCode = super.hashCode();
    if (isSetSpId()) {
      hashCode += prime * getSpId().hashCode();
    }
    if (isSetSpeciesReference()) {
      hashCode += prime * getSpeciesReference().hashCode();
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
      attributes.remove("spatialRef");
      attributes.put(SpatialConstants.shortLabel + ":spatialRef", getSpId());
    }
    if (isSetSpeciesReference()) {
      attributes.remove("variable");
      attributes.put(SpatialConstants.shortLabel + ":variable",
        String.valueOf(getSpeciesReference()));
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
      if (attributeName.equals(SpatialConstants.spatialId)) {
        try {
          setSpId(value);
        } catch (Exception e) {
          MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ"), value,
            SpatialConstants.spatialId);
        }
      }
      else if (attributeName.equals(SpatialConstants.variable)) {
        try {
          setSpeciesReference(value);
        } catch (Exception e) {
          MessageFormat.format(SpatialConstants.bundle.getString("COULD_NOT_READ"), value, SpatialConstants.variable);
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
