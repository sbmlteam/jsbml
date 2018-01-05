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
import java.util.Map;

import javax.swing.tree.TreeNode;

import org.sbml.jsbml.PropertyUndefinedError;
import org.sbml.jsbml.ext.layout.BoundingBox;
import org.sbml.jsbml.ext.layout.IBoundingBox;
import org.sbml.jsbml.util.StringTools;

/**
 * @author Eugen Netz
 * @author Alexander Diamantikos
 * @author Jakob Matthes
 * @author Jan Rudolph
 * @since 1.0
 */
public class LineEnding extends GraphicalPrimitive2D implements IBoundingBox {
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
   * 
   * @param obj the {@link LineEnding} instance to clone
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
    setPackageVersion(-1);
    packageName = RenderConstants.shortLabel;
  }

  @Override
  public boolean getAllowsChildren() {
    return true;
  }

  @Override
  public int getChildCount() {
    int count = super.getChildCount();

    if (isSetBoundingBox()) {
      count++;
    }
    if (isSetGroup()) {
      count++;
    }

    return count;
  }

  @Override
  public TreeNode getChildAt(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException(index + " < 0");
    }

    int count = super.getChildCount(), pos = 0;

    if (index < count) {
      return super.getChildAt(index);
    } else {
      index -= count;
    }

    if (isSetBoundingBox()) {
      if (pos == index) {
        return getBoundingBox();
      }
      pos++;
    }
    if (isSetGroup()) {
      if (pos == index) {
        return getGroup();
      }
      pos++;
    }

    throw new IndexOutOfBoundsException(
        MessageFormat.format("Index {0,number,integer} >= {1,number,integer}",
            index, Math.min(pos, 0)));
  }

  @Override
  public BoundingBox createBoundingBox() {
    setBoundingBox(new BoundingBox());
    return boundingBox;
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
   * 
   * @param boundingBox the value of boundingBox
   */
  public void setBoundingBox(BoundingBox boundingBox) {
    BoundingBox oldBoundingBox = this.boundingBox;
    this.boundingBox = boundingBox;
    firePropertyChange(RenderConstants.boundingBox, oldBoundingBox, this.boundingBox);
    registerChild(boundingBox);
  }

  /**
   * Unsets the variable boundingBox
   */
  public void unsetBoundingBox() {
    if (isSetBoundingBox()) {
      BoundingBox oldBoundingBox = boundingBox;
      boundingBox = null;
      firePropertyChange(RenderConstants.boundingBox, oldBoundingBox, boundingBox);
    }
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
   * Sets the value of group
   * 
   * @param group the value of group
   */
  public void setGroup(RenderGroup group) {
    RenderGroup oldGroup = this.group;
    this.group = group;
    firePropertyChange(RenderConstants.group, oldGroup, this.group);
    registerChild(group);
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
   * Sets the value of enableRotationMapping
   * 
   * @param enableRotationMapping the value of enableRotationMapping
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

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 3121;
    int result = super.hashCode();
    result = prime * result
        + ((boundingBox == null) ? 0 : boundingBox.hashCode());
    result = prime * result + ((enableRotationMapping == null) ? 0
        : enableRotationMapping.hashCode());
    result = prime * result + ((group == null) ? 0 : group.hashCode());
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
    LineEnding other = (LineEnding) obj;
    if (boundingBox == null) {
      if (other.boundingBox != null) {
        return false;
      }
    } else if (!boundingBox.equals(other.boundingBox)) {
      return false;
    }
    if (enableRotationMapping == null) {
      if (other.enableRotationMapping != null) {
        return false;
      }
    } else if (!enableRotationMapping.equals(other.enableRotationMapping)) {
      return false;
    }
    if (group == null) {
      if (other.group != null) {
        return false;
      }
    } else if (!group.equals(other.group)) {
      return false;
    }
    return true;
  }
  
  
  @Override
  public Map<String, String> writeXMLAttributes() {
    Map<String, String> attributes = super.writeXMLAttributes();

    if (isSetId()) {
      attributes.remove("id");
      attributes.put(RenderConstants.shortLabel + ":id", getId());
    }
    if (isSetEnableRotationMapping()) {
      attributes.put(RenderConstants.shortLabel + ":" + RenderConstants.enableRotationMapping, enableRotationMapping.toString());
    }
    return attributes;
  }

  @Override
  public boolean readAttribute(String attributeName, String prefix, String value) {
    boolean isAttributeRead = super.readAttribute(attributeName, prefix, value);
    
    if (!isAttributeRead) {
      isAttributeRead = true;

      if (attributeName.equals("id")) {
        setId(value);
      }
      else if (attributeName.equals(RenderConstants.enableRotationMapping)) {
        setEnableRotationMapping(StringTools.parseSBMLBoolean(value));
      }
      else {
        isAttributeRead = false;
      }
    }

    return isAttributeRead;
  }
}
