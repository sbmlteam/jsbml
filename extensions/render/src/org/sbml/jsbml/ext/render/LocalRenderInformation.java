/*
 * $Id$
 * $URL$
 *
 * ---------------------------------------------------------------------------- 
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML> 
 * for the latest version of JSBML and more information about SBML. 
 * 
 * Copyright (C) 2009-2012 jointly by the following organizations: 
 * 1. The University of Tuebingen, Germany 
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK 
 * 3. The California Institute of Technology, Pasadena, CA, USA 
 * 
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation. A copy of the license agreement is provided 
 * in the file named "LICENSE.txt" included with this software distribution 
 * and also available online as <http://sbml.org/Software/JSBML/License>. 
 * ---------------------------------------------------------------------------- 
 */ 
package org.sbml.jsbml.ext.render;

import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;


/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 08.05.2012
 */
public class LocalRenderInformation extends RenderInformationBase {
  /**
   * 
   */
  private static final long serialVersionUID = -8056565578647428405L;
  
  private ListOf<LocalStyle> listOfLocalStyles;
  
  /**
   * Creates an LocalRenderInformation instance 
   */
  public LocalRenderInformation() {
    super();
    initDefaults();
  }


  /**
   * Creates a LocalRenderInformation instance with an id. 
   * 
   * @param id
   */
  public LocalRenderInformation(String id) {
    super(id);
    initDefaults();
  }


  /**
   * Creates a LocalRenderInformation instance with a level and version. 
   * 
   * @param level
   * @param version
   */
  public LocalRenderInformation(int level, int version) {
    this(null, null, level, version);
  }


  /**
   * Creates a LocalRenderInformation instance with an id, level, and version. 
   * 
   * @param id
   * @param level
   * @param version
   */
  public LocalRenderInformation(String id, int level, int version) {
    this(id, null, level, version);
  }


  /**
   * Creates a LocalRenderInformation instance with an id, name, level, and version. 
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public LocalRenderInformation(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(Integer.valueOf(MIN_SBML_LEVEL),
      Integer.valueOf(MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public LocalRenderInformation(LocalRenderInformation obj) {
    super(obj);
    this.listOfLocalStyles = obj.listOfLocalStyles;
  }


  /**
   * clones this class
   */
  public LocalRenderInformation clone() {
    return new LocalRenderInformation(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    //TODO
    //addNamespace(constant_class.namespaceURI);
    this.listOfLocalStyles = null;
  }
  
  
  /**
   * @return <code>true</code>, if listOfLocalStyles contains at least one element, 
   *         otherwise <code>false</code>
   */
  public boolean isSetListOfLocalStyles() {
    if ((listOfLocalStyles == null) || listOfLocalStyles.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * @return the listOfLocalStyles
   */
  public ListOf<LocalStyle> getListOfLocalStyles() {
    if (!isSetListOfLocalStyles()) {
      listOfLocalStyles = new ListOf<LocalStyle>(getLevel(), getVersion());
      //TODO
      //listOfLocalStyles.addNamespace(constant_class.namespaceURI);
      listOfLocalStyles.setSBaseListType(ListOf.Type.other);
      registerChild(listOfLocalStyles);
    }
    return listOfLocalStyles;
  }


  /**
   * @param listOfLocalStyles
   */
  public void setListOfLocalStyles(ListOf<LocalStyle> listOfLocalStyles) {
    unsetListOfLocalStyles();
    this.listOfLocalStyles = listOfLocalStyles;
    registerChild(this.listOfLocalStyles);
  }


  /**
   * @return <code>true</code>, if listOfLocalStyles contained at least one element, 
   *         otherwise <code>false</code>
   */
  public boolean unsetListOfLocalStyles() {
    if (isSetListOfLocalStyles()) {
      ListOf<LocalStyle> oldLocalStyles = this.listOfLocalStyles;
      this.listOfLocalStyles = null;
      oldLocalStyles.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * @param localStyle
   */
  public boolean addLocalStyle(LocalStyle localStyle) {
    return getListOfLocalStyles().add(localStyle);
  }


  /**
   * @param localStyle
   */
  public boolean removeLocalStyle(LocalStyle localStyle) {
    if (isSetListOfLocalStyles()) {
      return getListOfLocalStyles().remove(localStyle);
    }
    return false;
  }


  /**
   * @param index
   */
  public void removeLocalStyle(int i) {
    if (!isSetListOfLocalStyles()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfLocalStyles().remove(i);
  }


  /**
   * create a new LocalStyle element and adds it to the ListOfLocalStyles list
   */
  //TODO A Style needs a group
  /*
  public LocalStyle createLocalStyle(String id) {
    LocalStyle field = new LocalStyle(id, getLevel(), getVersion());
    addLocalStyle(field);
    return field;
  }
  */

  /**
   * TODO: optionally, create additional create methods with more
   * variables, for instance "bar" variable
   */
  // public LocalStyle createLocalStyle(String id, int bar) {
  //   LocalStyle field = createLocalStyle(id);
  //   field.setBar(bar);
  //   return field;
  // }
  /**
   * 
   */
  // TODO: Move to RenderConstants
  public static final int MIN_SBML_LEVEL = 3;
  public static final int MIN_SBML_VERSION = 1;
}
