/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2014  jointly by the following organizations:
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
package org.sbml.jsbml.ext.spatial;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBMLException;



/**
 * @author Alex Thomas
 * @version $Rev$
 * @since 1.0
 * @date Jan 20, 2014
 */
public class CSGSetOperator extends CSGNode {

  public static enum OperationType {

    UNION,
    INTERSECTION,
    DIFFERENCE;
  }

  /**
   * 
   */
  private static final long serialVersionUID = 3448308755493169761L;

  private OperationType operationType;

  private ListOf<CSGNode> listOfCSGNodes;

  public CSGSetOperator() {
    super();
  }


  /**
   * @param node
   */
  public CSGSetOperator(CSGSetOperator csgso) {
    super(csgso);
    if (csgso.isSetListOfCSGNodes()) {
      listOfCSGNodes = csgso.getListOfCSGNodes().clone();
    }

    if (csgso.isSetOperationType()) {
      operationType = csgso.getOperationType();
    }
  }


  /**
   * @param level
   * @param version
   */
  public CSGSetOperator(int level, int version) {
    super(level, version);
  }


  /**
   * 
   * @param id
   * @param level
   * @param version
   */
  public CSGSetOperator(String id, int level, int version) {
    super(id, level, version);
  }


  @Override
  public CSGSetOperator clone() {
    return new CSGSetOperator(this);
  }


  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      CSGSetOperator csgso = (CSGSetOperator) object;

      equal &= csgso.isSetOperationType() == isSetOperationType();
      if (equal && isSetOperationType()) {
        equal &= csgso.getOperationType().equals(getOperationType());
      }

