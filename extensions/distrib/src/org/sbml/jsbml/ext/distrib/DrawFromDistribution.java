/*
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * 
 * Copyright (C) 2009-2017 jointly by the following organizations:
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
package org.sbml.jsbml.ext.distrib;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.util.IdManager;
import org.sbml.jsbml.xml.XMLNode;

/**
 * 
 * @author Nicolas Rodriguez
 * @since 1.1
 */
public class DrawFromDistribution extends AbstractSBase implements IdManager {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -7217922005569440580L;

  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(DrawFromDistribution.class);

  /**
   * 
   */
  private ListOf<DistribInput> listOfDistribInputs;
  /**
   * 
   */
  private XMLNode uncertML;

  /**
   * Maps between the {@link DistribInput} identifiers and themselves.
   */
  private Map<String, DistribInput> mapOfDistribInputs;

  /**
   * Creates a DrawFromDistribution instance
   */
  public DrawFromDistribution() {
    super();
    initDefaults();
  }


  /**
   * Creates a DrawFromDistribution instance with a level and version.
   * 
   * @param level
   * @param version
   */
  public DrawFromDistribution(int level, int version) {
    super(level, version);
    initDefaults();
  }


  /**
   * Clone constructor
   * @param obj
   */
  public DrawFromDistribution(DrawFromDistribution obj) {
    super(obj);

    if (obj.isSetUncertML()) {
      setUncertML(obj.getUncertML().clone());
    }
    if (obj.isSetListOfDistribInputs()) {
      setListOfDistribInputs(obj.getListOfDistribInputs().clone());
    }
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 5153;
    int result = super.hashCode();
    result = prime * result
        + ((listOfDistribInputs == null) ? 0 : listOfDistribInputs.hashCode());
    result = prime * result + ((uncertML == null) ? 0 : uncertML.hashCode());
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
    DrawFromDistribution other = (DrawFromDistribution) obj;
    if (listOfDistribInputs == null) {
      if (other.listOfDistribInputs != null) {
        return false;
      }
    } else if (!listOfDistribInputs.equals(other.listOfDistribInputs)) {
      return false;
    }
    if (uncertML == null) {
      if (other.uncertML != null) {
        return false;
      }
    } else if (!uncertML.equals(other.uncertML)) {
      return false;
    }
    return true;
  }


