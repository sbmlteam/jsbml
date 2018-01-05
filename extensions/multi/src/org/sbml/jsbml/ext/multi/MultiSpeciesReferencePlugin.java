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

import java.text.MessageFormat;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SpeciesReference;

/**
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class MultiSpeciesReferencePlugin extends MultiSimpleSpeciesReferencePlugin {


  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 2L;

  /**
   * 
   */
  private ListOf<SpeciesTypeComponentMapInProduct> listOfSpeciesTypeComponentMapInProducts;


  /**
   * Creates an MultiSpeciesReferencePlugin instance
   */
  public MultiSpeciesReferencePlugin() {
    super();
    initDefaults();
  }


  /**
   * Creates a MultiSpeciesReferencePlugin instance with a {@link SpeciesReference}.
   * 
   * @param sp the {@link SpeciesReference} for the new element.
   */
  public MultiSpeciesReferencePlugin(SpeciesReference sp) {
    super(sp);
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public MultiSpeciesReferencePlugin(MultiSpeciesReferencePlugin obj) {
    super(obj);

    // copy all class attributes
    if (obj.isSetListOfSpeciesTypeComponentMapInProducts()) {
      setListOfSpeciesTypeComponentMapInProducts(obj.getListOfSpeciesTypeComponentMapInProducts().clone());
    }
  }


  /**
   * clones this class
   */
  @Override
  public MultiSpeciesReferencePlugin clone() {
    return new MultiSpeciesReferencePlugin(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  @Override
  public void initDefaults() {
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 6287;
    int result = super.hashCode();
    result = prime
        * result
        + ((listOfSpeciesTypeComponentMapInProducts == null) ? 0
          : listOfSpeciesTypeComponentMapInProducts.hashCode());
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
    MultiSpeciesReferencePlugin other = (MultiSpeciesReferencePlugin) obj;
    if (listOfSpeciesTypeComponentMapInProducts == null) {
      if (other.listOfSpeciesTypeComponentMapInProducts != null) {
        return false;
      }
    } else if (!listOfSpeciesTypeComponentMapInProducts.equals(other.listOfSpeciesTypeComponentMapInProducts)) {
      return false;
    }
    return true;
  }

  /**
   * Returns {@code true} if {@link #listOfSpeciesTypeComponentMapInProducts} contains at least
   * one element.
   *
   * @return {@code true} if {@link #listOfSpeciesTypeComponentMapInProducts} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean isSetListOfSpeciesTypeComponentMapInProducts() {
    if (listOfSpeciesTypeComponentMapInProducts == null) {
      return false;
    }
    return true;
  }


  /**
   * Returns the {@link #listOfSpeciesTypeComponentMapInProducts}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfSpeciesTypeComponentMapInProducts}.
   */
  public ListOf<SpeciesTypeComponentMapInProduct> getListOfSpeciesTypeComponentMapInProducts() {
    if (listOfSpeciesTypeComponentMapInProducts == null) {
      listOfSpeciesTypeComponentMapInProducts = new ListOf<SpeciesTypeComponentMapInProduct>();
      listOfSpeciesTypeComponentMapInProducts.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSpeciesTypeComponentMapInProducts.setPackageName(null);
      listOfSpeciesTypeComponentMapInProducts.setPackageName(MultiConstants.shortLabel);
      listOfSpeciesTypeComponentMapInProducts.setSBaseListType(ListOf.Type.other);
      listOfSpeciesTypeComponentMapInProducts.setOtherListName(MultiConstants.listOfSpeciesTypeComponentMapInProducts);
      
      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(listOfSpeciesTypeComponentMapInProducts);
      }
    }
    return listOfSpeciesTypeComponentMapInProducts;
  }


  /**
   * Sets the given {@code ListOf<SpeciesTypeComponentMapInProduct>}.
   * If {@link #listOfSpeciesTypeComponentMapInProducts} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfSpeciesTypeComponentMapInProducts the list of {@link SpeciesTypeComponentMapInProduct}
   */
  public void setListOfSpeciesTypeComponentMapInProducts(ListOf<SpeciesTypeComponentMapInProduct> listOfSpeciesTypeComponentMapInProducts) {
    unsetListOfSpeciesTypeComponentMapInProducts();
    this.listOfSpeciesTypeComponentMapInProducts = listOfSpeciesTypeComponentMapInProducts;

    if (listOfSpeciesTypeComponentMapInProducts != null) {
      listOfSpeciesTypeComponentMapInProducts.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfSpeciesTypeComponentMapInProducts.setPackageName(null);
      listOfSpeciesTypeComponentMapInProducts.setPackageName(MultiConstants.shortLabel);
      listOfSpeciesTypeComponentMapInProducts.setSBaseListType(ListOf.Type.other);
      listOfSpeciesTypeComponentMapInProducts.setOtherListName(MultiConstants.listOfSpeciesTypeComponentMapInProducts);
      
      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(this.listOfSpeciesTypeComponentMapInProducts);
      }
    }
  }


  /**
   * Returns {@code true} if {@link #listOfSpeciesTypeComponentMapInProducts} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfSpeciesTypeComponentMapInProducts} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfSpeciesTypeComponentMapInProducts() {
    if (isSetListOfSpeciesTypeComponentMapInProducts()) {
      ListOf<SpeciesTypeComponentMapInProduct> oldSpeciesTypeComponentMapInProducts = listOfSpeciesTypeComponentMapInProducts;
      listOfSpeciesTypeComponentMapInProducts = null;
      oldSpeciesTypeComponentMapInProducts.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link SpeciesTypeComponentMapInProduct} to the {@link #listOfSpeciesTypeComponentMapInProducts}.
   * <p>The listOfSpeciesTypeComponentMapInProducts is initialized if necessary.
   *
   * @param speciesTypeComponentMapInProduct the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addSpeciesTypeComponentMapInProduct(SpeciesTypeComponentMapInProduct speciesTypeComponentMapInProduct) {
    return getListOfSpeciesTypeComponentMapInProducts().add(speciesTypeComponentMapInProduct);
  }


  /**
   * Removes an element from the {@link #listOfSpeciesTypeComponentMapInProducts}.
   *
   * @param speciesTypeComponentMapInProduct the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeSpeciesTypeComponentMapInProduct(SpeciesTypeComponentMapInProduct speciesTypeComponentMapInProduct) {
    if (isSetListOfSpeciesTypeComponentMapInProducts()) {
      return getListOfSpeciesTypeComponentMapInProducts().remove(speciesTypeComponentMapInProduct);
    }
    return false;
  }


  /**
   * Removes an element from the {@link #listOfSpeciesTypeComponentMapInProducts} at the given index.
   *
   * @param i the index where to remove the {@link SpeciesTypeComponentMapInProduct}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfSpeciesTypeComponentMapInProducts)}).
   */
  public SpeciesTypeComponentMapInProduct removeSpeciesTypeComponentMapInProduct(int i) {
    if (!isSetListOfSpeciesTypeComponentMapInProducts()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfSpeciesTypeComponentMapInProducts().remove(i);
  }


  /**
   * Creates a new SpeciesTypeComponentMapInProduct element and adds it to the
   * {@link #listOfSpeciesTypeComponentMapInProducts} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfSpeciesTypeComponentMapInProducts}
   */
  public SpeciesTypeComponentMapInProduct createSpeciesTypeComponentMapInProduct() {
    SpeciesTypeComponentMapInProduct speciesTypeComponentMapInProduct = new SpeciesTypeComponentMapInProduct();
    addSpeciesTypeComponentMapInProduct(speciesTypeComponentMapInProduct);
    return speciesTypeComponentMapInProduct;
  }


  /**
   * Gets an element from the {@link #listOfSpeciesTypeComponentMapInProducts} at the given index.
   *
   * @param i the index of the {@link SpeciesTypeComponentMapInProduct} element to get.
   * @return an element from the listOfSpeciesTypeComponentMapInProducts at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public SpeciesTypeComponentMapInProduct getSpeciesTypeComponentMapInProduct(int i) {
    if (!isSetListOfSpeciesTypeComponentMapInProducts()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfSpeciesTypeComponentMapInProducts().get(i);
  }


  /**
   * Returns the number of {@link SpeciesTypeComponentMapInProduct}s in this
   * {@link MultiSpeciesReferencePlugin}.
   * 
   * @return the number of {@link SpeciesTypeComponentMapInProduct}s in this
   *         {@link MultiSpeciesReferencePlugin}.
   */
  public int getSpeciesTypeComponentMapInProductCount() {
    return isSetListOfSpeciesTypeComponentMapInProducts() ? getListOfSpeciesTypeComponentMapInProducts().size() : 0;
  }


  /**
   * Returns the number of {@link SpeciesTypeComponentMapInProduct}s in this
   * {@link MultiSpeciesReferencePlugin}.
   * 
   * @return the number of {@link SpeciesTypeComponentMapInProduct}s in this
   *         {@link MultiSpeciesReferencePlugin}.
   * @libsbml.deprecated same as {@link #getSpeciesTypeComponentMapInProductCount()}
   */
  public int getNumSpeciesTypeComponentMapInProducts() {
    return getSpeciesTypeComponentMapInProductCount();
  }


  @Override
  public boolean getAllowsChildren() {
    return true;
  }


  @Override
  public int getChildCount() {
    int count = super.getChildCount();

    if (isSetListOfSpeciesTypeComponentMapInProducts()) {
      count++;
    }

    return count;
  }


  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }
    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }

    if (isSetListOfSpeciesTypeComponentMapInProducts()) {
      if (pos == index) {
        return getListOfSpeciesTypeComponentMapInProducts();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(
      MessageFormat.format(resourceBundle.getString("IndexExceedsBoundsException"),
        index, Math.min(pos, 0)));
  }

}
