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

import java.text.MessageFormat;
import java.util.Map;

import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;


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
    if (getLevelAndVersion().compareTo(Integer.valueOf(RenderConstants.MIN_SBML_LEVEL),
      Integer.valueOf(RenderConstants.MIN_SBML_VERSION)) < 0) {
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderInformationBase#clone()
   */
  @Override
  public LocalRenderInformation clone() {
    return new LocalRenderInformation(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderInformationBase#initDefaults()
   */
  @Override
  public void initDefaults() {
    addNamespace(RenderConstants.namespaceURI);
    this.listOfLocalStyles = null;
  }
  
  /**
   * @return {@code true}, if listOfLocalStyles contains at least one element, 
   *         otherwise {@code false}
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
      listOfLocalStyles.addNamespace(RenderConstants.namespaceURI);
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
   * @return {@code true}, if listOfLocalStyles contained at least one element, 
   *         otherwise {@code false}
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
  
  
  @Override
  public int getChildCount() {
    int count = super.getChildCount();
     if (isSetListOfLocalStyles()) {
      count++;
     }
    return count;
  }


  @Override
  public SBase getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(childIndex + " < 0");
    }
    int pos = 0;
    if (isSetListOfColorDefinitions()) {
      if (pos == childIndex) {
        return getListOfColorDefinitions();
      }
      pos++;
    }
    if (isSetListOfGradientDefintions()) {
      if (pos == childIndex) {
        return getListOfGradientDefintions();
      }
      pos++;
    }
    if (isSetListOfLineEndings()) {
      if (pos == childIndex) {
        return getListOfLineEndings();
      }
      pos++;
    }
    if (isSetListOfLocalStyles()) {
      if (pos == childIndex) {
        return getListOfLocalStyles();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      "Index {0,number,integer} >= {1,number,integer}", childIndex,
      +Math.min(pos, 0)));
  }
  
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderInformationBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    return attributes;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderInformationBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    return isAttributeRead;
  }

}
