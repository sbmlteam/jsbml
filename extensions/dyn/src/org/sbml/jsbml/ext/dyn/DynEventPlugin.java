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
 * 6. Boston University, Boston, MA, USA
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.dyn;

import java.text.MessageFormat;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.Event;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Harold G&oacute;mez
 * @since 1.0
 */
public class DynEventPlugin extends DynSBasePlugin {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 3183158090508499169L;

  /**
   * Indicates whether all model components are affected by the dynEvent
   */
  private Boolean applyToAll;

  /**
   * List of model components affected by the dynEvent
   */
  protected ListOf<DynElement> listOfDynElements;

  /**
   * Empty constructor
   */
  public DynEventPlugin() {
    super();
    initDefaults();
  }

  /**
   * Constructor
   * 
   * @param event
   */
  public DynEventPlugin(Event event) {
    super(event);
    initDefaults();
  }

  /**
   * Constructor
   * 
   * @param dynEventPlugin
   */
  public DynEventPlugin(DynEventPlugin dynEventPlugin) {
    super(dynEventPlugin);

    if (dynEventPlugin.isSetListOfDynElements()) {
      setListOfDynElements(dynEventPlugin.getListOfDynElements().clone());
    }
    if (dynEventPlugin.isSetApplyToAll()) {
      setApplyToAll(new Boolean(dynEventPlugin.getApplyToAll()));
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.dyn.DynSBasePlugin#initDefaults()
   */
  @Override
  public void initDefaults() {
    applyToAll = false;
  }

  /**
   * @return applyToAll
   */
  public boolean isApplyToAll() {
    return getApplyToAll();
  }

  /**
   * Returns the value of applyToAll
   * 
   * @return the value of applyToAll
   */
  public boolean getApplyToAll() {
    if (isSetApplyToAll()) {
      return applyToAll;
    }
    return false;
  }

  /**
   * Returns whether applyToAll is set
   * 
   * @return whether applyToAll is set
   */
  public boolean isSetApplyToAll() {
    return applyToAll != null && applyToAll != false;
  }

  /**
   * Sets the value of applyToAll
   * @param applyToAll
   */
  public void setApplyToAll(boolean applyToAll) {
    Boolean oldApplyToAll = this.applyToAll;
    this.applyToAll = applyToAll;
    firePropertyChange(DynConstants.applyToAll, oldApplyToAll,
      this.applyToAll);
  }

  /**
   * Unsets the applyToAll field
   * 
   * @return {@code true}, if applyToAll was set before, otherwise
   *         {@code false}
   */
  public boolean unsetApplyToAll() {
    if (isSetApplyToAll()) {
      boolean oldApplyToAll = applyToAll;
      applyToAll = false;
      firePropertyChange(DynConstants.applyToAll, oldApplyToAll,
        applyToAll);
      return true;
    }
    return false;
  }

  /**
   * Returns the value of listOfDynElements
   * 
   * @return the value of listOfDynElements
   */
  public ListOf<DynElement> getListOfDynElements() {
    if (listOfDynElements == null) {
      listOfDynElements = new ListOf<DynElement>();
      listOfDynElements.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'dyn'
      listOfDynElements.setPackageName(null);
      listOfDynElements.setPackageName(DynConstants.shortLabel);
      listOfDynElements.setSBaseListType(ListOf.Type.other);

      extendedSBase.registerChild(listOfDynElements);
    }
    return listOfDynElements;
  }

  /**
   * Returns whether listOfDynElements is set
   * 
   * @return whether listOfDynElements is set
   */
  public boolean isSetListOfDynElements() {
    if ((listOfDynElements == null) || listOfDynElements.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * Sets the value of listOfDynElements
   * @param listOfDynElements
   */
  public void setListOfDynElements(ListOf<DynElement> listOfDynElements) {
    unsetListOfDynElements();

    this.listOfDynElements = listOfDynElements;

    if (this.listOfDynElements != null) {
      listOfDynElements.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'dyn'
      listOfDynElements.setPackageName(null);
      listOfDynElements.setPackageName(DynConstants.shortLabel);
      this.listOfDynElements.setSBaseListType(ListOf.Type.other);
    }
    if (isSetExtendedSBase()) {
      extendedSBase.registerChild(listOfDynElements);
    }
  }

  /**
   * Unsets the variable listOfDynElements
   * 
   * @return {@code true}, if listOfDynElements was set before, otherwise
   *         {@code false}
   */
  public boolean unsetListOfDynElements() {
    if (listOfDynElements == null) {
      ListOf<DynElement> oldListOfDynElements = listOfDynElements;
      listOfDynElements = null;
      firePropertyChange(DynConstants.listOfDynElements,
        oldListOfDynElements, listOfDynElements);
      return true;
    }
    return false;
  }

  /**
   * Creates a new DynElement element and adds it to the listOfDynElements
   * list
   * @return
   */
  public DynElement createDynElement() {
    return createDynElement(null);
  }

  /**
   * Creates a new {@link DynElement} element and adds it to the
   * {@link #getListOfDynElements()}.
   * 
   * @param id
   * @return a new {@link DynElement} element
   */
  public DynElement createDynElement(String id) {
    DynElement dynElement = new DynElement(id, null, getLevel(), getVersion());
    addDynElement(dynElement);
    return dynElement;
  }

  /**
   * Adds a new {@link DynElement} to the listOfDynElements. The
   * listOfDynElements is initialized if necessary.
   *
   * @param dynElement
   *            the element to add to the list
   * @return {@code true} (as specified by {@link Collection#add})
   */
  public boolean addDynElement(DynElement dynElement) {
    return getListOfDynElements().add(dynElement);
  }

  /**
   * Removes the ith element from the listOfDynElements
   * 
   * @param i
   */
  public void removeDynElement(int i) {
    if (!isSetListOfDynElements()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfDynElements().remove(i);
  }

  /**
   * Removes an element from the listOfDynElements
   *
   * @param dynElement
   *            the element to be removed from the list
   * @return {@code true} if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeDynElement(DynElement dynElement) {
    if (isSetListOfDynElements()) {
      return getListOfDynElements().remove(dynElement);
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.dyn.DynSBasePlugin#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.dyn.DynSBasePlugin#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int childIndex) {
    if (childIndex < 0 || childIndex >= 1) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexExceedsBoundsException"),
        childIndex, +Math.min(getChildCount(), 0)));
    }

    return listOfDynElements;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.dyn.DynSBasePlugin#getChildCount()
   */
  @Override
  public int getChildCount() { // TODO - will not write the ListOf if it is empty
    return isSetListOfDynElements() ? 1 : 0;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.dyn.DynSBasePlugin#equals(java.lang.Object)
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
    DynEventPlugin other = (DynEventPlugin) obj;
    if (applyToAll != other.applyToAll) {
      return false;
    }
    if (listOfDynElements == null) {
      if (other.listOfDynElements != null) {
        return false;
      }
    } else if (!listOfDynElements.equals(other.listOfDynElements)) {
      return false;
    }
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.dyn.DynSBasePlugin#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3371;
    int hashCode = super.hashCode();
    hashCode = prime
        * hashCode
        + ((listOfDynElements == null) ? 0 : listOfDynElements
          .hashCode());
    if (isSetApplyToAll()) {
      hashCode += prime * applyToAll.hashCode();
    }
    return hashCode;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.dyn.DynSBasePlugin#clone()
   */
  @Override
  public DynEventPlugin clone() {
    return new DynEventPlugin(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.dyn.DynSBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetApplyToAll()) {
      attributes.put(DynConstants.shortLabel + ":applyToAll",
        String.valueOf(applyToAll));
    }
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.dyn.DynSBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value) {

    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead && attributeName.equals(DynConstants.applyToAll)) {
      try {
        setApplyToAll(StringTools.parseSBMLBoolean(value));
        isAttributeRead = true;
      } catch (Exception e) {
        MessageFormat.format(
          DynConstants.bundle.getString("COULD_NOT_READ_EVENT"), value,
          DynConstants.applyToAll);
        e.printStackTrace();
      }
    }

    return isAttributeRead;
  }

}