      equal &= csgso.isSetListOfCSGNodes() == isSetListOfCSGNodes();
      if (equal && isSetListOfCSGNodes()) {
        equal &= csgso.getListOfCSGNodes().equals(getListOfCSGNodes());
      }
    }
    return equal;
  }


  /**
   * Returns the value of operationType
   *
   * @return the value of operationType
   */
  public OperationType getOperationType() {
    if (isSetOperationType()) {
      return operationType;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.operationType, this);
  }


  /**
   * Returns whether operationType is set
   *
   * @return whether operationType is set
   */
  public boolean isSetOperationType() {
    return operationType != null;
  }


  /**
   * Sets the value of operationType
   */
  public void setOperationType(OperationType operationType) {
    OperationType oldOperationType = this.operationType;
    this.operationType = operationType;
    firePropertyChange(SpatialConstants.operationType, oldOperationType, this.operationType);
  }


  /**
   * Unsets the variable operationType
   *
   * @return {@code true}, if operationType was set before,
   *         otherwise {@code false}
   */
  public boolean unsetOperationType() {
    if (isSetOperationType()) {
      OperationType oldOperationType = operationType;
      operationType = null;
      firePropertyChange(SpatialConstants.operationType, oldOperationType, operationType);
      return true;
    }
    return false;
  }


  /**
   * Returns {@code true}, if listOfCSGNodes contains at least one element.
   *
   * @return {@code true}, if listOfCSGNodes contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfCSGNodes() {
    if ((listOfCSGNodes == null) || listOfCSGNodes.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * Returns the listOfCSGNodes. Creates it if it is not already existing.
   *
   * @return the listOfCSGNodes
   */
  public ListOf<CSGNode> getListOfCSGNodes() {
    if (!isSetListOfCSGNodes()) {
      listOfCSGNodes = new ListOf<CSGNode>(getLevel(),
          getVersion());
      listOfCSGNodes.setNamespace(SpatialConstants.namespaceURI);
      listOfCSGNodes.setSBaseListType(ListOf.Type.other);
      registerChild(listOfCSGNodes);
    }
    return listOfCSGNodes;
  }


  /**
   * Sets the given {@code ListOf<CSGNode>}. If listOfCSGNodes
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfCSGNodes
   */
  public void setListOfCSGNodes(ListOf<CSGNode> listOfCSGNodes) {
    unsetListOfCSGNodes();
    this.listOfCSGNodes = listOfCSGNodes;
    registerChild(this.listOfCSGNodes);
  }


  /**
   * Returns {@code true}, if listOfCSGNodes contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfCSGNodes contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfCSGNodes() {
    if (isSetListOfCSGNodes()) {
      ListOf<CSGNode> oldCSGNodes = listOfCSGNodes;
      listOfCSGNodes = null;
      oldCSGNodes.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link CSGNode} to the listOfCSGNodes.
   * <p>The listOfCSGNodes is initialized if necessary.
   *
   * @param csgNode the element to add to the list
   * @return true (as specified by {@link Collection.add})
   */
  public boolean addCSGNode(CSGNode csgNode) {
    return getListOfCSGNodes().add(csgNode);
  }


  /**
   * Removes an element from the listOfCSGNodes.
   *
   * @param csgNode the element to be removed from the list
   * @return true if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeCSGNode(CSGNode csgNode) {
    if (isSetListOfCSGNodes()) {
      return getListOfCSGNodes().remove(csgNode);
    }
    return false;
  }


  /**
   * Removes an element from the listOfCSGNodes at the given index.
   *
   * @param i the index where to remove the {@link CSGNode}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index < 0 || index > list.size)
   */
  public void removeCSGNode(int i) {
    if (!isSetListOfCSGNodes()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfCSGNodes().remove(i);
  }


  /**
   * TODO: if the ID is mandatory for CSGNode objects,
   * one should also add this methods
   */
  //public void removeCSGNode(String id) {
  //  getListOfCSGNodes().removeFirst(new NameFilter(id));
  //}
  /**
   * Creates a new CSGNode element and adds it to the ListOfCSGNodes list
   */
  public CSGPrimitive createCSGPrimitive() {
    return createCSGPrimitive(null);
  }

  public CSGPseudoPrimitive createCSGPseudoPrimitive() {
    return createCSGPseudoPrimitive(null);
  }

  public CSGSetOperator createCSGSetOperator() {
    return createCSGSetOperator(null);
  }

  public CSGTranslation createCSGTranslation() {
    return createCSGTranslation(null);
  }

  public CSGRotation createCSGRotation() {
    return createCSGRotation(null);
  }

  public CSGScale createCSGScale() {
    return createCSGScale(null);
  }

  public CSGHomogeneousTransformation createCSGHomogeneousTransformation() {
    return createCSGHomogeneousTransformation(null);
  }


  /**
   * Creates a new {@link CSGHomogeneousTransformation} element and adds it to the ListOfCSGNodes list
   *
   * @return a new {@link CSGHomogeneousTransformation} element
   */
  public CSGHomogeneousTransformation createCSGHomogeneousTransformation(String id) {
    CSGHomogeneousTransformation csgNode = new CSGHomogeneousTransformation(id, getLevel(), getVersion());
    addCSGNode(csgNode);
    return csgNode;
  }

  /**
   * Creates a new {@link CSGScale} element and adds it to the ListOfCSGNodes list
   *
   * @return a new {@link CSGScale} element
   */
  public CSGScale createCSGScale(String id) {
    CSGScale csgNode = new CSGScale(id, getLevel(), getVersion());
    addCSGNode(csgNode);
    return csgNode;
  }

  /**
   * Creates a new {@link CSGRotation} element and adds it to the ListOfCSGNodes list
   *
   * @return a new {@link CSGRotation} element
   */
  public CSGRotation createCSGRotation(String id) {
    CSGRotation csgNode = new CSGRotation(id, getLevel(), getVersion());
    addCSGNode(csgNode);
    return csgNode;
  }

  /**
   * Creates a new {@link CSGTranslation} element and adds it to the ListOfCSGNodes list
   *
   * @return a new {@link CSGTranslation} element
   */
  public CSGTranslation createCSGTranslation(String id) {
    CSGTranslation csgNode = new CSGTranslation(id, getLevel(), getVersion());
    addCSGNode(csgNode);
    return csgNode;
  }

  /**
   * Creates a new {@link CSGSetOperator} element and adds it to the ListOfCSGNodes list
   *
   * @return a new {@link CSGSetOperator} element
   */
  public CSGSetOperator createCSGSetOperator(String id) {
    CSGSetOperator csgNode = new CSGSetOperator(id, getLevel(), getVersion());
    addCSGNode(csgNode);
    return csgNode;
  }

  /**
   * Creates a new {@link CSGPseudoPrimitive} element and adds it to the ListOfCSGNodes list
   *
   * @return a new {@link CSGPseudoPrimitive} element
   */
  public CSGPseudoPrimitive createCSGPseudoPrimitive(String id) {
    CSGPseudoPrimitive csgNode = new CSGPseudoPrimitive(id, getLevel(), getVersion());
    addCSGNode(csgNode);
    return csgNode;
  }

  /**
   * Creates a new {@link CSGPrimitive} element and adds it to the ListOfCSGNodes list
   *
   * @return a new {@link CSGPrimitive} element
   */
  public CSGPrimitive createCSGPrimitive(String id) {
    CSGPrimitive csgNode = new CSGPrimitive(id, getLevel(), getVersion());
    addCSGNode(csgNode);
    return csgNode;
  }

  /**
   * TODO: optionally, create additional create methods with more
   * variables, for instance "bar" variable
   */
  // public CSGNode createCSGNode(String id, int bar) {
  //   CSGNode csgNode = createCSGNode(id);
  //   csgNode.setBar(bar);
  //   return csgNode;
  // }
  /**
   * 
   */


  @Override
  public boolean getAllowsChildren() {
    return true;
  }


  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetListOfCSGNodes()) {
      count++;
    }
    return count;
  }


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
    if (isSetListOfCSGNodes()) {
      if (pos == index) {
        return getListOfCSGNodes();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      "Index {0,number,integer} >= {1,number,integer}", index,
      +Math.min(pos, 0)));
  }


  @Override
  public int hashCode() {
    final int prime = 983;//Change this prime number
    int hashCode = super.hashCode();
    if (isSetOperationType()) {
      hashCode += prime * getOperationType().hashCode();
    }
    return hashCode;
  }


  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetOperationType()) {
      attributes.remove("operationType");
      attributes.put(SpatialConstants.shortLabel + ":operationType", getOperationType().toString());
    }
    return attributes;
  }


  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.operationType)) {
        if (!Pattern.matches("[a-z]*", value)) {
          throw new SBMLException("The value is not all lower-case.");
        }
        setOperationType(OperationType.valueOf(value.toUpperCase()));
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("CSGSetOperator [operationType=");
    builder.append(operationType);
    builder.append("]");
    return builder.toString();
  }

}
