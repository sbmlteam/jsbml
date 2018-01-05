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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class LocalStyle extends Style {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 4976081641247006722L;

  /**
   * 
   */
  private List<String> idList;

  /**
   * Creates a new {@link LocalStyle} instance
   *
   */
  public LocalStyle() {
    super();
  }


  /**
   * Creates a LocalStyle instance with a group
   *
   * @param group the render group
   */
  public LocalStyle(RenderGroup group) {
    super(group);
  }

  /**
   * Creates a LocalStyle instance with a level and version.
   *
   * @param level the SBML level
   * @param version the SBML version
   * @param group the render group
   */
  public LocalStyle(int level, int version, RenderGroup group) {
    super(null, level, version, group);
  }

  /**
   * Creates a LocalStyle instance with an id, name, level, and version.
   *
   * @param id the id
   * @param level the SBML level
   * @param version the SBML version
   * @param group the render group
   */
  public LocalStyle(String id, int level, int version, RenderGroup group) {
    super(id, level, version, group);
  }


  /**
   * Creates a new {@link LocalStyle} instance, cloned from the given object.
   * 
   * @param localStyle the local style to be cloned.
   */
  public LocalStyle(LocalStyle localStyle) {
    super(localStyle);
    
    if (localStyle.isSetIDList()) {
      setIDList(new ArrayList<String>(localStyle.getIDList()));
    }
  }


  @Override
  public LocalStyle clone() {
    return new LocalStyle(this);
  }
  
  /* (non-Javadoc)
   * @see org.sbml.jsbml.AbstractSBase#getElementName()
   */
  @Override
  public String getElementName() {
    return RenderConstants.style;
  }


  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3301;
    int result = super.hashCode();
    result = prime * result + idList.hashCode();
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
    LocalStyle other = (LocalStyle) obj;
    if ((isSetIDList() != other.isSetId()) || (isSetIDList() && !idList.equals(other.idList))) {
      return false;
    }
    return true;
  }

  /**
   * @return the value of idList
   */
  public List<String> getIDList() {
    if (!isSetIDList()) {
      return idList = new ArrayList<String>();
    }
    return idList;
  }

  /**
   * @return whether idList is set
   */
  public boolean isSetIDList() {
    return idList != null;
  }

  /**
   * Set the value of idList
   * 
   * @param idList the id lists
   */
  public void setIDList(List<String> idList) {
    List<String> oldIDList = this.idList;
    this.idList = idList;
    firePropertyChange(RenderConstants.idList, oldIDList, this.idList);
  }

  /**
   * Unsets the variable idList
   * @return {@code true}, if idList was set before,
   *         otherwise {@code false}
   */
  public boolean unsetIDList() {
    if (isSetIDList()) {
      List<String> oldIDList = idList;
      idList = null;
      firePropertyChange(RenderConstants.idList, oldIDList, idList);
      return true;
    }
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Style#writeXMLAttributes()
   */
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();
    if (isSetIDList()) {
      attributes.remove(RenderConstants.idList);
      attributes.put(RenderConstants.shortLabel + ':' + RenderConstants.idList,
        XMLTools.arrayToWhitespaceSeparatedString(getIDList().toArray(new String[0])));
    }
    return attributes;
  }


  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.Style#readAttribute(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    if (!isAttributeRead) {
      isAttributeRead = true;
      if (attributeName.equals(RenderConstants.idList)) {
        setIDList(Arrays.asList(value.split(" ")));
      }
      else {
        isAttributeRead = false;
      }
    }
    return isAttributeRead;
  }

}
