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
package org.sbml.jsbml.ext.distrib;

import java.text.MessageFormat;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;

/**
 * The {@link Uncertainty} class is a collection of zero or more statistical measures related to the uncertainty of the parent
 * SBML element. 
 * 
 * <p>It may only contain one of each type of measurement, which means that each of its UncertParameter
 * children must have a unique type attribute for every value but 'externalParameter'. Each UncertParameter child
 * with a type of 'externalParameter' must, in turn, have a unique definitionURL value. If a given SBML element
 * has multiple measures of the same type (for example, as measured from different sources or different experiments),
 * it should be given multiple Uncertainty children. Each Uncertainty child must be a unique set of statistical measures.</p>
 * 
 * <p>Note that the described uncertainty for elements that change in value over time apply only to the element's
 * uncertainty at a snapshot in time, and not the uncertainty in how it changes in time. For typical simulations, this
 * means the element's initial condition. 
 *
 * @author Nicolas Rodriguez
 * @since 1.4
 */
public class Uncertainty extends AbstractDistribSBase {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -904719821379100471L;

  /**
   * 
   */
  private ListOf<UncertParameter> listOfUncertParameters;
  
  /**
   * Creates an Uncertainty instance
   */
  public Uncertainty() {
    super();
    initDefaults();
  }


