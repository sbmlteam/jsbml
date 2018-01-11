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

import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.layout.Layout;
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * @author Jakob Matthes
 * @author rodrigue
 * @since 1.0
 */
public class RenderLayoutPlugin extends AbstractRenderPlugin {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 6636572993878851570L;
  /**
   * 
   */
  private ListOfLocalRenderInformation listOfLocalRenderInformation;

  /**
   * Creates a RenderLayoutPlugin instance
   * @param layout
   */
  public RenderLayoutPlugin(Layout layout) {
    super(layout);
    initDefaults();
  }


  /**
   * Clone constructor
   * @param obj
   */
  public RenderLayoutPlugin(RenderLayoutPlugin obj) {
    super(obj);

    if (obj.isSetListOfLocalRenderInformation()) {
      setListOfLocalRenderInformation(obj.getListOfLocalRenderInformation().clone());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.AbstractRenderPlugin#clone()
   */
  @Override
  public RenderLayoutPlugin clone() {
    return new RenderLayoutPlugin(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.AbstractRenderPlugin#initDefaults()
   */
  @Override
  public void initDefaults() {
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.AbstractRenderPlugin#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.AbstractRenderPlugin#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetListOfLocalRenderInformation()) {
      count++;
    }
    return count;
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
    if (isSetListOfLocalRenderInformation()) {
      if (pos == childIndex) {
        return getListOfLocalRenderInformation();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), childIndex,
      Math.min(pos, 0)));
  }

  /**
   * @return {@code true}, if listOfLocalRenderInformation contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfLocalRenderInformation() {
    if ((listOfLocalRenderInformation == null) || listOfLocalRenderInformation.isEmpty()) {
      return false;
    }
    return true;
  }

  /**
   * @return the listOfLocalRenderInformation
   */
  public ListOfLocalRenderInformation getListOfLocalRenderInformation() {
    if (!isSetListOfLocalRenderInformation()) {
      listOfLocalRenderInformation = new ListOfLocalRenderInformation();

      if (isSetExtendedSBase()) {
        extendedSBase.registerChild(listOfLocalRenderInformation);
      }
    }
    return listOfLocalRenderInformation;
  }

  /**
   *
   * @param i
   * @return
   */
  public LocalRenderInformation getLocalRenderInformation(int i) {
    return getListOfLocalRenderInformation().get(i);
  }

  /**
   * @param listOfLocalRenderInformation
   */
  public void setListOfLocalRenderInformation(ListOfLocalRenderInformation listOfLocalRenderInformation) {
    unsetListOfLocalRenderInformation();
    this.listOfLocalRenderInformation = listOfLocalRenderInformation;

    if (isSetExtendedSBase()) {
      extendedSBase.registerChild(this.listOfLocalRenderInformation);
    }
  }

  /**
   * @return {@code true}, if listOfLocalRenderInformation contained at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfLocalRenderInformation() {
    if (isSetListOfLocalRenderInformation()) {
      ListOfLocalRenderInformation oldLocalRenderInformation = listOfLocalRenderInformation;
      listOfLocalRenderInformation = null;
      oldLocalRenderInformation.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * @param field
   * @return
   */
  public boolean addLocalRenderInformation(LocalRenderInformation field) {
    return getListOfLocalRenderInformation().add(field);
  }

  /**
   * @param field
   * @return
   */
  public boolean removeLocalRenderInformation(LocalRenderInformation field) {
    if (isSetListOfLocalRenderInformation()) {
      return getListOfLocalRenderInformation().remove(field);
    }
    return false;
  }

  /**
   * @param i
   */
  public void removeLocalRenderInformation(int i) {
    if (!isSetListOfLocalRenderInformation()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfLocalRenderInformation().remove(i);
  }

  /**
   *
   * @param id
   */
  public void removeLocalRenderInformation(String id) {
    getListOfLocalRenderInformation().removeFirst(new NameFilter(id));
  }

  /**
   * Creates a new LocalRenderInformation element and adds it to the ListOfLocalRenderInformation list
   * 
   * <p><b>NOTE:</b>
   * only use this method, if ID is not mandatory in LocalRenderInformation
   * otherwise use @see createLocalRenderInformation(String id)!</p>
   * @return
   */
  public LocalRenderInformation createLocalRenderInformation() {
    return createLocalRenderInformation(null);
  }

  /**
   * Creates a new LocalRenderInformation element and adds it to the ListOfLocalRenderInformation list
   * 
   * @param id
   * @return
   */
  public LocalRenderInformation createLocalRenderInformation(String id) {
    LocalRenderInformation field = new LocalRenderInformation(id);
    addLocalRenderInformation(field);
    return field;
  }

}
