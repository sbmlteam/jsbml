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

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBase;


/**
 * @author rodrigue
 * @since 1.4
 */
public class UncertStatistics extends AbstractDistrictSBase {

  // TODO - implements XML attributes, equals and hashcode, children

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  private UncertValue singleValueStatistic;
  
  /**
   * 
   */
  private UncertStatisticsSpan spanStatistic;
  
  /**
   * 
   */
  private ListOf<ExternalParameter> listOfExternalParameters;


  /**
   * Creates a new instance of {@link UncertStatistics}
   */
  public UncertStatistics() {
    super();
  }



  /**
   * Creates a new instance of {@link UncertStatistics}
   * 
   * @param level
   * @param version
   */
  public UncertStatistics(int level, int version) {
    super(level, version);
  }



  /**
   * Creates a new instance of {@link UncertStatistics}
   * 
   * @param sb
   */
  public UncertStatistics(SBase sb) {
    super(sb);
  }



  /**
   * Creates a new instance of {@link UncertStatistics}
   * 
   * @param id
   * @param level
   * @param version
   */
  public UncertStatistics(String id, int level, int version) {
    super(id, level, version);
  }



  /**
   * Creates a new instance of {@link UncertStatistics}
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public UncertStatistics(String id, String name, int level, int version) {
    super(id, name, level, version);
  }



  /**
   * Creates a new instance of {@link UncertStatistics}
   * 
   * @param id
   */
  public UncertStatistics(String id) {
    super(id);
  }



  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public AbstractSBase clone() {
    // TODO Auto-generated method stub
    return null;
  }
  
  
  /**
   * Returns {@code true} if {@link #listOfExternalParameters} contains at least
   * one element.
   *
   * @return {@code true} if {@link #listOfExternalParameters} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean isSetListOfExternalParameters() {
    if (listOfExternalParameters == null) {
      return false;
    }
    return true;
  }


  /**
   * Returns the {@link #listOfExternalParameters}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfExternalParameters}.
   */
  public ListOf<ExternalParameter> getListOfExternalParameters() {
    if (listOfExternalParameters == null) {
      listOfExternalParameters = new ListOf<ExternalParameter>();
      listOfExternalParameters.setNamespace(DistribConstants.namespaceURI); // TODO - set the proper namespace
      listOfExternalParameters.setSBaseListType(ListOf.Type.other);

      registerChild(listOfExternalParameters);
    }

    return listOfExternalParameters;
  }


  /**
   * Sets the given {@code ListOf<ExternalParameter>}.
   * If {@link #listOfExternalParameters} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfExternalParameters
   */
  public void setListOfExternalParameters(ListOf<ExternalParameter> listOfExternalParameters) {
    unsetListOfExternalParameters();
    this.listOfExternalParameters = listOfExternalParameters;
    this.listOfExternalParameters.setSBaseListType(ListOf.Type.other);

    registerChild(this.listOfExternalParameters);
  }


  /**
   * Returns {@code true} if {@link #listOfExternalParameters} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfExternalParameters} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfExternalParameters() {
    if (isSetListOfExternalParameters()) {
      ListOf<ExternalParameter> oldExternalParameters = this.listOfExternalParameters;
      this.listOfExternalParameters = null;
      oldExternalParameters.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link ExternalParameter} to the {@link #listOfExternalParameters}.
   * <p>The listOfExternalParameters is initialized if necessary.
   *
   * @param field the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addExternalParameter(ExternalParameter field) {
    return getListOfExternalParameters().add(field);
  }


  /**
   * Removes an element from the {@link #listOfExternalParameters}.
   *
   * @param field the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeExternalParameter(ExternalParameter field) {
    if (isSetListOfExternalParameters()) {
      return getListOfExternalParameters().remove(field);
    }
    return false;
  }


  /**
   * Removes an element from the {@link #listOfExternalParameters}.
   *
   * @param fieldId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or
   *         {@code null}.
   */
  public ExternalParameter removeExternalParameter(String fieldId) {
    if (isSetListOfExternalParameters()) {
      return getListOfExternalParameters().remove(fieldId);
    }
    return null;
  }


