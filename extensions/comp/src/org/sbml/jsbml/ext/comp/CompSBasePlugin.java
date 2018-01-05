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
package org.sbml.jsbml.ext.comp;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;

/**
 * Extends the {@link SBase} class by adding an optional ListOf{@link ReplacedElement}s child and a single optional {@link ReplacedBy} child.
 *
 * <p>The {@link CompSBasePlugin} class codifies the extensions to the {@link SBase} class defined in the 'Hierarchical Model Composition' package (comp).
 * These extensions allows the modeler to define one or more {@link Submodel} elements which the parent {@link SBase} object replaces,
 * and/or a single {@link Submodel} element which replaces the parent {@link SBase} object.
 *
 * <br>This is accomplished through the addition of an optional ListOf{@link ReplacedElement}s child, which may contain one or more {@link ReplacedElement}
 * objects, each of which references a {@link Submodel} element to be replaced by the containing {@link SBase} object, and through the addition of a single
 * optional {@link ReplacedBy} child, which references a {@link Submodel} element which is to replace the containing {@link SBase} object.
 *
 * <p>If a single {@link SBase} element both contains a ListOf{@link ReplacedElement}s and has a {@link ReplacedBy} child, it and all the referenced
 * {@link ReplacedElement} objects are to be replaced by the object referenced by the {@link ReplacedBy} element.
 *
 *
 * @author Nicolas Rodriguez
 * @since 1.0
 * @see ReplacedElement
 * @see ReplacedBy
 */
