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
 * 6. The University of Utah, Salt Lake City, UT, USA
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.arrays;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.NamedSBase;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.util.IdManager;

/**
 * The {@link ArraysSBasePlugin} class extends the {@link SBase} class by adding
 * an optional ListOf{@link Dimension}s child and a single optional
 * {@link Index} child.
 * 
 * <p>
 * The {@link ArraysSBasePlugin} class codifies the extensions to the
 * {@link SBase} class defined in the 'Arrays' package. These extensions allows
 * the modeler to define one or more {@link Dimension} elements to indicate the
 * parent {@link SBase} object is arrayed. In addition, these extensions allows
 * the definition of one or more {@link Index} elements to reference the parent
 * {@link SBase} that has one or more array dimensions.</p>
 * 
 * @author Leandro Watanabe
 * @since 1.0
 * @see Dimension
 * @see Index
 */
public class ArraysSBasePlugin extends AbstractSBasePlugin implements IdManager{

  /**
   * 
   */
  private static final long serialVersionUID = -5467877915615614247L;


  /**
   * A {@link Logger} for this class.
   */
  private static final transient Logger logger = Logger.getLogger(ArraysSBasePlugin.class);

  /**
   * Maps between the {@link Dimension} identifiers and themselves.
   */
  Map<String, Dimension> mapOfDimensions;

  /**
   * 
   */
  private ListOf<Index> listOfIndices;

  /**
   * 
   */
  private ListOf<Dimension> listOfDimensions;

  /**
   * Creates an new {@link ArraysSBasePlugin} instance.
   */
  public ArraysSBasePlugin() {
    super();
    initDefaults();
  }

  /**
   * Creates an new {@link ArraysSBasePlugin} instance.
   * 
   * @param extendedSBase the core {@link SBase} that is extended
   */
  public ArraysSBasePlugin(SBase extendedSBase) {
    super(extendedSBase);
    initDefaults();
  }

