/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2022 jointly by the following organizations:
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
package org.sbml.jsbml.ext.distrib;

import java.text.MessageFormat;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

/**
 * 
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class DistribSBasePlugin extends AbstractSBasePlugin {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 1500L;
  
  
  /**
   * 
   */
  private ListOf<Uncertainty> listOfUncertainties;

  /**
   * Creates a new {@link DistribSBasePlugin} instance.
   * 
   */
  public DistribSBasePlugin() {
    super();
  }

  /**
   * Creates a new {@link DistribSBasePlugin} instance.
   * 
   * @param sbase the {@link SBase} to be extended.
   */
  public DistribSBasePlugin(SBase sbase) {
    super(sbase);
  }

  /**
   * Creates a new {@link DistribSBasePlugin} instance, cloned from
   * the input parameter.
   * 
   * @param obj the {@link DistribSBasePlugin} to clone
   */
  public DistribSBasePlugin(DistribSBasePlugin obj) {
    super(obj);

    if (obj.isSetListOfUncertainties()) {
      setListOfUncertainties(obj.getListOfUncertainties().clone());
    }
  }



  @Override
  public String getPackageName() {
    return DistribConstants.shortLabel;
  }

  @Override
  public String getPrefix() {
    return DistribConstants.shortLabel;
  }

  @Override
  public String getURI() {
    return getElementNamespace();
  }


  @Override
  public int getChildCount() {
    int count = 0;

    if (isSetListOfUncertainties()) {
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
    int pos = 0;

    if (isSetListOfUncertainties()) {
      if (pos == index) {
        return getListOfUncertainties();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }

  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  @Override
  public AbstractSBasePlugin clone() {
    return new DistribSBasePlugin(this);
  }

  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    return false;
  }
  
  
  /**
   * Returns {@code true} if {@link #listOfUncertainties} contains at least
   * one element.
   *
   * @return {@code true} if {@link #listOfUncertainties} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean isSetListOfUncertainties() {
    if (listOfUncertainties == null) {
      return false;
    }
    return true;
  }

  /**
   * Returns the {@link #listOfUncertainties}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfUncertainties}.
   */
  public ListOf<Uncertainty> getListOfUncertainties() {
    if (listOfUncertainties == null) {
      listOfUncertainties = new ListOf<Uncertainty>();
      listOfUncertainties.setNamespace(DistribConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
      listOfUncertainties.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'distrib'
      listOfUncertainties.setPackageName(null);
      listOfUncertainties.setPackageName(DistribConstants.shortLabel);
      listOfUncertainties.setSBaseListType(ListOf.Type.other);

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(listOfUncertainties);
      }
    }
    return listOfUncertainties;
  }

  /**
   * Sets the given {@code ListOf<Uncertainty>}.
   * If {@link #listOfUncertainties} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfUncertainties the list to set
   */
  public void setListOfUncertainties(ListOf<Uncertainty> listOfUncertainties) {
    unsetListOfUncertainties();
    this.listOfUncertainties = listOfUncertainties;

    if (listOfUncertainties != null) {
      listOfUncertainties.unsetNamespace();
      listOfUncertainties.setNamespace(DistribConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
      listOfUncertainties.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'distrib'
      listOfUncertainties.setPackageName(null);
      listOfUncertainties.setPackageName(DistribConstants.shortLabel);
      this.listOfUncertainties.setSBaseListType(ListOf.Type.other);

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(this.listOfUncertainties);
      }
    }

  }

  /**
   * Returns {@code true} if {@link #listOfUncertainties} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfUncertainties} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfUncertainties() {
    if (isSetListOfUncertainties()) {
      ListOf<Uncertainty> oldUncertainties = this.listOfUncertainties;
      this.listOfUncertainties = null;
      oldUncertainties.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Adds a new {@link Uncertainty} to the {@link #listOfUncertainties}.
   * <p>The listOfUncertainties is initialized if necessary.
   *
   * @param uncertainty the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addUncertainty(Uncertainty uncertainty) {
    return getListOfUncertainties().add(uncertainty);
  }

  /**
   * Removes an element from the {@link #listOfUncertainties}.
   *
   * @param uncertainty the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeUncertainty(Uncertainty uncertainty) {
    if (isSetListOfUncertainties()) {
      return getListOfUncertainties().remove(uncertainty);
    }
    return false;
  }

  // TODO - if Uncertaintie has no id attribute, you should remove this method.
  /**
   * Removes an element from the {@link #listOfUncertainties}.
   *
   * @param uncertaintyId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or
   *         {@code null}.
   */
  public Uncertainty removeUncertainty(String uncertaintyId) {
    if (isSetListOfUncertainties()) {
      return getListOfUncertainties().remove(uncertaintyId);
    }
    return null;
  }

  /**
   * Removes an element from the {@link #listOfUncertainties} at the given index.
   *
   * @param i the index where to remove the {@link Uncertainty}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfUncertainties)}).
   */
  public Uncertainty removeUncertainty(int i) {
    if (!isSetListOfUncertainties()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfUncertainties().remove(i);
  }

  /**
   * Creates a new Uncertainty element and adds it to the
   * {@link #listOfUncertainties} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfUncertainties}
   */
  public Uncertainty createUncertainty() {
    return createUncertainty(null);
  }

  /**
   * Creates a new {@link Uncertainty} element and adds it to the
   * {@link #listOfUncertainties} list.
   *
   * @param id the identifier that is to be applied to the new element.
   * @return the newly created {@link Uncertainty} element, which is the last
   *         element in the {@link #listOfUncertainties}.
   */
  public Uncertainty createUncertainty(String id) {
    Uncertainty uncertainty = new Uncertainty(id);
    addUncertainty(uncertainty);
    return uncertainty;
  }


  /**
   * Gets an element from the {@link #listOfUncertainties} at the given index.
   *
   * @param i the index of the {@link Uncertainty} element to get.
   * @return an element from the listOfUncertainties at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index < 0 || index > list.size).
   */
  public Uncertainty getUncertainty(int i) {
    if (!isSetListOfUncertainties()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfUncertainties().get(i);
  }

  /**
   * Gets an element from the listOfUncertainties, with the given id.
   *
   * @param uncertaintyId the id of the {@link Uncertainty} element to get.
   * @return an element from the listOfUncertainties with the given id
   *         or {@code null}.
   */
  public Uncertainty getUncertainty(String uncertaintyId) {
    if (isSetListOfUncertainties()) {
      return getListOfUncertainties().get(uncertaintyId);
    }
    return null;
  }

  /**
   * Returns the number of {@link Uncertainty}s in this
   * {@link DistribSBasePlugin}.
   * 
   * @return the number of {@link Uncertainty}s in this
   *         {@link DistribSBasePlugin}.
   */
  public int getUncertaintyCount() {
    return isSetListOfUncertainties() ? getListOfUncertainties().size() : 0;
  }

  /**
   * Returns the number of {@link Uncertainty}s in this
   * {@link DistribSBasePlugin}.
   * 
   * @return the number of {@link Uncertainty}s in this
   *         {@link DistribSBasePlugin}.
   * @libsbml.deprecated same as {@link #getUncertaintyCount()}
   */
  public int getNumUncertainties() {
    return getUncertaintyCount();
  }


}