public class CompSBasePlugin extends AbstractSBasePlugin {


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
   */
  @Override
  public String getPackageName() {
    return CompConstants.shortLabel;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPrefix()
   */
  @Override
  public String getPrefix() {
    return CompConstants.shortLabel;
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
  public SBase getParent() {
    if (isSetExtendedSBase()) {
      return (SBase) getExtendedSBase().getParent();
    }

    return null;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.AbstractSBasePlugin#getParentSBMLObject()
   */
  @Override
  public SBase getParentSBMLObject() {
    return getParent();
  }
  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = 3364911411091523856L;

  /**
   * 
   */
  ListOf<ReplacedElement> listOfReplacedElements;

  /**
   * 
   */
  ReplacedBy replacedBy;

  /**
   * Creates an CompSBasePlugin instance
   */
  public CompSBasePlugin() {
    super();
    initDefaults();
  }

  /**
   * Creates a CompSBasePlugin instance with a level and version.
   *
   * @param extendedSBase the core {@link SBase} that is extended.
   */
  public CompSBasePlugin(SBase extendedSBase) {
    super(extendedSBase);
    initDefaults();
  }

  /**
   * Clone constructor
   * 
   * @param obj the instance to clone
   */
  public CompSBasePlugin(CompSBasePlugin obj) {
    super(obj);

    if (obj.isSetListOfReplacedElements()) {
      setListOfReplacedElements(obj.getListOfReplacedElements().clone());
    }
    if (obj.isSetReplacedBy()) {
      setReplacedBy(obj.getReplacedBy().clone());
    }
  }

  /**
   * clones this class
   */
  @Override
  public CompSBasePlugin clone() {
    return new CompSBasePlugin(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {}



  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3359;
    int result = super.hashCode();
    result = prime
        * result
        + ((listOfReplacedElements == null) ? 0
          : listOfReplacedElements.hashCode());
    result = prime * result
        + ((replacedBy == null) ? 0 : replacedBy.hashCode());
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
    CompSBasePlugin other = (CompSBasePlugin) obj;
    if (listOfReplacedElements == null) {
      if (other.listOfReplacedElements != null) {
        return false;
      }
    } else if (!listOfReplacedElements.equals(other.listOfReplacedElements)) {
      return false;
    }
    if (replacedBy == null) {
      if (other.replacedBy != null) {
        return false;
      }
    } else if (!replacedBy.equals(other.replacedBy)) {
      return false;
    }
    return true;
  }


  /**
   * Returns the value of replacedBy
   * 
   * @return the value of replacedBy
   */
  public ReplacedBy getReplacedBy() {
    if (isSetReplacedBy()) {
      return replacedBy;
    }

    return null;
  }

  /**
   * Returns whether replacedBy is set
   * 
   * @return whether replacedBy is set
   */
  public boolean isSetReplacedBy() {
    return replacedBy != null;
  }

  /**
   * Sets the value of the optional replacedBy element.
   * 
   * @param replacedBy the value of replacedBy
   *
   */
  public void setReplacedBy(ReplacedBy replacedBy) {
    ReplacedBy oldReplacedBy = this.replacedBy;
    this.replacedBy = replacedBy;
    if (extendedSBase != null) {
      extendedSBase.registerChild(replacedBy);
    }
    firePropertyChange(CompConstants.replacedBy, oldReplacedBy, this.replacedBy);
  }

  /**
   * Creates a new {@link ReplacedBy} element and sets it in this {@link CompSBasePlugin}.
   *
   * @return a new {@link ReplacedBy} element.
   */
  public ReplacedBy createReplacedBy() {
    ReplacedBy replacedBy = new ReplacedBy();
    setReplacedBy(replacedBy);
    return replacedBy;
  }

  /**
   * Unsets the variable replacedBy
   * @return {@code true}, if replacedBy was set before,
   *         otherwise {@code false}
   */
  public boolean unsetReplacedBy() {
    if (isSetReplacedBy()) {
      ReplacedBy oldReplacedBy = replacedBy;
      replacedBy = null;
      firePropertyChange(CompConstants.replacedBy, oldReplacedBy, replacedBy);
      return true;
    }
    return false;
  }


  /**
   * Returns {@code true}, if listOfReplacedElements contains at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfReplacedElements contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfReplacedElements() {
    if ((listOfReplacedElements == null) || listOfReplacedElements.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * Returns the n-th {@link ReplacedElement} object in this {@link CompSBasePlugin}.
   *
   * @param index an index
   * @return the {@link ReplacedElement} with the given index if it exists.
   * @throws IndexOutOfBoundsException if the index is out of range: (index < 0 || index >= size())
   */
  public ReplacedElement getReplacedElement(int index) {
    return getListOfReplacedElements().get(index);
  }

  /**
   * Returns a {@link ReplacedElement} element that has the given 'id' within
   * this {@link CompSBasePlugin} or {@code null} if no such element can be found.
   *
   * @param id
   *        an id indicating a {@link ReplacedElement} element of the
   *        {@link CompSBasePlugin}.
   * @return a {@link ReplacedElement} element of the {@link CompSBasePlugin} that has
   *         the given 'id' as id or {@code null} if no element with this
   *         'id' can be found.
   */
  public ReplacedElement getReplacedElement(String id) {
    return getListOfReplacedElements().get(id);
  }

  /**
   * Returns the number of {@link ReplacedElement} objects in this {@link CompSBasePlugin}.
   *
   * @return the number of {@link ReplacedElement} objects in this {@link CompSBasePlugin}.
   */
  public int getReplacedElementCount() {
    if (!isSetListOfReplacedElements()) {
      return 0;
    }

    return getListOfReplacedElements().size();
  }

  /**
   * Returns the listOfReplacedElements
   *
   * @return the listOfReplacedElements
   */
  public ListOf<ReplacedElement> getListOfReplacedElements() {
    if (!isSetListOfReplacedElements()) {
      if (extendedSBase != null) {
        listOfReplacedElements = new ListOf<ReplacedElement>(extendedSBase.getLevel(), extendedSBase.getVersion());
      } else {
        listOfReplacedElements = new ListOf<ReplacedElement>();
      }
      listOfReplacedElements.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'comp'
      listOfReplacedElements.setPackageName(null);
      listOfReplacedElements.setPackageName(CompConstants.shortLabel);
      listOfReplacedElements.setSBaseListType(ListOf.Type.other);
      listOfReplacedElements.setOtherListName(CompConstants.listOfReplacedElements);
      
      if (extendedSBase != null) {
        extendedSBase.registerChild(listOfReplacedElements);
      }
    }
    return listOfReplacedElements;
  }

  /**
   * Returns the number of {@link ReplacedElement} of this {@link CompSBasePlugin}.
   *
   * @return the number of {@link ReplacedElement} of this {@link CompSBasePlugin}.
   * @libsbml.deprecated use {@link #getReplacedElementCount()}
   */
  public int getNumReplacedElements() {
    return getReplacedElementCount();
  }

  /**
   * Sets the listOfReplacedElements. If there was already some elements defined
   * on listOfReplacedElements, they will be unset beforehand. If present it must
   * contain at least one {@link ReplacedElement} object.
   *
   * @param listOfReplacedElements the list of {@link ReplacedElement}s.
   */
  public void setListOfReplacedElements(ListOf<ReplacedElement> listOfReplacedElements) {
    unsetListOfReplacedElements();
    this.listOfReplacedElements = listOfReplacedElements;

    if (listOfReplacedElements != null) {
      listOfReplacedElements.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'comp'
      listOfReplacedElements.setPackageName(null);
      listOfReplacedElements.setPackageName(CompConstants.shortLabel);
      listOfReplacedElements.setSBaseListType(ListOf.Type.other);
      listOfReplacedElements.setOtherListName(CompConstants.listOfReplacedElements);
    }
    if (extendedSBase != null) {
      extendedSBase.registerChild(this.listOfReplacedElements);
    }
  }

  /**
   * Returns {@code true}, if listOfReplacedElements contained at least one element,
   *         otherwise {@code false}
   *
   * @return {@code true}, if listOfReplacedElements contained at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfReplacedElements() {
    if (isSetListOfReplacedElements()) {
      ListOf<ReplacedElement> oldReplacedElements = listOfReplacedElements;
      listOfReplacedElements = null;
      oldReplacedElements.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Adds a new element to listOfReplacedElements.
   * <p>listOfReplacedElements is initialized if necessary.
   *
   * @param replacedElement the element to be added to the list
   * @return {@code true} (as specified by {@link Collection#add})
   */
  public boolean addReplacedElement(ReplacedElement replacedElement) {
    return getListOfReplacedElements().add(replacedElement);
  }

  /**
   * Removes an element from listOfReplacedElements.
   *
   * @param replacedElement the element to be removed from the list
   * @return {@code true} if this list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeReplacedElement(ReplacedElement replacedElement) {
    if (isSetListOfReplacedElements()) {
      return getListOfReplacedElements().remove(replacedElement);
    }
    return false;
  }

  /**
   * Removes the ith element from the ListOfReplacedElements.
   *
   * @param i the index of the element to be removed
   * @throws IndexOutOfBoundsException if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size)
   */
  public void removeReplacedElement(int i) {
    if (!isSetListOfReplacedElements()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfReplacedElements().remove(i);
  }


  /**
   * Creates a new {@link ReplacedElement} element and adds it to the ListOfReplacedElements list.
   *
   * @return a new {@link ReplacedElement} element.
   */
  public ReplacedElement createReplacedElement() {
    ReplacedElement replacedElement = new ReplacedElement();
    addReplacedElement(replacedElement);
    return replacedElement;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix,
    String value)
  {
    return false; // no new attributes defined
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes()
  {
    // no new attributes defined
    return new TreeMap<String, String>();
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = 0;

    if (isSetListOfReplacedElements()) {
      count++;
    }
    if (isSetReplacedBy()) {
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

    if (isSetListOfReplacedElements()) {
      if (pos == index) {
        return getListOfReplacedElements();
      }
      pos++;
    }
    if (isSetReplacedBy()) {
      if (pos == index) {
        return getReplacedBy();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }



}
