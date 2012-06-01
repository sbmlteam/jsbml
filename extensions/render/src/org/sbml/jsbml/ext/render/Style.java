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
import java.util.List;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.LevelVersionError;
import org.sbml.jsbml.PropertyUndefinedError;
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
public class Style extends AbstractNamedSBase {
  /**
   *
   */
  private static final long serialVersionUID = -4660813321564690910L;


  protected Group group;
	protected String id;
	protected List<String> roleList;
	protected List<String> typeList;


  /**
   * Creates a Style instance with a group
   *
   * @param group
   */
  public Style(Group group) {
    super();
    this.group = group;
    initDefaults();
  }

  /**
   * Creates a Style instance with a level and version.
   *
   * @param level
   * @param version
   */
  public Style(int level, int version, Group group) {
    this(null, level, version, group);
  }

  /**
   * Creates a Style instance with an id, name, level, and version.
   *
   * @param id
   * @param level
   * @param version
   * @param group
   */
  public Style(String id, int level, int version, Group group) {
    super(level, version);
    if (getLevelAndVersion().compareTo(Integer.valueOf(RenderConstants.MIN_SBML_LEVEL),
      Integer.valueOf(RenderConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    this.id = id;
    this.group = group;
    initDefaults();
  }

  /**
   * Clone constructor
   */
  public Style(Style obj) {
    super(obj);
    this.id = obj.id;
    this.roleList = obj.roleList;
    this.typeList = obj.typeList;
    this.group = obj.group;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  //@Override
  @Override
  public Style clone() {
    return new Style(this);
  }

  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  @Override
  public SBase getChildAt(int childIndex) {
    if (childIndex < 0) {
      throw new IndexOutOfBoundsException(childIndex + " < 0");
    }
    int pos = 0;
    throw new IndexOutOfBoundsException(MessageFormat.format(
      "Index {0,number,integer} >= {1,number,integer}", childIndex,
      +Math.min(pos, 0)));
  }


  @Override
  public int getChildCount() {
    int count = 0;
    return count;
  }


  /**
   * @return the value of group
   */
  public Group getGroup() {
    if (isSetGroup()) {
      return group;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.group, this);
  }


  /**
   * @return the value of typeList
   */
  public List<String> getTypeList() {
    if (isSetTypeList()) {
      return typeList;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.typeList, this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    addNamespace(RenderConstants.namespaceURI);
    this.roleList = null;
    this.typeList = null;
  }


  /**
   *
   */
  //@Override
  public boolean isIdMandatory() {
    return true;
  }


  /**
   * @return whether group is set
   */
  public boolean isSetGroup() {
    return this.group != null;
  }


  /**
   * @return whether typeList is set
   */
  public boolean isSetTypeList() {
    return this.typeList != null;
  }


  /**
   * Set the value of group
   */
  public void setGroup(Group group) {
    Group oldGroup = this.group;
    this.group = group;
    firePropertyChange(RenderConstants.group, oldGroup, this.group);
  }


  /**
   * Set the value of typeList
   */
  public void setTypeList(List<String> typeList) {
    List<String> oldTypeList = this.typeList;
    this.typeList = typeList;
    firePropertyChange(RenderConstants.typeList, oldTypeList, this.typeList);
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#toString()
   */
  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return null;
  }


  /**
   * Unsets the variable group
   * @return <code>true</code>, if group was set before,
   *         otherwise <code>false</code>
   */
  public boolean unsetGroup() {
    if (isSetGroup()) {
      Group oldGroup = this.group;
      this.group = null;
      firePropertyChange(RenderConstants.group, oldGroup, this.group);
      return true;
    }
    return false;
  }

  /**
   * Unsets the variable typeList
   * @return <code>true</code>, if typeList was set before,
   *         otherwise <code>false</code>
   */
  public boolean unsetTypeList() {
    if (isSetTypeList()) {
      List<String> oldTypeList = this.typeList;
      this.typeList = null;
      firePropertyChange(RenderConstants.typeList, oldTypeList, this.typeList);
      return true;
    }
    return false;
  }

}
