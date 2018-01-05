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
package org.sbml.jsbml.ext.render;

import java.text.MessageFormat;

import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.ListOf;
import org.sbml.jsbml.SBase;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class GlobalRenderInformation extends RenderInformationBase {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 855680727119080659L;

  /**
   * 
   */
  private ListOf<Style> listOfStyles;

  /**
   * Creates an GlobalRenderInformation instance
   */
  public GlobalRenderInformation() {
    super();
    initDefaults();
  }

  /**
   * Clone constructor
   * @param obj
   */
  public GlobalRenderInformation(GlobalRenderInformation obj) {
    super(obj);

    if (obj.isSetListOfStyles()) {
      setListOfStyles(obj.getListOfStyles().clone());
    }
  }

  /**
   * Creates a GlobalRenderInformation instance with a level and version.
   * 
   * @param level
   * @param version
   */
  public GlobalRenderInformation(int level, int version) {
    this(null, null, level, version);
  }

  /**
   * Creates a {@link GlobalRenderInformation} instance with an id.
   * 
   * @param id
   */
  public GlobalRenderInformation(String id) {
    super(id);
    initDefaults();
  }

  /**
   * Creates a {@link GlobalRenderInformation} instance with an id, level, and version.
   * 
   * @param id
   * @param level
   * @param version
   */
  public GlobalRenderInformation(String id, int level, int version) {
    this(id, null, level, version);
  }

  /**
   * Creates a {@link GlobalRenderInformation} instance with an id, name, level, and version.
   * 
   * @param id
   * @param name
   * @param level
   * @param version
   */
  public GlobalRenderInformation(String id, String name, int level, int version) {
    super(id, name, level, version);
    if (getLevelAndVersion().compareTo(Integer.valueOf(RenderConstants.MIN_SBML_LEVEL),
      Integer.valueOf(RenderConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    initDefaults();
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderInformationBase#clone()
   */
  @Override
  public GlobalRenderInformation clone() {
    return new GlobalRenderInformation(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderInformationBase#initDefaults()
   */
  @Override
  public void initDefaults() {
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3067;
    int result = super.hashCode();
    result = prime * result
        + ((listOfStyles == null) ? 0 : listOfStyles.hashCode());
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
    GlobalRenderInformation other = (GlobalRenderInformation) obj;
    if (listOfStyles == null) {
      if (other.listOfStyles != null) {
        return false;
      }
    } else if (!listOfStyles.equals(other.listOfStyles)) {
      return false;
    }
    return true;
  }


  /**
   * @return {@code true}, if listOfStyles contains at least one element,
   *         otherwise {@code false}
   */
  public boolean isSetListOfStyles() {
    if ((listOfStyles == null) || listOfStyles.isEmpty()) {
      return false;
    }
    return true;
  }


  /**
   * @return the listOfStyles
   */
  public ListOf<Style> getListOfStyles() {
    if (!isSetListOfStyles()) {
      listOfStyles = new ListOf<Style>(getLevel(), getVersion());
      listOfStyles.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'render'
      listOfStyles.setPackageName(null);
      listOfStyles.setPackageName(RenderConstants.shortLabel);
      listOfStyles.setSBaseListType(ListOf.Type.other);
      listOfStyles.setOtherListName(RenderConstants.listOfStyles);
      
      registerChild(listOfStyles);
    }
    return listOfStyles;
  }


  /**
   * @param listOfStyles
   */
  public void setListOfStyles(ListOf<Style> listOfStyles) {
    unsetListOfStyles();
    this.listOfStyles = listOfStyles;
    
    if (listOfStyles != null) {
      listOfStyles.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'render'
      listOfStyles.setPackageName(null);
      listOfStyles.setPackageName(RenderConstants.shortLabel);
      listOfStyles.setSBaseListType(ListOf.Type.other);
      listOfStyles.setOtherListName(RenderConstants.listOfStyles);
    
      registerChild(this.listOfStyles);
    }
  }


  /**
   * @return {@code true}, if listOfStyles contained at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfStyles() {
    if (isSetListOfStyles()) {
      ListOf<Style> oldStyles = listOfStyles;
      listOfStyles = null;
      oldStyles.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }


  /**
   * @param style
   * @return
   */
  public boolean addStyle(Style style) {
    return getListOfStyles().add(style);
  }


  /**
   * @param style
   * @return
   */
  public boolean removeStyle(Style style) {
    if (isSetListOfStyles()) {
      return getListOfStyles().remove(style);
    }
    return false;
  }

  /**
   * @param i
   */
  public void removeStyle(int i) {
    if (!isSetListOfStyles()) {
      throw new IndexOutOfBoundsException(Integer.toString(i));
    }
    getListOfStyles().remove(i);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderInformationBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();
    if (isSetListOfStyles()) {
      count++;
    }
    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.RenderInformationBase#getChildAt(int)
   */
  @Override
  public SBase getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(resourceBundle.getString("IndexSurpassesBoundsException"), childIndex, 0));
    }
    int pos = 0;
    if (isSetListOfColorDefinitions()) {
      if (pos == childIndex) {
        return getListOfColorDefinitions();
      }
      pos++;
    }
    if (isSetListOfGradientDefinitions()) {
      if (pos == childIndex) {
        return getListOfGradientDefinitions();
      }
      pos++;
    }
    if (isSetListOfLineEndings()) {
      if (pos == childIndex) {
        return getListOfLineEndings();
      }
      pos++;
    }
    if (isSetListOfStyles()) {
      if (pos == childIndex) {
        return getListOfStyles();
      }
      pos++;
    }
    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), childIndex,
      Math.min(pos, 0)));
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