  /**
   * Removes an element from the {@link #listOfExternalParameters} at the given index.
   *
   * @param i the index where to remove the {@link ExternalParameter}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfExternalParameters)}).
   */
  public ExternalParameter removeExternalParameter(int i) {
    if (!isSetListOfExternalParameters()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfExternalParameters().remove(i);
  }


  /**
   * Creates a new ExternalParameter element and adds it to the
   * {@link #listOfExternalParameters} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfExternalParameters}
   */
  public ExternalParameter createExternalParameter() {
    return createExternalParameter(null);
  }


  /**
   * Creates a new {@link ExternalParameter} element and adds it to the
   * {@link #listOfExternalParameters} list.
   *
   * @param id the identifier that is to be applied to the new element.
   * @return the newly created {@link ExternalParameter} element, which is the last
   *         element in the {@link #listOfExternalParameters}.
   */
  public ExternalParameter createExternalParameter(String id) {
    ExternalParameter field = new ExternalParameter(id);
    addExternalParameter(field);
    return field;
  }

  /**
   * Gets an element from the {@link #listOfExternalParameters} at the given index.
   *
   * @param i the index of the {@link ExternalParameter} element to get.
   * @return an element from the listOfExternalParameters at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index < 0 || index > list.size).
   */
  public ExternalParameter getExternalParameter(int i) {
    if (!isSetListOfExternalParameters()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfExternalParameters().get(i);
  }


  /**
   * Gets an element from the listOfExternalParameters, with the given id.
   *
   * @param fieldId the id of the {@link ExternalParameter} element to get.
   * @return an element from the listOfExternalParameters with the given id
   *         or {@code null}.
   */
  public ExternalParameter getExternalParameter(String fieldId) {
    if (isSetListOfExternalParameters()) {
      return getListOfExternalParameters().get(fieldId);
    }
    return null;
  }


  /**
   * Returns the number of {@link ExternalParameter}s in this
   * {@link ExternalDistribution}.
   * 
   * @return the number of {@link ExternalParameter}s in this
   *         {@link ExternalDistribution}.
   */
  public int getExternalParameterCount() {
    return isSetListOfExternalParameters() ? getListOfExternalParameters().size() : 0;
  }


  /**
   * Returns the number of {@link ExternalParameter}s in this
   * {@link ExternalDistribution}.
   * 
   * @return the number of {@link ExternalParameter}s in this
   *         {@link ExternalDistribution}.
   * @libsbml.deprecated same as {@link #getExternalParameterCount()}
   */
  public int getNumExternalParameters() {
    return getExternalParameterCount();
  }

  
  /**
   * Returns the value of {@link #singleValueStatistic}.
   *
   * @return the value of {@link #singleValueStatistic}.
   */
  public UncertValue getSingleValueStatistic() {
    if (isSetSingleValueStatistic()) {
      return singleValueStatistic;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.singleValueStatistic, this);
  }


  /**
   * Returns whether {@link #singleValueStatistic} is set.
   *
   * @return whether {@link #singleValueStatistic} is set.
   */
  public boolean isSetSingleValueStatistic() {
    return this.singleValueStatistic != null;
  }


  /**
   * Sets the value of singleValueStatistic
   *
   * @param singleValueStatistic the value of singleValueStatistic to be set.
   */
  public void setSingleValueStatistic(UncertValue singleValueStatistic) {
    UncertValue oldSingleValueStatistic = this.singleValueStatistic;
    this.singleValueStatistic = singleValueStatistic;
    firePropertyChange(DistribConstants.singleValueStatistic, oldSingleValueStatistic, this.singleValueStatistic);
  }


  /**
   * Unsets the variable singleValueStatistic.
   *
   * @return {@code true} if singleValueStatistic was set before, otherwise {@code false}.
   */
  public boolean unsetSingleValueStatistic() {
    if (isSetSingleValueStatistic()) {
      UncertValue oldSingleValueStatistic = this.singleValueStatistic;
      this.singleValueStatistic = null;
      firePropertyChange(DistribConstants.singleValueStatistic, oldSingleValueStatistic, this.singleValueStatistic);
      return true;
    }
    return false;
  }
  
  
  /**
   * Returns the value of {@link #spanStatistic}.
   *
   * @return the value of {@link #spanStatistic}.
   */
  public UncertStatisticsSpan getSpanStatistic() {
    if (isSetSpanStatistic()) {
      return spanStatistic;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(DistribConstants.spanStatistic, this);
  }


  /**
   * Returns whether {@link #spanStatistic} is set.
   *
   * @return whether {@link #spanStatistic} is set.
   */
  public boolean isSetSpanStatistic() {
    return this.spanStatistic != null;
  }


  /**
   * Sets the value of spanStatistic
   *
   * @param spanStatistic the value of spanStatistic to be set.
   */
  public void setSpanStatistic(UncertStatisticsSpan spanStatistic) {
    UncertStatisticsSpan oldSpanStatistic = this.spanStatistic;
    this.spanStatistic = spanStatistic;
    firePropertyChange(DistribConstants.spanStatistic, oldSpanStatistic, this.spanStatistic);
  }


  /**
   * Unsets the variable spanStatistic.
   *
   * @return {@code true} if spanStatistic was set before, otherwise {@code false}.
   */
  public boolean unsetSpanStatistic() {
    if (isSetSpanStatistic()) {
      UncertStatisticsSpan oldSpanStatistic = this.spanStatistic;
      this.spanStatistic = null;
      firePropertyChange(DistribConstants.spanStatistic, oldSpanStatistic, this.spanStatistic);
      return true;
    }
    return false;
  }
  
}
