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
import org.sbml.jsbml.SBMLException;


/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class Domain extends AbstractSpatialNamedSBase {


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(Domain.class);

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 7959434109047369076L;

  /**
   * 
   */
  private String domainType;

  /**
   * 
   */
  private ListOf<InteriorPoint> listOfInteriorPoints;

  /**
   * 
   */
  public Domain() {
    super();
  }

  /**
   * @param level
   * @param version
   */
  public Domain(int level, int version) {
    super(level, version);
  }

  /**
   * @param id
   * @param level
   * @param version
   */
  public Domain(String id, int level, int version) {
    super(id,level,version);
  }

  /**
   * @param dm
   */
  public Domain(Domain dm) {
    super(dm);
    if (dm.isSetListOfInteriorPoints()) {
      setListOfInteriorPoints(dm.getListOfInteriorPoints().clone());
    }
    if (dm.isSetDomainType()) {
      setDomainType(dm.getDomainType());
    }

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  @Override
  public Domain clone() {
    return new Domain(this);
  }




  /**
   * Returns the value of domainType
   *
   * @return the value of domainType
   */
  public String getDomainType() {
    if (isSetDomainType()) {
      return domainType;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.domainType, this);
  }


  /**
   * Returns whether domainType is set
   *
   * @return whether domainType is set
   */
  public boolean isSetDomainType() {
    return domainType != null;
  }


  /**
   * Sets the value of domainType
   * @param domainType
   */
  public void setDomainType(String domainType) {
    String oldDomainType = this.domainType;
    this.domainType = domainType;
    firePropertyChange(SpatialConstants.domainType, oldDomainType, this.domainType);
  }


  /**
   * Unsets the variable domainType
   *
   * @return {@code true}, if domainType was set before,
   *         otherwise {@code false}
   */
  public boolean unsetDomainType() {
    if (isSetDomainType()) {
      String oldDomainType = domainType;
      domainType = null;
      firePropertyChange(SpatialConstants.domainType, oldDomainType, domainType);
      return true;
    }
    return false;
  }



  /**
   * Returns {@code true}, if listOfInteriorPoints contains at least one element.
   *
   * @return {@code true}, if listOfInteriorPoints contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfInteriorPoints() {
    if ((listOfInteriorPoints == null) || listOfInteriorPoints.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * Returns the listOfInteriorPoints. Creates it if it is not already existing.
   *
   * @return the listOfInteriorPoints
   */
  public ListOf<InteriorPoint> getListOfInteriorPoints() {
    if (!isSetListOfInteriorPoints()) {
      listOfInteriorPoints = new ListOf<InteriorPoint>();
      listOfInteriorPoints.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfInteriorPoints.setPackageName(null);
      listOfInteriorPoints.setPackageName(SpatialConstants.shortLabel);
      listOfInteriorPoints.setSBaseListType(ListOf.Type.other);
      registerChild(listOfInteriorPoints);
    }
    return listOfInteriorPoints;
  }


  /**
   * Sets the given {@code ListOf<InteriorPoint>}. If listOfInteriorPoints
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfInteriorPoints
   */
  public void setListOfInteriorPoints(ListOf<InteriorPoint> listOfInteriorPoints) {
    unsetListOfInteriorPoints();
    this.listOfInteriorPoints = listOfInteriorPoints;

    if (listOfInteriorPoints != null) {
      listOfInteriorPoints.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfInteriorPoints.setPackageName(null);
      listOfInteriorPoints.setPackageName(SpatialConstants.shortLabel);
      listOfInteriorPoints.setSBaseListType(ListOf.Type.other);

      registerChild(this.listOfInteriorPoints);
    }
  }


  /**
   * Returns {@code true}, if listOfInteriorPoints contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfInteriorPoints contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfInteriorPoints() {
    if (isSetListOfInteriorPoints()) {
      ListOf<InteriorPoint> oldInteriorPoints = listOfInteriorPoints;
      listOfInteriorPoints = null;
      oldInteriorPoints.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link InteriorPoint} to the listOfInteriorPoints.
   * <p>The listOfInteriorPoints is initialized if necessary.
   *
   * @param interiorPoint the element to add to the list
   * @return {@code true} (as specified by {@link Collection#add})
   */
  public boolean addInteriorPoint(InteriorPoint interiorPoint) {
    return getListOfInteriorPoints().add(interiorPoint);
  }


  /**
   * Removes an element from the listOfInteriorPoints.
   *
   * @param interiorPoint the element to be removed from the list
   * @return true if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeInteriorPoint(InteriorPoint interiorPoint) {
    if (isSetListOfInteriorPoints()) {
      if (getListOfInteriorPoints().size()<=1) {
        return false; // There must be at least one InteriorPoint defined in this list
      }
      else {
        return getListOfInteriorPoints().remove(interiorPoint);
      }
    }
    return false;
  }


  /**
   * Removes an element from the listOfInteriorPoints at the given index.
   *
   * @param i the index where to remove the {@link InteriorPoint}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public void removeInteriorPoint(int i) {
    if (!isSetListOfInteriorPoints()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    if (getListOfInteriorPoints().size()<=1) {
      throw new SBMLException("There must be at least one InteriorPoint defined for this list");
    }
    getListOfInteriorPoints().remove(i);
  }


  /**
   * Creates a new {@link InteriorPoint} element and adds it to the ListOfInteriorPoints list
   *
   * @return a new {@link InteriorPoint} element
   */
  public InteriorPoint createInteriorPoint() {
    InteriorPoint interiorPoint = new InteriorPoint(getLevel(), getVersion());
    addInteriorPoint(interiorPoint);
    return interiorPoint;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetListOfInteriorPoints()) {
      count++;
    }
    return count;
  }


  /*
   * (non-Javadoc)
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
    if (isSetListOfInteriorPoints()) {
      if (pos == index) {
        return getListOfInteriorPoints();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1571;
    int hashCode = super.hashCode();
    if (isSetDomainType()) {
      hashCode += prime * getDomainType().hashCode();
    }
    if (isSetListOfInteriorPoints()) {
      hashCode += prime * getListOfInteriorPoints().hashCode();
    }
    return hashCode;
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.AbstractSpatialNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetDomainType()) {
      attributes.remove("domainType");
      attributes.put(SpatialConstants.shortLabel + ":domainType", getDomainType());
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
      if (attributeName.equals(SpatialConstants.domainType)) {
        try {
          setDomainType(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.domainType, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      Domain dm = (Domain) object;
      equal &= dm.isSetDomainType() == isSetDomainType();
      if (equal && isSetDomainType()) {
        equal &= dm.getDomainType().equals(getDomainType());
      }
      equal &= dm.isSetListOfInteriorPoints() == isSetListOfInteriorPoints();
      if (equal && isSetListOfInteriorPoints()) {
        equal &= dm.getListOfInteriorPoints().equals(getListOfInteriorPoints());
      }
    }
    return equal;
  }

}
