/*
 *
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
package org.sbml.jsbml.ext.render;

import java.text.MessageFormat;

import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * 
 * @author Jakob Matthes
 * @author rodrigue
 * @since 1.0
 */
public class RenderListOfLayoutsPlugin extends AbstractRenderPlugin {

  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = -4727110538908666931L;

  /**
   *
   */
  private ListOfGlobalRenderInformation listOfGlobalRenderInformation;

  /**
   * Creates an RenderModelPlugin instance
   * @param listOfLayouts
   */
  public RenderListOfLayoutsPlugin(ListOf<Layout> listOfLayouts) {
    super(listOfLayouts);
    initDefaults();
  }

  /**
   * Clone constructor
   * @param obj
   */
  public RenderListOfLayoutsPlugin(RenderListOfLayoutsPlugin obj) {
    super(obj);

    if (obj.isSetListOfGlobalRenderInformation()) {
      setListOfGlobalRenderInformation(obj.getListOfGlobalRenderInformation().clone());
    }
  }

  /**
   * @param field
   * @return
   */
  public boolean addGlobalRenderInformation(GlobalRenderInformation field) {
    return getListOfGlobalRenderInformation().add(field);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.AbstractRenderPlugin#clone()
   */
  @Override
  public RenderListOfLayoutsPlugin clone() {
    return new RenderListOfLayoutsPlugin(this);
  }

  /**
   * create a new GlobalRenderInformation element and adds it to the ListOfGlobalRenderInformation list
   * <p><b>NOTE:</b>
   * only use this method, if ID is not mandatory in GlobalRenderInformation
   * otherwise use @see createGlobalRenderInformation(String id)!</p>
   * @return
   */
  public GlobalRenderInformation createGlobalRenderInformation() {
    return createGlobalRenderInformation(null);
  }

  /**
   * create a new GlobalRenderInformation element and adds it to the ListOfGlobalRenderInformation list
   * @param id
   * @return
   */
  public GlobalRenderInformation createGlobalRenderInformation(String id) {
    GlobalRenderInformation field = new GlobalRenderInformation(id);
    addGlobalRenderInformation(field);
    return field;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.AbstractRenderPlugin#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.AbstractRenderPlugin#getChildAt(int)
   */
  @Override
  public SBase getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(resourceBundle.getString("IndexSurpassesBoundsException"), childIndex, 0));
    }
    int pos = 0;
    if (isSetListOfGlobalRenderInformation()) {
      if (pos == childIndex) {
        return getListOfGlobalRenderInformation();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), childIndex,
      Math.min(pos, 0)));
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.AbstractRenderPlugin#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetListOfGlobalRenderInformation()) {
      count++;
    }
    return count;
  }

  /**
   * @return the listOfGlobalRenderInformation
   */
  public ListOfGlobalRenderInformation getListOfGlobalRenderInformation() {
    if (!isSetListOfGlobalRenderInformation()) {
      listOfGlobalRenderInformation = new ListOfGlobalRenderInformation();

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(listOfGlobalRenderInformation);
      }
    }
    return listOfGlobalRenderInformation;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.AbstractRenderPlugin#initDefaults()
   */
  @Override
  public void initDefaults() {
  }

  /**
   * @return {@code true}, if listOfGlobalRenderInformation contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfGlobalRenderInformation() {
    if (listOfGlobalRenderInformation == null) {
      return false;
    }
    return true;
  }

  /**
   * @param field
   * @return
   */
  public boolean removeGlobalRenderInformation(GlobalRenderInformation field) {
    if (isSetListOfGlobalRenderInformation()) {
      return getListOfGlobalRenderInformation().remove(field);
    }
    return false;
  }

  /**
   * @param i
   */
  public void removeGlobalRenderInformation(int i) {
    if (!isSetListOfGlobalRenderInformation()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfGlobalRenderInformation().remove(i);
  }

  /**
   *
   * @param id
   */
  public void removeGlobalRenderInformation(String id) {
    getListOfGlobalRenderInformation().removeFirst(new NameFilter(id));
  }


  /**
   * @param listOfGlobalRenderInformation
   */
  public void setListOfGlobalRenderInformation(ListOfGlobalRenderInformation listOfGlobalRenderInformation) {
    unsetListOfGlobalRenderInformation();
    this.listOfGlobalRenderInformation = listOfGlobalRenderInformation;

    if (isSetExtendedSBase()) {
      extendedSBase.registerChild(listOfGlobalRenderInformation);
    }
  }

  /**
   * @return {@code true}, if listOfGlobalRenderInformation contained at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfGlobalRenderInformation() {
    if (isSetListOfGlobalRenderInformation()) {
      ListOfGlobalRenderInformation oldGlobalRenderInformation = listOfGlobalRenderInformation;
      listOfGlobalRenderInformation = null;
      oldGlobalRenderInformation.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

}
