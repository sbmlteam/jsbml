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
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.Compartment;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.util.StringTools;

/**
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class MultiCompartmentPlugin extends AbstractSBasePlugin  {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 4526455581462978178L;

  /**
   * 
   */
  private String compartmentType;

  /**
   * 
   */
  private Boolean isType;

  /**
   * 
   */
  private ListOf<CompartmentReference> listOfCompartmentReferences;

  /**
   * Creates an MultiCompartmentPlugin instance.
   */
  public MultiCompartmentPlugin() {
    super();
    initDefaults();
  }


  /**
   * Creates a {@link MultiCompartmentPlugin} instance associated with the given
   * {@link Compartment}.
   * 
   * @param compartment
   *        the compartment to extend.
   */
  public MultiCompartmentPlugin(Compartment compartment) {
    super(compartment);
    initDefaults();
  }


  /**
   * Clone constructor.
   * 
   * @param obj the {@link MultiCompartmentPlugin} to clone
   */
  public MultiCompartmentPlugin(MultiCompartmentPlugin obj) {
    super(obj);

    if (obj.isSetListOfCompartmentReferences()) {
      setListOfCompartmentReferences(obj.getListOfCompartmentReferences().clone());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#clone()
   */
  @Override
  public MultiCompartmentPlugin clone() {
    return new MultiCompartmentPlugin(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
   */
  @Override
  public String getPackageName() {
    return MultiConstants.shortLabel;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPrefix()
   */
  @Override
  public String getPrefix() {
    return MultiConstants.shortLabel;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getURI()
   */
  @Override
  public String getURI() {
    return getElementNamespace();
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#getParent()
   */
  @Override
  public Compartment getParent() {
    return (Compartment) getExtendedSBase();
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getParentSBMLObject()
   */
  @Override
  public Compartment getParentSBMLObject() {
    return getParent();
  }


  /**
   * Returns {@code true} if {@link #listOfCompartmentReferences} contains at least
   * one element.
   *
   * @return {@code true} if {@link #listOfCompartmentReferences} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean isSetListOfCompartmentReferences() {
    if (listOfCompartmentReferences == null) {
      return false;
    }
    return true;
  }


  /**
   * Returns the {@link #listOfCompartmentReferences}.
   * Creates it if it does not already exist.
   *
   * @return the {@link #listOfCompartmentReferences}.
   */
  public ListOf<CompartmentReference> getListOfCompartmentReferences() {
    if (listOfCompartmentReferences == null) {
      listOfCompartmentReferences = new ListOf<CompartmentReference>();
      listOfCompartmentReferences.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfCompartmentReferences.setPackageName(null);
      listOfCompartmentReferences.setPackageName(MultiConstants.shortLabel);
      listOfCompartmentReferences.setSBaseListType(ListOf.Type.other);
      listOfCompartmentReferences.setOtherListName(MultiConstants.listOfCompartmentReferences);

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(listOfCompartmentReferences);
      }
    }

    return listOfCompartmentReferences;
  }


  /**
   * Sets the given {@code ListOf<CompartmentReference>}.
   * If {@link #listOfCompartmentReferences} was defined before and contains some
   * elements, they are all unset.
   *
   * @param listOfCompartmentReferences the list of {@link CompartmentReference}s to set
   */
  public void setListOfCompartmentReferences(ListOf<CompartmentReference> listOfCompartmentReferences) {
    unsetListOfCompartmentReferences();
    this.listOfCompartmentReferences = listOfCompartmentReferences;

    if (listOfCompartmentReferences != null) {
      listOfCompartmentReferences.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'multi'
      listOfCompartmentReferences.setPackageName(null);
      listOfCompartmentReferences.setPackageName(MultiConstants.shortLabel);
      listOfCompartmentReferences.setSBaseListType(ListOf.Type.other);
      listOfCompartmentReferences.setOtherListName(MultiConstants.listOfCompartmentReferences);
      
      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(this.listOfCompartmentReferences);
      }
    }
  }


  /**
   * Returns {@code true} if {@link #listOfCompartmentReferences} contains at least
   * one element, otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfCompartmentReferences} contains at least
   *         one element, otherwise {@code false}.
   */
  public boolean unsetListOfCompartmentReferences() {
    if (isSetListOfCompartmentReferences()) {
      ListOf<CompartmentReference> oldCompartmentReferences = listOfCompartmentReferences;
      listOfCompartmentReferences = null;
      oldCompartmentReferences.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link CompartmentReference} to the {@link #listOfCompartmentReferences}.
   * <p>The listOfCompartmentReferences is initialized if necessary.
   *
   * @param compartmentReference the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addCompartmentReference(CompartmentReference compartmentReference) {
    return getListOfCompartmentReferences().add(compartmentReference);
  }


  /**
   * Removes an element from the {@link #listOfCompartmentReferences}.
   *
   * @param compartmentReference the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was
   *         removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeCompartmentReference(CompartmentReference compartmentReference) {
    if (isSetListOfCompartmentReferences()) {
      return getListOfCompartmentReferences().remove(compartmentReference);
    }
    return false;
  }


  /**
   * Removes an element from the {@link #listOfCompartmentReferences}.
   *
   * @param compartmentReferenceId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or
   *         {@code null}.
   */
  public CompartmentReference removeCompartmentReference(String compartmentReferenceId) {
    if (isSetListOfCompartmentReferences()) {
      return getListOfCompartmentReferences().remove(compartmentReferenceId);
    }
    return null;
  }


  /**
   * Removes an element from the {@link #listOfCompartmentReferences} at the given index.
   *
   * @param i the index where to remove the {@link CompartmentReference}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or if the index is
   *         out of bound ({@code (i < 0) || (i > listOfCompartmentReferences)}).
   */
  public CompartmentReference removeCompartmentReference(int i) {
    if (!isSetListOfCompartmentReferences()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfCompartmentReferences().remove(i);
  }


  /**
   * Creates a new CompartmentReference element and adds it to the
   * {@link #listOfCompartmentReferences} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfCompartmentReferences}
   */
  public CompartmentReference createCompartmentReference() {
    return createCompartmentReference(null);
  }


  /**
   * Creates a new {@link CompartmentReference} element and adds it to the
   * {@link #listOfCompartmentReferences} list.
   *
   * @param id the identifier that is to be applied to the new element.
   * @return the newly created {@link CompartmentReference} element, which is the last
   *         element in the {@link #listOfCompartmentReferences}.
   */
  public CompartmentReference createCompartmentReference(String id) {
    CompartmentReference compartmentReference = new CompartmentReference(id);
    addCompartmentReference(compartmentReference);
    return compartmentReference;
  }


  /**
   * Gets an element from the {@link #listOfCompartmentReferences} at the given index.
   *
   * @param i the index of the {@link CompartmentReference} element to get.
   * @return an element from the listOfCompartmentReferences at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public CompartmentReference getCompartmentReference(int i) {
    if (!isSetListOfCompartmentReferences()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfCompartmentReferences().get(i);
  }


  /**
   * Gets an element from the listOfCompartmentReferences, with the given id.
   *
   * @param compartmentReferenceId the id of the {@link CompartmentReference} element to get.
   * @return an element from the listOfCompartmentReferences with the given id
   *         or {@code null}.
   */
  public CompartmentReference getCompartmentReference(String compartmentReferenceId) {
    if (isSetListOfCompartmentReferences()) {
      return getListOfCompartmentReferences().get(compartmentReferenceId);
    }
    return null;
  }


  /**
   * Returns the number of {@link CompartmentReference}s in this
   * {@link MultiCompartmentPlugin}.
   * 
   * @return the number of {@link CompartmentReference}s in this
   *         {@link MultiCompartmentPlugin}.
   */
  public int getCompartmentReferenceCount() {
    return isSetListOfCompartmentReferences() ? getListOfCompartmentReferences().size() : 0;
  }


  /**
   * Returns the number of {@link CompartmentReference}s in this
   * {@link MultiCompartmentPlugin}.
   * 
   * @return the number of {@link CompartmentReference}s in this
   *         {@link MultiCompartmentPlugin}.
   * @libsbml.deprecated same as {@link #getCompartmentReferenceCount()}
   */
  public int getNumCompartmentReferences() {
    return getCompartmentReferenceCount();
  }


  /**
   * Returns the value of {@link #compartmentType}.
   *
   * @return the value of {@link #compartmentType}.
   */
  public String getCompartmentType() {
    if (isSetCompartmentType()) {
      return compartmentType;
    }

    return null;
  }


  /**
   * Returns whether {@link #compartmentType} is set.
   *
   * @return whether {@link #compartmentType} is set.
   */
  public boolean isSetCompartmentType() {
    return compartmentType != null;
  }


  /**
   * Sets the value of compartmentType
   *
   * @param compartmentType the value of compartmentType to be set.
   */
  public void setCompartmentType(String compartmentType) {
    String oldCompartmentType = this.compartmentType;
    this.compartmentType = compartmentType;
    firePropertyChange(MultiConstants.compartmentType, oldCompartmentType, this.compartmentType);
  }


  /**
   * Unsets the variable compartmentType.
   *
   * @return {@code true} if compartmentType was set before, otherwise {@code false}.
   */
  public boolean unsetCompartmentType() {
    if (isSetCompartmentType()) {
      String oldCompartmentType = compartmentType;
      compartmentType = null;
      firePropertyChange(MultiConstants.compartmentType, oldCompartmentType, compartmentType);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of {@link #isType}.
   *
   * @return the value of {@link #isType}.
   */
  public boolean getIsType() {
    if (isSetIsType()) {
      return isType;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(MultiConstants.isType, this);
  }

  /**
   * Returns the value of {@link #isType}, return false if isType is not defined.
   *
   * @return the value of {@link #isType}, return false if isType is not defined.
   */
  public boolean isType() {
    if (isSetIsType()) {
      return isType;
    }

    return false;
  }


  /**
   * Returns whether {@link #isType} is set.
   *
   * @return whether {@link #isType} is set.
   */
  public boolean isSetIsType() {
    return isType != null;
  }


  /**
   * Sets the value of isType
   *
   * @param isType the value of isType to be set.
   */
  public void setIsType(boolean isType) {
    Boolean oldIsType = this.isType;
    this.isType = isType;
    firePropertyChange(MultiConstants.isType, oldIsType, this.isType);
  }


  /**
   * Unsets the variable isType.
   *
   * @return {@code true} if isType was set before, otherwise {@code false}.
   */
  public boolean unsetIsType() {
    if (isSetIsType()) {
      Boolean oldIsType = isType;
      isType = null;
      firePropertyChange(MultiConstants.isType, oldIsType, isType);
      return true;
    }
    return false;
  }

  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  @Override
  public int getChildCount() {
    int count = 0;

    if (isSetListOfCompartmentReferences()) {
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

    if (isSetListOfCompartmentReferences()) {
      if (pos == index) {
        return getListOfCompartmentReferences();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(
      MessageFormat.format(resourceBundle.getString("IndexExceedsBoundsException"),
        index, Math.min(pos, 0)));
  }

  
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 6217;
    int result = super.hashCode();
    result = prime * result
        + ((compartmentType == null) ? 0 : compartmentType.hashCode());
    result = prime * result + ((isType == null) ? 0 : isType.hashCode());
    result = prime * result + ((listOfCompartmentReferences == null) ? 0
        : listOfCompartmentReferences.hashCode());
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
    MultiCompartmentPlugin other = (MultiCompartmentPlugin) obj;
    if (compartmentType == null) {
      if (other.compartmentType != null) {
        return false;
      }
    } else if (!compartmentType.equals(other.compartmentType)) {
      return false;
    }
    if (isType == null) {
      if (other.isType != null) {
        return false;
      }
    } else if (!isType.equals(other.isType)) {
      return false;
    }
    if (listOfCompartmentReferences == null) {
      if (other.listOfCompartmentReferences != null) {
        return false;
      }
    } else if (!listOfCompartmentReferences
        .equals(other.listOfCompartmentReferences)) {
      return false;
    }
    return true;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetCompartmentType()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.compartmentType, getCompartmentType());
    }
    if (isSetIsType()) {
      attributes.put(MultiConstants.shortLabel + ":" + MultiConstants.isType, isType.toString());
    }
    return attributes;
  }

  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = true;

    if (attributeName.equals(MultiConstants.compartmentType)) {
      setCompartmentType(value);
    }
    else if (attributeName.equals(MultiConstants.isType)) {
      setIsType(StringTools.parseSBMLBoolean(value));
    }
    else {
      isAttributeRead = false;
    }

    return isAttributeRead;
  }
}
