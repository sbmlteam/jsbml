/*
 * $Id$
 * $URL$
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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractSBase;

/**
 * @author Andreas Dr&auml;ger
 * @version $Rev$
 * @since 1.1
 * @date 06.03.2015
 */
public abstract class LogicalOperator extends AbstractSBase implements Association {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 7724258767809732147L;


  /**
   * 
   */
  private List<Association> listOfAssociations;


  /**
   * 
   */
  public LogicalOperator() {
    super();
    initDefaults();
  }


  /**
   * @param level
   * @param version
   */
  public LogicalOperator(int level, int version) {
    super(level, version);
    initDefaults();
  }

  /**
   * @param association
   */
  public LogicalOperator(LogicalOperator association) {
    super(association);

    if (association.isSetListOfAssociations()) {
      for (Association childAssociation : association.getListOfAssociations()) {
        addAssociation((Association) childAssociation.clone());
      }
    }
  }

  /**
   * Sets the given {@code ListOf<Association>}. If {@link #listOfAssociations}
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfAssociations
   */
  public void addAllAssociations(List<Association> listOfAssociations) {
    for (Association association : listOfAssociations) {
      addAssociation(association);
    }
  }


  /**
   * Adds a new {@link Association} to the {@link #listOfAssociations}.
   * <p>The listOfAssociations is initialized if necessary.
   *
   * @param association the element to add to the list
   * @return {@code true} (as specified by {@link java.util.Collection#add})
   * @see java.util.Collection#add(Object)
   */
  public boolean addAssociation(Association association) {
    registerChild(association);
    return getListOfAssociations().add(association);
  }

  /**
   * Identical to calling {@link #addAssociation(Association)}.
   * @param child
   * @see #addAssociation(Association)
   */
  public void addChild(Association child) {
    addAssociation(child);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public abstract LogicalOperator clone();

  /**
   * Creates a new {@link Association} element and adds it to the
   * {@link #listOfAssociations} list.
   * 
   * @return the newly created {@link Association} element, which is the last
   *         element in the {@link #listOfAssociations}.
   */
  public And createAnd() {
    And association = new And(getLevel(), getVersion());
    addAssociation(association);
    return association;
  }

  /**
   * Creates a new Association element and adds it to the
   * {@link #listOfAssociations} list.
   *
   * @return the newly created element, i.e., the last item in the
   *         {@link #listOfAssociations}
   */
  public GeneProductRef createGeneProductRef() {
    return createGeneProductRef(null);
  }

  /**
   * Creates a new {@link Association} element and adds it to the
   * {@link #listOfAssociations} list.
   * 
   * @param id
   *        the identifier that is to be applied to the new element.
   * @return the newly created {@link Association} element, which is the last
   *         element in the {@link #listOfAssociations}.
   */
  public GeneProductRef createGeneProductRef(String id) {
    GeneProductRef association = new GeneProductRef(id, getLevel(), getVersion());
    addAssociation(association);
    return association;
  }

  /**
   * Creates a new {@link Association} element and adds it to the
   * {@link #listOfAssociations} list.
   * 
   * @return the newly created {@link Association} element, which is the last
   *         element in the {@link #listOfAssociations}.
   */
  public Or createOr() {
    Or association = new Or(getLevel(), getVersion());
    addAssociation(association);
    return association;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /**
   * Gets an element from the {@link #listOfAssociations} at the given index.
   *
   * @param i the index of the {@link Association} element to get.
   * @return an element from the listOfAssociations at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index < 0 || index > list.size).
   */
  public Association getAssociation(int i) {
    if (!isSetListOfAssociations()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfAssociations().get(i);
  }


  /**
   * Returns the number of {@link Association}s in this {@link LogicalOperator}.
   * 
   * @return the number of {@link Association}s in this {@link LogicalOperator}.
   */
  public int getAssociationCount() {
    return isSetListOfAssociations() ? getListOfAssociations().size() : 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(index + " < 0");
    }
    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }
    if (isSetListOfAssociations()) {
      return getListOfAssociations().get(index);
    }
    throw new IndexOutOfBoundsException(
      MessageFormat.format("Index {0,number,integer} >= {1,number,integer}",
        index, Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetListOfAssociations()) {
      count += listOfAssociations.size();
    }
    return count;
  }

  /**
   * Returns the {@link #listOfAssociations}. Creates it if it does not already exist.
   *
   * @return the {@link #listOfAssociations}.
   */
  public List<Association> getListOfAssociations() {
    if (listOfAssociations == null) {
      listOfAssociations = new ArrayList<Association>();
    }
    return listOfAssociations;
  }

  /**
   * Returns the number of {@link Association}s in this {@link LogicalOperator}.
   * 
   * @return the number of {@link Association}s in this {@link LogicalOperator}.
   * @libsbml.deprecated same as {@link #getAssociationCount()}
   */
  public int getNumAssociations() {
    return getAssociationCount();
  }

  /**
   * 
   */
  private void initDefaults() {
    setNamespace(FBCConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
    setPackageVersion(-1);
    packageName = FBCConstants.shortLabel;
  }

  /**
   * Returns {@code true} if {@link #listOfAssociations} contains at least one element.
   *
   * @return {@code true} if {@link #listOfAssociations} contains at least one element,
   *         otherwise {@code false}.
   */
  public boolean isSetListOfAssociations() {
    if ((listOfAssociations == null) || listOfAssociations.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * Returns {@code true} if {@link #listOfAssociations} contains at least one
   * element,
   * otherwise {@code false}.
   *
   * @return {@code true} if {@link #listOfAssociations} contains at least one
   *         element,
   *         otherwise {@code false}.
   */
  public boolean removeAllAssociations() {
    if (isSetListOfAssociations()) {
      for (int i = listOfAssociations.size() - 1; i >= 0; i--) {
        removeAssociation(i);
      }
      return true;
    }
    return false;
  }

  /**
   * Removes an element from the {@link #listOfAssociations}.
   *
   * @param association the element to be removed from the list.
   * @return {@code true} if the list contained the specified element and it was removed.
   * @see java.util.List#remove(Object)
   */
  public boolean removeAssociation(Association association) {
    if (isSetListOfAssociations()) {
      return getListOfAssociations().remove(association);
    }
    return false;
  }

  /**
   * Removes an element from the listOfAssociations at the given index.
   *
   * @param i the index where to remove the {@link Association}.
   * @return the specified element if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index < 0 || index > list.size).
   */
  public Association removeAssociation(int i) {
    if (!isSetListOfAssociations()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    Association element = getListOfAssociations().remove(i);
    element.fireNodeRemovedEvent();
    return element;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(getClass().getSimpleName());
    builder.append(" [");
    if (isSetMetaId()) {
      builder.append("metaid=");
      builder.append(getMetaId());
    }
    builder.append("]");
    return builder.toString();
  }

}
