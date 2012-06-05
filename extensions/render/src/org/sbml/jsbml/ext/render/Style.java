/*
 * $Id$
 * $URL:
 * https://jsbml.svn.sourceforge.net/svnroot/jsbml/trunk/extensions/render/
 * src/org/sbml/jsbml/ext/render/Style.java $
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 * Copyright (C) 2009-2012 jointly by the following organizations:
 * 1. The University of Tuebingen, Germany
 * 2. EMBL European Bioinformatics Institute (EBML-EBI), Hinxton, UK
 * 3. The California Institute of Technology, Pasadena, CA, USA
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
  protected String[] roleList;
  protected String[] typeList;


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
    super(id, level, version);
    if (getLevelAndVersion().compareTo(
      Integer.valueOf(RenderConstants.MIN_SBML_LEVEL),
      Integer.valueOf(RenderConstants.MIN_SBML_VERSION)) < 0) {
      throw new LevelVersionError(getElementName(), level, version);
    }
    this.group = group;
    initDefaults();
  }


  /**
   * Clone constructor
   */
  public Style(Style obj) {
    super(obj);
    this.roleList = obj.roleList;
    this.typeList = obj.typeList;
    this.group = obj.group;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#clone()
   */
  // @Override
  @Override
  public Style clone() {
    return new Style(this);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
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


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
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
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    addNamespace(RenderConstants.namespaceURI);
    this.roleList = null;
    this.typeList = null;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  // @Override
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
   * Set the value of group
   */
  public void setGroup(Group group) {
    Group oldGroup = this.group;
    this.group = group;
    firePropertyChange(RenderConstants.group, oldGroup, this.group);
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#toString()
   */
  @Override
  public String toString() {
    // TODO Auto-generated method stub
    return null;
  }


  /**
   * Unsets the variable group
   * 
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
   * @return the value of roleList
   */
  public String[] getRoleList() {
    if (isSetRoleList()) {
      return roleList;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.roleList, this);
  }


  /**
   * @return whether roleList is set 
   */
  public boolean isSetRoleList() {
    return this.roleList != null;
  }


  /**
   * Set the value of roleList
   */
  public void setRoleList(String[] roleList) {
    String[] oldRoleList = this.roleList;
    this.roleList = roleList;
    firePropertyChange(RenderConstants.roleList, oldRoleList, this.roleList);
  }
  
  
  /**
   * @return the value of typeList
   */
  public String[] getTypeList() {
    if (isSetTypeList()) {
      return typeList;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.typeList, this);
  }


  /**
   * @return whether typeList is set 
   */
  public boolean isSetTypeList() {
    return this.typeList != null;
  }


  /**
   * Set the value of typeList
   */
  public void setTypeList(String[] typeList) {
    String[] oldTypeList = this.typeList;
    this.typeList = typeList;
    firePropertyChange(RenderConstants.typeList, oldTypeList, this.typeList);
  }


  /**
   * Unsets the variable typeList 
   * @return <code>true</code>, if typeList was set before, 
   *         otherwise <code>false</code>
   */
  public boolean unsetTypeList() {
    if (isSetTypeList()) {
      String[] oldTypeList = this.typeList;
      this.typeList = null;
      firePropertyChange(RenderConstants.typeList, oldTypeList, this.typeList);
      return true;
    }
    return false;
  }


  /**
   * Unsets the variable roleList 
   * @return <code>true</code>, if roleList was set before, 
   *         otherwise <code>false</code>
   */
  public boolean unsetRoleList() {
    if (isSetRoleList()) {
      String[] oldRoleList = this.roleList;
      this.roleList = null;
      firePropertyChange(RenderConstants.roleList, oldRoleList, this.roleList);
      return true;
    }
    return false;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetRoleList()) {
      attributes.remove(RenderConstants.roleList);
      attributes.put(RenderConstants.shortLabel + ":" + RenderConstants.roleList,
        XMLTools.arrayToWhitespaceSeparatedString(getRoleList()));
    }
    if (isSetTypeList()) {
      attributes.remove(RenderConstants.typeList);
      attributes.put(RenderConstants.shortLabel + ":" + RenderConstants.typeList,
        XMLTools.arrayToWhitespaceSeparatedString(getTypeList()));
    }
    return attributes;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String,
   * java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(RenderConstants.roleList)) {
        setRoleList(value.split(":"));
      }
      else if (attributeName.equals(RenderConstants.typeList)) {
        setTypeList(value.split(":"));
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }
}
