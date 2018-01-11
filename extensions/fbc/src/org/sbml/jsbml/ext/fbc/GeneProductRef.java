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
package org.sbml.jsbml.ext.fbc;

import java.util.Map;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.Model;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * References a {@link GeneProduct}. 
 *
 * <p>Introduced to FBC in version 2.</p>
 * 
 * @author Andreas Dr&auml;ger
 * @since 1.1
 */
public class GeneProductRef extends AbstractNamedSBase implements Association, UniqueNamedSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -2978063936433918607L;

  /**
   * 
   */
  private String geneProduct;

  /**
   * Creates a new {@link GeneProductRef} instance.
   */
  public GeneProductRef() {
    super();
    initDefaults();
  }

  /**
   * Creates a new {@link GeneProductRef} instance.
   * 
   * @param gpr the instance to clone
   */
  public GeneProductRef(GeneProductRef gpr) {
    super(gpr);
    if (gpr.isSetGeneProduct()) {
      setGeneProduct(gpr.getGeneProduct());
    }
  }

  /**
   * Creates a new {@link GeneProductRef} instance.
   * 
   * @param level the SBML level
   * @param version the SBML version
   */
  public GeneProductRef(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * Creates a new {@link GeneProductRef} instance.
   * 
   * @param id the id
   */
  public GeneProductRef(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a new {@link GeneProductRef} instance.
   * 
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   */
  public GeneProductRef(String id, int level, int version) {
    super(id, level, version);
    initDefaults();
  }

  /**
   * Creates a new {@link GeneProductRef} instance.
   * 
   * @param id the id
   * @param name the name
   * @param level the SBML level
   * @param version the SBML version
   */
  public GeneProductRef(String id, String name, int level, int version) {
    super(id, name, level, version);
    initDefaults();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public GeneProductRef clone() {
    return new GeneProductRef(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
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
    GeneProductRef other = (GeneProductRef) obj;
    if (geneProduct == null) {
      if (other.geneProduct != null) {
        return false;
      }
    } else if (!geneProduct.equals(other.geneProduct)) {
      return false;
    }
    return true;
  }

  /**
   * Returns the value of {@link #geneProduct}.
   *
   * @return the value of {@link #geneProduct}.
   */
  public String getGeneProduct() {
    if (isSetGeneProduct()) {
      return geneProduct;
    }
    return "";
  }

  /**
   * Returns the {@link GeneProduct} referenced by this {@link GeneProductRef}.
   * 
   * @return the {@link GeneProduct} instance referenced by this {@link GeneProductRef} or null.
   */
  public GeneProduct getGeneProductInstance() {
    if (isSetGeneProduct()) {
      Model m = getModel();
      return (m != null) ?(GeneProduct) m.findNamedSBase(getGeneProduct()) : null;
    }
    return null;
  }

  /**
   * Returns whether the gene product id is set and this id is a valid {@link GeneProduct} id in this model.
   *  
   * @return {@code true} the gene product id is set and this id is a valid {@link GeneProduct} id in this model.
   */
  public boolean isSetGeneProductInstance() {
    return getGeneProductInstance() != null;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((geneProduct == null) ? 0 : geneProduct.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  @Override
  public boolean isIdMandatory() {
    return false;
  }

  /**
   * Returns whether {@link #geneProduct} is set.
   *
   * @return whether {@link #geneProduct} is set.
   */
  public boolean isSetGeneProduct() {
    return geneProduct != null;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(FBCConstants.geneProduct)) {
        setGeneProduct(value);
      } else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

  /**
   * Sets the value of geneProduct
   * 
   * @param geneProduct the {@link GeneProduct} id
   */
  public void setGeneProduct(String geneProduct) {
    String oldGeneProduct = this.geneProduct;
    this.geneProduct = geneProduct;
    firePropertyChange(FBCConstants.geneProduct, oldGeneProduct, this.geneProduct);
  }

  /**
   * Unsets the variable geneProduct
   *
   * @return {@code true}, if geneProduct was set before,
   *         otherwise {@code false}
   */
  public boolean unsetGeneProduct() {
    if (isSetGeneProduct()) {
      String oldGeneProduct = geneProduct;
      geneProduct = null;
      firePropertyChange(FBCConstants.geneProduct, oldGeneProduct, geneProduct);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetGeneProduct()) {
      attributes.put(FBCConstants.shortLabel + ":" + FBCConstants.geneProduct, getGeneProduct());
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
