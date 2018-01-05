/*
 *
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

import java.util.HashMap;
import java.util.Map;

import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.ext.layout.GraphicalObject;

/**
 * Extends the {@link GraphicalObject} class with an extra attribute.
 * 
 * @author Nicolas Rodriguez
 * @since 1.2
 */
public class RenderGraphicalObjectPlugin extends AbstractRenderPlugin {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 6636572993878851570L;
  /**
   * 
   */
  private String objectRole;

  /**
   * Creates a new {@link RenderGraphicalObjectPlugin} instance.
   * 
   * @param graphicalObject the {@link GraphicalObject} that is extended
   */
  public RenderGraphicalObjectPlugin(GraphicalObject graphicalObject) {
    super(graphicalObject);
    initDefaults();
  }


  /**
   * Clone constructor
   * 
   * @param obj the {@link RenderGraphicalObjectPlugin} instance to clone
   */
  public RenderGraphicalObjectPlugin(RenderGraphicalObjectPlugin obj) {
    super(obj);

    if (obj.isSetObjectRole()) {
      setObjectRole(obj.objectRole);
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.AbstractRenderPlugin#clone()
   */
  @Override
  public RenderGraphicalObjectPlugin clone() {
    return new RenderGraphicalObjectPlugin(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.AbstractRenderPlugin#initDefaults()
   */
  @Override
  public void initDefaults() {
  }

  
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3041;
    int result = super.hashCode();
    result = prime * result
        + ((objectRole == null) ? 0 : objectRole.hashCode());
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
    RenderGraphicalObjectPlugin other = (RenderGraphicalObjectPlugin) obj;
    if (objectRole == null) {
      if (other.objectRole != null) {
        return false;
      }
    } else if (!objectRole.equals(other.objectRole)) {
      return false;
    }
    return true;
  }


  /**
   * Returns the value of {@link #objectRole}.
   *
   * @return the value of {@link #objectRole}.
   */
  public String getObjectRole() {
    if (isSetObjectRole()) {
      return objectRole;
    }
    // This is necessary if we cannot return null here. For variables of type String return an empty String if no value is defined.
    throw new PropertyUndefinedError(RenderConstants.objectRole, this);
  }

  /**
   * Returns whether {@link #objectRole} is set.
   *
   * @return whether {@link #objectRole} is set.
   */
  public boolean isSetObjectRole() {
    return objectRole != null;
  }

  /**
   * Sets the value of objectRole
   *
   * @param objectRole the value of objectRole to be set.
   */
  public void setObjectRole(String objectRole) {
    String oldObjectRole = this.objectRole;
    this.objectRole = objectRole;
    firePropertyChange(RenderConstants.objectRole, oldObjectRole, this.objectRole);
  }

  /**
   * Unsets the variable objectRole.
   *
   * @return {@code true} if objectRole was set before, otherwise {@code false}.
   */
  public boolean unsetObjectRole() {
    if (isSetObjectRole()) {
      String oldObjectRole = this.objectRole;
      this.objectRole = null;
      firePropertyChange(RenderConstants.objectRole, oldObjectRole, this.objectRole);
      return true;
    }
    return false;
  }

  
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = new HashMap<String, String>();

    if (isSetObjectRole()) {
      attributes.put(RenderConstants.shortLabel + ":" + RenderConstants.objectRole, getObjectRole());
    }

    return attributes;
  }

  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) 
  {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);

    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals(RenderConstants.objectRole)) {
        setObjectRole(value);
      }
      else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }
}
