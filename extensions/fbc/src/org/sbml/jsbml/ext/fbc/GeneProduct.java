/*
 * $Id: GeneProduct.java 2166 2015-03-31 06:36:12Z andreas-draeger $
 * $URL: svn://svn.code.sf.net/p/jsbml/code/trunk/extensions/fbc/src/org/sbml/jsbml/ext/fbc/GeneProduct.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2015  jointly by the following organizations:
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
package org.sbml.jsbml.ext.fbc;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * Introduced to FBC in version 2.
 * 
 * @author Andreas Dr&auml;ger
 * @version $Rev: 2166 $
 * @since 1.1
 * @date 06.03.2015
 */
public class GeneProduct extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -6117717488126260095L;

  /**
   * 
   */
  private String label;

  /**
   * 
   */
  public GeneProduct() {
    super();
    initDefaults();
  }

  /**
   * @param nsb
   */
  public GeneProduct(GeneProduct nsb) {
    super(nsb);
  }

  /**
   * @param level
   * @param version
   */
  public GeneProduct(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * @param id
   */
  public GeneProduct(String id) {
    super(id);
    initDefaults();
  }

  /**
   * @param id
   * @param level
   * @param version
   */
  public GeneProduct(String id, int level, int version) {
    super(id, level, version);
    initDefaults();
  }

  /**
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public GeneProduct(String id, String name, int level, int version) {
    super(id, name, level, version);
    initDefaults();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public GeneProduct clone() {
    return new GeneProduct(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setNamespace(FBCConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
    setPackageVersion(-1);
    packageName = FBCConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (!super.equals(obj)) {
      return false;
    }
    GeneProduct other = (GeneProduct) obj;
    if (label == null) {
      if (other.label != null) {
        return false;
      }
    } else if (!label.equals(other.label)) {
      return false;
    }
    return true;
  }

  /**
   * Returns the value of {@link #label}
   *
   * @return the value of {@link #label}
   */
  public String getLabel() {
    if (isSetLabel()) {
      return label;
    }
    return "";
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((label == null) ? 0 : label.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return true;
  }

  /**
   * Returns whether {@link #label} is set
   *
   * @return whether {@link #label} is set
   */
  public boolean isSetLabel() {
    return label != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(FBCConstants.geneProductIdentifier) || attributeName.equals(FBCConstants.label)) {
        setLabel(value);
      } else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }

  /**
   * Sets the value of {@link #label}
   * @param label
   */
  public void setLabel(String label) {
    String oldLabel = this.label;
    this.label = label;
    firePropertyChange(FBCConstants.label, oldLabel, this.label);
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(getClass().getSimpleName());
    builder.append(" [label=");
    builder.append(label);
    builder.append(", ");
    builder.append("id=");
    builder.append(getId());
    builder.append(", ");
    builder.append("metaid=");
    builder.append(getMetaId());
    builder.append(", ");
    builder.append("name=");
    builder.append(getName());
    builder.append("]");
    return builder.toString();
  }

  /**
   * Unsets the variable {@link #label}
   *
   * @return {@code true}, if {@link #label} was set before,
   *         otherwise {@code false}
   */
  public boolean unsetLabel() {
    if (isSetLabel()) {
      String oldLabel = label;
      label = null;
      firePropertyChange(FBCConstants.label, oldLabel, label);
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
      attributes.put(FBCConstants.shortLabel + ":id", getId());
    }
    if (isSetName()) {
      attributes.remove("name");
      attributes.put(FBCConstants.shortLabel + ":name", getName());
    }
    if (isSetLabel()) {
      attributes.put(FBCConstants.shortLabel + ":" + FBCConstants.label, getLabel());
    }

    return attributes;
  }

}
