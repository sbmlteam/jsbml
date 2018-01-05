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
 * 
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation. A copy of the license agreement is provided
 * in the file named "LICENSE.txt" included with this software distribution
 * and also available online as <http://sbml.org/Software/JSBML/License>.
 * ----------------------------------------------------------------------------
 */
package org.sbml.jsbml.ext.req;

import java.text.MessageFormat;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * Represents the extended {@link SBase} as define in the req package.
 * <p>
 * The Required Elements package is an exceedingly small package that allows
 * model writers to declare specifically which components of the model have had
 * their mathematics changed, by which package or annotation, and whether
 * interpretation of those components in the absence of any package information
 * results in a workable model. It accomplishes this by defining a class that
 * can be added as an optional child of any SBML element with mathematical
 * meaning.
 * <p>
 * To define that an {@link SBase} can have some optional {@link ChangedMath}
 * children.
 * 
 * @author Nicolas Rodriguez
 * @since 1.0
 */
public class ReqSBasePlugin extends AbstractSBasePlugin {

  /**
   * Generated serial version identifier.
   */
  private static final long serialVersionUID = -8899476363934112466L;

  /**
   * Creates an {@link ReqSBasePlugin} instance
   */
  public ReqSBasePlugin() {
    super();
    initDefaults();
  }

  /**
   * Creates a {@link ReqSBasePlugin} instance associated with the given {@link SBase}.
   * 
   * @param sbase
   */
  public ReqSBasePlugin(SBase sbase) {
    super(sbase);
    initDefaults();
  }


  /**
   * Clone constructor
   * 
   * @param obj
   */
  public ReqSBasePlugin(ReqSBasePlugin obj) {
    super(obj);

    if (obj.isSetListOfChangedMaths()) {
      setListOfChangedMaths(obj.getListOfChangedMaths().clone());
    }
  }

  /**
   * clones this class
   */
  @Override
  public ReqSBasePlugin clone() {
    return new ReqSBasePlugin(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
  }



  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPackageName()
   */
  @Override
  public String getPackageName() {
    return ReqConstants.shortLabel;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getPrefix()
   */
  @Override
  public String getPrefix() {
    return ReqConstants.shortLabel;
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


  /**
   * Returns {@code true}, if listOfChangedMaths contains at least one element.
   *
   * @return {@code true}, if listOfChangedMaths contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfChangedMaths() {
    if ((listOfChangedMaths == null) || listOfChangedMaths.isEmpty()) {
      return false;
    }

    return true;
  }

  /**
   * Returns the listOfChangedMaths. Creates it if it is not already existing.
   *
   * @return the listOfChangedMaths
   */
  public ListOf<ChangedMath> getListOfChangedMaths() {
    if (!isSetListOfChangedMaths()) {
      listOfChangedMaths = new ListOf<ChangedMath>();

      listOfChangedMaths.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'req'
      listOfChangedMaths.setPackageName(null);
      listOfChangedMaths.setPackageName(ReqConstants.shortLabel);
      listOfChangedMaths.setSBaseListType(ListOf.Type.other);

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(listOfChangedMaths);
      }
    }

    return listOfChangedMaths;
  }

  /**
   * Sets the given {@code ListOf<ChangedMath>}. If listOfChangedMaths
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfChangedMaths
   */
  public void setListOfChangedMaths(ListOf<ChangedMath> listOfChangedMaths) {
    unsetListOfChangedMaths();
    this.listOfChangedMaths = listOfChangedMaths;

    if (listOfChangedMaths != null) {
      listOfChangedMaths.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'req'
      listOfChangedMaths.setPackageName(null);
      listOfChangedMaths.setPackageName(ReqConstants.shortLabel);
      listOfChangedMaths.setSBaseListType(ListOf.Type.other); // Just in case

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(this.listOfChangedMaths);
      }
    }
  }

  /**
   * Removes all of the elements from this list of {@link ChangedMath}s.
   *
   * @return {@code true}, if listOfChangedMaths contained at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfChangedMaths() {
    if (isSetListOfChangedMaths()) {
      ListOf<ChangedMath> oldMathChangeds = listOfChangedMaths;
      listOfChangedMaths = null;
      oldMathChangeds.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Adds a new {@link ChangedMath} to the listOfChangedMaths.
   * <p>The listOfChangedMaths is initialized if necessary.
   *
   * @param changedMath the element to add to the list
   * @return true (as specified by {@link Collection#add})
   */
  public boolean addChangedMath(ChangedMath changedMath) {
    return getListOfChangedMaths().add(changedMath);
  }

  /**
   * Removes an element from the listOfChangedMaths.
   *
   * @param changedMath the element to be removed from the list
   * @return true if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeChangedMath(ChangedMath changedMath) {
    if (isSetListOfChangedMaths()) {
      return getListOfChangedMaths().remove(changedMath);
    }
    return false;
  }

  /**
   * Removes an element from the listOfChangedMaths at the given index.
   *
   * @param i - the index where to remove the {@link ChangedMath}
   * @throws IndexOutOfBoundsException - if the listOf is not set or
   * if the index is out of bound (index &lt; 0 || index &gt; list.size).
   */
  public void removeChangedMath(int i) {
    if (!isSetListOfChangedMaths()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfChangedMaths().remove(i);
  }

  /**
   * Removes an element from the listOfChangedMaths with the given id.
   *
   * @param id - the id of the {@link ChangedMath} to be removed
   */
  public void removeChangedMath(String id) {
    getListOfChangedMaths().removeFirst(new NameFilter(id));
  }

  /**
   * Creates a new ChangedMath element and adds it to the ListOfMathChangeds list
   * @return
   */
  public ChangedMath createChangedMath() {
    return createChangedMath(null);
  }

  /**
   * Creates a new {@link ChangedMath} element and adds it to the ListOfMathChangeds list
   * 
   * @param id
   * @return a new {@link ChangedMath} element
   */
  public ChangedMath createChangedMath(String id) {
    ChangedMath changedMath = new ChangedMath(id);
    addChangedMath(changedMath);
    return changedMath;
  }

  /**
   * 
   */
  private ListOf<ChangedMath> listOfChangedMaths;

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = 0;

    if (isSetListOfChangedMaths()) {
      count++;
    }

    return count;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }

    int pos = 0;

    if (isSetListOfChangedMaths()) {
      if (pos == index) {
        return getListOfChangedMaths();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }

  // TODO - support the old attributes from the first draft specs ??
  // req:mathOverridden="http://www.sbml.org/sbml/level1/distrib/level1"
  // req:coreHasAlternateMath=true>
  // And what to do with them ?

}
