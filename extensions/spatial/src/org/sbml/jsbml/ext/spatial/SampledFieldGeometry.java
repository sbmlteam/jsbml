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
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBMLException;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * @author Alex Thomas
 * @author Andreas Dr&auml;ger
 * @since 1.0
 */
public class SampledFieldGeometry extends GeometryDefinition {



  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(SampledFieldGeometry.class);

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -1541677165317653439L;

  /**
   * 
   */
  private ListOf<SampledVolume> listOfSampledVolumes;
  /**
   * 
   */
  private String sampledField;

  /**
   * Creates a new {@link SampledFieldGeometry} instance.
   */
  public SampledFieldGeometry() {
    super();
  }

  /**
   * Creates a new {@link SampledFieldGeometry} instance cloned from the given sampledFieldGeometry.
   * 
   * @param sampledFieldGeometry the {@link SampledFieldGeometry} to clone.
   */
  public SampledFieldGeometry(SampledFieldGeometry sampledFieldGeometry) {
    super(sampledFieldGeometry);

    if (sampledFieldGeometry.isSetSampledField()) {
      setSampledField(sampledFieldGeometry.getSampledField());
    }

    if (sampledFieldGeometry.isSetListOfSampledVolumes()) {
      setListOfSampledVolumes(sampledFieldGeometry.getListOfSampledVolumes().clone());
    }
  }

