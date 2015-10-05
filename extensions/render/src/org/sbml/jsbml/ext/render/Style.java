/*
 * $Id$
 * $URL$
 * ----------------------------------------------------------------------------
 * This file is part of JSBML. Please visit <http://sbml.org/Software/JSBML>
 * for the latest version of JSBML and more information about SBML.
 *
 * Copyright (C) 2009-2015 jointly by the following organizations:
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
import java.util.Arrays;
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.AbstractNamedSBase;
import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.UniqueNamedSBase;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 08.05.2012
 */
public class Style extends AbstractNamedSBase implements UniqueNamedSBase {

  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = -4660813321564690910L;
  /**
   * 
   */
  protected RenderGroup group;
  /**
   * 
   */
  protected String[] roleList;
  /**
   * 
   */
  protected String[] typeList;

  /**
   * Creates a Style instance with a group
   */
  public Style() {
    super();
    initDefaults();
  }

  /**
   * Creates a Style instance with a group
   * 
   * @param group
   */
  public Style(RenderGroup group) {
    super();
    setGroup(group);
    initDefaults();
  }


  /**
   * Creates a Style instance with a level and version.
   * 
   * @param level
   * @param version
   * @param group
   */
  public Style(int level, int version, RenderGroup group) {
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
  public Style(String id, int level, int version, RenderGroup group) {
    super(id, level, version);

    // Removed to potentially support SBML Level 2 Render
    //    if (getLevelAndVersion().compareTo(
    //      Integer.valueOf(RenderConstants.MIN_SBML_LEVEL),
    //      Integer.valueOf(RenderConstants.MIN_SBML_VERSION)) < 0) {
    //      throw new LevelVersionError(getElementName(), level, version);
    //    }
    initDefaults();
    setGroup(group);
  }


  /**
   * Clone constructor
   * @param obj
   */
  public Style(Style obj) {
    super(obj);
    roleList = obj.roleList; // TODO - do a proper copy of the arrays
    typeList = obj.typeList;

    if (obj.isSetGroup()) {
      setGroup(obj.getGroup().clone());
    }
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
    return true;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildAt(int)
   */
  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(MessageFormat.format(
        resourceBundle.getString("IndexSurpassesBoundsException"), index, 0));
    }
    int count = super.getChildCount(), pos = 0;
    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }

    if (isSetGroup()) {
      if (pos == index) {
        return getGroup();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(MessageFormat.format(
      resourceBundle.getString("IndexExceedsBoundsException"), index,
      Math.min(pos, 0)));
  }

  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = super.getChildCount();

    if (isSetGroup()) {
      count++;
    }

    return count;
  }


  /**
   * @return the value of group
   */
  public RenderGroup getGroup() {
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
    setNamespace(RenderConstants.namespaceURI); // TODO - removed once the mechanism are in place to set package version and namespace
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;
    roleList = null;
    typeList = null;
  }


  /*
   * (non-Javadoc)
   * @see org.sbml.jsbml.NamedSBase#isIdMandatory()
   */
  // @Override
  @Override
  public boolean isIdMandatory() {
    return true;
  }


  /**
   * @return whether group is set
   */
  public boolean isSetGroup() {
    return group != null;
  }


  /**
   * Set the value of group
   * @param group
   */
  public void setGroup(RenderGroup group) {
    unsetGroup();
    this.group = group;
    registerChild(group);

  }


  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Style [group=" + group + ", roleList=" + Arrays.toString(roleList)
        + ", typeList=" + Arrays.toString(typeList) + "]";
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3083;
    int result = super.hashCode();
    result = prime * result + ((group == null) ? 0 : group.hashCode());
    result = prime * result + Arrays.hashCode(roleList);
    result = prime * result + Arrays.hashCode(typeList);
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
    Style other = (Style) obj;
    if (group == null) {
      if (other.group != null) {
        return false;
      }
    } else if (!group.equals(other.group)) {
      return false;
    }
    if (!Arrays.equals(roleList, other.roleList)) {
      return false;
    }
    if (!Arrays.equals(typeList, other.typeList)) {
      return false;
    }
    return true;
  }

  /**
   * Unsets the variable group
   * 
   * @return {@code true}, if group was set before,
   *         otherwise {@code false}
   */
  public boolean unsetGroup() {
    if (isSetGroup()) {
      RenderGroup oldGroup = group;
      group = null;
      oldGroup.fireNodeRemovedEvent();
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
    return roleList != null;
  }


  /**
   * Set the value of roleList
   * @param roleList
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
    return typeList != null;
  }


  /**
   * Set the value of typeList
   * @param typeList
   */
  public void setTypeList(String[] typeList) {
    String[] oldTypeList = this.typeList;
    this.typeList = typeList;
    firePropertyChange(RenderConstants.typeList, oldTypeList, this.typeList);
  }


  /**
   * Unsets the variable typeList
   * @return {@code true}, if typeList was set before,
   *         otherwise {@code false}
   */
  public boolean unsetTypeList() {
    if (isSetTypeList()) {
      String[] oldTypeList = typeList;
      typeList = null;
      firePropertyChange(RenderConstants.typeList, oldTypeList, typeList);
      return true;
    }
    return false;
  }


  /**
   * Unsets the variable roleList
   * @return {@code true}, if roleList was set before,
   *         otherwise {@code false}
   */
  public boolean unsetRoleList() {
    if (isSetRoleList()) {
      String[] oldRoleList = roleList;
      roleList = null;
      firePropertyChange(RenderConstants.roleList, oldRoleList, roleList);
      return true;
    }
    return false;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetRoleList()) {
      attributes.remove(RenderConstants.roleList);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.roleList,
        XMLTools.arrayToWhitespaceSeparatedString(getRoleList()));
    }
    if (isSetTypeList()) {
      attributes.remove(RenderConstants.typeList);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.typeList,
        XMLTools.arrayToWhitespaceSeparatedString(getTypeList()));
    }
    return attributes;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractNamedSBase#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(RenderConstants.roleList)) {
        setRoleList(value.split(" "));
      }
      else if (attributeName.equals(RenderConstants.typeList)) {
        setTypeList(value.split(" "));
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }
}
