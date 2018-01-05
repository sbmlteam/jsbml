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
package org.sbml.jsbml.ext.multi;

import java.util.Map;

import org.sbml.jsbml.AbstractSBase;

/**
 * A speciesTypeComponentMapInProduct defines the mapping between a component in a reactant and a component
 * in a product. The identifications of a component and the speciesReference should be sufficient to identify
 * the component in the context of a reaction. The attributes reactant and reactantComponent can identify the
 * component in a reactant, and the productComponent attribute and the product storing the mapping information
 * can identify the component in a product.
 *
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class SpeciesTypeComponentMapInProduct extends AbstractSBase {

  /**
   * Serial version identifier.
   */
  private static final long serialVersionUID = 1L;
  /**
   * 
   */
  private String reactant;
  /**
   * 
   */
  private String reactantComponent;
  /**
   * 
   */
  private String productComponent;


  /**
   * Creates an SpeciesTypeComponentMapInProduct instance
   */
  public SpeciesTypeComponentMapInProduct() {
    super();
    initDefaults();
  }


  /**
   * Creates a SpeciesTypeComponentMapInProduct instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public SpeciesTypeComponentMapInProduct(int level, int version) {
    super(level, version);
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public SpeciesTypeComponentMapInProduct(SpeciesTypeComponentMapInProduct obj) {
    super(obj);

    // copy all class attributes
    if (obj.isSetReactant()) {
      setReactant(obj.getReactant());
    }
    if (obj.isSetReactantComponent()) {
      setReactantComponent(obj.getReactantComponent());
    }
    if (obj.isSetProductComponent()) {
      setProductComponent(obj.getProductComponent());
    }
  }


  /**
   * clones this class
   */
  @Override
  public SpeciesTypeComponentMapInProduct clone() {
    return new SpeciesTypeComponentMapInProduct(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    packageName = MultiConstants.shortLabel;
    setPackageVersion(-1);
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 6217;
    int result = super.hashCode();
    result = prime * result
        + ((productComponent == null) ? 0 : productComponent.hashCode());
    result = prime * result + ((reactant == null) ? 0 : reactant.hashCode());
    result = prime * result
        + ((reactantComponent == null) ? 0 : reactantComponent.hashCode());
    return result;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
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
    SpeciesTypeComponentMapInProduct other = (SpeciesTypeComponentMapInProduct) obj;

    if (productComponent == null) {
      if (other.productComponent != null) {
        return false;
      }
    } else if (!productComponent.equals(other.productComponent)) {
      return false;
    }
    if (reactant == null) {
      if (other.reactant != null) {
        return false;
      }
    } else if (!reactant.equals(other.reactant)) {
      return false;
    }
    if (reactantComponent == null) {
      if (other.reactantComponent != null) {
        return false;
      }
    } else if (!reactantComponent.equals(other.reactantComponent)) {
      return false;
    }
    return true;
  }


  /**
   * Returns the value of {@link #reactant}.
   *
   * @return the value of {@link #reactant}.
   */
  public String getReactant() {
    if (isSetReactant()) {
      return reactant;
    }

    return null;
  }


  /**
   * Returns whether {@link #reactant} is set.
   *
   * @return whether {@link #reactant} is set.
   */
  public boolean isSetReactant() {
    return reactant != null;
  }


  /**
   * Sets the value of reactant
   *
   * @param reactant the value of reactant to be set.
   */
  public void setReactant(String reactant) {
    String oldReactant = this.reactant;
    this.reactant = reactant;
    firePropertyChange(MultiConstants.reactant, oldReactant, this.reactant);
  }


  /**
   * Unsets the variable reactant.
   *
   * @return {@code true} if reactant was set before, otherwise {@code false}.
   */
  public boolean unsetReactant() {
    if (isSetReactant()) {
      String oldReactant = reactant;
      reactant = null;
      firePropertyChange(MultiConstants.reactant, oldReactant, reactant);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of {@link #reactantComponent}.
   *
   * @return the value of {@link #reactantComponent}.
   */
  public String getReactantComponent() {
    if (isSetReactantComponent()) {
      return reactantComponent;
    }

    return null;
  }


  /**
   * Returns whether {@link #reactantComponent} is set.
   *
   * @return whether {@link #reactantComponent} is set.
   */
  public boolean isSetReactantComponent() {
    return reactantComponent != null;
  }


  /**
   * Sets the value of reactantComponent
   *
   * @param reactantComponent the value of reactantComponent to be set.
   */
  public void setReactantComponent(String reactantComponent) {
    String oldReactantComponent = this.reactantComponent;
    this.reactantComponent = reactantComponent;
    firePropertyChange(MultiConstants.reactantComponent, oldReactantComponent, this.reactantComponent);
  }


  /**
   * Unsets the variable reactantComponent.
   *
   * @return {@code true} if reactantComponent was set before, otherwise {@code false}.
   */
  public boolean unsetReactantComponent() {
    if (isSetReactantComponent()) {
      String oldReactantComponent = reactantComponent;
      reactantComponent = null;
      firePropertyChange(MultiConstants.reactantComponent, oldReactantComponent, reactantComponent);
      return true;
    }
    return false;
  }



  /**
   * Returns the value of {@link #productComponent}.
   *
   * @return the value of {@link #productComponent}.
   */
  public String getProductComponent() {
    if (isSetProductComponent()) {
      return productComponent;
    }

    return null;
  }


  /**
   * Returns whether {@link #productComponent} is set.
   *
   * @return whether {@link #productComponent} is set.
   */
  public boolean isSetProductComponent() {
    return productComponent != null;
  }


  /**
   * Sets the value of productComponent
   *
   * @param productComponent the value of productComponent to be set.
   */
  public void setProductComponent(String productComponent) {
    String oldProductComponent = this.productComponent;
    this.productComponent = productComponent;
    firePropertyChange(MultiConstants.productComponent, oldProductComponent, this.productComponent);
  }


  /**
   * Unsets the variable productComponent.
   *
   * @return {@code true} if productComponent was set before, otherwise {@code false}.
   */
  public boolean unsetProductComponent() {
    if (isSetProductComponent()) {
      String oldProductComponent = productComponent;
      productComponent = null;
      firePropertyChange(MultiConstants.productComponent, oldProductComponent, productComponent);
      return true;
    }
    return false;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetReactant()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.reactant, getReactant());
    }
    if (isSetReactantComponent()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.reactantComponent, getReactantComponent());
    }
    if (isSetProductComponent()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.productComponent, getProductComponent());
    }

    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(MultiConstants.reactant)) {
        setReactant(value);
      }
      else if (attributeName.equals(MultiConstants.reactantComponent)) {
        setReactantComponent(value);
      }
      else if (attributeName.equals(MultiConstants.productComponent)) {
        setProductComponent(value);
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }
}