  /**
   * Creates a new {@link SampledFieldGeometry} instance.
   * 
   * @param id the element id
   * @param level the SBML level
   * @param version the SBML version
   */
  public SampledFieldGeometry(String id, int level, int version) {
    super(id,level,version);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractTreeNode#clone()
   */
  @Override
  public SampledFieldGeometry clone() {
    return new SampledFieldGeometry(this);
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.GeometryDefinition#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    boolean equal = super.equals(object);
    if (equal) {
      SampledFieldGeometry sfg = (SampledFieldGeometry) object;
      equal &= sfg.isSetListOfSampledVolumes() == isSetListOfSampledVolumes();
      if (equal && isSetListOfSampledVolumes()) {
        equal &= sfg.getListOfSampledVolumes().equals(getListOfSampledVolumes());
      }
      equal &= sfg.isSetSampledField() == isSetSampledField();
      if (equal && isSetSampledField()) {
        equal &= sfg.getSampledField().equals(getSampledField());
      }
    }
    return equal;
  }

  /**
   * Returns the value of sampledField
   *
   * @return the value of sampledField
   */
  public String getSampledField() {
    if (isSetSampledField()) {
      return sampledField;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(SpatialConstants.sampledField, this);
  }


  /**
   * Returns whether sampledField is set
   *
   * @return whether sampledField is set
   */
  public boolean isSetSampledField() {
    return sampledField != null;
  }


  /**
   * Sets the value of sampledField
   * 
   * @param sampledField the sampledField value to set
   */
  public void setSampledField(String sampledField) {
    String oldSampledField = this.sampledField;
    this.sampledField = sampledField;
    firePropertyChange(SpatialConstants.sampledField, oldSampledField, this.sampledField);
  }


  /**
   * Unsets the variable sampledField
   *
   * @return {@code true}, if sampledField was set before,
   *         otherwise {@code false}
   */
  public boolean unsetSampledField() {
    if (isSetSampledField()) {
      String oldSampledField = sampledField;
      sampledField = null;
      firePropertyChange(SpatialConstants.sampledField, oldSampledField, sampledField);
      return true;
    }
    return false;
  }


  /**
   * Returns {@code true}, if listOfSampledVolumes contains at least one element.
   *
   * @return {@code true}, if listOfSampledVolumes contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfSampledVolumes() {
    if ((listOfSampledVolumes == null) || listOfSampledVolumes.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * Returns the listOfSampledVolumes. Creates it if it is not already existing.
   *
   * @return the listOfSampledVolumes
   */
  public ListOf<SampledVolume> getListOfSampledVolumes() {
    if (!isSetListOfSampledVolumes()) {
      listOfSampledVolumes = new ListOf<SampledVolume>(getLevel(),
          getVersion());
      listOfSampledVolumes.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfSampledVolumes.setPackageName(null);
      listOfSampledVolumes.setPackageName(SpatialConstants.shortLabel);
      listOfSampledVolumes.setSBaseListType(ListOf.Type.other);
      registerChild(listOfSampledVolumes);
    }
    return listOfSampledVolumes;
  }


  /**
   * Sets the given {@code ListOf<SampledVolume>}. If listOfSampledVolumes
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfSampledVolumes the list of {@link SampledVolume} to set.
   */
  public void setListOfSampledVolumes(ListOf<SampledVolume> listOfSampledVolumes) {
    unsetListOfSampledVolumes();
    this.listOfSampledVolumes = listOfSampledVolumes;

    if (listOfSampledVolumes != null) {
      listOfSampledVolumes.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'spatial'
      listOfSampledVolumes.setPackageName(null);
      listOfSampledVolumes.setPackageName(SpatialConstants.shortLabel);
      listOfSampledVolumes.setSBaseListType(ListOf.Type.other);

      registerChild(this.listOfSampledVolumes);
    }

  }


  /**
   * Returns {@code true}, if listOfSampledVolumes contain at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfSampledVolumes contain at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfSampledVolumes() {
    if (isSetListOfSampledVolumes()) {
      ListOf<SampledVolume> oldSampledVolumes = listOfSampledVolumes;
      listOfSampledVolumes = null;
      oldSampledVolumes.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link SampledVolume} to the listOfSampledVolumes.
   * <p>The listOfSampledVolumes is initialized if necessary.
   *
   * @param sampledVolume the element to add to the list
   * @return true (as specified by {@link Collection#add})
   */
  public boolean addSampledVolume(SampledVolume sampledVolume) {
    return getListOfSampledVolumes().add(sampledVolume);
  }


  /**
   * Removes an element from the listOfSampledVolumes.
   *
   * @param sampledVolume the element to be removed from the list
   * @return true if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeSampledVolume(SampledVolume sampledVolume) {
    if (isSetListOfSampledVolumes()) {
      if (getListOfSampledVolumes().size()<=1) {
        return false;
      } else {
        return getListOfSampledVolumes().remove(sampledVolume);
      }
    }
    return false;
  }


  /**
   * Removes an element from the listOfSampledVolumes at the given index.
   *
   * @param i the index where to remove the {@link SampledVolume}
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public void removeSampledVolume(int i) {
    if (!isSetListOfSampledVolumes()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    if (getListOfSampledVolumes().size()<=1) {
      throw new SBMLException("There must be at least one SampledVolume defined for this list");
    }
    getListOfSampledVolumes().remove(i);
  }

  /**
   * Removes an element from the listOfSampledVolumes with the given id.
   * 
   * @param id the id of the element to remove.
   */
  public void removeSampledVolume(String id) {
    getListOfSampledVolumes().removeFirst(new NameFilter(id));
  }

  /**
   * Creates a new {@link SampledVolume} instance and adds it to the ListOfSampledVolumes list
   * 
   * @return a new {@link SampledVolume} instance
   */
  public SampledVolume createSampledVolume() {
    return createSampledVolume(null);
  }

  /**
   * Creates a new {@link SampledVolume} instance and adds it to the ListOfSampledVolumes list
   * 
   * @param id the id to set in the new SampledVolume instance
   *
   * @return a new {@link SampledVolume} instance
   */
  public SampledVolume createSampledVolume(String id) {
    SampledVolume sampledVolume = new SampledVolume(id, getLevel(), getVersion());
    addSampledVolume(sampledVolume);
    return sampledVolume;
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
    if (isSetListOfSampledVolumes()) {
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
    if (isSetListOfSampledVolumes()) {
      if (pos == index) {
        return getListOfSampledVolumes();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.GeometryDefinition#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 1951;
    int hashCode = super.hashCode();
    if (isSetSampledField()) {
      hashCode += prime * getSampledField().hashCode();
    }
    if (isSetListOfSampledVolumes()) {
      hashCode += prime * getListOfSampledVolumes().hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.GeometryDefinition#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetSampledField()) {
      attributes.put(SpatialConstants.shortLabel + ":sampledField", getSampledField());
    }
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.spatial.GeometryDefinition#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = (super.readAttribute(attributeName, prefix, value))
        && (SpatialConstants.shortLabel == prefix);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(SpatialConstants.sampledField)) {
        try {
          setSampledField(value);
        } catch (Exception e) {
          logger.warn(MessageFormat.format(
            SpatialConstants.bundle.getString("COULD_NOT_READ_ATTRIBUTE"), value, SpatialConstants.sampledField, getElementName()));
        }
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
