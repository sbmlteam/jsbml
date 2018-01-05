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
import org.sbml.jsbml.util.filters.NameFilter;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class LocalRenderInformation extends RenderInformationBase {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = -8056565578647428405L;

  /**
   * 
   */
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
   * @param obj
   */
  public LocalRenderInformation(LocalRenderInformation obj) {
    super(obj);

    if (obj.isSetListOfLocalStyles()) {
      setListOfLocalStyles(obj.listOfLocalStyles.clone());
    }
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
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;

    listOfLocalStyles = null;
  }



  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3229;
    int result = super.hashCode();
    result = prime * result
        + ((listOfLocalStyles == null) ? 0 : listOfLocalStyles.hashCode());
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
    LocalRenderInformation other = (LocalRenderInformation) obj;
    if (listOfLocalStyles == null) {
      if (other.listOfLocalStyles != null) {
        return false;
      }
    } else if (!listOfLocalStyles.equals(other.listOfLocalStyles)) {
      return false;
    }
    return true;
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

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getElementName()
   */
  @Override
  public String getElementName() {
    return RenderConstants.localRenderInformation;
  }

  /**
   * @return the listOfLocalStyles
   */
  public ListOf<LocalStyle> getListOfLocalStyles() {
    if (!isSetListOfLocalStyles()) {
      listOfLocalStyles = new ListOf<LocalStyle>(getLevel(), getVersion());
      listOfLocalStyles.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'render'
      listOfLocalStyles.setPackageName(null);
      listOfLocalStyles.setPackageName(RenderConstants.shortLabel);
      listOfLocalStyles.setSBaseListType(ListOf.Type.other);
      listOfLocalStyles.setOtherListName(RenderConstants.listOfLocalStyles);

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

    if (listOfLocalStyles != null) {
      listOfLocalStyles.setPackageVersion(-1);
      // changing the ListOf package name from 'core' to 'render'
      listOfLocalStyles.setPackageName(null);
      listOfLocalStyles.setPackageName(RenderConstants.shortLabel);
      listOfLocalStyles.setSBaseListType(ListOf.Type.other);
      listOfLocalStyles.setOtherListName(RenderConstants.listOfLocalStyles);

      registerChild(this.listOfLocalStyles);
    }
  }

  /**
   * @return {@code true}, if listOfLocalStyles contained at least one element,
   *         otherwise {@code false}
   */
  public boolean unsetListOfLocalStyles() {
    if (isSetListOfLocalStyles()) {
      ListOf<LocalStyle> oldLocalStyles = listOfLocalStyles;
      listOfLocalStyles = null;
      oldLocalStyles.fireNodeRemovedEvent();
      return true;
    }
    return false;
  }

  /**
   * @param localStyle
   * @return
   */
  public boolean addLocalStyle(LocalStyle localStyle) {
    return getListOfLocalStyles().add(localStyle);
  }

  /**
   * @param localStyle
   * @return
   */
  public boolean removeLocalStyle(LocalStyle localStyle) {
    if (isSetListOfLocalStyles()) {
      return getListOfLocalStyles().remove(localStyle);
    }
    return false;
  }

  /**
   * @param i
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
    if (isSetListOfLocalStyles()) {
      if (pos == childIndex) {
        return getListOfLocalStyles();
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

  /**
   * 
   * @param id
   * @return
   */
  public ColorDefinition getColorDefinition(String id) {
    return isSetListOfColorDefinitions() ? listOfColorDefinitions.firstHit(new NameFilter(id)) : null;
  }

  /**
   * 
   * @return
   */
  public int getColorDefinitionCount() {
    return isSetListOfColorDefinitions() ? listOfColorDefinitions.size() : 0;
  }

  /**
   * 
   * @return
   */
  public int getLocalStyleCount() {
    return isSetListOfLocalStyles() ? listOfLocalStyles.size() : 0;
  }

}