  /**
   * Clone constructor
   * 
   * @param obj the instance to clone
   */
  public ArraysSBasePlugin(ArraysSBasePlugin obj) {
    super(obj);

    if (obj.isSetListOfDimensions()) {
      setListOfDimensions(obj.listOfDimensions.clone());
    }

    if (obj.isSetListOfIndices()){
      setListOfIndices(obj.listOfIndices.clone());
    }

  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#clone()
   */
  @Override
  public ArraysSBasePlugin clone() {
    return new ArraysSBasePlugin(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    setPackageVersion(-1);
  }

  /**
   * Returns {@code true}, if listOfIndices contains at least one element.
   *
   * @return {@code true}, if listOfIndices contains at least one element,
   *         otherwise {@code false}.
   */
  public boolean isSetListOfIndices() {
    if ((listOfIndices == null) || listOfIndices.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * Returns the listOfIndices. Creates it if it is not already existing.
   *
   * @return the listOfIndices.
   */
  public ListOf<Index> getListOfIndices() {
    if (!isSetListOfIndices()) {
      listOfIndices = new ListOf<Index>();
      listOfIndices.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'arrays'
      listOfIndices.setPackageName(null);
      listOfIndices.setPackageName(ArraysConstants.shortLabel);
      listOfIndices.setSBaseListType(ListOf.Type.other);
      listOfIndices.setOtherListName(ArraysConstants.listOfIndices);

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(listOfIndices);
      }
    }
    return listOfIndices;
  }

  /**
   * Sets the given {@code ListOf<Index>}. If listOfIndices
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfIndices the list of {@link Index}
   */
  public void setListOfIndices(ListOf<Index> listOfIndices) {
    unsetListOfIndices();
    this.listOfIndices = listOfIndices;

    if (listOfIndices != null) {
      listOfIndices.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'arrays'
      listOfIndices.setPackageName(null);
      listOfIndices.setPackageName(ArraysConstants.shortLabel);
      listOfIndices.setSBaseListType(ListOf.Type.other);
      listOfIndices.setOtherListName(ArraysConstants.listOfIndices);
    }

    if (isSetExtendedSBase()) {
      extendedSBase.registerChild(this.listOfIndices);
    }
  }

  /**
   * Returns {@code true}, if listOfIndices contain at least one element,
   *         otherwise {@code false}.
   *
   * @return {@code true}, if listOfIndices contain at least one element,
   *         otherwise {@code false}.
   */
  public boolean unsetListOfIndices() {
    if (isSetListOfIndices()) {
      ListOf<Index> oldIndices = listOfIndices;
      listOfIndices = null;
      oldIndices.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Adds a new {@link Index} to the listOfIndices.
   * <p>The listOfIndices is initialized if necessary.
   *
   * @param field the element to add to the list
   * @return true (as specified by {@link Collection#add})
   */
  public boolean addIndex(Index field) {
    return getListOfIndices().add(field);
  }

  /**
   * Removes an element from the listOfIndices.
   *
   * @param field the element to be removed from the list.
   * @return true if the list contained the specified element and it was removed.
   * @see List#remove(Object)
   */
  public boolean removeIndex(Index field) {
    if (isSetListOfIndices()) {
      return getListOfIndices().remove(field);
    }
    return false;
  }

  /**
   * Removes an element from the listOfIndices based on array dimension.
   *
   * @param arrayDim
   *        the element with given array dimension to be removed from the list.
   * @return true if the list contained the specified element with given array
   *         dimension and it was removed.
   * @see List#remove(Object)
   */
  public boolean removeIndexByArrayDimension(int arrayDim) {
    if (isSetListOfIndices()) {
      ListOf<Index> list = getListOfIndices();
      for (Index index : list) {
        if (index.isSetArrayDimension()) {
          if (index.getArrayDimension() == arrayDim) {
            return index.removeFromParent();
          }
        }
      }
    }
    return false;
  }

  /**
   * Removes an element from the listOfIndices at the given index.
   *
   * @param i the index where to remove the {@link Index}.
   * @return the specified element, if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
   */
  public Index removeIndex(int i) {
    if (!isSetListOfIndices()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfIndices().remove(i);
  }

  /**
   * Creates a new Index element and adds it to the listOfIndices list.
   * 
   * @return a new {@link Index} instance.
   */
  public Index createIndex() {
    Index field = new Index();
    addIndex(field);
    return field;
  }

  /**
   * Gets an element from the listOfIndices at the given index.
   *
   * @param i the index of the {@link Index} element to get.
   * @return an element from the listOfIndices at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
   */
  public Index getIndex(int i) {
    if (!isSetListOfIndices()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfIndices().get(i);
  }

  /**
   * Gets an element from the listOfIndices based on array dimension
   * and referenced attribute.
   *
   * @param dim the index of the {@link Index} element to get.
   * @param attribute the referenced attribute
   * @return an element from the listOfIndices at the given index.
   */
  public Index getIndex(int dim, String attribute) {
    if (isSetListOfIndices()) {
      ListOf<Index> list = getListOfIndices();
      for (Index index : list) {
        if (index.isSetArrayDimension() && index.isSetReferencedAttribute()) {
          if (index.getArrayDimension() == dim
              && index.getReferencedAttribute().equals(attribute)) {
            return index;
          }
        }
      }
    }
    return null;
  }

  /**
   * Returns the number of {@link Index}s in this {@link ArraysSBasePlugin}.
   * 
   * @return the number of {@link Index}s in this {@link ArraysSBasePlugin}.
   */
  public int getIndexCount() {
    return isSetListOfIndices() ? getListOfIndices().size() : 0;
  }

  /**
   * Returns the number of {@link Index}s in this {@link ArraysSBasePlugin}.
   * 
   * @return the number of {@link Index}s in this {@link ArraysSBasePlugin}.
   * @libsbml.deprecated same as {@link #getIndexCount()}
   */
  public int getNumIndices() {
    return getIndexCount();
  }

  /**
   * Returns {@code true}, if listOfDimensions contains at least one element.
   *
   * @return {@code true}, if listOfDimensions contains at least one element,
   *         otherwise {@code false}.
   */
  public boolean isSetListOfDimensions() {
    if ((listOfDimensions == null) || listOfDimensions.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * Returns the listOfDimensions. Creates it if it is not already existing.
   *
   * @return the listOfDimensions.
   */
  public ListOf<Dimension> getListOfDimensions() {
    if (!isSetListOfDimensions()) {
      listOfDimensions = new ListOf<Dimension>();
      listOfDimensions.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'arrays'
      listOfDimensions.setPackageName(null);
      listOfDimensions.setPackageName(ArraysConstants.shortLabel);
      listOfDimensions.setSBaseListType(ListOf.Type.other);
      listOfDimensions.setOtherListName(ArraysConstants.listOfDimensions);

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(listOfDimensions);
      }
    }
    return listOfDimensions;
  }

  /**
   * Sets the given {@code ListOf<Dimension>}. If listOfDimensions
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfDimensions the list of {@link Dimension}s
   */
  public void setListOfDimensions(ListOf<Dimension> listOfDimensions) {
    unsetListOfDimensions();
    this.listOfDimensions = listOfDimensions;

    if (listOfDimensions != null) {
      listOfDimensions.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'arrays'
      listOfDimensions.setPackageName(null);
      listOfDimensions.setPackageName(ArraysConstants.shortLabel);
      listOfDimensions.setSBaseListType(ListOf.Type.other);
      listOfDimensions.setOtherListName(ArraysConstants.listOfDimensions);
    }

    if (isSetExtendedSBase()) {
      extendedSBase.registerChild(this.listOfDimensions);
    }
  }

  /**
   * Returns {@code true}, if listOfDimensions contain at least one element,
   *         otherwise {@code false}.
   *
   * @return {@code true}, if listOfDimensions contain at least one element,
   *         otherwise {@code false}.
   */
  public boolean unsetListOfDimensions() {
    if (isSetListOfDimensions()) {
      ListOf<Dimension> oldDimensions = listOfDimensions;
      listOfDimensions = null;
      oldDimensions.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Adds a new {@link Dimension} to the listOfDimensions.
   * <p>The listOfDimensions is initialized if necessary.
   *
   * @param field the element to add to the list
   * @return true (as specified by {@link Collection#add})
   */
  public boolean addDimension(Dimension field) {
    return getListOfDimensions().add(field);
  }

  /**
   * Removes an element from the listOfDimensions.
   *
   * @param field the element to be removed from the list.
   * @return true if the list contained the specified element and it was removed.
   * @see List#remove(Object)
   */
  public boolean removeDimension(Dimension field) {
    if (isSetListOfDimensions()) {
      return getListOfDimensions().remove(field);
    }
    return false;
  }

  /**
   * Removes an element from the listOfDimensions.
   *
   * @param fieldId the id of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or null.
   */
  public Dimension removeDimension(String fieldId) {
    if (isSetListOfDimensions()) {
      return getListOfDimensions().remove(fieldId);
    }
    return null;
  }

  /**
   * Removes an element from the listOfDimensions by array dimension.
   *
   * @param arrayDim the array dimension of the element to be removed from the list.
   * @return the removed element, if it was successfully found and removed or null.
   */
  public Dimension removeDimensionByArrayDimension(int arrayDim) {
    if (isSetListOfDimensions()) {
      Dimension dimension = getDimensionByArrayDimension(arrayDim);

      if (dimension != null) {
        dimension.removeFromParent();
      }

      return dimension;

    }
    return null;
  }

  /**
   * Removes an element from the listOfDimensions at the given index.
   *
   * @param i the index where to remove the {@link Dimension}.
   * @return the specified element, if it was successfully found and removed.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
   */
  public Dimension removeDimension(int i) {
    if (!isSetListOfDimensions()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    return getListOfDimensions().remove(i);
  }

  /**
   * Creates a new Dimension element and adds it to the ListOfDimensions list.
   * 
   * @return a new {@link Dimension} element.
   */
  public Dimension createDimension() {
    return createDimension(null);
  }

  /**
   * Creates a new {@link Dimension} element and adds it to the ListOfDimensions list.
   * 
   * @param id the id
   * @return a new {@link Dimension} element.
   */
  public Dimension createDimension(String id) {
    Dimension field = new Dimension(id);
    addDimension(field);
    return field;
  }

  /**
   * Gets an element from the listOfDimensions at the given index.
   *
   * @param i the dimension of the {@link Dimension} element to get.
   * @return an element from the listOfDimensions at the given index.
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; 2)
   */
  public Dimension getDimension(int i) {
    if (!isSetListOfDimensions()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }

    return getListOfDimensions().get(i);
  }
  /**
   * Gets an element from the listOfDimensions at the given arrayDimension.
   *
   * @param i the dimension of the {@link Dimension} element to get.
   * @return an element from the listOfDimensions at the given index or null.
   */
  public Dimension getDimensionByArrayDimension(int i) {
    if (isSetListOfDimensions()) {
      for (Dimension dim : getListOfDimensions()) {
        if (dim.isSetArrayDimension() && dim.getArrayDimension() == i) {
          return dim;
        }
      }
    }
    return null;
  }

  /**
   * Gets an element from the listOfDimensions, with the given id.
   *
   * @param fieldId the id of the {@link Dimension} element to get.
   * @return an element from the listOfDimensions with the given id or {@code null}.
   */
  public Dimension getDimension(String fieldId) {
    if (isSetListOfDimensions()) {
      return getListOfDimensions().get(fieldId);
    }
    return null;
  }

  /**
   * Returns the number of {@link Dimension}s in this {@link ArraysSBasePlugin}.
   * 
   * @return the number of {@link Dimension}s in this {@link ArraysSBasePlugin}.
   */
  public int getDimensionCount() {
    return isSetListOfDimensions() ? getListOfDimensions().size() : 0;
  }

  /**
   * Returns the number of {@link Dimension}s in this {@link ArraysSBasePlugin}.
   * 
   * @return the number of {@link Dimension}s in this {@link ArraysSBasePlugin}.
   * @libsbml.deprecated same as {@link #getDimensionCount()}
   */
  public int getNumDimensions() {
    return getDimensionCount();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getElementNamespace()
   */
  @Override
  public String getElementNamespace() {

    return ArraysConstants.getNamespaceURI(getLevel(), getVersion());
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
   */
  @Override
  public String getPackageName() {

    return ArraysConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPrefix()
   */
  @Override
  public String getPrefix() {
    return ArraysConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getURI()
   */
  @Override
  public String getURI() {
    return getElementNamespace();
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {

    if (childIndex < 0){
      throw new IndexOutOfBoundsException(MessageFormat.format(resourceBundle.getString("IndexSurpassesBoundsException"), childIndex, 0));
    }

    int pos = 0;
    if (isSetListOfDimensions()) {
      if (pos == childIndex) {
        return getListOfDimensions();
      }

      pos++;
    }

    if (isSetListOfIndices()) {
      if (pos == childIndex) {
        return getListOfIndices();
      }

      pos++;
    }

    throw new IndexOutOfBoundsException(MessageFormat.format("Index {0,  number, integer} >= {1, number, integer}", childIndex, (Math.min(pos, 0))));
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {

    int count = 0;
    if (isSetListOfDimensions()) {
      count++;
    }
    if (isSetListOfIndices()) {
      count++;
    }
    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    // There is no attribute
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    // No attribute
    Map<String, String> attributes = new TreeMap<String, String>();
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.IdManager#accept(org.sbml.jsbml.SBase)
   */
  @Override
  public boolean accept(SBase sbase) {
    return sbase instanceof Dimension;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.IdManager#register(org.sbml.jsbml.SBase)
   */
  @Override
  public boolean register(SBase sbase) {

    boolean success = true;

    if (sbase instanceof Dimension) {
      Dimension dimension = (Dimension) sbase;

      if (dimension.isSetId()) {
        String id = dimension.getId();

        if (mapOfDimensions == null) {
          mapOfDimensions = new HashMap<String, Dimension>();
        }

        if (mapOfDimensions.containsKey(id)) {
          String elementName = null;
          String elementId = null;

          if (isSetExtendedSBase()) {
            elementName = getExtendedSBase().getElementName();

            if (getExtendedSBase() instanceof NamedSBase) {
              elementId = "id[" + ((NamedSBase) getExtendedSBase()).getId() + "]";
            } else if (getExtendedSBase().isSetMetaId()) {
              elementId = "metaid[" + getExtendedSBase().getMetaId() + "]";
            }
          }

          if (mapOfDimensions.get(id) == sbase) {
            // Allow to call several times the register method on the same object. Happens when the SBasePlugin is created before being added to the SBMLDocument
            success = true;
          } else {
            logger.error(MessageFormat.format(
              "A Dimension with the id \"{0}\" is already present in this {1}{2}. The new element will not be added to the model.",
              id, elementName, elementId != null ? " identified by " + elementId : ""));

            success = false;
          }
        } else {
          mapOfDimensions.put(id, dimension);

          if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("registered Dimension id=\"{0}\" in {1}",
              id, (isSetExtendedSBase() ? getExtendedSBase().getElementName() : "")));
          }
        }
      }
    } else {
      logger.error(MessageFormat.format(
        "Trying to register something that is not a Dimension: \"{0}\".", sbase));
    }

    // TODO : register all the Port children if any !!

    return success;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.util.IdManager#unregister(org.sbml.jsbml.SBase)
   */
  @Override
  public boolean unregister(SBase sbase) {

    // Always returning true at the moment to avoid exception when unregistering element
    boolean success = true;

    if (sbase instanceof Dimension) {
      Dimension dimension = (Dimension) sbase;

      if (dimension.isSetId()) {
        String id = dimension.getId();

        if (mapOfDimensions == null) {
          logger.warn(MessageFormat.format(
            "No Dimension have been registered in this {0}. Nothing to be done.",
            (isSetExtendedSBase() ? getExtendedSBase().getElementName() : "")));
          return success;
        }

        if (mapOfDimensions.containsKey(id)) {
          mapOfDimensions.remove(id);
          if (logger.isDebugEnabled()) {
            logger.debug(MessageFormat.format("unregistered Dimension id={0} in {1}",
              id, (isSetExtendedSBase() ? getExtendedSBase().getElementName() : "")));
          }
        } else {

          String elementName = null;
          String elementId = null;

          if (isSetExtendedSBase()) {
            elementName = getExtendedSBase().getElementName();

            if (getExtendedSBase() instanceof NamedSBase) {
              elementId = "id[" + ((NamedSBase) getExtendedSBase()).getId() + "]";
            } else if (getExtendedSBase().isSetMetaId()) {
              elementId = "metaid[" + getExtendedSBase().getMetaId() + "]";
            }
          }
          logger.warn(MessageFormat.format(
            "A Dimension with the id \"{0}\" is not present in this {1}{2}. Nothing to be done.",
            id, elementName, elementId != null ? " identified by " + elementId : ""));
        }
      }
    } else {
      logger.error(MessageFormat.format(
        "Trying to unregister something that is not a Dimension: \"{0}\".", sbase));
    }

    // TODO : unregister all the Dimension children if any !!

    return success;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 2719;
    int result = super.hashCode();
    result = prime * result
        + ((listOfDimensions == null) ? 0 : listOfDimensions.hashCode());
    result = prime * result
        + ((listOfIndices == null) ? 0 : listOfIndices.hashCode());
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
    ArraysSBasePlugin other = (ArraysSBasePlugin) obj;
    if (listOfDimensions == null) {
      if (other.listOfDimensions != null) {
        return false;
      }
    } else if (!listOfDimensions.equals(other.listOfDimensions)) {
      return false;
    }
    if (listOfIndices == null) {
      if (other.listOfIndices != null) {
        return false;
      }
    } else if (!listOfIndices.equals(other.listOfIndices)) {
      return false;
    }
    return true;
  }

}
