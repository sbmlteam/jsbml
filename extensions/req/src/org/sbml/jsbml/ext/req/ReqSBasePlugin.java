/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2014 jointly by the following organizations:
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
import java.util.Collection;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.AbstractSBasePlugin;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * 
 * @author Nicolas Rodriguez
 * @version $Rev$
 * @since 1.0
 */
public class ReqSBasePlugin extends AbstractSBasePlugin {

  /**
   * Creates an {@link ReqSBasePlugin} instance 
   */
  public ReqSBasePlugin() {
    super();
    initDefaults();
  }

  /**
   * Creates a ReqSBasePlugin instance with a level and version. 
   * 
   * @param level
   * @param version
   */
  public ReqSBasePlugin(SBase sbase) {
    super(sbase);
  }


  /**
   * Clone constructor
   */
  public ReqSBasePlugin(ReqSBasePlugin obj) {
    super(obj);

    if (obj.isSetListOfMathChangeds()) {
      for (MathChanged mathChanged : obj.getListOfMathChangeds()) {
        addMathChanged(mathChanged.clone());
      }
    }
  }
  
  /**
   * clones this class
   */
  public ReqSBasePlugin clone() {
    return new ReqSBasePlugin(this);
  }

  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    // TODO : get the correct namespace from the SBMLdocument, otherwise don't set it yet.
  }


  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.SBasePlugin#getElementNamespace()
   */
  @Override
  public String getElementNamespace() {
    return ReqConstants.namespaceURI;
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
    return ReqConstants.namespaceURI;
  }

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  
  /**
   * Returns {@code true}, if listOfMathChangeds contains at least one element.
   *
   * @return {@code true}, if listOfMathChangeds contains at least one element, 
   *         otherwise {@code false}
   */
  public boolean isSetListOfMathChangeds() {
    if ((listOfMathChangeds == null) || listOfMathChangeds.isEmpty()) {
      return false;
    }
    
    return true;
  }

  /**
   * Returns the listOfMathChangeds. Creates it if it is not already existing.
   *
   * @return the listOfMathChangeds
   */
  public ListOf<MathChanged> getListOfMathChangeds() {
    if (!isSetListOfMathChangeds()) {
      listOfMathChangeds = new ListOf<MathChanged>(extendedSBase.getLevel(),
          extendedSBase.getVersion());
      // TODO : get the correct namespace from the SBMLdocument, otherwise don't set it yet.
      listOfMathChangeds.setNamespace(ReqConstants.namespaceURI);
      listOfMathChangeds.setSBaseListType(ListOf.Type.other);
      extendedSBase.registerChild(listOfMathChangeds);
    }
    
    return listOfMathChangeds;
  }

  /**
   * Sets the given {@code ListOf<MathChanged>}. If listOfMathChangeds
   * was defined before and contains some elements, they are all unset.
   *
   * @param listOfMathChangeds
   */
  public void setListOfMathChangeds(ListOf<MathChanged> listOfMathChangeds) {
    unsetListOfMathChangeds();
    this.listOfMathChangeds = listOfMathChangeds;
    extendedSBase.registerChild(this.listOfMathChangeds);
  }

  /**
   * Removes all of the elements from this list of {@link MathChanged}s.
   *
   * @return {@code true}, if listOfMathChangeds contained at least one element, 
   *         otherwise {@code false}
   */
  public boolean unsetListOfMathChangeds() {
    if (isSetListOfMathChangeds()) {
      ListOf<MathChanged> oldMathChangeds = this.listOfMathChangeds;
      this.listOfMathChangeds = null;
      oldMathChangeds.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * Adds a new {@link MathChanged} to the listOfMathChangeds.
   * <p>The listOfMathChangeds is initialized if necessary.
   *
   * @param mathChanged the element to add to the list
   * @return true (as specified by {@link Collection.add})
   */
  public boolean addMathChanged(MathChanged mathChanged) {
    return getListOfMathChangeds().add(mathChanged);
  }

  /**
   * Removes an element from the listOfMathChangeds.
   *
   * @param mathChanged the element to be removed from the list
   * @return true if the list contained the specified element
   * @see List#remove(Object)
   */
  public boolean removeMathChanged(MathChanged mathChanged) {
    if (isSetListOfMathChangeds()) {
      return getListOfMathChangeds().remove(mathChanged);
    }
    return false;
  }

  /**
   * Removes an element from the listOfMathChangeds at the given index.
   *
   * @param i - the index where to remove the {@link MathChanged}
   * @throws IndexOutOfBoundsException - if the listOf is not set or
   * if the index is out of bound (index < 0 || index > list.size)
   */
  public void removeMathChanged(int i) {
    if (!isSetListOfMathChangeds()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfMathChangeds().remove(i);
  }

  /**
   * Removes an element from the listOfMathChangeds with the given id.
   *
   * @param id - the id of the {@link MathChanged} to be removed
   */
  public void removeMathChanged(String id) {
    getListOfMathChangeds().removeFirst(new NameFilter(id));
  }

  /**
   * Creates a new MathChanged element and adds it to the ListOfMathChangeds list
   */
  public MathChanged createMathChanged() {
    return createMathChanged(null);
  }

  /**
   * Creates a new {@link MathChanged} element and adds it to the ListOfMathChangeds list
   *
   * @return a new {@link MathChanged} element
   */
  public MathChanged createMathChanged(String id) {
    MathChanged mathChanged = new MathChanged(id, getLevel(), getVersion());
    addMathChanged(mathChanged);
    return mathChanged;
  }


  /**
   * 
   */
  private ListOf<MathChanged> listOfMathChangeds;
  
  

  /* (non-Javadoc)
   * @see javax.swing.tree.TreeNode#getChildCount()
   */
  public int getChildCount() {
    int count = 0;

    if (isSetListOfMathChangeds()) {
      count++;
    }

    return count;
  }

  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(index + " < 0");
    }

    int pos = 0;

    if (isSetListOfMathChangeds()) {
      if (pos == index) {
        return getListOfMathChangeds();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
        "Index {0,number,integer} >= {1,number,integer}", index,
        +((int) Math.min(pos, 0))));
  }


  
}
