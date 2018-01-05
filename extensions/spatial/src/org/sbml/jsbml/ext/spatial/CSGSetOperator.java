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
package org.sbml.jsbml.ext.spatial;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * @author Alex Thomas
 * @author Piero Dalle Pezze
 * @since 1.0
 */
public class CSGSetOperator extends CSGNode {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(CSGSetOperator.class);

  /**
   * 
   */
  private static final long serialVersionUID = 3448308755493169761L;

  /**
   * 
   */
  private SetOperation operationType;

  /**
   * 
   */
  private String complementA;
  /**
   * 
   */
  private String complementB;

  /**
   * 
   */
  private ListOf<CSGNode> listOfCSGNodes;

  /**
   * 
   */
  public CSGSetOperator() {
    super();
  }


  /**
   * @param csgso
   */
  public CSGSetOperator(CSGSetOperator csgso) {
    super(csgso);
    if (csgso.isSetListOfCSGNodes()) {
      setListOfCSGNodes(csgso.getListOfCSGNodes().clone());
    }

    if (csgso.isSetOperationType()) {
      setOperationType(csgso.getOperationType());
    }

    if (csgso.isSetComplementA()) {
      setComplementA(csgso.getComplementA());
    }

    if (csgso.isSetComplementB()) {
      setComplementB(csgso.getComplementB());
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

      equal &= csgso.isSetComplementA() == isSetComplementA();
      if (equal && isSetComplementA()) {
        equal &= csgso.getComplementA().equals(getComplementA());
      }

      equal &= csgso.isSetComplementB() == isSetComplementB();
      if (equal && isSetComplementB()) {
        equal &= csgso.getComplementB().equals(getComplementB());
      }
    }
    return equal;
  }


  /**
   * Returns the value of complementA
   *
   * @return the value of complementA
   */
  public String getComplementA() {
    if (isSetComplementA()) {
      return complementA;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.complementA, this);
  }


  /**
   * Returns whether complementA is set
   *
   * @return whether complementA is set
   */
  public boolean isSetComplementA() {
    return complementA != null;
  }


  /**
   * Sets the value of complementA
   * @param complementA
   */
  public void setComplementA(String complementA) {
    String oldComplementA = this.complementA;
    this.complementA = complementA;
    firePropertyChange(SpatialConstants.complementA, oldComplementA, this.complementA);
  }


  /**
   * Unsets the variable complementA
   *
   * @return {@code true}, if complementA was set before,
   *         otherwise {@code false}
   */
  public boolean unsetComplementA() {
    if (isSetComplementA()) {
      String oldComplementA = complementA;
      complementA = null;
      firePropertyChange(SpatialConstants.complementA, oldComplementA, complementA);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of complementB
   *
   * @return the value of complementB
   */
  public String getComplementB() {
    if (isSetComplementB()) {
      return complementB;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.complementB, this);
  }


  /**
   * Returns whether complementB is set
   *
   * @return whether complementB is set
   */
  public boolean isSetComplementB() {
    return complementB != null;
  }


  /**
   * Sets the value of complementB
   * @param complementB
   */
  public void setComplementB(String complementB) {
    String oldComplementB = this.complementB;
    this.complementB = complementB;
    firePropertyChange(SpatialConstants.complementB, oldComplementB, this.complementB);
  }


  /**
   * Unsets the variable complementB
   *
   * @return {@code true}, if complementB was set before,
   *         otherwise {@code false}
   */
  public boolean unsetComplementB() {
    if (isSetComplementB()) {
      String oldComplementB = complementB;
      complementB = null;
      firePropertyChange(SpatialConstants.complementB, oldComplementB, complementB);
      return true;
    }
    return false;
  }


  /**
   * Returns the value of operationType
   *
   * @return the value of operationType
   */
  public SetOperation getOperationType() {
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
   * @param operationType
   */
  public void setOperationType(SetOperation operationType) {
    SetOperation oldOperationType = this.operationType;
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
      SetOperation oldOperationType = operationType;
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
      listOfCSGNodes.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfCSGNodes.setPackageName(null);
      listOfCSGNodes.setPackageName(SpatialConstants.shortLabel);
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

    if (listOfCSGNodes != null) {
      listOfCSGNodes.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfCSGNodes.setPackageName(null);
      listOfCSGNodes.setPackageName(SpatialConstants.shortLabel);
      listOfCSGNodes.setSBaseListType(ListOf.Type.other);

      registerChild(this.listOfCSGNodes);
    }
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
   * @return true (as specified by {@link Collection#add})
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
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public void removeCSGNode(int i) {
    if (!isSetListOfCSGNodes()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfCSGNodes().remove(i);
  }

  /**
   * @param id
   */
  public void removeCSGNode(String id) {
    getListOfCSGNodes().removeFirst(new NameFilter(id));
  }
  /**
   * Creates a new CSGNode element and adds it to the ListOfCSGNodes list
   * @return
   */
  public CSGPrimitive createCSGPrimitive() {
    return createCSGPrimitive(null);
  }

  /**
   * @return
   */
  public CSGPseudoPrimitive createCSGPseudoPrimitive() {
    return createCSGPseudoPrimitive(null);
  }

  /**
   * @return
   */
  public CSGSetOperator createCSGSetOperator() {
    return createCSGSetOperator(null);
  }

  /**
   * @return
   */
  public CSGTranslation createCSGTranslation() {
    return createCSGTranslation(null);
  }

  /**
   * @return
   */
  public CSGRotation createCSGRotation() {
    return createCSGRotation(null);
  }

  /**
   * @return
   */
  public CSGScale createCSGScale() {
    return createCSGScale(null);
  }

  /**
   * @return
   */
  public CSGHomogeneousTransformation createCSGHomogeneousTransformation() {
    return createCSGHomogeneousTransformation(null);
  }


  /**
   * Creates a new {@link CSGHomogeneousTransformation} element and adds it to the ListOfCSGNodes list
   * @param id
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
   * @param id
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
   * @param id
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
   * @param id
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
   * @param id
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
   * @param id
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
   * @param id
   *
   * @return a new {@link CSGPrimitive} element
   */
  public CSGPrimitive createCSGPrimitive(String id) {
    CSGPrimitive csgNode = new CSGPrimitive(id, getLevel(), getVersion());
    addCSGNode(csgNode);
    return csgNode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetListOfCSGNodes()) {
      count++;
    }
    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#getChildAt(int)
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
    if (isSetListOfCSGNodes()) {
      if (pos == index) {
        return getListOfCSGNodes();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1861;
    int hashCode = super.hashCode();
    if (isSetOperationType()) {
      hashCode += prime * getOperationType().hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetOperationType()) {
      attributes.remove("operationType");
      attributes.put(SpatialConstants.shortLabel + ":operationType", getOperationType().toString());
    }

    if (isSetComplementA()) {
      attributes.remove("complementA");
      attributes.put(SpatialConstants.shortLabel + ":complementA",
        getComplementA());
    }

    if (isSetComplementB()) {
      attributes.remove("complementB");
      attributes.put(SpatialConstants.shortLabel + ":complementB",
        getComplementB());
    }
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.operationType)) {
        try {
          setOperationType(SetOperation.valueOf(value));
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.operationType, getElementName()));
        }
      }

      else if (attributeName.equals(SpatialConstants.complementA)) {
        try {
          setComplementA(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.complementA, getElementName()));
        }
      }

      else if (attributeName.equals(SpatialConstants.complementB)) {
        try {
          setComplementB(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.complementB, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getElementName()
   */
  @Override
  public String getElementName() {
    return SpatialConstants.csgSetOperator;
  }

}
