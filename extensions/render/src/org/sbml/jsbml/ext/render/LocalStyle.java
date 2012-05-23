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


/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 08.05.2012
 */
public class LocalStyle extends Style {
	/**
   * 
   */
  private static final long serialVersionUID = 4976081641247006722L;
  
  // TODO List of ids or layouts? Type, setters and getters, add, remove
	private List<String> idList;
	
	/**
   * Creates a LocalStyle instance with a group
   * 
   * @param group
   */
	public LocalStyle(Group group){
	  super(group);
	  this.idList = new ArrayList<String>();
	}
	
	/**
   * Creates a LocalStyle instance with a level and version. 
   * 
   * @param level
   * @param version
   */
  public LocalStyle(int level, int version, Group group) {
    super(null, level, version, group);
    this.idList = new ArrayList<String>();
  }
	
	
	 /**
   * Creates a LocalStyle instance with an id, name, level, and version. 
   * 
   * @param id
   * @param level
   * @param version
   * @param group
   */
	public LocalStyle(String id, int level, int version, Group group){
	  super(id, level, version, group);
	  this.idList = new ArrayList<String>();
	}
	

  /**
   * @return the value of idList
   */
  public List<String> getIdList(){
    return this.idList;
  }
  
  /**
   * Set the value of idList
   */
  public void setIdList(ArrayList<String> idList){
    //ArrayList<String> oldIdList = this.idList;
    this.idList = idList;
    //TODO
    //firePropertyChange(constant_class.idList, oldIdList, this.idList);  
  }
		
}