  /**
   * Creates a Uncertainty instance with an id.
   * 
   * @param id the identifier for the new element.
   */
  public Uncertainty(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a Uncertainty instance with a level and version.
   * 
   * @param level SBML Level
   * @param version SBML Version
   */
  public Uncertainty(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a Uncertainty instance with an id, level, and version.
   * 
   * @param id the identifier for this element.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public Uncertainty(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a Uncertainty instance with an id, name, level, and version.
   * 
   * @param id the identifier for this element.
   * @param name a human-readable name for this element that can be used for display purposes.
   * @param level the SBML Level
   * @param version the SBML Version
   */
  public Uncertainty(String id, String name, int level, int version) {
    super(id, name, level, version);
    initDefaults();
  }


  /**
   * Clone constructor
   * @param obj the object to clone
   */
  public Uncertainty(Uncertainty obj) {
    super(obj);
    
    // TODO
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = DistribConstants.shortLabel;
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
  public Uncertainty clone() {
    return new Uncertainty(this);
  }
  

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();

    if (getUncertParameterCount() > 0) {
      count += getUncertParameterCount();
    }
    
    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
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

    if (getUncertParameterCount() > 0) {

      for (UncertParameter uncertParam : listOfUncertParameters) {
        if (index == pos) {
          return uncertParam;
        }
        pos++;
      }
    }

    throw new IndexOutOfBoundsException(
      MessageFormat.format(resourceBundle.getString("IndexExceedsBoundsException"),
        index, Math.min(pos, 0)));
  }


  
  /**
   * Returns {@code true} if {@link #listOfUncertParameters} contains at least
   * one element.
   *
   * @return {@code true} if {@link #listOfUncertParameters} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean isSetListOfUncertParameters() {
    if (listOfUncertParameters == null) {
      return false;
    }
    return true;
  }

  /**
   * Returns the {@link #listOfUncertParameters}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfUncertParameters}.
   */
  public ListOf<UncertParameter> getListOfUncertParameters() {
    if (listOfUncertParameters == null) {
      listOfUncertParameters = new ListOf<UncertParameter>();
      listOfUncertParameters.setNamespace(DistribConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
      listOfUncertParameters.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'distrib'
      listOfUncertParameters.setPackageName(null);
      listOfUncertParameters.setPackageName(DistribConstants.shortLabel);
      listOfUncertParameters.setSBaseListType(ListOf.Type.other);

      registerChild(listOfUncertParameters);
    }
    return listOfUncertParameters;
  }

  /**
   * Sets the given {@code ListOf<UncertParameter>}.
   * If {@link #listOfUncertParameters} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfUncertParameters
   */
  public void setListOfUncertParameters(ListOf<UncertParameter> listOfUncertParameters) {
    unsetListOfUncertParameters();
    this.listOfUncertParameters = listOfUncertParameters;

    if (listOfUncertParameters != null) {
      listOfUncertParameters.unsetNamespace();
      listOfUncertParameters.setNamespace(DistribConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
      listOfUncertParameters.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'distrib'
      listOfUncertParameters.setPackageName(null);
      listOfUncertParameters.setPackageName(DistribConstants.shortLabel);
      this.listOfUncertParameters.setSBaseListType(ListOf.Type.other);

      registerChild(listOfUncertParameters);
    }

  }

  /**
   * Returns {@code true} if {@link #listOfUncertParameters} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfUncertParameters} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfUncertParameters() {
    if (isSetListOfUncertParameters()) {
      ListOf<UncertParameter> oldUncertParameters = this.listOfUncertParameters;
      this.listOfUncertParameters = null;
      oldUncertParameters.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Adds a new {@link UncertParameter} to the {@link #listOfUncertParameters}.
   * <p>The listOfUncertParameters is initialized if necessary.
   *
   * @param uncertParameter the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addUncertParameter(UncertParameter uncertParameter) {
    return getListOfUncertParameters().add(uncertParameter);
  }

  /**
   * Removes an element from the {@link #listOfUncertParameters}.
   *
   * @param uncertParameter the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeUncertParameter(UncertParameter uncertParameter) {
    if (isSetListOfUncertParameters()) {
      return getListOfUncertParameters().remove(uncertParameter);
    }
    return false;
  }

  // TODO - if UncertParameter has no id attribute, you should remove this method.
  /**
   * Removes an element from the {@link #listOfUncertParameters}.
   *
   * @param uncertParameterId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or
   *         {@code null}.
   */
  public UncertParameter removeUncertParameter(String uncertParameterId) {
    if (isSetListOfUncertParameters()) {
      return getListOfUncertParameters().remove(uncertParameterId);
    }
    return null;
  }

  /**
   * Removes an element from the {@link #listOfUncertParameters} at the given index.
   *
   * @param i the index where to remove the {@link UncertParameter}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfUncertParameters)}).
   */
  public UncertParameter removeUncertParameter(int i) {
    if (!isSetListOfUncertParameters()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfUncertParameters().remove(i);
  }

  /**
   * Creates a new UncertParameter element and adds it to the
   * {@link #listOfUncertParameters} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfUncertParameters}
   */
  public UncertParameter createUncertParameter() {
    return createUncertParameter(null);
  }

  /**
   * Creates a new {@link UncertParameter} element and adds it to the
   * {@link #listOfUncertParameters} list.
   *
   * @param id the identifier that is to be applied to the new element.
   * @return the newly created {@link UncertParameter} element, which is the last
   *         element in the {@link #listOfUncertParameters}.
   */
  public UncertParameter createUncertParameter(String id) {
    UncertParameter uncertParameter = new UncertParameter(id);
    addUncertParameter(uncertParameter);
    return uncertParameter;
  }
  

  /**
   * Creates a new {@link UncertSpan} element and adds it to the
   * {@link #listOfUncertParameters} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfUncertParameters}
   */
  public UncertSpan createUncertSpan() {
    return createUncertSpan(null);
  }

  /**
   * Creates a new {@link UncertSpan} element and adds it to the
   * {@link #listOfUncertParameters} list.
   *
   * @param id the identifier that is to be applied to the new element.
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfUncertParameters}
   */
  public UncertSpan createUncertSpan(String id) {
    UncertSpan uncertParameter = new UncertSpan(id);
    addUncertParameter(uncertParameter);
    return uncertParameter;
  }

  /**
   * Gets an element from the {@link #listOfUncertParameters} at the given index.
   *
   * @param i the index of the {@link UncertParameter} element to get.
   * @return an element from the listOfUncertParameters at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index < 0 || index > list.size).
   */
  public UncertParameter getUncertParameter(int i) {
    if (!isSetListOfUncertParameters()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfUncertParameters().get(i);
  }

  // TODO - if UncertParameter has no id attribute, you should remove this method.
  /**
   * Gets an element from the listOfUncertParameters, with the given id.
   *
   * @param uncertParameterId the id of the {@link UncertParameter} element to get.
   * @return an element from the listOfUncertParameters with the given id
   *         or {@code null}.
   */
  public UncertParameter getUncertParameter(String uncertParameterId) {
    if (isSetListOfUncertParameters()) {
      return getListOfUncertParameters().get(uncertParameterId);
    }
    return null;
  }

  /**
   * Returns the number of {@link UncertParameter}s in this
   * {@link Uncertainty}.
   * 
   * @return the number of {@link UncertParameter}s in this
   *         {@link Uncertainty}.
   */
  public int getUncertParameterCount() {
    return isSetListOfUncertParameters() ? getListOfUncertParameters().size() : 0;
  }

  /**
   * Returns the number of {@link UncertParameter}s in this
   * {@link Uncertainty}.
   * 
   * @return the number of {@link UncertParameter}s in this
   *         {@link Uncertainty}.
   * @libsbml.deprecated same as {@link #getUncertParameterCount()}
   */
  public int getNumUncertParameters() {
    return getUncertParameterCount();
  }


}
