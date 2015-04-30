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

import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.SBase;
import org.sbml.jsbml.ext.layout.BoundingBox;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @version $Rev$
 * @since 1.0
 * @date 08.05.2012
 */
public class LineEnding extends GraphicalPrimitive2D {
  /**
   * Generated serial version identifier
   */
  private static final long serialVersionUID = 938880502591638386L;
  /**
   * 
   */
  private Boolean enableRotationMapping;
  /**
   * 
   */
  private BoundingBox boundingBox;
  /**
   * 
   */
  private RenderGroup group;

  /**
   * Creates an LineEnding instance
   */
  public LineEnding() {
    super();
    initDefaults();
  }

  /**
   * Clone constructor
   * @param obj
   */
  public LineEnding(LineEnding obj) {
    super(obj);
    
    if (obj.isSetBoundingBox()) {
      setBoundingBox(obj.getBoundingBox().clone());
    }
    enableRotationMapping = obj.enableRotationMapping;
    if (obj.isSetGroup()) {
      setGroup(obj.getGroup().clone());
    }
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive2D#clone()
   */
  @Override
  public LineEnding clone() {
    return new LineEnding(this);
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive2D#initDefaults()
   */
  @Override
  public void initDefaults() {
    enableRotationMapping = true;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#getAllowsChildren()
   */
  @Override
  public boolean getAllowsChildren() {
    return false;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#getChildCount()
   */
  @Override
  public int getChildCount() {
    int count = 0;
    return count;
  }

  /* (non-Javadoc)
   * @see org.sbml.jsbml.ext.render.GraphicalPrimitive1D#getChildAt(int)
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

  /**
   * @return the value of boundingBox
   */
  public BoundingBox getBoundingBox() {
    if (isSetBoundingBox()) {
      return boundingBox;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.boundingBox, this);
  }

  /**
   * @return whether boundingBox is set
   */
  public boolean isSetBoundingBox() {
    return boundingBox != null;
  }

  /**
   * Set the value of boundingBox
   * @param boundingBox
   */
  public void setBoundingBox(BoundingBox boundingBox) {
    BoundingBox oldBoundingBox = this.boundingBox;
    this.boundingBox = boundingBox;
    firePropertyChange(RenderConstants.boundingBox, oldBoundingBox, this.boundingBox);
  }

  /**
   * Unsets the variable boundingBox
   * @return {@code true}, if boundingBox was set before,
   *         otherwise {@code false}
   */
  public boolean unsetBoundingBox() {
    if (isSetBoundingBox()) {
      BoundingBox oldBoundingBox = boundingBox;
      boundingBox = null;
      firePropertyChange(RenderConstants.boundingBox, oldBoundingBox, boundingBox);
      return true;
    }
    return false;
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
    RenderGroup oldGroup = this.group;
    this.group = group;
    firePropertyChange(RenderConstants.group, oldGroup, this.group);
  }

  /**
   * Unsets the variable group
   * @return {@code true}, if group was set before,
   *         otherwise {@code false}
   */
  public boolean unsetGroup() {
    if (isSetGroup()) {
      RenderGroup oldGroup = group;
      group = null;
      firePropertyChange(RenderConstants.group, oldGroup, group);
      return true;
    }
    return false;
  }

  /**
   * @return the value of enableRotationMapping
   */
  public boolean isEnableRotationMapping() {
    if (isSetEnableRotationMapping()) {
      return enableRotationMapping;
    }
    // This is necessary if we cannot return null here.
    throw new PropertyUndefinedError(RenderConstants.enableRotationMapping, this);
  }

  /**
   * @return whether enableRotationMapping is set
   */
  public boolean isSetEnableRotationMapping() {
    return enableRotationMapping != null;
  }

  /**
   * Set the value of enableRotationMapping
   * @param enableRotationMapping
   */
  public void setEnableRotationMapping(Boolean enableRotationMapping) {
    Boolean oldEnableRotationMapping = this.enableRotationMapping;
    this.enableRotationMapping = enableRotationMapping;
    firePropertyChange(RenderConstants.enableRotationMapping, oldEnableRotationMapping, this.enableRotationMapping);
  }

  /**
   * Unsets the variable {@link #enableRotationMapping}
   * @return {@code true}, if enableRotationMapping was set before,
   *         otherwise {@code false}
   */
  public boolean unsetEnableRotationMapping() {
    if (isSetEnableRotationMapping()) {
      Boolean oldEnableRotationMapping = enableRotationMapping;
      enableRotationMapping = null;
      firePropertyChange(RenderConstants.enableRotationMapping, oldEnableRotationMapping, enableRotationMapping);
      return true;
    }
    return false;
  }

}