  /**
   * clones this class
   */
  @Override
  public DrawFromDistribution clone() {
    return new DrawFromDistribution(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = DistribConstants.shortLabel;
  }


  /**
   * Returns the value of uncertML
   *
   * @return the value of uncertML
   */
  public XMLNode getUncertML() {
    if (isSetUncertML()) {
      return uncertML;
    }

    return null;
  }


  /**
   * Returns whether uncertML is set
   *
   * @return whether uncertML is set
   */
  public boolean isSetUncertML() {
    return uncertML != null;
  }


  /**
   * Sets the value of uncertML
   * @param uncertML
   */
  public void setUncertML(XMLNode uncertML) {
    XMLNode oldUncertML = this.uncertML;
    this.uncertML = uncertML;
    this.uncertML.setParent(this);
    firePropertyChange(DistribConstants.uncertML, oldUncertML, this.uncertML);
  }


  /**
   * Unsets the variable uncertML
   *
   * @return {@code true}, if uncertML was set before,
   *         otherwise {@code false}
   */
  public boolean unsetUncertML() {
    if (isSetUncertML()) {
      XMLNode oldUncertML = uncertML;
      uncertML = null;
      firePropertyChange(DistribConstants.uncertML, oldUncertML, uncertML);
      return true;
    }
    return false;
  }


  /**
   * Returns {@code true}, if listOfDistribInputs contains at least one element.
   *
   * @return {@code true}, if listOfDistribInputs contains at least one element,
   *         otherwise {@code false}.
   */
  public boolean isSetListOfDistribInputs() {
    if ((listOfDistribInputs == null) || listOfDistribInputs.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * Returns the listOfDistribInputs. Creates it if it is not already existing.
   *
   * @return the listOfDistribInputs.
   */
  public ListOf<DistribInput> getListOfDistribInputs() {
    if (!isSetListOfDistribInputs()) {
      listOfDistribInputs = new ListOf<DistribInput>();
      listOfDistribInputs.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'distrib'
      listOfDistribInputs.setPackageName(null);
      listOfDistribInputs.setPackageName(DistribConstants.shortLabel);
      listOfDistribInputs.setSBaseListType(ListOf.Type.other);

      registerChild(listOfDistribInputs);
    }
    return listOfDistribInputs;
  }


  /**
   * Sets the given {@code ListOf<DistribInput>}. If listOfDistribInputs
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfDistribInputs
   */
  public void setListOfDistribInputs(ListOf<DistribInput> listOfDistribInputs) {
    unsetListOfDistribInputs();
    this.listOfDistribInputs = listOfDistribInputs;

    if (listOfDistribInputs != null) {
      listOfDistribInputs.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'distrib'
      listOfDistribInputs.setPackageName(null);
      listOfDistribInputs.setPackageName(DistribConstants.shortLabel);
      listOfDistribInputs.setSBaseListType(ListOf.Type.other);
    }

    registerChild(listOfDistribInputs);
  }


  /**
   * Returns {@code true}, if listOfDistribInputs contain at least one element,
   *         otherwise {@code false}.
   *
   * @return {@code true}, if listOfDistribInputs contain at least one element,
   *         otherwise {@code false}.
   */
  public boolean unsetListOfDistribInputs() {
    if (isSetListOfDistribInputs()) {
      ListOf<DistribInput> oldDistribInputs = listOfDistribInputs;
      listOfDistribInputs = null;
      oldDistribInputs.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * Adds a new {@link DistribInput} to the listOfDistribInputs.
   * <p>The listOfDistribInputs is initialized if necessary.
   *
   * @param DistribInput the element to add to the list
   * @return {@code true} (as specified by {@link Collection#add(Object)})
   */
  public boolean addDistribInput(DistribInput DistribInput) {
    return getListOfDistribInputs().add(DistribInput);
  }


  /**
   * Removes an element from the listOfDistribInputs.
   *
   * @param DistribInput the element to be removed from the list.
   * @return true if the list contained the specified element and it was removed.
   * @see List#remove(Object)
   */
  public boolean removeDistribInput(DistribInput DistribInput) {
    if (isSetListOfDistribInputs()) {
      return getListOfDistribInputs().remove(DistribInput);
    }
    return false;
  }


  /**
   * Removes an element from the listOfDistribInputs.
   *
   * @param DistribInputId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or null.
   */
  public DistribInput removeDistribInput(String DistribInputId) {
    if (isSetListOfDistribInputs()) {
      return getListOfDistribInputs().remove(DistribInputId);
    }
    return null;
  }


  /**
   * Removes an element from the listOfDistribInputs at the given index.
   *
   * @param i the index where to remove the {@link DistribInput}.
   * @return the specified element, if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
   */
  public DistribInput removeDistribInput(int i) {
    if (!isSetListOfDistribInputs()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfDistribInputs().remove(i);
  }


  /**
   * Creates a new DistribInput element and adds it to the ListOfDistribInputs list.
   * @return
   */
  public DistribInput createDistribInput() {
    return createDistribInput(null);
  }


  /**
   * Creates a new {@link DistribInput} element and adds it to the ListOfDistribInputs list.
   *
   * @param id the id to be set on the new {@link DistribInput} element.
   * @return a new {@link DistribInput} element.
   */
  public DistribInput createDistribInput(String id) {
    DistribInput DistribInput = new DistribInput(id);
    addDistribInput(DistribInput);
    return DistribInput;
  }


  /**
   * Gets an element from the listOfDistribInputs at the given index.
   *
   * @param i the index of the {@link DistribInput} element to get.
   * @return an element from the listOfDistribInputs at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
   */
  public DistribInput getDistribInput(int i) {
    if (!isSetListOfDistribInputs()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfDistribInputs().get(i);
  }


  /**
   * Gets an element from the listOfDistribInputs, with the given id.
   *
   * @param distribInputId the id of the {@link DistribInput} element to get.
   * @return an element from the listOfDistribInputs with the given id or null.
   */
  public DistribInput getDistribInput(String distribInputId) {
    if (isSetListOfDistribInputs()) {
      return getListOfDistribInputs().get(distribInputId);
    }
    return null;
  }


  /**
   * Returns the number of {@link DistribInput}s in this {@link DrawFromDistribution}.
   * 
   * @return the number of {@link DistribInput}s in this {@link DrawFromDistribution}.
   */
  public int getDistribInputCount() {
    return isSetListOfDistribInputs() ? getListOfDistribInputs().size() : 0;
  }


  /**
   * Returns the number of {@link DistribInput}s in this {@link DrawFromDistribution}.
   * 
   * @return the number of {@link DistribInput}s in this {@link DrawFromDistribution}.
   * @libsbml.deprecated same as {@link #getDistribInputCount()}
   */
  public int getNumDistribInputs() {
    return getDistribInputCount();
  }


  @Override
  public boolean getAllowsChildren() {
    return true;
  }


  @Override
  public int getChildCount() {
    int count = super.getChildCount();

    if (isSetListOfDistribInputs()) {
      count++;
    }
    if (isSetUncertML()) {
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
    int count = super.getChildCount(), pos = 0;

    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }

    if (isSetListOfDistribInputs()) {
      if (pos == index) {
        return getListOfDistribInputs();
      }
      pos++;
    }
    if (isSetUncertML()) {
      if (pos == index) {
        return getUncertML();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.IdManager#accept(org.sbml.jsbml.SBase)
   */
  @Override
  public boolean accept(SBase sbase) {
    return sbase instanceof DistribInput;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.IdManager#register(org.sbml.jsbml.SBase)
   */
  @Override
  public boolean register(SBase sbase) {

    boolean success = true;

    if (sbase instanceof DistribInput) {
      DistribInput distribInput = (DistribInput) sbase;

      if (distribInput.isSetId()) {
        String distribInputId = distribInput.getId();

        if (mapOfDistribInputs == null) {
          mapOfDistribInputs = new HashMap<String, DistribInput>();
        }

        if (mapOfDistribInputs.containsKey(distribInputId) && mapOfDistribInputs.get(distribInputId) != sbase) {
          logger.error(MessageFormat.format(
            "A DistribInput with the id \"{0}\" is already present in this DrawFromDistribution. The new element will not be added to the DrawFromDistribution.",
            distribInputId));
          success = false;
        } else {

          mapOfDistribInputs.put(distribInputId, distribInput);

          if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("registered DistribInput id={0} in DrawFromDistribution",
              distribInputId));
          }
        }
      }
    } else {
      logger.error(MessageFormat.format(
        "Trying to register something that is not a DistribInput: \"{0}\".", sbase));
    }

    // TODO : register all the DistribInput children if any !!

    return success;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.IdManager#unregister(org.sbml.jsbml.SBase)
   */
  @Override
  public boolean unregister(SBase sbase) {

    // Always returning true at the moment to avoid exception when unregistering element
    boolean success = true;

    if (sbase instanceof DistribInput) {
      DistribInput port = (DistribInput) sbase;

      if (port.isSetId()) {
        String portId = port.getId();

        if (mapOfDistribInputs == null) {
          logger.warn("No DistribInput have been registered in this DrawFromDistribution {0}. Nothing to be done.");
          return success;
        }

        if (mapOfDistribInputs.containsKey(portId)) {
          mapOfDistribInputs.remove(portId);

          if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("unregistered DistribInput id={0} in DrawFromDistribution",  portId));
          }
        } else {

          logger.warn(MessageFormat.format(
            "A DistribInput with the id \"{0}\" is not present in this DrawFromDistribution. Nothing to be done.",
            portId));
        }
      }
    } else {
      logger.error(MessageFormat.format(
        "Trying to unregister something that is not a DistribInput: \"{0}\".", sbase));
    }

    // TODO : unregister all the DistribInput children if any !!

    return success;
  }


}
