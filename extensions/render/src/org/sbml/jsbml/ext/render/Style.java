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

import java.util.ArrayList;
import java.util.List;

import org.sbml.jsbml.AbstractSBase;
import org.sbml.jsbml.LevelVersionError;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 08.05.2012
 */
// TODO Getter, Setter, isSet
public class Style extends AbstractSBase {  
  /**
   * 
   */
  private static final long serialVersionUID = -4660813321564690910L;
  
  
  protected String id;
	protected List<String> typeList;
	protected List<String> roleList;
	protected Group group;
	

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


  /**
   * clones this class
   */
  public Style clone() {
    return new Style(this);
  }


  /**
   * Initializes the default values using the namespace.
   */
  public void initDefaults() {
    addNamespace(RenderConstants.namespaceURI);
    this.roleList = null;
    this.typeList = null;
  }

  public String toString() {
    // TODO Auto-generated method stub
    return null;
  }
  
  
  /**
   * @return the value of id
   */
  public String getId() {
    return this.id;
  }

  /**
   * Set the value of id
   */
  public void setId(String id) {
    String oldId = this.id;
    this.id = id;
    firePropertyChange(RenderConstants.id, oldId, this.id);
  }
  
  /**
   * @return the value of group
   */
  public Group getGroup() {
    return this.group;
  }
  
  /**
   * Set the value of group
   */
  public void setGroup(Group group) {
    Group oldGroup = this.group;
    this.group = group;
    firePropertyChange(RenderConstants .group, oldGroup, this.group);
  }
  
  /**
   * @return the value of typeList
   */
  public List<String> getTypeList() {
    return this.typeList;    
  }
  
  /**
   * Set the value of typeList
   */
  public void setTypeList(List<String> typeList) {
    List<String> oldTypeList = this.typeList;
    this.typeList = typeList;
    firePropertyChange(RenderConstants.typeList, oldTypeList, this.typeList);
  }
  
  /**
   * @return the value of roleList
   */
  public List<String> getRoleList() {
    return this.roleList;
  }
  
  /**
   * Set the value of roleList
   */
  public void setRoleList(ArrayList<String> roleList) {
    List<String> oldRoleList = this.roleList;
    this.roleList = roleList;
    firePropertyChange(RenderConstants.roleList, oldRoleList, this.roleList);  
  }
}
