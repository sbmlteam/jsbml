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
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.TreeNodeChangeEvent;


/**
 * The {@link ExternalParameter} class, like the {@link ExternalDistribution}, is provided to allow a modeler to encode externally provided
 * parameters not otherwise explicitly handled by this specification. 
 * 
 * <p>Again, the range of possibilities is vast,
 * so modelers should ensure that the tool they wish to use encodes support for any ExternalParameter they define.</p>
 *
 * <p>The ExternalParameter inherits from UncertValue, and adds the required attribute 'definitionURL', which is of
 * type ExternalRef, and an optional child ListOfExternalParameters. The definitionURL must be a URI that
 * defines a valid distribution-related parameter. It is again strongly recommended that modelers use distribution
 * parameters from ProbOnto (http://probonto.org/), as consistently referencing a single ontology will improve
 * exchangeability.
 *
 * @author rodrigue
 * @since 1.4
 */
public class ExternalParameter extends UncertValue {

  /**
   * 
   */
  private String definitionURL;
  
  /**
   * 
   */
  private ListOf<ExternalParameter> listOfExternalParameters;
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  /**
   * Creates a new instance of {@link ExternalParameter}
   * 
   */
  public ExternalParameter() {
    super();
  }


  /**
   * Creates a new instance of {@link ExternalParameter}
   * 
   * @param level
   * @param version
   */
  public ExternalParameter(int level, int version) {
    super(level, version);
  }


  /**
   * Creates a new instance of {@link ExternalParameter}
   * 
   * @param id
   * @param level
   * @param version
   */
  public ExternalParameter(String id, int level, int version) {
    super(id, level, version);
  }


  /**
   * Creates a new instance of {@link ExternalParameter}
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public ExternalParameter(String id, String name, int level, int version) {
    super(id, name, level, version);
  }


  /**
   * Creates a new instance of {@link ExternalParameter}
   * 
   * @param id
   */
  public ExternalParameter(String id) {
    super(id);
  }


  /**
   * Creates a new instance of {@link ExternalParameter}
   * 
   * @param sb
   */
  public ExternalParameter(ExternalParameter sb) {
    super(sb);

    if (sb.isSetDefinitionURL()) {
      setDefinitionURL(sb.getDefinitionURL());
    }
    if (sb.isSetListOfExternalParameters()) {
      setListOfExternalParameters(sb.getListOfExternalParameters().clone());
    }
  }

  @Override
  public ExternalParameter clone() {
    return new ExternalParameter(this);
  }
  
  /**
   * Returns the value of {@link #definitionURL}.
   *
   * @return the value of {@link #definitionURL}.
   */
  public String getDefinitionURL() {

    if (isSetDefinitionURL()) {
      return definitionURL;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(TreeNodeChangeEvent.definitionURL, this);
  }


  /**
   * Returns whether {@link #definitionURL} is set.
   *
   * @return whether {@link #definitionURL} is set.
   */
  public boolean isSetDefinitionURL() {
    return this.definitionURL != null;
  }


  /**
   * Sets the value of definitionURL
   *
   * @param definitionURL the value of definitionURL to be set.
   */
  public void setDefinitionURL(String definitionURL) {
    String oldDefinitionURL = this.definitionURL;
    this.definitionURL = definitionURL;
    firePropertyChange(TreeNodeChangeEvent.definitionURL, oldDefinitionURL, this.definitionURL);
  }


  /**
   * Unsets the variable definitionURL.
   *
   * @return {@code true} if definitionURL was set before, otherwise {@code false}.
   */
  public boolean unsetDefinitionURL() {
    if (isSetDefinitionURL()) {
      String oldDefinitionURL = this.definitionURL;
      this.definitionURL = null;
      firePropertyChange(TreeNodeChangeEvent.definitionURL, oldDefinitionURL, this.definitionURL);
      return true;
    }
    return false;
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



  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3307;
    int result = super.hashCode();
    result =
      prime * result + ((definitionURL == null) ? 0 : definitionURL.hashCode());
    result = prime * result + ((listOfExternalParameters == null) ? 0
      : listOfExternalParameters.hashCode());
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
    ExternalParameter other = (ExternalParameter) obj;
    if (definitionURL == null) {
      if (other.definitionURL != null) {
        return false;
      }
    } else if (!definitionURL.equals(other.definitionURL)) {
      return false;
    }
    if (listOfExternalParameters == null) {
      if (other.listOfExternalParameters != null) {
        return false;
      }
    } else if (!listOfExternalParameters.equals(
      other.listOfExternalParameters)) {
      return false;
    }
    return true;
  }
  
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.distrib.UncertValue#writeXMLAttributes()
   */
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetDefinitionURL()) {
      attributes.put(DistribConstants.shortLabel + ":" + TreeNodeChangeEvent.definitionURL, getDefinitionURL());
    }
    
    return attributes;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.distrib.UncertValue#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  public boolean readAttribute(String attributeName, String prefix,
    String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(TreeNodeChangeEvent.definitionURL)) {
        setDefinitionURL(value);
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }
  
  
  @Override
  public boolean getAllowsChildren() {
    return true;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  public int getChildCount() {
    int count = super.getChildCount();

    if (isSetListOfExternalParameters()) {
      count++;
    }

    return count;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
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

    if (isSetListOfExternalParameters()) {
      if (pos == index) {
        return getListOfExternalParameters();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }

}
